package com.kh.spring.board.controller;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.kh.spring.board.model.service.BoardService;
import com.kh.spring.board.model.vo.Attachment;
import com.kh.spring.board.model.vo.Board;
import com.kh.spring.common.util.HelloSpringUtils;

import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping("/board")
@Slf4j
public class BoardController {
	
	@Autowired
	private BoardService boardService;
	
	/**
	 * pageContext - request - session - application
	 * - 빈을 관리하는 스프링의 servlet-context가 아니다.
	 */
	@Autowired
	private ServletContext application;
	
	@Autowired
	private ResourceLoader resourceLoader;
	
	@GetMapping(
			value = "urlResource.do",
			produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
	@ResponseBody
	public Resource urlResource(HttpServletResponse response) {
		String location = "https://www.w3chools.com/tags/att_a_download.asp";
		Resource resource = resourceLoader.getResource(location);
		response.addHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=att_a_download.html");
		
		return resource;
	}
	
	/**
	 * Resource 각 종류별 자원을 처리하기 위한 추상체
	 * 	- 웹상의 자원 UrlResource
	 * 	- classpath 파일 ClassPathResource
	 *  - 서버컴퓨터파일 FileSystemResource
	 *  - 웹루트디렉토리 파일 ServletContextResource
	 *  - 입력스트림 자원 InputStreamResource
	 * @throws UnsupportedEncodingException 
	 *  
	 *  @ResponseBody 리턴된 객체를 응답메세지에 직접출력
	 */
	@GetMapping(
			value = "/fileDownload.do",
			produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
	@ResponseBody
	public Resource fileDownload(@RequestParam int no, HttpServletResponse response) throws UnsupportedEncodingException {
		Attachment attach = boardService.selectOneAttachment(no);
		log.debug("attach = {}", attach);
		
		// 다운로드받을 파일 경로
		String saveDirectory = application.getRealPath("/resources/upload/board");
		File downFile = new File(saveDirectory, attach.getRenamedFilename());
		String location = "file:" + downFile; // file객체의 toString은 절대경로로 오버라이드되어 있다.
		log.debug("location = {}", location);
		Resource resource = resourceLoader.getResource(location);
		
		// 헤더 설정
		String filename = new String(attach.getOriginalFilename().getBytes("utf-8"), "iso-8859-1");	// 한글파일 깨짐방지
		response.addHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + filename);
		
		return resource;
	}
	
	@GetMapping("/boardForm.do")
	public void boardForm() {}
	
	@GetMapping("/boardDetail.do")
	public void boardDetail(@RequestParam int no, Model model) {
//		Board board = boardService.selectOneBoard(no);
		Board board = boardService.selectOneBoardCollection(no);
		log.debug("board = {}", board);
		model.addAttribute("board", board);
	}
	
	@PostMapping("/boardEnroll.do")
	public String boardEnroll(
			Board board, 
			@RequestParam(name="upFile", required=false) MultipartFile[] upFiles, 
			RedirectAttributes redirectAtt) throws IllegalStateException, IOException {
		log.debug("board = {}", board);
		String saveDirectory = application.getRealPath("/resources/upload/board"); // 저장경로
		List<Attachment> attachments = new ArrayList<>();
		
		// 1. 첨부파일을 서버컴퓨터에 저장 : rename
		// 2. 저장된 파일의 정보 -> Attachment객체 -> DB attachment 테이블에 insert
		for(int i = 0; i < upFiles.length; i++) {
			MultipartFile upFile = upFiles[i];
			if(!upFile.isEmpty()) {
				
				// 1. 저장 경로 | renamedFilename
				String originalFilename = upFile.getOriginalFilename();
				String renamedFilename = HelloSpringUtils.rename(originalFilename);
				File dest = new File(saveDirectory, renamedFilename);
				upFile.transferTo(dest);
				
				// 2. 
				Attachment attach = new Attachment();
				attach.setOriginalFilename(originalFilename);
				attach.setRenamedFilename(renamedFilename);
				attachments.add(attach);
			}
		}
		// 게시물 올리기전 첨부파일이 있다면 attachments에 추가
		if(!attachments.isEmpty())
			board.setAttachments(attachments);
		log.debug("board = {}", board);
		
		int result = boardService.insertBoard(board); // 첨부파일 추가된 상태
		String msg = "게시물 등록 성공!";
		redirectAtt.addFlashAttribute("msg", msg);
		
		return "redirect:/board/boardList.do";
	}
	
	@GetMapping("/boardList.do")
	public void boardList(
			@RequestParam(defaultValue = "1") int cPage, 
			Model model, 
			HttpServletRequest request) {
		
		// 1. content 영역
		int limit = 10;
		int offset = (cPage - 1) * limit;
		
		Map<String, Object> param = new HashMap<>();
		param.put("offset", offset);
		param.put("limit", limit);
		
		List<Board> list = boardService.selectBoardList(param);
		log.debug("list = {}", list);
		
		// 2. pagebar 영역
		int totalContent = boardService.selectTotalBoardCount(); // 전체 게시물 수
		String url = request.getRequestURI();
		String pagebar = HelloSpringUtils.getPagebar(cPage, limit, totalContent, url);
		
		model.addAttribute("list", list);
		model.addAttribute("pagebar", pagebar);
	}
	
}
