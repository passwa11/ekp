<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<br>
<table class="tb_normal lbpmToolsTable" width="90%">
	<tr>
		<td width=20%><bean:message bundle="sys-lbpmservice-support" key="lbpmTools.nodeTmeout.type" /></td>
		<td width="80%">
			<sunbor:enums property="nodeTimeoutType" enumsType="lbpm_tool_node_timeout_type"
					htmlElementProperties="onchange='nodeTimeoutTypeChange(this.value);' style='border-radius:4px;height:25px;width:100%;box-sizing:content-box'" elementType="select"/>
		</td>
	</tr>
	<tr class="nodeNameTr">
		<td width=20%><bean:message bundle="sys-lbpmservice-support" key="lbpmTools.nodeName" /></td>
		<td width="80%">
			<input type="text" name="nodeTimeoutNodeName" subject="${ lfn:message('sys-lbpmservice-support:lbpmTools.nodeName') }" style="width:98%" class="inputSgl" placeholder="${ lfn:message('sys-lbpmservice-support:lbpmTools.nodeNameDesc') }">
		</td>
	</tr>
	<tr>
		<td width=20%><bean:message bundle="sys-lbpmservice-support" key="lbpmTools.range" /></td>
		<td width="80%">
			<!-- 被选中的模板ID集合“;”分割 -->
			<input type="hidden" name="nodeTimeoutCateIds"/>
			<input type="hidden" name="nodeTimeoutCateNames"/>
			<input type="hidden" name="nodeTimeoutModelNames"/>
			<input type="hidden" name="nodeTimeoutModuleNames"/>
			<input type="hidden" name="nodeTimeoutTemplateIds"/>
			<input type="hidden" name="nodeTimeoutTemplateNames"/>
			<input type="hidden" name="nodeTimeoutTemplateId">
			<input type="text" name="nodeTimeoutTemplateName" readOnly style="width:88%" class="inputSgl" placeholder="${ lfn:message('sys-lbpmservice-support:lbpmTools.rangeDesc') }">
			<a href="javascript:void(0);" onclick="lbpmToolsSelectSubFlow('nodeTimeoutTemplateId','nodeTimeoutTemplateName','nodeTimeoutCateIds','nodeTimeoutCateNames','nodeTimeoutModelNames','nodeTimeoutModuleNames','nodeTimeoutTemplateIds','nodeTimeoutTemplateNames');" style="color:#666"><kmss:message key="FlowChartObject.Lang.Node.select" bundle="sys-lbpmservice" /></a>
			</br>
		</td>
	</tr>
	<tr>
		<td width=20%><bean:message bundle="sys-lbpmservice-support" key="lbpmTools.nodeTmeout.time" /></td>
		<td width="80%">
			<xform:text property="dayOfUpdate" showStatus="edit" className="inputsgl" style="text-align:center" value="0" htmlElementProperties="size='3' onkeyup='controlNumber(this)' onafterpaste='controlNumber(this)'"/>
			<kmss:message key="FlowChartObject.Lang.Node.day" bundle="sys-lbpmservice" />
			<xform:text property="hourOfUpdate" showStatus="edit" className="inputsgl" style="text-align:center" value="0" htmlElementProperties="size='3' onkeyup='controlNumber(this)' onafterpaste='controlNumber(this)'"/>
			<kmss:message key="FlowChartObject.Lang.Node.hour" bundle="sys-lbpmservice" />
			<xform:text property="minuteOfUpdate" showStatus="edit" className="inputsgl" style="text-align:center" value="0" htmlElementProperties="size='3' onkeyup='controlNumber(this)' onafterpaste='controlNumber(this)'"/>
			<kmss:message key="FlowChartObject.Lang.Node.minute" bundle="sys-lbpmservice" />
			<span id="repeatConfig" style="line-height:25px;">
				<br><label><input name="repeatDayOfNotify" type="checkbox" value="true" onclick="showRepeatConfig(this.checked);"><kmss:message key="FlowChartObject.Lang.Node.repeat" bundle="sys-lbpmservice" /></label>&nbsp;&nbsp;
				<span id="repeatConfigDiv" style="display:none">
					<input name="repeatTimesDayOfNotify" class="inputsgl" value="1" size="3" style="text-align:center" onkeyup="this.value = ((value=value.replace(/\D/g,''))==''? value : parseInt(this.value.replace(/\D/g,''),10))">
					<kmss:message key="FlowChartObject.Lang.Node.times" bundle="sys-lbpmservice" />&nbsp;&nbsp;
					<kmss:message key="FlowChartObject.Lang.Node.interval" bundle="sys-lbpmservice" />
					<input name="intervalDayOfNotify" class="inputsgl" value="0" size="3" style="text-align:center" onkeyup="controlNumber(this)" onafterpaste="controlNumber(this)">
						<kmss:message key="FlowChartObject.Lang.Node.day" bundle="sys-lbpmservice" />
					<input name="intervalHourOfNotify" class="inputsgl" value="0" size="3" style="text-align:center" onkeyup="controlNumber(this)" onafterpaste="controlNumber(this)">
						<kmss:message key="FlowChartObject.Lang.Node.hour" bundle="sys-lbpmservice" />
					<input name="intervalMinuteOfNotify" class="inputsgl" value="0" size="3" style="text-align:center" onkeyup="controlNumber(this)" onafterpaste="controlNumber(this)">
						<kmss:message key="FlowChartObject.Lang.Node.minute" bundle="sys-lbpmservice" />
				</span>
			</span>
		</td>
	</tr>
	<tr>
		<td colspan="2" style="text-align: center;">
			<br>
		 	<ui:button text="${ lfn:message('sys-lbpmservice-support:lbpmTools.button.search') }" style="width:150px" onclick="searchNodeTimeout();"></ui:button>
		</td>
	</tr>
</table>
<br>
<script type="text/javascript">
function controlNumber(obj){
	obj.value=(parseInt((obj.value=obj.value.replace(/\D/g,''))==''||parseInt((obj.value=obj.value.replace(/\D/g,''))==0)?'0':obj.value,10));
}

function nodeTimeoutTypeChange(value){
	if(value=="8"||value=="9"){
		$(".nodeNameTr").hide();
		$("input[name='nodeTimeoutNodeName']").val('');
	}else{
		$(".nodeNameTr").show();
	}
	if(value=="1"){
		$("#repeatConfig").show();
	}else{
		$("#repeatConfig").hide();
	}
}

function showRepeatConfig(checked){
	if (checked == true) {
		$('#repeatConfigDiv').show();
	} else {
		$('#repeatConfigDiv').hide();
	}
}

seajs.use(['lui/jquery', 'lui/dialog'], function($, dialog) {
	window.searchNodeTimeout = function() {
		window.del_load = dialog.loading();
		var nodeTimeoutType = $("select[name='nodeTimeoutType']").val();
		var dayOfUpdate = $("input[name='dayOfUpdate']").val();
		var hourOfUpdate = $("input[name='hourOfUpdate']").val();
		var minuteOfUpdate = $("input[name='minuteOfUpdate']").val();
		$("#lbpmToolDiv").show();
		var myIframe=LUI("lbpmToolIframe");
		if(nodeTimeoutType=="8"||nodeTimeoutType=="9"){
			myIframe.src='${LUI_ContextPath}/sys/lbpmservice/support/lbpm_tools/node_timeout/lbpm_tool_node_timeout2_list.jsp?cateIds='+$("input[name='nodeTimeoutCateIds']").val()+'&modelNames='+$("input[name='nodeTimeoutModelNames']").val()+'&templateIds='+$("input[name='nodeTimeoutTemplateIds']").val()+'&nodeName='+encodeURIComponent($("input[name='nodeTimeoutNodeName']").val())+'&nodeTimeoutType='+nodeTimeoutType+'&dayOfUpdate='+dayOfUpdate+'&hourOfUpdate='+hourOfUpdate+'&minuteOfUpdate='+minuteOfUpdate+'&more=2';
		}else{
			var url='${LUI_ContextPath}/sys/lbpmservice/support/lbpm_tools/node_timeout/lbpm_tool_node_timeout_list.jsp?cateIds='+$("input[name='nodeTimeoutCateIds']").val()+'&modelNames='+$("input[name='nodeTimeoutModelNames']").val()+'&templateIds='+$("input[name='nodeTimeoutTemplateIds']").val()+'&nodeName='+encodeURIComponent($("input[name='nodeTimeoutNodeName']").val())+'&nodeTimeoutType='+nodeTimeoutType+'&dayOfUpdate='+dayOfUpdate+'&hourOfUpdate='+hourOfUpdate+'&minuteOfUpdate='+minuteOfUpdate;
			if(nodeTimeoutType=="1"){
				var repeatDayOfNotify = $("input[name='repeatDayOfNotify']").is(':checked');
				url+='&repeatDayOfNotify='+repeatDayOfNotify;
				if(repeatDayOfNotify){
					var repeatTimesDayOfNotify = $("input[name='repeatTimesDayOfNotify']").val();
					var intervalDayOfNotify = $("input[name='intervalDayOfNotify']").val();
					var intervalHourOfNotify = $("input[name='intervalHourOfNotify']").val();
					var intervalMinuteOfNotify = $("input[name='intervalMinuteOfNotify']").val();
					url+='&repeatTimesDayOfNotify='+repeatTimesDayOfNotify+'&intervalDayOfNotify='+intervalDayOfNotify+'&intervalHourOfNotify='+intervalHourOfNotify+'&intervalMinuteOfNotify='+intervalMinuteOfNotify;
				}
			}
			myIframe.src = url;
		}
		myIframe.reload();
	}
});
</script>