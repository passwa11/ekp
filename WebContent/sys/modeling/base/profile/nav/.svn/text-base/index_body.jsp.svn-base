<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ include file="/sys/modeling/base/modeling_head.jsp" %>
<template:include ref="config.profile.list">
    <template:replace name="content">
		<style>
			.txtlistpath{
				display: none;
			}
		</style>
	<!-- 筛选器 -->
	<list:criteria id="criteria1">
		<list:cri-ref key="docSubject" ref="criterion.sys.docSubject" title="${lfn:message('sys-modeling-base:modelingAppNav.docSubject') }"></list:cri-ref>
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
					<list:sort property="fdOrder" text="${lfn:message('sys-modeling-base:modelingAppNav.fdOrder') }" group="sort.list" value="up"></list:sort>
					<list:sort property="docSubject" text="${lfn:message('sys-modeling-base:modelingAppNav.docSubject') }" group="sort.list" ></list:sort>
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
					<kmss:auth requestURL="/sys/modeling/base/modelingAppNav.do?method=add&fdAppId=${param.fdAppId}">
						<!-- 新建 -->
						<ui:button text="${lfn:message('button.add')}" onclick="addNav()" order="1" ></ui:button>

						<!-- 复制导航 -->
						<ui:button text="${lfn:message('sys-modeling-base:modelingAppNav.button.copyNavs')}" onclick="copyNav()" order="2" ></ui:button>
					</kmss:auth>
					<kmss:auth requestURL="/sys/modeling/base/modelingAppNav.do?method=deleteall&fdAppId=${param.fdAppId}">
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
			{url:'/sys/modeling/base/modelingAppNav.do?method=list&fdAppId=${param.fdAppId}'}
		</ui:source>
		<list:colTable isDefault="true" layout="sys.ui.listview.columntable" onRowClick="edit('!{fdId}')" >
			<list:col-checkbox></list:col-checkbox>
			<list:col-auto props="fdOrder,docSubject,docCreator,docCreateTime,operations"></list:col-auto>
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
       			fdAppId : '${param.fdAppId}'
       		},
            contextPath: '${LUI_ContextPath}',
            modelName: 'com.landray.kmss.sys.modeling.base.profile.model.ModelingAppNav',
            templateName: '',
            basePath: '/sys/modeling/base/modelingAppNav.do',
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
				comfirmCopyNav:'${lfn:message("sys-modeling-base:modelingAppNav.copyNavsTitle")}'
            }
        };
    	Com_IncludeFile("list.js", "${LUI_ContextPath}/sys/modeling/base/resources/js/", 'js', true);
	 	seajs.use(['lui/jquery', 'lui/dialog', 'lui/topic'], function($, dialog, topic) {
	 		// 监听新建更新等成功后刷新
			topic.subscribe('successReloadPage', function() {
				topic.publish ("list.refresh");
			});
			topic.subscribe("list.loaded",function(){
                var bodyHeight = $(parent.parent.document).find(".model-body-wrap-iframe").eq(0).outerHeight(true)-75;
                $("body",parent.document).find('#trigger_iframe').height(bodyHeight);
                $("body",parent.document).find("#modelingAside").css("display","block");
                $("body",parent.document).find(".lui_modeling_main.aside_main").eq(0).css("padding-top","10px");
            });
			// 新建
	 		window.addNav = function() {
		 		/* var url = '${LUI_ContextPath}/sys/modeling/base/modelingAppNav.do?method=add&fdAppId=' + listOption.param.fdAppId;
	    		var iframe = window.parent.document.getElementById("trigger_iframe");
	    		$(iframe).attr("src",url);
	    		//修改样式
				var height=$(iframe).parents('body').height()-1;
				$(iframe).height(height)
	    		$(iframe).parents(".lui_modeling_main.aside_main").eq(0).css("padding-top","0px");
	    		$(iframe).parents(".lui_modeling_main.aside_main").eq(0).parents(".lui_modeling").eq(0).find("#modelingAside").css("display","none"); */
	 			 var url = "/sys/modeling/base/modelingAppNav.do?method=add&type=createNav&fdAppId=" + listOption.param.fdAppId;
				 dialog.iframe(url, "${lfn:message("sys-modeling-base:modeling.baseinfo.BasicInformation")}", function (rt) {
					 if(rt.type == 'success' && rt.fdId){
						var url = '${LUI_ContextPath}/sys/modeling/base/modelingAppNav.do?method=edit&fdAppId=' + listOption.param.fdAppId + "&fdId=" + rt.fdId; 
			    		var iframe = window.parent.document.getElementById("trigger_iframe");
			    		$(iframe).attr("src",url);
			    		//修改样式
						var height=$(iframe).parents('body').height()-15;
						$(iframe).height(height)
			    		$(iframe).parents(".lui_modeling_main.aside_main").eq(0).css("padding-top","0px");
			    		$(iframe).parents(".lui_modeling_main.aside_main").eq(0).parents(".lui_modeling").eq(0).find("#modelingAside").css("display","none");
						 var tabTitle = window.parent.document.getElementById("space-title");
						 $(tabTitle).css("display","none");
					 }
                 }, {width: 550, height: 405, params: {formWindow: window}});
	 		};
	 		// 编辑
	 		window.edit = function(id) {
		 		if(id){
		 			var url = '${LUI_ContextPath}/sys/modeling/base/modelingAppNav.do?method=edit&fdAppId=' + listOption.param.fdAppId + '&fdId=' + id;
		 			var iframe = window.parent.document.getElementById("trigger_iframe");
		    		$(iframe).attr("src",url);
		    		//修改样式
					// var bodyHeight = $(parent.parent.document).find(".model-body-wrap-iframe").eq(0).outerHeight(true);
					var height=$(iframe).parents('body').height()-15;
					$(iframe).height(height)
		    		$(iframe).parents(".lui_modeling_main.aside_main").eq(0).css("padding-top","0px");
		    		$(iframe).parents(".lui_modeling_main.aside_main").eq(0).parents(".lui_modeling").eq(0).find("#modelingAside").css("display","none");
					var tabTitle = window.parent.document.getElementById("space-title");
					$(tabTitle).css("display","none");
		 		}
	 		};
	 		// 删除
	 		window.del = function(id) {
		 		if(id){
		 			var url = '${LUI_ContextPath}/sys/modeling/base/modelingAppNav.do?method=delete&fdId'+ id;
		 		}
	 		};

	 		//复制导航
			window.copyNav = function() {
				var selected = [];

				$("input[name='List_Selected']:checked").each(function(){
					selected.push($(this).val());
				});
				if(selected.length==0){
					dialog.alert(listOption.lang.noSelect);
					return;
				}

				dialog.confirm(listOption.lang.comfirmCopyNav,function(ok){
					if(ok==true){
						var del_load = dialog.loading();
						var param = {"List_Selected":selected};
						var hash = getValueByHash("docCategory");
						if(hash){
							param.docCategory = hash;
						}
						hash = getValueByHash("docTemplate");
						if(hash){
							param.docTemplate = hash;
						}
						$.ajax({
							url:'${LUI_ContextPath}/sys/modeling/base/modelingAppNav.do?method=copyNav',
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