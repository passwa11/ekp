<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ taglib uri="/WEB-INF/KmssConfig/eop/basedata/fssc.tld" prefix="fssc"%>
<div data-dojo-type="mui/table/ScrollableHContainer" >
	<div data-dojo-type="mui/table/ScrollableHView" style="margin-top:20px;" id="dt_wrap_expense_hview">
		  <table class="muiNormal" id="TABLE_DocList_fdDetailList_Form" width="100%" border="0" cellspacing="0" cellpadding="0">
		   <tr align="center" class="tr_normal_title">
		        <td style="width:30px;">
		            ${lfn:message('page.serial')}
		        </td>
		        <c:if test="${docTemplate.fdAllocType=='2' }">
		        <td  style="width:120px">
		            ${lfn:message('fssc-expense:fsscExpenseDetail.fdCostCenter')}
		        </td>
		        </c:if>
		        <td  style="width:120px">
		            ${lfn:message('fssc-expense:fsscExpenseDetail.fdExpenseItem')}
		        </td>
		        <td  style="width:100px">
		            ${lfn:message('fssc-expense:fsscExpenseDetail.fdRealUser')}
		        </td>
		        <fssc:checkVersion version="true">
		        <c:if test="${fn:indexOf(docTemplate.fdExtendFields,'7')>-1 }">
		        <td  style="width:120px">
		            ${lfn:message('fssc-expense:fsscExpenseDetail.fdDept')}
		        </td>
		        </c:if>
		        </fssc:checkVersion>
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
		        <td  style="width:90px">
		            ${lfn:message('fssc-expense:fsscExpenseDetail.fdApprovedApplyMoney')}
		        </td>
		        <c:if test="${fn:indexOf(docTemplate.fdExtendFields,'2')>-1 }">
		        <td  style="width:90px">
		            ${lfn:message('fssc-expense:fsscExpenseDetail.fdInputTaxMoney')}
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
			   <kmss:ifModuleExist path="/fssc/budget">
				   <fssc:switchOn property="fdIsBudget" defaultValue="1">
				   <td  style="width:80px">
						   ${lfn:message('fssc-expense:fsscExpenseDetail.fdBudgetStatus')}
				   </td>
				   </fssc:switchOn>
			   </kmss:ifModuleExist>
		        <fssc:checkVersion version="true">
		        <td  style="width:80px">
		            ${lfn:message('fssc-expense:fsscExpenseDetail.fdStandardStatus')}
		        </td>
		        </fssc:checkVersion>
		    </tr>
		    <c:forEach items="${fsscExpenseMainForm.fdDetailList_Form}" var="fdDetailList_FormItem" varStatus="vstatus">
		        <tr KMSS_IsContentRow="1">
		            <td align="center">
		                ${vstatus.index+1}
		                <input type="hidden" name="fdDetailList_Form[${vstatus.index}].fdId" value="${fdDetailList_FormItem.fdId }" />
		                <input type="hidden" name="fdDetailList_Form[${vstatus.index}].fdCompanyId" value="${fdDetailList_FormItem.fdCompanyId }" />
		                <input type="hidden" name="fdDetailList_Form[${vstatus.index}].fdBudgetStatus" value="${fdDetailList_FormItem.fdBudgetStatus }" />
		                <input type="hidden" name="fdDetailList_Form[${vstatus.index}].fdFeeInfo" value="${fdDetailList_FormItem.fdFeeInfo }" />
		            	<input type="hidden" name="fdDetailList_Form[${vstatus.index}].fdFeeStatus" value="${fdDetailList_FormItem.fdFeeStatus }" />
		                <input type="hidden" name="fdDetailList_Form[${vstatus.index}].fdStandardStatus" value="${fdDetailList_FormItem.fdStandardStatus }" />
		                <input type="hidden" name="fdDetailList_Form[${vstatus.index}].fdBudgetInfo" value="${fdDetailList_FormItem.fdBudgetInfo }" />
		                <input type="hidden" name="fdDetailList_Form[${vstatus.index}].fdBudgetRate" value="${fdDetailList_FormItem.fdBudgetRate }" />
		                <input type="hidden" name="fdDetailList_Form[${vstatus.index}].fdBudgetMoney" value="${fdDetailList_FormItem.fdBudgetMoney }" />
		                <input type="hidden" name="fdDetailList_Form[${vstatus.index}].fdBudgetMoneyOld" value="${fdDetailList_FormItem.fdBudgetMoney }" />
		                <input type="hidden" name="fdDetailList_Form[${vstatus.index}].fdStandardMoney" value="${fdDetailList_FormItem.fdStandardMoney }" />
		                <input type="hidden" name="fdDetailList_Form[${vstatus.index}].fdExpenseItemId" value="${fdDetailList_FormItem.fdExpenseItemId }" />
		                <input type="hidden" name="fdDetailList_Form[${vstatus.index}].fdApplyMoney" value="${fdDetailList_FormItem.fdApplyMoney }" />
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
			            <c:if test="${docTemplate.fdIsForeign!='true' }">
			            <input type="hidden" name="fdDetailList_Form[${vstatus.index}].fdCurrencyId" value="${fdDetailList_FormItem.fdCurrencyId }" />
			            <input type="hidden" name="fdDetailList_Form[${vstatus.index}].fdExchangeRate" value="${fdDetailList_FormItem.fdExchangeRate }" />
			            </c:if>
			            	<input type="hidden" name="fdDetailList_Form[${vstatus.index}].fdProappInfo" value="${fdDetailList_FormItem.fdProappInfo }" />
			            	<input type="hidden" name="fdDetailList_Form[${vstatus.index}].fdProappStatus" value="${fdDetailList_FormItem.fdProappStatus }" />
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
		            <td align="center">
			            <div id="_xform_fdDetailList_Form[${vstatus.index}].fdExpenseItemId" _xform_type="dialog">
			                <xform:dialog propertyId="fdDetailList_Form[${vstatus.index}].fdExpenseItemId" propertyName="fdDetailList_Form[${vstatus.index}].fdExpenseItemName" required="true" subject="${lfn:message('fssc-expense:fsscExpenseDetail.fdCostCenter')}" style="width:90%;">
			                    FSSC_SelectExpenseItem(${vstatus.index});
			                </xform:dialog>
			            </div>
			        </td>
		            <td align="center">
		                <div id="_xform_fdDetailList_Form[${vstatus.index}].fdRealUserId" _xform_type="address">
		                    <xform:address propertyId="fdDetailList_Form[${vstatus.index}].fdRealUserId" propertyName="fdDetailList_Form[${vstatus.index}].fdRealUserName" orgType="ORG_TYPE_PERSON" required="true" subject="${lfn:message('fssc-expense:fsscExpenseDetail.fdRealUser')}"
		                    style="width:90%;" />
		                </div>
		            </td>
		            <fssc:checkVersion version="true">
		            <c:if test="${fn:indexOf(docTemplate.fdExtendFields,'7')>-1 }">
		            <td align="center">
			            <div id="_xform_fdDetailList_Form[!{index}].fdDeptId" _xform_type="address">
			                <xform:address propertyId="fdDetailList_Form[${vstatus.index}].fdDeptId" propertyName="fdDetailList_Form[${vstatus.index}].fdDeptName" orgType="ORG_TYPE_DEPT" showStatus="view" required="true" subject="${lfn:message('fssc-expense:fsscExpenseDetail.fdDept')}"
			                style="width:90%;" />
			            </div>
			        </td>
			        </c:if>
			        </fssc:checkVersion>
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
		                    <kmss:showNumber value="${fdDetailList_FormItem.fdApplyMoney }" pattern="0.00"/>
		                </div>
		            </td>
		            <td align="center">
		                <div id="_xform_fdDetailList_Form[${vstatus.index}].fdApprovedApplyMoney" _xform_type="text">
		                    <xform:text onValueChange="FSSC_ChangeMoney" property="fdDetailList_Form[${vstatus.index}].fdApprovedApplyMoney" validators="currency-dollay max(${fdDetailList_FormItem.fdApplyMoney })" required="true" subject="${lfn:message('fssc-expense:fsscExpenseDetail.fdApprovedApplyMoney')}" style="width:80%;" />
		                </div>
		            </td>
		            <c:if test="${fn:indexOf(docTemplate.fdExtendFields,'2')>-1 }">
				        <td align="center">
				        	<div id="_xform_fdDetailList_Form[${vstatus.index}].fdInputTaxMoney" _xform_type="text" class="inputTax" style="display:${fdDetailList_FormItem.fdIsDeduct=='true'?'':'none'};">
				                <xform:text property="fdDetailList_Form[${vstatus.index}].fdInputTaxMoney"  validators="currency-dollar" required="true" subject="${lfn:message('fssc-expense:fsscExpenseDetail.fdInputTaxMoney')}" style="width:85%;" />
				            </div>
				        </td>
			        </c:if>
		            <c:if test="${docTemplate.fdIsForeign=='true' }">
		            <td align="center">
		                <div id="_xform_fdDetailList_Form[${vstatus.index}].fdCurrencyId" _xform_type="dialog">
		                    <xform:dialog propertyId="fdDetailList_Form[${vstatus.index}].fdCurrencyId" required="true" propertyName="fdDetailList_Form[${vstatus.index}].fdCurrencyName" style="width:85%;" subject="${lfn:message('fssc-expense:fsscExpenseDetail.fdCurrency')}">
		                        FSSC_SelectCurrency(${vstatus.index});
		                    </xform:dialog>
			                    <xform:text onValueChange="FSSC_ChangeExchangeRate" property="fdDetailList_Form[${vstatus.index}].fdExchangeRate" showStatus="noShow" required="true" subject="${lfn:message('fssc-expense:fsscExpenseDetail.fdExchangeRate')}" style="width:40%;" />
		                </div>
		            </td>
		            <td align="center">
		                <div id="_xform_fdDetailList_Form[${vstatus.index}].fdStandardMoney" _xform_type="text">
		                	<kmss:showNumber value="${fdDetailList_FormItem.fdStandardMoney }" pattern="0.00"/>
		                </div>
		            </td>
		            </c:if>
		            <td align="center">
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
			        <td >
			        	<c:if test="${docTemplate.fdBudgetShowType eq '1'}">
			        	<div id="buget_status_${vstatus.index }" class="budget_container">
			        	</div>
			        	</c:if>
			        	<c:if test="${docTemplate.fdBudgetShowType eq '2'}">
			        	<div id="buget_status_${vstatus.index }">
			        	</div>
			        	</c:if>
			        </td>
						</fssc:switchOn>
			        </kmss:ifModuleExist>
			        <fssc:checkVersion version="true">
			        <td >
			        	<div id="standard_status_${vstatus.index }" class="budget_container standard_status_${fdDetailList_FormItem.fdStandardStatus }">
			        	</div>
			        </td>
			        </fssc:checkVersion>
		        </tr>
		    </c:forEach>
		</table>
		<input type="hidden" name="fdAccountsList_Flag" value="1">
	</div>
</div>

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

