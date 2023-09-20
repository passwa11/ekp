<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<template:include ref="default.dialog">
    <template:replace name="head">
        <link rel="stylesheet" type="text/css"
            href="${LUI_ContextPath}/sys/portal/sys_portal_material_main/source/cropper/cropper.css" />
        <link rel="stylesheet" type="text/css"
            href="${LUI_ContextPath}/sys/portal/sys_portal_material_main/source/material_main.css" />
        <script type="text/javascript">
            var initData = {
                contextPath: '${LUI_ContextPath}',
            };
            Com_IncludeFile("security.js");
            Com_IncludeFile("domain.js");
            Com_IncludeFile("data.js|dialog.js|jquery.js");
            Com_IncludeFile("swf_attachment.js", "${LUI_ContextPath}/sys/attachment/js/", "js", true);
            Com_IncludeFile("cropper_material.js", "${LUI_ContextPath}/sys/portal/sys_portal_material_main/source/",
                "js", true);
            Com_IncludeFile("cropper.js", "${LUI_ContextPath}/sys/portal/sys_portal_material_main/source/cropper/",
                "js", true);

            Com_IncludeFile("material_main.js", "${LUI_ContextPath}/sys/portal/sys_portal_material_main/source/", 'js',
                true);
        </script>
 
    </template:replace>
    <template:replace name="content">
        <div class="lui_material_edit_container">
            <div class="lui_material_edit_center">
                <div class="lui_material_edit_c_img">
                    <img src="${LUI_ContextPath }/sys/attachment/sys_att_main/sysAttMain.do?method=download&fdId=${main.fdAttId }&open=1"
                        alt="">
                </div>
                <div class="lui_material_edit_c_table">
                    <table>
                        <tbody>
                            <tr>
                                <td>${ lfn:message('sys-portal:sysPortalMaterialMain.fdName')}</td>
                                <td>&emsp;<input type="text" name="fdName" value="${lfn:escapeHtml(main.fdName) }" onchange="validXss(event)"></td>
                            </tr>
                            <tr>
                                <td>${ lfn:message('sys-portal:sysPortalMaterialMain.fdTags')}</td>
                                <td>&emsp;<input type="text" name="fdTags" value="${lfn:escapeHtml(mainForm.fdTagNames) }" onchange="validXss(event)"></td>
                            </tr>
                         <tr>
                                    <td>${ lfn:message('sys-portal:sysPortalMaterialMain.fdSize')}</td><td>&emsp;<span name="size">${main.fdSize }</span></td>
                                </tr>
                                <tr>
                                    <td>${ lfn:message('sys-portal:sysPortalMaterialMain.ppi')}</td><td>&emsp;<span name="width">${main.fdWidth }</span>X<span name="height">${main.fdLength }</span></td>

                                </tr>
                            <tr>
                                <td style="dsiplay:none">
                                    <input id="att_id" type="hidden" value="">
                                    <span id="att_btn"></span>
                                </td>
                            </tr>
                        </tbody>
                    </table>
                </div>
            </div>

        </div>
        <div class="material_dlg_btn_bar">
            <span class="material_dlg_btn gary" onclick="Com_CloseWindow();">${lfn:message('button.cancel') }</span>
            <span class="material_dlg_btn" onclick="doEditSave('${main.fdId}', '${main.fdAttId}');">${lfn:message('button.ok') }</span>
        </div>

    </template:replace>

</template:include>
