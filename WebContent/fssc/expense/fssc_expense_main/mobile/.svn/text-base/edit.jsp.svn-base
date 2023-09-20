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
                <c:when test="${fsscExpenseMainForm.method_GET == 'add' }">
                    <c:out value="${ lfn:message('operation.create') } - ${ lfn:message('fssc-expense:table.fsscExpenseMain') }" />
                </c:when>
                <c:otherwise>
                    <c:out value="${fsscExpenseMainForm.docSubject} - " />
                    <c:out value="${lfn:message('fssc-expense:table.fsscExpenseMain') }" />
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

                    'fdInvoiceList': '${lfn:escapeJs(lfn:message("fssc-expense:table.fsscExpenseInvoiceDetail"))}',
                    'fdTravelList': '${lfn:escapeJs(lfn:message("fssc-expense:table.fsscExpenseTravelDetail"))}',
                    'fdDetailList': '${lfn:escapeJs(lfn:message("fssc-expense:table.fsscExpenseDetail"))}',
                    'fdAccountsList': '${lfn:escapeJs(lfn:message("fssc-expense:table.fsscExpenseAccounts"))}',
                    'fdOffsetList': '${lfn:escapeJs(lfn:message("fssc-expense:table.fsscExpenseOffsetLoan"))}'
                };

                var initData = {
                    contextPath: '${LUI_ContextPath}',
                };
                Com_IncludeFile("security.js");
                Com_IncludeFile("domain.js");
                Com_IncludeFile("form_option.js", "${LUI_ContextPath}/fssc/expense/fssc_expense_main/", 'js', true);
                Com_IncludeFile("mobile_edit.js", "${LUI_ContextPath}/fssc/expense/resource/js/", 'js', true);
            </script>
        </template:replace>
        <template:replace name="content">
            <html:form action="/fssc/expense/fssc_expense_main/fsscExpenseMain.do?method=save">

                <div id="scrollView" data-dojo-type="mui/view/DocScrollableView" data-dojo-mixins="mui/form/_ValidateMixin">
                    <div data-dojo-type="mui/panel/AccordionPanel">
                        <div data-dojo-type="mui/panel/Content" data-dojo-props="title:'${lfn:message('fssc-expense:py.JiBenXinXi') }',icon:'mui-ul'">
                            <div class="muiFormContent">
                                <table class="muiSimple" cellpadding="0" cellspacing="0">
                                    <tr>
                                        <td class="muiTitle">
                                            ${lfn:message('fssc-expense:fsscExpenseMain.docSubject')}
                                        </td>
                                        <td>
                                            <div id="_xform_docSubject" _xform_type="text">
                                                <xform:text property="docSubject" showStatus="edit" mobile="true" style="width:95%;" />
                                            </div>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td class="muiTitle">
                                            ${lfn:message('fssc-expense:fsscExpenseMain.fdClaimant')}
                                        </td>
                                        <td>
                                            <div id="_xform_fdClaimantId" _xform_type="address">
                                                <xform:address propertyId="fdClaimantId" propertyName="fdClaimantName" orgType="ORG_TYPE_PERSON" showStatus="edit" required="true" subject="${lfn:message('fssc-expense:fsscExpenseMain.fdClaimant')}" mobile="true" style="width:95%;" />
                                            </div>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td class="muiTitle">
                                            ${lfn:message('fssc-expense:fsscExpenseMain.fdCompany')}
                                        </td>
                                        <td>
                                            <div id="_xform_fdCompanyId" _xform_type="dialog">
                                                <div data-dojo-type="mui/form/CommonDialog" data-dojo-props="idField:'fdCompanyId',nameField:'fdCompanyName',isMul:false,modelName:'com.landray.kmss.eop.basedata.model.EopBasedataCompany',dataURL:getSource('eop_basedata_company_fdCompany'),subject:'${lfn:message('fssc-expense:fsscExpenseMain.fdCompany')}',required:true,curIds:'${lfn:escapeHtml(lfn:escapeJs(fsscExpenseMainForm.fdCompanyId))}',curNames:'${lfn:escapeHtml(lfn:escapeJs(fsscExpenseMainForm.fdCompanyName))}',afterSelect:afterDialogSel">
                                                </div>
                                            </div>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td class="muiTitle">
                                            ${lfn:message('fssc-expense:fsscExpenseMain.fdCostCenter')}
                                        </td>
                                        <td>
                                            <div id="_xform_fdCostCenterId" _xform_type="dialog">
                                                <div data-dojo-type="mui/form/CommonDialog" data-dojo-props="idField:'fdCostCenterId',nameField:'fdCostCenterName',isMul:false,modelName:'com.landray.kmss.eop.basedata.model.EopBasedataCostCenter',dataURL:getSource('eop_basedata_cost_center_selectCostCenter'),subject:'${lfn:message('fssc-expense:fsscExpenseMain.fdCostCenter')}',required:true,curIds:'${lfn:escapeHtml(lfn:escapeJs(fsscExpenseMainForm.fdCostCenterId))}',curNames:'${lfn:escapeHtml(lfn:escapeJs(fsscExpenseMainForm.fdCostCenterName))}',afterSelect:afterDialogSel">
                                                </div>
                                            </div>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td class="muiTitle">
                                            ${lfn:message('fssc-expense:fsscExpenseMain.fdContent')}
                                        </td>
                                        <td>
                                            <div id="_xform_fdContent" _xform_type="textarea">
                                                <xform:textarea property="fdContent" showStatus="edit" mobile="true" style="width:95%;" />
                                            </div>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td class="muiTitle">
                                            ${lfn:message('fssc-expense:fsscExpenseMain.fdTotalStandaryMoney')}
                                        </td>
                                        <td>
                                            <div id="_xform_fdTotalStandaryMoney" _xform_type="text">
                                                <xform:text property="fdTotalStandaryMoney" showStatus="edit" mobile="true" style="width:95%;" />
                                            </div>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td class="muiTitle">
                                            ${lfn:message('fssc-expense:fsscExpenseMain.fdTotalApprovedMoney')}
                                        </td>
                                        <td>
                                            <div id="_xform_fdTotalApprovedMoney" _xform_type="text">
                                                <xform:text property="fdTotalApprovedMoney" showStatus="edit" mobile="true" style="width:95%;" />
                                            </div>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td colspan="2">
                                            <div data-dojo-type="dojox/mobile/ListItem" data-dojo-props='rightIcon:"mui mui-forward",clickable:true, noArrow:true, _setIconAttr:function(){},onClick:function(){expandDetail("TABLE_DocList_fdTravelList_Form","scrollView");}'>
                                                <div layout="left">${lfn:message('fssc-expense:fsscExpenseMain.fdTravelList')}
                                                </div>
                                                <div layout="right">${lfn:message('fssc-expense:fssc.expense.detail.view')}
                                                </div>
                                            </div>
                                            <div class="detailTableView" data-dojo-type="mui/table/DetailTableScrollableView" data-dojo-mixins="mui/form/_ValidateMixin" id="TABLE_DocList_fdTravelList_Form_view">
                                                <div data-dojo-type="dojox/mobile/Heading" fixed="top" class="muiHeaderDetail">
                                                    <div class="muiHeaderDetailTitle">${lfn:message('fssc-expense:fsscExpenseMain.fdTravelList')}
                                                    </div>
                                                    <div class="muiHeaderDetailBack" onclick="collapseDetail('TABLE_DocList_fdTravelList_Form')">
                                                        <bean:message key="button.save" />
                                                    </div>
                                                </div>
                                                <br/>
                                                <br/>
                                                <table cellspacing="0" cellpadding="0" class="detailTableSimple">
                                                    <tr>
                                                        <td class="detailTableSimpleTd">
                                                            <table width="100%" border="0" cellspacing="0" cellpadding="0" id="TABLE_DocList_fdTravelList_Form">
                                                                <tr style="display:none;">
                                                                    <td>
                                                                    </td>
                                                                </tr>
                                                                <tr data-dojo-type="mui/form/Template" KMSS_IsReferRow="1" style="display:none;" border='0'>
                                                                    <td class="detail_wrap_td">
                                                                        <xform:text showStatus="noShow" property="fdTravelList_Form[!{index}].fdId" />
                                                                        <table class="muiSimple">
                                                                            <tr>
                                                                                <td colspan="2" align="left" valign="middle" class="muiDetailTableNo">
                                                                                    <span>${lfn:message('page.the')}!{index}${ lfn:message('page.row') }</span>
                                                                                    <div class="muiDetailTableDel" onclick="deleteDetailRow('TABLE_DocList_fdTravelList_Form',this);">
                                                                                        <i class="mui mui-close"></i>
                                                                                    </div>
                                                                                </td>
                                                                            </tr>
                                                                            <tr>
                                                                                <td class="muiTitle">
                                                                                    ${lfn:message('fssc-expense:fsscExpenseTravelDetail.fdBeginDate')}
                                                                                </td>
                                                                                <td>
                                                                                    <div id="_xform_fdTravelList_Form[!{index}].fdBeginDate" _xform_type="datetime">
                                                                                        <xform:datetime property="fdTravelList_Form[!{index}].fdBeginDate" showStatus="edit" dateTimeType="date" mobile="true" style="width:95%;" />
                                                                                    </div>
                                                                                </td>
                                                                            </tr>
                                                                            <tr>
                                                                                <td class="muiTitle">
                                                                                    ${lfn:message('fssc-expense:fsscExpenseTravelDetail.fdEndDate')}
                                                                                </td>
                                                                                <td>
                                                                                    <div id="_xform_fdTravelList_Form[!{index}].fdEndDate" _xform_type="datetime">
                                                                                        <xform:datetime property="fdTravelList_Form[!{index}].fdEndDate" showStatus="edit" dateTimeType="date" mobile="true" style="width:95%;" />
                                                                                    </div>
                                                                                </td>
                                                                            </tr>
                                                                            <tr>
                                                                                <td class="muiTitle">
                                                                                    ${lfn:message('fssc-expense:fsscExpenseTravelDetail.fdTravelDays')}
                                                                                </td>
                                                                                <td>
                                                                                    <div id="_xform_fdTravelList_Form[!{index}].fdTravelDays" _xform_type="text">
                                                                                        <xform:text property="fdTravelList_Form[!{index}].fdTravelDays" showStatus="edit" mobile="true" value="0" style="width:95%;" />
                                                                                    </div>
                                                                                </td>
                                                                            </tr>
                                                                            <tr>
                                                                                <td class="muiTitle">
                                                                                    ${lfn:message('fssc-expense:fsscExpenseTravelDetail.fdStartPlace')}
                                                                                </td>
                                                                                <td>
                                                                                    <div id="_xform_fdTravelList_Form[!{index}].fdStartPlace" _xform_type="text">
                                                                                        <xform:text property="fdTravelList_Form[!{index}].fdStartPlace" showStatus="edit" subject="${lfn:message('fssc-expense:fsscExpenseTravelDetail.fdStartPlace')}" validators=" maxLength(200)" mobile="true" style="width:95%;" />
                                                                                    </div>
                                                                                </td>
                                                                            </tr>
                                                                            <tr>
                                                                                <td class="muiTitle">
                                                                                    ${lfn:message('fssc-expense:fsscExpenseTravelDetail.fdArrivalId')}
                                                                                </td>
                                                                                <td>
                                                                                    <div id="_xform_fdTravelList_Form[!{index}].fdArrivalId" _xform_type="text">
                                                                                        <xform:text property="fdTravelList_Form[!{index}].fdArrivalId" showStatus="edit" subject="${lfn:message('fssc-expense:fsscExpenseTravelDetail.fdArrivalId')}" validators=" maxLength(36)" mobile="true" style="width:95%;" />
                                                                                    </div>
                                                                                </td>
                                                                            </tr>
                                                                            <tr>
                                                                                <td class="muiTitle">
                                                                                    ${lfn:message('fssc-expense:fsscExpenseTravelDetail.fdArrivalPlace')}
                                                                                </td>
                                                                                <td>
                                                                                    <div id="_xform_fdTravelList_Form[!{index}].fdArrivalPlace" _xform_type="text">
                                                                                        <xform:text property="fdTravelList_Form[!{index}].fdArrivalPlace" showStatus="edit" subject="${lfn:message('fssc-expense:fsscExpenseTravelDetail.fdArrivalPlace')}" validators=" maxLength(200)" mobile="true" style="width:95%;" />
                                                                                    </div>
                                                                                </td>
                                                                            </tr>
                                                                            <tr>
                                                                                <td class="muiTitle">
                                                                                    ${lfn:message('fssc-expense:fsscExpenseTravelDetail.fdVehicle')}
                                                                                </td>
                                                                                <td>
                                                                                    <div id="_xform_fdTravelList_Form[!{index}].fdVehicleId" _xform_type="dialog">
                                                                                        <div data-dojo-type="mui/form/CommonDialog" data-dojo-props="idField:'fdTravelList_Form[!{index}].fdVehicleId',nameField:'fdTravelList_Form[!{index}].fdVehicleName',isMul:false,modelName:'com.landray.kmss.eop.basedata.model.EopBasedataVehicle',dataURL:getSource('eop_basedata_vehicle_fdVehicle'),subject:'${lfn:message('fssc-expense:fsscExpenseTravelDetail.fdVehicle')}',afterSelect:afterDialogSel">
                                                                                        </div>
                                                                                    </div>
                                                                                </td>
                                                                            </tr>
                                                                            <tr>
                                                                                <td class="muiTitle">
                                                                                    ${lfn:message('fssc-expense:fsscExpenseTravelDetail.fdBerth')}
                                                                                </td>
                                                                                <td>
                                                                                    <div id="_xform_fdTravelList_Form[!{index}].fdBerthId" _xform_type="dialog">
                                                                                        <div data-dojo-type="mui/form/CommonDialog" data-dojo-props="idField:'fdTravelList_Form[!{index}].fdBerthId',nameField:'fdTravelList_Form[!{index}].fdBerthName',isMul:false,modelName:'com.landray.kmss.eop.basedata.model.EopBasedataBerth',dataURL:getSource('eop_basedata_berth_fdBerth'),subject:'${lfn:message('fssc-expense:fsscExpenseTravelDetail.fdBerth')}',afterSelect:afterDialogSel">
                                                                                        </div>
                                                                                    </div>
                                                                                </td>
                                                                            </tr>
                                                                        </table>
                                                                    </td>
                                                                </tr>
                                                                <c:forEach items="${fsscExpenseMainForm.fdTravelList_Form}" var="fdTravelList_FormItem" varStatus="vstatus">
                                                                    <tr KMSS_IsContentRow="1">
                                                                        <td class="detail_wrap_td">
                                                                            <xform:text showStatus="noShow" property="fdTravelList_Form[${vstatus.index}].fdId" />
                                                                            <table class="muiSimple">
                                                                                <tr>
                                                                                    <td colspan="2" align="left" valign="middle" class="muiDetailTableNo">
                                                                                        <span>${lfn:message('page.the')}${vstatus.index}${ lfn:message('page.row') }</span>
                                                                                        <div class="muiDetailTableDel" onclick="deleteDetailRow('TABLE_DocList_fdTravelList_Form',this);">
                                                                                            <i class="mui mui-close"></i>
                                                                                        </div>
                                                                                    </td>
                                                                                </tr>
                                                                                <tr>
                                                                                    <td class="muiTitle">
                                                                                        ${lfn:message('fssc-expense:fsscExpenseTravelDetail.fdBeginDate')}
                                                                                    </td>
                                                                                    <td>
                                                                                        <div id="_xform_fdTravelList_Form[${vstatus.index}].fdBeginDate" _xform_type="datetime">
                                                                                            <xform:datetime property="fdTravelList_Form[${vstatus.index}].fdBeginDate" showStatus="edit" dateTimeType="date" mobile="true" style="width:95%;" />
                                                                                        </div>
                                                                                    </td>
                                                                                </tr>
                                                                                <tr>
                                                                                    <td class="muiTitle">
                                                                                        ${lfn:message('fssc-expense:fsscExpenseTravelDetail.fdEndDate')}
                                                                                    </td>
                                                                                    <td>
                                                                                        <div id="_xform_fdTravelList_Form[${vstatus.index}].fdEndDate" _xform_type="datetime">
                                                                                            <xform:datetime property="fdTravelList_Form[${vstatus.index}].fdEndDate" showStatus="edit" dateTimeType="date" mobile="true" style="width:95%;" />
                                                                                        </div>
                                                                                    </td>
                                                                                </tr>
                                                                                <tr>
                                                                                    <td class="muiTitle">
                                                                                        ${lfn:message('fssc-expense:fsscExpenseTravelDetail.fdTravelDays')}
                                                                                    </td>
                                                                                    <td>
                                                                                        <div id="_xform_fdTravelList_Form[${vstatus.index}].fdTravelDays" _xform_type="text">
                                                                                            <xform:text property="fdTravelList_Form[${vstatus.index}].fdTravelDays" showStatus="edit" mobile="true" style="width:95%;" />
                                                                                        </div>
                                                                                    </td>
                                                                                </tr>
                                                                                <tr>
                                                                                    <td class="muiTitle">
                                                                                        ${lfn:message('fssc-expense:fsscExpenseTravelDetail.fdStartPlace')}
                                                                                    </td>
                                                                                    <td>
                                                                                        <div id="_xform_fdTravelList_Form[${vstatus.index}].fdStartPlace" _xform_type="text">
                                                                                            <xform:text property="fdTravelList_Form[${vstatus.index}].fdStartPlace" showStatus="edit" subject="${lfn:message('fssc-expense:fsscExpenseTravelDetail.fdStartPlace')}" validators=" maxLength(200)" mobile="true" style="width:95%;" />
                                                                                        </div>
                                                                                    </td>
                                                                                </tr>
                                                                                <tr>
                                                                                    <td class="muiTitle">
                                                                                        ${lfn:message('fssc-expense:fsscExpenseTravelDetail.fdArrivalId')}
                                                                                    </td>
                                                                                    <td>
                                                                                        <div id="_xform_fdTravelList_Form[${vstatus.index}].fdArrivalId" _xform_type="text">
                                                                                            <xform:text property="fdTravelList_Form[${vstatus.index}].fdArrivalId" showStatus="edit" subject="${lfn:message('fssc-expense:fsscExpenseTravelDetail.fdArrivalId')}" validators=" maxLength(36)" mobile="true" style="width:95%;" />
                                                                                        </div>
                                                                                    </td>
                                                                                </tr>
                                                                                <tr>
                                                                                    <td class="muiTitle">
                                                                                        ${lfn:message('fssc-expense:fsscExpenseTravelDetail.fdArrivalPlace')}
                                                                                    </td>
                                                                                    <td>
                                                                                        <div id="_xform_fdTravelList_Form[${vstatus.index}].fdArrivalPlace" _xform_type="text">
                                                                                            <xform:text property="fdTravelList_Form[${vstatus.index}].fdArrivalPlace" showStatus="edit" subject="${lfn:message('fssc-expense:fsscExpenseTravelDetail.fdArrivalPlace')}" validators=" maxLength(200)" mobile="true" style="width:95%;" />
                                                                                        </div>
                                                                                    </td>
                                                                                </tr>
                                                                                <tr>
                                                                                    <td class="muiTitle">
                                                                                        ${lfn:message('fssc-expense:fsscExpenseTravelDetail.fdVehicle')}
                                                                                    </td>
                                                                                    <td>
                                                                                        <div id="_xform_fdTravelList_Form[${vstatus.index}].fdVehicleId" _xform_type="dialog">
                                                                                            <div data-dojo-type="mui/form/CommonDialog" data-dojo-props="idField:'fdTravelList_Form[${vstatus.index}].fdVehicleId',nameField:'fdTravelList_Form[${vstatus.index}].fdVehicleName',isMul:false,modelName:'com.landray.kmss.eop.basedata.model.EopBasedataVehicle',dataURL:getSource('eop_basedata_vehicle_fdVehicle'),subject:'${lfn:message('fssc-expense:fsscExpenseTravelDetail.fdVehicle')}',curIds:'${lfn:escapeHtml(lfn:escapeJs(fsscExpenseMainForm.fdTravelList_Form[vstatus.index].fdVehicleId))}',curNames:'${lfn:escapeHtml(lfn:escapeJs(fsscExpenseMainForm.fdTravelList_Form[vstatus.index].fdVehicleName))}',afterSelect:afterDialogSel">
                                                                                            </div>
                                                                                        </div>
                                                                                    </td>
                                                                                </tr>
                                                                                <tr>
                                                                                    <td class="muiTitle">
                                                                                        ${lfn:message('fssc-expense:fsscExpenseTravelDetail.fdBerth')}
                                                                                    </td>
                                                                                    <td>
                                                                                        <div id="_xform_fdTravelList_Form[${vstatus.index}].fdBerthId" _xform_type="dialog">
                                                                                            <div data-dojo-type="mui/form/CommonDialog" data-dojo-props="idField:'fdTravelList_Form[${vstatus.index}].fdBerthId',nameField:'fdTravelList_Form[${vstatus.index}].fdBerthName',isMul:false,modelName:'com.landray.kmss.eop.basedata.model.EopBasedataBerth',dataURL:getSource('eop_basedata_berth_fdBerth'),subject:'${lfn:message('fssc-expense:fsscExpenseTravelDetail.fdBerth')}',curIds:'${lfn:escapeHtml(lfn:escapeJs(fsscExpenseMainForm.fdTravelList_Form[vstatus.index].fdBerthId))}',curNames:'${lfn:escapeHtml(lfn:escapeJs(fsscExpenseMainForm.fdTravelList_Form[vstatus.index].fdBerthName))}',afterSelect:afterDialogSel">
                                                                                            </div>
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
                                                    <li data-dojo-type="mui/tabbar/TabBarButton" class="muiBtnNext " data-dojo-props="colSize:4,onClick:function(){addDetailRow('TABLE_DocList_fdTravelList_Form');}, label:'${lfn:message('doclist.add')}'" style="width:95%">
                                                    </li>
                                                </ul>
                                            </div>
                                            <input type="hidden" name="fdTravelList_Flag" value="1">
                                            <script>
                                                Com_IncludeFile("doclist.js");
                                            </script>
                                            <script>
                                                DocList_Info.push('TABLE_DocList_fdTravelList_Form');
                                            </script>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td colspan="2">
                                            <div data-dojo-type="dojox/mobile/ListItem" data-dojo-props='rightIcon:"mui mui-forward",clickable:true, noArrow:true, _setIconAttr:function(){},onClick:function(){expandDetail("TABLE_DocList_fdDetailList_Form","scrollView");}'>
                                                <div layout="left">${lfn:message('fssc-expense:fsscExpenseMain.fdDetailList')}
                                                </div>
                                                <div layout="right">${lfn:message('fssc-expense:fssc.expense.detail.view')}
                                                </div>
                                            </div>
                                            <div class="detailTableView" data-dojo-type="mui/table/DetailTableScrollableView" data-dojo-mixins="mui/form/_ValidateMixin" id="TABLE_DocList_fdDetailList_Form_view">
                                                <div data-dojo-type="dojox/mobile/Heading" fixed="top" class="muiHeaderDetail">
                                                    <div class="muiHeaderDetailTitle">${lfn:message('fssc-expense:fsscExpenseMain.fdDetailList')}
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
                                                                                    ${lfn:message('fssc-expense:fsscExpenseDetail.fdCompany')}
                                                                                </td>
                                                                                <td>
                                                                                    <div id="_xform_fdDetailList_Form[!{index}].fdCompanyId" _xform_type="dialog">
                                                                                        <div data-dojo-type="mui/form/CommonDialog" data-dojo-props="idField:'fdDetailList_Form[!{index}].fdCompanyId',nameField:'fdDetailList_Form[!{index}].fdCompanyName',isMul:false,modelName:'com.landray.kmss.eop.basedata.model.EopBasedataCompany',dataURL:getSource('eop_basedata_company_fdCompany'),subject:'${lfn:message('fssc-expense:fsscExpenseDetail.fdCompany')}',required:true,afterSelect:afterDialogSel">
                                                                                        </div>
                                                                                    </div>
                                                                                </td>
                                                                            </tr>
                                                                            <tr>
                                                                                <td class="muiTitle">
                                                                                    ${lfn:message('fssc-expense:fsscExpenseDetail.fdCostCenter')}
                                                                                </td>
                                                                                <td>
                                                                                    <div id="_xform_fdDetailList_Form[!{index}].fdCostCenterId" _xform_type="dialog">
                                                                                        <div data-dojo-type="mui/form/CommonDialog" data-dojo-props="idField:'fdDetailList_Form[!{index}].fdCostCenterId',nameField:'fdDetailList_Form[!{index}].fdCostCenterName',isMul:false,modelName:'com.landray.kmss.eop.basedata.model.EopBasedataCostCenter',dataURL:getSource('eop_basedata_cost_center_selectCostCenter'),subject:'${lfn:message('fssc-expense:fsscExpenseDetail.fdCostCenter')}',required:true,afterSelect:afterDialogSel">
                                                                                        </div>
                                                                                    </div>
                                                                                </td>
                                                                            </tr>
                                                                            <tr>
                                                                                <td class="muiTitle">
                                                                                    ${lfn:message('fssc-expense:fsscExpenseDetail.fdRealUser')}
                                                                                </td>
                                                                                <td>
                                                                                    <div id="_xform_fdDetailList_Form[!{index}].fdRealUserId" _xform_type="address">
                                                                                        <xform:address propertyId="fdDetailList_Form[!{index}].fdRealUserId" propertyName="fdDetailList_Form[!{index}].fdRealUserName" orgType="ORG_TYPE_PERSON" showStatus="edit" required="true" subject="${lfn:message('fssc-expense:fsscExpenseDetail.fdRealUser')}"
                                                                                        mobile="true" style="width:95%;" />
                                                                                    </div>
                                                                                </td>
                                                                            </tr>
                                                                            <tr>
                                                                                <td class="muiTitle">
                                                                                    ${lfn:message('fssc-expense:fsscExpenseDetail.fdApplyMoney')}
                                                                                </td>
                                                                                <td>
                                                                                    <div id="_xform_fdDetailList_Form[!{index}].fdApplyMoney" _xform_type="text">
                                                                                        <xform:text property="fdDetailList_Form[!{index}].fdApplyMoney" showStatus="edit" required="true" subject="${lfn:message('fssc-expense:fsscExpenseDetail.fdApplyMoney')}" mobile="true" value="0" style="width:95%;" />
                                                                                    </div>
                                                                                </td>
                                                                            </tr>
                                                                            <tr>
                                                                                <td class="muiTitle">
                                                                                    ${lfn:message('fssc-expense:fsscExpenseDetail.fdStandardMoney')}
                                                                                </td>
                                                                                <td>
                                                                                    <div id="_xform_fdDetailList_Form[!{index}].fdStandardMoney" _xform_type="text">
                                                                                        <xform:text property="fdDetailList_Form[!{index}].fdStandardMoney" showStatus="edit" mobile="true" value="0" style="width:95%;" />
                                                                                    </div>
                                                                                </td>
                                                                            </tr>
                                                                            <tr>
                                                                                <td class="muiTitle">
                                                                                    ${lfn:message('fssc-expense:fsscExpenseDetail.fdCurrency')}
                                                                                </td>
                                                                                <td>
                                                                                    <div id="_xform_fdDetailList_Form[!{index}].fdCurrencyId" _xform_type="dialog">
                                                                                        <div data-dojo-type="mui/form/CommonDialog" data-dojo-props="idField:'fdDetailList_Form[!{index}].fdCurrencyId',nameField:'fdDetailList_Form[!{index}].fdCurrencyName',isMul:false,modelName:'com.landray.kmss.eop.basedata.model.EopBasedataCurrency',dataURL:getSource('eop_basedata_currency_fdCurrency'),subject:'${lfn:message('fssc-expense:fsscExpenseDetail.fdCurrency')}',afterSelect:afterDialogSel">
                                                                                        </div>
                                                                                    </div>
                                                                                </td>
                                                                            </tr>
                                                                            <tr>
                                                                                <td class="muiTitle">
                                                                                    ${lfn:message('fssc-expense:fsscExpenseDetail.fdExchangeRate')}
                                                                                </td>
                                                                                <td>
                                                                                    <div id="_xform_fdDetailList_Form[!{index}].fdExchangeRate" _xform_type="text">
                                                                                        <xform:text property="fdDetailList_Form[!{index}].fdExchangeRate" showStatus="edit" required="true" subject="${lfn:message('fssc-expense:fsscExpenseDetail.fdExchangeRate')}" mobile="true" style="width:95%;" />
                                                                                    </div>
                                                                                </td>
                                                                            </tr>
                                                                            <tr>
                                                                                <td class="muiTitle">
                                                                                    ${lfn:message('fssc-expense:fsscExpenseDetail.fdApprovedApplyMoney')}
                                                                                </td>
                                                                                <td>
                                                                                    <div id="_xform_fdDetailList_Form[!{index}].fdApprovedApplyMoney" _xform_type="text">
                                                                                        <xform:text property="fdDetailList_Form[!{index}].fdApprovedApplyMoney" showStatus="edit" mobile="true" style="width:95%;" />
                                                                                    </div>
                                                                                </td>
                                                                            </tr>
                                                                            <tr>
                                                                                <td class="muiTitle">
                                                                                    ${lfn:message('fssc-expense:fsscExpenseDetail.fdApprovedStandardMoney')}
                                                                                </td>
                                                                                <td>
                                                                                    <div id="_xform_fdDetailList_Form[!{index}].fdApprovedStandardMoney" _xform_type="text">
                                                                                        <xform:text property="fdDetailList_Form[!{index}].fdApprovedStandardMoney" showStatus="edit" mobile="true" style="width:95%;" />
                                                                                    </div>
                                                                                </td>
                                                                            </tr>
                                                                            <tr>
                                                                                <td class="muiTitle">
                                                                                    ${lfn:message('fssc-expense:fsscExpenseDetail.fdUse')}
                                                                                </td>
                                                                                <td>
                                                                                    <div id="_xform_fdDetailList_Form[!{index}].fdUse" _xform_type="text">
                                                                                        <xform:text property="fdDetailList_Form[!{index}].fdUse" showStatus="edit" mobile="true" style="width:95%;" />
                                                                                    </div>
                                                                                </td>
                                                                            </tr>
                                                                        </table>
                                                                    </td>
                                                                </tr>
                                                                <c:forEach items="${fsscExpenseMainForm.fdDetailList_Form}" var="fdDetailList_FormItem" varStatus="vstatus">
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
                                                                                        ${lfn:message('fssc-expense:fsscExpenseDetail.fdCompany')}
                                                                                    </td>
                                                                                    <td>
                                                                                        <div id="_xform_fdDetailList_Form[${vstatus.index}].fdCompanyId" _xform_type="dialog">
                                                                                            <div data-dojo-type="mui/form/CommonDialog" data-dojo-props="idField:'fdDetailList_Form[${vstatus.index}].fdCompanyId',nameField:'fdDetailList_Form[${vstatus.index}].fdCompanyName',isMul:false,modelName:'com.landray.kmss.eop.basedata.model.EopBasedataCompany',dataURL:getSource('eop_basedata_company_fdCompany'),subject:'${lfn:message('fssc-expense:fsscExpenseDetail.fdCompany')}',required:true,curIds:'${lfn:escapeHtml(lfn:escapeJs(fsscExpenseMainForm.fdDetailList_Form[vstatus.index].fdCompanyId))}',curNames:'${lfn:escapeHtml(lfn:escapeJs(fsscExpenseMainForm.fdDetailList_Form[vstatus.index].fdCompanyName))}',afterSelect:afterDialogSel">
                                                                                            </div>
                                                                                        </div>
                                                                                    </td>
                                                                                </tr>
                                                                                <tr>
                                                                                    <td class="muiTitle">
                                                                                        ${lfn:message('fssc-expense:fsscExpenseDetail.fdCostCenter')}
                                                                                    </td>
                                                                                    <td>
                                                                                        <div id="_xform_fdDetailList_Form[${vstatus.index}].fdCostCenterId" _xform_type="dialog">
                                                                                            <div data-dojo-type="mui/form/CommonDialog" data-dojo-props="idField:'fdDetailList_Form[${vstatus.index}].fdCostCenterId',nameField:'fdDetailList_Form[${vstatus.index}].fdCostCenterName',isMul:false,modelName:'com.landray.kmss.eop.basedata.model.EopBasedataCostCenter',dataURL:getSource('eop_basedata_cost_center_selectCostCenter'),subject:'${lfn:message('fssc-expense:fsscExpenseDetail.fdCostCenter')}',required:true,curIds:'${lfn:escapeHtml(lfn:escapeJs(fsscExpenseMainForm.fdDetailList_Form[vstatus.index].fdCostCenterId))}',curNames:'${lfn:escapeHtml(lfn:escapeJs(fsscExpenseMainForm.fdDetailList_Form[vstatus.index].fdCostCenterName))}',afterSelect:afterDialogSel">
                                                                                            </div>
                                                                                        </div>
                                                                                    </td>
                                                                                </tr>
                                                                                <tr>
                                                                                    <td class="muiTitle">
                                                                                        ${lfn:message('fssc-expense:fsscExpenseDetail.fdRealUser')}
                                                                                    </td>
                                                                                    <td>
                                                                                        <div id="_xform_fdDetailList_Form[${vstatus.index}].fdRealUserId" _xform_type="address">
                                                                                            <xform:address propertyId="fdDetailList_Form[${vstatus.index}].fdRealUserId" propertyName="fdDetailList_Form[${vstatus.index}].fdRealUserName" orgType="ORG_TYPE_PERSON" showStatus="edit" required="true" subject="${lfn:message('fssc-expense:fsscExpenseDetail.fdRealUser')}"
                                                                                            mobile="true" style="width:95%;" />
                                                                                        </div>
                                                                                    </td>
                                                                                </tr>
                                                                                <tr>
                                                                                    <td class="muiTitle">
                                                                                        ${lfn:message('fssc-expense:fsscExpenseDetail.fdApplyMoney')}
                                                                                    </td>
                                                                                    <td>
                                                                                        <div id="_xform_fdDetailList_Form[${vstatus.index}].fdApplyMoney" _xform_type="text">
                                                                                            <xform:text property="fdDetailList_Form[${vstatus.index}].fdApplyMoney" showStatus="edit" required="true" subject="${lfn:message('fssc-expense:fsscExpenseDetail.fdApplyMoney')}" mobile="true" style="width:95%;" />
                                                                                        </div>
                                                                                    </td>
                                                                                </tr>
                                                                                <tr>
                                                                                    <td class="muiTitle">
                                                                                        ${lfn:message('fssc-expense:fsscExpenseDetail.fdStandardMoney')}
                                                                                    </td>
                                                                                    <td>
                                                                                        <div id="_xform_fdDetailList_Form[${vstatus.index}].fdStandardMoney" _xform_type="text">
                                                                                            <xform:text property="fdDetailList_Form[${vstatus.index}].fdStandardMoney" showStatus="edit" mobile="true" style="width:95%;" />
                                                                                        </div>
                                                                                    </td>
                                                                                </tr>
                                                                                <tr>
                                                                                    <td class="muiTitle">
                                                                                        ${lfn:message('fssc-expense:fsscExpenseDetail.fdCurrency')}
                                                                                    </td>
                                                                                    <td>
                                                                                        <div id="_xform_fdDetailList_Form[${vstatus.index}].fdCurrencyId" _xform_type="dialog">
                                                                                            <div data-dojo-type="mui/form/CommonDialog" data-dojo-props="idField:'fdDetailList_Form[${vstatus.index}].fdCurrencyId',nameField:'fdDetailList_Form[${vstatus.index}].fdCurrencyName',isMul:false,modelName:'com.landray.kmss.eop.basedata.model.EopBasedataCurrency',dataURL:getSource('eop_basedata_currency_fdCurrency'),subject:'${lfn:message('fssc-expense:fsscExpenseDetail.fdCurrency')}',curIds:'${lfn:escapeHtml(lfn:escapeJs(fsscExpenseMainForm.fdDetailList_Form[vstatus.index].fdCurrencyId))}',curNames:'${lfn:escapeHtml(lfn:escapeJs(fsscExpenseMainForm.fdDetailList_Form[vstatus.index].fdCurrencyName))}',afterSelect:afterDialogSel">
                                                                                            </div>
                                                                                        </div>
                                                                                    </td>
                                                                                </tr>
                                                                                <tr>
                                                                                    <td class="muiTitle">
                                                                                        ${lfn:message('fssc-expense:fsscExpenseDetail.fdExchangeRate')}
                                                                                    </td>
                                                                                    <td>
                                                                                        <div id="_xform_fdDetailList_Form[${vstatus.index}].fdExchangeRate" _xform_type="text">
                                                                                            <xform:text property="fdDetailList_Form[${vstatus.index}].fdExchangeRate" showStatus="edit" required="true" subject="${lfn:message('fssc-expense:fsscExpenseDetail.fdExchangeRate')}" mobile="true" style="width:95%;" />
                                                                                        </div>
                                                                                    </td>
                                                                                </tr>
                                                                                <tr>
                                                                                    <td class="muiTitle">
                                                                                        ${lfn:message('fssc-expense:fsscExpenseDetail.fdApprovedApplyMoney')}
                                                                                    </td>
                                                                                    <td>
                                                                                        <div id="_xform_fdDetailList_Form[${vstatus.index}].fdApprovedApplyMoney" _xform_type="text">
                                                                                            <xform:text property="fdDetailList_Form[${vstatus.index}].fdApprovedApplyMoney" showStatus="edit" mobile="true" style="width:95%;" />
                                                                                        </div>
                                                                                    </td>
                                                                                </tr>
                                                                                <tr>
                                                                                    <td class="muiTitle">
                                                                                        ${lfn:message('fssc-expense:fsscExpenseDetail.fdApprovedStandardMoney')}
                                                                                    </td>
                                                                                    <td>
                                                                                        <div id="_xform_fdDetailList_Form[${vstatus.index}].fdApprovedStandardMoney" _xform_type="text">
                                                                                            <xform:text property="fdDetailList_Form[${vstatus.index}].fdApprovedStandardMoney" showStatus="edit" mobile="true" style="width:95%;" />
                                                                                        </div>
                                                                                    </td>
                                                                                </tr>
                                                                                <tr>
                                                                                    <td class="muiTitle">
                                                                                        ${lfn:message('fssc-expense:fsscExpenseDetail.fdUse')}
                                                                                    </td>
                                                                                    <td>
                                                                                        <div id="_xform_fdDetailList_Form[${vstatus.index}].fdUse" _xform_type="text">
                                                                                            <xform:text property="fdDetailList_Form[${vstatus.index}].fdUse" showStatus="edit" mobile="true" style="width:95%;" />
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
                                            </div>
                                            <input type="hidden" name="fdDetailList_Flag" value="2">
                                        </td>
                                    </tr>
                                    <tr>
                                        <td colspan="2">
                                            <div data-dojo-type="dojox/mobile/ListItem" data-dojo-props='rightIcon:"mui mui-forward",clickable:true, noArrow:true, _setIconAttr:function(){},onClick:function(){expandDetail("TABLE_DocList_fdInvoiceList_Form","scrollView");}'>
                                                <div layout="left">${lfn:message('fssc-expense:fsscExpenseMain.fdInvoiceList')}
                                                </div>
                                                <div layout="right">${lfn:message('fssc-expense:fssc.expense.detail.view')}
                                                </div>
                                            </div>
                                            <div class="detailTableView" data-dojo-type="mui/table/DetailTableScrollableView" data-dojo-mixins="mui/form/_ValidateMixin" id="TABLE_DocList_fdInvoiceList_Form_view">
                                                <div data-dojo-type="dojox/mobile/Heading" fixed="top" class="muiHeaderDetail">
                                                    <div class="muiHeaderDetailTitle">${lfn:message('fssc-expense:fsscExpenseMain.fdInvoiceList')}
                                                    </div>
                                                    <div class="muiHeaderDetailBack" onclick="collapseDetail('TABLE_DocList_fdInvoiceList_Form')">
                                                        <bean:message key="button.save" />
                                                    </div>
                                                </div>
                                                <br/>
                                                <br/>
                                                <table cellspacing="0" cellpadding="0" class="detailTableSimple">
                                                    <tr>
                                                        <td class="detailTableSimpleTd">
                                                            <table width="100%" border="0" cellspacing="0" cellpadding="0" id="TABLE_DocList_fdInvoiceList_Form">
                                                                <tr style="display:none;">
                                                                    <td>
                                                                    </td>
                                                                </tr>
                                                                <tr data-dojo-type="mui/form/Template" KMSS_IsReferRow="1" style="display:none;" border='0'>
                                                                    <td class="detail_wrap_td">
                                                                        <xform:text showStatus="noShow" property="fdInvoiceList_Form[!{index}].fdId" />
                                                                        <table class="muiSimple">
                                                                            <tr>
                                                                                <td colspan="2" align="left" valign="middle" class="muiDetailTableNo">
                                                                                    <span>${lfn:message('page.the')}!{index}${ lfn:message('page.row') }</span>
                                                                                    <div class="muiDetailTableDel" onclick="deleteDetailRow('TABLE_DocList_fdInvoiceList_Form',this);">
                                                                                        <i class="mui mui-close"></i>
                                                                                    </div>
                                                                                </td>
                                                                            </tr>
                                                                            <tr>
                                                                                <td class="muiTitle">
                                                                                    ${lfn:message('fssc-expense:fsscExpenseInvoiceDetail.fdCompany')}
                                                                                </td>
                                                                                <td>
                                                                                    <div id="_xform_fdInvoiceList_Form[!{index}].fdCompanyId" _xform_type="dialog">
                                                                                        <div data-dojo-type="mui/form/CommonDialog" data-dojo-props="idField:'fdInvoiceList_Form[!{index}].fdCompanyId',nameField:'fdInvoiceList_Form[!{index}].fdCompanyName',isMul:false,modelName:'com.landray.kmss.eop.basedata.model.EopBasedataCompany',dataURL:getSource('eop_basedata_company_fdCompany'),subject:'${lfn:message('fssc-expense:fsscExpenseInvoiceDetail.fdCompany')}',afterSelect:afterDialogSel">
                                                                                        </div>
                                                                                    </div>
                                                                                </td>
                                                                            </tr>
                                                                            <tr>
                                                                                <td class="muiTitle">
                                                                                    ${lfn:message('fssc-expense:fsscExpenseInvoiceDetail.fdIsVat')}
                                                                                </td>
                                                                                <td>
                                                                                    <div id="_xform_fdInvoiceList_Form[!{index}].fdIsVat" _xform_type="radio">
                                                                                        <xform:radio property="fdInvoiceList_Form[!{index}].fdIsVat" htmlElementProperties="id='fdInvoiceList_Form[!{index}].fdIsVat'" showStatus="edit" mobile="true">
                                                                                            <xform:enumsDataSource enumsType="common_yesno" />
                                                                                        </xform:radio>
                                                                                    </div>
                                                                                </td>
                                                                            </tr>
                                                                            <tr>
                                                                                <td class="muiTitle">
                                                                                    ${lfn:message('fssc-expense:fsscExpenseInvoiceDetail.fdExpenseType')}
                                                                                </td>
                                                                                <td>
                                                                                    <div id="_xform_fdInvoiceList_Form[!{index}].fdExpenseTypeId" _xform_type="dialog">
                                                                                        <div data-dojo-type="mui/form/CommonDialog" data-dojo-props="idField:'fdInvoiceList_Form[!{index}].fdExpenseTypeId',nameField:'fdInvoiceList_Form[!{index}].fdExpenseTypeName',isMul:false,modelName:'com.landray.kmss.eop.basedata.model.EopBasedataExpenseItem',dataURL:getSource('eop_basedata_expense_item_fdParent'),subject:'${lfn:message('fssc-expense:fsscExpenseInvoiceDetail.fdExpenseType')}',afterSelect:afterDialogSel">
                                                                                        </div>
                                                                                    </div>
                                                                                </td>
                                                                            </tr>
                                                                            <tr>
                                                                                <td class="muiTitle">
                                                                                    ${lfn:message('fssc-expense:fsscExpenseInvoiceDetail.fdInvoiceNumber')}
                                                                                </td>
                                                                                <td>
                                                                                    <div id="_xform_fdInvoiceList_Form[!{index}].fdInvoiceNumber" _xform_type="text">
                                                                                        <xform:text property="fdInvoiceList_Form[!{index}].fdInvoiceNumber" showStatus="edit" subject="${lfn:message('fssc-expense:fsscExpenseInvoiceDetail.fdInvoiceNumber')}" validators=" maxLength(200)" mobile="true" style="width:95%;" />
                                                                                    </div>
                                                                                </td>
                                                                            </tr>
                                                                            <tr>
                                                                                <td class="muiTitle">
                                                                                    ${lfn:message('fssc-expense:fsscExpenseInvoiceDetail.fdInvoiceDate')}
                                                                                </td>
                                                                                <td>
                                                                                    <div id="_xform_fdInvoiceList_Form[!{index}].fdInvoiceDate" _xform_type="datetime">
                                                                                        <xform:datetime property="fdInvoiceList_Form[!{index}].fdInvoiceDate" showStatus="edit" dateTimeType="date" mobile="true" style="width:95%;" />
                                                                                    </div>
                                                                                </td>
                                                                            </tr>
                                                                            <tr>
                                                                                <td class="muiTitle">
                                                                                    ${lfn:message('fssc-expense:fsscExpenseInvoiceDetail.fdTax')}
                                                                                </td>
                                                                                <td>
                                                                                    <div id="_xform_fdInvoiceList_Form[!{index}].fdTax" _xform_type="text">
                                                                                        <xform:text property="fdInvoiceList_Form[!{index}].fdTax" showStatus="edit" mobile="true" style="width:95%;" />
                                                                                    </div>
                                                                                </td>
                                                                            </tr>
                                                                            <tr>
                                                                                <td class="muiTitle">
                                                                                    ${lfn:message('fssc-expense:fsscExpenseInvoiceDetail.fdInvoiceMoney')}
                                                                                </td>
                                                                                <td>
                                                                                    <div id="_xform_fdInvoiceList_Form[!{index}].fdInvoiceMoney" _xform_type="text">
                                                                                        <xform:text property="fdInvoiceList_Form[!{index}].fdInvoiceMoney" showStatus="edit" mobile="true" style="width:95%;" />
                                                                                    </div>
                                                                                </td>
                                                                            </tr>
                                                                            <tr>
                                                                                <td class="muiTitle">
                                                                                    ${lfn:message('fssc-expense:fsscExpenseInvoiceDetail.fdNoTaxMoney')}
                                                                                </td>
                                                                                <td>
                                                                                    <div id="_xform_fdInvoiceList_Form[!{index}].fdNoTaxMoney" _xform_type="text">
                                                                                        <xform:text property="fdInvoiceList_Form[!{index}].fdNoTaxMoney" showStatus="edit" mobile="true" style="width:95%;" />
                                                                                    </div>
                                                                                </td>
                                                                            </tr>
                                                                            <tr>
                                                                                <td class="muiTitle">
                                                                                    ${lfn:message('fssc-expense:fsscExpenseInvoiceDetail.fdTaxMoney')}
                                                                                </td>
                                                                                <td>
                                                                                    <div id="_xform_fdInvoiceList_Form[!{index}].fdTaxMoney" _xform_type="text">
                                                                                        <xform:text property="fdInvoiceList_Form[!{index}].fdTaxMoney" showStatus="edit" mobile="true" style="width:95%;" />
                                                                                    </div>
                                                                                </td>
                                                                            </tr>
                                                                            <tr>
                                                                                <td class="muiTitle">
                                                                                    ${lfn:message('fssc-expense:fsscExpenseInvoiceDetail.fdInvoiceCode')}
                                                                                </td>
                                                                                <td>
                                                                                    <div id="_xform_fdInvoiceList_Form[!{index}].fdInvoiceCode" _xform_type="text">
                                                                                        <xform:text property="fdInvoiceList_Form[!{index}].fdInvoiceCode" showStatus="edit" subject="${lfn:message('fssc-expense:fsscExpenseInvoiceDetail.fdInvoiceCode')}" validators=" maxLength(200)" mobile="true" style="width:95%;" />
                                                                                    </div>
                                                                                </td>
                                                                            </tr>
                                                                        </table>
                                                                    </td>
                                                                </tr>
                                                                <c:forEach items="${fsscExpenseMainForm.fdInvoiceList_Form}" var="fdInvoiceList_FormItem" varStatus="vstatus">
                                                                    <tr KMSS_IsContentRow="1">
                                                                        <td class="detail_wrap_td">
                                                                            <xform:text showStatus="noShow" property="fdInvoiceList_Form[${vstatus.index}].fdId" />
                                                                            <table class="muiSimple">
                                                                                <tr>
                                                                                    <td colspan="2" align="left" valign="middle" class="muiDetailTableNo">
                                                                                        <span>${lfn:message('page.the')}${vstatus.index}${ lfn:message('page.row') }</span>
                                                                                        <div class="muiDetailTableDel" onclick="deleteDetailRow('TABLE_DocList_fdInvoiceList_Form',this);">
                                                                                            <i class="mui mui-close"></i>
                                                                                        </div>
                                                                                    </td>
                                                                                </tr>
                                                                                <tr>
                                                                                    <td class="muiTitle">
                                                                                        ${lfn:message('fssc-expense:fsscExpenseInvoiceDetail.fdCompany')}
                                                                                    </td>
                                                                                    <td>
                                                                                        <div id="_xform_fdInvoiceList_Form[${vstatus.index}].fdCompanyId" _xform_type="dialog">
                                                                                            <div data-dojo-type="mui/form/CommonDialog" data-dojo-props="idField:'fdInvoiceList_Form[${vstatus.index}].fdCompanyId',nameField:'fdInvoiceList_Form[${vstatus.index}].fdCompanyName',isMul:false,modelName:'com.landray.kmss.eop.basedata.model.EopBasedataCompany',dataURL:getSource('eop_basedata_company_fdCompany'),subject:'${lfn:message('fssc-expense:fsscExpenseInvoiceDetail.fdCompany')}',curIds:'${lfn:escapeHtml(lfn:escapeJs(fsscExpenseMainForm.fdInvoiceList_Form[vstatus.index].fdCompanyId))}',curNames:'${lfn:escapeHtml(lfn:escapeJs(fsscExpenseMainForm.fdInvoiceList_Form[vstatus.index].fdCompanyName))}',afterSelect:afterDialogSel">
                                                                                            </div>
                                                                                        </div>
                                                                                    </td>
                                                                                </tr>
                                                                                <tr>
                                                                                    <td class="muiTitle">
                                                                                        ${lfn:message('fssc-expense:fsscExpenseInvoiceDetail.fdIsVat')}
                                                                                    </td>
                                                                                    <td>
                                                                                        <div id="_xform_fdInvoiceList_Form[${vstatus.index}].fdIsVat" _xform_type="radio">
                                                                                            <xform:radio property="fdInvoiceList_Form[${vstatus.index}].fdIsVat" htmlElementProperties="id='fdInvoiceList_Form[${vstatus.index}].fdIsVat'" showStatus="edit" mobile="true">
                                                                                                <xform:enumsDataSource enumsType="common_yesno" />
                                                                                            </xform:radio>
                                                                                        </div>
                                                                                    </td>
                                                                                </tr>
                                                                                <tr>
                                                                                    <td class="muiTitle">
                                                                                        ${lfn:message('fssc-expense:fsscExpenseInvoiceDetail.fdExpenseType')}
                                                                                    </td>
                                                                                    <td>
                                                                                        <div id="_xform_fdInvoiceList_Form[${vstatus.index}].fdExpenseTypeId" _xform_type="dialog">
                                                                                            <div data-dojo-type="mui/form/CommonDialog" data-dojo-props="idField:'fdInvoiceList_Form[${vstatus.index}].fdExpenseTypeId',nameField:'fdInvoiceList_Form[${vstatus.index}].fdExpenseTypeName',isMul:false,modelName:'com.landray.kmss.eop.basedata.model.EopBasedataExpenseItem',dataURL:getSource('eop_basedata_expense_item_fdParent'),subject:'${lfn:message('fssc-expense:fsscExpenseInvoiceDetail.fdExpenseType')}',curIds:'${lfn:escapeHtml(lfn:escapeJs(fsscExpenseMainForm.fdInvoiceList_Form[vstatus.index].fdExpenseTypeId))}',curNames:'${lfn:escapeHtml(lfn:escapeJs(fsscExpenseMainForm.fdInvoiceList_Form[vstatus.index].fdExpenseTypeName))}',afterSelect:afterDialogSel">
                                                                                            </div>
                                                                                        </div>
                                                                                    </td>
                                                                                </tr>
                                                                                <tr>
                                                                                    <td class="muiTitle">
                                                                                        ${lfn:message('fssc-expense:fsscExpenseInvoiceDetail.fdInvoiceNumber')}
                                                                                    </td>
                                                                                    <td>
                                                                                        <div id="_xform_fdInvoiceList_Form[${vstatus.index}].fdInvoiceNumber" _xform_type="text">
                                                                                            <xform:text property="fdInvoiceList_Form[${vstatus.index}].fdInvoiceNumber" showStatus="edit" subject="${lfn:message('fssc-expense:fsscExpenseInvoiceDetail.fdInvoiceNumber')}" validators=" maxLength(200)" mobile="true" style="width:95%;" />
                                                                                        </div>
                                                                                    </td>
                                                                                </tr>
                                                                                <tr>
                                                                                    <td class="muiTitle">
                                                                                        ${lfn:message('fssc-expense:fsscExpenseInvoiceDetail.fdInvoiceDate')}
                                                                                    </td>
                                                                                    <td>
                                                                                        <div id="_xform_fdInvoiceList_Form[${vstatus.index}].fdInvoiceDate" _xform_type="datetime">
                                                                                            <xform:datetime property="fdInvoiceList_Form[${vstatus.index}].fdInvoiceDate" showStatus="edit" dateTimeType="date" mobile="true" style="width:95%;" />
                                                                                        </div>
                                                                                    </td>
                                                                                </tr>
                                                                                <tr>
                                                                                    <td class="muiTitle">
                                                                                        ${lfn:message('fssc-expense:fsscExpenseInvoiceDetail.fdTax')}
                                                                                    </td>
                                                                                    <td>
                                                                                        <div id="_xform_fdInvoiceList_Form[${vstatus.index}].fdTax" _xform_type="text">
                                                                                            <xform:text property="fdInvoiceList_Form[${vstatus.index}].fdTax" showStatus="edit" mobile="true" style="width:95%;" />
                                                                                        </div>
                                                                                    </td>
                                                                                </tr>
                                                                                <tr>
                                                                                    <td class="muiTitle">
                                                                                        ${lfn:message('fssc-expense:fsscExpenseInvoiceDetail.fdInvoiceMoney')}
                                                                                    </td>
                                                                                    <td>
                                                                                        <div id="_xform_fdInvoiceList_Form[${vstatus.index}].fdInvoiceMoney" _xform_type="text">
                                                                                            <xform:text property="fdInvoiceList_Form[${vstatus.index}].fdInvoiceMoney" showStatus="edit" mobile="true" style="width:95%;" />
                                                                                        </div>
                                                                                    </td>
                                                                                </tr>
                                                                                <tr>
                                                                                    <td class="muiTitle">
                                                                                        ${lfn:message('fssc-expense:fsscExpenseInvoiceDetail.fdNoTaxMoney')}
                                                                                    </td>
                                                                                    <td>
                                                                                        <div id="_xform_fdInvoiceList_Form[${vstatus.index}].fdNoTaxMoney" _xform_type="text">
                                                                                            <xform:text property="fdInvoiceList_Form[${vstatus.index}].fdNoTaxMoney" showStatus="edit" mobile="true" style="width:95%;" />
                                                                                        </div>
                                                                                    </td>
                                                                                </tr>
                                                                                <tr>
                                                                                    <td class="muiTitle">
                                                                                        ${lfn:message('fssc-expense:fsscExpenseInvoiceDetail.fdTaxMoney')}
                                                                                    </td>
                                                                                    <td>
                                                                                        <div id="_xform_fdInvoiceList_Form[${vstatus.index}].fdTaxMoney" _xform_type="text">
                                                                                            <xform:text property="fdInvoiceList_Form[${vstatus.index}].fdTaxMoney" showStatus="edit" mobile="true" style="width:95%;" />
                                                                                        </div>
                                                                                    </td>
                                                                                </tr>
                                                                                <tr>
                                                                                    <td class="muiTitle">
                                                                                        ${lfn:message('fssc-expense:fsscExpenseInvoiceDetail.fdInvoiceCode')}
                                                                                    </td>
                                                                                    <td>
                                                                                        <div id="_xform_fdInvoiceList_Form[${vstatus.index}].fdInvoiceCode" _xform_type="text">
                                                                                            <xform:text property="fdInvoiceList_Form[${vstatus.index}].fdInvoiceCode" showStatus="edit" subject="${lfn:message('fssc-expense:fsscExpenseInvoiceDetail.fdInvoiceCode')}" validators=" maxLength(200)" mobile="true" style="width:95%;" />
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
                                                    <li data-dojo-type="mui/tabbar/TabBarButton" class="muiBtnNext " data-dojo-props="colSize:4,onClick:function(){addDetailRow('TABLE_DocList_fdInvoiceList_Form');}, label:'${lfn:message('doclist.add')}'" style="width:95%">
                                                    </li>
                                                </ul>
                                            </div>
                                            <input type="hidden" name="fdInvoiceList_Flag" value="1">
                                            <script>
                                                DocList_Info.push('TABLE_DocList_fdInvoiceList_Form');
                                            </script>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td colspan="2">
                                            <div data-dojo-type="dojox/mobile/ListItem" data-dojo-props='rightIcon:"mui mui-forward",clickable:true, noArrow:true, _setIconAttr:function(){},onClick:function(){expandDetail("TABLE_DocList_fdAccountsList_Form","scrollView");}'>
                                                <div layout="left">${lfn:message('fssc-expense:fsscExpenseMain.fdAccountsList')}
                                                </div>
                                                <div layout="right">${lfn:message('fssc-expense:fssc.expense.detail.view')}
                                                </div>
                                            </div>
                                            <div class="detailTableView" data-dojo-type="mui/table/DetailTableScrollableView" data-dojo-mixins="mui/form/_ValidateMixin" id="TABLE_DocList_fdAccountsList_Form_view">
                                                <div data-dojo-type="dojox/mobile/Heading" fixed="top" class="muiHeaderDetail">
                                                    <div class="muiHeaderDetailTitle">${lfn:message('fssc-expense:fsscExpenseMain.fdAccountsList')}
                                                    </div>
                                                    <div class="muiHeaderDetailBack" onclick="collapseDetail('TABLE_DocList_fdAccountsList_Form')">
                                                        <bean:message key="button.save" />
                                                    </div>
                                                </div>
                                                <br/>
                                                <br/>
                                                <table cellspacing="0" cellpadding="0" class="detailTableSimple">
                                                    <tr>
                                                        <td class="detailTableSimpleTd">
                                                            <table width="100%" border="0" cellspacing="0" cellpadding="0" id="TABLE_DocList_fdAccountsList_Form">
                                                                <tr style="display:none;">
                                                                    <td>
                                                                    </td>
                                                                </tr>
                                                                <tr data-dojo-type="mui/form/Template" KMSS_IsReferRow="1" style="display:none;" border='0'>
                                                                    <td class="detail_wrap_td">
                                                                        <xform:text showStatus="noShow" property="fdAccountsList_Form[!{index}].fdId" />
                                                                        <table class="muiSimple">
                                                                            <tr>
                                                                                <td colspan="2" align="left" valign="middle" class="muiDetailTableNo">
                                                                                    <span>${lfn:message('page.the')}!{index}${ lfn:message('page.row') }</span>
                                                                                    <div class="muiDetailTableDel" onclick="deleteDetailRow('TABLE_DocList_fdAccountsList_Form',this);">
                                                                                        <i class="mui mui-close"></i>
                                                                                    </div>
                                                                                </td>
                                                                            </tr>
                                                                            <tr>
                                                                                <td class="muiTitle">
                                                                                    ${lfn:message('fssc-expense:fsscExpenseAccounts.fdAccountName')}
                                                                                </td>
                                                                                <td>
                                                                                    <div id="_xform_fdAccountsList_Form[!{index}].fdAccountName" _xform_type="text">
                                                                                        <xform:text property="fdAccountsList_Form[!{index}].fdAccountName" showStatus="edit" subject="${lfn:message('fssc-expense:fsscExpenseAccounts.fdAccountName')}" validators=" maxLength(200)" mobile="true" style="width:95%;" />
                                                                                    </div>
                                                                                </td>
                                                                            </tr>
                                                                            <tr>
                                                                                <td class="muiTitle">
                                                                                    ${lfn:message('fssc-expense:fsscExpenseAccounts.fdBankName')}
                                                                                </td>
                                                                                <td>
                                                                                    <div id="_xform_fdAccountsList_Form[!{index}].fdBankName" _xform_type="text">
                                                                                        <xform:text property="fdAccountsList_Form[!{index}].fdBankName" showStatus="edit" subject="${lfn:message('fssc-expense:fsscExpenseAccounts.fdBankName')}" validators=" maxLength(400)" mobile="true" style="width:95%;" />
                                                                                    </div>
                                                                                </td>
                                                                            </tr>
                                                                            <tr>
                                                                                <td class="muiTitle">
                                                                                    ${lfn:message('fssc-expense:fsscExpenseAccounts.fdBankAccount')}
                                                                                </td>
                                                                                <td>
                                                                                    <div id="_xform_fdAccountsList_Form[!{index}].fdBankAccount" _xform_type="text">
                                                                                        <xform:text property="fdAccountsList_Form[!{index}].fdBankAccount" showStatus="edit" subject="${lfn:message('fssc-expense:fsscExpenseAccounts.fdBankAccount')}" validators=" maxLength(200)" mobile="true" style="width:95%;" />
                                                                                    </div>
                                                                                </td>
                                                                            </tr>
                                                                            <tr>
                                                                                <td class="muiTitle">
                                                                                    ${lfn:message('fssc-expense:fsscExpenseAccounts.fdMoney')}
                                                                                </td>
                                                                                <td>
                                                                                    <div id="_xform_fdAccountsList_Form[!{index}].fdMoney" _xform_type="text">
                                                                                        <xform:text property="fdAccountsList_Form[!{index}].fdMoney" showStatus="edit" mobile="true" style="width:95%;" />
                                                                                    </div>
                                                                                </td>
                                                                            </tr>
                                                                        </table>
                                                                    </td>
                                                                </tr>
                                                                <c:forEach items="${fsscExpenseMainForm.fdAccountsList_Form}" var="fdAccountsList_FormItem" varStatus="vstatus">
                                                                    <tr KMSS_IsContentRow="1">
                                                                        <td class="detail_wrap_td">
                                                                            <xform:text showStatus="noShow" property="fdAccountsList_Form[${vstatus.index}].fdId" />
                                                                            <table class="muiSimple">
                                                                                <tr>
                                                                                    <td colspan="2" align="left" valign="middle" class="muiDetailTableNo">
                                                                                        <span>${lfn:message('page.the')}${vstatus.index}${ lfn:message('page.row') }</span>
                                                                                        <div class="muiDetailTableDel" onclick="deleteDetailRow('TABLE_DocList_fdAccountsList_Form',this);">
                                                                                            <i class="mui mui-close"></i>
                                                                                        </div>
                                                                                    </td>
                                                                                </tr>
                                                                                <tr>
                                                                                    <td class="muiTitle">
                                                                                        ${lfn:message('fssc-expense:fsscExpenseAccounts.fdAccountName')}
                                                                                    </td>
                                                                                    <td>
                                                                                        <div id="_xform_fdAccountsList_Form[${vstatus.index}].fdAccountName" _xform_type="text">
                                                                                            <xform:text property="fdAccountsList_Form[${vstatus.index}].fdAccountName" showStatus="edit" subject="${lfn:message('fssc-expense:fsscExpenseAccounts.fdAccountName')}" validators=" maxLength(200)" mobile="true" style="width:95%;" />
                                                                                        </div>
                                                                                    </td>
                                                                                </tr>
                                                                                <tr>
                                                                                    <td class="muiTitle">
                                                                                        ${lfn:message('fssc-expense:fsscExpenseAccounts.fdBankName')}
                                                                                    </td>
                                                                                    <td>
                                                                                        <div id="_xform_fdAccountsList_Form[${vstatus.index}].fdBankName" _xform_type="text">
                                                                                            <xform:text property="fdAccountsList_Form[${vstatus.index}].fdBankName" showStatus="edit" subject="${lfn:message('fssc-expense:fsscExpenseAccounts.fdBankName')}" validators=" maxLength(400)" mobile="true" style="width:95%;" />
                                                                                        </div>
                                                                                    </td>
                                                                                </tr>
                                                                                <tr>
                                                                                    <td class="muiTitle">
                                                                                        ${lfn:message('fssc-expense:fsscExpenseAccounts.fdBankAccount')}
                                                                                    </td>
                                                                                    <td>
                                                                                        <div id="_xform_fdAccountsList_Form[${vstatus.index}].fdBankAccount" _xform_type="text">
                                                                                            <xform:text property="fdAccountsList_Form[${vstatus.index}].fdBankAccount" showStatus="edit" subject="${lfn:message('fssc-expense:fsscExpenseAccounts.fdBankAccount')}" validators=" maxLength(200)" mobile="true" style="width:95%;" />
                                                                                        </div>
                                                                                    </td>
                                                                                </tr>
                                                                                <tr>
                                                                                    <td class="muiTitle">
                                                                                        ${lfn:message('fssc-expense:fsscExpenseAccounts.fdMoney')}
                                                                                    </td>
                                                                                    <td>
                                                                                        <div id="_xform_fdAccountsList_Form[${vstatus.index}].fdMoney" _xform_type="text">
                                                                                            <xform:text property="fdAccountsList_Form[${vstatus.index}].fdMoney" showStatus="edit" mobile="true" style="width:95%;" />
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
                                                    <li data-dojo-type="mui/tabbar/TabBarButton" class="muiBtnNext " data-dojo-props="colSize:4,onClick:function(){addDetailRow('TABLE_DocList_fdAccountsList_Form');}, label:'${lfn:message('doclist.add')}'" style="width:95%">
                                                    </li>
                                                </ul>
                                            </div>
                                            <input type="hidden" name="fdAccountsList_Flag" value="1">
                                            <script>
                                                DocList_Info.push('TABLE_DocList_fdAccountsList_Form');
                                            </script>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td colspan="2">
                                            <div data-dojo-type="dojox/mobile/ListItem" data-dojo-props='rightIcon:"mui mui-forward",clickable:true, noArrow:true, _setIconAttr:function(){},onClick:function(){expandDetail("TABLE_DocList_fdOffsetList_Form","scrollView");}'>
                                                <div layout="left">${lfn:message('fssc-expense:fsscExpenseMain.fdOffsetList')}
                                                </div>
                                                <div layout="right">${lfn:message('fssc-expense:fssc.expense.detail.view')}
                                                </div>
                                            </div>
                                            <div class="detailTableView" data-dojo-type="mui/table/DetailTableScrollableView" data-dojo-mixins="mui/form/_ValidateMixin" id="TABLE_DocList_fdOffsetList_Form_view">
                                                <div data-dojo-type="dojox/mobile/Heading" fixed="top" class="muiHeaderDetail">
                                                    <div class="muiHeaderDetailTitle">${lfn:message('fssc-expense:fsscExpenseMain.fdOffsetList')}
                                                    </div>
                                                    <div class="muiHeaderDetailBack" onclick="collapseDetail('TABLE_DocList_fdOffsetList_Form')">
                                                        <bean:message key="button.save" />
                                                    </div>
                                                </div>
                                                <br/>
                                                <br/>
                                                <table cellspacing="0" cellpadding="0" class="detailTableSimple">
                                                    <tr>
                                                        <td class="detailTableSimpleTd">
                                                            <table width="100%" border="0" cellspacing="0" cellpadding="0" id="TABLE_DocList_fdOffsetList_Form">
                                                                <tr style="display:none;">
                                                                    <td>
                                                                    </td>
                                                                </tr>
                                                                <tr data-dojo-type="mui/form/Template" KMSS_IsReferRow="1" style="display:none;" border='0'>
                                                                    <td class="detail_wrap_td">
                                                                        <xform:text showStatus="noShow" property="fdOffsetList_Form[!{index}].fdId" />
                                                                        <table class="muiSimple">
                                                                            <tr>
                                                                                <td colspan="2" align="left" valign="middle" class="muiDetailTableNo">
                                                                                    <span>${lfn:message('page.the')}!{index}${ lfn:message('page.row') }</span>
                                                                                    <div class="muiDetailTableDel" onclick="deleteDetailRow('TABLE_DocList_fdOffsetList_Form',this);">
                                                                                        <i class="mui mui-close"></i>
                                                                                    </div>
                                                                                </td>
                                                                            </tr>
                                                                            <tr>
                                                                                <td class="muiTitle">
                                                                                    ${lfn:message('fssc-expense:fsscExpenseOffsetLoan.docSubject')}
                                                                                </td>
                                                                                <td>
                                                                                    <div id="_xform_fdOffsetList_Form[!{index}].docSubject" _xform_type="text">
                                                                                        <xform:text property="fdOffsetList_Form[!{index}].docSubject" showStatus="edit" required="true" subject="${lfn:message('fssc-expense:fsscExpenseOffsetLoan.docSubject')}" validators=" maxLength(200)" mobile="true" style="width:95%;" />
                                                                                    </div>
                                                                                </td>
                                                                            </tr>
                                                                            <tr>
                                                                                <td class="muiTitle">
                                                                                    ${lfn:message('fssc-expense:fsscExpenseOffsetLoan.fdNumber')}
                                                                                </td>
                                                                                <td>
                                                                                    <div id="_xform_fdOffsetList_Form[!{index}].fdNumber" _xform_type="text">
                                                                                        <xform:text property="fdOffsetList_Form[!{index}].fdNumber" showStatus="edit" mobile="true" style="width:95%;" />
                                                                                    </div>
                                                                                </td>
                                                                            </tr>
                                                                            <tr>
                                                                                <td class="muiTitle">
                                                                                    ${lfn:message('fssc-expense:fsscExpenseOffsetLoan.fdLoanMoney')}
                                                                                </td>
                                                                                <td>
                                                                                    <div id="_xform_fdOffsetList_Form[!{index}].fdLoanMoney" _xform_type="text">
                                                                                        <xform:text property="fdOffsetList_Form[!{index}].fdLoanMoney" showStatus="edit" mobile="true" style="width:95%;" />
                                                                                    </div>
                                                                                </td>
                                                                            </tr>
                                                                            <tr>
                                                                                <td class="muiTitle">
                                                                                    ${lfn:message('fssc-expense:fsscExpenseOffsetLoan.fdCanOffsetMoney')}
                                                                                </td>
                                                                                <td>
                                                                                    <div id="_xform_fdOffsetList_Form[!{index}].fdCanOffsetMoney" _xform_type="text">
                                                                                        <xform:text property="fdOffsetList_Form[!{index}].fdCanOffsetMoney" showStatus="edit" mobile="true" style="width:95%;" />
                                                                                    </div>
                                                                                </td>
                                                                            </tr>
                                                                            <tr>
                                                                                <td class="muiTitle">
                                                                                    ${lfn:message('fssc-expense:fsscExpenseOffsetLoan.fdOffsetMoney')}
                                                                                </td>
                                                                                <td>
                                                                                    <div id="_xform_fdOffsetList_Form[!{index}].fdOffsetMoney" _xform_type="text">
                                                                                        <xform:text property="fdOffsetList_Form[!{index}].fdOffsetMoney" showStatus="edit" mobile="true" style="width:95%;" />
                                                                                    </div>
                                                                                </td>
                                                                            </tr>
                                                                            <tr>
                                                                                <td class="muiTitle">
                                                                                    ${lfn:message('fssc-expense:fsscExpenseOffsetLoan.fdLeftMoney')}
                                                                                </td>
                                                                                <td>
                                                                                    <div id="_xform_fdOffsetList_Form[!{index}].fdLeftMoney" _xform_type="text">
                                                                                        <xform:text property="fdOffsetList_Form[!{index}].fdLeftMoney" showStatus="edit" mobile="true" style="width:95%;" />
                                                                                    </div>
                                                                                </td>
                                                                            </tr>
                                                                        </table>
                                                                    </td>
                                                                </tr>
                                                                <c:forEach items="${fsscExpenseMainForm.fdOffsetList_Form}" var="fdOffsetList_FormItem" varStatus="vstatus">
                                                                    <tr KMSS_IsContentRow="1">
                                                                        <td class="detail_wrap_td">
                                                                            <xform:text showStatus="noShow" property="fdOffsetList_Form[${vstatus.index}].fdId" />
                                                                            <table class="muiSimple">
                                                                                <tr>
                                                                                    <td colspan="2" align="left" valign="middle" class="muiDetailTableNo">
                                                                                        <span>${lfn:message('page.the')}${vstatus.index}${ lfn:message('page.row') }</span>
                                                                                        <div class="muiDetailTableDel" onclick="deleteDetailRow('TABLE_DocList_fdOffsetList_Form',this);">
                                                                                            <i class="mui mui-close"></i>
                                                                                        </div>
                                                                                    </td>
                                                                                </tr>
                                                                                <tr>
                                                                                    <td class="muiTitle">
                                                                                        ${lfn:message('fssc-expense:fsscExpenseOffsetLoan.docSubject')}
                                                                                    </td>
                                                                                    <td>
                                                                                        <div id="_xform_fdOffsetList_Form[${vstatus.index}].docSubject" _xform_type="text">
                                                                                            <xform:text property="fdOffsetList_Form[${vstatus.index}].docSubject" showStatus="edit" required="true" subject="${lfn:message('fssc-expense:fsscExpenseOffsetLoan.docSubject')}" validators=" maxLength(200)" mobile="true" style="width:95%;" />
                                                                                        </div>
                                                                                    </td>
                                                                                </tr>
                                                                                <tr>
                                                                                    <td class="muiTitle">
                                                                                        ${lfn:message('fssc-expense:fsscExpenseOffsetLoan.fdNumber')}
                                                                                    </td>
                                                                                    <td>
                                                                                        <div id="_xform_fdOffsetList_Form[${vstatus.index}].fdNumber" _xform_type="text">
                                                                                            <xform:text property="fdOffsetList_Form[${vstatus.index}].fdNumber" showStatus="edit" mobile="true" style="width:95%;" />
                                                                                        </div>
                                                                                    </td>
                                                                                </tr>
                                                                                <tr>
                                                                                    <td class="muiTitle">
                                                                                        ${lfn:message('fssc-expense:fsscExpenseOffsetLoan.fdLoanMoney')}
                                                                                    </td>
                                                                                    <td>
                                                                                        <div id="_xform_fdOffsetList_Form[${vstatus.index}].fdLoanMoney" _xform_type="text">
                                                                                            <xform:text property="fdOffsetList_Form[${vstatus.index}].fdLoanMoney" showStatus="edit" mobile="true" style="width:95%;" />
                                                                                        </div>
                                                                                    </td>
                                                                                </tr>
                                                                                <tr>
                                                                                    <td class="muiTitle">
                                                                                        ${lfn:message('fssc-expense:fsscExpenseOffsetLoan.fdCanOffsetMoney')}
                                                                                    </td>
                                                                                    <td>
                                                                                        <div id="_xform_fdOffsetList_Form[${vstatus.index}].fdCanOffsetMoney" _xform_type="text">
                                                                                            <xform:text property="fdOffsetList_Form[${vstatus.index}].fdCanOffsetMoney" showStatus="edit" mobile="true" style="width:95%;" />
                                                                                        </div>
                                                                                    </td>
                                                                                </tr>
                                                                                <tr>
                                                                                    <td class="muiTitle">
                                                                                        ${lfn:message('fssc-expense:fsscExpenseOffsetLoan.fdOffsetMoney')}
                                                                                    </td>
                                                                                    <td>
                                                                                        <div id="_xform_fdOffsetList_Form[${vstatus.index}].fdOffsetMoney" _xform_type="text">
                                                                                            <xform:text property="fdOffsetList_Form[${vstatus.index}].fdOffsetMoney" showStatus="edit" mobile="true" style="width:95%;" />
                                                                                        </div>
                                                                                    </td>
                                                                                </tr>
                                                                                <tr>
                                                                                    <td class="muiTitle">
                                                                                        ${lfn:message('fssc-expense:fsscExpenseOffsetLoan.fdLeftMoney')}
                                                                                    </td>
                                                                                    <td>
                                                                                        <div id="_xform_fdOffsetList_Form[${vstatus.index}].fdLeftMoney" _xform_type="text">
                                                                                            <xform:text property="fdOffsetList_Form[${vstatus.index}].fdLeftMoney" showStatus="edit" mobile="true" style="width:95%;" />
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
                                                    <li data-dojo-type="mui/tabbar/TabBarButton" class="muiBtnNext " data-dojo-props="colSize:4,onClick:function(){addDetailRow('TABLE_DocList_fdOffsetList_Form');}, label:'${lfn:message('doclist.add')}'" style="width:95%">
                                                    </li>
                                                </ul>
                                            </div>
                                            <input type="hidden" name="fdOffsetList_Flag" value="1">
                                            <script>
                                                DocList_Info.push('TABLE_DocList_fdOffsetList_Form');
                                            </script>
                                        </td>
                                    </tr>
                                </table>
                            </div>
                        </div>

                        <div data-dojo-type="mui/panel/Content" data-dojo-props="title:'${lfn:message('fssc-expense:py.BiaoDanNeiRong')}',icon:'mui-ul'">
                            <c:if test="${fsscExpenseMainForm.docUseXform == 'false'}">
                                <div class="muiFormContent">
                                    <table class="muiSimple" cellpadding="0" cellspacing="0">
                                        <tr>
                                            <td colspan="2">
                                                <xform:rtf showStatus="edit" property="docXform" mobile="true"></xform:rtf>
                                            </td>
                                        </tr>
                                    </table>
                                </div>
                            </c:if>
                            <c:if test="${fsscExpenseMainForm.docUseXform == 'true' || empty fsscExpenseMainForm.docUseXform}">
                                <div data-dojo-type="mui/table/ScrollableHContainer">
                                    <div data-dojo-type="mui/table/ScrollableHView" class="muiFormContent">
                                        <c:import url="/sys/xform/mobile/import/sysForm_mobile.jsp" charEncoding="UTF-8">
                                            <c:param name="formName" value="fsscExpenseMainForm" />
                                            <c:param name="fdKey" value="fsscExpenseMain" />
                                            <c:param name="backTo" value="scrollView" />
                                            <c:param name="mobile" value="true" />
                                        </c:import>
                                        <br/>
                                    </div>
                                </div>
                            </c:if>
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
                    <c:param name="formName" value="fsscExpenseMainForm" />
                    <c:param name="fdKey" value="fsscExpenseMain" />
                    <c:param name="viewName" value="lbpmView" />
                    <c:param name="backTo" value="scrollView" />
                    <c:param name="onClickSubmitButton" value="form_submit();" />
                </c:import>

                <c:import url="/sys/right/mobile/edit_hidden.jsp" charEncoding="UTF-8">
                    <c:param name="formName" value="fsscExpenseMainForm" />
                    <c:param name="moduleModelName" value="com.landray.kmss.fssc.expense.model.FsscExpenseMain" />
                </c:import>

            </html:form>
        </template:replace>
    </template:include>
