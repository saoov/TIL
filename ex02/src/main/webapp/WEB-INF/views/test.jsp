<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta>
<title>Insert title here</title>
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
<style type="text/css">
	#modDiv{
		width : 300px;
		height: 100px;
		background-color: gray;
		position: absolute;
		top: 50%;
		left: 50%;
		margin-top: -50px;
		margin-left: -150px;
		padding: 10px;
		z-index: 1000;
	}
</style>
<!--  Jquery2.1.4 -->
<!-- Q.JQuery plugins가 아래로 가니까 안된다...왜지? -->
<script src="/resources/plugins/jQuery/jQuery-2.1.4.min.js"></script>
<script>
	var bno = 2;
	/*
	초기 댓글창 보이기
	*/
	getAllList();
	getPageList(1);

	function getAllList() {
		$.getJSON("/replies/all/" + bno, function(data) {
			var str = "";
			console.log(data.length);

			$(data).each(
					function() {
						str += "<li data-rno='"+this.rno+"' class='replyLi'>"
								+ this.rno + ":" + this.replytext + "<button>MOD</button></li>";
					});
			$("#replies").html(str);
		});
	};
	
	/*
	*댓글 페이징
	*/
	
	function printPaging(pageMaker){
		var str = "";
		
		if(pageMaker.prev){
			str+="<li><a href='"+(pageMaker.startPage-1)+"'> << </a></li>";
		}
		for(var i = pageMaker.startPage, len = pageMaker.endPage; i<= len; i++){
			var strClass = pageMaker.cri.page == i ? 'class=active':'';
			str += "<li "+strClass+"><a href='"+i+"'>"+i+"</a></li>";
		}
		if(pageMaker.next){
			str+="<li><a href='"+(pageMaker.endPage + 1)+"'> >> </a></li>";
		}
		$('.pagination').html(str);
	};
	
	function getPageList(page){
		$.getJSON("/replies/"+bno+"/"+page, function(data){
			console.log(data.list.length);
			
			var str ="";
			
			$(data.list).each(function(){
				str+="<li data-rno='"+this.rno+"' class='replyLi'>"+this.rno+":"+this.replytext+"<button>MOD</button></li>";
			});
			$("#replies").html(str);
			printPaging(data.pageMaker);
		});
	}
	
	
	
	/*
	*댓글 추가
	*/
	$(document).ready(function(){
		$("#replyAddBtn").on("click", function() {
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
				success : function(result) {
					if (result == 'success') {
						alert("등록 되었습니다.");
						getAllList();
					}
				}
			});
		});
	});
	
	/*
	*댓글 수정
	*/
	$(document).ready(function(){
		$("#replyModBtn").on("click", function() {
			var rno = $(".modal-title").html();
			var replytext = $("#replytext").val();

			$.ajax({
				type : 'put',
				url : '/replies/'+rno,
				headers : {
					"Content-Type" : "application/json",
					"X-HTTP-Method-Override" : "PUT"
				},
				data:JSON.stringify({replytext:replytext}),
				dataType : 'text',
				success : function(result) {
					console.log("result : "+result);
					if (result == 'success') {
						alert("수정 되었습니다.");
						$("#modDiv").hide("slow");
						getPageList(1);
					}
				}
			});
		});
	});
	
	/*
	*댓글 삭제
	*/
	$(document).ready(function(){
		$("#replyDelBtn").on("click", function() {
			var rno = $(".modal-title").html();
			var replytext = $("#replytext").val();

			$.ajax({
				type : 'delete',
				url : '/replies/'+rno,
				headers : {
					"Content-Type" : "application/json",
					"X-HTTP-Method-Override" : "DELETE"
				},
				dataType : 'text',
				success : function(result) {
					console.log("result : "+result);
					if (result == 'success') {
						alert("삭제 되었습니다.");
						$("#modDiv").hide("slow");
						getAllList();
					}
				}
			});
		});
	});
	
	/*
	*댓글 페이지 클릭 이벤트 처리
	*/
	
	$(document).ready(function(){
		var replyPage = 1;
		$(".pagination").on("click", "li a", function(event){
			event.preventDefault();
			replyPage = $(this).attr("href");
			getPageList(replyPage);
		});
	});
	
	/*
	*댓글 창 보여지는 부분
	*/
$(document).ready(function(){
	$("#replies").on("click",".replyLi button", function(){
		var reply = $(this).parent();
		var rno = reply.attr("data-rno");
		var replytext = reply.text();
		
		$(".modal-title").html(rno);
		$("#replytext").val(replytext);
		$("#modDiv").show("slow");
		
	});
	
})
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
	
	<ul class="pagination">
	</ul>
	
	
	<!--댓글 창 보여지는 부분  -->
	
	<div id="modDiv" style="display: none;">
		<div class="modal-title"></div>
		<div>
			<input type="text" id="replytext">
		</div>
		<div>
			<button type="button" id="replyModBtn">Modify</button>
			<button type="button" id="replyDelBtn">Delete</button>
			<button type="button" id="CloseBtn">Close</button>
		</div>
	</div>

</body>
</html>