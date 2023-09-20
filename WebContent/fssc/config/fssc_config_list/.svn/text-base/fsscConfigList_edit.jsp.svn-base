<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<%@page import="com.landray.kmss.fssc.config.util.FsscConfigUtil" %>
    
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
                Com_IncludeFile("form_option.js", "${LUI_ContextPath}/fssc/config/fssc_config_list/", 'js', true);
                Com_IncludeFile("main_edit.js", "${LUI_ContextPath}/fssc/config/resource/js/", 'js', true);
                Com_IncludeFile("swf_attachment.js", "${KMSS_Parameter_ContextPath}sys/attachment/js/", "js", true);
            </script>
        </template:replace>

        <template:replace name="title">
            <c:choose>
                <c:when test="${fsscConfigListForm.method_GET == 'add' }">
                    <c:out value="${ lfn:message('operation.create') } - ${ lfn:message('fssc-config:table.fsscConfigList') }" />
                </c:when>
                <c:otherwise>
                    <c:out value="${fsscConfigListForm.fdName} - " />
                    <c:out value="${ lfn:message('fssc-config:table.fsscConfigList') }" />
                </c:otherwise>
            </c:choose>
        </template:replace>
        <template:replace name="toolbar">
            <ui:toolbar id="toolbar" layout="sys.ui.toolbar.float" count="3">
                <c:choose>
                    <c:when test="${ fsscConfigListForm.method_GET == 'edit' }">
                        <ui:button text="${ lfn:message('button.update') }" onclick="if(validateDetail()){Com_Submit(document.fsscConfigListForm, 'update');}" />
                    </c:when>
                    <c:when test="${ fsscConfigListForm.method_GET == 'add' }">
                        <ui:button text="${ lfn:message('button.save') }" onclick="if(validateDetail()){Com_Submit(document.fsscConfigListForm, 'save');}" />
                    </c:when>
                </c:choose>

                <ui:button text="${ lfn:message('button.close') }" order="5" onclick="Com_CloseWindow();" />
            </ui:toolbar>
        </template:replace>
        <template:replace name="path">
            <ui:menu layout="sys.ui.menu.nav">
                <ui:menu-item text="${ lfn:message('home.home') }" icon="lui_icon_s_home" />
                <ui:menu-item text="${ lfn:message('fssc-config:table.fsscConfigList') }" />
            </ui:menu>
        </template:replace>
        <template:replace name="content">
            <html:form action="/fssc/config/fssc_config_list/fsscConfigList.do">

                <ui:tabpage expand="false" var-navwidth="90%">
                    <ui:content title="${ lfn:message('fssc-config:py.JiBenXinXi') }" expand="true">
                        <table class="tb_normal" width="100%">
                            <tr>
                                <td class="td_normal_title" width="15%">
                                    ${lfn:message('fssc-config:fsscConfigList.fdName')}
                                </td>
                                <td width="35%">
                                    <%-- 物资名称--%>
                                    <div id="_xform_fdName" _xform_type="text">
                                        <xform:text property="fdName" showStatus="edit" style="width:95%;" />
                                    </div>
                                </td>
                                <td class="td_normal_title" width="15%">
                                    ${lfn:message('fssc-config:fsscConfigList.fdCode')}
                                </td>
                                <td width="35%">
                                    <%-- 物资编码--%>
                                    <div id="_xform_fdCode" _xform_type="text">
                                        <xform:text property="fdCode" showStatus="edit" style="width:95%;" />
                                    </div>
                                </td>
                            </tr>
                            <tr>
                                <td class="td_normal_title" width="15%">
                                    ${lfn:message('fssc-config:fsscConfigList.fdGoodsType')}
                                </td>
                                <td width="35%">
                                    <%-- 物资类别--%>
                                    <div id="_xform_fdGoodsType" _xform_type="text">
                                        <xform:text property="fdGoodsType" showStatus="edit" style="width:95%;" />
                                    </div>
                                </td>
                                <td class="td_normal_title" width="15%">
                                    ${lfn:message('fssc-config:fsscConfigList.fdGoodsProperty')}
                                </td>
                                <td width="35%">
                                    <%-- 物资属性--%>
                                    <div id="_xform_fdGoodsProperty" _xform_type="text">
                                        <xform:text property="fdGoodsProperty" showStatus="edit" style="width:95%;" />
                                    </div>
                                </td>
                            </tr>
                            <tr>
                                <td class="td_normal_title" width="15%">
                                    ${lfn:message('fssc-config:fsscConfigList.fdMinNum')}
                                </td>
                                <td width="35%">
                                    <%-- 最小起订量--%>
                                    <div id="_xform_fdMinNum" _xform_type="text">
                                        <xform:text property="fdMinNum" showStatus="edit" validators=" digits" style="width:95%;" />
                                    </div>
                                </td>
                                <td class="td_normal_title" width="15%">
                                    ${lfn:message('fssc-config:fsscConfigList.fdSpec')}
                                </td>
                                <td width="35%">
                                    <%-- 规格--%>
                                    <div id="_xform_fdSpec" _xform_type="text">
                                        <xform:text property="fdSpec" showStatus="edit" style="width:95%;" />
                                    </div>
                                </td>
                            </tr>
                            <tr>
                                <td class="td_normal_title" width="15%">
                                    ${lfn:message('fssc-config:fsscConfigList.fdUnit')}
                                </td>
                                <td width="35%">
                                    <%-- 单位--%>
                                    <div id="_xform_fdUnit" _xform_type="text">
                                        <xform:text property="fdUnit" showStatus="edit" style="width:95%;" />
                                    </div>
                                </td>
                                <td class="td_normal_title" width="15%">
                                    ${lfn:message('fssc-config:fsscConfigList.fdPrice')}
                                </td>
                                <td width="35%">
                                    <%-- 单价--%>
                                    <div id="_xform_fdPrice" _xform_type="text">
                                        <xform:text property="fdPrice" showStatus="edit" validators=" number min(0)" style="width:95%;" />
                                    </div>
                                </td>
                            </tr>
                            <tr>
                                <td class="td_normal_title" width="15%">
                                    ${lfn:message('fssc-config:fsscConfigList.docCreator')}
                                </td>
                                <td width="35%">
                                    <%-- 创建人--%>
                                    <div id="_xform_docCreatorId" _xform_type="address">
                                        <ui:person personId="${fsscConfigListForm.docCreatorId}" personName="${fsscConfigListForm.docCreatorName}" />
                                    </div>
                                </td>
                                <td class="td_normal_title" width="15%">
                                    ${lfn:message('fssc-config:fsscConfigList.docCreateTime')}
                                </td>
                                <td width="35%">
                                    <%-- 创建时间--%>
                                    <div id="_xform_docCreateTime" _xform_type="datetime">
                                        <xform:datetime property="docCreateTime" showStatus="view" dateTimeType="datetime" style="width:95%;" />
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