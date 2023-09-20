<%@page import="java.net.URLEncoder"%>
<%@page import="com.landray.kmss.util.StringUtil"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
	String redirectto = (String)request.getAttribute("redirectto");
	String referer = request.getParameter("_referer");
	String convertFlag ="";
	if(StringUtil.isNotNull(redirectto)){
		if(redirectto.indexOf("?") > -1){
			convertFlag="&";
		}else{
			convertFlag="?";
		}
	}
	if(StringUtil.isNotNull(referer)){
		redirectto +=convertFlag + "_referer=" + URLEncoder.encode(referer);
	}
	request.setAttribute("redirectto", redirectto);
	
if(redirectto.startsWith("http") || redirectto.startsWith("https") )
{
out.println(	"<html>	");
out.println(	"<script language='javascript' type='text/javascript'>   ");
out.println(  "top.window.location='"+ redirectto+"' ;")  ;
out.println(	"</script>"); 
out.println(	"</html>	");
}
else
{
%>
<c:redirect url="${redirectto}" />
<%
}
%>