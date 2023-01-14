<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<html>

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
  background: linear-gradient(to bottom, #304352, #d7d2cc);
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
  z-index: -2;
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

body{
background: #ffdb83;
padding-left:100px;
}

.conf0{fill:#FC6394;}
.conf1{fill:#EF3C8A;}
.conf2{fill:#5ADAEA;}
.conf3{fill:#974CBE;}
.conf4{fill:#3CBECD;}
.conf5{fill:#813BBE;}
.conf6{fill:#F9B732;}
.conf7{display:none;fill:none;stroke:#000000;stroke-miterlimit:10;}
.conf8{fill:none;stroke:#F9B732;stroke-width:9;stroke-linecap:round;stroke-miterlimit:10;}


.confetti-cone{
  transform-origin: 200px 50px;
  animation:confetti-cone1 1.2s ease infinite;
}
@keyframes confetti-cone1{
0%{
   transform:translate(40px, 95px)  rotate(45deg) scale(1, 1);
}
15%{
   transform:translate(10px, 145px)  rotate(45deg) scale(1.1, 0.85);

}
100%{
  transform:translate(40px, 105px)  rotate(45deg) scale(1, 1);
}
  }

#yellow-strip {
  fill:none;
  stroke:#F9B732;
  stroke-width:9;
  stroke-linecap:round;
  stroke-miterlimit:10;
  animation: confdash 1.2s ease infinite;
}


@keyframes confdash {
0%{
  stroke-dasharray:1000;
  stroke-dashoffset:500;
  transform:translate(-30px, 30px);
  opacity:0;
}
2%{
  stroke-dasharray:1000;
  stroke-dashoffset:500;
  transform:translate(-30px, 30px);
  opacity:0;
}
35%{
stroke-dasharray:1000;
stroke-dashoffset:900;
transform:translate(-2px, 0px);
opacity:1;
}

85%{
  stroke-dasharray:1000;
  stroke-dashoffset:1000;
  transform:translate(1px, -5px);
  opacity:1;
}
  90%{
  stroke-dashoffset:1000;
   stroke-dashoffset:1000;
  transform:translate(2px, -8px);
  opacity:0;
}
  100%{
  stroke-dashoffset:1000;
   stroke-dashoffset:500;
  transform:translate(2px, -8px);
  opacity:0;
}
}



#a2{
  transform-origin: 310.9px 241px;
  animation: confa 1.2s ease-out infinite;
}

#a1
{transform-origin: 276px 246px;
  animation: confa 1.2s ease-out infinite;
}

@keyframes confa {
0%{
  opacity:0;
  transform: translate(-30px, 20px) rotate(0);
}
15%{
  opacity:1;
  transform: translate(25px, -10px) rotate(60deg);
}
80%{
  opacity:1;
  transform: translate(33px, -18px) rotate(180deg);
}
100%{
  opacity:0;
  transform: translate(37px, -23px) scale(0.5)rotate(230deg);
}
}


#b1{
  transform-origin: 195.2px 232.6px;
  animation: confb 1.2s ease-out infinite;
}

#b2{
  transform-origin: 230.8px 219.8px;
  animation: confb 1.2s ease-out infinite;
}
#b3 {transform-origin: 222.8px 190.6px;
  animation: confb 1.2s ease-out infinite;
}

#b4 {transform-origin: 262px 188.5px;
  animation: confb 1.2s ease-out infinite;
}

#b5 {transform-origin: 282.3px 170.6px;
  animation: confb 1.2s ease-out infinite;
}


@keyframes confb {
0%{
  opacity:0;
  transform: translate(-30px, 20px) rotate(0);
}
12%{
  opacity:1;
  transform: translate(25px, -10px) rotate(60deg);
}
76%{
  opacity:1;
  transform: translate(33px, -18px) rotate(180deg);
}
100%{
  opacity:0;
  transform: translate(37px, -23px) scale(0.5) rotate(240deg);
}
}

#c1 {transform-origin: 174.8px 183.4px;
  animation: confc 1.2s ease-out infinite;
}
#c2{
  transform-origin: 178.9px 156.2px;
  animation: confc 1.2s ease-out infinite;
}

#c3{
  transform-origin: 206.7px 140px;
  animation: confc 1.2s ease-out infinite;
}

#c4{
  transform-origin: 213.5px 120.2px;
  animation: confc 1.2s ease-out infinite;
}

@keyframes confc {
0%{
  opacity:0.7;
  transform: translate(-30px, 20px) rotate(0);
}
18%{
  opacity:1;
  transform: translate(5px, -10px) rotate(60deg);
}
76%{
  opacity:1;
  transform: translate(13px, -18px) rotate(180deg);
}
100%{
  opacity:0;
  transform: translate(17px, -23px) scale(0.5) rotate(230deg);
}
}

#d1{
  transform-origin: 127px 176px;
  animation: confd 1.2s ease-out infinite;
}
#d2 {transform-origin: 133px 118px;
  animation: confd 1.2s ease-out infinite;
}
#d3{ transform-origin: 152px 100px;
  animation: confd 1.2s ease-out infinite;
}

@keyframes confd {
0%{
  opacity:0.7;
  transform: translate(-20px, 20px) rotate(0);
}
18%{
  opacity:1;
  transform: translate(-5px, -10px) rotate(60deg);
}
76%{
  opacity:1;
  transform: translate(-8px, -18px) rotate(180deg);
}
100%{
  opacity:0;
  transform: translate(-10px, -23px) scale(0.5) rotate(230deg);
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
<!-- 폭죽 -->
<svg version="1.1" id="Layer_1" xmlns="http://www.w3.org/2000/svg" xmlns:xlink="http://www.w3.org/1999/xlink" x="0px" y="0px"
     	 viewBox="0 0 800 400" style="enable-background:0 0 800 800;" xml:space="preserve">
     	 <g class="confetti-cone">
     		 <path class="conf0" d="M131.5,172.6L196,343c2.3,6.1,11,6.1,13.4,0l65.5-170.7L131.5,172.6z"/>
     		 <path class="conf1" d="M131.5,172.6L196,343c2.3,6.1,11,6.1,13.4,0l6.7-17.5l-53.6-152.9L131.5,172.6z"/>

     			<path class="conf2" d="M274.2,184.2c-1.8,1.8-4.2,2.9-7,2.9l-129.5,0.4c-5.4,0-9.8-4.4-9.8-9.8c0-5.4,4.4-9.8,9.9-9.9l129.5-0.4
     				c5.4,0,9.8,4.4,9.8,9.8C277,180,275.9,182.5,274.2,184.2z"/>
     			<polygon class="conf3" points="231.5,285.4 174.2,285.5 143.8,205.1 262.7,204.7 			"/>
     			<path class="conf4" d="M166.3,187.4l-28.6,0.1c-5.4,0-9.8-4.4-9.8-9.8c0-5.4,4.4-9.8,9.9-9.9l24.1-0.1c0,0-2.6,5-1.3,10.6
     				C161.8,183.7,166.3,187.4,166.3,187.4z"/>
     			<ellipse transform="matrix(0.7071 -0.7071 0.7071 0.7071 -89.8523 231.0278)" class="conf2" cx="233.9" cy="224" rx="5.6" ry="5.6"/>
     			<path class="conf5" d="M143.8,205.1l5.4,14.3c6.8-2.1,14.4-0.5,19.7,4.8c7.7,7.7,7.6,20.1-0.1,27.8c-1.7,1.7-3.7,3-5.8,4l11.1,29.4
     				l27.7,0l-28-80.5L143.8,205.1z"/>
     			<path class="conf2" d="M169,224.2c-5.3-5.3-13-6.9-19.7-4.8l13.9,36.7c2.1-1,4.1-2.3,5.8-4C176.6,244.4,176.6,231.9,169,224.2z"/>
     			<ellipse transform="matrix(0.7071 -0.7071 0.7071 0.7071 -119.0946 221.1253)" class="conf6" cx="207.4" cy="254.3" rx="11.3" ry="11.2"/>
     	</g>

     		<rect x="113.7" y="135.7" transform="matrix(0.7071 -0.7071 0.7071 0.7071 -99.5348 209.1582)" class="conf7" width="178" height="178"/>
     	<line class="conf7" x1="76.8" y1="224.7" x2="328.6" y2="224.7"/>
     	<polyline class="conf7" points="202.7,350.6 202.7,167.5 202.7,98.9 	"/>
<!-- here comes the confettis-->

      	<circle class="conf2" id="b1" cx="195.2" cy="232.6" r="5.1"/>
      	<circle class="conf0" id="b2" cx="230.8" cy="219.8" r="5.4"/>
      	<circle class="conf0" id="c2" cx="178.9" cy="160.4" r="4.2"/>
      	<circle class="conf6" id="d2"cx="132.8" cy="123.6" r="5.4"/>
      	<circle class="conf0" id="d3" cx="151.9" cy="105.1" r="5.4"/>

      	<path class="conf0" id="d1" d="M129.9,176.1l-5.7,1.3c-1.6,0.4-2.2,2.3-1.1,3.5l3.8,4.2c1.1,1.2,3.1,0.8,3.6-0.7l1.9-5.5
      		C132.9,177.3,131.5,175.7,129.9,176.1z"/>
      	<path class="conf6" id="b5" d="M284.5,170.7l-5.4,1.2c-1.5,0.3-2.1,2.2-1,3.3l3.6,3.9c1,1.1,2.9,0.8,3.4-0.7l1.8-5.2
      		C287.4,171.9,286.1,170.4,284.5,170.7z"/>
      	<circle class="conf6" id="c3"cx="206.7" cy="144.4" r="4.5"/>
        <path class="conf2" id="c1" d="M176.4,192.3h-3.2c-1.6,0-2.9-1.3-2.9-2.9v-3.2c0-1.6,1.3-2.9,2.9-2.9h3.2c1.6,0,2.9,1.3,2.9,2.9v3.2
      		C179.3,191,178,192.3,176.4,192.3z"/>
      	<path class="conf2" id="b4" d="M263.7,197.4h-3.2c-1.6,0-2.9-1.3-2.9-2.9v-3.2c0-1.6,1.3-2.9,2.9-2.9h3.2c1.6,0,2.9,1.3,2.9,2.9v3.2
      		C266.5,196.1,265.2,197.4,263.7,197.4z"/>
      	<!-- yellow-strip-1-->
         <path id="yellow-strip" d="M179.7,102.4c0,0,6.6,15.3-2.3,25c-8.9,9.7-24.5,9.7-29.7,15.6c-5.2,5.9-0.7,18.6,3.7,28.2
      		c4.5,9.7,2.2,23-10.4,28.2"/>
      	<path class="conf8" id="yellow-strip" d="M252.2,156.1c0,0-16.9-3.5-28.8,2.4c-11.9,5.9-14.9,17.8-16.4,29c-1.5,11.1-4.3,28.8-31.5,33.4"/>
      	<path class="conf0" id="a1" d="M277.5,254.8h-3.2c-1.6,0-2.9-1.3-2.9-2.9v-3.2c0-1.6,1.3-2.9,2.9-2.9h3.2c1.6,0,2.9,1.3,2.9,2.9v3.2
      		C280.4,253.5,279.1,254.8,277.5,254.8z"/>
      	<path class="conf3" id="c4" d="M215.2,121.3L215.2,121.3c0.3,0.6,0.8,1,1.5,1.1l0,0c1.6,0.2,2.2,2.2,1.1,3.3l0,0c-0.5,0.4-0.7,1.1-0.6,1.7v0
      		c0.3,1.6-1.4,2.8-2.8,2l0,0c-0.6-0.3-1.2-0.3-1.8,0h0c-1.4,0.7-3.1-0.5-2.8-2v0c0.1-0.6-0.1-1.3-0.6-1.7l0,0
      		c-1.1-1.1-0.5-3.1,1.1-3.3l0,0c0.6-0.1,1.2-0.5,1.5-1.1v0C212.5,119.8,214.5,119.8,215.2,121.3z"/>
      	<path class="conf3" id="b3" d="M224.5,191.7L224.5,191.7c0.3,0.6,0.8,1,1.5,1.1l0,0c1.6,0.2,2.2,2.2,1.1,3.3v0c-0.5,0.4-0.7,1.1-0.6,1.7l0,0
      		c0.3,1.6-1.4,2.8-2.8,2h0c-0.6-0.3-1.2-0.3-1.8,0l0,0c-1.4,0.7-3.1-0.5-2.8-2l0,0c0.1-0.6-0.1-1.3-0.6-1.7v0
      		c-1.1-1.1-0.5-3.1,1.1-3.3l0,0c0.6-0.1,1.2-0.5,1.5-1.1l0,0C221.7,190.2,223.8,190.2,224.5,191.7z"/>
      	<path class="conf3" id="a2" d="M312.6,242.1L312.6,242.1c0.3,0.6,0.8,1,1.5,1.1l0,0c1.6,0.2,2.2,2.2,1.1,3.3l0,0c-0.5,0.4-0.7,1.1-0.6,1.7v0
      		c0.3,1.6-1.4,2.8-2.8,2l0,0c-0.6-0.3-1.2-0.3-1.8,0h0c-1.4,0.7-3.1-0.5-2.8-2v0c0.1-0.6-0.1-1.3-0.6-1.7l0,0
      		c-1.1-1.1-0.5-3.1,1.1-3.3l0,0c0.6-0.1,1.2-0.5,1.5-1.1v0C309.9,240.6,311.9,240.6,312.6,242.1z"/>
      	<path class="conf8" id="yellow-strip" d="M290.7,215.4c0,0-14.4-3.4-22.6,2.7c-8.2,6.2-8.2,23.3-17.1,29.4c-8.9,6.2-19.8-2.7-32.2-4.1
      		c-12.3-1.4-19.2,5.5-20.5,10.9"/>
      </g>
</svg>
<!-- 폭죽 -->


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
      <li><span style="margin-top: -8px; font-size: 12px;">${vo.c_start } ~ <fmt:formatDate value="${c_end }" pattern="YYYY-MM-dd" /></span></li>
    </ul>
        <ul>
      <li>참여 인원 <span>${CList }<b>명</b></span></li>
      <li>성공 인원  <span>${Success }<b>명</b></span></li>
      <li>모인 꿀머니  <span>${ChallengeMoney }<b>꿀</b></span></li>
      <li style="background-color: #FFDB83;">환급 꿀머니 <span>${ChallengeMoney / Success}<b>꿀</b></span></li>
    </ul>
    
  </div>
  <div class="footer">
   <p><i class="fa fa-credit-card" aria-hidden="true"></i></p>
    <p><i class="fa fa-truck" aria-hidden="true"></i></p>
    <div class="qr">
      <button class="button" type="button" onclick="location.href='/review?cno=${vo.cno}';">후기 작성하기</button>
    </div>
        <div class="address">
          <p><b>🎉축하합니다! 성공적으로 챌린지를 마치셨습니다!</b> <span></span></p>
      </div>
  </div>
  </div>
</div>

</body>
</html>



<!-- </div> -->
<%-- <%@ include file="../include/footer.jsp"%> --%>