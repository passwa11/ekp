<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ page import="java.io.File" %>
<%@ page import="java.lang.*" %>
<%@ page import="java.lang.reflect.Method" %>
<%@ page import="javax.servlet.http.HttpServletRequest" %>
<%@ page import="com.landray.kmss.framework.util.PluginConfigLocationsUtil" %>
<%
   String fdModelName  =request.getParameter("fdModelName");
   if(new File(PluginConfigLocationsUtil.getKmssConfigPath()
			+ "/sys/modeling/").exists()&&"com.landray.kmss.sys.modeling.base.model.ModelingAppModel".equals(fdModelName)){
	   request.setAttribute("hasModeling", true);
	   
	   Object modelingUiUtil = Class.forName(
				"com.landray.kmss.sys.modeling.base.util.ModelingUiUtil")
				.newInstance();
		Class clazz = modelingUiUtil.getClass();
		Method method = clazz.getMethod(
				"setDefaultTheme",
				HttpServletRequest.class);
		method.invoke(modelingUiUtil,
				request);
   }
%>

<template:include file="/sys/profile/resource/template/list.jsp">
	<%-- 统计图表_列表展示页  --%>
	<template:replace name="content">
		<!-- 筛选器 -->
		<div style="margin-top:10px">
			<list:criteria id="criteria1">
				<list:cri-ref key="docSubject" ref="criterion.sys.docSubject" title="${lfn:message('dbcenter-echarts:dbEchartsChart.docSubject') }">
				</list:cri-ref>
				<list:cri-auto modelName="com.landray.kmss.dbcenter.echarts.model.DbEchartsChart" property="dbEchartsTemplate" />
				<list:cri-criterion title="${ lfn:message('dbcenter-echarts:dbEchartsChart.createMode')}" key="fdType"> 
					<list:box-select>
						<list:item-select>
							<ui:source type="Static">
								[
								{text:'${ lfn:message('dbcenter-echarts:dbEchartsChart.configMode') }',value:'01'},
								{text:'${ lfn:message('dbcenter-echarts:dbEchartsChart.normalProgramMode') }',value:'0'},
								{text:'${ lfn:message('dbcenter-echarts:dbEchartsChart.advanceProgramMode') }',value:'1'}
								]
							</ui:source>
						</list:item-select>
					</list:box-select>
			    </list:cri-criterion>
			</list:criteria>
		</div>
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
					   <list:sort property="docCreateTime" text="${lfn:message('dbcenter-echarts:dbEchartsChart.docCreateTime') }" group="sort.list" value="down"></list:sort>
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
						<kmss:authShow roles="ROLE_DBCENTERECHARTS_CHART_ADD">
							<ui:button text="${ lfn:message('button.add') }" 
								onclick="addChart();">
							</ui:button>
						</kmss:authShow>
						<kmss:auth requestURL="/dbcenter/echarts/db_echarts_chart/dbEchartsChart.do?method=deleteall">
							<ui:button text="${ lfn:message('button.deleteall') }" onclick="deleteAll();">
							</ui:button>
						</kmss:auth>
					</ui:toolbar>	
				</div>
			</div>
		</div>
		<ui:fixed elem=".lui_list_operation"></ui:fixed>
		<%--list页面--%>
		<list:listview id="listview">
			<ui:source type="AjaxJson">
				{url:'/dbcenter/echarts/db_echarts_chart/dbEchartsChart.do?method=list&fdKey=${JsParam.fdKey}&fdModelName=${JsParam.fdModelName}&orderby=docCreateTime&ordertype=down'}
			</ui:source>
			<%--列表视图--%>
			<list:colTable isDefault="true" layout="sys.ui.listview.columntable" 
			     rowHref="/dbcenter/echarts/db_echarts_chart/dbEchartsChart.do?method=view&fdId=!{fdId}&chartDefault=1">
				<list:col-checkbox></list:col-checkbox>
				<list:col-serial></list:col-serial>
				<list:col-auto props="docSubject,dbEchartsTemplate.fdName,createMode,docCreateTime,docCreator.fdName,operations"></list:col-auto>
			</list:colTable>
		</list:listview>
		<br>
		<!-- 分页 -->
	 	<list:paging/>
	 	<script type="text/javascript">
		 	seajs.use(['lui/jquery', 'lui/dialog', 'lui/topic'], function($, dialog, topic) {

				// 监听新建更新等成功后刷新
				topic.subscribe('successReloadPage', function() {
					var modelName = '${JsParam.fdModelName}';
					if(modelName && modelName == 'com.landray.kmss.sys.modeling.base.model.ModelingAppModel'){
						topic.publish("list.refresh");
					}
				});

		 		/**------ 新建  统计图表  ------ **/
		 		window.addChart = function(fdType){
		 			var url = "/dbcenter/echarts/db_echarts_chart/dbEchartsChart_mode.jsp?fdKey=${JsParam.fdKey}&fdModelName=${JsParam.fdModelName}&fdTemplateId="+cateId;
					var height = "455";
					var width = "600";
		 			dialog.iframe(url,"${ lfn:message('dbcenter-echarts:chart.mode.chooseMode') }",null,{width:width,height : height});
		 		};
		 		
		 		/**------ 修改  统计图表  ------ **/
		 		window.edit = function(id){
					if(id){
						Com_OpenWindow('dbEchartsChart.do?method=edit&fdId='+id);
					}
			 	};
			 	
			 	/**------ 单条数据删除 统计图表  ------**/
			 	window.deleteDoc = function(id){
					dialog.confirm('<bean:message key="page.comfirmDelete"/>',function(isOk){
						if(isOk){
							window.del_load = dialog.loading();
							var delUrl = '<c:url value="/dbcenter/echarts/db_echarts_chart/dbEchartsChart.do?method=delete&fdId="/>'+id;
							if("${JsParam.fdModelName}" === "com.landray.kmss.sys.modeling.base.model.ModelingAppModel") {
								delUrl = '<c:url value="/sys/modeling/base/dbEchartsChart.do?method=delete&fdId="/>'+id;
							}
							$.ajax({
								url: delUrl,
								type: "GET",
								dataType: "json",
								success: delCallback,
								error: delErrorCallback
							});
						}
					});			 		
			 	};
		 		
			 	/**------ 批量删除  统计图表  ------ **/
		 		window.deleteAll = function(){
					var values = [];
				    $("input[name='List_Selected']:checked").each(function(){
						values.push($(this).val());
					});
					if(values.length==0){
						dialog.alert('<bean:message key="page.noSelect"/>');
						return;
					}
					dialog.confirm('<bean:message key="page.comfirmDelete"/>',function(value){
						if(value==true){
							window.del_load = dialog.loading();
							let delUrl = '<c:url value="/dbcenter/echarts/db_echarts_chart/dbEchartsChart.do?method=deleteall"/>';
							if("${JsParam.fdModelName}" === "com.landray.kmss.sys.modeling.base.model.ModelingAppModel") {
								delUrl = '<c:url value="/sys/modeling/base/dbEchartsChart.do?method=deleteall"/>';
							}
							$.ajax({
								url: delUrl,
								type:"POST",
								data:$.param({"List_Selected":values},true),
								dataType:"json",
								success:delCallback,
								error:delErrorCallback
							});
						}
					});
				};
				
				/**------ 删除成功回调函数  ------ **/
				window.delCallback = function(data){
					if(window.del_load!=null){
						window.del_load.hide();
					}
					topic.publish("list.refresh");
					if("${JsParam.fdModelName}" === "com.landray.kmss.sys.modeling.base.model.ModelingAppModel") {
						if(data.datas){
							var url='/sys/modeling/base/listview/config/dialog_relation.jsp';
							dialog.iframe(url, "删除关联模块", function(){
							},{
								width : 600,
								height : 400,
								params : { datas : data.datas}
							});
						}
					}else{
						dialog.result(data);
					}
				};
				
				/**------ 删除失败回调函数  ------ **/
				window.delErrorCallback = function(data){
					var messages=data.responseJSON.message;
					var message=messages[0];
					if(window.del_load!=null){
						window.del_load.hide();
					}
					dialog.alert(message.msg);
					topic.publish("list.refresh");
				}
				
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
	</template:replace>
</template:include>
