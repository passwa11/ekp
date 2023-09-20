<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include file="/sys/profile/resource/template/list.jsp">
	<template:replace name="content">
		<!-- 筛选器 -->
		<list:criteria id="criteria" expand="true">
			<list:cri-ref style="width:145px;" key="fdName" ref="criterion.sys.docSubject" title="${lfn:message('sys-mportal:sysMportalPage.fdName') }"></list:cri-ref>
			<list:cri-auto modelName="com.landray.kmss.sys.mportal.model.SysMportalPage" property="fdEnabled" />
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
						<list:sort property="fdOrder" text="${lfn:message('sys-mportal:sysMportalCard.fdOrder') }" group="sort.list" value="up"></list:sort>
						<list:sort property="fdName" text="${lfn:message('sys-mportal:sysMportalPage.fdName') }" group="sort.list" ></list:sort>
						<list:sort property="docCreateTime" text="${lfn:message('sys-mportal:sysMportalPage.docCreateTime') }" group="sort.list" ></list:sort>
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
					<ui:toolbar id="Btntoolbar">
						<kmss:auth requestURL="/sys/mportal/sys_mportal_page/sysMportalPage.do?method=add">
							<ui:button text="${ lfn:message('button.add') }"  onclick="add();"></ui:button> 
						</kmss:auth>
						<kmss:auth requestURL="/sys/mportal/sys_mportal_page/sysMportalPage.do?method=deleteall">
							<ui:button text="${ lfn:message('button.deleteall') }" onclick="deleteAll();"></ui:button>
							<!-- 快速排序 -->
							<c:import url="/sys/profile/common/change_order_num.jsp" charEncoding="UTF-8">
								<c:param name="modelName" value="com.landray.kmss.sys.mportal.model.SysMportalPage"></c:param>
								<c:param name="property" value="fdOrder"></c:param>
							</c:import>
						</kmss:auth>
						<kmss:authShow roles="ROLE_SYS_MPORTAL_ADMIN">
							<ui:button text="${ lfn:message('sys-mportal:btn.fdIsAvailable.onAll') }" onclick="enableAll()"></ui:button>
							<ui:button text="${ lfn:message('sys-mportal:btn.fdIsAvailable.offAll') }" onclick="disableAll()"></ui:button>
						</kmss:authShow>
					</ui:toolbar>
				</div>
			</div>
		</div>
	
		<ui:fixed elem=".lui_list_operation"></ui:fixed>
		
		<!-- 内容列表 -->
		<list:listview>
			<ui:source type="AjaxJson">
				{url:'/sys/mportal/sys_mportal_page/sysMportalPage.do?method=list&contentType=json'}
			</ui:source>
			<list:colTable isDefault="true" layout="sys.ui.listview.columntable"
				rowHref="" name="columntable">
				<list:col-checkbox></list:col-checkbox>
				<list:col-auto props="fdOrder,fdName,fdEnabled,docCreator.fdName,docCreateTime,operations"></list:col-auto>
			</list:colTable>
		</list:listview>
		<!-- 分页 -->
	 	<list:paging/>
	
		<script type="text/javascript">
			seajs.use(['lui/jquery','lui/topic','lui/dialog'],function($,topic,dialog){
				
				// 监听新建更新等成功后刷新
				topic.subscribe('successReloadPage', function() {
					topic.publish('list.refresh');
				});
				
				//新建
				window.add = function(){
					Com_OpenWindow('${LUI_ContextPath}/sys/mportal/sys_mportal_page/sysMportalPage.do?method=add');
				};
				 // 编辑
		 		window.edit = function(id) {
			 		if(id)
		 				Com_OpenWindow('<c:url value="/sys/mportal/sys_mportal_page/sysMportalPage.do" />?method=edit&fdId=' + id);
		 		};
		 		//批量置为无效
				window.disableAll = function(id){
					var values = [];
					if(id){
						values.push(id);
					}else{
						 $("input[name='List_Selected']:checked").each(function(){
								values.push($(this).val());
						 });
					}
					if(values.length == 0){
						dialog.alert('<bean:message key="page.noSelect"/>');
						return;
					}
					var url = '<c:url value="/sys/mportal/sys_mportal_page/sysMportalPage.do?method=disableAll"/>';
					dialog.confirm('<bean:message key="page.invalid"/>',function(value){
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
				
				//批量置为无效
				window.enableAll = function(id){
					var values = [];
					if(id){
						values.push(id);
					}else{
						 $("input[name='List_Selected']:checked").each(function(){
								values.push($(this).val());
						 });
					}
					if(values.length == 0){
						dialog.alert('<bean:message key="page.noSelect"/>');
						return;
					}
					var url = '<c:url value="/sys/mportal/sys_mportal_page/sysMportalPage.do?method=enableAll"/>';
					dialog.confirm('${lfn:message("sys-mportal:sysMportal.enable.confirm") }',function(value){
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
		 		
				//删除
				window.deleteAll = function(id){
					var values = [];
					if(id) {
		 				values.push(id);
			 		} else{
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
							$.post('<c:url value="/sys/mportal/sys_mportal_page/sysMportalPage.do?method=deleteall"/>',
									$.param({"List_Selected":values},true),delCallback,'json');
						}
					});
				};
				
				//删除回调函数
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
				
				
				
				window.del = function(id) {
		 			if(!id) {
		 				dialog.alert('<bean:message key="page.noSelect"/>');
						return;
		 			} else {
		 				dialog.confirm('<bean:message key="page.comfirmDelete"/>',function(flag){
							if(flag) {
								var loading = dialog.loading();
								$.get('<c:url value="/sys/mportal/sys_mportal_page/sysMportalPage.do" />?method=delete&fdId=' + id,
									function(data) {
										if(loading)
											loading.hide();
										if(data && data.status==true){
											topic.publish("list.refresh");
											dialog.success('<bean:message key="return.optSuccess" />');
										}else{
											dialog.failure('<bean:message key="return.optFailure" />');
										}
									},'json');
							}
						});
		 			}
		 		};
			});
			seajs.use(["sys/mportal/sys_mportal_card/css/edit.css"]);
			
		</script>
	
	</template:replace>
</template:include>