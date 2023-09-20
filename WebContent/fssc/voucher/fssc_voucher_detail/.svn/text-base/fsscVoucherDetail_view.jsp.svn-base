<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/kmss-xform.tld" prefix="xform"%>
<%@ include file="/resource/jsp/common.jsp"%>
<%@page import="com.landray.kmss.util.ResourceUtil"%>
<%@page import="com.landray.kmss.util.UserUtil"%>
<table class="tb_normal" width="100%" id="TABLE_DocList">
	<tr align="center" class="tr_normal_title">
		<td style="width:40px;">
			${lfn:message('fssc-voucher:fsscVoucherDetail.fdOrder')}
		</td>
		<td>
			${lfn:message('fssc-voucher:fsscVoucherDetail.fdType')}
		</td>
		<td>
			${lfn:message('fssc-voucher:fsscVoucherDetail.fdBaseAccountsCode')}
		</td>
		<td>
			${lfn:message('fssc-voucher:fsscVoucherDetail.fdBaseAccountsName')}
		</td>
		<td>
			${lfn:message('fssc-voucher:fsscVoucherDetail.fdBaseCostCenter')}
		</td>
		<td>
			${lfn:message('fssc-voucher:fsscVoucherDetail.fdBaseErpPerson')}
		</td>
		<td>
			${lfn:message('fssc-voucher:fsscVoucherDetail.fdBaseCashFlow')}
		</td>
		<td>
			${lfn:message('fssc-voucher:fsscVoucherDetail.fdBaseCustomer')}
		</td>
		<td>
			${lfn:message('fssc-voucher:fsscVoucherDetail.fdBaseSupplier')}
		</td>
		<td class="fdBaseWbs" style="display: none;">
			${lfn:message('fssc-voucher:fsscVoucherDetail.fdBaseWbs')}
		</td>
		<td class="fdBaseInnerOrder" style="display: none;">
			${lfn:message('fssc-voucher:fsscVoucherDetail.fdBaseInnerOrder')}
		</td>
		<td>
			${lfn:message('fssc-voucher:fsscVoucherDetail.fdBaseProject')}
		</td>
		<td>
			${lfn:message('fssc-voucher:fsscVoucherDetail.fdBasePayBank')}
		</td>
		<td>
			${lfn:message('fssc-voucher:fsscVoucherDetail.fdContractCode')}
		</td>
		<td>
			${lfn:message('fssc-voucher:fsscVoucherDetail.fdDept')}
		</td>
		<td>
			${lfn:message('fssc-voucher:fsscVoucherDetail.fdMoney')}
		</td>
		<td>
			${lfn:message('fssc-voucher:fsscVoucherDetail.fdVoucherText')}
		</td>
	</tr>
	<c:forEach items="${fsscVoucherMainForm.fdDetail_Form}" var="fdDetail_FormItem" varStatus="vstatus">
		<tr onclick="customClickRowforView(this,'${fsscVoucherMainForm.fdId}')" class="fdItem">
			<td align="center">
				${vstatus.index+1}
			</td>
			<td align="center">
				<%-- 借/贷--%>
				<input type="hidden" name="fdDetail_Form[${vstatus.index}].fdId" value="${fdDetail_FormItem.fdId}" />
				<input type="hidden" name="fdDetail_Form[${vstatus.index}].fdType" value="${fdDetail_FormItem.fdType}" />
				<div id="_xform_fdDetail_Form[${vstatus.index}].fdType" _xform_type="select">
					<xform:select property="fdDetail_Form[${vstatus.index}].fdType" htmlElementProperties="id='fdDetail_Form[${vstatus.index}].fdType'" showStatus="view" subject="${lfn:message('fssc-voucher:fsscVoucherDetail.fdType')}" validators=" maxLength(1)">
						<xform:enumsDataSource enumsType="fssc_voucher_fd_type" />
					</xform:select>
				</div>
			</td>
			<td align="center" onclick='FS_ViewDetailNew(this)'>
					<%-- 会计科目编号--%>
				<div id="_xform_fdDetail_Form[${vstatus.index}].fdBaseAccountsCode" _xform_type="text">
					<xform:text property="fdDetail_Form[${vstatus.index}].fdBaseAccountsCode" showStatus="view" style="width:95%;" />
				</div>
			</td>
			<td align="center">
				<%-- 会计科目--%>
				<input type="hidden" name="fdDetail_Form[${vstatus.index}].fdBaseAccountsName" value="${fdDetail_FormItem.fdBaseAccountsName}" />
				<div id="_xform_fdDetail_Form[${vstatus.index}].fdBaseAccountsId" _xform_type="dialog">
					<xform:dialog propertyId="fdDetail_Form[${vstatus.index}].fdBaseAccountsId" propertyName="fdDetail_Form[${vstatus.index}].fdBaseAccountsName" showStatus="view" style="width:95%;">
						dialogSelect(false,'eop_basedata_accounts_fdAccount','fdDetail_Form[*].fdBaseAccountsId','fdDetail_Form[*].fdBaseAccountsName');
					</xform:dialog>
				</div>
			</td>
			<td align="center">
				<%-- 成本中心--%>
				<input type="hidden" name="fdDetail_Form[${vstatus.index}].fdBaseCostCenterName" value="${fdDetail_FormItem.fdBaseCostCenterName}" />
				<div id="_xform_fdDetail_Form[${vstatus.index}].fdBaseCostCenterId" _xform_type="dialog">
					<xform:dialog propertyId="fdDetail_Form[${vstatus.index}].fdBaseCostCenterId" propertyName="fdDetail_Form[${vstatus.index}].fdBaseCostCenterName" showStatus="view" style="width:95%;">
						dialogSelect(false,'eop_basedata_cost_center_selectCostCenter','fdDetail_Form[*].fdBaseCostCenterId','fdDetail_Form[*].fdBaseCostCenterName');
					</xform:dialog>
				</div>
			</td>
			<td align="center">
				<%-- 个人--%>
				<input type="hidden" name="fdDetail_Form[${vstatus.index}].fdBaseErpPersonName" value="${fdDetail_FormItem.fdBaseErpPersonName}" />
				<div id="_xform_fdDetail_Form[${vstatus.index}].fdBaseErpPersonId" _xform_type="address">
					<xform:dialog propertyId="fdDetail_Form[${vstatus.index}].fdBaseErpPersonId" propertyName="fdDetail_Form[${vstatus.index}].fdBaseErpPersonName" showStatus="view" style="width:95%;">
						dialogSelect(false,'eop_basedata_erp_person_fdERPPerson','fdDetail_Form[*].fdBaseErpPersonId','fdDetail_Form[*].fdBaseErpPersonName',null,{'fdCompanyId':$('[name=fdCompanyId]').val()});
					</xform:dialog>
				</div>
			</td>
			<td align="center">
				<%-- 现金流量项目--%>
				<input type="hidden" name="fdDetail_Form[${vstatus.index}].fdBaseCashFlowName" value="${fdDetail_FormItem.fdBaseCashFlowName}" />
				<div id="_xform_fdDetail_Form[${vstatus.index}].fdBaseCashFlowId" _xform_type="dialog">
					<xform:dialog propertyId="fdDetail_Form[${vstatus.index}].fdBaseCashFlowId" propertyName="fdDetail_Form[${vstatus.index}].fdBaseCashFlowName" showStatus="view" style="width:95%;">
						dialogSelect(false,'eop_basedata_cash_flow_getCashFlow','fdDetail_Form[*].fdBaseCashFlowId','fdDetail_Form[*].fdBaseCashFlowName');
					</xform:dialog>
				</div>
			</td>
			<td align="center">
				<%-- 客户--%>
				<input type="hidden" name="fdDetail_Form[${vstatus.index}].fdBaseCustomerName" value="${fdDetail_FormItem.fdBaseCustomerName}" />
				<div id="_xform_fdDetail_Form[${vstatus.index}].fdBaseCustomerId" _xform_type="dialog">
					<xform:dialog propertyId="fdDetail_Form[${vstatus.index}].fdBaseCustomerId" propertyName="fdDetail_Form[${vstatus.index}].fdBaseCustomerName" showStatus="view" style="width:95%;">
						dialogSelect(false,'eop_basedata_customer_getCustomer','fdDetail_Form[*].fdBaseCustomerId','fdDetail_Form[*].fdBaseCustomerName');
					</xform:dialog>
				</div>
			</td>
			<td align="center">
				<%-- 供应商--%>
				<input type="hidden" name="fdDetail_Form[${vstatus.index}].fdBaseSupplierName" value="${fdDetail_FormItem.fdBaseSupplierName}" />
				<div id="_xform_fdDetail_Form[${vstatus.index}].fdBaseSupplierId" _xform_type="dialog">
					<xform:dialog propertyId="fdDetail_Form[${vstatus.index}].fdBaseSupplierId" propertyName="fdDetail_Form[${vstatus.index}].fdBaseSupplierName" showStatus="view" style="width:95%;">
						dialogSelect(false,'eop_basedata_supplier_getSupplier','fdDetail_Form[*].fdBaseSupplierId','fdDetail_Form[*].fdBaseSupplierName');
					</xform:dialog>
				</div>
			</td>
			<td align="center" class="fdBaseWbs" style="display: none;">
				<%-- WBS号--%>
				<input type="hidden" name="fdDetail_Form[${vstatus.index}].fdBaseWbsName" value="${fdDetail_FormItem.fdBaseWbsName}" />
				<div id="_xform_fdDetail_Form[${vstatus.index}].fdBaseWbsId" _xform_type="dialog">
					<xform:dialog propertyId="fdDetail_Form[${vstatus.index}].fdBaseWbsId" propertyName="fdDetail_Form[${vstatus.index}].fdBaseWbsName" showStatus="view" style="width:95%;">
						dialogSelect(false,'eop_basedata_wbs_fdWbs','fdDetail_Form[*].fdBaseWbsId','fdDetail_Form[*].fdBaseWbsName');
					</xform:dialog>
				</div>
			</td>
			<td align="center" class="fdBaseWbs" style="display: none;">
				<%-- 内部订单--%>
				<input type="hidden" name="fdDetail_Form[${vstatus.index}].fdBaseInnerOrderName" value="${fdDetail_FormItem.fdBaseInnerOrderName}" />
				<div id="_xform_fdDetail_Form[${vstatus.index}].fdBaseInnerOrderId" _xform_type="dialog">
					<xform:dialog propertyId="fdDetail_Form[${vstatus.index}].fdBaseInnerOrderId" propertyName="fdDetail_Form[${vstatus.index}].fdBaseInnerOrderName" showStatus="view" style="width:95%;">
						dialogSelect(false,'eop_basedata_inner_order_fdInnerOrder','fdDetail_Form[*].fdBaseInnerOrderId','fdDetail_Form[*].fdBaseInnerOrderName');
					</xform:dialog>
				</div>
			</td>
			<td align="center">
				<%-- 核算项目--%>
				<input type="hidden" name="fdDetail_Form[${vstatus.index}].fdBaseProjectName" value="${fdDetail_FormItem.fdBaseProjectName}" />
				<div id="_xform_fdDetail_Form[${vstatus.index}].fdBaseProjectId" _xform_type="dialog">
					<xform:dialog propertyId="fdDetail_Form[${vstatus.index}].fdBaseProjectId" propertyName="fdDetail_Form[${vstatus.index}].fdBaseProjectName" showStatus="view" style="width:95%;">
						dialogSelect(false,'eop_basedata_project_project','fdDetail_Form[*].fdBaseProjectId','fdDetail_Form[*].fdBaseProjectName');
					</xform:dialog>
				</div>
			</td>
			<td align="center">
				<%-- 银行--%>
				<input type="hidden" name="fdDetail_Form[${vstatus.index}].fdBasePayBankName" value="${fdDetail_FormItem.fdBasePayBankName}" />
				<div id="_xform_fdDetail_Form[${vstatus.index}].fdBasePayBankId" _xform_type="dialog">
					<xform:dialog propertyId="fdDetail_Form[${vstatus.index}].fdBasePayBankId" propertyName="fdDetail_Form[${vstatus.index}].fdBasePayBankName" showStatus="view" style="width:95%;">
						dialogSelect(false,'eop_basedata_pay_bank_fdBank','fdDetail_Form[*].fdBasePayBankId','fdDetail_Form[*].fdBasePayBankName');
					</xform:dialog>
				</div>
			</td>
			<td align="center">
				<%-- 合同编号--%>
				<input type="hidden" name="fdDetail_Form[${vstatus.index}].fdContractCode" value="${fdDetail_FormItem.fdContractCode}" />
				<div id="_xform_fdDetail_Form[${vstatus.index}].fdContractCode" _xform_type="text">
					<xform:text property="fdDetail_Form[${vstatus.index}].fdContractCode" showStatus="view" style="width:95%;" />
				</div>
			</td>
			<td align="center">
				<%-- 部门--%>
				<input type="hidden" name="fdDetail_Form[${vstatus.index}].fdDeptName" value="${fdDetail_FormItem.fdDeptName}" />
				<div id="_xform_fdDetail_Form[${vstatus.index}].fdDeptId" _xform_type="address">
					<xform:address propertyId="fdDetail_Form[${vstatus.index}].fdDeptId" propertyName="fdDetail_Form[${vstatus.index}].fdDeptName" orgType="ORG_TYPE_DEPT" showStatus="view" style="width:90%;" />
				</div>
			</td>
			<td align="center">
				<%-- 金额--%>
				<input type="hidden" name="fdDetail_Form[${vstatus.index}].fdMoney" value="<kmss:showNumber value="${fdDetail_FormItem.fdMoney}" pattern="0.00"></kmss:showNumber>" />
				<div id="_xform_fdDetail_Form[${vstatus.index}].fdMoney" _xform_type="text">
					<kmss:showNumber value="${fdDetail_FormItem.fdMoney}" pattern="###,##0.00"></kmss:showNumber>
				</div>
			</td>
			<td align="center">
				<%-- 摘要文本--%>
				<input type="hidden" name="fdDetail_Form[${vstatus.index}].fdVoucherText" value="${fdDetail_FormItem.fdVoucherText}" />
				<div id="_xform_fdDetail_Form[${vstatus.index}].fdVoucherText" _xform_type="text">
					<xform:text property="fdDetail_Form[${vstatus.index}].fdVoucherText" showStatus="view" style="width:95%;" />
				</div>
			</td>
		</tr>
	</c:forEach>
</table>
