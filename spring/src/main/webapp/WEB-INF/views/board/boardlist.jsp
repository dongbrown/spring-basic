<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<c:set var="path" value="${pageContext.request.contextPath}"/>
<jsp:include page="/WEB-INF/views/common/header.jsp">
    <jsp:param name="title" value=""/>
</jsp:include>
<section id="board-container" class="container">
    <div>
        <span>총 ${totalContents }건의 게시물이 있습니다.</span>
        <span>            
            <a class="btn btn-outline-success" href='${path}/board/inputboard.do'>글쓰기</a>
        </span>
    </div>
        
    <table id="tbl-board" class="table table-striped table-hover">
        <tr>
            <th>번호</th>
            <th>제목</th>
            <th>작성자</th>
            <th>작성일</th>
            <th>첨부파일</th>
            <th>조회수</th>
            <th>수정</th>
            <th>삭제</th>
        </tr>
       <c:if test="${not empty boards}">
        <c:forEach items="${boards}" var="b">
            <tr>
                <td>${b.boardNo}</td>
                <td><a href="${path }/board/boardDetail?boardNo=${b.boardNo }">${b.boardTitle}</a></td>
                <td>${b.boardWriter }</td>
                <td>${b.boardDate}</td>
                <td></td>
                <td>${b.boardReadCount}</td>
                <td>
                    <button class="btn btn-outline-success" 
                    onclick="location.href='${path}/board/updateBoard.do?boardNo=${b.boardNo}'">수정</button>
                </td>
                <td>
                    <button class="btn btn-outline-danger"
                    onclick="deleteBoard(${b.boardNo})">삭제</button>
                </td>
            </tr>
        </c:forEach>
    </c:if>
    </table> 
    <div id="pageBar">
        ${pageBar }
    </div>
</section>
<jsp:include page="/WEB-INF/views/common/footer.jsp"/>

<script>
function deleteBoard(boardNo) {
    fetch('${path}/board/deleteBoard.do?boardNo=' + boardNo, {
        method: 'DELETE'
    }).then(response => {
        if(response.ok) {
            alert("삭제 성공");
            location.reload();
        } else {
            alert("삭제 실패");
        }
    }).catch(error => {
        console.error('Error:', error);
        alert("삭제 중 오류 발생");
    });
}
</script>
