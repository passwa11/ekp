<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@page import="com.landray.kmss.km.smissive.util.KmSmissiveConfigUtil"%>
<%@page import="com.landray.kmss.sys.number.util.NumberResourceUtil"%>
<%@page import="com.landray.kmss.sys.attachment.util.JgWebOffice"%>
<%@page import="com.landray.kmss.sys.attachment.integrate.wps.util.SysAttWpsCloudUtil"%>
<%@page import="com.landray.kmss.sys.attachment.integrate.wps.util.SysAttWpsWebOfficeUtil"%>
<%
	 pageContext.setAttribute("_isJGEnabled", new Boolean(
			com.landray.kmss.sys.attachment.util.JgWebOffice
					.isJGEnabled()));
     pageContext.setAttribute("_isWpsWebOfficeEnable", new Boolean(SysAttWpsWebOfficeUtil.isEnable()));
     pageContext.setAttribute("_isWpsCloudEnable", new Boolean(SysAttWpsCloudUtil.isEnable()));
%>
<template:replace name="title">
	<c:choose>
		<c:when test="${ kmSmissiveMainForm.method_GET == 'add' }">
			<c:out value="${lfn:message('km-smissive:kmSmissiveMain.create.title') } - ${ lfn:message('km-smissive:module.km.smissive') }"></c:out>	
		</c:when>
		<c:otherwise>
			<c:out value="${kmSmissiveMainForm.docSubject} - ${ lfn:message('km-smissive:module.km.smissive') }"></c:out>
		</c:otherwise>
	</c:choose>
</template:replace>
<template:replace name="toolbar">
	<c:if test="${kmSmissiveMainForm.docDeleteFlag ==1}">
	<ui:toolbar id="toolbar" style="display:none;" count="6"></ui:toolbar>
</c:if>
<c:if test="${kmSmissiveMainForm.docDeleteFlag !=1}">
	<ui:toolbar id="toolbar" layout="sys.ui.toolbar.float" count="6">
	<%@ include file="/km/smissive/script.jsp"%>
		<%-- 暂存 --%> 
	<c:if test="${kmSmissiveMainForm.method_GET=='add'}">
		<c:choose>
			<c:when test="${_isWpsWebOfficeEnable == 'true' || _isWpsCloudEnable == 'true'}">
				<ui:button text="${lfn:message('km-smissive:smissive.button.store') }" order="2" onclick="submitForm('save','10');">
				</ui:button>
				<c:choose>
					<c:when test="${param.approveModel eq 'right'}">
						 <ui:button text="${lfn:message('button.submit') }" order="2" onclick="submitForm('save','20');"  styleClass="lui_widget_btn_primary" isForcedAddClass="true">
						</ui:button>
					</c:when>
					<c:otherwise>
						 <ui:button text="${lfn:message('button.submit') }" order="2" onclick="submitForm('save','20');" >
						</ui:button>
					</c:otherwise>
				</c:choose>
			</c:when>
			<c:otherwise>
				<ui:button text="${lfn:message('km-smissive:smissive.button.store') }" order="2" onclick="if(addBookMarks())submitForm('save','10');">
				</ui:button>
				<c:choose>
					<c:when test="${param.approveModel eq 'right'}">
						 <ui:button text="${lfn:message('button.submit') }" order="2" onclick="if(addBookMarks())submitForm('save','20');"  styleClass="lui_widget_btn_primary" isForcedAddClass="true">
						</ui:button>
					</c:when>
					<c:otherwise>
						 <ui:button text="${lfn:message('button.submit') }" order="2" onclick="if(addBookMarks())submitForm('save','20');" >
						</ui:button>
					</c:otherwise>
				</c:choose>
			</c:otherwise>
		</c:choose>
		
	</c:if>
	<% if(NumberResourceUtil.isModuleNumberEnable("com.landray.kmss.km.smissive.model.KmSmissiveMain")){ %>
		<c:if  test="${aaa['modifyDocNum4Draft'] == 'true' and not empty fdNoId}">
			    <ui:button text="${lfn:message('km-smissive:kmSmissive.modifyDocNum') }" order="3"
					 onclick="generateFileNum();">
				</ui:button>
	    </c:if>
	<%} %>
	<c:if test="${kmSmissiveMainForm.method_GET=='edit' && (kmSmissiveMainForm.docStatus=='10' || kmSmissiveMainForm.docStatus=='11')}">
		<c:choose>
			<c:when test="${_isWpsWebOfficeEnable == 'true' || _isWpsCloudEnable == 'true'}">
			 	<%-- 编辑 --%>
			    <ui:button text="${lfn:message('km-smissive:smissive.button.store') }" order="2" onclick="submitForm('update','10');">
				</ui:button>
				<c:choose>
					<c:when test="${param.approveModel eq 'right'}">
						<ui:button text="${lfn:message('button.submit') }" order="2" onclick="submitForm('update','20');" styleClass="lui_widget_btn_primary" isForcedAddClass="true">
						</ui:button>
					</c:when>
					<c:otherwise>
						<ui:button text="${lfn:message('button.submit') }" order="2" onclick="submitForm('update','20');">
						</ui:button>
					</c:otherwise>
				</c:choose>
			</c:when>
			<c:otherwise>
				<%-- 编辑 --%>
			    <ui:button text="${lfn:message('km-smissive:smissive.button.store') }" order="2" onclick="if(addBookMarks())submitForm('update','10');">
				</ui:button>
				<c:choose>
					<c:when test="${param.approveModel eq 'right'}">
						<ui:button text="${lfn:message('button.submit') }" order="2" onclick="if(addBookMarks())submitForm('update','20');" styleClass="lui_widget_btn_primary" isForcedAddClass="true">
						</ui:button>
					</c:when>
					<c:otherwise>
						<ui:button text="${lfn:message('button.submit') }" order="2" onclick="if(addBookMarks())submitForm('update','20');">
						</ui:button>
					</c:otherwise>
				</c:choose>
			</c:otherwise>
		</c:choose>	
		
	</c:if>
	<c:if test="${kmSmissiveMainForm.method_GET=='edit'&& kmSmissiveMainForm.docStatus!='10' && kmSmissiveMainForm.docStatus!='11'}">
		<c:choose>
			<c:when test="${_isWpsWebOfficeEnable == 'true' || _isWpsCloudEnable == 'true'}">
				<c:choose>
					<c:when test="${param.approveModel eq 'right'}">
						<ui:button text="${lfn:message('button.submit') }" order="2" onclick="submitForm('update');" styleClass="lui_widget_btn_primary" isForcedAddClass="true">
						</ui:button>
					</c:when>
					<c:otherwise>
						<ui:button text="${lfn:message('button.submit') }" order="2" onclick="submitForm('update');">
						</ui:button>
					</c:otherwise>
				</c:choose>	
			</c:when>
			<c:otherwise>
				<c:choose>
					<c:when test="${param.approveModel eq 'right'}">
						<ui:button text="${lfn:message('button.submit') }" order="2" onclick="if(addBookMarks())submitForm('update');" styleClass="lui_widget_btn_primary" isForcedAddClass="true">
						</ui:button>
					</c:when>
					<c:otherwise>
						<ui:button text="${lfn:message('button.submit') }" order="2" onclick="if(addBookMarks())submitForm('update');">
						</ui:button>
					</c:otherwise>
				</c:choose>	
			</c:otherwise>
		</c:choose>
	</c:if>
	 <ui:button text="${ lfn:message('button.close') }" order="5"  onclick="Com_CloseWindow()">
	 </ui:button>
	</ui:toolbar>
	</c:if>
</template:replace>
<template:replace name="path">
		<ui:combin ref="menu.path.simplecategory">
			<ui:varParams 
				moduleTitle="${ lfn:message('km-smissive:table.kmSmissiveMain') }" 
				modulePath="/km/smissive/" 
				modelName="com.landray.kmss.km.smissive.model.KmSmissiveTemplate" 
				autoFetch="false"
				href="/km/smissive/" 
				categoryId="${kmSmissiveMainForm.fdTemplateId}" />
		</ui:combin>
</template:replace>	

