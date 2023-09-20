<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<ui:content title="${ lfn:message('fssc-expense:py.JiBenXinXi') }" expand="true">
	<table class="tb_normal" width="100%" id="TABLE_DocList_fdDetailList_Form" align="center" tbdraggable="true">
	    <tr align="center" class="tr_normal_title">
	        <td style="width:20px;"></td>
	        <td style="width:40px;">
	            ${lfn:message('page.serial')}
	        </td>
	        <td width="10%">
	            ${lfn:message('fssc-expense:fsscExpenseBalanceDetail.fdType')}
	        </td>
	        <td width="10%">
	            ${lfn:message('fssc-expense:fsscExpenseBalanceDetail.fdExpenseItem')}
	        </td>
	        <td width="10%">
	            ${lfn:message('fssc-expense:fsscExpenseBalanceDetail.fdAccount')}
	        </td>
	        <td width="10%">
	            ${lfn:message('fssc-expense:fsscExpenseBalanceDetail.fdCostCenter')}
	        </td>
	        <td width="10%">
	            ${lfn:message('fssc-expense:fsscExpenseBalanceDetail.fdPerson')}
	        </td>
	        <td width="10%">
	            ${lfn:message('fssc-expense:fsscExpenseDetail.fdDept')}
	        </td>
	        <td width="10%">
	            ${lfn:message('fssc-expense:fsscExpenseBalanceDetail.fdCashFlow')}
	        </td>
	        <td width="10%">
	            ${lfn:message('fssc-expense:fsscExpenseBalanceDetail.fdProject')}
	        </td>
	        <td width="10%">
	            ${lfn:message('fssc-expense:fsscExpenseBalanceDetail.fdMoney')}
	        </td>
	        <td width="10%">
	            ${lfn:message('fssc-expense:fsscExpenseBalanceDetail.fdRemark')}
	        </td>
	        <td style="width:80px;">
	        </td>
	    </tr>
	    <tr KMSS_IsReferRow="1" style="display:none;">
	        <td align="center">
	            <input type='checkbox' name='DocList_Selected' />
	        </td>
	        <td align="center" KMSS_IsRowIndex="1">
	            !{index}
	        </td>
	        <td align="center">
	            <%-- 借/贷--%>
	            <input type="hidden" name="fdDetailList_Form[!{index}].fdId" value="" disabled="true" />
	            <input type="hidden" name="fdDetailList_Form[!{index}].fdBudgetInfo" value="" />
	            <input type="hidden" name="fdDetailList_Form[!{index}].fdBudgetMoney" value="" />
	            <div id="_xform_fdDetailList_Form[!{index}].fdType" _xform_type="select">
	                <xform:select property="fdDetailList_Form[!{index}].fdType" htmlElementProperties="id='fdDetailList_Form[!{index}].fdType'" style="width:80%" showStatus="edit" required="true" subject="${lfn:message('fssc-expense:fsscExpenseBalanceDetail.fdType')}" validators=" maxLength(5)">
	                    <xform:enumsDataSource enumsType="fssc_expense_detal_voucher_type" />
	                </xform:select>
	            </div>
	        </td>
	        <td align="center">
	            <%-- 费用类型--%>
	            <div id="_xform_fdDetailList_Form[!{index}].fdExpenseItemId" _xform_type="dialog">
	                <xform:dialog propertyId="fdDetailList_Form[!{index}].fdExpenseItemId" propertyName="fdDetailList_Form[!{index}].fdExpenseItemName" required="true" showStatus="edit" style="width:85%;" subject="${lfn:message('fssc-expense:fsscExpenseBalanceDetail.fdExpenseItem')}">
	                    dialogSelect(false,'eop_basedata_expense_item_fdParent','fdDetailList_Form[*].fdExpenseItemId','fdDetailList_Form[*].fdExpenseItemName',null,{fdCompanyId:$('[name=fdCompanyId]').val(),fdNotId:''},function(rtn){FSSC_AfterExpenseItemSelected(!{index},rtn)});
	                </xform:dialog>
	            </div>
	        </td>
	        <td align="center">
	            <%-- 会计科目--%>
	            <div id="_xform_fdDetailList_Form[!{index}].fdAccountId" _xform_type="dialog">
	                <xform:dialog propertyId="fdDetailList_Form[!{index}].fdAccountId" propertyName="fdDetailList_Form[!{index}].fdAccountName" subject="${lfn:message('fssc-expense:fsscExpenseBalanceDetail.fdAccount') }" showStatus="edit" required="true" style="width:85%;">
	                    dialogSelect(false,'eop_basedata_accounts_com_fdAccount','fdDetailList_Form[*].fdAccountId','fdDetailList_Form[*].fdAccountName',null,{fdCompanyId:$('[name=fdCompanyId]').val(),fdNotId:''},function(rtn){FSSC_AfterAccountSelected(!{index},rtn)});
	                </xform:dialog>
	            </div>
	        </td>
	        <td align="center">
	            <%-- 成本中心--%>
	            <div id="_xform_fdDetailList_Form[!{index}].fdCostCenterId" _xform_type="dialog">
	                <xform:dialog propertyId="fdDetailList_Form[!{index}].fdCostCenterId" propertyName="fdDetailList_Form[!{index}].fdCostCenterName" subject="${lfn:message('fssc-expense:fsscExpenseBalanceDetail.fdCostCenter') }" showStatus="edit" style="width:85%;">
	                    dialogSelect(false,'eop_basedata_cost_center_selectCostCenter','fdDetailList_Form[*].fdCostCenterId','fdDetailList_Form[*].fdCostCenterName',null,{fdCompanyId:$('[name=fdCompanyId]').val(),fdNotId:''});
	                </xform:dialog>
	            </div>
	        </td>
	        <td align="center">
	            <%-- 个人--%>
	            <div id="_xform_fdDetailList_Form[!{index}].fdPersonId" _xform_type="address">
	                <xform:address propertyId="fdDetailList_Form[!{index}].fdPersonId" propertyName="fdDetailList_Form[!{index}].fdPersonName" orgType="ORG_TYPE_PERSON" showStatus="edit" style="width:85%;" />
	            </div>
	        </td>
	        <td align="center">
	            <%-- 承担部门--%>
	            <div id="_xform_fdDetailList_Form[!{index}].fdPersonId" _xform_type="address">
	                <xform:address propertyId="fdDetailList_Form[!{index}].fdDeptId" propertyName="fdDetailList_Form[!{index}].fdDeptName" orgType="ORG_TYPE_DEPT" showStatus="edit" style="width:85%;" />
	            </div>
	        </td>
	        <td align="center">
	            <%-- 现金流量项目--%>
	            <div id="_xform_fdDetailList_Form[!{index}].fdCashFlowId" _xform_type="dialog">
	                <xform:dialog propertyId="fdDetailList_Form[!{index}].fdCashFlowId" propertyName="fdDetailList_Form[!{index}].fdCashFlowName" subject="${lfn:message('fssc-expense:fsscExpenseBalanceDetail.fdCashFlow') }" showStatus="edit" style="width:85%;" >
	                    dialogSelect(false,'eop_basedata_cash_flow_getCashFlow','fdDetailList_Form[*].fdCashFlowId','fdDetailList_Form[*].fdCashFlowName',null,{fdCompanyId:$('[name=fdCompanyId]').val(),fdNotId:''});
	                </xform:dialog>
	            </div>
	        </td>
	        <td align="center">
	            <%-- 项目--%>
	            <div id="_xform_fdDetailList_Form[!{index}].fdProjectId" _xform_type="dialog">
	                <xform:dialog propertyId="fdDetailList_Form[!{index}].fdProjectId" propertyName="fdDetailList_Form[!{index}].fdProjectName"  subject="${lfn:message('fssc-expense:fsscExpenseBalanceDetail.fdProject') }" showStatus="edit" style="width:85%;" >
	                    dialogSelect(false,'eop_basedata_project_project','fdDetailList_Form[*].fdProjectId','fdDetailList_Form[*].fdProjectName',null,{fdCompanyId:$('[name=fdCompanyId]').val(),fdNotId:''});
	                </xform:dialog>
	            </div>
	        </td>
	        <td align="center">
	            <%-- 金额--%>
	            <div id="_xform_fdDetailList_Form[!{index}].fdMoney" _xform_type="text">
	                <xform:text property="fdDetailList_Form[!{index}].fdMoney" onValueChange="FSSC_ChangeMoney" showStatus="edit" required="true" subject="${lfn:message('fssc-expense:fsscExpenseBalanceDetail.fdMoney')}" validators=" number" style="width:85%;" />
	            </div>
	        </td>
	        <td align="center">
	            <%-- 备注--%>
	            <div id="_xform_fdDetailList_Form[!{index}].fdRemark" _xform_type="text">
	                <xform:text property="fdDetailList_Form[!{index}].fdRemark" showStatus="edit" subject="${lfn:message('fssc-expense:fsscExpenseBalanceDetail.fdRemark')}" validators=" maxLength(200)" style="width:85%;" />
	            </div>
	        </td>
	        <td align="center">
	        	&nbsp;
	            <a href="javascript:void(0);" onclick="DocList_DeleteRow();" title="${lfn:message('doclist.delete')}">
	                <img src="${KMSS_Parameter_StylePath}icons/icon_del.png" border="0" />
	            </a>
	        </td>
	    </tr>
	    <c:forEach items="${fsscExpenseBalanceForm.fdDetailList_Form}" var="fdDetailList_FormItem" varStatus="vstatus">
	        <tr KMSS_IsContentRow="1">
	            <td align="center">
	                <input type="checkbox" name="DocList_Selected" />
	            </td>
	            <td align="center">
	                ${vstatus.index+1}
	            </td>
	            <td align="center">
		            <%-- 借/贷--%>
		            <input type="hidden" name="fdDetailList_Form[${vstatus.index }].fdBudgetInfo" value="${fdDetailList_FormItem.fdBudgetInfo }" />
		            <input type="hidden" name="fdDetailList_Form[${vstatus.index }].fdBudgetMoney" value="${fdDetailList_FormItem.fdBudgetMoney }" />
		            <input type="hidden" name="fdDetailList_Form[${vstatus.index }].fdId" value="${fdDetailList_FormItem.fdId }"/>
		            <div id="_xform_fdDetailList_Form[${vstatus.index }].fdType" _xform_type="select">
		                <xform:select property="fdDetailList_Form[${vstatus.index }].fdType" htmlElementProperties="id='fdDetailList_Form[${vstatus.index }].fdType'" style="width:80%" showStatus="edit" required="true" subject="${lfn:message('fssc-expense:fsscExpenseBalanceDetail.fdType')}" validators=" maxLength(5)">
		                    <xform:enumsDataSource enumsType="fssc_expense_detal_voucher_type" />
		                </xform:select>
		            </div>
		        </td>
		        <td align="center">
		            <%-- 费用类型--%>
		            <div id="_xform_fdDetailList_Form[${vstatus.index }].fdExpenseItemId" _xform_type="dialog">
		                <xform:dialog propertyId="fdDetailList_Form[${vstatus.index }].fdExpenseItemId" propertyName="fdDetailList_Form[${vstatus.index }].fdExpenseItemName" required="true" showStatus="edit" style="width:85%;" subject="${lfn:message('fssc-expense:fsscExpenseBalanceDetail.fdExpenseItem')}">
		                    dialogSelect(false,'eop_basedata_expense_item_fdParent','fdDetailList_Form[*].fdExpenseItemId','fdDetailList_Form[*].fdExpenseItemName',null,{fdCompanyId:$('[name=fdCompanyId]').val(),fdNotId:''},function(rtn){FSSC_AfterExpenseItemSelected(${vstatus.index },rtn)});
		                </xform:dialog>
		            </div>
		        </td>
		        <td align="center">
		            <%-- 会计科目--%>
		            <div id="_xform_fdDetailList_Form[${vstatus.index }].fdAccountId" _xform_type="dialog">
		                <xform:dialog propertyId="fdDetailList_Form[${vstatus.index }].fdAccountId" propertyName="fdDetailList_Form[${vstatus.index }].fdAccountName" subject="${lfn:message('fssc-expense:fsscExpenseBalanceDetail.fdAccount') }" showStatus="edit" required="true" style="width:85%;">
		                    dialogSelect(false,'eop_basedata_accounts_com_fdAccount','fdDetailList_Form[*].fdAccountId','fdDetailList_Form[*].fdAccountName',null,{fdCompanyId:$('[name=fdCompanyId]').val(),fdNotId:''},function(rtn){FSSC_AfterAccountSelected(${vstatus.index },rtn)});
		                </xform:dialog>
		            </div>
		        </td>
		        <td align="center">
		            <%-- 成本中心--%>
		            <div id="_xform_fdDetailList_Form[${vstatus.index }].fdCostCenterId" _xform_type="dialog">
		                <xform:dialog propertyId="fdDetailList_Form[${vstatus.index }].fdCostCenterId" propertyName="fdDetailList_Form[${vstatus.index }].fdCostCenterName" subject="${lfn:message('fssc-expense:fsscExpenseBalanceDetail.fdCostCenter') }" showStatus="edit" style="width:85%;">
		                    dialogSelect(false,'eop_basedata_cost_center_selectCostCenter','fdDetailList_Form[*].fdCostCenterId','fdDetailList_Form[*].fdCostCenterName',null,{fdCompanyId:$('[name=fdCompanyId]').val(),fdNotId:''});
		                </xform:dialog>
		            </div>
		        </td>
		        <td align="center">
		            <%-- 个人--%>
		            <div id="_xform_fdDetailList_Form[${vstatus.index }].fdPersonId" _xform_type="address">
		                <xform:address propertyId="fdDetailList_Form[${vstatus.index }].fdPersonId" propertyName="fdDetailList_Form[${vstatus.index }].fdPersonName" orgType="ORG_TYPE_PERSON" showStatus="edit" style="width:85%;" />
		            </div>
		        </td>
		        <td align="center">
		            <%-- 承担部门--%>
		            <div id="_xform_fdDetailList_Form[${vstatus.index }].fdPersonId" _xform_type="address">
		                <xform:address propertyId="fdDetailList_Form[${vstatus.index }].fdDeptId" propertyName="fdDetailList_Form[${vstatus.index }].fdDeptName" orgType="ORG_TYPE_DEPT" showStatus="edit" style="width:85%;" />
		            </div>
		        </td>
		        <td align="center">
		            <%-- 现金流量项目--%>
		            <div id="_xform_fdDetailList_Form[${vstatus.index }].fdCashFlowId" _xform_type="dialog">
		                <xform:dialog propertyId="fdDetailList_Form[${vstatus.index }].fdCashFlowId" propertyName="fdDetailList_Form[${vstatus.index }].fdCashFlowName" showStatus="edit" style="width:85%;" subject="${lfn:message('fssc-expense:fsscExpenseBalanceDetail.fdCashFlow')}">
		                    dialogSelect(false,'eop_basedata_cash_flow_getCashFlow','fdDetailList_Form[*].fdCashFlowId','fdDetailList_Form[*].fdCashFlowName',null,{fdCompanyId:$('[name=fdCompanyId]').val(),fdNotId:''});
		                </xform:dialog>
		            </div>
		        </td>
		        <td align="center">
		            <%-- 项目--%>
		            <div id="_xform_fdDetailList_Form[${vstatus.index }].fdProjectId" _xform_type="dialog">
		                <xform:dialog propertyId="fdDetailList_Form[${vstatus.index }].fdProjectId" propertyName="fdDetailList_Form[${vstatus.index }].fdProjectName" showStatus="edit" style="width:85%;" subject="${lfn:message('fssc-expense:fsscExpenseBalanceDetail.fdProject')}">
		                    dialogSelect(false,'eop_basedata_project_project','fdDetailList_Form[*].fdProjectId','fdDetailList_Form[*].fdProjectName',null,{fdCompanyId:$('[name=fdCompanyId]').val(),fdNotId:''});
		                </xform:dialog>
		            </div>
		        </td>
		        <td align="center">
		            <%-- 金额--%>
		            <div id="_xform_fdDetailList_Form[${vstatus.index }].fdMoney" _xform_type="text">
		                <xform:text property="fdDetailList_Form[${vstatus.index }].fdMoney" showStatus="edit" onValueChange="FSSC_ChangeMoney" required="true" subject="${lfn:message('fssc-expense:fsscExpenseBalanceDetail.fdMoney')}" validators=" number" style="width:85%;" />
		            </div>
		        </td>
		        <td align="center">
		            <%-- 备注--%>
		            <div id="_xform_fdDetailList_Form[${vstatus.index }].fdRemark" _xform_type="text">
		                <xform:text property="fdDetailList_Form[${vstatus.index }].fdRemark" showStatus="edit" subject="${lfn:message('fssc-expense:fsscExpenseBalanceDetail.fdRemark')}" validators=" maxLength(200)" style="width:85%;" />
		            </div>
		        </td>
	            <td align="center">
	                &nbsp;
	                <a href="javascript:void(0);" onclick="DocList_DeleteRow();" title="${lfn:message('doclist.delete')}">
	                    <img src="${KMSS_Parameter_StylePath}icons/icon_del.png" border="0" />
	                </a>
	            </td>
	        </tr>
	    </c:forEach>
	    <tr type="optRow" class="tr_normal_opt" invalidrow="true">
	        <td colspan="12">
	            <a href="javascript:void(0);" onclick="DocList_AddRow();">
	                <img src="${KMSS_Parameter_StylePath}icons/icon_add.png" border="0" />${lfn:message('doclist.add')}
	            </a>
	            &nbsp;
	            <a href="javascript:void(0);" onclick="DocList_MoveRowBySelect(-1);">
	                <img src="${KMSS_Parameter_StylePath}icons/icon_up.png" border="0" />${lfn:message('doclist.moveup')}
	            </a>
	            &nbsp;
	            <a href="javascript:void(0);" onclick="DocList_MoveRowBySelect(1);">
	                <img src="${KMSS_Parameter_StylePath}icons/icon_down.png" border="0" />${lfn:message('doclist.movedown')}
	            </a>
	            &nbsp;
	        </td>
	    </tr>
	</table>
	<input type="hidden" name="fdDetailList_Flag" value="1">
	<script>
	    Com_IncludeFile("doclist.js");
	</script>
	<script>
	    DocList_Info.push('TABLE_DocList_fdDetailList_Form');
	    //选择费用类型后带出会计科目
	    window.FSSC_AfterExpenseItemSelected = function(index,obj){
	    	if(!obj||obj.length==0){
	    		return;
	    	}
	    	$("[name='fdDetailList_Form["+index+"].fdAccountId']").val("");
	    	$("[name='fdDetailList_Form["+index+"].fdAccountName']").val("");
	    	var data = new KMSSData();
	    	data.AddBeanData("fsscExpenseDataService&type=getAccountByExpenseItem&fdExpenseItemId="+obj[0].fdId);
	    	data = data.GetHashMapArray(); 
	    	if(data&&data.length>0){
	    		$("[name='fdDetailList_Form["+index+"].fdAccountId']").val(data[0].id);
	    		$("[name='fdDetailList_Form["+index+"].fdAccountName']").val(data[0].name);
	    		var fields = ['fdCostCenterName','fdPersonName','fdFlowCashName','fdProjectName'];
	    		var req = data[0].item?data[0].item.split(";"):[];
	    		for(var i=0;i<fields.length;i++){
	    			var obj = $("[name='fdDetailList_Form["+index+"]."+fields[i]+"']")
	    			var validate = obj.attr("validate")||"";
	    			validate = validate.replace(/required/g,'');
	    			if(req.contains(i+1)){
	    				validate += " required";
	    			}
	    			obj.attr("validate",validate);
	    			var td = DocListFunc_GetParentByTagName("TD",obj[0]);
	    			var star = $(td).find(".txtstrong");
	    			star = star.length>0?star:$("<span class='txtstrong'>*</span>").appendTo($(td).find("div:eq(0)"));
	    			if(validate.indexOf('required')>-1){
	    				star.show();
	    			}else{
	    				star.hide();
	    			}
	    		}
	    	}
	    }
	    window.FSSC_AfterAccountSelected = function(index,obj){
	    	if(!obj||obj.length==0){
	    		return;
	    	}
	    	var fields = ['fdCostCenterName','fdPersonName','fdFlowCashName','fdProjectName'];
    		var req = obj[0].fdCostItem?obj[0].fdCostItem.split(";"):[];
    		for(var i=0;i<fields.length;i++){
    			var obj = $("[name='fdDetailList_Form["+index+"]."+fields[i]+"']")
    			var validate = obj.attr("validate")||"";
    			validate = validate.replace(/required/g,'');
    			if(req.contains(i+1)){
    				validate += " required";
    			}
    			obj.attr("validate",validate);
    			var td = DocListFunc_GetParentByTagName("TD",obj[0]);
    			var star = $(td).find(".txtstrong");
    			star = star.length>0?star:$("<span class='txtstrong'>*</span>").appendTo($(td).find("div:eq(0)"));
    			if(validate.indexOf('required')>-1){
    				star.show();
    			}else{
    				star.hide();
    			}
    		}
	    } 
	    //改变金额自动计算预算金额
	    window.FSSC_ChangeMoney = function(v,e){
	    	if(!v||isNaN(v)){
	    		return;
	    	}
	    	var index = e.name.replace(/\S+\[(\d+)\]\S+/,'$1');
	    	$("[name='fdDetailList_Form["+index+"].fdBudgetMoney']").val(multiPoint(v,$("[name=fdBudgetRate]").val()));
	    }
	</script>
</ui:content>
