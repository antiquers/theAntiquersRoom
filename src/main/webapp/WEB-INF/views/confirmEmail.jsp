<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: KBS
  Date: 11/9/2021
  Time: 오전 12:01
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
    <script>
        $(document).ready(function (){

            //timer에 사용될 변수와, timer div hide()
            $("#timer").hide();
            var counter = 0;
            var lefttime = 180;

            //Ajax로 인한 중복요청문제를 방지하기 위한 변수설정
            var isAjaxing = false;

            //mailSendForAuthorizationForm의 submit을 Ajax로 실행
            $("#sendMailBtn").click(function (){

                //sendMailBtn 클릭시 userId값을 userIdForAuth input에 할당
                var userId = $("#userId").val();
                $("#userIdForAuth").val(userId);

                //form 내부 input값을 serialize
                var data  = $("#mailSendForAuthorizationForm").serializeObject();

                //Ajax로 인한 중복요청문제를 방지
                if( isAjaxing ){
                    return;
                }

                isAjaxing = true;

                console.log(data);

                //비동기 요청
                $.ajax({
                    async: true,
                    cache : false,
                    type : 'POST',
                    data : JSON.stringify(data),
                    url : "/sendEmail",
                    contentType: 'application/json',
                    success : function (result) {

                        //emailsend 성공시 타이머생성
                        if(result.check){

                            console.log('emaiisent');
                            counter = 0;
                            lefttime = 180;
                            $("#timer").show();

                        }

                        //Ajax로 인한 중복요청문제를 방지
                        setTimeout(function (){ isAjaxing = false}, 1000);

                    },
                    error : function (error) {

                        console.log("error", error);

                        //Ajax로 인한 중복요청문제를 방지
                        setTimeout(function (){ isAjaxing = false}, 1000);

                    },

                });

            });

            $("#checkAuthBtn").click(function (){

                //Ajax로 인한 중복요청문제를 방지
                if( isAjaxing ){
                    return;
                }

                isAjaxing = true;

                //비동기 요청
                var data  = $("#checkAuthorizationKeyForm").serializeObject();

                console.log(data);
                $.ajax({
                    async: true,
                    cache : false,
                    type : 'POST',
                    data : JSON.stringify(data),
                    url : "/confirmEmail",
                    contentType: 'application/json',
                    success : function (result) {

                        //emailconfirm 성공시 #mymsg의 emailCheckStatus태그 내용을 변경
                        if(result.confirmResult){
                            $("#emailCheckStatus").text('이메일 인증이 완료되었습니다.')
                        }else {
                            $("#emailCheckStatus").text('유효하지 않은 인증코드입니다.')
                        }

                        //Ajax로 인한 중복요청문제를 방지
                        setTimeout(function (){ isAjaxing = false}, 1000);

                    },
                    error : function (error) {

                        console.log("error", error);

                        //Ajax로 인한 중복요청문제를 방지
                        setTimeout(function (){ isAjaxing = false}, 1000);

                    },

                });

            });

            //데이터를 JSON형태로 변환
            jQuery.fn.serializeObject = function() {
                var obj = null;
                try {
                    if (this[0].tagName && this[0].tagName.toUpperCase() == "FORM") {
                        var arr = this.serializeArray();
                        if (arr) {
                            obj = {};
                            jQuery.each(arr, function() {
                                obj[this.name] = this.value;
                            });
                        }//if ( arr ) {
                    }
                } catch (e) {
                    alert(e.message);
                } finally {
                }

                return obj;
            };

            //값을 지정된 시간형태로 바꿔주는 function
            function convertSeconds(s){

                var min = Math.floor(s / 60);
                var sec = s % 60;
                return min + ':' + sec;

            }

            //타이머

            setInterval(function (){
                counter++;
                if(lefttime >= counter){
                    $("#timer").html(convertSeconds(lefttime - counter));
                } else {
                    $("#timer").html('시간이 만료되었습니다.');
                }
            }, 1000);

        });

    </script>
</head>
<body>

    <%-- 메일전송 --%>
    <form id = "mailSendForAuthorizationForm" action="#">
        <input type="email" id="userId" name="userId">
        <button type="button" id="sendMailBtn">send</button>
    </form>

    <%-- 인증번호 확인 --%>
    <form id = "checkAuthorizationKeyForm" action="#">
        <input id="userIdForAuth" name="userId" hidden>
        <input name="auth">
        <button type="button" id="checkAuthBtn">check</button>
        <%-- 타이머 --%>
        <div id="timer"></div>
        <%-- 인증결과 --%>
        <p id = "emailCheckStatus"></p>
    </form>

</body>
</html>
