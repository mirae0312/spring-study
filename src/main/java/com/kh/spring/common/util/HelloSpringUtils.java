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
		final int totalPage = (int) Math.ceil((double) totalContent / numPerPage);// totalPage구하는 공식
		final int pageStart = (cPage - 1) / pagebarSize * pagebarSize + 1;
		int pageEnd = pageStart + pagebarSize - 1;
		pageEnd = totalPage < pageEnd ? totalPage : pageEnd;
		int pageNo = pageStart;

		pagebar.append("<nav>\r\n" 
				+ "			  <ul class=\"pagination justify-content-center pagination-sm\">\r\n"
				+ "			    ");
		// [이전]
		if (pageNo == 1) {
			// 이전 영역 비활성화
			pagebar.append("<li class=\"page-item disabled\">\r\n"
					+ "			      <a class=\"page-link\" href=\"javascript:paging(" + (pageNo - 1)
					+ ");\" aria-label=\"Previous\">\r\n"
					+ "			        <span aria-hidden=\"true\">&laquo;</span>\r\n"
					+ "			        <span class=\"sr-only\">Previous</span>\r\n" + "			      </a>\r\n"
					+ "			    </li>");
		} else {
			// 이전 영역 활성화
			pagebar.append(
					"<li class=\"page-item\">\r\n" + "			      <a class=\"page-link\" href=\"javascript:paging("
							+ (pageNo - 1) + ");\" aria-label=\"Previous\">\r\n"
							+ "			        <span aria-hidden=\"true\">&laquo;</span>\r\n"
							+ "			        <span class=\"sr-only\">Previous</span>\r\n"
							+ "			      </a>\r\n" + "			    </li>");
		}

		// pageNo
		while (pageNo <= pageEnd) {
			if (pageNo == cPage) {
				// 현재페이지
				pagebar.append("<li class=\"page-item active\"><a class=\"page-link\" href=\"javascript:paging("
						+ pageNo + ")\">" + pageNo + "</a></li>\r\n");
			} else {
				// 현재페이지가 아닌 경우
				pagebar.append("<li class=\"page-item\"><a class=\"page-link\" href=\"javascript:paging(" + pageNo
						+ ")\">" + pageNo + "</a></li>\r\n");
			}

			pageNo++;
		}

		// [다음]
		if (pageNo > totalPage) {
			// 다음 페이지 비활성화
			pagebar.append("<li class=\"page-item disabled\">\r\n"
					+ "			      <a class=\"page-link\" href=\"#\" aria-label=\"Next\">\r\n"
					+ "			        <span aria-hidden=\"true\">&raquo;</span>\r\n"
					+ "			        <span class=\"sr-only\">Next</span>\r\n" + "			      </a>\r\n"
					+ "			    </li>\r\n" + "			  ");
		} else {
			// 다음 페이지 활성화
			pagebar.append("<li class=\"page-item\">\r\n"
					+ "			      <a class=\"page-link\" href=\"javascript:paging(" + pageNo
					+ ")\" aria-label=\"Next\">\r\n"
					+ "			        <span aria-hidden=\"true\">&raquo;</span>\r\n"
					+ "			        <span class=\"sr-only\">Next</span>\r\n" + "			      </a>\r\n"
					+ "			    </li>\r\n" + "			  ");
		}

		pagebar.append("			  </ul>\r\n" + "			</nav>\r\n" + "<script>"
				+ "const paging = (pageNo) => { location.href = `" + url + "${pageNo}`;  };" + "</script>");
		return pagebar.toString();
	}

}
