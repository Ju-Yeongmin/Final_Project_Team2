<%@ page language="java" contentType="text/html; charset=UTF-8"
   pageEncoding="UTF-8"%>
<%@ include file="../include/header.jsp"%>
<%@ include file="../include/sidebar.jsp"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>


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



<div id="message"></div>

<section id="about" class="about">
   <div class="container">
      <div class="section-title">
         <h2>절약형 차곡 챌린지</h2>
      </div> 
     <div class="row">
		<div class="col-lg-5 mx-6 aos-init aos-animate" data-aos="fade-right" >
		<img class="img-responsive" src="${pageContext.request.contextPath }/resources${vo.c_thumbFile }" alt="Photo" 
			 style="width:400px; height:400px;">
		</div>
		<div class="col-lg-6 pt-4 pt-lg-0 content aos-init aos-animate" data-aos="fade-left" >
			 <h3><span style="color: #66BB7A; font-weight: bold;">[${vo2.ct_top }]</span> ${vo.c_title }</h3>
			 <jsp:useBean id="now" class="java.util.Date" />
			 <fmt:parseNumber value="${now.time / (1000*60*60*24)}" integerOnly="true" var="nowfmtTime" scope="request"/>
			 <fmt:parseDate value="${vo.c_start}" var="startDate" pattern="yyyy-MM-dd"/>
			 <fmt:parseNumber value="${(startDate.time + 1000*60*60*24)/ (1000*60*60*24)}" integerOnly="true" var="startTime" scope="request"/>
			 <fmt:parseNumber value="${c_end.time / (1000*60*60*24)}" integerOnly="true" var="endTime" scope="request" />
			
			<c:if test="${startTime - nowfmtTime <= 0 && nowfmtTime - endTime <= 0}">
				<p class="fst-italic">챌린지가 <b>시작</b>되었습니다!</p>
			</c:if>
			<c:if test="${startTime - nowfmtTime > 0}">
				<p class="fst-italic">챌린지가 &nbsp;&nbsp;  <span style="color: #66BB7A; font-weight: bold; font-size: 20px;"> ${startTime - nowfmtTime }</span> 일 후에 시작됩니다!</p>
			</c:if>
			<c:if test="${nowfmtTime - endTime > 0}">
				<p class="fst-italic">챌린지가 <b>종료</b>되었습니다!</p>
				<c:choose>
					<c:when test="${vo3.m_sum == vo3.c_amount}">
						<button type="button" onclick="location.href='/challenge/success?cno=${vo.cno}';">결과보기</button>
					</c:when>
					<c:otherwise>
						<button type="button" onclick="location.href='/challenge/defeat?cno=${vo.cno}';">결과보기</button>
					</c:otherwise>
				</c:choose>
			</c:if>
			<br><br>
			<div class="row">
				<div class="col-lg-6" style="line-height: 180%">
	             <div class="progress-group" style="width: 280px;" >
	               <span class="progress-text">챌린지 장 </span>
	               <span class="progress-number"><b>${host.nick }</b>님</span>
	             </div>
	             <div class="progress-group" style="width: 280px;">
	               <span class="progress-text">챌린지 인원</span>
	               <span class="progress-number"><b>${minusPeoList.size() } </b>/ ${vo.c_pcnt }</span>
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
   </div>
</section>
<section class="content" style="min-height:10%;">
   <div class="box box-default">
		<div class="box-header with-border" style="background: #66BB7A; height: 50px; opacity: 85%; color: #fff;">
			<div class="text-center">
			  <h3 class="box-title" style="margin-top: 1%;">
			  	<span style="color: #3e6c49;">${vo.c_period }</span>주 동안 
		    	<span style="color: #3e6c49;">${vo2.ct_top }</span>를
		    	총 <span style="color: #3e6c49;">${vo.c_amount }</span>원 절약합니다.
			  </h3>
			</div>
		</div>
	</div>
   
   
<!--    가계부 연동하기 모달 -->
<!-- 모달 css 파일 : resources -> plugins -> modal -> minusModal.css  -->
	<form action="/challenge/minusFeed?cno=${vo.cno}" method="post" id="frm">
	<input type="hidden" value="${mno }" id="mno" name="mno">
	<input type="hidden" value="${vo.cno }" id="cno" name="cno">
	<div class="modal fade" id="modal-default" style="margin-top: 10%;">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal" aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<h4 class="modal-title">나의 가계부 연동</h4>
				</div>
				<div class="modal-body">
	<div class="box-body">
		<div class="table-responsive">
			<table class="table no-margin">
			<c:if test="${minusAbook.size() > 0}">
				<thead>
					<tr>
						<th>날짜</th>
						<th>내용</th>
						<th>금액</th>
						<th>카테고리</th>
						<th>소분류</th>
						<th>연동</th>
					</tr>
				</thead>
				</c:if>
				<c:choose>
				<c:when test="${minusAbook.size() > 0}">
				<c:forEach var="mAbook" begin="0" end="${minusAbook.size()}" items="${minusAbook}">
				<tbody>
					<tr>
						<td>${mAbook.ab_date }</td>
						<td>${mAbook.ab_content }</td>
						<td>${mAbook.ab_amount }</td>
						<td>${mAbook.ct_top }</td>
						<td>${mAbook.ct_bottom }</td>
						<td>
							<input type="checkbox" id="checkbox" name="ab_amount" value="${mAbook.ab_amount}">
						</td>
					</tr>
				</tbody>
				</c:forEach>
				</c:when>
				<c:otherwise>
				연동할 가계부 정보가 없습니다.
				</c:otherwise>
				</c:choose>
			</table>
		</div>

	</div>
				</div>

				<div class="modal-footer">
					<button type="button" class="btn btn-default pull-left" data-dismiss="modal">닫기</button>
					<button type="submit" class="btn btn-primary" id="saveABook" onclick="document.getElementById('frm').submit();">
					저장하기</button>
				</div>
			</div>

		</div>
	</div>
	</form>

	<button type="button" class="btn btn-default" data-toggle="modal" data-target="#modal-default" style="margin-left: 90%">
	가계부 연동하기
	</button>
</section>


<!-- Main content -->
<section class="content">
   <div class="row">
      <div class="col-md-12">
         <div class="box">
            <!-- /.box-header -->
            <div class="box-body no-padding">
               <table class="table table-striped">
                  <tr>
                     <th class="col-md-1">No.</th>
                     <th class="col-md-1">닉네임</th>
                     <th>진행도</th>
                     <th class="col-md-1">잔여 금액</th>
                     <th class="col-md-1">달성 여부</th>
                  </tr>
                  
                <c:forEach var="minusPeople" begin="0" end="${minusPeoList.size()}" items="${minusPeoList}">
                <c:set var="i" value="${i+1 }"/>
                  <tr>
                     <td>${i }.</td>
                     <td>${minusPeople.nick }</td>
                     <td>
                        <div class="progress progress-xs progress-striped active">
                           <div class="progress-bar 
                           <c:if test="${minusPeople.m_sum/vo.c_amount *100 == 100}">
                           		progress-bar-success
                           </c:if>
                           <c:if test="${minusPeople.m_sum/vo.c_amount *100 >= 60 && minusPeople.m_sum/vo.c_amount *100 < 100}">
                           		progress-bar-primary
                           </c:if>
                           <c:if test="${minusPeople.m_sum/vo.c_amount *100 >= 30 && minusPeople.m_sum/vo.c_amount *100 < 60}">
                           		progress-bar-yellow
                           </c:if>
                           <c:if test="${minusPeople.m_sum/vo.c_amount *100 < 30}">
                           		progress-bar-danger
                           </c:if>
                           "
                              style="width: ${minusPeople.m_sum/vo.c_amount *100}%"></div>
                        </div>
                     </td>
                     <td><span class="badge bg-green">${vo.c_amount - minusPeople.m_sum }</span></td>
	                  <td>
		                  <c:if test="${startTime - nowfmtTime > 0}"> <!-- 시작 전   -->
		                  		<span class="label label-success">진행 예정</span>
						 </c:if>
		                  <c:if test="${startTime - nowfmtTime <= 0 && nowfmtTime - endTime <= 0}"> <!-- 진행 중   -->
		                  	<c:if test="${minusPeople.m_sum > 0 }">
		                  		<span class="label label-success">진행중</span>
		                  	</c:if>
		                  	<c:if test="${minusPeople.m_sum > vo.c_amount }">
		                  		<span class="label label-danger">실패</span>
		                  	</c:if>
						 </c:if>
						 <c:if test="${nowfmtTime - endTime > 0}"> <!-- 종료 후  -->
							<c:if test="${minusPeople.m_sum <= vo.c_amount }">
		                  		<span class="label label-success">성공</span>
		                  	</c:if>
							<c:if test="${minusPeople.m_sum > vo.c_amount }">
		                  		<span class="label label-danger">실패</span>
		                  	</c:if>
						 </c:if>
                     </td>
                  </tr>
                  </c:forEach>
                  
                  
               </table>
            </div>
         </div>
      </div>
   </div>
</section>

 <!-- 칭찬하기/주시하기  @@@@@@@@@@@@@@@@@@@@@@@@@ -->
    <div class="col-xs-12" style="margin-left: 10px; ">
	 <div class="row">
	  <h3 class=" text-center"> << ${vo.c_title } >> </h3>
	   <div class="messaging" id="messaging">
	      <div class="inbox_msg">
	        <div class="inbox_people">
	          <div class="headind_srch">
	            <div class="recent_heading">
	              <h4>참가자</h4>
	            </div>
	          </div>
         <!-- <div class="chat_list active_chat"> 어두운색 배경으로 비활성화 가능 -->
	          <div class="inbox_chat">
          <c:forEach var="minusPeoList" items="${minusPeoList}">
	            <div class="chat_list">
	              <div class="chat_people">
	                <div class="chat_img"> 
	                <c:if test="${minusPeoList.profile != null }">
	                	<img src="${pageContext.request.contextPath }/resources${minusPeoList.profile }" alt="sunil"> 
	                </c:if>
	                <c:if test="${minusPeoList.profile == null }">
	                	<img src="https://ptetutorials.com/images/user-profile.png" alt="sunil"> 
	                </c:if>
	                </div>
	                <div class="chat_ib">
<%-- 	                  <h5>${plusPeoList.nick} <span class="chat_date"><fmt:formatDate value="${now }" pattern="MMM DD일"/></span></h5><!-- 최근 접속일자로 바꿀 것 --> --%>
	                  <h5>${minusPeoList.nick} <span class="chat_date"></h5>
<!-- 	                  <a href="#"><i class="fa fa-circle text-success"></i> Online</a></span> -->
<!-- 	                  <a href="#"><i class="fa fa-circle text-gray"></i> Offline</a> -->
	                </div>
	              </div>
	            </div>
	          </c:forEach>
	          </div>
	        </div>
	        <div class="mesgs">
	          <div class="msg_history" id="msg_history">
				<main class="chat" id="chat">
				
            <!-- 받은 메시지 -->
				<div id="nextMsg"></div>
			<!-- 받은 메시지 -->
			
			</main>
	          </div>
	          <div class="type_msg">
	            <div class="input_msg_write">
	              <input type="text" class="msg" id="msg" placeholder="Type a message" />
	              <button class="msg_send_btn" id="btnSend"><i class="fa fa-paper-plane-o" aria-hidden="true"></i></button>
	            </div>
	          </div>
	        </div>
	      </div>
	      </div>
	  <!-- <!-- 칭찬하기/주시하기  @@@@@@@@@@@@@@@@@@@@@@@@@ -->
	  <!-- /.content-wrapper -->
	</div>
</div>
</div>
</div>
<%@ include file="../include/footer.jsp"%>