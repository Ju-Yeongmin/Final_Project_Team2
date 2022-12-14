<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>    
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ include file="../include/header.jsp" %>
<%@ include file="../include/sidebarAsset.jsp" %>
	
<h1>달력형 가계부</h1>

<!-- jQuery.number -->
<script src="/resources/js/jquery.number.min.js"></script>
<!-- fullcalendar CDN -->
<link href='https://cdn.jsdelivr.net/npm/fullcalendar@5.8.0/main.min.css' rel='stylesheet' />
<script src='https://cdn.jsdelivr.net/npm/fullcalendar@5.8.0/main.min.js'></script>
<!-- fullcalendar 언어 CDN -->
<script src='https://cdn.jsdelivr.net/npm/fullcalendar@5.8.0/locales-all.min.js'></script>
<!-- fmt --> 
<script class="cssdesk" src="https://cdnjs.cloudflare.com/ajax/libs/moment.js/2.11.0/moment.min.js" type="text/javascript"></script>

<div class="row" style="padding: 50px;">
<input id="chk" class="chk" value="1" type="checkbox" checked>지출
<input id="chk" class="chk" value="2" type="checkbox" checked>수입
<div id="calendar"></div>
</div>

<style>
.fc-toolbar-chunk {
  display: flex; // 일렬로 나란히
  align-items: center; // 수직 가운데 정렬
}
</style>

<script>
//document.addEventListener('DOMContentLoaded', function() {
//	console.log('확인');
$(document).ready(function (){
	
	var cal = document.getElementById('calendar');
	
	var mm=0;
	var inout=0;
	
	calendar = new FullCalendar.Calendar(cal, {
		initialView: 'dayGridMonth',
	    customButtons: {
	    	customPrev: {
				text: '<',
				click: function() {
					mm+=1;
					calendar.prev();
				}
			},
			customNext: {
				text: '>',
				click: function() {
					mm-=1;
					calendar.next();
				}
			},
			customToday:{
				text: '오늘',
				click: function(){
					mm=0;
					calendar.today();
				}
			}
		},
		headerToolbar: {
			right: 'customToday',
			center: 'customPrev title customNext',
			left: ''
		},
		dayHeaderContent: function (date) {
			  let weekList = ["일", "월", "화", "수", "목", "금", "토"];
			  return weekList[date.dow];
		},
		titleFormat: function(date) {
			year = date.date.year;
			month = date.date.month + 1;
			return year + "년 " + month + "월";
		},
		events : 
		function(info, successCallback, failureCallback){
			$.ajax({
				url: "/asset/cal?mm="+mm+"&inout="+inout,
					method: "GET",
					dataType: "json",
					success:
						function(result){
						console.log(result);
						var events = [];	
						if(result!=null){
							$.each(result, function(index, element){
								if(element.sort=="out"){
									events.push({
										title : '-'+$.number(element.title)+'원',
										start : moment(element.start).format('YYYY-MM-DD'),
										textColor : "#DD4B39",
										color : "#FFFFFF",
										cid:"1"
									});
								} else if (element.sort=="in"){
									events.push({
										title : '+'+$.number(element.title)+'원',
										start : moment(element.start).format('YYYY-MM-DD'),
										textColor : "#00A65A",
										color : "#FFFFFF",
										cid:"2"
									});
								}
							});
						}	//if
						successCallback(events);
					}	//success function
				});	//ajax
		},	//event
		eventDidMount: function (arg) {
			var chk = document.querySelectorAll(".chk");
			chk.forEach(function (v) {
				if (v.checked) {
					// 체크된 값 표시
					if (arg.event.extendedProps.cid === v.value) {
						arg.el.style.display = "block";
					}
				} else {
					// 체크되지않은 값 표시X
					if (arg.event.extendedProps.cid === v.value) {
						arg.el.style.display = "none";
					}
				}
			});
		}	//eventDidMount
	});	//calendar
	
	calendar.render();
	
	var csx = document.querySelectorAll(".chk");
	csx.forEach(function (el) {
		el.addEventListener("change", function () {
			calendar.refetchEvents();
		});
	});
});
</script>

<%@ include file="../include/footer.jsp" %>