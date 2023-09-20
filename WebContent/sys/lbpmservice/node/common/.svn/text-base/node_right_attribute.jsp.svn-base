<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" errorPage="/resource/jsp/jsperror.jsp"%>
<%@ include file="/resource/jsp/common.jsp"%>

<script type="text/javascript">

$(function(){
	$(".rightReadTip").mouseover(function(){
		$(".rightReadTipArea").show();
		var left = $(this).offset().left-$(".rightReadTipArea").width()/2+$(this).width()/2;
		var top = $(this).offset().top+23;
		var h = document.documentElement.scrollTop || document.body.scrollTop;
		$(".rightReadTipArea").css({
			"left" : left+40,
		    "top" : top-h
		});
	});
	$(".rightReadTip").mouseout(function(){
		$(".rightReadTipArea").hide();
	});
});

</script>
<style>
.rightReadTipArea:before,.rightReadTipArea:after{
	content:"";
	display:inline-block;
	width:0;
	height:0;
	border: 6px solid transparent;
	border-bottom-color: #EAEDF3;
	position:absolute;
	top: -13px;
	z-index: 1;
	left: 50%;
	transform: translateX(-50%);
}

.rightReadTipArea:after{
	border-bottom-color: #fff;
	margin-top: 1px;
}

.rightReadTipArea{
	display: none;
	list-style: none;
	background: #FFFFFF;
	border: 1px solid #EAEDF3;
	box-shadow: 0 1px 3px 0 rgba(0,0,0,0.04);
	border-radius: 4px;
	width: 130px;
	text-align: center;
	position: fixed;
	z-index: 999;
}
</style>

<table class="tb_normal" width="100%">
	<tr class="embeddedSubFlowExceptTr">
		<td rowspan="2" width="100px"><kmss:message key="FlowChartObject.Lang.Right.modifyNotionPopedomSet" bundle="sys-lbpm-engine" /></td>
		<td width="100px"><kmss:message key="FlowChartObject.Lang.Right.canModifyHandlerNodeNames" bundle="sys-lbpm-engine" /></td>
		<td>
			<input type="hidden" name="wf_canModifyHandlerNodeIds">
			<textarea name="canModifyHandlerNodeNames" style="width:100%;height:50px" readonly></textarea>
			<a href="#" onclick="selectNodes('wf_canModifyHandlerNodeIds', 'canModifyHandlerNodeNames');"><kmss:message key="FlowChartObject.Lang.select" bundle="sys-lbpm-engine" /></a>
		</td>
	</tr>
	<tr class="embeddedSubFlowExceptTr">
		<td width="100px"><kmss:message key="FlowChartObject.Lang.Right.mustModifyHandlerNodeNames" bundle="sys-lbpm-engine" /></td>
		<td>
			<input type="hidden" name="wf_mustModifyHandlerNodeIds">
			<textarea name="mustModifyHandlerNodeNames" style="width:100%;height:50px" readonly></textarea>
			<a href="#" onclick="selectNodes('wf_mustModifyHandlerNodeIds', 'mustModifyHandlerNodeNames');"><kmss:message key="FlowChartObject.Lang.select" bundle="sys-lbpm-engine" /></a>
			<br><span class="com_help"><kmss:message key="FlowChartObject.Lang.Right.mustModifyHandlerNodeNote" bundle="sys-lbpm-engine" /></span>
		</td>
	</tr>
	<tr>
		<td rowspan="3" width="100px"><kmss:message key="FlowChartObject.Lang.Right.canViewCurNodePopedomSet" bundle="sys-lbpm-engine" />
		<img class="rightReadTip" src="${KMSS_Parameter_ContextPath}sys/lbpmservice/resource/images/icon_help.png"></img>
		<div class="rightReadTipArea">
			<span><bean:message bundle='sys-lbpmservice-support' key='lbpmext.rightRead.fdRuleTip'/></span>
		</div>
		</td>
		<td width="100px"><kmss:message key="FlowChartObject.Lang.Right.nodeCanViewCurNode" bundle="sys-lbpm-engine" /></td>
		<td>
			<input type="hidden" name="wf_nodeCanViewCurNodeIds">
			<textarea name="wf_nodeCanViewCurNodeNames" style="width:100%;height:50px" readonly></textarea>
			<a href="#" onclick="selectNotionNodes('wf_nodeCanViewCurNodeIds', 'wf_nodeCanViewCurNodeNames');"><kmss:message key="FlowChartObject.Lang.select" bundle="sys-lbpm-engine" /></a>
		</td>
	</tr>
	<tr>
		<td width="100px"><kmss:message key="FlowChartObject.Lang.Right.otherCanViewCurNode" bundle="sys-lbpm-engine" /></td>
		<td>
			<input type="hidden" name="wf_otherCanViewCurNodeIds" orgattr="otherCanViewCurNodeIds:otherCanViewCurNodeNames">
			<textarea name="wf_otherCanViewCurNodeNames" style="width:100%;height:50px" readonly></textarea>
			<a href="#" onclick="Dialog_Address(true,'wf_otherCanViewCurNodeIds','wf_otherCanViewCurNodeNames', ';',ORG_TYPE_ALL);"><kmss:message key="FlowChartObject.Lang.select" bundle="sys-lbpm-engine" /></a>
		</td>
	</tr>
	<tr>
		<td width="100px"><kmss:message key="FlowChartObject.Lang.Right.canModifyNotionPopedom" bundle="sys-lbpm-engine" /></td>
		<td>
			<label>
				<input name="wf_canModifyNotionPopedom" type="checkbox" value="true">
			</label>
		</td>
	</tr>
	<tr id="canModifyFlowTr">
		<td width="100px"><kmss:message key="FlowChartObject.Lang.Right.canModifyFlow" bundle="sys-lbpm-engine" /></td>
		<td colspan="2">
			<label>
				<input name="wf_canModifyFlow" type="radio" value="true">
				<kmss:message key="FlowChartObject.Lang.Yes" bundle="sys-lbpm-engine" />
			</label><label>
				<input name="wf_canModifyFlow" type="radio" value="false" checked>
				<kmss:message key="FlowChartObject.Lang.No" bundle="sys-lbpm-engine" />
			</label>
		</td>
	</tr>
	<c:if test="${param.nodePrivilegeSetting eq 'true'}">
	<tr>
		<td width="100px"><kmss:message key="FlowChartObject.Lang.Right.nodePrivilegersSetting" bundle="sys-lbpm-engine" /></td>
		<td colspan="2">
			<label><input type="radio" name="wf_nodePrivilegeSelectType" value="org" onclick="switchNodePrivilegeSelectType(value);" checked><kmss:message key="FlowChartObject.Lang.Node.selectAddress" bundle="sys-lbpmservice" /></label>
			<label><input type="radio" name="wf_nodePrivilegeSelectType" value="formula" onclick="switchNodePrivilegeSelectType(value);"><kmss:message key="FlowChartObject.Lang.Node.selectFormList" bundle="sys-lbpmservice" /></label>
			<input name="wf_nodePrivilegeNames" class="inputsgl" style="width:400px" readonly>
			<input name="wf_nodePrivilegeIds" type="hidden" orgattr="nodePrivilegeIds:nodePrivilegeNames">
			<span id="SPAN_NodePrivilege_SelectType1">
				<a href="#" onclick="Dialog_Address(true, 'wf_nodePrivilegeIds', 'wf_nodePrivilegeNames', null, ORG_TYPE_POSTORPERSON | ORG_TYPE_ROLE);"><kmss:message key="FlowChartObject.Lang.select" bundle="sys-lbpm-engine" /></a>
			</span>
			<span id="SPAN_NodePrivilege_SelectType2" style="display:none ">
				<a href="#" onclick="selectByFormula('wf_nodePrivilegeIds', 'wf_nodePrivilegeNames');"><kmss:message key="FlowChartObject.Lang.select" bundle="sys-lbpm-engine" /></a>
			</span>

			<br>
			<label>
				<input type="checkbox" name="wf_modifyNodeHandler" value="true" checked>
				<kmss:message key="FlowChartObject.Lang.Right.modifyNodeHandler" bundle="sys-lbpm-engine" />
			</label>&nbsp;
			<label>
				<input type="checkbox" name="wf_urgeHandler" value="true" checked>
				<kmss:message key="FlowChartObject.Lang.Right.urgeNodeHandler" bundle="sys-lbpm-engine" />
			</label>&nbsp;
		</td>
	</tr>
	</c:if>
</table>
<c:if test="${param.nodePrivilegeSetting eq 'true'}">
	<script>
		var nodePrivilegeSelectType = AttributeObject.NodeData["nodePrivilegeSelectType"];
		AttributeObject.Init.AllModeFuns.push(function() {
			// #102116 节点特权人初始化
			if(nodePrivilegeSelectType == "formula"){
				document.getElementById('SPAN_NodePrivilege_SelectType1').style.display='none';
				document.getElementById('SPAN_NodePrivilege_SelectType2').style.display='';
			}else{
				document.getElementById('SPAN_NodePrivilege_SelectType1').style.display='';
				document.getElementById('SPAN_NodePrivilege_SelectType2').style.display='none';
			}
		});

		//特权人设置 zhanlei
		function switchNodePrivilegeSelectType(value) {
			if (nodePrivilegeSelectType == value)
				return;
			nodePrivilegeSelectType = value;
			SPAN_NodePrivilege_SelectType1.style.display = nodePrivilegeSelectType == "org" ? "" : "none";
			SPAN_NodePrivilege_SelectType2.style.display = nodePrivilegeSelectType == "formula" ? "" : "none";

			document.getElementsByName("wf_nodePrivilegeIds")[0].value = "";
			document.getElementsByName("wf_nodePrivilegeNames")[0].value = "";
			document.getElementsByName("wf_nodePrivilegeNames")[0].style.display = "";
			AttributeObject.Utils.switchOrgAttributes(document.getElementsByName("wf_nodePrivilegeIds")[0], nodePrivilegeSelectType);
		}
	</script>
</c:if>
<script>

function selectNodes(idField, nameField){
	var data = new KMSSData(), NodeData = AttributeObject.NodeData;
	for(var i=0; i<FlowChartObject.Nodes.all.length; i++){
		var node = FlowChartObject.Nodes.all[i];
		if(node.Data.id == NodeData.id || (node.Data.groupNodeId != null && node.Data.groupNodeId!=NodeData.groupNodeId))
			continue;
		var nodDesc = AttributeObject.Utils.nodeDesc(node);
		if ((nodDesc.isHandler && !nodDesc.isDraftNode) || nodDesc.isSendNode || node.Type == "embeddedSubFlowNode") {
			data.AddHashMap({id:node.Data.id, name:node.Data.id+"."+node.Data.name});
			if(node.Type == "embeddedSubFlowNode"){
				var fdContent = getContentByRefId(node.Data.embeddedRefId);
				if(fdContent){
					//嵌入的流程图对象
					var embeddedFlow = WorkFlow_LoadXMLData(fdContent);
					for(var j = 0;j<embeddedFlow.nodes.length;j++){
						var eNode = embeddedFlow.nodes[j];
						if((_isHandler(eNode) && !_isDraftNode(eNode)) || _isSendNode(eNode)){
							data.AddHashMap({id:node.Data.id+"-"+eNode.id, name:node.Data.id+"."+node.Data.name+"("+eNode.id+"."+eNode.name+")"});
						}
					}
				}
			}
		}
	}
	var dialog = new KMSSDialog(true, true);
	dialog.winTitle = FlowChartObject.Lang.dialogTitle;
	dialog.AddDefaultOption(data);
	dialog.BindingField(idField, nameField, ";");
	dialog.Show();
}

function _isHandler(node) {
	var sNodDesc = FlowChartObject.NodeTypeDescs[FlowChartObject.NodeDescMap[node.XMLNODENAME]];
	return sNodDesc ? (
			sNodDesc.isHandler() && 
			!sNodDesc.isAutomaticRun() && 
			!sNodDesc.isBranch() && 
			!sNodDesc.isSubProcess() && 
			!sNodDesc.isConcurrent() &&
			sNodDesc.uniqueMark() == null
		) ||  sNodDesc.uniqueMark() == 'signNodeDesc' ||  sNodDesc.uniqueMark() == 'voteNodeDesc' : false;
};
function _isDraftNode(node) {
	var sNodDesc = FlowChartObject.NodeTypeDescs[FlowChartObject.NodeDescMap[node.XMLNODENAME]];
	return sNodDesc ? (sNodDesc.uniqueMark() == 'draftNodeDesc') : false;
};
function _isSendNode(node) {
	var sNodDesc = FlowChartObject.NodeTypeDescs[FlowChartObject.NodeDescMap[node.XMLNODENAME]];
	return sNodDesc ? (
			sNodDesc.isHandler() && 
			sNodDesc.isAutomaticRun() && 
			!sNodDesc.isBranch() && 
			!sNodDesc.isSubProcess() && 
			!sNodDesc.isConcurrent() &&
			sNodDesc.uniqueMark() == null
		) : false;
};

//嵌入子流程根据redId获得流程图xml
function getContentByRefId(fdRefId){
	var fdContent = "";
	var ajaxurl = Com_Parameter.ContextPath + 'sys/lbpmservice/support/lbpmEmbeddedSubFlow.do?method=getContentByRefId&fdRefId='+fdRefId;
	var kmssData = new KMSSData();
	kmssData.SendToUrl(ajaxurl, function(http_request) {
		var responseText = http_request.responseText;
		var json = eval("("+responseText+")");
		if (json.fdContent){
			fdContent = json.fdContent;
		}
	},false);
	return fdContent;
}

//选择可查看当前节点的节点  add by limh 2010年9月19日
function selectNotionNodes(idField, nameField){
	var data = new KMSSData(), NodeData = AttributeObject.NodeData;
	for(var i=0; i<FlowChartObject.Nodes.all.length; i++){
		var node = FlowChartObject.Nodes.all[i];
		if(node.Data.id == NodeData.id || node.Data.groupNodeId != null)
			continue;
		var nodDesc = AttributeObject.Utils.nodeDesc(node);
		if (nodDesc.isHandler || nodDesc.isDraftNode || nodDesc.isSendNode) {
			data.AddHashMap({id:node.Data.id, name:node.Data.id+"."+node.Data.name});
		}
	}
	var dialog = new KMSSDialog(true, true);
	dialog.winTitle = FlowChartObject.Lang.dialogTitle;
	dialog.AddDefaultOption(data);
	dialog.BindingField(idField, nameField, ";");
	dialog.Show();
}

AttributeObject.Init.AllModeFuns.push(function() {
	AttributeObject.Utils.loadNodeNameInfo("wf_canModifyHandlerNodeIds", "canModifyHandlerNodeNames");
	AttributeObject.Utils.loadNodeNameInfo("wf_mustModifyHandlerNodeIds", "mustModifyHandlerNodeNames");
	//增加可查看当前节点意见的节点数据的加载 add by limh 2010年9月19日
	AttributeObject.Utils.loadNodeNameInfo("wf_nodeCanViewCurNodeIds", "wf_nodeCanViewCurNodeNames");
	if(FlowChartObject.IsEmbedded){
		//Doc_HideLabelById("Label_Tabel","node_right_tr");
		$("#canModifyFlowTr").hide();
	}
	if("${param.nodeType}"=="embeddedSubFlowNode" || "${param.nodeType}"=="dynamicSubFlowNode"){
		$(".embeddedSubFlowExceptTr").hide();
	}
});
</script>