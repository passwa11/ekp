<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/resource/jsp/common.jsp"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.HashMap"%>
<%@page
	import="java.util.List,java.util.ArrayList,com.landray.kmss.util.StringUtil,com.landray.kmss.util.ResourceUtil,com.landray.kmss.util.SpringBeanUtil,com.landray.kmss.util.UserUtil"%>
<%@page import="com.landray.kmss.sys.ui.plugin.SysUiPluginUtil"%>

<link rel="preload" type="text/css"
	href="<%=request.getContextPath() + "/" + SysUiPluginUtil.getThemesFileByName(request, "module")%>"></link>

<c:set var="anonymDataForm" value="${requestScope[param.formName]}" />
 
<ui:switch property="fdAnonymous" 
	enabledText="${ lfn:message('sys-portal:sysportal.switch.anonymSupp') }"
	disabledText="${ lfn:message('sys-portal:sysportal.switch.anonymNotSupp') }" showType="${param.showType }" 
	checked="${anonymDataForm.fdAnonymous}" onValueChange="valueChange(event)" ></ui:switch>

	
<script type="text/javascript">
	var func = '${param.switchChange}';
	function valueChange(event){
		var fdCanAnonym = $(event.target).is(":checked");
		if(func){
			eval(func+'('+fdCanAnonym+')');
		}
	}
</script>