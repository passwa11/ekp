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
                <c:when test="${fsscExpenseShareMainForm.method_GET == 'add' }">
                    <c:out value="${ lfn:message('operation.create') } - ${ lfn:message('fssc-expense:table.fsscExpenseShareMain') }" />
                </c:when>
                <c:otherwise>
                    <c:out value="${fsscExpenseShareMainForm.docSubject} - " />
                    <c:out value="${lfn:message('fssc-expense:table.fsscExpenseShareMain') }" />
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

                    'fdDetailList': '${lfn:escapeJs(lfn:message("fssc-expense:table.fsscExpenseShareDetail"))}'
                };

                var initData = {
                    contextPath: '${LUI_ContextPath}',
                };
                Com_IncludeFile("security.js");
                Com_IncludeFile("domain.js");
                Com_IncludeFile("form_option.js", "${LUI_ContextPath}/fssc/expense/fssc_expense_share_main/", 'js', true);
                Com_IncludeFile("mobile_edit.js", "${LUI_ContextPath}/fssc/expense/resource/js/", 'js', true);
            </script>
        </template:replace>
        <template:replace name="content">
            <html:form action="/fssc/expense/fssc_expense_share_main/fsscExpenseShareMain.do?method=save">

                <div id="scrollView" data-dojo-type="mui/view/DocScrollableView" data-dojo-mixins="mui/form/_ValidateMixin">
                    <div data-dojo-type="mui/panel/AccordionPanel">
                        <div data-dojo-type="mui/panel/Content" data-dojo-props="title:'${lfn:message('fssc-expense:py.JiBenXinXi') }',icon:'mui-ul'">
                            <div class="muiFormContent">
                                <table class="muiSimple" cellpadding="0" cellspacing="0">
                                    <tr>
                                        <td class="muiTitle">
                                            ${lfn:message('fssc-expense:fsscExpenseShareMain.docSubject')}
                                        </td>
                                        <td>
                                            <div id="_xform_docSubject" _xform_type="text">
                                                <xform:text property="docSubject" showStatus="edit" mobile="true" style="width:95%;" />
                                            </div>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td class="muiTitle">
                                            ${lfn:message('fssc-expense:fsscExpenseShareMain.docCreator')}
                                        </td>
                                        <td>
                                            <div id="_xform_docCreatorId" _xform_type="address">
                                                <ui:person personId="${fsscExpenseShareMainForm.docCreatorId}" personName="${fsscExpenseShareMainForm.docCreatorName}" />
                                            </div>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td class="muiTitle">
                                            ${lfn:message('fssc-expense:fsscExpenseShareMain.fdOperator')}
                                        </td>
                                        <td>
                                            <div id="_xform_fdOperatorId" _xform_type="address">
                                                <xform:address propertyId="fdOperatorId" propertyName="fdOperatorName" orgType="ORG_TYPE_PERSON" showStatus="edit" mobile="true" style="width:95%;" />
                                            </div>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td class="muiTitle">
                                            ${lfn:message('fssc-expense:fsscExpenseShareMain.fdOperatorDept')}
                                        </td>
                                        <td>
                                            <div id="_xform_fdOperatorDeptId" _xform_type="address">
                                                <xform:address propertyId="fdOperatorDeptId" propertyName="fdOperatorDeptName" orgType="ORG_TYPE_ALL" showStatus="edit" mobile="true" style="width:95%;" />
                                            </div>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td class="muiTitle">
                                            ${lfn:message('fssc-expense:fsscExpenseShareMain.fdExpenseMain')}
                                        </td>
                                        <td>
                                            <div id="_xform_fdExpenseMainId" _xform_type="dialog">
                                                <div data-dojo-type="mui/form/CommonDialog" data-dojo-props="idField:'fdExpenseMainId',nameField:'fdExpenseMainName',isMul:false,modelName:'com.landray.kmss.fssc.expense.model.FsscExpenseMain',dataURL:getSource('fssc_expense_main_getExpenseMain'),subject:'${lfn:message('fssc-expense:fsscExpenseShareMain.fdExpenseMain')}',curIds:'${lfn:escapeHtml(lfn:escapeJs(fsscExpenseShareMainForm.fdExpenseMainId))}',curNames:'${lfn:escapeHtml(lfn:escapeJs(fsscExpenseShareMainForm.fdExpenseMainName))}',afterSelect:afterDialogSel">
                                                </div>
                                            </div>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td class="muiTitle">
                                            ${lfn:message('fssc-expense:fsscExpenseShareMain.fdOperateDate')}
                                        </td>
                                        <td>
                                            <div id="_xform_fdOperateDate" _xform_type="datetime">
                                                <xform:datetime property="fdOperateDate" showStatus="edit" dateTimeType="date" mobile="true" style="width:95%;" />
                                            </div>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td class="muiTitle">
                                            ${lfn:message('fssc-expense:fsscExpenseShareMain.fdDescription')}
                                        </td>
                                        <td>
                                            <div id="_xform_fdDescription" _xform_type="textarea">
                                                <xform:textarea property="fdDescription" showStatus="edit" mobile="true" style="width:95%;" />
                                            </div>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td class="muiTitle">
                                            ${lfn:message('fssc-expense:fsscExpenseShareMain.docNumber')}
                                        </td>
                                        <td>
                                            <div id="_xform_docNumber" _xform_type="text">
                                                <xform:text property="docNumber" showStatus="view" mobile="true" style="width:95%;" />
                                            </div>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td colspan="2">
                                            <div data-dojo-type="dojox/mobile/ListItem" data-dojo-props='rightIcon:"mui mui-forward",clickable:true, noArrow:true, _setIconAttr:function(){},onClick:function(){expandDetail("TABLE_DocList_fdDetailList_Form","scrollView");}'>
                                                <div layout="left">${lfn:message('fssc-expense:fsscExpenseShareMain.fdDetailList')}
                                                </div>
                                                <div layout="right">${lfn:message('fssc-expense:fssc.expense.detail.view')}
                                                </div>
                                            </div>
                                            <div class="detailTableView" data-dojo-type="mui/table/DetailTableScrollableView" data-dojo-mixins="mui/form/_ValidateMixin" id="TABLE_DocList_fdDetailList_Form_view">
                                                <div data-dojo-type="dojox/mobile/Heading" fixed="top" class="muiHeaderDetail">
                                                    <div class="muiHeaderDetailTitle">${lfn:message('fssc-expense:fsscExpenseShareMain.fdDetailList')}
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
                                                                                    ${lfn:message('fssc-expense:fsscExpenseShareDetail.fdCompany')}
                                                                                </td>
                                                                                <td>
                                                                                    <div id="_xform_fdDetailList_Form[!{index}].fdCompanyId" _xform_type="dialog">
                                                                                        <div data-dojo-type="mui/form/CommonDialog" data-dojo-props="idField:'fdDetailList_Form[!{index}].fdCompanyId',nameField:'fdDetailList_Form[!{index}].fdCompanyName',isMul:false,modelName:'com.landray.kmss.eop.basedata.model.EopBasedataCompany',dataURL:getSource('eop_basedata_company_fdCompany'),subject:'${lfn:message('fssc-expense:fsscExpenseShareDetail.fdCompany')}',required:true,afterSelect:afterDialogSel">
                                                                                        </div>
                                                                                    </div>
                                                                                </td>
                                                                            </tr>
                                                                            <tr>
                                                                                <td class="muiTitle">
                                                                                    ${lfn:message('fssc-expense:fsscExpenseShareDetail.fdCostCenter')}
                                                                                </td>
                                                                                <td>
                                                                                    <div id="_xform_fdDetailList_Form[!{index}].fdCostCenterId" _xform_type="dialog">
                                                                                        <div data-dojo-type="mui/form/CommonDialog" data-dojo-props="idField:'fdDetailList_Form[!{index}].fdCostCenterId',nameField:'fdDetailList_Form[!{index}].fdCostCenterName',isMul:false,modelName:'com.landray.kmss.eop.basedata.model.EopBasedataCostCenter',dataURL:getSource('eop_basedata_cost_center_selectCostCenter'),subject:'${lfn:message('fssc-expense:fsscExpenseShareDetail.fdCostCenter')}',required:true,afterSelect:afterDialogSel">
                                                                                        </div>
                                                                                    </div>
                                                                                </td>
                                                                            </tr>
                                                                            <tr>
                                                                                <td class="muiTitle">
                                                                                    ${lfn:message('fssc-expense:fsscExpenseShareDetail.fdCostDept')}
                                                                                </td>
                                                                                <td>
                                                                                    <div id="_xform_fdDetailList_Form[!{index}].fdCostDeptId" _xform_type="dialog">
                                                                                        <div data-dojo-type="mui/form/CommonDialog" data-dojo-props="idField:'fdDetailList_Form[!{index}].fdCostDeptId',nameField:'fdDetailList_Form[!{index}].fdCostDeptName',isMul:false,modelName:'com.landray.kmss.eop.basedata.model.EopBasedataCostCenter',dataURL:getSource('eop_basedata_cost_center_selectCostCenter'),subject:'${lfn:message('fssc-expense:fsscExpenseShareDetail.fdCostDept')}',afterSelect:afterDialogSel">
                                                                                        </div>
                                                                                    </div>
                                                                                </td>
                                                                            </tr>
                                                                            <tr>
                                                                                <td class="muiTitle">
                                                                                    ${lfn:message('fssc-expense:fsscExpenseShareDetail.fdExpenseItem')}
                                                                                </td>
                                                                                <td>
                                                                                    <div id="_xform_fdDetailList_Form[!{index}].fdExpenseItemId" _xform_type="dialog">
                                                                                        <div data-dojo-type="mui/form/CommonDialog" data-dojo-props="idField:'fdDetailList_Form[!{index}].fdExpenseItemId',nameField:'fdDetailList_Form[!{index}].fdExpenseItemName',isMul:false,modelName:'com.landray.kmss.eop.basedata.model.EopBasedataExpenseItem',dataURL:getSource('eop_basedata_expense_item_fdParent'),subject:'${lfn:message('fssc-expense:fsscExpenseShareDetail.fdExpenseItem')}',afterSelect:afterDialogSel">
                                                                                        </div>
                                                                                    </div>
                                                                                </td>
                                                                            </tr>
                                                                            <tr>
                                                                                <td class="muiTitle">
                                                                                    ${lfn:message('fssc-expense:fsscExpenseShareDetail.fdHappenDate')}
                                                                                </td>
                                                                                <td>
                                                                                    <div id="_xform_fdDetailList_Form[!{index}].fdHappenDate" _xform_type="datetime">
                                                                                        <xform:datetime property="fdDetailList_Form[!{index}].fdHappenDate" showStatus="edit" dateTimeType="date" mobile="true" style="width:95%;" />
                                                                                    </div>
                                                                                </td>
                                                                            </tr>
                                                                            <tr>
                                                                                <td class="muiTitle">
                                                                                    ${lfn:message('fssc-expense:fsscExpenseShareDetail.fdMoney')}
                                                                                </td>
                                                                                <td>
                                                                                    <div id="_xform_fdDetailList_Form[!{index}].fdMoney" _xform_type="text">
                                                                                        <xform:text property="fdDetailList_Form[!{index}].fdMoney" showStatus="edit" mobile="true" style="width:95%;" />
                                                                                    </div>
                                                                                </td>
                                                                            </tr>
                                                                            <tr>
                                                                                <td class="muiTitle">
                                                                                    ${lfn:message('fssc-expense:fsscExpenseShareDetail.fdRate')}
                                                                                </td>
                                                                                <td>
                                                                                    <div id="_xform_fdDetailList_Form[!{index}].fdRate" _xform_type="text">
                                                                                        <xform:text property="fdDetailList_Form[!{index}].fdRate" showStatus="edit" mobile="true" style="width:95%;" />
                                                                                    </div>
                                                                                </td>
                                                                            </tr>
                                                                            <tr>
                                                                                <td class="muiTitle">
                                                                                    ${lfn:message('fssc-expense:fsscExpenseShareDetail.fdCurrency')}
                                                                                </td>
                                                                                <td>
                                                                                    <div id="_xform_fdDetailList_Form[!{index}].fdCurrencyId" _xform_type="dialog">
                                                                                        <div data-dojo-type="mui/form/CommonDialog" data-dojo-props="idField:'fdDetailList_Form[!{index}].fdCurrencyId',nameField:'fdDetailList_Form[!{index}].fdCurrencyName',isMul:false,modelName:'com.landray.kmss.eop.basedata.model.EopBasedataCurrency',dataURL:getSource('eop_basedata_currency_fdCurrency'),subject:'${lfn:message('fssc-expense:fsscExpenseShareDetail.fdCurrency')}',afterSelect:afterDialogSel">
                                                                                        </div>
                                                                                    </div>
                                                                                </td>
                                                                            </tr>
                                                                            <tr>
                                                                                <td class="muiTitle">
                                                                                    ${lfn:message('fssc-expense:fsscExpenseShareDetail.fdStandardMoney')}
                                                                                </td>
                                                                                <td>
                                                                                    <div id="_xform_fdDetailList_Form[!{index}].fdStandardMoney" _xform_type="text">
                                                                                        <xform:text property="fdDetailList_Form[!{index}].fdStandardMoney" showStatus="edit" mobile="true" style="width:95%;" />
                                                                                    </div>
                                                                                </td>
                                                                            </tr>
                                                                            <tr>
                                                                                <td class="muiTitle">
                                                                                    ${lfn:message('fssc-expense:fsscExpenseShareDetail.fdRemark')}
                                                                                </td>
                                                                                <td>
                                                                                    <div id="_xform_fdDetailList_Form[!{index}].fdRemark" _xform_type="text">
                                                                                        <xform:text property="fdDetailList_Form[!{index}].fdRemark" showStatus="readOnly" mobile="true" style="width:95%;" />
                                                                                    </div>
                                                                                </td>
                                                                            </tr>
                                                                        </table>
                                                                    </td>
                                                                </tr>
                                                                <c:forEach items="${fsscExpenseShareMainForm.fdDetailList_Form}" var="fdDetailList_FormItem" varStatus="vstatus">
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
                                                                                        ${lfn:message('fssc-expense:fsscExpenseShareDetail.fdCompany')}
                                                                                    </td>
                                                                                    <td>
                                                                                        <div id="_xform_fdDetailList_Form[${vstatus.index}].fdCompanyId" _xform_type="dialog">
                                                                                            <div data-dojo-type="mui/form/CommonDialog" data-dojo-props="idField:'fdDetailList_Form[${vstatus.index}].fdCompanyId',nameField:'fdDetailList_Form[${vstatus.index}].fdCompanyName',isMul:false,modelName:'com.landray.kmss.eop.basedata.model.EopBasedataCompany',dataURL:getSource('eop_basedata_company_fdCompany'),subject:'${lfn:message('fssc-expense:fsscExpenseShareDetail.fdCompany')}',required:true,curIds:'${lfn:escapeHtml(lfn:escapeJs(fsscExpenseShareMainForm.fdDetailList_Form[vstatus.index].fdCompanyId))}',curNames:'${lfn:escapeHtml(lfn:escapeJs(fsscExpenseShareMainForm.fdDetailList_Form[vstatus.index].fdCompanyName))}',afterSelect:afterDialogSel">
                                                                                            </div>
                                                                                        </div>
                                                                                    </td>
                                                                                </tr>
                                                                                <tr>
                                                                                    <td class="muiTitle">
                                                                                        ${lfn:message('fssc-expense:fsscExpenseShareDetail.fdCostCenter')}
                                                                                    </td>
                                                                                    <td>
                                                                                        <div id="_xform_fdDetailList_Form[${vstatus.index}].fdCostCenterId" _xform_type="dialog">
                                                                                            <div data-dojo-type="mui/form/CommonDialog" data-dojo-props="idField:'fdDetailList_Form[${vstatus.index}].fdCostCenterId',nameField:'fdDetailList_Form[${vstatus.index}].fdCostCenterName',isMul:false,modelName:'com.landray.kmss.eop.basedata.model.EopBasedataCostCenter',dataURL:getSource('eop_basedata_cost_center_selectCostCenter'),subject:'${lfn:message('fssc-expense:fsscExpenseShareDetail.fdCostCenter')}',required:true,curIds:'${lfn:escapeHtml(lfn:escapeJs(fsscExpenseShareMainForm.fdDetailList_Form[vstatus.index].fdCostCenterId))}',curNames:'${lfn:escapeHtml(lfn:escapeJs(fsscExpenseShareMainForm.fdDetailList_Form[vstatus.index].fdCostCenterName))}',afterSelect:afterDialogSel">
                                                                                            </div>
                                                                                        </div>
                                                                                    </td>
                                                                                </tr>
                                                                                <tr>
                                                                                    <td class="muiTitle">
                                                                                        ${lfn:message('fssc-expense:fsscExpenseShareDetail.fdCostDept')}
                                                                                    </td>
                                                                                    <td>
                                                                                        <div id="_xform_fdDetailList_Form[${vstatus.index}].fdCostDeptId" _xform_type="dialog">
                                                                                            <div data-dojo-type="mui/form/CommonDialog" data-dojo-props="idField:'fdDetailList_Form[${vstatus.index}].fdCostDeptId',nameField:'fdDetailList_Form[${vstatus.index}].fdCostDeptName',isMul:false,modelName:'com.landray.kmss.eop.basedata.model.EopBasedataCostCenter',dataURL:getSource('eop_basedata_cost_center_selectCostCenter'),subject:'${lfn:message('fssc-expense:fsscExpenseShareDetail.fdCostDept')}',curIds:'${lfn:escapeHtml(lfn:escapeJs(fsscExpenseShareMainForm.fdDetailList_Form[vstatus.index].fdCostDeptId))}',curNames:'${lfn:escapeHtml(lfn:escapeJs(fsscExpenseShareMainForm.fdDetailList_Form[vstatus.index].fdCostDeptName))}',afterSelect:afterDialogSel">
                                                                                            </div>
                                                                                        </div>
                                                                                    </td>
                                                                                </tr>
                                                                                <tr>
                                                                                    <td class="muiTitle">
                                                                                        ${lfn:message('fssc-expense:fsscExpenseShareDetail.fdExpenseItem')}
                                                                                    </td>
                                                                                    <td>
                                                                                        <div id="_xform_fdDetailList_Form[${vstatus.index}].fdExpenseItemId" _xform_type="dialog">
                                                                                            <div data-dojo-type="mui/form/CommonDialog" data-dojo-props="idField:'fdDetailList_Form[${vstatus.index}].fdExpenseItemId',nameField:'fdDetailList_Form[${vstatus.index}].fdExpenseItemName',isMul:false,modelName:'com.landray.kmss.eop.basedata.model.EopBasedataExpenseItem',dataURL:getSource('eop_basedata_expense_item_fdParent'),subject:'${lfn:message('fssc-expense:fsscExpenseShareDetail.fdExpenseItem')}',curIds:'${lfn:escapeHtml(lfn:escapeJs(fsscExpenseShareMainForm.fdDetailList_Form[vstatus.index].fdExpenseItemId))}',curNames:'${lfn:escapeHtml(lfn:escapeJs(fsscExpenseShareMainForm.fdDetailList_Form[vstatus.index].fdExpenseItemName))}',afterSelect:afterDialogSel">
                                                                                            </div>
                                                                                        </div>
                                                                                    </td>
                                                                                </tr>
                                                                                <tr>
                                                                                    <td class="muiTitle">
                                                                                        ${lfn:message('fssc-expense:fsscExpenseShareDetail.fdHappenDate')}
                                                                                    </td>
                                                                                    <td>
                                                                                        <div id="_xform_fdDetailList_Form[${vstatus.index}].fdHappenDate" _xform_type="datetime">
                                                                                            <xform:datetime property="fdDetailList_Form[${vstatus.index}].fdHappenDate" showStatus="edit" dateTimeType="date" mobile="true" style="width:95%;" />
                                                                                        </div>
                                                                                    </td>
                                                                                </tr>
                                                                                <tr>
                                                                                    <td class="muiTitle">
                                                                                        ${lfn:message('fssc-expense:fsscExpenseShareDetail.fdMoney')}
                                                                                    </td>
                                                                                    <td>
                                                                                        <div id="_xform_fdDetailList_Form[${vstatus.index}].fdMoney" _xform_type="text">
                                                                                            <xform:text property="fdDetailList_Form[${vstatus.index}].fdMoney" showStatus="edit" mobile="true" style="width:95%;" />
                                                                                        </div>
                                                                                    </td>
                                                                                </tr>
                                                                                <tr>
                                                                                    <td class="muiTitle">
                                                                                        ${lfn:message('fssc-expense:fsscExpenseShareDetail.fdRate')}
                                                                                    </td>
                                                                                    <td>
                                                                                        <div id="_xform_fdDetailList_Form[${vstatus.index}].fdRate" _xform_type="text">
                                                                                            <xform:text property="fdDetailList_Form[${vstatus.index}].fdRate" showStatus="edit" mobile="true" style="width:95%;" />
                                                                                        </div>
                                                                                    </td>
                                                                                </tr>
                                                                                <tr>
                                                                                    <td class="muiTitle">
                                                                                        ${lfn:message('fssc-expense:fsscExpenseShareDetail.fdCurrency')}
                                                                                    </td>
                                                                                    <td>
                                                                                        <div id="_xform_fdDetailList_Form[${vstatus.index}].fdCurrencyId" _xform_type="dialog">
                                                                                            <div data-dojo-type="mui/form/CommonDialog" data-dojo-props="idField:'fdDetailList_Form[${vstatus.index}].fdCurrencyId',nameField:'fdDetailList_Form[${vstatus.index}].fdCurrencyName',isMul:false,modelName:'com.landray.kmss.eop.basedata.model.EopBasedataCurrency',dataURL:getSource('eop_basedata_currency_fdCurrency'),subject:'${lfn:message('fssc-expense:fsscExpenseShareDetail.fdCurrency')}',curIds:'${lfn:escapeHtml(lfn:escapeJs(fsscExpenseShareMainForm.fdDetailList_Form[vstatus.index].fdCurrencyId))}',curNames:'${lfn:escapeHtml(lfn:escapeJs(fsscExpenseShareMainForm.fdDetailList_Form[vstatus.index].fdCurrencyName))}',afterSelect:afterDialogSel">
                                                                                            </div>
                                                                                        </div>
                                                                                    </td>
                                                                                </tr>
                                                                                <tr>
                                                                                    <td class="muiTitle">
                                                                                        ${lfn:message('fssc-expense:fsscExpenseShareDetail.fdStandardMoney')}
                                                                                    </td>
                                                                                    <td>
                                                                                        <div id="_xform_fdDetailList_Form[${vstatus.index}].fdStandardMoney" _xform_type="text">
                                                                                            <xform:text property="fdDetailList_Form[${vstatus.index}].fdStandardMoney" showStatus="edit" mobile="true" style="width:95%;" />
                                                                                        </div>
                                                                                    </td>
                                                                                </tr>
                                                                                <tr>
                                                                                    <td class="muiTitle">
                                                                                        ${lfn:message('fssc-expense:fsscExpenseShareDetail.fdRemark')}
                                                                                    </td>
                                                                                    <td>
                                                                                        <div id="_xform_fdDetailList_Form[${vstatus.index}].fdRemark" _xform_type="text">
                                                                                            <xform:text property="fdDetailList_Form[${vstatus.index}].fdRemark" showStatus="readOnly" mobile="true" style="width:95%;" />
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
                    <c:param name="formName" value="fsscExpenseShareMainForm" />
                    <c:param name="fdKey" value="fsscExpenseShareMain" />
                    <c:param name="viewName" value="lbpmView" />
                    <c:param name="backTo" value="scrollView" />
                    <c:param name="onClickSubmitButton" value="form_submit();" />
                </c:import>

                <c:import url="/sys/right/mobile/edit_hidden.jsp" charEncoding="UTF-8">
                    <c:param name="formName" value="fsscExpenseShareMainForm" />
                    <c:param name="moduleModelName" value="com.landray.kmss.fssc.expense.model.FsscExpenseShareMain" />
                </c:import>

            </html:form>
        </template:replace>
    </template:include>
