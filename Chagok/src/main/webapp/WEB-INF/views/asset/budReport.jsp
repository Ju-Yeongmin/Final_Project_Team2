<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ include file="../include/header.jsp"%>
<%@ include file="../include/sidebarAsset.jsp"%>

<div>
	<a href="/asset/budget?mm=0">이번 달</a>
	<a href="/asset/budget?mm=1">지난 달</a>
	<a href="/asset/budget?mm=2">2개월 전</a>
	<a href="/asset/budget?mm=3">3개월 전</a>
<hr>
</div>

<c:set var="a" value="${pMonth }"/>
<c:set var="y" value="${fn:substring(a,0,4) }"/>
<c:set var="m" value="${fn:substring(a,4,6) }"/>
<c:set var="pMonth" value="${y }년 ${m }월"/>
<c:set var="mm" value="${param.mm }" />

<input type="button" id="setbud"
	class="btn btn-block btn-success btn-lg"
	style="width: 200px; margin: 20px 40px" value="예산 수정하기"
	onclick="location.href='/asset/updBud?mm='+${mm}+'';">

<div class="row">
	<div class="col-md-12">
		<div class="box">
			<div class="box-header with-border">
				<h3 class="box-title">
				${pMonth } 예산 리포트
				</h3>
			</div>

			<c:set var="dtSum" value="${map.dtSum }" />
			<c:set var="totalBud" value="${map.totalBud }" />
			<c:set var="d" value="${dtSum div totalBud }" />
			<c:set var="d2" value="${totalBud - dtSum }"/>
			<div class="box-body">
				<div class="row">
					<div class="col-md-6" style="text-align: center;">
						<section ng-click="pageLink({menu:'report', subMenu:'budget'})">
							<div class="data-section-title">
								<h4>예산 소진율</h4>
							</div>
							<div id="radialprogress"></div>
							
						</section>
					</div>

					<div class="col-md-6">
						<p class="text-center">
						<h3>
							이번달 예산의
							<fmt:formatNumber value="${d }" type="percent" />
							를 소진했어요.
						</h3>
						<h4>${pMonth }
							예산 :
							<fmt:formatNumber value="${totalBud }" />
						</h4>
						<h4>
							오늘까지 지출
							<fmt:formatNumber value="${dtSum }" />
						</h4>
						</p>
						<div class="progress-group">
							<span class="progress-text">예산 소진율</span>
							<span class="progress-number">
							<fmt:formatNumber value="${dtSum }" /> / <fmt:formatNumber value="${totalBud }" />
							</span>
							<div class="progress">
								<div class="progress-bar progress-bar-green" role="progressbar"
									aria-valuemin="0" aria-valuemax="100"
									style="width:<fmt:formatNumber value="${d }" type="percent"/>">
								</div>
							</div>
						</div>


					</div>

				</div>

			</div>


			<div class="box-body">
				<div class="row">
					<div class="col-md-6">
						<h3>한 달 예산이 <fmt:formatNumber value="${d2 }" /> 원 남았어요.</h3>
						<div class="row">
							<div class="col-md-6">
								<h4>하루 평균 지출</h4>
								<span id="dayAvg"></span>
							</div>
							<div class="col-md-6">
								<h4>하루 평균 예산</h4>
								<span id="dayBud"></span>
							</div>
						</div>
						<div>
							<h3>이 추세로 쓴다면 이번달 예상 지출액은</h3>
							<h3>
								<fmt:formatNumber value="${map.expSum }" />
								원 입니다.
							</h3>
						</div>
						<div>
							<div class="info-box bg-green">
								<span class="info-box-number">지출 팁!</span>
								<span class="info-box-text">이번달 예산에 맞추기 위해서는<br>
								하루에<span id="expBud"></span>원씩 써야 합니다.</span>
							</div>
						</div>
					</div>

					<div class="col-md-6">
						<p class="text-center">
							<strong>일간 지출 추이</strong>
						</p>
						<div class="chart">
							<canvas id="linechart" style="height: 300px; width: 600px;"></canvas>
						</div>
					</div>

				</div>

			</div>


		</div>

	</div>

</div>

<script src="https://cdnjs.cloudflare.com/ajax/libs/d3/3.4.11/d3.min.js"></script>
<!-- jQuery.number -->
<script src="/resources/js/jquery.number.min.js"></script>
<!-- chart.js -->
<script src="https://cdnjs.cloudflare.com/ajax/libs/Chart.js/2.9.4/Chart.min.js"></script>

<script>
$(document).ready(function(){
	var svg ;

	function drawProgress(end){ 
		d3.select("svg").remove() 
		  if(svg){
		  svg.selectAll("*").remove();
		  
		}
		var wrapper = document.getElementById('radialprogress');
		var start = 0;
		 
		var colours = {
		  fill: '#00A65A',
		  track: '#FFD883',
		  text: '#00A65A',
		  stroke: '#fff',
		}
	
		var radius = 80;
		var border = 12;
		var strokeSpacing = 4;
		var endAngle = Math.PI * 2;
		var formatText = d3.format('.0%');
		var boxSize = radius * 2;
		var count = end;
		var progress = start;
		var step = end < start ? -0.01 : 0.01;
	
		//Define the circle
		var circle = d3.svg.arc()
		  .startAngle(0)
		  .innerRadius(radius)
		  .outerRadius(radius - border);
	
		//setup SVG wrapper
		svg = d3.select(wrapper)
		  .append('svg')
		  .attr('width', boxSize)
		  .attr('height', boxSize);
	
		  
		// ADD Group container
		var g = svg.append('g')
		  .attr('transform', 'translate(' + boxSize / 2 + ',' + boxSize / 2 + ')');
	
		//Setup track
		var track = g.append('g').attr('class', 'radial-progress');
		track.append('path')
		  .attr('fill', colours.track)
		  .attr('stroke', colours.stroke)
		  .attr('stroke-width', strokeSpacing + 'px')
		  .attr('d', circle.endAngle(endAngle));
	
		//Add colour fill
		var value = track.append('path')
		  .attr('fill', colours.fill)
		  .attr('stroke', colours.stroke)
		  .attr('stroke-width', strokeSpacing + 'px');
	
		//Add text value
		var numberText = track.append('text')
		  .attr('fill', colours.text)
		  .attr('text-anchor', 'middle')
		  .attr('dy', '.5rem'); 
	
		  //update position of endAngle
		  value.attr('d', circle.endAngle(endAngle * end));
		  //update text value
		  numberText.text(formatText(end));
	  
	}

	   drawProgress(${d});
});
</script>

<script type="text/javascript">
var date = new Date();
var firstDay = new Date(date.getFullYear(), date.getMonth(), 1);
var lastDay = new Date(date.getFullYear(), date.getMonth()+1, 0);
var day = (date.getTime()-firstDay.getTime()) / (1000*60*60*24);	// 1일~오늘
var day2 = (lastDay.getTime()-firstDay.getTime()) / (1000*60*60*24)+1;	// 1일~마지막일
var day3 = (lastDay.getTime()-date.getTime()) / (1000*60*60*24)+1;	// 1일~마지막일
var dayAvg = $.number(Math.round(${dtSum }/day));
var dayBud = $.number(Math.round(${totalBud }/day2));
var expBud = $.number(Math.round(${d2 }/day3));

$(document).ready(function(){
	$('#dayAvg').text(dayAvg+'원');
	$('#dayBud').text(dayBud+'원');
	$('#expBud').text(expBud+'원');
});
</script>

<script type="text/javascript">
var day = ${map.dayjson}
var label1 = new Array();
var value1 = new Array();

for(var i=0; i<day.length; i++){
	var d = day[i];
	label1.push(d.date);
	value1.push(d.sum);
}

var dataline1 = {
	labels: label1,
	datasets: [{
		data: value1,
	    fill: false,
	    borderColor: 'rgb(75, 192, 192)',
	    tension: 0.1
	}]
};

var optionline = {
		legend: {
			display: false
		}
	}

var ctx1 = document.getElementById('linechart').getContext('2d');
var linechart = new Chart(ctx1, {
	type: 'line',
	data: dataline1,
	options: optionline
});
</script>

<%@ include file="../include/footer.jsp"%>