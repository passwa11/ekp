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
			<ui:button text="${ lfn:message('button.save') }" onclick="submitForm('save');"></ui:button>
			<ui:button text="${ lfn:message('button.saveadd') }" onclick="submitForm('saveadd');"></ui:button>
			<ui:button text="${ lfn:message('button.close') }" onclick="Com_CloseWindow();"></ui:button>
		</ui:toolbar>
	</template:replace>
	<template:replace name="content">
	<link rel="stylesheet" type="text/css" href="${KMSS_Parameter_ContextPath}dbcenter/echarts/db_echarts_chart/configure/css/listview.css">
		<p class="txttitle"><bean:message bundle="dbcenter-echarts" key="chart.mode.configure.title"/></p>

		<html:form action="/dbcenter/echarts/db_echarts_chart/dbEchartsChart.do">
			<ui:step id="__step" style="background-color:#f2f2f2" >
				<ui:content title="${ lfn:message('dbcenter-echarts:dbEcharts.echart.configure.chooseChart') }" toggle="true">
					<div id="chartsTypeListView" class="lui-chartConfig-container" data-lui-type="dbcenter/echarts/db_echarts_chart/configure/js/chartsListView!chartsListView">
						<div data-lui-type="lui/data/source!Static" style="display:none;">	
							<script type="text/code">
								{
									"charts" : <%=chartsType%> 
								}
							</script>
						</div>
					</div>
				</ui:content>
				
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
				function _validatorElements(elIds){
					var arr = elIds.split(";");
					var ret = true;
					for(var i=0;i<arr.length;i++){
						var el= document.getElementsByName(arr[i])[0];
						if(!g_validator.validateElement(el)){
							ret = false;
						}
					}
					return ret;
				}

				// 所有的图表类型
				var __chartsType = <%=chartsType%>;
				seajs.use(['lui/topic'], function(topic) {
					// 下一步事件
					topic.subscribe('JUMP.STEP', function(evt){
						/*  evt = {'last' : xxx,'cur' : xx}; */
						// 切换时校验
						// 当切换图表选择时，需要确认
						if(evt.cur == '0' && (evt.last == '1' || evt.last == '2'||evt.last == '3'||evt.last == '4')){
							evt.cancel = !confirm("${ lfn:message('dbcenter-echarts:dbEcharts.echart.configure.changeChartWarning') }");
						}else if(evt.cur == '1' && evt.last == '0'){
							// 当切换到数据配置时，确保选择了图形
							var listView = LUI("chartsTypeListView");
							if(!listView.isChosen){
								alert("${ lfn:message('dbcenter-echarts:dbEcharts.echart.configure.plzChooseChartType') }");
								evt.cancel = true;
							}
							// chartStyle需要更新样式选项
							topic.channel("dbcenterchart").publish("chart.select",{"item":listView.curItem});
					}
					
					if(evt.last == '1' && evt.cur!='0' ){
						var configType = $("input[type='radio'][name='fdConfigType']:checked").val();  
						if(configType == "01"){  /** 系统数据配置   **/
							$('[name="table.modelNameText"]').attr("validate","required");
						}else{
							$('[name="table.modelNameText"]').attr("validate","");
						}
						if(!_validatorElements("docSubject;fdDbEchartsTemplateName;table.modelNameText")) {
								evt.cancel = true;
								return false;
						}
						return true;
					}

					// chartStyle需要更新样式选项，添加延时是因为切换上一步再切换下一步的时候，页面还没切换过去，echart组件无法获取准确的宽高，导致图表缩小
					if(evt.cur == '4' ){
						var listView = LUI("chartsTypeListView");
						setTimeout(function(){
							topic.channel("dbcenterchart").publish("chart.select",{"item":listView.curItem});
						},100);
					}

					});
				});
			</script>
		</html:form>

	</template:replace>
</template:include>