<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<%@page import="com.landray.kmss.sys.unit.util.SysUnitUtil" %>

<%
    pageContext.setAttribute("currentPerson", UserUtil.getKMSSUser().getUserId());
    pageContext.setAttribute("currentPost", UserUtil.getKMSSUser().getPostIds());
    pageContext.setAttribute("currentDept", UserUtil.getKMSSUser().getDeptId());
    if (UserUtil.getUser().getFdParentOrg() != null) {
        pageContext.setAttribute("currentOrg", UserUtil.getUser().getFdParentOrg().getFdId());
    } else {
        pageContext.setAttribute("currentOrg", "");
    } %>

<template:include ref="default.view">
    <template:replace name="head">
        <style type="text/css">
            .lui_paragraph_title {
                font-size: 15px;
                color: #15a4fa;
                padding: 15px 0px 5px 0px;
            }

            .lui_paragraph_title span {
                display: inline-block;
                margin: -2px 5px 0px 0px;
            }

            .inputsgl[readonly], .tb_normal .inputsgl[readonly] {
                border: 0px;
                color: #868686
            }

            .tips {
                font-size: 9px;
                color: #666;
                margin: 10px 5px;
            }

            .data_center_tr_height {
                height: 35px;
            }
        </style>
        <script type="text/javascript">
            var formInitData = {};
            var messageInfo = {};
            Com_IncludeFile("swf_attachment.js", "${KMSS_Parameter_ContextPath}sys/attachment/js/", "js", true);
        </script>
    </template:replace>
    <template:replace name="title">
        <c:out value="${ lfn:message('sys-unit:table.sysUnitDataCenterUnit') }"/>
    </template:replace>
    <template:replace name="toolbar">
        <script>
            function deleteDoc(delUrl) {
                seajs.use(['lui/dialog'], function (dialog) {
                    dialog.confirm('${ lfn:message("page.comfirmDelete") }', function (isOk) {
                        if (isOk) {
                            Com_OpenWindow(delUrl, '_self');
                        }
                    });
                });
            }

            Com_IncludeFile("security.js");
            Com_IncludeFile("domain.js");

        </script>
        <ui:toolbar id="toolbar" layout="sys.ui.toolbar.float" count="3">
            <!--edit-->
            <kmss:auth
                    requestURL="/sys/unit/sys_unit_data_center_unit/sysUnitDataCenterUnit.do?method=edit&fdId=${param.fdId}">
                <ui:button text="${lfn:message('button.edit')}"
                           onclick="Com_OpenWindow('sysUnitDataCenterUnit.do?method=edit&fdId=${param.fdId}','_self');"
                           order="2"/>
            </kmss:auth>
            <!--delete-->
            <kmss:auth
                    requestURL="/sys/unit/sys_unit_data_center_unit/sysUnitDataCenterUnit.do?method=delete&fdId=${param.fdId}">
                <ui:button text="${lfn:message('button.delete')}"
                           onclick="deleteDoc('sysUnitDataCenterUnit.do?method=delete&fdId=${param.fdId}');" order="4"/>
            </kmss:auth>
            <ui:button text="${lfn:message('button.close')}" order="5" onclick="Com_CloseWindow();"/>
        </ui:toolbar>
    </template:replace>
    <template:replace name="path">
        <ui:menu layout="sys.ui.menu.nav">
            <ui:menu-item text="${ lfn:message('home.home') }" icon="lui_icon_s_home" href="/" target="_self"/>
            <ui:menu-item text="${ lfn:message('sys-unit:table.sysUnitDataCenterUnit') }"
                          href="/sys/unit/sys_unit_data_center_unit/" target="_self"/>
        </ui:menu>
    </template:replace>
    <template:replace name="content">

        <div class='lui_form_title_frame'>
            <div class='lui_form_subject'>
                    ${lfn:message('sys-unit:table.sysUnitDataCenterUnit')}
            </div>
            <div class='lui_form_baseinfo'>
            </div>
        </div>
        <table class="tb_normal" width="100%">
            <tr class="data_center_tr_height">
                <td class="td_normal_title" width="15%">
                        ${lfn:message('sys-unit:sysUnitDataCenterUnit.fdUnitCode')}
                </td>
                <td colspan="3" width="85.0%">
                    <div id="_xform_fdName" _xform_type="text">
                        <xform:text property="fdUnitCode" showStatus="view" style="width:95%;"/>
                    </div>
                </td>
            </tr>
            <tr class="data_center_tr_height">
                <td class="td_normal_title" width="15%">
                        ${lfn:message('sys-unit:sysUnitDataCenterUnit.fdSecretary')}
                </td>
                <td colspan="3" width="85.0%">
                    <div>
                        <c:out value="${sysUnitDataCenterUnitForm.fdSecretaryNames}"/>
                    </div>
                </td>
            </tr>
            <%--<tr class="data_center_tr_height">
                <td class="td_normal_title" width="15%">
                        ${lfn:message('sys-unit:sysUnitDataCenterUnit.fdSupervisePerson')}
                </td>
                <td colspan="3" width="85.0%">
                    <div>
                        <c:out value="${sysUnitDataCenterUnitForm.fdSupervisePersonNames}"/>
                    </div>
                </td>
            </tr>--%>
            <tr class="data_center_tr_height">
                <td class="td_normal_title" width="15%">
                        ${lfn:message('sys-unit:sysUnitDataCenterUnit.fdCenters')}
                </td>
                <td colspan="3" width="85.0%">
                    <div>
                        <c:out value="${sysUnitDataCenterUnitForm.fdCenterNames}"/>
                    </div>
                </td>
            </tr>
            <tr class="data_center_tr_height">
                <td class="td_normal_title" width="15%">
                        ${lfn:message('sys-unit:sysUnitDataCenterUnit.fdIsAvailable')}
                </td>
                <td colspan="3" width="85.0%">
                        <%-- 是否有效--%>
                    <div id="_xform_fdIsAvailable" _xform_type="radio">
                        <xform:radio property="fdIsAvailable" htmlElementProperties="id='fdIsAvailable'"
                                     showStatus="view">
                            <xform:enumsDataSource enumsType="common_yesno"/>
                        </xform:radio>
                    </div>
                </td>
            </tr>
            <tr class="data_center_tr_height">
                <td class="td_normal_title" width="15%">
                        ${lfn:message('sys-unit:sysUnitDataCenterUnit.docCreator')}
                </td>
                <td colspan="3" width="85.0%">
                    <div>
                        <c:out value="${sysUnitDataCenterUnitForm.docCreatorName}"/>
                    </div>
                </td>
            </tr>
            <tr class="data_center_tr_height">
                <td class="td_normal_title" width="15%">
                        ${lfn:message('sys-unit:sysUnitDataCenterUnit.docCreateTime')}
                </td>
                <td colspan="3" width="85.0%">
                    <div id="_xform_docCreator" _xform_type="radio">
                        <c:out value="${sysUnitDataCenterUnitForm.docCreateTime}"/>
                    </div>
                </td>
            </tr>

        </table>
    </template:replace>

</template:include>