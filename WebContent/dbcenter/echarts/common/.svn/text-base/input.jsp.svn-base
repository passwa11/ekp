<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%-- {inputs:[{name, key, outer, value, split, criteria, format:String|Boolean|Integer|Double|Date|Time|DateTime|User, argument}]} --%>
<script>
DocList_Info.push("inputs_DocList");
</script>
<table id="inputs_DocList" class="tb_normal" width="100%" data-dbecharts-table="${HtmlParam.field}">
	<tr class="tr_normal_title">
		<td style="width:3%;">${ lfn:message('dbcenter-echarts:input_hint_001')}
		</td>
		<td style="width:7%;">${ lfn:message('dbcenter-echarts:input_hint_002')}
		<a href="javascript:_showHelpLogInfo('inputNameHelp');"><span id="inputNameHelp" class="glyphicon glyphicon-question-sign" style="color:#4898d5"></span></a>						
		</td>
		<td style="width:7%;">${ lfn:message('dbcenter-echarts:input_hint_003')}
		<a href="javascript:_showHelpLogInfo('inputKeyHelp');"><span id="inputKeyHelp" class="glyphicon glyphicon-question-sign" style="color:#4898d5"></span></a>						
		</td>
		<td style="width:7%;">${ lfn:message('dbcenter-echarts:input_hint_004')}
		<a href="javascript:_showHelpLogInfo('inputFormatHelp');"><span id="inputFormatHelp" class="glyphicon glyphicon-question-sign" style="color:#4898d5"></span></a>						
		</td>
		<td style="width:10%;">${ lfn:message('dbcenter-echarts:input_hint_005')}
		<a href="javascript:_showHelpLogInfo('inputValueHelp');"><span id="inputValueHelp" class="glyphicon glyphicon-question-sign" style="color:#4898d5"></span></a>						
		</td>
		<td style="width:10%;">${ lfn:message('dbcenter-echarts:input_hint_000')}
		<a href="javascript:_showHelpLogInfo('inputForumals');"><span id="inputForumals" class="glyphicon glyphicon-question-sign" style="color:#4898d5"></span></a>						
		</td>
		<td style="width:13%;">${ lfn:message('dbcenter-echarts:input_hint_006')}
		<a href="javascript:_showHelpLogInfo('inputArgumentHelp');"><span id="inputArgumentHelp" class="glyphicon glyphicon-question-sign" style="color:#4898d5"></span></a>						
		</td>
		<td style="width:7%;">${ lfn:message('dbcenter-echarts:input_hint_007')}
		<a href="javascript:_showHelpLogInfo('inputSplitHelp');"><span id="inputSplitHelp" class="glyphicon glyphicon-question-sign" style="color:#4898d5"></span></a>						
		</td>
		<td style="width:12%;">${ lfn:message('dbcenter-echarts:input_hint_008')}
		<a href="javascript:_showHelpLogInfo('inputCriteriaHelp');"><span id="inputCriteriaHelp" class="glyphicon glyphicon-question-sign" style="color:#4898d5"></span></a>						
		</td>
		<td style="width:7%;">${ lfn:message('dbcenter-echarts:input_hint_009')}
		<a href="javascript:_showHelpLogInfo('inputOuterHelp');"><span id="inputOuterHelp" class="glyphicon glyphicon-question-sign" style="color:#4898d5"></span></a>						
		</td>
		<td style="width:7%;">${ lfn:message('dbcenter-echarts:input_hint_025')}
		<a href="javascript:_showHelpLogInfo('inputHideHelp');"><span id="inputHideHelp" class="glyphicon glyphicon-question-sign" style="color:#4898d5"></span></a>						
		</td>
		<td style="width:10%;">
			<a href="#" onclick="DocList_AddRow();return false;">${ lfn:message('dbcenter-echarts:input_hint_010')}</a>
		</td>
	</tr>
	<!--基准行-->
	<tr KMSS_IsReferRow="1" style="display:none">
		<td KMSS_IsRowIndex="1"></td>
		<td><center>
			<input name="inputs[!{index}].name" class="inputsgl" style="width:99%;" data-dbecharts-config="${HtmlParam.field}">
		</center></td>
		<td><center>
		<!--
			<select name="inputs[!{index}].key"  class="inputsgl" style="width:90%;" data-dbecharts-config="${HtmlParam.field}"  validate="required" subject="${ lfn:message('dbcenter-echarts:input_hint_011')}">
			</select>
		-->
			<input name="inputs[!{index}].key"  class="inputsgl" style="width:99%;" data-dbecharts-config="${HtmlParam.field}" validate="required" subject="${ lfn:message('dbcenter-echarts:input_hint_011')}">
		</center></td>
		
		<td><center>
			<select name="inputs[!{index}].format" data-dbecharts-config="${HtmlParam.field}">
				<option value="String">${ lfn:message('dbcenter-echarts:input_hint_012')}</option>
				<option value="Boolean">${ lfn:message('dbcenter-echarts:input_hint_013')}</option>
				<option value="Integer">${ lfn:message('dbcenter-echarts:input_hint_014')}</option>
				<option value="Double">${ lfn:message('dbcenter-echarts:input_hint_015')}</option>
				<option value="Date">${ lfn:message('dbcenter-echarts:input_hint_016')}</option>
				<option value="Time">${ lfn:message('dbcenter-echarts:input_hint_017')}</option>
				<option value="DateTime">${ lfn:message('dbcenter-echarts:input_hint_018')}</option>
				<option value="User">${ lfn:message('dbcenter-echarts:input_hint_019')}</option>
				<option value="Like">${ lfn:message('dbcenter-echarts:input_hint_026')}</option>
				<option value="">${ lfn:message('dbcenter-echarts:input_hint_020')}</option>
			</select>
		</center></td>
		<td><center>
			<input name="inputs[!{index}].value" class="inputsgl" id="initValue!{index}" style="width:99%;" data-dbecharts-config="${HtmlParam.field}" onchange='checkValue(this.value,!{index})'>
		</center></td>
		<td><center>
			<input name="inputs[!{index}].expressionText1"  type="text" readonly class="inputsgl"  size='10' data-dbecharts-config="fdCode" 
			 subject="${ lfn:message('dbcenter-echarts:table.model.rowstats.col.formal') }">
			<input name="inputs[!{index}].expressionValue1"   type="hidden" data-dbecharts-config="fdCode">
			<span class='highLight' ><a href='javascript:selectFormulas(!{index});' id="ddd!{index}">设置</a></span>
		</center></td>
		<td><center>
			<input name="inputs[!{index}].argument" class="inputsgl" expressionValue1="expressionValue!{index}" style="width:99%;" data-dbecharts-config="${HtmlParam.field}">
		</center></td>
		<td><center>
			<input name="inputs[!{index}].split" class="inputsgl" style="width:99%;" data-dbecharts-config="${HtmlParam.field}">
		</center></td>
		<td><center>
			<textarea name="inputs[!{index}].criteria" style="width:99%; height:40px;" data-dbecharts-config="${HtmlParam.field}"></textarea>
		</center></td>
		<td><center>
			<label><input type="checkbox" name="inputs[!{index}].outer"  data-dbecharts-config="${HtmlParam.field}">&nbsp;${ lfn:message('dbcenter-echarts:input_hint_021')}</label>
		</center></td>
		<td><center>
			<label><input type="checkbox"  name="inputs[!{index}].hide" data-dbecharts-config="${HtmlParam.field}">&nbsp;${ lfn:message('dbcenter-echarts:input_hint_021')}</label>
		</center></td>
		<td><center>
			<a href="#" onclick="DocList_DeleteRow();return false;">${ lfn:message('dbcenter-echarts:input_hint_022')}</a>
			<a href="#" onclick="DocList_MoveRow(-1);return false;">${ lfn:message('dbcenter-echarts:input_hint_023')}</a>
			<a href="#" onclick="DocList_MoveRow(1);return false;">${ lfn:message('dbcenter-echarts:input_hint_024')}</a>
		</center></td>
	</tr>
</table>
