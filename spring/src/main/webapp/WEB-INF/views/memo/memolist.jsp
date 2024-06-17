<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="springform" uri="http://www.springframework.org/tags/form" %>

<c:set var="path" value="${pageContext.request.contextPath}" />
<jsp:include page="/WEB-INF/views/common/header.jsp">
	<jsp:param name="title" value="" />
</jsp:include>
<style>
div#memo-container {
	width: 60%;
	margin: 0 auto;
}
</style>
<section id="content">
	<div id="memo-container">
		<springform:form modelAttribute="memo" action="${path}/memo/enrollmemo.do" class="form-inline"
			method="post">
			<springform:input path="memo" type="text" class="form-control col-sm-6" name="memo"
				placeholder="메모"  />&nbsp; 
			<springform:errors path="memo" cssClass="error"/>
			<springform:input path="password" type="password"
				class="form-control col-sm-2" name="password" maxlength="4"
				placeholder="비밀번호"  />&nbsp;
			<springform:errors path="password" cssClass="error"/>
			<button class="btn btn-outline-success" type="submit">저장</button>
		</springform:form>
	</div>
	<table class="table">
		<tr>
			<th scope="col">번호</th>
			<th scope="col">메모</th>
			<th scope="col">날짜</th>
			<th scope="col">수정</th>
			<th scope="col">삭제</th>
		</tr>
		<c:if test="${not empty memos}">
			<c:forEach items="${memos}" var="m">
				<tr>
					<td>${m.memoNo}</td>
					<td>${m.memo}</td>
					<td>${m.memoDate}</td>
					<td>
                  <button class="btn btn-outline-success" 
                  onclick="location.href='/memo/updatememoForm.do?memoNo=${m.memoNo}'">수정</button>

					</td>
					<td>
						<button class="btn btn-outline-danger"
							onclick="deleteMemo(${m.memoNo})">삭제</button>
					</td>
				</tr>
			</c:forEach>
		</c:if>
	</table>
	<div id="pageBar">${pageBar }</div>
</section>
<script>
function deleteMemo(memoNo) {
    if (confirm("정말 삭제하시겠습니까?")) {
        fetch('${path}/memo/deletememo.do?memoNo=' + memoNo, {
            method: 'DELETE'
        }).then(response => {
            if (response.ok) {
                alert("삭제 성공");
                location.reload(); // 페이지 새로고침
            } else {
                alert("삭제 실패");
            }
        }).catch(error => {
            console.error('Error:', error);
            alert("삭제 중 오류 발생");
        });
    }
}
</script>


<jsp:include page="/WEB-INF/views/common/footer.jsp" />
