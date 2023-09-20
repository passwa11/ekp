<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<%@page import="com.landray.kmss.sys.unit.util.SysUnitUtil" %>

<% pageContext.setAttribute("currentUser", UserUtil.getKMSSUser());
    pageContext.setAttribute("currentPerson", UserUtil.getKMSSUser().getUserId());
    pageContext.setAttribute("currentPost", UserUtil.getKMSSUser().getPostIds());
    pageContext.setAttribute("currentDept", UserUtil.getKMSSUser().getDeptId());
    if (UserUtil.getUser().getFdParentOrg() != null) {
        pageContext.setAttribute("currentOrg", UserUtil.getUser().getFdParentOrg().getFdId());
    } else {
        pageContext.setAttribute("currentOrg", "");
    } %>

<template:include ref="default.edit">
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
    </template:replace>

    <template:replace name="title">
        <c:choose>
            <c:when test="${sysUnitDataCenterForm.method_GET == 'add' }">
                <c:out value="${ lfn:message('operation.create') } - ${ lfn:message('sys-unit:table.sysUnitDataCenter') }"/>
            </c:when>
            <c:otherwise>
                <c:out value="${sysUnitDataCenterForm.fdName} - "/>
                <c:out value="${ lfn:message('sys-unit:table.sysUnitDataCenter') }"/>
            </c:otherwise>
        </c:choose>
    </template:replace>
    <template:replace name="toolbar">
        <ui:toolbar id="toolbar" layout="sys.ui.toolbar.float" count="3">
            <c:choose>
                <c:when test="${ sysUnitDataCenterForm.method_GET == 'edit' }">
                    <ui:button text="${ lfn:message('button.update') }" onclick="submitMethod('update');"/>
                </c:when>
                <c:when test="${ sysUnitDataCenterForm.method_GET == 'add' }">
                    <ui:button text="${ lfn:message('button.save') }" onclick="submitMethod('save');"/>
                </c:when>
            </c:choose>

            <ui:button text="${ lfn:message('button.close') }" order="5" onclick="Com_CloseWindow();"/>
        </ui:toolbar>
    </template:replace>
    <template:replace name="path">
        <ui:menu layout="sys.ui.menu.nav">
            <ui:menu-item text="${ lfn:message('home.home') }" icon="lui_icon_s_home"/>
            <ui:menu-item text="${ lfn:message('sys-unit:table.sysUnitDataCenter') }"/>
        </ui:menu>
    </template:replace>
    <template:replace name="content">
        <html:form action="/sys/unit/sys_unit_data_center/sysUnitDataCenter.do">
            <div class='lui_form_title_frame'>
                <div class='lui_form_subject'>
                        ${lfn:message('sys-unit:table.sysUnitDataCenter')}
                </div>
            </div>
            <table class="tb_normal" width="100%">
                <tr class="data_center_tr_height">
                    <td class="td_normal_title" width="15%">
                            ${lfn:message('sys-dec:sysDecDataCenter.fdType')}
                    </td>
                    <td colspan="3" width="85.0%">
                            <%-- 系统类型--%>
                        <div id="_xform_fdType" _xform_type="radio">
                            <xform:radio property="fdType" htmlElementProperties="id='fdType'" showStatus="edit">
                                <xform:enumsDataSource enumsType="sys_unit_data_center_type"/>
                            </xform:radio>
                        </div>
                    </td>
                </tr>
                <tr class="data_center_tr_height">
                    <td class="td_normal_title" width="15%">
                            ${lfn:message('sys-unit:sysUnitDataCenter.fdName')}
                    </td>
                    <td colspan="3" width="85.0%">
                            <%-- 系统名称--%>
                        <div id="_xform_fdName" _xform_type="text">
                            <xform:text property="fdName" showStatus="edit" style="width:95%;"/>
                        </div>
                    </td>
                </tr>
                <tr class="data_center_tr_height">
                    <td class="td_normal_title" width="15%">
                            ${lfn:message('sys-unit:sysUnitDataCenter.fdAppkey')}
                    </td>
                    <td colspan="3" width="85.0%">
                            <%-- 系统编码--%>
                        <div id="_xform_fdAppkey" _xform_type="text">
                            <xform:text property="fdAppkey" showStatus="edit" style="width:95%;" required="true"
                                        validators="checkOnly checkChar"/>
                            <c:choose>
                                <c:when test="${sysUnitDataCenterForm.fdOldAppKey eq null }">
                                    <html:hidden property="fdOldAppKey" value="${sysUnitDataCenterForm.fdAppkey }"/>
                                </c:when>
                                <c:otherwise>
                                    <html:hidden property="fdOldAppKey" value="${sysUnitDataCenterForm.fdOldAppKey }"/>
                                </c:otherwise>
                            </c:choose>
                        </div>
                    </td>
                </tr>
                <tr class="data_center_tr_height">
                    <td class="td_normal_title" width="15%">
                            ${lfn:message('sys-unit:sysUnitDataCenter.fdDomain')}
                    </td>
                    <td colspan="3" width="85.0%">
                            <%-- 系统域名--%>
                        <div id="_xform_fdDomain" _xform_type="text">
                            <xform:text property="fdDomain" showStatus="edit" style="width:95%;"/>
                        </div>
                    </td>
                </tr>
                <tr class="data_center_tr_height">
                    <td class="td_normal_title" width="15%">
                            ${lfn:message('sys-unit:sysUnitDataCenter.fdIp')}
                    </td>
                    <td colspan="3" width="85.0%">
                            <%-- IP --%>
                        <div id="_xform_fdIp" _xform_type="text">
                            <xform:text property="fdIp" showStatus="edit" style="width:38.5%;"/>
                        </div>
                    </td>
                </tr>
                <tr class="data_center_tr_height">
                    <td class="td_normal_title" width="15%">
                            ${lfn:message('sys-unit:sysUnitDataCenter.fdAccount')}
                    </td>
                    <td colspan="3" width="85.0%">
                            <%-- 对接账号--%>
                        <div id="_xform_fdAccount" _xform_type="text">
                            <xform:text property="fdAccount" showStatus="edit" style="width:95%;"/>
                        </div>
                    </td>
                </tr>
                <c:if test="${ sysUnitDataCenterForm.method_GET == 'add' }">
                    <tr class="data_center_tr_height">
                        <td class="td_normal_title" width="15%">
                                ${lfn:message('sys-unit:sysUnitDataCenter.fdPassword')}
                        </td>
                        <td colspan="3" width="85.0%">
                                <%-- 密码--%>
                            <div id="_xform_fdPassword" _xform_type="text">
                                <xform:text property="fdPassword" showStatus="edit" style="width:95%;"/>
                            </div>
                        </td>
                    </tr>
                </c:if>
                <tr class="data_center_tr_height">
                    <td class="td_normal_title" width="15%">
                            ${lfn:message('sys-unit:sysUnitDataCenter.fdDataType')}
                    </td>
                    <td colspan="3" width="85.0%">
                            <%-- 是否有效--%>
                        <div id="_xform_fdDataType" _xform_type="radio">
                            <xform:radio property="fdDataType" htmlElementProperties="id='fdDataType'"
                                         showStatus="edit">
                                <xform:simpleDataSource value="xml">XML格式</xform:simpleDataSource>
                                <xform:simpleDataSource value="json">JSON格式</xform:simpleDataSource>
                            </xform:radio>
                        </div>
                    </td>
                </tr>
                <tr class="data_center_tr_height">
                    <td class="td_normal_title" width="15%">
                            ${lfn:message('sys-unit:sysUnitDataCenter.fdFileType')}
                    </td>
                    <td colspan="3" width="85.0%">
                        <div id="_xform_fdFileType" _xform_type="radio">
                            <xform:radio property="fdFileType" htmlElementProperties="id='fdFileType'"
                                         showStatus="edit">
                                <xform:simpleDataSource value="base64">Base64</xform:simpleDataSource>
                                <xform:simpleDataSource value="rest">Rest接口下载</xform:simpleDataSource>
                            </xform:radio>
                        </div>
                    </td>
                </tr>
                <tr class="data_center_tr_height">
                    <td class="td_normal_title" width="15%">
                        开通访问策略模块
                    </td>
                    <td colspan="3" width="85.0%">
                        <div id="_xform_" _xform_type="checkbox">
                            <xform:checkbox property="fdRestModels" htmlElementProperties="id='fdRestModel'"
                                            showStatus="edit" subject="开通访问策略模块">
                                <xform:customizeDataSource
                                        className="com.landray.kmss.sys.unit.service.spring.SysUnitDataCenterRestModelData"/>
                            </xform:checkbox>
                        </div>
                    </td>
                </tr>
                <tr class="data_center_tr_height">
                    <td class="td_normal_title" width="15%">
                            ${lfn:message('sys-unit:sysUnitDataCenter.fdIsAvailable')}
                    </td>
                    <td colspan="3" width="85.0%">
                            <%-- 是否有效--%>
                        <div id="_xform_fdIsAvailable" _xform_type="radio">
                            <xform:radio property="fdIsAvailable" htmlElementProperties="id='fdIsAvailable'"
                                         showStatus="edit">
                                <xform:enumsDataSource enumsType="common_yesno"/>
                            </xform:radio>
                        </div>
                    </td>
                </tr>
                <tr>
                    <td class="td_normal_title" width="15%">
                            ${lfn:message('sys-unit:sysUnitDataCenter.fdDesc')}
                    </td>
                    <td colspan="3" width="85.0%">
                            <%-- 描述--%>
                        <div id="_xform_fdDesc" _xform_type="textarea">
                            <xform:textarea property="fdDesc" showStatus="edit" style="width:95%;"/>
                        </div>
                    </td>
                </tr>
            </table>
            <html:hidden property="fdId"/>
            <html:hidden property="method_GET"/>
        </html:form>
        <script language="JavaScript">
            var validation = $KMSSValidation(document.forms['sysUnitDataCenterForm']);

            validation.addValidator("checkOnly", "此编码已存在，请重新输入", function (value, object, text) {
                    return checkOnly(value, object, text);
                }
            );

            validation.addValidator("checkChar", "此编码存在非法字符'/'，请重新输入", function (value, object, text) {
                    var flag = true;
                    if (value.indexOf("/") > 0) {
                        flag = false;
                    }
                    return flag;
                }
            );

            function checkOnly(value, object, text) {
                var flag = false;
                var fdDataCenterId = document.getElementsByName('fdId')[0];
                var url = Com_Parameter.ContextPath + "sys/unit/sys_unit_data_center/sysUnitDataCenter.do?method=checkOnly";
                $.ajax({
                    url: url,
                    type: 'POST',
                    async: false,
                    dataType: 'json',
                    data: $.param({"centerCode": value, "fdDataCenterId": fdDataCenterId.value}, true),
                    success: function (result) {
                        if (result) {
                            if (result.isExistence == "false") {
                                flag = true;
                            }
                        }
                    }
                });
                return flag;
            }

            function submitMethod(method) {
                var formObj = document.sysUnitDataCenterForm;
                if (validation.validate()) {
                    Com_Submit(formObj, method);
                }
            }
        </script>
    </template:replace>
</template:include>