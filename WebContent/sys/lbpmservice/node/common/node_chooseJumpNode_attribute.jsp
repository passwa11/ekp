<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/resource/jsp/common.jsp"%>
<td width="100px">
	<kmss:message key="FlowChartObject.Lang.Node.chooseJumpNode" bundle="sys-lbpmservice" />
</td>
<td colspan="2">	
	<input name="wf_canHandlerJumpNodeIds" type="hidden" orgattr="optHandlerIds:optHandlerNames">
	<input name="wf_canHandlerJumpNodeNames" class="inputsgl" style="width:400px" readonly>
	<span id="SPAN_chooseJumpNodeSelectType">
	<a href="#" onclick="_Dialog_List_ShowHandlerNode('wf_canHandlerJumpNodeIds', 'wf_canHandlerJumpNodeNames', ';','', function(){});"><kmss:message key="FlowChartObject.Lang.select" bundle="sys-lbpm-engine" /></a>
	</span><br/>
	<span class="com_help"><kmss:message bundle="sys-lbpmservice" key="lbpmNode.chooseJumpNode.info" /></span>
</td>

<script type="text/javascript">
//可跳转节点脚本开始
/**
 * 可选的跳转节点
 * splitStr : 值分隔符
 */
function _Dialog_List_ShowHandlerNode(idField, nameField, splitStr, isMulField,action){
	var dialog = new KMSSDialog(true, true);
	dialog.BindingField(idField, nameField, splitStr, isMulField);
	
	dialog.SetAfterShow(action);
	
	//获取流程中所有节点
	var wfAllNodes = FlowChartObject.Nodes.all;
	//获取当前配置的节点对象
	var currentNode = AttributeObject.NodeObject;
	var data=new KMSSData();
	var nodeIds = new Array();
	var rtnVals = new Array();
	loadHandlerNodes(currentNode,nodeIds,wfAllNodes);
	for (var i = 0; i < nodeIds.length; i++){
		var _tmp = new Array();
		_tmp["id"] = nodeIds[i].nodeId;
		_tmp["name"] = nodeIds[i].nodeId + '' + nodeIds[i].nodeText;
		rtnVals.push(_tmp);
	}
	data.AddHashMapArray(rtnVals);
	dialog.optionData.AddKMSSData(data);
	dialog.Show(window.screen.width*520/1366,window.screen.height*400/768);
};

/**
 * 获取审批节点
 */
function loadHandlerNodes(nodeObj,nodeIds,allNodes){
	if (allNodes){
		for (var i = 0; i < allNodes.length; i++){
			var _node = allNodes[i];
			if(_node.Data.id != nodeObj.Data.id 
					&& _node.Desc.uniqueMark()!=FlowChartObject.NodeDescMap.endNode
					&& _node.Desc.uniqueMark()!=FlowChartObject.NodeDescMap.startNode
					&& _node.Desc.uniqueMark()!=FlowChartObject.NodeDescMap.autoBranchNode
					&& _node.Desc.uniqueMark()!=FlowChartObject.NodeDescMap.manualBranchNode
					&& _node.Desc.uniqueMark()!=FlowChartObject.NodeDescMap.splitNode
					&& _node.Desc.uniqueMark()!=FlowChartObject.NodeDescMap.joinNode
					&& _node.Data.x && _node.Data.x>0){ 
				var _tmp = new Object;
				_tmp.nodeId = _node.Data.id;
				_tmp.nodeText = _node.Data.name;
				nodeIds.push(_tmp);
			}
		}
	}
};
//可跳转节点脚本结束
</script>