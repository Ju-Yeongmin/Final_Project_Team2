<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ include file="../include/header.jsp" %>
<%@ include file="../include/sidebar.jsp" %>

<h1>피드 가기</h1>

${feed }
<div class="box box-primary">
	<div class="box-header with-border">
		<h3 class="box-title">[차곡] 한달동안 같이 50만원 모아보실 분!</h3>
	</div>

	<div class="box-body">
		<strong><i class="fa fa-book margin-r-5"></i> 주최자 </strong>
		<p class="text-muted"></p>
		<hr>
		<strong><i class="fa fa-map-marker margin-r-5"></i> 챌린지 인원</strong>
		<p class="text-muted">8명</p>
		<hr>
		<strong><i class="fa fa-table"></i> 챌린지 기간</strong>
		<p>
			<span class="label label-danger">4주</span>
		</p>
		<hr>
		<strong><i class="fa fa-file-text-o margin-r-5"></i> 챌린지 시작일</strong>
		<p> 2022-12-24</p>
		<hr>
		<strong><i class="fa fa-file-text-o margin-r-5"></i> 예치금 </strong>
		<p> 10,000 꿀머니</p>
	</div>

</div>




<div class="col-md-6">

	<div class="box box-danger">
		<div class="box-header with-border">
			<h3 class="box-title">현재 참여중인 멤버</h3>
			<div class="box-tools pull-right">
				<span class="label label-danger">${feed.c_pcnt }</span>
			</div>
		</div>

		<div class="box-body no-padding">
				<ul class="users-list clearfix">
					<li>
						<img src="" alt="User Image"> 
							<a class="users-list-name" href="#">${feed.c_person }</a> 
<!-- 						<span class="users-list-date">오늘</span> -->
					</li>
				</ul>

		</div>

		<div class="box-footer text-center">
			<a href="javascript:void(0)" class="uppercase">View All Users</a>
		</div>

	</div>

</div>
${feed }
<br>
<%@ include file="../include/footer.jsp"%>