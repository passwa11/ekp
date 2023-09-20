<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/resource/jsp/common.jsp"%>
<%@ taglib uri="/WEB-INF/KmssConfig/sys/lbpmservice/taglib/taglib.tld" prefix="xlang"%>
<!-- 自由流的启动并行分支节点属性面板 -->
<table width="420px" id="Label_Tabel">
	<tr>
		<td style="background-color:#f6f6f6 !important;">
			<table width="95%" style="background-color:#f6f6f6 !important;" class="tb_normal" id="config_table">
				<c:import url="/sys/lbpmservice/node/common/node_fixed_attribute.jsp" charEncoding="UTF-8">
				</c:import>
				<c:import url="/sys/lbpm/engine/node/common/node_name_attribute.jsp" charEncoding="UTF-8">
					<c:param name="flowType" value="1" />
				</c:import>
				<tr>
					<td width="100px"><kmss:message key="FlowChartObject.Lang.Node.concurrencyBranchSplitType" bundle="sys-lbpm-engine-node-splitnode" /></td>
					<td width="490px">
						<label><input type="radio" name="wf_splitType" value="all" checked onclick="switchSplitType();"><kmss:message key="FlowChartObject.Lang.Node.concurrencyBranchSplitAllType" bundle="sys-lbpm-engine-node-splitnode" /></label>
						<label><input type="radio" name="wf_splitType" value="condition" onclick="switchSplitType();"><kmss:message key="FlowChartObject.Lang.Node.concurrencyBranchSplitConditionType" bundle="sys-lbpm-engine-node-splitnode" /></label>
						<label><input type="radio" name="wf_splitType" value="custom" onclick="switchSplitType();"><kmss:message key="FlowChartObject.Lang.Node.concurrencyBranchSplitCustomType" bundle="sys-lbpm-engine-node-splitnode" /></label>
					</td>
				</tr>
				<tr splitType="condition" style="display:none;">
					<td colspan="2">
						<table class="tb_normal" id="lineTable">
							<tr class="tr_normal_title">
								<td style="width:94px"><kmss:message key="FlowChartObject.Lang.Line.nextNode" bundle="sys-lbpm-engine" /></td>
								<td style="width:135px"><kmss:message key="FlowChartObject.Lang.Line.name" bundle="sys-lbpm-engine" /></td>
								<td style="width:330px"><kmss:message key="FlowChartObject.Lang.Line.condition" bundle="sys-lbpm-engine" /></td>
							</tr>
							<tr KMSS_IsReferRow="1" style="display:none">
								<td></td>
								<td>
									<%-- <input name="lineName" class="inputsgl">
									<xlang:lbpmlang property="lineName" style="width:100%" langs=""/> --%>
									<c:if test="${!isLangSuportEnabled }">
										<input name="lineName" class="inputsgl">
									</c:if>
									<c:if test="${isLangSuportEnabled }">
										<xlang:lbpmlangNew property="lineName" style="width:100%" className="inputsgl" langs=""/>
									</c:if>
									<input name="nextNodeName" type="hidden">
								</td>
								<td>
									<input type="hidden" name="lineCondition">
									<input name="lineDisCondition" class="inputsgl" readonly style="width:95% ">
									<span class="txtstrong">*</span><br>
									<a href="#" onclick="openExpressionEditor();"><kmss:message key="FlowChartObject.Lang.Line.formula" bundle="sys-lbpm-engine" /></a>
									<span style="width:180px"></span>
									<a href="#" onclick="DocList_MoveRow(-1);"><kmss:message key="FlowChartObject.Lang.Operation.operationMoveUp" bundle="sys-lbpmservice" /></a>
									<a href="#" onclick="DocList_MoveRow(1);"><kmss:message key="FlowChartObject.Lang.Operation.operationMoveDown" bundle="sys-lbpmservice" /></a>
								</td>
							</tr>
						</table>
					</td>
				</tr>
				<tr id='startBranchHandler'>
					<td width="100px"><kmss:message key="FlowChartObject.Lang.Node.startBranchHandler" bundle="sys-lbpm-engine-node-splitnode" /></td>
					<td width="490px">
						<label><input type="radio" name="wf_startBranchHandlerSelectType" value="org" onclick="switchStartBranchHandlerSelectType(value);" checked><kmss:message key="FlowChartObject.Lang.Node.selectAddress" bundle="sys-lbpmservice" /></label>
						<label><input type="radio" name="wf_startBranchHandlerSelectType" value="formula" onclick="switchStartBranchHandlerSelectType(value);"><kmss:message key="FlowChartObject.Lang.Node.selectFormList" bundle="sys-lbpmservice" /></label>
						<input type="hidden" name="wf_startBranchHandlerId" orgattr="startBranchHandlerId:startBranchHandlerName">
						<input name="wf_startBranchHandlerName" class="inputsgl" readonly style="width:80% ">
						<span id="SPAN_SelectType1">
						<a href="#" onclick="Dialog_Address(true, 'wf_startBranchHandlerId', 'wf_startBranchHandlerName', null, ORG_TYPE_POSTORPERSON | ORG_TYPE_ROLE);"><kmss:message key="FlowChartObject.Lang.select" bundle="sys-lbpm-engine" /></a>
						</span>
						<span id="SPAN_SelectType2" style="display:none ">
						<a href="#" onclick="selectByFormula('wf_startBranchHandlerId', 'wf_startBranchHandlerName');"><kmss:message key="FlowChartObject.Lang.select" bundle="sys-lbpm-engine" /></a>
						</span>
						<br>
						<span class="com_help"><kmss:message key="FlowChartObject.Lang.Node.startBranchHandler.desc" bundle="sys-lbpm-engine-node-splitnode"/></span>
					</td>
				</tr>
				<tr id='defaultStartBranch' style="display: none">
					<td width="100px"><kmss:message key="FlowChartObject.Lang.Node.defaultStartBranch" bundle="sys-lbpm-engine-node-splitnode" /></td>
					<td width="490px">
						<input type="hidden" name="wf_defaultStartBranchIds">
						<input name="wf_defaultStartBranchNames" class="inputsgl" readonly style="width:80% ">
						<a href="#" onclick="selectDefaultLineNode('wf_defaultStartBranchIds','wf_defaultStartBranchNames',';',false);"><kmss:message key="FlowChartObject.Lang.select" bundle="sys-lbpm-engine" /></a>
						<br/>
						<div style="display: none" id='canSelectDiv'><input type="checkbox" name="wf_canSelectDefaultBranch" value="true" style="margin-right: 5px;margin-top:5px"><span style="position: relative;top: -1px"><kmss:message key="FlowChartObject.Lang.Node.defaultStartBranch.select" bundle="sys-lbpm-engine-node-splitnode" /></span></div>
					</td>
				</tr>
				<tr>
					<td width="100px"><kmss:message key="FlowChartObject.Lang.Line.tip" bundle="sys-lbpm-engine" /></td>
					<td width="490px">
						<kmss:message key="FlowChartObject.Lang.Node.concurrencyBranchSplitTipInfo" bundle="sys-lbpm-engine-node-splitnode" />
					</td>
				</tr>
			</table>
		</td>
	</tr>
</table>
<script>
	DocList_Info.push("lineTable");
	var startBranchHandlerSelectType = AttributeObject.NodeData["startBranchHandlerSelectType"];
	function _appendLangsValue(obj,data){
		var langs = data["langs"];
		var lineName =  data["name"]||"";
		if(typeof langs !="undefined"){
			var isEdit = AttributeObject.isEdit ? AttributeObject.isEdit() : FlowChartObject.IsEdit;
			if(isEdit){
				lineName = _getLangLabel(lineName,langs,langJson["official"]["value"]);
			}else{
				lineName = _getLangLabel(lineName,langs,userLang);
			}
		}
		obj["name"]=lineName||"";
		for(var j=0;j<langJson["support"].length;j++){
			var name ="lineName_"+langJson["support"][j]["value"];
			var val = _getLangLabel("",langs,langJson["support"][j]["value"]);
			obj[name]=val||"";
		}
	}

	AttributeObject.Init.AllModeFuns.push(function() {
		var NodeData = AttributeObject.NodeData;
		var LineOut = AttributeObject.NodeObject.LineOut;
		function getSortData(){
			var o1=[],o2=[];
			if(LineOut!=null && LineOut.length>0){
				LineOut = LineOut.sort(FlowChartObject.LinesSort);
				for(var i=0;i<LineOut.length;i++){
					var nn = LineOut[i].EndNode.Data.name+"["+LineOut[i].EndNode.Data.id+"]";
					var o = {nextNodeName:nn, name : LineOut[i].Data["name"]||"",
						condition:LineOut[i].Data["condition"]||"",disCondition:LineOut[i].Data["disCondition"]||"",
						priority:LineOut[i].Data["priority"]||""
					};
					//补多语言
					_appendLangsValue(o,LineOut[i].Data);
					if(o["priority"]==""){
						o2[o2.length] = o;
					}else{
						o1[o["priority"]] = o;
					}
				}
			}
			o1 = o1.concat(o2);
			return o1;
		}

		var a = getSortData();
		if(a!=null && a.length>0){
			for(var i=0;i<a.length;i++){
				if(!a[i]){
					continue;
				}
				var rd = {lineName : a[i]["name"]||"",nextNodeName : a[i]["nextNodeName"],
					lineCondition : a[i]["condition"]||"", lineDisCondition : a[i]["disCondition"]||""};

				//补多语言
				for(var j=0;j<langJson["support"].length;j++){
					var name ="lineName_"+langJson["support"][j]["value"];
					rd[name]=a[i][name]||"";
				}

				var row = DocList_AddRow("lineTable",[a[i]["nextNodeName"]],rd);
				/*
                var row = DocList_AddRow("lineTable",[a[i]["nextNodeName"]],
                    {lineName : a[i]["name"]||"",nextNodeName : a[i]["nextNodeName"],
                    lineCondition : a[i]["condition"]||"", lineDisCondition : a[i]["disCondition"]||""});
                */
				row.style.verticalAlign="top";
			}
		}
		switchSplitType();

		if (startBranchHandlerSelectType=="formula") {
			document.getElementById('SPAN_SelectType1').style.display='none';
			document.getElementById('SPAN_SelectType2').style.display='';
		}

		// 自由流实时保存节点数据
		var isOpenNewWin = "${JsParam.isOpenNewWin eq 'true'}";
		if(isOpenNewWin!="true"){
			$("input[name^='__notify_type_']:checkbox").change(function(){
				saveNodeData();
			});
			$("input[name^='_wf_name_']").change(function(){
				saveNodeData();
			});
			$("input[name^='wf_']").change(function(){
				saveNodeData();
			});
			$("input[name='lineDisCondition']").change(function(){
				saveNodeData();
			});
			$("input[name='lineName']").change(function(){
				saveNodeData();
			});
		}
	});

	AttributeObject.SubmitFuns.push(AttributeObject.Utils.refreshLineOut);
	AttributeObject.SubmitFuns.push(updateNodeTypeCode);

	function updateNodeTypeCode(){
		if (AttributeObject.NodeObject.Data["splitType"] == 'condition') {
			AttributeObject.NodeObject.TypeCode = FlowChartObject.NODETYPE_CONDITION;
		} else {
			AttributeObject.NodeObject.TypeCode = FlowChartObject.NODETYPE_NORMAL;
		}
	}

	function nodeDataCheck(data){
		var LineOut = AttributeObject.NodeObject.LineOut;
		if(data.splitType == 'condition' && LineOut!=null && LineOut.length>0){
			for(var i=0;i<LineOut.length;i++){
				var nn = LineOut[i].EndNode.Data.name+"["+LineOut[i].EndNode.Data.id+"]";
				//var nextNodeName = LineOut[i].EndNode.Data.name;
				var o = getLineValue(nn);
				if(o==null){
					alert('<kmss:message key="FlowChartObject.Lang.Node.concurrencyBranchSplitConditionNotNull" bundle="sys-lbpm-engine-node-splitnode" />');
					return false;
				}
				var isOpenNewWin = "${JsParam.isOpenNewWin eq 'true'}";
				if(isOpenNewWin=="true"){
					if (o["condition"] == null || Com_Trim(o["condition"]) == '') {
						alert('<kmss:message key="FlowChartObject.Lang.Node.concurrencyBranchSplitConditionNotNull" bundle="sys-lbpm-engine-node-splitnode" />');
						return false;
					}
				}
				LineOut[i].Data["name"] = o["name"];
				LineOut[i].Data["condition"] = o["condition"];
				LineOut[i].Data["disCondition"] = o["disCondition"];
				LineOut[i].Data["priority"] = o["priority"];
				LineOut[i].Data["langs"] = o["langs"];
			}
		}

		return true;
	}

	AttributeObject.CheckDataFuns.push(nodeDataCheck);

	function getLineValue(nextNodeName){
		var lineName = document.getElementsByName("lineName");
		var lineCondition = document.getElementsByName("lineCondition");
		var lineDisCondition = document.getElementsByName("lineDisCondition");
		var nextNodeNames = document.getElementsByName("nextNodeName");
		for(var i=0;i<nextNodeNames.length;i++){
			if(nextNodeNames[i].value==nextNodeName){
				//return {name:lineName[i].value,condition:lineCondition[i].value,disCondition:lineDisCondition[i].value,priority:""+i};
				var o = {name:lineName[i].value,condition:lineCondition[i].value,disCondition:lineDisCondition[i].value,priority:""+i};
				var langs = _getLangByElName("lineName",i,"");
				o["langs"] =  JSON.stringify(langs);
				return o;
			}
		}
		return null;
	}

	function openExpressionEditor() {
		var index = getElementIndex();
		Formula_Dialog(document.getElementsByName("lineCondition")[index-1],
				document.getElementsByName("lineDisCondition")[index-1],
				FlowChartObject.FormFieldList,
				"Boolean",
				null,
				"com.landray.kmss.sys.lbpm.engine.formula.LbpmFunction",
				FlowChartObject.ModelName);
	}

	function getElementIndex(){
		var row = DocListFunc_GetParentByTagName("TR");
		var table = DocListFunc_GetParentByTagName("TABLE", row);
		for(var i=1; i<table.rows.length; i++){
			if(table.rows[i]==row){
				return i;
			}
		}
		return -1;
	}

	function switchSplitType() {
		var splitType = document.getElementsByName("wf_splitType"),
				i,
				typeValue,
				table = document.getElementById("config_table");

		for (i = 0; i < splitType.length; i++) {
			if (splitType[i].checked) {
				typeValue = splitType[i].value;
				break;
			}
		}
		for (i = 0; i < table.rows.length; i++) {
			var row = table.rows[i];
			var attrType = row.getAttribute('splitType');
			if (attrType != null && attrType != '') {
				if (attrType == typeValue) {
					row.style.display = '';
				} else {
					row.style.display = "none";
				}
			}
		}

		//显示或隐藏默认启动分支
		var defaultStartBranchObj = document.getElementById("defaultStartBranch");
		if(typeValue == 'custom'){
			defaultStartBranchObj.style.display = "";
		}else{
			defaultStartBranchObj.style.display = "none";
			var wf_defaultStartBranchIdsObj =document.getElementsByName("wf_defaultStartBranchIds")[0];
			var wf_defaultStartBranchNamesObj =document.getElementsByName("wf_defaultStartBranchNames")[0];
			var wf_canSelectDefaultBranchObj = document.getElementsByName("wf_canSelectDefaultBranch");
			wf_defaultStartBranchIdsObj.value = "";
			wf_defaultStartBranchNamesObj.value = "";
			wf_canSelectDefaultBranchObj.checked = "false";
		}
	}

	/*默认启动分支*/
	AttributeObject.Init.AllModeFuns.push(function(){
		if(AttributeObject.NodeData && AttributeObject.NodeData.defaultStartBranchIds){
			$("#canSelectDiv").show();
		}
	});
	//选择节点
	function selectDefaultLineNode(idField, nameField, splitStr, isMulField){
		var dialog = new KMSSDialog(true, true);
		dialog.BindingField(idField, nameField, splitStr, isMulField);

		var action = function(data){
			//显示是否允许选择
			if(data && data.data.length > 0){
				$("#canSelectDiv").show();
			}else{
				$("#canSelectDiv").hide();
			}
		}
		dialog.SetAfterShow(action);

		var branchNodes = getBranchNodes();
		var data=new KMSSData();
		data.AddHashMapArray(branchNodes);

		dialog.optionData.AddKMSSData(data);
		dialog.Show(window.screen.width*520/1366,window.screen.height*400/768);
	}
	/*
     * 获取每一条分支的第一个节点
     */
	function getBranchNodes(){
		var dataCheckbox = [];
		var splitNode = AttributeObject.NodeObject;
		for(var i=0;i<splitNode.LineOut.length;i++){
			var lineObj=splitNode.LineOut[i];
			var endNode = lineObj.EndNode;
			if(endNode && endNode.Status != 1){
				continue;
			}
			var descRoute=endNode.Data.name+"["+endNode.Data.id+"]";
			dataCheckbox.push({id:endNode.Data.id, name: descRoute});
		}
		return dataCheckbox;
	}

	function selectByFormula(idField, nameField){
		Formula_Dialog(idField,
				nameField,
				FlowChartObject.FormFieldList,
				"com.landray.kmss.sys.organization.model.SysOrgElement[]",
				null,
				"com.landray.kmss.sys.lbpm.engine.formula.LbpmFunction",
				FlowChartObject.ModelName);
	}

	//审批人选择方式
	function switchStartBranchHandlerSelectType(value){
		if(startBranchHandlerSelectType==value)
			return;
		startBranchHandlerSelectType = value;
		SPAN_SelectType1.style.display=startBranchHandlerSelectType=="org"?"":"none";
		SPAN_SelectType2.style.display=startBranchHandlerSelectType=="formula"?"":"none";
		document.getElementsByName("wf_startBranchHandlerId")[0].value = "";
		document.getElementsByName("wf_startBranchHandlerName")[0].value = "";
		AttributeObject.Utils.switchOrgAttributes(document.getElementsByName("wf_startBranchHandlerId")[0], startBranchHandlerSelectType);
	}
</script>