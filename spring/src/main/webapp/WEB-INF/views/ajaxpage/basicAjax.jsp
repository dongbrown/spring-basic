<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<c:set var="path" value="${pageContext.request.contextPath}"/>
	<!-- div id="result" 에 들어갈 부분만 -->
	<h3>점심메뉴</h3>
	<ul>
		<c:forEach var="l" items="${lunch }">
			<li>${l }</li>
		</c:forEach>
	</ul>



