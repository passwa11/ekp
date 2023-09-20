<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/resource/jsp/view_top.jsp"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ include file="/eop/basedata/resource/jsp/jshead.jsp" %>
<%@ taglib uri="/WEB-INF/KmssConfig/eop/basedata/fssc.tld" prefix="fssc"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<link rel="stylesheet" href="<c:url value="/eop/basedata/resource/css/print.css"/>" />
<title>${lfn:message('button.print') }</title>
<style type="text/css">
	.tb_normal TD{
		border:1px #000 solid;
	}
	.tb_normal{
		border:1px #000 solid;
	}
	.tb_noborder{
		border:0px;
	}
	.tb_noborder TD{
		border:0px;
	}
	table td {
		color: #000;
	}
	.tb_normal > tbody > tr > td{
		border: 1px #000 solid !important;
	}
</style>
<style media="print" type="text/css">
	#S_OperationBar{display:none !important;}
</style>
<c:if test="${param.method=='print'}">
<div id="optBarDiv">
	<%--打印 --%>
	<c:if test="${fsscExpenseMainForm.docStatus != '10'}">
		<input type="button" value="${lfn:message('button.print') }" onclick="javascript:print();">
	</c:if>
	<input type="button" value="${lfn:message('button.close') }" onclick="Com_CloseWindow();">
</div>
</c:if>
<center>

	<div class='lui_form_title_frame'>
		<div class='lui_form_subject'>
			<p class="txttitle"><c:out value="${fsscExpenseMainForm.docTemplateName}" /></p>
		</div>
		<%--条形码--%>
		<div id="barcodeTarget" style="float:right;margin-right:40px;margin-top: -25px;" ></div>
	</div>
	<table class="tb_normal" style="width:95%;">
		<!--基本信息-->
		<tr>
			<!-- 主题  -->
			<td class="td_normal_title" width=12%>
				${lfn:message('fssc-expense:fsscExpenseMain.docSubject')}
			</td>
			<td width="15%" colspan="3">
				<xform:text property="docSubject" style="width:85%" />
			</td>
			<!-- 单据编号 -->
			<td class="td_normal_title" width=12%>
				${lfn:message('fssc-expense:fsscExpenseMain.docNumber')}
			</td><td width="15%">
			<xform:text property="docNumber" style="width:85%" />
		</td>
		</tr>
		<!-- 报销人 -->
		<tr>

			<td class="td_normal_title" width=12%>
				${lfn:message('fssc-expense:fsscExpenseMain.fdClaimant')}
			</td><td width="15%">
			<c:out value="${fsscExpenseMain.fdClaimant.fdName}" />
		</td>
			<td class="td_normal_title" width=12%>
				${lfn:message('fssc-expense:fsscExpenseMain.fdClaimantNumber')}
			</td>
			<td>
				<c:out value="${fsscExpenseMain.fdClaimant.fdNo }"></c:out>
			</td>
			<!-- 报销人部门 -->
			<td class="td_normal_title" width=12%>
				${lfn:message('fssc-expense:fsscExpenseMain.fdClaimantDept')}
			</td><td width="15%">
			<c:out value="${fsscExpenseMain.fdClaimantDept.fdName}" />
		</td>
		</tr>
		<!-- 记帐公司  -->
		<tr>

			<td class="td_normal_title" width=12%>
				${lfn:message('fssc-expense:fsscExpenseMain.fdCompany')}
			</td><td width="15%">
			<c:out value="${fsscExpenseMain.fdCompany.fdName}" />(<c:out value="${fsscExpenseMain.fdCompany.fdCode}" />)
		</td>
			<!-- 归属部门  -->
			<td class="td_normal_title" width=12%>
				${lfn:message('fssc-expense:fsscExpenseMain.fdCostCenter')}
			</td><td width="15%" >
			<c:out value="${fsscExpenseMain.fdCostCenter.fdName}" />
		</td>
			<!-- 归属项目  -->
			<td class="td_normal_title" width=12%>
				${lfn:message('fssc-expense:fsscExpenseMain.fdProject')}
			</td><td width="15%" >
			<c:out value="${fsscExpenseMain.fdProject.fdName}" />
		</td>
		</tr>
		<c:if test="${fsscExpenseMain.docTemplate.fdIsFee=='true' }">
			<tr>
				<td class="td_normal_title" width="16.6%">
						${lfn:message('fssc-expense:fsscExpenseMain.fdFeeNames')}
				</td>
				<td colspan="5" width="83.0%">
						${fsscExpenseMainForm.fdFeeNames}
				</td>
			</tr>
		</c:if>
		<c:if test="${fn:indexOf(fsscExpenseMain.docTemplate.fdExtendFields,'8')>-1 }">
			<tr>
				<td class="td_normal_title" width="16.6%">
						${lfn:message('fssc-expense:fsscExpenseMain.fdProjectAccounting')}
				</td>
				<td colspan="5" width="83.0%">
						${fsscExpenseMainForm.fdProjectAccountingName}
				</td>
			</tr>
		</c:if>
		<tr>
			<td  class="td_normal_title" width=12%>
				${lfn:message('fssc-expense:fsscExpenseMain.fdContent')}
			</td><td colspan="5" width="15%" style="word-wrap:break-word;word-break:break-all;">
			<c:out value="${fsscExpenseMain.fdContent}" />
		</td>
		</tr>
		<tr>
			<td class="td_normal_title" width="16.6%">
				${lfn:message('fssc-expense:fsscExpenseMain.fdAttNumber')}
			</td>
			<td width="16.6%">
				${fsscExpenseMainForm.fdAttNumber}
			</td>
			<%--#128275 报销新增--%>
			<td class="td_normal_title" width="16.6%">
				${lfn:message('fssc-expense:fsscExpenseMain.fdTotalStandaryMoney')}
			</td>
			<td width="16.6%" >
				<kmss:showNumber value="${fsscExpenseMainForm.fdTotalStandaryMoney }" pattern="###,##0.00"/><br/>
				<xform:text property="fdTotalStandaryMoney" showStatus="noShow"></xform:text>
				<div id="fdTotalStandaryUpperMoney"></div>
			</td>
			<td class="td_normal_title" width="16.6%">
				${lfn:message('fssc-expense:fsscExpenseMain.fdTotalApprovedMoney')}
			</td>
			<td width="16.6%">
				<kmss:showNumber value="${fsscExpenseMainForm.fdTotalApprovedMoney }" pattern="###,##0.00"/><br/>
				<xform:text property="fdTotalApprovedMoney" showStatus="noShow"></xform:text>
				<div id="fdTotalApprovedUpperMoney"></div>
			</td>
		</tr>
		<c:if test="${docTemplate.fdExpenseType eq '2' and docTemplate.fdIsTravelAlone eq 'true'}">
			<tr class="td_normal_title accountDetail" >
				<td  colspan="6">${lfn:message('fssc-expense:table.fsscExpenseTravelDetail') }</td>
			</tr>
			<!-- 行程明细 -->
			<tr class="accountDetail">
				<td  colspan="6" width="100%">
					<table class="tb_normal" width="100%" id="TABLE_DocList_fdTravelList_Form" align="center">
						<tr align="center" class="tr_normal_title">
							<td width="5%">
									${lfn:message('page.serial')}
							</td>
							<td width="50px">
									${lfn:message('fssc-expense:fsscExpenseTravelDetail.fdSubject')}
							</td>
							<td width="10%">
									${lfn:message('fssc-expense:fsscExpenseTravelDetail.fdBeginDate')}
							</td>
							<td width="10%">
									${lfn:message('fssc-expense:fsscExpenseTravelDetail.fdEndDate')}
							</td>
							<td width="50px">
									${lfn:message('fssc-expense:fsscExpenseTravelDetail.fdTravelDays')}
							</td>
							<td width="15%">
									${lfn:message('fssc-expense:fsscExpenseTravelDetail.fdPersonList')}
							</td>
							<td width="10%">
									${lfn:message('fssc-expense:fsscExpenseTravelDetail.fdStartPlace')}
							</td>
							<td width="10%">
									${lfn:message('fssc-expense:fsscExpenseTravelDetail.fdArrivalPlace')}
							</td>
							<fssc:checkVersion version="true">
								<c:if test="${fn:indexOf(docTemplate.fdExtendFields,'6')>-1  }">
									<td width="10%">
											${lfn:message('fssc-expense:fsscExpenseTravelDetail.fdBerth')}
									</td>
								</c:if>
							</fssc:checkVersion>
						</tr>
						<c:forEach items="${fsscExpenseMainForm.fdTravelList_Form}" var="fdTravelList_FormItem" varStatus="vstatus">
							<tr KMSS_IsContentRow="1">
								<td align="center">
										${vstatus.index+1}
								</td>
								<td align="center">
										${fdTravelList_FormItem.fdSubject }
								</td>
								<td align="center">
										${fdTravelList_FormItem.fdBeginDate}
								</td>
								<td align="center">
										${fdTravelList_FormItem.fdEndDate}
								</td>
								<td align="center">
									<c:out value="${fdTravelList_FormItem.fdTravelDays}"></c:out>
								</td>
								<td align="center">
										${fdTravelList_FormItem.fdPersonListNames}
								</td>
								<td align="center">
										${fdTravelList_FormItem.fdStartPlace}
								</td>
								<td align="center">
										${fdTravelList_FormItem.fdArrivalPlace}
								</td>
								<fssc:checkVersion version="true">
									<c:if test="${fn:indexOf(docTemplate.fdExtendFields,'6')>-1  }">
										<td align="center">
												${fdTravelList_FormItem.fdBerthName}
										</td>
									</c:if>
								</fssc:checkVersion>
							</tr>
						</c:forEach>
					</table>
				</td>
			</tr>
		</c:if>
		<!-- 费用明细 -->
		<tr class="td_normal_title accountDetail" >
			<td colspan="4">${lfn:message('fssc-expense:table.fsscExpenseDetail') }</td>
			<td colspan="2" width="33.6%" align="right">
				${lfn:message('fssc-expense:table.fsscExpenseDetail.total')}:<kmss:showNumber value="${fsscExpenseMainForm.fdTotalApprovedMoney }" pattern="###,##0.00"/>
			</td>
		</tr>
		<!--收款账户明细-->
		<tr class="accountDetail">
			<td  colspan="6" width="100%">
				<table class="tb_normal" width="100%" id="TABLE_DocList_fdDetailList_Form" align="center">
					<tr align="center" class="tr_normal_title">
						<td width="5%">
							${lfn:message('page.serial')}
						</td>
						<c:if test="${docTemplate.fdAllocType=='2' }">
							<td width="5%">
									${lfn:message('fssc-expense:fsscExpenseDetail.fdCostCenter')}
							</td>
						</c:if>
						<td width="5%">
							${lfn:message('fssc-expense:fsscExpenseDetail.fdExpenseItem')}
						</td>
						<td width="3%">
							${lfn:message('fssc-expense:fsscExpenseDetail.fdRealUser')}
						</td>
						<c:if test="${fn:indexOf(docTemplate.fdExtendFields,'7')>-1 }">
							<td  style="width:6%">
									${lfn:message('fssc-expense:fsscExpenseDetail.fdDept')}
							</td>
						</c:if>
						<fssc:configEnabled property="fdFinancialSystem" value="SAP">
							<c:if test="${fn:indexOf(docTemplate.fdExtendFields,'4')>-1 }">
								<td  style="width:6%">
										${lfn:message('fssc-expense:fsscExpenseDetail.fdWbs')}
								</td>
							</c:if>
							<c:if test="${fn:indexOf(docTemplate.fdExtendFields,'3')>-1 }">
								<td  style="width:6%">
										${lfn:message('fssc-expense:fsscExpenseDetail.fdInnerOrder')}
								</td>
							</c:if>
						</fssc:configEnabled>
						<c:if test="${fn:indexOf(docTemplate.fdExtendFields,'5')>-1 }">
							<td  style="width:5%">
									${lfn:message('fssc-expense:fsscExpenseDetail.fdStartDate')}
							</td>
						</c:if>
						<td width="5%">
							<c:if test="${fn:indexOf(docTemplate.fdExtendFields,'5')>-1 }">
								${lfn:message('fssc-expense:fsscExpenseTravelDetail.fdEndDate')}
							</c:if>
							<c:if test="${fn:indexOf(docTemplate.fdExtendFields,'5')==-1 }">
								${lfn:message('fssc-expense:fsscExpenseDetail.fdHappenDate')}
							</c:if>
						</td>
						<c:if test="${fn:indexOf(docTemplate.fdExtendFields,'5')>-1 }">
							<td  style="width:2%">
									${lfn:message('fssc-expense:fsscExpenseDetail.fdTravelDays')}
							</td>
						</c:if>
						<c:if test="${docTemplate.fdExpenseType eq '2' and docTemplate.fdIsTravelAlone eq 'false'}">
							<td style="width:5%">
									${lfn:message('fssc-expense:fsscExpenseDetail.fdStartPlace')}
							</td>
							<td style="width:5%">
									${lfn:message('fssc-expense:fsscExpenseDetail.fdArrivalPlace')}
							</td>
							<fssc:checkVersion version="true">
								<c:if test="${fn:indexOf(docTemplate.fdExtendFields,'6')>-1 }">
									<td  style="width:4%">
											${lfn:message('fssc-expense:fsscExpenseDetail.fdBerth')}
									</td>
								</c:if>
							</fssc:checkVersion>
						</c:if>
						<c:if test="${fn:indexOf(docTemplate.fdExtendFields,'1')>-1 }">
							<td  style="width:2%">
									${lfn:message('fssc-expense:fsscExpenseDetail.fdPersonNumber')}
							</td>
						</c:if>
						<td width="2%">
							${lfn:message('fssc-expense:fsscExpenseDetail.fdApplyMoney')}
						</td>
						<c:if test="${fn:indexOf(docTemplate.fdExtendFields,'2')>-1 }">
							<td  width="2%">
									${lfn:message('fssc-expense:fsscExpenseDetail.fdInvoiceMoney')}
							</td>
						</c:if>
						<c:if test="${fn:indexOf(docTemplate.fdExtendFields,'9')>-1 }">
							<td  width="2%">
									${lfn:message('fssc-expense:fsscExpenseDetail.fdNonDeductMoneyN')}
							</td>
						</c:if>
						<td  width="4%">
							${lfn:message('fssc-expense:fsscExpenseDetail.fdApprovedApplyMoney')}
						</td>
						<c:if test="${fn:indexOf(docTemplate.fdExtendFields,'2')>-1 }">
							<td  width="4%">
									${lfn:message('fssc-expense:fsscExpenseDetail.fdInputTaxMoney')}
							</td>
							<td  width="2%">
									${lfn:message('fssc-expense:fsscExpenseDetail.fdNoTaxMoneyN')}
							</td>
						</c:if>
						<c:if test="${docTemplate.fdIsForeign=='true' }">
							<td width="4%">
									${lfn:message('fssc-expense:fsscExpenseDetail.fdCurrency')}/${lfn:message('fssc-expense:fsscExpenseDetail.fdExchangeRate')}
							</td>
							<td width="6%">
									${lfn:message('fssc-expense:fsscExpenseDetail.fdStandardMoney')}
							</td>
						</c:if>
						<td width="6%">
							${lfn:message('fssc-expense:fsscExpenseDetail.fdUse')}
						</td>
						<c:if test="${docTemplate.fdExpenseType eq '2' and docTemplate.fdIsTravelAlone eq 'true'}">
							<td width="6%">
									${lfn:message('fssc-expense:fsscExpenseDetail.fdTravel')}
							</td>
						</c:if>
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
							<c:if test="${fsscExpenseMain.docTemplate.fdAllocType=='2' }">
								<td align="center">
										${fdDetailList_FormItem.fdCostCenterName}
								</td>
							</c:if>
							<td align="center">
									${fdDetailList_FormItem.fdExpenseItemName}
							</td>
							<td align="center">
									${fdDetailList_FormItem.fdRealUserName}
							</td>
							<c:if test="${fn:indexOf(docTemplate.fdExtendFields,'7')>-1 }">
								<td align="center">
										${fdDetailList_FormItem.fdDeptName}
								</td>
							</c:if>
							<fssc:configEnabled property="fdFinancialSystem" value="SAP">
								<c:if test="${fn:indexOf(docTemplate.fdExtendFields,'4')>-1 }">
									<td  style="width:120px">
											${fdDetailList_FormItem.fdWbsName}
									</td>
								</c:if>
								<c:if test="${fn:indexOf(docTemplate.fdExtendFields,'3')>-1 }">
									<td  style="width:120px">
											${fdDetailList_FormItem.fdInnerOrderName}
									</td>
								</c:if>
							</fssc:configEnabled>
							<c:if test="${fn:indexOf(docTemplate.fdExtendFields,'5')>-1 }">
								<td  align="center">
										${fdDetailList_FormItem.fdStartDate}
								</td>
							</c:if>
							<td align="center">
									${fdDetailList_FormItem.fdHappenDate}
							</td>
							<c:if test="${fn:indexOf(docTemplate.fdExtendFields,'5')>-1 }">
								<td  align="center">
										${fdDetailList_FormItem.fdTravelDays}
								</td>
							</c:if>
							<c:if test="${docTemplate.fdExpenseType eq '2' and docTemplate.fdIsTravelAlone eq 'false'}">
								<td  align="center">
										${fdDetailList_FormItem.fdStartPlace}
								</td>
								<td align="center">
										${fdDetailList_FormItem.fdArrivalPlace}
								</td>
								<fssc:checkVersion version="true">
									<c:if test="${fn:indexOf(docTemplate.fdExtendFields,'6')>-1 }">
										<td  align="center">
												${fdDetailList_FormItem.fdBerthName}
										</td>
									</c:if>
								</fssc:checkVersion>
							</c:if>
							<c:if test="${fn:indexOf(docTemplate.fdExtendFields,'1')>-1 }">
								<td align="center">
										${fdDetailList_FormItem.fdPersonNumber}
								</td>
							</c:if>
							<td align="center">
								<kmss:showNumber value="${fdDetailList_FormItem.fdApplyMoney }" pattern="###,##0.00"/>
							</td>
							<c:if test="${fn:indexOf(docTemplate.fdExtendFields,'2')>-1 }">
								<td  align="center">
									<kmss:showNumber value="${fdDetailList_FormItem.fdInvoiceMoney}" pattern="###,##0.00"/>
								</td>
							</c:if>
							<c:if test="${fn:indexOf(docTemplate.fdExtendFields,'9')>-1 }">
								<td  align="center">
									<kmss:showNumber value="${fdDetailList_FormItem.fdNonDeductMoney}" pattern="###,##0.00"/>
								</td>
							</c:if>
							<td align="center">
								<kmss:showNumber value="${fdDetailList_FormItem.fdApprovedApplyMoney}" pattern="###,##0.00"/>
							</td>
							<c:if test="${fn:indexOf(docTemplate.fdExtendFields,'2')>-1 }">
								<td  align="center">
									<kmss:showNumber value="${fdDetailList_FormItem.fdInputTaxMoney}" pattern="###,##0.00"/>
								</td>
								<td  align="center">
									<kmss:showNumber value="${fdDetailList_FormItem.fdNoTaxMoney}" pattern="###,##0.00"/>
								</td>
							</c:if>
							<c:if test="${docTemplate.fdIsForeign=='true' }">
								<td align="center">
										${fdDetailList_FormItem.fdCurrencyName}&nbsp;<kmss:showNumber value="${fdDetailList_FormItem.fdExchangeRate }" pattern="0.0#####"/>
								</td>
								<td align="center">
									<kmss:showNumber value="${fdDetailList_FormItem.fdStandardMoney }" pattern="###,##0.00"/>
								</td>
							</c:if>
							<td align="center">
								<xform:text property="fdDetailList_Form[${vstatus.index}].fdUse" style="width:90%;" />
							</td>
							<c:if test="${docTemplate.fdExpenseType eq '2'  and docTemplate.fdIsTravelAlone eq 'true'}">
								<td align="center">
										${fdDetailList_FormItem.fdTravel }
								</td>
							</c:if>
						</tr>
					</c:forEach>
				</table>
			</td>
		</tr>
		<!-- 摊销明细 -->
		<c:if test="${(fsscExpenseMainForm.fdAmortizeList_Form)!= null && fn:length(fsscExpenseMainForm.fdAmortizeList_Form) > 0}">

			<tr class="td_normal_title" >
				<td  colspan="6">${lfn:message('fssc-expense:table.fsscExpenseAmortize') }</td>
			</tr>
			<tr>
				<td  colspan="6" width="100%" id="account_detail_of_message">
					<table class="tb_normal" width="100%">
						<tr>
							<td class="td_normal_title" width="10">${lfn:message('fssc-expense:fsscExpenseMain.fdAmortizeArea') }</td>
							<td width="23%">
								<xform:text showStatus="view" style="width:40%" property="fdAmortizeBegin" validators="checkAmortizeMonth" subject="${lfn:message('fssc-expense:fsscExpenseMain.fdAmortizeBegin') }" htmlElementProperties="id='fdAmortizeBegin'"></xform:text>
								~
								<xform:text showStatus="view" style="width:40%" property="fdAmortizeEnd" validators="checkAmortizeMonth" subject="${lfn:message('fssc-expense:fsscExpenseMain.fdAmortizeEnd') }" htmlElementProperties="id='fdAmortizeEnd'"></xform:text>
							</td>
							<td class="td_normal_title" width="10">${lfn:message('fssc-expense:fsscExpenseMain.fdAmortizeMonth') }</td>
							<td width="23%">
								<xform:text property="fdAmortizeMonth" showStatus="view"/>
							</td>
							<td class="td_normal_title" width="10">${lfn:message('fssc-expense:fsscExpenseMain.fdAmortizeMoney') }</td>
							<td width="23%">
								<xform:text property="fdAmortizeMoney" showStatus="readOnly"/>
							</td>
						</tr>
						<tr>
							<td colspan="6">
								<table class="tb_normal" width="100%" id="TABLE_DocList_Amortize">
									<tr align="center" class="tr_normal_title">
										<td width="5%">
												${lfn:message('page.serial')}
										</td>
										<td width="35%">
												${lfn:message('fssc-expense:fsscExpenseAmortize.fdMonth')}
										</td>
										<td width="30%">
												${lfn:message('fssc-expense:fsscExpenseAmortize.fdPercent')}(%)
										</td>
										<td width="30%">
												${lfn:message('fssc-expense:fsscExpenseAmortize.fdMoney')}
										</td>
									</tr>
									<c:forEach items="${fsscExpenseMainForm.fdAmortizeList_Form}" var="fdAmortizeList_FormItem" varStatus="vstatus">
										<tr KMSS_IsContentRow="1">
											<td align="center">
													${vstatus.index+1}
											</td>
											<td align="center">
												<input type="hidden" value="${fdAmortizeList_FormItem.fdId }" name="fdAmortizeList_Form[${vstatus.index }].fdId">
													${fdAmortizeList_FormItem.fdMonth}
											</td>
											<td align="center">
													${fdAmortizeList_FormItem.fdPercent}
											</td>
											<td align="center">
													${fdAmortizeList_FormItem.fdMoney}
											</td>
										</tr>
									</c:forEach>
								</table>
							</td>
						</tr>
					</table>
				</td>
			</tr>
		</c:if>
		<!-- 发票明细 -->
		<c:if test="${(fsscExpenseMainForm.fdInvoiceList_Form)!= null && fn:length(fsscExpenseMainForm.fdInvoiceList_Form) > 0}">

			<tr class="td_normal_title" >
				<td  colspan="6">${lfn:message('fssc-expense:table.fsscExpenseInvoiceDetail') }</td>
			</tr>
			<tr>
				<td  colspan="6" width="100%" id="account_detail_of_message">
					<table class="tb_normal" width="100%" id="TABLE_DocList_fdInvoiceList_Form" align="center" >
						<tr align="center" class="tr_normal_title">
							<td width="5%">
									${lfn:message('page.serial')}
							</td>
							<td width="10%">
									${lfn:message('fssc-expense:fsscExpenseInvoiceDetail.fdInvoiceType')}
							</td>
							<td width="10%">
									${lfn:message('fssc-expense:fsscExpenseInvoiceDetail.fdInvoiceNumber')}
							</td>

							<td width="10%">
									${lfn:message('fssc-expense:fsscExpenseInvoiceDetail.fdInvoiceCode')}
							</td>
							<td width="10%">
									${lfn:message('fssc-expense:fsscExpenseInvoiceDetail.fdInvoiceDate')}
							</td>
							<td width="8%">
									${lfn:message('fssc-expense:fsscExpenseInvoiceDetail.fdInvoiceMoney')}
							</td>
							<td width="8%">
									${lfn:message('fssc-expense:fsscExpenseInvoiceDetail.fdTax')}
							</td>
							<td width="8%">
									${lfn:message('fssc-expense:fsscExpenseInvoiceDetail.fdTaxMoney')}
							</td>
							<td width="8%">
									${lfn:message('fssc-expense:fsscExpenseInvoiceDetail.fdNoTaxMoney')}
							</td>
						</tr>
						<c:forEach items="${fsscExpenseMainForm.fdInvoiceList_Form}" var="fdInvoiceList_FormItem" varStatus="vstatus">
							<tr KMSS_IsContentRow="1">
								<td align="center">
										${vstatus.index+1}
								</td>
								<td align="center">
									<div id="_xform_fdInvoiceType" _xform_type="select">
										<xform:select property="fdInvoiceList_Form[${vstatus.index}].fdInvoiceType" htmlElementProperties="id='fdInvoiceType'">
											<xform:enumsDataSource enumsType="fssc_invoice_type" />
										</xform:select>
									</div>
								</td>
								<td align="center">
										${fdInvoiceList_FormItem.fdInvoiceNumber}
								</td>
								<td align="center">
										${fdInvoiceList_FormItem.fdInvoiceCode}
								</td>
								<td align="center">
										${fdInvoiceList_FormItem.fdInvoiceDate}
								</td>
								<td align="center">
									<kmss:showNumber value="${fdInvoiceList_FormItem.fdInvoiceMoney }" pattern="0.00"/>
								</td>
								<td align="center">
									<kmss:showNumber value="${fdInvoiceList_FormItem.fdTax }" pattern="0.0#"/>%
								</td>
								<td align="center">
									<kmss:showNumber value="${fdInvoiceList_FormItem.fdTaxMoney }" pattern="0.00"/>
								</td>
								<td align="center">
									<kmss:showNumber value="${fdInvoiceList_FormItem.fdNoTaxMoney }" pattern="0.00"/>
								</td>
							</tr>
						</c:forEach>
					</table>
				</td>
			</tr>
		</c:if>
		<!-- 借款明细 -->
		<c:if test="${(fsscExpenseMainForm.fdOffsetList_Form)!= null && fn:length(fsscExpenseMainForm.fdOffsetList_Form) > 0 && offsetMoney>0}">

			<tr class="td_normal_title" >
				<td colspan="4">${lfn:message('fssc-expense:table.fsscExpenseOffsetLoan')}</td>
				<td colspan="2" width="33.6%" align="right">
						${lfn:message('fssc-expense:table.fsscExpenseOffsetLoan.total')}:<kmss:showNumber value="${offsetMoney}" pattern="###,##0.00"/>
				</td>
			</tr>
			<tr>
				<td  colspan="6" width="100%" id="account_detail_of_message">
					<table class="tb_normal" width="100%" id="TABLE_DocList_fdOffsetList_Form" align="center">
						<tr align="center" class="tr_normal_title">
							<td style="width:40px;">
									${lfn:message('page.serial')}
							</td>
							<td>
									${lfn:message('fssc-expense:fsscExpenseOffsetLoan.docSubject')}
							</td>
							<td>
									${lfn:message('fssc-expense:fsscExpenseOffsetLoan.fdNumber')}
							</td>
							<td>
									${lfn:message('fssc-expense:fsscExpenseOffsetLoan.fdLoanMoney')}
							</td>
							<td>
									${lfn:message('fssc-expense:fsscExpenseOffsetLoan.fdCanOffsetMoney')}
							</td>
							<td>
									${lfn:message('fssc-expense:fsscExpenseOffsetLoan.fdOffsetMoney')}
							</td>
							<td>
									${lfn:message('fssc-expense:fsscExpenseOffsetLoan.fdLeftMoney')}
							</td>
						</tr>
						<c:forEach items="${fsscExpenseMainForm.fdOffsetList_Form}" var="fdOffsetList_FormItem" varStatus="vstatus">
							<c:if test="${(fdOffsetList_FormItem.fdOffsetMoney)!=null}">
								<tr KMSS_IsContentRow="1">
									<td align="center">
											${vstatus.index+1}
									</td>
									<td align="center">
											${fdOffsetList_FormItem.docSubject}
									</td>
									<td align="center">
											${fdOffsetList_FormItem.fdNumber}
									</td>
									<td align="center">
										<kmss:showNumber value="${fdOffsetList_FormItem.fdLoanMoney }" pattern="0.00"/>
									</td>
									<td align="center">
										<kmss:showNumber value="${fdOffsetList_FormItem.fdCanOffsetMoney }" pattern="0.00"/>
									</td>
									<td align="center">
										<kmss:showNumber value="${fdOffsetList_FormItem.fdOffsetMoney }" pattern="0.00"/>
									</td>
									<td align="center">
										<kmss:showNumber value="${fdOffsetList_FormItem.fdLeftMoney }" pattern="0.00"/>
									</td>
								</tr>
							</c:if>
						</c:forEach>
					</table>
				</td>
			</tr>
		</c:if>
		<!-- 收款账户 -->
		<c:if test="${(fsscExpenseMainForm.fdAccountsList_Form)!= null && fn:length(fsscExpenseMainForm.fdAccountsList_Form) > 0 && accountMoney>0}">

			<tr class="td_normal_title">
				<td colspan="4">${lfn:message('fssc-expense:table.fsscExpenseAccounts')}</td>
				<td colspan="2" width="33.6%" align="right">
						${lfn:message('fssc-expense:table.fsscExpenseAccounts.total')}:<kmss:showNumber value="${accountMoney}" pattern="###,##0.00"/>
				</td>
			</tr>
			<tr>
				<td  colspan="6" width="100%" id="account_detail_of_message">
					<table class="tb_normal" width="100%" id="TABLE_DocList_fdAccountsList_Form" align="center">
						<tr align="center" class="tr_normal_title">
							<td width="5%">
									${lfn:message('page.serial')}
							</td>
							<td width="12%">
									${lfn:message('fssc-expense:fsscExpenseAccounts.fdPayWay')}
							</td>
							<td width="12%">
									${lfn:message('fssc-expense:fsscExpenseAccounts.fdBank')}
							</td>
							<fssc:checkVersion version="true">
								<td width="12%">
										${lfn:message('fssc-expense:fsscExpenseAccounts.fdCurrency')}
								</td>
							</fssc:checkVersion>
							<td width="12%">
									${lfn:message('fssc-expense:fsscExpenseAccounts.fdAccountName')}
							</td>
							<td width="12%">
									${lfn:message('fssc-expense:fsscExpenseAccounts.fdBankName')}
							</td>
							<td width="12%">
									${lfn:message('fssc-expense:fsscExpenseAccounts.fdBankAccount')}
							</td>
							<td width="12%">
									${lfn:message('fssc-expense:fsscExpenseAccounts.fdMoney')}
							</td>
						</tr>
						<c:forEach items="${fsscExpenseMainForm.fdAccountsList_Form}" var="fdAccountsList_FormItem" varStatus="vstatus">
							<tr KMSS_IsContentRow="1">
								<td align="center">
										${vstatus.index+1}
								</td>
								<td align="center">
										${fdAccountsList_FormItem.fdPayWayName }
								</td>
								<td align="center">
										${fdAccountsList_FormItem.fdPayBankName }
								</td>
								<fssc:checkVersion version="true">
									<td align="center">
											${fdAccountsList_FormItem.fdCurrencyName }
									</td>
								</fssc:checkVersion>
								<td align="center">
										${fdAccountsList_FormItem.fdAccountName }
								</td>
								<td align="center">
										${fdAccountsList_FormItem.fdBankName }
								</td>
								<td align="center">
										${fdAccountsList_FormItem.fdBankAccount }
								</td>
								<td align="center">
									<kmss:showNumber value="${fdAccountsList_FormItem.fdMoney }" pattern="###,##0.00"></kmss:showNumber>
								</td>
							</tr>
						</c:forEach>
					</table>
				</td>
			</tr>
		</c:if>
		<!--滴滴明细-->
		<c:if test="${not empty  fsscExpenseMainForm.fdDidiDetail_Form}">
			<tr class="td_normal_title">
				<td colspan="6">${lfn:message('fssc-expense:table.fsscExpenseDidiDetail')}</td>
			</tr>
			<tr>
				<td  colspan="6" width="100%" id="didi_detail_of_message">
					<table class="tb_normal" width="100%" id="TABLE_DocList_fdDidiDetail_Form" align="center">
						<tr align="center" class="tr_normal_title">
							<td width="5%">
									${lfn:message('page.serial')}
							</td>
							<td width="12%">
									${lfn:message('fssc-expense:fsscExpenseDidiDetail.fdPassenger')}
							</td>
							<td width="12%">
									${lfn:message('fssc-expense:fsscExpenseDetail.fdStartPlace')}
							</td>
							<td width="12%">
									${lfn:message('fssc-expense:fsscExpenseDidiDetail.fdEndPlace')}
							</td>
							<td width="12%">
									${lfn:message('fssc-expense:fsscExpenseDidiDetail.fdStartTime')}
							</td>
							<td width="12%">
									${lfn:message('fssc-expense:fsscExpenseDidiDetail.fdEndTime')}
							</td>
							<td width="12%">
									${lfn:message('fssc-expense:fsscExpenseDidiDetail.fdCarLevel')}
							</td>
							<td width="12%">
									${lfn:message('fssc-expense:fsscExpenseDidiDetail.fdMoney')}
							</td>
						</tr>
						<c:forEach items="${fsscExpenseMainForm.fdDidiDetail_Form}" var="fdDidiList_FormItem" varStatus="vstatus">
							<tr KMSS_IsContentRow="1">
								<td align="center">
										${vstatus.index+1}
								</td>
								<td align="center">
										${fdDidiList_FormItem.fdPassenger }
								</td>
								<td align="center">
										${fdDidiList_FormItem.fdStartPlace }
								</td>
								<td align="center">
										${fdDidiList_FormItem.fdEndPlace }
								</td>
								<td align="center">
										${fdDidiList_FormItem.fdStartTime }
								</td>
								<td align="center">
										${fdDidiList_FormItem.fdEndTime }
								</td>
								<td align="center">
										${fdDidiList_FormItem.fdCarLevel }
								</td>
								<td align="center">
									<kmss:showNumber value="${fdDidiList_FormItem.fdMoney }" pattern="###,##0.00"></kmss:showNumber>
								</td>
							</tr>
						</c:forEach>
					</table>
				</td>
			</tr>
		</c:if>
		<!--交易数据明细-->
		<kmss:ifModuleExist path="/fssc/ccard">
		<c:if test="${not empty fsscExpenseMainForm.fdTranDataList_Form }">
		<tr class="td_normal_title">
			<td colspan="4">${lfn:message('fssc-expense:table.fsscExpenseTranData')}</td>
			<td colspan="2" width="33.6%" align="right">
					${lfn:message('fssc-expense:table.fsscExpenseAccounts.total')}:<kmss:showNumber value="${accountMoney}" pattern="###,##0.00"/>
			</td>
		</tr>
		<tr>
			<td  colspan="6" width="100%" id="trandata_detail_of_message">
				<table class="tb_normal" width="100%" id="TABLE_DocList_fdTranDataList_Form" align="center">
					<tr align="center" class="tr_normal_title">
						<td style="width:40px;">
								${lfn:message('page.serial')}
						</td>
						<td>
								${lfn:message('fssc-expense:fsscExpenseTranData.fdCrdNum')}
						</td>
						<td>
								${lfn:message('fssc-expense:fsscExpenseTranData.fdActChiNam')}
						</td>
						<td>
								${lfn:message('fssc-expense:fsscExpenseTranData.fdTrsDte')}
						</td>
						<td>
								${lfn:message('fssc-expense:fsscExpenseTranData.fdTrxTim')}
						</td>
						<td>
								${lfn:message('fssc-expense:fsscExpenseTranData.fdOriCurAmt')}
						</td>
						<td>
								${lfn:message('fssc-expense:fsscExpenseTranData.fdOriCurCod')}
						</td>
						<td>
								${lfn:message('fssc-expense:fsscExpenseTranData.fdTrsCod')}
						</td>
					</tr>
					<c:forEach items="${fsscExpenseMainForm.fdTranDataList_Form}" var="fdTranDataList_FormItem" varStatus="vstatus">
					<tr KMSS_IsContentRow="1" class="docListTr">
						<td class="docList" align="center">
								${vstatus.index+1}
						</td>
						<td class="docList" align="center">
								<%-- 卡号--%>
							<input type="hidden" name="fdTranDataList_Form[${vstatus.index}].fdId" value="${fdTranDataList_FormItem.fdId}" />
							<input type="hidden" name="fdTranDataList_Form[${vstatus.index}].fdTranDataId" value="${fdTranDataList_FormItem.fdTranDataId}" />
							<input type="hidden" name="fdTranDataList_Form[${vstatus.index}].fdState" value="${fdTranDataList_FormItem.fdState}" />
							<div id="_xform_fdTranDataList_Form[${vstatus.index}].fdCrdNum" _xform_type="text">
								<xform:text property="fdTranDataList_Form[${vstatus.index}].fdCrdNum" showStatus="view" style="width:95%;" />
							</div>
						</td>
						<td class="docList" align="center">
								<%-- 持卡人中文名称--%>
							<div id="_xform_fdTranDataList_Form[${vstatus.index}].fdActChiNam" _xform_type="text">
								<xform:text property="fdTranDataList_Form[${vstatus.index}].fdActChiNam" showStatus="view" style="width:95%;" />
							</div>
						</td>
						<td class="docList" align="center">
								<%-- 交易日期--%>
							<div id="_xform_fdTranDataList_Form[${vstatus.index}].fdTrsDte" _xform_type="datetime">
								<xform:datetime property="fdTranDataList_Form[${vstatus.index}].fdTrsDte" showStatus="view" dateTimeType="date" style="width:95%;" />
							</div>
						</td>
						<td class="docList" align="center">
								<%-- 交易时间--%>
							<div id="_xform_fdTranDataList_Form[${vstatus.index}].fdTrxTim" _xform_type="text">
								<xform:text property="fdTranDataList_Form[${vstatus.index}].fdTrxTim" showStatus="view" style="width:95%;" />
							</div>
						</td>
						<td class="docList" align="center">
								<%-- 交易金额--%>
							<div id="_xform_fdTranDataList_Form[${vstatus.index}].fdOriCurAmt" _xform_type="text">
								<xform:text property="fdTranDataList_Form[${vstatus.index}].fdOriCurAmt" showStatus="view" style="width:95%;" />
							</div>
						</td>
						<td class="docList" align="center">
								<%-- 交易币种--%>
							<div id="_xform_fdTranDataList_Form[${vstatus.index}].fdOriCurCod" _xform_type="text">
								<xform:text property="fdTranDataList_Form[${vstatus.index}].fdOriCurCod" showStatus="view" style="width:95%;" />
							</div>
						</td>
						<td class="docList" align="center">
								<%-- 交易类型--%>
							<div id="_xform_fdTranDataList_Form[${vstatus.index}].fdTrsCod" _xform_type="select">
								<xform:select property="fdTranDataList_Form[${vstatus.index}].fdTrsCod" htmlElementProperties="id='fdTrsCod'" showStatus="view">
									<xform:enumsDataSource enumsType="fssc_tran_data_trsCod" />
								</xform:select>
							</div>
						</td>
					</tr>
					</c:forEach>
				</table>
			</td>
		</tr>
		</c:if>
		</kmss:ifModuleExist>


		<!--表单内容-->
		<c:if test="${fsscExpenseMainForm.docUseXform == 'true' || empty fsscExpenseMainForm.docUseXform}">


		<tr>
			<td class="td_normal_title" colspan="6">${lfn:message('fssc-expense:py.BiaoDanNeiRong')}</td>
		</tr>
		<tr>
			<td colspan="6">
				<c:import url="/sys/xform/include/sysForm_view.jsp" charEncoding="UTF-8">
					<c:param name="formName" value="fsscExpenseMainForm" />
					<c:param name="fdKey" value="fsscExpenseMain" />
					<c:param name="useTab" value="false" />
				</c:import>
			</td>
		</tr>

		</c:if>

		<kmss:ifModuleExist path="/fssc/provision">
			<!--预提明细-->
		<tr id="provisionTr">
			<td colspan="6" class="td_normal_title">${lfn:message('fssc-expense:py.YuTiXinXi') }</td>
		</tr>


		<tr  id="provisionTable">
			<td colspan="6" >
						<table class="tb_normal" width="100%" id="ytTable">
							<tr>
								<td  class="td_normal_title" width="10%" align="center">${lfn:message('page.serial') }</td>
								<td  class="td_normal_title" width="60%" align="center">${lfn:message('fssc-expense:py.MingCheng') }</td>
								<td  class="td_normal_title" width="30%" align="center">${lfn:message('fssc-expense:py.JinE') }</td>
							</tr>
						</table>
					</td>
				</tr>

				<script>
					//默认隐藏
					LUI.ready(function(){
						setTimeout(function(){
							FSSC_ReloadProvisionInfo();
						},1000)
					})
					//加载预提信息进行展示
					window.FSSC_ReloadProvisionInfo = function(){
						$("#ytTable tr:gt(0)").remove();
						var provisionInfo = {},fdProvisionLedger = {};
						$("#TABLE_DocList_fdDetailList_Form>tbody>tr:gt(0)").each(function(){
							var fdProvisionInfo = $(this).find("[name$=fdProvisionInfo]").val()||'[]';
							fdProvisionInfo = JSON.parse(fdProvisionInfo);
							for(var i=0;i<fdProvisionInfo.length;i++){
								if(fdProvisionLedger[fdProvisionInfo[i].fdLedgerId]){
									continue;
								}
								var key = [],title = '';
								if(fdProvisionInfo[i].fdProappName){
									key.push(fdProvisionInfo[i].fdProappName);
								}
								if(fdProvisionInfo[i].fdExpenseItemName){
									key.push(fdProvisionInfo[i].fdExpenseItemName);
								}
								if(fdProvisionInfo[i].fdSupplierName){
									key.push(fdProvisionInfo[i].fdSupplierName);
								}
								if(fdProvisionInfo[i].fdContractName){
									key.push(fdProvisionInfo[i].fdContractName);
								}
								if(fdProvisionInfo[i].fdCostCenterName){
									key.push(fdProvisionInfo[i].fdCostCenterName);
								}
								if(fdProvisionInfo[i].fdProjectName){
									key.push(fdProvisionInfo[i].fdProjectName);
								}
								if(fdProvisionInfo[i].fdInnerOrderName){
									key.push(fdProvisionInfo[i].fdInnerOrderName);
								}
								if(fdProvisionInfo[i].fdWbsName){
									key.push(fdProvisionInfo[i].fdWbsName);
								}
								title = key.join(' / ');
								if(provisionInfo[title]!=null){
									provisionInfo[title] = numAdd(provisionInfo[title],fdProvisionInfo[i].fdMoney);
								}else{
									provisionInfo[title] = fdProvisionInfo[i].fdMoney
								}
								fdProvisionLedger[fdProvisionInfo[i].fdLedgerId] = true;
							}
						})
						//如果有预提信息，则拼接到明细表，并显示content
						if(JSON.stringify(provisionInfo)!='{}'){
							var index = 1;
							for(var i in provisionInfo){
								$("<tr><td align='center'>"+(index++)+"</td><td>"+i+"</td><td>"+provisionInfo[i]+"</td></tr>").appendTo($("#ytTable"));
							}
						}else{//没有预提信息，隐藏content
							$("#provisionTable").hide();
							$("#provisionTr").hide();
						}
					}

				</script>

		</kmss:ifModuleExist>



		<!-- 交单记录-->
		<c:if test="${not empty presList }">
		<tr>
				<td colspan="6" class="td_normal_title">${lfn:message('fssc-pres:table.fsscPresMain') }</td>
			</tr>
				<tr class="td_normal_title"><td colspan="6" width="100%">
				<table class="tb_normal" width="100%" align="center">
						<tr>
							<td align="center">${lfn:message('fssc-pres:fsscPresMain.fdNumber')}</td>
							<td align="center">${lfn:message('fssc-pres:fsscPresMain.fdName')}</td>
							<td align="center">${lfn:message('fssc-pres:fsscPresMain.fdType')}</td>
							<td align="center">${lfn:message('fssc-pres:fsscPresMain.fdDesc')}</td>
							<td align="center">${lfn:message('fssc-pres:fsscPresMain.docCreateTime')}</td>
							<td align="center">${lfn:message('fssc-pres:fsscPresMain.docCreator')}</td>
						</tr>
						<c:forEach items="${presList}" var="pres" varStatus="vstatus">
							<tr>
								<td align="center">${pres.fdNumber}</td>
								<td align="center">${pres.fdName}</td>
								<td align="center">
									<sunbor:enumsShow enumsType="fssc_pres_type" value="${pres.fdType }"></sunbor:enumsShow>
								</td>
								<td align="center">${pres.fdDesc}</td>
								<td align="center">${pres.docCreateTime}</td>
								<td align="center">${pres.docCreatorName}</td>
							</tr>
						</c:forEach>

				</table></td></tr>


		</c:if>




<c:if test="${saveApproval||param.method=='print'}">

		<tr>
			<td class="td_normal_title"  colspan="6">
				<bean:message bundle="sys-lbpmservice" key="lbpmProcess.history.description.show" />
			</td>
		</tr>
		<tr>
			<td colspan="6">
				<c:import url="/sys/workflow/include/sysWfProcess_log.jsp" charEncoding="UTF-8">
					<c:param name="formName" value="fsscExpenseMainForm" />
				</c:import>
			</td>
		</tr>

</c:if>
</center>
<script type="text/javascript">
	Com_IncludeFile("jquery.js");
	Com_IncludeFile("Number.js", "${LUI_ContextPath}/fssc/common/resource/js/", 'js', true);
	$(document).ready(function(){
		var money=$("input[name='fdTotalStandaryMoney']").val();
		$("#fdTotalStandaryUpperMoney").html(FSSC_MenoyToUppercase(money));
		money=$("input[name='fdTotalApprovedMoney']").val();
		$("#fdTotalApprovedUpperMoney").html(FSSC_MenoyToUppercase(money));
	});
	Com_IncludeFile("document.js", 'style/default/doc/');

</script>
<%-- 条形码公共页面 --%>
<c:import url="/eop/basedata/resource/jsp/barcode.jsp" charEncoding="UTF-8">
	<c:param name="docNumber">${fsscExpenseMainForm.docNumber }</c:param>
</c:import>
<%@ include file="/resource/jsp/view_down.jsp"%>
