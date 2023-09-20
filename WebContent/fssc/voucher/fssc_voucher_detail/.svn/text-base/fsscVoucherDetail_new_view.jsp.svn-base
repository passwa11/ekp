<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" import="com.landray.kmss.util.ResourceUtil,java.io.File"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<style>
	.lui_toolbar_btn_def {
		font-size: 14px;
		height: 26px;
		line-height: 26px;
		color: #fff;
		text-align: center;
		display: block;
		border-radius: 4px;
		background-color: #cbcbcb;
		transition-duration: .3s;
	}

	td.tr_srpread{ background:#cbcbcb;  text-align:center; }
	/*td.tr_srpread:hover{ background:#b5b5b5; color:#fff;}
	td.tr_srpread:hover .arrow i{ border-bottom-color:#fff;  }
	td.tr_srpread:hover .arrow .mtr2{ border-bottom-color:#b5b5b5;}*/
	td.tr_srpread:hover>div{color:#fff;}

	td.tr_srpread>div{ display:inline-block; cursor:pointer; width:100%; color:#4a4a4a}
	.tr_srpread .arrow{ position:relative; width:20px; height:10px; top:2px; display:inline-block;}
	.tr_srpread .arrow i{ display:inline-block; width:0px; height:0px; position:absolute; left:0px; bottom:0px; border-width:8px; border-style:solid; border-color:transparent; border-bottom-color:#4a4a4a;}
	.tr_srpread .arrow .mrt1{ bottom:3px; }
	.tr_srpread .arrow .mtr2{ border-bottom-color:#cbcbcb;}
</style>
<div style="display: none;">
	<div id="table_of_pro_detail">
		<table class="tb_normal" id="table_of_pro_detail_table">
			<tr>
				<!-- 借/贷 --> 
				<td width="10%" class="td_normal_title" align="center">
					${lfn:message('fssc-voucher:fsscVoucherDetail.fdType')}
				</td>
				<td width="15%">
					<div id="fdType_New"></div>
				</td>
				<!-- 科目代码--> 
				 <td class="td_normal_title" align="center" width="10%">
					 ${lfn:message('fssc-voucher:fsscVoucherDetail.fdBaseAccounts')}
     			 </td> 
     			 <td width="15%">
					 <div id="fdBaseAccountsName_New"></div>
				 </td>
     			  <!-- 金额 -->
     			 <td class="td_normal_title" align="center"  width="10%">
					 ${lfn:message('fssc-voucher:fsscVoucherDetail.fdMoney')}
      			 </td>
      			 <td width="15%">
					 <div id="fdMoney_New"></div>
      			 </td>
			    <!-- 文本 --> 
			    <td class="td_normal_title" align="center"   width="10%"> 
					${lfn:message('fssc-voucher:fsscVoucherDetail.fdVoucherText')}
				</td>
			    <td width="15%">
					<div id="fdVoucherText_New"></div>
			    </td>
			</tr>
			<tr>
				<!-- 成本中心 --> 
			     <td class="td_normal_title" align="center" width="10%">
					 ${lfn:message('fssc-voucher:fsscVoucherDetail.fdBaseCostCenter')}
			     </td> 
			    <td width="15%">
					<div id="fdBaseCostCenterName_New"></div>
				</td>
			     <!-- 个人 --> 
			     <td class="td_normal_title" align="center" width="10%">
					 ${lfn:message('fssc-voucher:fsscVoucherDetail.fdBaseErpPerson')}
			     </td>
			     <td width="15%">
					 <div id="fdBaseErpPersonName_New"></div>
				 </td>
			     <!-- 现金流量项目 --> 
			     <td class="td_normal_title" align="center" width="10%">
					 ${lfn:message('fssc-voucher:fsscVoucherDetail.fdBaseCashFlow')}
			     </td>
			     <td>
					 <div id="fdBaseCashFlowName_New"></div>
				 </td>
				<!-- 核算项目 -->
				<td class="td_normal_title" align="center"  width="10%">
					${lfn:message('fssc-voucher:fsscVoucherDetail.fdBaseProject')}
				</td>
				<td width="15%">
					<div id="fdBaseProjectName_New"></div>
				</td>
			</tr>
			<tr>
				<!-- 客户 -->
			     <td class="td_normal_title" align="center" width="10%">
					 ${lfn:message('fssc-voucher:fsscVoucherDetail.fdBaseCustomer')}
			     </td>
			     <td width="15%">
					 <div id="fdBaseCustomerName_New"></div>
				 </td>
				<!-- 供应商 -->
			     <td class="td_normal_title" align="center" width="10%">
					 ${lfn:message('fssc-voucher:fsscVoucherDetail.fdBaseSupplier')}
			     </td>
				<td width="15%" class="fdBaseSupplierContent" colspan="5">
					 <div id="fdBaseSupplierName_New"></div>
				</td>
			     <!-- WBS号 -->
				<td class="td_normal_title fdBaseWbs" align="center"  width="10%" style="display: none;">
					 ${lfn:message('fssc-voucher:fsscVoucherDetail.fdBaseWbs')}
			     </td>
				<td width="15%" class="fdBaseWbs" style="display: none;">
					 <div id="fdBaseWbsName_New"></div>
				</td>
			     <!-- 内部订单 -->
				<td class="td_normal_title fdBaseInnerOrder" align="center"  width="10%" style="display: none;">
					 ${lfn:message('fssc-voucher:fsscVoucherDetail.fdBaseInnerOrder')}
			     </td>
				<td width="15%" class="fdBaseInnerOrder" style="display: none;">
					 <div id="fdBaseInnerOrderName_New"></div>
				</td>
			</tr>
			<tr>
				<!-- 银行 -->
				<td class="td_normal_title fdBasePayBank" align="center"  width="10%">
					${lfn:message('fssc-voucher:fsscVoucherDetail.fdBasePayBank')}
				</td>
				<td width="15%" class="fdBasePayBank">
					<div id="fdBasePayBankName_New"></div>
				</td>
				<!-- 合同编号 -->
				<td class="td_normal_title" align="center"   width="10%"> 
					${lfn:message('fssc-voucher:fsscVoucherDetail.fdContractCode')}
				</td>
			    <td width="15%">
					<div id="fdContractCode_New"></div>
			    </td>
			    <!-- 部门 -->
				<td class="td_normal_title" align="center"   width="10%"> 
					${lfn:message('fssc-voucher:fsscVoucherDetail.fdDept')}
				</td>
			    <td width="15%" colspan="3">
					<div id="fdDeptName_New"></div>
			    </td>
			</tr>
			<tr>
				<td class="tr_srpread" colspan="8" onclick="closeDetail('view');" >
					<div title="${lfn:message('fssc-voucher:button.retract')}" class="lui_toolbar_btn_def" id="lui-id-18" data-lui-type="lui/toolbar!Button" data-lui-cid="lui-id-18" data-lui-parse-init="19">
						<span class="arrow" ><i class="mrt1"></i><i class="mtr2"></i></span>${lfn:message('fssc-voucher:button.retract')}
					</div>
				</td>
			</tr>
		</table>
	</div>
</div>
