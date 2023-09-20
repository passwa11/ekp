<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
		<div class="lui_list_operation">
			<!-- 全选 -->
			<div class="lui_list_operation_order_btn">
				<list:selectall></list:selectall>
			</div>
			<!-- 分页 -->
			<div class="lui_list_operation_page_top">	
				<list:paging layout="sys.ui.paging.top" > 		
				</list:paging>
			</div>
			<div style="float:right">
				<div style="display: inline-block;vertical-align: middle;">
					<ui:toolbar count="10">
							<kmss:auth
								requestURL="/sys/recycle/sys_recycle_doc/sysRecycle.do?method=hardDeleteAll&categoryId=${param.categoryId}&nodeType=${param.nodeType}&modelName=${param.modelName}"
								requestMethod="GET">								
								<ui:button text="${lfn:message('sys-recycle:button.hardDeleteAll')}" onclick="hardDeleteAll()" order="1"></ui:button>
							</kmss:auth>
							
							<kmss:auth
								requestURL="/sys/recycle/sys_recycle_doc/sysRecycle.do?method=recoverAll&categoryId=${param.categoryId}&nodeType=${param.nodeType}&modelName=${param.modelName}"
								requestMethod="GET">								
								<ui:button text="${lfn:message('sys-recycle:button.recoverAll')}" onclick="recoverAll()" order="6"></ui:button>
							</kmss:auth>
					</ui:toolbar>
				</div>
			</div>
		</div>
		
		<ui:fixed elem=".lui_list_operation"></ui:fixed>
		
		<list:listview id="listview">
			<ui:source type="AjaxJson">
					{url:'/sys/recycle/sys_recycle_doc/sysRecycle.do?method=listDoc&modelName=${JsParam.modelName}&categoryId=${JsParam.categoryId}&nodeType=${JsParam.nodeType}${moreParam}'}
			</ui:source>
			  <!-- 列表视图 -->	
			<list:colTable isDefault="false" layout="sys.ui.listview.columntable" 
				rowHref="/sys/recycle/sys_recycle_doc/sysRecycle.do?method=redirect&modelId=!{fdModelId}&modelName=${JsParam.modelName}"
				name="columntable">
				<list:col-checkbox></list:col-checkbox>
				<list:col-serial></list:col-serial> 
				
				<list:col-auto props="docSubject,operator.fdName,fdOptDate,fdOptType"></list:col-auto>
			</list:colTable>
			  <!-- 摘要视图 -->	
		</list:listview> 
		 
	 	<list:paging></list:paging>	 
		
	 	<script type="text/javascript">
			seajs.use(['lui/jquery','lui/dialog','lui/topic'], function($, dialog , topic) {
				// 永久删除
				window.hardDeleteAll = function() {
					__ajax('<bean:message bundle="sys-recycle" key="page.comfirmHardDelete"/>', 
							'<c:url value="/sys/recycle/sys_recycle_doc/sysRecycle.do?method=hardDeleteAll"/>&categoryId=${JsParam.categoryId}&modelName=${JsParam.modelName}');
				};
				
				// 还原
				window.recoverAll = function() {
					__ajax('<bean:message bundle="sys-recycle" key="page.comfirmRecover"/>', 
							'<c:url value="/sys/recycle/sys_recycle_doc/sysRecycle.do?method=recoverAll"/>&categoryId=${JsParam.categoryId}&modelName=${JsParam.modelName}');
				}
				
				window.__ajax = function(msg, url) {
					var values = [];
					$("input[name='List_Selected']:checked").each(function(){
							values.push($(this).val());
						});
					if(values.length==0){
						dialog.alert('<bean:message key="page.noSelect"/>');
						return;
					}
					dialog.confirm(msg, function(value){
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
				}
				
				window.delCallback = function(data){
					if(window.del_load!=null){
						window.del_load.hide();
						topic.publish("list.refresh");
					}
					dialog.result(data);
				};
			});
		</script>	 