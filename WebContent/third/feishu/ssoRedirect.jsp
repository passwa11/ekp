<%@ page import="com.landray.kmss.util.StringUtil" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%
String to = request.getParameter("to");
if(StringUtil.isNull(to)){
    response.sendRedirect(StringUtil.isNull(request.getContextPath())?"/":request.getContextPath());
    return;
}

to = new String(org.bouncycastle.util.encoders.Base64.decode(to),"UTF-8");
response.sendRedirect(to);

%>