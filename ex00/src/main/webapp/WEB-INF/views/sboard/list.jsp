<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    <%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
<meta content='width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no' name='viewport'>
    <!-- Bootstrap 3.3.4 -->
    <link href="/resources/bootstrap/css/bootstrap.min.css" rel="stylesheet" type="text/css" />
    <!-- Font Awesome Icons -->
    <link href="https://maxcdn.bootstrapcdn.com/font-awesome/4.3.0/css/font-awesome.min.css" rel="stylesheet" type="text/css" />
    <!-- Ionicons -->
    <link href="https://code.ionicframework.com/ionicons/2.0.1/css/ionicons.min.css" rel="stylesheet" type="text/css" />
    <!-- Theme style -->
    <link href="/resources/dist/css/AdminLTE.min.css" rel="stylesheet" type="text/css" />
    <!-- AdminLTE Skins. Choose a skin from the css/skins 
         folder instead of downloading all of them to reduce the load. -->
    <link href="/resources/dist/css/skins/_all-skins.min.css" rel="stylesheet" type="text/css" />

    <!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
        <script src="https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js"></script>
        <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
    <![endif]-->
    
  </head>
      <!-- jQuery 2.1.4 -->
    <script src="/resources/plugins/jQuery/jQuery-2.1.4.min.js"></script>
<meta charset="UTF-8">
<title>Insert title here</title>
<script>
	var result = '${msg}'
	if(result=='success'){
		alert('처리가 완료되었습니다');
	}
	
	$(document).ready(function(){
		$('#searchBtn').on("click",function(event){
			self.location = "list"
			+'${pageMaker.makeQuery(1)}'
			+'&searchType='
			+$("select option:selected").val()
			+'&keyword=' + encodeURIComponent($('#keywordInput').val());
		});
		
		$('#newBtn').on("click",function(evt){
			self.location = "register";
		});
	});
</script>
</head>
<body>

	<div class="box-body">
		<select name="searchType">
		<option value="n"
			<c:out value="${cri.searchType ==null ? 'selected' : '' }"/>>
			------</option>
		<option value="t"
			<c:out value="${cri.searchType eq 't' ? 'selected' : '' }"/>>
			Title</option>
		<option value="c"
			<c:out value="${cri.searchType eq 'c' ? 'selected' : '' }"/>>
			Content</option>
		<option value="w"
			<c:out value="${cri.searchType eq 'w' ? 'selected' : '' }"/>>
			Writer</option>
		<option value="tc"
			<c:out value="${cri.searchType eq 'tc' ? 'selected' : '' }"/>>
			Title OR Content</option>
		<option value="cw"
			<c:out value="${cri.searchType eq 'cw' ? 'selected' : '' }"/>>
			Title OR Writer</option>
		<option value="tcw"
			<c:out value="${cri.searchType eq 'tcw' ? 'selected' : '' }"/>>
			Title OR Content OR Writer</option>
		</select>
		
		<input type="text" name='keyword' id='keywordInput' value='${cri.keyword }'>
		<button id='searchBtn'>Search</button>
		<button id='newBtn'>New Board</button>
	</div>


	<table class="table table-bordered">
			<tr>
				<th style="width: 10px">BNO</th>
				<th>title</th>
				<th>writer</th>
				<th>regdate</th>
				<th style="width: 40px">VIEWCNT</th>
			</tr>
		<c:forEach items="${list }" var="boardVO">
			<tr>
				<th style="width: 10px">${boardVO.bno }</th>
				<th><a href="/sboard/readPage${pageMaker.makeSearch(pageMaker.cri.page)}&bno=${boardVO.bno }">${boardVO.title }</a></th>
				<th>${boardVO.writer }</th>
				<th><fmt:formatDate pattern="yyyy-MM-dd HH:mm" value="${boardVO.regdate }"/></th>
				<th><span class="badge bg-red">${boardVO.viewcnt }</span></th>
			</tr>
		</c:forEach>
	</table>

	<div class="text-center">
		<ul class="pagination">
		
		
			<c:if test="${pageMaker.prev }">
				<li><a href="list${pageMaker.makeSearch(pageMaker.startPage - 1) }">&laquo;</a></li>
			</c:if>
			
			<c:forEach begin="${pageMaker.startPage }" end="${pageMaker.endPage }" var="idx">
				<li <c:out value="${pageMaker.cri.page == idx?'class = active':'' }"/>>
				<a href="list${pageMaker.makeSearch(idx) }">${idx }</a>
				</li>
			</c:forEach>
			
			
			<c:if test="${pageMaker.next && pageMaker.endPage > 0 }">
				<li><a href="list${pageMaker.makeSearch(pageMaker.endPage + 1) }">&raquo;</a></li>
			</c:if>
			
			
		</ul>
	</div>
</body>
</html>