<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="EUC-KR">
<title>Insert title here</title>
<!--  Jquery2.1.4 -->
<!-- Q.JQuery plugins가 아래로 가니까 안된다...왜지? -->
<script src="/resources/plugins/jQuery/jQuery-2.1.4.min.js"></script>
<script>
	function getAllList(){
	var bno = 2;
		$.getJSON("/replies/all/"+bno,function(data){
			var str = "";
			console.log(data.length);
			
			$(data).each(
			function(){
				str += "<li data-rno='"+this.rno+"' class='replyLi'>"
					+ this.rno + ":" + this.replytext
					+ "</li>";
			});
			$("#replies").html(str);
		});
	}
	
	$("#replyAddBtn").on("click",function(){
		var replyer = $("#newReplyWriter").val();
		var replytext = $("#newReplyText").val();
		
		$.ajax({
			type : 'post',
			url : '/replies',
			headers : {
				"Content-Type" : "application/json",
				"X-HTTP-Method-Override" : "POST"
			},
			dataType : 'text',
			data : JSON.stringify({
				bno : bno,
				replyer : replyer,
				replytext : replytext
			}),
			success : function(result){
				if(result == 'success'){
					alert("등록 되었습니다.");
					getAllList();
				}
			}
		});
	});
</script>

</head>
<body>
	<h2>Ajax Test Page</h2>
	
	<div>
		<div>
			REPLYER <input type="text" name="replyer" id="newReplyWriter">
		</div>
		<div>
			REPLY TEXT <input type="text" name="replytext" id="newReplyText">
		</div>
		<button id="replyAddBtn">Add Reply</button>
	</div>
		<ul id="replies">
		</ul>
</body>
</html>