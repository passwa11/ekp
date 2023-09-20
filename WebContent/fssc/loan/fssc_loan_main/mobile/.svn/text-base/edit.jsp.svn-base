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
                <c:when test="${fsscLoanMainForm.method_GET == 'add' }">
                    <c:out value="${ lfn:message('operation.create') } - ${ lfn:message('fssc-loan:table.fsscLoanMain') }" />
                </c:when>
                <c:otherwise>
                    <c:out value="${fsscLoanMainForm.docSubject} - " />
                    <c:out value="${lfn:message('fssc-loan:table.fsscLoanMain') }" />
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
                Com_IncludeFile("form_option.js", "${LUI_ContextPath}/fssc/loan/fssc_loan_main/", 'js', true);
                Com_IncludeFile("mobile_edit.js", "${LUI_ContextPath}/fssc/loan/resource/js/", 'js', true);
            </script>
        </template:replace>
        <template:replace name="content">
            <html:form action="/fssc/loan/fssc_loan_main/fsscLoanMain.do?method=save">

                <div id="scrollView" data-dojo-type="mui/view/DocScrollableView" data-dojo-mixins="mui/form/_ValidateMixin">
                    <div data-dojo-type="mui/panel/AccordionPanel">

                        <div data-dojo-type="mui/panel/Content" data-dojo-props="title:'${lfn:message('fssc-loan:py.JiBenXinXi') }',icon:'mui-ul'">
                            <div class="muiFormContent">
                                <table class="muiSimple" cellpadding="0" cellspacing="0">
                                    <tr>
                                        <td class="muiTitle">
                                            ${lfn:message('fssc-loan:fsscLoanMain.docSubject')}
                                        </td>
                                        <td>
                                            <div id="_xform_docSubject" _xform_type="text">
                                                <xform:text property="docSubject" showStatus="edit" mobile="true" style="width:95%;" />
                                            </div>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td class="muiTitle">
                                            ${lfn:message('fssc-loan:fsscLoanMain.fdLoanPerson')}
                                        </td>
                                        <td>
                                            <div id="_xform_fdLoanPersonId" _xform_type="address">
                                                <xform:address propertyId="fdLoanPersonId" propertyName="fdLoanPersonName" orgType="ORG_TYPE_PERSON" showStatus="edit" required="true" subject="${lfn:message('fssc-loan:fsscLoanMain.fdLoanPerson')}" mobile="true" style="width:95%;" />
                                            </div>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td class="muiTitle">
                                            ${lfn:message('fssc-loan:fsscLoanMain.fdLoanDept')}
                                        </td>
                                        <td>
                                            <div id="_xform_fdLoanDeptId" _xform_type="address">
                                                <xform:address propertyId="fdLoanDeptId" propertyName="fdLoanDeptName" orgType="ORG_TYPE_ORGORDEPT" showStatus="view" mobile="true" style="width:95%;" />
                                            </div>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td class="muiTitle">
                                            ${lfn:message('fssc-loan:fsscLoanMain.fdCostCenter')}
                                        </td>
                                        <td>
                                            <div id="_xform_fdCostCenterId" _xform_type="dialog">
                                                <div data-dojo-type="mui/form/CommonDialog" data-dojo-props="idField:'fdCostCenterId',nameField:'fdCostCenterName',isMul:false,modelName:'com.landray.kmss.eop.basedata.model.EopBasedataCostCenter',dataURL:getSource('eop_basedata_cost_center_selectCostCenter'),subject:'${lfn:message('fssc-loan:fsscLoanMain.fdCostCenter')}',required:true,curIds:'${lfn:escapeHtml(lfn:escapeJs(fsscLoanMainForm.fdCostCenterId))}',curNames:'${lfn:escapeHtml(lfn:escapeJs(fsscLoanMainForm.fdCostCenterName))}',afterSelect:afterDialogSel">
                                                </div>
                                            </div>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td class="muiTitle">
                                            ${lfn:message('fssc-loan:fsscLoanMain.fdCompany')}
                                        </td>
                                        <td>
                                            <div id="_xform_fdCompanyId" _xform_type="dialog">
                                                <div data-dojo-type="mui/form/CommonDialog" data-dojo-props="idField:'fdCompanyId',nameField:'fdCompanyName',isMul:false,modelName:'com.landray.kmss.eop.basedata.model.EopBasedataCompany',dataURL:getSource('eop_basedata_company_fdCompany'),subject:'${lfn:message('fssc-loan:fsscLoanMain.fdCompany')}',required:true,curIds:'${lfn:escapeHtml(lfn:escapeJs(fsscLoanMainForm.fdCompanyId))}',curNames:'${lfn:escapeHtml(lfn:escapeJs(fsscLoanMainForm.fdCompanyName))}',afterSelect:afterDialogSel">
                                                </div>
                                            </div>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td class="muiTitle">
                                            ${lfn:message('fssc-loan:fsscLoanMain.fdLoanMoney')}
                                        </td>
                                        <td>
                                            <div id="_xform_fdLoanMoney" _xform_type="text">
                                                <xform:text property="fdLoanMoney" showStatus="edit" validators=" number min(0)" mobile="true" style="width:95%;" />
                                            </div>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td class="muiTitle">
                                            ${lfn:message('fssc-loan:fsscLoanMain.fdExpectedDate')}
                                        </td>
                                        <td>
                                            <div id="_xform_fdExpectedDate" _xform_type="datetime">
                                                <xform:datetime property="fdExpectedDate" showStatus="edit" dateTimeType="date" required="true" mobile="true" style="width:95%;" />
                                            </div>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td class="muiTitle">
                                            ${lfn:message('fssc-loan:fsscLoanMain.fdOffsetters')}
                                        </td>
                                        <td>
                                            <div id="_xform_fdOffsetterIds" _xform_type="address">
                                                <xform:address propertyId="fdOffsetterIds" propertyName="fdOffsetterNames" mulSelect="true" orgType="ORG_TYPE_ALL" showStatus="edit" required="true" subject="${lfn:message('fssc-loan:fsscLoanMain.fdOffsetters')}" mobile="true" style="width:95%;" />
                                            </div>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td class="muiTitle">
                                            ${lfn:message('fssc-loan:fsscLoanMain.fdTotalLoanMoney')}
                                        </td>
                                        <td>
                                            <div id="_xform_fdTotalLoanMoney" _xform_type="text">
                                                <xform:text property="fdTotalLoanMoney" showStatus="view" mobile="true" style="width:95%;" />
                                            </div>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td class="muiTitle">
                                            ${lfn:message('fssc-loan:fsscLoanMain.fdTotalRepaymentMoney')}
                                        </td>
                                        <td>
                                            <div id="_xform_fdTotalRepaymentMoney" _xform_type="text">
                                                <xform:text property="fdTotalRepaymentMoney" showStatus="view" mobile="true" style="width:95%;" />
                                            </div>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td class="muiTitle">
                                            ${lfn:message('fssc-loan:fsscLoanMain.fdBasePayWay')}
                                        </td>
                                        <td>
                                            <div id="_xform_fdBasePayWayId" _xform_type="dialog">
                                                <div data-dojo-type="mui/form/CommonDialog" data-dojo-props="idField:'fdBasePayWayId',nameField:'fdBasePayWayName',isMul:false,modelName:'com.landray.kmss.eop.basedata.model.EopBasedataPayWay',dataURL:getSource('eop_basedata_pay_way_getPayWay'),subject:'${lfn:message('fssc-loan:fsscLoanMain.fdBasePayWay')}',required:true,curIds:'${lfn:escapeHtml(lfn:escapeJs(fsscLoanMainForm.fdBasePayWayId))}',curNames:'${lfn:escapeHtml(lfn:escapeJs(fsscLoanMainForm.fdBasePayWayName))}',afterSelect:afterDialogSel">
                                                </div>
                                            </div>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td class="muiTitle">
                                            ${lfn:message('fssc-loan:fsscLoanMain.fdAccPayeeName')}
                                        </td>
                                        <td>
                                            <div id="_xform_fdAccPayeeName" _xform_type="text">
                                                <xform:text property="fdAccPayeeName" showStatus="edit" mobile="true" style="width:95%;" />
                                            </div>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td class="muiTitle">
                                            ${lfn:message('fssc-loan:fsscLoanMain.fdPayeeAccount')}
                                        </td>
                                        <td>
                                            <div id="_xform_fdPayeeAccount" _xform_type="text">
                                                <xform:text property="fdPayeeAccount" showStatus="edit" mobile="true" style="width:95%;" />
                                            </div>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td class="muiTitle">
                                            ${lfn:message('fssc-loan:fsscLoanMain.fdPayeeBank')}
                                        </td>
                                        <td>
                                            <div id="_xform_fdPayeeBank" _xform_type="text">
                                                <xform:text property="fdPayeeBank" showStatus="edit" mobile="true" style="width:95%;" />
                                            </div>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td class="muiTitle">
                                            ${lfn:message('fssc-loan:fsscLoanMain.fdReason')}
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
                    <c:param name="formName" value="fsscLoanMainForm" />
                    <c:param name="fdKey" value="fsscLoanMain" />
                    <c:param name="viewName" value="lbpmView" />
                    <c:param name="backTo" value="scrollView" />
                    <c:param name="onClickSubmitButton" value="form_submit();" />
                </c:import>

                <c:import url="/sys/right/mobile/edit_hidden.jsp" charEncoding="UTF-8">
                    <c:param name="formName" value="fsscLoanMainForm" />
                    <c:param name="moduleModelName" value="com.landray.kmss.fssc.loan.model.FsscLoanMain" />
                </c:import>

            </html:form>
        </template:replace>
    </template:include>
