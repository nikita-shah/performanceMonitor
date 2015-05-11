<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Usage Information</title>
<script src='http://cdn.zingchart.com/zingchart.min.js'></script>
<script
	src="http://ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js"></script>
</head>
<body>


	<%-- <!-- call some controller method to display an image -->
	<!-- something i was trying with the static chart refresh -->
	<!--  <% response.setIntHeader("Refresh",1); %>  -->  	
	<!-- this is a static image <img src="<c:url value="/charts/timeSeriesChartDemo1"/>" alt="check check"/>-->
	<!-- getting a static chart from values out of database -->
	<!--  <img src="<c:url value="/charts/memoryUsageChart/${processName}"/>" alt="check check"/>-->
	 <!-- this include works absolutely fine .. i wanted to give the user entered process name so was trying something out -->	
	 <%@ include file="ZingFeed.html" %> 
 --%>


	<script>
		var chartData = {
			"type" : "line",
			"title" : {
				"text" : "Resource usage by ${processName}"
			},
			 "chart":{
		            "marginRight":100
		        },
			"legend" : {
				"draggable":true,
				 "drag-handler":"icon",
				    "icon":{
				        "line-color":"yellow",
				        "line-width":"3px",
				        
				    },
				    "marker":{
				        "type":"circle"
				      }
			},
			"scale-x":{
			      "zooming":true
			    },
			    "scale-y":{
			      "zooming":true
			    },
			    "curtain":[
		                    {
		                    "text": "the data is loading...",
		                    "color": "red",
		                    "text-size": 30,
		                    "bold": true,
		                    "alpha": 0.5
		                }
		                ],
			"refresh" : {
				"type" : "feed",
				"transport" : "js",
				"url" : "feed()",
				"interval" : 1000
			},
			"plot" : {
				"valueBox" : {
					"type" : "all",
					"placement" : "top"
				}
			},
			"series" : [ {

				"values" : [],
				"text" : "memory usage"

			},
			{
				"values" : [],
				"text" : "page faults"
			},

			{
				"values" : [],
				"text" : "resident memory"
			},
			{
				"values" : [],
				"text" : "virtual mem size"
			},
			{
				"values" : [],
				"text" : "cpu percent*100"
			},
			{
				"values" : [],
				"text" : "number of processes"
			}
			 ]
		};

		window.onload = function() {
			zingchart.render({
				id : "chartDiv",
				data : chartData,
				height : 600,
				width : "100%",
				//defaultsurl:"./resources/candy.txt"
			});
		};

		window.feed = function(callback) {
			$.ajax({
				type : "GET",
				dataType : "json",
				headers : {
					Accept : "application/json",
					"Access-Control-Allow-Origin" : "*"
				},
				url : "/PerformanceMonitor/showProcessUsage/${processName}",
				success : function(data) {
					var mem = data.mem.size / 100000;
					var tick = {
						plot0 : parseInt(mem),
						plot1 : parseInt(data.mem.pagefaults),
						plot2 : parseInt(data.mem.resident/100000),
						plot3 : parseInt(data.mem.vsize/100000),
						plot4 : parseInt(data.cpu.percent * 100),
						plot5 : parseInt(data.cpu.processes)
										
					};
					callback(JSON.stringify(tick));
				}
			});
		};
		
		
	</script>
	<%--   <div id="processInfo">
    process name : ${processName}
	<br> cpu percentage : ${cpuPercentage}
	<br> memory usage : ${memorySize}</div> --%>
	<br>
	<br>
	<br>
	<div id='chartDiv'></div>
	<button style="background-color:lightblue" id="clearFeed" type="button" >Clear Feed</button>
	<button style="background-color:lightblue" id="startFeed" type="button" >Start Feed</button>
    <button style="background-color:lightblue" id="stopFeed" type="button" >Stop Feed</button>

<script>
$("#clearFeed").bind('click', function() {
	  zingchart.exec("chartDiv", "clearfeed");
	});
$("#startFeed").bind('click', function() {
	  zingchart.exec("chartDiv", "startfeed");
	});
$("#stopFeed").bind('click', function() {
	  zingchart.exec("chartDiv", "stopfeed");
	});
</script>

</body>
</html>