<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>

		<!-- 筛选器 -->
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
						<list:sort property="fdName" text="${lfn:message('sys-modeling-base:modeling.model.sortfdName') }" group="sort.list"  value="down"></list:sort>
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
						<ui:toolbar id="Btntoolbar">						
						  	<kmss:authShow roles="ROLE_MODELING_SETTING">
							 	<ui:button text="${lfn:message('sys-modeling-auth:sysModelingAuth.BatchSettingPermissions') }" id="batchSet" onclick="doBatchSetting();" order="1" ></ui:button>
							 	<ui:button text="${lfn:message('sys-modeling-auth:sysModelingAuth.OperationAuthorityPublishing') }" id="publishSet" onclick="publishAuth();" order="2" ></ui:button>
							</kmss:authShow>
						</ui:toolbar>
					</div>
				</div>

		</div>
		<ui:fixed elem=".lui_list_operation"></ui:fixed>
		<!-- 内容列表 -->
		<list:listview>
			<ui:source type="AjaxJson">
				{url:'/sys/modeling/auth/sys_auth_role/sysModelingSimpleAuthRole.do?method=list&fdAppModelId=${param.fdAppModelId}'}
			</ui:source>
			<list:colTable isDefault="false" layout="sys.ui.listview.columntable">
				<list:col-checkbox></list:col-checkbox>
				<list:col-serial></list:col-serial>
				<list:col-auto props="fdOprName,fdType,fdAuthUra,operations"></list:col-auto>
			</list:colTable>
			<ui:event topic="list.loaded">
				Dropdown.init();
			</ui:event>
		</list:listview>
		<!-- 分页 -->
		</div>
		<list:paging/>
	 	
	 	<script type="text/javascript">

 		seajs.use([ 'sys/ui/js/dialog' ,'lui/topic'],function(dialog,topic) {
 			topic.subscribe('successReloadPage', function() {
 		          topic.publish("list.refresh");
 		    });
			window.doSetting = function(fdRoleId){
	 			var url='/sys/modeling/auth/sys_auth_role/sysModelingSimpleAuthRole.do?method=load&fdRoleId=' + fdRoleId;
				dialog.iframe(url,'${lfn:message("sys-modeling-auth:sysModelingAuth.OperationAuthoritySetting") }',null,{
					width : 650,
					height : 680
				});
			}

			window.publishAuth = function(){
					 $.ajax({
						 url: Com_Parameter.ContextPath + "sys/modeling/auth/sys_auth_role/sysModelingAuthRole.do?method=publish&fdAppModelId=${param.fdAppModelId}",
						 dataType : 'json',
						 type : 'post',
						 data:{ },
						 async : false,
						 success : function(data){
							 if(data.errcode==0){
								dialog.success(data.errmsg);
							}else{
								dialog.failure(data.errmsg);
							 }
							 refresh();
						 }
					 });
				}

			window.doBatchSetting = function(){

				var values = [];
				$("input[name='List_Selected']:checked").each(function(){
					values.push($(this).val());
				});
				
				if(values.length==0){
					dialog.alert('<bean:message key="page.noSelect"/>');
					return;
				}
				var fdRoleId=values.join(";");
	 			var url='/sys/modeling/auth/sys_auth_role/sysModelingSimpleAuthRole.do?method=loadBatch&fdRoleId=' + fdRoleId;
				dialog.iframe(url,"${lfn:message('sys-modeling-auth:sysModelingAuth.BatchSettingPermissions') }",null,{
					width : 550,
					height : 390
				});
			}

			function refresh(){
				topic.publish("list.refresh");
			}
			
		}); 
	 	</script>

	