<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%-- 基准行 仿标准明细表 图表中心定制--%>
<tr KMSS_Db_IsReferRow="1" style="display:none">
	<td colspan="2" style="padding:0px">
		<table class="tb_normal SQLStructureTable" style="width:100%;border:0px;">
			<tbody>
				<tr>
					<td class="td_normal_title" colspan="2" align="center">
						<div style="width:100%;position:relative;">
							<div style="text-align:center;"><span class="db_serial"></span></div>
							<div class="opera_del" style="text-align:right;position:absolute;right:0;top:0;cursor:pointer;">
								<span onclick="dbEchartsCustom_DelRow(this);">${ lfn:message('dbcenter-echarts:dbEcharts.echart.custom.dele') }</span>
							</div>
						</div>
					</td>
				</tr>
				<tr>
					<td class="td_normal_title" width=15%>${ lfn:message('dbcenter-echarts:dbEcharts.echart.custom.dataSource2') }</td>
					<td>
						<div class="inputselectsgl" onclick="selectModelNameDialog(this);" style="width: 30%">
							<input name="tables[!{index}].modelName" value="" type="hidden" data-dbecharts-config="fdCode">
							<div class="input">
								<input subject="${ lfn:message('dbcenter-echarts:dbEcharts.echart.custom.dataSource') }" name="tables[!{index}].modelNameText" type="text" data-dbecharts-config="fdCode" validate="required" readonly="">
							</div>
							<div class="selectitem"></div>
						</div>
						<span class="txtstrong">*</span>
					</td>
				</tr>
				<!-- 返回值 -->
				<tr>
					<td class="td_normal_title" width=15%>${ lfn:message('dbcenter-echarts:dbEcharts.echart.custom.returnVal') }</td>
					<td>
						<table class="tb_normal selectValueTable" style="width:98%;display:inline-table;" data-issummary ="true">
							<tr class="dbEcharts_Configure_Table_Title">
								<td width="45%">${ lfn:message('dbcenter-echarts:dbEcharts.echart.custom.fieldName') }</td>
								<td width="15%">${ lfn:message('dbcenter-echarts:dbEcharts.echart.custom.summary') }</td>
								<td width="30%">${ lfn:message('dbcenter-echarts:dbEcharts.echart.custom.dataFormat') }</td>
								<td width="10%">
									<a href="javascript:void(0);" onclick="dbEchartsCustom_AddRow(this,'select');" style="color:#1b83d8;">
										${ lfn:message('dbcenter-echarts:dbEcharts.echart.custom.add') }
									</a>
								</td>
							</tr>
						</table>
					</td>
				</tr>
				<tr>
					<td class="td_normal_title" width=15%>${ lfn:message('dbcenter-echarts:dbEcharts.echart.custom.dataFilter') }</td>
					<td>
						<table class="tb_normal whereConditionTable" style="width:100%;">
							<tr class="dbEcharts_Configure_Table_Title">
								<td width="35%">${ lfn:message('dbcenter-echarts:dbEcharts.echart.custom.fieldName') }</td>
								<td width="10%">${ lfn:message('dbcenter-echarts:dbEcharts.echart.custom.operator') }</td>
								<td width="30%">${ lfn:message('dbcenter-echarts:dbEcharts.echart.custom.fieldVal') }</td>
								<td width="15%">${ lfn:message('dbcenter-echarts:dbEcharts.echart.custom.dataFormat') }</td>
								<td width="10%">
									<a href="javascript:void(0);" onclick="dbEchartsCustom_AddRow(this,'where');" style="color:#1b83d8;">
										${ lfn:message('dbcenter-echarts:dbEcharts.echart.custom.add') }
									</a>
								</td>
							</tr>
						</table>
					</td>
				</tr>
				<!-- 列表排序 -->
				<tr>
					<td class="td_normal_title" width=15%>${ lfn:message('dbcenter-echarts:dbEcharts.echart.custom.fieldOrder') }</td>
					<td>
						<label class="checkboxlabel"><input name="tables[!{index}].listview.isCustomSort" type="checkbox" data-dbecharts-config="fdCode" onclick="dbEchartsCustom_CustomSortChange(this);"/>&nbsp;${ lfn:message('dbcenter-echarts:dbEcharts.echart.custom.customOrder') }</label>
						<div class="listview_orderby" style="display:none;">
							<span>${ lfn:message('dbcenter-echarts:dbEcharts.echart.custom.orderField') }</span>
							<select name="tables[!{index}].listview.sort" data-dbecharts-config="fdCode"></select>
							&nbsp;&nbsp;&nbsp;
							<span>${ lfn:message('dbcenter-echarts:dbEcharts.echart.custom.orderType') }</span>
							<select name="tables[!{index}].listview.sorttype" data-dbecharts-config="fdCode">
								<option value="asc">${ lfn:message('dbcenter-echarts:dbEcharts.echart.custom.asc') }</option>
								<option value="desc">${ lfn:message('dbcenter-echarts:dbEcharts.echart.custom.des') }</option>
							</select>
						</div>
					</td>
				</tr>
			</tbody>
		</table>
	</td>
</tr>
<tr data-coltype="optCol">
	<td colspan="2" class="td_normal_title" align="center"><span style="cursor:pointer;color:#1b83d8" onclick="dbEchartsCustom_AddSource();">${ lfn:message('dbcenter-echarts:dbEcharts.echart.custom.addOtherData') }</span></td>
</tr>