<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
	<div>
		<!-- 列表视图 -->
		<p class="txttitle2"><span>${ lfn:message('dbcenter-echarts:dbEcharts.echart.table.listView') }</span></p>
		<table class="tb_normal dbEcharts_Configure_Table">
			<tr>
				<td class="td_normal_title" width=15%>${ lfn:message('dbcenter-echarts:dbEcharts.echart.table.viewCol') }</td>
				<td width=85%>
					<table class="tb_normal listViewTable" style="width:100%;">
						<tr class="dbEcharts_Configure_Table_Title">
							<td width="20%">${ lfn:message('dbcenter-echarts:dbEcharts.echart.table.returnVal') }</td>
							<td width="30%">${ lfn:message('dbcenter-echarts:dbEcharts.echart.table.col') }</td>
							<td width="10%">${ lfn:message('dbcenter-echarts:dbEcharts.echart.table.colWidth') }</td>
							<td width="10%">${ lfn:message('dbcenter-echarts:dbEcharts.echart.table.align') }</td>
							<td width="10%">${ lfn:message('dbcenter-echarts:dbEcharts.echart.table.hidden') }</td>
							<td width="10%">${ lfn:message('dbcenter-echarts:dbEcharts.echart.table.order') }</td>
							<td width="10%">${ lfn:message('dbcenter-echarts:dbEcharts.echart.table.operation') }</td>
						</tr>
					</table>
				</td>
			</tr>
		</table>
		
		<!-- 列表样式 -->
		<p class="txttitle2"><span>${ lfn:message('dbcenter-echarts:dbEcharts.echart.table.style') }</span></p>
		<table class="tb_normal" width=100%>
			<!-- 列表宽度 -->
			<tr>
				<td class="td_normal_title" width=15%>${ lfn:message('dbcenter-echarts:dbEcharts.echart.table.width') }</td>
				<td>
					<label class="checkboxlabel"><input name="listview.isAdapterWidth" type="checkbox" data-dbecharts-config="fdCode" onclick="dbEchartsTable_CheckBoxChangeDom(this,'listview_width','none','inline-block');"/>&nbsp;${ lfn:message('dbcenter-echarts:dbEcharts.echart.table.resize') }</label>
					<div class="listview_width" style="display:none;">
						<span>${ lfn:message('dbcenter-echarts:dbEcharts.echart.table.widthVal') }</span>
						<input name="listview.width" class='inputsgl' validate="digits min(1)" subject="${ lfn:message('dbcenter-echarts:dbEcharts.echart.table.widthVal') }" style="width:50px;" type="text" data-dbecharts-config="fdCode" />px
					</div>
				</td>
			</tr>
			<!-- 列表翻页 -->
			<tr>
				<td class="td_normal_title" width=15%>${ lfn:message('dbcenter-echarts:dbEcharts.echart.table.listPaging') }</td>
				<td>
					<label class="checkboxlabel"><input name="listview.page" type="checkbox" data-dbecharts-config="fdCode" onclick="dbEchartsTable_CheckBoxChangeDom(this,'listview_rowSize','inline-block','none');"/>&nbsp;${ lfn:message('dbcenter-echarts:dbEcharts.echart.table.paging') }</label>
					<div class="listview_rowSize" style="display:inline-block;">
						<span>${ lfn:message('dbcenter-echarts:dbEcharts.echart.table.showNum') }</span>
						<input name="listview.rowSize" style="width:30px" class='inputsgl' type="text" data-dbecharts-config="fdCode" />${ lfn:message('dbcenter-echarts:dbEcharts.echart.table.strip') }
					</div>
				</td>
			</tr>
			<!-- 列表排序 -->
			<tr>
				<td class="td_normal_title" width=15%>${ lfn:message('dbcenter-echarts:dbEcharts.echart.table.listOrder') }</td>
				<td>
					<label class="checkboxlabel"><input name="listview.isCustomSort" type="checkbox" data-dbecharts-config="fdCode" onclick="dbEchartsTable_CustomSortChange(this);"/>&nbsp;${ lfn:message('dbcenter-echarts:dbEcharts.echart.table.customOrder') }</label>
					<div class="listview_orderby" style="display:none;">
						<span>${ lfn:message('dbcenter-echarts:dbEcharts.echart.table.orderField') }</span>
						<select name="listview.sort" data-dbecharts-config="fdCode" onchange="dbEchartsTable_SortValidate(this);"></select>
						&nbsp;&nbsp;&nbsp;
						<span>${ lfn:message('dbcenter-echarts:dbEcharts.echart.table.orderType') }</span>
						<select name="listview.sorttype" data-dbecharts-config="fdCode">
							<option value="asc">${ lfn:message('dbcenter-echarts:dbEcharts.echart.table.asc') }</option>
							<option value="desc">${ lfn:message('dbcenter-echarts:dbEcharts.echart.table.des') }</option>
						</select>
					</div>
				</td>
			</tr>
			
			<!-- 列表汇总 -->
			 <input type="hidden" value="0" name="listview.listSummary"  data-dbecharts-config="fdCode">
			<!--
			<tr>
				<td class="td_normal_title" width=15%>列表汇总</td>
				<td>
				   <div class="listview_listSummary">
					  <input type="radio" value="1" name="listview.listSummary"  data-dbecharts-config="fdCode"> <span>开启</span>&nbsp;&nbsp;&nbsp;
					   <input type="radio" value="0" name="listview.listSummary"  data-dbecharts-config="fdCode" checked="checked"> <span>关闭</span>
					   &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<font style="color:#778899">汇总功能只会针对字段类型为"数字"的进行汇总</font>
				   </div>
				   
				</td>
			</tr>
			-->
		</table>
	</div>
