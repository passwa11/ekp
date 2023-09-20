<%@page import="com.landray.kmss.util.ResourceUtil"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>

<!-- 草稿状态的文档默认选中基本信息页签 -->
<c:if test="${kmArchivesBorrowForm.docStatus=='10'}">
	<ui:event event="layoutDone">
		$("i.lui-fm-icon-2").closest(".lui_tabpanel_vertical_icon_navs_item_l").click();
    </ui:event>
</c:if>
<ui:content title="${lfn:message('km-archives:py.JiBenXinXi') }" titleicon="lui-fm-icon-2">
	<table class="tb_normal lui-fm-noneBorderTable" width=100%>
		<!-- 标题 -->
		<tr>
			<td class="tr_normal_title" width=30%>
				<bean:message bundle="km-archives" key="kmArchivesBorrow.docSubject" />
			</td>
			<td>
				<c:out value="${ kmArchivesBorrowForm.docSubject }"></c:out>
			</td>
		</tr>
		<!-- 流程 -->
		<tr>
			<td class="tr_normal_title" width=30%>
				<bean:message bundle="km-archives" key="kmArchivesBorrow.docTemplate" />
			</td>
			<td>
				<c:out value="${ kmArchivesBorrowForm.docTemplateName }"></c:out>
			</td>
		</tr>
		<!-- 借阅时间 -->
		<tr>
			<td class="tr_normal_title" width=30%>
				<bean:message bundle="km-archives" key="kmArchivesBorrow.fdBorrowDate" />
			</td>
			<td>
				<c:out value="${ kmArchivesBorrowForm.fdBorrowDate }"></c:out>
			</td>
		</tr>
		<!-- 所属部门 -->
		<tr>
			<td class="tr_normal_title" width=30%>
				<bean:message bundle="km-archives" key="kmArchivesBorrow.docDept" />
			</td>
			<td>
				<c:out value="${ kmArchivesBorrowForm.docDeptName }"></c:out>
			</td>
		</tr>
		<!-- 借阅人 -->
		<tr>
			<td class="tr_normal_title" width=30%>
				<bean:message bundle="km-archives" key="kmArchivesBorrow.fdBorrower" />
			</td>
			<td>
				<c:out value="${ kmArchivesBorrowForm.fdBorrowerName }"></c:out>
			</td>
		</tr>
	</table>
</ui:content>