<%@ page language="java" contentType="text/html; charset=UTF-8"
		 pageEncoding="UTF-8"%>
<%@ include file="/resource/jsp/common.jsp"%>
<%@ taglib uri="/WEB-INF/KmssConfig/sys/lbpmservice/taglib/taglib.tld" prefix="xlang"%>
<%@page import="com.landray.kmss.sys.lbpmservice.taglib.MultiLangTextGroupTag"%>
<style>
	#paramsConfigList input[name="fdParamName"][readonly]{
		border: none;
		width: 100%;
		text-align: center;
	}
	#groupConfigList input[name="fdRefName"][readonly]{
		border: none;
		width: 100%;
		text-align: center;
	}
</style>
<table width="95%" id="Label_Tabel">
	<tr LKS_LabelName="<kmss:message key="FlowChartObject.Lang.Label_Base" bundle="sys-lbpm-engine" />">
		<td>
			<table width="100%" class="tb_normal" id="config_table">
				<c:import url="/sys/lbpm/engine/node/common/node_name_attribute.jsp" charEncoding="UTF-8" />
				<tr>
					<td width="100px"><kmss:message key="FlowChartObject.Lang.dynamicSubFlow" bundle="sys-lbpmservice-node-group" /></td>
					<td>
						<input name="wf_dynamicGroupId" type="hidden" />
						<input name="wf_dynamicGroupName" class="inputsgl" style="width:400px" readonly />
						<span>
							<a href="javascript:void(0);" onclick="selectDynamicSubFlow(false, 'wf_dynamicGroupId', 'wf_dynamicGroupName', '选择片段组');"><kmss:message key="FlowChartObject.Lang.select" bundle="sys-lbpm-engine" /></a>
						</span>
					</td>
				</tr>
				<tr>
					<td width="100px"><kmss:message key="FlowChartObject.Lang.dynamicSubFlowDisPlayName" bundle="sys-lbpmservice-node-group" /></td>
					<td width="490px">
						<input name="wf_dynamicGroupShowName" class="inputsgl" style="width:80% "><br>
						<span class="com_help"><kmss:message key="FlowChartObject.Lang.dynamicSubFlowTip" bundle="sys-lbpmservice-node-group" /></span>
					</td>
				</tr>
				<tr>
					<td width="100px"><kmss:message key="FlowChartObject.Lang.Node.concurrencyBranchSplitType" bundle="sys-lbpm-engine-node-splitnode" /></td>
					<td width="490px">
						<label><input type="radio" name="wf_splitType" value="all" checked onclick="switchSplitType();"><kmss:message key="FlowChartObject.Lang.Node.concurrencyBranchSplitAllType" bundle="sys-lbpm-engine-node-splitnode" /></label>
						<label><input type="radio" name="wf_splitType" value="custom" onclick="switchSplitType();"><kmss:message key="FlowChartObject.Lang.Node.concurrencyBranchSplitCustomType" bundle="sys-lbpm-engine-node-splitnode" /></label>
					</td>
				</tr>
				<tr id='startBranchHandler'>
					<td width="100px"><kmss:message key="FlowChartObject.Lang.dynamicSubFlowPrivileged" bundle="sys-lbpmservice-node-group" /></td>
					<td width="490px">
						<label><input type="radio" name="wf_startBranchHandlerSelectType" value="org" onclick="switchStartBranchHandlerSelectType(value);" checked><kmss:message key="FlowChartObject.Lang.Node.selectAddress" bundle="sys-lbpmservice" /></label>
						<label><input type="radio" name="wf_startBranchHandlerSelectType" value="formula" onclick="switchStartBranchHandlerSelectType(value);"><kmss:message key="FlowChartObject.Lang.Node.selectFormList" bundle="sys-lbpmservice" /></label>
						<input type="hidden" name="wf_startBranchHandlerId">
						<input name="wf_startBranchHandlerName" class="inputsgl" readonly style="width:80% ">
						<span id="SPAN_SelectType1">
						<a href="#" onclick="Dialog_Address(true, 'wf_startBranchHandlerId', 'wf_startBranchHandlerName', null, ORG_TYPE_POSTORPERSON | ORG_TYPE_ROLE);"><kmss:message key="FlowChartObject.Lang.select" bundle="sys-lbpm-engine" /></a>
						</span>
						<span id="SPAN_SelectType2" style="display:none ">
						<a href="#" onclick="selectByFormula('wf_startBranchHandlerId', 'wf_startBranchHandlerName');"><kmss:message key="FlowChartObject.Lang.select" bundle="sys-lbpm-engine" /></a>
						</span>
						<br>
						<%--<span class="com_help"><kmss:message key="FlowChartObject.Lang.Node.startBranchHandler.desc" bundle="sys-lbpm-engine-node-splitnode"/></span>--%>
					</td>
				</tr>
				<tr id='defaultStartBranch' style="display: none">
					<td width="100px"><kmss:message key="FlowChartObject.Lang.dynamicSubFlowDefault" bundle="sys-lbpmservice-node-group" /></td>
					<td width="490px">
						<input type="hidden" name="wf_defaultStartBranchIds">
						<input name="wf_defaultStartBranchNames" class="inputsgl" readonly style="width:80% ">
						<a href="#" onclick="selectDefaultLineNode('wf_defaultStartBranchIds','wf_defaultStartBranchNames',';',false);"><kmss:message key="FlowChartObject.Lang.select" bundle="sys-lbpm-engine" /></a>
						<br/>
						<div style="display: none" id='canSelectDiv'><input type="checkbox" name="wf_canSelectDefaultBranch" value="true" style="margin-right: 5px;margin-top:5px"><span style="position: relative;top: -1px"><kmss:message key="FlowChartObject.Lang.Node.defaultStartBranch.select" bundle="sys-lbpm-engine-node-splitnode" /></span></div>
					</td>
				</tr>
				<c:import url="/sys/lbpmservice/node/common/node_handler_common_operation.jsp" charEncoding="UTF-8" />
				<tr>
					<td><bean:message bundle="sys-lbpmservice-support" key="lbpm.embeddedsubflow.paramConfig" /></td>
					<td>
						<div style="max-height: 200px;overflow: auto">
							<table id="paramsConfigList" class="tb_normal" width="100%" style="text-align:center;">
								<tr>
									<td width="50%"><bean:message bundle="sys-lbpmservice-support" key="lbpm.embeddedsubflow.param" /></td>
									<td width="50%"><bean:message bundle="sys-lbpmservice-support" key="lbpm.embeddedsubflow.field" /></td>
								</tr>
								<tr KMSS_IsReferRow="1" style="display:none" class="content">
									<td>
										<input type="hidden" name="fdParamValue">
										<input type="text" name="fdParamName" readonly="readonly">
									</td>
									<td>
										<input type="hidden" name="fdFormName">
										<select name="fdFormValue" onchange="fillFormName(this);">
											<option value="">==<bean:message bundle="sys-lbpmservice-support" key="lbpm.embeddedsubflow.select" />==</option>
										</select>
										<span class="txtstrong">*</span>
									</td>
								</tr>
							</table>
							<input type="hidden" name="ext_paramsConfig" />
						</div>
					</td>
				</tr>
				<tr>
					<td colspan="2">
						<table id="groupConfigList" class="tb_normal" width="100%" style="text-align:center;">
							<tr>
								<td width="10%"><bean:message bundle="sys-lbpmservice-support" key="lbpmDynamicSubFlow.orderInfo" /></td>
								<td width="70%"><kmss:message key="FlowChartObject.Lang.dynamicSubFlowName" bundle="sys-lbpmservice-node-group" /></td>
								<td width="20%"><bean:message bundle="sys-lbpmservice-support" key="lbpmDynamicSubFlow.operation" /></td>
							</tr>
							<tr KMSS_IsReferRow="1" style="display:none" class="content">
								<td KMSS_IsRowIndex="1">
									{1}
								</td>
								<td>
									<input type="hidden" name="fdId">
									<input type="hidden" name="fdRefId">
									<input type="text" name="fdRefName" readonly="readonly">
								</td>
								<td>
									<span class="com_btn_link" onclick="viewGroup(this);" style="cursor: pointer"><bean:message bundle="sys-lbpmservice-support" key="button.view" /></span>
								</td>
							</tr>
						</table>
					</td>
				</tr>
			</table>
		</td>
	</tr>
	<tr id="subFlowTr" LKS_LabelName="<kmss:message key="FlowChartObject.Lang.Node.subFlowChart" bundle="sys-lbpmservice-node-group" />" LKS_LabelId="subFlowChart">
		<td>
			<table style="width:100%;height:100%;" class="tb_normal">
				<tr>
					<td>
						<textarea name="fdSubFlowContent" style="display:none"></textarea>
						<input type="hidden" name="fdTranProcessXML">
						<iframe style="width:100%;height:600px;" scrolling="no" id="WF_IFrame"></iframe>
					</td>
				</tr>
			</table>
		</td>
	</tr>
	<tr LKS_LabelName="<kmss:message key="FlowChartObject.Lang.Label_Popedom" bundle="sys-lbpmservice" />" LKS_LabelId="node_right_tr">
		<td>
			<c:import url="/sys/lbpmservice/node/common/node_right_attribute.jsp" charEncoding="UTF-8" />
		</td>
	</tr>
</table>

<script>
	var __groups = null;
	var __params = null;
	DocList_Info.push("paramsConfigList");
	DocList_Info.push("groupConfigList");

	AttributeObject.Init.AllModeFuns.unshift(function() {
		if(AttributeObject.NodeData && AttributeObject.NodeData.defaultStartBranchIds){
			$("#canSelectDiv").show();
		}
		//显示或隐藏默认启动分支
		if(AttributeObject.NodeData.splitType == "custom"){
			var defaultStartBranchObj = document.getElementById("defaultStartBranch");
			defaultStartBranchObj.style.display = "";
		}
		if (FlowChartObject.IsTemplate == true) {
			Doc_HideLabelById("Label_Tabel","subFlowChart");
		}else{
			var NodeData = AttributeObject.NodeData;
			if(NodeData.isInit=="true"){
				var subNodesXML = '<process><nodes></nodes><lines></lines></process>';
				document.getElementsByName("fdSubFlowContent")[0].value = subNodesXML;
				// 构建初始的空白子流程的processData
				var processData = new Array();
				processData.XMLNODENAME = "process";
				processData.nodes = new Array();
				processData.lines = new Array();

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

						node.Data.x = -node.Data.x;
						node.Data.y = -node.Data.y;
						subNodes.push(node);
					}
					for (var i=0;i<node.LineOut.length;i++) {
						if (Com_ArrayGetIndex(subLines, node.LineOut[i]) == -1) {
							var nextNode = node.LineOut[i].EndNode;
							if (node.Type != "groupStartNode" && nextNode.Type != "groupEndNode") {
								subLines.push(node.LineOut[i]);
							}
							if(Com_ArrayGetIndex(subNodes, nextNode) == -1){
								loadSubNodeLine(nextNode);
							}
						}
					}
				};
				// 递归寻找出需要显示在子流程图的子节点和连线，并调整坐标，然后把节点以及连线的信息分别填充到processData的nodes和lines中
				loadSubNodeLine(groupStartNode);
				var isExitBranch = false;
				for (var i=0;i<subNodes.length;i++) {
					subNodes[i].FormatXMLData();
					processData.nodes.push(subNodes[i].Data);
					if(subNodes[i].Data.XMLNODENAME == "autoBranchNode" || subNodes[i].Data.XMLNODENAME == "manualBranchNode"){
						isExitBranch = true;
					}
				}
				for (var j=0;j<subLines.length;j++) {
					subLines[j].FormatXMLData();
					var newData = $.extend(true, {}, subLines[j].Data);
					if(newData._points){
						newData.points = newData._points;
					}
					processData.lines.push(newData);
				}
				// 构建子流程的xml
				subNodesXML = WorkFlow_BuildXMLString(processData, "process");
				// 成功构建并取得子流程的xml后把子节点的坐标还原
				for (var i=0;i<subNodes.length;i++) {
					subNodes[i].Data.x = - subNodes[i].Data.x;
					subNodes[i].Data.y = - subNodes[i].Data.y;
				}
				// 填充子流程XML以及流转信息XML到指定隐藏域
				document.getElementsByName("fdSubFlowContent")[0].value = subNodesXML;
				document.getElementsByName("fdTranProcessXML")[0].value = WorkFlow_BuildXMLString(FlowChartObject.StatusData, "process-status", true);
				//iFrame加载子流程图
				var loadUrl =  '<c:url value="/sys/lbpm/flowchart/page/panel.html" />?embedded=true&edit=false&extend=oa&contentField=fdSubFlowContent&showBar=false&showMenu=false';
				loadUrl += '&template=' + FlowChartObject.IsTemplate;
				loadUrl += '&modelName=' + FlowChartObject.ModelName + '&modelId=' + FlowChartObject.ModelId;
				if (FlowChartObject.StatusData != null) {
					loadUrl += '&statusField=fdTranProcessXML';
				}
				document.getElementById("WF_IFrame").setAttribute("src", loadUrl);
				setTimeout(function(FlowChartObject){
					$("#WF_IFrame").css("width","100%");
					$("#WF_IFrame").css("height","600px");
				},300);
				if (FlowChartObject.IsTemplate == false && FlowChartObject.IsEdit == false) {
					//Doc_SetCurrentLabel("Label_Tabel", 2);
				}
			}else{
				Doc_HideLabelById("Label_Tabel","subFlowChart");
			}
		}
	});

	AttributeObject.Init.AllModeFuns.push(function() {
		showGroups();
		showParams(getParams());
	})

	function selectDynamicSubFlow(isMulti, idField, nameField, title){
		idField = document.getElementsByName(idField)[0];
		nameField = document.getElementsByName(nameField)[0];
		var dialog = new KMSSDialog(isMulti, true);
		dialog.BindingField(idField, nameField);
		dialog.winTitle = title;
		var node = dialog.CreateTree(title);
		node.AppendBeanData("lbpmEmbeddedSubFlowTreeService&type=cate&cateId=!{value}","lbpmDynamicSubFlowTreeServiceImp&cateId=!{value}");
		dialog.SetAfterShow(function(rtnVal){
			if (rtnVal != null && rtnVal.data && rtnVal.data.length>0) {
				__groups = rtnVal.data[0].groups;
				showGroups(__groups);
				if(rtnVal.data[0].params){
					showParams(rtnVal.data[0].params);
				}
				var wf_defaultStartBranchIdsObj =document.getElementsByName("wf_defaultStartBranchIds")[0];
				var wf_defaultStartBranchNamesObj =document.getElementsByName("wf_defaultStartBranchNames")[0];
				var wf_canSelectDefaultBranchObj = document.getElementsByName("wf_canSelectDefaultBranch")[0];
				wf_defaultStartBranchIdsObj.value = "";
				wf_defaultStartBranchNamesObj.value = "";
				wf_canSelectDefaultBranchObj.checked = false;
			}
		});
		dialog.Show();
	}

	Com_AddEventListener(window, "load", function() {
		// 添加标签切换事件
		var table = document.getElementById("subFlowTr");
		while((table != null) && (table.tagName.toLowerCase() != "table")){
			table = table.parentNode;
		}
		if(table != null && window.Doc_AddLabelSwitchEvent){
			Doc_AddLabelSwitchEvent(table, "DynamicSubFlow_OnLabelSwitch");
		}
	});

	function DynamicSubFlow_OnLabelSwitch(tableName, index) {
		var trs = document.getElementById(tableName).rows;
		if(trs[index].id!="subFlowTr")
			return;
		setTimeout(function(){
			$("#WF_IFrame").css("width","100%");
			$("#WF_IFrame").css("height","600px");
		},300);
	}

	function fillFormName(select) {
		var $option=$(select).find("option:selected");
		$(select).closest("tr").find("[name='fdFormName']").val($option.text());
	}

	function showGroups(groups) {
		if(!groups){
			groups = getGroups();
		}
		if(groups){
			$("#groupConfigList tr.content").each(function(){
				DocList_DeleteRow(this);
			})
			var ___groups = JSON.parse(groups);
			for(var i=0;i<___groups.length;i++){
				var param = ___groups[i];
				var fieldValues = new Object();
				fieldValues["fdId"]=param.fdId;
				fieldValues["fdRefId"]=param.fdRefId;
				fieldValues["fdRefName"]=param.fdAlias;
				DocList_AddRow("groupConfigList",null,fieldValues);
			}
		}
	}

	function getGroups(){
		var dynamicGroupId = $("input[name='wf_dynamicGroupId']").val();
		if(dynamicGroupId){
			var fields = new KMSSData().AddBeanData("lbpmDynamicSubFlowTreeServiceImp&type=group&fdId="+dynamicGroupId).GetHashMapArray();
			if(fields && fields.length>0 && fields[0].groups){
				__groups = fields[0].groups;
			}
		}
		return __groups;
	}

	function getParams(){
		var dynamicGroupId = $("input[name='wf_dynamicGroupId']").val();
		if(dynamicGroupId){
			var fields = new KMSSData().AddBeanData("lbpmDynamicSubFlowTreeServiceImp&type=param&fdId="+dynamicGroupId).GetHashMapArray();
			if(fields && fields.length>0 && fields[0].params){
				return fields[0].params;
			}
		}
		return "";
	}

	function showParams(content) {
		$("#paramsConfigList .content").each(function(){
			DocList_DeleteRow(this);
		});
		var paramsConfig = [];
		var extAttributes = AttributeObject.NodeData.extAttributes;
		if(extAttributes){
			for(var i = 0;i<extAttributes.length;i++){
				if(extAttributes[i].name == "paramsConfig" && extAttributes[i].value){
					paramsConfig = JSON.parse(extAttributes[i].value);
					break;
				}
			}
		}
		if(content && FlowChartObject.FormFieldList && FlowChartObject.FormFieldList.length>0){
			var contents = JSON.parse(content);
			for(var i=0;i<contents.length;i++){
				var param = contents[i];
				var fieldValues = new Object();
				fieldValues["fdParamValue"]=param.fdParamValue;
				fieldValues["fdParamName"]=param.fdParamName;
				var tr = DocList_AddRow("paramsConfigList",null,fieldValues);
				var $select = $(tr).find("select[name='fdFormValue']");
				var fdParamType = param.fdParamType;
				if(fdParamType){
					var fdIsMulti = param.fdIsMulti;
					if(fdParamType && fdParamType.indexOf("ORG_TYPE_") != -1){
						if(fdParamType=='ORG_TYPE_PERSON'){
							if(fdIsMulti=="true"){
								fdParamType = "com.landray.kmss.sys.organization.model.SysOrgPerson[]";
							}else{
								fdParamType = "com.landray.kmss.sys.organization.model.SysOrgPerson";
							}
						}else{
							if(fdIsMulti=="true"){
								fdParamType = "com.landray.kmss.sys.organization.model.SysOrgElement[]";
							}else{
								fdParamType = "com.landray.kmss.sys.organization.model.SysOrgElement";
							}
						}
					}else if(fdParamType=="BigDecimal_Money"){
						fdParamType="BigDecimal";
					}
				}
				for(var j=0;j<FlowChartObject.FormFieldList.length;j++){
					var info = FlowChartObject.FormFieldList[j];
					var _type = info.type;
					if(_type=="BigDecimal_Money"){
						_type="BigDecimal";
					}
					if(_type == fdParamType){
						$select.append("<option value='"+info.name+"'>"+info.label+"</option>");
					}
				}
				for(var k=0;k<paramsConfig.length;k++){
					if(paramsConfig[k].fdParamValue == param.fdParamValue){
						$select.val(paramsConfig[k].fdFormValue);
						$(tr).find("[name='fdFormName']").val(paramsConfig[k].fdFormName);
					}
				}
			}
		}else{
			for(var i=0;i<paramsConfig.length;i++){
				var param = paramsConfig[i];
				var fieldValues = new Object();
				fieldValues["fdParamValue"]=param.fdParamValue;
				fieldValues["fdParamName"]=param.fdParamName;
				fieldValues["fdFormValue"]=param.fdFormValue;
				fieldValues["fdFormName"]=param.fdFormName;
				var tr = DocList_AddRow("paramsConfigList",null,fieldValues);
				var $select = $(tr).find("select[name='fdFormValue']");
				$select.append("<option value='"+param.fdFormValue+"'>"+param.fdFormName+"</option>");
				$select.val(param.fdFormValue);
			}
		}
	}

	//校验事件
	AttributeObject.CheckDataFuns.push(function(){
		for(var i = 0;i<$("#paramsConfigList .content").length;i++){
			var row = $("#paramsConfigList .content")[i];
			var fdFormValue = $(row).find("select[name='fdFormValue']").val();
			if(!fdFormValue){
				alert('<bean:message bundle="sys-lbpmservice-support" key="lbpm.embeddedsubflow.comfimMsg" />');
				return false;
			}
		}
		return true;
	});

	//提交事件(因写入XML也在提交事件中，故这里需要将提交事件放到最前面)
	AttributeObject.SubmitFuns.unshift(function(){
		var content = [];
		$("#paramsConfigList .content").each(function(){
			var fdParamValue = $(this).find("[name='fdParamValue']").val();
			var fdParamName = $(this).find("[name='fdParamName']").val();
			var fdFormValue = $(this).find("[name='fdFormValue']").val();
			var fdFormName = $(this).find("[name='fdFormName']").val();
			content.push({"fdParamValue":fdParamValue,"fdParamName":fdParamName,"fdFormValue":fdFormValue,"fdFormName":fdFormName});
		});
		$("input[name='ext_paramsConfig']").val(JSON.stringify(content));
	});

	function selectByFormula(idField, nameField){
		Formula_Dialog(idField,
				nameField,
				FlowChartObject.FormFieldList,
				"com.landray.kmss.sys.organization.model.SysOrgElement[]",
				null,
				"com.landray.kmss.sys.lbpm.engine.formula.LbpmFunction",
				FlowChartObject.ModelName);
	}

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
	 * 获取分支节点
	 */
	function getBranchNodes(){
		var dataCheckbox = [];
		if(__groups){
			var ___groups = JSON.parse(__groups);
			for(var i=0;i<___groups.length;i++){
				var param = ___groups[i];
				dataCheckbox.push({id:param.fdId, name: param.fdAlias});
			}
		}
		return dataCheckbox;
	}

	function switchSplitType() {
		var splitType = document.getElementsByName("wf_splitType"),
				i,
				typeValue;

		for (i = 0; i < splitType.length; i++) {
			if (splitType[i].checked) {
				typeValue = splitType[i].value;
				break;
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
			var wf_canSelectDefaultBranchObj = document.getElementsByName("wf_canSelectDefaultBranch")[0];
			wf_defaultStartBranchIdsObj.value = "";
			wf_defaultStartBranchNamesObj.value = "";
			wf_canSelectDefaultBranchObj.checked = false;
			$("#canSelectDiv").hide();
		}
	}

	function viewGroup(dom){
		var dialog = new KMSSDialog();
		dialog.Parameters = {
			__groups : __groups,
			fdId : $(dom).closest("tr").find("input[name='fdId']").val(),
			FlowChartObject : FlowChartObject
		};
		dialog.URL = Com_Parameter.ContextPath
				+ "sys/lbpmservice/node/group/dynamicsubflownode/node_group_attribute.jsp?t="+encodeURIComponent(new Date());
		dialog.Show(window.screen.width*600/1366,window.screen.height*480/768);
	}

	var startBranchHandlerSelectType = AttributeObject.NodeData["startBranchHandlerSelectType"];

	function switchStartBranchHandlerSelectType(value){
		if(startBranchHandlerSelectType==value)
			return;
		startBranchHandlerSelectType = value;
		SPAN_SelectType1.style.display=startBranchHandlerSelectType=="org"?"":"none";
		SPAN_SelectType2.style.display=startBranchHandlerSelectType=="formula"?"":"none";
		document.getElementsByName("wf_startBranchHandlerId")[0].value = "";
		document.getElementsByName("wf_startBranchHandlerName")[0].value = "";
	}
</script>