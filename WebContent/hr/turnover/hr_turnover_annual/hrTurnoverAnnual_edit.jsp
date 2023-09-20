<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<%@page import="com.landray.kmss.hr.turnover.util.HrTurnoverUtil" %>
    
        <% pageContext.setAttribute("currentUser", UserUtil.getKMSSUser());
        pageContext.setAttribute("currentPerson", UserUtil.getKMSSUser().getUserId());
        pageContext.setAttribute("currentPost", UserUtil.getKMSSUser().getPostIds());
        pageContext.setAttribute("currentDept", UserUtil.getKMSSUser().getDeptId());
        if(UserUtil.getUser().getFdParentOrg() != null) {
            pageContext.setAttribute("currentOrg", UserUtil.getUser().getFdParentOrg().getFdId());
        } else {
            pageContext.setAttribute("currentOrg", "");
        } %>
    
    <template:include ref="default.edit">
        <template:replace name="head">
            <style type="text/css">
                
                		.lui_paragraph_title{
                			font-size: 15px;
                			color: #15a4fa;
                	    	padding: 15px 0px 5px 0px;
                		}
                		.lui_paragraph_title span{
                			display: inline-block;
                			margin: -2px 5px 0px 0px;
                		}
                		.inputsgl[readonly], .tb_normal .inputsgl[readonly] {
                		    border: 0px;
                		    color: #868686
                		}
                		
            </style>
            <script type="text/javascript">
                var formInitData = {

                };
                var messageInfo = {

                };

                var initData = {
                    contextPath: '${LUI_ContextPath}'
                };
                Com_IncludeFile("security.js");
                Com_IncludeFile("domain.js");
                Com_IncludeFile("form.js");
                Com_IncludeFile("form_option.js", "${LUI_ContextPath}/hr/turnover/hr_turnover_annual/", 'js', true);
                Com_IncludeFile("main_edit.js", "${LUI_ContextPath}/hr/turnover/resource/js/", 'js', true);
                Com_IncludeFile("swf_attachment.js", "${KMSS_Parameter_ContextPath}sys/attachment/js/", "js", true);
            </script>
        </template:replace>

        <template:replace name="title">
            <c:choose>
                <c:when test="${hrTurnoverAnnualForm.method_GET == 'add' }">
                    <c:out value="${ lfn:message('operation.create') } - ${ lfn:message('hr-turnover:table.hrTurnoverAnnual') }" />
                </c:when>
                <c:otherwise>
                    <c:out value="${hrTurnoverAnnualForm.fdDesc} - " />
                    <c:out value="${ lfn:message('hr-turnover:table.hrTurnoverAnnual') }" />
                </c:otherwise>
            </c:choose>
        </template:replace>
        <template:replace name="toolbar">
            <ui:toolbar id="toolbar" layout="sys.ui.toolbar.float" count="3">
                <c:choose>
                    <c:when test="${ hrTurnoverAnnualForm.method_GET == 'edit' }">
                        <ui:button text="${ lfn:message('button.update') }" onclick="if(validateDetail()){Com_Submit(document.hrTurnoverAnnualForm, 'update');}" />
                    </c:when>
                    <c:when test="${ hrTurnoverAnnualForm.method_GET == 'add' }">
                        <ui:button text="${ lfn:message('button.save') }" onclick="if(validateDetail()){Com_Submit(document.hrTurnoverAnnualForm, 'save');}" />
                    </c:when>
                </c:choose>

                <ui:button text="${ lfn:message('button.close') }" order="5" onclick="Com_CloseWindow();" />
            </ui:toolbar>
        </template:replace>
        <template:replace name="path">
            <ui:menu layout="sys.ui.menu.nav">
                <ui:menu-item text="${ lfn:message('home.home') }" icon="lui_icon_s_home" />
                <ui:menu-item text="${ lfn:message('hr-turnover:table.hrTurnoverAnnual') }" />
            </ui:menu>
        </template:replace>
        <template:replace name="content">
            <html:form action="/hr/turnover/hr_turnover_annual/hrTurnoverAnnual.do">

                <ui:tabpage expand="false" var-navwidth="90%">
                    <ui:content title="${ lfn:message('hr-turnover:py.JiBenXinXi') }" expand="true">
                        <table class="tb_normal" width="100%">
                            <tr>
                                <td class="td_normal_title" width="15%">
                                    ${lfn:message('hr-turnover:hrTurnoverAnnual.docCreator')}
                                </td>
                                <td width="35%">
                                    <%-- 创建人--%>
                                    <div id="_xform_docCreatorId" _xform_type="address">
                                        <ui:person personId="${hrTurnoverAnnualForm.docCreatorId}" personName="${hrTurnoverAnnualForm.docCreatorName}" />
                                    </div>
                                </td>
                                <td class="td_normal_title" width="15%">
                                    ${lfn:message('hr-turnover:hrTurnoverAnnual.docCreateTime')}
                                </td>
                                <td width="35%">
                                    <%-- 创建时间--%>
                                    <div id="_xform_docCreateTime" _xform_type="datetime">
                                        <xform:datetime property="docCreateTime" showStatus="view" dateTimeType="datetime" style="width:95%;" />
                                    </div>
                                </td>
                            </tr>
                            <tr>
                                <td class="td_normal_title" width="15%">
                                    ${lfn:message('hr-turnover:hrTurnoverAnnual.fdYear')}
                                </td>
                                <td width="35%">
                                    <%-- 年度--%>
                                    <div id="_xform_fdYear" _xform_type="text">
                                        <xform:text property="fdYear" showStatus="edit" validators=" digits min(2021) max(9999)" style="width:95%;" />
                                    </div>
                                </td>
                                <td class="td_normal_title" width="15%">
                                    ${lfn:message('hr-turnover:hrTurnoverAnnual.fdRateO')}
                                </td>
                                <td width="35%">
                                    <%-- O类离职率目标值--%>
                                    <div id="_xform_fdRateO" _xform_type="text">
                                        <xform:text property="fdRateO" showStatus="edit" validators=" number" style="width:95%;" />
                                    </div>
                                </td>
                            </tr>
                            <tr>
                                <td class="td_normal_title" width="15%">
                                    ${lfn:message('hr-turnover:hrTurnoverAnnual.fdRateP')}
                                </td>
                                <td width="35%">
                                    <%-- P类离职率目标值--%>
                                    <div id="_xform_fdRateP" _xform_type="text">
                                        <xform:text property="fdRateP" showStatus="edit" validators=" number" style="width:95%;" />
                                    </div>
                                </td>
                                <td class="td_normal_title" width="15%">
                                    ${lfn:message('hr-turnover:hrTurnoverAnnual.fdRateS')}
                                </td>
                                <td width="35%">
                                    <%-- S类离职率目标值--%>
                                    <div id="_xform_fdRateS" _xform_type="text">
                                        <xform:text property="fdRateS" showStatus="edit" validators=" number" style="width:95%;" />
                                    </div>
                                </td>
                            </tr>
                            <tr>
                                <td class="td_normal_title" width="15%">
                                    ${lfn:message('hr-turnover:hrTurnoverAnnual.fdRateM')}
                                </td>
                                <td width="35%">
                                    <%-- M类离职率目标值--%>
                                    <div id="_xform_fdRateM" _xform_type="text">
                                        <xform:text property="fdRateM" showStatus="edit" validators=" number" style="width:95%;" />
                                    </div>
                                </td>
                                <td colspan="2" width="50.0%">
                                </td>
                            </tr>
                            <tr>
                                <td class="td_normal_title" width="15%">
                                    ${lfn:message('hr-turnover:hrTurnoverAnnual.fdDesc')}
                                </td>
                                <td colspan="3" width="85.0%">
                                    <%-- 描述--%>
                                    <div id="_xform_fdDesc" _xform_type="textarea">
                                        <xform:textarea property="fdDesc" showStatus="edit" style="width:95%;" />
                                    </div>
                                </td>
                            </tr>
                        </table>
                    </ui:content>
                </ui:tabpage>
                <html:hidden property="fdId" />


                <html:hidden property="method_GET" />
            </html:form>
        </template:replace>


    </template:include>