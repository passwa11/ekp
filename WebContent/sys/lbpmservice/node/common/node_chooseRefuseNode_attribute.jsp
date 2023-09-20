<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/resource/jsp/common.jsp"%>
<td width="100px">
	<kmss:message key="FlowChartObject.Lang.Node.chooseRefuseNode" bundle="sys-lbpmservice" />
</td>
<td colspan="2">	
	<input name="wf_refuseNodeIds" type="hidden" orgattr="optHandlerIds:optHandlerNames">
	<input name="wf_refuseNodeNames" class="inputsgl" style="width:400px" readonly>
	<span id="SPAN_chooseRefuseNodeSelectType">
	<a href="#" onclick="_Dialog_List_ShowPrevNode('wf_refuseNodeIds', 'wf_refuseNodeNames', ';','', function(){resetDefaultNode();});"><kmss:message key="FlowChartObject.Lang.select" bundle="sys-lbpm-engine" /></a>
	</span><br/>
	<span class="com_help"><kmss:message bundle="sys-lbpmservice" key="lbpmNode.chooseRefuseNode.info" /></span>
</td>

<script type="text/javascript">
//可驳回节点脚本开始
/**
 * 可选的驳回节点
 * splitStr : 值分隔符
 */
function _Dialog_List_ShowPrevNode(idField, nameField, splitStr, isMulField,action){
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
	var allNodeIds = new Array();
	_chooseRefuse_current_nodeId = currentNode.Data.id;
	loadPreNodes(currentNode,nodeIds,allNodeIds);
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
 * 获取当前节点的前面节点
 */
function loadPreNodes(nodeObj,nodeIds,allNodeIds){
	if (nodeObj.LineIn && nodeObj.LineIn.length > 0){
		//获取当前节点的入线
		var flowChartLineIns = nodeObj.LineIn;
		//可能有多条入线
		for (var i = 0; i < flowChartLineIns.length; i++){
			var _flowChartLineIn = flowChartLineIns[i];
			//获取节点
			var _prevNode = _flowChartLineIn.StartNode;
			//获取节点id
			var _nodeId = _flowChartLineIn.StartNode.Data ? _flowChartLineIn.StartNode.Data.id : "";
			//获取节点名称
			//var _nodeName = _flowChartLineIn.StartNode.Data ? _flowChartLineIn.StartNode.Data.XMLNODENAME : "";
			var _nodeName = _flowChartLineIn.StartNode.Data ? _flowChartLineIn.StartNode.Data.name : "";
			//防止出现死循环
			if ($.inArray(_nodeId,allNodeIds) > -1){
				continue;
			}else{
				allNodeIds.push(_nodeId);
			}
			if ($.inArray(_nodeId,nodeIds) > -1 || $.isEmptyObject(_prevNode) || _nodeId === _chooseRefuse_current_nodeId ){
				continue;
			}else if (_prevNode.Type === "signNode" || _prevNode.Type === "reviewNode" || _prevNode.Type === "draftNode" || _prevNode.Type === "robtodoNode"){
				var _nodeText = _prevNode.Text;
				var _tmp = new Object;
				_tmp.nodeId = _nodeId;
				//_tmp.nodeText = _nodeText;
				_tmp.nodeText = _nodeName;
				nodeIds.push(_tmp);
			}
			loadPreNodes(_prevNode,nodeIds,allNodeIds);
		}
	}
};

//重置默认可驳加值
function resetDefaultNode()
{
	//获取可驳回的节点
	var elementNode  = document.getElementsByName('wf_refuseNodeIds');
	var orignVlaue = [];
	if(elementNode != null && elementNode != '')
	{
		elementValue = document.getElementsByName('wf_refuseNodeIds')[0];
		if(elementValue != null && elementValue != '')
		{
			var reFuseNodeValue = document.getElementsByName('wf_refuseNodeIds')[0].value;
			if(reFuseNodeValue != null && reFuseNodeValue != '')
			{
				orignVlaue = reFuseNodeValue.split(";");
			}
		}
	}
	//获取默认的节点
	if(orignVlaue.length > 0)
	{
		var deVlaue = [];
		var deElementNode  = document.getElementsByName('wf_deRefuseNodeIds');
		if(deElementNode != null && deElementNode != '')
		{
			var deElementValue = document.getElementsByName('wf_deRefuseNodeIds')[0];
			if(deElementValue != null && deElementValue != '')
			{
				var deFuseNodeValue = document.getElementsByName('wf_deRefuseNodeIds')[0].value;
				if(deFuseNodeValue != null && deFuseNodeValue != '')
				{

					if($.inArray(deFuseNodeValue,orignVlaue) < 0)  //可驳回节点中不包含默认可驳回节点，默认可驳回清空
					{
						$("input[name=wf_deRefuseNodeNames]").val("");
						$("input[name=wf_deRefuseNodeIds]").val("");
					}
				}
			}
		}	
	}
};
//可驳回节点脚本结束
</script>