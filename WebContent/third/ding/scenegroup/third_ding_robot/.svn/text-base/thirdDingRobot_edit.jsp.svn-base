<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<%@page import="com.landray.kmss.third.ding.scenegroup.util.ThirdDingUtil" %>
    
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
                Com_IncludeFile("form_option.js", "${LUI_ContextPath}/third/ding/scenegroup/third_ding_robot/", 'js', true);
                Com_IncludeFile("main_edit.js", "${LUI_ContextPath}/third/ding/resource/js/", 'js', true);
                Com_IncludeFile("swf_attachment.js", "${KMSS_Parameter_ContextPath}sys/attachment/js/", "js", true);
            </script>
        </template:replace>

        <template:replace name="title">
            <c:choose>
                <c:when test="${thirdDingRobotForm.method_GET == 'add' }">
                    <c:out value="${ lfn:message('operation.create') } - ${ lfn:message('third-ding-scenegroup:table.thirdDingRobot') }" />
                </c:when>
                <c:otherwise>
                    <c:out value="${thirdDingRobotForm.fdName} - " />
                    <c:out value="${ lfn:message('third-ding-scenegroup:table.thirdDingRobot') }" />
                </c:otherwise>
            </c:choose>
        </template:replace>
        <template:replace name="toolbar">
            <ui:toolbar id="toolbar" layout="sys.ui.toolbar.float" count="3">
                <c:choose>
                    <c:when test="${ thirdDingRobotForm.method_GET == 'edit' }">
                        <ui:button text="${ lfn:message('button.update') }" onclick="if(validateDetail()){Com_Submit(document.thirdDingRobotForm, 'update');}" />
                    </c:when>
                    <c:when test="${ thirdDingRobotForm.method_GET == 'add' }">
                        <ui:button text="${ lfn:message('button.save') }" onclick="if(validateDetail()){Com_Submit(document.thirdDingRobotForm, 'save');}" />
                    </c:when>
                </c:choose>

                <ui:button text="${ lfn:message('button.close') }" order="5" onclick="Com_CloseWindow();" />
            </ui:toolbar>
        </template:replace>
        <template:replace name="path">
            <ui:menu layout="sys.ui.menu.nav">
                <ui:menu-item text="${ lfn:message('home.home') }" icon="lui_icon_s_home" />
                <ui:menu-item text="${ lfn:message('third-ding-scenegroup:table.thirdDingRobot') }" />
            </ui:menu>
        </template:replace>
        <template:replace name="content">
            <html:form action="/third/ding/scenegroup/third_ding_robot/thirdDingRobot.do">

                <ui:tabpage expand="false" var-navwidth="90%">
                    <ui:content title="${ lfn:message('third-ding-scenegroup:py.JiBenXinXi') }" expand="true">
                        <div class='lui_form_title_frame'>
                            <div class='lui_form_subject'>
                                ${lfn:message('third-ding-scenegroup:table.thirdDingRobot')}
                            </div>
                            <div class='lui_form_baseinfo'>

                            </div>
                        </div>
                        <table class="tb_normal" width="100%">
                            <tr>
                                <td class="td_normal_title" width="15%">
                                    ${lfn:message('third-ding-scenegroup:thirdDingRobot.fdName')}
                                </td>
                                <td width="35%">
                                    <%-- 名称--%>
                                    <div id="_xform_fdName" _xform_type="text">
                                        <xform:text property="fdName" showStatus="edit" style="width:95%;" />
                                    </div>
                                </td>
                                <td class="td_normal_title" width="15%">
                                    ${lfn:message('third-ding-scenegroup:thirdDingRobot.fdIsAvailable')}
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
                                    ${lfn:message('third-ding-scenegroup:thirdDingRobot.fdDesc')}
                                </td>
                                <td colspan="3" width="85.0%">
                                    <%-- 描述--%>
                                    <div id="_xform_fdDesc" _xform_type="textarea">
                                        <xform:textarea property="fdDesc" showStatus="edit" style="width:95%;" />
                                    </div>
                                </td>
                            </tr>
                            <tr>
                                <td class="td_normal_title" width="15%">
                                    ${lfn:message('third-ding-scenegroup:thirdDingRobot.fdWebhook')}
                                </td>
                                <td width="35%">
                                    <%-- Webhook地址--%>
                                    <div id="_xform_fdWebhook" _xform_type="text">
                                        <xform:text property="fdWebhook" showStatus="edit" style="width:95%;" />
                                    </div>
                                </td>
                                <td class="td_normal_title" width="15%">
                                    ${lfn:message('third-ding-scenegroup:thirdDingRobot.fdSecurityKey')}
                                </td>
                                <td width="35%">
                                    <%-- 密钥--%>
                                    <div id="_xform_fdSecurityKey" _xform_type="text">
                                        <xform:text property="fdSecurityKey" showStatus="edit" style="width:95%;" />
                                    </div>
                                </td>
                            </tr>
                            <tr>
                                <td class="td_normal_title" width="15%">
                                    ${lfn:message('third-ding-scenegroup:thirdDingRobot.fdKey')}
                                </td>
                                <td width="35%">
                                    <%-- 关键字--%>
                                    <div id="_xform_fdKey" _xform_type="text">
                                        <xform:text property="fdKey" showStatus="edit" style="width:95%;" />
                                    </div>
                                </td>
                                <td colspan="2">
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