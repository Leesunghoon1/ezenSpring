<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
        <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<jsp:include page="../layout/header.jsp"></jsp:include>

<!-- search 구역 -->
<div  class="col-sm-12 col-md-6">
<form action="/board/list" method="get">
	<div class="input-group mb-3">
	<c:set value="${ph.pgvo.type }" var="typed"></c:set>
		<select name="type">
			<option ${typed == null? 'selected':'' }>Choose...</option>
			<option value="t" ${typed eq 't'? 'selected':'' } >title</option>
			<option value="w" ${typed eq 'w'? 'selected':'' }>writer</option>
			<option value="c" ${typed eq 'c'? 'selected':'' }>content</option>
			<option value="tw" ${typed eq 'tw'? 'selected':'' }>title+writer</option>
			<option value="tc" ${typed eq 'tc'? 'selected':'' }>title+content</option>
			<option value="wc" ${typed eq 'wc'? 'selected':'' }>writer+content</option>
			<option value="twc" ${typed eq 'twc'? 'selected':'' }>title+writer+content</option>
		</select>
		<input class="form-control" type="text" name="keyword" value="${ph.pgvo.keyword }" placeholder="Search...">
		<input type="hidden" name="pageNo" value="${ph.pgvo.pageNo }">
		<input type="hidden" name="qty" value="${ph.pgvo.qty }">
		<button type="submit" class="btn btn-success position-relative">
			search
			<span class="position-absolute top-0 start-100 translate-middle badge rounded-pill bg-danger">
				${ph.totalCount }
	    	<span class="visually-hidden">unread messages</span></span>
    	</button>
	</div>
</form>
</div>
<br>
<table class="table table=hover">
<thead>
<tr>
	<th>#</th>
	<th>제목</th>
	<th>작성자</th>
	<th>작성일</th>
	<th>조회수</th>
</tr>

</thead>

<tbody>
<c:forEach items="${list }" var="bvo">
	<tr>
	<td>${bvo.bno }</td>
	<td><a href="/board/detail?bno=${bvo.bno }">${bvo.title }</a></td>
	<td>${bvo.writer }</td>
	<td>${bvo.registerDate }</td>
	<td>${bvo.read_count }</td>
	</tr>
</c:forEach>
</tbody>
</table>

<!-- 페이지네이션 라인 -->
<div>
	<!-- prev -->
	<c:if test="${ph.prev }">
	<a  href="/board/list?pageNo=${ph.startPage-1 }&qty=${ph.pgvo.qty}&type=${ph.pgvo.type }
	&keyword=${pgh.pgvo.keytword }">◁ |</a>
	</c:if>
	<c:forEach begin="${ph.startPage }" end="${ph.endPage }" var="i">
	<a href="/board/list?pageNo=${i }&qty=${ph.pgvo.qty}&type=${ph.pgvo.type}
	&keyword=${ph.pgvo.keyword}">${i } </a>
	</c:forEach>
	<!-- next  -->
	<c:if test="${ph.next }">
	<a href="/board/list?pageNo=${ph.endPage +1 }&qty=${ph.pgvo.qty}&type=${ph.pgvo.type}
	&keyword=${ph.pgvo.keyword}"> | ▷ </a>
	</c:if>
</div>


<jsp:include page="../layout/footer.jsp"></jsp:include>
</body>
</html>