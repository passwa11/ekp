<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<%@ taglib uri="/WEB-INF/KmssConfig/eop/basedata/fssc.tld" prefix="fssc"%>
<ui:content title="${lfn:message('fssc-expense:table.fsscExpenseDetail') }" expand="true">
<div class="btn_container">

   <%--  <kmss:ifModuleExist path="/fssc/ccard">
    <div class="fssc_expense_btn" onclick="FSSC_SelectTranData()">
        <span class="iconfont icon-tianjia"></span>
            ${lfn:message('fssc-expense:button.addTranData') }
    </div>
    <input type="hidden" name="fdTranDataId"/>
    <input type="hidden" name="fdTranDataName"/>
    </kmss:ifModuleExist> --%>
	<div class="fssc_expense_btn" onclick="FSSC_AddExpenseDetail()">
	<span class="iconfont icon-tianjia"></span>
		${lfn:message('fssc-expense:button.addExpense') }
	</div>
	<%-- fssc:checkVersion version="true">
	<kmss:ifModuleExist path="/fssc/mobile">
	<div class="fssc_expense_btn" onclick="FSSC_SelectNote()">
	<span class="iconfont icon-tianjia"></span>
		${lfn:message('fssc-expense:fsscExpenseDetail.selectNote.button') }
	</div>
	<input type="hidden" name="fdNoteIds"/>
	<input type="hidden" name="fdNoteNames"/>
	</kmss:ifModuleExist>
	</fssc:checkVersion>
	<div class="fssc_expense_btn" onclick="FSSC_AddInvoice()">
	<span class="iconfont icon-tianjia"></span>
		${lfn:message('fssc-expense:button.addInvoice') }
	</div> --%>
</div>
<div style="min-width:980px;overflow:scroll;width:100%;" id="detail_div_for_length">
<table class="tb_normal feetable"   id="TABLE_DocList_fdDetailList_Form" align="center">
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
        <td  style="width:100px">
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
        <td  style="width:120px">
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
        
        <td  style="width:120px">
            ${lfn:message('fssc-expense:fsscExpenseDetail.fdUse')}
        </td>
        <c:if test="${docTemplate.fdExpenseType eq '2' and docTemplate.fdIsTravelAlone eq 'true' }">
        <td  style="width:90px">
			${lfn:message('fssc-expense:fsscExpenseDetail.fdTravel')}
        </td>
        </c:if>
        <fssc:ifModuleExists path="/fssc/budget/">
            <fssc:switchOn property="fdIsBudget" defaultValue="1">
        <td  style="width:80px">
        	${lfn:message('fssc-expense:fsscExpenseDetail.fdBudgetStatus')}
        </td>
            </fssc:switchOn>
        </fssc:ifModuleExists>
        <td  style="width:80px">
            ${lfn:message('fssc-expense:py.status')}
        </td>
        <td style="width:40px;">
        	
        </td>
    </tr>
    <tr KMSS_IsReferRow="1" style="display:none;">
        <td align="center" KMSS_IsRowIndex="1">
            !{index}
        </td>
        <c:if test="${docTemplate.fdAllocType=='2' }">
        <td align="center">
            <div id="_xform_fdDetailList_Form[!{index}].fdCostCenterId" _xform_type="dialog">
                <xform:dialog propertyId="fdDetailList_Form[!{index}].fdCostCenterId" propertyName="fdDetailList_Form[!{index}].fdCostCenterName"  required="true" subject="${lfn:message('fssc-expense:fsscExpenseDetail.fdCostCenter')}" style="width:90%;">
                    FSSC_SelectCostCenter();
                </xform:dialog>
            </div>
        </td>
        </c:if>
        <c:if test="${docTemplate.fdAllocType=='1' }">
        <td align="center">
            <div id="_xform_fdDetailList_Form[!{index}].fdCostCenterId" _xform_type="dialog">
            <c:set var="showType" value="edit"></c:set>
                <xform:dialog propertyId="fdDetailList_Form[!{index}].fdCostCenterId" propertyName="fdDetailList_Form[!{index}].fdCostCenterName" useNewStyle="false" showStatus="${showType }" required="true" subject="${lfn:message('fssc-expense:fsscExpenseDetail.fdCostCenter')}" style="width:90%;">
                    FSSC_SelectCostCenter();
                </xform:dialog>
            </div>
        </td>
        </c:if>
        <td align="center">
        	<input type="hidden" name="fdDetailList_Form[!{index}].fdId" value="" />
        	<input type="hidden" name="fdDetailList_Form[!{index}].fdNoteId" value="" />
            <input type="hidden" name="fdDetailList_Form[!{index}].fdApprovedApplyMoney" value="" />
            <input type="hidden" name="fdDetailList_Form[!{index}].fdApprovedStandardMoney" value="" />
            <input type="hidden" name="fdDetailList_Form[!{index}].fdCompanyId" value="" />
            <input type="hidden" name="fdDetailList_Form[!{index}].fdBudgetInfo" value="" />
            <input type="hidden" name="fdDetailList_Form[!{index}].fdBudgetStatus" value="" />
            <input type="hidden" name="fdDetailList_Form[!{index}].fdFeeInfo" value="" />
            <input type="hidden" name="fdDetailList_Form[!{index}].fdFeeStatus" value="" />
            <input type="hidden" name="fdDetailList_Form[!{index}].fdProappInfo" value="" />
            <input type="hidden" name="fdDetailList_Form[!{index}].fdProappStatus" value="" />
            <input type="hidden" name="fdDetailList_Form[!{index}].fdStandardStatus" value="" />
            <input type="hidden" name="fdDetailList_Form[!{index}].fdStandardInfo" value="" />
            <input type="hidden" name="fdDetailList_Form[!{index}].fdBudgetMoney" value="" />
            <c:if test="${fsscExpenseMainForm.docStatus=='20'}">
           		 <input type="hidden" name="fdDetailList_Form[!{index}].fdBudgetMoneyOld" value="" />
           	</c:if>
            <input type="hidden" name="fdDetailList_Form[!{index}].fdBudgetRate" value="" />
            <input type="hidden" name="fdDetailList_Form[!{index}].fdInputTaxRate" value="" />
             <input type="hidden" name="fdDetailList_Form[!{index}].fdInputTaxRateId" value="" />
            <input type="hidden" name="fdDetailList_Form[!{index}].fdExpenseTempId" value="" />
            <input type="hidden" name="fdDetailList_Form[!{index}].fdExpenseTempDetailIds" value="" />
            <c:if test="${docTemplate.fdIsProjectShare=='false' or docTemplate.fdIsProject=='false' or empty docTemplate.fdIsProjectShare }">
            <input type="hidden" name="fdDetailList_Form[!{index}].fdProjectId" value="" />
            </c:if>
            <c:if test="${docTemplate.fdIsForeign!='true' }">
            <input type="hidden" name="fdDetailList_Form[!{index}].fdCurrencyId" value="" />
            <input type="hidden" name="fdDetailList_Form[!{index}].fdExchangeRate" value="" />
            <input type="hidden" name="fdDetailList_Form[!{index}].fdStandardMoney" value="" />
            </c:if>
            <input type="hidden" name="fdDetailList_Form[!{index}].fdProvisionMoney" value="" />
            <input type="hidden" name="fdDetailList_Form[!{index}].fdProvisionInfo" value="" />
            <input type="hidden" name="fdDetailList_Form[!{index}].fdDayCalType" value="" />
            <div id="_xform_fdDetailList_Form[!{index}].fdExpenseItemId" _xform_type="dialog">
                <xform:dialog propertyId="fdDetailList_Form[!{index}].fdExpenseItemId" propertyName="fdDetailList_Form[!{index}].fdExpenseItemName" required="true" subject="${lfn:message('fssc-expense:fsscExpenseDetail.fdExpenseItem')}" style="width:90%;">
                    FSSC_SelectExpenseItem(!{index});
                </xform:dialog>
            </div>
        </td>
        <c:if test="${docTemplate.fdIsProject=='true'&&docTemplate.fdIsProjectShare=='true'}">
        <td align="center">
            <xform:dialog propertyId="fdDetailList_Form[!{index}].fdProjectId" propertyName="fdDetailList_Form[!{index}].fdProjectName" required="true" subject="${lfn:message('fssc-expense:fsscExpenseDetail.fdProject')}" style="width:90%;">
                FSSC_SelectProjectDetail();
            </xform:dialog>
        </td>
        </c:if>
        <td align="center">
            <div id="_xform_fdDetailList_Form[!{index}].fdRealUserId" _xform_type="address">
                <xform:address propertyId="fdDetailList_Form[!{index}].fdRealUserId" propertyName="fdDetailList_Form[!{index}].fdRealUserName" onValueChange="changeRealUser" orgType="ORG_TYPE_PERSON" showStatus="edit" required="true" subject="${lfn:message('fssc-expense:fsscExpenseDetail.fdRealUser')}" style="width:85%;" />
            </div>
        </td>
        <c:if test="${fn:indexOf(docTemplate.fdExtendFields,'7')>-1 }">
        <td align="center">
            <div id="_xform_fdDetailList_Form[!{index}].fdDeptId" _xform_type="address">
                <xform:address propertyId="fdDetailList_Form[!{index}].fdDeptId" propertyName="fdDetailList_Form[!{index}].fdDeptName" onValueChange="changeDept" orgType="ORG_TYPE_DEPT" showStatus="edit"  subject="${lfn:message('fssc-expense:fsscExpenseDetail.fdDept')}"  style="width:90%;" />
            </div>
        </td>
        </c:if>
        <fssc:configEnabled property="fdFinancialSystem" value="SAP">
        <c:if test="${fn:indexOf(docTemplate.fdExtendFields,'4')>-1 }">
	        <c:if test="${docTemplate.fdIsProject=='true' }">
	        <td align="center">
	            <div id="_xform_fdDetailList_Form[!{index}].fdWbsId" _xform_type="dialog">
	                <xform:dialog propertyId="fdDetailList_Form[!{index}].fdWbsId" propertyName="fdDetailList_Form[!{index}].fdWbsName" subject="${lfn:message('fssc-expense:fsscExpenseDetail.fdWbs')}" style="width:90%;">
	                    FSSC_SelectWbsIsProject(!{index});
	                </xform:dialog>
	            </div>
	        </td>
	        </c:if>
	        <c:if test="${docTemplate.fdIsProject=='false' }">
	        <td align="center">
	            <div id="_xform_fdDetailList_Form[!{index}].fdWbsId" _xform_type="dialog">
	                <xform:dialog propertyId="fdDetailList_Form[!{index}].fdWbsId" propertyName="fdDetailList_Form[!{index}].fdWbsName" subject="${lfn:message('fssc-expense:fsscExpenseDetail.fdWbs')}" style="width:90%;">
	                    FSSC_SelectWbs(!{index});
	                </xform:dialog>
	            </div>
	        </td>
	        </c:if>
        </c:if>
        <c:if test="${fn:indexOf(docTemplate.fdExtendFields,'3')>-1 }">
        <td align="center">
            <div id="_xform_fdDetailList_Form[!{index}].fdInnerOrderId" _xform_type="dialog">
                <xform:dialog propertyId="fdDetailList_Form[!{index}].fdInnerOrderId" propertyName="fdDetailList_Form[!{index}].fdInnerOrderName" subject="${lfn:message('fssc-expense:fsscExpenseDetail.fdInnerOrder')}" style="width:90%;">
                    FSSC_SelectInnerOrder(!{index});
                </xform:dialog>
            </div>
        </td>
        </c:if>
        </fssc:configEnabled>
        <c:if test="${fn:indexOf(docTemplate.fdExtendFields,'5')>-1 }">
        <td>
       		<xform:datetime property="fdDetailList_Form[!{index}].fdStartDate" validators="checExpensekDate" onValueChange="changeDate" showStatus="edit" required="true" subject="${lfn:message('fssc-expense:fsscExpenseDetail.fdStartDate')}" style="width:85%;" dateTimeType="date"/>
       	</td>
       	</c:if>
        <td align="center">
            <div id="_xform_fdDetailList_Form[!{index}].fdHappenDate" _xform_type="text">
                <xform:datetime property="fdDetailList_Form[!{index}].fdHappenDate" validators="checExpensekDate" onValueChange="changeDate" showStatus="edit" required="true" subject="${lfn:message('fssc-expense:fsscExpenseDetail.fdHappenDate')}" style="width:85%;" dateTimeType="date"/>
            </div>
        </td>
        <c:if test="${fn:indexOf(docTemplate.fdExtendFields,'5')>-1 }">
        <td>
       		<xform:text property="fdDetailList_Form[!{index}].fdTravelDays" style="width:85%" showStatus="readOnly"></xform:text>
       	</td>
       	</c:if>
       	<c:if test="${docTemplate.fdExpenseType eq '2' and docTemplate.fdIsTravelAlone eq 'false'}">
        <td>
       	 	<xform:dialog propertyId="fdDetailList_Form[!{index}].fdStartPlaceId" required="true" propertyName="fdDetailList_Form[!{index}].fdStartPlace" subject="${lfn:message('fssc-expense:fsscExpenseDetail.fdStartPlace')}" style="width:90%;">
                FSSC_SelectPlace('fdDetailList_Form[*].fdStartPlaceId','fdDetailList_Form[*].fdStartPlace');
            </xform:dialog>
       	</td>
       	<td>
       	 	<xform:dialog propertyId="fdDetailList_Form[!{index}].fdArrivalPlaceId" required="true" propertyName="fdDetailList_Form[!{index}].fdArrivalPlace" subject="${lfn:message('fssc-expense:fsscExpenseDetail.fdArrivalPlace')}" style="width:90%;">
               FSSC_SelectPlace('fdDetailList_Form[*].fdArrivalPlaceId','fdDetailList_Form[*].fdArrivalPlace');
            </xform:dialog>
       	</td>
       	<fssc:checkVersion version="true">
       	<c:if test="${fn:indexOf(docTemplate.fdExtendFields,'6')>-1 }">
        <td>
       	 	<xform:dialog propertyId="fdDetailList_Form[!{index}].fdBerthId" propertyName="fdDetailList_Form[!{index}].fdBerthName" subject="${lfn:message('fssc-expense:fsscExpenseDetail.fdBerth')}" style="width:90%;">
                FSSC_SelectBerth('fdDetailList_Form[*].fdBerthId','fdDetailList_Form[*].fdBerthName');
            </xform:dialog>
       	</td>
       	</c:if>
       	</fssc:checkVersion>
       	</c:if>
        <c:if test="${fn:indexOf(docTemplate.fdExtendFields,'1')>-1 }">
        <td align="center">
            <div id="_xform_fdDetailList_Form[!{index}].fdPersonNumber" _xform_type="text">
                <xform:text property="fdDetailList_Form[!{index}].fdPersonNumber" required="true" validators="digits min(1)" onValueChange="changePersonNumber" subject="${lfn:message('fssc-expense:fsscExpenseDetail.fdPersonNumber')}" style="width:85%;"/>
            </div>
        </td>
        </c:if>
        <td align="center">
            <div id="_xform_fdDetailList_Form[!{index}].fdApplyMoney" _xform_type="text">
                <xform:text property="fdDetailList_Form[!{index}].fdApplyMoney" onValueChange="FSSC_ChangeMoney(this.value,this);" showStatus="edit" validators="currency-dollar" required="true" subject="${lfn:message('fssc-expense:fsscExpenseDetail.fdApplyMoney')}"  style="width:80%;" />  
            </div> 
        </td>
        <%--发票金额和进项税金额一起展示 --%>
        <c:if test="${fn:indexOf(docTemplate.fdExtendFields,'2')>-1 }">
        <td align="center">
            <div id="_xform_fdDetailList_Form[!{index}].fdInvoiceMoney" _xform_type="text">
                 <xform:text property="fdDetailList_Form[!{index}].fdInvoiceMoney" showStatus="readOnly" validators="currency-dollar" subject="${lfn:message('fssc-expense:fsscExpenseDetail.fdInvoiceMoney')}"  style="width:85%;color:#333;" /> 
              
            </div>
        </td>
        </c:if>
        <c:if test="${fn:indexOf(docTemplate.fdExtendFields,'9')>-1 }">
        <td align="center">
            <div id="_xform_fdDetailList_Form[!{index}].fdNonDeductMoney" _xform_type="text">
                <xform:text property="fdDetailList_Form[!{index}].fdNonDeductMoney" onValueChange="FSSC_CalculateMoney" showStatus="edit" validators="currency-dollar"  subject="${lfn:message('fssc-expense:fsscExpenseDetail.fdNonDeductMoney')}" value="0.00" style="width:80%;" /> 
            </div>
        </td>
        </c:if>
        <c:if test="${fn:indexOf(docTemplate.fdExtendFields,'2')>-1 }">
        <td align="center">
        	<xform:checkbox property="fdDetailList_Form[!{index}].fdIsDeduct" onValueChange="FSSC_ChangeIsDeduct">
        		<xform:simpleDataSource value="true" textKey="fsscExpenseDetail.fdIsDeduct" bundle="fssc-expense"/>
        	</xform:checkbox>
        	<!-- 选择税率 -->
        	 <div id="_xform_fdDetailList_Form[!{index}].fdInputTaxRate" _xform_type="dialog" style="display:none;" >
		                <%-- <xform:dialog propertyId="fdDetailList_Form[!{index}].fdInputTaxRateId" propertyName="fdDetailList_Form[!{index}].fdInputTaxRate" subject="${lfn:message('fssc-expense:fsscExpenseDetail.fdInputTaxRate')}" style="width:90%;">
		                    FSSC_SelectInputTaxRate(!{index});
		                </xform:dialog> --%>
<%-- 		                <input type="hidden" name="fdDetailList_Form[!{index}].fdInputTaxRate" value="${fdDetailList_FormItem.fdApprovedStandardMoney }" />
 --%>		                <select name="fdDetailList_Form[!{index}].fdInputTaxRate_select" onchange="FSSC_selectTaxMoney(!{index})">
						  <option value="0">0</option>
						  <option value="3">3</option>
						  <option value="6">6</option>
						  <option value="13">13</option>
						</select>%
		            </div>
            
             <div id="_xform_fdDetailList_Form[!{index}].fdInputTaxMoney" _xform_type="text" class="inputTax" style="display:none;">
                      <input type="text" name="fdDetailList_Form[!{index}].fdInputTaxMoney" value="<kmss:showNumber value="${fdDetailList_FormItem.fdInputTaxMoney }" pattern="0.00"/>" class="inputsgl" onchange="FSSC_CalculateNoTaxMoney(this.value,this)" showStatus="edit" validate="${fdDetailList_FormItem.fdIsDeduct=='true'?'required':''} currency-dollar" subject="${lfn:message('fssc-expense:fsscExpenseDetail.fdInputTaxMoney')}" style="width:80%;" />
        			<span class="txtstrong">*</span>
        			</div>
        </td>
        <td align="center">
            <div id="_xform_fdDetailList_Form[!{index}].fdNoTaxMoney" _xform_type="text">
                <xform:text property="fdDetailList_Form[!{index}].fdNoTaxMoney" showStatus="edit" onValueChange="FSSC_ChangeMoney(this.value,this);" validators="currency-dollar" required="true" subject="${lfn:message('fssc-expense:fsscExpenseDetail.fdNoTaxMoney')}" value="0" style="width:80%;" />
            </div>
        </td>
        </c:if>
        <c:if test="${docTemplate.fdIsForeign=='true' }">
        <td align="center">
            <div id="_xform_fdDetailList_Form[!{index}].fdCurrencyId" _xform_type="dialog">
                <xform:dialog propertyId="fdDetailList_Form[!{index}].fdCurrencyId" required="true" propertyName="fdDetailList_Form[!{index}].fdCurrencyName" showStatus="edit" style="width:85%;" subject="${lfn:message('fssc-expense:fsscExpenseDetail.fdCurrency')}">
                    FSSC_SelectCurrency(!{index});
                </xform:dialog>
                <xform:text property="fdDetailList_Form[!{index}].fdExchangeRate" onValueChange="FSSC_ChangeExchangeRate" showStatus="edit" required="true" subject="${lfn:message('fssc-expense:fsscExpenseDetail.fdExchangeRate')}" style="width:80%;" />
            </div>
        </td>
        <td align="center">
            <div id="_xform_fdDetailList_Form[!{index}].fdStandardMoney" _xform_type="text">
                <xform:text property="fdDetailList_Form[!{index}].fdStandardMoney" showStatus="readOnly" value="0" style="width:90%;" />
            </div>
            
        </td>
        </c:if>
        <td align="center">
            <div id="_xform_fdDetailList_Form[!{index}].fdUse" _xform_type="text">
                <xform:text property="fdDetailList_Form[!{index}].fdUse" showStatus="edit" style="width:90%;" />
            </div>
        </td>
        <c:if test="${docTemplate.fdExpenseType eq '2'  and docTemplate.fdIsTravelAlone eq 'true'}">
        <td>
			<select name="fdDetailList_Form[!{index}].fdTravel" validate="required" style="width:86%;" subject="${lfn:message('fssc-expense:fsscExpenseDetail.fdTravel')}">
				<option value="">${lfn:message('page.firstOption') }</option>
			</select>
			<span class="txtstrong">*</span>
        </td>
        </c:if>
        <fssc:ifModuleExists path="/fssc/budget/">
            <fssc:switchOn property="fdIsBudget" defaultValue="1">
        <td align="left">
        	<div id="buget_status_!{index}">
        	</div>
        </td>
            </fssc:switchOn>
        </fssc:ifModuleExists>
        <td align="left" class="status_other">
        	<!-- 关联事前，显示事前状态灯 -->
        	<c:if test="${docTemplate.fdIsFee=='true' }">
	        	<div id="fee_status_!{index}" class="budget_container">
	        	</div>
        	</c:if>
       	 	<!-- 关联立项，显示立项状态灯 -->
        	<c:if test="${docTemplate.fdIsProapp=='true' }">
	        	<div id="proapp_status_!{index}" class="budget_container">
	        	</div>
        	</c:if>
        	<fssc:checkVersion version="true">
        	<div id="standard_status_!{index}" class="budget_container standard_status_0">
        	</div>
        	</fssc:checkVersion>
        </td>
        <td align="center">
        	<a href="javascript:void(0);" onclick="viewInvoiceTemp();" title="${lfn:message('fssc-expense:button.viewInvoice')}">
                <img src="${LUI_ContextPath}/resource/style/common/images/small_search.png" border="0" style="width:20px;height:20px;" />
            </a>
            &nbsp;
            <a href="javascript:void(0);" onclick="cascade_DeleteInvoice();DocList_DeleteRow();FSSC_GetTotalMoney()" title="${lfn:message('doclist.delete')}">
                <img src="${KMSS_Parameter_StylePath}icons/icon_del.png" border="0" />
            </a>
        </td>
    </tr>
    <c:forEach items="${fsscExpenseMainForm.fdDetailList_Form}" var="fdDetailList_FormItem" varStatus="vstatus">
	    <c:set var="moneyMax" value=""/>
		<c:if test="${fsscExpenseMainForm.docStatus==11&&fdDetailList_FormItem.fdApprovedApplyMoney!=null }">
			<c:set var="moneyMax" value="max(${fdDetailList_FormItem.fdApprovedApplyMoney})"/>
		</c:if>
        <tr KMSS_IsContentRow="1">
            <td align="center">
                ${vstatus.index+1}
            </td>
            <c:if test="${docTemplate.fdAllocType=='2' }">
            <td align="center">
                <div id="_xform_fdDetailList_Form[${vstatus.index}].fdCostCenterId" _xform_type="dialog">
                    <xform:dialog propertyId="fdDetailList_Form[${vstatus.index}].fdCostCenterId" propertyName="fdDetailList_Form[${vstatus.index}].fdCostCenterName" showStatus="edit" required="true" subject="${lfn:message('fssc-expense:fsscExpenseDetail.fdCostCenter')}"
                    style="width:90%;">
                        FSSC_SelectCostCenter();
                    </xform:dialog>
                </div>
            </td>
            </c:if>
        <c:if test="${docTemplate.fdAllocType=='1' }">
        <td align="center">
            <div id="_xform_fdDetailList_Form[${vstatus.index}].fdCostCenterId" _xform_type="dialog">
            <c:set var="showType" value="edit"></c:set>
                <xform:dialog propertyId="fdDetailList_Form[${vstatus.index}].fdCostCenterId" propertyName="fdDetailList_Form[${vstatus.index}].fdCostCenterName" useNewStyle="false" showStatus="${showType }" required="true" subject="${lfn:message('fssc-expense:fsscExpenseDetail.fdCostCenter')}" style="width:90%;">
                    FSSC_SelectCostCenter();
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
	            <input type="hidden" name="fdDetailList_Form[${vstatus.index}].fdApprovedApplyMoney" value="${fdDetailList_FormItem.fdApprovedApplyMoney }" />
            	<input type="hidden" name="fdDetailList_Form[${vstatus.index}].fdApprovedStandardMoney" value="${fdDetailList_FormItem.fdApprovedStandardMoney }" />
                <input type="hidden" name="fdDetailList_Form[${vstatus.index}].fdId" value="${fdDetailList_FormItem.fdId }" />
                <input type="hidden" name="fdDetailList_Form[${vstatus.index}].fdNoteId" value="${fdDetailList_FormItem.fdNoteId }" />
                <input type="hidden" name="fdDetailList_Form[${vstatus.index}].fdCompanyId" value="${fdDetailList_FormItem.fdCompanyId }" />
                <input type="hidden" name="fdDetailList_Form[${vstatus.index}].fdBudgetInfo" value="${fdDetailList_FormItem.fdBudgetInfo }" />
            	<input type="hidden" name="fdDetailList_Form[${vstatus.index}].fdBudgetStatus" value="${fdDetailList_FormItem.fdBudgetStatus }" />
            	<input type="hidden" name="fdDetailList_Form[${vstatus.index}].fdFeeInfo" value="${fdDetailList_FormItem.fdFeeInfo }" />
            	<input type="hidden" name="fdDetailList_Form[${vstatus.index}].fdFeeStatus" value="${fdDetailList_FormItem.fdFeeStatus }" />
            	<input type="hidden" name="fdDetailList_Form[${vstatus.index}].fdProappInfo" value="${fdDetailList_FormItem.fdProappInfo }" />
            	<input type="hidden" name="fdDetailList_Form[${vstatus.index}].fdProappStatus" value="${fdDetailList_FormItem.fdProappStatus }" />
            	<input type="hidden" name="fdDetailList_Form[${vstatus.index}].fdStandardStatus" value="${fdDetailList_FormItem.fdStandardStatus }" />
            	<input type="hidden" name="fdDetailList_Form[${vstatus.index}].fdStandardInfo" value="${fdDetailList_FormItem.fdStandardInfo }" />
            	<input type="hidden" name="fdDetailList_Form[${vstatus.index}].fdBudgetMoney" value="${fdDetailList_FormItem.fdBudgetMoney }" />
            	<c:if test="${fsscExpenseMainForm.docStatus=='20'}">
            		 <input type="hidden" name="fdDetailList_Form[${vstatus.index}].fdBudgetMoneyOld" value="${fdDetailList_FormItem.fdBudgetMoney }" />
            	</c:if>
            	<input type="hidden" name="fdDetailList_Form[${vstatus.index}].fdBudgetRate" value="${fdDetailList_FormItem.fdBudgetRate}" />
            	<input type="hidden" name="fdDetailList_Form[${vstatus.index}].fdInputTaxRate" value="${fdDetailList_FormItem.fdInputTaxRate}" />
            	<xform:text showStatus="noShow" property="fdDetailList_Form[${vstatus.index}].fdInputTaxRateId"  />
            	<input type="hidden" name="fdDetailList_Form[${vstatus.index}].fdExpenseTempId" value="${fdDetailList_FormItem.fdExpenseTempId }" />
            	<input type="hidden" name="fdDetailList_Form[${vstatus.index}].fdExpenseTempDetailIds" value="${fdDetailList_FormItem.fdExpenseTempDetailIds }" />
	            <c:if test="${docTemplate.fdIsProjectShare=='false' or docTemplate.fdIsProject=='false'  or empty docTemplate.fdIsProjectShare}">
	           		<input type="hidden" name="fdDetailList_Form[${vstatus.index}].fdProjectId" value="${fdDetailList_FormItem.fdProjectId }" />
	           	</c:if>
	            <c:if test="${docTemplate.fdIsForeign!='true' }">
	            <input type="hidden" name="fdDetailList_Form[${vstatus.index}].fdCurrencyId" value="${fdDetailList_FormItem.fdCurrencyId }" />
	            <input type="hidden" name="fdDetailList_Form[${vstatus.index}].fdExchangeRate" value="${fdDetailList_FormItem.fdExchangeRate }" />
	            <input type="hidden" name="fdDetailList_Form[${vstatus.index}].fdStandardMoney" value="${fdDetailList_FormItem.fdStandardMoney }" />
	            </c:if>
	            <input type="hidden" name="fdDetailList_Form[${vstatus.index}].fdProvisionMoney" value="${fdDetailList_FormItem.fdProvisionMoney }" />
            	<input type="hidden" name="fdDetailList_Form[${vstatus.index}].fdProvisionInfo" value='${fdDetailList_FormItem.fdProvisionInfo }' />
                <input type="hidden" name="fdDetailList_Form[${vstatus.index}].fdDayCalType" value='${fdDetailList_FormItem.fdDayCalType }' />
	        </td>
	        <c:if test="${docTemplate.fdIsProject=='true'&&docTemplate.fdIsProjectShare=='true'}">
        	<td align="center">
	            <xform:dialog propertyId="fdDetailList_Form[${vstatus.index}].fdProjectId" propertyName="fdDetailList_Form[${vstatus.index}].fdProjectName" required="true" subject="${lfn:message('fssc-expense:fsscExpenseDetail.fdProject')}" style="width:90%;">
	                FSSC_SelectProjectDetail();
	            </xform:dialog>
	        </td>
	        </c:if>
            <td align="center">
                <div id="_xform_fdDetailList_Form[${vstatus.index}].fdRealUserId" _xform_type="address">
                    <xform:address propertyId="fdDetailList_Form[${vstatus.index}].fdRealUserId" propertyName="fdDetailList_Form[${vstatus.index}].fdRealUserName" onValueChange="changeRealUser" orgType="ORG_TYPE_PERSON" showStatus="edit" required="true" subject="${lfn:message('fssc-expense:fsscExpenseDetail.fdRealUser')}"
                    style="width:90%;" />
                </div>
            </td>
            <c:if test="${fn:indexOf(docTemplate.fdExtendFields,'7')>-1 }">
            <td align="center">
	            <div id="_xform_fdDetailList_Form[!{index}].fdDeptId" _xform_type="address">
	                <xform:address propertyId="fdDetailList_Form[${vstatus.index}].fdDeptId" propertyName="fdDetailList_Form[${vstatus.index}].fdDeptName" onValueChange="changeDept" orgType="ORG_TYPE_DEPT" showStatus="edit" subject="${lfn:message('fssc-expense:fsscExpenseDetail.fdDept')}"
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
	       		<xform:datetime property="fdDetailList_Form[${vstatus.index}].fdStartDate" showStatus="edit" validators="checExpensekDate" onValueChange="changeDate" required="true" subject="${lfn:message('fssc-expense:fsscExpenseDetail.fdStartDate')}" style="width:85%;" dateTimeType="date"/>
	       	</td>
	       	</c:if>
            <td align="center">
	            <div id="_xform_fdDetailList_Form[!{index}].fdHappenDate" _xform_type="text">
	                <xform:datetime property="fdDetailList_Form[${vstatus.index}].fdHappenDate" showStatus="edit" validators="checExpensekDate" onValueChange="changeDate" required="true" subject="${lfn:message('fssc-expense:fsscExpenseDetail.fdHappenDate')}" style="width:85%;" dateTimeType="date"/>
	            </div>
	        </td>
	        <c:if test="${fn:indexOf(docTemplate.fdExtendFields,'5')>-1 }">
	        <td>
	       		<xform:text property="fdDetailList_Form[${vstatus.index}].fdTravelDays" style="width:85%" showStatus="readOnly"></xform:text>
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
	       	 	<xform:dialog propertyId="fdDetailList_Form[${vstatus.index}].fdBerthId" propertyName="fdDetailList_Form[${vstatus.index}].fdBerthName" subject="${lfn:message('fssc-expense:fsscExpenseDetail.fdBerth')}" style="width:90%;">
	                FSSC_SelectBerth('fdDetailList_Form[*].fdBerthId','fdDetailList_Form[*].fdBerthName');
	            </xform:dialog>
	       	</td>
	       	</c:if>
	       	</fssc:checkVersion>
	       	</c:if>
	        <c:if test="${fn:indexOf(docTemplate.fdExtendFields,'1')>-1 }">
	        <td align="center">
	            <div id="_xform_fdDetailList_Form[${vstatus.index}].fdPersonNumber" _xform_type="text">
	                <xform:text property="fdDetailList_Form[${vstatus.index}].fdPersonNumber" required="true" validators="digits min(1)" onValueChange="changePersonNumber" subject="${lfn:message('fssc-expense:fsscExpenseDetail.fdPersonNumber')}" style="width:85%;"/>
	            </div>
	        </td>
	        </c:if>
            <td align="center">
                 <div id="_xform_fdDetailList_Form[${vstatus.index}].fdApplyMoney" _xform_type="text">
                     <input type="text" name="fdDetailList_Form[${vstatus.index}].fdApplyMoney" value="<kmss:showNumber value="${fdDetailList_FormItem.fdApplyMoney }" pattern="0.00"/>" class="inputsgl" validate="required currency-dollay required ${moneyMax }"  onblur="FSSC_ChangeMoney(this.value,this);"  subject="${lfn:message('fssc-expense:fsscExpenseDetail.fdApplyMoney')}" style="width:90%;"/>
                </div> 
            </td>
            <%--发票金额和进项税金额一起展示 --%>
        	<c:if test="${fn:indexOf(docTemplate.fdExtendFields,'2')>-1 }">
            <td align="center">
	            <div id="_xform_fdDetailList_Form[${vstatus.index}].fdInvoiceMoney" _xform_type="text">
	                <input type="text" readOnly="readOnly" class="inputsgl"  name="fdDetailList_Form[${vstatus.index}].fdInvoiceMoney"  value="<kmss:showNumber  value="${fdDetailList_FormItem.fdInvoiceMoney }" pattern="0.00"/>" validate="currency-dollar" subject="${lfn:message('fssc-expense:fsscExpenseDetail.fdInvoiceMoney')}"  style="width:85%;"/>
	            </div>
	        </td>
	        </c:if>
	        <c:if test="${fn:indexOf(docTemplate.fdExtendFields,'9')>-1 }">
	        <td align="center">
	            <div id="_xform_fdDetailList_Form[${vstatus.index}].fdNonDeductMoney" _xform_type="text">
	                  <input type="text" class="inputsgl"  value="<kmss:showNumber value="${fdDetailList_FormItem.fdNonDeductMoney }" pattern="0.00"/>" validate="currency-dollar"  name="fdDetailList_Form[${vstatus.index}].fdNonDeductMoney" onchange="FSSC_CalculateMoney(this.value,this)" subject="${lfn:message('fssc-expense:fsscExpenseDetail.fdNonDeductMoney')}"  style="width:85%;"/>
	            </div>
	        </td>
	        </c:if>
             <c:if test="${fn:indexOf(docTemplate.fdExtendFields,'2')>-1 }">
	        <td align="center">
	        	<xform:checkbox property="fdDetailList_Form[${vstatus.index}].fdIsDeduct" onValueChange="FSSC_ChangeIsDeduct">
	        		<xform:simpleDataSource value="true" textKey="fsscExpenseDetail.fdIsDeduct" bundle="fssc-expense"/>
	        	</xform:checkbox>
	        	<!-- 选择税率 -->
	        	 <div id="_xform_fdDetailList_Form[${vstatus.index}].fdInputTaxRate" _xform_type="dialog" style="display:${fdDetailList_FormItem.fdIsDeduct=='true'?'':'none'};">
		              <%--   <xform:dialog propertyId="fdDetailList_Form[${vstatus.index}].fdInputTaxRateId" propertyName="fdDetailList_Form[${vstatus.index}].fdInputTaxRate" subject="${lfn:message('fssc-expense:fsscExpenseDetail.fdInputTaxRate')}" style="width:90%;">
		                    FSSC_SelectInputTaxRate(${vstatus.index});
		                </xform:dialog> --%>
		                 <select name="fdDetailList_Form[${vstatus.index}].fdInputTaxRate_select" onchange="FSSC_selectTaxMoney(${vstatus.index})">
						  <option value="0">0</option>
						  <option value="3">3</option>
						  <option value="6">6</option>
						  <option value="13">13</option>
						</select>%
		            </div>
	        	
	            <div id="_xform_fdDetailList_Form[${vstatus.index}].fdInputTaxMoney" _xform_type="text" class="inputTax" style="display:${fdDetailList_FormItem.fdIsDeduct=='true'?'':'none'};">
	                <input type="text" name="fdDetailList_Form[${vstatus.index}].fdInputTaxMoney" value="<kmss:showNumber value="${fdDetailList_FormItem.fdInputTaxMoney }" pattern="0.00"></kmss:showNumber>" class="inputsgl" onchange="FSSC_CalculateNoTaxMoney(this.value,this)" validate="currency-dollar required"   subject="${lfn:message('fssc-expense:fsscExpenseDetail.fdInputTaxMoney')}" style="width:85%;" />
	            	<span class="txtstrong">*</span>
	            </div>
	        </td>
	        <td align="center">
	            <div id="_xform_fdDetailList_Form[${vstatus.index}].fdNoTaxMoney" _xform_type="text">
	               <input type="text" name="fdDetailList_Form[${vstatus.index}].fdNoTaxMoney" value="<kmss:showNumber value="${fdDetailList_FormItem.fdNoTaxMoney }" pattern="0.00"/>" class="inputsgl" validate="required currency-dollar" onchange="FSSC_ChangeMoney(this.value,this);" required="true" subject="${lfn:message('fssc-expense:fsscExpenseDetail.fdNoTaxMoney')}" style="width:85%;"/>
	            </div>
	        </td>
	        </c:if>
            <c:if test="${docTemplate.fdIsForeign=='true' }">
            <td align="center">
                <div id="_xform_fdDetailList_Form[${vstatus.index}].fdCurrencyId" _xform_type="dialog">
                    <xform:dialog propertyId="fdDetailList_Form[${vstatus.index}].fdCurrencyId" required="true" propertyName="fdDetailList_Form[${vstatus.index}].fdCurrencyName" showStatus="edit" style="width:85%;" subject="${lfn:message('fssc-expense:fsscExpenseDetail.fdCurrency')}">
                        FSSC_SelectCurrency(${vstatus.index});
                    </xform:dialog>
                    <input name="fdDetailList_Form[${vstatus.index}].fdExchangeRate" value='<kmss:showNumber value="${fdDetailList_FormItem.fdExchangeRate }" pattern="0.00####"/>' onchange="FSSC_ChangeExchangeRate(this.value,this)" subject="${lfn:message('fssc-expense:fsscExpenseDetail.fdExchangeRate')}" style="width:85%;" class="inputsgl"/>
                </div>
            </td>
            
            <td align="center">
               <div id="_xform_fdDetailList_Form[${vstatus.index}].fdStandardMoney" _xform_type="text">
                    <input type="text" readonly="readonly" value="<kmss:showNumber  value="${fdDetailList_FormItem.fdStandardMoney }" pattern="0.00"/>" style="width:90%;" class="inputsgl" name="fdDetailList_Form[${vstatus.index}].fdStandardMoney"/>
                </div>  
            </td>
            </c:if>
            <td align="center">
                <div id="_xform_fdDetailList_Form[${vstatus.index}].fdUse" _xform_type="text">
                    <xform:text property="fdDetailList_Form[${vstatus.index}].fdUse" showStatus="edit" style="width:90%;" />
                </div>
            </td>
            <c:if test="${docTemplate.fdExpenseType eq '2' and docTemplate.fdIsTravelAlone eq 'true'}">
	        <td>
				<select name="fdDetailList_Form[${vstatus.index}].fdTravel" validate="required" style="width:86%;">
                    <option value="">${lfn:message('page.firstOption') }</option>
					<c:forEach items="${fsscExpenseMainForm.fdTravelList_Form}" var="fdTravelList_FormItem" varStatus="v-status">
						<option value="${fdTravelList_FormItem.fdSubject }" <c:if test="${fdTravelList_FormItem.fdSubject eq fdDetailList_FormItem.fdTravel }">selected</c:if>>				
							${fdTravelList_FormItem.fdSubject }
						</option>
					</c:forEach>
				</select>
				<span class="txtstrong">*</span>
	        </td>
	        </c:if>
	        <fssc:ifModuleExists path="/fssc/budget/">
                <fssc:switchOn property="fdIsBudget" defaultValue="1">
	        <td align="left">
	        	<div id="buget_status_${vstatus.index}">
	        	</div>
	        </td>
                </fssc:switchOn>
	        </fssc:ifModuleExists>
	        <td align="left" class="status_other">
	        	<!-- 关联事前，显示事前状态灯 -->
	        	<c:if test="${docTemplate.fdIsFee=='true' }">
		        	<div id="fee_status_${vstatus.index}" class="budget_container">
		        	</div>
	        	</c:if>
	       	 	<!-- 关联立项，显示立项状态灯 -->
	        	<c:if test="${docTemplate.fdIsProapp=='true' }">
		        	<div id="proapp_status_${vstatus.index}" class="budget_container">
		        	</div>
	        	</c:if>
	        	<fssc:checkVersion version="true">
	        	<div id="standard_status_${vstatus.index}" class="budget_container">
	        	</div>
	        	</fssc:checkVersion>
	        </td>
            <td align="center">
            	<a href="javascript:void(0);" onclick="viewInvoiceTemp();" title="${lfn:message('fssc-expense:button.viewInvoice')}">
	                <img src="${LUI_ContextPath}/resource/style/common/images/small_search.png" border="0" style="width:20px;height:20px;" />
	            </a>
	            <c:if test="${fsscExpenseMainForm.docStatus!='20'}">
	            &nbsp;
                <a href="javascript:void(0);" onclick="cascade_DeleteInvoice();DocList_DeleteRow();FSSC_GetTotalMoney()" title="${lfn:message('doclist.delete')}">
                    <img src="${KMSS_Parameter_StylePath}icons/icon_del.png" border="0" />
                </a>
                </c:if>
            </td>
        </tr>
    </c:forEach>
    <%-- <tr type="optRow" class="tr_normal_opt" invalidrow="true">
        <td colspan="13">
            <a href="javascript:void(0);" onclick="FSSC_AddExpenseDetail()">
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
        </td>
    </tr> --%>
</table>
</div>
<table class="tb_normal" width="100%">
	<tr>
        <td class="td_normal_title" width="16.6%" colspan="2">
            ${lfn:message('fssc-expense:fsscExpenseMain.fdAttNumber')}
        </td>
        <td width="16.6%">
            <div id="_xform_fdAttNumber" _xform_type="text">
                <xform:text property="fdAttNumber" required="true" style="width:85%;" validators="digits min(0)"/>
            </div>
        </td>
        <td class="td_normal_title" width="16.6%">
            ${lfn:message('fssc-expense:fsscExpenseMain.fdTotalStandaryMoney')} 
        </td>
        <td width="16.6%">
            <div id="_xform_fdTotalStandaryMoney" _xform_type="text">
                <xform:text property="fdTotalStandaryMoney" showStatus="readOnly" style="width:95%;"/>
            </div>
        </td>
        <td class="td_normal_title" width="16.6%">
            ${lfn:message('fssc-expense:fsscExpenseMain.fdTotalApprovedMoney')}
        </td>
        <td width="16.6%" id="remainTd">
            <div id="_xform_fdTotalApprovedMoney" _xform_type="text">
                 <xform:text property="fdTotalApprovedMoney" showStatus="readOnly" style="width:95%;"/>
            </div>
        </td>
    </tr>
</table>
<c:import url="/fssc/expense/fssc_expense_amortize/fsscExpenseDetail_edit_amortz.jsp"></c:import>
<input type="hidden" name="fdDetailList_Flag" value="1">
<input type="hidden" name="fdDidiDetail_Flag" value="1">
<input type="hidden" name="fdTravelList_Flag" value="1">
<input type="hidden" name="fdInvoiceList_Flag" value="1">
<input type="hidden" name="fdAccountsList_Flag" value="1">
<input type="hidden" name="fdOffsetList_Flag" value="1">
<input type="hidden" name="fdAmortizeList_Flag" value="1">
<input type="hidden" name="fdTranDataList_Flag" value="1">
<script src="${LUI_ContextPath }/eop/basedata/resource/js/importDetail.js"></script>
<style>
	.status_other>.budget_container{float:left;margin-left:20%;margin-right:0;}
</style>
 <script>
	 LUI.ready(function(){
		 setTimeout(function(){
                var width = $(".lui_form_path_frame").width();
				$("#detail_div_for_length").css("width",parseInt(width)-70);
				$(".tempTB").css("width",width+"px");
				var widfix = setInterval(function(){
					attachmentObject_invoice.resizeAllUploader();
					if($(".lui_form_path_frame").width()==$(".tempTB").width()){
						clearInterval(widfix);
						attachmentObject_invoice.resizeAllUploader();
					}
				},50)
				tableFreezeStarter2("TABLE_DocList_fdDetailList_Form",true,true,false,true,"edit");
			},200);
	 });
	 $("#TABLE_DocList_fdDetailList_Form").on("detaillist-init",function(){
		/*  var options ={
				container : $(".btn_container"),
				table : "TABLE_DocList_fdDetailList_Form",
				modelName : "com.landray.kmss.fssc.expense.model.FsscExpenseDetail",
				fdCompanyId : $("[name=fdCompanyId]").val(),
				docTemplateId : $("[name=docTemplateId]").val(),
				callback : FSSC_DetailImported
			}
			new ImportDetailUtil(options).addButton(); */
	  });
</script>
</ui:content>
