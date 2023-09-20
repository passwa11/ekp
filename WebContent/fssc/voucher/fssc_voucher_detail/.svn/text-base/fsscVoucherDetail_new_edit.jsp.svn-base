<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" import="com.landray.kmss.util.ResourceUtil,java.io.File"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<div style="display: none;">
	<div id="table_of_pro_detail">
		<table class="tb_normal" id="table_of_pro_detail_table" >
			<tr>
				<!-- 借/贷 --> 
				<td width="10%" class="td_normal_title" align="center">
					${lfn:message('fssc-voucher:fsscVoucherDetail.fdType')}
				</td>
				<td width="15%">
					<input type="hidden" name="fdAccountProperty_New"/>
					<xform:select showPleaseSelect="false" property="fdType_New" showStatus="edit">
					    <xform:enumsDataSource enumsType="fssc_voucher_fd_type" />
					</xform:select>
					<span class="txtstrong" >*</span>
				</td>
				<!-- 科目代码--> 
				 <td class="td_normal_title" align="center" width="10%">
					 ${lfn:message('fssc-voucher:fsscVoucherDetail.fdBaseAccounts')}
     			 </td> 
     			 <td width="15%">
					 <input type="hidden" name="fdBaseAccountsCode_New"/>
					 <xform:dialog propertyId="fdBaseAccountsId_New" propertyName="fdBaseAccountsName_New" subject="${lfn:message('fssc-voucher:fsscVoucherDetail.fdBaseAccounts') }" showStatus="edit" style="width:91%;">
						 dialogSelect(false,'eop_basedata_accounts_fdMinLevel','fdBaseAccountsId_New','fdBaseAccountsName_New',null,{'fdCompanyId':$('[name=fdCompanyId]').val()}, selectFdBaseAccountsNameCallback);
					 </xform:dialog>
					 <span class="txtstrong" >*</span>
     			 </td>
     			  <!-- 金额 -->
     			 <td class="td_normal_title" align="center"  width="10%">
					 ${lfn:message('fssc-voucher:fsscVoucherDetail.fdMoney')}
      			 </td>
      			 <td width="15%">
      			 	 <xform:text property="fdMoney_New" style="width:85%;" showStatus="edit"/>
					 <span class="txtstrong" >*</span>
      			 </td>
			    <!-- 文本 --> 
			    <td class="td_normal_title" align="center"   width="10%"> 
					${lfn:message('fssc-voucher:fsscVoucherDetail.fdVoucherText')}
				</td>
			    <td width="15%">
			    	<xform:text property="fdVoucherText_New" style="width:85%;" showStatus="edit"/>
					<span class="txtstrong" >*</span>
			    </td>
			</tr>
			<tr>
				<!-- 成本中心 --> 
			     <td class="td_normal_title" align="center" width="10%">
					 ${lfn:message('fssc-voucher:fsscVoucherDetail.fdBaseCostCenter')}
			     </td> 
			    <td width="15%">
					<xform:dialog propertyId="fdBaseCostCenterId_New" propertyName="fdBaseCostCenterName_New" subject="${lfn:message('fssc-voucher:fsscVoucherDetail.fdBaseCostCenter') }" showStatus="edit" style="width:91%;">
						dialogSelect(false,'eop_basedata_cost_center_selectCostCenter','fdBaseCostCenterId_New','fdBaseCostCenterName_New',null,{'fdCompanyId':$('[name=fdCompanyId]').val()});
					</xform:dialog>
					<span id="fdBaseCostCenterName_reset" class="txtstrong" style="display: none;">*</span>
				</td>
			     <!-- 个人 --> 
			     <td class="td_normal_title" align="center" width="10%">
					 ${lfn:message('fssc-voucher:fsscVoucherDetail.fdBaseErpPerson')}
			     </td>
			     <td width="15%">
					 <xform:dialog propertyId="fdBaseErpPersonId_New" propertyName="fdBaseErpPersonName_New" subject="${lfn:message('fssc-voucher:fsscVoucherDetail.fdBaseErpPerson') }" showStatus="edit" style="width:91%;">
						 dialogSelect(false,'eop_basedata_erp_person_fdERPPerson','fdBaseErpPersonId_New','fdBaseErpPersonName_New',null,{'fdCompanyId':$('[name=fdCompanyId]').val()});
					 </xform:dialog>
					<span id="fdBaseErpPersonName_reset" class="txtstrong" style="display: none;">*</span>
				 </td>
			     <!-- 现金流量项目 --> 
			     <td class="td_normal_title" align="center" width="10%">
					 ${lfn:message('fssc-voucher:fsscVoucherDetail.fdBaseCashFlow')}
			     </td>
			     <td>
					 <xform:dialog propertyId="fdBaseCashFlowId_New" propertyName="fdBaseCashFlowName_New" subject="${lfn:message('fssc-voucher:fsscVoucherDetail.fdBaseCashFlow') }" showStatus="edit" style="width:91%;">
						 dialogSelect(false,'eop_basedata_cash_flow_getCashFlow','fdBaseCashFlowId_New','fdBaseCashFlowName_New',null,{'fdCompanyId':$('[name=fdCompanyId]').val()});
					 </xform:dialog>
					<span id="fdBaseCashFlowName_reset" class="txtstrong" style="display: none;">*</span>
				 </td>
				<!-- 核算项目 -->
				<td class="td_normal_title" align="center"  width="10%">
					${lfn:message('fssc-voucher:fsscVoucherDetail.fdBaseProject')}
				</td>
				<td width="15%">
					<xform:dialog propertyId="fdBaseProjectId_New" propertyName="fdBaseProjectName_New" subject="${lfn:message('fssc-voucher:fsscVoucherDetail.fdBaseProject') }" showStatus="edit" style="width:91%;">
						dialogSelect(false,'eop_basedata_project_project','fdBaseProjectId_New','fdBaseProjectName_New',null,{'fdCompanyId':$('[name=fdCompanyId]').val(),'fdProjectType':'2'});
					</xform:dialog>
					<span id="fdBaseProjectName_reset" class="txtstrong" style="display: none;">*</span>
				</td>
			</tr>
			<tr>
				<!-- 客户 -->
			     <td class="td_normal_title" align="center" width="10%">
					 ${lfn:message('fssc-voucher:fsscVoucherDetail.fdBaseCustomer')}
			     </td>
			     <td width="15%">
					 <xform:dialog propertyId="fdBaseCustomerId_New" propertyName="fdBaseCustomerName_New" subject="${lfn:message('fssc-voucher:fsscVoucherDetail.fdBaseCustomer') }" showStatus="edit" style="width:91%;">
						 dialogSelect(false,'eop_basedata_customer_getCustomer','fdBaseCustomerId_New','fdBaseCustomerName_New',null,{'fdCompanyId':$('[name=fdCompanyId]').val()});
					 </xform:dialog>
					 <span id="fdBaseCustomerName_reset" class="txtstrong" style="display: none;">*</span>
				 </td>
				<!-- 供应商 -->
			     <td class="td_normal_title" align="center" width="10%">
					 ${lfn:message('fssc-voucher:fsscVoucherDetail.fdBaseSupplier')}
			     </td>
			     <td width="15%" class="fdBaseSupplierContent" colspan="5">
					 <xform:dialog propertyId="fdBaseSupplierId_New" propertyName="fdBaseSupplierName_New" subject="${lfn:message('fssc-voucher:fsscVoucherDetail.fdBaseSupplier') }" showStatus="edit" style="width:91%;">
						 dialogSelect(false,'eop_basedata_supplier_getSupplier','fdBaseSupplierId_New','fdBaseSupplierName_New',null,{'fdCompanyId':$('[name=fdCompanyId]').val()});
					 </xform:dialog>
				    <span id="fdBaseSupplierName_reset" class="txtstrong" style="display: none;">*</span>
				</td>
			     <!-- WBS号 -->
			     <td class="td_normal_title fdBaseWbs" align="center"  width="10%" style="display: none;">
					 ${lfn:message('fssc-voucher:fsscVoucherDetail.fdBaseWbs')}
			     </td> 
			     <td width="15%" class="fdBaseWbs" style="display: none;">
					 <xform:dialog propertyId="fdBaseWbsId_New" propertyName="fdBaseWbsName_New" subject="${lfn:message('fssc-voucher:fsscVoucherDetail.fdBaseWbs') }" showStatus="edit" style="width:91%;">
						 dialogSelect(false,'eop_basedata_wbs_fdWbs','fdBaseWbsId_New','fdBaseWbsName_New',null,{'fdCompanyId':$('[name=fdCompanyId]').val()});
					 </xform:dialog>
					 <span id="fdBaseWbsName_reset" class="txtstrong" style="display: none;">*</span>
				</td>
			     <!-- 内部订单 -->
			     <td class="td_normal_title fdBaseInnerOrder" align="center"  width="10%" style="display: none;">
					 ${lfn:message('fssc-voucher:fsscVoucherDetail.fdBaseInnerOrder')}
			     </td> 
			     <td width="15%" class="fdBaseInnerOrder" style="display: none;">
					 <xform:dialog propertyId="fdBaseInnerOrderId_New" propertyName="fdBaseInnerOrderName_New" subject="${lfn:message('fssc-voucher:fsscVoucherDetail.fdBaseInnerOrder') }" showStatus="edit" style="width:91%;">
						 dialogSelect(false,'eop_basedata_inner_order_fdInnerOrder','fdBaseInnerOrderId_New','fdBaseInnerOrderName_New',null,{'fdCompanyId':$('[name=fdCompanyId]').val()});
					 </xform:dialog>
					 <span id="fdBaseInnerOrderName_reset" class="txtstrong" style="display: none;">*</span>
				 </td>
			</tr>
			<tr>
				<!-- 银行 -->
				<td class="td_normal_title fdBasePayBank" align="center"  width="10%">
					${lfn:message('fssc-voucher:fsscVoucherDetail.fdBasePayBank')}
				</td>
				<td width="15%" class="fdBasePayBank">
					<xform:dialog propertyId="fdBasePayBankId_New" propertyName="fdBasePayBankName_New" subject="${lfn:message('fssc-voucher:fsscVoucherDetail.fdBasePayBank') }" showStatus="edit" style="width:91%;">
						dialogSelect(false,'eop_basedata_pay_bank_fdBank','fdBasePayBankId_New','fdBasePayBankName_New',null,{'fdCompanyId':$('[name=fdCompanyId]').val()},afterSelectPayBank);
					</xform:dialog>
					<span id="fdBasePayBankName_reset" class="txtstrong" style="display: none;">*</span>
				</td>
				<!-- 合同编号 --> 
			    <td class="td_normal_title" align="center"   width="10%"> 
					${lfn:message('fssc-voucher:fsscVoucherDetail.fdContractCode')}
				</td>
			    <td width="15%">
			    	<xform:text property="fdContractCode_New" style="width:85%;" showStatus="edit"/>
					<span id="fdContractCode_reset" class="txtstrong" style="display: none;">*</span>
			    </td>
			    <!-- 部门 -->
				<td class="td_normal_title" align="center"  width="10%">
					${lfn:message('fssc-voucher:fsscVoucherDetail.fdDept')}
				</td>
				<td width="15%" colspan="3">
					<xform:address propertyId="fdDeptId_New" propertyName="fdDeptName_New" orgType="ORG_TYPE_DEPT" showStatus="edit" style="width:90%;" />
					<span id="fdDeptName_reset" class="txtstrong" style="display: none;">*</span>
				</td>
			</tr>
			<tr>
				<td colspan="8" style="text-align: center;">
					<ui:button text="${lfn:message('fssc-voucher:button.confirm')}"
						order="2" onclick="saveVoucherDetail();initSAP();">
					</ui:button> 
					<ui:button text="${lfn:message('fssc-voucher:button.cancel')}"
						order="5" onclick="closeDetail();">
					</ui:button>
				</td>
			</tr>
		</table>
	</div>
</div>
<script>
	Com_IncludeFile("quickSelect.js", "${LUI_ContextPath}/eop/basedata/resource/js/", 'js', true);
</script>
<script>
    //选择会计科目回调函数
    function selectFdBaseAccountsNameCallback(rtnData){
        if(rtnData && rtnData.length > 0){
            var obj = rtnData[0];
            $("input[name='fdBaseAccountsCode_New']").val(obj["fdCode"]);//会计科目编号
        }else{
            $("input[name='fdBaseAccountsCode_New']").val("");//会计科目编号
		}
        FS_FdAccountCodeChange();
    }
    function FS_FdAccountCodeChange()
    {
        var fdAccountsId=$("input[name='fdBaseAccountsId_New']").val();
        $.ajax({
            type: "post",
            url: "<%=request.getContextPath() %>/eop/basedata/eop_basedata_accounts/eopBasedataAccounts.do?method=getFdAccountProperty&fdAccountsId="+fdAccountsId,
            dataType: "json",
            contentType: "application/x-www-form-urlencoded;charset=utf-8",
            success:function(data)
            {
                var propertyEleName="fdAccountProperty_New";
                if(data!=null && data.property)
                {
                    document.getElementsByName(propertyEleName)[0].value=data.property;
                    changeRequired();
                }else{
                    document.getElementsByName(propertyEleName)[0].value="";
                    changeRequired();
                }

            },
            error: function (XMLHttpRequest, textStatus, errorThrown) {
            }
        });
    }
    
    
    function afterSelectPayBank(rtn){
 	   if(!rtn){
 			return;
 		}
 		$("[name='fdBasePayBankName_New']").val(rtn[0].fdAccountName+"("+rtn[0].fdBankAccount+")"); 
     }

    function changeRequired()
    {
        var properties,eleName;
        //resetRequired();
        //1 成本中心 fdBaseCostCenterName_reset     2 项目 fdBaseProjectName_reset     3 客户 fdBaseCustomerName_reset
		//4 现金流量项目 fdBaseCashFlowName_reset     5 人员 fdBaseErpPersonName_reset     6 WBS号 fdBaseWbsName_reset
		//7 内部订单 fdBaseInnerOrderName_reset		8 银行 fdBasePayBankName_reset     9 供应商 fdBaseSupplierName_reset
		//10合同编号 fdContractCode_reset			11部门 fdDeptName_reset
        var propertyEleName="fdAccountProperty_New";
        if(document.getElementsByName(propertyEleName).length==0)
        {
            properties="";
        }
        else
        {
            properties=document.getElementsByName(propertyEleName)[0].value;
        }
        var array=properties.split(";");
        removeCheckRequiredOf();//清空可选的所有校验并隐藏对应的*
        for(var i=0;i<array.length;i++)
        {
            var propery=array[i];
            if(propery=='1')
            {//成本中心
                eleName="fdBaseCostCenterName";
            }
            else if(propery=='2')
            {//项目
                eleName="fdBaseProjectName";
            }
            else if(propery=='3')
            {//客户
                eleName="fdBaseCustomerName";
            }
            else if(propery=='4')
            {//现金流量项目
                eleName="fdBaseCashFlowName";
            }
            else if(propery=='5')
            {//人员
                eleName="fdBaseErpPersonName";
            }
            else if(propery=='6')
            {//WBS号
                eleName="fdBaseWbsName";
            }
            else if(propery=='7')
            {//内部订单
                eleName="fdBaseInnerOrderName";
            }
            else if(propery=='8')
            {//银行
                eleName="fdBasePayBankName";
            }
            else if(propery=='9')
            {//供应商
                eleName="fdBaseSupplierName";
            }
            else if(propery=='10')
            {//合同编号
                eleName="fdContractCode";
            }
            else if(propery=='11')
            {//部门
                eleName="fdDeptName";
            }
            $('#'+eleName+'_reset').show();
            $('input[name="'+eleName+'_New"]').attr("validate","required");
        }
    }

    function removeCheckRequiredOf(){
        $('input[name="fdBaseCostCenterName_New"]').attr("validate","");
        $('input[name="fdBaseProjectName_New"]').attr("validate","");
        $('input[name="fdBaseCustomerName_New"]').attr("validate","");
        $('input[name="fdBaseCashFlowName_New"]').attr("validate","");
        $('input[name="fdBaseErpPersonName_New"]').attr("validate","");
        $('input[name="fdBaseWbsName_New"]').attr("validate","");
        $('input[name="fdBaseInnerOrderName_New"]').attr("validate","");
        $('input[name="fdBasePayBankName_New"]').attr("validate","");
        $('input[name="fdBaseSupplierName_New"]').attr("validate","");
        $('input[name="fdContractCode_New"]').attr("validate","");
        $('input[name="fdDeptName_New"]').attr("validate","");
        
        $('#fdBaseCostCenterName_reset').hide();
        $('#fdBaseProjectName_reset').hide();
        $('#fdBaseCustomerName_reset').hide();
        $('#fdBaseCashFlowName_reset').hide();
        $('#fdBaseErpPersonName_reset').hide();
        $('#fdBaseWbsName_reset').hide();
        $('#fdBaseInnerOrderName_reset').hide();
        $('#fdBasePayBankName_reset').hide();
        $('#fdBaseSupplierName_reset').hide();
        $('#fdContractCode_reset').hide();
        $('#fdDeptName_reset').hide();
    }
</script>
