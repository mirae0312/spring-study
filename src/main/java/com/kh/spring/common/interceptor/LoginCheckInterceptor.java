package com.kh.spring.common.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.kh.spring.member.model.vo.Member;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class LoginCheckInterceptor extends HandlerInterceptorAdapter {
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		// 로그인 여부 체크
		HttpSession session = request.getSession();
		Member loginMember = (Member)session.getAttribute("loginMember");
		
		if(loginMember == null) {
			
			// 로그인 성공 후 이동페이지 session에 저장
			// /spring/board/boadDetail.do?no=123
			String next = request.getRequestURL().toString(); // http://localhost:9090/spring/board/boardDetail
			String queryString = request.getQueryString(); // no=123&query=abc
			if(queryString != null) next += "?" + queryString;
			
			session.setAttribute("next", next);
			response.sendRedirect(request.getContextPath() + "/member/memberLogin.do");
			return false;
		}
		
		return super.preHandle(request, response, handler);
	}
	
}
