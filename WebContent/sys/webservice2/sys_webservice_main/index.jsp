<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include file="/sys/profile/resource/template/list.jsp">
	<template:replace name="content">
		<!-- 筛选器 -->
		<list:criteria id="criteria" expand="true">
		    <list:cri-ref key="fdName" ref="criterion.sys.docSubject" title="${lfn:message('sys-webservice2:sysWebserviceMain.fdName') }">
			</list:cri-ref>
			<list:cri-auto modelName="com.landray.kmss.sys.webservice2.model.SysWebserviceMain" property="fdServiceStatus"/>
			<list:cri-auto modelName="com.landray.kmss.sys.webservice2.model.SysWebserviceMain" property="fdStartupType"/>
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
						<list:sort property="fdName" text="${lfn:message('sys-webservice2:sysWebserviceMain.fdName') }" group="sort.list"></list:sort>
						<list:sort property="fdServiceStatus" text="${lfn:message('sys-webservice2:sysWebserviceMain.fdServiceStatus') }" group="sort.list"></list:sort>
						<list:sort property="fdStartupType" text="${lfn:message('sys-webservice2:sysWebserviceMain.fdStartupType') }" group="sort.list"></list:sort>
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
					    <kmss:auth requestURL="/sys/webservice2/sys_webservice_main/sysWebserviceMain.do?method=startall">
					        <ui:button text="${lfn:message('sys-webservice2:button.list.startservice')}" onclick="startAll();" order="1" ></ui:button>
						</kmss:auth>	
						<kmss:auth requestURL="/sys/webservice2/sys_webservice_main/sysWebserviceMain.do?method=stopall">
						    <ui:button text="${lfn:message('sys-webservice2:button.list.stopservice')}" onclick="stopAll();" order="2" ></ui:button>
						</kmss:auth>
						<kmss:auth requestURL="/sys/webservice2/sys_webservice_init/sysWebserviceInit.do?method=init">
						    <ui:button text="${lfn:message('sys-webservice2:sysWebservice.init')}" onclick="webserviceInit();" order="2" ></ui:button>
						</kmss:auth>
					</ui:toolbar>
				</div>
			</div>
		</div>
		<ui:fixed elem=".lui_list_operation"></ui:fixed>
		<!-- 内容列表 -->
		<list:listview>
			<ui:source type="AjaxJson">
				{url:'/sys/webservice2/sys_webservice_main/sysWebserviceMain.do?method=list&status=${JsParam.status}&type=${JsParam.type}'}
			</ui:source>
			<list:colTable isDefault="true" layout="sys.ui.listview.columntable" 
			     rowHref="/sys/webservice2/sys_webservice_main/sysWebserviceMain.do?method=view&fdId=!{fdId}">
				<list:col-checkbox></list:col-checkbox>
				<list:col-auto props="fdName,fdServiceBean,fdServiceStatus,fdStartupType,operations"></list:col-auto>
			</list:colTable>
		</list:listview>
		<br>
		<!-- 分页 -->
	 	<list:paging/>
	 	
	 	<script type="text/javascript">
		 	seajs.use(['lui/jquery', 'lui/dialog', 'lui/topic'], function($, dialog, topic) {
		 		
		 		 // 导入web服务
		 		window.webserviceInit = function() {
			 			window.del_load = dialog.loading();
						$.ajax({
							url : '<c:url value="/sys/webservice2/sys_webservice_init/sysWebserviceInit.do" />?method=init',
							type : 'POST',
							dataType : 'json',
							error : function(data) {
								if(window.del_load != null) {
									window.del_load.hide(); 
								}
								dialog.result(data.responseJSON);
							},
							success: function(data) {
								// 这里只显示操作成功，不显示具体的操作内容项
								data.message = undefined;
								if(window.del_load != null){
									window.del_load.hide(); 
									topic.publish("list.refresh");
								}
								dialog.result(data);
							}
					   });
		 			//Com_OpenWindow('<c:url value="/sys/webservice2/sys_webservice_init/sysWebserviceInit.do?method=init"/>');
		 		};
		 		
		 	   // 编辑
		 		window.edit = function(id) {
			 		if(id)
		 				Com_OpenWindow('<c:url value="/sys/webservice2/sys_webservice_main/sysWebserviceMain.do" />?method=edit&fdId=' + id);
		 		};
		 		window.startAll = function(id){
					var values = [];
					if(id){
						values.push(id);
					}else{
						 $("input[name='List_Selected']:checked").each(function(){
							values.push($(this).val());
						});
					}
					if(values.length==0){
						dialog.alert('<bean:message key="page.noSelect"/>');
						return;
					}
					window.del_load = dialog.loading();
					$.post('<c:url value="/sys/webservice2/sys_webservice_main/sysWebserviceMain.do?method=startall"/>',
							$.param({"List_Selected":values},true),startCallback,'json');
				};
				window.stopAll = function(id){
					var values = [];
					if(id){
						values.push(id);
					}else{
						 $("input[name='List_Selected']:checked").each(function(){
							values.push($(this).val());
						});
					}
					if(values.length==0){
						dialog.alert('<bean:message key="page.noSelect"/>');
						return;
					}
					window.del_load = dialog.loading();
					$.post('<c:url value="/sys/webservice2/sys_webservice_main/sysWebserviceMain.do?method=stopall"/>',
							$.param({"List_Selected":values},true),stopCallback,'json');
				};
				window.startCallback = function(data){
					if(window.del_load!=null){
						window.del_load.hide();
						topic.publish("list.refresh");
					}
					dialog.result(data);
				};
				window.stopCallback = function(data){
					if(window.del_load!=null){
						window.del_load.hide();
						topic.publish("list.refresh");
					}
					dialog.result(data);
				};
		 	});
	 	</script>
	</template:replace>
</template:include>
