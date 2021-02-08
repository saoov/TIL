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
</script>
</head>
<body>
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
				<th><a href="/board/read?bno=${boardVO.bno }">${boardVO.title }</a></th>
				<th>${boardVO.writer }</th>
				<th><fmt:formatDate pattern="yyyy-MM-dd HH:mm" value="${boardVO.regdate }"/></th>
				<th><span class="badge bg-red">${boardVO.viewcnt }</span></th>
			</tr>
		</c:forEach>
	</table>
</body>
</html>