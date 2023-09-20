<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ page import="com.landray.kmss.util.UserUtil"%>
<template:include ref="default.simple" spa="true">
	<template:replace name="body">
		<script type="text/javascript">
			seajs.use(['theme!list']);	
		</script>
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
							<list:sort property="docCreateTime" text="${lfn:message('km-archives:kmArchivesMain.docCreateTime') }" group="sort.list" value="down"></list:sort>
						</list:sortgroup>
					</ui:toolbar>
				</div>
			</div>
			<!-- 分页 -->
			<div class="lui_list_operation_page_top">	
				<list:paging layout="sys.ui.paging.top" > 		
				</list:paging>
			</div>
			<div style="float:right">
				<div style="display: inline-block;vertical-align: middle;">
					<ui:toolbar id="Btntoolbar">
						<kmss:auth requestURL="/km/archives/km_archives_main/kmArchivesMain.do?method=deleteall">
							<ui:button id="delBtnDraft" text="${lfn:message('button.deleteall')}" order="3" onclick="delDoc()"></ui:button>
						</kmss:auth>
					</ui:toolbar>
				</div>
			</div>
		</div>
		<ui:fixed elem=".lui_list_operation"></ui:fixed>
		<list:listview id="listview" cfg-criteriaInit="${empty param.categoryId?'false':'true'}">
			<ui:source type="AjaxJson">
					{url:'/km/archives/km_archives_main/kmArchivesMain.do?method=data&q.docStatus=10'}
			</ui:source>
			<list:colTable url="${LUI_ContextPath }/sys/profile/listShow/sys_listShow/sysListShow.do?method=getSort&modelName=com.landray.kmss.km.archives.model.KmArchivesMain" isDefault="true" layout="sys.ui.listview.columntable" rowHref="/km/archives/km_archives_main/kmArchivesMain.do?method=view&fdId=!{fdId}">
				<list:col-checkbox></list:col-checkbox>
				<list:col-serial></list:col-serial>
				<list:col-auto></list:col-auto> 
			</list:colTable>
		</list:listview> 
		<br>
	 	<list:paging></list:paging>
	 	<script type="text/javascript">
	 	var SYS_SEARCH_MODEL_NAME ="com.landray.kmss.km.archives.model.KmArchivesMain";
	 	seajs.use(['lui/jquery', 'lui/dialog','lui/topic'], function($, dialog , topic) {
		 	//删除
	 		window.delDoc = function(){
	 			var values = [];
				$("input[name='List_Selected']:checked").each(function(){
						values.push($(this).val());
					});
				if(values.length==0){
					dialog.alert('<bean:message key="page.noSelect"/>');
					return;
				}
				url = '<c:url value="/km/archives/km_archives_main/kmArchivesMain.do?method=deleteall&draft=true"/>';
				var config = {
					url : url, // 删除数据的URL
					data : $.param({"List_Selected":values},true), // 要删除的数据
					modelName : "com.landray.kmss.km.archives.model.KmArchivesMain" // 主要是判断此文档是否有部署软删除
				};
				// 通用删除方法
				Com_Delete(config, delCallback);
			};
			window.delCallback = function(data){
				topic.publish("list.refresh");
				dialog.result(data);
			};
			
			// 监听新建更新等成功后刷新
			topic.subscribe('successReloadPage', function() {
				topic.publish('list.refresh');
			});
	 	});
	 	</script>
	</template:replace>
	</template:include>
