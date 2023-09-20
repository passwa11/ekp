<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include ref="config.view">
	<template:replace name="toolbar">
		<ui:toolbar id="toolbar" layout="sys.ui.toolbar.float" var-navwidth="95%">
			<ui:button text="${ lfn:message('button.close') }" onclick="Com_CloseWindow();"></ui:button>	
		</ui:toolbar>
	</template:replace>
	<template:replace name="content">
<script>
	Com_IncludeFile("config.css", "${LUI_ContextPath}/dbcenter/echarts/common/", "css", true);
</script>
<style type="text/css">
 .scenario_help_content{
    margin-left: 16px !important;
    margin-right: 10px;
 }
 .scenario_analysis_table{
    width: 100%;
    margin-top: 12px;
 }
 .scenario_analysis_table .title_row{
    background-color: #f6f6f6;
 }
 .scenario_analysis_table .title_row .title_1{
    text-align: center;
 }
 .scenario_analysis_table .title_row .title_2{
    padding-left: 20px;
 }
 .scenario_analysis_table .title_row .title_3{
    text-align: center;
 }
 .scenario_analysis_table .data_content_row{
    height: 60px;
    border-bottom: 1px solid #d2d2d2;
 }
 .scenario_analysis_table .data_content_row .data_col_1{
    padding-left: 10px;
    padding-right: 10px;
    text-align: center;
 }
 .scenario_help_content .scenario_analysis_tip{
   margin-top: 10px;
 }
 
 .choose_guide_content{
    margin-left: 16px !important;
    margin-right: 10px;
    text-align: center;
 }
 
 .chart_type_icon_div{
    float: left;
    margin-right: 12px;
    width: 40px;
    height: 36px;
 }
</style>
<center>
<br>
<table class="tb_normal" width=95%> 
	<tr class="tr_normal_title">
		<td colspan="4" class="config_title">
			${ lfn:message('dbcenter-echarts:chart.help.chooseChartDesc') }
		</td>
	</tr>
	<tr>
		<td colspan="4">
		
		    <!------------------- 特点概述  start ------------------->
			<div class="help_title">${ lfn:message('dbcenter-echarts:chart.help.featuresDesc') }</div>
			<div class="help_content">
				<ul>
					<li><b>${ lfn:message('dbcenter-echarts:chart.help.pie') }</b>： 
					${ lfn:message('dbcenter-echarts:chart.help.pieDesc') };
					</li>
					<li><b>${ lfn:message('dbcenter-echarts:chart.help.line') }</b>： 
					${ lfn:message('dbcenter-echarts:chart.help.lineDesc') };
					</li>
					<li><b>${ lfn:message('dbcenter-echarts:chart.help.area') }</b>： 
					${ lfn:message('dbcenter-echarts:chart.help.areaDesc') };
					</li>
					<li><b>${ lfn:message('dbcenter-echarts:chart.help.bar') }</b>： 
					${ lfn:message('dbcenter-echarts:chart.help.barDesc') };
					</li>
					<li><b>${ lfn:message('dbcenter-echarts:chart.help.gauge') }</b>： 
					${ lfn:message('dbcenter-echarts:chart.help.gaugeDesc') };
					</li>															
				</ul>
			</div>
			<!------------------- 特点概述  end ------------------->
			
			<!------------------- 场景分析   start ------------------->
			<div class="help_title">${ lfn:message('dbcenter-echarts:chart.help.sceneAnalysis') }</div>
			<div class="help_content scenario_help_content">
                <table class="scenario_analysis_table">
                     <tr class="title_row">
                       <td width="20%" class="config_title title_1" >${ lfn:message('dbcenter-echarts:chart.help.dataRelation') }</td>
                       <td width="40%" class="config_title title_2" >${ lfn:message('dbcenter-echarts:chart.help.applyExample') }</td>
                       <td width="40%" class="config_title title_3" >${ lfn:message('dbcenter-echarts:chart.help.chartType') }</td>
                     </tr>
                     <tr class="data_content_row">
                       <td class="data_col_1">${ lfn:message('dbcenter-echarts:chart.help.categoryCompare') }</td>
                       <td class="data_col_2">${ lfn:message('dbcenter-echarts:chart.help.salesComparison') }</td>
                       <td class="data_col_3">
	                       <div class="chart_type_icon_div"><img src="images/icon-u56.png"></div>
	                       <div class="chart_type_icon_div"><img src="images/icon-u58.png"></div>
                       </td>
                     </tr>
                     <tr class="data_content_row">
                       <td class="data_col_1">${ lfn:message('dbcenter-echarts:chart.help.sequentially') }</td>
                       <td class="data_col_2">${ lfn:message('dbcenter-echarts:chart.help.applyExample1') }</td>
                       <td class="data_col_3">
	                       <div class="chart_type_icon_div"><img src="images/icon-u62.png"></div>
	                       <div class="chart_type_icon_div"><img src="images/icon-u64.png"></div>
	                       <div class="chart_type_icon_div"><img src="images/icon-u56.png"></div>
                       </td>
                     </tr>  
                     <tr class="data_content_row">
                       <td class="data_col_1">${ lfn:message('dbcenter-echarts:chart.help.applyExample2') }</td>
                       <td class="data_col_2"><div>${ lfn:message('dbcenter-echarts:chart.help.applyExample3') }</div><div>${ lfn:message('dbcenter-echarts:chart.help.applyExample4') }</div></td>
                       <td class="data_col_3">
	                       <div class="chart_type_icon_div"><img src="images/icon-u70.png"></div>
	                       <div class="chart_type_icon_div"><img src="images/icon-u58.png"></div>
	                       <div class="chart_type_icon_div"><img src="images/icon-u74.png"></div>
	                       <div class="chart_type_icon_div"><img src="images/icon-u76.png"></div>
	                   </td>    
                     </tr>  
                     <tr class="data_content_row">
                       <td class="data_col_1">${ lfn:message('dbcenter-echarts:chart.help.applyExample5') }</td>
                       <td class="data_col_2">${ lfn:message('dbcenter-echarts:chart.help.applyExample6') }</td>
                       <td class="data_col_3">
	                       <div class="chart_type_icon_div"><img src="images/icon-u56.png"></div>
                       </td>
                     </tr> 
                     <tr class="data_content_row">
                       <td class="data_col_1">${ lfn:message('dbcenter-echarts:chart.help.applyExample7') }</td>
                       <td class="data_col_2">${ lfn:message('dbcenter-echarts:chart.help.applyExample8') }</td>
                       <td class="data_col_3">
	                       <div class="chart_type_icon_div"><img src="images/icon-u56.png"></div>
                       </td>
                     </tr>                                                                               
                </table>
                <div class="scenario_analysis_tip">${ lfn:message('dbcenter-echarts:chart.help.applyExampleDesc') }</div>
			</div>
			<!------------------- 场景分析   end ------------------->
			
			<!------------------- 图表类型选择指南  start ------------------->
			<div class="help_title">${ lfn:message('dbcenter-echarts:chart.help.chartTypeGuide') }</div>
			<div class="help_content choose_guide_content">
			  <img src="images/chart-choose-guide.png" >
			</div>
			<!------------------- 图表类型选择指南  end ------------------->
		</td>
	</tr>
</table>
<br>
</center>
	</template:replace>
</template:include>