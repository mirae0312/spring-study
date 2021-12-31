package com.kh.spring.board.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.kh.spring.board.model.service.BoardService;
import com.kh.spring.board.model.vo.Board;
import com.kh.spring.common.util.HelloSpringUtils;

import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping("/board")
@Slf4j
public class BoardController {
	
	@Autowired
	private BoardService boardService;
	
	@GetMapping("/boardForm.do")
	public void boardForm() {}
	
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
