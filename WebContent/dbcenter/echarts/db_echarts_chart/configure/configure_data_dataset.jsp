<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ include file="/dbcenter/echarts/common/configure/jsp/SQLCommonJS.jsp"%>
<%@ include file="/dbcenter/echarts/common/configure/jsp/echartCommonJS.jsp"%>
<%@page import="com.landray.kmss.dbcenter.echarts.util.ConfigureUtil" %>
<%@page import="net.sf.json.JSONObject" %>

<!-- 数据集配置区域 -->
<center id="echart_config_area_dataset" >
	<div style="display:inline-block;*display:inline;*zoom:1;width:95%;">
		<table class="tb_normal dbEcharts_Configure_Table_dataset" width=100%> 

			<!-- 数据过滤 -->
			<tr>
				<td class="td_normal_title" width=15%>${ lfn:message('dbcenter-echarts:dbEcharts.echart.configure.dataFilter') }</td>
				<td>
					<table class="tb_normal whereConditionTable" style="width:100%;">
						<tr class="dbEcharts_Configure_Table_Title">
							<td width="32%">${ lfn:message('dbcenter-echarts:dbEcharts.echart.configure.fieldName') }</td>
							<td width="10%">${ lfn:message('dbcenter-echarts:dbEcharts.echart.configure.operator') }</td>
							<td width="30%">${ lfn:message('dbcenter-echarts:dbEcharts.echart.configure.fieldVal') }</td>
							<td width="15%">${ lfn:message('dbcenter-echarts:dbEcharts.echart.configure.dataFormat') }</td>
							<td width="13%">
								<a href="javascript:void(0);" onclick="dbEchartsChart_AddRow(this,'where');" style="color:#1b83d8;">
									${ lfn:message('dbcenter-echarts:dbEcharts.echart.configure.add') }
								</a>
							</td>
						</tr>
					</table>
				</td>
			</tr>

			<!-- 筛选项 -->
			<tr>
				<td class="td_normal_title" width=15%>${ lfn:message('dbcenter-echarts:dbEcharts.echart.configure.filterItems') }</td>
				<td>
					<table class="tb_normal filterItemTable" style="width:100%;">
						<tr class="dbEcharts_Configure_Table_Title">
							<td width="25%">${ lfn:message('dbcenter-echarts:dbEcharts.echart.configure.fieldName') }</td>
							<td width="20%">${ lfn:message('dbcenter-echarts:dbEcharts.echart.configure.presentation.name') }</td>
							<td width="11%">${ lfn:message('dbcenter-echarts:dbEcharts.echart.configure.dataFormat') }</td>
							<td width="11%">${ lfn:message('dbcenter-echarts:dbEcharts.echart.configure.presentation.form') }</td>
							<td width="20%">${ lfn:message('dbcenter-echarts:dbEcharts.echart.configure.defaultVal') }</td>
							<td width="13%">
								<a href="javascript:void(0);" onclick="dbEchartsChart_AddRow(this,'filter');" style="color:#1b83d8;">
									${ lfn:message('dbcenter-echarts:dbEcharts.echart.configure.add') }
								</a>
							</td>
						</tr>
					</table>
				</td>
			</tr>

			<!-- 返回值 -->
			<tr>
				<td class="td_normal_title" width=15%>${ lfn:message('dbcenter-echarts:dbEcharts.echart.configure.returnVal') }</td>
				<td>
					<table class="tb_normal selectValueTable" style="width:98%;display:inline-table;" data-issummary ="true">
						<tr class="dbEcharts_Configure_Table_Title">
							<td width="45%">${ lfn:message('dbcenter-echarts:dbEcharts.echart.configure.fieldName') }</td>
							<td width="21%">${ lfn:message('dbcenter-echarts:dbEcharts.echart.configure.summaryType') }</td>
							<td width="21%">${ lfn:message('dbcenter-echarts:dbEcharts.echart.configure.dataFormat') }</td>
							<td width="13%">
								<a href="javascript:void(0);" onclick="dbEchartsChart_AddRow(this,'select');" style="color:#1b83d8;">
									${ lfn:message('dbcenter-echarts:dbEcharts.echart.configure.add') }
								</a>
							</td>
						</tr>
					</table>
				</td>
			</tr>
			
		</table>			
	</center>
