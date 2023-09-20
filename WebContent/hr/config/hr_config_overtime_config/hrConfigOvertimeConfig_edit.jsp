<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<%@page import="com.landray.kmss.hr.config.util.HrConfigUtil" %>
    
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
                Com_IncludeFile("form_option.js", "${LUI_ContextPath}/hr/config/hr_config_overtime_config/", 'js', true);
                Com_IncludeFile("main_edit.js", "${LUI_ContextPath}/hr/config/resource/js/", 'js', true);
                Com_IncludeFile("swf_attachment.js", "${KMSS_Parameter_ContextPath}sys/attachment/js/", "js", true);
            </script>
        </template:replace>

        <template:replace name="title">
            <c:choose>
                <c:when test="${hrConfigOvertimeConfigForm.method_GET == 'add' }">
                    <c:out value="${ lfn:message('operation.create') } - ${ lfn:message('hr-config:table.hrConfigOvertimeConfig') }" />
                </c:when>
                <c:otherwise>
                    <c:out value="${hrConfigOvertimeConfigForm.fdName} - " />
                    <c:out value="${ lfn:message('hr-config:table.hrConfigOvertimeConfig') }" />
                </c:otherwise>
            </c:choose>
        </template:replace>
        <template:replace name="toolbar">
            <ui:toolbar id="toolbar" layout="sys.ui.toolbar.float" count="3">
                <c:choose>
                    <c:when test="${ hrConfigOvertimeConfigForm.method_GET == 'edit' }">
                        <ui:button text="${ lfn:message('button.update') }" onclick="if(validateDetail()){Com_Submit(document.hrConfigOvertimeConfigForm, 'update');}" />
                    </c:when>
                    <c:when test="${ hrConfigOvertimeConfigForm.method_GET == 'add' }">
                        <ui:button text="${ lfn:message('button.save') }" onclick="if(validateDetail()){Com_Submit(document.hrConfigOvertimeConfigForm, 'save');}" />
                    </c:when>
                </c:choose>

                <ui:button text="${ lfn:message('button.close') }" order="5" onclick="Com_CloseWindow();" />
            </ui:toolbar>
        </template:replace>
        <template:replace name="path">
            <ui:menu layout="sys.ui.menu.nav">
                <ui:menu-item text="${ lfn:message('home.home') }" icon="lui_icon_s_home" />
                <ui:menu-item text="${ lfn:message('hr-config:table.hrConfigOvertimeConfig') }" />
            </ui:menu>
        </template:replace>
        <template:replace name="content">
            <html:form action="/hr/config/hr_config_overtime_config/hrConfigOvertimeConfig.do">

                <ui:tabpage expand="false" var-navwidth="90%">
                    <ui:content title="${ lfn:message('hr-config:py.JiBenXinXi') }" expand="true">
                        <div class='lui_form_title_frame'>
                            <div class='lui_form_subject'>
                                ${lfn:message('hr-config:table.hrConfigOvertimeConfig')}
                            </div>
                            <div class='lui_form_baseinfo'>

                            </div>
                        </div>
                        <table class="tb_normal" width="100%">
                            <tr>
                                <td class="td_normal_title" width="15%">
                                    ${lfn:message('hr-config:hrConfigOvertimeConfig.fdName')}
                                </td>
                                <td width="35%">
                                    <%-- 规则名称--%>
                                    <div id="_xform_fdName" _xform_type="text">
                                        <xform:text property="fdName" showStatus="edit" style="width:95%;" />
                                    </div>
                                </td>
                                <td class="td_normal_title" width="15%">
                                    ${lfn:message('hr-config:hrConfigOvertimeConfig.fdOrg')}
                                </td>
                                <td width="35%">
                                    <%-- 特殊人员--%>
                                    <div id="_xform_fdOrgId" _xform_type="address">
                                        <xform:address propertyId="fdOrgIds" propertyName="fdOrgNames"  mulSelect="true" orgType="ORG_TYPE_PERSON" showStatus="edit" style="width:95%;" />
                                    </div>
                                </td>
                            </tr>
                            <tr>
                                <td class="td_normal_title" width="15%">
                                    ${lfn:message('hr-config:hrConfigOvertimeConfig.fdOvertimeType')}
                                </td>
                                <td width="35%">
                                    <%-- 加班类别--%>
                                    <div id="_xform_fdOvertimeType" _xform_type=checkbox>
                                        <xform:checkbox property="fdOvertimeType" htmlElementProperties="id='fdOvertimeType'" showStatus="edit">
                                            <xform:enumsDataSource enumsType="hr_config_overtime_type" />
                                        </xform:checkbox>
                                    </div>
                                </td>
                                <td class="td_normal_title" width="15%">
                                    ${lfn:message('hr-config:hrConfigOvertimeConfig.fdOvertimeWelfare')}
                                </td>
                                <td width="35%">
                                    <%-- 加班补偿--%>
                                    <div id="_xform_fdOvertimeWelfare" _xform_type="radio">
                                        <xform:checkbox property="fdOvertimeWelfare" htmlElementProperties="id='fdOvertimeWelfare'" showStatus="edit">
                                            <xform:enumsDataSource enumsType="hr_config_overrtime_welfare" />
                                        </xform:checkbox>
                                    </div>
                                </td>
                            </tr>
                            <tr>
                                <td class="td_normal_title" width="15%">
                                    ${lfn:message('hr-config:hrConfigOvertimeConfig.fdRank')}
                                </td>
							<td colspan="3" width="85.0%">
								<%-- 职级--%> <xform:dialog propertyId="fdRankIds"
									propertyName="fdRankNames" showStatus="edit" required="true"
									subject="${lfn:message('hr-config:hrConfigOvertimeConfig.fdRank')}"
									style="width:95%;">
                                            dialogSelect(true,'hr_organization_rank','fdRankIds','fdRankNames',null,{},null);
                                        </xform:dialog>
								</div>
							</td>
						</tr>
                            <tr>
                                <td class="td_normal_title" width="15%">
                                    ${lfn:message('hr-config:hrConfigOvertimeConfig.fdWorkTime')}
                                </td>
                                <td width="35%">
                                    <%-- 标准工作时长--%>
                                    <div id="_xform_fdWorkTime" _xform_type="text">
                                        <xform:text property="fdWorkTime" showStatus="edit" validators=" digits min(0) max(10)" style="width:95%;" />
                                    </div>
                                </td>
                                <td class="td_normal_title" width="15%">
                                    ${lfn:message('hr-config:hrConfigOvertimeConfig.fdIsAvailable')}
                                </td>
                                <td width="35%">
                                    <%-- 是否有效--%>
                                    <div id="_xform_fdIsAvailable" _xform_type="radio">
                                        <xform:radio property="fdIsAvailable" htmlElementProperties="id='fdIsAvailable'" showStatus="edit">
                                            <xform:enumsDataSource enumsType="common_yesno" />
                                        </xform:radio>
                                    </div>
                                </td>
                            </tr>
                            <tr>
                                <td class="td_normal_title" width="15%">
                                    ${lfn:message('hr-config:hrConfigOvertimeConfig.docCreateTime')}
                                </td>
                                <td width="35%">
                                    <%-- 创建时间--%>
                                    <div id="_xform_docCreateTime" _xform_type="datetime">
                                        <xform:datetime property="docCreateTime" showStatus="view" dateTimeType="datetime" style="width:95%;" />
                                    </div>
                                </td>
                                <td class="td_normal_title" width="15%">
                                    ${lfn:message('hr-config:hrConfigOvertimeConfig.docCreator')}
                                </td>
                                <td width="35%">
                                    <%-- 创建人--%>
                                    <div id="_xform_docCreatorId" _xform_type="address">
                                        <ui:person personId="${hrConfigOvertimeConfigForm.docCreatorId}" personName="${hrConfigOvertimeConfigForm.docCreatorName}" />
                                    </div>
                                </td>
                            </tr>
                        </table>
                    </ui:content>
                </ui:tabpage>
                <html:hidden property="fdId" />


                <html:hidden property="method_GET" />
            </html:form>
            <script>
            function orgRankChange(value,_this){
            	debugger;
    		}
           </script>
        </template:replace>


    </template:include>