package com.kh.spring.member.controller;

import java.beans.PropertyEditor;
import java.util.Date;
import java.text.SimpleDateFormat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.kh.spring.member.model.service.MemberService;
import com.kh.spring.member.model.vo.Member;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
@RequestMapping("/member")
public class MemberController {
	
	@Autowired
	private MemberService memberService;
	
	@Autowired
	private BCryptPasswordEncoder bcryptPasswordEncoder;
//	@RequestMapping(value="memberLogin.do", method = RequestMethod.GET)
	@GetMapping("/memberLogin.do")
	public String memberLigin() {
		return "member/memberLogin";
	}
	
	@PostMapping("/memberLogin.do")
	public String memberLigin(
			@RequestParam String id, // @RequestParam : 필수값
			@RequestParam String password, 
			RedirectAttributes redirectAttr) { 
		// 인증(사용자가있는지 비교)
		Member member = memberService.selectOneMember(id);
		log.info("member = {}", member);
		
		if(member != null && bcryptPasswordEncoder.matches(password, member.getPassword())) {
			// 로그인 성공시
			redirectAttr.addFlashAttribute("msg", "로그인 성공!");
		}
		else {
			// 로그인 실패시
			redirectAttr.addFlashAttribute("msg", "아이디 또는 비밀번호가 일치하지 않습니다.");
		}
		return "redirect:/";
	}

	// 회원가입 제출 요청(GET)
	@GetMapping("memberEnroll.do")
	public String memberEnroll() {
		return "member/memberEnroll";
	}
	
	@PostMapping("/memberEnroll.do")
	public String memberEnroll(Member member, RedirectAttributes redirectAttr) {
		log.info("member= {}", member);
		// 비밀번호 암호화 처리
		String rawPassword = member.getPassword(); // 평문
		// 랜덤 salt값을 이용한 hashing처리.
		String encodedPassword = bcryptPasswordEncoder.encode(rawPassword); // 암호화 처리
		member.setPassword(encodedPassword);
		
		// 업무로직
		int result = memberService.insertMember(member);
		
		String msg = result > 0 ? "Member 등록 성공!" : "Member 등록 실패!";
		log.info("msg = {}", msg);
		
		// redirect후에 session의 속성을 참조할 수 있도록 한다.
		redirectAttr.addFlashAttribute("msg", "회원 가입 성공!");
		return "redirect:/";
	}
	
	
	// birthday 변환 문자열 -> Date객체
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		// boolean allowEmpty - true 빈문자열 ""인 경우 null 변환함. 빈문자열인 경우 오류남(false)
		PropertyEditor editor = new CustomDateEditor(sdf, true);
		// java.util.Date 변환시 editor객체 사용
		binder.registerCustomEditor(Date.class, editor);
	}
}
