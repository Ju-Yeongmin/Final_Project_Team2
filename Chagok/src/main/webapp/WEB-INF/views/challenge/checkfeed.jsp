<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%@ include file="../include/header.jsp" %>
<%@ include file="../include/sidebar.jsp" %>

<h1>피드 가기</h1>

${challengeList }

<script type="text/javascript">
	function move() {
// 		var move = document.getElementByid('#move');
		
		if(${vo.c_sort} == 0){
			location.href = "/challenge/plusfeed?cno="+${vo.cno};
			return false;
		}else if (${vo.c_sort} == 1){
 			location.href = "/challenge/minusFeed?cno="+${vo.cno};
 			return;
		}
		
	} 

</script>

<section class="content">
	<div class="row">
		<div class="col-lg-5 mx-6 aos-init aos-animate" data-aos="fade-right" >
	        <img class="img-responsive" src="${pageContext.request.contextPath }/resources/dist/img/photo1.png" alt="Photo" style="width:500px; height:400px;">
		</div>
		<div class="col-lg-6 pt-4 pt-lg-0 content aos-init aos-animate" data-aos="fade-left" >
<%-- 			<c:forEach var="vo" items="challengeList"> --%>
			
<%-- 				<c:if test="${vo.c_sort eq 0 }"> --%>
<%-- 					<c:set var="sort" value="저축형"/> --%>
<%-- 				</c:if> --%>
<%-- 				<c:if test="${vo.c_sort eq 1 }"> --%>
<%-- 					<c:set var="sort" value="절약형"/> --%>
<%-- 				</c:if> --%>
				
			<h3><span style="color: #66BB7A; font-weight: bold;">[${sort }]</span> ${vo.c_title }</h3>
<%-- 			</c:forEach> --%>
			 <jsp:useBean id="now" class="java.util.Date" />
			 <fmt:parseNumber value="${now.time / (1000*60*60*24)}" integerOnly="true" var="nowfmtTime" scope="request"/>
			 <fmt:parseDate value="${vo.c_start}" var="startDate" pattern="yyyy-MM-dd"/>
			 <fmt:parseNumber value="${startDate.time / (1000*60*60*24)}" integerOnly="true" var="startTime" scope="request"/>
			 <fmt:parseNumber value="${c_end.time / (1000*60*60*24)}" integerOnly="true" var="endTime" scope="request" />
			<c:if test="${startTime - nowfmtTime <= 0 && nowfmtTime - endTime <= 0}">
				<p class="fst-italic">챌린지가 <b>시작</b>되었습니다!</p>
			</c:if>
			<c:if test="${startTime - nowfmtTime > 0}">
				<p class="fst-italic">챌린지가 &nbsp;&nbsp;  <span style="color: #66BB7A; font-weight: bold; font-size: 20px;"> ${startTime - nowfmtTime }</span> 일 후에 시작됩니다!</p>
			</c:if>
			<c:if test="${nowfmtTime - endTime > 0}">
				<p class="fst-italic">챌린지가 <b>종료</b>되었습니다!</p>
			</c:if>
			<br><br>
			<div class="row">
				<div class="col-lg-6" style="line-height: 180%">
	             <div class="progress-group" style="width: 280px;" >
	               <span class="progress-text">챌린지 장 </span>
	               <span class="progress-number"><b>${vo.c_host }</b>님</span>
	             </div>
	             <div class="progress-group" style="width: 280px;">
	               <span class="progress-text">챌린지 인원</span>
	               <span class="progress-number"><b>${challengeList.size() } </b>/ ${vo.c_pcnt }</span>
	             </div>
	             <div class="progress-group" style="width: 280px;">
	               <span class="progress-text">예치금</span>
	               <span class="progress-number"><b>${vo.c_deposit }</b>꿀</span>
	             </div>  
	          	 <div class="progress-group" style="width: 280px;">
	               <span class="progress-text">챌린지 기간</span>
	               <span class="progress-number"><b>${vo.c_period }</b>주</span>
	             </div>  
	             <div class="progress-group" style="width: 280px;">
	               <span class="progress-text">챌린지 시작일</span>
	               <span class="progress-number">
	               	<b><fmt:formatDate value="${startDate }" pattern="YYYY-MM-dd"/></b>
	               </span>
	              </div>
	             <div class="progress-group" style="width: 280px;">
	               <span class="progress-text">챌린지 종료일</span>
	               <span class="progress-number">
	               	<b><fmt:formatDate value="${c_end }" pattern="YYYY-MM-dd"/></b>
	               </span>
	              </div>
	             
	         	</div>
	       </div>
		</div>
	</div>
	<br>

<div class="col-md-6">

	<div class="box box-danger">
		<div class="box-header with-border">
			<h3 class="box-title">현재 참여중인 멤버</h3>
			<div class="box-tools pull-right">
				<span class="label label-danger">${challengeList.size() }</span>
			</div>
		</div>

		<div class="box-body no-padding">
<%-- 			<c:forEach var="vo" item="${challengeList }"> --%>
				<ul class="users-list clearfix">
					<li>
						<img src="" alt="User Image"> 
						<br>
							${vo.c_person } 
 					</li>
				</ul>
<%-- 			</c:forEach> --%>

		</div>

		<div class="box-footer text-center">
			<a href="javascript:void(0)" class="uppercase">View All Users</a>
		</div>

	</div>
<input type="button" value="피드가기" id="move" onclick="move();">
</div>
</section>

${challengeList }
<!-- <br> -->
<%@ include file="../include/footer.jsp"%>