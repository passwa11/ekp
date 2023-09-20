<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%-- {transfers:[{key, outer, value}]} --%>
<script>
DocList_Info.push("transfers_DocList");
</script>
<table id="transfers_DocList" class="tb_normal" width="100%" data-dbecharts-table="${HtmlParam.field}">
	<tr class="tr_normal_title">
		<td style="width:40px;">${ lfn:message('dbcenter-echarts:transfer_hint_001')}</td>
		<td style="width:200px;">${ lfn:message('dbcenter-echarts:transfer_hint_002')}</td>
		<td>${ lfn:message('dbcenter-echarts:transfer_hint_003')}</td>
		<td style="width:60px;">${ lfn:message('dbcenter-echarts:transfer_hint_004')}</td>
		<td style="width:80px;">
			<a href="#" onclick="DocList_AddRow();return false;">${ lfn:message('dbcenter-echarts:transfer_hint_005')}</a>
		</td>
	</tr>
	<!--基准行-->
	<tr KMSS_IsReferRow="1" style="display:none">
		<td KMSS_IsRowIndex="1"></td>
		<td><center>
			<input name="transfers[!{index}].key" class="inputsgl" style="width:99%;" data-dbecharts-config="${HtmlParam.field}" validate="required" subject="${ lfn:message('dbcenter-echarts:transfer_hint_006')}">
		</center></td>
		<td><center>
			<input name="transfers[!{index}].value" class="inputsgl" style="width:99%;" data-dbecharts-config="${HtmlParam.field}">
		</center></td>
		<td><center>
			<label><input type="checkbox" name="transfers[!{index}].outer" data-dbecharts-config="${HtmlParam.field}">&nbsp;${ lfn:message('dbcenter-echarts:transfer_hint_007')}</label>
		</center></td>
		<td><center>
			<a href="#" onclick="DocList_DeleteRow();return false;">${ lfn:message('dbcenter-echarts:transfer_hint_008')}</a>
			<a href="#" onclick="DocList_MoveRow(-1);return false;">${ lfn:message('dbcenter-echarts:transfer_hint_009')}</a>
			<a href="#" onclick="DocList_MoveRow(1);return false;">${ lfn:message('dbcenter-echarts:transfer_hint_010')}</a>
		</center></td>
	</tr>
</table>