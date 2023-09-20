<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/resource/jsp/common.jsp"%>
<%@ taglib uri="/WEB-INF/KmssConfig/sys/lbpmservice/taglib/taglib.tld" prefix="xlang"%>
<%@page import="com.landray.kmss.sys.lbpmservice.taglib.MultiLangTextareaGroupTagNew"%>
<style>
.inputread {border:0px; color:#868686;background-color:#f6f6f6;}
.inputsgl {width:150px;border:0px !important;margin:3px 0 3px 0;}
.tdTitle {color:#666;width:75px;}
</style>
<!-- 自由流的抄送节点属性面板 -->
<table width="420px" id="Label_Tabel">
	<tr>
		<td style="background-color:#f6f6f6 !important;">
			<table width="95%" style="background-color:#f6f6f6 !important;" class="tb_normal">
				<c:import url="/sys/lbpmservice/node/common/node_fixed_attribute.jsp" charEncoding="UTF-8">
				</c:import>
				<c:import url="/sys/lbpm/engine/node/common/node_name_attribute.jsp" charEncoding="UTF-8">
					<c:param name="flowType" value="1" />
				</c:import>
				<tr>
					<td class="tdTitle"><kmss:message key="FlowChartObject.Lang.Node.handlerNames_Send" bundle="sys-lbpmservice" /></td>
					<td>
						<span id="handlerSelectTypeSpan">
							<label><input type="radio" name="wf_handlerSelectType" value="org" onclick="switchHandlerSelectType(value);" checked><kmss:message key="FlowChartObject.Lang.Node.selectAddress" bundle="sys-lbpmservice" /></label>
							<label><input type="radio" name="wf_handlerSelectType" value="matrix" onclick="switchHandlerSelectType(value);"><kmss:message key="FlowChartObject.Lang.Node.selectOrgMatrix" bundle="sys-lbpmservice" /></label>
							<label><input type="radio" name="wf_handlerSelectType" value="formula" onclick="switchHandlerSelectType(value);"><kmss:message key="FlowChartObject.Lang.Node.selectFormList" bundle="sys-lbpmservice" /></label>
							<br>
						</span>
						<input name="wf_handlerNames" class="inputsgl" style="width:200px" readonly>
						<input name="wf_handlerIds" type="hidden" orgattr="handlerIds:handlerNames">
						<span id="SPAN_SelectType1">
							<a href="#" onclick="selectByOrg('wf_handlerIds', 'wf_handlerNames');"><kmss:message key="FlowChartObject.Lang.select" bundle="sys-lbpm-engine" /></a>
						</span>
						<span id="SPAN_SelectType2" style="display:none ">
							<a href="#" onclick="selectByFormula('wf_handlerIds', 'wf_handlerNames');"><kmss:message key="FlowChartObject.Lang.select" bundle="sys-lbpm-engine" /></a>
						</span>
						<span id="SPAN_SelectType3" style="display:none ">
							<a href="#" onclick="selectByMatrix('wf_handlerIds', 'wf_handlerNames');"><kmss:message key="FlowChartObject.Lang.select" bundle="sys-lbpm-engine" /></a>
						</span>
						<span class="txtstrong">*</span>
						<br/>
						<label>
							<input type="checkbox" name="wf_ignoreOnHandlerEmpty" value="true">
							<kmss:message key="FlowChartObject.Lang.Node.ignoreOnHandlerCalculateEmpty" bundle="sys-lbpmservice" />
						</label>
						<label>
							<input type="checkbox" name="wf_canAddOpinion" value="true">
							<kmss:message key="FlowChartObject.Lang.Node.canAddOpinion" bundle="sys-lbpmservice" />
						</label>
					</td>
				</tr>
				<tr>
					<td class="tdTitle"><bean:message bundle="sys-lbpmservice" key="lbpmNode.currentNode.notify.type" /></td>
					<td id="NODE_TD_notifyType">
						<kmss:editNotifyType property="node_notifyType" value="no" /><br />
					</td>
				</tr>	
				<tr style="display:none">
					<td width="100px"><kmss:message key="FlowChartObject.Lang.Node.description" bundle="sys-lbpm-engine" /></td>
					<td>
						<%-- <textarea name="wf_description" style="width:100%"></textarea>
						<xlang:lbpmlangArea property="_wf_description" style="width:100%" langs=""/>
						<br> --%>
						<c:if test="${!isLangSuportEnabled }">
							<textarea name="wf_description" style="width:100%"></textarea>
							<br>
						</c:if>
						<c:if test="${isLangSuportEnabled }">
						 	<xlang:lbpmlangAreaNew property="_wf_description" alias="wf_description" style="width:100%" langs=""/>
						</c:if>
						<kmss:message key="FlowChartObject.Lang.Node.imgLink" bundle="sys-lbpm-engine" />
					</td>
				</tr>
				<tr style="display:none">
		<td>
			<table class="tb_normal" width="100%">
				<tr>
					<td width="100px"><kmss:message key="FlowChartObject.Lang.Node.optHandlerNames_Send" bundle="sys-lbpmservice" /></td>
					<td>
						<label><input type="radio" name="wf_optHandlerSelectType" value="org" onclick="switchOptHandlerSelectType(value);" checked><kmss:message key="FlowChartObject.Lang.Node.selectAddress" bundle="sys-lbpmservice" /></label>
						<label><input type="radio" name="wf_optHandlerSelectType" value="formula" onclick="switchOptHandlerSelectType(value);"><kmss:message key="FlowChartObject.Lang.Node.selectFormList" bundle="sys-lbpmservice" /></label>
						<label><input type="radio" name="wf_optHandlerSelectType" value="mechanism" onclick="switchOptHandlerSelectType(value);"><kmss:message key="FlowChartObject.Lang.Node.selectMechanism" bundle="sys-lbpmservice" /></label>
						<label><input type="radio" name="wf_optHandlerSelectType" value="dept" onclick="switchOptHandlerSelectType(value);"><kmss:message key="FlowChartObject.Lang.Node.selectDept" bundle="sys-lbpmservice" /></label>
						<input name="wf_optHandlerIds" type="hidden" orgattr="optHandlerIds:optHandlerNames">
						<input name="wf_optHandlerNames" class="inputsgl" style="width:400px" readonly>
						<span id="SPAN_OptSelectType1">
						<a href="#" onclick="Dialog_Address(true, 'wf_optHandlerIds', 'wf_optHandlerNames', null, ORG_TYPE_ALL | ORG_TYPE_ROLE);"><kmss:message key="FlowChartObject.Lang.select" bundle="sys-lbpm-engine" /></a>
						</span>
						<span id="SPAN_OptSelectType2" style="display:none ">
						<a href="#" onclick="selectByFormula('wf_optHandlerIds', 'wf_optHandlerNames');"><kmss:message key="FlowChartObject.Lang.select" bundle="sys-lbpm-engine" /></a>
						</span>
						<br><div id="DIV_OptHandlerCalType"><kmss:message key="FlowChartObject.Lang.Node.optHandlerCalType" bundle="sys-lbpmservice" />: 
						<label>
							<input name="wf_optHandlerCalType" type="radio" value="1">
							<kmss:message key="FlowChartObject.Lang.Node.handler" bundle="sys-lbpm-engine" />
						</label><label>
							<input name="wf_optHandlerCalType" type="radio" value="2" checked>
							<kmss:message key="FlowChartObject.Lang.Node.creator" bundle="sys-lbpm-engine" />
						</label><br></div><label>
							<input name="wf_useOptHandlerOnly" type="checkbox" value="true">
							<kmss:message key="FlowChartObject.Lang.Node.useOptHandlerOnly" bundle="sys-lbpmservice" />
						</label>
					</td>
				</tr>
			</table>
		</td>
	</tr>
	<tr style="display:none">
		<td>
		<table class="tb_normal" width="100%">
			<tr>
				<td rowspan="2" width="100px"><kmss:message key="FlowChartObject.Lang.Right.canViewCurNodePopedomSet" bundle="sys-lbpm-engine" /></td>
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
		</table>
		</td>
	</tr>
	<tr style="display:none">
		<td>
		<c:import url="/sys/lbpm/flowchart/page/node_event_attribute.jsp" charEncoding="UTF-8" />
		</td>
	</tr>
			</table>
		</td>
	</tr>
</table>

<script>
var handlerSelectType = AttributeObject.NodeData["handlerSelectType"];
var optHandlerSelectType = AttributeObject.NodeData["optHandlerSelectType"];


AttributeObject.Init.AllModeFuns.push(function() {

	if(!handlerSelectType || (handlerSelectType!="formula" && handlerSelectType!="matrix")){
		document.getElementById('SPAN_SelectType1').style.display='';
		document.getElementById('SPAN_SelectType2').style.display='none';
		document.getElementById('SPAN_SelectType3').style.display='none';
	}else{
		document.getElementById('SPAN_SelectType1').style.display='none';
		if (handlerSelectType=="formula") {
			document.getElementById('SPAN_SelectType2').style.display='';
		} else if (handlerSelectType=="matrix") {
			document.getElementById('SPAN_SelectType3').style.display='';
		}
	}

	if (optHandlerSelectType=="formula"){
		document.getElementById('SPAN_OptSelectType1').style.display='none';
		document.getElementById('SPAN_OptSelectType2').style.display='';
		document.getElementById('DIV_OptHandlerCalType').style.display='none';
		document.getElementsByName("wf_optHandlerNames")[0].style.display="";
	} else if (!optHandlerSelectType || optHandlerSelectType=="org"){
		document.getElementById('SPAN_OptSelectType1').style.display='';
		document.getElementById('SPAN_OptSelectType2').style.display='none';
		document.getElementById('DIV_OptHandlerCalType').style.display='';
		document.getElementsByName("wf_optHandlerNames")[0].style.display="";
	} else {
		document.getElementById('SPAN_OptSelectType1').style.display='none';
		document.getElementById('SPAN_OptSelectType2').style.display='none';
		document.getElementById('DIV_OptHandlerCalType').style.display='none';
		document.getElementsByName("wf_optHandlerNames")[0].style.display="none";
	}
	
	var settingInfo = getSettingInfo();
	var notifyType = AttributeObject.NodeData["notifyType"];
	if (!notifyType) {
		notifyType = settingInfo["defaultNotifyType"];
	}
	$("input[name^='__notify_type_']:checkbox").each(function(index,element){
		if(notifyType && notifyType.indexOf($(element).val())>-1){
			$(element).attr("checked","true");
		}else{
			$(element).removeAttr("checked");
		}
	});
	var isOpenNewWin = "${JsParam.isOpenNewWin eq 'true'}";
	if(isOpenNewWin!="true"){
		// 自由流实时保存节点数据
		$("input[name^='__notify_type_']:checkbox").change(function(){
			saveNodeData();
		});
		$("input[name^='_wf_name_']").change(function(){
			saveNodeData();
		});
		$("input[name^='wf_']").change(function(){
			saveNodeData();
		});
	}
});


//数据校验
AttributeObject.CheckDataFuns.push(function(data) {
	if(data.useOptHandlerOnly=="true" && data.optHandlerIds==""){
		//如果选择的是本部门或者本机构，不进行备选列表为空判断，否则要进行判断
		if(data.optHandlerSelectType && data.optHandlerSelectType != "dept" && data.optHandlerSelectType != "mechanism"){
			alert('<kmss:message key="FlowChartObject.Lang.Node.checkOptHandlerEmpty" bundle="sys-lbpmservice" />');
			return false;
		}
	}
	/* if (!data.handlerIds || data.handlerIds == "") {
		alert('<kmss:message key="FlowChartObject.Lang.Node.checkHandlerEmpty" bundle="sys-lbpmservice" />');
		return false;
	} */
	return true;
});

//审批人选择方式
function switchHandlerSelectType(value){
	if(handlerSelectType==value)
		return;
	handlerSelectType = value;
	SPAN_SelectType1.style.display=handlerSelectType=="org"?"":"none";
	SPAN_SelectType2.style.display=handlerSelectType=="formula"?"":"none";
	SPAN_SelectType3.style.display=handlerSelectType=="matrix"?"":"none";
	document.getElementsByName("wf_handlerIds")[0].value = "";
	document.getElementsByName("wf_handlerNames")[0].value = "";

	AttributeObject.Utils.switchOrgAttributes(document.getElementsByName("wf_handlerIds")[0], handlerSelectType);
}

//备选审批人选择方式
function switchOptHandlerSelectType(value) {
	if(optHandlerSelectType==value)
		return;
	optHandlerSelectType = value;
	document.getElementById('SPAN_OptSelectType1').style.display=optHandlerSelectType=="org"?"":"none";
	document.getElementById('SPAN_OptSelectType2').style.display=optHandlerSelectType=="formula"?"":"none";
	document.getElementById('DIV_OptHandlerCalType').style.display=optHandlerSelectType=="org"?"":"none";
	document.getElementsByName("wf_optHandlerNames")[0].style.display=(optHandlerSelectType == "formula" || optHandlerSelectType == "org")?"":"none";
	document.getElementsByName("wf_optHandlerIds")[0].value = "";
	document.getElementsByName("wf_optHandlerNames")[0].value = "";
}
function selectNotionNodes(idField, nameField){
	var data = new KMSSData(), NodeData = AttributeObject.NodeData;
	for(var i=0; i<FlowChartObject.Nodes.all.length; i++){
		var node = FlowChartObject.Nodes.all[i];
		if(node.Data.id == NodeData.id)
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
function selectByOrg(idField, nameField){
	var orgType = ORG_TYPE_ALL | ORG_TYPE_ROLE;
	Dialog_Address(true, idField, nameField, null, orgType);
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
//使用矩阵组织选择
function selectByMatrix(idField, nameField){
	// 弹出矩阵组织设置窗口
	var dialog = new KMSSDialog();
	dialog.FormFieldList = FlowChartObject.FormFieldList;
	dialog.ModelName = FlowChartObject.ModelName;
	dialog.BindingField(idField, nameField);
	dialog.URL = Com_Parameter.ContextPath + "sys/lbpmservice/node/common/node_handler_matrix_config.jsp";
	var size = getSizeForAddress();
	dialog.Show(size.width, size.height);
}
AttributeObject.Init.EditModeFuns.push(function(nodeData) {
	//多语言
	_initPropLang4Edit("nodeDesc",nodeData,"description","_");
});
AttributeObject.Init.ViewModeFuns.push(function(nodeData) {
	//多语言
	_initPropLang4View("nodeDesc",nodeData,"description","_");
});

AttributeObject.AppendDataFuns.push(function(nodeData){
/**
	"nodeDesc":[//描述
		{"lang":"zh-CN","value":"主管审批意见"},{"lang":"en-US","value":"Manager Auditing Note"}
	],
**/
	_propLang4AppendData("nodeDesc",nodeData,"description","_");
	
	var notifyType = "";
	$("input[name^='__notify_type_']:checkbox:checked").each(function(index,element){
		notifyType+=";"+$(element).val();
	});
	if(notifyType){
		notifyType = notifyType.substring(1);
		nodeData["notifyType"]=notifyType;
	}else{
		nodeData["notifyType"]=null;
	}
});
</script>