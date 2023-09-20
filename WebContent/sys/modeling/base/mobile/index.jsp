<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<%@ include file="/sys/modeling/base/modeling_head.jsp" %>
<template:include ref="config.profile.list">
    <template:replace name="content">
        <div style="margin:5px 10px;">
            <!-- 筛选 -->
            <list:criteria id="criteria1">
                <list:cri-ref key="docSubject" ref="criterion.sys.docSubject"
                              title="${lfn:message('sys-modeling:modelingAppMobile.docSubject')}"/>
            </list:criteria>
            <!-- 操作 -->
            <div class="lui_list_operation">
                <!-- 全选 -->
                <div class="lui_list_operation_order_btn">
                    <list:selectall></list:selectall>
                </div>
                <!-- 分割线 -->
                <div class="lui_list_operation_line"></div>
                <!-- 排序 -->
                <div class="lui_list_operation_sort_btn">
                    <div class="lui_list_operation_order_text">
                            ${ lfn:message('list.orderType') }：
                    </div>
                    <div class="lui_list_operation_sort_toolbar">
                        <ui:toolbar layout="sys.ui.toolbar.sort" style="float:left" >
                            <list:sortgroup>
                                <list:sort property="docCreateTime" text="${lfn:message('model.fdCreateTime') }" group="sort.list"></list:sort>
                            </list:sortgroup>
                        </ui:toolbar>
                    </div>
                </div>
                <!-- 分页 -->
                <div class="lui_list_operation_page_top">
                    <list:paging layout="sys.ui.paging.top" >
                    </list:paging>
                </div>
                <div style="float:right">
                    <div style="display: inline-block;vertical-align: middle;">
                        <ui:toolbar count="3">
                            <ui:button text="${lfn:message('button.add')}" onclick="addDocById()" order="2"/>
                            <c:set var="canDelete" value="true"/>
                            <!---->
                            <ui:button text="${lfn:message('button.deleteall')}" onclick="deleteAll()" order="4"
                                       id="btnDelete"/>
                        </ui:toolbar>
                    </div>
                </div>
            </div>
            <ui:fixed elem=".lui_list_operation"/>
            <!-- 列表 -->
            <list:listview id="listview">
                <ui:source type="AjaxJson">
                    {url:appendQueryParameter('/sys/modeling/base/mobile/modelingAppMobile.do?method=data&fdAppId=${param.fdAppId}')}
                </ui:source>
                <!-- 列表视图 -->
                <list:colTable isDefault="false" onRowClick="toView('!{fdId}')" name="columntable">
                    <list:col-checkbox/>
                    <list:col-auto props="fdOrder,docSubject,fdIndex,docCreateTime,docCreator.name,operations" url=""/></list:colTable>
            </list:listview>
            <!-- 翻页 -->
            <list:paging/>
        </div>
        <script>
            var listOption = {
                contextPath: '${LUI_ContextPath}',
                jPath: 'modelingAppMobile',
                modelName: 'com.landray.kmss.sys.modeling.base.model.ModelingAppMobile',
                templateName: '',
                basePath: '/sys/modeling/base/mobile/modelingAppMobile.do',
                canDelete: '${canDelete}',
                mode: '',
                templateService: '',
                templateAlert: '${lfn:message("sys-modeling:treeModel.alert.templateAlert")}',
                customOpts: {

                    ____fork__: 0
                },
                lang: {
                    noSelect: '${lfn:message("page.noSelect")}',
                    comfirmDelete: '${lfn:message("page.comfirmDelete")}'
                }

            };
            Com_IncludeFile("list.js", "${LUI_ContextPath}/sys/modeling/base/resources/js/", 'js', true);
            seajs.use(["lui/topic", "lui/jquery", "sys/ui/js/dialog"], function (topic,$, dialog) {
                /*topic.subscribe("list.loaded",function(){
                    var bodyHeight = $(document.body).outerHeight(true)+70;
                    $("body",parent.document).find('#trigger_iframe').height(bodyHeight);
                });*/
                window.addDocById = function () {
                    var url = "${LUI_ContextPath}/sys/modeling/base/mobile/modelingAppMobile.do?method=add&fdAppId=${param.fdAppId}";
                    var dialogUrl ="/sys/modeling/base/mobile/dialog_list_type.jsp";
                    dialog.iframe(dialogUrl,"${lfn:message('sys-modeling-base:module.mobile.indexTmp.select')}",function(data){
                        if(data == null||data == 'cancle'){
                            return;
                        }
                        url = url + "&fdType="+data;
                        var tabTitle = window.parent.document.getElementById("space-title");
                        $(tabTitle).css("display","none");
                        var iframe = window.parent.document.getElementById("trigger_iframe");
                        $(iframe).attr("src",url);
                        //修改样式
                        var height=$(iframe).parents('body').height()+15;
                        $(iframe).parents(".lui_modeling_main.aside_main").eq(0).css("padding-top","0px");
                        $(iframe).height(height)
                    },{
                        width : 1027,
                        height : 620,
                        params : {}
                    });

                };
                window.toView = function (fdId) {
                    location.href = "${LUI_ContextPath}/sys/modeling/base/mobile/modelingAppMobile.do?method=edit&fdAppId=${param.fdAppId}&fdId=" + fdId;
                    var tabTitle = window.parent.document.getElementById("space-title");
                    $(tabTitle).css("display","none");
                    var iframe = window.parent.document.getElementById("trigger_iframe");
                    //修改样式
                    var height=$(iframe).parents('body').height()+15;
                    $(iframe).parents(".lui_modeling_main.aside_main").eq(0).css("padding-top","0px");
                    $(iframe).height(height)
                }
            });
        </script>
    </template:replace>
</template:include>
