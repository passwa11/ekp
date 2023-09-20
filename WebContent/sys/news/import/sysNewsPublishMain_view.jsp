<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/resource/jsp/common.jsp"%> 
<c:set var="sysNewsPublishMainForm" value="${requestScope[param.formName].sysNewsPublishMainForm}" />
<c:set var="docForm" value="${requestScope[param.formName]}" />
<c:if test="${sysNewsPublishMainForm.fdModelName!=null && 
sysNewsPublishMainForm.fdModelName!='' && sysNewsPublishMainForm.fdModelId!=null && sysNewsPublishMainForm.fdModelId!=''}"> 
	<c:set var="sysNewsPublishUrl"
		   value="/sys/news/sys_news_publish_main/sysNewsPublishMain_viewManualFrame.jsp?fdModelNameParam=${sysNewsPublishMainForm.fdModelName}&fdModelIdParam=${sysNewsPublishMainForm.fdModelId}" />
	<%
	    boolean isShowUiContent = !"false".equals(request.getParameter("isShowUiContent"));
	if(isShowUiContent){ %>
		<c:set var="order" value="${empty param.order ? '10' : param.order}"/>
		<c:set var="disable" value="${empty param.disable ? 'false' : param.disable}"/>
		<ui:content title="${lfn:message('sys-news:sysNewsPublishMain.tab.publish.label')}" titleicon="${not empty param.titleicon?param.titleicon:''}"
			cfg-order="${order}" cfg-disable="${disable}">
			 <%@ include file="sysNewsPublishMain_viewSimple.jsp"%>
		</ui:content>
	<%}else{ %>
	       <%@ include file="sysNewsPublishMain_viewSimple.jsp"%>
	<% }%>
</c:if>