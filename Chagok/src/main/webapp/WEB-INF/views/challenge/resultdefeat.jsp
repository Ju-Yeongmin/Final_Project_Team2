<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<html>


<!-- 로딩 코드 start -->
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
<style type="text/css">
#waiting {
    width: 100%;
    height: 100%;
    top: 0;
    left: 0;
    position: fixed;
    display: flex;
    background: white;
    z-index: 999;
    opacity: 0.9;
}
#waiting > img {
    display: flex;
    width: fit-content;
    height: fit-content;
    margin: auto;
}
</style>
<div id="waiting">
   <img src="./resources/imgUpload/new-loading.gif">
</div>

<script type="text/javascript">
    $(window).on('load', function() {
        setTimeout(function(){
            $("#waiting").fadeOut();
        }, 300);
    });
</script>
<!-- 로딩 코드 end -->



<head>
<link rel="stylesheet" type="text/css" href="../resources/mainpagecss/css/font.css">
<script src="https://cdnjs.cloudflare.com/ajax/libs/gsap/2.0.1/TweenLite.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/gsap/2.0.1/TimelineMax.min.js"></script>

<style>
body {
    font-family: 'GmarketSans';
    position: relative;
    background: #304352;
    background: -webkit-linear-gradient(to bottom, #304352, #d7d2cc);
    /* background: linear-gradient(to bottom, #304352, #d7d2cc); */
    background-color: #52595a;
}
body .wrapper {
  width: 100vw;
  height: 100vh;
}
body .wrapper .receipt {
  position: absolute;
  top: 50%;
  left: 55%;
  transform: translateX(-50%) translateY(-50%);
  background: #ffffff;
  width: 90%;
  max-width: 700px;
  padding: 1.5em;
  border: 2px solid #231f20;
  z-index: -1;
}
body .wrapper .receipt h1 {
  text-align: center;
  font-size: 52px;
  margin-top: -75px;
  color: #231f20;
  margin-bottom: 5px;
  position: relative;
}
body .wrapper .receipt h1:before {
  content: "";
  position: absolute;
  top: 50%;
  left: 50%;
  transform: translateX(-50%) translateY(-50%);
  z-index: -1;
  display: block;
  height: 40%;
  width: 40%;
  top: 75%;
  background: #66BB7A;
  min-width: 275px;
}
body .wrapper .receipt .left, body .wrapper .receipt .right {
  margin-top: 1em;
}
body .wrapper .receipt div.left {
  width: 30%;
  float: left;
  position: relative;
  padding-top: 1em;
  padding-left: 0.5em;
}
body .wrapper .receipt div.left div {
  display: block;
  position: relative;
}
body .wrapper .receipt div.left div:before {
  content: "";
  position: absolute;
  width: 100px;
  height: 100px;
  max-width: 100%;
  max-height: 100%;
/*   background: #66BB7A; */
  border-radius: 100%;
  left: 30px;
  top: -40px;
}
body .wrapper .receipt div.left:before {
	margin-top: 25px;
    content: "";
    display: block;
    position: absolute;
    width: 105%;
    height: 100%;
    top: 0px;
    right: 0px;
    left: 0px;
    background-size: 6px;
    border: 1px solid #231f20;
    box-shadow: 5px 5px 0px #66bb7a;
}
body .wrapper .receipt div.right {
  width: 60%;
  float: left;
  position: relative;
  margin-left: 15px;
}
body .wrapper .receipt div.right ul {
  list-style-type: none;
  display: block;
  width: 100%;
}
body .wrapper .receipt div.right ul:nth-of-type(2) {
  margin-top: 20px;
  position: relative;
}
body .wrapper .receipt div.right ul:nth-of-type(2):before {
  content: "";
  position: absolute;
  width: 100%;
  height: 110%;
  left: 5%;
  top: -5%;
  background: #faf9fa;
}
body .wrapper .receipt div.right ul:nth-of-type(2) li:after {
  background: #ffffff;
}
body .wrapper .receipt div.right ul li {
  display: block;
  width: 100%;
  clear: both;
  margin-bottom: 15px;
  padding-bottom: 10px;
  position: relative;
  font-size: 18px;
}
body .wrapper .receipt div.right ul li:after {
  content: "";
  display: block;
  position: absolute;
  left: 0;
  bottom: 0;
  width: 100%;
  height: 1px;
  background: -webkit-linear-gradient(to right, #304352, #d7d2cc);
  background: linear-gradient(to right, #304352, #d7d2cc);
}
body .wrapper .receipt div.right ul li:last-of-type:after {
  display: none;
}
body .wrapper .receipt div.right ul li span {
  float: right;
  color: #304352;
  margin-top: 8px;
}
body .wrapper .receipt div.right ul li span b {
  font-size: 0.5em;
/*   vertical-align: super; */
  margin-right: 2.5px;
}
body .wrapper .receipt img {
  margin-top: 25px;
  max-width: 300px;
  display: block;
  width: 100%;
/*   margin-top: 50%; */
/*   transform: translateY(-50%); */
}
body .wrapper .footer {
  display: block;
  width: 100%;
  padding-top: 2rem;
  clear: both;
}
body .wrapper .footer p {
  max-width: 40%;
  width: 100%;
  float: left;
  display: block;
  font-size: 0.9em;
  margin-top: 20px;
  margin-bottom: 10px;
  text-align: center;
  color: #231f20;
}
body .wrapper .footer p span {
  color: #594f51;
  margin-left: 10px;
  border-bottom: 1px dashed;
  display: inline-block;
}
body .wrapper .footer .qr {
  max-width: 60px;
  font-size: 0.5em;
  text-align: left;
  display: block;
  float: right;
}
body .wrapper .footer .qr img {
  max-width: 100%;
  opacity: 0.8;
}
body .wrapper .footer .address {
  clear: both;
  display: block;
  width: calc(100%);
  background: #231f20;
  padding: 25px;
  margin-bottom: -25px;
  margin-left: -25px;
}
body .wrapper .footer .address p {
  color: #ffffff;
  float: none;
  margin: 10px 0px;
  font-family: 'GmarketSans';
  width: auto;
  max-width: none;
}
body .wrapper .footer .address p b {
  font-family: 'GmarketSans';
  font-size: 1.8em;
  color: #ccff00;
}
body .wrapper .footer .address p span {
  display: inline;
  border-bottom: none;
  color: #a69b9e;
}
.scene {
    font-size: 30vw;
    position: relative;
    margin-top: 10vw;
    margin-left: 5vw;
}
.cloud {
  display: flex;
  justify-content: center;
  align-items: center;
	background: #d2e1ff;
	width: 1em;
	height: 0.343em;
	border-radius: 0.172em;
	position: relative;
  will-change: transform;
  animation: float alternate infinite 2s ease-in-out; 
}
.cloud:after,
.cloud:before {
  background-color: inherit;
	content: '';
  display: block;
	position: absolute;
}
.cloud:after {
  font-size: 0.3em;
	width: 1em;
	height: 1em;
	border-radius: 1em;
	left: 0.5em;
	top: -0.5em;
}
.cloud:before {
  font-size: 0.55em;
	width: 1em;
	height: 1em;
	border-radius: 1em;
	right: 0.2em;
	top: -0.45em;
}
.scene .shadow {
  width: 0.9em;
  height: 0.1em;
  border-radius: 50%;
  background-color: rgba(0,0,0,0.4);
  margin-left: 0.05em;
  transform: scale(0.9);
  animation: shadow alternate infinite 2s ease-in-out;
}
.eye {
  font-size: 0.4em;
  width: 0.14em;
  height: 0.2em;
  border-radius: 50%;
  box-shadow: 0 -0.03em 0 0 black;
  margin: 0 0.3em 0.5em;
  position: relative;
  z-index: 1;
  box-shadow: none;
  width: 0.14em;
  height: 0.05em;
  border-radius: 0.03em;
  background-color: black;
}
.eye:before {
  content: '';
  display: block;
  position: absolute;
  top: 0.05em;
  width: 0.10em;
  height: 0.65em;
  margin-left: 0.02em;
  background-color: #00adff;
}
.mouth {
  position: absolute;
  background: #7b0810;
  overflow: hidden;
  font-size: 0.17em;
  width: 1em;
  height: 0.7em;
  left: calc(50% - 0.5em);
  bottom: 0.3em;
  border-radius: 1.2em 1.2em 0.7em 0.7em;
  will-change: transform;
  transform-origin: top center;
  animation: mouthcry alternate infinite 1s ease-in-out;
}
.mouth::before {
  content: '';
  display: block;
  background: #ff737b;
  width: 100%;
  height: 0.2em;
  position: absolute;
  bottom: 0;
  border-radius: 50% 50% 0 0;
}
.rain {
  width: 0.7em;
  height: 20vh;
  margin-left: 0.15em;
  display: flex;
  justify-content: space-around;
}
.drop {
	position: relative;
	width: 2px;
  height: 0.05em;
  border-radius: 1px;
  background: #d2e1ff;
	animation: rain 700ms infinite ease-out;
}
.drop:nth-child(1) {
	top: 0.05em;
	animation-delay: -1.0s;
}
.drop:nth-child(2) {
	animation-delay: -1.4s;
}
.drop:nth-child(3) {
	top: 0.05em;
	animation-delay: -1.6s;
}
.drop:nth-child(4) {
	top: 0.01em;
	animation-delay: -1.2s;
}
.drop:nth-child(5) {
	top: 0.015em;
	animation-delay: -1.6s;
}
@keyframes rain {
	100% {
		opacity: 0.2;
		transform: translateY(20vh);
	}
}
@keyframes float {
  100% {
    transform: translateY(2vh);
  }
}
@keyframes shadow {
  100% {
    transform: scale(1);
  }
}
@keyframes mouthcry {
  0%, 80% {
    transform: scale(1);
  }
  100% {
    transform: scale(0.2);
  }
}
.button {
    position: fixed;
    /* bottom: 15px; */
    right: 15px;
    top: 370;
    background: #66BB7A;
    border: none;
    border-radius: 3px;
    color: white;
    padding: 15px;
    transition: 0.3s;
    font-weight: 800;
    width: 120;
}
.button:hover {
	box-shadow: 0 2px 10px #66bb7a;
}
</style>


<%-- <%@ include file="../include/header.jsp"%> --%>
<%-- <%@ include file="../include/sidebar.jsp"%> --%>
</head>

<body>
<div class="wrapper"> 
<!-- 구름 -->
<svg version="1.1" id="Layer_1" xmlns="http://www.w3.org/2000/svg" xmlns:xlink="http://www.w3.org/1999/xlink" x="0px" y="0px"
     	 viewBox="800 400 100 0" style="enable-background:0 0 800 800;" xml:space="preserve" >
  <div class="scene">
  <div class="cloud">
    <div class="eye"></div>
    <div class="eye"></div>
    <div class="mouth"></div>
  </div>
  <div class="rain">
    <div class="drop"></div>
    <div class="drop"></div>
    <div class="drop"></div>
    <div class="drop"></div>
    <div class="drop"></div>
  </div>
  <div class="shadow"></div>
  </div>
</svg>
<!-- 구름 -->


<div class="receipt">
  <h1>챌린지 결과</h1>
  
	<c:forEach var="vo" items="${challengeList }">
		<c:if test="${vo.c_sort eq 0 }">
		<c:set var="sort" value="저축형"/>
		</c:if>
		<c:if test="${vo.c_sort eq 1 }">
		<c:set var="sort" value="절약형"/>
		</c:if>
	</c:forEach>

  <div class="left">
    <div>
  <img src="${pageContext.request.contextPath }/resources${vo.c_thumbFile }">
    </div>
  </div>
  <div class="right">
    <ul>
      <li style="font-size: 24px;"><span></span>${vo.c_title }</li>
      <li><span style="margin-top: -8px; font-size: 12px;">${vo.c_start} ~ <fmt:formatDate value="${c_end }" pattern="YYYY-MM-dd" /></span></li>
    </ul>
        <ul>
      <li>참여 인원 <span>${CList }<b>명</b></span></li>
      <li>성공 인원  <span>${Success }<b>명</b></span></li>
      <li>모인 꿀머니  <span>${ChallengeMoney }<b>꿀</b></span></li>
      <li style="background-color: #b4bfd3;">&nbsp;<span>도전 실패로 환급받으실 꿀이 없습니다.<b></b></span></li>
    </ul>
    
  </div>
  <div class="footer">
   <p><i class="fa fa-credit-card" aria-hidden="true"></i></p>
    <p><i class="fa fa-truck" aria-hidden="true"></i></p>
    <div class="qr">
    </div>
        <div class="address">
          <p><b>😥아쉽습니다! 챌린지 완주에 실패하셨습니다.</b> <span></span></p>
      </div>
  </div>
<%--   <button class="button" style="z-index: 5; position: absolute; left: 910px; margin-top: 10px;" type="button" onclick="location.href='/review?cno=${vo.cno}';">후기 작성하기</button> --%>
  </div>
</div>

</body>
</html>



<!-- </div> -->
<%-- <%@ include file="../include/footer.jsp"%> --%>