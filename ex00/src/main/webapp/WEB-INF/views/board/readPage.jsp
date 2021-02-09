<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script src="http://code.jquery.com/jquery-latest.js"></script>
<script type="text/javascript">
	$(document).ready(function(){
		var formObj = $("form[role='form']");
		
		console.log(formObj);
		
		$(".btn-warning").on("click", function(){
			formObj.attr("action", "/board/modifyPage");
			formObj.attr("method", "get");
			formObj.submit();
		});
		
		$(".btn-danger").on("click", function(){
			formObj.attr("action", "/board/removePage");
			formObj.submit();
		});
		
		$(".btn-primary").on("click",function(){
			formObj.attr("method","get");
			formObj.attr("action","/board/listPage");
			formObj.submit();
		});
	})
</script>
</head>
<body>
	<form role="form" action="modifyPage" method="post">
		<input type="hidden" name="bno" value="${boardVO.bno }">
		<input type="hidden" name="page" value="${cri.page }">
		<input type="hidden" name="perPageNum" value="${cri.perPageNum }">
	</form>
	
	<div class="box-body">
			<div class="form-group">
				<label for="exampleInputTitle">Title</label>
				<input type="text" name="title" class="form-control" value="${boardVO.title }" readonly>
			</div>
			<div class="form-group">
				<label for="exampleInputContent">Content</label>
				<textarea class="form-control" name="content" rows="3" readonly >${boardVO.content }</textarea>
			</div>
			<div class="form-group">
				<label for="exampleInputWriter">Writer</label>
				<input type="text" name="writer" class="form-control" value="${boardVO.writer }" readonly>
			</div>
		</div>
		
		
		<div class="box-footer">
			<button type="submit" class="btn-warning">Modify</button>
			<button type="submit" class="btn-danger">Remove</button>
			<button type="submit" class="btn-primary">List all</button>
		</div>
</body>
</html>