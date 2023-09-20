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
						<list:sort property="fdName" text="${lfn:message('sys-modeling-base:modelingAppSpace.fdName') }" group="sort.list" ></list:sort>
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
						<kmss:auth requestURL="/sys/modeling/base/modelingAppSpace.do?method=add&fdAppId=${param.fdAppId}">
							<!-- 增加 -->
							<ui:button text="${lfn:message('button.add')}" onclick="addSpacePc()" order="1" ></ui:button>
						</kmss:auth>
						<kmss:auth requestURL="/sys/modeling/base/modelingAppSpace.do?method=deleteall&fdAppId=${param.fdAppId}">
							<!-- 删除 -->
							<ui:button text="${lfn:message('button.deleteall')}" onclick="deletePc();" order="3" ></ui:button>
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
				{url:'/sys/modeling/base/modelingAppSpace.do?method=list&fdAppId=${param.fdAppId}'}
			</ui:source>
			<list:colTable isDefault="true" layout="sys.ui.listview.columntable" onRowClick="edit('!{fdId}')" >
				<list:col-checkbox></list:col-checkbox>
				<list:col-auto props="fdName,docCreateTime,docCreator,operations"></list:col-auto>
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
				modelName: 'com.landray.kmss.sys.modeling.base.space.model.ModelingAppSpace',
				templateName: '',
				basePath: '/sys/modeling/base/modelingAppSpace.do',
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
					topic.publish("list.refresh");
				});
				topic.subscribe("list.loaded",function(){
					var bodyHeight = $(parent.parent.document).find(".model-body-wrap-iframe").eq(0).outerHeight(true)-75;
					$("body",parent.document).find('#trigger_iframe').height(bodyHeight);
					$("body",parent.document).find("#modelingAside").css("display","block");
					$("body",parent.document).find(".lui_modeling_main.aside_main").eq(0).css("padding-top","10px");
				});
				// 增加
				window.addSpacePc = function() {
					var url = '${LUI_ContextPath}/sys/modeling/base/modelingAppSpace.do?method=add&fdAppId=' + listOption.param.fdAppId;
					var iframe = window.parent.document.getElementById("trigger_iframe");
					$(iframe).attr("src",url);
					//修改样式
					var height=$(iframe).parents('body').height()-15;
					$(iframe).height(height)
					$(iframe).parents(".lui_modeling_main.aside_main").eq(0).css("padding-top","0px");
					$(iframe).parents(".lui_modeling_main.aside_main").eq(0).parents(".lui_modeling").eq(0).find("#modelingAside").css("display","none");
					var tabTitle = window.parent.document.getElementById("space-title");
					$(tabTitle).css("display","none");
				};
				// 编辑
				window.edit = function(id) {
					if(id){
						var url = '${LUI_ContextPath}/sys/modeling/base/modelingAppSpace.do?method=edit&fdAppId=' + listOption.param.fdAppId + '&fdId=' + id;
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
				};
				//批量删除
				window.deletePc = function(id){
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
					//检查所选的PC首页是否被业务导航引用，否则删除失败 提示用户
					var checkUrl = '<c:url value="/sys/modeling/base/modelingAppSpace.do?method=checkPcDelete"/>&fdAppId=' + listOption.param.fdAppId + '';
					$.ajax({
						url: checkUrl,
						type: 'POST',
						data:$.param({"List_Selected":values},true),
						dataType: 'json',
						error: function(data){
							if(window.del_load!=null){
								window.del_load.hide();
							}
							dialog.result(data.responseJSON);
						},
						success: function(data){
							if(data){
								var url = '<c:url value="/sys/modeling/base/modelingAppSpace.do?method=deleteall"/>';
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
							}else{
								dialog.failure('${lfn:message('sys-modeling-base:modelingAppSpace.pc.delete.tips')}');
							}
						}
					});
				};
				function delCallback(data){
					if(window.del_load!=null)
						window.del_load.hide();
					if(data!=null && data.status==true){
						topic.publish("list.refresh");
						dialog.success('${lfn:message("return.optSuccess")}');
					}else{
						dialog.failure('${lfn:message("return.optFailure")}');
					}
				};
			});
		</script>
	</template:replace>
</template:include>