<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%@ include file="../include/header.jsp" %>
<%@ include file="../include/sidebar.jsp" %>

<html lang="ko">


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
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="css/css.css">
	
<script>
			$(document).ready(function(){
				var formObj = $("form[role='form']");
					
				$(".sbtn2").click(function(){
					formObj.attr("action","/economydelete");
					formObj.submit();
				});
						
			});
</script> 	

<!-- <script type="text/javascript"> -->

<!--  		$(document).ready(function(){ -->
<!--  			var fun01 = $("form[role='form']"); -->
				
<!--  			$("fun01").click(function(){ -->
<!--  				formObj.attr("action","/insertcomment"); -->
<!--  				formObj.submit(); -->
<!--  			}); -->
			
<!--  			location.reload();	 -->
<!--  		}); -->
	
	
<!-- </script> -->

	
</head>	
<body>
${nick }
<!-- <form role="form" method="post"> -->
<%-- 	<input type="hidden" name="bno" value="${vo.bno }"> --%>
<!-- </form> -->

    <div class="board_wrap">
        <div class="board_title">
            <strong> 뉴스 / 재테크 </strong>
            <p>여러가지 정보를 공유합시다.</p>
        </div>
	</div>
	
        <div class="board_wrap">
            <div class="board_write">
                <div class="title">
            		<dl>
            			<dt>제목</dt>
            			<dd>${vo.b_title }<dd>
                	</dl>
                </div>
                <div class="info">
                    <dl>
                        <dt>글번호</dt>
                        <dd>${vo.bno }</dd>
                    </dl>
                    <dl>
                        <dt>작성자</dt>
                        <dd>${vo.b_writer }</dd>
                    </dl>
                    <dl>
                        <dt>작성일</dt>
                        <dd><fmt:formatDate value="${vo.b_date }" pattern="yyyy-MM-dd"/></dd>
                    </dl>
<!--                     <dl> -->
<!--                         <dt>조회수</dt> -->
<!--                         <dd>33</dd> -->
<!--                     </dl> -->
                </div>
                <div class="cont">
                    <textarea name="b_content"  style="resize: none;" readonly placeholder="내용을 작성해주세요">${vo.b_content }</textarea>
                    <div class="bt_wrap">
	                    <input class="sbtn" type=button value="좋아요">
    	                <input class="sbtn2" type=button value="싫어요">
    	                
                    </div>
                </div>
            </div>
           
            <div class="bt_wrap">
                	<input class="sbtn" type="button" value="목록" onclick="back()" >
<!--                 <a href="notice" class="on">목록</a> -->
				<c:set var="writer" value="${vo.b_writer }"/>
                <c:if test="${nick == '관리자' || nick == writer}">
                	<input class="sbtn" type="button" value="수정하기" onclick="location.href='/economyupdate?bno=${vo.bno}';" >
            	    <input class="sbtn2" type="submit" value="삭제하기" onclick="location.href='/economydelete?bno=${vo.bno}';">
                </c:if>
            </div>
            <br>
            <div class="board_write">
                <div class="title">
            		<dl>
            			<dt>전체 댓글 [${CM }]</dt>
            		</dl>
                </div>
                <c:forEach items="${CV }" var="CV">
                <div class="info">
                     <dl>
                     	<dt>작성자</dt>
                        <dd>${CV.cm_nick }</dd>
                        <dd><br></dd>
                    </dl>
                    <dl>
                    	<dd><br> </dd>
                        <dd style="float:right;"><fmt:formatDate value="${CV.cm_regdate }" pattern="yyyy-MM-dd HH:mm:ss"/></dd>
                    </dl>
                    <br>
                   <br>
                    <dl>
                        <dd>${CV.cm_content }</dd>
                    </dl>
                    <br>
						<dl>
                    <c:set var="cnick" value="${CV.cm_nick }"/>
                    <c:if test="${nick == '관리자' || nick == cnick}">
							<dd>
								<button type="submit" class="btn" value="수정"  style="background-color:#66BB7A;"
								onclick="location.href='/updatecomment?cm_cbno=${CV.cm_cbno}';">수정</button>
								
								<button type="submit" class="btn" value="삭제"  style="background-color:#dd4b39;"
								onclick="location.href='/deletecomment?cm_cbno=${CV.cm_cbno}';">삭제</button>
								
                    		</dd>
					</c:if>
				</dl>
				<br>
				<dl>
					<dd style="float:left;">
					<button type="submit" class="btn" value="댓글 달기" style="background-color:#FFDB83;"
					onclick="location.href='/recommentwrite';">댓글 달기</button>
					</dd>
				</dl>
                </div>
                </c:forEach>
                
                <div class="board_page" style="text-align:center;">
                	<ul class= "pagination pagination-sm no-margin pull-center">
<!--                 <a href="#" class="bt first"><<</a> -->
                	<c:if test="${pageMaker.prev }">
					<li><a href="/economy?page=${pageMaker.startPage-1 }"class="bt prev"><</a></li>
					</c:if>
               <c:forEach var="idx" begin="${pageMaker.startPage }" end="${pageMaker.endPage }" step="1">
					<li 
						<c:out value="${idx == pageMaker.cri.page? 'class=active':'' }"/>
						><a href="/economy?page=${idx }" class="num">${idx }</a></li>
				</c:forEach>
                <c:if test="${pageMaker.next }">
					<li><a href="/economy?page=${pageMaker.endPage+1 }" class="bt next">></a></li>
				</c:if>
<!--                 <a href="#" class="bt last">>></a> -->
                </ul>
                
         </div>
            </div>
            <br>
             <form role="form" action="/insertcomment" method="post">
            <div class="board_write">
             <div class="title"  >
             		<dl>
             			<dt>댓글 내용 작성</dt>
             		</dl>
             	 	<dl>
                        <dt>작성자</dt>
                        <dd><input type="text" name="cm_nick" style="width:20%;" value="${nick }" readonly></dd>
                    </dl>
             	 <div class="cont">
                    <textarea name="cm_content"  style="resize: none; width:100%; height:100%;" placeholder="댓글을 작성해주세요"></textarea>
                </div>
            <div class="bt_wrap"> 
            	<input class="sbtn" type="submit"  value="등록하기">
				<input type="hidden" name="bno" value="${vo.bno }">
            </div>
             </div>
			</div>
            </form>
            
         </div>
        

</body>
</html>

<style>
* {
    margin: 0;
    padding: 0;
}

html {
    font-size: 10px;
}

ul, li {
    list-style: none;
}

a {
    text-decoration: none;
    color: inherit;
}

.board_wrap {
    width: 1000px;
    margin: 40px auto;
}

.board_title {
    margin-bottom: 30px;
}

.board_title strong {
    font-size: 3rem;
}

.board_title p {
    margin-top: 5px;
    font-size: 1.4rem;
}

.bt_wrap {
    margin-top: 30px;
    text-align: center;
}

.bt_wrap a {
    display: inline-block;
    min-width: 80px;
    margin-left: 10px;
    padding: 10px;
    border: 1px solid #FFDB83;
    border-radius: 2px;
    font-size: 1.4rem;
}

.bt_wrap a:first-child {
    margin-left: 0;
}

.bt_wrap a.on {
    background: #FFDB83;
    color: #fff;
}

.board_list {
    width: 100%;
    border-top: 2px solid #000;
}

.board_list > div {
    border-bottom: 1px solid #ddd;
    font-size: 0;
}

.board_list > div.top {
    border-bottom: 1px solid #999;
}

.board_list > div:last-child {
    border-bottom: 1px solid #000;
}

.board_list > div > div {
    display: inline-block;
    padding: 15px 0;
    text-align: center;
    font-size: 1.4rem;
}

.board_list > div.top > div {
    font-weight: 600;
}

.board_list .num {
    width: 10%;
}

.board_list .title {
    width: 60%;
    text-align: left;
}

.board_list .top .title {
    text-align: center;
}

.board_list .writer {
    width: 10%;
}

.board_list .date {
    width: 10%;
}

.board_list .count {
    width: 10%;
}

.board_page {
    margin-top: 30px;
    text-align: center;
    font-size: 0;
}

.board_page a {
    display: inline-block;
    width: 32px;
    height: 32px;
    box-sizing: border-box;
    vertical-align: middle;
    border: 1px solid #ddd;
    border-left: 0;
    line-height: 100%;
}

.board_page a.bt {
    padding-top: 10px;
    font-size: 1.2rem;
    letter-spacing: -1px;
}

.board_page a.num {
    padding-top: 9px;
    font-size: 1.4rem;
}

.board_page a.num.on {
    border-color: #000;
    background: #000;
    color: #fff;
}

.board_page a:first-child {
    border-left: 1px solid #ddd;
}

.board_view {
    width: 100%;
    border-top: 2px solid #66BB7A;
    margin-left: 20px;
}

.board_view .title {
    padding: 20px 15px;
    border-bottom: 1px dashed #ddd;
    font-size: 2.2rem;
}

.board_view .info {
	padding: 15px 0px 0px 20px;
    border-bottom: 1px solid #66BB7A;
    font-size: 0;
    display: flex;
    justify-content: space-between;
}

.board_view .info dl {
    position: relative;
    display: inline-block;
    padding: 0 20px;
}

.board_view .info dl:first-child {
    padding-left: 0;
}

.board_view .info dl::before {
    content: "";
    position: absolute;
    top: 1px;
    left: 0;
    display: block;
    width: 1px;
    height: 13px;
    background: #ddd;
}

.board_view .info dl:first-child::before {
    display: none;
}

.board_view .info dl dt,
.board_view .info dl dd {
    display: inline-block;
    font-size: 1.4rem;
}

.board_view .info dl dt {

}

.board_view .info dl dd {
    margin-left: 15px;
    color: #777;
}

.board_view .cont {
    padding: 15px;
    border-bottom: 1px solid #66BB7A;
    line-height: 160%;
    font-size: 1.4rem;
}

.board_write {
    border-top: 2px solid #66BB7A;
}

.board_write .title,
.board_write .info {
    padding: 15px 0px 0px 15px;
}

.board_write .info {
    border-top: 1px dashed #ddd;
    border-bottom: 1px solid #66BB7A;
    font-size: 0;
}

.board_write .title dl {
    font-size: 0;
}

.board_write .info dl {
    display: inline-block;
    width: 33.3%;
    vertical-align: middle;
}

.board_write .title dt,
.board_write .title dd,
.board_write .info dt,
.board_write .info dd {
    display: inline-block;
    vertical-align: middle;
    font-size: 1.4rem;
}

.board_write .title dt,
.board_write .info dt {
    width: 100px;
}

.board_write .title dd {
    width: calc(100% - 100px);
}

.board_write .title input[type="text"],
.board_write .info input[type="text"],
.board_write .info input[type="password"] {
    padding: 10px;
    box-sizing: border-box;
}

.board_write .title input[type="text"] {
    width: 80%;
}

.board_write .cont {
    border-bottom: 1px solid #66BB7A;
}

.board_write .cont textarea {
    display: block;
    width: 100%;
    height: 300px;
    padding: 30px;
    box-sizing: border-box;
    border: 0;
    resize: vertical;
}


@media (max-width: 1000px) {
    .board_wrap {
        width: 100%;
        min-width: 320px;
        padding: 0 30px;
        box-sizing: border-box;
    }

    .board_list .num,
    .board_list .writer,
    .board_list .count {
        display: none;
    }

    .board_list .date {
        width: 40%;
    }

    .board_list .title {
        text-indent: 10px;
    }

    .board_list .top .title {
        text-indent: 0;
    }

    .board_page a {
        width: 26px;
        height: 26px;
    }

    .board_page a.bt {
        padding-top: 7px;
    }
    
    .board_page a.num {
        padding-top: 6px;
    }

    .board_view .info dl {
        width: 50%;
        padding: 0;
    }

    .board_view .info dl:nth-child(-n+2) {
        margin-bottom: 5px;
    }

    .board_view .info dl::before {
        display: none;
    }

    .board_view .info dl dt,
    .board_view .info dl dd {
        font-size: 1.2rem;
    }

    .board_write .info dl {
        width: 49%;
    }

    .board_write .info dl:first-child {
        margin-right: 2%;
    }

    .board_write .title dt,
    .board_write .info dt {
        display: none;
    }

    .board_write .title dd,
    .board_write .info dd {
        width: 100%;
    }

    .board_write .title input[type="text"],
    .board_write .info input[type="text"],
    .board_write .info input[type="password"] {
        width: 100%;
    }
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

</style>
</div>
<%@ include file="../include/footer.jsp"%>