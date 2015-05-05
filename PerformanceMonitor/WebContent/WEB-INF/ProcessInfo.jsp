<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Usage Information</title>
 <script src='http://cdn.zingchart.com/zingchart.min.js'></script>
   <script src="http://ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js"></script>
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
        "type":"line",
        "title": {
            "text": "Resource usage by ${processName}"
        },
        "refresh": {
            "type": "feed",
            "transport": "js",
            "url": "feed()",
            "interval": 1000
        },
        "plot": {
            "valueBox": {
                "type": "all",
                "placement": "top"
            }
        },
        "series":[
            {
            	
                "values":[],
                "text":"1st plot"
            
            },
            {
            	"values":[],
            	"text":"2nd plot"
            }
        ]
    };
   
   

    window.onload = function() {
        zingchart.render({
            id: "chartDiv",
            data: chartData,
            height: 600,
            width: "100%"
        });
    };

    window.feed = function(callback) {
        $.ajax({
            type: "GET",
            dataType: "json",
            headers: {
                Accept: "application/json",
                "Access-Control-Allow-Origin": "*"
            },
            url: "/PerformanceMonitor/showProcessUsage/${processName}",
            success: function (data) {             
                var mem = data.mem.size/100000;
                var tick = {
                    plot0: parseInt(mem),
                    plot1: parseInt(data.cpu.percent*100)
                };
                callback(JSON.stringify(tick));
            }
        });
    };
    </script>
    <div id="processInfo">
    process name : ${processName}
	<br> cpu percentage : ${cpuPercentage}
	<br> memory usage : ${memorySize}</div>
	<br>
	<br>
	<br>
    <div id='chartDiv'></div>

    
</body>
</html>