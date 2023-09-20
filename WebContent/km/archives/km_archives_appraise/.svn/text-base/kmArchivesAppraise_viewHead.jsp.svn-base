<%@page import="com.landray.kmss.sys.lbpmservice.support.model.LbpmSetting"%>
<%@page import="com.landray.kmss.util.ResourceUtil"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
	<template:replace name="head">
	</template:replace>
	
	<template:replace name="title">
        <c:out value="${kmArchivesAppraiseForm.docSubject} - " />
        <c:out value="${lfn:message('km-archives:table.kmArchivesAppraise') }" />
    </template:replace>
	
	<%--操作按钮--%>
	<template:replace name="toolbar">
		<script>
	    	function deleteDoc(delUrl) {
	            seajs.use(['lui/dialog'], function(dialog) {
	                Com_Delete_Get(delUrl, 'com.landray.kmss.km.archives.model.KmArchivesAppraise');
	            });
	        }
    	</script>
		<ui:toolbar id="toolbar" layout="sys.ui.toolbar.float" count="6">
			<c:if test="${ kmArchivesAppraiseForm.docStatus=='10' || kmArchivesAppraiseForm.docStatus=='11' || kmArchivesAppraiseForm.docStatus=='20' }">
				<kmss:auth requestURL="/km/archives/km_archives_appraise/kmArchivesAppraise.do?method=edit&fdId=${param.fdId}">
			    	<ui:button text="${lfn:message('button.edit')}" onclick="Com_OpenWindow('kmArchivesAppraise.do?method=edit&fdId=${param.fdId}','_self');" order="2" />
			    </kmss:auth>
		    </c:if>
		    <kmss:auth requestURL="/km/archives/km_archives_appraise/kmArchivesAppraise.do?method=delete&fdId=${param.fdId}">
		    	<ui:button text="${lfn:message('button.delete')}" onclick="deleteDoc('kmArchivesAppraise.do?method=delete&fdId=${param.fdId}');" order="4" />
		    </kmss:auth>
			<%--关闭--%>
			<ui:button text="${lfn:message('button.close')}" order="5" onclick="Com_CloseWindow();">
			</ui:button>
		</ui:toolbar>
	</template:replace>
	
	<template:replace name="path">
        <ui:menu layout="sys.ui.menu.nav">
            <ui:menu-item text="${ lfn:message('home.home') }" icon="lui_icon_s_home" href="/" target="_self" />
            <ui:menu-item text="${ lfn:message('km-archives:table.kmArchivesAppraise') }" href="/km/archives/" target="_self" />
        </ui:menu>
    </template:replace>