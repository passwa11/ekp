<%@page import="com.landray.kmss.util.ResourceUtil"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<ui:content title="${lfn:message('fssc-loan:py.JiBenXinXi') }" titleicon="lui-fm-icon-2">
	<!-- 草稿状态的文档默认选中基本信息页签 -->
	<c:if test="${fsscLoanMainForm.docStatus=='10'}">
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
			<td  class="td_normal_title" width="32%" >${lfn:message('fssc-loan:fsscLoanMain.docSubject') }</td>
			<td  width="75%">${fsscLoanMainForm.docSubject}</td>
		</tr>
		<tr>
			<td  class="td_normal_title" width="32%" >${lfn:message('fssc-loan:fsscLoanMain.docTemplate') }</td>
			<td  width="75%">${fsscLoanMainForm.docTemplateName}</td>
		</tr>
		<tr>
			<td  class="td_normal_title" width="32%" >${lfn:message('fssc-loan:fsscLoanMain.docCreator') }</td>
			<td  width="75%">${ fsscLoanMainForm.docCreatorName}</td>
		</tr>
		<tr>
			<td  class="td_normal_title" width="32%" >${lfn:message('fssc-loan:fsscLoanMain.docNumber') }</td>
			<td  width="75%">${ fsscLoanMainForm.docNumber}</td>
		</tr>
		<tr>
			<td  class="td_normal_title" width="32%" >${lfn:message('fssc-loan:fsscLoanMain.docCreateTime') }</td>
			<td  width="75%">${ fsscLoanMainForm.docCreateTime}</td>
		</tr>

		<tr>
			<td  class="td_normal_title" width="32%" >${lfn:message('fssc-loan:fsscLoanMain.docCreatorName') }</td>
			<td  width="75%"><sunbor:enumsShow enumsType="eop_basedata_payment_status" value="${fsscLoanMainForm.docCreatorName }"></sunbor:enumsShow></td>
		</tr>
		
		<tr>
			<td  class="td_normal_title" width="32%" >${lfn:message('fssc-loan:fsscLoanMain.fdLoanChargePerson')}</td>
			<td  width="75%">  <ui:person personId="${fsscLoanMainForm.fdLoanChargePersonId}" personName="${fsscLoanMainForm.fdLoanChargePersonName}" /></td>
		</tr>
		<tr>
			<td  class="td_normal_title" width="32%">${lfn:message('fssc-loan:fsscLoanMain.docStatus') }</td>
			<td  width="75%"><sunbor:enumsShow enumsType="common_status" value="${fsscLoanMainForm.docStatus }"></sunbor:enumsShow></td>
		</tr>
		 <tr>
			<td  class="td_normal_title" width="32%" >${lfn:message('fssc-loan:fsscLoanMain.fdPaymentStatus') }</td>
			<td  width="75%"><sunbor:enumsShow enumsType="eop_basedata_payment_status" value="${fsscLoanMainForm.fdPaymentStatus }"></sunbor:enumsShow></td>
		</tr>
		<tr>
			<td  class="td_normal_title" width="32%" >${lfn:message('fssc-loan:fsscLoanMain.fdVoucherStatus') }</td>
			<td  width="75%"> <sunbor:enumsShow enumsType="eop_basedata_fd_voucher_status" value="${fsscLoanMainForm.fdVoucherStatus }"></sunbor:enumsShow></td>
		</tr>
		<tr>
			<td  class="td_normal_title" width="32%" >${lfn:message('fssc-loan:fsscLoanMain.fdBookkeepingStatus') }</td>
			<td  width="75%"> <sunbor:enumsShow enumsType="eop_basedata_fd_bookkeeping_status" value="${fsscLoanMainForm.fdBookkeepingStatus }"></sunbor:enumsShow></td>
		</tr>
		<tr>
			<td  class="td_normal_title" width="32%" >${lfn:message('fssc-loan:fsscLoanMain.docPublishTime') }</td>
			<td  width="75%" colspan="5">${fsscLoanMainForm.docPublishTime }</td>
		</tr>
	</table>
</ui:content>
