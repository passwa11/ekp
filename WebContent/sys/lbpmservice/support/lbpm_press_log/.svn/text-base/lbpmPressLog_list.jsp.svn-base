<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include file="/sys/profile/resource/template/list.jsp">
	<template:replace name="head">
		<link type="text/css" rel="stylesheet" href="<c:url value="/sys/lbpmmonitor/sys_lbpm_monitor_flow/css/common.css"/>"/>
	</template:replace>
	<template:replace name="title">
		<c:out value="${ lfn:message('sys-lbpmservice-support:module.sys.lbpmservice.support') }"></c:out>
	</template:replace>
	<template:replace name="content">
			<!-- 查询条件  -->
		<list:criteria id="criteria1">
			
			<list:cri-ref ref="criterion.sys.person" 
							  key="docCreator" multi="false"
							  title="${lfn:message('sys-lbpmservice-support:lbpmPressLog.docCreator') }" />
							  
			<list:cri-criterion title="${ lfn:message('sys-lbpmservice-support:lbpmPressLog.fdActionKey')}" key="fdActionKey" multi="false"> 
				<list:box-select>
					<list:item-select >
						<ui:source type="Static">
							[{text:'${ lfn:message('sys-lbpmservice-support:lbpmPressLog.fdActionKey.draftTypeName')}', value:'draftPress'},
							{text:'${ lfn:message('sys-lbpmservice-support:lbpmPressLog.fdActionKey.reviewTypeName')}',value:'reviewPress'}]
						</ui:source>
					</list:item-select>
				</list:box-select>
			</list:cri-criterion>
							  
			<list:cri-auto modelName="com.landray.kmss.sys.lbpmservice.support.model.LbpmPressLog" 
				property="docCreateTime;" />
		</list:criteria>
		 
		 
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
								<list:sort property="docCreateTime" text="${ lfn:message('sys-lbpmservice-support:lbpmPressLog.docCreateTime') }" group="sort.list" value="down"></list:sort>
							</list:sortgroup>
						</ui:toolbar>
					</div>
				</div>
				
				<!-- 分页 -->
				<div class="lui_list_operation_page_top">
					<%@ include file="/sys/profile/showconfig/showConfig_paging_top.jsp" %>
				</div>
				
				<div style="float:right">
					<div style="display: inline-block;vertical-align: middle;">
						<ui:toolbar id="Btntoolbar">
							<kmss:auth requestURL="/sys/lbpmservice/support/lbpm_press_log/lbpmPressLog.do?method=deleteall">					 								
								<ui:button text="${lfn:message('button.deleteall')}" onclick="delDoc()" order="4"></ui:button>
							</kmss:auth>
						</ui:toolbar>
					</div>
				</div>
			</div>
			<ui:fixed elem=".lui_list_operation" id="hack_fix"></ui:fixed>
		
		 
		 
	 	<list:listview id="listview">
			<ui:source type="AjaxJson">
					{url:'/sys/lbpmservice/support/lbpm_press_log/lbpmPressLog.do?method=data&categoryId=${param.categoryId}'}
			</ui:source>
			
			<!-- 列表视图 -->	
			<list:colTable isDefault="false" name="columntable">
				<list:col-checkbox></list:col-checkbox>
				<list:col-serial></list:col-serial>
				<list:col-auto props="docCreateTime;docCreator.fdName;fdAction;fdPressed.fdName,operations"></list:col-auto>
			</list:colTable>
		</list:listview> 
		 
		 <br>
		<%@ include file="/sys/profile/showconfig/showConfig_paging_buttom.jsp"%>
	 	

	      	<script type="text/javascript">
	      	
	    	seajs.use(['lui/jquery','lui/dialog','lui/topic','lui/toolbar'], function($, dialog , topic, toolbar) {

	    		/**------ 取消关注我的报表  ------**/
	    	 	window.deleteLbpmPress = function(id){
	    			dialog.confirm('${ lfn:message('page.comfirmDelete.newVersion') }',function(isOk){
	    				if(isOk){
	    					window.del_load = dialog.loading();
	    					var attentionUrl = '<c:url value="/sys/lbpmservice/support/lbpm_press_log/lbpmPressLog.do?method=delete&fdId="/>'+id;
	    					$.ajax({
	    						url: attentionUrl,
	    						type: "GET",
	    						dataType: "json",
	    						success: delCallback,
	    						error: delErrorCallback
	    					});
	    				}
	    			});			 		
	    	 	};
	    	 	
	    	 	 /**------ 删除成功回调函数  ------**/
	            window.delCallback = function(data){
	                if(window.del_load!=null){
	                    window.del_load.hide();
	                }
	                topic.publish("list.refresh");
	                dialog.result(data);
	            };

	            /**------ 删除失败回调函数  ------**/
	            window.delErrorCallback = function(data){
	                var messages=data.responseJSON.message;
	                var message=messages[0];
	                if(window.del_load!=null){
	                    window.del_load.hide();
	                }
	                dialog.alert(message.msg);
	                topic.publish("list.refresh");
	            }
	            
	    	 	
				//删除
				window.delDoc = function(id){
					var values = [];
					if(id) {
						values.push(id);
					} else {
						$("input[name='List_Selected']:checked").each(function(){
							values.push($(this).val());
						});
					}
					if(values.length==0){
						dialog.alert('<bean:message key="page.noSelect"/>');
						return;
					}
					dialog.confirm('<bean:message key="page.comfirmDelete"/>',function(value){
						if(value==true){
							window.del_load = dialog.loading();
							$.post('<c:url value="/sys/lbpmservice/support/lbpm_press_log/lbpmPressLog.do?method=deleteall"/>',
									$.param({"List_Selected":values},true),delCallback,'json');
						}
					});
				};
				//删除回调	
				window.delCallback = function(data){
					if(window.del_load!=null)
						window.del_load.hide();
					if(data!=null && data.status==true){
						topic.publish("list.refresh");
						dialog.success('<bean:message key="return.optSuccess" />');
					}else{
						dialog.failure('<bean:message key="return.optFailure" />');
					}
				};
			});
	    	
	      	</script>
	
	
	</template:replace>
</template:include>


