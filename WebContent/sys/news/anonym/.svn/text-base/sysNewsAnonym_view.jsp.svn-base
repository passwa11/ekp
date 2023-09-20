<%@page import="com.landray.kmss.sys.attachment.util.JgWebOffice"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<%@page import="com.landray.kmss.sys.anonym.util.SysAnonymUtil" %>
<%@page import="com.landray.kmss.sys.anonym.model.*" %>
<%@page import="com.landray.kmss.sys.news.model.SysNewsMain" %>
<%@page import="com.landray.kmss.sys.news.forms.SysNewsMainForm" %>
<%@page import="com.landray.kmss.sys.news.service.ISysNewsMainService" %>
<%@page import="com.landray.kmss.util.*" %>
<%@page import="com.landray.kmss.common.actions.RequestContext" %>
<%

	/* SysAnonymMain sysAnonymMain = (SysAnonymMain)request.getAttribute("sysAnonymMain");
	if(sysAnonymMain != null){
		String fdModelId = sysAnonymMain.getFdModelId();
		String fdModelName = sysAnonymMain.getFdModelName();
		if(StringUtil.isNotNull(fdModelId)){
			SysNewsMainForm sysNewsMainForm = null;
			ISysNewsMainService sysNewsMainService = (ISysNewsMainService)SpringBeanUtil.getBean("sysNewsMainService");
			SysNewsMain sysNewsMain = (SysNewsMain)sysNewsMainService.findByPrimaryKey(fdModelId);
			sysNewsMainForm = (SysNewsMainForm)sysNewsMainService.cloneModelToForm(sysNewsMainForm, sysNewsMain, new RequestContext(request));
			pageContext.setAttribute("sysNewsMainForm", sysNewsMainForm);
			request.setAttribute("com.landray.kmss.web.taglib.FormBean", sysNewsMainForm);
		}
	} */
	
%>
<template:include ref="default.view" >
 	<template:replace name="head">
 		<style type="text/css">
	 		.headline{
	 			margin: 15px 0;
				line-height: 1.42857143;
				text-align: left;
				padding: 10px 0;
				border-bottom: 1px solid #d7d7d7;
	 		}
	 	</style>
    </template:replace>
	<template:replace name="title">
		<c:out value="${sysAnonymCommonForm.fdName} - ${ lfn:message('sys-news:news.moduleName') }"></c:out>
	</template:replace>
	<template:replace name="toolbar">
		<ui:toolbar id="toolbar" layout="sys.ui.toolbar.float" count="5">
			<ui:button text="${lfn:message('button.close')}" order="5"
				onclick="Com_CloseWindow();">
			</ui:button>
		</ui:toolbar>
	</template:replace>
	<template:replace name="content">
		<div class='lui_form_title_frame'>
			<div class='lui_form_subject'>
				<bean:write name="sysAnonymCommonForm" property="fdName" />
			</div>
			<div class='lui_form_baseinfo'>
				<!--发布日期-->
				<c:if test="${ not empty sysAnonymCommonForm.docPublishTime }">
					<bean:message key="sysNewsMain.docPublishTime"  bundle="sys-news" />：
					<span><kmss:showDate value="${sysAnonymCommonForm.docPublishTime}" type="datetime"></kmss:showDate></span>
				</c:if>
				<!--作者-->
				<bean:message bundle="sys-news" key="sysNewsMain.fdAuthorId" />：
					<span>
						<c:out value="${sysAnonymCommonForm.docAuthorName}"></c:out>
					</span>
				<bean:message bundle="sys-news" key="sysNewsMain.fdDepartmentId" />：<span><bean:write	 name="sysAnonymCommonForm" property="fdDeptName" /></span>
			</div>
		</div>
		<div class="headline"></div>
		<!--内容-->
		<!-- 摘要 -->
		<c:if test="${ not empty sysAnonymCommonForm.fdSummary }">
			<div class="lui_form_summary_frame" style="text-indent: 0;">
				<font color="#003048">
					<bean:write name="sysAnonymCommonForm" property="fdSummary" />
				</font>
			</div>
		</c:if>
		<div class="lui_form_content_frame clearfloat"
			id="lui_form_content_frame">
			<div style="min-height: 150px;" id="contentDiv">
				<c:if test="${sysAnonymCommonForm.docField1=='rtf'}">
					${sysAnonymCommonForm.docContent}
				</c:if>
				<c:if test="${sysAnonymCommonForm.docField1=='word'}">
					<c:import url="/sys/anonym/dataview/attachment/sysAttMain_viewHtml.jsp" charEncoding="UTF-8">
						<c:param name="fdKey" value="fileContentKey" />
						<c:param name="fdAttType" value="office" />
						<c:param name="formBeanName" value="sysAnonymCommonForm" />
						<c:param name="fdModelId" value="${sysAnonymCommonForm.fdId}" />
						<c:param name="fdModelName" value="com.landray.kmss.sys.anonym.model.SysAnonymCommon" />
						<c:param name="isExpand" value="true" />
					</c:import>
				</c:if>
				<!-- pdf格式 -->
				<c:forEach items="${sysAnonymCommonForm.attachmentForms['fileContentKey'].attachments}" var="sysAttMain"	varStatus="vstatus">
					<c:set var="attId" value="${sysAttMain.fdId}"/>
					<c:set var="fdAttMainId" value="${sysAttMain.fdId}" scope="request"/>
				</c:forEach>
				<c:if test="${sysAnonymCommonForm.docField1=='att' && fn:length(sysAnonymCommonForm.attachmentForms['fileContentKey'].attachments)>0}">
					<%
						//取fdAttMainId的值判断附件是否已经转换
						if(JgWebOffice.isExistViewPath(request)) {
					%>
					<div class="lui_form_content_frame">
						<iframe id="pdfFrame" src="<c:url value="/sys/anonym/sysAnonymData.do?method=view&fdId=${attId}&inner=yes"/>" width="100%" style="min-height:565px;" frameborder="0">
						</iframe>
					</div>
					<%
					}else{
					%>
					<c:import url="/sys/anonym/dataview/attachment/sysAttMain_view.jsp" charEncoding="UTF-8">
						<c:param name="fdViewType" value="/sys/anonym/dataview/attachment/anoym_display.js" />
						<c:param name="formBeanName" value="sysAnonymCommonForm" />
						<c:param name="fdModelName" value="com.landray.kmss.sys.anonym.model.SysAnonymCommon" />
						<c:param name="fdKey" value="fileContentKey" />
					</c:import>
					<%
						}
					%>
				</c:if>
			</div>
			<!--附件-->
			<div class="lui_form_spacing"></div>
			<div>
				<c:import url="/sys/anonym/dataview/attachment/sysAttMain_view.jsp" charEncoding="UTF-8">
					<c:param name="fdViewType" value="/sys/anonym/dataview/attachment/anoym_display.js" />
					<c:param name="formBeanName" value="sysAnonymCommonForm" />
					<c:param name="fdKey" value="fileDocKey" />
					<c:param name="fdModelName" value="com.landray.kmss.sys.anonym.model.SysAnonymCommon" />
				</c:import>
			</div>
		</div>
	</template:replace>
</template:include>
