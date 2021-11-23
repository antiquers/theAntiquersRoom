<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
	<!DOCTYPE html>
	<html>

	<head>
		<meta charset="UTF-8">
		<title>앤티커스 : 회원가입</title>

		<link rel="stylesheet" href="../../../resources/css/register.css">

		<script language="javascript" src="https://ajax.aspnetcdn.com/ajax/jQuery/jquery-3.3.1.min.js"></script>

		<script>
			$(document)
				.ready(
					function () {
						let idcheck = false;
						let pwcheck = false;
						let pwchcheck = false;
						let niccheck = false;
						let phonecheck = false;

						// 제출버튼 활성화 함수
						  function buttonlive() {
							if (idcheck && pwcheck && pwchcheck
								&& niccheck && phonecheck) {
								$("#checkit").click(function(){
								alert("성공적으로 가입되었습니다.");
								});
							} else {
								alert("입력칸을 모두 채워주세요.");
								$("#checkit").prop("disabled", true);
							} 
						} 					
						

						//이메일 정규표현식 체크 
						$('#emailbtn').click(function () {
							const regxEmail = /^[A-Za-z0-9_\.\-]+@[A-Za-z0-9\-]+\.[A-Za-z0-9\-]+/; //문자 + @ + 문자.문자
							if ($('#email').val() == "") {
								$('#idchecker').text("아이디를 입력해주세요.");
								$('#idchecker').css('color', '#f82a2aa3');
								$('#email').focus();
								idcheck = false;
								buttonlive();
							} else if (regxEmail.test($('#email').val()) != true) {
								$('#idchecker').text("올바른 양식으로 입력해주세요.");
								$('#idchecker').css('color', '#f82a2aa3');
								idcheck = false;

								// 이메일 중복체크 
							} else {
								console.log($('#email').val());
								$.ajax(
									{
										async: true,
										type: "post",
										url: "/checkId",
										data: JSON.stringify({ userId: $('#email').val() }),
										contentType: "application/json",
										success: function (data) {
											console.log('success');
												if (data.emailCheck == false) {
													$('#idchecker').text("OK");
													$('#idchecker').css('color', '#f82a2aa3');
													$('#email').focus();
													idcheck = true;
												} else {
													$('#idchecker').text("사용중인 아이디입니다.");
													$('#idchecker').css('color', '#f82a2aa3');
													$('#email').focus();
													idcheck = false;
												}
											
										},
										error: function (e) {
											console.log('error');
										},
										complete: function () {
											console.log('complete');
										}
									}
								);
							}
						});

						// 비밀번호
						$("#password")
							.keyup(
								function () {
									const password = $("#password")
										.val();
									const regxpwd = /^(?=.*[A-Za-z])(?=.*\d)(?=.*[~!@#$%^&*()+|=])[A-Za-z\d~!@#$%^&*()+|=]{8,16}$/;
									const pwdTest = regxpwd
										.test(password); //true / false 반환
									//빈칸일 경우 
									if ($("#password").val() == "") {
										$('.passwordcheck')
											.text("패스워드를 입력해주세요.");
										$('.passwordcheck').css('color',
											'#f82a2aa3');
										pwcheck = false;
									} else if (!pwdTest) { //false면
										$('.passwordcheck')
											.text(
												"8~16자리의 영문+숫자+특수문자로 입력하세요.");
										$('.passwordcheck').css('color',
											'#f82a2aa3');
										pwcheck = false;
									} else {
										$('.passwordcheck').text('OK');
										$('#idchecker').css('color', '#f82a2aa3');
										pwcheck = true;
									}
								});

						// 비밀번호체크
						$('#confirmpassword').keyup(
							function () {
								if ($('#password').val() != $(
									'#confirmpassword').val()) {
									$('.pwdconfirmcheck').text('비밀번호가 일치하지 않습니다.');
									$('.pwdconfirmcheck').css('color', '#f82a2aa3');
									pwchcheck = false;
								} else {
									$('.pwdconfirmcheck').text('OK');
									$('.pwdconfirmcheck').css('color', '#f82a2aa3');
									pwchcheck = true;
								}
							});

						//닉네임 체크
						$('#nickname').keyup(function () {
							if ($('#nickname').val() == "") {
								$('.nicknamecheck').text("닉네임을 입력해주세요.");
								$('.nicknamecheck').css('color', '#f82a2aa3');
								$('#nickname').focus();
								niccheck = false;
							} else {
								$.ajax(
									{
										async: true,
										type: "post",
										url: "/checkNickName",
										data: JSON.stringify({ nickName: $('#nickname').val()}),
										contentType: "application/json",
										success: function (data) {
											console.log('nickname in ajax');
											console.log('data.nicknameCheck');
											if (data.nicknameCheck == false) {
												$('.nicknamecheck').text("OK");
												$('.nicknamecheck').css('color', '#f82a2aa3');
												niccheck = true;
											} else {
												$('.nicknamecheck').text("사용중인 닉네임입니다.");
												$('.nicknamecheck').css('color', '#f82a2aa3');
												niccheck = false;
											}
										}
									}
								);
							}
						});

						// 핸드폰
						$("#phonenumber")
							.on(
								"keyup",
								function () {
									const regxPhone = /^(010[1-9][0-9]{7})$/
									// 010으로 시작, 중간번호의 첫 숫자는 0 제외, 총 11자리 숫자
									if ($('#phonenumber').val() == "") {
										$(".phonenumbercheck").text(
											"휴대폰 번호를 입력해주세요.");
										$(".phonenumbercheck").css('color',
											'#f82a2aa3');
										phonecheck = false;
									} else if (regxPhone.test($(
										'#phonenumber').val()) != true) {
										$(".phonenumbercheck").text(
											"올바른 양식으로 입력해주세요.");
										$(".phonenumbercheck").css('color',
											'#f82a2aa3');
										phonecheck = false;
									} else {
										$.ajax ({
											async: true,
											type: "post", 
											url: "/checkPhone",
											data: JSON.stringify({ phone: $('#phonenumber').val()}),
											contentType: "application/json",
											success: function(data){
												if(data.phonenumberCheck == false) {
													$(".phonenumbercheck").text("OK");
													$(".phonenumbercheck").css('color', '#f82a2aa3');
													phonecheck = true;
												} else {
													$(".phonenumbercheck").text("이 번호로 가입한 이력이 존재합니다.");
													$(".phonenumbercheck").css('color', '#f82a2aa3');
													phonecheck = false;
												}
											} 
										});
									}
								});
						
						//가입하기 버튼클릭시에 alert 창 띄우기
						  $('#checkit').click(function() {
							 buttonlive();
						});
					});
		</script>
	</head>

	<body>
	
		<!-- 사이트 로고-->
		<div class="basecontainer">
			<div class="top-header">
				<a href="#" class="logo">
					<div class="logo">The</div>
					<div class="logo">Antiquer's Room</div>
				</a>
			</div>
			<div>
				<div id="subject">회원가입</div>
			</div>

			<form name="infoRegister" action="/users/register" method="post">

				<!-- container -->
				<div class="form-container">
					<div class="regbase"> 

						<!-- 이메일 입력 -->
						<div class="regcontainer">
							<div class="regconfirm"> 

								<input class="registerEmail" id="email" type="text" name="userId" placeholder="이메일">
								<input class="regbtn" id="emailbtn" type="button" value="중복확인">
								<p id="idchecker"></p>
							</div>

							<!--이메일 인증번호 입력 -->
							<div class="regconfirm">
								<input class="registerEmail" type="text" placeholder="인증번호">
								<input class="regbtn" type="button" value="인증하기">
							</div>
						</div>

						<!-- 비밀번호 입력  -->
						<div class="regconfirm">
							<input type="text" class="register" id="password" name="password" placeholder="비밀번호">
							<p class="passwordcheck"></p>
						</div>

						<!-- 비밀번호 확인  -->
						<div class="regconfirm">
							<input type="text" class="register" id="confirmpassword" placeholder="비밀번호 확인">
							<p class="pwdconfirmcheck"></p>
						</div>
						
						<!-- 닉네임 입력  -->
						<div class="regconfirm">
							<input type="text" class="register" id="nickname" name="nickName" placeholder="닉네임">
							<p class="nicknamecheck"></p>
						</div>

						<!-- 핸드폰번호 입력 -->
						<div class="regconfirm">
							<input type="text" class="register" id="phonenumber" name="phone" placeholder="핸드폰번호 (-제외)">
							<p class="phonenumbercheck"></p>
						</div>

						<!-- 가입하기 버튼  -->
						<div id="registerbtn">
							<button type="submit" class="submitbtn" id="checkit">가입하기</button>
						</div>
					</div>
				</div>

			</form>
		</div>
		
		<%@ include file="/WEB-INF/views/common/footer.jsp" %>
		
	</body>
	

	</html>