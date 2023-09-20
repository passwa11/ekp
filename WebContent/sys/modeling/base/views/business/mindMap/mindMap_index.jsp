<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<%@ include file="/sys/modeling/base/modeling_head.jsp" %>
<%@page import="com.landray.kmss.sys.modeling.base.util.SysModelingUtil" %>
<template:include ref="config.profile.list">
    <template:replace name="content">
        <div style="margin:5px 10px;">
            <!-- 筛选 -->
            <list:criteria id="criteria1">
                <list:cri-ref key="fdName" ref="criterion.sys.docSubject"
                              title="${lfn:message('sys-modeling-base:modelingBusiness.fdName')}"/>
                <list:cri-auto modelName="com.landray.kmss.sys.modeling.base.business.model.ModelingMindMap"
                               property="docCreateTime"/>
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
                        <ui:toolbar layout="sys.ui.toolbar.sort" style="float:left">
                            <list:sortgroup>
                                <list:sort property="docCreateTime" text="${lfn:message('model.fdCreateTime') }"
                                           group="sort.list"></list:sort>
                                <list:sort property="fdName"
                                           text="${lfn:message('sys-modeling-base:modelingBusiness.fdName')}"
                                           group="sort.list"></list:sort>
                            </list:sortgroup>
                        </ui:toolbar>
                    </div>
                </div>
                <!-- 分页 -->
                <div class="lui_list_operation_page_top">
                    <list:paging layout="sys.ui.paging.top">
                    </list:paging>
                </div>
                <div style="float:right">
                    <div style="display: inline-block;vertical-align: middle;">
                        <ui:toolbar count="3">
                            <ui:button text="${lfn:message('button.add')}" onclick="addDocById()" order="2"/>
                            <ui:button text="${lfn:message('sys-modeling-base:modelingCollectionView.button.copyViews')}" onclick="copyView()" order="3"/>
                            <c:set var="canDelete" value="true"/>
                            <!---->
                            <ui:button text="${lfn:message('button.deleteall')}" onclick="del()" order="4"
                                       id="btnDelete"/>
                        </ui:toolbar>
                    </div>
                </div>
            </div>
            <ui:fixed elem=".lui_list_operation"/>
            <!-- 列表 -->
            <list:listview id="listview">
                <ui:source type="AjaxJson">
                    {url:appendQueryParameter('/sys/modeling/base/mindMap.do?method=data&fdModelId=${param.fdModelId}')}
                </ui:source>
                <!-- 列表视图 -->
                <list:colTable isDefault="false" onRowClick="toEdit('!{fdId}')" name="columntable">
                    <list:col-checkbox/>
                    <list:col-serial/>
                    <list:col-auto props="fdName,docCreateTime,docCreator.name" url=""/></list:colTable>
            </list:listview>
            <!-- 翻页 -->
            <list:paging/>
        </div>
        <script>
            var listOption = {
                contextPath: '${LUI_ContextPath}',
                jPath: 'resPanel',
                modelName: 'com.landray.kmss.sys.modeling.base.business.model.ModelingMindMap',
                templateName: '',
                basePath: '/sys/modeling/base/mindMap.do',
                canDelete: '${canDelete}',
                mode: '',
                templateService: '',
                templateAlert: '${lfn:message("sys-modeling:treeModel.alert.templateAlert")}',
                customOpts: {

                    ____fork__: 0
                },
                lang: {
                    noSelect: '${lfn:message("page.noSelect")}',
                    comfirmDelete: '${lfn:message("page.comfirmDelete")}',
                    comfirmCopyMindMap:'${lfn:message("sys-modeling-base:modelingCollectionView.copyViewsTitle")}'
                }

            };
            Com_IncludeFile("list.js", "${LUI_ContextPath}/sys/modeling/base/resources/js/", 'js', true);
            seajs.use(["lui/jquery", "sys/ui/js/dialog", "lui/topic"], function ($, dialog, topic) {
                // 监听新建更新等成功后刷新
                topic.subscribe('successReloadPage', function () {
                    topic.publish("list.refresh");
                });
                topic.subscribe("list.loaded", function () {
                    var bodyHeight = $(document.body).outerHeight(true) + 70;
                    $("body", parent.document).find('#trigger_iframe').height(bodyHeight);
                });
                window.addDocById = function () {
                    var url = '${LUI_ContextPath}/sys/modeling/base/mindMap.do?method=add&fdModelId=${param.fdModelId}';
                    var iframe = window.parent.document.getElementById("trigger_iframe");
                    $(iframe).attr("src", url);
                    //修改样式
                    $(iframe).parents(".lui_modeling_main.aside_main").eq(0).css("padding-top", "0px");
                    $(iframe).parents(".lui_modeling_main.aside_main").eq(0).parents(".lui_modeling").eq(0).find("#modelingAside").css("display", "none");
                }
                window.toEdit = function (fdId) {
                    var url = '${LUI_ContextPath}/sys/modeling/base/mindMap.do?method=edit&fdId=' + fdId ;
                    var iframe = window.parent.document.getElementById("trigger_iframe");
                    $(iframe).attr("src", url);
                    //修改样式
                    $(iframe).parents(".lui_modeling_main.aside_main").eq(0).css("padding-top", "0px");
                    $(iframe).parents(".lui_modeling_main.aside_main").eq(0).parents(".lui_modeling").eq(0).find("#modelingAside").css("display", "none");
                }

                //复制视图
                window.copyView = function() {
                    var selected = [];
                    $("input[name='List_Selected']:checked").each(function(){
                        selected.push($(this).val());
                    });
                    if(selected.length==0){
                        dialog.alert(listOption.lang.noSelect);
                        return;
                    }

                    dialog.confirm(listOption.lang.comfirmCopyMindMap,function(ok){

                        if(ok==true){
                            var del_load = dialog.loading();
                            var param = {"List_Selected":selected};
                            $.ajax({
                                url:'${LUI_ContextPath}/sys/modeling/base/mindMap.do?method=copyView',
                                data:$.param(param,true),
                                dataType:'json',
                                type:'POST',
                                success:function(data){
                                    if(del_load!=null){
                                        del_load.hide();
                                        topic.publish("list.refresh");
                                    }
                                    dialog.result(data);
                                },
                                error:function(req){
                                    if(req.responseJSON){
                                        var data = req.responseJSON;
                                        dialog.failure(data.title);
                                    }else{
                                        dialog.failure('${lfn:message('sys-modeling-base:modeling.page.operation.failed') }');
                                    }
                                    del_load.hide();
                                }
                            });
                        }
                    });
                };
            });
        </script>
    </template:replace>
</template:include>