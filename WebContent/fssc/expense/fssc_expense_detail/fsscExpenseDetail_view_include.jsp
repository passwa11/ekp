<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<%@ taglib uri="/WEB-INF/KmssConfig/eop/basedata/fssc.tld" prefix="fssc"%>
<ui:content title="${lfn:message('fssc-expense:table.fsscExpenseDetail') }" expand="true">
<style type="text/css">
.noshowUser{
display: none
}
</style>
<div style="min-width:980;max-width:1200;over-flow:scroll;'">
<table class="tb_normal" id="TABLE_DocList_fdDetailList_Form" align="center" width="100%">
    <tr align="center" class="tr_normal_title">
        <td style="width:30px;">
            ${lfn:message('page.serial')}
        </td>
        <c:if test="${docTemplate.fdAllocType=='2' }">
        <td  style="width:120px">
            ${lfn:message('fssc-expense:fsscExpenseDetail.fdCostCenter')}
        </td>
        </c:if>
        <c:if test="${docTemplate.fdAllocType=='1' }">
        <td  style="width:120px">
            ${lfn:message('fssc-expense:fsscExpenseDetail.fdCostCenter')}
        </td>
        </c:if>
        <td  style="width:120px">
            ${lfn:message('fssc-expense:fsscExpenseDetail.fdExpenseItem')}
        </td>
        <c:if test="${docTemplate.fdIsProject=='true'&&docTemplate.fdIsProjectShare=='true'}">
        <td  style="width:120px">
            ${lfn:message('fssc-expense:fsscExpenseDetail.fdProject')}
        </td>
        </c:if>
        <td  style="width:100px;display: none">
            ${lfn:message('fssc-expense:fsscExpenseDetail.fdRealUser')}
        </td>
        <c:if test="${fn:indexOf(docTemplate.fdExtendFields,'7')>-1 }">
        <td  style="width:120px">
            ${lfn:message('fssc-expense:fsscExpenseDetail.fdDept')}
        </td>
        </c:if>
        <fssc:configEnabled property="fdFinancialSystem" value="SAP">
        <c:if test="${fn:indexOf(docTemplate.fdExtendFields,'4')>-1 }">
        <td  style="width:120px">
            ${lfn:message('fssc-expense:fsscExpenseDetail.fdWbs')}
        </td>	
        </c:if>
        <c:if test="${fn:indexOf(docTemplate.fdExtendFields,'3')>-1 }">
        <td  style="width:120px">
            ${lfn:message('fssc-expense:fsscExpenseDetail.fdInnerOrder')}
        </td>	
        </c:if>
        </fssc:configEnabled>
        <c:if test="${fn:indexOf(docTemplate.fdExtendFields,'5')>-1 }">
        <td  style="width:120px">
       		${lfn:message('fssc-expense:fsscExpenseDetail.fdStartDate')}
       	</td>
       	</c:if>
        <td  style="width:120px">
        	<c:if test="${fn:indexOf(docTemplate.fdExtendFields,'5')>-1 }">
        		${lfn:message('fssc-expense:fsscExpenseTravelDetail.fdEndDate')}
        	</c:if>
            <c:if test="${fn:indexOf(docTemplate.fdExtendFields,'5')==-1 }">
        		${lfn:message('fssc-expense:fsscExpenseDetail.fdHappenDate')}
        	</c:if>         
        </td>
        <c:if test="${fn:indexOf(docTemplate.fdExtendFields,'5')>-1 }">
        <td  style="width:80px">
       	 	${lfn:message('fssc-expense:fsscExpenseDetail.fdTravelDays')}
       	</td>
       	</c:if>
       	<c:if test="${docTemplate.fdExpenseType eq '2' and docTemplate.fdIsTravelAlone eq 'false'}">
        <td  style="width:120px">
       	 	${lfn:message('fssc-expense:fsscExpenseDetail.fdStartPlace')}
       	</td>
       	<td  style="width:120px">
       	 	${lfn:message('fssc-expense:fsscExpenseDetail.fdArrivalPlace')}
       	</td>
       	<fssc:checkVersion version="true">
       	<c:if test="${fn:indexOf(docTemplate.fdExtendFields,'6')>-1 }">
        <td  style="width:120px">
       	 	${lfn:message('fssc-expense:fsscExpenseDetail.fdBerth')}
       	</td>
       	</c:if>
       	</fssc:checkVersion>
       	</c:if>
        <c:if test="${fn:indexOf(docTemplate.fdExtendFields,'1')>-1 }">
        <td  style="width:120px">
            ${lfn:message('fssc-expense:fsscExpenseDetail.fdPersonNumber')}
        </td>
        </c:if>
        <td  style="width:90px">
            ${lfn:message('fssc-expense:fsscExpenseDetail.fdApplyMoney')}
        </td>
        <%--发票金额和进项税金额一起展示 --%>
        <c:if test="${fn:indexOf(docTemplate.fdExtendFields,'2')>-1 }">
        <td  style="width:90px">
            ${lfn:message('fssc-expense:fsscExpenseDetail.fdInvoiceMoney')}
        </td>
        </c:if>
        <c:if test="${fn:indexOf(docTemplate.fdExtendFields,'9')>-1 }">
        <td  style="width:90px">
            ${lfn:message('fssc-expense:fsscExpenseDetail.fdNonDeductMoney')}
        </td>
        </c:if>
        <td  style="width:90px">
            ${lfn:message('fssc-expense:fsscExpenseDetail.fdApprovedApplyMoney')}
        </td>
        <c:if test="${fn:indexOf(docTemplate.fdExtendFields,'2')>-1 }">
        <td  style="width:90px">
            ${lfn:message('fssc-expense:fsscExpenseDetail.fdInputTaxMoney')}
        </td>
        <td  style="width:90px">
            ${lfn:message('fssc-expense:fsscExpenseDetail.fdNoTaxMoney')}
        </td>
        </c:if>
        <c:if test="${docTemplate.fdIsForeign=='true' }">
        <td  style="width:80px">
            ${lfn:message('fssc-expense:fsscExpenseDetail.fdCurrency')}/${lfn:message('fssc-expense:fsscExpenseDetail.fdExchangeRate')}
        </td>
        <td  style="width:70px">
            ${lfn:message('fssc-expense:fsscExpenseDetail.fdStandardMoney')}
        </td>
        </c:if>
        
        <td  style="width:0px" class="noshowUser">
            ${lfn:message('fssc-expense:fsscExpenseDetail.fdUse')}
        </td>
        <c:if test="${docTemplate.fdExpenseType eq '2' and docTemplate.fdIsTravelAlone eq 'true' }">
        <td  style="width:90px">
			${lfn:message('fssc-expense:fsscExpenseDetail.fdTravel')}
        </td>
        </c:if>
        <kmss:ifModuleExist path="/fssc/budget">
			<fssc:switchOn property="fdIsBudget" defaultValue="1">
        <td  style="width:120px">
           ${lfn:message('fssc-expense:fsscExpenseDetail.fdBudgetStatus')}
        </td>
			</fssc:switchOn>
        </kmss:ifModuleExist>
        <td  style="width:80px;display: none">
            ${lfn:message('fssc-expense:py.status')}
        </td>
        <td  style="width:15px"></td>
    </tr>
    <c:forEach items="${fsscExpenseMainForm.fdDetailList_Form}" var="fdDetailList_FormItem" varStatus="vstatus">
        <tr KMSS_IsContentRow="1">
            <td align="center">
                ${vstatus.index+1}
                <input type="hidden" name="fdDetailList_Form[${vstatus.index}].fdId" value="${fdDetailList_FormItem.fdId }" />
                <input type="hidden" name="fdDetailList_Form[${vstatus.index}].fdNoteId" value="${fdDetailList_FormItem.fdNoteId }" />
                <input type="hidden" name="fdDetailList_Form[${vstatus.index}].fdCompanyId" value="${fdDetailList_FormItem.fdCompanyId }" />
                <input type="hidden" name="fdDetailList_Form[${vstatus.index}].fdBudgetStatus" value="${fdDetailList_FormItem.fdBudgetStatus }" />
                <input type="hidden" name="fdDetailList_Form[${vstatus.index}].fdFeeInfo" value="${fdDetailList_FormItem.fdFeeInfo }" />
           		<input type="hidden" name="fdDetailList_Form[${vstatus.index}].fdFeeStatus" value="${fdDetailList_FormItem.fdFeeStatus }" />
           		<input type="hidden" name="fdDetailList_Form[${vstatus.index}].fdProappInfo" value="${fdDetailList_FormItem.fdProappInfo }" />
           		<input type="hidden" name="fdDetailList_Form[${vstatus.index}].fdProappStatus" value="${fdDetailList_FormItem.fdProappStatus }" />
                <input type="hidden" name="fdDetailList_Form[${vstatus.index}].fdStandardStatus" value="${fdDetailList_FormItem.fdStandardStatus }" />
                <input type="hidden" name="fdDetailList_Form[${vstatus.index}].fdStandardInfo" value="${fdDetailList_FormItem.fdStandardInfo }"/>
                <input type="hidden" name="fdDetailList_Form[${vstatus.index}].fdBudgetInfo" value="${fdDetailList_FormItem.fdBudgetInfo }" />
                <input type="hidden" name="fdDetailList_Form[${vstatus.index}].fdBudgetRate" value="${fdDetailList_FormItem.fdBudgetRate }" />
                <input type="hidden" name="fdDetailList_Form[${vstatus.index}].fdBudgetMoney" value="${fdDetailList_FormItem.fdBudgetMoney }" />
                <input type="hidden" name="fdDetailList_Form[${vstatus.index}].fdBudgetMoneyOld" value="${fdDetailList_FormItem.fdBudgetMoney }" />
                <input type="hidden" name="fdDetailList_Form[${vstatus.index}].fdStandardMoney" value="${fdDetailList_FormItem.fdStandardMoney }" />
                <input type="hidden" name="fdDetailList_Form[${vstatus.index}].fdExpenseItemId" value="${fdDetailList_FormItem.fdExpenseItemId }" />
                <input type="hidden" name="fdDetailList_Form[${vstatus.index}].fdApplyMoney" value="${fdDetailList_FormItem.fdApplyMoney }" />
                <input type="hidden" name="fdDetailList_Form[${vstatus.index}].fdNoTaxMoney" value="${fdDetailList_FormItem.fdNoTaxMoney }" />
                <input type="hidden" name="fdDetailList_Form[${vstatus.index}].fdApprovedStandardMoney" value="${fdDetailList_FormItem.fdApprovedStandardMoney }" />
                <input type="hidden" name="fdDetailList_Form[${vstatus.index}].fdCurrencyId" value="${fdDetailList_FormItem.fdCurrencyId }" />
                <input type="hidden" name="fdDetailList_Form[${vstatus.index}].fdWbsId" value="${fdDetailList_FormItem.fdWbsId }" />
                <input type="hidden" name="fdDetailList_Form[${vstatus.index}].fdInnerOrderId" value="${fdDetailList_FormItem.fdInnerOrderId }" />
                <input type="hidden" name="fdDetailList_Form[${vstatus.index}].fdRealUserId" value="${fdDetailList_FormItem.fdRealUserId }" />
                <input type="hidden" name="fdDetailList_Form[${vstatus.index}].fdDeptId" value="${fdDetailList_FormItem.fdDeptId }" />
	            <input type="hidden" name="fdDetailList_Form[${vstatus.index}].fdCostCenterId" value="${fdDetailList_FormItem.fdCostCenterId }" />
	            <input type="hidden" name="fdDetailList_Form[${vstatus.index}].fdPersonNumber" value="${fdDetailList_FormItem.fdPersonNumber }" />
	            <input type="hidden" name="fdDetailList_Form[${vstatus.index}].fdHappenDate" value="${fdDetailList_FormItem.fdHappenDate }" />
	            <input type="hidden" name="fdDetailList_Form[${vstatus.index}].fdTravel" value="${fdDetailList_FormItem.fdTravel }" />
	            <input type="hidden" name="fdDetailList_Form[${vstatus.index}].fdStartDate" value="${fdDetailList_FormItem.fdStartDate }" />
	            <input type="hidden" name="fdDetailList_Form[${vstatus.index}].fdStartPlace" value="${fdDetailList_FormItem.fdStartPlace }" />
	            <input type="hidden" name="fdDetailList_Form[${vstatus.index}].fdStartPlaceId" value="${fdDetailList_FormItem.fdStartPlaceId }" />
	            <input type="hidden" name="fdDetailList_Form[${vstatus.index}].fdArrivalPlace" value="${fdDetailList_FormItem.fdArrivalPlace }" />
	            <input type="hidden" name="fdDetailList_Form[${vstatus.index}].fdTravelDays" value="${fdDetailList_FormItem.fdTravelDays }" />
	            <input type="hidden" name="fdDetailList_Form[${vstatus.index}].fdArrivalPlaceId" value="${fdDetailList_FormItem.fdArrivalPlaceId }" />
	            <input type="hidden" name="fdDetailList_Form[${vstatus.index}].fdBerthId" value="${fdDetailList_FormItem.fdBerthId }" />
	            <input type="hidden" name="fdDetailList_Form[${vstatus.index}].fdInputTaxRate" value="${fdDetailList_FormItem.fdInputTaxRate }" />
	            <xform:text showStatus="noShow" property="fdDetailList_Form[${vstatus.index}].fdInputTaxRateId"  />
	            <input type="hidden" name="fdDetailList_Form[${vstatus.index}].fdExpenseTempId" value="${fdDetailList_FormItem.fdExpenseTempId }" />
	            <input type="hidden" name="fdDetailList_Form[${vstatus.index}].fdExpenseTempDetailIds" value="${fdDetailList_FormItem.fdExpenseTempDetailIds }" />
	            <c:if test="${docTemplate.fdIsForeign!='true' }">
	            <input type="hidden" name="fdDetailList_Form[${vstatus.index}].fdCurrencyId" value="${fdDetailList_FormItem.fdCurrencyId }" />
	            <input type="hidden" name="fdDetailList_Form[${vstatus.index}].fdExchangeRate" value="${fdDetailList_FormItem.fdExchangeRate }" />
	            </c:if>
	           	<input type="hidden" name="fdDetailList_Form[${vstatus.index}].fdProjectId" value="${fdDetailList_FormItem.fdProjectId }" />
            	<input type="hidden" name="fdDetailList_Form[${vstatus.index}].fdProvisionMoney" value="${fdDetailList_FormItem.fdProvisionMoney }" />
            	<input type="hidden" name="fdDetailList_Form[${vstatus.index}].fdProvisionInfo" value='${fdDetailList_FormItem.fdProvisionInfo }' />
            </td>
        	<c:if test="${docTemplate.fdAllocType=='2' }">
            <td align="center">
                <div id="_xform_fdDetailList_Form[${vstatus.index}].fdCostCenterId" _xform_type="dialog">
                    <xform:dialog propertyId="fdDetailList_Form[${vstatus.index}].fdCostCenterId" propertyName="fdDetailList_Form[${vstatus.index}].fdCostCenterName" required="true" subject="${lfn:message('fssc-expense:fsscExpenseDetail.fdCostCenter')}"
                    style="width:90%;">
                        dialogSelect(false,'eop_basedata_cost_center_selectCostCenter','fdDetailList_Form[*].fdCostCenterId','fdDetailList_Form[*].fdCostCenterName');
                    </xform:dialog>
                </div>
            </td>
            </c:if>
            <c:if test="${docTemplate.fdAllocType=='1' }">
            <td align="center">
                <div id="_xform_fdDetailList_Form[${vstatus.index}].fdCostCenterId" _xform_type="dialog">
                    <xform:dialog propertyId="fdDetailList_Form[${vstatus.index}].fdCostCenterId" propertyName="fdDetailList_Form[${vstatus.index}].fdCostCenterName" required="true" subject="${lfn:message('fssc-expense:fsscExpenseDetail.fdCostCenter')}"
                    style="width:90%;">
                        dialogSelect(false,'eop_basedata_cost_center_selectCostCenter','fdDetailList_Form[*].fdCostCenterId','fdDetailList_Form[*].fdCostCenterName');
                    </xform:dialog>
                </div>
            </td>
            </c:if>
            <td align="center">
	            <div id="_xform_fdDetailList_Form[${vstatus.index}].fdExpenseItemId" _xform_type="dialog">
	                <xform:dialog propertyId="fdDetailList_Form[${vstatus.index}].fdExpenseItemId" propertyName="fdDetailList_Form[${vstatus.index}].fdExpenseItemName" required="true" subject="${lfn:message('fssc-expense:fsscExpenseDetail.fdExpenseItem')}" style="width:90%;">
	                    FSSC_SelectExpenseItem(${vstatus.index});
	                </xform:dialog>
	            </div>
	        </td>
	        <c:if test="${docTemplate.fdIsProject=='true'&&docTemplate.fdIsProjectShare=='true'}">
        	<td align="center">
	            <xform:dialog propertyId="fdDetailList_Form[${vstatus.index}].fdProjectId" propertyName="fdDetailList_Form[${vstatus.index}].fdProjectName" required="true" subject="${lfn:message('fssc-expense:fsscExpenseDetail.fdProject')}" style="width:90%;">
	                FSSC_SelectProjectDetail();
	            </xform:dialog>
	        </td>
	        </c:if>
            <td align="center"  class="noshowUser">
                <div id="_xform_fdDetailList_Form[${vstatus.index}].fdRealUserId" _xform_type="address">
                    <xform:address propertyId="fdDetailList_Form[${vstatus.index}].fdRealUserId" propertyName="fdDetailList_Form[${vstatus.index}].fdRealUserName" orgType="ORG_TYPE_PERSON" required="true" subject="${lfn:message('fssc-expense:fsscExpenseDetail.fdRealUser')}"
                    style="width:90%;" />
                </div>
            </td>
            <c:if test="${fn:indexOf(docTemplate.fdExtendFields,'7')>-1 }">
            <td align="center">
	            <div id="_xform_fdDetailList_Form[!{index}].fdDeptId" _xform_type="address">
	                <xform:address propertyId="fdDetailList_Form[${vstatus.index}].fdDeptId" propertyName="fdDetailList_Form[${vstatus.index}].fdDeptName" orgType="ORG_TYPE_DEPT" showStatus="view" required="true" subject="${lfn:message('fssc-expense:fsscExpenseDetail.fdDept')}"
	                style="width:90%;" />
	            </div>
	        </td>
	        </c:if>
	        <fssc:configEnabled property="fdFinancialSystem" value="SAP">
	        <c:if test="${fn:indexOf(docTemplate.fdExtendFields,'4')>-1 }">
		        <c:if test="${docTemplate.fdIsProject=='true' }">
		        <td align="center">
		            <div id="_xform_fdDetailList_Form[${vstatus.index}].fdWbsId" _xform_type="dialog">
		                <xform:dialog propertyId="fdDetailList_Form[${vstatus.index}].fdWbsId" propertyName="fdDetailList_Form[${vstatus.index}].fdWbsName" subject="${lfn:message('fssc-expense:fsscExpenseDetail.fdWbs')}" style="width:90%;">
		                    FSSC_SelectWbsIsProject(${vstatus.index});
		                </xform:dialog>
		            </div>
		        </td>
		        </c:if>
		        <c:if test="${docTemplate.fdIsProject=='false' }">
		        <td align="center">
		            <div id="_xform_fdDetailList_Form[${vstatus.index}].fdWbsId" _xform_type="dialog">
		                <xform:dialog propertyId="fdDetailList_Form[${vstatus.index}].fdWbsId" propertyName="fdDetailList_Form[${vstatus.index}].fdWbsName" subject="${lfn:message('fssc-expense:fsscExpenseDetail.fdWbs')}" style="width:90%;">
		                    FSSC_SelectWbs(${vstatus.index});
		                </xform:dialog>
		            </div>
		        </td>
		        </c:if>
	        </c:if>
	        <c:if test="${fn:indexOf(docTemplate.fdExtendFields,'3')>-1 }">
	        <td align="center">
	            <div id="_xform_fdDetailList_Form[${vstatus.index}].fdInnerOrderId" _xform_type="dialog">
	                <xform:dialog propertyId="fdDetailList_Form[${vstatus.index}].fdInnerOrderId" propertyName="fdDetailList_Form[${vstatus.index}].fdInnerOrderName" subject="${lfn:message('fssc-expense:fsscExpenseDetail.fdInnerOrder')}" style="width:90%;">
	                    FSSC_SelectInnerOrder(${vstatus.index});
	                </xform:dialog>
	            </div>
	        </td>
	        </c:if>
	        </fssc:configEnabled>
	        <c:if test="${fn:indexOf(docTemplate.fdExtendFields,'5')>-1 }">
	        <td>
	       		<xform:datetime property="fdDetailList_Form[${vstatus.index}].fdStartDate"  required="true" subject="${lfn:message('fssc-expense:fsscExpenseDetail.fdStartDate')}" style="width:85%;" dateTimeType="date"/>
	       	</td>
	       	</c:if>
            <td align="center">
	            <div id="_xform_fdDetailList_Form[${vstatus.index}].fdHappenDate" _xform_type="text">
	            		<xform:text property="fdDetailList_Form[${vstatus.index}].fdHappenDate" value="${fdDetailList_FormItem.fdHappenDate }" showStatus="noShow"/>
	                <xform:datetime property="fdDetailList_Form[${vstatus.index}].fdHappenDate" required="true" subject="${lfn:message('fssc-expense:fsscExpenseDetail.fdHappenDate')}" style="width:85%;" dateTimeType="date"/>
	            </div>
	        </td>
	        <c:if test="${fn:indexOf(docTemplate.fdExtendFields,'5')>-1 }">
	        <td>
	       		<xform:text property="fdDetailList_Form[${vstatus.index}].fdTravelDays"></xform:text>
	       	</td>
	       	</c:if>
	       	<c:if test="${docTemplate.fdExpenseType eq '2' and docTemplate.fdIsTravelAlone eq 'false'}">
	        <td>
	       	 	<xform:dialog propertyId="fdDetailList_Form[${vstatus.index}].fdStartPlaceId" required="true" propertyName="fdDetailList_Form[${vstatus.index}].fdStartPlace" subject="${lfn:message('fssc-expense:fsscExpenseDetail.fdStartPlace')}" style="width:90%;">
	                FSSC_SelectPlace('fdDetailList_Form[*].fdStartPlaceId','fdDetailList_Form[*].fdStartPlace');
	            </xform:dialog>
	       	</td>
	       	<td>
	       	 	<xform:dialog propertyId="fdDetailList_Form[${vstatus.index}].fdArrivalPlaceId" required="true" propertyName="fdDetailList_Form[${vstatus.index}].fdArrivalPlace" subject="${lfn:message('fssc-expense:fsscExpenseDetail.fdArrivalPlace')}" style="width:90%;">
	                FSSC_SelectPlace('fdDetailList_Form[*].fdArrivalPlaceId','fdDetailList_Form[*].fdArrivalPlace');
	            </xform:dialog>
	       	</td>
	       	<fssc:checkVersion version="true">
	       	<c:if test="${fn:indexOf(docTemplate.fdExtendFields,'6')>-1 }">
	        <td>
	       	 	<xform:dialog propertyId="fdDetailList_Form[${vstatus.index}].fdBerthId" required="true" propertyName="fdDetailList_Form[${vstatus.index}].fdBerthName" subject="${lfn:message('fssc-expense:fsscExpenseDetail.fdBerth')}" style="width:90%;">
	                FSSC_SelectBerth('fdDetailList_Form[*].fdBerthId','fdDetailList_Form[*].fdBerthName');
	            </xform:dialog>
	       	</td>
	       	</c:if>
	       	</fssc:checkVersion>
	       	</c:if>
	        <c:if test="${fn:indexOf(docTemplate.fdExtendFields,'1')>-1 }">
	        <td align="center">
	            <div id="_xform_fdDetailList_Form[${vstatus.index}].fdPersonNumber" _xform_type="text">
	                <xform:text property="fdDetailList_Form[${vstatus.index}].fdPersonNumber" required="true" subject="${lfn:message('fssc-expense:fsscExpenseDetail.fdPersonNumber')}" style="width:85%;"/>
	            </div>
	        </td>
	        </c:if>
            <td align="center">
                <div id="_xform_fdDetailList_Form[${vstatus.index}].fdApplyMoney" _xform_type="text">
                      <input type="text" class="inputsgl" style="width:85%;color:#333;"  readonly="readonly" name="fdDetailList_Form[${vstatus.index}].fdApplyMoney" value="<kmss:showNumber value="${fdDetailList_FormItem.fdApplyMoney }" pattern="0.00" />" /> 
                </div>
            </td>
            <%--发票金额和进项税金额一起展示 --%>
        	<c:if test="${fn:indexOf(docTemplate.fdExtendFields,'2')>-1 }">
            <td align="center">
	            <div id="_xform_fdDetailList_Form[${vstatus.index}].fdInvoiceMoney" _xform_type="text">
	                <input name="fdDetailList_Form[${vstatus.index}].fdInvoiceMoney" value="<kmss:showNumber value="${fdDetailList_FormItem.fdInvoiceMoney }" pattern="0.00"/>" class="inputsgl" readonly="readonly" style="width:85%;color:#333;"/>
	            </div>
	        </td>
	        </c:if>
	        <c:if test="${fn:indexOf(docTemplate.fdExtendFields,'9')>-1 }">
	        <td align="center">
	            <div id="_xform_fdDetailList_Form[${vstatus.index}].fdNonDeductMoney" _xform_type="text">
	                <c:if test="${fsscExpenseMainForm.sysWfBusinessForm.fdNodeAdditionalInfo.examine!='true' }">
	                <input type="text" name="fdDetailList_Form[${vstatus.index}].fdNonDeductMoney" value="<kmss:showNumber value="${fdDetailList_FormItem.fdNonDeductMoney }" pattern="0.00"/>" validate="currency-dollar"  __validate_serial="_validate_1"  onchange="FSSC_CalculateMoneyView(this.value,this)" class="inputsgl"  readonly="readonly" subject="${lfn:message('fssc-expense:fsscExpenseDetail.fdNonDeductMoney')}"  style="width:85%;" />
	            	</c:if>
	                <c:if test="${fsscExpenseMainForm.sysWfBusinessForm.fdNodeAdditionalInfo.examine=='true' }">
	               	<input type="text" name="fdDetailList_Form[${vstatus.index}].fdNonDeductMoney" value="<kmss:showNumber value="${fdDetailList_FormItem.fdNonDeductMoney }" pattern="0.00" />" validate="currency-dollar" __validate_serial="_validate_1" onchange="FSSC_CalculateMoneyView(this.value,this)" class="inputsgl" subject="${lfn:message('fssc-expense:fsscExpenseDetail.fdNonDeductMoney')}"  style="width:85%;" />
	            	</c:if>
	            </div>
	        </td>
	        </c:if>
            <c:if test="${fsscExpenseMainForm.sysWfBusinessForm.fdNodeAdditionalInfo.examine=='true' }">
            <td align="center">
            <!-- 审批页面 核准金额 -->
               <div id="_xform_fdDetailList_Form[${vstatus.index}].fdApprovedApplyMoney" _xform_type="text">
                  <input type="text" onchange="FSSC_ChangeMoney(this.value,this)" name="fdDetailList_Form[${vstatus.index}].fdApprovedApplyMoney" validate="required currency-dollay max(${fdDetailList_FormItem.fdApplyMoney })"  __validate_serial="_validate_1" value="<kmss:showNumber value="${fdDetailList_FormItem.fdApprovedApplyMoney }" pattern="0.00"/>"  class="inputsgl"  subject="${lfn:message('fssc-expense:fsscExpenseDetail.fdApprovedApplyMoney')}" style="width:90%;" /> 
                </div>
            </td>
            </c:if>
            <c:if test="${fsscExpenseMainForm.sysWfBusinessForm.fdNodeAdditionalInfo.examine!='true' }">
            <td align="center">
             <div id="_xform_fdDetailList_Form[${vstatus.index}].fdApprovedApplyMoney" _xform_type="text">
                    <kmss:showNumber value="${fdDetailList_FormItem.fdApprovedApplyMoney }" pattern="0.00"/>
                </div> 
            </td>
            </c:if>
            <c:if test="${fn:indexOf(docTemplate.fdExtendFields,'2')>-1 }">
            <c:if test="${fsscExpenseMainForm.sysWfBusinessForm.fdNodeAdditionalInfo.examine=='true' }">
	        	<td align="center">
		        	<xform:checkbox property="fdDetailList_Form[${vstatus.index}].fdIsDeduct" showStatus="edit" onValueChange="FSSC_ChangeIsDeductView">
		        		<xform:simpleDataSource value="true" textKey="fsscExpenseDetail.fdIsDeduct" bundle="fssc-expense"/>
		        	</xform:checkbox>
		        	<!-- 选择进项税率 -->
	                <%-- <div class="inputselectsgl" id="_xform_fdDetailList_Form[${vstatus.index}].fdInputTaxRate"  style="width:90%;display:${fdDetailList_FormItem.fdIsDeduct=='true'?'':'none'};" onClick="FSSC_SelectInputTaxRateView(${vstatus.index});" >
	              	<input name="fdDetailList_Form[${vstatus.index}].fdInputTaxRateId"  type="hidden" value="${fdInputTaxRateId }" />
	                	<div class="input">
	                		<input  name="fdDetailList_Form[${vstatus.index}].fdInputTaxRate" subject="${lfn:message('fssc-expense:fsscExpenseDetail.fdInputTaxRate') }" value="${fdDetailList_FormItem.fdInputTaxRate }">
	                	</div>
	                	<div class="selectitem" ></div>
	                </div> --%>
	                 <select name="fdDetailList_Form[${vstatus.index}].fdInputTaxRate_select" onchange="FSSC_selectTaxMoney(${vstatus.index})">
						  <option value="0">0</option>
						  <option value="3">3</option>
						  <option value="6">6</option>
						  <option value="13">13</option>
						</select>%
	                <!-- 进项税额 -->
		            <div id="_xform_fdDetailList_Form[${vstatus.index}].fdInputTaxMoney" _xform_type="text" class="inputTax" style="display:${fdDetailList_FormItem.fdIsDeduct=='true'?'':'none'};">
		                <input type="text" name="fdDetailList_Form[${vstatus.index}].fdInputTaxMoney" value="<kmss:showNumber value="${fdDetailList_FormItem.fdInputTaxMoney }" pattern="0.00"/>" class="inputsgl" showStatus="edit" onchange="FSSC_CalculateNoTaxMoneyView(this.value,this)" validate="currency-dollar min(0) required"  subject="${lfn:message('fssc-expense:fsscExpenseDetail.fdInputTaxMoney')}" style="width:80%;" />
		           </div>
	            </td>
	            <td align="center">
		            <div id="_xform_fdDetailList_Form[${vstatus.index}].fdNoTaxMoney" _xform_type="text">
		                <input type="text" class="inputsgl" name="fdDetailList_Form[${vstatus.index}].fdNoTaxMoney"  onchange="FSSC_ChangeMoney(this.value,this);FSSC_MatchBudget()" value="<kmss:showNumber value="${fdDetailList_FormItem.fdNoTaxMoney }" pattern="0.00"/>" validate="required currency-dollar"  __validate_serial="_validate_1"  subject="${lfn:message('fssc-expense:fsscExpenseDetail.fdNoTaxMoney')}" style="width:85%;" />
		            </div>
		        </td>
	        </c:if>
	        <c:if test="${empty fsscExpenseMainForm.sysWfBusinessForm.fdNodeAdditionalInfo.examine }">
	        <td align="center">
	           <!-- 显示进项税率 -->
	        	  	<div id="_xform_fdDetailList_Form[${vstatus.index}].fdInputTaxRate" _xform_type="text" class="inputTax" style="display:${fdDetailList_FormItem.fdIsDeduct=='true'?'':'none'};">
	              <input type="text" readonly="readonly" class="inputsgl"   name="fdDetailList_Form[${vstatus.index}].fdInputTaxRate" value="${fdDetailList_FormItem.fdInputTaxRate}%" style="width:85%;"/>
	             </div>
		            
	        	<div id="_xform_fdDetailList_Form[${vstatus.index}].fdInputTaxMoney" _xform_type="text" class="inputTax" style="display:${fdDetailList_FormItem.fdIsDeduct=='true'?'':'none'};">
	              <input type="text" readonly="readonly" class="inputsgl"   name="fdDetailList_Form[${vstatus.index}].fdInputTaxMoney" value="<kmss:showNumber value="${fdDetailList_FormItem.fdInputTaxMoney}"  pattern="0.00" />" style="width:85%;"/>
	             </div>
	        </td>
	        <td align="center">
	             <div id="_xform_fdDetailList_Form[${vstatus.index}].fdNoTaxMoney" _xform_type="text">
	             <kmss:showNumber value="${fdDetailList_FormItem.fdNoTaxMoney }" pattern="###,##0.00"/>
	        </td>
	        </c:if>
	        </c:if>
            <c:if test="${docTemplate.fdIsForeign=='true' }">
            <td align="center">
                <div id="_xform_fdDetailList_Form[${vstatus.index}].fdCurrencyId" _xform_type="dialog">
                    <xform:dialog propertyId="fdDetailList_Form[${vstatus.index}].fdCurrencyId" required="true" propertyName="fdDetailList_Form[${vstatus.index}].fdCurrencyName" style="width:85%;" subject="${lfn:message('fssc-expense:fsscExpenseDetail.fdCurrency')}">
                        FSSC_SelectCurrency(${vstatus.index});
                    </xform:dialog>
                    <c:if test="${fsscExpenseMainForm.sysWfBusinessForm.fdNodeAdditionalInfo.examine!='true' }">
                    	<kmss:showNumber value="${fdDetailList_FormItem.fdExchangeRate }" pattern="0.0#####"/>
                    	<xform:text property="fdDetailList_Form[${vstatus.index}].fdExchangeRate" showStatus="noShow"/>
	                </c:if>
	                <c:if test="${fsscExpenseMainForm.sysWfBusinessForm.fdNodeAdditionalInfo.examine=='true' }">
	                    <xform:text onValueChange="FSSC_ChangeExchangeRate" property="fdDetailList_Form[${vstatus.index}].fdExchangeRate" showStatus="edit" required="true" subject="${lfn:message('fssc-expense:fsscExpenseDetail.fdExchangeRate')}" style="width:40%;" />
	                </c:if>
                </div>
            </td>
            <td align="center">
                <div id="_xform_fdDetailList_Form[${vstatus.index}].fdStandardMoney" _xform_type="text">
                	<kmss:showNumber value="${fdDetailList_FormItem.fdStandardMoney }" pattern="0.00"/>
                </div>
            </td>
            </c:if>
            <td align="center" class="noshowUser">
                <div id="_xform_fdDetailList_Form[${vstatus.index}].fdUse" _xform_type="text">
                    <xform:text property="fdDetailList_Form[${vstatus.index}].fdUse" style="width:90%;" />
                </div>
            </td>
            <c:if test="${docTemplate.fdExpenseType eq '2'  and docTemplate.fdIsTravelAlone eq 'true'}">
	        <td align="center">
				${fdDetailList_FormItem.fdTravel }
	        </td>
	        </c:if>
	        <kmss:ifModuleExist path="/fssc/budget">
				<fssc:switchOn property="fdIsBudget" defaultValue="1">
	        <td align="left">
	        	<div id="buget_status_${vstatus.index }">
	        	</div>
	        </td>
				</fssc:switchOn>
	        </kmss:ifModuleExist>
	        <td align="left" class="status_other noshowUser">
	        	<!-- 关联事前，显示事前状态灯 -->
	        	<c:if test="${docTemplate.fdIsFee=='true' }">
		        	<div id="fee_status_${vstatus.index }" class="budget_container">
		        	</div>
	        	</c:if>
	       	 	<!-- 关联立项，显示立项状态灯 -->
	        	<c:if test="${docTemplate.fdIsProapp=='true' }">
		        	<div id="proapp_status_${vstatus.index }" class="budget_container">
		        	</div>
	        	</c:if>
	        	<fssc:checkVersion version="true">
	        	<div id="standard_status_${vstatus.index }" class="budget_container">
	        	</div>
	        	</fssc:checkVersion>
	        </td>
	        <td align="center" >
            	<a href="javascript:void(0);" onclick="viewInvoiceTemp('${fsscExpenseMainForm.sysWfBusinessForm.fdNodeAdditionalInfo.examine}');" title="${lfn:message('fssc-expense:button.viewInvoice')}">
	                <img src="${LUI_ContextPath}/resource/style/common/images/small_search.png" border="0" />
	            </a>
            </td>
        </tr>
    </c:forEach>
</table>
</div>
<c:import url="/fssc/expense/fssc_expense_amortize/fsscExpenseDetail_view_amortz.jsp"></c:import>
</ui:content>
<input type="hidden" name="fdDetailList_Flag" value="1">
<input type="hidden" name="fdTravelList_Flag" value="1">
<input type="hidden" name="fdInvoiceList_Flag" value="1">
<input type="hidden" name="fdAccountsList_Flag" value="1">
<input type="hidden" name="fdLoanList_Flag" value="1">
<input type="hidden" name="fdOffsetList_Flag" value="1">
<style>
	.status_other>.budget_container{float:left;margin-left:20%;margin-right:0;}
</style>
<script>
	if(window.navigator.userAgent.toLowerCase().indexOf("msie")>-1
			||window.navigator.userAgent.toLowerCase().indexOf("trident")>-1){//IE
		setTimeout(function(){initData();},3000);
	}else{//非IE
		LUI.ready(function(){
			initData();
		});
	};
	window.initData=function(){
		var len = $("#TABLE_DocList_fdDetailList_Form tr").length-1;
		for(var i=0;i<len;i++){
			FSSC_ShowBudgetInfo(i);
			FSSC_ShowStandardInfo(i);
		}
	}
    seajs.use(['lui/dialog','lang!fssc-expense'],function(dialog,lang){
    	//显示预算状态信息
    	window.FSSC_ShowBudgetInfo = function(index){
    		var fdBudgetShowType = "${docTemplate.fdBudgetShowType}";
   			var fdBudgetStatus = $("[name='fdDetailList_Form["+index+"].fdBudgetStatus']").val()||0;
   			var fdFeeStatus = $("[name='fdDetailList_Form["+index+"].fdFeeStatus']").val()||0;
   			var showBudget = null,showInfo = null;
   			var info = $("[name='fdDetailList_Form["+index+"].fdBudgetInfo']").val()||'[]';
   			var fee = $("[name='fdDetailList_Form["+index+"].fdFeeInfo']").val()||'[]';
   			info = JSON.parse(info.replace(/\'/g,'"'));
   			fee = JSON.parse(fee.replace(/\'/g,'"'));
   			var fdProappId = '${fsscExpenseMainForm.fdProappId}';
   			if(fdProappId){//如果有立项
   				var fdProappStatus = $("[name='fdDetailList_Form["+index+"].fdProappStatus']").val();
   				var showInfo = $("[name='fdDetailList_Form["+index+"].fdProappInfo']").val()||'{}';
   				showInfo = JSON.parse(showInfo.replace(/\'/g,'"'));
				//显示红灯及超申请提示
				$("#proapp_status_"+index).attr("class","budget_container budget_status_"+fdProappStatus);
				$("#proapp_status_"+index).attr("title",lang['py.proapp.'+fdProappStatus]);
				if(fdBudgetShowType==1){
					$("#buget_status_"+index).attr("class","budget_container budget_status_0");
					$("#buget_status_"+index).attr("title",lang['py.budget.0']);
				}else{
					$("#buget_status_"+index).html(lang['py.money.total']+"0<br>"+lang['py.money.using']+"0<br>"+lang['py.money.used']+"0<br>"+lang['py.money.usable']+"<span class='budget_money_"+i+"'>"+"0</span>");
					$(".buget_status_"+index).css("color",fdBudgetStatus=='2'?"red":"#333");
				}
				return;
   			}
   			//显示事前信息
			//显示红灯及超申请提示
			$("#fee_status_"+index).attr("class","budget_container budget_status_"+fdFeeStatus);
			$("#fee_status_"+index).attr("title",lang['py.fee.'+fdFeeStatus]);
   			//显示预算信息
 			for(var i=0;i<info.length;i++){
	   			//获取可用金额最少的预算用于展示
	   			if(!showBudget||showBudget.fdCanUseAmount>info[i].fdCanUseAmount){
	   				showBudget = info[i];
	   			}
	   		}
	   		if(!showBudget){
	   			showBudget = {fdTotalAmount:0,fdOccupyAmount:0,fdAlreadyUsedAmount:0,fdCanUseAmount:0,canUseAmountDisplay:0}
	   		}
	   		if(fdBudgetShowType=='1'){//显示图标
   				$("#buget_status_"+index).attr("class","budget_container");
   				$("#buget_status_"+index).addClass("budget_status_"+fdBudgetStatus);
   				$("#buget_status_"+index).attr("title",lang['py.budget.'+fdBudgetStatus]);
   				layui.use('layer', function(){
	 	   			$("[id*='buget_status_']").mouseover(function(){
		   	 	  		var info = $(this).parent().parent().find("[name$=fdBudgetInfo]").val();
			   	 	  	if(!info){
		   	 	  			return;
		   	 	  		}
		   	 	  		info = JSON.parse(info.replace(/\'/ig,'\"'));
		   	 	  		if(info.length==0){
		   	 	  			return;
		   	 	  		}
		   	 	  		var text = '';
		   	 	  		for(var i=0;i<info.length;i++){
		   	 	  			text+=info[i].fdSubject+"<br>";
		   	 	  		}
		   	 	  		var id = layui.layer.tips(text, this, {
		   	 	  			tips: [1, '#6AA237'],
		   	 	  			time:0,
		   	 	  			//offset:'50px',
		   	 	  			anim: 1
		   	 	  		});
		   	 	  		$(this).attr("title","")
		   	 	  	}).mouseout(function(){
		   	 	  		layui.layer.closeAll("tips");
		   	 	  	})
  	   			})
	   		}else{//显示金额
	   			$("#buget_status_"+index).html(lang['py.money.total']+showBudget.fdTotalAmount+"<br>"+lang['py.money.using']+showBudget.fdOccupyAmount+"<br>"+lang['py.money.used']+showBudget.fdAlreadyUsedAmount+"<br>"+lang['py.money.usable']+"<span class='budget_money_"+index+"'>"+(showBudget.canUseAmountDisplay?showBudget.canUseAmountDisplay:showBudget.fdCanUseAmount)+"</span>");
				$(".budget_money_"+index).css("color",fdBudgetStatus=='2'?"red":"#333");
	   		}
    	}
    	//显示标准信息
    	window.FSSC_ShowStandardInfo=function(index){
    		if(!$("[name=checkVersion]").val()){
    			return;
    		}
    		var fdStandardStatus = $("[name='fdDetailList_Form["+index+"].fdStandardStatus']").val()||0;
 			$("#standard_status_"+index).attr("class","budget_container");
 			$("#standard_status_"+index).addClass("standard_status_"+fdStandardStatus);
 			$("#standard_status_"+index).attr("title",lang['py.standard.'+fdStandardStatus]);
 			layui.use('layer', function(){
   	   			$("[id*='standard_status']").mouseover(function(){
	   	 	  		var info = $(this).parent().parent().find("[name$=fdStandardInfo]").val();
	   	 	  		if(!info){
	   	 	  			return;
	   	 	  		}
	   	 	  		info = JSON.parse(info.replace(/\'/ig,'\"'));
	   	 	  		if(info.length==0){
	   	 	  			return;
	   	 	  		}
	   	 	  		var text = '';
	   	 	  		for(var i=0;i<info.length;i++){
	   	 	  			text+=info[i].subject+"<br>";
	   	 	  		}
	   	 	  		var id = layui.layer.tips(text, this, {
	   	 	  			tips: [1, '#6AA237'],
	   	 	  			time:0,
	   	 	  			//offset:'50px',
	   	 	  			anim: 1
	   	 	  		});
	   	 	  	}).mouseout(function(){
	   	 	  		layui.layer.closeAll("tips");
	   	 	  	})
	   		})
    	}
    	//修改核准金额或汇率后，自动计算本币金额及总额
    	window.FSSC_ChangeMoney=function(v,e,i){
    		var index = i==null?e.name.replace(/\S+\[(\d+)\]\S+/g,'$1'):i;
    		var fdApplyMoney = $("[name='fdDetailList_Form["+index+"].fdApprovedApplyMoney']").val();
    		var fdExchangeRate = $("[name='fdDetailList_Form["+index+"].fdExchangeRate']").val();
    		if(isNaN(fdApplyMoney)||isNaN(fdExchangeRate)){
    			return;
    		}
    		var fdStandardMoney = multiPoint(fdExchangeRate,fdApplyMoney);
    		var fdBudgetRate = $("[name='fdDetailList_Form["+index+"].fdBudgetRate']").val();
    		var fdInputTaxRate = $("[name='fdDetailList_Form["+index+"].fdInputTaxRate']").val();
    		var fdInputTaxMoney=$("[name='fdDetailList_Form["+index+"].fdInputTaxMoney']").val();
    		var fdNoTaxMoney = $("[name='fdDetailList_Form["+index+"].fdNoTaxMoney']").val();
    		var fdNonDeductMoney=$("[name='fdDetailList_Form["+index+"].fdNonDeductMoney']").val();
    		var isDeduct=$("[name='fdDetailList_Form["+index+"].fdIsDeduct']").val();
    		if(!fdNonDeductMoney){
    			fdNonDeductMoney=0;
    		}
    		if(isDeduct=="true"){
    			fdNoTaxMoney = numDiv(numSub(fdApplyMoney,fdNonDeductMoney),numAdd(1,numDiv(fdInputTaxRate,100)));
         		fdNoTaxMoney = parseFloat(fdNoTaxMoney).toFixed(2);
        		$("[name='fdDetailList_Form["+index+"].fdNoTaxMoney']").val(fdNoTaxMoney);
        		$("[name='fdDetailList_Form["+index+"].fdInputTaxMoney']").val(numSub(numSub(fdApplyMoney*1,fdNonDeductMoney*1),fdNoTaxMoney*1));
    		}else{
    			$("[name='fdDetailList_Form["+index+"].fdNoTaxMoney']").val(fdApplyMoney);
    		}
    		
    		$("[name='fdDetailList_Form["+index+"].fdStandardMoney']").val(fdStandardMoney);
    		$("[name='fdDetailList_Form["+index+"].fdApprovedStandardMoney']").val(fdStandardMoney);
    		var fdBudgetMoney = multiPoint(fdBudgetRate,fdApplyMoney);
    		var fdDeduFlag=$("[name='fdDeduFlag']").val();
    		var fdNoTaxMoney = $("[name='fdDetailList_Form["+index+"].fdNoTaxMoney']").val();
    		if(fdBudgetRate){
    			if("2"==fdDeduFlag&&fdNoTaxMoney){  //不含税金额
    				fdBudgetMoney = multiPoint(fdNoTaxMoney,fdBudgetRate);
    			}else{
    				fdBudgetMoney = multiPoint(fdApplyMoney,fdBudgetRate);
    			}
    		}
    		$("[name='fdDetailList_Form["+index+"].fdBudgetMoney']").val(fdBudgetMoney);
    		$("[id='_xform_fdDetailList_Form["+index+"].fdStandardMoney']").html(fdStandardMoney)
    		var fdTotalApprovedMoney = 0;
    		$("[name$=fdApprovedApplyMoney]").each(function(i){
    			var rate = $("[name='fdDetailList_Form["+i+"].fdExchangeRate']").val()
    			fdTotalApprovedMoney = addPoint(fdTotalApprovedMoney,multiPoint(this.value*1,rate));
    		})
    		$("[name=fdTotalApprovedMoney]").val(fdTotalApprovedMoney);
    		$("#fdTotalApprovedMoney").html(fdTotalApprovedMoney);
    		if($("#fdTotalApprovedUpperMoney").length==1){
    			$("#fdTotalApprovedUpperMoney").html(FSSC_MenoyToUppercase($("[name=fdTotalApprovedMoney]").val()));
    		}
    		var len = $("#TABLE_DocList_fdAccountsList_Form>tbody>tr:gt(0)").length;
    		if(len==1){//如果只有一行收款账户，自动计算总金额
    			var tr = $("#TABLE_DocList_fdAccountsList_Form>tbody>tr:eq(1)");
    			$("[name$=fdOffsetMoney]").each(function(){
    				if(!isNaN(this.value)){
    					fdTotalApprovedMoney = numSub(fdTotalApprovedMoney,this.value);
    				}
    			})
    			var fdRate = tr.find("[name$=fdExchangeRate]").val()*1;
    			if(!isNaN(fdRate)&&fdRate!=0){
    				tr.find("[name$=fdMoney]").val(divPoint(fdTotalApprovedMoney,fdRate));
    			}
    		}
    		//重新计算摊销金额
    		FSSC_ResetAmortizeMoney();
    		//重新匹配预算
    		FSSC_MatchBudget(null,$("[name='fdDetailList_Form["+index+"].fdApprovedApplyMoney']").get(0));
    		//重新匹配标准
    		FSSC_MathStandard(index);
    		FSSC_MatchProvsion(index);
    	}
    	//重新计算摊销金额
    	window.FSSC_ResetAmortizeMoney = function(){
    		if("${fsscExpenseMainForm.fdIsAmortize}"!='true'){
    			return;
    		}
    		var fdAmortizeMoney = $("[name=fdTotalApprovedMoney]").val(); 
    		$("[name=fdAmortizeMoney]").val(fdAmortizeMoney);
    		var use = 0,mon=0,percent=0,len = $("[name$=fdPercent]").length;
    		for(var i=0;i<len;i++){
    			if(i==len-1){
    				$("[name='fdAmortizeList_Form["+i+"].fdMoney']").val(parseFloat(numSub(fdAmortizeMoney,use)).toFixed(2));
    			}else{
    				percent = $("[name='fdAmortizeList_Form["+i+"].fdPercent']").val()*1;
    				mon = numMulti(fdAmortizeMoney,percent);
    				mon = divPoint(mon,100);
    				$("[name='fdAmortizeList_Form["+i+"].fdMoney']").val(mon);
    			}
    			use = numAdd(use,mon);
    		}
    	}
    	//修改汇率时同步修改金额、收款明细中的汇率、金额
    	window.FSSC_ChangeExchangeRate = function(v,e){
    		if(!$("[name=checkVersion]").val()){
    			return;
    		}
    		var index = DocListFunc_GetParentByTagName("TR");
    		index = index.rowIndex - 1;
    		var fdCompanyId = $("[name=fdCompanyId]").val();
    		var data = new KMSSData();
    		data.AddBeanData("eopBasedataCompanyService&authCurrent=true&type=isCurrencySame&fdCompanyId="+fdCompanyId);
    		data = data.GetHashMapArray();
    		data = data&&data.length>0?data[0].result:'false';
    		//同步汇率
    		var fdCurrencyId = $("[name='fdDetailList_Form["+index+"].fdCurrencyId']").val();
    		$("[name$='.fdCurrencyId'][value="+fdCurrencyId+"]").each(function(){
    			var TR = DocListFunc_GetParentByTagName("TR",this);
    			$(TR).find("[name$=fdExchangeRate]").val(v);
    			//如果公司的本位币和预算币是一样的，同步更新预算汇率
    			if(data=='true'){
    				$(TR).find("[name$='fdBudgetRate']").val(v);
    			}
    			var rate = $(TR).find("[name$='fdBudgetRate']").val()
    			var mon = $(TR).find("[name$='fdApprovedApplyMoney']").val()  //核准金额
    			if(!rate||!mon){
    				return;
    			}
    			$(TR).find("[name$='fdBudgetMoney']").val(multiPoint(rate,mon));
    			$(TR).find("[name$='fdApprovedStandardMoney']").val(multiPoint(v,mon)); //同步设置核准本币金额
    		});
    		//重新计算报销金额 
    		$("[name$=fdApplyMoney]").each(function(i){
    			FSSC_ChangeMoney(v,null,i);
    		});
    	}
    	//改变是否抵扣时自动计算进项税额
    	window.FSSC_ChangeIsDeductView= function(v,e){
    		var index = DocListFunc_GetParentByTagName("TR");
    		index = index.rowIndex - 1;
    		var fdApplyMoney = $("[name='fdDetailList_Form["+index+"].fdApprovedApplyMoney']").val();
    		var fdNonDeductMoney=$("[name='fdDetailList_Form["+index+"].fdNonDeductMoney']").val();
    		if(!fdNonDeductMoney){
    			fdNonDeductMoney=0;
    		}
    		if(v){
    			$("[id='_xform_fdDetailList_Form["+index+"].fdInputTaxMoney']").show();
    			$("[id='_xform_fdDetailList_Form["+index+"].fdInputTaxRate']").show();
    			var data = new KMSSData();
    			data = data.AddBeanData("eopBasedataInputTaxService&authCurrent=true&fdExpenseItemId="+$("[name='fdDetailList_Form["+index+"].fdExpenseItemId']").val());
    			data = data.GetHashMapArray();
    			if(data&&data.length>0){
    				
    				var fdInputTaxRate=$("[name='fdDetailList_Form["+index+"].fdInputTaxRate']").val();
    				var fdInputTaxMoney = $("[name='fdDetailList_Form["+index+"].fdInputTaxMoney']").val()*1;
    		 		var fdNoTaxMoney = $("[name='fdDetailList_Form["+index+"].fdNoTaxMoney']").val()*1;
    		 		var fdApplyMoney = $("[name='fdDetailList_Form["+index+"].fdApprovedApplyMoney']").val()*1;
    		 		//不含税额=发票金额/(1+税率)
    		 		fdNoTaxMoney = numDiv(numSub(fdApplyMoney,fdNonDeductMoney),numAdd(1,numDiv(fdInputTaxRate,100)));
    		 		fdNoTaxMoney = parseFloat(fdNoTaxMoney).toFixed(2);
    		 		$("[name='fdDetailList_Form["+index+"].fdInputTaxMoney']").val(numSub(numSub(fdApplyMoney*1,fdNonDeductMoney*1),fdNoTaxMoney*1));
    		 		$("[name='fdDetailList_Form["+index+"].fdNoTaxMoney']").val(fdNoTaxMoney);
    			}
    		}else{
    			
    			$("[id='_xform_fdDetailList_Form["+index+"].fdInputTaxMoney']").hide();
    			$("[id='_xform_fdDetailList_Form["+index+"].fdInputTaxRate']").hide();
    			$("[name='fdDetailList_Form["+index+"].fdInputTaxMoney']").val(0);
    			$("[name='fdDetailList_Form["+index+"].fdNoTaxMoney']").val(fdApplyMoney);
    		}
    		var fdBudgetMoney = "";
    		var fdDeduFlag=$("[name='fdDeduFlag']").val();
    		var fdBudgetRate = $("[name='fdDetailList_Form["+index+"].fdBudgetRate']").val();
    		var fdNoTaxMoney = $("[name='fdDetailList_Form["+index+"].fdNoTaxMoney']").val();
    		if(fdBudgetRate){
    			if("2"==fdDeduFlag&&fdNoTaxMoney){  //不含税金额
    				fdBudgetMoney = multiPoint(fdNoTaxMoney,fdBudgetRate);
    			}else{
    				fdBudgetMoney = multiPoint(fdApplyMoney,fdBudgetRate);
    			}
    		}
    		$("[name='fdDetailList_Form["+index+"].fdBudgetMoney']").val(fdBudgetMoney);
    	}
    	
    	
    	 /*********************************************
   	  * 修改进项税额，重新计算不含税金额
   	  *********************************************/
   	 window.FSSC_CalculateNoTaxMoneyView=function(val,obj){
   		 var index = obj.name.replace(/\S+\[(\d+)\]\S+/g,'$1');
   		 var fdApplyMoney=$("[name='fdDetailList_Form["+index+"].fdApprovedApplyMoney']").val();  //申请金额
   		 var fdInputTaxMoney=$("[name='fdDetailList_Form["+index+"].fdInputTaxMoney']").val();  //进项税额
   		var fdNonDeductMoney=$("[name='fdDetailList_Form["+index+"].fdNonDeductMoney']").val();  //不可抵扣金额
   	    if(!fdNonDeductMoney){
		 fdNonDeductMoney=0;
	    }
   		 if(fdApplyMoney&&fdInputTaxMoney){
   			$("[name='fdDetailList_Form["+index+"].fdNoTaxMoney']").val(numSub(subPoint(fdApplyMoney,fdNonDeductMoney),fdInputTaxMoney));  //不含税金额
   			var fdBudgetMoney = "";
   			var fdDeduFlag=$("[name='fdDeduFlag']").val();
   			var fdBudgetRate = $("[name='fdDetailList_Form["+index+"].fdBudgetRate']").val();
   			var fdNoTaxMoney = $("[name='fdDetailList_Form["+index+"].fdNoTaxMoney']").val();
   			if(fdBudgetRate){
   				if("2"==fdDeduFlag&&fdNoTaxMoney){  //不含税金额
   					fdBudgetMoney = multiPoint(fdNoTaxMoney,fdBudgetRate);
   				}else{
   					fdBudgetMoney = multiPoint(fdApplyMoney,fdBudgetRate);
   				}
   			}
   			$("[name='fdDetailList_Form["+index+"].fdBudgetMoney']").val(fdBudgetMoney);
   			if("2"==fdDeduFlag){
   				FSSC_MatchBudget(null,$("[name='fdDetailList_Form["+index+"].fdApplyMoney']").get(0));  //预算匹配
   			}
   		 }
   	 }
   	 //费用明细选择税率
  	window.FSSC_SelectInputTaxRateView=function(index){
		//var index = DocListFunc_GetParentByTagName('TR').rowIndex-1;
		var fdCompanyId = $("[name='fdDetailList_Form["+index+"].fdCompanyId']").val();
		var fdExpenseItemId=$("[name='fdDetailList_Form["+index+"].fdExpenseItemId']").val();
		if(!fdCompanyId){
			dialog.alert(lang['tips.pleaseSelectCompany']);
			return;
		}
		if(!fdExpenseItemId){
			dialog.alert(lang['tips.pleaseSelectExpenseCategory']);
			return;
		}
		$("[name='fdDetailList_Form["+index+"].fdInputTaxRate']").attr("subject",lang['fsscExpenseDetail.fdInputTaxRate']);
		dialogSelect(false,'eop_basedata_input_tax_getInputTax','fdDetailList_Form['+index+'].fdInputTaxRateId','fdDetailList_Form['+index+'].fdInputTaxRate',null,{fdCompanyId:fdCompanyId,fdExpenseItemId:fdExpenseItemId},function(rtn){
			if(!rtn){
				return;
			}
			var rate = rtn[0].fdTaxRate.replace("%","");
			$("[name='fdDetailList_Form["+index+"].fdInputTaxRate']").val(rate);
			//计算税额、不含税额
			FSSC_selectTaxMoney(index);
			var fdBudgetMoney = "";
			var fdDeduFlag=$("[name='fdDeduFlag']").val();
			var fdBudgetRate = $("[name='fdDetailList_Form["+index+"].fdBudgetRate']").val();
			var fdNoTaxMoney = $("[name='fdDetailList_Form["+index+"].fdNoTaxMoney']").val();
			var fdApplyMoney = $("input[name='fdDetailList_Form["+index+"].fdApprovedApplyMoney']").val();
			if(fdBudgetRate){
				if("2"==fdDeduFlag&&fdNoTaxMoney){  //不含税金额
					fdBudgetMoney = multiPoint(fdNoTaxMoney,fdBudgetRate);
				}else{
					fdBudgetMoney = multiPoint(fdApplyMoney,fdBudgetRate);
				}
			}
			$("[name='fdDetailList_Form["+index+"].fdBudgetMoney']").val(fdBudgetMoney);
		});
	
  	}	
  	
  	/* window.FSSC_selectTaxMoney = function(index){ 		
  		var fdInputTaxRate = $("[name='fdDetailList_Form["+index+"].fdInputTaxRate']").val().replace("%","");
  		//计算税额、不含税额
  		var fdNonDeductMoney=$("[name='fdDetailList_Form["+index+"].fdNonDeductMoney']").val()*1;
  		 if(!fdNonDeductMoney){
			 fdNonDeductMoney=0;
		 }
  		var fdInputTaxMoney = $("[name='fdDetailList_Form["+index+"].fdInputTaxMoney']").val()*1;
  		var fdNoTaxMoney = $("[name='fdDetailList_Form["+index+"].fdNoTaxMoney']").val()*1;
  		var fdApplyMoney = $("[name='fdDetailList_Form["+index+"].fdApprovedApplyMoney']").val()*1;
  		//不含税额=发票金额/(1+税率)
  		fdNoTaxMoney = numDiv(numSub(fdApplyMoney,fdNonDeductMoney),numAdd(1,numDiv(fdInputTaxRate,100)));
  		fdNoTaxMoney = parseFloat(fdNoTaxMoney).toFixed(2);
  		$("[name='fdDetailList_Form["+index+"].fdInputTaxMoney']").val(numSub(numSub(fdApplyMoney*1,fdNonDeductMoney*1),fdNoTaxMoney*1));
  		$("[name='fdDetailList_Form["+index+"].fdNoTaxMoney']").val(fdNoTaxMoney);
  	} */
  	
  	window.FSSC_selectTaxMoney = function(index){ 	
  		var fdInputTaxRateSelect=$("select[name='fdDetailList_Form["+index+"].fdInputTaxRate_select']").val();
  		$("[name='fdDetailList_Form["+index+"].fdInputTaxRate']").val(fdInputTaxRateSelect);
  		var fdInputTaxRate = $("[name='fdDetailList_Form["+index+"].fdInputTaxRate']").val();
  		//计算税额、不含税额
  		var fdNonDeductMoney=$("[name='fdDetailList_Form["+index+"].fdNonDeductMoney']").val()*1;
  		if(!fdNonDeductMoney){
  			fdNonDeductMoney=0;
  		}
  		var fdInputTaxMoney = $("[name='fdDetailList_Form["+index+"].fdInputTaxMoney']").val()*1;
  		var fdNoTaxMoney = $("[name='fdDetailList_Form["+index+"].fdNoTaxMoney']").val()*1;
  		var fdApplyMoney = $("[name='fdDetailList_Form["+index+"].fdApplyMoney']").val()*1;
  		//不含税额=发票金额/(1+税率)
  		fdNoTaxMoney = numDiv(numSub(fdApplyMoney,fdNonDeductMoney),numAdd(1,numDiv(fdInputTaxRate,100)));
  		fdNoTaxMoney = parseFloat(fdNoTaxMoney).toFixed(2);
  		$("[name='fdDetailList_Form["+index+"].fdInputTaxMoney']").val(numSub(numSub(fdApplyMoney*1,fdNonDeductMoney*1),fdNoTaxMoney*1));
  		$("[name='fdDetailList_Form["+index+"].fdNoTaxMoney']").val(fdNoTaxMoney);
  		//重新计算fdBudgetMoney
  		var fdBudgetMoney="";
  		var fdBudgetRate=$("[name='fdDetailList_Form["+index+"].fdBudgetRate']").val();
  		var fdDeduFlag=$("[name='fdDeduFlag']").val();
		if(fdBudgetRate){
			if("2"==fdDeduFlag&&fdNoTaxMoney){  //不含税金额
				fdBudgetMoney = multiPoint(fdNoTaxMoney,fdBudgetRate);
			}else if(fdApplyMoney){
				fdBudgetMoney = multiPoint(fdApplyMoney,fdBudgetRate);
			}
		}
		$("[name='fdDetailList_Form["+index+"].fdBudgetMoney']").val(fdBudgetMoney);
  	}
  	
  	
  	 /*********************************************
	  * 修改不可抵扣金额，重新计算可抵扣金额和不含税金额
	  *********************************************/
	 window.FSSC_CalculateMoneyView=function(val,obj){
		 var index = obj.name.replace(/\S+\[(\d+)\]\S+/g,'$1');
		 var fdInputTaxRate=$("[name='fdDetailList_Form["+index+"].fdInputTaxRate']").val();  //进项税率
		 var fdApplyMoney=$("[name='fdDetailList_Form["+index+"].fdApprovedApplyMoney']").val();  //申请金额
		 var fdNonDeductMoney=$("[name='fdDetailList_Form["+index+"].fdNonDeductMoney']").val();  //不可抵扣金额
		 var idDeduct=$("[name='fdDetailList_Form["+index+"].fdIsDeduct']").val();  //不可抵扣金额
		 if(!fdNonDeductMoney){
			 fdNonDeductMoney=0;
		 }
		 if(fdApplyMoney&&idDeduct=="true"){
			 fdNoTaxMoney = numDiv(numSub(fdApplyMoney,fdNonDeductMoney),numAdd(1,numDiv(fdInputTaxRate,100)));
		 	 fdNoTaxMoney = parseFloat(fdNoTaxMoney).toFixed(2);
		 	 $("[name='fdDetailList_Form["+index+"].fdInputTaxMoney']").val(numSub(numSub(fdApplyMoney*1,fdNonDeductMoney*1),fdNoTaxMoney*1));
		 	 $("[name='fdDetailList_Form["+index+"].fdNoTaxMoney']").val(fdNoTaxMoney);
		 }else{
			 $("[name='fdDetailList_Form["+index+"].fdNoTaxMoney']").val(fdApplyMoney);
		 }
	 }
  	 
	 window.FSSC_ShowProappInfo = function(data,i,param){
			var pass = true;
			var fdBudgetShowType = $("[name=fdBudgetShowType]").val();
			var div = $("#TABLE_DocList_fdDetailList_Form>tbody>tr:gt(0)").eq(i).find("[id^=proapp_status]");
			if(data){
				fdProappStatus = "1";
				var fdMoney = data.fdUsableMoney;
				var fdUsableMoney = data.fdUsableMoney;
				$("#TABLE_DocList_fdDetailList_Form>tbody>tr:gt(0)").each(function(k){
					if(k>i)return;
					var object = {
						fdExpenseItemId : $(this).find("[name$=fdExpenseItemId]").val(),
						fdCostCenterId : $(this).find("[name$=fdCostCenterId]").val(),
						fdProjectId : $("[name$=fdProjectId]").val(),
						fdWbsId : $(this).find("[name$=fdWbsId]").val(),
						fdInnerOrderId : $(this).find("[name$=fdInnerOrderId]").val()
					}
					for(var v in object){
						if(object[v]!=param[v])return;
					}
					//待审编辑主文档，判断预算需加回已经占用的预算
					var fdBudgetMoneyOld=$(this).find("[name$=fdBudgetMoneyOld]").val();
					if(!fdBudgetMoneyOld){
						fdBudgetMoneyOld=0;
					}
					if(docStatus=='20'){
						fdMoney = numSub(numAdd(fdMoney,fdBudgetMoneyOld),$(this).find("[name$=fdBudgetMoney]").val()*1);
					}else{
						fdMoney = numSub(fdMoney,$(this).find("[name$=fdBudgetMoney]").val()*1);
					}
				})
				if(fdMoney<0){
					fdProappStatus = "2";
					pass = false;
				}
				$("[name$='["+i+"].fdProappInfo']").val(JSON.stringify(data).replace(/\"/g,"'"));
				$("[name$='["+i+"].fdProappStatus']").val(fdProappStatus);
				if(fdBudgetShowType=='1'){//显示图标
					div.attr("class","budget_container");
					div.addClass("budget_status_"+fdProappStatus);
					div.attr("title",lang['py.proapp.'+fdProappStatus]);
				}else{//显示金额
					div.html(lang['py.money.total']+data.fdTotalMoney+"<br>"+lang['py.money.using']+data.fdUsingMoney+"<br>"+lang['py.money.used']+data.fdUsedMoney+"<br>"+lang['py.money.usable']+"<span class='budget_money_"+index+"'>"+data.fdUsableMoney+"</span>");
					$(".budget_money_"+index).css("color",fdProappStatus=='2'?"red":"#333");
				}
			}else{
				if(fdBudgetShowType=='1'){//显示图标
					div.attr("class","budget_container");
					div.addClass("budget_status_0");
					div.attr("title",lang['py.budget.0']);
				}else{//显示金额
					div.html(lang['py.money.total']+"0<br>"+lang['py.money.using']+"0<br>"+lang['py.money.used']+"0<br>"+lang['py.money.usable']+"0");
				}
				$("[name$='["+i+"].fdFeeInfo']").val('{}');
				$("[name$='["+i+"].fdFeeStatus']").val('0');
			}
			return pass;
		}
    })
</script>
<br/>
<style>
	.budget_container{width:12px;height:12px;border-radius:50%;text-align:left;margin-left:40%;margin-right:40%; }
	@-webkit-keyframes outerlight{
		0%{box-shadow:0 0 7px #333;}
		25%{box-shadow:0 0 14px #333;}
		50%:{box-shadow:0 0 21px #333;}
		75%{box-shadow:0 0 14px #333;}
		100%{box-shadow:0 0 7px #333;}
	}
	.budget_status_0{background:#CDCDCD;}
	.budget_status_1{background:#6AA237;}
	.budget_status_2{background:#C94739;}
	.standard_status_0{display:none;}
	.standard_status_1{background:#6AA237;}
	.standard_status_2{background:#C94739;}
</style>
