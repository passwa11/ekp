<%@ page import="java.net.URLEncoder"%>
<%@ page import="com.landray.kmss.util.*"%>
<% 
	String to = request.getParameter("to");
	to = SecureUtil.BASE64Decoder(to);
	System.out.println(to);
	response.sendRedirect(to);
%>
