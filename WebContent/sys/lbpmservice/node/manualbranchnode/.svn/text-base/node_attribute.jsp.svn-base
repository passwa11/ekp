<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/resource/jsp/common.jsp"%>

<table width="590px" id="Label_Tabel">
	<tr LKS_LabelName="<kmss:message key="FlowChartObject.Lang.Label_Base" bundle="sys-lbpm-engine" />">
		<td>
			<table width="100%" class="tb_normal" id="config_table">
				<c:import url="/sys/lbpm/engine/node/common/node_name_attribute.jsp" charEncoding="UTF-8" />
				<!-- 起草节点处理勾选项 -->
				<tr id="decidedBranchOnDraftTr">
					<td width="100px"><kmss:message key="FlowChartObject.Lang.Node.nodeOptions" bundle="sys-lbpmservice" /></td>
					<td>
					<label>
						<input name="wf_decidedBranchOnDraft" type="checkbox" value="true">
						<kmss:message key="FlowChartObject.Lang.Node.manualBarnchNode_decidedBranchOnDraft" bundle="sys-lbpmservice-node-manualbranchnode" />
					</label>
					</td>
				</tr>
				<!-- 默认分支 -->
				<tr>
					<td width="100px"><kmss:message key="FlowChartObject.Lang.Node.manualBarnchNode_defaultBranch" bundle="sys-lbpmservice-node-manualbranchnode" /></td>
					<td>
					<label>
						<select id="wf_defaultBranch" name="wf_defaultBranch">
						</select>
					</td>
				</tr>
			</table>
		</td>
	</tr>
	<tr LKS_LabelName="<kmss:message key="FlowChartObject.Lang.Label_Event" bundle="sys-lbpm-engine" />">
		<td>
		<c:import url="/sys/lbpm/flowchart/page/node_event_attribute.jsp" charEncoding="UTF-8" />
		</td>
	</tr>
</table>
<script>
AttributeObject.Init.AllModeFuns.push(initBranchSelectObj);

function initBranchSelectObj() {
	if(FlowChartObject.IsEmbedded){
		$("#decidedBranchOnDraftTr").hide();
	}
	var manualBranchNode = AttributeObject.NodeObject;
	var select = document.getElementById("wf_defaultBranch");
	select.options.add(new Option("=== " + FlowChartObject.Lang.pleaseSelect + " ===", ""));
	if (manualBranchNode.LineOut && manualBranchNode.LineOut.length > 0) {
		for (var i = 0; i < manualBranchNode.LineOut.length; i++) {
			var line = manualBranchNode.LineOut[i];
			select.options.add(new Option((line.Text && line.Text!="") ? (line.Text + "(" + line.EndNode.Data.id + "." + line.EndNode.Data.name + ")") : (line.EndNode.Data.id + "." + line.EndNode.Data.name), line.Data.id));
		}
	}
	var selected = manualBranchNode.Data["defaultBranch"];
	if (selected && selected != "") {
		for (var i = 0; i < select.options.length; i ++) {
			var opt = select.options[i];
			if (opt.value == selected) {
				opt.selected = true;
				return;
			}
		}
	}
	AttributeObject.NodeObject.Data["defaultBranch"] = "";
}
</script>