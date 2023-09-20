<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ taglib uri="/WEB-INF/KmssConfig/eop/basedata/fssc.tld" prefix="fssc"%>
<div data-dojo-type="mui/table/ScrollableHContainer" >
	<div data-dojo-type="mui/table/ScrollableHView" style="margin-top:20px;" id="TABLE_DocList_fdOffsetList_Form">
		 <table class="muiNormal" id="TABLE_DocList_fdOffsetList_Form" width="100%" border="0" cellspacing="0" cellpadding="0">
	 			<tr align="center" class="tr_normal_title">
                        <td style="width:40px;">
                            ${lfn:message('page.serial')}
                        </td>
                        <td>
                            ${lfn:message('fssc-expense:fsscExpenseBalanceDetail.fdType')}
                        </td>
                        <td>
                            ${lfn:message('fssc-expense:fsscExpenseBalanceDetail.fdExpenseItem')}
                        </td>
                        <td>
                            ${lfn:message('fssc-expense:fsscExpenseBalanceDetail.fdAccount')}
                        </td>
                        <td>
                            ${lfn:message('fssc-expense:fsscExpenseBalanceDetail.fdCostCenter')}
                        </td>
                        <td>
                            ${lfn:message('fssc-expense:fsscExpenseBalanceDetail.fdPerson')}
                        </td>
                        <td>
                            ${lfn:message('fssc-expense:fsscExpenseDetail.fdDept')}
                        </td>
                        <td>
                            ${lfn:message('fssc-expense:fsscExpenseBalanceDetail.fdCashFlow')}
                        </td>
                        <td>
                            ${lfn:message('fssc-expense:fsscExpenseBalanceDetail.fdProject')}
                        </td>
                        <td>
                            ${lfn:message('fssc-expense:fsscExpenseBalanceDetail.fdMoney')}
                        </td>
                        <td>
                            ${lfn:message('fssc-expense:fsscExpenseBalanceDetail.fdRemark')}
                        </td>
                    </tr>
                    <c:forEach items="${fsscExpenseBalanceForm.fdDetailList_Form}" var="fdDetailList_FormItem" varStatus="vstatus">
                        <tr KMSS_IsContentRow="1">
                            <td align="center">
                                ${vstatus.index+1}
                            </td>
                            <td align="center">
                                <%-- 借/贷--%>
                                <input type="hidden" name="fdDetailList_Form[${vstatus.index}].fdId" value="${fdDetailList_FormItem.fdId}" />
                                <div id="_xform_fdDetailList_Form[${vstatus.index}].fdType" _xform_type="select">
                                    <xform:select property="fdDetailList_Form[${vstatus.index}].fdType" htmlElementProperties="id='fdDetailList_Form[${vstatus.index}].fdType'" showStatus="view">
                                        <xform:enumsDataSource enumsType="fssc_expense_detal_voucher_type" />
                                    </xform:select>
                                </div>
                            </td>
                            <td align="center">
                                <%-- 费用类型--%>
                                <div id="_xform_fdDetailList_Form[${vstatus.index}].fdExpenseItemId" _xform_type="dialog">
                                    <xform:dialog propertyId="fdDetailList_Form[${vstatus.index}].fdExpenseItemId" propertyName="fdDetailList_Form[${vstatus.index}].fdExpenseItemName" showStatus="view" style="width:95%;">
                                        dialogSelect(false,'eop_basedata_expense_item_fdParent','fdDetailList_Form[*].fdExpenseItemId','fdDetailList_Form[*].fdExpenseItemName');
                                    </xform:dialog>
                                </div>
                            </td>
                            <td align="center">
                                <%-- 会计科目--%>
                                <div id="_xform_fdDetailList_Form[${vstatus.index}].fdAccountId" _xform_type="dialog">
                                    <xform:dialog propertyId="fdDetailList_Form[${vstatus.index}].fdAccountId" propertyName="fdDetailList_Form[${vstatus.index}].fdAccountName" showStatus="view" style="width:95%;">
                                        dialogSelect(false,'eop_basedata_accounts_com_fdAccount','fdDetailList_Form[*].fdAccountId','fdDetailList_Form[*].fdAccountName');
                                    </xform:dialog>
                                </div>
                            </td>
                            <td align="center">
                                <%-- 成本中心--%>
                                <div id="_xform_fdDetailList_Form[${vstatus.index}].fdCostCenterId" _xform_type="dialog">
                                    <xform:dialog propertyId="fdDetailList_Form[${vstatus.index}].fdCostCenterId" propertyName="fdDetailList_Form[${vstatus.index}].fdCostCenterName" showStatus="view" style="width:95%;">
                                        dialogSelect(false,'eop_basedata_cost_center_selectCostCenter','fdDetailList_Form[*].fdCostCenterId','fdDetailList_Form[*].fdCostCenterName');
                                    </xform:dialog>
                                </div>
                            </td>
                            <td align="center">
                                <%-- 个人--%>
                                <div id="_xform_fdDetailList_Form[${vstatus.index}].fdPersonId" _xform_type="address">
                                    <xform:address propertyId="fdDetailList_Form[${vstatus.index}].fdPersonId" propertyName="fdDetailList_Form[${vstatus.index}].fdPersonName" orgType="ORG_TYPE_PERSON" showStatus="view" style="width:95%;" />
                                </div>
                            </td>
                            <td align="center">
                                <%-- 个人--%>
                                <div id="_xform_fdDetailList_Form[${vstatus.index}].fdDeptId" _xform_type="address">
                                    <xform:address propertyId="fdDetailList_Form[${vstatus.index}].fdDeptId" propertyName="fdDetailList_Form[${vstatus.index}].fdDeptName" orgType="ORG_TYPE_DEPT" showStatus="view" style="width:95%;" />
                                </div>
                            </td>
                            <td align="center">
                                <%-- 现金流量项目--%>
                                <div id="_xform_fdDetailList_Form[${vstatus.index}].fdCashFlowId" _xform_type="dialog">
                                    <xform:dialog propertyId="fdDetailList_Form[${vstatus.index}].fdCashFlowId" propertyName="fdDetailList_Form[${vstatus.index}].fdCashFlowName" showStatus="view" style="width:95%;">
                                        dialogSelect(false,'eop_basedata_cash_flow_getCashFlow','fdDetailList_Form[*].fdCashFlowId','fdDetailList_Form[*].fdCashFlowName');
                                    </xform:dialog>
                                </div>
                            </td>
                            <td align="center">
                                <%-- 项目--%>
                                <div id="_xform_fdDetailList_Form[${vstatus.index}].fdProjectId" _xform_type="dialog">
                                    <xform:dialog propertyId="fdDetailList_Form[${vstatus.index}].fdProjectId" propertyName="fdDetailList_Form[${vstatus.index}].fdProjectName" showStatus="view" style="width:95%;">
                                        dialogSelect(false,'eop_basedata_project_project','fdDetailList_Form[*].fdProjectId','fdDetailList_Form[*].fdProjectName');
                                    </xform:dialog>
                                </div>
                            </td>
                            <td align="center">
                                <%-- 金额--%>
                                <div id="_xform_fdDetailList_Form[${vstatus.index}].fdMoney" _xform_type="text">
                                    <kmss:showNumber value="${fdDetailList_FormItem.fdMoney }" pattern="0.00"/>
                                </div>
                            </td>
                            <td align="center">
                                <%-- 备注--%>
                                <div id="_xform_fdDetailList_Form[${vstatus.index}].fdRemark" _xform_type="text">
                                    <xform:text property="fdDetailList_Form[${vstatus.index}].fdRemark" showStatus="view" style="width:95%;" />
                                </div>
                            </td>
                        </tr>
                    </c:forEach>
                </table>
		<input type="hidden" name="fdAccountsList_Flag" value="1">
	</div>
</div>


