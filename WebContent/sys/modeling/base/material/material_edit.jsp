<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<%@page import="com.landray.kmss.sys.modeling.base.material.util.ModelingMaterialUtil" %>
    
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
                Com_IncludeFile("form_option.js", "${LUI_ContextPath}/sys/modeling/base/material/", 'js', true);
                Com_IncludeFile("main_edit.js", "${LUI_ContextPath}/sys/portal/resource/js/", 'js', true);
                Com_IncludeFile("swf_attachment.js", "${KMSS_Parameter_ContextPath}sys/attachment/js/", "js", true);
            </script>
        </template:replace>

        <template:replace name="title">
            <c:choose>
                <c:when test="${modelingMaterialMainForm.method_GET == 'add' }">
                    <c:out value="${ lfn:message('operation.create') } - ${ lfn:message('sys-modeling-base:table.modelingMaterialMain') }" />
                </c:when>
                <c:otherwise>
                    <c:out value="${modelingMaterialMainForm.fdName} - " />
                    <c:out value="${ lfn:message('sys-modeling-base:table.modelingMaterialMain') }" />
                </c:otherwise>
            </c:choose>
        </template:replace>
        <template:replace name="toolbar">
            <ui:toolbar id="toolbar" layout="sys.ui.toolbar.float" count="3">
                <c:choose>
                    <c:when test="${ modelingMaterialMainForm.method_GET == 'edit' }">
                        <ui:button text="${ lfn:message('button.update') }" onclick="if(validateDetail()){Com_Submit(document.modelingMaterialMainForm, 'update');}" />
                    </c:when>
                    <c:when test="${ modelingMaterialMainForm.method_GET == 'add' }">
                        <ui:button text="${ lfn:message('button.save') }" onclick="if(validateDetail()){Com_Submit(document.modelingMaterialMainForm, 'save');}" />
                    </c:when>
                </c:choose>

                <ui:button text="${ lfn:message('button.close') }" order="5" onclick="Com_CloseWindow();" />
            </ui:toolbar>
        </template:replace>
        <template:replace name="path">
            <ui:menu layout="sys.ui.menu.nav">
                <ui:menu-item text="${ lfn:message('home.home') }" icon="lui_icon_s_home" />
                <ui:menu-item text="${ lfn:message('sys-modeling-base:table.modelingMaterialMain') }" />
            </ui:menu>
        </template:replace>
        <template:replace name="content">
            <html:form action="/sys/modeling/base/modelingMaterialMain.do">

                <ui:tabpage expand="false" var-navwidth="90%">
                    <ui:content title="${ lfn:message('sys-modeling-base:behavior.baseinfo') }" expand="true">
                        <div class='lui_form_title_frame'>
                            <div class='lui_form_subject'>
                                ${lfn:message('sys-modeling-base:table.modelingMaterialMain')}
                            </div>
                            <div class='lui_form_baseinfo'>

                            </div>
                        </div>
                        <table class="tb_normal" width="100%">
                            <tr>
                                <td class="td_normal_title" width="15%">
                                    ${lfn:message('sys-modeling-base:modelingMaterialMain.docCreator')}
                                </td>
                                <td width="35%">
                                    <%-- 创建人--%>
                                    <div id="_xform_docCreatorId" _xform_type="address">
                                        <ui:person personId="${modelingMaterialMainForm.docCreatorId}" personName="${modelingMaterialMainForm.docCreatorName}" />
                                    </div>
                                </td>
                                <td class="td_normal_title" width="15%">
                                    ${lfn:message('sys-modeling-base:modelingMaterialMain.fdName')}
                                </td>
                                <td width="35%">
                                    <%-- 名称--%>
                                    <div id="_xform_fdName" _xform_type="text">
                                        <xform:text property="fdName" showStatus="edit" style="width:95%;" />
                                    </div>
                                </td>
                            </tr>
                            <tr>
                                <td class="td_normal_title" width="15%">
                                    ${lfn:message('sys-modeling-base:modelingMaterialMain.docAlteror')}
                                </td>
                                <td width="35%">
                                    <%-- 修改人--%>
                                    <div id="_xform_docAlterorId" _xform_type="address">
                                        <ui:person personId="${modelingMaterialMainForm.docAlterorId}" personName="${modelingMaterialMainForm.docAlterorName}" />
                                    </div>
                                </td>
                                <td class="td_normal_title" width="15%">
                                    ${lfn:message('sys-modeling-base:modelingMaterialMain.docCreateTime')}
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
                                    ${lfn:message('sys-modeling-base:modelingMaterialMain.docAlterTime')}
                                </td>
                                <td width="35%">
                                    <%-- 更新时间--%>
                                    <div id="_xform_docAlterTime" _xform_type="datetime">
                                        <xform:datetime property="docAlterTime" showStatus="view" dateTimeType="datetime" style="width:95%;" />
                                    </div>
                                </td>
                                <td class="td_normal_title" width="15%">
                                    ${lfn:message('sys-modeling-base:modelingMaterialMain.fdSize')}
                                </td>
                                <td width="35%">
                                    <%-- 大小--%>
                                    <div id="_xform_fdSize" _xform_type="text">
                                        <xform:text property="fdSize" showStatus="edit" validators=" digits" style="width:95%;" />
                                        <br><span class="com_help">${lfn:message('sys-modeling-base:modelingMaterialMain.fdSize.tips')}</span>
                                    </div>
                                </td>
                            </tr>
                            <tr>
                                <td class="td_normal_title" width="15%">
                                    ${lfn:message('sys-modeling-base:modelingMaterialMain.fdWidth')}
                                </td>
                                <td width="35%">
                                    <%-- 宽--%>
                                    <div id="_xform_fdWidth" _xform_type="text">
                                        <xform:text property="fdWidth" showStatus="edit" validators=" number" style="width:95%;" />
                                    </div>
                                </td>
                                <td class="td_normal_title" width="15%">
                                        ${lfn:message('sys-modeling-base:modelingMaterialMain.fdLength')}
                                </td>
                                <td width="35%">
                                        <%-- 长--%>
                                    <div id="_xform_fdLength" _xform_type="text">
                                        <xform:text property="fdLength" showStatus="edit" validators=" number" style="width:95%;" />
                                    </div>
                                </td>
                            </tr>
                            <tr>
                                <td class="td_normal_title" width="15%">
                                    ${lfn:message('sys-modeling-base:modelingMaterialMain.attMaterial')}
                                </td>
                                <td width="85%" colspan="3">
                                    <%-- 附件--%>
                                    <c:import url="/sys/attachment/sys_att_main/sysAttMain_edit.jsp" charEncoding="UTF-8">
                                        <c:param name="fdKey" value="attModelingMaterial" />
                                        <c:param name="formBeanName" value="modelingMaterialMainForm" />
                                        <c:param name="fdRequired" value="true" />
                                        <c:param name="fdMulti" value="true" />
                                        <c:param name="enabledFileType" value="*.gif;*.jpg;*.jpeg;*.png" />
                                        <c:param name="fdAttType" value="pic" />
                                    </c:import>
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