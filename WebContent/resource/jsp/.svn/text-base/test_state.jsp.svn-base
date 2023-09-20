<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
String mode = String.valueOf(request.getServletContext().getInitParameter("pluginMode"));
//如果该节点是安全模式，表示无法对外提供服务，应该返回unavailable，否则返回OK
if("safe".equalsIgnoreCase(mode)){  
    response.setStatus(HttpServletResponse.SC_SERVICE_UNAVAILABLE);
}else{
    response.setStatus(HttpServletResponse.SC_OK);
}
%>


