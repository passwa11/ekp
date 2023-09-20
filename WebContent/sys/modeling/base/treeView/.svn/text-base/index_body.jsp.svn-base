<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ include file="/sys/modeling/base/modeling_head.jsp" %>
<template:include ref="config.profile.list">
	<template:replace name="head">
	</template:replace>
	<template:replace name="content">
	<!-- 筛选器 -->
	<list:criteria id="criteria1">
		<list:cri-ref key="fdName" ref="criterion.sys.docSubject" title="${lfn:message('sys-modeling-base:modelingAppListview.fdName') }"></list:cri-ref>
		<list:cri-auto modelName="com.landray.kmss.sys.modeling.base.treeview.model.ModelingTreeView"
					   property="docCreateTime"/>
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
				<ui:toolbar layout="sys.ui.toolbar.sort" style="float:left" >
					<list:sort property="docCreateTime" text="${lfn:message('model.fdCreateTime') }"
							   group="sort.list" value="down"></list:sort>
					<list:sort property="fdName" text="${lfn:message('sys-modeling-base:modelingAppListview.fdName') }" group="sort.list" ></list:sort>
				</ui:toolbar>
			</div>
		</div>
		<!-- 分页 -->
		<div class="lui_list_operation_page_top">
			<list:paging layout="sys.ui.paging.top" > 		
			</list:paging>
		</div>
		<!-- 操作按钮 -->
		<div style="float:right">
			<div style="display: inline-block;vertical-align: middle;">
				<ui:toolbar id="Btntoolbar">
					<kmss:auth requestURL="/sys/modeling/base/modelingTreeView.do?method=add&fdModelId=${param.fdModelId}">
						<!-- 增加 -->
						<ui:button text="${lfn:message('button.add')}" onclick="addListview()" order="1" ></ui:button>
						<ui:button text="${lfn:message('sys-modeling-base:modelingCollectionView.button.copyViews')}" onclick="copyView()" order="2" ></ui:button>
					</kmss:auth>
					<kmss:auth requestURL="/sys/modeling/base/modelingTreeView.do?method=deleteall&fdModelId=${param.fdModelId}">
						<!-- 删除 -->
						<ui:button text="${lfn:message('button.deleteall')}" onclick="del()" order="3" ></ui:button>
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
			{url:'/sys/modeling/base/modelingTreeView.do?method=list&fdModelId=${param.fdModelId}'}
		</ui:source>
		<list:colTable isDefault="true" layout="sys.ui.listview.columntable" onRowClick="edit('!{fdId}')" >
			<list:col-checkbox></list:col-checkbox>
			<list:col-serial/>
			<list:col-auto props="fdName,docCreateTime,docCreator.name"></list:col-auto>
		</list:colTable>
		<ui:event topic="list.loaded">
			Dropdown.init();
		</ui:event>
	</list:listview>
	<!-- 分页 -->
 	<list:paging/>
 	
 	<script type="text/javascript">
    	var listOption = {
       		param : {
       			fdAppModelId : '${param.fdModelId}'
       		},
            contextPath: '${LUI_ContextPath}',
            modelName: 'com.landray.kmss.sys.modeling.base.model.ModelingTreeView',
            templateName: '',
            basePath: '/sys/modeling/base/modelingTreeView.do',
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
                dialogTitle : '${lfn:message("sys-modeling-base:modelingAppListview.editDialogTitle")}',
                relatedDialogTitle : '${lfn:message("sys-modeling-base:modelingAppListview.relatedDialogTitle")}',
				comfirmCopyTreeView:'${lfn:message("sys-modeling-base:modelingCollectionView.copyViewsTitle")}'
            }
        };
    	Com_IncludeFile("list.js", "${LUI_ContextPath}/sys/modeling/base/resources/js/", 'js', true);
	 	seajs.use(['lui/jquery', 'lui/dialog', 'lui/topic'], function($, dialog, topic) {
	 		// 监听新建更新等成功后刷新
			topic.subscribe('successReloadPage', function() {
				topic.publish("list.refresh");
			});
			topic.subscribe("list.loaded",function(){
                var bodyHeight = $(parent.parent.document).find(".model-body-wrap-iframe").eq(0).outerHeight(true)-75;
                $("body",parent.document).find('#trigger_iframe').height(bodyHeight);
                $("body",parent.document).find("#modelingAside").css("display","block");
                $("body",parent.document).find(".lui_modeling_main.aside_main").eq(0).css("padding-top","10px");
            });
			// 增加
	 		window.addListview = function() {
		 		var url = '${LUI_ContextPath}/sys/modeling/base/modelingTreeView.do?method=add&fdModelId=' + listOption.param.fdAppModelId + '&fdMobile=' + listOption.param.fdMobile;
	    		var iframe = window.parent.document.getElementById("trigger_iframe");
	    		$(iframe).attr("src",url);
	    		//修改样式
	    		$(iframe).parents(".lui_modeling_main.aside_main").eq(0).css("padding-top","0px");
	    		$(iframe).parents(".lui_modeling_main.aside_main").eq(0).parents(".lui_modeling").eq(0).find("#modelingAside").css("display","none");
	 		};
	 		// 编辑
	 		window.edit = function(id) {
		 		if(id){
		 			var url = '${LUI_ContextPath}/sys/modeling/base/modelingTreeView.do?method=edit&fdModelId=' + listOption.param.fdAppModelId + '&fdId=' + id;
		 			var iframe = window.parent.document.getElementById("trigger_iframe");
		    		$(iframe).attr("src",url);
		    		//修改样式
		    		$(iframe).parents(".lui_modeling_main.aside_main").eq(0).css("padding-top","0px");
		    		$(iframe).parents(".lui_modeling_main.aside_main").eq(0).parents(".lui_modeling").eq(0).find("#modelingAside").css("display","none");
		 		}
	 		};

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
							url:'${LUI_ContextPath}/sys/modeling/base/modelingTreeView.do?method=copyView',
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