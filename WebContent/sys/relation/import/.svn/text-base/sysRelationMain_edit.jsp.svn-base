<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/resource/jsp/common.jsp"%>
<%@page import="com.landray.kmss.sys.relation.forms.SysRelationConditionForm"%>
<%@page import="com.landray.kmss.sys.relation.forms.SysRelationStaticNewForm"%>
<%@page import="com.landray.kmss.sys.relation.forms.SysRelationTextForm"%>
<%@page import="com.landray.kmss.sys.relation.forms.SysRelationEntryForm"%>
<%@page import="com.landray.kmss.sys.relation.forms.SysRelationMainDataForm"%>
<%@page import="com.landray.kmss.sys.relation.util.SysRelationUtil"%>
<%@page import="net.sf.json.JSONObject"%>
<%@page import="com.landray.kmss.util.StringUtil"%>
<%@page import="java.util.List"%>
<link rel="stylesheet" type="text/css"
	  href="${LUI_ContextPath}/sys/relation/sys_relation_main/style/relaPageBtn.css">
<c:if test="${JsParam.enable ne 'false' && param.formName!=null}">
<c:choose>
	<c:when test="${param.approveType eq 'right'}">
		<c:set var="mainModelForm" value="${requestScope[param.formName]}" scope="request"/>
		<c:set var="sysRelationMainForm" value="${mainModelForm.sysRelationMainForm}" scope="request"/>
		<ui:event event="layoutDone">
			var relationMainContent = LUI("sysRelationMainContent");
			if(relationOpt && relationMainContent!=null){
				$("<span style='margin-left:10px' class='lui_tabpanel_navs_item_title' id='rela_config_btn_right'>${lfn:message('button.create')}</span>").appendTo(relationMainContent.element.find(".lui-fm-tab-title"));
				$("#rela_config_btn_right").click(function(){
					Com_EventStopPropagation();
					relationOpt.openAddUrl = true;
					relationOpt.editConfig();
				});
			}
			<c:if test="${empty sysRelationMainForm.sysRelationEntryFormList}">
				$("i.lui-fm-icon-1").closest(".lui_tabpanel_vertical_icon_navs_item_l").hide();
			</c:if>
		</ui:event>
		<ui:content title="${lfn:message('sys-relation:title.sysRelationMain.setting')}" titleicon="lui-fm-icon-1" id="sysRelationMainContent">
			<ui:accordionpanel id="sysRelationMainContentPanel" channel="relation" style="min-width:200px;" layout="sys.ui.accordionpanel.simpletitle">
				<c:set var="mainModelForm" value="${requestScope[param.formName]}" scope="request"/>
				<c:set var="currModelId" value="${mainModelForm.fdId}" scope="request"/>
				<c:set var="currModelName" value="${mainModelForm.modelClass.name}" scope="request"/>
				<c:if test="${mainModelForm.method_GET=='add' || mainModelForm.method_GET=='edit'}">
					<%@ include file="sysRelationMain_include_edit.jsp" %>
					<ui:button parentId="toolbar"
						text="${lfn:message('sys-relation:title.sysRelationMain.setting')}" order="4" id="rela_config_btn">
					</ui:button>
				</c:if>
			</ui:accordionpanel>
		</ui:content>
	</c:when>
	<c:otherwise>
		<ui:accordionpanel id="sysRelationMainContentPanel" channel="relation" style="min-width:200px;" layout="${not empty param.layout ? param.layout : '' }">
			<c:set var="mainModelForm" value="${requestScope[param.formName]}" scope="request"/>
			<c:set var="currModelId" value="${mainModelForm.fdId}" scope="request"/>
			<c:set var="currModelName" value="${mainModelForm.modelClass.name}" scope="request"/>
			<c:if test="${mainModelForm.method_GET=='add' || mainModelForm.method_GET=='edit'}">
				<%@ include file="sysRelationMain_include_edit.jsp" %>
				<ui:button parentId="toolbar" 
					text="${lfn:message('sys-relation:title.sysRelationMain.setting')}" order="4" id="rela_config_btn">
				</ui:button>
			</c:if>
		</ui:accordionpanel>
	</c:otherwise>
</c:choose>
</c:if>
