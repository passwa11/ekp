<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/resource/jsp/common.jsp"%>
<%@ taglib uri="/WEB-INF/KmssConfig/sys/lbpmservice/taglib/taglib.tld" prefix="xlang"%>
<%@page import="com.landray.kmss.sys.lbpmservice.taglib.MultiLangTextGroupTag"%>

<table width="590px" id="Label_Tabel">
	<tr LKS_LabelName="<kmss:message key="FlowChartObject.Lang.Label_Base" bundle="sys-lbpm-engine" />">
		<td>
			<table width="100%" class="tb_normal" id="config_table">
				<c:import url="/sys/lbpm/engine/node/common/node_name_attribute.jsp" charEncoding="UTF-8" />
				<tr>
					<td width="100px"><kmss:message key="FlowChartObject.Lang.Node.initSubNodeHandler" bundle="sys-lbpmservice-node-group" /></td>
					<td>
						<input name="wf_initSubNodeHandlerNames" class="inputsgl" style="width:400px" readonly>
						<input name="wf_initSubNodeHandlerIds" type="hidden" orgattr="handlerIds:handlerNames">
						<span id="SPAN_SelectType1">
						<a href="#" onclick="Dialog_Address(false, 'wf_initSubNodeHandlerIds', 'wf_initSubNodeHandlerNames', null, ORG_TYPE_POSTORPERSON);"><kmss:message key="FlowChartObject.Lang.select" bundle="sys-lbpm-engine" /></a>
						</span>
					</td>
				</tr>
				<tr>
					<td width="100px"><kmss:message key="FlowChartObject.Lang.Line.tip" bundle="sys-lbpm-engine" /></td>
					<td width="490px">
					<kmss:message key="FlowChartObject.Lang.Node.initSubNodeHandlerTipInfo" bundle="sys-lbpmservice-node-group" />
					</td>
				</tr>
			</table>
		</td>
	</tr>
	<tr LKS_LabelName="<kmss:message key="FlowChartObject.Lang.Node.subFlowChart" bundle="sys-lbpmservice-node-group" />" LKS_LabelId="subFlowChart">
		<td>
		<table style="width:100%;height:100%;" class="tb_normal">
			<tr>
				<td>
				<textarea name="fdSubFlowContent" style="display:none"></textarea>
				<input type="hidden" name="fdTranProcessXML">
				<iframe style="width:100%;height:398px;" scrolling="no" id="WF_IFrame"></iframe>
				</td>
			</tr>
		</table>
		</td>
	</tr>
</table>

<script>
AttributeObject.SubmitFuns.push(function() {
	var NodeData = AttributeObject.NodeData;
	var initSubNodeHandlerIds = document.getElementsByName("wf_initSubNodeHandlerIds")[0].value;
	var initSubNodeHandlerNames = document.getElementsByName("wf_initSubNodeHandlerNames")[0].value;
	
	if (initSubNodeHandlerIds != null && initSubNodeHandlerIds != "") {
		var initSubNode = FlowChartObject.Nodes.GetNodeById(NodeData["initSubNodeId"]);
		if (initSubNode != null) {
			// 更新初始子节点的处理人
			initSubNode.Data["handlerIds"] = initSubNodeHandlerIds;
			initSubNode.Data["handlerNames"] = initSubNodeHandlerNames;
			return;
		} else {
			// 添加初始子节点到组内
			var subNode = FlowChartObject.Nodes.createSubNode("reviewNode",NodeData.id);
			subNode.Data["groupNodeId"] = NodeData.id;
			subNode.Data["groupNodeType"] = "freeSubFlowNode";
			subNode.Data["handlerIds"] = initSubNodeHandlerIds;
			subNode.Data["handlerNames"] = initSubNodeHandlerNames;
			subNode.SetStatus(FlowChartObject.STATUS_NORMAL);
			NodeData["initSubNodeId"] = subNode.Data.id;
			var data = new KMSSData();
			data.AddBeanData("getOperTypesByNodeService&nodeType=freeFlowReviewNode");
			data = data.GetHashMapArray();
			for(var j=0;j<data.length;j++){
				if(data[j].isDefault=="true"){
					subNode.Data["operations"]["refId"] = data[j].value;
					break;
				}
			}
		}
	} else {
		// 移除初始子节点
		FlowChartObject.Nodes.deleteSubNode(NodeData["initSubNodeId"]);
		NodeData["initSubNodeId"] = null;
	}
});

AttributeObject.Init.AllModeFuns.unshift(function() {
	if (FlowChartObject.IsTemplate == true) {
		Doc_HideLabelById("Label_Tabel","subFlowChart");
		return;
	}
	var NodeData = AttributeObject.NodeData;
	var subNodesXML = '<process><nodes></nodes><lines></lines></process>';
	document.getElementsByName("fdSubFlowContent")[0].value = subNodesXML;
	// 构建初始的空白子流程的processData
	var processData = new Array();
	processData.XMLNODENAME = "process";
	processData.nodes = new Array();
	processData.lines = new Array();
	
	var currX = 200;
	var currY = 60;
	var groupStartNode = FlowChartObject.Nodes.GetNodeById(NodeData.startNodeId);
	var subNodes = new Array();
	var subLines = new Array();
	var loadSubNodeLine = function(node) {
		if (node.Type != "groupStartNode" && node.Type != "groupEndNode") {
			// 子节点移除不必要的属性，避免转换成xml时异常
			if (node.Data.startLines) {
				delete node.Data.startLines;
				delete node.Data.endLines;
			}
			
			node.Data.x = currX;
			node.Data.y = currY;
			currY += 80;
			subNodes.push(node);
		}
		for (var i=0;i<node.LineOut.length;i++) {
			if (Com_ArrayGetIndex(subLines, node.LineOut[i]) == -1) {
				var nextNode = node.LineOut[i].EndNode;
				if (node.Type != "groupStartNode" && nextNode.Type != "groupEndNode") {
					subLines.push(node.LineOut[i]);
				}
				loadSubNodeLine(nextNode);
			}
		}
	};
	// 递归寻找出需要显示在子流程图的子节点和连线，并调整坐标，然后把节点以及连线的信息分别填充到processData的nodes和lines中
	loadSubNodeLine(groupStartNode);
	for (var i=0;i<subNodes.length;i++) {
		subNodes[i].FormatXMLData();
		processData.nodes.push(subNodes[i].Data);
	}
	for (var j=0;j<subLines.length;j++) {
		subLines[j].FormatXMLData();
		processData.lines.push(subLines[j].Data);
	}
	// 构建子流程的xml
	subNodesXML = WorkFlow_BuildXMLString(processData, "process");
	// 成功构建并取得子流程的xml后把子节点的坐标还原
	for (var i=0;i<subNodes.length;i++) {
		subNodes[i].Data.x = - NodeData.x;
		subNodes[i].Data.y = - NodeData.y;
	}
	
	// 填充子流程XML以及流转信息XML到指定隐藏域
	document.getElementsByName("fdSubFlowContent")[0].value = subNodesXML;
	document.getElementsByName("fdTranProcessXML")[0].value = WorkFlow_BuildXMLString(FlowChartObject.StatusData, "process-status", true);
	//iFrame加载子流程图	
	var loadUrl =  '<c:url value="/sys/lbpm/flowchart/page/panel.html" />?edit=false&extend=oa&contentField=fdSubFlowContent&showBar=false&showMenu=false';
	loadUrl += '&template=' + FlowChartObject.IsTemplate;
	loadUrl += '&modelName=' + FlowChartObject.ModelName + '&modelId=' + FlowChartObject.ModelId;
	if (FlowChartObject.StatusData != null) {
		loadUrl += '&statusField=fdTranProcessXML';
	}
	document.getElementById("WF_IFrame").setAttribute("src", loadUrl);
	setTimeout(function(FlowChartObject){
		$("#WF_IFrame").css("width","100%");
		$("#WF_IFrame").css("height","398px");
	},300);
	if (FlowChartObject.IsTemplate == false && FlowChartObject.IsEdit == false) {
		Doc_SetCurrentLabel("Label_Tabel", 2);
	}
});
</script>