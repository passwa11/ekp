<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<%@page import="com.landray.kmss.hr.ratify.util.HrRatifyUtil" %>
    
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
                    contextPath: '${LUI_ContextPath}',
                };
                Com_IncludeFile("security.js");
                Com_IncludeFile("domain.js");
                Com_IncludeFile("form.js");
                Com_IncludeFile("form_option.js", "${LUI_ContextPath}/hr/ratify/hr_ratify_main/", 'js', true);
                Com_IncludeFile("main_edit.js", "${LUI_ContextPath}/hr/ratify/resource/js/", 'js', true);
                Com_IncludeFile("swf_attachment.js", "${KMSS_Parameter_ContextPath}sys/attachment/js/", "js", true);
            </script>
        </template:replace>
        <c:if test="${hrRatifyMainForm.method_GET == 'edit' || (param['i.docTemplate']!=null && param['i.docTemplate']!='')}">
            <template:replace name="title">
                <c:choose>
                    <c:when test="${hrRatifyMainForm.method_GET == 'add' }">
                        <c:out value="${ lfn:message('operation.create') } - ${ lfn:message('hr-ratify:table.hrRatifyMain') }" />
                    </c:when>
                    <c:otherwise>
                        <c:out value="${hrRatifyMainForm.docSubject} - " />
                        <c:out value="${ lfn:message('hr-ratify:table.hrRatifyMain') }" />
                    </c:otherwise>
                </c:choose>
            </template:replace>
            <template:replace name="toolbar">
                <ui:toolbar id="toolbar" layout="sys.ui.toolbar.float" count="3">
                    <c:choose>
                        <c:when test="${ hrRatifyMainForm.method_GET == 'edit' }">
                            <c:if test="${ hrRatifyMainForm.docStatus=='10' || hrRatifyMainForm.docStatus=='11' }">
                                <ui:button text="${ lfn:message('button.savedraft') }" onclick="if(validateDetail()){submitForm('${hrRatifyMainForm.docStatus}','update',true);}" />
                            </c:if>
                            <c:if test="${ hrRatifyMainForm.docStatus=='10' || hrRatifyMainForm.docStatus=='11' || hrRatifyMainForm.docStatus=='20' }">
                                <ui:button text="${ lfn:message('button.submit') }" onclick="if(validateDetail()){submitForm('20','update');}" />
                            </c:if>
                        </c:when>
                        <c:when test="${ hrRatifyMainForm.method_GET == 'add' }">
                            <ui:button text="${ lfn:message('button.savedraft') }" order="2" onclick="if(validateDetail()){submitForm('10','save',true);}" />
                            <ui:button text="${ lfn:message('button.submit') }" order="2" onclick="if(validateDetail()){submitForm('20','save');}" />
                        </c:when>
                    </c:choose>

                    <ui:button text="${ lfn:message('button.close') }" order="5" onclick="Com_CloseWindow();" />
                </ui:toolbar>
            </template:replace>
            <template:replace name="path">
                <ui:menu layout="sys.ui.menu.nav">
                    <ui:menu-item text="${ lfn:message('home.home') }" icon="lui_icon_s_home" />
                    <ui:menu-item text="${ lfn:message('hr-ratify:table.hrRatifyMain') }" />
                </ui:menu>
            </template:replace>
            <template:replace name="content">
                <html:form action="/hr/ratify/hr_ratify_main/hrRatifyMain.do">

                    <ui:tabpage expand="false" var-navwidth="90%">
                        <ui:content title="${lfn:message('hr-ratify:py.BiaoDanNeiRong')}" expand="true" toggle="false">
                            <table class="tb_normal" width=100%>
                                <%-- 标题--%>
                            	<tr>
	                            	<td align="right" style="border-right: 0px;" width=15%>
	                                    ${lfn:message('hr-ratify:hrRatifyMain.docSubject')}
	                                </td>
	                                <td style="border-left: 0px !important;">
	                                    <div id="_xform_docSubject" _xform_type="text">
	                                        <c:if test="${hrRatifyMainForm.titleRegulation==null || hrRatifyMainForm.titleRegulation=='' }">
												<xform:text property="docSubject"  style="width:98%;" className="inputsgl"/>
											</c:if>
											<c:if test="${hrRatifyMainForm.titleRegulation!=null && hrRatifyMainForm.titleRegulation!='' }">
												<xform:text property="docSubject" style="width:98%;height:auto;color:#333;" className="inputsgl" showStatus="readOnly" value="${lfn:message('hr-ratify:hrRatifyMain.docSubject.info') }" />
											</c:if>
	                                    </div>
	                                </td>
                            	</tr>
                            </table>
                            <br>
                            <c:if test="${hrRatifyMainForm.docUseXform == 'false'}">
                                <table class="tb_normal" width=100%>
                                    <tr>
                                        <td colspan="2">
                                            <kmss:editor property="docXform" width="95%" />
                                        </td>
                                    </tr>
                                </table>
                            </c:if>
                            <c:if test="${hrRatifyMainForm.docUseXform == 'true' || empty hrRatifyMainForm.docUseXform}">
                                <c:import url="/sys/xform/include/sysForm_edit.jsp" charEncoding="UTF-8">
                                    <c:param name="formName" value="hrRatifyMainForm" />
                                    <c:param name="fdKey" value="${fdTempKey }" />
                                    <c:param name="useTab" value="false" />
                                </c:import>
                            </c:if>
                        </ui:content>

                        <ui:content title="${ lfn:message('hr-ratify:py.JiBenXinXi') }" expand="true">
                            <table class="tb_normal" width="100%">
                                <tr>
                                	<td class="td_normal_title" width="15%">
                                		${lfn:message('hr-ratify:hrRatifyMKeyword.docKeyword')}
                                	</td>
                                	<td colspan="3">
                                		<xform:text property="fdKeywordNames" style="width:97%" />
                                	</td>
                                </tr>
                                <tr>
                                    <td class="td_normal_title" width="15%">
                                        ${lfn:message('hr-ratify:hrRatifyMain.docTemplate')}
                                    </td>
                                    <td colspan="3">
                                        <%-- 分类模板--%>
                                        <div id="_xform_docTemplateId" _xform_type="dialog">
                                            <html:hidden property="docTemplateId" /> 
											<c:out value="${ hrRatifyMainForm.docTemplateName}"/>
                                        </div>
                                    </td>
                                </tr>
                                <tr>
                                    <td class="td_normal_title" width="15%">
                                        ${lfn:message('hr-ratify:hrRatifyMain.docCreator')}
                                    </td>
                                    <td width="35%">
                                        <%-- 创建人--%>
                                        <div id="_xform_docCreatorId" _xform_type="address">
                                            <ui:person personId="${hrRatifyMainForm.docCreatorId}" personName="${hrRatifyMainForm.docCreatorName}" />
                                        </div>
                                    </td>
                                    <td class="td_normal_title" width="15%">
                                        ${lfn:message('hr-ratify:hrRatifyMain.docNumber')}
                                    </td>
                                    <td width="35%">
                                        <%-- 编号--%>
                                        <div id="_xform_docNumber" _xform_type="text">
                                            <c:if test="${not empty hrRatifyMainForm.docNumber}">
										   		<xform:text property="docNumber" showStatus="readOnly" style="width:95%;" />
										   	</c:if>	
										   	<c:if test="${empty hrRatifyMainForm.docNumber}">
										 	   	<span style="color: #868686;">${lfn:message("hr-ratify:hrRatifyMain.docNumber.title")}</span>
										   	</c:if>
                                        </div>
                                    </td>
                                </tr>
                                <tr>
                                    <td class="td_normal_title" width="15%">
                                        ${lfn:message('hr-ratify:hrRatifyMain.fdDepartment')}
                                    </td>
                                    <td width="35%">
                                        <%-- 部门--%>
                                        <div id="_xform_fdDepartmentId" _xform_type="address">
                                            <xform:address propertyId="fdDepartmentId" propertyName="fdDepartmentName" orgType="ORG_TYPE_ALL" showStatus="view" style="width:95%;" />
                                        </div>
                                    </td>
                                    <td class="td_normal_title" width="15%">
                                        ${lfn:message('hr-ratify:hrRatifyMain.docCreateTime')}
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
                                        ${lfn:message('hr-ratify:hrRatifyMain.docStatus')}
                                    </td>
                                    <td width="35%">
                                        <%-- 文档状态--%>
                                        <div id="_xform_docStatus" _xform_type="select">
                                            <xform:select property="docStatus" htmlElementProperties="id='docStatus'" showStatus="view">
                                                <xform:enumsDataSource enumsType="hr_ratify_doc_status" />
                                            </xform:select>
                                        </div>
                                    </td>
                                    <td class="td_normal_title" width="15%">
                                        ${lfn:message('hr-ratify:hrRatifyMain.docPublishTime')}
                                    </td>
                                    <td width="35%">
                                        <%-- 结束时间--%>
                                        <div id="_xform_docPublishTime" _xform_type="datetime">
                                            <xform:datetime property="docPublishTime" showStatus="view" dateTimeType="datetime" style="width:95%;" />
                                        </div>
                                    </td>
                                </tr>
                                <tr>
                                    <td class="td_normal_title" width="15%">
                                        ${lfn:message('hr-ratify:hrRatifyMain.fdFeedback')}
                                    </td>
                                    <td colspan="3">
                                        <%-- 实施反馈人--%>
                                        <div id="_xform_fdFeedbackIds" _xform_type="address">
                                            <xform:address propertyId="fdFeedbackIds" propertyName="fdFeedbackNames" mulSelect="true" orgType="ORG_TYPE_ALL" showStatus="edit" style="width:95%;" />
                                        </div>
                                    </td>
                                </tr>
                            </table>
                        </ui:content>
                        <c:import url="/sys/lbpmservice/import/sysLbpmProcess_edit.jsp" charEncoding="UTF-8">
                            <c:param name="formName" value="hrRatifyMainForm" />
                            <c:param name="fdKey" value="${fdTempKey }" />
                            <c:param name="isExpand" value="false" />
                        </c:import>

                        <c:import url="/sys/right/import/right_edit.jsp" charEncoding="UTF-8">
                            <c:param name="formName" value="hrRatifyMainForm" />
                            <c:param name="moduleModelName" value="com.landray.kmss.hr.ratify.model.HrRatifyMain" />
                        </c:import>

                    </ui:tabpage>
                    <html:hidden property="fdId" />
                    <html:hidden property="docStatus" />
                    <html:hidden property="docTemplateId" />
                    <html:hidden property="method_GET" />
                </html:form>
            </template:replace>

        </c:if>
    </template:include>