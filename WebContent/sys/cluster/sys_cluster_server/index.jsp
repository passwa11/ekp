<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="com.landray.kmss.sys.cluster.util.SysClusterConstants"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include ref="config.list">
	<template:replace name="content">
		<!-- 筛选器 -->
		<list:criteria id="criteria">
		     <list:cri-ref key="fdName" ref="criterion.sys.docSubject" title="${lfn:message('sys-cluster:sysClusterServer.fdName') }">
			</list:cri-ref>
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
					<ui:toolbar id="Btntoolbar" cfg-dataInit="false">
						<% if(SysClusterConstants.CLUSTER_ENABLED){ %>
					    <ui:button text="${lfn:message('sys-cluster:sysClusterDispatcher.setting')}"  onclick="setting();" order="1" ></ui:button>
						<% } %>
						<ui:button text="${lfn:message('button.add')}"  onclick="add();" order="1" ></ui:button>
						<ui:button text="${lfn:message('button.deleteall')}" onclick="deleteAll();" order="2" ></ui:button>
						<ui:button text="${lfn:message('sys-cluster:sysCluster.test')}" onclick="open('../test.jsp','_blank');" order="2" ></ui:button>
					</ui:toolbar>
				</div>
			</div>
		</div>
		<ui:fixed elem=".lui_list_operation"></ui:fixed>
		<!-- 内容列表 -->
		<list:listview>
			<ui:source type="AjaxJson">
				{url:'/sys/cluster/sys_cluster_server/sysClusterServer.do?method=list'}
			</ui:source>
			<list:colTable isDefault="true" layout="sys.ui.listview.columntable" 
			    rowHref="/sys/cluster/sys_cluster_server/sysClusterServer.do?method=view&fdId=!{fdId}">
				<list:col-auto props="checkbox,fdName,fdKey,status,fdPid,fdAddress,fdStartTime,fdRefreshTime,fdDispatcherType"></list:col-auto>
			</list:colTable>
		</list:listview>
		<br>
		<!-- 分页 -->
	 	<list:paging/>
		<br>
	<style>
		.help li{
			font-weight: bold;
			line-height:250%;
		}
		.help div{
			font-weight:normal;
			line-height:180%;
		}
	</style>
	<div width="85%">
		<a href="#" style="width:98%;display:block;background-color: #f0f0f0; font-size: 12px;padding:10px 0px 10px 10px;"
			onclick="DIV_HELP.style.display=DIV_HELP.style.display=='none'?'':'none';">
			<bean:message bundle="sys-cluster" key="sysCluster.common"/></a>
		<div id="DIV_HELP" class="help" style="display:none;">
			<li><bean:message bundle="sys-cluster" key="sysCluster.problem1"/></li>
			<div>
				<bean:message bundle="sys-cluster" key="sysCluster.reply1"/>
			</div>
			<li><bean:message bundle="sys-cluster" key="sysCluster.problem2"/></li>
			<div>
			<bean:message bundle="sys-cluster" key="sysCluster.reply2"/>
			</div>
			<li><bean:message bundle="sys-cluster" key="sysCluster.problem3"/></li>
			<div>
			<bean:message bundle="sys-cluster" key="sysCluster.reply3"/>
			</div>
			<li><bean:message bundle="sys-cluster" key="sysCluster.problem4"/></li>
			<div>
			<bean:message bundle="sys-cluster" key="sysCluster.reply4"/>
			</div>
			<li><bean:message bundle="sys-cluster" key="sysCluster.problem5"/></li>
			<div>
			<bean:message bundle="sys-cluster" key="sysCluster.reply5"/>
			</div>
			<li><bean:message bundle="sys-cluster" key="sysCluster.problem6"/></li>
			<div>
			<bean:message bundle="sys-cluster" key="sysCluster.reply6"/>
			</div>
		</div>
	</div>
	 	
	 	
	 	<script type="text/javascript">
		 	seajs.use(['lui/jquery', 'lui/dialog', 'lui/topic'], function($, dialog, topic) {
			 	// 增加
		 		window.add = function() {
		 			Com_OpenWindow('<c:url value="/sys/cluster/sys_cluster_server/sysClusterServer.do" />?method=add&parentId=${param.parentId}');
		 		};
		 		// 编辑
		 		window.edit = function(id) {
		 			Com_OpenWindow('<c:url value="/sys/cluster/sys_cluster_server/sysClusterServer.do" />?method=edit&fdId=' + id);
		 		};
		 	    // 设置调度服务运行地址
		 		window.setting = function(id) {
		 			Com_OpenWindow('<c:url value="/sys/cluster/sys_cluster_dispatcher/sysClusterDispatcher.do" />?method=list');
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
					var url = '<c:url value="/sys/cluster/sys_cluster_server/sysClusterServer.do?method=deleteall"/>';
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
	</template:replace>
</template:include>
