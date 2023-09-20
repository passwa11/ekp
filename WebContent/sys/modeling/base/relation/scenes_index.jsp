<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<%@ include file="/sys/modeling/base/modeling_head.jsp" %>
<template:include ref="config.profile.list">
    <template:replace name="content">
        <div style="margin:5px 10px;">
            <!-- 筛选 -->
            <list:criteria id="criteria1">
                <list:cri-ref key="fdName" ref="criterion.sys.docSubject"
                              title="${lfn:message('sys-modeling-base:sysModelingScenes.fdName')}"/>
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
                    {url:appendQueryParameter('/sys/modeling/base/sysModelingScenes.do?method=data&modelMain.fdId=${param.fdModelId}')}
                </ui:source>
                <!-- 列表视图 -->
                <list:colTable isDefault="false" onRowClick="toView('!{fdId}')" name="columntable">
                    <list:col-checkbox/>
                    <list:col-serial/>
                    <list:col-auto props="fdName,fdType,docCreateTime,docCreator.name" url=""/></list:colTable>
            </list:listview>
            <!-- 翻页 -->
            <list:paging/>
        </div>
        <script>
            var listOption = {
                contextPath: '${LUI_ContextPath}',
                jPath: 'behavior',
                modelName: 'com.landray.kmss.sys.modeling.base.model.SysModelingScenes',
                templateName: '',
                basePath: '/sys/modeling/base/sysModelingScenes.do',
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
                topic.subscribe("list.loaded",function(){
                    var bodyHeight = $(document.body).outerHeight(true)+70;
                    $("body",parent.document).find('#trigger_iframe').height(bodyHeight);
                });
                window.addDocById = function () {
                    dialog.iframe("/sys/modeling/base/sysModelingScenes.do?method=add&modelId=${param.fdModelId}", "${lfn:message('sys-modeling-base:behavior.new.scene')}",
                        function (value) {
                            topic.publish("list.refresh");
                        }, {
                            width: 1010,
                            height: 600
                        });
                }
                window.toView = function (fdId) {
                    dialog.iframe("/sys/modeling/base/sysModelingScenes.do?method=edit&fdId=" + fdId, "${lfn:message('sys-modeling-base:behavior.Details')}",
                        function (value) {
                            topic.publish("list.refresh");
                        }, {
                            width: 1010,
                            height: 600
                        });
                }
            });
        </script>
    </template:replace>
</template:include>
