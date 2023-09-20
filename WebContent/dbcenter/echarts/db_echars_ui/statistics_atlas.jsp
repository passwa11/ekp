<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@page import="com.landray.kmss.sys.authorization.constant.ISysAuthConstant"%>
<template:include ref="default.simple" spa="true">
	<template:replace name="title">
		<c:out value="${ lfn:message('dbcenter-echarts:module.dbcenter.piccenter') }"></c:out>
	</template:replace>
	<template:replace name="body">
		<script type="text/javascript">
				seajs.use(['theme!list']);	
		</script>
		<ui:tabpanel id="echartsPanel" layout="sys.ui.tabpanel.list" >
			<ui:content id="atlasContent" title="${ lfn:message('dbcenter-echarts:table.dbEchartsChartSet') }">
			
		<!-- 筛选器 -->
		<list:criteria id="criteria">
		     <list:cri-ref key="docSubject" ref="criterion.sys.docSubject" title="${lfn:message('dbcenter-echarts:dbEchartsChartSet.docSubject') }">
			</list:cri-ref>
			<list:cri-auto modelName="com.landray.kmss.dbcenter.echarts.model.DbEchartsChartSet" 
				property="dbEchartsTemplate" />
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
					   <list:sort property="docCreateTime" text="${lfn:message('dbcenter-echarts:dbEchartsChartSet.docCreateTime') }" group="sort.list" value="down"></list:sort>
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
						<kmss:authShow roles="ROLE_DBCENTERECHARTS_CHARTSET_ADD">
							<ui:button text="${ lfn:message('button.add') }" 
								onclick="addChartSet();">
							</ui:button>
						</kmss:authShow>
						<kmss:auth requestURL="/dbcenter/echarts/db_echarts_chart_set/dbEchartsChartSet.do?method=deleteall">
							<ui:button text="${ lfn:message('button.deleteall') }" onclick="deleteAll();">
							</ui:button>
						</kmss:auth>
					</ui:toolbar>	
				</div>
			</div>
		</div>
		<ui:fixed elem=".lui_list_operation"></ui:fixed>
		<!-- 内容列表 -->
		<list:listview>
			<ui:source type="AjaxJson">
				{url:'/dbcenter/echarts/db_echarts_chart_set/dbEchartsChartSet.do?method=list&orderby=docCreateTime&ordertype=down&categoryId=${param.categoryId }'}
			</ui:source>
			<list:colTable isDefault="true" layout="sys.ui.listview.columntable" 
			     rowHref="/dbcenter/echarts/db_echarts_chart_set/dbEchartsChartSet.do?method=view&fdId=!{fdId}">
				<list:col-checkbox></list:col-checkbox>
				<list:col-serial></list:col-serial>
				<list:col-auto props="docSubject,dbEchartsTemplate.fdName,docCreateTime,docCreator.fdName"></list:col-auto>
			</list:colTable>
		</list:listview>
		 
	 	<list:paging></list:paging>	
	 	</ui:content>
	</ui:tabpanel> 
	</template:replace>
</template:include>
<script type="text/javascript">
		 	seajs.use(['lui/jquery', 'lui/dialog', 'lui/topic'], function($, dialog, topic) {
		 		
		 		/**------ 新建  统计图表集  ------**/
		 		window.addChartSet = function(){
		 			// 如果筛选器中没有选中分类则直接打开新建页面
		 			if(cateId==""){
			 			var addUrl = "${LUI_ContextPath}/dbcenter/echarts/db_echarts_chart_set/dbEchartsChartSet.do?method=add";
			 			Com_OpenWindow(addUrl);	
		 			}else{
			 			dialog.simpleCategoryForNewFile(
								'com.landray.kmss.dbcenter.echarts.model.DbEchartsTemplate',
								'/dbcenter/echarts/db_echarts_chart_set/dbEchartsChartSet.do?method=add&fdTemplateId=!{id}',false,null,null,cateId);
		 			}
		 		};
		 		
		 		/**------ 修改  统计图表集  ------**/
		 		window.edit = function(id){
					if(id){
						Com_OpenWindow('${LUI_ContextPath}/dbcenter/echarts/db_echarts_chart_set/dbEchartsChartSet.do?method=edit&fdId='+id);
					}
			 	};
		 		
			 	/**------ 删除  统计图表集  ------**/
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
							$.post('<c:url value="/dbcenter/echarts/db_echarts_chart_set/dbEchartsChartSet.do?method=deleteall"/>',
									$.param({"List_Selected":values},true),delCallback,'json');
						}
					});
				};
				
				/**------ 删除成功回调函数  ------**/
				window.delCallback = function(data){
					if(window.del_load!=null){
						window.del_load.hide();
					}
					topic.publish("list.refresh");
					dialog.result(data);
				};
				
				/**------ 筛选器内容修改记录下选择的分类  ------**/
				var cateId = ""; // 选中的分类ID
				topic.subscribe('criteria.spa.changed',function(evt){	
					cateId = ""; // 清空选中分类ID
					for(var i=0;i<evt['criterions'].length;i++){
						  //获取分类id和类型
		             	  if(evt['criterions'][i].key == "dbEchartsTemplate"){
		                 	 cateId= evt['criterions'][i].value[0];
		             	  }
					}
				});
				
		 	});
	 	</script>