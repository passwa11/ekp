<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>

<center id="echart_area_chartset" >
	<div>
		<table class="tb_normal"  width=100%>
			<!-- SQL语句 -->
			<tr class="tr_normal_title">
				<td class="config_title"  align="left">
					${ lfn:message('dbcenter-echarts:chart.mode.programming.dataset.sql') }
				</td>
			</tr>
			<tr>
				<td>
					<c:import charEncoding="UTF-8" url="/dbcenter/echarts/common/query.jsp">
						<c:param name="readOnly" value="true" />
						<c:param name="field" value="fdCode" />
					</c:import>
				</td>
			</tr>
			<!-- 条件参数 -->
			<tr class="tr_normal_title">
				<td class="config_title" align="left">
					${ lfn:message('dbcenter-echarts:chart.mode.programming.dataset.params') }
				</td>
			</tr>
			<tr>
				<td>
					<c:import charEncoding="UTF-8" url="/dbcenter/echarts/common/input.jsp">
						<c:param name="field" value="fdCode" />
					</c:import>
				</td>
			</tr>
			<!-- 返回结果格式转换 -->
			<tr class="tr_normal_title">
				<td class="config_title" align="left">
					${ lfn:message('dbcenter-echarts:chart.mode.programming.dataset.result') }
				</td>
			</tr>
			<tr>
				<td>
					<%@ include file="/dbcenter/echarts/common/output.jsp"%>
				</td>
			</tr>

			<!-- 行统计定义 -->
			<tr class="tr_normal_title">
				<td class="config_title" align="left">
					${ lfn:message('dbcenter-echarts:table.model.rowstats') }
				</td>
			</tr>
			<tr>
				<td>
					<%@ include file="/dbcenter/echarts/common/rowstats.jsp"%>
				</td>
			</tr>

			<!-- 列统计定义 -->
			<tr class="tr_normal_title">
				<td class="config_title" align="left">
					${ lfn:message('dbcenter-echarts:table.model.colstats') }
				</td>
			</tr>
			<tr>
				<td>
					<%@ include file="/dbcenter/echarts/common/colstats.jsp"%>
				</td>
			</tr>

		</table>		
	</div>
</center>