<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/resource/jsp/common.jsp"%>
<%@ taglib uri="/WEB-INF/KmssConfig/sys/lbpmservice/taglib/taglib.tld" prefix="xlang"%>
<%@page import="com.landray.kmss.sys.lbpmservice.taglib.MultiLangTextGroupTag"%>
<%@page import="com.landray.kmss.util.EnumerationTypeUtil"%>
<%@ page import="java.util.List" %>
<%@ page import="com.alibaba.fastjson.JSONArray" %>
<%@ page import="com.alibaba.fastjson.JSON" %>

<%
	// 所有枚举类型
	List enums = EnumerationTypeUtil.getColumnEnumsByType("lbpm_embeddedsubflow_param_type");
	pageContext.setAttribute("enums", JSONArray.parseArray(JSON.toJSONString(enums)));
	
%>
<style>
	#paramsConfigList input[name="fdParamName"][readonly]{
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
					<td width="100px"><kmss:message key="FlowChartObject.Lang.Node.embeddedId" bundle="sys-lbpmservice-node-group" /></td>
					<td>
						<input name="wf_embeddedRefId" type="hidden" />
						<input name="wf_embeddedName" class="inputsgl" style="width:400px" readonly />
						<span id="SPAN_SelectType1">
							<a href="javascript:void(0);" onclick="selectEmbeddedSubFlow(false, 'wf_embeddedRefId', 'wf_embeddedName', '选择嵌入子流程');"><kmss:message key="FlowChartObject.Lang.select" bundle="sys-lbpm-engine" /></a>
						</span>
					</td>
				</tr>
				<c:import url="/sys/lbpmservice/node/common/node_handler_common_operation.jsp" charEncoding="UTF-8" />
				<tr>
					<td><bean:message bundle="sys-lbpmservice-support" key="lbpm.embeddedsubflow.paramConfig" /></td>
					<td>
						<table id="paramsConfigList" class="tb_normal" width="100%">
							<tr>
								<td width="50%"><bean:message bundle="sys-lbpmservice-support" key="lbpm.embeddedsubflow.param" /></td>
								<td width="50%"><bean:message bundle="sys-lbpmservice-support" key="lbpm.embeddedsubflow.field" /></td>
							</tr>
							<tr KMSS_IsReferRow="1" style="display:none" class="content">
								<td width="50%">
									<input type="hidden" name="fdParamValue">
									<input type="hidden" name="fdParamType">
									<input type="text" name="fdParamName" readonly="readonly">
								</td>
								<td width="50%">
									<input name="fdFormName" class="inputsgl" style="">
									<input type="hidden" name="fdFormValue">
									<span id="SPAN_OptSelectType1">
						<a href="#" onclick="paramFormulaDialog(this)"><bean:message key="lbpm.embeddedsubflow.select" bundle="sys-lbpmservice-support" /></a>
					</span> <br> <br>
								</td>
							</tr>
						</table>
						<input type="hidden" name="ext_paramsConfig" />
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
DocList_Info.push("paramsConfigList");
var enums = ${enums};
AttributeObject.Init.AllModeFuns.unshift(function() {
	if (FlowChartObject.IsTemplate == true) {
		if(!AttributeObject.NodeData["embeddedRefId"]){
			Doc_HideLabelById("Label_Tabel","subFlowChart");
		}else{
			loadDefaultFlowByRefId();
		}
	}else{
		var NodeData = AttributeObject.NodeData;
		if(NodeData.isInit=="true" && NodeData["embeddedRefId"]){
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
			for (var i=0;i<subNodes.length;i++) {
				subNodes[i].FormatXMLData();
				processData.nodes.push(subNodes[i].Data);
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
				Doc_SetCurrentLabel("Label_Tabel", 2);
			}
			showParams();
		}else{
			loadDefaultFlowByRefId();
		}
	}
});

function selectEnumValueByType(type){
	var label = "";
	enums.forEach(function (item){
		if(item && item.value === type){
			label = item.label;
			return false;
		}
	});
	return label;
}

function paramFormulaDialog(dom){
	var fdFormValue = $(dom).closest("td").find("input[name='fdFormValue']")
	var fdFormName = $(dom).closest("td").find("input[name='fdFormName']")
	// 当前的数据类型
	var fdParamType = $(dom).closest("tr").find("input[name='fdParamType']");
	Formula_Dialog(fdFormValue[0],fdFormName[0],
			FlowChartObject.FormFieldList, fdParamType[0].value
			, null, 'com.landray.kmss.sys.lbpm.engine.formula.LbpmFunction', FlowChartObject.ModelName);
}

function loadDefaultFlowByRefId(){
	var ajaxurl = Com_Parameter.ContextPath + 'sys/lbpmservice/support/lbpmEmbeddedSubFlow.do?method=getContentByRefId&fdRefId=';
	ajaxurl += AttributeObject.NodeData["embeddedRefId"];
	var kmssData = new KMSSData();
	kmssData.SendToUrl(ajaxurl, function(http_request) {
		var responseText = http_request.responseText;
		var json = eval("("+responseText+")");
		if (json.fdContent){
			showParams(json.fdContent);
			$("textarea[name='fdSubFlowContent']").val(json.fdContent);
			var loadUrl =  '<c:url value="/sys/lbpm/flowchart/page/panel.html" />?embedded=true&edit=false&extend=oa&contentField=fdSubFlowContent&showBar=false&showMenu=false';
			loadUrl += '&template=' + FlowChartObject.IsTemplate;
			loadUrl += '&modelName=' + FlowChartObject.ModelName + '&modelId=' + FlowChartObject.ModelId;
			document.getElementById("WF_IFrame").setAttribute("src", loadUrl);
		}
	},false);
}

function selectEmbeddedSubFlow(isMulti, idField, nameField, title){
	idField = document.getElementsByName(idField)[0];
	nameField = document.getElementsByName(nameField)[0];
	var dialog = new KMSSDialog(isMulti, true);
	dialog.BindingField(idField, nameField);
	dialog.winTitle = title;
	var node = dialog.CreateTree(title);
	node.AppendBeanData("lbpmEmbeddedSubFlowTreeService&type=cate&cateId=!{value}","lbpmEmbeddedSubFlowTreeService&type=doc&cateId=!{value}&modelName="+FlowChartObject.ModelName);
	dialog.SetAfterShow(function(rtnVal){
		if (rtnVal != null && rtnVal.data && rtnVal.data.length>0) {
			showParams(rtnVal.data[0].content);
			Doc_ShowLabelById("Label_Tabel","subFlowChart");
			$("textarea[name='fdSubFlowContent']").val(rtnVal.data[0].content);
			var loadUrl =  '<c:url value="/sys/lbpm/flowchart/page/panel.html" />?embedded=true&edit=false&extend=oa&contentField=fdSubFlowContent&showBar=false&showMenu=false';
			loadUrl += '&template=' + FlowChartObject.IsTemplate;
			loadUrl += '&modelName=' + FlowChartObject.ModelName + '&modelId=' + FlowChartObject.ModelId;
			setTimeout(function(){
				document.getElementById("WF_IFrame").setAttribute("src", loadUrl);
			},0);
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
		Doc_AddLabelSwitchEvent(table, "EmbeddedSubFlow_OnLabelSwitch");
	}
});

function EmbeddedSubFlow_OnLabelSwitch(tableName, index) {
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
		var processData = WorkFlow_LoadXMLData(content);
		if(processData.otherParams){
			var contents = JSON.parse(processData.otherParams);
			for(var i=0;i<contents.length;i++){
				var param = contents[i];
				var fieldValues = new Object();
				fieldValues["fdParamValue"]=param.fdParamValue;
				var fdParamType = param.fdParamType;
				var paramLabel = selectEnumValueByType(param.fdParamType);
				if(fdParamType){
					var fdIsMulti = param.fdIsMulti;
					if(fdParamType && fdParamType.indexOf("ORG_TYPE_") != -1){
						if(fdParamType=='ORG_TYPE_PERSON'){
							if(fdIsMulti=="true"){
								fdParamType = "com.landray.kmss.sys.organization.model.SysOrgPerson[]";
								paramLabel += " [多值] ";
							}else{
								fdParamType = "com.landray.kmss.sys.organization.model.SysOrgPerson";
							}
						}else{
							if(fdIsMulti=="true"){
								paramLabel += " [多值] ";
								fdParamType = "com.landray.kmss.sys.organization.model.SysOrgElement[]";
							}else{
								fdParamType = "com.landray.kmss.sys.organization.model.SysOrgElement";
							}
						}
					}else if(fdParamType=="BigDecimal_Money"){
						fdParamType="BigDecimal";
					}
				}
				fieldValues["fdParamType"]= fdParamType;
				fieldValues["fdParamName"]=param.fdParamName+"("+paramLabel+")";
				for(var k=0;k<paramsConfig.length;k++){
					if(paramsConfig[k].fdParamValue == param.fdParamValue) {
						var formName = paramsConfig[k].fdFormName;
						var formValue = paramsConfig[k].fdFormValue;
						for(var l=0;l<FlowChartObject.FormFieldList.length;l++) {
							var info = FlowChartObject.FormFieldList[l];
							if(formValue === info.name){
								if(!(info.name.indexOf("$")>-1)){
									formName = "$"+info.label+"$";
									formValue = "$"+info.name+"$";
								}
								break;
							}
						}
						fieldValues["fdFormName"] = formName;
						fieldValues["fdFormValue"] = formValue;
						break;
					}
				}

				DocList_AddRow("paramsConfigList",null,fieldValues);

			}
		}
	}else{
		for(var i=0;i<paramsConfig.length;i++){
			var param = paramsConfig[i];
			var fieldValues = new Object();
			fieldValues["fdParamValue"]=param.fdParamValue;
			fieldValues["fdParamName"]=param.fdParamName;

			var formName = param.fdFormName;
			var formValue = param.fdFormValue;
			for(var l=0;l<FlowChartObject.FormFieldList.length;l++) {
				var info = FlowChartObject.FormFieldList[l];
				if(formValue === info.name){
					if(!(info.label.indexOf("$")>-1)){
						formName = "$"+info.label+"$";
						formValue = "$"+info.name+"$";
					}
					break;
				}
			}
			fieldValues["fdFormValue"]=formValue;
			fieldValues["fdFormName"]=formName;

			DocList_AddRow("paramsConfigList",null,fieldValues);
		}
	}
}

//校验事件
AttributeObject.CheckDataFuns.push(function(){
	for(var i = 0;i<$("#paramsConfigList .content").length;i++){
		var row = $("#paramsConfigList .content")[i];
		//var fdFormValue = $(row).find("select[name='fdFormValue']").val();
		var fdFormValue = $(row).find("input[name='fdFormValue']").val();
		if(!fdFormValue){
			alert('<bean:message bundle="sys-lbpmservice-support" key="lbpm.embeddedsubflow.comfimMsg" />');
			return false;
		}
	}
	return true;
});

//提交事件(因写入XML也在提交事件中，故这里需要将提交事件放到最前面)
AttributeObject.SubmitFuns.unshift(function(){
	if(!FlowChartObject.IsTemplate && $("input[name='wf_embeddedRefId']").val()
			&& AttributeObject.NodeData["embeddedRefId"]!=$("input[name='wf_embeddedRefId']").val()
			&& AttributeObject.NodeData.isInit=="true"){
		AttributeObject.NodeData.isInit = "false";
		for(var i = 0;i<FlowChartObject.Nodes.all.length;i++){
			var node = FlowChartObject.Nodes.all[i];
			if(node && node.Data["groupNodeId"]==AttributeObject.NodeData.id && node.Type!="groupStartNode" && node.Type!="groupEndNode"){
				node.Delete();
				i--;
			}
		}
		if(window.opener){
			var groupStartNode = FlowChartObject.Nodes.GetNodeById(AttributeObject.NodeData.startNodeId);
			var groupEndNode = FlowChartObject.Nodes.GetNodeById(AttributeObject.NodeData.endNodeId);
			var line = new opener.FlowChart_Line();
			line.LinkNode(groupStartNode, groupEndNode, FlowChartObject.NODEPOINT_POSITION_BOTTOM, FlowChartObject.NODEPOINT_POSITION_TOP);
		}
	}
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
</script>