<%@page import="com.landray.kmss.util.ResourceUtil"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<ui:content title="${lfn:message('fssc-budget:py.JiBenXinXi') }" titleicon="lui-fm-icon-2">
	<!-- 草稿状态的文档默认选中基本信息页签 -->
	<c:if test="${fsscBudgetAdjustMainForm.docStatus=='10'}">
		<script>
			LUI.ready(function(){
				setTimeout(function(){
					$("i.lui-fm-icon-2").closest(".lui_tabpanel_vertical_icon_navs_item_l").click();
				},200);
			});
		</script>
	</c:if>
	<table class="tb_normal lui-fm-noneBorderTable" width=100%>
		<!--主题-->
		<tr>
			<td class="tr_normal_title" width=30%>
				<bean:message bundle="fssc-budget" key="fsscBudgetAdjustMain.docSubject" />
			</td>
			<td>
				<c:out value="${ fsscBudgetAdjustMainForm.docSubject}"></c:out>
			</td>
		</tr>
		<!--模板名称-->
		<tr>
			<td class="tr_normal_title" width=30%>
				<bean:message bundle="fssc-budget" key="fsscBudgetAdjustMain.docTemplate" />
			</td>
			<td>
				<c:out value="${ fsscBudgetAdjustMainForm.docTemplateName}"></c:out>
			</td>
		</tr>
		<!--申请人-->
		<tr>
			<td class="tr_normal_title" width=30%>
				<bean:message bundle="fssc-budget" key="fsscBudgetAdjustMain.docCreator" />
			</td>
			<td>
				<xform:text property="docCreatorId" showStatus="noShow"/> 
				<c:out value="${ fsscBudgetAdjustMainForm.docCreatorName}"></c:out>
			</td>
		</tr>
		<!-- 申请单编号 -->
		<tr>
			<td class="tr_normal_title" width=30%>
				<bean:message bundle="fssc-budget" key="fsscBudgetAdjustMain.docNumber" />
			</td>
			<td>
				<c:out value="${ fsscBudgetAdjustMainForm.docNumber}"></c:out>
			</td>
		</tr>
		<!--创建时间-->
		<tr>
			<td class="tr_normal_title" width=30%>
				<bean:message bundle="fssc-budget" key="fsscBudgetAdjustMain.docCreateTime" />
			</td>
			<td>
				<c:out value="${ fsscBudgetAdjustMainForm.docCreateTime}"></c:out>
			</td>
		</tr>
		<!--状态-->
		<tr>
			<td class="tr_normal_title" width=30%>
				<bean:message bundle="fssc-budget" key="fsscBudgetAdjustMain.docStatus" />
			</td>
			<td>
				<sunbor:enumsShow enumsType="common_status" value="${fsscBudgetAdjustMainForm.docStatus}"></sunbor:enumsShow>
			</td>
		</tr>
		<!--发布时间-->
		<tr>
			<td class="tr_normal_title" width=30%>
				<bean:message bundle="fssc-budget" key="fsscBudgetAdjustMain.docPublishTime" />
			</td>
			<td>
				<c:out value="${ fsscBudgetAdjustMainForm.docPublishTime}"></c:out>
			</td>
		</tr>
	</table>
</ui:content>
