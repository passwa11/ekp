<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<%@page import="com.landray.kmss.sys.oms.util.SysOmsUtil" %>
    
        <%
            pageContext.setAttribute("currentPerson", UserUtil.getKMSSUser().getUserId());
        pageContext.setAttribute("currentPost", UserUtil.getKMSSUser().getPostIds());
        pageContext.setAttribute("currentDept", UserUtil.getKMSSUser().getDeptId());
        if(UserUtil.getUser().getFdParentOrg() != null) {
            pageContext.setAttribute("currentOrg", UserUtil.getUser().getFdParentOrg().getFdId());
        } else {
            pageContext.setAttribute("currentOrg", "");
        } %>
    
    <template:include ref="default.view">
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
                Com_IncludeFile("swf_attachment.js", "${KMSS_Parameter_ContextPath}sys/attachment/js/", "js", true);
            </script>
        </template:replace>
        <template:replace name="title">
            <c:out value="${sysOmsGroupForm.fdName} - " />
            <c:out value="${ lfn:message('sys-oms:table.sysOmsGroup') }" />
        </template:replace>
        <template:replace name="toolbar">
            <script>
                function deleteDoc(delUrl) {
                    seajs.use(['lui/dialog'], function(dialog) {
                        dialog.confirm('${ lfn:message("page.comfirmDelete") }', function(isOk) {
                            if(isOk) {
                                Com_OpenWindow(delUrl, '_self');
                            }
                        });
                    });
                }

                function openWindowViaDynamicForm(popurl, params, target) {
                    var form = document.createElement('form');
                    if(form) {
                        try {
                            target = !target ? '_blank' : target;
                            form.style = "display:none;";
                            form.method = 'post';
                            form.action = popurl;
                            form.target = target;
                            if(params) {
                                for(var key in params) {
                                    var
                                    v = params[key];
                                    var vt = typeof
                                    v;
                                    var hdn = document.createElement('input');
                                    hdn.type = 'hidden';
                                    hdn.name = key;
                                    if(vt == 'string' || vt == 'boolean' || vt == 'number') {
                                        hdn.value =
                                        v +'';
                                    } else {
                                        if($.isArray(
                                            v)) {
                                            hdn.value =
                                            v.join(';');
                                        } else {
                                            hdn.value = toString(
                                                v);
                                        }
                                    }
                                    form.appendChild(hdn);
                                }
                            }
                            document.body.appendChild(form);
                            form.submit();
                        } finally {
                            document.body.removeChild(form);
                        }
                    }
                }

                function doCustomOpt(fdId, optCode) {
                    if(!fdId || !optCode) {
                        return;
                    }

                    if(viewOption.customOpts && viewOption.customOpts[optCode]) {
                        var param = {
                            "List_Selected_Count": 1
                        };
                        var argsObject = viewOption.customOpts[optCode];
                        if(argsObject.popup == 'true') {
                            var popurl = viewOption.contextPath + argsObject.popupUrl + '&fdId=' + fdId;
                            for(var arg in argsObject) {
                                param[arg] = argsObject[arg];
                            }
                            openWindowViaDynamicForm(popurl, param, '_self');
                            return;
                        }
                        var optAction = viewOption.contextPath + viewOption.basePath + '?method=' + optCode + '&fdId=' + fdId;
                        Com_OpenWindow(optAction, '_self');
                    }
                }
                window.doCustomOpt = doCustomOpt;
                var viewOption = {
                    contextPath: '${LUI_ContextPath}',
                    basePath: '/sys/oms/sys_oms_group/sysOmsGroup.do',
                    customOpts: {

                        ____fork__: 0
                    }
                };

                Com_IncludeFile("security.js");
                Com_IncludeFile("domain.js");
            </script>
            <ui:toolbar id="toolbar" layout="sys.ui.toolbar.float" count="3">

                <!--edit-->
                <kmss:auth requestURL="/sys/oms/sys_oms_group/sysOmsGroup.do?method=edit&fdId=${param.fdId}">
                    <ui:button text="${lfn:message('button.edit')}" onclick="Com_OpenWindow('sysOmsGroup.do?method=edit&fdId=${param.fdId}','_self');" order="2" />
                </kmss:auth>
                <!--delete-->
                <kmss:auth requestURL="/sys/oms/sys_oms_group/sysOmsGroup.do?method=delete&fdId=${param.fdId}">
                    <ui:button text="${lfn:message('button.delete')}" onclick="deleteDoc('sysOmsGroup.do?method=delete&fdId=${param.fdId}');" order="4" />
                </kmss:auth>
                <ui:button text="${lfn:message('button.close')}" order="5" onclick="Com_CloseWindow();" />


            </ui:toolbar>
        </template:replace>
        <template:replace name="path">
            <ui:menu layout="sys.ui.menu.nav">
                <ui:menu-item text="${ lfn:message('home.home') }" icon="lui_icon_s_home" href="/" target="_self" />
                <ui:menu-item text="${ lfn:message('sys-oms:table.sysOmsGroup') }" href="/sys/oms/sys_oms_group/" target="_self" />
            </ui:menu>
        </template:replace>
        <template:replace name="content">

            <ui:tabpage expand="false" var-navwidth="90%">
                <ui:content title="${ lfn:message('sys-oms:py.JiBenXinXi') }" expand="true">
                    <div class='lui_form_title_frame'>
                        <div class='lui_form_subject'>
                            ${lfn:message('sys-oms:table.sysOmsGroup')}
                        </div>
                        <div class='lui_form_baseinfo'>

                        </div>
                    </div>
                    <table class="tb_normal" width="100%">
                        <tr>
                            <td class="td_normal_title" width="15%">
                                ${lfn:message('sys-oms:sysOmsGroup.fdName')}
                            </td>
                            <td width="35%">
                                <%-- 名称--%>
                                <div id="_xform_fdName" _xform_type="text">
                                    <xform:text property="fdName" showStatus="view" style="width:95%;" />
                                </div>
                            </td>
                            <td class="td_normal_title" width="15%">
                                ${lfn:message('sys-oms:sysOmsGroup.fdIsAvailable')}
                            </td>
                            <td width="35%">
                                <%-- 是否有效--%>
                                <div id="_xform_fdIsAvailable" _xform_type="radio">
                                    <xform:radio property="fdIsAvailable" htmlElementProperties="id='fdIsAvailable'" showStatus="view">
                                        <xform:enumsDataSource enumsType="common_yesno" />
                                    </xform:radio>
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td class="td_normal_title" width="15%">
                                ${lfn:message('sys-oms:sysOmsGroup.fdShortName')}
                            </td>
                            <td width="35%">
                                <%-- 简称--%>
                                <div id="_xform_fdShortName" _xform_type="text">
                                    <xform:text property="fdShortName" showStatus="view" style="width:95%;" />
                                </div>
                            </td>
                            <td class="td_normal_title" width="15%">
                                ${lfn:message('sys-oms:sysOmsGroup.fdNo')}
                            </td>
                            <td width="35%">
                                <%-- 编号--%>
                                <div id="_xform_fdNo" _xform_type="text">
                                    <xform:text property="fdNo" showStatus="view" style="width:95%;" />
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td class="td_normal_title" width="15%">
                                ${lfn:message('sys-oms:sysOmsGroup.fdKeyword')}
                            </td>
                            <td width="35%">
                                <%-- 关键字--%>
                                <div id="_xform_fdKeyword" _xform_type="text">
                                    <xform:text property="fdKeyword" showStatus="view" style="width:95%;" />
                                </div>
                            </td>
                            <td class="td_normal_title" width="15%">
                                ${lfn:message('sys-oms:sysOmsGroup.fdImportinfo')}
                            </td>
                            <td width="35%">
                                <%-- 映射关系--%>
                                <div id="_xform_fdImportinfo" _xform_type="text">
                                    <xform:text property="fdImportinfo" showStatus="view" style="width:95%;" />
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td class="td_normal_title" width="15%">
                                ${lfn:message('sys-oms:sysOmsGroup.fdIsBusiness')}
                            </td>
                            <td width="35%">
                                <%-- 是否业务相关--%>
                                <div id="_xform_fdIsBusiness" _xform_type="radio">
                                    <xform:radio property="fdIsBusiness" htmlElementProperties="id='fdIsBusiness'" showStatus="view">
                                        <xform:enumsDataSource enumsType="common_yesno" />
                                    </xform:radio>
                                </div>
                            </td>
                            <td class="td_normal_title" width="15%">
                                ${lfn:message('sys-oms:sysOmsGroup.fdMemo')}
                            </td>
                            <td width="35%">
                                <%-- 备注--%>
                                <div id="_xform_fdMemo" _xform_type="text">
                                    <xform:text property="fdMemo" showStatus="view" style="width:95%;" />
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td class="td_normal_title" width="15%">
                                ${lfn:message('sys-oms:sysOmsGroup.fdRecordStatus')}
                            </td>
                            <td width="35%">
                                <%-- 更新状态--%>
                                <div id="_xform_fdRecordStatus" _xform_type="text">
                                    <xform:text property="fdRecordStatus" showStatus="view" style="width:95%;" />
                                </div>
                            </td>
                            <td class="td_normal_title" width="15%">
                                ${lfn:message('sys-oms:sysOmsGroup.fdCreateTime')}
                            </td>
                            <td width="35%">
                                <%-- 创建时间--%>
                                <div id="_xform_fdCreateTime" _xform_type="datetime">
                                    <xform:datetime property="fdCreateTime" showStatus="view" dateTimeType="datetime" style="width:95%;" />
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td class="td_normal_title" width="15%">
                                ${lfn:message('sys-oms:sysOmsGroup.fdAlterTime')}
                            </td>
                            <td width="35%">
                                <%-- 更新时间--%>
                                <div id="_xform_fdAlterTime" _xform_type="datetime">
                                    <xform:datetime property="fdAlterTime" showStatus="view" dateTimeType="datetime" style="width:95%;" />
                                </div>
                            </td>
                            <td class="td_normal_title" width="15%">
                                ${lfn:message('sys-oms:sysOmsGroup.fdLdapDn')}
                            </td>
                            <td width="35%">
                                <%-- LDAP DN--%>
                                <div id="_xform_fdLdapDn" _xform_type="text">
                                    <xform:text property="fdLdapDn" showStatus="view" style="width:95%;" />
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td class="td_normal_title" width="15%">
                                ${lfn:message('sys-oms:sysOmsGroup.fdCreator')}
                            </td>
                            <td width="35%">
                                <%-- 创建者--%>
                                <div id="_xform_fdCreator" _xform_type="text">
                                    <xform:text property="fdCreator" showStatus="view" style="width:95%;" />
                                </div>
                            </td>
                            <td class="td_normal_title" width="15%">
                                ${lfn:message('sys-oms:sysOmsGroup.fdOrgEmail')}
                            </td>
                            <td width="35%">
                                <%-- 组织邮箱--%>
                                <div id="_xform_fdOrgEmail" _xform_type="text">
                                    <xform:text property="fdOrgEmail" showStatus="view" style="width:95%;" />
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td class="td_normal_title" width="15%">
                                ${lfn:message('sys-oms:sysOmsGroup.fdOrder')}
                            </td>
                            <td width="35%">
                                <%-- 排序号--%>
                                <div id="_xform_fdOrder" _xform_type="text">
                                    <xform:text property="fdOrder" showStatus="view" style="width:95%;" />
                                </div>
                            </td>
                            <td class="td_normal_title" width="15%">
                                ${lfn:message('sys-oms:sysOmsGroup.fdHandleStatus')}
                            </td>
                            <td width="35%">
                                <%-- 处理状态--%>
                                <div id="_xform_fdHandleStatus" _xform_type="text">
                                    <xform:text property="fdHandleStatus" showStatus="view" style="width:95%;" />
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td class="td_normal_title" width="15%">
                                ${lfn:message('sys-oms:sysOmsGroup.fdDynamicMap')}
                            </td>
                            <td colspan="3" width="85.0%">
                                <%-- 动态参数--%>
                                <div id="_xform_fdDynamicMap" _xform_type="rtf">
                                    <xform:rtf property="fdDynamicMap" showStatus="view" width="95%" />
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td class="td_normal_title" width="15%">
                                ${lfn:message('sys-oms:sysOmsGroup.fdCustomMap')}
                            </td>
                            <td colspan="3" width="85.0%">
                                <%-- 自定义参数--%>
                                <div id="_xform_fdCustomMap" _xform_type="rtf">
                                    <xform:rtf property="fdCustomMap" showStatus="view" width="95%" />
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td class="td_normal_title" width="15%">
                                ${lfn:message('sys-oms:sysOmsGroup.fdMembers')}
                            </td>
                            <td colspan="3" width="85.0%">
                                <%-- 群组成员--%>
                                <div id="_xform_fdMembers" _xform_type="rtf">
                                    <xform:rtf property="fdMembers" showStatus="view" width="95%" />
                                </div>
                            </td>
                        </tr>
                    </table>
                </ui:content>
            </ui:tabpage>
        </template:replace>

    </template:include>