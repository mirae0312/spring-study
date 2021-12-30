package com.kh.spring.common.util;

public class HelloSpringUtils {


	/**
	 * 
	 * cPage
	 * numPerPage
	 * totalContent
	 * url
	 * 
	 * totalPage 전체페이지
	 * pagebarSize 페이지바크기 5
	 * pageNo
	 * pageStart - pageEnd
	 * 
	 */
	public static String getPagebar(int cPage, int numPerPage, int totalContent, String url) {
		
		StringBuilder pagebar = new StringBuilder();
		url = url + "?cPage="; // pageNo 추가전 상태
		
		final int pagebarSize = 5;
		final int totalPage = (int)Math.ceil((double)totalContent / numPerPage);// totalPage구하는 공식
		final int pageStart = (cPage - 1) / pagebarSize * pagebarSize + 1;
		int pageEnd = pageStart + pagebarSize - 1;
		pageEnd = totalPage < pageEnd ? totalPage : pageEnd;
		int pageNo = pageStart;
		
		// [이전]
		if(pageNo == 1) {
			// 페이지가 1이라면 이전페이지 없음
		}
		else {
			pagebar.append("<a href='" + url + (pageNo - 1) + "'>prev</a>\n");
		}
		
		// pageNo
		while(pageNo <= pageEnd) {
			if(pageNo == cPage) {
				// 현재페이지
				pagebar.append("<span class='cPage'>" + cPage + "</span>\n");
			}
			else {
				// 현재페이지가 아니라면
				pagebar.append("<a href='" + url + pageNo + "'>" + pageNo + "</a>\n");
			}
			pageNo++;
		}
		
		// [다음]
		if(pageNo > totalPage) {
			
		}
		else {
			pagebar.append("<a href='" + url + pageNo + "'>next</a>\n");
		}
		
		return pagebar.toString();
	}

	
}
