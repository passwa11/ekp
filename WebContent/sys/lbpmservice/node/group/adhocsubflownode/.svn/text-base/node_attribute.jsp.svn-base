<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/resource/jsp/common.jsp"%>
<%@ taglib uri="/WEB-INF/KmssConfig/sys/lbpmservice/taglib/taglib.tld" prefix="xlang"%>
<%@page import="com.landray.kmss.sys.lbpmservice.taglib.MultiLangTextGroupTagNew"%>
<table width="590px" id="Label_Tabel">
	<tr LKS_LabelName="<kmss:message key="FlowChartObject.Lang.Label_Base" bundle="sys-lbpm-engine" />">
		<td>
			<table width="100%" class="tb_normal" id="config_table">
				<c:import url="/sys/lbpm/engine/node/common/node_name_attribute.jsp" charEncoding="UTF-8" />
				<!-- 起草节点处理勾选项 -->
				<%-- <tr style="display:none">
					<td width="100px"><kmss:message key="FlowChartObject.Lang.Node.nodeOptions" bundle="sys-lbpmservice" /></td>
					<td>
					<label>
						<input name="wf_decidedBranchOnDraft" type="checkbox" value="true">
						<kmss:message key="FlowChartObject.Lang.Node.manualBarnchNode_decidedBranchOnDraft" bundle="sys-lbpmservice-node-manualbranchnode" />
					</label>
					</td>
				</tr> --%>
				<!-- 默认选择节点 -->
				<%-- <tr style="display:none">
					<td width="100px"><kmss:message key="FlowChartObject.Lang.Node.manualBarnchNode_defaultBranch" bundle="sys-lbpmservice-node-manualbranchnode" /></td>
					<td>
						<select name="wf_defaultBranch" onmousedown="adHocSubAppendDefaultNodeId(this);" style="width:110px">
							<option value=""><kmss:message key="FlowChartObject.Lang.pleaseSelect" bundle="sys-lbpm-engine" /></option>
						</select>
					</td>
				</tr> --%>
				<tr>
					<td colspan="2" style="padding:0px;">
						<table id="adHocSubFlowList" class="tb_normal" width="100%" style="word-break:break-all">
							<tr>
								<td width="30px"><kmss:message key="lbpmExpecterLog.fdFactId" bundle="sys-lbpm-engine" /></td>
								<td width="170px"><kmss:message key="FlowChartObject.Lang.Node.name" bundle="sys-lbpm-engine" /></td>
								<td width="120px"><kmss:message key="FlowChartObject.Lang.Node.handlerNames" bundle="sys-lbpmservice" /></td>
								<td width="60px"><kmss:message key="FlowChartObject.Lang.Node.processType" bundle="sys-lbpmservice" /></td>
								<td width="70px"><kmss:message key="FlowChartObject.Lang.Line.nextNode" bundle="sys-lbpm-engine" /></td>
								<td width="60px" align="center">
									<kmss:message key="list.operation" />
								</td>
							</tr>
							<tr KMSS_IsReferRow="1" style="display:none">
								<td>
									<input name="adHocSubNodeId" class="inputsgl" type="text" readonly="readonly" style="width:100%;border-bottom:0px;color:black;"/>
									<input name="adHocSubNodeType" type="hidden"/>
								</td>
								<td>
									<%-- <input name="adHocSubNodeName" class="inputsgl" style="width:60%">
									<xlang:lbpmlang property="adHocSubNodeName" style="width:65%" langs=""/> --%>
									<c:if test="${!isLangSuportEnabled }">
										<input name="adHocSubNodeName" class="inputsgl" style="width:60%">
									</c:if>
									<c:if test="${isLangSuportEnabled }">
										<xlang:lbpmlangNew property="adHocSubNodeName" style="width:100%" langs="" className="inputsgl"/>
									</c:if>
									<span class="txtstrong">*</span>
								</td>
								<td>
									<select name="adHocSubHandlerSelectType" onchange="adHocSubHandlerSelectTypeChange(this);" style="width:98%">
										<option value="org"><kmss:message key="FlowChartObject.Lang.Node.selectAddress" bundle="sys-lbpmservice" /></option>
										<option value="formula"><kmss:message key="FlowChartObject.Lang.Node.selectFormList" bundle="sys-lbpmservice" /></option>
										<option value="matrix"><kmss:message key="FlowChartObject.Lang.Node.selectOrgMatrix" bundle="sys-lbpmservice" /></option>
									</select>
									<input name="adHocSub.!{index}.HandlerNames" class="inputsgl" style="width:65%" readonly /><span class="txtstrong">*</span>
									<input name="adHocSub.!{index}.HandlerIds" type="hidden" orgattr="handlerIds:handlerNames" />
									<span id="adHocSub_SPAN_SelectType1">
										<a href="javascript:void(0)" onclick="adHocSubSelectByAddress(this);"><kmss:message key="FlowChartObject.Lang.select" bundle="sys-lbpm-engine" /></a>
									</span>
									<span id="adHocSub_SPAN_SelectType2" style="display:none ">
										<a href="javascript:void(0)" onclick="adHocSubSelectByFormula(this);"><kmss:message key="FlowChartObject.Lang.select" bundle="sys-lbpm-engine" /></a>
									</span>
									<span id="adHocSub_SPAN_SelectType3" style="display:none ">
										<a href="javascript:void(0)" onclick="adHocSubSelectByMatrix(this);"><kmss:message key="FlowChartObject.Lang.select" bundle="sys-lbpm-engine" /></a>
									</span>
								</td>
								<td>
									<select name="adHocSubFlowType"  style="width:98%">
										<option value="0"><kmss:message key="FlowChartObject.Lang.Node.processType_0" bundle="sys-lbpmservice" /></option>
										<option value="1"><kmss:message key="FlowChartObject.Lang.Node.processType_1" bundle="sys-lbpmservice" /></option>
										<option value="2"><kmss:message key="FlowChartObject.Lang.Node.processType_20" bundle="sys-lbpmservice" /></option>
									</select>
								</td>
								<td>
									<!-- <input name="adHocSubNextNodeId" class="inputsgl" style="width:98%" /> -->
									<select name="adHocSubNextNodeId" style="width:98%" onmouseover="adHocSubAppendNextNodeId(this);" onchange="adHocSubNextNodeIdChange(this);">
										<option value=""><kmss:message key="FlowChartObject.Lang.pleaseSelect" bundle="sys-lbpm-engine" /></option>
									</select>
								</td>
								<td align="center">
									<a href="javascript:void(0)" onclick="adHocSub_DocList_DeleteRow(this);"><kmss:message key="FlowChartObject.Lang.Operation.Text.Delete" bundle="sys-lbpm-engine" /></a>
									<a href="javascript:void(0)" onclick="adHocSubOpenAttribute(this);" class='adHocSubOpenAttribute'><kmss:message key="FlowChartObject.Lang.Operation.Text.Attribute" bundle="sys-lbpm-engine" /></a>
								</td>
							</tr>
							<tr type="optRow" class="tr_normal_opt" style="display:none;">
								<td colspan="6">
									<div style="width:100%;position:relative;color:#fff;white-space:nowrap;">
										<span onclick='adHocSub_DocList_AddRow("reviewNode");' style="display:inline-block;line-height:20px;cursor:pointer;">
											<span style="display: inline-block;width: 20px;height: 20px; background: url(../images/normal_opt_sprite.png) no-repeat 0 0;background-position: -20px 0;position: relative;top: 2px;"></span>
											<span style="display: inline-block;vertical-align:text-bottom;margin-left:6px;"><kmss:message key="FlowChartObject.Lang.Operation.Title.ChangeMode.reviewNode" bundle="sys-lbpmservice-node-reviewnode" /></span>
										</span>
										<span onclick='adHocSub_DocList_AddRow("signNode");' style="margin-left:15px;display:inline-block;line-height:20px;cursor:pointer;">
											<span style="display: inline-block;width: 20px;height: 20px; background: url(../images/normal_opt_sprite.png) no-repeat 0 0;background-position: -20px 0;position: relative;top: 2px;"></span>
											<span style="display: inline-block;vertical-align:text-bottom;margin-left:6px;"><kmss:message key="FlowChartObject.Lang.Operation.Title.ChangeMode.signNode" bundle="sys-lbpmservice-node-signnode" /></span>
										</span>
									</div>
								</td>
							</tr>
						</table>
						<input type="hidden" name="wf_adHocSubFlowData" />
					</td>
				</tr>
			</table>
		</td>
	</tr>
	<tr LKS_LabelName="<kmss:message key="FlowChartObject.Lang.Node.subFlowChart" bundle="sys-lbpmservice-node-group" />" LKS_LabelId="subFlowChartConfig">
	 	<td>
	 		<iframe src="<c:url value="/sys/lbpm/flowchart/page/panel.html" />?extend=oa&template=true&modelName=${param.modelName}&FormFieldList=FlowChartObject.FormFieldList&isEmpty=true&showMenu=false&showBar=false"
			style="width:100%;height:398px" scrolling="no" id="adHocSubFlow_WF_IFrame"></iframe>
	 	</td>
	</tr>
	<tr LKS_LabelName="<kmss:message key="FlowChartObject.Lang.Node.subFlowChart" bundle="sys-lbpmservice-node-group" />" LKS_LabelId="subFlowChartInstance">
		<td>
			<textarea name="fdSubFlowContent" style="display:none"></textarea>
			<input type="hidden" name="fdTranProcessXML">
			<iframe style="width:100%;height:398px;" scrolling="no" id="adHocSubFlow_RT_WF_IFrame"></iframe>
		</td>
	</tr>
</table>
<script>
DocList_Info.push("adHocSubFlowList");

var SettingInfo = null;
function getSettingInfo(){
	if (SettingInfo == null) {
		SettingInfo = new KMSSData().AddBeanData("lbpmSettingInfoService").GetHashMapArray()[0];
	}
	return SettingInfo;
}

var adHocSubFlowNodeData = {
	nodeIndex : 1, //节点下标
	lineIndex : 1, //连线下标
	nodes : {},    //节点信息
	lines : {},    //连线信息
	maxX : 80,
	maxY : 40
};

AttributeObject.Init.AllModeFuns.push(function(){
	setTimeout(function(){
		//初始化节点数据，填充子节点信息到明细表
		initAdHocSubFlowNodeData();
		//模板时展示即席子节点概览信息，实例时展示即席子流程节点内实际的节点信息
		if (FlowChartObject.IsTemplate) {
			Doc_HideLabelById("Label_Tabel","subFlowChartInstance");
		} else {
			Doc_HideLabelById("Label_Tabel","subFlowChartConfig");
			loadInstanceSubFlowData();
		}
	},500);
});

//校验事件
AttributeObject.CheckDataFuns.push(function(){
	var adHocSubFlowList = document.getElementById("adHocSubFlowList");
	for(var i = 1;i<adHocSubFlowList.rows.length-1;i++){
		var row = adHocSubFlowList.rows[i];
		var adHocSubNodeName = $(row).find("input[name='adHocSubNodeName']").val();
		var handlerNames = $(row).find("input[name$='HandlerNames']").val();
		var nodeName = '<kmss:message key="table.lbpmNode" bundle="sys-lbpm-engine" />';
		var notNull = '<kmss:message key="FlowChartObject.Lang.Node.checkNameEmpty" bundle="sys-lbpm-engine" />';
		var handlerMsg = '<kmss:message key="FlowChartObject.Lang.Node.handlerIsNotNull" bundle="sys-lbpmservice-node-group" />';
		var typeMsg = '<kmss:message key="FlowChartObject.Lang.Node.flowTypeMsg" bundle="sys-lbpmservice-node-group" />';
		if(!adHocSubNodeName){
			alert(nodeName+$(row).find("input[name='adHocSubNodeId']").val()+notNull);
			return false;
		}
		if(!handlerNames){
			alert(nodeName+$(row).find("input[name='adHocSubNodeId']").val()+handlerMsg);
			return false;
		}
		var adHocSubNextNodeId = $(row).find("select[name='adHocSubNextNodeId']").val();
		if(!adHocSubNextNodeId){
			var adHocSubFlowType = $(row).find("select[name='adHocSubFlowType']").val();
			if(adHocSubFlowType == "2"){
				alert(nodeName+$(row).find("input[name='adHocSubNodeId']").val()+typeMsg);
				return false;
			}
		}
	}
	return true;
});

//提交事件(因写入XML也在提交事件中，故这里需要将提交事件放到最前面)
AttributeObject.SubmitFuns.unshift(function(){
	var adHocSubFlowList = document.getElementById("adHocSubFlowList");
	for(var i = 1;i<adHocSubFlowList.rows.length-1;i++){
		var row = adHocSubFlowList.rows[i];
		adHocSubFlowNodeRefresh($(row));
	}
	var datas = {};
	var nodes = [];
	for(var nodeId in adHocSubFlowNodeData.nodes){
		adHocSubFlowNodeData.nodes[nodeId].FormatXMLData();
		nodes.push(adHocSubFlowNodeData.nodes[nodeId].Data);
	}
	var lines = [];
	for(var nodeId in adHocSubFlowNodeData.lines){
		adHocSubFlowNodeData.lines[nodeId].FormatXMLData();
		lines.push(adHocSubFlowNodeData.lines[nodeId].Data);
	}
	datas["nodes"] =  nodes;
	datas["lines"] =  lines;
	$("input[name='wf_adHocSubFlowData']").val(WorkFlow_BuildXMLString(datas,"process"));
});

function initAdHocSubFlowNodeData() {
	if (document.getElementById("adHocSubFlow_WF_IFrame").contentWindow == null ||  document.getElementById("adHocSubFlow_WF_IFrame").contentWindow.document.getElementById("iframe_chart") == null) {
		setTimeout(initAdHocSubFlowNodeData,500);
		return;
	}
	var subFlowIFrame = document.getElementById("adHocSubFlow_WF_IFrame").contentWindow.document.getElementById("iframe_chart");
	if(subFlowIFrame.contentWindow){
		var flowChartObject = subFlowIFrame.contentWindow.FlowChartObject;
		if(flowChartObject) {
			flowChartObject.FormFieldList = FlowChartObject.FormFieldList;
			flowChartObject.SubFormInfoList = FlowChartObject.SubFormInfoList;
			flowChartObject.SubPrintInfoList = FlowChartObject.SubPrintInfoList;
			flowChartObject.xform_mode = FlowChartObject.xform_mode;
			flowChartObject.Designer = FlowChartObject.Designer;
			flowChartObject.modelName = FlowChartObject.modelName;
			flowChartObject.IsEdit = AttributeObject.isEdit();
		}
	}
	//重新编辑，回写nodes、lines
	var adHocSubFlowList = AttributeObject.NodeData["adHocSubFlowData"];
	if(adHocSubFlowList){
		var datas = WorkFlow_LoadXMLData(adHocSubFlowList);
		if(datas.nodes){
			for(var i = 0;i<datas.nodes.length;i++){
				var nodeData = datas.nodes[i];
				//根据保存的节点信息构建明细表行及节点对象
				adHocSub_DocList_AddRow(nodeData.XMLNODENAME,nodeData);
			}
		}
		if(datas.lines){
			for(var i = 0;i<datas.lines.length;i++){
				var lineData = datas.lines[i];
				var index = lineData.id.replace("SL",'');
				if(adHocSubFlowNodeData.lineIndex <= parseInt(index)){
					adHocSubFlowNodeData.lineIndex = parseInt(index)+1;
				}
				//构建连线对象
				var newLine = new subFlowIFrame.contentWindow.FlowChart_Line(lineData);
				newLine.LinkNode(adHocSubFlowNodeData.nodes[lineData.startNodeId], adHocSubFlowNodeData.nodes[lineData.endNodeId], lineData.startPosition, lineData.endPosition);
				newLine.Refresh(subFlowIFrame.contentWindow.LINE_REFRESH_TYPE_DOM);
				//缓存连线对象信息
				adHocSubFlowNodeData.lines[lineData.startNodeId] = newLine;
			}
			for(var i = 0;i<datas.lines.length;i++){
				var lineData = datas.lines[i];
				//修改节点层级
				adHocSubChangeNodeLevel(adHocSubFlowNodeData.nodes[lineData.startNodeId]);
				//根据连线拿到起始节点所在的明细表行
				var $tr = adHocSubFindTrByNodeId(lineData.startNodeId);
				if($tr && $tr.length>0){
					//为当前行下一节点编号构建选项并赋值
					var $nextNode = $tr.find("select[name='adHocSubNextNodeId']");
					adHocSubAppendNextNodeId($nextNode[0]);
					$nextNode.val(lineData.endNodeId);
				}
			}
		}
		//重置节点坐标
		adHocSubFlowResetNodePoints();
	}
	//为默认分支构建选项并赋值
	var defaultBranch = AttributeObject.NodeData["defaultBranch"];
	if(defaultBranch){
		var $defaultNode = $("select[name='wf_defaultBranch']");
		adHocSubAppendDefaultNodeId($defaultNode[0]);
		$defaultNode.val(defaultBranch);
	}
	//查看页面使明细表中字段disabled，因初始化明细表在自动调用disabled方法后
	if(!AttributeObject.isEdit()){
		var adHocSubFlowList = document.getElementById("adHocSubFlowList");
		AttributeObject.Utils.disabledOperation(adHocSubFlowList);
		$(".adHocSubOpenAttribute").text('<kmss:message key="FlowChartObject.Lang.Event.eventView" bundle="sys-lbpm-engine" />').show();
		setTimeout(function(){
			$("#adHocSubFlow_WF_IFrame").css("height","398px");
		},100);
	}else{
		$(".tr_normal_opt").show();
	}
}

// 流程实例查看子流程节点信息
function loadInstanceSubFlowData(){
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
		subLines[j].Data.startPosition = 3;
		subLines[j].Data.endPosition = 1;
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
	document.getElementById("adHocSubFlow_RT_WF_IFrame").setAttribute("src", loadUrl);
	setTimeout(function(FlowChartObject){
		$("#adHocSubFlow_RT_WF_IFrame").css("width","100%");
		$("#adHocSubFlow_RT_WF_IFrame").css("height","398px");
	},300);
	if (FlowChartObject.IsTemplate == false && FlowChartObject.IsEdit == false) {
		Doc_SetCurrentLabel("Label_Tabel", 3);
	}
}

//添加明细表行，并构建节点对象
function adHocSub_DocList_AddRow(nodeTye,data){
	var Data = new Object();
	var fieldValues = new Object();
	var nodeId = '';
	if(data){
		Data = data;
		nodeId = fieldValues["adHocSubNodeId"] = data.id;
		var index = nodeId.replace("SN",'');
		if(adHocSubFlowNodeData.nodeIndex <= parseInt(index)){
			adHocSubFlowNodeData.nodeIndex = parseInt(index)+1;
		}
	 	fieldValues["adHocSubNodeName"] = data.name;
	 	fieldValues["adHocSub.!{index}.HandlerIds"] = data.handlerIds;
	 	fieldValues["adHocSub.!{index}.HandlerNames"] = data.handlerNames;
	 	fieldValues["adHocSubFlowType"] = data.processType;
	 	fieldValues["adHocSubHandlerSelectType"] = data.handlerSelectType;
		if(data.langs){
			var nodeNamelang = JSON.parse(data.langs)["nodeName"];
			if(isLangSuportEnabled && nodeNamelang){
				for(var i=0;i<nodeNamelang.length;i++){
					fieldValues["adHocSubNodeName_"+nodeNamelang[i].lang] = nodeNamelang[i].value;
				}
			}
		}
	}else{
		var newData = new Object();
		newData.x = adHocSubFlowNodeData.maxX;
		newData.y = adHocSubFlowNodeData.maxY;
		adHocSubFlowNodeData.maxY += 80;
		nodeId = "SN"+adHocSubFlowNodeData.nodeIndex++;
		newData.id = fieldValues["adHocSubNodeId"]=nodeId;
		newData.name = fieldValues["adHocSubNodeName"]=Data_GetResourceString("sys-lbpmservice:lbpm.nodeType."+nodeTye);
	 	Data = newData;
	 	if(isLangSuportEnabled){
			for(var h=0;h<langJson["support"].length;h++){
				var lang = langJson["support"][h]["value"];
				fieldValues["adHocSubNodeName_"+lang] = Data_GetResourceString("sys-lbpmservice:lbpm.nodeType."+nodeTye,lang);
			}
		}
	}
	fieldValues["adHocSubNodeType"]=nodeTye;
	DocList_AddRow("adHocSubFlowList",null,fieldValues);
	var $tr = adHocSubFindTrByNodeId(nodeId);
	if(nodeTye=='signNode'){
		$tr.find("select[name='adHocSubFlowType'] option[value='2']").text('<kmss:message key="FlowChartObject.Lang.Node.processType_21" bundle="sys-lbpmservice" />');
	}
	if(fieldValues["adHocSubHandlerSelectType"]=="org"){
		$tr.find("#adHocSub_SPAN_SelectType1").show();
		$tr.find("#adHocSub_SPAN_SelectType2").hide();
		$tr.find("#adHocSub_SPAN_SelectType3").hide();
	} else if (fieldValues["adHocSubHandlerSelectType"]=="formula") {
		$tr.find("#adHocSub_SPAN_SelectType1").hide();
		$tr.find("#adHocSub_SPAN_SelectType2").show();
		$tr.find("#adHocSub_SPAN_SelectType3").hide();
	} else if (fieldValues["adHocSubHandlerSelectType"]=="matrix") {
		$tr.find("#adHocSub_SPAN_SelectType1").hide();
		$tr.find("#adHocSub_SPAN_SelectType2").hide();
		$tr.find("#adHocSub_SPAN_SelectType3").show();
	}
	var win = $("#iframe_chart",$("#adHocSubFlow_WF_IFrame")[0].contentWindow.document)[0].contentWindow;
	var node = new win.FlowChart_Node(nodeTye,Data);
	//为节点添加初始值，防止未打开属性面板直接保存丢失一些必须的初始值
	if(!data){
		FlowChartObject.Nodes.initNodeData(node);
		var data = new KMSSData();
		data.AddBeanData("getOperTypesByNodeService&nodeType=" + nodeTye);
		data = data.GetHashMapArray();
		for(var j=0;j<data.length;j++){
			if(data[j].isDefault=="true"){
				node.Data["operations"]["refId"] = data[j].value;
				break;
			}
		}
		getSettingInfo();
		node.Data["needMobileHandWrittenSignatureReviewNode"] = SettingInfo["needMobileHandWrittenSignatureReviewNode"];
		node.Data["needMobileHandWrittenSignatureSignNode"] = SettingInfo["needMobileHandWrittenSignatureSignNode"];
	}

	//添加层级，用于流程图排序
	node.level = 1;
	//添加节点面板关闭回掉函数，为明细表行赋值
	node.AfterShow = function(myNode){
		var myData = myNode.Data;
		if(myData.id){
			//根据节点Id，找到对应的明细表行
			var $tr = adHocSubFindTrByNodeId(myData.id);
			if($tr && $tr.length>0){
				if(myData.name){
					$tr.find("input[name='adHocSubNodeName']").val(myData.name);
				}
				if(myData.handlerSelectType){
					var $adHocSubHandlerTypeSelect = $tr.find("select[name='adHocSubHandlerSelectType']");
					$adHocSubHandlerTypeSelect.val(myData.handlerSelectType);
					adHocSubHandlerSelectTypeChange($adHocSubHandlerTypeSelect[0]);
				}
				if(myData.handlerIds){
					$tr.find("input[name$='HandlerIds']").val(myData.handlerIds)
				}
				if(myData.handlerNames){
					$tr.find("input[name$='HandlerNames']").val(myData.handlerNames);
				}
				if(myData.processType){
					$tr.find("select[name='adHocSubFlowType']").val(myData.processType);
				}
				if(myData.langs){
					var nodeNamelang = JSON.parse(myData.langs)["nodeName"];
					if(isLangSuportEnabled && nodeNamelang){
						for(var i=0;i<nodeNamelang.length;i++){
							$tr.find("input[name='adHocSubNodeName_"+nodeNamelang[i].lang+"']").val(nodeNamelang[i].value);
						}
					}
				}
			}
		}
	}
	//缓存节点信息（节点Id为key，节点对象为值）
	adHocSubFlowNodeData.nodes[nodeId] = node;
}

//根据节点Id，找到对应的明细表行
function adHocSubFindTrByNodeId(nodeId){
	var $tr = null;
	$("#adHocSubFlowList input[name='adHocSubNodeId']").each(function(){
		if($(this).val() == nodeId){
			$tr = $(this).closest("tr");
			return false;
		}
	});
	return $tr;
}

//删除明细表行操作
function adHocSub_DocList_DeleteRow(dom){
	//当前行对应节点Id
	var nodeId = $(dom).closest("tr").find("input[name='adHocSubNodeId']").val();
	//移除默认分支中当前行对应的节点Id选项
	$("select[name='wf_defaultBranch'] option[value='"+nodeId+"']").remove();
	var node = adHocSubFlowNodeData.nodes[nodeId];
	//删除节点对象
	node.Delete();
	//删除缓存的节点对象
	delete adHocSubFlowNodeData.nodes[nodeId];
	//若缓存的连线信息中含有当前行对应节点Id为key的信息，删除对应连线信息
	if(adHocSubFlowNodeData.lines[nodeId]){
		delete adHocSubFlowNodeData.lines[nodeId];
	}
	//若其他行里下一节点编号中含有当前节点Id，移除这一选项，并发布值改变事件
	$("#adHocSubFlowList select[name='adHocSubNextNodeId']").each(function(){
		var op = $(this).find("option[value='"+nodeId+"']");
		if(op.length>0){
			op.remove();
			$(this).change();
		}
	});
	//删除明细表行
	DocList_DeleteRow();
}

//打开属性面板
function adHocSubOpenAttribute(dom){
	var $tr = $(dom).closest("tr");
	//同步当前行信息到对应的节点对象中，并刷新节点对象
	var node = adHocSubFlowNodeRefresh($tr);
	if (node && node.ShowAttribute) {
		node.ShowAttribute();
	}	
}

//根据明细表行同步信息到对应的节点对象中，并刷新节点对象
function adHocSubFlowNodeRefresh($tr){
	var nodeId = $tr.find("input[name='adHocSubNodeId']").val();
	var node = adHocSubFlowNodeData.nodes[nodeId];
	node.Data.name = $tr.find("input[name='adHocSubNodeName']").val();
	node.Data.handlerIds = $tr.find("input[name$='HandlerIds']").val();
	node.Data.handlerNames = $tr.find("input[name$='HandlerNames']").val();
	node.Data.processType = $tr.find("select[name='adHocSubFlowType']").val();
	node.Data.handlerSelectType = $tr.find("select[name='adHocSubHandlerSelectType']").val();
	if(isLangSuportEnabled){
		var langs = {};
		var nodeNamelang = [];
		var official = langJson["official"]["value"];
		for(var h=0;h<langJson["support"].length;h++){
			var lang = langJson["support"][h]["value"];
			if(official==lang){
				nodeNamelang.push({"lang":lang,"value":$tr.find("input[name='adHocSubNodeName']").val()});
			}else{
				nodeNamelang.push({"lang":lang,"value":$tr.find("input[name='adHocSubNodeName_"+lang+"']").val()});
			}
		}
		langs["nodeName"] = nodeNamelang;
		node.Data.langs = JSON.stringify(langs);
	}
	node.Refresh();
	return node;
}

//构建默认分支（不能选择有流入的节点）
function adHocSubAppendDefaultNodeId(dom){
	//先缓存当前值
	var defaultBranch = $(dom).val();
	//清空选项重新构建
	$(dom).html("");
	$(dom).append('<option value=""><kmss:message key="FlowChartObject.Lang.pleaseSelect" bundle="sys-lbpm-engine" /></option>');
	//可出现选项
	var nextNodeIds = [];
	//需排除的选项
	var exceptIds = [];
	var adHocSubFlowList = document.getElementById("adHocSubFlowList");
	for(var i = 1;i<adHocSubFlowList.rows.length-1;i++){
		var row = adHocSubFlowList.rows[i];
		var $tr = $(row);
		//添加当前行节点Id到可选值列表
		nextNodeIds.push($tr.find("input[name='adHocSubNodeId']").val());
		//排除当前行下一节点Id
		var nextId = $tr.find("select[name='adHocSubNextNodeId']").val();
		if(nextId){
			exceptIds.push(nextId);
		}
	}
	//构建选项
	for(var i=0;i<nextNodeIds.length;i++){
		var op = $(dom).find("option[value='"+nextNodeIds[i]+"']");
		if(Com_ArrayGetIndex(exceptIds,nextNodeIds[i]) < 0 && op.length==0){
			$(dom).append('<option value="'+nextNodeIds[i]+'">'+nextNodeIds[i]+'</option>');
		}
	}
	//重新赋值
	$(dom).val(defaultBranch);
}

//构建下一节点编号
function adHocSubAppendNextNodeId(dom){
	//先缓存当前值
	var nextNodeId = $(dom).val();
	//清空选项重新构建
	$(dom).html("");
	$(dom).append('<option value=""><kmss:message key="FlowChartObject.Lang.pleaseSelect" bundle="sys-lbpm-engine" /></option>');
	//可出现选项
	var nextNodeIds = [];
	//需排除的选项
	var exceptIds = [];
	//排除当前行节点Id，不能自己选自己
	var nowId= $(dom).closest("tr").find("input[name='adHocSubNodeId']").val();
	exceptIds.push(nowId);
	var adHocSubFlowList = document.getElementById("adHocSubFlowList");
	for(var i = 1;i<adHocSubFlowList.rows.length-1;i++){
		var row = adHocSubFlowList.rows[i];
		var $tr = $(row);
		//添加当前行节点Id到可选值列表
		nextNodeIds.push($tr.find("input[name='adHocSubNodeId']").val());
		//排除当前行下一节点Id
		var nextId = $tr.find("select[name='adHocSubNextNodeId']").val();
		if(nextId && Com_ArrayGetIndex(exceptIds,nextId) < 0){
			exceptIds.push(nextId);
		}
	}
	//循环遍历排除流入当前节点线上的节点，避免死循环
	adHocSubAddExceptIdsByLine(exceptIds,nowId);
	for(var i=0;i<nextNodeIds.length;i++){
		var op = $(dom).find("option[value='"+nextNodeIds[i]+"']");
		if(Com_ArrayGetIndex(exceptIds,nextNodeIds[i]) < 0 && op.length==0){
			$(dom).append('<option value="'+nextNodeIds[i]+'">'+nextNodeIds[i]+'</option>');
		}
	}
	//重新赋值
	$(dom).val(nextNodeId);
}

//循环遍历排除流入当前节点线上的节点，避免死循环
function adHocSubAddExceptIdsByLine(exceptIds,nowId){
	for(var nodeId in adHocSubFlowNodeData.lines){
		if(adHocSubFlowNodeData.lines[nodeId].EndNode.Data.id == nowId){
			if(Com_ArrayGetIndex(exceptIds,nodeId) < 0){
				exceptIds.push(nodeId);
			}
			adHocSubAddExceptIdsByLine(exceptIds,nodeId);
			break;
		}
	}
}

//下一节点编号值改变事件
function adHocSubNextNodeIdChange(dom){
	var nodeId = $(dom).closest("tr").find("input[name='adHocSubNodeId']").val();
	//当前节点
	var node = adHocSubFlowNodeData.nodes[nodeId];
	if(dom.value){
		//下一节点
		var nextNode = adHocSubFlowNodeData.nodes[dom.value];
		if(nextNode){
			var win = $("#iframe_chart",$("#adHocSubFlow_WF_IFrame")[0].contentWindow.document)[0].contentWindow;
			//若存在当前节点Id为key的线条
			if(adHocSubFlowNodeData.lines[nodeId]){
				//拿到改变前的下一节点
				var preNextNode = adHocSubFlowNodeData.lines[nodeId].EndNode;
				if(preNextNode){
					//修改改变前的下一节点层级及对应连线上的节点层级
					preNextNode.level = 1;
					adHocSubChangeNodeLevel(preNextNode);
				}
				//重新连接当前节点和下一节点
				adHocSubFlowNodeData.lines[nodeId].LinkNode(node, nextNode, '2', '4');
				adHocSubFlowNodeData.lines[nodeId].Refresh(win.LINE_REFRESH_TYPE_DOM);
			}else{
				//不存在则重现构建连线对象
				var data = new Object();
				data.id = "SL"+adHocSubFlowNodeData.lineIndex++;
				var newLine = new win.FlowChart_Line(data);
				//连接当前节点和下一节点
				newLine.LinkNode(node, nextNode, '2', '4');
				newLine.Refresh(win.LINE_REFRESH_TYPE_DOM);
				//缓存连线信息（开始节点Id为key，连线对象为值）
				adHocSubFlowNodeData.lines[nodeId] = newLine;
			}
			//修改当前节点层级及对应连线上的节点层级
			adHocSubChangeNodeLevel(node);
		}
		//移除默认分支上的对应选项
		$("select[name='wf_defaultBranch'] option[value='"+dom.value+"']").remove();
	}else{
		//若下一节点编号置为空，则认为删除当前连线
		if(adHocSubFlowNodeData.lines[nodeId]){
			var nextNode = adHocSubFlowNodeData.lines[nodeId].EndNode;
			//删除连线对象
			adHocSubFlowNodeData.lines[nodeId].Delete();
			//删除缓存的连线对象
			delete adHocSubFlowNodeData.lines[nodeId];
			//重置删除前下一节点及对应连线上的节点层级
			if(nextNode){
				nextNode.level = 1;
				adHocSubChangeNodeLevel(nextNode);
			}
		}
	}
}

//重置节点层级为上一节点的层级+1
function adHocSubChangeNodeLevel(node){
	if(adHocSubFlowNodeData.lines[node.Data.id]){
		var nextNode = adHocSubFlowNodeData.lines[node.Data.id].EndNode;
		nextNode.level = node.level+1;
		adHocSubChangeNodeLevel(nextNode);
	}
}

//默认处理人选择方式值改变事件
function adHocSubHandlerSelectTypeChange(dom){
	if(dom.value=="org"){
		$(dom).closest("tr").find("#adHocSub_SPAN_SelectType1").show();
		$(dom).closest("tr").find("#adHocSub_SPAN_SelectType2").hide();
		$(dom).closest("tr").find("#adHocSub_SPAN_SelectType3").hide();
	}else if(dom.value=="formula") {
		$(dom).closest("tr").find("#adHocSub_SPAN_SelectType1").hide();
		$(dom).closest("tr").find("#adHocSub_SPAN_SelectType2").show();
		$(dom).closest("tr").find("#adHocSub_SPAN_SelectType3").hide();
	}else{
		$(dom).closest("tr").find("#adHocSub_SPAN_SelectType1").hide();
		$(dom).closest("tr").find("#adHocSub_SPAN_SelectType2").hide();
		$(dom).closest("tr").find("#adHocSub_SPAN_SelectType3").show();
	}
	$(dom).closest("tr").find("input[name$='HandlerNames']").val("");
	$(dom).closest("tr").find("input[name$='HandlerIds']").val("");
}

//从组织架构选择
function adHocSubSelectByAddress(dom){
	var idField = $(dom).closest("tr").find("input[name$=HandlerIds]").attr("name");
	var nameField = $(dom).closest("tr").find("input[name$=HandlerNames]").attr("name");
	Dialog_Address(true, idField, nameField, null , ORG_TYPE_POSTORPERSON | ORG_TYPE_ROLE);
}

//从公式定义器选择
function adHocSubSelectByFormula(dom){
	var idField = $(dom).closest("tr").find("input[name$=HandlerIds]").attr("name");
	var nameField = $(dom).closest("tr").find("input[name$=HandlerNames]").attr("name");
	Formula_Dialog(idField, nameField ,FlowChartObject.FormFieldList,"com.landray.kmss.sys.organization.model.SysOrgElement[]",
			null,"com.landray.kmss.sys.lbpm.engine.formula.LbpmFunction",FlowChartObject.ModelName);
}

//使用矩阵组织选择
function adHocSubSelectByMatrix(dom){
	var idField = $(dom).closest("tr").find("input[name$=HandlerIds]").attr("name");
	var nameField = $(dom).closest("tr").find("input[name$=HandlerNames]").attr("name");
	// 弹出矩阵组织设置窗口
	var dialog = new KMSSDialog();
	dialog.FormFieldList = FlowChartObject.FormFieldList;
	dialog.ModelName = FlowChartObject.ModelName;
	dialog.BindingField(idField, nameField);
	dialog.URL = Com_Parameter.ContextPath + "sys/lbpmservice/node/common/node_handler_matrix_config.jsp";
	var size = getSizeForAddress();
	dialog.Show(size.width, size.height);
}

//选项卡切换事件
function adHocSubFlowOnLabelSwitch(tableName,index){
	if(index==2){
		var adHocSubFlowList = document.getElementById("adHocSubFlowList");
		for(var i = 1;i<adHocSubFlowList.rows.length-1;i++){
			var row = adHocSubFlowList.rows[i];
			//根据明细表行同步信息到对应的节点对象中，并刷新节点对象
			adHocSubFlowNodeRefresh($(row));
		}
		//根据节点层级重置节点坐标
		adHocSubFlowResetNodePoints();
		setTimeout(function(){
			$("#adHocSubFlow_WF_IFrame").css("height","398px");
		},100);
	}
}

//根据节点层级重置节点坐标
function adHocSubFlowResetNodePoints(){
	var currX = 120;
	var currY = 60;
	for(var nodeId in adHocSubFlowNodeData.nodes){
		if(adHocSubFlowNodeData.nodes[nodeId].level == 1){
			var node = adHocSubFlowNodeData.nodes[nodeId];
			node.Data.x = currX;
			node.Data.y = currY;
			node.MoveTo(node.Data.x, node.Data.y);
			adHocSubFlowMoveNode(node);
			currY += 80;
		}
	}
	adHocSubFlowNodeData.maxY = currY;
}

//重置当前节点连线上的节点坐标
function adHocSubFlowMoveNode(node){
	if(adHocSubFlowNodeData.lines[node.Data.id]){
		var nextNode = adHocSubFlowNodeData.lines[node.Data.id].EndNode;
		nextNode.Data.x = node.Data.x+160;
		nextNode.Data.y = node.Data.y;
		nextNode.MoveTo(nextNode.Data.x, nextNode.Data.y);
		adHocSubFlowMoveNode(nextNode);
	}
}

Com_AddEventListener(window, "load", function() {
	var table = document.getElementById("Label_Tabel");
	if(table!=null && window.Doc_AddLabelSwitchEvent){
		Doc_AddLabelSwitchEvent(table, "adHocSubFlowOnLabelSwitch");
	} 
});
</script>