<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/resource/jsp/common.jsp"%>
<%@page import="com.landray.kmss.sys.anonym.util.SysAnonymUtil" %>
<%@page import="com.landray.kmss.framework.plugin.core.config.IExtension" %>
<%@page import="com.landray.kmss.sys.anonym.util.SysAnonymUtil" %>
<%@page import="com.landray.kmss.common.forms.IExtendForm"%>
<c:set var="sysAnonymForm" value="${requestScope[param.formName]}" />
<c:set var="anonymFdKey" value="${param.fdKey}" />
<c:set var="anonymOrder" value="${empty param.order ? '70' : param.order}"/>
<c:set var="anonymDisable" value="${empty param.disable ? 'false' : param.disable}"/>
<% 
	IExtendForm docMainForm = (IExtendForm)pageContext.getAttribute("sysAnonymForm");
	String fdModelId = docMainForm.getFdId();
	String fdModelName = docMainForm.getModelClass().getName();
	pageContext.setAttribute("fdModelId", fdModelId);
	pageContext.setAttribute("fdModelName", fdModelName);
	IExtension extension = SysAnonymUtil.getExtensionByModelName(fdModelName);
	if (extension != null) {
		request.setAttribute("anonymEts", extension);
	}
%>
<c:if test="${not empty anonymEts}">
	<script language="JavaScript">
		Com_IncludeFile("dialog.js");  
		function anonym_publish(){ 
		 	var url ="/sys/anonym/sys_anonym_main/sysAnonymMain_viewFrame.jsp?fdModelName=${fdModelName}&fdModelId=${fdModelId}&fdKey=${anonymFdKey}";   
			seajs.use([ 'lui/dialog'], function(dialog,lang) {
				dialog.iframe(url, '<bean:message bundle="sys-anonym" key="table.sysAnonymMain.opt" />', null, {width : 700, height : 310});
			});
		} 
	</script>
	<c:set var="sysAnonymUrl"
		   value="/sys/anonym/sys_anonym_main/sysAnonymMain_viewFrame.jsp?fdModelNameParam=${fdModelName}&fdModelIdParam=${fdModelId}" />
	<ui:button parentId="toolbar" text="${lfn:message('sys-anonym:button.publish.label') }" onclick="anonym_publish();" order="3" />
	<ui:content title="${lfn:message('sys-anonym:button.publish.label') }" cfg-order="${anonymOrder}" cfg-disable="${anonymDisable}" titleicon="${not empty param.titleicon?param.titleicon:''}" >
		<ui:event event="show">
			showAnonymTab();
		</ui:event>
		<%@include file="/sys/anonym/import/sysAnonym_viewSimple.jsp"%>
	</ui:content>
</c:if>