<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
    <%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
    <%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
<h2>${msg}</h2>
<h3>Total number of processes : ${fn:length(list)}</h3>

<form:form method="POST" action="/PerformanceMonitor/showProcessUsage">
<input type= "text" placeholder="Type Process name" name = "processName">
<br>
<br>
<input type="submit" value="Get Usage">
</form:form>

<!--  
<ul>
			<c:forEach var="listValue" items="${list}">
				<li>${listValue}</li>
			</c:forEach>
		</ul>
-->		
</body>
</html>