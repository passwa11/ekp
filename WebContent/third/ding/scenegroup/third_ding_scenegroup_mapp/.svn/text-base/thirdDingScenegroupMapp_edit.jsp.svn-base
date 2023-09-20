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
                Com_IncludeFile("form_option.js", "${LUI_ContextPath}/third/ding/scenegroup/third_ding_scenegroup_mapp/", 'js', true);
                Com_IncludeFile("main_edit.js", "${LUI_ContextPath}/third/ding/resource/js/", 'js', true);
                Com_IncludeFile("swf_attachment.js", "${KMSS_Parameter_ContextPath}sys/attachment/js/", "js", true);
            </script>
        </template:replace>

        <template:replace name="title">
            <c:choose>
                <c:when test="${thirdDingScenegroupMappForm.method_GET == 'add' }">
                    <c:out value="${ lfn:message('operation.create') } - ${ lfn:message('third-ding-scenegroup:table.thirdDingScenegroupMapp') }" />
                </c:when>
                <c:otherwise>
                    <c:out value="${thirdDingScenegroupMappForm.fdName} - " />
                    <c:out value="${ lfn:message('third-ding-scenegroup:table.thirdDingScenegroupMapp') }" />
                </c:otherwise>
            </c:choose>
        </template:replace>
        <template:replace name="toolbar">
            <ui:toolbar id="toolbar" layout="sys.ui.toolbar.float" count="3">
                <c:choose>
                    <c:when test="${ thirdDingScenegroupMappForm.method_GET == 'edit' }">
                        <ui:button text="${ lfn:message('button.update') }" onclick="if(validateDetail()){Com_Submit(document.thirdDingScenegroupMappForm, 'update');}" />
                    </c:when>
                    <c:when test="${ thirdDingScenegroupMappForm.method_GET == 'add' }">
                        <ui:button text="${ lfn:message('button.save') }" onclick="if(validateDetail()){Com_Submit(document.thirdDingScenegroupMappForm, 'save');}" />
                    </c:when>
                </c:choose>

                <ui:button text="${ lfn:message('button.close') }" order="5" onclick="Com_CloseWindow();" />
            </ui:toolbar>
        </template:replace>
        <template:replace name="path">
            <ui:menu layout="sys.ui.menu.nav">
                <ui:menu-item text="${ lfn:message('home.home') }" icon="lui_icon_s_home" />
                <ui:menu-item text="${ lfn:message('third-ding-scenegroup:table.thirdDingScenegroupMapp') }" />
            </ui:menu>
        </template:replace>
        <template:replace name="content">
            <html:form action="/third/ding/scenegroup/third_ding_scenegroup_mapp/thirdDingScenegroupMapp.do">

                <ui:tabpage expand="false" var-navwidth="90%">
                    <ui:content title="${ lfn:message('third-ding-scenegroup:py.JiBenXinXi') }" expand="true">
                        <div class='lui_form_title_frame'>
                            <div class='lui_form_subject'>
                                ${lfn:message('third-ding-scenegroup:table.thirdDingScenegroupMapp')}
                            </div>
                            <div class='lui_form_baseinfo'>

                            </div>
                        </div>
                        <table class="tb_normal" width="100%">
                            <tr>
                                <td class="td_normal_title" width="15%">
                                    ${lfn:message('third-ding-scenegroup:thirdDingScenegroupMapp.fdName')}
                                </td>
                                <td colspan="3" width="85.0%">
                                    <%-- 群名称--%>
                                    <div id="_xform_fdName" _xform_type="text">
                                        <xform:text property="fdName" showStatus="edit" style="width:95%;" />
                                    </div>
                                </td>
                            </tr>
                            <tr>
                                <td class="td_normal_title" width="15%">
                                    ${lfn:message('third-ding-scenegroup:thirdDingScenegroupMapp.fdModule')}
                                </td>
                                <td width="35%">
                                    <%-- 所属模板--%>
                                    <div id="_xform_fdModuleId" _xform_type="radio">
                                        <xform:select property="fdModuleId" htmlElementProperties="id='fdModuleId'" showStatus="edit" required="true">
                                            <xform:beanDataSource serviceBean="thirdDingScenegroupModuleService" selectBlock="fdId,fdName" />
                                        </xform:select>
                                    </div>
                                </td>
                                <td class="td_normal_title" width="15%">
                                    ${lfn:message('third-ding-scenegroup:thirdDingScenegroupMapp.fdStatus')}
                                </td>
                                <td width="35%">
                                    <%-- 群状态--%>
                                    <div id="_xform_fdStatus" _xform_type="select">
                                        <xform:select property="fdStatus" htmlElementProperties="id='fdStatus'" showStatus="edit">
                                            <xform:enumsDataSource enumsType="third_ding_group_status" />
                                        </xform:select>
                                    </div>
                                </td>
                            </tr>
                            <tr>
                                <td class="td_normal_title" width="15%">
                                    ${lfn:message('third-ding-scenegroup:thirdDingScenegroupMapp.fdSceneGroupId')}
                                </td>
                                <td width="35%">
                                    <%-- 群ID--%>
                                    <div id="_xform_fdSceneGroupId" _xform_type="text">
                                        <xform:text property="fdSceneGroupId" showStatus="edit" style="width:95%;" />
                                    </div>
                                </td>
                                <td class="td_normal_title" width="15%">
                                    ${lfn:message('third-ding-scenegroup:thirdDingScenegroupMapp.fdChatId')}
                                </td>
                                <td width="35%">
                                    <%-- 群会话ID--%>
                                    <div id="_xform_fdChatId" _xform_type="text">
                                        <xform:text property="fdChatId" showStatus="edit" style="width:95%;" />
                                    </div>
                                </td>
                            </tr>
                            <tr>
                                <td class="td_normal_title" width="15%">
                                    ${lfn:message('third-ding-scenegroup:thirdDingScenegroupMapp.fdModelName')}
                                </td>
                                <td width="35%">
                                    <%-- model名称--%>
                                    <div id="_xform_fdModelName" _xform_type="text">
                                        <xform:text property="fdModelName" showStatus="edit" style="width:95%;" />
                                    </div>
                                </td>
                                <td class="td_normal_title" width="15%">
                                    ${lfn:message('third-ding-scenegroup:thirdDingScenegroupMapp.fdModelId')}
                                </td>
                                <td width="35%">
                                    <%-- model id--%>
                                    <div id="_xform_fdModelId" _xform_type="text">
                                        <xform:text property="fdModelId" showStatus="edit" style="width:95%;" />
                                    </div>
                                </td>
                            </tr>
                            <tr>
                                <td class="td_normal_title" width="15%">
                                    ${lfn:message('third-ding-scenegroup:thirdDingScenegroupMapp.fdKey')}
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