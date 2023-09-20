<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<%@ include file="/sys/modeling/base/modeling_head.jsp" %>

<template:include ref="config.profile.list">
    <template:replace name="content">
     <!--    <div style="margin:5px 10px;"> -->
            <!-- 筛选 -->
            <list:criteria id="criteria1">
                <list:cri-ref key="fdName" ref="criterion.sys.docSubject" title="${lfn:message('sys-modeling-base:modelingImportProperty.fdName')}"/>
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
                                <list:sort property="modelingPortletCfg.docCreateTime" text="${lfn:message('model.fdCreateTime') }" group="sort.list"></list:sort>
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
                            <kmss:auth requestURL="/sys/modeling/base/modelingPortletCfg.do?method=add">
                                <ui:button text="${lfn:message('button.add')}" onclick="addCfg()" order="1"/>
                                <ui:button text="${lfn:message('sys-modeling-base:modelingCollectionView.button.copyViews')}" onclick="copyView()" order="2"/>
                            </kmss:auth>
                            <kmss:auth requestURL="/sys/modeling/base/modelingPortletCfg.do?method=deleteall">
                                <c:set var="canDelete" value="true"/>
                            </kmss:auth>
                            <!---->
                            <ui:button text="${lfn:message('button.deleteall')}" onclick="deleteAll()" order="4"
                                       id="btnDelete"/>
                        </ui:toolbar>
                    </div>
                </div>
            </div>
      <!--   </div> -->
        <ui:fixed elem=".lui_list_operation"/>
        <!-- 列表 -->
        <list:listview id="listview">
            <ui:source type="AjaxJson">
                {url:appendQueryParameter('/sys/modeling/base/modelingPortletCfg.do?method=data&fdModelId=${param.fdModelId }&fdDevice=${param.fdDevice }')}
            </ui:source>
            <!-- 列表视图 -->
            <list:colTable isDefault="false" onRowClick="edit('!{fdId}')" name="columntable">
                <list:col-checkbox/>
                <list:col-serial/>
                <list:col-auto props="fdName;fdFormat;docCreateTime;docCreator.name;" url=""/></list:colTable>
        </list:listview>
        <!-- 翻页 -->
        </div>
        <list:paging/>

        <script>
            var listOption = {
                contextPath: '${LUI_ContextPath}',
                modelName: 'com.landray.kmss.sys.modeling.base.model.ModelingPortletCfg',
                templateName: '',
                basePath: '/sys/modeling/base/modelingPortletCfg.do',
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
                    comfirmCopyTreeView:'${lfn:message("sys-modeling-base:modelingCollectionView.copyViewsTitle")}'
                }

            };

            Com_IncludeFile("list.js", "${LUI_ContextPath}/sys/modeling/base/resources/js/", 'js', true);

            /* function addCfg() {
                console.log(listOption.param)
                openPageInDialog(listOption.basePath + "?method=add&fdModelId=${param.fdModelId }&fdDevice=${param.fdDevice }","新建portlet视图");
            }

            function edit(id) {
                openPageInDialog(listOption.basePath + "?method=edit&fdId=" + id, "编辑portlet视图");
            } */


            seajs.use(["lui/jquery", "sys/ui/js/dialog","lui/topic"], function ($, dialog,topic) {
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
                
                window.addCfg = function() {
                    //openPageInDialog(listOption.basePath + "?method=add&fdModelId=${param.fdModelId }&fdDevice=${param.fdDevice }","新建portlet视图");
                    var url = listOption.basePath + "?method=add&fdModelId=${param.fdModelId }&fdDevice=${param.fdDevice }";
                    //var title = "新建门户部件";
                    var title='${lfn:message('sys-modeling-base:add.portal.part')}';
                    dialog.iframe(url, title, function(data){
    	    			//回调
    	    			topic.publish('list.refresh');
    	    		}, {width:1010,height:600});
                }

                window.edit = function(id) {
                   // openPageInDialog(listOption.basePath + "?method=edit&fdId=" + id, "编辑portlet视图");
                    var url = listOption.basePath + "?method=edit&fdId=" + id;
                   // var title = "编辑门户部件";
                    var title='${lfn:message('sys-modeling-base:edit.portal.part')}';
                    dialog.iframe(url, title, function(data){
    	    			//回调
    	    			topic.publish('list.refresh');
    	    		}, { width: 1010,
                        height: 648,});
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

                    dialog.confirm(listOption.lang.comfirmCopyTreeView,function(ok){

                        if(ok==true){
                            var del_load = dialog.loading();
                            var param = {"List_Selected":selected};
                            $.ajax({
                                url:'${LUI_ContextPath}/sys/modeling/base/modelingPortletCfg.do?method=copyView',
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