<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%-- listview:{url,page,sort,sorttype} columns:[{name,key,width,align,template,sort,hidden}]} --%>
<script>
DocList_Info.push("columns_DocList");
</script>
<b>${lfn:message('dbcenter-echarts:table_config_apply_01')}</b>
<div class="config_table">
	<div class="config_item2">
		URL：<input name="listview.url" class="inputsgl" data-dbecharts-config="fdCode" subject="URL" placeholder="${lfn:message('dbcenter-echarts:table_config_apply_02')}" style="width:400px;">
	</div>
	<div class="config_item">
		<label><input name="listview.page" type="checkbox" data-dbecharts-config="fdCode">&nbsp;${lfn:message('dbcenter-echarts:table_config_apply_03')}</label>
	</div>
	
	<div class="config_item">
		${lfn:message('dbcenter-echarts:table_config_apply_05')}<input name="listview.sort" class="inputsgl" data-dbecharts-config="fdCode">
	</div>
	<div class="config_item">
		${lfn:message('dbcenter-echarts:table_config_apply_06')}<select name="listview.sorttype" data-dbecharts-config="fdCode">
			<option value="down">${lfn:message('dbcenter-echarts:table_config_apply_07')}</option>
			<option value="up">${lfn:message('dbcenter-echarts:table_config_apply_08')}</option>
		</select>
	</div><br/>
	<div class="config_item">
		<label>${lfn:message('dbcenter-echarts:echart.data.total')}</label>
		 <input type="radio" value="1" name="listview.listSummary"  data-dbecharts-config="fdCode"> <span>${lfn:message('dbcenter-echarts:echart.data.total.open')}</span>&nbsp;&nbsp;&nbsp;
         <input type="radio" value="0" name="listview.listSummary"  data-dbecharts-config="fdCode" checked="checked"> <span>${lfn:message('dbcenter-echarts:echart.data.total.close')}</span>
	</div>
	<div style="line-height: 25px;">
	&nbsp;${lfn:message('dbcenter-echarts:table_config_apply_09')}
	<br>&nbsp;${lfn:message('dbcenter-echarts:table_config_apply_10')}
	<br/>&nbsp;${lfn:message('dbcenter-echarts:echart.data.total.tip')}
	</div>
</div>
<br><b>${lfn:message('dbcenter-echarts:table_config_apply_11')}</b>
<table id="columns_DocList" class="tb_normal" width="100%" data-dbecharts-table="fdCode">
	<tr class="tr_normal_title">
		<td style="width:40px;">${lfn:message('dbcenter-echarts:table_config_apply_12')}</td>
		<td style="width:100px;">${lfn:message('dbcenter-echarts:table_config_apply_13')}</td>
		<td style="width:100px;">${lfn:message('dbcenter-echarts:table_config_apply_15')}</td>
		<td style="width:100px;">${lfn:message('dbcenter-echarts:table_config_apply_16')}</td>
		<td style="width:100px;">${lfn:message('dbcenter-echarts:table_config_apply_17')}</td>
		<td style="width:160px;">${lfn:message('dbcenter-echarts:table_config_apply_18')}</td>
		<td>${lfn:message('dbcenter-echarts:table_config_apply_19')}</td>
		<td style="width:40px;">${lfn:message('dbcenter-echarts:table_config_apply_20')}</td>
		<td style="width:40px;">${lfn:message('dbcenter-echarts:table_config_apply_21')}</td>
		<td style="width:100px;">
			<a href="#" onclick="DocList_AddRow();return false;">${lfn:message('dbcenter-echarts:table_config_apply_22')}</a>
		</td>
	</tr>
	<!--基准行-->
	<tr KMSS_IsReferRow="1" style="display:none">
		<td KMSS_IsRowIndex="1"></td>
		<td><center>
			<input name="columns[!{index}].name" class="inputsgl" style="width:99%;" data-dbecharts-config="fdCode" validate="required" subject="${lfn:message('dbcenter-echarts:table_config_apply_13')}">
		</center></td>
		<td><center>
			<input name="columns[!{index}].key" class="inputsgl" style="width:99%;" data-dbecharts-config="fdCode" validate="required" subject="${lfn:message('dbcenter-echarts:table_config_apply_15')}">
		</center></td>
		<td><center>
			<input name="columns[!{index}].width" class="inputsgl" style="width:99%;" data-dbecharts-config="fdCode">
		</center></td>
		<td><center>
			<select name="columns[!{index}].align" data-dbecharts-config="fdCode">
				<option value="center">${lfn:message('dbcenter-echarts:table_config_apply_26')}</option>
				<option value="left">${lfn:message('dbcenter-echarts:table_config_apply_27')}</option>
				<option value="right">${lfn:message('dbcenter-echarts:table_config_apply_28')}</option>
			</select>
		</center></td>
		<td><center>
			${lfn:message('dbcenter-echarts:table_config_apply_29')}<input name="columns[!{index}].rowKey" class="inputsgl" style="width:80px;" data-dbecharts-config="fdCode"><br>
			${lfn:message('dbcenter-echarts:table_config_apply_30')}<input name="columns[!{index}].colKey" class="inputsgl" style="width:80px;" data-dbecharts-config="fdCode">
		</center></td>
		<td><center>
			<textarea name="columns[!{index}].template" style="width:99%; height:40px;" data-dbecharts-config="fdCode"></textarea>
		</center></td>
		<td><center>
			<input name="columns[!{index}].sort" type="checkbox" data-dbecharts-config="fdCode">
		</center></td>
		<td><center>
			<input name="columns[!{index}].hidden" type="checkbox" data-dbecharts-config="fdCode">
		</center></td>
		<td><center>
			<a href="#" onclick="DocList_DeleteRow();return false;">${lfn:message('dbcenter-echarts:table_config_apply_31')}</a>
			<a href="#" onclick="DocList_MoveRow(-1);return false;">${lfn:message('dbcenter-echarts:table_config_apply_32')}</a>
			<a href="#" onclick="DocList_MoveRow(1);return false;">${lfn:message('dbcenter-echarts:table_config_apply_33')}</a>
		</center></td>
	</tr>
</table>