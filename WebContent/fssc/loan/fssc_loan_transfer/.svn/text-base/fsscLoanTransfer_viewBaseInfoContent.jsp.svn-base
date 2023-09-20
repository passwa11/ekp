<%@page import="com.landray.kmss.util.ResourceUtil"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<ui:content title="${lfn:message('fssc-loan:py.JiBenXinXi') }" titleicon="lui-fm-icon-2">
	<!-- 草稿状态的文档默认选中基本信息页签 -->
	<c:if test="${fsscLoanTransferForm.docStatus=='10'}">
		<script>
			LUI.ready(function(){
				setTimeout(function(){
					$("i.lui-fm-icon-2").closest(".lui_tabpanel_vertical_icon_navs_item_l").click();
				},200);
			});
		</script>
	</c:if>
	<table class="tb_simple" width="100%">
		<tr>
			<td  class="td_normal_title" width="32%" >${lfn:message('fssc-loan:fsscLoanTransfer.docSubject') }</td>
			<td  width="75%">${fsscLoanTransferForm.docSubject}</td>
		</tr>
		<tr>
			<td  class="td_normal_title" width="32%" >${lfn:message('fssc-loan:fsscLoanTransfer.docCreator') }</td>
			<td  width="75%">${ fsscLoanTransferForm.docCreatorName}</td>
		</tr>
		<tr>
			<td  class="td_normal_title" width="32%" >${lfn:message('fssc-loan:fsscLoanTransfer.docNumber') }</td>
			<td  width="75%">${ fsscLoanTransferForm.docNumber}</td>
		</tr>
		<tr>
			<td  class="td_normal_title" width="32%" >${lfn:message('fssc-loan:fsscLoanTransfer.docCreateTime') }</td>
			<td  width="75%">${ fsscLoanTransferForm.docCreateTime}</td>
		</tr>
		<tr>
			<td  class="td_normal_title" width="32%">${lfn:message('fssc-loan:fsscLoanTransfer.docStatus') }</td>
			<td  width="75%"><sunbor:enumsShow enumsType="common_status" value="${fsscLoanTransferForm.docStatus}"></sunbor:enumsShow></td>
		</tr>
		<tr>
			<td  class="td_normal_title" width="32%" >${lfn:message('fssc-loan:fsscLoanTransfer.fdVoucherStatus') }</td>
			<td  width="75%"> <sunbor:enumsShow enumsType="eop_basedata_fd_voucher_status" value="${fsscLoanTransferForm.fdVoucherStatus }"></sunbor:enumsShow></td>
		</tr>
		<tr>
			<td  class="td_normal_title" width="32%" >${lfn:message('fssc-loan:fsscLoanTransfer.fdBookkeepingStatus') }</td>
			<td  width="75%"> <sunbor:enumsShow enumsType="eop_basedata_fd_bookkeeping_status" value="${fsscLoanTransferForm.fdBookkeepingStatus }"></sunbor:enumsShow></td>
		</tr>
		<tr>
			<td  class="td_normal_title" width="32%" >${lfn:message('fssc-loan:fsscLoanTransfer.docPublishTime') }</td>
			<td  width="75%" colspan="5">${fsscLoanTransferForm.docPublishTime }</td>
		</tr>
	</table>
</ui:content>
