<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>

<template:include ref="config.profile.list">
	<template:replace name="title">${ lfn:message('sys-organization:sysOrgElement.person') }</template:replace>
	<template:replace name="content">
		<!-- 筛选器 -->
		<list:criteria id="criteria1">
			<list:cri-ref key="fdSearchName" ref="criterion.sys.docSubject" title="${lfn:message('sys-organization:sysOrgElement.search2') }" style="width: 600px;"></list:cri-ref>
		</list:criteria>
		
		<!-- 操作栏 -->
		<div class="lui_list_operation">
			<!-- 排序 -->
			<div style='color: #979797;float: left;padding-top:1px;'>
				${ lfn:message('list.orderType') }：
			</div>
			<div style="float:left">
				<div style="display: inline-block;vertical-align: middle;">
					<ui:toolbar layout="sys.ui.toolbar.sort" style="float:left" >
					<list:sortgroup>
						<list:sort property="fdNo" text="${lfn:message('sys-organization:sysOrgPerson.fdNo') }" group="sort.list" value="up"></list:sort>
						<list:sort property="fdName" text="${lfn:message('sys-organization:sysOrgPerson.fdName') }" group="sort.list"></list:sort>
						<list:sort property="fdLoginName" text="${lfn:message('sys-organization:sysOrgPerson.fdLoginName') }" group="sort.list"></list:sort>
						<list:sort property="fdEmail" text="${lfn:message('sys-organization:sysOrgPerson.fdEmail') }" group="sort.list"></list:sort>
						<list:sort property="fdMobileNo" text="${lfn:message('sys-organization:sysOrgPerson.fdMobileNo') }" group="sort.list"></list:sort>
					</list:sortgroup>
					</ui:toolbar>
				</div>
			</div>
			<!-- mini分页 -->
			<div style="float:left;">	
				<list:paging layout="sys.ui.paging.top" > 		
				</list:paging>
			</div>
			<% if(com.landray.kmss.sys.authorization.util.TripartiteAdminUtil.isSecurity()) { // 安全保密管理员才能做激活操作%>
			<!-- 操作按钮 -->
			<div style="float:right">
				<div style="display: inline-block;vertical-align: middle;">
					<ui:toolbar id="Btntoolbar" count="5">
						<c:if test="${'0' eq param.available}">
						<!-- 批量激活 -->
						<ui:button text="${lfn:message('sys-organization:org.personnel.activation.all') }" onclick="doActivate();"></ui:button>
						</c:if>
					</ui:toolbar>
				</div>
			</div>
			<% } %>
		</div>
		<ui:fixed elem=".lui_list_operation"></ui:fixed>
		<!-- 内容列表 -->
		<list:listview>
			<ui:source type="AjaxJson">
				{url:'/sys/organization/sys_org_person/sysOrgPerson.do?method=actiList&available=${JsParam.available}'}
			</ui:source>
			<list:colTable isDefault="true" layout="sys.ui.listview.columntable" 
				rowHref="/sys/organization/sys_org_person/sysOrgPerson.do?method=view&fdId=!{fdId}">
				<% if(com.landray.kmss.sys.authorization.util.TripartiteAdminUtil.isSecurity()) { // 安全保密管理员才能做激活操作%>
				<c:if test="${'0' eq param.available}">
				<list:col-checkbox></list:col-checkbox>
				</c:if>
				<% } %>
				<list:col-auto props="fdNo,fdName,fdLoginName,fdEmail,fdMobileNo"></list:col-auto>
				<% if(com.landray.kmss.sys.authorization.util.TripartiteAdminUtil.isSecurity()) { // 安全保密管理员才能做激活操作%>
				<c:if test="${'0' eq param.available}">
				<list:col-auto props="operations"></list:col-auto>
				</c:if>
				<% } %>
			</list:colTable>
		</list:listview>
		<br>
		<!-- 分页 -->
	 	<list:paging/>
	 	
	 	<script type="text/javascript">
		 	seajs.use(['lui/jquery', 'lui/dialog', 'lui/topic'], function($, dialog, topic) {
		 		// 监听新建更新等成功后刷新
				topic.subscribe('successReloadPage', function() {
					topic.publish("list.refresh");
				});
		 		
			 	// 增加
		 		window.doActivate = function(id) {
		 			var values = [];
		 			if(id) {
		 				values.push(id);
		 			}else {
						$("input[name='List_Selected']:checked").each(function(){
							values.push($(this).val());
						});
			 		}
		 			
		 			var url = '<c:url value="/sys/organization/sys_org_person/sysOrgPerson.do?method=activation"/>';
					dialog.confirm('<bean:message bundle="sys-organization" key="sysOrgPerson.person.activation.confirm"/>',function(value){
						if(value == true){
							window.file_load = dialog.loading();
							$.ajax({
								url: url,
								type: 'POST',
								data:$.param({'List_Selected' : values},true),
								dataType: 'json',
								error: function(data){
									if(window.file_load!=null){
										window.file_load.hide(); 
									}
									dialog.result(data.responseJSON);
								},
								success: function(data){
									if(window.file_load!=null){
										window.file_load.hide(); 
									}
									topic.publish('list.refresh');
									dialog.result(data);
								}
						   });
						}
					});
		 		}
		 		
		 	});
	 	</script>
	</template:replace>
</template:include>
