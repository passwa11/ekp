<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include ref="config.list">
	<template:replace name="content">
		<!-- 操作栏 -->
		<div class="lui_list_operation">
			<!-- 全选 -->
			<div class="lui_list_operation_order_btn">
				<list:selectall></list:selectall>
			</div>
			<!-- 操作按钮 -->
			<div style="float:right">
				<div style="display: inline-block;vertical-align: middle;">
					<ui:toolbar id="Btntoolbar" count="6">
						<kmss:auth requestURL="/sys/mobile/sys_mobile_offline/sysMobilePackage.do?method=packageModule" requestMethod="GET">
							<ui:button text="${lfn:message('third-pda:pdaOfflineApp.button') }" onclick="downloadOffline();" order="1" ></ui:button>
						</kmss:auth>
					</ui:toolbar>
				</div>
			</div>
		</div>
		<ui:fixed elem=".lui_list_operation"></ui:fixed>
		<!-- 内容列表 -->
		<list:listview>
			<ui:source type="AjaxJson">
				{url:'/sys/mobile/sys_mobile_offline/sysMobilePackage.do?method=list'}
			</ui:source>
			<list:colTable isDefault="true" layout="sys.ui.listview.columntable" name="columntable">
				<list:col-checkbox></list:col-checkbox>
				<list:col-serial></list:col-serial>
				<list:col-auto props="appName,folder,homepage,description"></list:col-auto>
			</list:colTable>
		</list:listview>
	
		<script type="text/javascript">
			seajs.use(['lui/jquery','lui/topic','lui/dialog'],function($,topic,dialog){
				// 监听新建更新等成功后刷新
				topic.subscribe('successReloadPage', function() {
					topic.publish('list.refresh');
				});
				function getIds() {
					var rtn = [];
					$('[name="List_Selected"]:checked').each(function(i, val) {
						rtn.push( (i > 0 ? ";" : "appIds=") + val.value)
					});
					return rtn.join('');
				}
				function showResult() {
					dialog.alert("${lfn:message('third-pda:pdaOfflineApp.showResult')}");
				}
				window.downloadOffline = function() {
					var data = getIds();
					if (data == null || data == '') {
						dialog.alert("${lfn:message('sys-mobile:mui.select.task')}");
						return;
					}
					$.ajax({
						type: 'POST',
						dataType: 'json',
						url: '<c:url value="/sys/mobile/sys_mobile_offline/sysMobilePackage.do?method=packageModule"/>',
						data: data
					});
					showResult();
				};
			});
		</script>
	</template:replace>
</template:include>