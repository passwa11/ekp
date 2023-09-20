<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ page import=" com.landray.kmss.util.ResourceUtil"%>
<%request.setAttribute("dateTimeFormatter",ResourceUtil.getString("date.format.datetime"));%>
<%
	 pageContext.setAttribute("_isJGEnabled", new Boolean(
			com.landray.kmss.sys.attachment.util.JgWebOffice
					.isJGEnabled()));
%> 
<%-- 页签名--%>
<template:replace name="title">
	<c:choose>
		<c:when test="${ kmImeetingSummaryForm.method_GET == 'add' }">
			<c:out value="${ lfn:message('km-imeeting:kmImeetingSummary.opt.create') } - ${ lfn:message('km-imeeting:module.km.imeeting') }"></c:out>	
		</c:when>
		<c:otherwise>
			<c:out value="${kmImeetingSummaryForm.fdName} - ${ lfn:message('km-imeeting:module.km.imeeting') }"></c:out>
		</c:otherwise>
	</c:choose>
</template:replace>

<%-- 按钮栏--%>
<template:replace name="toolbar">
	<ui:toolbar id="toolbar" layout="sys.ui.toolbar.float"> 
		<c:if test="${kmImeetingSummaryForm.method_GET=='edit'}">
			<c:choose>
				<c:when test="${param.approveModel eq 'right'}">
					<c:if test="${kmImeetingSummaryForm.docStatus<'20'}">
						<ui:button text="${lfn:message('button.savedraft') }" order="2" onclick="commitMethod('update', 'true');">
			  			</ui:button>
					   <ui:button text="${lfn:message('button.submit') }" order="2" onclick="commitMethod('update','false');" styleClass="lui_widget_btn_primary" isForcedAddClass="true">
					   </ui:button>
					</c:if>
					<c:if test="${kmImeetingSummaryForm.docStatus=='20'}">
					   <ui:button text="${lfn:message('button.submit') }" order="2" onclick="commitMethod('update','false');" styleClass="lui_widget_btn_primary" isForcedAddClass="true">
					   </ui:button>
					</c:if>
					<c:if test="${kmImeetingSummaryForm.docStatus>='30'}">
					    <ui:button text="${lfn:message('button.submit') }" order="2" onclick="Com_Submit(document.kmImeetingSummaryForm, 'update');" styleClass="lui_widget_btn_primary" isForcedAddClass="true">
					    </ui:button>
					</c:if>
				</c:when>
				<c:otherwise>
					<c:if test="${kmImeetingSummaryForm.docStatus<'20'}">
						<ui:button text="${lfn:message('button.savedraft') }" order="2" onclick="commitMethod('update', 'true');">
			   			</ui:button>
					   <ui:button text="${lfn:message('button.submit') }" order="2" onclick="commitMethod('update','false');">
					   </ui:button>
					</c:if>
					<c:if test="${kmImeetingSummaryForm.docStatus=='20'}">
					   <ui:button text="${lfn:message('button.submit') }" order="2" onclick="commitMethod('update','false');">
					   </ui:button>
					</c:if>
					<c:if test="${kmImeetingSummaryForm.docStatus>='30'}">
					    <ui:button text="${lfn:message('button.submit') }" order="2" onclick="Com_Submit(document.kmImeetingSummaryForm, 'update');">
					    </ui:button>
					</c:if>
				</c:otherwise>
			</c:choose>	
		</c:if>
		<c:if test="${kmImeetingSummaryForm.method_GET=='add'}">
				 <ui:button text="${lfn:message('km-imeeting:kmImeetingSummary.btn.relateToMeeing') }" order="2" onclick="relateToMeeting();">
			    </ui:button>
		        <ui:button text="${lfn:message('button.savedraft') }" order="2" onclick="commitMethod('save', 'true');">
			    </ui:button>
			    <c:choose>
					<c:when test="${param.approveModel eq 'right'}">
						  <ui:button text="${lfn:message('button.submit') }" order="2" onclick="commitMethod('save', 'false');" styleClass="lui_widget_btn_primary" isForcedAddClass="true">
			    		  </ui:button>
					</c:when>
					<c:otherwise>
						  <ui:button text="${lfn:message('button.submit') }" order="2" onclick="commitMethod('save', 'false');">
			    		  </ui:button>
					</c:otherwise>
				</c:choose>
		</c:if> 
		<ui:button text="${ lfn:message('button.close') }" order="5"  onclick="Com_CloseWindow()">
		</ui:button>
	</ui:toolbar>  
</template:replace>
<%-- 导航路径--%>
<template:replace name="path">
	<ui:menu layout="sys.ui.menu.nav"  id="categoryId"> 
			<ui:menu-item text="${ lfn:message('home.home') }" icon="lui_icon_s_home">
			</ui:menu-item>
			<ui:menu-item text="${ lfn:message('km-imeeting:module.km.imeeting') }">
			</ui:menu-item>
			<ui:menu-item text="${ lfn:message('km-imeeting:table.kmImeetingSummary') }">
			</ui:menu-item>
			<ui:menu-source autoFetch="false">
				<ui:source type="AjaxJson">
					{"url":"/sys/category/criteria/sysCategoryCriteria.do?method=path&modelName=com.landray.kmss.km.imeeting.model.KmImeetingTemplate&categoryId=${kmImeetingSummaryForm.fdTemplateId}"} 
				</ui:source>
			</ui:menu-source>
	</ui:menu>
</template:replace>