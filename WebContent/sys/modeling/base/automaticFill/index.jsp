<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ include file="/sys/modeling/base/modeling_head.jsp" %>
<template:include ref="config.profile.list">
	<template:replace name="content">
		<div style="margin:5px 10px;">
			<!-- 筛选器 -->
			<list:criteria id="criteria">
			     <list:cri-ref key="fdName" ref="criterion.sys.docSubject" title="${lfn:message('sys-modeling-base:modeling.dataValidate.fdName')}">
				</list:cri-ref>
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
						    <list:sortgroup>
								<list:sort property="docCreateTime" text="${lfn:message('model.fdCreateTime') }" group="sort.list"></list:sort>
								<list:sort property="fdName" text="${ lfn:message('sys-modeling-base:modeling.dataValidate.fdName') }" group="sort.list"></list:sort>
							</list:sortgroup>
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
						<kmss:auth requestURL="/sys/modeling/base/modelingAutomaticFill.do?method=add&fdModelId=${param.fdModelId}">
							<!-- 增加 -->
							<ui:button text="${lfn:message('button.add')}" onclick="add()" order="1" ></ui:button>
						</kmss:auth>
						<kmss:auth requestURL="/sys/modeling/base/modelingAutomaticFill.do?method=deleteall&fdModelId=${param.fdModelId}">
							<!-- 删除 -->
							<ui:button text="${lfn:message('button.deleteall')}" onclick="del()" order="2" ></ui:button>
						</kmss:auth>
					</div>
				</div>
			</div>
			<ui:fixed elem=".lui_list_operation"></ui:fixed>
				<!-- 内容列表 -->
				<list:listview>
					<ui:source type="AjaxJson">
						{url:'/sys/modeling/base/modelingAutomaticFill.do?method=list&fdModelId=${JsParam.fdModelId}'}
					</ui:source>
					<list:colTable isDefault="true" layout="sys.ui.listview.columntable" onRowClick="edit('!{fdId}')">
						<list:col-checkbox></list:col-checkbox>
						<list:col-auto></list:col-auto>
					</list:colTable>
					<ui:event topic="list.loaded">
						Dropdown.init();
					</ui:event>
				</list:listview>
				<!-- 分页 -->
			 	<list:paging/>
		</div>


		<script type="text/javascript">
			var listOption = {
				contextPath: '${LUI_ContextPath}',
				jPath: 'automaticFill',
				modelName: 'com.landray.kmss.sys.modeling.base.formDesign.model.ModelingAutomaticFill',
				templateName: '',
				basePath: '/sys/modeling/base/modelingAutomaticFill.do',
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
			seajs.use(['lui/jquery', 'lui/dialog', 'lui/topic'], function($, dialog, topic) {

		 		topic.subscribe('successReloadPage', function() {
					topic.publish("list.refresh");
				});
			 	// 增加
		 		window.add = function() {
					var url = '${LUI_ContextPath}/sys/modeling/base/modelingAutomaticFill.do?method=add&fdModelId=${JsParam.fdModelId}';
					console.log(url)
					var iframe = window.parent.document.getElementById("cfg_iframe");
					$(iframe).attr("src",url);
					//修改样式
					$(iframe).css("height","1200px");
					$(iframe).parents(".lui_modeling_main.aside_main").eq(0).css("padding-top","0px");
					$(iframe).parents(".lui_modeling_main.aside_main").eq(0).parents(".lui_modeling").eq(0).find("#modelingAside").css("display","none");

				};

		 	    // 编辑
		 		window.edit = function(id) {
			 		if(id){
				 		var url = '${LUI_ContextPath}/sys/modeling/base/modelingAutomaticFill.do?method=edit&fdId=' + id;
						var iframe = window.parent.document.getElementById("cfg_iframe");
						$(iframe).attr("src", url);
						$(iframe).css("height","800px");
						$(iframe).parents(".lui_modeling_main.aside_main").eq(0).css("padding-top","0px");
						$(iframe).parents(".lui_modeling_main.aside_main").eq(0).parents(".lui_modeling").eq(0).find("#modelingAside").css("display","none");
						$(iframe).parents(".lui-mulit-zh-cn-html").eq(0).css('overflow','hidden');
					}
		 		};

		 		window.deleteDoc = function(id){
		 			var url = '${LUI_ContextPath}/sys/modeling/base/modelingAutomaticFill.do?method=delete"/>';
					dialog.confirm('<bean:message key="page.comfirmDelete"/>',function(value){
						if(value==true){
							window.del_load = dialog.loading();
							$.ajax({
								url: url,
								type: 'GET',
								data:{fdId:id},
								dataType: 'json',
								error: function(data){
									if(window.del_load!=null){
										window.del_load.hide();
									}
									dialog.result(data.responseJSON);
								},
								success: delCallback
						   });
						}
					});
		 		};


		 		window.deleteAll = function(id){
					var values = [];
					if(id) {
		 				values.push(id);
			 		} else {
						$("input[name='List_Selected']:checked").each(function() {
							values.push($(this).val());
						});
			 		}
					if(values.length==0){
						dialog.alert('<bean:message key="page.noSelect"/>');
						return;
					}
					var url = '${LUI_ContextPath}/sys/modeling/base/modelingAutomaticFill.do?method=deleteall&fdModelId=${JsParam.fdModelId}';
					dialog.confirm('<bean:message key="page.comfirmDelete"/>',function(value){
						if(value==true){
							window.del_load = dialog.loading();
							$.ajax({
								url: url,
								type: 'POST',
								data:$.param({"List_Selected":values},true),
								dataType: 'json',
								error: function(data){
									if(window.del_load!=null){
										window.del_load.hide();
									}
									dialog.result(data.responseJSON);
								},
								success: delCallback
						   });
						}
					});
				};
				window.delCallback = function(data){
					if(window.del_load!=null){
						window.del_load.hide();
						topic.publish("list.refresh");
					}
					dialog.result(data);
				};
				// 监听新建更新等成功后刷新
				topic.subscribe('successReloadPage', function() {
					topic.publish('list.refresh');
				});

				seajs.use(["lui/jquery", "sys/ui/js/dialog","lui/topic"], function ($, dialog,topic) {
                    topic.subscribe("list.loaded",function(){
                        var bodyHeight = $(document.body).outerHeight(true)+70;
                        $("body",parent.document).find('#cfg_iframe').animate({
                        	height : bodyHeight
                        },"fast");
                    });
                });
		 	});
	 	</script>
		</div></div>
	</template:replace>
</template:include>