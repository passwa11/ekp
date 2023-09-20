<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>

<!-- 图表属性配置区域 -->
<center id="echart_config_area_chartset" >
	<div style="display:inline-block;*display:inline;*zoom:1;width:95%;">
		<table class="tb_normal dbEcharts_Configure_Table_chartset"  width=100%>
			<tr>
				<td class="td_normal_title" width=15%>${ lfn:message('dbcenter-echarts:dbEcharts.echart.configure.category') }</td>
				<td>
					<table class="tb_normal categoryTable" style="width:98%;display:inline-table;">
						<tr class="dbEcharts_Configure_Table_Title">
							<td width="35">${ lfn:message('dbcenter-echarts:dbEcharts.echart.configure.fieldName') }</td>
							<td width="20%">${ lfn:message('dbcenter-echarts:dbEcharts.echart.configure.dataFormat') }</td>
							<td width="15%">${ lfn:message('dbcenter-echarts:dbEcharts.echart.configure.sort') }</td>
							<td width="20%">>${ lfn:message('dbcenter-echarts:dbEcharts.echart.configure.data.quantity') }</td>
							<td width="10%">
								<a href="javascript:void(0);" onclick="dbEchartsChart_AddRow(this,'category');" data-pair="category" style="color:#1b83d8;">
									${ lfn:message('dbcenter-echarts:dbEcharts.echart.configure.add') }
								</a>
							</td>
						</tr>
					</table>
				</td>
			</tr>
			<tr>
				<td class="td_normal_title" width=15%>${ lfn:message('dbcenter-echarts:dbEcharts.echart.configure.dataSeris') }</td>
				<td>
					<table class="tb_normal seriesTable" style="width:98%;display:inline-table;">
						<tr class="dbEcharts_Configure_Table_Title">
							<td width="45%">${ lfn:message('dbcenter-echarts:dbEcharts.echart.configure.fieldName') }</td>
							<td width="45%">${ lfn:message('dbcenter-echarts:dbEcharts.echart.configure.dataFormat') }</td>
							<td width="10%">
								<a href="javascript:void(0);" onclick="dbEchartsChart_AddRow(this,'series');" data-pair="series" style="color:#1b83d8;">
									${ lfn:message('dbcenter-echarts:dbEcharts.echart.configure.add') }
								</a>
							</td>
						</tr>
					</table>
				</td>
			</tr>
			
			<tr>
				<td class="td_normal_title" width=15%>${ lfn:message('dbcenter-echarts:dbEcharts.echart.configure.axisName') }</td>
				<td>
					<div class="XYAxisWrap">
						<label>${ lfn:message('dbcenter-echarts:dbEcharts.echart.configure.XaxisName') }</label> <input name="chartOption.xAxis[0].name" class='inputsgl' style="width:30%;" type="text" data-dbecharts-config="fdCode" />
						&nbsp;&nbsp;&nbsp;
						<label>${ lfn:message('dbcenter-echarts:dbEcharts.echart.configure.YaxisName') }</label> <input name="chartOption.yAxis[0].name" class='inputsgl' style="width:30%;" type="text" data-dbecharts-config="fdCode" />
					</div>
				</td>
			</tr>

		</table>		
	</div>
</center>
