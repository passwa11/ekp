<%@ page import="com.landray.kmss.sys.modeling.base.constant.ModelingAppVersionConstant" %>
<%@ page import="com.landray.kmss.sys.modeling.base.util.SysModelingUtil" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<%@ include file="/sys/modeling/base/modeling_head.jsp" %>
<template:include ref="config.profile.list">
    <template:replace name="content">
        <div style="margin:5px 10px;">
            <!-- 筛选 -->
            <list:criteria id="criteria1">
                <list:cri-auto modelName="com.landray.kmss.sys.modeling.base.formlog.model.ModelingFormModifiedLog" property="docCreateTime" />
            </list:criteria>
            <!-- 操作 -->
            <div class="lui_list_operation">
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

                        </ui:toolbar>
                    </div>
                </div>
            </div>
        </div>
        <ui:fixed elem=".lui_list_operation"/>
        <!-- 列表 -->
        <list:listview id="listview">
            <ui:source type="AjaxJson">
                {url:appendQueryParameter('/sys/modeling/base/modelingFormModified.do?method=data&fdModelId=${param.fdModelId}')}
            </ui:source>
            <!-- 列表视图 -->
            <list:colTable isDefault="false" onRowClick="view('!{fdId}')" name="columntable">
                <list:col-serial/>
                <list:col-auto props="modelMain.fdName;docCreateTime;docCreator.name;" url=""/></list:colTable>
        </list:listview>
        <!-- 翻页 -->
        </div>
        <list:paging/>

        <script>
            var listOption = {
                contextPath: '${LUI_ContextPath}',
                modelName: 'com.landray.kmss.sys.modeling.base.formlog.model.ModelingFormModifiedLog',
                templateName: '',
                basePath: '/sys/modeling/base/modelingFormModified.do',
                canDelete: '${canDelete}',
                mode: '',
                templateService: '',
                templateAlert: '${lfn:message("sys-modeling-base:treeModel.alert.templateAlert")}',
                customOpts: {

                    ____fork__: 0
                },
                lang: {
                    noSelect: '${lfn:message("page.noSelect")}',
                    comfirmDelete: '${lfn:message("page.comfirmDelete")}'
                }

            };

            Com_IncludeFile("list.js", "${LUI_ContextPath}/sys/modeling/base/resources/js/", 'js', true);


            seajs.use(["lui/jquery", "sys/ui/js/dialog","lui/topic"], function ($, dialog,topic) {
                topic.subscribe("list.loaded",function(){
                    var bodyHeight = $(document.body).outerHeight(true)+70;
                    $("body",parent.document).find('#trigger_iframe').height(bodyHeight);
                });
                window.view = function(id) {
                    // openPageInDialog(listOption.basePath + "?method=edit&fdId=" + id, "编辑portlet视图");
                    window.open(listOption.contextPath+ listOption.basePath + "?method=view&fdId=" + id,"_blank");
                    // location.href =;
                }
	        });
        </script>
    </template:replace>
</template:include>