<%@page import="com.landray.kmss.sys.authentication.user.KMSSUser"%>
<%@page import="com.landray.kmss.util.UserUtil"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%

out.clear(); 
out = pageContext.pushBody(); 


response.setCharacterEncoding("UTF-8");
String result = "";
KMSSUser user = UserUtil.getKMSSUser();
if(user!=null && !user.isAnonymous()){
	result = "true";
}else{
	result = "false";
}
response.getOutputStream().write(result.getBytes("UTF-8"));
response.getOutputStream().close();

return;
%>