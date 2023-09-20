<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<ui:content title="${ lfn:message('fssc-expense:py.JiBenXinXi') }" titleicon="lui-fm-icon-2">
	<table class="tb_simple" width="100%">
		<tr>
			<td  class="td_normal_title" width="30%" >${lfn:message('fssc-expense:fsscExpenseMain.docCreator') }</td>
			<td  width="75%">${fsscExpenseMainForm.docCreatorName }</td>
		</tr>
		<tr>
			<td  class="td_normal_title" width="30%" >${lfn:message('fssc-expense:fsscExpenseMain.docCreateTime') }</td>
			<td  width="75%">${fsscExpenseMainForm.docCreateTime }</td>
		</tr>
		<tr>
			<td  class="td_normal_title" width="30%" >${lfn:message('fssc-expense:fsscExpenseMain.fdPaymentStatus') }</td>
			<td  width="23%"><sunbor:enumsShow enumsType="eop_basedata_payment_status" value="${fsscExpenseMainForm.fdPaymentStatus }"></sunbor:enumsShow></td>
		</tr>
		<tr>
			<td  class="td_normal_title" width="30%" >${lfn:message('fssc-expense:fsscExpenseMain.fdVoucherStatus') }</td>
			<td  width="75%"> <sunbor:enumsShow enumsType="eop_basedata_fd_voucher_status" value="${fsscExpenseMainForm.fdVoucherStatus }"></sunbor:enumsShow></td>
		</tr>
		<tr>
			<td  class="td_normal_title" width="30%" >${lfn:message('fssc-expense:fsscExpenseMain.fdBookkeepingStatus') }</td>
			<td  width="75%"> <sunbor:enumsShow enumsType="eop_basedata_fd_bookkeeping_status" value="${fsscExpenseMainForm.fdBookkeepingStatus }"></sunbor:enumsShow></td>
		</tr>
		<tr>
			<td  class="td_normal_title" width="30%">${lfn:message('fssc-expense:fsscExpenseMain.docStatus') }</td>
			<td  width="75%"><sunbor:enumsShow enumsType="common_status" value="${fsscExpenseMainForm.docStatus }"></sunbor:enumsShow></td>
		</tr>
		<tr>
			<td  class="td_normal_title" width="30%">${lfn:message('fssc-expense:fsscExpenseMain.docPublishTime') }</td>
			<td  width="75%">${fsscExpenseMainForm.docPublishTime }</td>
		</tr>
	</table>
</ui:content>
