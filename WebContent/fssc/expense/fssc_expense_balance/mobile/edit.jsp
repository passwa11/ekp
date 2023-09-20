<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<%@ include file="/sys/mobile/jsp/ajax-accept.jsp" %>
<%@ taglib uri="/WEB-INF/KmssConfig/sys/person/person.tld" prefix="person" %>
<%@page import="com.landray.kmss.fssc.expense.util.FsscExpenseUtil" %>
    
        <% pageContext.setAttribute("currentUser", UserUtil.getKMSSUser());
        pageContext.setAttribute("currentPost", UserUtil.getKMSSUser().getPostIds());
        pageContext.setAttribute("currentDept", UserUtil.getKMSSUser().getDeptId());
        if(UserUtil.getUser().getFdParentOrg() != null) {
            pageContext.setAttribute("currentOrg", UserUtil.getUser().getFdParentOrg().getFdId());
        } else {
            pageContext.setAttribute("currentOrg", "");
        } %>
    
    <template:include ref="mobile.edit" compatibleMode="true">
        <template:replace name="title">
            <c:choose>
                <c:when test="${fsscExpenseBalanceForm.method_GET == 'add' }">
                    <c:out value="${ lfn:message('operation.create') } - ${ lfn:message('fssc-expense:table.fsscExpenseBalance') }" />
                </c:when>
                <c:otherwise>
                    <c:out value="${fsscExpenseBalanceForm.docSubject} - " />
                    <c:out value="${lfn:message('fssc-expense:table.fsscExpenseBalance') }" />
                </c:otherwise>
            </c:choose>
        </template:replace>
        <template:replace name="head">
            <style>
                .detailTips{
                				color: red;
                	    		font-weight: lighter;
                	    		display: inline-block;
                	    		font-size: 1rem;
                			}
                			.muiFormNoContent{
                				padding-left:1rem;
                				border-top:1px solid #ddd;
                				border-bottom: 1px solid #ddd;
                			 }
                			 .muiDocFrameExt{
                				margin-left: 0rem;
                			 }
                			 .muiDocFrameExt .muiDocInfo{
                				border: none;
                			 }
            </style>
            <script type="text/javascript">
                var formInitData = {

                };
                var lang = {
                    "the": "${lfn:message('page.the')}",
                    "row": "${lfn:message('page.row')}"
                };
                var messageInfo = {

                    'fdDetailList': '${lfn:escapeJs(lfn:message("fssc-expense:table.fsscExpenseBalanceDetail"))}'
                };

                var initData = {
                    contextPath: '${LUI_ContextPath}',
                };
                Com_IncludeFile("security.js");
                Com_IncludeFile("domain.js");
                Com_IncludeFile("form_option.js", "${LUI_ContextPath}/fssc/expense/fssc_expense_balance/", 'js', true);
                Com_IncludeFile("mobile_edit.js", "${LUI_ContextPath}/fssc/expense/resource/js/", 'js', true);
            </script>
        </template:replace>
        <template:replace name="content">
            <html:form action="/fssc/expense/fssc_expense_balance/fsscExpenseBalance.do?method=save">

                <div id="scrollView" data-dojo-type="mui/view/DocScrollableView" data-dojo-mixins="mui/form/_ValidateMixin">
                    <div data-dojo-type="mui/panel/AccordionPanel">
                        <div data-dojo-type="mui/panel/Content" data-dojo-props="title:'${lfn:message('fssc-expense:py.JiBenXinXi') }',icon:'mui-ul'">
                            <div class="muiFormContent">
                                <table class="muiSimple" cellpadding="0" cellspacing="0">
                                    <tr>
                                        <td class="muiTitle">
                                            ${lfn:message('fssc-expense:fsscExpenseBalance.docSubject')}
                                        </td>
                                        <td>
                                            <div id="_xform_docSubject" _xform_type="text">
                                                <xform:text property="docSubject" showStatus="edit" mobile="true" style="width:95%;" />
                                            </div>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td class="muiTitle">
                                            ${lfn:message('fssc-expense:fsscExpenseBalance.docCreator')}
                                        </td>
                                        <td>
                                            <div id="_xform_docCreatorId" _xform_type="address">
                                                <ui:person personId="${fsscExpenseBalanceForm.docCreatorId}" personName="${fsscExpenseBalanceForm.docCreatorName}" />
                                            </div>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td class="muiTitle">
                                            ${lfn:message('fssc-expense:fsscExpenseBalance.docCreatorDept')}
                                        </td>
                                        <td>
                                            <div id="_xform_docCreatorDeptId" _xform_type="address">
                                                <xform:address propertyId="docCreatorDeptId" propertyName="docCreatorDeptName" orgType="ORG_TYPE_ORGORDEPT" showStatus="view" mobile="true" style="width:95%;" />
                                            </div>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td class="muiTitle">
                                            ${lfn:message('fssc-expense:fsscExpenseBalance.docCreateTime')}
                                        </td>
                                        <td>
                                            <div id="_xform_docCreateTime" _xform_type="datetime">
                                                <xform:datetime property="docCreateTime" showStatus="view" dateTimeType="datetime" mobile="true" style="width:95%;" />
                                            </div>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td class="muiTitle">
                                            ${lfn:message('fssc-expense:fsscExpenseBalance.fdCompany')}
                                        </td>
                                        <td>
                                            <div id="_xform_fdCompanyId" _xform_type="dialog">
                                                <div data-dojo-type="mui/form/CommonDialog" data-dojo-props="idField:'fdCompanyId',nameField:'fdCompanyName',isMul:false,modelName:'com.landray.kmss.eop.basedata.model.EopBasedataCompany',dataURL:getSource('eop_basedata_company_fdCompany'),subject:'${lfn:message('fssc-expense:fsscExpenseBalance.fdCompany')}',required:true,curIds:'${lfn:escapeHtml(lfn:escapeJs(fsscExpenseBalanceForm.fdCompanyId))}',curNames:'${lfn:escapeHtml(lfn:escapeJs(fsscExpenseBalanceForm.fdCompanyName))}',afterSelect:afterDialogSel">
                                                </div>
                                            </div>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td class="muiTitle">
                                            ${lfn:message('fssc-expense:fsscExpenseBalance.fdCostCenter')}
                                        </td>
                                        <td>
                                            <div id="_xform_fdCostCenterId" _xform_type="dialog">
                                                <div data-dojo-type="mui/form/CommonDialog" data-dojo-props="idField:'fdCostCenterId',nameField:'fdCostCenterName',isMul:false,modelName:'com.landray.kmss.eop.basedata.model.EopBasedataCostCenter',dataURL:getSource('eop_basedata_cost_center_selectCostCenter'),subject:'${lfn:message('fssc-expense:fsscExpenseBalance.fdCostCenter')}',required:true,curIds:'${lfn:escapeHtml(lfn:escapeJs(fsscExpenseBalanceForm.fdCostCenterId))}',curNames:'${lfn:escapeHtml(lfn:escapeJs(fsscExpenseBalanceForm.fdCostCenterName))}',afterSelect:afterDialogSel">
                                                </div>
                                            </div>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td class="muiTitle">
                                            ${lfn:message('fssc-expense:fsscExpenseBalance.fdAttNum')}
                                        </td>
                                        <td>
                                            <div id="_xform_fdAttNum" _xform_type="text">
                                                <xform:text property="fdAttNum" showStatus="edit" validators=" digits min(0)" mobile="true" style="width:95%;" />
                                            </div>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td class="muiTitle">
                                            ${lfn:message('fssc-expense:fsscExpenseBalance.fdVoucherType')}
                                        </td>
                                        <td>
                                            <div id="_xform_fdVoucherTypeId" _xform_type="radio">
                                                <xform:radio property="fdVoucherTypeId" htmlElementProperties="id='fdVoucherTypeId'" showStatus="edit" mobile="true">
                                                    <xform:beanDataSource serviceBean="eopBasedataVoucherTypeService" selectBlock="fdId,fdName" />
                                                </xform:radio>
                                            </div>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td class="muiTitle">
                                            ${lfn:message('fssc-expense:fsscExpenseBalance.fdCurrency')}
                                        </td>
                                        <td>
                                            <div id="_xform_fdCurrencyId" _xform_type="dialog">
                                                <div data-dojo-type="mui/form/CommonDialog" data-dojo-props="idField:'fdCurrencyId',nameField:'fdCurrencyName',isMul:false,modelName:'com.landray.kmss.eop.basedata.model.EopBasedataCurrency',dataURL:getSource('eop_basedata_currency_fdCurrency'),subject:'${lfn:message('fssc-expense:fsscExpenseBalance.fdCurrency')}',required:true,curIds:'${lfn:escapeHtml(lfn:escapeJs(fsscExpenseBalanceForm.fdCurrencyId))}',curNames:'${lfn:escapeHtml(lfn:escapeJs(fsscExpenseBalanceForm.fdCurrencyName))}',afterSelect:afterDialogSel">
                                                </div>
                                            </div>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td class="muiTitle">
                                            ${lfn:message('fssc-expense:fsscExpenseBalance.fdMonth')}
                                        </td>
                                        <td>
                                            <div id="_xform_fdMonth" _xform_type="text">
                                                <xform:text property="fdMonth" showStatus="edit" mobile="true" style="width:95%;" />
                                            </div>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td class="muiTitle">
                                            ${lfn:message('fssc-expense:fsscExpenseBalance.fdSubject')}
                                        </td>
                                        <td>
                                            <div id="_xform_fdSubject" _xform_type="text">
                                                <xform:text property="fdSubject" showStatus="edit" mobile="true" style="width:95%;" />
                                            </div>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td colspan="2">
                                            <div data-dojo-type="dojox/mobile/ListItem" data-dojo-props='rightIcon:"mui mui-forward",clickable:true, noArrow:true, _setIconAttr:function(){},onClick:function(){expandDetail("TABLE_DocList_fdDetailList_Form","scrollView");}'>
                                                <div layout="left">${lfn:message('fssc-expense:fsscExpenseBalance.fdDetailList')}
                                                </div>
                                                <div layout="right">${lfn:message('fssc-expense:fssc.expense.detail.view')}
                                                </div>
                                            </div>
                                            <div class="detailTableView" data-dojo-type="mui/table/DetailTableScrollableView" data-dojo-mixins="mui/form/_ValidateMixin" id="TABLE_DocList_fdDetailList_Form_view">
                                                <div data-dojo-type="dojox/mobile/Heading" fixed="top" class="muiHeaderDetail">
                                                    <div class="muiHeaderDetailTitle">${lfn:message('fssc-expense:fsscExpenseBalance.fdDetailList')}
                                                    </div>
                                                    <div class="muiHeaderDetailBack" onclick="collapseDetail('TABLE_DocList_fdDetailList_Form')">
                                                        <bean:message key="button.save" />
                                                    </div>
                                                </div>
                                                <br/>
                                                <br/>
                                                <table cellspacing="0" cellpadding="0" class="detailTableSimple">
                                                    <tr>
                                                        <td class="detailTableSimpleTd">
                                                            <table width="100%" border="0" cellspacing="0" cellpadding="0" id="TABLE_DocList_fdDetailList_Form">
                                                                <tr style="display:none;">
                                                                    <td>
                                                                    </td>
                                                                </tr>
                                                                <tr data-dojo-type="mui/form/Template" KMSS_IsReferRow="1" style="display:none;" border='0'>
                                                                    <td class="detail_wrap_td">
                                                                        <xform:text showStatus="noShow" property="fdDetailList_Form[!{index}].fdId" />
                                                                        <table class="muiSimple">
                                                                            <tr>
                                                                                <td colspan="2" align="left" valign="middle" class="muiDetailTableNo">
                                                                                    <span>${lfn:message('page.the')}!{index}${ lfn:message('page.row') }</span>
                                                                                    <div class="muiDetailTableDel" onclick="deleteDetailRow('TABLE_DocList_fdDetailList_Form',this);">
                                                                                        <i class="mui mui-close"></i>
                                                                                    </div>
                                                                                </td>
                                                                            </tr>
                                                                            <tr>
                                                                                <td class="muiTitle">
                                                                                    ${lfn:message('fssc-expense:fsscExpenseBalanceDetail.fdType')}
                                                                                </td>
                                                                                <td>
                                                                                    <div id="_xform_fdDetailList_Form[!{index}].fdType" _xform_type="select">
                                                                                        <xform:select property="fdDetailList_Form[!{index}].fdType" htmlElementProperties="id='fdDetailList_Form[!{index}].fdType'" showStatus="edit" required="true" subject="${lfn:message('fssc-expense:fsscExpenseBalanceDetail.fdType')}" validators=" maxLength(5)"
                                                                                        mobile="true">
                                                                                            <xform:enumsDataSource enumsType="fssc_expense_detal_voucher_type" />
                                                                                        </xform:select>
                                                                                    </div>
                                                                                </td>
                                                                            </tr>
                                                                            <tr>
                                                                                <td class="muiTitle">
                                                                                    ${lfn:message('fssc-expense:fsscExpenseBalanceDetail.fdExpenseItem')}
                                                                                </td>
                                                                                <td>
                                                                                    <div id="_xform_fdDetailList_Form[!{index}].fdExpenseItemId" _xform_type="dialog">
                                                                                        <div data-dojo-type="mui/form/CommonDialog" data-dojo-props="idField:'fdDetailList_Form[!{index}].fdExpenseItemId',nameField:'fdDetailList_Form[!{index}].fdExpenseItemName',isMul:false,modelName:'com.landray.kmss.eop.basedata.model.EopBasedataExpenseItem',dataURL:getSource('eop_basedata_expense_item_fdParent'),subject:'${lfn:message('fssc-expense:fsscExpenseBalanceDetail.fdExpenseItem')}',afterSelect:afterDialogSel">
                                                                                        </div>
                                                                                    </div>
                                                                                </td>
                                                                            </tr>
                                                                            <tr>
                                                                                <td class="muiTitle">
                                                                                    ${lfn:message('fssc-expense:fsscExpenseBalanceDetail.fdAccount')}
                                                                                </td>
                                                                                <td>
                                                                                    <div id="_xform_fdDetailList_Form[!{index}].fdAccountId" _xform_type="dialog">
                                                                                        <c:out value="${fsscExpenseBalanceForm.fdDetailList_Form[vstatus.index].fdAccountName}" />
                                                                                    </div>
                                                                                </td>
                                                                            </tr>
                                                                            <tr>
                                                                                <td class="muiTitle">
                                                                                    ${lfn:message('fssc-expense:fsscExpenseBalanceDetail.fdCostCenter')}
                                                                                </td>
                                                                                <td>
                                                                                    <div id="_xform_fdDetailList_Form[!{index}].fdCostCenterId" _xform_type="dialog">
                                                                                        <div data-dojo-type="mui/form/CommonDialog" data-dojo-props="idField:'fdDetailList_Form[!{index}].fdCostCenterId',nameField:'fdDetailList_Form[!{index}].fdCostCenterName',isMul:false,modelName:'com.landray.kmss.eop.basedata.model.EopBasedataCostCenter',dataURL:getSource('eop_basedata_cost_center_selectCostCenter'),subject:'${lfn:message('fssc-expense:fsscExpenseBalanceDetail.fdCostCenter')}',afterSelect:afterDialogSel">
                                                                                        </div>
                                                                                    </div>
                                                                                </td>
                                                                            </tr>
                                                                            <tr>
                                                                                <td class="muiTitle">
                                                                                    ${lfn:message('fssc-expense:fsscExpenseBalanceDetail.fdPerson')}
                                                                                </td>
                                                                                <td>
                                                                                    <div id="_xform_fdDetailList_Form[!{index}].fdPersonId" _xform_type="address">
                                                                                        <xform:address propertyId="fdDetailList_Form[!{index}].fdPersonId" propertyName="fdDetailList_Form[!{index}].fdPersonName" orgType="ORG_TYPE_PERSON" showStatus="edit" mobile="true" style="width:95%;" />
                                                                                    </div>
                                                                                </td>
                                                                            </tr>
                                                                            <tr>
                                                                                <td class="muiTitle">
                                                                                    ${lfn:message('fssc-expense:fsscExpenseBalanceDetail.fdCashFlow')}
                                                                                </td>
                                                                                <td>
                                                                                    <div id="_xform_fdDetailList_Form[!{index}].fdCashFlowId" _xform_type="dialog">
                                                                                        <div data-dojo-type="mui/form/CommonDialog" data-dojo-props="idField:'fdDetailList_Form[!{index}].fdCashFlowId',nameField:'fdDetailList_Form[!{index}].fdCashFlowName',isMul:false,modelName:'com.landray.kmss.eop.basedata.model.EopBasedataCashFlow',dataURL:getSource('eop_basedata_cash_flow_getCashFlow'),subject:'${lfn:message('fssc-expense:fsscExpenseBalanceDetail.fdCashFlow')}',afterSelect:afterDialogSel">
                                                                                        </div>
                                                                                    </div>
                                                                                </td>
                                                                            </tr>
                                                                            <tr>
                                                                                <td class="muiTitle">
                                                                                    ${lfn:message('fssc-expense:fsscExpenseBalanceDetail.fdProject')}
                                                                                </td>
                                                                                <td>
                                                                                    <div id="_xform_fdDetailList_Form[!{index}].fdProjectId" _xform_type="dialog">
                                                                                        <div data-dojo-type="mui/form/CommonDialog" data-dojo-props="idField:'fdDetailList_Form[!{index}].fdProjectId',nameField:'fdDetailList_Form[!{index}].fdProjectName',isMul:false,modelName:'com.landray.kmss.eop.basedata.model.EopBasedataProject',dataURL:getSource('eop_basedata_project_project'),subject:'${lfn:message('fssc-expense:fsscExpenseBalanceDetail.fdProject')}',afterSelect:afterDialogSel">
                                                                                        </div>
                                                                                    </div>
                                                                                </td>
                                                                            </tr>
                                                                            <tr>
                                                                                <td class="muiTitle">
                                                                                    ${lfn:message('fssc-expense:fsscExpenseBalanceDetail.fdMoney')}
                                                                                </td>
                                                                                <td>
                                                                                    <div id="_xform_fdDetailList_Form[!{index}].fdMoney" _xform_type="text">
                                                                                        <xform:text property="fdDetailList_Form[!{index}].fdMoney" showStatus="edit" required="true" subject="${lfn:message('fssc-expense:fsscExpenseBalanceDetail.fdMoney')}" validators=" number" mobile="true" style="width:95%;" />
                                                                                    </div>
                                                                                </td>
                                                                            </tr>
                                                                            <tr>
                                                                                <td class="muiTitle">
                                                                                    ${lfn:message('fssc-expense:fsscExpenseBalanceDetail.fdRemark')}
                                                                                </td>
                                                                                <td>
                                                                                    <div id="_xform_fdDetailList_Form[!{index}].fdRemark" _xform_type="text">
                                                                                        <xform:text property="fdDetailList_Form[!{index}].fdRemark" showStatus="edit" subject="${lfn:message('fssc-expense:fsscExpenseBalanceDetail.fdRemark')}" validators=" maxLength(200)" mobile="true" style="width:95%;" />
                                                                                    </div>
                                                                                </td>
                                                                            </tr>
                                                                        </table>
                                                                    </td>
                                                                </tr>
                                                                <c:forEach items="${fsscExpenseBalanceForm.fdDetailList_Form}" var="fdDetailList_FormItem" varStatus="vstatus">
                                                                    <tr KMSS_IsContentRow="1">
                                                                        <td class="detail_wrap_td">
                                                                            <xform:text showStatus="noShow" property="fdDetailList_Form[${vstatus.index}].fdId" />
                                                                            <table class="muiSimple">
                                                                                <tr>
                                                                                    <td colspan="2" align="left" valign="middle" class="muiDetailTableNo">
                                                                                        <span>${lfn:message('page.the')}${vstatus.index}${ lfn:message('page.row') }</span>
                                                                                        <div class="muiDetailTableDel" onclick="deleteDetailRow('TABLE_DocList_fdDetailList_Form',this);">
                                                                                            <i class="mui mui-close"></i>
                                                                                        </div>
                                                                                    </td>
                                                                                </tr>
                                                                                <tr>
                                                                                    <td class="muiTitle">
                                                                                        ${lfn:message('fssc-expense:fsscExpenseBalanceDetail.fdType')}
                                                                                    </td>
                                                                                    <td>
                                                                                        <div id="_xform_fdDetailList_Form[${vstatus.index}].fdType" _xform_type="select">
                                                                                            <xform:select property="fdDetailList_Form[${vstatus.index}].fdType" htmlElementProperties="id='fdDetailList_Form[${vstatus.index}].fdType'" showStatus="edit" required="true" subject="${lfn:message('fssc-expense:fsscExpenseBalanceDetail.fdType')}" validators=" maxLength(5)"
                                                                                            mobile="true">
                                                                                                <xform:enumsDataSource enumsType="fssc_expense_detal_voucher_type" />
                                                                                            </xform:select>
                                                                                        </div>
                                                                                    </td>
                                                                                </tr>
                                                                                <tr>
                                                                                    <td class="muiTitle">
                                                                                        ${lfn:message('fssc-expense:fsscExpenseBalanceDetail.fdExpenseItem')}
                                                                                    </td>
                                                                                    <td>
                                                                                        <div id="_xform_fdDetailList_Form[${vstatus.index}].fdExpenseItemId" _xform_type="dialog">
                                                                                            <div data-dojo-type="mui/form/CommonDialog" data-dojo-props="idField:'fdDetailList_Form[${vstatus.index}].fdExpenseItemId',nameField:'fdDetailList_Form[${vstatus.index}].fdExpenseItemName',isMul:false,modelName:'com.landray.kmss.eop.basedata.model.EopBasedataExpenseItem',dataURL:getSource('eop_basedata_expense_item_fdParent'),subject:'${lfn:message('fssc-expense:fsscExpenseBalanceDetail.fdExpenseItem')}',curIds:'${lfn:escapeHtml(lfn:escapeJs(fsscExpenseBalanceForm.fdDetailList_Form[vstatus.index].fdExpenseItemId))}',curNames:'${lfn:escapeHtml(lfn:escapeJs(fsscExpenseBalanceForm.fdDetailList_Form[vstatus.index].fdExpenseItemName))}',afterSelect:afterDialogSel">
                                                                                            </div>
                                                                                        </div>
                                                                                    </td>
                                                                                </tr>
                                                                                <tr>
                                                                                    <td class="muiTitle">
                                                                                        ${lfn:message('fssc-expense:fsscExpenseBalanceDetail.fdAccount')}
                                                                                    </td>
                                                                                    <td>
                                                                                        <div id="_xform_fdDetailList_Form[${vstatus.index}].fdAccountId" _xform_type="dialog">
                                                                                            <c:out value="${fsscExpenseBalanceForm.fdDetailList_Form[vstatus.index].fdAccountName}" />
                                                                                        </div>
                                                                                    </td>
                                                                                </tr>
                                                                                <tr>
                                                                                    <td class="muiTitle">
                                                                                        ${lfn:message('fssc-expense:fsscExpenseBalanceDetail.fdCostCenter')}
                                                                                    </td>
                                                                                    <td>
                                                                                        <div id="_xform_fdDetailList_Form[${vstatus.index}].fdCostCenterId" _xform_type="dialog">
                                                                                            <div data-dojo-type="mui/form/CommonDialog" data-dojo-props="idField:'fdDetailList_Form[${vstatus.index}].fdCostCenterId',nameField:'fdDetailList_Form[${vstatus.index}].fdCostCenterName',isMul:false,modelName:'com.landray.kmss.eop.basedata.model.EopBasedataCostCenter',dataURL:getSource('eop_basedata_cost_center_selectCostCenter'),subject:'${lfn:message('fssc-expense:fsscExpenseBalanceDetail.fdCostCenter')}',curIds:'${lfn:escapeHtml(lfn:escapeJs(fsscExpenseBalanceForm.fdDetailList_Form[vstatus.index].fdCostCenterId))}',curNames:'${lfn:escapeHtml(lfn:escapeJs(fsscExpenseBalanceForm.fdDetailList_Form[vstatus.index].fdCostCenterName))}',afterSelect:afterDialogSel">
                                                                                            </div>
                                                                                        </div>
                                                                                    </td>
                                                                                </tr>
                                                                                <tr>
                                                                                    <td class="muiTitle">
                                                                                        ${lfn:message('fssc-expense:fsscExpenseBalanceDetail.fdPerson')}
                                                                                    </td>
                                                                                    <td>
                                                                                        <div id="_xform_fdDetailList_Form[${vstatus.index}].fdPersonId" _xform_type="address">
                                                                                            <xform:address propertyId="fdDetailList_Form[${vstatus.index}].fdPersonId" propertyName="fdDetailList_Form[${vstatus.index}].fdPersonName" orgType="ORG_TYPE_PERSON" showStatus="edit" mobile="true" style="width:95%;" />
                                                                                        </div>
                                                                                    </td>
                                                                                </tr>
                                                                                <tr>
                                                                                    <td class="muiTitle">
                                                                                        ${lfn:message('fssc-expense:fsscExpenseBalanceDetail.fdCashFlow')}
                                                                                    </td>
                                                                                    <td>
                                                                                        <div id="_xform_fdDetailList_Form[${vstatus.index}].fdCashFlowId" _xform_type="dialog">
                                                                                            <div data-dojo-type="mui/form/CommonDialog" data-dojo-props="idField:'fdDetailList_Form[${vstatus.index}].fdCashFlowId',nameField:'fdDetailList_Form[${vstatus.index}].fdCashFlowName',isMul:false,modelName:'com.landray.kmss.eop.basedata.model.EopBasedataCashFlow',dataURL:getSource('eop_basedata_cash_flow_getCashFlow'),subject:'${lfn:message('fssc-expense:fsscExpenseBalanceDetail.fdCashFlow')}',curIds:'${lfn:escapeHtml(lfn:escapeJs(fsscExpenseBalanceForm.fdDetailList_Form[vstatus.index].fdCashFlowId))}',curNames:'${lfn:escapeHtml(lfn:escapeJs(fsscExpenseBalanceForm.fdDetailList_Form[vstatus.index].fdCashFlowName))}',afterSelect:afterDialogSel">
                                                                                            </div>
                                                                                        </div>
                                                                                    </td>
                                                                                </tr>
                                                                                <tr>
                                                                                    <td class="muiTitle">
                                                                                        ${lfn:message('fssc-expense:fsscExpenseBalanceDetail.fdProject')}
                                                                                    </td>
                                                                                    <td>
                                                                                        <div id="_xform_fdDetailList_Form[${vstatus.index}].fdProjectId" _xform_type="dialog">
                                                                                            <div data-dojo-type="mui/form/CommonDialog" data-dojo-props="idField:'fdDetailList_Form[${vstatus.index}].fdProjectId',nameField:'fdDetailList_Form[${vstatus.index}].fdProjectName',isMul:false,modelName:'com.landray.kmss.eop.basedata.model.EopBasedataProject',dataURL:getSource('eop_basedata_project_project'),subject:'${lfn:message('fssc-expense:fsscExpenseBalanceDetail.fdProject')}',curIds:'${lfn:escapeHtml(lfn:escapeJs(fsscExpenseBalanceForm.fdDetailList_Form[vstatus.index].fdProjectId))}',curNames:'${lfn:escapeHtml(lfn:escapeJs(fsscExpenseBalanceForm.fdDetailList_Form[vstatus.index].fdProjectName))}',afterSelect:afterDialogSel">
                                                                                            </div>
                                                                                        </div>
                                                                                    </td>
                                                                                </tr>
                                                                                <tr>
                                                                                    <td class="muiTitle">
                                                                                        ${lfn:message('fssc-expense:fsscExpenseBalanceDetail.fdMoney')}
                                                                                    </td>
                                                                                    <td>
                                                                                        <div id="_xform_fdDetailList_Form[${vstatus.index}].fdMoney" _xform_type="text">
                                                                                            <xform:text property="fdDetailList_Form[${vstatus.index}].fdMoney" showStatus="edit" required="true" subject="${lfn:message('fssc-expense:fsscExpenseBalanceDetail.fdMoney')}" validators=" number" mobile="true" style="width:95%;" />
                                                                                        </div>
                                                                                    </td>
                                                                                </tr>
                                                                                <tr>
                                                                                    <td class="muiTitle">
                                                                                        ${lfn:message('fssc-expense:fsscExpenseBalanceDetail.fdRemark')}
                                                                                    </td>
                                                                                    <td>
                                                                                        <div id="_xform_fdDetailList_Form[${vstatus.index}].fdRemark" _xform_type="text">
                                                                                            <xform:text property="fdDetailList_Form[${vstatus.index}].fdRemark" showStatus="edit" subject="${lfn:message('fssc-expense:fsscExpenseBalanceDetail.fdRemark')}" validators=" maxLength(200)" mobile="true" style="width:95%;" />
                                                                                        </div>
                                                                                    </td>
                                                                                </tr>
                                                                            </table>
                                                                        </td>
                                                                    </tr>
                                                                </c:forEach>
                                                            </table>
                                                            <br/>
                                                        </td>
                                                    </tr>
                                                </table>
                                                <br/>
                                                <br/>
                                                <ul data-dojo-type="mui/tabbar/TabBar" fixed="bottom" data-dojo-props='fill:"grid"'>
                                                    <li data-dojo-type="mui/tabbar/TabBarButton" class="muiBtnNext " data-dojo-props="colSize:4,onClick:function(){addDetailRow('TABLE_DocList_fdDetailList_Form');}, label:'${lfn:message('doclist.add')}'" style="width:95%">
                                                    </li>
                                                </ul>
                                            </div>
                                            <input type="hidden" name="fdDetailList_Flag" value="1">
                                            <script>
                                                Com_IncludeFile("doclist.js");
                                            </script>
                                            <script>
                                                DocList_Info.push('TABLE_DocList_fdDetailList_Form');
                                            </script>
                                        </td>
                                    </tr>
                                </table>
                            </div>
                        </div>

                    </div>
                    <ul data-dojo-type="mui/tabbar/TabBar" fixed="bottom" data-dojo-props='fill:"grid"'>

                        <li data-dojo-type="mui/tabbar/TabBarButton" class="muiBtnNext" data-dojo-props='colSize:2,moveTo:"lbpmView",icon1:"",transition:"slide"'>
                            <bean:message bundle="fssc-expense" key="button.next" />
                        </li>
                    </ul>
                </div>
                <html:hidden property="fdId" />
                <html:hidden property="docStatus" />
                <html:hidden property="method_GET" />


                <c:import url="/sys/lbpmservice/mobile/import/edit.jsp" charEncoding="UTF-8">
                    <c:param name="formName" value="fsscExpenseBalanceForm" />
                    <c:param name="fdKey" value="fsscExpenseBalance" />
                    <c:param name="viewName" value="lbpmView" />
                    <c:param name="backTo" value="scrollView" />
                    <c:param name="onClickSubmitButton" value="form_submit();" />
                </c:import>

                <c:import url="/sys/right/mobile/edit_hidden.jsp" charEncoding="UTF-8">
                    <c:param name="formName" value="fsscExpenseBalanceForm" />
                    <c:param name="moduleModelName" value="com.landray.kmss.fssc.expense.model.FsscExpenseBalance" />
                </c:import>

            </html:form>
        </template:replace>
    </template:include>
