<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" import="com.landray.kmss.util.ResourceUtil,java.io.File"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@page import="com.landray.kmss.sys.news.model.SysNewsConfig"%>
<%@page import="com.landray.kmss.sys.authorization.constant.ISysAuthConstant"%>
<%
	 pageContext.setAttribute("_isJGEnabled", new Boolean(
			com.landray.kmss.sys.attachment.util.JgWebOffice
					.isJGEnabled()));
   	 SysNewsConfig sysNewsConfig = new SysNewsConfig();
     pageContext.setAttribute("ImageW",sysNewsConfig.getfdImageW());
     pageContext.setAttribute("ImageH",sysNewsConfig.getfdImageH());

%>

<c:import url="/sys/recycle/import/redirect.jsp">
	<c:param name="formBeanName" value="sysNewsMainForm"></c:param>
</c:import>

<%-- 标题 --%>
<template:replace name="title">
<c:choose>
	<c:when test="${sysNewsMainForm.method_GET=='add' }">
		<c:out value="${ lfn:message('sys-news:sysNews.create.title') } - ${ lfn:message('sys-news:news.moduleName') }"></c:out>	
	</c:when>
	<c:otherwise>
		<c:out value="${sysNewsMainForm.docSubject} - ${ lfn:message('sys-news:news.moduleName') }"></c:out>
	</c:otherwise>
</c:choose>
</template:replace>
<template:replace name="head">
	<style>
		#office-iframe{
			width:100%;
			min-height:580px!important;
		}
	</style>
</template:replace>
<%-- 按钮栏 --%>
<template:replace name="toolbar">
	<c:if test="${kmReviewMainForm.docDeleteFlag ==1}">
		<div id="toolbar" style="display:none"></div>
	</c:if>
	<c:if test="${kmReviewMainForm.docDeleteFlag !=1}">
	<ui:toolbar id="toolbar" layout="sys.ui.toolbar.float" count="5">
		<c:choose>
			<c:when test="${ sysNewsMainForm.method_GET == 'add' }">
				<ui:button text="${lfn:message('sys-news:news.button.store') }" order="2" onclick="submitForm('save','10');">
				</ui:button>
				<ui:button text="${ lfn:message('sys-attachment:sysAttMain.preview') }" order="3" onclick="submitForm('savePreview','10');">
				</ui:button>
				<c:choose>
					<c:when test="${param.approveModel eq 'right'}">
						<ui:button text="${lfn:message('button.submit') }" order="2" styleClass="lui_widget_btn_primary" isForcedAddClass="true" 
								onclick="submitForm('save','20');">
						</ui:button>
					</c:when>
					<c:otherwise>
						<ui:button text="${lfn:message('button.submit') }" order="2" onclick="submitForm('save','20');"></ui:button>
					</c:otherwise>
				</c:choose>
			</c:when>
			<c:when test="${ sysNewsMainForm.method_GET == 'edit' && (sysNewsMainForm.docStatus=='10' || sysNewsMainForm.docStatus=='11')}">
				<ui:button text="${lfn:message('sys-news:news.button.store') }" order="2" onclick="submitForm('update','10');">
				</ui:button>
				<ui:button text="${ lfn:message('sys-attachment:sysAttMain.preview') }" order="3" onclick="submitForm('savePreview','10');">
				</ui:button>
				<c:choose>
					<c:when test="${param.approveModel eq 'right'}">
						<ui:button text="${lfn:message('button.submit') }" order="2" styleClass="lui_widget_btn_primary" isForcedAddClass="true" 
								onclick="submitForm('update','20');">
						</ui:button>
					</c:when>
					<c:otherwise>
						<ui:button text="${lfn:message('button.submit') }" order="2" onclick="submitForm('update','20');"></ui:button>
					</c:otherwise>
				</c:choose>
			</c:when>
			<c:when test="${sysNewsMainForm.method_GET=='edit'&& sysNewsMainForm.docStatus!='10'&& sysNewsMainForm.docStatus!='11'}">
				<ui:button text="${lfn:message('button.submit') }" order="2" onclick="submitForm('update');">
				</ui:button>	
			</c:when>			
		</c:choose>		
		<ui:button text="${lfn:message('button.close') }" order="5" onclick="Com_CloseWindow();">
		</ui:button>
	</ui:toolbar>
	</c:if>
</template:replace>
<%-- 路径 --%>
<template:replace name="path">
	<ui:combin ref="menu.path.simplecategory">
		<ui:varParams 
			moduleTitle="${ lfn:message('sys-news:news.moduleName') }" 
			modelName="com.landray.kmss.sys.news.model.SysNewsTemplate" 
			autoFetch="false"
			categoryId="${sysNewsMainForm.fdTemplateId}" />
	</ui:combin>
</template:replace>	