<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<%@ include file="/sys/modeling/base/modeling_head.jsp" %>
<template:include ref="config.profile.list">
    <template:replace name="head">
        <link type="text/css" rel="stylesheet" href="${LUI_ContextPath}/sys/modeling/base/resources/css/newIndexBody.css?s_cache=${LUI_Cache}" />
    </template:replace>
    <template:replace name="content">
        <div class="new_listview_tips">
            <div class="new_listview_text">
                <i></i>
                <div class="new_listview_tips_content">
                    ${lfn:message('sys-modeling-base:view.old.version.only.maintenance') }
                </div>
            </div>
            <div class="new_listview_button_new" onclick="goToNewVersion()">
                <i style=""></i>
                <div class="new_listview_button_text">${lfn:message('sys-modeling-base:listview.go.to.new.version') }</div>
            </div>
        </div>
        <!-- 筛选器 -->
        <list:criteria id="criteria1">
            <list:cri-ref key="fdName" ref="criterion.sys.docSubject"
                          title="${lfn:message('sys-modeling-base:modelingAppListview.fdName') }"></list:cri-ref>
        </list:criteria>

        <!-- 操作栏 -->
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
                        <list:sort property="fdName"
                                   text="${lfn:message('sys-modeling-base:modelingAppListview.fdName') }"
                                   group="sort.list" value="up"></list:sort>
                    </ui:toolbar>
                </div>
            </div>
            <!-- 分页 -->
            <div class="lui_list_operation_page_top">
                <list:paging layout="sys.ui.paging.top">
                </list:paging>
            </div>
            <!-- 操作按钮 -->
            <div style="float:right">
                <div style="display: inline-block;vertical-align: middle;">
                    <ui:toolbar id="Btntoolbar">
                        <kmss:auth
                                requestURL="/sys/modeling/base/modelingAppListview.do?method=add&fdModelId=${param.fdModelId}">
                            <!-- 增加 -->
                           <%-- <ui:button text="${lfn:message('button.add')}" onclick="addListview()"
                                       order="1"></ui:button>--%>
                        </kmss:auth>
                        <!-- 快速排序 -->
                        <%-- <c:import url="/sys/profile/common/change_order_num.jsp" charEncoding="UTF-8">
                            <c:param name="modelName" value="com.landray.kmss.sys.modeling.base.model.ModelingAppListview"></c:param>
                            <c:param name="property" value="fdOrder"></c:param>
                        </c:import> --%>
                        <kmss:auth
                                requestURL="/sys/modeling/base/modelingAppListview.do?method=deleteall&fdModelId=${param.fdModelId}">
                            <!-- 删除 -->
                            <ui:button text="${lfn:message('button.deleteall')}" onclick="del()" order="2"></ui:button>
                        </kmss:auth>
                    </ui:toolbar>
                </div>
            </div>
        </div>
        </div>
        <ui:fixed elem=".lui_list_operation"></ui:fixed>
        <!-- 内容列表 -->
        <list:listview>
            <ui:source type="AjaxJson">
                {url:'/sys/modeling/base/pcAndMobileView.do?method=list&fdModelId=${param.fdModelId}'}
            </ui:source>
            <list:colTable isDefault="true" layout="sys.ui.listview.columntable" onRowClick="edit('!{fdId}')">
                <list:col-checkbox></list:col-checkbox>
                <list:col-auto props="fdName,docCreator,docCreateTime"></list:col-auto>
            </list:colTable>
            <ui:event topic="list.loaded">
                Dropdown.init();
            </ui:event>
        </list:listview>
        <!-- 分页 -->
        <list:paging/>

        <script type="text/javascript">
            var listOption = {
                param: {
                    fdAppModelId: '${param.fdModelId}'
                },
                contextPath: '${LUI_ContextPath}',
                modelName: 'com.landray.kmss.sys.modeling.base.pcmobile.model.ModelingPcAndMobileView',
                templateName: '',
                basePath: '/sys/modeling/base/pcAndMobileView.do',
                canDelete: '${canDelete}',
                mode: '',
                templateService: '',
                templateAlert: '${lfn:message("sys-modeling-base:treeModel.alert.templateAlert")}',
                customOpts: {
                    ____fork__: 0
                },
                lang: {
                    noSelect: '${lfn:message("page.noSelect")}',
                    comfirmDelete: '${lfn:message("page.comfirmDelete")}',
                    dialogTitle: '新建查看视图（PC+移动端模式）',
                    relatedDialogTitle: '${lfn:message("sys-modeling-base:modelingAppListview.relatedDialogTitle")}'
                }
            };
            Com_IncludeFile("list.js", "${LUI_ContextPath}/sys/modeling/base/resources/js/", 'js', true);
            seajs.use(['lui/jquery', 'lui/dialog', 'lui/topic'], function ($, dialog, topic) {
                // 监听新建更新等成功后刷新
                topic.subscribe('successReloadPage', function () {
                    topic.publish("list.refresh");
                });
                topic.subscribe("list.loaded", resizeFrame);
                function resizeFrame(){
                    var bodyHeight = $(parent.parent.document).find(".model-body-wrap-iframe").eq(0).outerHeight(true) - 75;
                    $("body", parent.document).find('#trigger_iframe').height(bodyHeight);
                    $("body", parent.document).find("#modelingAside").css("display", "block");
                    $("body", parent.document).find(".lui_modeling_main.aside_main").eq(0).css("padding-top", "10px");
                }
                // 增加
                window.addListview = function () {
                    var url = '/sys/modeling/base/pcAndMobile/res/import/create_dialog.jsp';
                    var dialogParams = {
                        noReaders:true,
                        authReaders: [],
                        fdName: "",
                        fdIsDefault: "false"
                    };
                    dialog.iframe(url, listOption.lang.dialogTitle, function (data) {
                        console.log("dialog====",data)
                        if (data == null)
                            return;
                        //回调
                        $.ajax({
                            url: Com_Parameter.ContextPath + "sys/modeling/base/pcAndMobileView.do?method=ajaxSaveBase",
                            dataType: 'json',
                            type: 'post',
                            data: {
                                fdModelId: listOption.param.fdAppModelId,
                                fdName:data.fdName,
                                fdIsDefault: data.fdIsDefault
                            },
                            async: false,
                            success: function (result) {
                                if (result.status === '200') {
                                    //刷新当前窗口-#108568,延时1.8秒，提交后保证后台数据已新建
                                    var sloading = dialog.loading("新建成功，等待跳转...");
                                    setTimeout(function(){
                                        edit(result.fdId);
                                        sloading.hide();
                                    },1800);
                                } else {
                                    dialog.failure(rtn.error);
                                }
                            }
                        });
                    }, {width: 540, height: 270, params: dialogParams});
                };
                // 编辑
                window.edit = function (id) {
                    if (id) {
                        var url = Com_Parameter.ContextPath + "sys/modeling/base/pcAndMobileView.do?method=edit&fdId="+id;
                        var iframe = window.parent.document.getElementById("trigger_iframe");
                        $(iframe).attr("src",url);
                        resizeFrame();
                    }
                };
                // 前往新版
                window.goToNewVersion = function() {
                    var  url ='${LUI_ContextPath}/sys/modeling/base/view/config/new/index_body.jsp?fdModelId='+ listOption.param.fdAppModelId +'&method=edit';
                    var iframe = window.parent.document.getElementById("trigger_iframe");
                    $(iframe).attr("src",url);
                    $(window.parent.document.getElementById("modelingAsideOld")).css("display","none");
                };
            });
        </script>
    </template:replace>
</template:include>