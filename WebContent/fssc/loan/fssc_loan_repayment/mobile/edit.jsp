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
                <c:when test="${fsscLoanRepaymentForm.method_GET == 'add' }">
                    <c:out value="${ lfn:message('operation.create') } - ${ lfn:message('fssc-loan:table.fsscLoanRepayment') }" />
                </c:when>
                <c:otherwise>
                    <c:out value="${fsscLoanRepaymentForm.docSubject} - " />
                    <c:out value="${lfn:message('fssc-loan:table.fsscLoanRepayment') }" />
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
                Com_IncludeFile("form_option.js", "${LUI_ContextPath}/fssc/loan/fssc_loan_repayment/", 'js', true);
                Com_IncludeFile("mobile_edit.js", "${LUI_ContextPath}/fssc/loan/resource/js/", 'js', true);
            </script>
        </template:replace>
        <template:replace name="content">
            <html:form action="/fssc/loan/fssc_loan_repayment/fsscLoanRepayment.do?method=save">

                <div id="scrollView" data-dojo-type="mui/view/DocScrollableView" data-dojo-mixins="mui/form/_ValidateMixin">
                    <div data-dojo-type="mui/panel/AccordionPanel">
                        <div data-dojo-type="mui/panel/Content" data-dojo-props="title:'${lfn:message('fssc-loan:py.JiBenXinXi') }',icon:'mui-ul'">
                            <div class="muiFormContent">
                                <table class="muiSimple" cellpadding="0" cellspacing="0">
                                    <tr>
                                        <td class="muiTitle">
                                            ${lfn:message('fssc-loan:fsscLoanRepayment.fdRepaymentPerson')}
                                        </td>
                                        <td>
                                            <div id="_xform_fdRepaymentPersonId" _xform_type="address">
                                                <xform:address propertyId="fdRepaymentPersonId" propertyName="fdRepaymentPersonName" orgType="ORG_TYPE_PERSON" showStatus="edit" required="true" subject="${lfn:message('fssc-loan:fsscLoanRepayment.fdRepaymentPerson')}" mobile="true" style="width:95%;"
                                                />
                                            </div>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td class="muiTitle">
                                            ${lfn:message('fssc-loan:fsscLoanRepayment.fdRepaymentDept')}
                                        </td>
                                        <td>
                                            <div id="_xform_fdRepaymentDeptId" _xform_type="address">
                                                <xform:address propertyId="fdRepaymentDeptId" propertyName="fdRepaymentDeptName" orgType="ORG_TYPE_ORGORDEPT" showStatus="view" mobile="true" style="width:95%;" />
                                            </div>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td class="muiTitle">
                                            ${lfn:message('fssc-loan:fsscLoanRepayment.docCreateTime')}
                                        </td>
                                        <td>
                                            <div id="_xform_docCreateTime" _xform_type="datetime">
                                                <xform:datetime property="docCreateTime" showStatus="view" dateTimeType="datetime" mobile="true" style="width:95%;" />
                                            </div>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td class="muiTitle">
                                            ${lfn:message('fssc-loan:fsscLoanRepayment.fdLoanMain')}
                                        </td>
                                        <td>
                                            <div id="_xform_fdLoanMainId" _xform_type="dialog">
                                                <div data-dojo-type="mui/form/CommonDialog" data-dojo-props="idField:'fdLoanMainId',nameField:'fdLoanMainName',isMul:false,modelName:'com.landray.kmss.fssc.loan.model.FsscLoanMain',dataURL:getSource('fssc_loan_main_getLoanMain'),subject:'${lfn:message('fssc-loan:fsscLoanRepayment.fdLoanMain')}',required:true,curIds:'${lfn:escapeHtml(lfn:escapeJs(fsscLoanRepaymentForm.fdLoanMainId))}',curNames:'${lfn:escapeHtml(lfn:escapeJs(fsscLoanRepaymentForm.fdLoanMainName))}',afterSelect:afterDialogSel">
                                                </div>
                                            </div>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td class="muiTitle">
                                            ${lfn:message('fssc-loan:fsscLoanRepayment.fdCanOffsetMoney')}
                                        </td>
                                        <td>
                                            <div id="_xform_fdCanOffsetMoney" _xform_type="text">
                                                <xform:text property="fdCanOffsetMoney" showStatus="view" mobile="true" style="width:95%;" />
                                            </div>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td class="muiTitle">
                                            ${lfn:message('fssc-loan:fsscLoanRepayment.fdRepaymentMoney')}
                                        </td>
                                        <td>
                                            <div id="_xform_fdRepaymentMoney" _xform_type="text">
                                                <xform:text property="fdRepaymentMoney" showStatus="edit" validators=" number min(0)" mobile="true" style="width:95%;" />
                                            </div>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td class="muiTitle">
                                            ${lfn:message('fssc-loan:fsscLoanRepayment.fdBasePayWay')}
                                        </td>
                                        <td>
                                            <div id="_xform_fdBasePayWayId" _xform_type="dialog">
                                                <div data-dojo-type="mui/form/CommonDialog" data-dojo-props="idField:'fdBasePayWayId',nameField:'fdBasePayWayName',isMul:false,modelName:'com.landray.kmss.eop.basedata.model.EopBasedataPayWay',dataURL:getSource('eop_basedata_pay_way_getPayWay'),subject:'${lfn:message('fssc-loan:fsscLoanRepayment.fdBasePayWay')}',required:true,curIds:'${lfn:escapeHtml(lfn:escapeJs(fsscLoanRepaymentForm.fdBasePayWayId))}',curNames:'${lfn:escapeHtml(lfn:escapeJs(fsscLoanRepaymentForm.fdBasePayWayName))}',afterSelect:afterDialogSel">
                                                </div>
                                            </div>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td class="muiTitle">
                                            ${lfn:message('fssc-loan:fsscLoanRepayment.fdPaymentAccount')}
                                        </td>
                                        <td>
                                            <div id="_xform_fdPaymentAccount" _xform_type="text">
                                                <xform:text property="fdPaymentAccount" showStatus="edit" mobile="true" style="width:95%;" />
                                            </div>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td class="muiTitle">
                                            ${lfn:message('fssc-loan:fsscLoanRepayment.fdProveStatus')}
                                        </td>
                                        <td>
                                            <div id="_xform_fdProveStatus" _xform_type="text">
                                                <xform:text property="fdProveStatus" showStatus="view" mobile="true" style="width:95%;" />
                                            </div>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td class="muiTitle">
                                            ${lfn:message('fssc-loan:fsscLoanRepayment.fdReason')}
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
                    <c:param name="formName" value="fsscLoanRepaymentForm" />
                    <c:param name="fdKey" value="fsscLoanRepayment" />
                    <c:param name="viewName" value="lbpmView" />
                    <c:param name="backTo" value="scrollView" />
                    <c:param name="onClickSubmitButton" value="form_submit();" />
                </c:import>

                <c:import url="/sys/right/mobile/edit_hidden.jsp" charEncoding="UTF-8">
                    <c:param name="formName" value="fsscLoanRepaymentForm" />
                    <c:param name="moduleModelName" value="com.landray.kmss.fssc.loan.model.FsscLoanRepayment" />
                </c:import>

            </html:form>
        </template:replace>
    </template:include>
