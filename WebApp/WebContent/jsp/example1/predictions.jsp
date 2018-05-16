<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="jsp.example1.*" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>Predictions</title>
</head>
<body>
	  
	<jsp:useBean id="preds" 
				 type="jsp.example1.logic.Predictions"
				 class="jsp.example1.logic.Predictions">
	</jsp:useBean>
			
		<% 
			String verb = request.getMethod();
			
			
			if (!verb.equalsIgnoreCase("GET")) {
				response.sendError(response.SC_METHOD_NOT_ALLOWED, "Only GET requests are allowed.");
			} else {
				preds.setServletContext(application);
				out.println(preds.getPredictions());
			}
		%>	
</body>
</html>