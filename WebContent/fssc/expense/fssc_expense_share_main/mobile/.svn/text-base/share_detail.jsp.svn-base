<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ taglib uri="/WEB-INF/KmssConfig/eop/basedata/fssc.tld" prefix="fssc"%>
<div data-dojo-type="mui/table/ScrollableHContainer" >
	<div data-dojo-type="mui/table/ScrollableHView" style="margin-top:20px;" id="dt_wrap_invoice_hview">
		    <table class="muiNormal" id="TABLE_DocList_fdInvoiceList_Form" width="100%" border="0" cellspacing="0" cellpadding="0">
				 <tr align="center" class="tr_normal_title">
			        <td style="width:40px;">
			            ${lfn:message('page.serial')}
			        </td>
			        <td width="15%">
			            ${lfn:message('fssc-expense:fsscExpenseShareDetail.fdCompany')}
			        </td>
			        <td width="13%">
			            ${lfn:message('fssc-expense:fsscExpenseShareDetail.fdCostCenter')}
			        </td>
			        <td width="12%">
			            ${lfn:message('fssc-expense:fsscExpenseShareDetail.fdExpenseItem')}
			        </td>
			        <td width="10%">
			            ${lfn:message('fssc-expense:fsscExpenseShareDetail.fdHappenDate')}
			        </td>
			        <td width="10%">
			            ${lfn:message('fssc-expense:fsscExpenseShareDetail.fdMoney')}
			        </td>
			        <td width="10%">
			            ${lfn:message('fssc-expense:fsscExpenseShareDetail.fdCurrency')}
			        </td>
			        <td width="10%">
			            ${lfn:message('fssc-expense:fsscExpenseShareDetail.fdStandardMoney')}
			        </td>
			        <td width="15%">
			            ${lfn:message('fssc-expense:fsscExpenseShareDetail.fdRemark')}
			        </td>
			    </tr>
			    <c:forEach items="${fsscExpenseShareMainForm.fdDetailList_Form}" var="fdDetailList_FormItem" varStatus="vstatus">
			        <tr KMSS_IsContentRow="1">
			            <td align="center">
			                ${vstatus.index+1}
			            </td>
			            <td align="center">
				            <input type="hidden" name="fdDetailList_Form[${vstatus.index }].fdId" value="" disabled="true" />
				            <xform:dialog propertyName="fdDetailList_Form[${vstatus.index }].fdCompanyName" required="true" propertyId="fdDetailList_Form[!{index}].fdCompanyId" showStatus="view" subject="${lfn:message('fssc-expense:fsscExpenseShareDetail.fdCompany')}" validators=" maxLength(200)" style="width:85%;" >
				            	FSSC_SelectInvoiceCompany(${vstatus.index });
				            </xform:dialog>
				        </td>
				        <td align="center">
				            <div id="_xform_fdDetailList_Form[${vstatus.index }].fdCostCenterId" _xform_type="dialog">
				                <xform:dialog propertyId="fdDetailList_Form[${vstatus.index }].fdCostCenterId" propertyName="fdDetailList_Form[${vstatus.index }].fdCostCenterName" required="true" subject="${lfn:message('fssc-expense:fsscExpenseDetail.fdCostCenter')}" style="width:90%;">
				                    FSSC_SelectCostCenter(!{index});
				                </xform:dialog>
				            </div>
				        </td>
				        <td align="center">
				            ${fdDetailList_FormItem.fdExpenseItemName }
				        </td>
				        <td align="center">
				            ${fdDetailList_FormItem.fdHappenDate }
				        </td>
				        <td align="center">
				            <div id="_xform_fdDetailList_Form[${vstatus.index }].fdMoney" _xform_type="text" class="vat${vstatus.index }">
				                <kmss:showNumber value="${fdDetailList_FormItem.fdMoney }" pattern="0.00"/>
				            </div>
				        </td>
				        <td align="center">
				            <div id="_xform_fdDetailList_Form[${vstatus.index }].fdCurrency" _xform_type="text" class="vat${vstatus.index }">
				                <xform:text property="fdDetailList_Form[${vstatus.index }].fdCurrencyName" subject="${lfn:message('fssc-expense:fsscExpenseShareDetail.fdCurrency') }" showStatus="view" style="width:85%;" />
				            </div>
				        </td>
				        <td align="center">
				            <div id="_xform_fdDetailList_Form[${vstatus.index }].fdStandardMoney" _xform_type="text" class="vat${vstatus.index }">
				                <kmss:showNumber value="${fdDetailList_FormItem.fdStandardMoney }" pattern="0.00"/>
				            </div>
				        </td>
				        <td align="center">
				            <div id="_xform_fdDetailList_Form[${vstatus.index }].fdRemark" _xform_type="text" class="vat${vstatus.index }">
				                <xform:text property="fdDetailList_Form[${vstatus.index }].fdRemark" subject="${lfn:message('fssc-expense:fsscExpenseShareDetail.fdRemark') }" showStatus="view" style="width:80%;" />
				            </div>
				        </td>
			        </tr>
			    </c:forEach>
			</table>
		<input type="hidden" name="fdAccountsList_Flag" value="1">
	</div>
</div>
<script>
  Com_IncludeFile("doclist.js");
</script>
<script>
	DocList_Info.push('TABLE_DocList_fdDetailList_Form');
   	Com_AddEventListener(window,'load',function(){
   	   FSSC_AfterExpenseMainSelected([{fdId:'${fsscExpenseShareMainForm.fdModelId}'}]);
   	})
   	window.FSSC_AfterExpenseMainSelected = function(rtn){
   		if(!rtn){
   			return;
   		}
   		$.post(
   			'${LUI_ContextPath}/fssc/expense/fssc_expense_main/fsscExpenseMainData.do?method=getExpenseDetail',
 				{fdMainId:rtn[0].fdId},
 				function(data){
 					$("#TABLE_EXPENSE").append(data);
 				}
   		);
   	}
</script>


