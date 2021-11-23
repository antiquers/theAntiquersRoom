<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>

<head>
	<meta charset="UTF-8">
	<title>Header</title>

	<link rel="stylesheet" href="/resources/css/adminHeader.css">
	
	<!-- google font -->
	<link rel="preconnect" href="https://fonts.googleapis.com">
	<link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
	<link href="https://fonts.googleapis.com/css2?family=Merriweather&display=swap" rel="stylesheet">
	
	<!-- jQuery -->
	<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
	<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery-migrate/3.3.2/jquery-migrate.min.js"></script>

	<script>
		$(function(){
			$('#logout_btn').click(function(){
				console.log('click event triggered..');

				self.location='/users/logout';
			}); //logout_onclick
		}); //.jq
		
	</script>
</head>

<body>
	<!-- header -->
	<header id="header_container" style="width: 100%;">
		<div id="logo_container">
			<!-- 사이트 로고-->
			<div id="header_logo" role="banner">
				<a href="#">
					<img id="logoimg" src="" alt="" width="40" height="60">
					<div class="logo">Antiquer's Admin Page</div>
				</a>
			</div>
			<!-- rightside of header -->
			
		</div>

		<!-- navbar -->
		<nav id="navbar">
			<a class="menu" href="/admin/main">승인요청상품</a> 
			<a class="menu" href="#">경매상품</a> 
			<a class="menu" href="#">판매종료상품</a> 
			<a class="menu" href="#">회원목록</a> 
			<button type="button" id="logout_btn" >Log out</button>
		</nav>
	</header>

</body>

</html>