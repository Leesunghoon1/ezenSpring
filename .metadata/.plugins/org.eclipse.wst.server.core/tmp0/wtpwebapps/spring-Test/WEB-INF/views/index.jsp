<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"
%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>


<jsp:include page="./layout/header.jsp"></jsp:include>

<h1>
	Hello world!  
</h1>

<p>
	My Spring Project
</p>



<script>
const msg_login = `<c:out value="${msg_login}" />`;
const msg_logout = `<c:out value="${msg_logout}" />`;
if(msg_login == 1) {
	alert("로그인 실패");
}
if(msg_logout == 1) {
	alert("로그아웃 성공");
}
</script>
<jsp:include page="./layout/footer.jsp"></jsp:include>