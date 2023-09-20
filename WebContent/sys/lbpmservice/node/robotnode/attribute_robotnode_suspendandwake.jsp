<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>

<template:include ref="default.simple" sidebar="auto">
	<template:replace name="body"> 
		<script type="text/javascript"
			src="${LUI_ContextPath}/resource/js/jquery.js?s_cache=${LUI_Cache}"></script>
		<script type="text/javascript">
			Com_IncludeFile("document.js", "style/" + Com_Parameter.Style
					+ "/doc/");
			Com_IncludeFile("docutil.js|doclist.js|dialog.js|data.js|formula.js");
		</script>
		<script type="text/javascript">
			// 必须实现的方法，供父窗口(attribute_robotnode.html)调用。
			function returnValue() {
				if (!check())
					return null;
				
				var rtnJson = new Array();
				rtnJson.push("{");
				var wakeType = $("input[type='radio'][name='wakeType']:checked").val();
				if (wakeType == "countdown") {
					rtnJson.push("\"wakeType\":\"" + wakeType + "\"");
					rtnJson.push(",\"countdownDay\":\""
							+ $("input[name='countdownDay']").val() + "\"");
					rtnJson.push(",\"countdownHour\":\""
							+ $("input[name='countdownHour']").val() + "\"");
					rtnJson.push(",\"countdownMinute\":\""
							+ $("input[name='countdownMinute']").val() + "\"");
				} else {
					
					rtnJson.push("\"wakeType\":\"" + wakeType + "\"");
					
					var certainTimeIdsValue=($("input[name='certainTimeIds']").val()==null? "":$("input[name='certainTimeIds']").val());
					var certainTimeNameValue=($("input[name='certainTimeName']").val()==null? "":$("input[name='certainTimeName']").val());

					$("input[name='certainTimeIds']").val(certainTimeIdsValue.replace(/\n/g, "&br;").replace(/\"/g, "&quot;"));
					$("input[name='certainTimeName']").val(certainTimeNameValue.replace(/\n/g, "&br;").replace(/\"/g, "&quot;"));

					rtnJson.push(",\"certainTimeIds\":\""
							+ $("input[name='certainTimeIds']").val()
							+ "\"");
					rtnJson.push(",\"certainTimeName\":\""
							+ $("input[name='certainTimeName']").val()
							+ "\"");
				}
				// 结束
				rtnJson.push("}");
				return rtnJson.join('');
			};

			function check() {
				var wakeType = $("input[type='radio'][name='wakeType']:checked").val();
				if (wakeType == "countdown") {
					if ($("input[name='countdownDay']").val() != ""
							&& $("input[name='countdownHour']").val() != ""
							&& $("input[name='countdownMinute']").val() != "") {
						if($("input[name='countdownDay']").val()==0 && $("input[name='countdownHour']").val()==0 && $("input[name='countdownMinute']").val()==0){
							alert('<kmss:message key="FlowChartObject.Lang.Node.robot_Lbpm_Wake.Countdown.required" bundle="sys-lbpmservice-node-robotnode" />');
						} else {
							return true;
						}
					}
				} else if (wakeType == "certain") {
						if ($("input[name='certainTimeIds']").val() != ""
								&& $("input[name='certainTimeName']").val() != "") {
							return true;
						}
						alert('<kmss:message key="FlowChartObject.Lang.Node.robot_Lbpm_Wake.CertainTime.required" bundle="sys-lbpmservice-node-robotnode" />');
				} else {
					alert('<kmss:message key="FlowChartObject.Lang.Node.robot_Lbpm_WakeType.required" bundle="sys-lbpmservice-node-robotnode" />');
				}
				return false;
			};
			
			function changeWakeType() {
				var wakeType = $("input[type='radio'][name='wakeType']:checked").val();
				if (wakeType == "countdown") {
					$("#certain").hide();
					$("#countdown").show();
				} else if (wakeType == "certain") {
					$("#countdown").hide();
					$("#certain").show();
				} else {
					$("#certain").hide();
					$("#countdown").hide();
				}
			}

			function selectByFormula(idField, nameField) {
			
				Formula_Dialog(
						idField,
						nameField,
						parent.FlowChartObject.FormFieldList,
						"DateTime",
						null,
						"com.landray.kmss.sys.lbpm.engine.formula.LbpmFunction",
						parent.FlowChartObject.ModelName);
			}

			function init(json) {
				if (json.wakeType == "countdown") {
					$("input[type='radio'][name='wakeType'][value='countdown']")[0].checked = true;
					$("input[name='countdownDay']").val(json.countdownDay);
					$("input[name='countdownHour']").val(json.countdownHour);
					$("input[name='countdownMinute']")
							.val(json.countdownMinute);
				} else if (json.wakeType == "certain") {
					$("input[type='radio'][name='wakeType'][value='certain']")[0].checked = true;
					$("#countdown").hide();
					$("#certain").show();
					$("#wakeType").val("certain");
					$("input[name='certainTimeIds']").val(
							json.certainTimeIds);
					$("input[name='certainTimeName']").val(
							json.certainTimeName);
				} else {
					$("#countdown").show();
					$("#wakeType").val("countdown");
				}
				changeWakeType();
			}

			function initDocument() {
				// 节点原配置的类型不是当前类型或没有获取到相关配置
				if (parent.NodeData.unid != parent.document
						.getElementById("type").value
						|| !parent.NodeContent) {
					return;
				}
				
				// 获得内容对象
				var json = {};

				//输入的自定义代码有换行
				json = eval('(' + parent.NodeContent + ')');

				for(var item in json){
					if(item=="certainTimeIds"||item=="certainTimeName"){
						json[item]=json[item].replace(/&br;/g, "\n").replace(/&quot;/g, "\"");
					}
				}
				
				init(json);
			};
			
			LUI.ready(function(){
				initDocument();
			});
		</script>

		<table width="100%" class="tb_normal">
			<tr>
				<td width="15%"><kmss:message key="FlowChartObject.Lang.Node.robot_Lbpm_WakeType" bundle="sys-lbpmservice-node-robotnode" /></td>
				<td>
					<label><input type="radio" name="wakeType" value="countdown" onclick="changeWakeType();" checked><kmss:message key="FlowChartObject.Lang.Node.robot_Lbpm_WakeType.Countdown" bundle="sys-lbpmservice-node-robotnode" /></label>
					&nbsp;&nbsp;
					<label><input type="radio" name="wakeType" value="certain" onclick="changeWakeType()"><kmss:message key="FlowChartObject.Lang.Node.robot_Lbpm_WakeType.Certain" bundle="sys-lbpmservice-node-robotnode" /></label>
				</td>
			</tr>
			<tr id="countdown">
				<td width="15%"><kmss:message key="FlowChartObject.Lang.Node.robot_Lbpm_Wake.WakeTime" bundle="sys-lbpmservice-node-robotnode" /></td>
				<td><input name="countdownDay" class="inputsgl" value="0"
					size="3" style="text-align: center">
				<kmss:message key="FlowChartObject.Lang.Node.day"
						bundle="sys-lbpmservice" /> <input name="countdownHour"
					class="inputsgl" value="0" size="3" style="text-align: center">
				<kmss:message key="FlowChartObject.Lang.Node.hour"
						bundle="sys-lbpmservice" /> <input name="countdownMinute"
					class="inputsgl" value="0" size="3" style="text-align: center">
				<kmss:message key="FlowChartObject.Lang.Node.minute"
						bundle="sys-lbpmservice" /> <kmss:message key="FlowChartObject.Lang.Node.robot_Lbpm_Wake.Countdown.etc" bundle="sys-lbpmservice-node-robotnode" /></td>
			</tr>
			<tr id="certain" style="display: none">
				<td width="15%"><kmss:message key="FlowChartObject.Lang.Node.robot_Lbpm_Wake.WakeTime" bundle="sys-lbpmservice-node-robotnode" /></td>
				<td><input name="certainTimeName" class="inputsgl"
					style="width: 200px" readonly> <input name="certainTimeIds"
					type="hidden"> <a href="#" style="cursor: pointer;font-size: inherit"
					onclick="selectByFormula('certainTimeIds', 'certainTimeName');"><kmss:message
							key="FlowChartObject.Lang.select" bundle="sys-lbpm-engine" /></a></td>
			</tr>
			<%-- <tr>
				<td width="15%"><kmss:message key="FlowChartObject.Lang.Node.robot_Description" bundle="sys-lbpmservice-node-robotnode" /></td>
				<td><kmss:message key="FlowChartObject.Lang.Node.robot_Lbpm_SuspendAndWake_Help" bundle="sys-lbpmservice-node-robotnode" /></td>
			</tr> --%>
		</table>
	</template:replace>
</template:include>