<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<%@ include file="/sys/mobile/jsp/ajax-accept.jsp" %>
<%@ taglib uri="/WEB-INF/KmssConfig/sys/person/person.tld" prefix="person" %>
<%@page import="com.landray.kmss.fssc.loan.util.FsscLoanUtil" %>
    
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
                <c:when test="${fsscLoanTransferForm.method_GET == 'add' }">
                    <c:out value="${ lfn:message('operation.create') } - ${ lfn:message('fssc-loan:table.fsscLoanTransfer') }" />
                </c:when>
                <c:otherwise>
                    <c:out value="${fsscLoanTransferForm.docSubject} - " />
                    <c:out value="${lfn:message('fssc-loan:table.fsscLoanTransfer') }" />
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

                };

                var initData = {
                    contextPath: '${LUI_ContextPath}',
                };
                Com_IncludeFile("security.js");
                Com_IncludeFile("domain.js");
                Com_IncludeFile("form_option.js", "${LUI_ContextPath}/fssc/loan/fssc_loan_transfer/", 'js', true);
                Com_IncludeFile("mobile_edit.js", "${LUI_ContextPath}/fssc/loan/resource/js/", 'js', true);
            </script>
        </template:replace>
        <template:replace name="content">
            <html:form action="/fssc/loan/fssc_loan_transfer/fsscLoanTransfer.do?method=save">

                <div id="scrollView" data-dojo-type="mui/view/DocScrollableView" data-dojo-mixins="mui/form/_ValidateMixin">
                    <div data-dojo-type="mui/panel/AccordionPanel">
                        <div data-dojo-type="mui/panel/Content" data-dojo-props="title:'${lfn:message('fssc-loan:py.JiBenXinXi') }',icon:'mui-ul'">
                            <div class="muiFormContent">
                                <table class="muiSimple" cellpadding="0" cellspacing="0">
                                    <tr>
                                        <td class="muiTitle">
                                            ${lfn:message('fssc-loan:fsscLoanTransfer.fdTurnOut')}
                                        </td>
                                        <td>
                                            <div id="_xform_fdTurnOutId" _xform_type="address">
                                                <xform:address propertyId="fdTurnOutId" propertyName="fdTurnOutName" orgType="ORG_TYPE_PERSON" showStatus="view" mobile="true" style="width:95%;" />
                                            </div>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td class="muiTitle">
                                            ${lfn:message('fssc-loan:fsscLoanTransfer.fdTurnOutDept')}
                                        </td>
                                        <td>
                                            <div id="_xform_fdTurnOutDeptId" _xform_type="address">
                                                <xform:address propertyId="fdTurnOutDeptId" propertyName="fdTurnOutDeptName" orgType="ORG_TYPE_ORGORDEPT" showStatus="view" mobile="true" style="width:95%;" />
                                            </div>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td class="muiTitle">
                                            ${lfn:message('fssc-loan:fsscLoanTransfer.docCreateTime')}
                                        </td>
                                        <td>
                                            <div id="_xform_docCreateTime" _xform_type="datetime">
                                                <xform:datetime property="docCreateTime" showStatus="view" dateTimeType="datetime" mobile="true" style="width:95%;" />
                                            </div>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td class="muiTitle">
                                            ${lfn:message('fssc-loan:fsscLoanTransfer.fdLoanMain')}
                                        </td>
                                        <td>
                                            <div id="_xform_fdLoanMainId" _xform_type="dialog">
                                                <div data-dojo-type="mui/form/CommonDialog" data-dojo-props="idField:'fdLoanMainId',nameField:'fdLoanMainName',isMul:false,modelName:'com.landray.kmss.fssc.loan.model.FsscLoanMain',dataURL:getSource('fssc_loan_main_getLoanMain'),subject:'${lfn:message('fssc-loan:fsscLoanTransfer.fdLoanMain')}',required:true,curIds:'${lfn:escapeHtml(lfn:escapeJs(fsscLoanTransferForm.fdLoanMainId))}',curNames:'${lfn:escapeHtml(lfn:escapeJs(fsscLoanTransferForm.fdLoanMainName))}',afterSelect:afterDialogSel">
                                                </div>
                                            </div>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td class="muiTitle">
                                            ${lfn:message('fssc-loan:fsscLoanTransfer.fdCanOffsetMoney')}
                                        </td>
                                        <td>
                                            <div id="_xform_fdCanOffsetMoney" _xform_type="text">
                                                <xform:text property="fdCanOffsetMoney" showStatus="view" mobile="true" style="width:95%;" />
                                            </div>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td class="muiTitle">
                                            ${lfn:message('fssc-loan:fsscLoanTransfer.fdTransferMoney')}
                                        </td>
                                        <td>
                                            <div id="_xform_fdTransferMoney" _xform_type="text">
                                                <xform:text property="fdTransferMoney" showStatus="view" mobile="true" style="width:95%;" />
                                            </div>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td class="muiTitle">
                                            ${lfn:message('fssc-loan:fsscLoanTransfer.fdReceive')}
                                        </td>
                                        <td>
                                            <div id="_xform_fdReceiveId" _xform_type="address">
                                                <xform:address propertyId="fdReceiveId" propertyName="fdReceiveName" orgType="ORG_TYPE_PERSON" showStatus="edit" required="true" subject="${lfn:message('fssc-loan:fsscLoanTransfer.fdReceive')}" mobile="true" style="width:95%;" />
                                            </div>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td class="muiTitle">
                                            ${lfn:message('fssc-loan:fsscLoanTransfer.fdReceiveDept')}
                                        </td>
                                        <td>
                                            <div id="_xform_fdReceiveDeptId" _xform_type="address">
                                                <xform:address propertyId="fdReceiveDeptId" propertyName="fdReceiveDeptName" orgType="ORG_TYPE_ORGORDEPT" showStatus="edit" required="true" subject="${lfn:message('fssc-loan:fsscLoanTransfer.fdReceiveDept')}" mobile="true" style="width:95%;" />
                                            </div>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td class="muiTitle">
                                            ${lfn:message('fssc-loan:fsscLoanTransfer.fdReceiveCostCenter')}
                                        </td>
                                        <td>
                                            <div id="_xform_fdReceiveCostCenterId" _xform_type="dialog">
                                                <div data-dojo-type="mui/form/CommonDialog" data-dojo-props="idField:'fdReceiveCostCenterId',nameField:'fdReceiveCostCenterName',isMul:false,modelName:'com.landray.kmss.eop.basedata.model.EopBasedataCostCenter',dataURL:getSource('eop_basedata_cost_center_selectCostCenter'),subject:'${lfn:message('fssc-loan:fsscLoanTransfer.fdReceiveCostCenter')}',required:true,curIds:'${lfn:escapeHtml(lfn:escapeJs(fsscLoanTransferForm.fdReceiveCostCenterId))}',curNames:'${lfn:escapeHtml(lfn:escapeJs(fsscLoanTransferForm.fdReceiveCostCenterName))}',afterSelect:afterDialogSel">
                                                </div>
                                            </div>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td class="muiTitle">
                                            ${lfn:message('fssc-loan:fsscLoanTransfer.fdReason')}
                                        </td>
                                        <td>
                                            <div id="_xform_fdReason" _xform_type="textarea">
                                                <xform:textarea property="fdReason" showStatus="edit" mobile="true" style="width:95%;" />
                                            </div>
                                        </td>
                                    </tr>
                                </table>
                            </div>
                        </div>
                    </div>
                    <ul data-dojo-type="mui/tabbar/TabBar" fixed="bottom" data-dojo-props='fill:"grid"'>

                        <li data-dojo-type="mui/tabbar/TabBarButton" class="muiBtnNext" data-dojo-props='colSize:2,moveTo:"lbpmView",icon1:"",transition:"slide"'>
                            <bean:message bundle="fssc-loan" key="button.next" />
                        </li>
                    </ul>
                </div>
                <html:hidden property="fdId" />
                <html:hidden property="docStatus" />
                <html:hidden property="method_GET" />


                <c:import url="/sys/lbpmservice/mobile/import/edit.jsp" charEncoding="UTF-8">
                    <c:param name="formName" value="fsscLoanTransferForm" />
                    <c:param name="fdKey" value="fsscLoanTransfer" />
                    <c:param name="viewName" value="lbpmView" />
                    <c:param name="backTo" value="scrollView" />
                    <c:param name="onClickSubmitButton" value="form_submit();" />
                </c:import>

                <c:import url="/sys/right/mobile/edit_hidden.jsp" charEncoding="UTF-8">
                    <c:param name="formName" value="fsscLoanTransferForm" />
                    <c:param name="moduleModelName" value="com.landray.kmss.fssc.loan.model.FsscLoanTransfer" />
                </c:import>

            </html:form>
        </template:replace>
    </template:include>
