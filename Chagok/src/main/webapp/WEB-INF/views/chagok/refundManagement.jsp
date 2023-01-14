<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<%@ include file="../include/header.jsp"%>
<%@ include file="../include/sidebar.jsp"%>

<!-- 전체 페이지 (밑부분 스타일까지) 긁어가야 합니다~ -->
<!-- 전체 페이지 (밑부분 스타일까지) 긁어가야 합니다~ -->
			
 <head>
 <meta charset="utf-8">
 <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
 <link rel="stylesheet" href="../assets/css/theme.min.css">
 
 <style>
.board_wrap {
	width: 1000px;
	margin: 20px auto;
}

.board_title {
	margin-bottom: 30px;
}

.board_title strong {
	font-size: 3rem;
	font-weight: 1000;
}

.board_title p {
	margin-top: 10px;
	font-size: 1.4rem;
}

.bt_wrap {
    margin-top: 30px;
    text-align: center;
    font-size: 0;
}

.bt_wrap input {
    display: inline-block;
    min-width: 80px;
    margin-left: 10px;
    padding: 10px;
    border: 1px solid #FFDB83;
    border-radius: 2px;
    font-size: 1.4rem;
}

.bt_wrap input:first-child {
    margin-left: 0;
}

.bt_wrap input.sbtn {
	display: inline-block;
    min-width: 100px;
    margin-left: 10px;
    padding: 10px;
    border: 1px solid #FFDB83;
    border-radius: 2px;
    font-size: 1.4rem;
    background-color: #FFDB83;
    color: #fff;
    margin-bottom: 20px;
}

.bt_wrap input.sbtn2 {
	display: inline-block;
    min-width: 100px;
    margin-left: 10px;
    padding: 10px;
    border: 1px solid #FFDB83;
    border-radius: 2px;
    font-size: 1.4rem;
    background-color: #fff;
    color: #FFDB83;
}
.table.table-hover {
/*     margin-left: 10px; */
    width: 90%;
    border-bottom: 1px solid #ddd;
    border-top: 2px solid #66BB7A;
    margin-right: auto;
    margin-left: auto;
}
.board_page {
	width: 90%;
    display: inline-block;
}
</style>
 
 </head>

 

 <body>
 <script src="../assets/libs/jquery/dist/jquery.min.js"></script>
 <script src="../assets/libs/bootstrap/dist/js/bootstrap.bundle.min.js"></script>
 <script src="../assets/js/theme.min.js"></script>

<!-- 게시판 안내 -->
	<div class="col-xl-12 col-lg-12 col-md-12 col-sm-12 col-12">
		<div class="board_wrap">
			<div class="board_title">
				<strong>환급 관리</strong>
					<p>관리자 전용 페이지입니다.</p>
			</div>
		</div>
	</div>
<!-- 게시판 안내 -->			
				
<!-- 게시판 내용 -->				
	<table class="table table-hover">
			  <thead style="background-color: #dddddd30;font-size: 16px;">
			    <tr>
			      <th scope="col" style="text-align:center; padding: 15px 0;">회원번호</th>
			      <th scope="col" style="text-align:center; padding: 15px 0;">닉네임</th>
			      <th scope="col" style="text-align:center; padding: 15px 0;">이름</th>
			      <th scope="col" style="text-align:center; padding: 15px 0;">신청일</th>
			      <th scope="col" style="text-align:center; padding: 15px 0;">환급금</th>
			      <th scope="col" style="text-align:center; padding: 15px 0;">환불계좌정보</th>
			      <th scope="col" style="text-align:center; padding: 15px 0;">관리</th>
		    	</tr>
			  </thead>
			  
			  
			<tbody>
		       	<c:forEach items="${userList }" var="user" >
				    <tr>
				      <td style="text-align:center; padding: 15px 0;">${user.mno }</td>
				      <td style="text-align:center; padding: 15px 0;">${user.nick }</td>
				      <td style="text-align:center; padding: 15px 0;">${user.rname }</td>
				      <td style="text-align:center; padding: 15px 0;">환급금</td>
				      <td style="text-align:center; padding: 15px 0;">신청일</td>
				      <td style="text-align:center; padding: 15px 0;">
						<c:choose>
							<c:when test="${user.rname == null}">정보 없음</c:when>
							<c:otherwise>
								<a class="detail plus">▼ 환불계좌정보</a>
								<a class="detail minus" style="display: none">▲ 환불계좌정보</a>
							</c:otherwise>
						</c:choose>				      
				      </td>
					   <td style="text-align:center; padding: 15px 0;">
					   <button type="button" class="btn" style="background-color:#66BB7A;">
								환급하기
							</button>
					   </td>
				    </tr>
				    <tr style="display:none">
				    	<td>
				    	<td colspan="9" style="text-align:center; padding: 15px 0;">
				    		${user.rbank }은행&nbsp;&nbsp;&nbsp;${user.raccount }&nbsp;&nbsp;&nbsp;(예금주 : ${user.rname })
				    	</td>
				    </tr>
		    	</c:forEach>   
			</tbody>
	</table>
<!-- 게시판 내용 -->

					
<!-- 페이징 -->					
		  <div class="board_page">
                	<ul class= "pagination pagination-sm no-margin pull-right">
					<c:if test="${pagevo.prev }">
			            <li><a href="/refundManagement?page=${pagevo.startPage-1 }">«</a></li>
			        </c:if>
					<c:forEach var="idx" begin="${pagevo.startPage }" end="${pagevo.endPage }" step="1">
						<li
							<c:out value="${idx == pagevo.cri.page? 'class=active':'' }"/>
							>
							<a href="/refundManagement?page=${idx }">${idx }</a>
						</li>
					</c:forEach>
			        <c:if test="${pagevo.next }">
			            <li><a href="/refundManagement?page=${pagevo.endPage+1 }">»</a></li>
			        </c:if>
                </ul>
           </div>
 <!-- 페이징 -->
 
 </body>

<script>
$(document).ready(function(){
	$('.detail').click(function(){
		  var obj = $(this);
		  if( obj.hasClass("plus") ){
		    obj.hide();
		    obj.next().show();            
		    obj.parent().parent().next().show();
		  }else{
		     obj.hide();
		     obj.prev().show();
		     obj.parent().parent().next().hide();
		  }
	})
})


</script>
</div>
<%@ include file="../include/footer.jsp"%>