<%@page import="com.landray.kmss.util.StringUtil"%>
<%@page import="com.landray.kmss.sys.log.util.UserOperHelper"%>
<%@page import="com.landray.kmss.sys.appconfig.model.BaseAppconfigCache"%>
<%@page import="com.landray.kmss.util.ResourceUtil" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<!doctype html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
</head>
<body style="margin:20px;">
<div style="line-height:25px;">
<%
		UserOperHelper.setEventType(ResourceUtil.getString("sys-config:sys.sysAdminCommonTools.clearConfigCache"));
		UserOperHelper.setModelNameAndModelDesc(null, ResourceUtil.getString("sys-admin:home.nav.sysAdmin"));
		try{
			BaseAppconfigCache.clear();
			out.write(ResourceUtil.getString("sys.common.configcacheclear.success"));
			UserOperHelper.setOperSuccess(true);
		}catch(Exception e){
			out.write(ResourceUtil.getString("sys.common.configcacheclear.failure") + e.toString());
			UserOperHelper.logErrorMessage(StringUtil.getStackTrace(e));
			UserOperHelper.setOperSuccess(false);
		}
%>
</div>
</body>
</html>