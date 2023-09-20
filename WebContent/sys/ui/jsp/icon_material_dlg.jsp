<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include ref="default.dialog">
    <template:replace name="head">
        <style>
            .lui_material_vbox_selected {
                border: 1px solid #4285F4 !important;
            }
            .lui_material_select .lui_material_vbox_pic .lui_material_img{
                height: 100px;
                line-height: 100px;
                display: flex;
                align-content: center;
                justify-content: center;
                align-items: center;
            }
        </style>
        <link rel="stylesheet" type="text/css"
            href="${LUI_ContextPath}/sys/portal/sys_portal_material_main/source/material_main_icon.css" />

    </template:replace>
    <template:replace name="content">
                <!----------------------------------------------------------pic ----------------------------------------------------->
                <table class="tb_normal material_table" width="98%">
                    <tr>
                        <td colspan="4" class="lui_material_select">
<%--                            <list:criteria id="criteria_pic" channel="material">--%>
<%--                                <list:cri-ref key="fdTagsName" ref="criterion.sys.docSubject" expand="true"--%>
<%--                                    title="${ lfn:message('sys-portal:sysPortalMaterialMain.search.help')}" />--%>
<%--                            </list:criteria>--%>
                            <!-- 筛选 -->
                            <ui:fixed elem=".lui_list_operation_pic" />
                            <list:listview id="listview_pic" channel="material">
                                <ui:source type="AjaxJson">
                                    {url:'/sys/portal/sys_portal_material_main/sysPortalMaterialMain.do?method=gridData&type=2&sort=ture'}
                                </ui:source>
                                <list:gridTable name="gridtable_pic" columnNum="5"  channel="material" gridHref="">
                                    <list:row-template>
                                        {$
                                        <!-- #####################################grid item start[pic]###########################################################  -->
                                        <div class="lui_material_view_box lui_material_vbox_pic "
                                             title="{% grid['fdName']%}" onclick="selected('{% grid['fdId']%}')">
                                            <label style="cursor: pointer;">
                                                <div class="lui_material_img">
                                                    <img src="${LUI_ContextPath }{% grid['imageUrl']%}" alt="">
                                                </div>
                                                <input type="hidden" value="{% grid['imageUrl']%}"
                                                       id="url_{% grid['fdId']%}">
<%--                                                <div class="lui_material_text">--%>
<%--                                                    <p class="lui_material_text_title">--%>
<%--                                                        <b> {%grid['fdName']%}</b>--%>
<%--                                                    </p>--%>
<%--                                                    <p class="lui_material_text_tags">{%grid['tags']%}</p>--%>
<%--                                                </div>--%>
                                            </label>
                                        </div>
                                        <!-- #####################################grid item end###########################################################  -->
                                        $}
                                    </list:row-template>
                                </list:gridTable>
                            </list:listview>
                            <!-- 翻页 -->
                            <list:paging channel="material"/>
                        </td>
                    </tr>
                </table>
		<br/><br/>
<%--        <div class="material_dlg_btn_bar">--%>
<%--            <span class="material_dlg_btn gary" onclick="Com_CloseWindow();">${lfn:message('button.cancel') }</span>--%>
<%--            <span class="material_dlg_btn" onclick="doSubmit();">${lfn:message('button.select') }</span>--%>
<%--        </div>--%>

        <script type="text/javascript">
            seajs.use([
                'lui/jquery',
                'lui/dialog',
                'lui/topic',
                'lui/util/str'
            ], function ($, dialog, topic, str) {
                window.selected =   function(title) {
                    $(".lui_material_view_box ").removeClass("lui_material_vbox_selected");
                    $(event.target).parents(".lui_material_view_box ").addClass("lui_material_vbox_selected")
                    var fdId = title;
                    var url = $('#url_' + fdId).val();
                    var type = 2;
                    if (fdId) {
                        $dialog.hide({
                            "fdId": fdId,
                            "url": url,
                            "type": type,
                        });
                    } else {
                        $dialog.hide(null);
                    }
                }
                // window.doSubmit = function () {
                //     var fdId = $('[name="List_Selected"]:checked').val();
                //     var url = $('#url_' + fdId).val();
                //     var type = 2;
                //     if (fdId) {
                //         $dialog.hide({
                //             "fdId": fdId,
                //             "url": url,
                //             "type": type,
                //         });
                //     } else {
                //         $dialog.hide(null);
                //     }
                // };
                // window.cancel = function () {
                //     $dialog.hide(null);
                // }
            });
        </script>
    </template:replace>
</template:include>