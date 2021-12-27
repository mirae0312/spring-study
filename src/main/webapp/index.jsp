<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<fmt:requestEncoding value="utf-8" /> <!-- 한글꺠지는것 방지 -->
<jsp:include page="/WEB-INF/views/common/header.jsp">
	<jsp:param value="Hello spriiiiiiiiiing 헬로스프링" name="title"/>
</jsp:include>
		<img src="${pageContext.request.contextPath}/resources/images/logo-spring.png" id="center-image" alt="스프링로고" class="d-block mx-auto mt-5"/>
<jsp:include page="/WEB-INF/views/common/footer.jsp"/>
	