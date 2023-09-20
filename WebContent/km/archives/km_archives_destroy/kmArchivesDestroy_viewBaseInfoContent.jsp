<%@page import="com.landray.kmss.util.ResourceUtil"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<!-- 草稿状态的文档默认选中基本信息页签 -->
<c:if test="${kmArchivesDestroyForm.docStatus=='10'}">
	<ui:event event="layoutDone">
		$("i.lui-fm-icon-2").closest(".lui_tabpanel_vertical_icon_navs_item_l").click();
   	</ui:event>
</c:if>
<ui:content title="基本信息" titleicon="lui-fm-icon-2">
	<table class="tb_normal lui-fm-noneBorderTable" width=100%>
		<!-- 主题 -->
		<tr>
			<td width="35%">
				<bean:message bundle="km-archives" key="kmArchivesDestroy.docSubject" />
			</td>
			<td colspan="3">
				<c:out value="${ kmArchivesDestroyForm.docSubject}"></c:out>
			</td>
		</tr>
		<!-- 销毁人 -->
		<tr>
			<td width="35%">
				<bean:message bundle="km-archives" key="kmArchivesDestroy.docCreator" />
			</td>
			<td colspan="3">
				<c:out value="${ kmArchivesDestroyForm.docCreatorName}"></c:out>
			</td>
		</tr>
		<!-- 销毁时间 -->
		<tr>
			<td width="35%">
				<bean:message bundle="km-archives" key="kmArchivesDestroy.docCreateTime" />
			</td>
			<td colspan="3">
				<c:out value="${ kmArchivesDestroyForm.docCreateTime}"></c:out>
			</td>
		</tr>
		<tr>
			<td width="35%">
                ${lfn:message('km-archives:kmArchivesBorrow.docDept')}
            </td>
            <td colspan="3">
            	<c:out value="${ kmArchivesDestroyForm.docDeptName}"></c:out>
            </td>
		</tr>
		<tr>
			<td width="35%">
				<bean:message bundle="km-archives" key="kmArchivesDestroy.docTemplate" />
			</td>
			<td colspan="3">
				<c:out value="${ kmArchivesDestroyForm.docTemplateName}"></c:out>
			</td>
		</tr>
	</table>
</ui:content>