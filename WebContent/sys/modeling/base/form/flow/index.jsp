<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ include file="/sys/modeling/base/modeling_head.jsp" %>

<template:include file="/sys/profile/resource/template/list.jsp">
	<template:replace name="content">
		<link type="text/css" rel="stylesheet"
			  href="${KMSS_Parameter_ContextPath}sys/modeling/base/resources/css/modeling.css?s_cache=${LUI_Cache}"/>
		<div class="lui_modeling">
			<div class="lui_modeling_main aside_main" >
				<div style="margin:5px 10px;">
			<!-- 筛选器 -->
			<list:criteria id="criteria">
			     <list:cri-ref key="fdName" ref="criterion.sys.docSubject" title="${lfn:message('sys-modeling-base:modeling.flow.fdName')}">
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
								<list:sort property="fdName" text="${ lfn:message('sys-modeling-base:modeling.flow.fdName') }" group="sort.list"></list:sort>
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
						<ui:toolbar id="Btntoolbar" count="3">
							<ui:button text="${lfn:message('button.add')}"  onclick="add();" order="1" ></ui:button>
							<%--#160653 【流程设计】复制流程模板，与系统其它列表视图保持一致--%>
							<ui:button text="${lfn:message('sys-modeling-base:modeling.flow.copy.template')}" onclick="copyFlow();" order="2" ></ui:button>
							<ui:button text="${lfn:message('button.deleteall')}" onclick="deleteAll();" order="3" ></ui:button>
						</ui:toolbar>
					</div>
				</div>
			</div>
			<ui:fixed elem=".lui_list_operation"></ui:fixed>
				<!-- 内容列表 -->
				<list:listview>
					<ui:source type="AjaxJson">
						{url:'/sys/modeling/base/modelingAppFlow.do?method=list&fdModelId=${JsParam.fdModelId}'}
					</ui:source>
					<list:colTable isDefault="true" layout="sys.ui.listview.columntable" 
					    rowHref="/sys/modeling/base/modelingAppFlow.do?method=edit&fdId=!{fdId}">
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
									Com_OpenWindow('<c:url value="/sys/modeling/base/modelingAppFlow.do" />?method=add&fdModelId=${JsParam.fdModelId}');
								}else{
									dialog.alert("${lfn:message('sys-modeling-base:modeling.flow.validateForm')}")								
								}
							}
					   });
		 		};
		 	    // 编辑
		 		window.edit = function(id) {
			 		if(id)
		 				Com_OpenWindow('<c:url value="/sys/modeling/base/modelingAppFlow.do" />?method=edit&fdId=' + id);
		 		};

		 		window.deleteDoc = function(id){
		 			var url = '<c:url value="/sys/modeling/base/modelingAppFlow.do?method=delete"/>';
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
					var url = '<c:url value="/sys/modeling/base/modelingAppFlow.do?method=deleteall"/>&fdModelId=${JsParam.fdModelId}';
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

		 		window.copyFlow = function (){
                    var values = [];
                    $("input[name='List_Selected']:checked").each(function() {
                       values.push($(this).val());
                    });
                    if(values.length==0){
                        dialog.alert('<bean:message key="page.noSelect"/>');
                        return;
                    }
                    var url = '<c:url value="/sys/modeling/base/modelingAppFlow.do?method=copyFlow"/>&fdModelId=${JsParam.fdModelId}';
                    dialog.confirm('${lfn:message('sys-modeling-base:modeling.flow.sure.copy.template')}',function(value){
                        if(value==true){
                        	console.log("values",values);
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
                }
				/**
				 * 关联弹框
				 */
				var relatedHandler = function(datas){
					console.log(datas);
					var url='/sys/modeling/base/listview/config/dialog_relation.jsp';
					dialog.iframe(url, '删除关联模块', function(data){
					},{
						width : 600,
						height : 400,
						params : { datas : datas }
					});
				}
				window.delCallback = function(data){
					if(window.del_load!=null){
						window.del_load.hide();
						topic.publish("list.refresh");
					}
					if(data.status == false){
						if(data.err && data.err == 'related'){
							//删除失败，存在关联的数据
							relatedHandler(data.datas);
							return;
						}
					}
					dialog.result(data);
				};

				// 监听新建更新等成功后刷新
				topic.subscribe('successReloadPage', function() {
					topic.publish('list.refresh');
				});


		 	});
	 	</script>
		</div></div>
	</template:replace>
</template:include>