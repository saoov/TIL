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
		
		$(".btn-warning").on("click",function(){
			self.location="/board/listAll";
		});
		$(".btn-primary").on("click",function(){
			formObj.submit();
		});
	})
</script>
</head>
<body>
<form role="form" method="post">
	<div class="box-body">
			<div class="form-group">
				<label for="exampleInputBno">Bno</label>
				<input type="text" name="bno" class="form-control" value="${boardVO.bno }" readonly>
			</div>
			<div class="form-group">
				<label for="exampleInputTitle">Title</label>
				<input type="text" name="title" class="form-control" value="${boardVO.title }">
			</div>
			<div class="form-group">
				<label for="exampleInputContent">Content</label>
				<textarea class="form-control" name="content" rows="3">${boardVO.content }</textarea>
			</div>
			<div class="form-group">
				<label for="exampleInputWriter">Writer</label>
				<input type="text" name="writer" class="form-control" value="${boardVO.writer }">
			</div>
		</div>
</form>		
		
		<div class="box-footer">
			<button type="submit" class="btn btn-primary">Save</button>
			<button type="submit" class="btn btn-warning">Cancel</button>
		</div>

</body>
</html>