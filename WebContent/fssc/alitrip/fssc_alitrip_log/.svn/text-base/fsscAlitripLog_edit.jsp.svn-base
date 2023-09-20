<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<%@page import="com.landray.kmss.fssc.alitrip.util.FsscAlitripUtil" %>
    
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
                Com_IncludeFile("form_option.js", "${LUI_ContextPath}/fssc/alitrip/fssc_alitrip_log/", 'js', true);
                Com_IncludeFile("main_edit.js", "${LUI_ContextPath}/fssc/alitrip/resource/js/", 'js', true);
                Com_IncludeFile("swf_attachment.js", "${KMSS_Parameter_ContextPath}sys/attachment/js/", "js", true);
            </script>
        </template:replace>

        <template:replace name="title">
            <c:choose>
                <c:when test="${fsscAlitripLogForm.method_GET == 'add' }">
                    <c:out value="${ lfn:message('operation.create') } - ${ lfn:message('fssc-alitrip:table.fsscAlitripLog') }" />
                </c:when>
                <c:otherwise>
                    <c:out value="${fsscAlitripLogForm.fdDesc} - " />
                    <c:out value="${ lfn:message('fssc-alitrip:table.fsscAlitripLog') }" />
                </c:otherwise>
            </c:choose>
        </template:replace>
        <template:replace name="toolbar">
            <ui:toolbar id="toolbar" layout="sys.ui.toolbar.float" count="3">
                <c:choose>
                    <c:when test="${ fsscAlitripLogForm.method_GET == 'edit' }">
                        <ui:button text="${ lfn:message('button.update') }" onclick="if(validateDetail()){Com_Submit(document.fsscAlitripLogForm, 'update');}" />
                    </c:when>
                    <c:when test="${ fsscAlitripLogForm.method_GET == 'add' }">
                        <ui:button text="${ lfn:message('button.save') }" onclick="if(validateDetail()){Com_Submit(document.fsscAlitripLogForm, 'save');}" />
                    </c:when>
                </c:choose>

                <ui:button text="${ lfn:message('button.close') }" order="5" onclick="Com_CloseWindow();" />
            </ui:toolbar>
        </template:replace>
        <template:replace name="path">
            <ui:menu layout="sys.ui.menu.nav">
                <ui:menu-item text="${ lfn:message('home.home') }" icon="lui_icon_s_home" />
                <ui:menu-item text="${ lfn:message('fssc-alitrip:table.fsscAlitripLog') }" />
            </ui:menu>
        </template:replace>
        <template:replace name="content">
            <html:form action="/fssc/alitrip/fssc_alitrip_log/fsscAlitripLog.do">

                <ui:tabpage expand="false" var-navwidth="90%">
                    <ui:content title="${ lfn:message('fssc-alitrip:py.JiBenXinXi') }" expand="true">
                        <table class="tb_normal" width="100%">
                            <tr>
                                <td class="td_normal_title" width="15%">
                                    ${lfn:message('fssc-alitrip:fsscAlitripLog.fdOrder')}
                                </td>
                                <td width="35%">
                                    <%-- 排序号--%>
                                    <div id="_xform_fdOrder" _xform_type="text">
                                        <xform:text property="fdOrder" showStatus="edit" style="width:95%;" />
                                    </div>
                                </td>
                                <td class="td_normal_title" width="15%">
                                    ${lfn:message('fssc-alitrip:fsscAlitripLog.fdInterType')}
                                </td>
                                <td width="35%">
                                    <%-- 接口标识--%>
                                    <div id="_xform_fdInterType" _xform_type="text">
                                        <xform:text property="fdInterType" showStatus="edit" style="width:95%;" />
                                    </div>
                                </td>
                            </tr>
                            <tr>
                                <td class="td_normal_title" width="15%">
                                    ${lfn:message('fssc-alitrip:fsscAlitripLog.fdType')}
                                </td>
                                <td width="35%">
                                    <%-- 成功或者失败--%>
                                    <div id="_xform_fdType" _xform_type="text">
                                        <xform:text property="fdType" showStatus="edit" style="width:95%;" />
                                    </div>
                                </td>
                                <td class="td_normal_title" width="15%">
                                    ${lfn:message('fssc-alitrip:fsscAlitripLog.fdErrCode')}
                                </td>
                                <td width="35%">
                                    <%-- 错误代码--%>
                                    <div id="_xform_fdErrCode" _xform_type="text">
                                        <xform:text property="fdErrCode" showStatus="edit" style="width:95%;" />
                                    </div>
                                </td>
                            </tr>
                            <tr>
                                <td class="td_normal_title" width="15%">
                                    ${lfn:message('fssc-alitrip:fsscAlitripLog.fdDesc')}
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
                                    ${lfn:message('fssc-alitrip:fsscAlitripLog.fdBackMsg')}
                                </td>
                                <td colspan="3" width="85.0%">
                                    <%-- 返回值--%>
                                    <div id="_xform_fdBackMsg" _xform_type="rtf">
                                        <xform:rtf property="fdBackMsg" showStatus="edit" width="95%" />
                                    </div>
                                </td>
                            </tr>
                            <tr>
                                <td class="td_normal_title" width="15%">
                                    ${lfn:message('fssc-alitrip:fsscAlitripLog.docCreateTime')}
                                </td>
                                <td width="35%">
                                    <%-- 同步时间--%>
                                    <div id="_xform_docCreateTime" _xform_type="datetime">
                                        <xform:datetime property="docCreateTime" showStatus="view" dateTimeType="datetime" style="width:95%;" />
                                    </div>
                                </td>
                                <td class="td_normal_title" width="15%">
                                    ${lfn:message('fssc-alitrip:fsscAlitripLog.docAlterTime')}
                                </td>
                                <td width="35%">
                                    <%-- 更新时间--%>
                                    <div id="_xform_docAlterTime" _xform_type="datetime">
                                        <xform:datetime property="docAlterTime" showStatus="view" dateTimeType="datetime" style="width:95%;" />
                                    </div>
                                </td>
                            </tr>
                            <tr>
                                <td class="td_normal_title" width="15%">
                                    ${lfn:message('fssc-alitrip:fsscAlitripLog.fdErrMessage')}
                                </td>
                                <td colspan="3" width="85.0%">
                                    <%-- 错误信息--%>
                                    <div id="_xform_fdErrMessage" _xform_type="rtf">
                                        <xform:rtf property="fdErrMessage" showStatus="edit" width="95%" />
                                    </div>
                                </td>
                            </tr>
                            <tr>
                                <td class="td_normal_title" width="15%">
                                    ${lfn:message('fssc-alitrip:fsscAlitripLog.fdModelId')}
                                </td>
                                <td width="35%">
                                    <%-- 文档id--%>
                                    <div id="_xform_fdModelId" _xform_type="text">
                                        <xform:text property="fdModelId" showStatus="edit" style="width:95%;" />
                                    </div>
                                </td>
                                <td class="td_normal_title" width="15%">
                                    ${lfn:message('fssc-alitrip:fsscAlitripLog.fdModelName')}
                                </td>
                                <td width="35%">
                                    <%-- 模块model--%>
                                    <div id="_xform_fdModelName" _xform_type="text">
                                        <xform:text property="fdModelName" showStatus="edit" style="width:95%;" />
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
