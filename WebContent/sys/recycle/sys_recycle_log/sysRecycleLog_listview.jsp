<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
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
					<list:sortgroup>
					    <list:sort property="fdOptDate" text="${lfn:message('sys-recycle:sysRecycleLog.fdOptDate') }"  value="down"></list:sort>
						<list:sort property="fdOptType" text="${lfn:message('sys-recycle:sysRecycleLog.fdOptType') }" ></list:sort>
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
					<ui:toolbar count="10">
						<ui:button id="del" text="${lfn:message('button.deleteall')}" order="3" onclick="delDoc()"></ui:button>	
					</ui:toolbar>
				</div>
			</div>
		</div>
		
		<ui:fixed elem=".lui_list_operation"></ui:fixed>
		
		<list:listview id="listview">
			<ui:source type="AjaxJson">
					{url:'/sys/recycle/sys_recycle_log/sysRecycleLog.do?method=list&modelName=${JsParam.modelName}'}
			</ui:source>
			  <!-- 列表视图 -->	
			<list:colTable isDefault="false" layout="sys.ui.listview.columntable"
				rowHref="/sys/recycle/sys_recycle_doc/sysRecycle.do?method=redirect&modelId=!{fdModelId}&modelName=!{fdModelName}"
				name="columntable">
				<list:col-checkbox></list:col-checkbox>
				<list:col-serial></list:col-serial> 
				
				<list:col-auto props="docSubject,fdCreator.fdName,fdOperator.fdName,fdOperatorIp,fdOptDate,fdOptType"></list:col-auto>
			</list:colTable>
		</list:listview> 
		 
	 	<list:paging></list:paging>	 
		
	 	<script type="text/javascript">
			seajs.use(['lui/jquery','lui/dialog','lui/topic'], function($, dialog , topic) {

    			//删除
    	 		window.delDoc = function(draft){
    	 			var values = [];
    				$("input[name='List_Selected']:checked").each(function(){
    						values.push($(this).val());
    					});
    				if(values.length==0){
    					dialog.alert('<bean:message key="page.noSelect"/>');
    					return;
    				}
    				var url  = '<c:url value="/sys/recycle/sys_recycle_doc/sysRecycle.do?method=deleteall"/>';
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
				
			});
		</script>	 