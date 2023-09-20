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
						<ui:toolbar id="Btntoolbar" count="2">
							<ui:button text="${lfn:message('button.add')}"  onclick="add();" order="1" ></ui:button>
							<ui:button text="${lfn:message('button.deleteall')}" onclick="deleteAll();" order="2" ></ui:button>
						</ui:toolbar>
					</div>
				</div>
			</div>
			<ui:fixed elem=".lui_list_operation"></ui:fixed>
				<!-- 内容列表 -->
				<list:listview>
					<ui:source type="AjaxJson">
						{url:'/sys/modeling/base/modelingAppDataValidate.do?method=list&fdModelId=${JsParam.fdModelId}'}
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
			
		 	seajs.use(['lui/jquery', 'lui/dialog', 'lui/topic'], function($, dialog, topic) {
		 		
		 		topic.subscribe('successReloadPage', function() {
					topic.publish("list.refresh");
				});
		 		
			 	// 增加
		 		window.add = function() {
			 		// 新建时，先判断表单是否已经配置
			 		var url = '<c:url value="/sys/modeling/base/modelingAppFlow.do" />?method=hasXForm&fdModelId=${JsParam.fdModelId}'
			 		$.ajax({
							url: url,
							type: 'GET',
							dataType: 'json',
							success: function(rtn){
								if(rtn.hasXForm === "true"){
									var dataValidateUrl = '/sys/modeling/base/modelingAppDataValidate.do?method=add&fdModelId=${JsParam.fdModelId}';
							 		dialog.iframe(dataValidateUrl, "${lfn:message('sys-modeling-base:modeling.dataValidate.NewDataUnique') }", null, {
				                        width: 800,
				                        height: 700
				                    });
								}else{
									dialog.alert("${lfn:message('sys-modeling-base:modeling.flow.validateForm')}")								
								}
							}
					   });
		 		};
		 		
		 	    // 编辑
		 		window.edit = function(id) {
			 		if(id){
				 		var url = '/sys/modeling/base/modelingAppDataValidate.do?method=edit&fdId=' + id;
				 		dialog.iframe(url, "${lfn:message('sys-modeling-base:modeling.dataValidate.EditDataUnique') }", null, {
	                        width: 800,
	                        height: 700
	                    });
			 		}
		 		};
		 		
		 		window.deleteDoc = function(id){
		 			var url = '<c:url value="/sys/modeling/base/modelingAppDataValidate.do?method=delete"/>';
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
					var url = '<c:url value="/sys/modeling/base/modelingAppDataValidate.do?method=deleteall"/>&fdModelId=${JsParam.fdModelId}';
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