<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<ui:content title="${ lfn:message('fssc-expense:py.JiBenXinXi') }" titleicon="lui-fm-icon-2">
<table class="tb_simple" width="100%">
		<tr>
			<td  class="td_normal_title" width="30%" >${lfn:message('fssc-expense:fsscExpenseShareMain.docCreator') }</td>
			<td  width="75%">${fsscExpenseShareMainForm.docCreatorName }</td>
		</tr>
		<tr>
			<td  class="td_normal_title" width="30%" >${lfn:message('fssc-expense:fsscExpenseShareMain.docCreateTime') }</td>
			<td  width="75%">${fsscExpenseShareMainForm.docCreateTime }</td>
		</tr>
		<tr>
			<td  class="td_normal_title" width="30%">${lfn:message('fssc-expense:fsscExpenseShareMain.docStatus') }</td>
			<td  width="75%"><sunbor:enumsShow enumsType="common_status" value="${fsscExpenseShareMainForm.docStatus }"></sunbor:enumsShow></td>
		</tr>
		<tr>
			<td  class="td_normal_title" width="30%">${lfn:message('fssc-expense:fsscExpenseShareMain.docPublishTime') }</td>
			<td  width="75%">${fsscExpenseShareMainForm.docPublishTime }</td>
		</tr>
</table>
</ui:content>
