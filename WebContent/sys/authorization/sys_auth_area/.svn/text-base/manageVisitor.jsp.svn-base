<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<!-- file="/sys/profile/resource/template/list.jsp" -->
<template:include ref="config.list">
	<template:replace name="title">${ lfn:message('sys-authorization:table.sysAuthArea') }</template:replace>
	<template:replace name="content">
		
		<!-- 操作栏 -->
		<div class="lui_list_operation">
			<!-- 操作按钮 -->
			<div style="float:right">
				<div style="display: inline-block;vertical-align: middle;">
					<ui:toolbar id="Btntoolbar" count="5">
						<ui:button text="${lfn:message('sys-authorization:sysAuthArea.authAreaVisitor.update')}" onclick="changeVisitor()" order="3" ></ui:button>
					</ui:toolbar>
				</div>
			</div>
		</div>
		<ui:fixed elem=".lui_list_operation"></ui:fixed>
		<!-- 内容列表 -->
		<list:listview>
			<ui:source type="AjaxJson">
				{url:'/sys/authorization/sys_auth_area/sysAuthArea.do?method=getManageAreas'}
			</ui:source>
			<list:colTable isDefault="true" layout="sys.ui.listview.columntable" 
				rowHref="/sys/authorization/sys_auth_area/sysAuthArea.do?method=view&fdId=!{fdId}">
				<list:col-checkbox></list:col-checkbox>
				<list:col-serial></list:col-serial>
				<list:col-auto props="fdParent,fdName,authAreaOrg,authAreaVisitor"></list:col-auto>
			</list:colTable>
		</list:listview>
		<br>
	 	
	 	
	 	<script type="text/javascript">
		 	seajs.use(['lui/jquery', 'lui/dialog', 'lui/topic'], function($, dialog, topic) {
		 		window.changeVisitor = function() {
		 			var values = "";
					$("input[name='List_Selected']:checked").each(function(){
						 values += "," + $(this).val();
						});
					if(values==''){
						dialog.alert("${lfn:message('page.noSelect')}");
						return;
					}
					values = values.substring(1);
					var url = '/sys/authorization/sys_auth_area/changeVisitor.jsp?fdIds='+values;
					dialog.iframe(url,"${lfn:message('sys-authorization:sysAuthArea.authAreaVisitor.update')}", function(value) {
						if(value !== null)
							topic.publish("list.refresh");
					}, {
						"width" : 800,
						"height" : 400
					});
		 		}
		 	});
	 	</script>
	</template:replace>
</template:include>
