<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.landray.kmss.sys.authorization.util.TripartiteAdminUtil" %>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include file="/sys/profile/resource/template/list.jsp">
	<template:replace name="content">
		<!-- 筛选器 -->
		<list:criteria id="criteria">
		    <list:cri-ref key="fdServiceName" ref="criterion.sys.docSubject" title="${lfn:message('sys-restservice-server:sysRestserviceServerLog.fdServiceName') }">
			</list:cri-ref>
			<list:cri-auto modelName="com.landray.kmss.sys.restservice.server.model.SysRestserviceServerLog" property="fdServiceName"/>
			<list:cri-auto modelName="com.landray.kmss.sys.restservice.server.model.SysRestserviceServerLog" property="fdUserName"/>
			<list:cri-auto modelName="com.landray.kmss.sys.restservice.server.model.SysRestserviceServerLog" property="fdClientIp"/>
			<list:cri-auto modelName="com.landray.kmss.sys.restservice.server.model.SysRestserviceServerLog" property="fdStartTime"/>
			<list:cri-auto modelName="com.landray.kmss.sys.restservice.server.model.SysRestserviceServerLog" property="fdEndTime"/>
			<list:cri-auto modelName="com.landray.kmss.sys.restservice.server.model.SysRestserviceServerLog" property="fdExecResult"/>
		</list:criteria>
		<!-- 操作栏 -->
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
			<!-- 操作按钮 -->
			<div style="float:right">
				<div style="display: inline-block;vertical-align: middle;">
					<ui:toolbar id="Btntoolbar">
						<!-- 开启三员后不能删除 -->
						<% if(!TripartiteAdminUtil.IS_ENABLED_TRIPARTITE_ADMIN) { %>
					    <kmss:auth requestURL="/sys/restservice/server/sys_restservice_server_log/sysRestserviceServerLog.do?method=deleteall">
					        <ui:button text="${lfn:message('button.deleteall')}" onclick="deleteAll();" order="1" ></ui:button>
						</kmss:auth>	
						<% } %>
					</ui:toolbar>
				</div>
			</div>
		</div>
		<ui:fixed elem=".lui_list_operation"></ui:fixed>
		<!-- 内容列表 -->
		<list:listview>
			<ui:source type="AjaxJson">
				{url:'/sys/restservice/server/sys_restservice_server_log/sysRestserviceServerLog.do?method=${JsParam.methodName}'}
			</ui:source>
			<list:colTable isDefault="true" layout="sys.ui.listview.columntable" 
			     rowHref="/sys/restservice/server/sys_restservice_server_log/sysRestserviceServerLog.do?method=view&fdId=!{fdId}">
			    <!-- 开启三员后不能删除 -->
				<% if(!TripartiteAdminUtil.IS_ENABLED_TRIPARTITE_ADMIN) { %>
				<list:col-checkbox></list:col-checkbox>
				<% } %>
				<list:col-auto props="fdServiceName,fdServiceName,fdUserName,fdClientIp,fdStartTime,fdEndTime,fdExecResult"></list:col-auto>
				<!-- 开启三员后不能删除 -->
				<% if(!TripartiteAdminUtil.IS_ENABLED_TRIPARTITE_ADMIN) { %>
				<list:col-auto props="operations"></list:col-auto>
				<% } %>
			</list:colTable>
		</list:listview>
		<br>
		<!-- 分页 -->
	 	<list:paging/>
	 	<!-- 开启三员后不能删除 -->
		<% if(!TripartiteAdminUtil.IS_ENABLED_TRIPARTITE_ADMIN) { %>
	 	<script type="text/javascript">
		 	seajs.use(['lui/jquery', 'lui/dialog', 'lui/topic'], function($, dialog, topic) {
		 		window.deleteAll = function(id){
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
					dialog.confirm('<bean:message key="page.comfirmDelete"/>',function(value){
						if(value==true){
							window.del_load = dialog.loading();
							$.post('<c:url value="/sys/restservice/server/sys_restservice_server_log/sysRestserviceServerLog.do?method=deleteall"/>',
									$.param({"List_Selected":values},true),delCallback,'json');
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
	 	<% } %>
	</template:replace>
</template:include>
