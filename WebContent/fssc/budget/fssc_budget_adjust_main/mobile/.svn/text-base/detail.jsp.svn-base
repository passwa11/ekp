<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<div data-dojo-type="mui/table/ScrollableHContainer" >
		<div data-dojo-type="mui/table/ScrollableHView" style="margin-top:20px;" id="dt_wrap_gather_hview">
					<table class="muiNormal" width="100%" border="0" cellspacing="0" cellpadding="0" id="TABLE_Borrow_Lend_Form">
						<tr class="tr_normal_title">
							<td>${lfn:message('page.serial')}</td>
							<td style="display:none;" class="borrowclass3">${lfn:message('fssc-budget:fsscBudgetAdjustDetail.fdBorrowCostCenterGroup')}</td>
							<td style="display:none;" class="borrowclass4">${lfn:message('fssc-budget:fsscBudgetAdjustDetail.fdBorrowCostCenter')}</td>
							<td style="display:none;" class="borrowclass8">${lfn:message('fssc-budget:fsscBudgetAdjustDetail.fdBorrowBudgetItem')}</td>
							<td style="display:none;" class="borrowclass5">${lfn:message('fssc-budget:fsscBudgetAdjustDetail.fdBorrowProject')}</td>
							<td style="display:none;" class="borrowclass6">${lfn:message('fssc-budget:fsscBudgetAdjustDetail.fdBorrowInnerOrder')}</td>
							<td style="display:none;" class="borrowclass7">${lfn:message('fssc-budget:fsscBudgetAdjustDetail.fdBorrowWbs')}</td>
							<td style="display:none;" class="borrowclass10">${lfn:message('fssc-budget:fsscBudgetAdjustDetail.fdBorrowPerson')}</td>
							<td style="display:none;" class="borrowclass11">${lfn:message('fssc-budget:fsscBudgetAdjustDetail.fdBorrowPeriod')}</td>
							<td style="display:none;" class="lendclass3">${lfn:message('fssc-budget:fsscBudgetAdjustDetail.fdLendCostCenterGroup')}</td>
							<td style="display:none;" class="lendclass4">${lfn:message('fssc-budget:fsscBudgetAdjustDetail.fdLendCostCenter')}</td>
							<td style="display:none;" class="lendclass8">${lfn:message('fssc-budget:fsscBudgetAdjustDetail.fdLendBudgetItem')}</td>
							<td style="display:none;" class="lendclass5">${lfn:message('fssc-budget:fsscBudgetAdjustDetail.fdLendProject')}</td>
							<td style="display:none;" class="lendclass6">${lfn:message('fssc-budget:fsscBudgetAdjustDetail.fdLendInnerOrder')}</td>
							<td style="display:none;" class="lendclass7">${lfn:message('fssc-budget:fsscBudgetAdjustDetail.fdLendWbs')}</td>
							<td style="display:none;" class="lendclass10">${lfn:message('fssc-budget:fsscBudgetAdjustDetail.fdLendPerson')}</td>
							<td style="display:none;" class="lendclass11">${lfn:message('fssc-budget:fsscBudgetAdjustDetail.fdLendPeriod')}</td>
							<td>${lfn:message('fssc-budget:fsscBudgetAdjustDetail.fdMoney')}</td>
						</tr>
					    <c:forEach items="${fsscBudgetAdjustMainForm.fdDetailList_Form}" var="fdDetailList_FormItem" varStatus="vstatus">
					        <tr KMSS_IsContentRow="1">
					            <td KMSS_IsRowIndex=1 class="detailTableIndex">
					               	<xform:text showStatus="noShow" property="fdDetailList_Form[${vstatus.index}].fdId" />
									<span>${vstatus.index+1}</span>
								</td>
					            <td style="display:none;" class="borrowclass3">
					                <div id="_xform_fdDetailList_Form[${vstatus.index}].fdBorrowCostCenterGroupId" _xform_type="dialog">
					                    <c:out value="${fdDetailList_FormItem.fdBorrowCostCenterGroupName}" />
					                </div>
					            </td>
					            <td style="display:none;" class="borrowclass4">
					
					                <div id="_xform_fdDetailList_Form[${vstatus.index}].fdBorrowCostCenterId" _xform_type="dialog">
					                    <c:out value="${fdDetailList_FormItem.fdBorrowCostCenterName}" />
					                </div>
					            </td>
					            <td style="display:none;" class="borrowclass8">
					
					                <div id="_xform_fdDetailList_Form[${vstatus.index}].fdBorrowBudgetItemId" _xform_type="dialog">
					                    <c:out value="${fdDetailList_FormItem.fdBorrowBudgetItemName}" />
					                </div>
					            </td>
					            <td style="display:none;" class="borrowclass5">
					
					                <div id="_xform_fdDetailList_Form[${vstatus.index}].fdBorrowProjectId" _xform_type="dialog">
					                    <c:out value="${fdDetailList_FormItem.fdBorrowProjectName}" />
					                </div>
					            </td>
					            <td style="display:none;" class="borrowclass6">
					
					                <div id="_xform_fdDetailList_Form[${vstatus.index}].fdBorrowInnerOrderId" _xform_type="dialog">
					                    <c:out value="${fdDetailList_FormItem.fdBorrowInnerOrderName}" />
					                </div>
					            </td>
					            <td style="display:none;" class="borrowclass7">
					
					                <div id="_xform_fdDetailList_Form[${vstatus.index}].fdBorrowWbsId" _xform_type="dialog">
					                    <c:out value="${fdDetailList_FormItem.fdBorrowWbsName}" />
					                </div>
					            </td>
					            <td style="display:none;" class="borrowclass10">
					                <div id="_xform_fdDetailList_Form[${vstatus.index}].fdBorrowPersonId" _xform_type="address">
					                    <xform:address propertyId="fdDetailList_Form[${vstatus.index}].fdBorrowPersonId" propertyName="fdDetailList_Form[${vstatus.index}].fdBorrowPersonName" orgType="ORG_TYPE_PERSON" showStatus="view" mobile="true" style="width:95%;" />
					                </div>
					            </td>
								<td style="display:none;" class="borrowclass11">
									<div id="_xform_fdDetailList_Form[${vstatus.index}].fdBorrowPeriod" _xform_type="datetime">
										<c:out value="${fdDetailList_FormItem.fdBorrowPeriod}" />
									</div>
								</td>
					            <td style="display:none;" class="lendclass3">
					                <div id="_xform_fdDetailList_Form[${vstatus.index}].fdLendCostCenterGroupId" _xform_type="dialog">
					                    <c:out value="${fdDetailList_FormItem.fdLendCostCenterGroupName}" />
					                </div>
					            </td>
					            <td style="display:none;" class="lendclass4">
					                <div id="_xform_fdDetailList_Form[${vstatus.index}].fdLendCostCenterId" _xform_type="dialog">
					                    <c:out value="${fdDetailList_FormItem.fdLendCostCenterName}" />
					                </div>
					            </td>
					            <td style="display:none;" class="lendclass8">
					                <div id="_xform_fdDetailList_Form[${vstatus.index}].fdLendBudgetItemId" _xform_type="dialog">
					                    <c:out value="${fdDetailList_FormItem.fdLendBudgetItemName}" />
					                </div>
					            </td>
					            <td style="display:none;" class="lendclass5">
					                <div id="_xform_fdDetailList_Form[${vstatus.index}].fdLendProjectId" _xform_type="dialog">
					                    <c:out value="${fdDetailList_FormItem.fdLendProjectName}" />
					                </div>
					            </td>
					            <td style="display:none;" class="lendclass6">
					                <div id="_xform_fdDetailList_Form[${vstatus.index}].fdLendInnerOrderId" _xform_type="dialog">
					                    <c:out value="${fdDetailList_FormItem.fdLendInnerOrderName}" />
					                </div>
					            </td>
					            <td style="display:none;" class="lendclass7">
					                <div id="_xform_fdDetailList_Form[${vstatus.index}].fdLendWbsId" _xform_type="dialog">
					                    <c:out value="${fdDetailList_FormItem.fdLendWbsName}" />
					                </div>
					            </td>
					            <td style="display:none;" class="lendclass10">
					                <div id="_xform_fdDetailList_Form[${vstatus.index}].fdLendPersonId" _xform_type="address">
					                    <xform:address propertyId="fdDetailList_Form[${vstatus.index}].fdLendPersonId" propertyName="fdDetailList_Form[${vstatus.index}].fdLendPersonName" orgType="ORG_TYPE_PERSON" showStatus="view" mobile="true" style="width:95%;" />
					                </div>
					            </td>
								<td style="display:none;" class="lendclass11">
									<div id="_xform_fdDetailList_Form[${vstatus.index}].fdLendPeriod" _xform_type="dialog">
										<c:out value="${fdDetailList_FormItem.fdLendPeriod}" />
									</div>
								</td>
					            <td>
					
					                <div id="_xform_fdDetailList_Form[${vstatus.index}].fdLendMoney" _xform_type="text">
					                    <kmss:showNumber value="${fdDetailList_FormItem.fdMoney}" pattern="###,##0.00"></kmss:showNumber>
					                </div>
					            </td>
					        </tr>
					 	 </c:forEach>
				</table>
		</div>
</div>
