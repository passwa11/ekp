<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%-- {xAxis:{key,unique,order:normal|desc}, series:[{name,key,type:line|bar...,xKey,defaultValue,stack,group,forY2}]} --%>
<script>
DocList_Info.push("series_DocList");
</script>
<b>${ lfn:message('dbcenter-echarts:xData') }</b>
<table class="config_table" width="100%">
<tr>
	<td>
		<div class="config_item">
			${ lfn:message('dbcenter-echarts:canshu') }:<input name="xAxis.key" class="inputsgl" data-dbecharts-config="fdCode" validate="required" subject="${ lfn:message('dbcenter-echarts:xShujucanshu') }">
		</div>
	</td>
	<td>
		<div class="config_item">
			<label><input name="xAxis.unique" type="checkbox" data-dbecharts-config="fdCode">&nbsp;${ lfn:message('dbcenter-echarts:quchuchongfuzhi') }</label>
		</div>
	</td>
	<td>
		<div class="config_item">
			${ lfn:message('dbcenter-echarts:chongxinpaixu') }:
			<select name="xAxis.order" data-dbecharts-config="fdCode">
				<option value="">${ lfn:message('dbcenter-echarts:buchuli') }</option>
				<option value="normal">${ lfn:message('dbcenter-echarts:shunxu') }</option>
				<option value="desc">${ lfn:message('dbcenter-echarts:nixu') }</option>
			</select>
		</div>
	</td>
	<td>
		<div class="config_item2">
			URL：<input name="chart.url" class="inputsgl" data-dbecharts-config="fdCode" subject="URL" placeholder="${ lfn:message('dbcenter-echarts:url_placeholder') }" style="width:400px;">
		</div>
	</td>
	<td>
		<div class="config_item">
			<label><input name="chart.inner" type="checkbox" data-dbecharts-config="fdCode">&nbsp;${ lfn:message('dbcenter-echarts:neibutubiao') }</label>
		</div>
	</td>
</tr>
</table>
<br><b>${ lfn:message('dbcenter-echarts:yData') }</b>
<table id="series_DocList" class="tb_normal" width="100%" data-dbecharts-table="fdCode">
	<tr class="tr_normal_title">
		<td style="width:3%;">${ lfn:message('dbcenter-echarts:xuhao') }</td>
		<td style="width:8%;">${ lfn:message('dbcenter-echarts:mingcheng') }</td>
		<td style="width:12%;">${ lfn:message('dbcenter-echarts:canshu') }</td>
		<td style="width:10%;">${ lfn:message('dbcenter-echarts:zhanxian') }</td>
		<td style="width:15%;">${ lfn:message('dbcenter-echarts:shujubuquan') }</td>
		<td style="width:10%;">${ lfn:message('dbcenter-echarts:fenzucanshu') }</td>
		<td style="width:12%;">${ lfn:message('dbcenter-echarts:duiji') }</td>
		<td style="width:8%;">${ lfn:message('dbcenter-echarts:youY') }</td>
		<td style="width:12%;">${ lfn:message('dbcenter-echarts:tuxingxuanxiang') }</td>
		<td style="width:10%;">
			<a href="#" onclick="DocList_AddRow();return false;">${ lfn:message('dbcenter-echarts:tianjia') }</a>
		</td>
	</tr>
	<!--基准行-->
	<tr KMSS_IsReferRow="1" style="display:none">
		<td KMSS_IsRowIndex="1"></td>
		<td><center>
			<input name="series[!{index}].name" class="inputsgl" style="width:100px;" data-dbecharts-config="fdCode">
		</center></td>
		<td><center>
			<input name="series[!{index}].key" class="inputsgl" style="width:160px;" data-dbecharts-config="fdCode" validate="required" subject="${ lfn:message('dbcenter-echarts:yCanshu') }">
		</center></td>
		<td><center>
			<select name="series[!{index}].type" data-dbecharts-config="fdCode">
				<option value="line">${ lfn:message('dbcenter-echarts:zhexiantu') }</option>
				<option value="linearea">${ lfn:message('dbcenter-echarts:mianjitu') }</option>
				<option value="bar">${ lfn:message('dbcenter-echarts:zhuzhuangtu') }</option>
				<option value="pie">${ lfn:message('dbcenter-echarts:bingtu') }</option>
				<option value="gauge">${ lfn:message('dbcenter-echarts:yibiaopan') }</option>
				<option value="none">${ lfn:message('dbcenter-echarts:shujuxulie') }</option>
			</select>
		</center></td>
		<td><div style="text-align: right; margin-right:4px;">
			${ lfn:message('dbcenter-echarts:xCanshu') }:<input name="series[!{index}].xKey" class="inputsgl" style="width:80px;" data-dbecharts-config="fdCode">
			<br>
			${ lfn:message('dbcenter-echarts:morenzhi') }:<input name="series[!{index}].defaultValue" class="inputsgl" style="width:80px;" data-dbecharts-config="fdCode">
		</div></td>
		<td><center>
			<input name="series[!{index}].group" class="inputsgl" style="width:99%;" data-dbecharts-config="fdCode">
		</center></td>
		<td><center>
			<input name="series[!{index}].stack" class="inputsgl" style="width:99%;" data-dbecharts-config="fdCode" placeholder="${ lfn:message('dbcenter-echarts:zhixiangtongshiduiji') }">
		</center></td>
		<td><center>
			<label><input type="checkbox" name="series[!{index}].forY2" data-dbecharts-config="fdCode">&nbsp;${ lfn:message('dbcenter-echarts:shi') }</label>
		</center></td>
		<td><center>
			<textarea name="series[!{index}].properties" style="width:99%; height:40px;" data-dbecharts-config="fdCode"></textarea>
		</center></td>
		<td><center>
			<a href="#" onclick="DocList_DeleteRow();return false;">${ lfn:message('dbcenter-echarts:shanchu') }</a>
			<a href="#" onclick="DocList_MoveRow(-1);return false;">${ lfn:message('dbcenter-echarts:shangyi') }</a>
			<a href="#" onclick="DocList_MoveRow(1);return false;">${ lfn:message('dbcenter-echarts:xiayi') }</a>
		</center></td>
	</tr>
</table>