<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/resource/jsp/view_top.jsp"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<link rel="stylesheet" href="<c:url value="/eop/basedata/resource/style/print.css"/>" />
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
</style>
<style media="print" type="text/css">
	#S_OperationBar{display:none !important;}
</style>
<div id="optBarDiv">
	<%--打印 --%>
	<c:if test="${fsscExpenseMainForm.docStatus != '10'}">
	    <input type="button" value="${lfn:message('button.print') }" onclick="javascript:print();">
	</c:if>
	<input type="button" value="${lfn:message('button.close') }" onclick="Com_CloseWindow();">
</div>
<center>

<div class='lui_form_title_frame'>
    <div class='lui_form_subject'>
       <p class="txttitle">${lfn:message('fssc-expense:table.fsscExpenseBalance')}</p>
    </div>
    <%--条形码--%>
	<div id="barcodeTarget" style="float:right;margin-right:40px;margin-top: -20px;" ></div>
</div>
  <table class="tb_normal" style="width:95%;">
      <tr>
          <td class="td_normal_title" width="16.6%">
              ${lfn:message('fssc-expense:fsscExpenseBalance.docSubject')}
          </td>
          <td colspan="5" width="83.0%">
              <%-- 主题--%>
              <div id="_xform_docSubject" _xform_type="text">
                  <xform:text property="docSubject" showStatus="view" style="width:95%;" />
              </div>
          </td>
      </tr>
      <tr>
          <td class="td_normal_title" width="16.6%">
              ${lfn:message('fssc-expense:fsscExpenseBalance.docCreator')}
          </td>
          <td width="16.6%">
              <%-- 经办人--%>
              <div id="_xform_docCreatorId" _xform_type="address">
                  <ui:person personId="${fsscExpenseBalanceForm.docCreatorId}" personName="${fsscExpenseBalanceForm.docCreatorName}" />
              </div>
          </td>
          <td class="td_normal_title" width="16.6%">
              ${lfn:message('fssc-expense:fsscExpenseBalance.docCreatorDept')}
          </td>
          <td width="16.6%">
              <%-- 经办人部门--%>
              <div id="_xform_docCreatorDeptId" _xform_type="address">
                  <xform:address propertyId="docCreatorDeptId" propertyName="docCreatorDeptName" orgType="ORG_TYPE_ORGORDEPT" showStatus="view" style="width:95%;" />
              </div>
          </td>
          <td class="td_normal_title" width="16.6%">
              ${lfn:message('fssc-expense:fsscExpenseBalance.docCreateTime')}
          </td>
          <td width="16.6%">
              <%-- 创建日期--%>
              <div id="_xform_docCreateTime" _xform_type="datetime">
                  <xform:datetime property="docCreateTime" showStatus="view" style="width:95%;" />
              </div>
          </td>
      </tr>
      <tr>
          <td class="td_normal_title" width="16.6%">
              ${lfn:message('fssc-expense:fsscExpenseBalance.fdCompany')}
          </td>
          <td width="16.6%">
              <%-- 费用公司--%>
              <div id="_xform_fdCompanyId" _xform_type="dialog">
                  <xform:dialog propertyId="fdCompanyId" propertyName="fdCompanyName" showStatus="view" style="width:95%;">
                      dialogSelect(false,'eop_basedata_company_fdCompany','fdCompanyId','fdCompanyName');
                  </xform:dialog>
              </div>
          </td>
          <td class="td_normal_title" width="16.6%">
              ${lfn:message('fssc-expense:fsscExpenseBalance.fdCostCenter')}
          </td>
          <td width="16.6%">
              <%-- 成本中心--%>
              <div id="_xform_fdCostCenterId" _xform_type="dialog">
                  <xform:dialog propertyId="fdCostCenterId" propertyName="fdCostCenterName" showStatus="view" style="width:95%;">
                      dialogSelect(false,'eop_basedata_cost_center_selectCostCenter','fdCostCenterId','fdCostCenterName');
                  </xform:dialog>
              </div>
          </td>
          <td class="td_normal_title" width="16.6%">
              ${lfn:message('fssc-expense:fsscExpenseBalance.fdAttNum')}
          </td>
          <td width="16.6%">
              <%-- 附件(张)--%>
              <div id="_xform_fdAttNum" _xform_type="text">
                  <xform:text property="fdAttNum" showStatus="view" style="width:95%;" />
              </div>
          </td>
      </tr>
      <tr>
          <td class="td_normal_title" width="16.6%">
              ${lfn:message('fssc-expense:fsscExpenseBalance.fdVoucherType')}
          </td>
          <td width="16.6%">
              <%-- 凭证类型--%>
              <div id="_xform_fdVoucherTypeId" _xform_type="radio">
                  <xform:dialog propertyId="fdVoucherTypeId" propertyName="fdVoucherTypeName" required="true" subject="${lfn:message('fssc-expense:fsscExpenseBalance.fdCostCenter')}" style="width:95%;">
                      dialogSelect(false,'eop_basedata_voucher_type_selectVoucherType','fdVoucherTypeId','fdVoucherTypeName',null,{fdCompanyId:'${fsscExpenseBalanceForm.fdCompanyId }'});
                  </xform:dialog>
              </div>
          </td>
          <td class="td_normal_title" width="16.6%">
              ${lfn:message('fssc-expense:fsscExpenseBalance.fdCurrency')}
          </td>
          <td width="16.6%">
              <%-- 币种--%>
              <div id="_xform_fdCurrencyId" _xform_type="dialog">
                  <xform:dialog propertyId="fdCurrencyId" propertyName="fdCurrencyName" showStatus="view" style="width:95%;">
                      dialogSelect(false,'eop_basedata_currency_fdCurrency','fdCurrencyId','fdCurrencyName');
                  </xform:dialog>
              </div>
          </td>
          <td class="td_normal_title" width="16.6%">
              ${lfn:message('fssc-expense:fsscExpenseBalance.fdMonth')}
          </td>
          <td width="16.6%">
              <%-- 月份--%>
              <div id="_xform_fdMonth" _xform_type="text">
                  <xform:text property="fdMonth" showStatus="view" style="width:95%;" />
              </div>
          </td>
      </tr>
      <tr>
          <td class="td_normal_title" width="16.6%">
              ${lfn:message('fssc-expense:fsscExpenseBalance.fdSubject')}
          </td>
          <td colspan="5" width="83.0%">
              <%-- 凭证抬头文本--%>
              <div id="_xform_fdSubject" _xform_type="text">
                  <xform:text property="fdSubject" showStatus="view" style="width:95%;" />
              </div>
          </td>
      </tr>
      <tr>
          <td colspan="6" width="100%">
              <table class="tb_normal" width="100%" id="TABLE_DocList_fdDetailList_Form" align="center" tbdraggable="true">
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
                  </td>
              </tr>
               <tr>
				<td class="td_normal_title"  colspan="6">
					<bean:message bundle="sys-lbpmservice" key="lbpmProcess.history.description.show" />
					</td>
				</tr>
				
				<tr>
					<td colspan="6">
						<c:import url="/sys/workflow/include/sysWfProcess_log.jsp" charEncoding="UTF-8">
							<c:param name="formName" value="fsscExpenseBalanceForm" />
						</c:import>
					</td>
				</tr>
    </table>
</center>
<script type="text/javascript">
Com_IncludeFile("jquery.js");
</script>
<%-- 条形码公共页面 --%>
<c:import url="/eop/basedata/resource/jsp/barcode.jsp" charEncoding="UTF-8">
	<c:param name="docNumber">${fsscExpenseBalanceForm.docNumber }</c:param>
</c:import>
<%@ include file="/resource/jsp/view_down.jsp"%>
