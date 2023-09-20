<%@ page language="java" contentType="text/html; charset=UTF-8"
		 pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include ref="default.simple" spa="true">
	<template:replace name="body">
		<script type="text/javascript">
			seajs.use([ 'theme!list' ]);
			//根据name和scope清楚缓存
			window.cleanCache = function (name,scope) {
				seajs.use(['lui/jquery', 'lui/dialog'], function($, dialog){
					$.ajax({
						type : "POST",
						dataType : "json",
						url : "${LUI_ContextPath}/sys/cache/KmssCache.do?method=removeCaches",
						data : {
							name:name,
							scope:scope
						},
						success : function(result) {
							if(result.success){
								dialog.alert("clean成功");
								LUI('listview_local').tableRefresh();
								LUI('listview_cluster').tableRefresh();
								LUI('listview_redis').tableRefresh();
							}else{
								dialog.alert(result.error);
							}
						},
						error : function(s, s2, s3) {

						}
					});
				});
			}
			//弹出多选框
			var dialogAllKeys = [];
			var dialogRemoveName = "";
			var dialogRemoveScope = 0;
			window.dialogRemoveKeys = function (name, keys, scope) {
				keys = keys.replace(/^(\s|\[)+|(\s|\])+$/g, '');
				dialogAllKeys = keys.split(',');
				dialogRemoveName = name;
				dialogRemoveScope = scope;
				seajs.use(['lui/dialog'], function (dialog) {
					window.removeKeyDialog = dialog.iframe('/sys/cache/removeKey_dialog.jsp', '选择要移除的key', function (rtn) {
						if(rtn){
							//刷新
							LUI('listview_local').tableRefresh();
							LUI('listview_cluster').tableRefresh();
							LUI('listview_redis').tableRefresh();
						}
					}, {height:600,width:800});
				});
			}

		</script>
		<ui:tabpanel suckTop="true" layout="sys.ui.tabpanel.sucktop"
					 var-count="5" var-average='false' var-useMaxWidth='true'>
			<ui:content title="Local">
				<div class="lui_list_operation">
					<!-- 排序 -->
					<!-- 分页 -->
					<div class="lui_list_operation_page_top">
						<list:paging layout="sys.ui.paging.top">
						</list:paging>
					</div>
				</div>
				<ui:fixed elem=".lui_list_operation"></ui:fixed>
				<list:listview id="listview_local">
					<ui:source type="AjaxJson">
						{url:'/sys/cache/KmssCache.do?method=data&scope=1'}
					</ui:source>
					<!-- 列表视图 -->
					<list:colTable id="coltable_local" isDefault="false"
								   name="columntable">
						<list:col-serial />
						<list:col-auto
								props="name;keys;oper"
								url="" />
					</list:colTable>
				</list:listview>
				<list:paging></list:paging>
			</ui:content>
			<ui:content title="Cluster">
				<div class="lui_list_operation">
					<!-- 排序 -->
					<!-- 分页 -->
					<div class="lui_list_operation_page_top">
						<list:paging layout="sys.ui.paging.top">
						</list:paging>
					</div>
				</div>
				<ui:fixed elem=".lui_list_operation"></ui:fixed>
				<list:listview id="listview_cluster">
					<ui:source type="AjaxJson">
						{url:'/sys/cache/KmssCache.do?method=data&scope=2'}
					</ui:source>
					<!-- 列表视图 -->
					<list:colTable id="coltable_cluster" isDefault="false"
								   name="columntable">
						<list:col-serial />
						<list:col-auto
								props="name;keys;oper"
								url="" />
					</list:colTable>
				</list:listview>
				<list:paging></list:paging>
			</ui:content>
			<ui:content title="Redis">
				<div class="lui_list_operation">
					<!-- 排序 -->
					<!-- 分页 -->
					<div class="lui_list_operation_page_top">
						<list:paging layout="sys.ui.paging.top">
						</list:paging>
					</div>
				</div>
				<ui:fixed elem=".lui_list_operation"></ui:fixed>
				<list:listview id="listview_redis">
					<ui:source type="AjaxJson">
						{url:'/sys/cache/KmssCache.do?method=data&scope=3'}
					</ui:source>
					<!-- 列表视图 -->
					<list:colTable id="coltable_redis" isDefault="false"
								   name="columntable">
						<list:col-serial />
						<list:col-auto
								props="name;keys;oper"
								url="" />
					</list:colTable>
				</list:listview>
				<list:paging></list:paging>
			</ui:content>
		</ui:tabpanel>
	</template:replace>
</template:include>