<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include file="/tic/core/tic_ui_list.jsp">
	<template:replace name="title">${ lfn:message('tic-core:module.tic.manage') }</template:replace>
	
	<%-- 
	<template:replace name="path">
	 <ui:menu layout="sys.ui.menu.nav" style="height:40px;line-height:40px;" id="categoryId">
		<ui:menu-item text="${lfn:message('tic-core-common:ticCoreCommon.currPath')}" icon="lui_icon_s_home"  target="_parent">
			</ui:menu-item>
			<ui:menu-item text="${lfn:message('tic-core-common:ticCoreCommon.SAPIntegrate')}"  target="_parent">
			</ui:menu-item>
			<ui:menu-item text="${lfn:message('tic-core-common:ticCoreTree.planJob')}" href="javascript:location.reload();" target="_self">
			</ui:menu-item>
		</ui:menu>
	</template:replace>
	--%>
	
    
	<%-- 右边框内容 --%>
	<template:replace name="content">
		<%-- 筛选器 --%>
		<list:criteria>
			<list:cri-ref key="fdSubject" ref="criterion.sys.docSubject" title="${ lfn:message('sys-quartz:sysQuartzJob.fdSubject') }">
			</list:cri-ref>
			<list:cri-ref ref="criterion.sys.simpleCategory" key="fdCategory" multi="false" title="${lfn:message('tic-core-common:ticCoreCommon.categoryNavigation')}" expand="true">
			  <list:varParams modelName="${JsParam.modelName}"/>
			</list:cri-ref>
			<list:cri-auto modelName="com.landray.kmss.tic.core.sync.model.TicCoreSyncJob" property="fdEnabled"/>
		</list:criteria>
		
		<%-- 显示列表按钮行 --%>
		<div class="lui_list_operation">
			<div class="lui_list_operation_page_top">	
				<list:paging layout="sys.ui.paging.top" > 		
				</list:paging>
			</div>
			<div style="float: right">
				<div style="display: inline-block; vertical-align: middle;">
						<ui:toolbar>
							<kmss:auth requestURL="/tic/core/sync/tic_core_sync_job/ticCoreSyncJob.do?method=add">
								<ui:button text="${lfn:message('button.add')}" onclick="addDocByCate();" order="2" ></ui:button>
							</kmss:auth>
							<kmss:auth requestURL="/tic/core/sync/tic_core_sync_job/ticCoreSyncJob.do?method=deleteall">
								<ui:button text="${lfn:message('button.deleteall')}" order="4" onclick="delDoc('${LUI_ContextPath}/tic/core/sync/tic_core_sync_job/ticCoreSyncJob.do?method=deleteall')"></ui:button>
							</kmss:auth>
							<kmss:auth requestURL="/tic/core/sync/tic_core_sync_job/ticCoreSyncJob.do?method=chgenabled">
								<ui:button text="${lfn:message('sys-quartz:sysQuartzJob.button.enable')}"
									onclick="ticEnableSave('${LUI_ContextPath}/tic/core/sync/tic_core_sync_job/ticCoreSyncJob.do?method=chgenabled&fdEnabled=true');"></ui:button>
							</kmss:auth>	
							<kmss:auth requestURL="/tic/core/sync/tic_core_sync_job/ticCoreSyncJob.do?method=chgenabled">
								<ui:button text="${lfn:message('sys-quartz:sysQuartzJob.button.disable')}"
									onclick="ticEnableSave('${LUI_ContextPath}/tic/core/sync/tic_core_sync_job/ticCoreSyncJob.do?method=chgenabled&fdEnabled=false');"></ui:button>
							</kmss:auth>
						</ui:toolbar>	
					</div>
				</div>
		</div>
		<ui:fixed elem=".lui_list_operation"></ui:fixed>
		<list:listview id="listview">
			<ui:source type="AjaxJson">
				{url:'/tic/core/sync/tic_core_sync_job/ticCoreSyncJob.do?method=list&categoryId=${param.categoryId }&fdAppType=${param.fdAppType}&fdEnviromentId=${param.fdEnviromentId}'}
			</ui:source>
			<%--列表形式--%>
			<list:colTable isDefault="false" layout="sys.ui.listview.columntable" 
				rowHref="/tic/core/sync/tic_core_sync_job/ticCoreSyncJob.do?method=edit&fdId=!{fdId}&fdAppType=${param.fdAppType}&fdEnviromentId=${param.fdEnviromentId}" name="columntable">
				<list:col-checkbox></list:col-checkbox>
				<list:col-serial></list:col-serial>
				<list:col-auto props=""></list:col-auto>
			</list:colTable>   
		</list:listview> 
		<script type="text/javascript">
		seajs.use(['lui/jquery', 'lui/util/str', 'lui/dialog','lui/topic'], function($, strutil, dialog , topic) {
			// 监听新建更新等成功后刷新
			topic.subscribe('successReloadPage', function() {
				setTimeout(function(){
						topic.publish('list.refresh');
				}, 100);
			});
			var cateId = "${param.categoryId}";
			window.addDocByCate = function(){
				window.open('${LUI_ContextPath}/tic/core/sync/tic_core_sync_job/ticCoreSyncJob.do?method=add&fdAppType=${param.fdAppType}&fdEnviromentId=${param.fdEnviromentId}&fdtemplatId='+ cateId);
			};
			//根据筛选器分类异步校验权限
			topic.subscribe('criteria.changed',function(evt){
				//每次都重置分类id的值,因为可能存在直接点叉清除分类筛选项
				cateId = "";
					//每次都重置分类id的值,因为可能存在直接点叉清除分类筛选项
				for ( var i = 0; i < evt['criterions'].length; i++) {
					if (evt['criterions'][i].key == "fdCategory") {
						if (evt['criterions'][i].value[0] != cateId) {
							cateId = evt['criterions'][i].value[0];
						}
					}
				}
			});
			//删除
			window.delDoc = function(url) {
				var values = [];
				$("input[name='List_Selected']:checked").each( function() {
					values.push($(this).val());
				});
				if (values.length == 0) {
					dialog.alert('<bean:message key="page.noSelect"/>');
					return;
				}
				dialog.confirm('<bean:message key="page.comfirmDelete"/>',
						function(value) {
							if (value == true) {
								window.del_load = dialog.loading();
								$.post('<c:url value="' + url + '"/>', $.param(
										{
											"List_Selected" : values
										}, true), delCallback, 'json');
							}
						});
			};
			window.delCallback = function(data) {
				if (window.del_load != null) {
					window.del_load.hide();
				}
				if (data != null && data.status == true) {
					topic.publish("list.refresh");
					dialog.success('<bean:message key="return.optSuccess" />');
				} else {
					dialog.failure('<bean:message key="return.optFailure" />');
				}
			};
			// 启用/禁用
			window.ticEnableSave = function(url) {
				var values = [];
				$("input[name='List_Selected']:checked").each( function() {
					values.push($(this).val());
				});
				if (values.length == 0) {
					dialog.alert('<bean:message key="page.noSelect"/>');
					return false;
				}
				dialog.confirm('是否确认当前操作?', function(value) {
					if (value == true) {
						window.del_load = dialog.loading();
						$.post('<c:url value="' + url + '"/>', $.param( {
							"List_Selected" : values
						}, true), enableCallback, 'json');
					}
				});
				return null;
			};
			window.enableCallback = function(data) {
				if (window.del_load != null) {
					window.del_load.hide();
				}
				if (data != null && data.status == true) {
					topic.publish("list.refresh");
					dialog.success('<bean:message key="return.optSuccess" />');
				} else {
					dialog.failure('<bean:message key="return.optFailure" />');
				}
			};
		});
</script>
		<br>
	 	<list:paging></list:paging>
		<c:import url="/sys/profile/i18n/quicklyMultiLangEdit.jsp"></c:import>
	</template:replace>
</template:include>