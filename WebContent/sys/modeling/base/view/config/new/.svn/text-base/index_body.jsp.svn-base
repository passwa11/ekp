<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ include file="/sys/modeling/base/modeling_head.jsp" %>
<template:include ref="config.profile.list">
    <template:replace name="head">
        <link type="text/css" rel="stylesheet" href="${LUI_ContextPath}/sys/modeling/base/resources/css/newIndexBody.css?s_cache=${LUI_Cache}" />
        <style>
            .listview{
                min-height: 345px !important;
            }
        </style>
    </template:replace>
    <template:replace name="content">
        <center>
            <div class="listview_no_content" style="display: none">
                <div class="listview_default_images">
                </div>
                <div class="litview_new_tips">${lfn:message('sys-modeling-base:view.upgrade.use.new') }</div>
                <div class="litview_old_tips">${lfn:message('sys-modeling-base:view.return.old.version.maintenance') }</div>
                <div class="listview_botton">
                    <div class="listview_botton_new">
                        <i style=""></i>
                        <div class="listview_button_new_text" onclick="useNewVersion()">${lfn:message('sys-modeling-base:listview.use.new.version') }</div>
                    </div>
                    <div class="listview_button_old" onclick="reBackOldVersion()">
                        <i style=""></i>
                        <div class="listview_button_old_text">${lfn:message('sys-modeling-base:listview.back.old.version') }</div>
                    </div>
                </div>
            </div>
        </center>

        <div class="new_listview_tips" style="display: none">
            <div class="new_listview_text">
                <i></i>
                <div class="new_listview_tips_content">
                    ${lfn:message('sys-modeling-base:view.return.old.version.maintenance') }
                </div>
            </div>
            <div class="new_listview_button" onclick="reBackOldVersion()">
                <i style=""></i>
                <div class="new_listview_button_text">${lfn:message('sys-modeling-base:listview.back.old.version') }</div>
            </div>
        </div>
        <div class="listview_content">
            <div style="margin:5px 10px;">
                <!-- 筛选 -->
                <list:criteria id="criteria1">
                    <list:cri-ref key="fdName" ref="criterion.sys.docSubject" title="${lfn:message('sys-modeling-base:modelingAppView.fdName') }"></list:cri-ref>
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
                                <list:sort property="modelingAppView.docCreateTime" text="${lfn:message('sys-modeling-base:modelingAppView.docCreateTime')}" group="sort.list" />
                                <list:sort property="modelingAppView.docAlterTime" text="${lfn:message('sys-modeling-base:modelingAppView.docAlterTime')}" group="sort.list" />
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
                                <kmss:auth requestURL="/sys/modeling/base/modelingAppView.do?method=add">
                                    <ui:button text="${lfn:message('button.add')}" onclick="addView()" order="2" />
                                    <ui:button text="${lfn:message('sys-modeling-base:modelingCollectionView.button.copyViews')}" onclick="copyView()" order="3" />
                                </kmss:auth>
                                <kmss:auth requestURL="/sys/modeling/base/modelingAppView.do?method=deleteall">
                                    <c:set var="canDelete" value="true" />
                                </kmss:auth>
                                <ui:button text="${lfn:message('button.deleteall')}" onclick="del()" order="4" id="btnDelete" />
                            </ui:toolbar>
                        </div>
                    </div>
                </div>
            </div>
            <ui:fixed elem=".lui_list_operation" />
            <!-- 列表 -->
            <list:listview id="listview">
                <ui:source type="AjaxJson">
                    {url:appendQueryParameter('/sys/modeling/base/modelingAppView.do?method=data&fdModelId=${param.fdModelId}&fdMobile=${param.fdMobile}&fdIsNew=true')}
                </ui:source>
                <!-- 列表视图 -->
                <list:colTable isDefault="false" onRowClick="edit('!{fdId}')" name="columntable">
                    <list:col-checkbox />
                    <list:col-serial/>
                    <list:col-auto props="fdName;fdModel.name;docCreateTime;docAlterTime" url="" /></list:colTable>
            </list:listview>
            <!-- 翻页 -->
            <list:paging />
        </div>

        <script>
            var listOption = {
                param : {
                    fdAppModelId : '${param.fdModelId}',
                    fdMobile : '${param.fdMobile}'
                },
                contextPath: '${LUI_ContextPath}',
                modelName: 'com.landray.kmss.sys.modeling.base.model.ModelingAppView',
                templateName: '',
                basePath: '/sys/modeling/base/modelingAppView.do',
                canDelete: '${canDelete}',
                mode: '',
                templateService: '',
                templateAlert: '${lfn:message("sys-modeling-base:treeModel.alert.templateAlert")}',
                customOpts: {
                    ____fork__: 0
                },
                temp: {
                    relatedDatas: ''
                },
                lang: {
                    noSelect: '${lfn:message("page.noSelect")}',
                    comfirmDelete: '${lfn:message("page.comfirmDelete")}',
                    dialogTitle : '${lfn:message("sys-modeling-base:modelingAppView.editDialogTitle")}',
                    relatedDialogTitle : '${lfn:message("sys-modeling-base:modelingAppListview.relatedDialogTitle")}',
                    comfirmCopyView:'${lfn:message("sys-modeling-base:modelingCollectionView.copyViewsTitle")}'
                }
            };
            Com_IncludeFile("list.js", "${LUI_ContextPath}/sys/modeling/base/resources/js/", 'js', true);

            /*  function addView() {
                 openPageInDialog(listOption.basePath + "?method=add&fdModelId=" + listOption.param.fdAppModelId + '&fdMobile=' + listOption.param.fdMobile, listOption.lang.dialogTitle);
             } */

            /* function edit(id) {
                openPageInDialog(listOption.basePath + "?method=edit&fdModelId=" + listOption.param.fdAppModelId + "&fdId=" + id, listOption.lang.dialogTitle);
            } */
            seajs.use(["lui/jquery", "sys/ui/js/dialog","lui/topic"], function ($, dialog,topic) {
                topic.subscribe("list.loaded",function(){
                    // var bodyHeight = $(document.body).outerHeight(true)+70;
                    var bodyHeight = $(parent.parent.document).find(".model-body-wrap-iframe").eq(0).outerHeight(true)-40;
                    $("body",parent.document).find('#trigger_iframe').height(bodyHeight);
                    $("body",parent.document).find("#modelingAside").css("display","block");
                    $("body",parent.document).find(".lui_modeling_main.aside_main").eq(0).css("padding-top","10px");
                });

                window.addView = function(){
                    var url = "${LUI_ContextPath}"+listOption.basePath + "?method=add&fdModelId=" + listOption.param.fdAppModelId + '&fdMobile=' + listOption.param.fdMobile+"&fdIsNew=true";
                    var iframe = window.parent.document.getElementById("trigger_iframe");
                    $(iframe).attr("src",url);
                    //修改样式
                    $(iframe).parents(".lui_modeling_main.aside_main").eq(0).css("padding-top","0px");
                    $(iframe).parents(".lui_modeling_main.aside_main").eq(0).parents(".lui_modeling").eq(0).find("#modelingAside").css("display","none");
                }

                window.edit = function(id){
                    var url = "${LUI_ContextPath}"+listOption.basePath + "?method=edit&fdModelId=" + listOption.param.fdAppModelId + "&fdId=" + id;
                    /* var title = listOption.lang.dialogTitle;
                    dialog.iframe(url, title, function(data){
                        //回调
                        topic.publish('list.refresh');
                    }, {width:900,height:500}); */
                    var iframe = window.parent.document.getElementById("trigger_iframe");
                    $(iframe).attr("src",url);
                    //修改样式
                    $(iframe).parents(".lui_modeling_main.aside_main").eq(0).css("padding-top","0px");
                    $(iframe).parents(".lui_modeling_main.aside_main").eq(0).parents(".lui_modeling").eq(0).find("#modelingAside").css("display","none");
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

                    dialog.confirm(listOption.lang.comfirmCopyView,function(ok){

                        if(ok==true){
                            var del_load = dialog.loading();
                            var param = {"List_Selected":selected};
                            $.ajax({
                                url:'${LUI_ContextPath}/sys/modeling/base/modelingAppView.do?method=copyView',
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

            // 返回旧版
            window.reBackOldVersion = function() {
                var  url ='${LUI_ContextPath}/sys/modeling/base/pcAndMobile/view/index_body.jsp?fdModelId='+ listOption.param.fdAppModelId +'&method=edit';
                var iframe = window.parent.document.getElementById("trigger_iframe");
                $(iframe).attr("src",url);
                $(window.parent.document.getElementById("modelingAsideOld")).css("display","block");
            };

            Com_AddEventListener(window,"load",function(){
                checkNewListView();
            })

            function checkNewListView() {
                var url = Com_Parameter.ContextPath + "sys/modeling/base/modelingAppView.do?method=checkNewListView&fdModelId="+listOption.param.fdAppModelId;
                $.ajax({
                    url: url,
                    type: "post",
                    async : false,
                    success: function (rtn) {
                        if(rtn){
                            var isExistNewListView = rtn.isExistNewListView;
                            var isExistOldListView = rtn.isExistOldListView;
                            doShow(isExistNewListView,isExistOldListView);
                        }
                    },
                    error : function(rtn){
                    }
                });
            }

            function doShow(isExistNewListView,isExistOldListView) {
                if(!isExistNewListView && !isExistOldListView){
                    //没有新列表页没有旧列表，不显示返回旧版按钮
                    $(".new_listview_tips").css("display","none");
                }

                if(!isExistNewListView && isExistOldListView){
                    //没有新列表有旧列表，显示缺省页
                    $(".listview_no_content").css("display","block");
                    $(".listview_content").css("display","none");
                    $(".new_listview_tips").css("display","none");
                }

                if(isExistNewListView && !isExistOldListView){
                    //不显示返回旧版按钮，隐藏缺省页
                    $(".new_listview_tips").css("display","none");
                }

                if(isExistNewListView && isExistOldListView){
                    //显示返回旧版按钮，隐藏缺省页
                    $(".new_listview_tips").css("display","block");
                }
            }
            function useNewVersion() {
                $(".listview_no_content").css("display","none");
                $(".listview_content").css("display","block");
                $(".new_listview_tips").css("display","block");
            }
        </script>
    </template:replace>
</template:include>