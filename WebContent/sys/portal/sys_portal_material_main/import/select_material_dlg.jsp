<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include ref="default.dialog">
    <template:replace name="head">
        <style>
            .lui_material_vbox_selected {
                border: 1px solid #4285F4 !important;
            }
            .lui_material_select .lui_material_vbox_pic .lui_material_img{
                height: 130px;
    			line-height: 130px;
            }
        </style>
        <link rel="stylesheet" type="text/css"
            href="${LUI_ContextPath}/sys/portal/sys_portal_material_main/source/material_main.css" />

    </template:replace>
    <template:replace name="content">
        <p class="txttitle" style="margin: 10px 5px;">
            ${ lfn:message('sys-portal:portal.material.library') }
        </p>
       
                <!----------------------------------------------------------pic ----------------------------------------------------->
                <table class="tb_normal" width="98%">
                    <tr>
                        <td colspan="4" class="lui_material_select">
                            <list:criteria id="criteria_pic">
                                <list:cri-ref key="fdTagsName" ref="criterion.sys.docSubject" expand="true"
                                    title="${ lfn:message('sys-portal:sysPortalMaterialMain.search.help')}" />
                            </list:criteria>
                            <!-- 筛选 -->
                            <!-- 操作 -->
                            <div class="lui_list_operation lui_list_operation_pic">
                                <div style="float: left">
                                </div>
                                <div style="float: left;">
                                </div>
                                <div style="float: right">
                                </div>
                            </div>
                            <ui:fixed elem=".lui_list_operation_pic" />
                            <list:listview id="listview_pic">
                                <ui:source type="AjaxJson">
                                    {url:'/sys/portal/sys_portal_material_main/sysPortalMaterialMain.do?method=gridData&type=1'}
                                </ui:source>
                                <list:gridTable name="gridtable_pic" columnNum="3" gridHref="">
                                    <list:row-template>
                                        {$
                                        <!-- #####################################grid item start[pic]###########################################################  -->
                                        <div class="lui_material_view_box lui_material_vbox_pic "
                                            onclick="selected(event)">
                                            <label>
                                                <div class="lui_material_img">
                                                    <img src="${LUI_ContextPath }{% grid['imageUrl']%}" alt="">
                                                </div>
                                                <div class="lui_material_text">
                                                    <p class="lui_material_text_title">
                                                        <input type="radio" value="{% grid['fdId']%}"
                                                            name="List_Selected">
                                                        <input type="hidden" value="{% grid['imageUrl']%}"
                                                            id="url_{% grid['fdId']%}">
                                                        <b> {%grid['fdName']%}</b>
                                                    </p>
                                                    <p class="lui_material_text_tags">{%grid['tags']%}</p>
                                                </div>
                                            </label>
                                        </div>
                                        <!-- #####################################grid item end###########################################################  -->
                                        $}
                                    </list:row-template>
                                </list:gridTable>
                            </list:listview>
                            <!-- 翻页 -->
                            <list:paging />
                        </td>
                    </tr>
                </table>
		<br/><br/>
        <div class="material_dlg_btn_bar">
            <span class="material_dlg_btn gary" onclick="Com_CloseWindow();">${lfn:message('button.cancel') }</span>
            <span class="material_dlg_btn" onclick="doSubmit();">${lfn:message('button.select') }</span>
        </div>

        <script type="text/javascript">
            function selected(event) {
                $(".lui_material_view_box ").removeClass("lui_material_vbox_selected");
                $(event.target).parents(".lui_material_view_box ").addClass("lui_material_vbox_selected")
            }
            seajs.use([
                'lui/jquery',
                'lui/dialog',
                'lui/topic',
                'lui/util/str'
            ], function ($, dialog, topic, str) {
                window.doSubmit = function () {
                    var fdId = $('[name="List_Selected"]:checked').val();
                    var url = $('#url_' + fdId).val();
                    if (fdId) {
                        $dialog.hide({
                            "fdId": fdId,
                            "url": url
                        });
                    } else {
                        $dialog.hide(null);
                    }
                };

                window.cancel = function () {
                    $dialog.hide(null);
                }
            });
        </script>
    </template:replace>
</template:include>