<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="/WEB-INF/views/common/header.jsp">
   <jsp:param name="title" value="Demo리스트"/>
</jsp:include>
<section id="content">
   <table class="table">
      <tr>
         <th scope="col">번호</th>
         <th scope="col">이름</th>
         <th scope="col">나이</th>
         <th scope="col">이메일</th>
         <th scope="col">성별</th>
         <th scope="col">개발가능언어</th>
         <th scope="col">수정</th>
         <th scope="col">삭제</th>
      </tr>
      <c:if test="${not empty demos }">
         <c:forEach items="${demos }" var="d">
            <tr>
               <td>${d.devNo }</td>
               <td>${d.devName }</td>
               <td>${d.devAge }</td>
               <td>${d.devEmail }</td>
               <td>${d.devGender }</td>
               <td>
                  <ul>
                     <c:if test="${not empty d.devLang }">
                        <c:forEach items="${d.devLang }" var="l">
                           <li>${l }</li>
                        </c:forEach>
                     </c:if>
                  </ul>
               </td>
               <td>
                  <button class="btn btn-outline-success" onclick="location.href='/demo/updateDemoForm.do?devNo=${d.devNo}'">수정</button>
               </td>
               <td>
                  <button class="btn btn-outline-danger" onclick="deleteDemo(${d.devNo})">삭제</button>
               </td>
            </tr>
         </c:forEach>
      </c:if>
   </table>   
</section>
<jsp:include page="/WEB-INF/views/common/footer.jsp"/>
<script>
function deleteMemo(memoNo) {
    if(confirm("정말 삭제하시겠습니까?")) {
        fetch('${path}/memo/deletememo.do', {
            method: 'DELETE',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({ 'memoNo': memoNo })
        })
        .then(response => {
            if (response.ok) {
                alert("삭제 완료");
                location.reload(); // 페이지 새로고침
            } else {
                alert("삭제 실패");
            }
        })
        .catch(error => {
            console.error("Error:", error);
            alert("오류 발생");
        });
    }
}
</script>

