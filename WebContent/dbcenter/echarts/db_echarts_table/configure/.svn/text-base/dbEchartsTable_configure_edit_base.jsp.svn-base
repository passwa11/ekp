<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
			<table class="tb_normal" width=100%> 
				<tr>
					<td class="td_normal_title" width=15%>
						<bean:message bundle="dbcenter-echarts" key="dbEchartsTable.docSubject"/>
					</td><td width="85%">
						<xform:text property="docSubject" style="width:98%" />
					</td>
				</tr>
				<tr>
					<td class="td_normal_title" width=15%>
						${ lfn:message('dbcenter-echarts:dbEcharts.echart.table.caregory') }
					</td>
					<td width="85%">
						<xform:dialog required="true" subject="${lfn:message('dbcenter-echarts:dbEchartsTable.dbEchartsTemplate') }" propertyId="fdDbEchartsTemplateId" style="width:50%" propertyName="fdDbEchartsTemplateName" dialogJs="dbEcharts_treeDialog()">
						</xform:dialog>
					</td>
				</tr>
				<!-- 数据来源 -->
				<tr>
					<td class="td_normal_title" width=15%>${ lfn:message('dbcenter-echarts:dbEcharts.echart.table.dataSource') }</td>
					<td>
						<c:import charEncoding="UTF-8" url="/dbcenter/echarts/common/configure/jsp/model.jsp">
							<c:param name="callback" value="initSQLStructure"></c:param>
							<c:param name="fdModelName" value="${dbEchartsTableForm.fdModelName}"></c:param>
							<c:param name="fdKey" value="${dbEchartsTableForm.fdKey}"></c:param>
						</c:import>
					</td>
				</tr>
				<tr>
					<td class="td_normal_title" width=15%>
						${ lfn:message('dbcenter-echarts:dbEcharts.echart.table.listTitle') }
					</td>
					<td>
						<input class="inputsgl" name="chartOption.text" style="width:50%;" data-dbecharts-config="fdCode">
					</td>
				</tr>
				<tr>
					<td class="td_normal_title" width=15%>
						${ lfn:message('dbcenter-echarts:dbEcharts.echart.table.listSubTitle') }
					</td>
					<td>
						<input class="inputsgl" name="chartOption.subText" style="width:50%;" data-dbecharts-config="fdCode">
					</td>
				</tr>
			</table>
				