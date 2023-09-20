<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@page import="com.landray.kmss.util.ResourceUtil"%>

<%
	String _day = ResourceUtil.getKmssConfigString("kmss.logLoginBackupBefore");
	String _log = ResourceUtil.getString("table.sysLogLogout", "sys-log");
	String _info1 = ResourceUtil.getString(request, "sys.log.show.info", "sys-log", new Object[]{_day, _log});
	String _info2 = ResourceUtil.getString(request, "sys.log.bak.show.info", "sys-log", new Object[]{_day, _log});
	pageContext.setAttribute("_info1", _info1);
	pageContext.setAttribute("_info2", _info2);
%>

<template:include file="/sys/profile/resource/template/list.jsp">
	<template:replace name="title">${ lfn:message('sys-log:table.sysLogLogout') }</template:replace>
	<template:replace name="content">
		<ui:tabpanel>
			<ui:content title='${_info1}'>
				<!-- 筛选器 -->
				<list:criteria channel="sysLogLogout">
					<list:cri-ref ref="criterion.sys.person" key="fdOperatorId" title="${ lfn:message('sys-log:sysLogLogout.fdOperator') }"></list:cri-ref>
					<list:cri-auto modelName="com.landray.kmss.sys.log.model.SysLogLogout" property="fdIp"/>
					<list:cri-auto modelName="com.landray.kmss.sys.log.model.SysLogLogout" property="fdCreateTime"/>
				</list:criteria>
				<!-- 操作栏 -->
				<div class="lui_list_operation">
					<!-- 全选 -->
					<div class="lui_list_operation_order_btn">
						<list:selectall channel="sysLogLogout"></list:selectall>
					</div>
					<!-- 分割线 -->
					<div class="lui_list_operation_line"></div>
					<!-- 排序 -->
					<div class="lui_list_operation_sort_btn">
						<div class="lui_list_operation_order_text">
							${ lfn:message('list.orderType') }：
						</div>
						<div class="lui_list_operation_sort_toolbar">
							<ui:toolbar layout="sys.ui.toolbar.sort" style="float:left" channel="sysLogLogout">
							<list:sortgroup>
								<list:sort channel="sysLogLogout" property="fdCreateTime" text="${lfn:message('model.fdCreateTime') }" group="sort.list" value="down"></list:sort>
							</list:sortgroup>
							</ui:toolbar>
						</div>
					</div>
					<!-- 分页 -->
					<div class="lui_list_operation_page_top">
						<list:paging layout="sys.ui.paging.top" channel="sysLogLogout"> 		
						</list:paging>
					</div>
					<!-- 操作按钮 -->
					<div style="float:right">
						<div style="display: inline-block;vertical-align: middle;">
							<ui:toolbar count="5" channel="sysLogLogout">
								<kmss:auth requestURL="/sys/log/sys_logout_info/sysLogLogout.do?method=deleteall" requestMethod="POST">
									<!-- 删除 -->
									<ui:button text="${lfn:message('button.deleteall')}" onclick="del('')"></ui:button>
								</kmss:auth>
							</ui:toolbar>
						</div>
					</div>
				</div>
				<!-- 内容列表 -->
				<list:listview id="sysLogLogout" channel="sysLogLogout">
					<ui:source type="AjaxJson">
						{url:'/sys/log/sys_logout_info/sysLogLogout.do?method=list'}
					</ui:source>
					<list:colTable isDefault="true" layout="sys.ui.listview.columntable" 
						rowHref="/sys/log/sys_logout_info/sysLogLogout.do?method=view&fdId=!{fdId}" channel="sysLogLogout">
						<list:col-checkbox></list:col-checkbox>
						<list:col-serial></list:col-serial>
						<list:col-auto props="fdCreateTime,fdIp,fdBrowser,fdEquipment,fdOperator,operations"></list:col-auto>
					</list:colTable>
					<ui:event topic="list.loaded">
						Dropdown.init();
					</ui:event>
				</list:listview>
				<br>
				<!-- 分页 -->
			 	<list:paging channel="sysLogLogout"/>
			</ui:content>
			<ui:content title='${_info2}'>
				<!-- 筛选器 -->
				<list:criteria channel="sysLogLogoutBak">
					<list:cri-ref ref="criterion.sys.person" key="fdOperatorId" title="${ lfn:message('sys-log:sysLogLogout.fdOperator') }"></list:cri-ref>
					<list:cri-auto modelName="com.landray.kmss.sys.log.model.SysLogLogout" property="fdIp"/>
					<list:cri-auto modelName="com.landray.kmss.sys.log.model.SysLogLogout" property="fdCreateTime"/>
				</list:criteria>
			 	<!-- 操作栏 -->
				<div class="lui_list_operation">
					<!-- 全选 -->
					<div class="lui_list_operation_order_btn">
						<list:selectall channel="sysLogLogoutBak"></list:selectall>
					</div>
					<!-- 分割线 -->
					<div class="lui_list_operation_line"></div>
					<!-- 排序 -->
					<div class="lui_list_operation_sort_btn">
						<div class="lui_list_operation_order_text">
							${ lfn:message('list.orderType') }：
						</div>
						<div class="lui_list_operation_sort_toolbar">
							<ui:toolbar layout="sys.ui.toolbar.sort" style="float:left" channel="sysLogLogoutBak">
							<list:sortgroup>
								<list:sort channel="sysLogLogoutBak" property="fdCreateTime" text="${lfn:message('model.fdCreateTime') }" group="sort.list" value="down"></list:sort>
							</list:sortgroup>
							</ui:toolbar>
						</div>
					</div>
					<!-- 分页 -->
					<div class="lui_list_operation_page_top">
						<list:paging layout="sys.ui.paging.top" channel="sysLogLogoutBak">
						</list:paging>
					</div>
					<!-- 操作按钮 -->
					<div style="float:right">
						<div style="display: inline-block;vertical-align: middle;">
							<ui:toolbar count="5" channel="sysLogLogoutBak">
								<kmss:auth requestURL="/sys/log/sys_logout_info/sysLogLogout.do?method=deleteall" requestMethod="POST">
									<!-- 删除 -->
									<ui:button text="${lfn:message('button.deleteall')}" onclick="del('true')"></ui:button>
								</kmss:auth>
							</ui:toolbar>
						</div>
					</div>
				</div>
				<!-- 内容列表 -->
				<list:listview id="sysLogLogoutBak" channel="sysLogLogoutBak">
					<ui:source type="AjaxJson">
						{url:'/sys/log/sys_logout_info/sysLogLogout.do?method=list&isBak=true'}
					</ui:source>
					<list:colTable isDefault="true" layout="sys.ui.listview.columntable" 
						rowHref="/sys/log/sys_logout_info/sysLogLogout.do?method=view&fdId=!{fdId}&isBak=true" channel="sysLogLogoutBak">
						<list:col-checkbox></list:col-checkbox>
						<list:col-serial></list:col-serial>
						<list:col-auto props="fdCreateTime,fdIp,fdBrowser,fdEquipment,fdOperator,operations"></list:col-auto>
					</list:colTable>
					<ui:event topic="list.loaded">
						Dropdown.init();
					</ui:event>
				</list:listview>
				<br>
				<!-- 分页 -->
			 	<list:paging channel="sysLogLogoutBak"/>
			 </ui:content>
		</ui:tabpanel>
	 	
	 	<script type="text/javascript">
		 	seajs.use(['lui/jquery', 'lui/dialog', 'lui/topic'], function($, dialog, topic) {
		 		// 删除
		 		window.del = function(isBak, id) {
		 			var values = [];
		 			if(id) {
		 				values.push(id);
			 		} else {
			 			if(isBak == 'true') {
			 				$("#sysLogLogoutBak input[name='List_Selected']:checked").each(function() {
								values.push($(this).val());
							});
				 		} else {
				 			$("#sysLogLogout input[name='List_Selected']:checked").each(function() {
								values.push($(this).val());
							});
					 	}
			 		}
					if(values.length==0){
						dialog.alert('<bean:message key="page.noSelect"/>');
						return;
					}
					var url  = '<c:url value="/sys/log/sys_logout_info/sysLogLogout.do?method=deleteall"/>&isBak=' + isBak;
					dialog.confirm('<bean:message key="page.comfirmDelete"/>', function(value) {
						if(value == true) {
							window.del_load = dialog.loading();
							$.ajax({
								url : url,
								type : 'POST',
								data : $.param({"List_Selected" : values}, true),
								dataType : 'json',
								error : function(data) {
									if(window.del_load != null) {
										window.del_load.hide(); 
									}
									dialog.result(data.responseJSON);
								},
								success: function(data) {
									if(window.del_load != null){
										window.del_load.hide(); 
										if(isBak == 'true')
											topic.channel("sysLogLogoutBak").publish("list.refresh");
										else
											topic.channel("sysLogLogout").publish("list.refresh");
									}
									dialog.result(data);
								}
						   });
						}
					});
				};
		 	});
	 	</script>
	</template:replace>
</template:include>
