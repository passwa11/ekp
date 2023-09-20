<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<%-- 本页面仅适用于 RTF 中的"系统图片"控件 --%>
<template:include file="/sys/profile/resource/template/list.jsp">
    <template:replace name="head">
        <link rel="stylesheet" type="text/css" href="${KMSS_Parameter_ContextPath}sys/portal/sys_portal_material_main/source/material_main.css">
    </template:replace>
    <template:replace name="content">
        <div class="lui_material_div_wrap">
            <ui:tabpanel>
                <ui:content title="${lfn:message('sys-portal:sys.portal.material.rtf.pic') }">
                    <!-- 筛选 -->
                    <list:criteria channel="material_1">
                        <list:cri-ref key="fdTagsName" ref="criterion.sys.docSubject" title="${lfn:message('sys-portal:sys.portal.material.rtf.keyword') }" style="width: 300px;"></list:cri-ref>
                    </list:criteria>

                    <list:listview channel="material_1">
                        <ui:source type="AjaxJson">
                            {url:'/sys/portal/sys_portal_material_main/sysPortalMaterialMain.do?method=gridData&type=1&sort=ture'}
                        </ui:source>
                        <list:gridTable name="gridtable_pic" columnNum="5" channel="material_1" gridHref="">
                            <list:row-template>
                                {$
                                <div class="lui_material_view_box lui_material_vbox_pic "
                                     title="{% grid['fdName']%}" onclick="selectMaterial('{%grid['fdId']%}','${LUI_ContextPath}{%grid['imageUrl']%}')">
                                    <div class="lui_material_img">
                                        <img src="${LUI_ContextPath }{%grid['imageUrl']%}" alt="">
                                    </div>
                                    <div class="lui_material_text">
                                        <p class="lui_material_text_title">
                                            <b>{%grid['fdName']%}</b>
                                        </p>
                                        <p class="lui_material_text_tags">{%grid['tags']%}</p>
                                    </div>
                                </div>
                                $}
                            </list:row-template>
                        </list:gridTable>
                    </list:listview>
                    <!-- 翻页 -->
                    <list:paging channel="material_1"/>
                </ui:content>
                <ui:content title="${lfn:message('sys-portal:sys.portal.material.rtf.icon') }">
                    <!-- 筛选 -->
                    <list:criteria channel="material_2">
                        <list:cri-ref key="fdTagsName" ref="criterion.sys.docSubject" title="${lfn:message('sys-portal:sys.portal.material.rtf.keyword') }" style="width: 300px;"></list:cri-ref>
                    </list:criteria>

                    <list:listview channel="material_2">
                        <ui:source type="AjaxJson">
                            {url:'/sys/portal/sys_portal_material_main/sysPortalMaterialMain.do?method=gridData&type=2&sort=ture'}
                        </ui:source>
                        <list:gridTable name="gridtable_pic" columnNum="5" channel="material_2" gridHref="">
                            <list:row-template>
                                {$
                                <div class="lui_material_view_box lui_material_vbox_pic "
                                     title="{% grid['fdName']%}" onclick="selectMaterial('{% grid['fdId']%}','${LUI_ContextPath}{%grid['imageUrl']%}')">
                                    <div class="lui_material_img">
                                        <img src="${LUI_ContextPath }{%grid['imageUrl']%}" alt="">
                                    </div>
                                    <div class="lui_material_text">
                                        <p class="lui_material_text_title">
                                            <b>{%grid['fdName']%}</b>
                                        </p>
                                        <p class="lui_material_text_tags">{%grid['tags']%}</p>
                                    </div>
                                </div>
                                $}
                            </list:row-template>
                        </list:gridTable>
                    </list:listview>
                    <!-- 翻页 -->
                    <list:paging channel="material_2"/>
                </ui:content>
            </ui:tabpanel>
        </div>

        <script>
            window.selectMaterial = function(id, url) {
                if(parent.selectMaterial) {
                    parent.selectMaterial(id, url);
                }
            };
        </script>
    </template:replace>
</template:include>
