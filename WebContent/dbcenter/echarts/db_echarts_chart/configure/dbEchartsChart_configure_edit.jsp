<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ page import="com.landray.kmss.dbcenter.echarts.util.ConfigureUtil"%>
<%@ page import="net.sf.json.JSONObject"%>
<% 
	JSONObject chartsType = ConfigureUtil.getChartsType();
%>
<template:include ref="default.edit">
	<template:replace name="toolbar">
		<ui:toolbar id="toolbar" layout="sys.ui.toolbar.float" var-navwidth="95%">
			<ui:button text="${ lfn:message('dbcenter-echarts:button.help') }" onclick="Com_OpenWindow('${KMSS_Parameter_ContextPath}dbcenter/echarts/chart_config_help.jsp');">
			</ui:button>
			<c:choose>
				<c:when test="${ dbEchartsChartForm.method_GET == 'edit' }">
						<ui:button text="${ lfn:message('button.update') }" onclick="submitForm('update');"></ui:button>
				</c:when>
				<c:when test="${ dbEchartsChartForm.method_GET == 'add' || dbEchartsChartForm.method_GET == 'clone'}">	
						<ui:button text="${ lfn:message('button.update') }" onclick="submitForm('save');"></ui:button>
				</c:when>
			</c:choose>
			<ui:button text="${ lfn:message('button.close') }" onclick="Com_CloseWindow();"></ui:button>
		</ui:toolbar>
	</template:replace>
	<template:replace name="content">
	<kmss:windowTitle moduleKey="dbcenter-echarts:module.dbcenter.piccenter"  subjectKey="dbcenter-echarts:table.dbEchartsChart" subject="${dbEchartsChartForm.docSubject}" />
		<link rel="stylesheet" type="text/css" href="${KMSS_Parameter_ContextPath}dbcenter/echarts/db_echarts_chart/configure/css/listview.css">
		<p class="txttitle"><bean:message bundle="dbcenter-echarts" key="chart.mode.configure.title"/></p>

		<html:form action="/dbcenter/echarts/db_echarts_chart/dbEchartsChart.do">
			<%/*
			<c:import url="/dbcenter/echarts/db_echarts_chart/configure/configure_data.jsp" charEncoding="UTF-8">
			</c:import>
			*/%>

			<ui:step id="__step" style="background-color:#f2f2f2" >
				
				<ui:content title="${ lfn:message('dbcenter-echarts:dbEcharts.echart.configure.chartConfigure.base') }" toggle="true">
					<c:import url="/dbcenter/echarts/db_echarts_chart/configure/configure_data_base.jsp" charEncoding="UTF-8">
					</c:import>
				</ui:content>
		
				<ui:content title="${ lfn:message('dbcenter-echarts:dbEcharts.echart.configure.chartConfigure.dataset') }" toggle="true">
					<c:import url="/dbcenter/echarts/db_echarts_chart/configure/configure_data_dataset.jsp" charEncoding="UTF-8">
					</c:import>
				</ui:content>

				<ui:content title="${ lfn:message('dbcenter-echarts:dbEcharts.echart.configure.chartConfigure.chartset') }" toggle="true">
					<c:import url="/dbcenter/echarts/db_echarts_chart/configure/configure_data_chartset.jsp" charEncoding="UTF-8">
					</c:import>
				</ui:content>

				<ui:content title="${ lfn:message('dbcenter-echarts:dbEcharts.echart.configure.chartConfigure.chartview') }" toggle="true">
					<c:import url="/dbcenter/echarts/db_echarts_chart/configure/configure_data_chartview.jsp" charEncoding="UTF-8">
					</c:import>
				</ui:content>
			
			</ui:step>

			<ui:tabpage expand="false" var-navwidth="90%">
				<!--权限机制 -->
				<c:import url="/sys/right/import/right_edit.jsp"
					charEncoding="UTF-8">
					<c:param name="formName" value="dbEchartsChartForm" />
					<c:param name="moduleModelName"
						value="com.landray.kmss.dbcenter.echarts.model.DbEchartsChart" />
				</c:import>
			</ui:tabpage>

			<script>
				// 所有的图表类型
				var __chartsType = <%=chartsType%>;
				seajs.use(['lui/topic'], function(topic) {
					// 下一步事件
					topic.subscribe('JUMP.STEP', function(evt){
						// chartStyle需要更新样式选项，添加延时是因为切换上一步再切换下一步的时候，页面还没切换过去，echart组件无法获取准确的宽高，导致图表缩小
						if(evt.cur == '3' ){
							setTimeout(function(){
								topic.channel("dbcenterchart").publish("chart.select",{"item": previewChart.chartStyle.curItem});
							},100);
						}
					});
				});

				LUI.ready(function() {
					setTimeout(function(){
							var steps = LUI("__step");
							for(var i=0;i<steps.children.length;i++){
								if(steps.children[i].element){
									steps.next();
								}
							}
							steps.fireJump(0);
					},500);
				});
			</script>

		</html:form>

	</template:replace>
</template:include>