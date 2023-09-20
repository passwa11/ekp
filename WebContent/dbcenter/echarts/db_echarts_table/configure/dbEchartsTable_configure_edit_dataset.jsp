<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
	<div>
		<table class="tb_normal dbEcharts_Configure_Table">
			<!-- 数据过滤 -->
			<tr>
				<td class="td_normal_title" width=15%>${ lfn:message('dbcenter-echarts:dbEcharts.echart.table.dataFilter') }</td>
				<td>
					<table class="tb_normal whereConditionTable" style="width:100%;">
						<tr class="dbEcharts_Configure_Table_Title">
							<td width="34%">${ lfn:message('dbcenter-echarts:dbEcharts.echart.table.fieldName') }</td>
							<td width="10%">${ lfn:message('dbcenter-echarts:dbEcharts.echart.table.operator') }</td>
							<td width="30%">${ lfn:message('dbcenter-echarts:dbEcharts.echart.table.fieldVal') }</td>
							<td width="13%">${ lfn:message('dbcenter-echarts:dbEcharts.echart.table.dataFormat') }</td>
							<td width="13%">
								<a href="javascript:void(0);" onclick="dbEchartsChart_AddRow(this,'where');" style="color:#1b83d8;">
									${ lfn:message('dbcenter-echarts:dbEcharts.echart.table.add') }
								</a>
							</td>
						</tr>
					</table>
				</td>
			</tr>
			<!-- 筛选项 -->
			<tr>
				<td class="td_normal_title" width=15%>${ lfn:message('dbcenter-echarts:dbEcharts.echart.table.filter') }</td>
				<td>
					<table class="tb_normal filterItemTable" style="width:100%;">
						<tr class="dbEcharts_Configure_Table_Title">
							<td width="25%">${ lfn:message('dbcenter-echarts:dbEcharts.echart.table.fieldName') }</td>
							<td width="14%">${ lfn:message('dbcenter-echarts:dbEcharts.echart.configure.presentation.name') }</td>
							<td width="13%">${ lfn:message('dbcenter-echarts:dbEcharts.echart.configure.dataFormat') }</td>
							<td width="13%">${ lfn:message('dbcenter-echarts:dbEcharts.echart.configure.presentation.form') }</td>
							<td width="22%">${ lfn:message('dbcenter-echarts:dbEcharts.echart.table.defaultVal') }</td>
							<td width="13%">
								<a href="javascript:void(0);" onclick="dbEchartsChart_AddRow(this,'filter');" style="color:#1b83d8;">
									${ lfn:message('dbcenter-echarts:dbEcharts.echart.table.add') }
								</a>
							</td>
						</tr>
					</table>
				</td>
			</tr>
			<!-- 返回值 -->
			<tr>
				<td class="td_normal_title" width=15%>${ lfn:message('dbcenter-echarts:dbEcharts.echart.table.returnVal') }</td>
				<td>
					<table class="tb_normal selectValueTable" style="width:98%;display:inline-table;">
						<tr class="dbEcharts_Configure_Table_Title">
							<td width="45%">${ lfn:message('dbcenter-echarts:dbEcharts.echart.table.fieldName') }</td>
							<td width="42%">${ lfn:message('dbcenter-echarts:dbEcharts.echart.table.dataFormat') }</td>
							<td width="13%">
								<a href="javascript:void(0);" onclick="dbEchartsChart_AddRow(this,'select');" style="color:#1b83d8;">
									${ lfn:message('dbcenter-echarts:dbEcharts.echart.table.add') }
								</a>
							</td>
						</tr>
					</table>
				</td>
			</tr>

			<!-- 行统计定义 -->
			<tr>
				<td class="td_normal_title" width=15%>${ lfn:message('dbcenter-echarts:table.model.rowstats') }</td>
				<td>
					<table class="tb_normal rowstatsTable" style="width:98%;display:inline-table;">
						<tr class="dbEcharts_Configure_Table_Title">
							<td width="15%">${ lfn:message('dbcenter-echarts:table.model.rowstats.col.label') }</td>
							<td width="15%">${ lfn:message('dbcenter-echarts:table.model.rowstats.col.name') }</td>
							<td width="30%">${ lfn:message('dbcenter-echarts:table.model.rowstats.col..formal') }</td>
							<td width="17%">${ lfn:message('dbcenter-echarts:dbEcharts.echart.table.dataFormat') }</td>
							<td width="10%">${ lfn:message('dbcenter-echarts:table.model.rowstats.col.sort') }</td>
							<td width="13%">
								<a href="javascript:void(0);" onclick="dbEchartsChart_AddRow(this,'rowstats');" style="color:#1b83d8;">
									${ lfn:message('dbcenter-echarts:dbEcharts.echart.table.add') }
								</a>
							</td>
						</tr>
					</table>
				</td>
			</tr>

			<!-- 列统计定义 -->
			<tr>
				<td class="td_normal_title" width=15%>${ lfn:message('dbcenter-echarts:table.model.colstats') }</td>
				<td>
					<table class="tb_normal colstatsTable" style="width:98%;display:inline-table;">
						<tr class="dbEcharts_Configure_Table_Title">
							<td width="57%">${ lfn:message('dbcenter-echarts:table.model.colstats.col') }</td>
							<td width="15%">${ lfn:message('dbcenter-echarts:dbEcharts.echart.configure.summaryType') }</td>
							<td width="15%">${ lfn:message('dbcenter-echarts:dbEcharts.echart.table.dataFormat') }</td>
							<td width="13%">
								<a href="javascript:void(0);" onclick="dbEchartsChart_AddRow(this,'colstats');" style="color:#1b83d8;">
									${ lfn:message('dbcenter-echarts:dbEcharts.echart.table.add') }
								</a>
							</td>
						</tr>
					</table>
				</td>
			</tr>

		</table>
	</div>
