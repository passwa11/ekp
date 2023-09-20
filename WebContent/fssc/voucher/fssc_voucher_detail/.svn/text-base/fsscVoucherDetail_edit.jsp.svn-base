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
		<td class="td_normal_title" align="center" style="width: 5%;">
			<img src="${KMSS_Parameter_StylePath}icons/add.gif" style="cursor:pointer" onclick="FS_AddDetailNew();">
		</td>
	</tr>
	<tr KMSS_IsReferRow="1" style="display:none;">
		<td align="center" KMSS_IsRowIndex="1">
			!{index}
		</td>
		<td align="center" onclick='FS_ViewDetailNew(this)'>
			<%-- 借/贷--%>
			<input type="hidden" name="fdDetail_Form[!{index}].fdId" value="" disabled="true" />
			<div id="_xform_fdDetail_Form[!{index}].fdType" _xform_type="radio">
				<xform:select property="fdDetail_Form[!{index}].fdType" htmlElementProperties="id='fdDetail_Form[!{index}].fdType'" showStatus="readOnly" subject="${lfn:message('fssc-voucher:fsscVoucherDetail.fdType')}" validators=" maxLength(1)">
					<xform:enumsDataSource enumsType="fssc_voucher_fd_type" />
				</xform:select>
			</div>
		</td>
		<td align="center" onclick='FS_ViewDetailNew(this)'>
			<%-- 会计科目编号--%>
			<div id="_xform_fdDetail_Form[!{index}].fdBaseAccountsCode" _xform_type="text">
				<xform:text property="fdDetail_Form[!{index}].fdBaseAccountsCode" showStatus="readOnly" style="width:95%;" />
			</div>
		</td>
		<td align="center" onclick='FS_ViewDetailNew(this)'>
			<%-- 会计科目--%>
			<div id="_xform_fdDetail_Form[!{index}].fdBaseAccountsId" _xform_type="dialog">
				<xform:dialog propertyId="fdDetail_Form[!{index}].fdBaseAccountsId" propertyName="fdDetail_Form[!{index}].fdBaseAccountsName" showStatus="readOnly" style="width:95%;">
					dialogSelect(false,'eop_basedata_accounts_fdMinLevel','fdDetail_Form[*].fdBaseAccountsId','fdDetail_Form[*].fdBaseAccountsName');
				</xform:dialog>
			</div>
		</td>
		<td align="center" onclick='FS_ViewDetailNew(this)'>
			<%-- 成本中心--%>
			<div id="_xform_fdDetail_Form[!{index}].fdBaseCostCenterId" _xform_type="dialog">
				<xform:dialog propertyId="fdDetail_Form[!{index}].fdBaseCostCenterId" propertyName="fdDetail_Form[!{index}].fdBaseCostCenterName" showStatus="readOnly" style="width:95%;">
					dialogSelect(false,'eop_basedata_cost_center_selectCostCenter','fdDetail_Form[*].fdBaseCostCenterId','fdDetail_Form[*].fdBaseCostCenterName');
				</xform:dialog>
			</div>
		</td>
		<td align="center" onclick='FS_ViewDetailNew(this)'>
			<%-- 个人--%>
			<div id="_xform_fdDetail_Form[!{index}].fdBaseErpPersonId" _xform_type="address">
				<xform:dialog propertyId="fdDetail_Form[!{index}].fdBaseErpPersonId" propertyName="fdDetail_Form[!{index}].fdBaseErpPersonName" showStatus="readOnly" style="width:95%;">
					dialogSelect(false,'eop_basedata_erp_person_fdERPPerson','fdDetail_Form[!{index}].fdBaseErpPersonId','fdDetail_Form[!{index}].fdBaseErpPersonName',null,{'fdCompanyId':$('[name=fdCompanyId]').val()});
				</xform:dialog>
			</div>
		</td>
		<td align="center" onclick='FS_ViewDetailNew(this)'>
			<%-- 现金流量项目--%>
			<div id="_xform_fdDetail_Form[!{index}].fdBaseCashFlowId" _xform_type="dialog">
				<xform:dialog propertyId="fdDetail_Form[!{index}].fdBaseCashFlowId" propertyName="fdDetail_Form[!{index}].fdBaseCashFlowName" showStatus="readOnly" style="width:95%;">
					dialogSelect(false,'eop_basedata_cash_flow_getCashFlow','fdDetail_Form[*].fdBaseCashFlowId','fdDetail_Form[*].fdBaseCashFlowName');
				</xform:dialog>
			</div>
		</td>
		<td align="center" onclick='FS_ViewDetailNew(this)'>
			<%-- 客户--%>
			<div id="_xform_fdDetail_Form[!{index}].fdBaseCustomerId" _xform_type="dialog">
				<xform:dialog propertyId="fdDetail_Form[!{index}].fdBaseCustomerId" propertyName="fdDetail_Form[!{index}].fdBaseCustomerName" showStatus="readOnly" style="width:95%;">
					dialogSelect(false,'eop_basedata_customer_getCustomer','fdDetail_Form[*].fdBaseCustomerId','fdDetail_Form[*].fdBaseCustomerName');
				</xform:dialog>
			</div>
		</td>
		<td align="center" onclick='FS_ViewDetailNew(this)'>
			<%-- 供应商--%>
			<div id="_xform_fdDetail_Form[!{index}].fdBaseSupplierId" _xform_type="dialog">
				<xform:dialog propertyId="fdDetail_Form[!{index}].fdBaseSupplierId" propertyName="fdDetail_Form[!{index}].fdBaseSupplierName" showStatus="readOnly" style="width:95%;">
					dialogSelect(false,'eop_basedata_supplier_getSupplier','fdDetail_Form[*].fdBaseSupplierId','fdDetail_Form[*].fdBaseSupplierName');
				</xform:dialog>
			</div>
		</td>
		<td class="fdBaseWbs" align="center" onclick='FS_ViewDetailNew(this)' style="display: none;">
			<%-- WBS号--%>
			<div id="_xform_fdDetail_Form[!{index}].fdBaseWbsId" _xform_type="dialog">
				<xform:dialog propertyId="fdDetail_Form[!{index}].fdBaseWbsId" propertyName="fdDetail_Form[!{index}].fdBaseWbsName" showStatus="readOnly" style="width:95%;">
					dialogSelect(false,'eop_basedata_wbs_fdWbs','fdDetail_Form[*].fdBaseWbsId','fdDetail_Form[*].fdBaseWbsName');
				</xform:dialog>
			</div>
		</td>
		<td class="fdBaseInnerOrder" align="center" onclick='FS_ViewDetailNew(this)' style="display: none;">
			<%-- 内部订单--%>
			<div id="_xform_fdDetail_Form[!{index}].fdBaseInnerOrderId" _xform_type="dialog">
				<xform:dialog propertyId="fdDetail_Form[!{index}].fdBaseInnerOrderId" propertyName="fdDetail_Form[!{index}].fdBaseInnerOrderName" showStatus="readOnly" style="width:95%;">
					dialogSelect(false,'eop_basedata_inner_order_fdInnerOrder','fdDetail_Form[*].fdBaseInnerOrderId','fdDetail_Form[*].fdBaseInnerOrderName');
				</xform:dialog>
			</div>
		</td>
		<td align="center" onclick='FS_ViewDetailNew(this)'>
			<%-- 核算项目--%>
			<div id="_xform_fdDetail_Form[!{index}].fdBaseProjectId" _xform_type="dialog">
				<xform:dialog propertyId="fdDetail_Form[!{index}].fdBaseProjectId" propertyName="fdDetail_Form[!{index}].fdBaseProjectName" showStatus="readOnly" style="width:95%;">
					dialogSelect(false,'eop_basedata_project_project','fdDetail_Form[*].fdBaseProjectId','fdDetail_Form[*].fdBaseProjectName');
				</xform:dialog>
			</div>
		</td>
		<td align="center" onclick='FS_ViewDetailNew(this)'>
			<%-- 银行--%>
			<div id="_xform_fdDetail_Form[!{index}].fdBasePayBankId" _xform_type="dialog">
				<xform:dialog propertyId="fdDetail_Form[!{index}].fdBasePayBankId" propertyName="fdDetail_Form[!{index}].fdBasePayBankName" showStatus="readOnly" style="width:95%;">
					dialogSelect(false,'eop_basedata_pay_bank_fdBank','fdDetail_Form[*].fdBasePayBankId','fdDetail_Form[*].fdBasePayBankName');
				</xform:dialog>
			</div>
		</td>
		<td align="center" onclick='FS_ViewDetailNew(this)'>
			<%-- 合同编号--%>
			<div id="_xform_fdDetail_Form[!{index}].fdContractCode" _xform_type="text">
				<xform:text property="fdDetail_Form[!{index}].fdContractCode" showStatus="readOnly" style="width:95%;" />
			</div>
		</td>
		<td align="center" onclick='FS_ViewDetailNew(this)'>
			<%-- 部门--%>
			<div id="_xform_fdDetail_Form[!{index}].fdDeptId" _xform_type="address">
				<xform:address propertyId="fdDetail_Form[!{index}].fdDeptId" propertyName="fdDetail_Form[!{index}].fdDeptName" orgType="ORG_TYPE_DEPT" showStatus="readOnly" style="width:90%;" />
			</div>
		</td>
		<td align="center" onclick='FS_ViewDetailNew(this)'>
			<%-- 金额--%>
			<div id="_xform_fdDetail_Form[!{index}].fdMoney" _xform_type="text">
				<xform:text property="fdDetail_Form[!{index}].fdMoney" showStatus="readOnly" style="width:95%;"/>
			</div>
		</td>
		<td align="center" onclick='FS_ViewDetailNew(this)'>
			<%-- 摘要文本--%>
			<div id="_xform_fdDetail_Form[!{index}].fdVoucherText" _xform_type="text">
				<xform:text property="fdDetail_Form[!{index}].fdVoucherText" showStatus="readOnly" style="width:95%;" />
			</div>
		</td>
		<td align="center">
			<img src="${KMSS_Parameter_StylePath}icons/delete.gif" style="cursor:pointer" onclick="DocList_DeleteRow();">
		</td>
	</tr>
	<c:forEach items="${fsscVoucherMainForm.fdDetail_Form}" var="fdDetail_FormItem" varStatus="vstatus">
		<tr KMSS_IsContentRow="1">
			<td align="center" onclick='FS_ViewDetailNew(this)'>
					${vstatus.index+1}
			</td>
			<td align="center" onclick='FS_ViewDetailNew(this)'>
					<%-- 借/贷--%>
				<input type="hidden" name="fdDetail_Form[${vstatus.index}].fdId" value="${fdDetail_FormItem.fdId}" />
				<div id="_xform_fdDetail_Form[${vstatus.index}].fdType" _xform_type="select">
					<xform:select property="fdDetail_Form[${vstatus.index}].fdType" htmlElementProperties="id='fdDetail_Form[${vstatus.index}].fdType'" showStatus="readOnly" subject="${lfn:message('fssc-voucher:fsscVoucherDetail.fdType')}" validators=" maxLength(1)">
						<xform:enumsDataSource enumsType="fssc_voucher_fd_type" />
					</xform:select>
				</div>
			</td>
			<td align="center" onclick='FS_ViewDetailNew(this)'>
					<%-- 会计科目编号--%>
				<div id="_xform_fdDetail_Form[${vstatus.index}].fdBaseAccountsCode" _xform_type="text">
					<xform:text property="fdDetail_Form[${vstatus.index}].fdBaseAccountsCode" showStatus="readOnly" style="width:95%;" />
				</div>
			</td>
			<td align="center" onclick='FS_ViewDetailNew(this)'>
					<%-- 会计科目名称--%>
				<div id="_xform_fdDetail_Form[${vstatus.index}].fdBaseAccountsId" _xform_type="dialog">
					<xform:dialog propertyId="fdDetail_Form[${vstatus.index}].fdBaseAccountsId" propertyName="fdDetail_Form[${vstatus.index}].fdBaseAccountsName" showStatus="readOnly" style="width:95%;">
						dialogSelect(false,'eop_basedata_accounts_fdAccount','fdDetail_Form[*].fdBaseAccountsId','fdDetail_Form[*].fdBaseAccountsName', null, null, selectFdBaseAccountsNameCallback);
					</xform:dialog>
				</div>
			</td>
			<td align="center" onclick='FS_ViewDetailNew(this)'>
					<%-- 成本中心--%>
				<div id="_xform_fdDetail_Form[${vstatus.index}].fdBaseCostCenterId" _xform_type="dialog">
					<xform:dialog propertyId="fdDetail_Form[${vstatus.index}].fdBaseCostCenterId" propertyName="fdDetail_Form[${vstatus.index}].fdBaseCostCenterName" showStatus="readOnly" style="width:95%;">
						dialogSelect(false,'eop_basedata_cost_center_selectCostCenter','fdDetail_Form[*].fdBaseCostCenterId','fdDetail_Form[*].fdBaseCostCenterName');
					</xform:dialog>
				</div>
			</td>
			<td align="center" onclick='FS_ViewDetailNew(this)'>
					<%-- 个人--%>
				<div id="_xform_fdDetail_Form[${vstatus.index}].fdBaseErpPersonId" _xform_type="address">
					<xform:dialog propertyId="fdDetail_Form[${vstatus.index}].fdBaseErpPersonId" propertyName="fdDetail_Form[${vstatus.index}].fdBaseErpPersonName" showStatus="readOnly" style="width:95%;">
						dialogSelect(false,'eop_basedata_erp_person_fdERPPerson','fdDetail_Form[*].fdBaseErpPersonId','fdDetail_Form[*].fdBaseErpPersonName',null,{'fdCompanyId':$('[name=fdCompanyId]').val()});
					</xform:dialog>
				</div>
			</td>
			<td align="center" onclick='FS_ViewDetailNew(this)'>
					<%-- 现金流量项目--%>
				<div id="_xform_fdDetail_Form[${vstatus.index}].fdBaseCashFlowId" _xform_type="dialog">
					<xform:dialog propertyId="fdDetail_Form[${vstatus.index}].fdBaseCashFlowId" propertyName="fdDetail_Form[${vstatus.index}].fdBaseCashFlowName" showStatus="readOnly" style="width:95%;">
						dialogSelect(false,'eop_basedata_cash_flow_getCashFlow','fdDetail_Form[*].fdBaseCashFlowId','fdDetail_Form[*].fdBaseCashFlowName');
					</xform:dialog>
				</div>
			</td>
			<td align="center" onclick='FS_ViewDetailNew(this)'>
					<%-- 客户--%>
				<div id="_xform_fdDetail_Form[${vstatus.index}].fdBaseCustomerId" _xform_type="dialog">
					<xform:dialog propertyId="fdDetail_Form[${vstatus.index}].fdBaseCustomerId" propertyName="fdDetail_Form[${vstatus.index}].fdBaseCustomerName" showStatus="readOnly" style="width:95%;">
						dialogSelect(false,'eop_basedata_customer_getCustomer','fdDetail_Form[*].fdBaseCustomerId','fdDetail_Form[*].fdBaseCustomerName');
					</xform:dialog>
				</div>
			</td>
			<td align="center" onclick='FS_ViewDetailNew(this)'>
					<%-- 供应商--%>
				<div id="_xform_fdDetail_Form[${vstatus.index}].fdBaseSupplierId" _xform_type="dialog">
					<xform:dialog propertyId="fdDetail_Form[${vstatus.index}].fdBaseSupplierId" propertyName="fdDetail_Form[${vstatus.index}].fdBaseSupplierName" showStatus="readOnly" style="width:95%;">
						dialogSelect(false,'eop_basedata_supplier_getSupplier','fdDetail_Form[*].fdBaseSupplierId','fdDetail_Form[*].fdBaseSupplierName');
					</xform:dialog>
				</div>
			</td>
			<td class="fdBaseWbs" align="center" onclick='FS_ViewDetailNew(this)' style="display: none;">
					<%-- WBS号--%>
				<div id="_xform_fdDetail_Form[${vstatus.index}].fdBaseWbsId" _xform_type="dialog">
					<xform:dialog propertyId="fdDetail_Form[${vstatus.index}].fdBaseWbsId" propertyName="fdDetail_Form[${vstatus.index}].fdBaseWbsName" showStatus="readOnly" style="width:95%;">
						dialogSelect(false,'eop_basedata_wbs_fdWbs','fdDetail_Form[*].fdBaseWbsId','fdDetail_Form[*].fdBaseWbsName');
					</xform:dialog>
				</div>
			</td>
			<td class="fdBaseInnerOrder" align="center" onclick='FS_ViewDetailNew(this)' style="display: none;">
					<%-- 内部订单--%>
				<div id="_xform_fdDetail_Form[${vstatus.index}].fdBaseInnerOrderId" _xform_type="dialog">
					<xform:dialog propertyId="fdDetail_Form[${vstatus.index}].fdBaseInnerOrderId" propertyName="fdDetail_Form[${vstatus.index}].fdBaseInnerOrderName" showStatus="readOnly" style="width:95%;">
						dialogSelect(false,'eop_basedata_inner_order_fdInnerOrder','fdDetail_Form[*].fdBaseInnerOrderId','fdDetail_Form[*].fdBaseInnerOrderName');
					</xform:dialog>
				</div>
			</td>
			<td align="center" onclick='FS_ViewDetailNew(this)'>
					<%-- 核算项目--%>
				<div id="_xform_fdDetail_Form[${vstatus.index}].fdBaseProjectId" _xform_type="dialog">
					<xform:dialog propertyId="fdDetail_Form[${vstatus.index}].fdBaseProjectId" propertyName="fdDetail_Form[${vstatus.index}].fdBaseProjectName" showStatus="readOnly" style="width:95%;">
						dialogSelect(false,'eop_basedata_project_project','fdDetail_Form[*].fdBaseProjectId','fdDetail_Form[*].fdBaseProjectName');
					</xform:dialog>
				</div>
			</td>
			<td align="center" onclick='FS_ViewDetailNew(this)'>
					<%-- 银行--%>
				<div id="_xform_fdDetail_Form[${vstatus.index}].fdBasePayBankId" _xform_type="dialog">
					<xform:dialog propertyId="fdDetail_Form[${vstatus.index}].fdBasePayBankId" propertyName="fdDetail_Form[${vstatus.index}].fdBasePayBankName" showStatus="readOnly" style="width:95%;">
						dialogSelect(false,'eop_basedata_pay_bank_fdBank','fdDetail_Form[*].fdBasePayBankId','fdDetail_Form[*].fdBasePayBankName');
					</xform:dialog>
				</div>
			</td>
			<td align="center" onclick='FS_ViewDetailNew(this)'>
				<%-- 合同编号--%>
				<div id="_xform_fdDetail_Form[${vstatus.index}].fdContractCode" _xform_type="text">
					<xform:text property="fdDetail_Form[${vstatus.index}].fdContractCode" showStatus="readOnly" style="width:95%;" />
				</div>
			</td>
			<td align="center" onclick='FS_ViewDetailNew(this)'>
				<%-- 部门--%>
				<div id="_xform_fdDetail_Form[${vstatus.index}].fdDeptId" _xform_type="address">
					<xform:address propertyId="fdDetail_Form[${vstatus.index}].fdDeptId" propertyName="fdDetail_Form[${vstatus.index}].fdDeptName" orgType="ORG_TYPE_DEPT" showStatus="readOnly" style="width:90%;" />
				</div>
			</td>
			<td align="center" onclick='FS_ViewDetailNew(this)'>
					<%-- 金额--%>
				<div id="_xform_fdDetail_Form[${vstatus.index}].fdMoney" _xform_type="text">
					<input name="fdDetail_Form[${vstatus.index}].fdMoney" value='<kmss:showNumber value="${fdDetail_FormItem.fdMoney}" pattern="##0.00"></kmss:showNumber>' readonly="readonly" class="inputsgl" style="color:#333;"/>
				</div>
			</td>
			<td align="center" onclick='FS_ViewDetailNew(this)'>
					<%-- 摘要文本--%>
				<div id="_xform_fdDetail_Form[${vstatus.index}].fdVoucherText" _xform_type="text">
					<xform:text property="fdDetail_Form[${vstatus.index}].fdVoucherText" showStatus="readOnly" style="width:95%;" />
				</div>
			</td>
			<td align="center">
				<img src="${KMSS_Parameter_StylePath}icons/delete.gif" style="cursor:pointer" onclick="DocList_DeleteRow();">
			</td>
		</tr>
	</c:forEach>
</table>
<script>
    function FS_AddDetailNew(){
        var newrow = DocList_AddRow("TABLE_DocList");
        var rowIndex=$(newrow)[0].rowIndex;
        $('#fdOOrder'+rowIndex).html(rowIndex);
        $(newrow).bind('click',customClickRow(newrow,"add",$("#TABLE_DocList tr:first")));
        DocList_DeleteRow(newrow);
    }
    function FS_ViewDetailNew(val){
        customClickRow($(val).parent(),"update");
    }
/*******************************************
* 更新排序号
*******************************************/
function changgeIndexOfOrder(){
	var row = document.getElementById("TABLE_DocList").rows.length - 1;
	if (row > 0) {
		for(var i=0;i<row;i++){
			$('input[name="fdDetail_Form['+i+'].fdOrder"]').val(i);
		}
	}
}
</script>
