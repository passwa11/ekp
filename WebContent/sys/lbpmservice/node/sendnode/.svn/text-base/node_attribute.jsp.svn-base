<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/resource/jsp/common.jsp"%>
<%@ taglib uri="/WEB-INF/KmssConfig/sys/lbpmservice/taglib/taglib.tld" prefix="xlang"%>
<%@page import="com.landray.kmss.sys.lbpmservice.taglib.MultiLangTextareaGroupTagNew"%>
<table width="590px" id="Label_Tabel">
	<tr LKS_LabelName="<kmss:message key="FlowChartObject.Lang.Label_Base" bundle="sys-lbpm-engine" />">
		<td>
			<table width="100%" class="tb_normal">
				<c:import url="/sys/lbpm/engine/node/common/node_name_attribute.jsp" charEncoding="UTF-8" />
				<tr>
					<td width="100px"><kmss:message key="FlowChartObject.Lang.Node.handlerNames_Send" bundle="sys-lbpmservice" /></td>
					<td>
						<label><input type="radio" name="wf_handlerSelectType" value="org" onclick="switchHandlerSelectType(value);" checked><kmss:message key="FlowChartObject.Lang.Node.selectAddress" bundle="sys-lbpmservice" /></label>
						<label><input type="radio" name="wf_handlerSelectType" value="matrix" onclick="switchHandlerSelectType(value);"><kmss:message key="FlowChartObject.Lang.Node.selectOrgMatrix" bundle="sys-lbpmservice" /></label>
						<label><input type="radio" name="wf_handlerSelectType" value="formula" onclick="switchHandlerSelectType(value);"><kmss:message key="FlowChartObject.Lang.Node.selectFormList" bundle="sys-lbpmservice" /></label>
						<label><input type="radio" name="wf_handlerSelectType" value="rule" onclick="switchHandlerSelectType(value);"><kmss:message key="FlowChartObject.Lang.Node.selectRule" bundle="sys-lbpmservice" /></label>
						<input name="wf_handlerNames" class="inputsgl" style="width:400px" readonly>
						<input name="wf_handlerIds" type="hidden" orgattr="handlerIds:handlerNames">
						<span id="SPAN_SelectType1">
						<a href="#" onclick="Dialog_Address(true, 'wf_handlerIds', 'wf_handlerNames', null, ORG_TYPE_ALL | ORG_TYPE_ROLE);"><kmss:message key="FlowChartObject.Lang.select" bundle="sys-lbpm-engine" /></a>
						</span>
						<span id="SPAN_SelectType2" style="display:none ">
						<a href="#" onclick="selectByFormula('wf_handlerIds', 'wf_handlerNames');"><kmss:message key="FlowChartObject.Lang.select" bundle="sys-lbpm-engine" /></a>
						</span>
						<span id="SPAN_SelectType3" style="display:none "><br/>
						<div style="padding-top:6px">
						<kmss:message key="FlowChartObject.Lang.Node.orgMatrix" bundle="sys-lbpmservice" /> &nbsp;&nbsp;
						<input name="orgMatrixId" type="hidden">
						<input name="orgMatrixName" class="inputsgl" style="width:75%" readonly>
						<a href="#" onclick="selectOrgMatrix();"><kmss:message key="FlowChartObject.Lang.select" bundle="sys-lbpm-engine" /></a><br/>
						<input name="matrixVersion" type="hidden">
						<kmss:message key="FlowChartObject.Lang.Node.orgMatrix.results" bundle="sys-lbpmservice" /> &nbsp;&nbsp;
						<input name="matrixResultId" type="hidden">
						<input name="matrixResultName" class="inputsgl" style="width:75%" readonly>
						<a href="#" onclick="selectMatrixResultField();"><kmss:message key="FlowChartObject.Lang.select" bundle="sys-lbpm-engine" /></a><br/>
						<div style="margin-top:5px;">
							<kmss:message key="FlowChartObject.Lang.Node.orgMatrix.resultsMoreThanOne" bundle="sys-lbpmservice" />
							<label style="margin-right:10px;"><input type="radio" name="matrixResultOption" value="1" checked>
								<kmss:message key="FlowChartObject.Lang.Node.orgMatrix.resultOptionOfFirst" bundle="sys-lbpmservice" /></label>
							<label style="margin-right:10px;"><input type="radio" name="matrixResultOption" value="2">
								<kmss:message key="FlowChartObject.Lang.Node.orgMatrix.resultOptionOfAll" bundle="sys-lbpmservice" /></label>
							<label style="margin-right:10px;"><input type="radio" name="matrixResultOption" value="3">
								<kmss:message key="FlowChartObject.Lang.Node.orgMatrix.resultOptionOfError" bundle="sys-lbpmservice" /></label>
						</div>
						<div style="margin-top:6px;padding:2px;width:100%;height:80%;">
							<table id="conditionParamList" class="tb_normal" width="97%">
								<tr>
									<td width="45%"><kmss:message key="FlowChartObject.Lang.Node.orgMatrix.conditionParam" bundle="sys-lbpmservice" /></td>
									<td width="45%"><kmss:message key="FlowChartObject.Lang.Node.orgMatrix.conditionValue" bundle="sys-lbpmservice" /></td>
									<td width="10%" align="center">
										<a href="javascript:void(0)" onclick="addMatrixOrgConditionParamRow();"><img src="${KMSS_Parameter_StylePath}icons/add.gif" title="<kmss:message key="FlowChartObject.Lang.Operation.operationAdd" bundle="sys-lbpmservice" />"></a>
									</td>
								</tr>
								<tr KMSS_IsReferRow="1" style="display:none">
									<td>
										<select name="conditionParam" onchange="conditionParamChange(this);" style="width:100%">
										</select>
										<select name="conditionType" style="width:35%;display:none">
											<option value="fdId">ID</option>
											<option value="fdName">Name</option>
										</select>
									</td>
									<td>
										<input type="hidden" name="conditionParam.expression.value">
										<input type="text" name="conditionParam.expression.text" readonly="readonly" style="width:80%" class="inputSgl">
										<a href="javascript:void(0);"  onclick="assignConditionParamValue(this);"><kmss:message key="FlowChartObject.Lang.Node.select" bundle="sys-lbpmservice" /></a>
									</td>
									<td align="center">
										<a href="javascript:void(0)" onclick="DocList_DeleteRow();"><img src="${KMSS_Parameter_StylePath}icons/delete.gif" title="<kmss:message key="FlowChartObject.Lang.Operation.operationDelete" bundle="sys-lbpmservice" />"></a>
									</td>
								</tr>
							</table>
						</div>
						</div>
						</span>
						<span id="SPAN_SelectType4" style="display:none">
							<c:import url="/sys/rule/sys_ruleset_quote/sysRuleQuote.jsp" charEncoding="UTF-8">
								<c:param name="type" value="reviewnode"></c:param>
								<c:param name="returnType" value="ORG_TYPE_POST|ORG_TYPE_PERSON"></c:param>
								<c:param name="mode" value="all"></c:param>
								<c:param name="key" value="handler"></c:param>
							</c:import>
						</span>
						<br/>
						<label>
							<input type="checkbox" name="wf_ignoreOnHandlerEmpty" value="true" checked>
							<kmss:message key="FlowChartObject.Lang.Node.ignoreOnHandlerEmpty" bundle="sys-lbpmservice" />
						</label>
						<label>
							<input type="checkbox" name="wf_canAddOpinion" value="true">
							<kmss:message key="FlowChartObject.Lang.Node.canAddOpinion" bundle="sys-lbpmservice" />
						</label>
					</td>
				</tr>
				<c:import url="/sys/lbpmservice/node/common/node_notify_attribute.jsp" charEncoding="UTF-8" />	
				<tr>
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
			</table>
		</td>
	</tr>
	<tr LKS_LabelName="<kmss:message key="FlowChartObject.Lang.Label_Advance" bundle="sys-lbpm-engine" />">
		<td>
			<table class="tb_normal" width="100%">
				<tr>
					<td width="100px"><kmss:message key="FlowChartObject.Lang.Node.optHandlerNames_Send" bundle="sys-lbpmservice" /></td>
					<td>
						<label><input type="radio" name="wf_optHandlerSelectType" value="org" onclick="switchOptHandlerSelectType(value);" checked><kmss:message key="FlowChartObject.Lang.Node.selectAddress" bundle="sys-lbpmservice" /></label>
						<label><input type="radio" name="wf_optHandlerSelectType" value="formula" onclick="switchOptHandlerSelectType(value);"><kmss:message key="FlowChartObject.Lang.Node.selectFormList" bundle="sys-lbpmservice" /></label>
						<label><input type="radio" name="wf_optHandlerSelectType" value="mechanism" onclick="switchOptHandlerSelectType(value);"><kmss:message key="FlowChartObject.Lang.Node.selectMechanism" bundle="sys-lbpmservice" /></label>
						<label><input type="radio" name="wf_optHandlerSelectType" value="dept" onclick="switchOptHandlerSelectType(value);"><kmss:message key="FlowChartObject.Lang.Node.selectDept" bundle="sys-lbpmservice" /></label>
						<label><input type="radio" name="wf_optHandlerSelectType" value="rule" onclick="switchOptHandlerSelectType(value);"><kmss:message key="FlowChartObject.Lang.Node.selectRule" bundle="sys-lbpmservice" /></label>
						<label><input type="radio" name="wf_optHandlerSelectType" value="otherOrgDept" onclick="switchOptHandlerSelectType(value);"><kmss:message key="FlowChartObject.Lang.Node.selectOtherOrgDept" bundle="sys-lbpmservice" /></label><br/>
						<input name="wf_optHandlerIds" type="hidden" orgattr="optHandlerIds:optHandlerNames">
						<input name="wf_optHandlerNames" class="inputsgl" style="width:400px" readonly>
						<span id="SPAN_OptSelectType1">
						<a href="#" onclick="Dialog_Address(true, 'wf_optHandlerIds', 'wf_optHandlerNames', null, ORG_TYPE_ALL | ORG_TYPE_ROLE);"><kmss:message key="FlowChartObject.Lang.select" bundle="sys-lbpm-engine" /></a>
						</span>
						<span id="SPAN_OptSelectType2" style="display:none ">
						<a href="#" onclick="selectByFormula('wf_optHandlerIds', 'wf_optHandlerNames');"><kmss:message key="FlowChartObject.Lang.select" bundle="sys-lbpm-engine" /></a>
						</span>
						<span id="SPAN_OptSelectType3" style="display:none">
							<c:import url="/sys/rule/sys_ruleset_quote/sysRuleQuote.jsp" charEncoding="UTF-8">
								<c:param name="type" value="reviewnode"></c:param>
								<c:param name="returnType" value="ORG_TYPE_POST|ORG_TYPE_PERSON|ORG_TYPE_DEPT"></c:param>
								<c:param name="mode" value="all"></c:param>
								<c:param name="key" value="optHandler"></c:param>
							</c:import>
						</span>
						<span id="SPAN_OptSelectType4" style="display:none">
							<a href="#" onclick="Dialog_Address(true, 'wf_optHandlerIds', 'wf_optHandlerNames', null, ORG_TYPE_ORGORDEPT);"><kmss:message key="FlowChartObject.Lang.select" bundle="sys-lbpm-engine" /></a>
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
	<tr LKS_LabelName="<kmss:message key="FlowChartObject.Lang.Label_Popedom" bundle="sys-lbpmservice" />" LKS_LabelId="node_right_tr">
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
	<tr LKS_LabelName="<kmss:message key="FlowChartObject.Lang.Label_Event" bundle="sys-lbpm-engine" />">
		<td>
		<c:import url="/sys/lbpm/flowchart/page/node_event_attribute.jsp" charEncoding="UTF-8" />
		</td>
	</tr>
	<c:import url="/sys/lbpmservice/node/common/node_custom_notify_attribute.jsp" charEncoding="UTF-8">
		<c:param name="nodeType" value="${param.nodeType}" />
		<c:param name="modelName" value="${param.modelName }" />
	</c:import>
</table>

<script>
var handlerSelectType = AttributeObject.NodeData["handlerSelectType"];
var optHandlerSelectType = AttributeObject.NodeData["optHandlerSelectType"];


AttributeObject.Init.AllModeFuns.push(function() {

	if(!handlerSelectType || (handlerSelectType!="formula" && handlerSelectType!="matrix" && handlerSelectType!="rule")){
		document.getElementById('SPAN_SelectType1').style.display='';
		document.getElementById('SPAN_SelectType2').style.display='none';
		document.getElementById('SPAN_SelectType3').style.display='none';
		document.getElementById('SPAN_SelectType4').style.display='none';
	}else{
		document.getElementById('SPAN_SelectType1').style.display='none';
		if (handlerSelectType=="formula") {
			document.getElementById('SPAN_SelectType2').style.display='';
		} else if (handlerSelectType=="matrix") {
			document.getElementsByName("wf_handlerNames")[0].style.display = "none";
			document.getElementById('SPAN_SelectType3').style.display='';
		}else if (handlerSelectType=="rule") {
			document.getElementsByName("wf_handlerNames")[0].style.display = "none";
			document.getElementById('SPAN_SelectType4').style.display='';
			$(".rule.handler").eq(0).show();
		}
	}

	if (optHandlerSelectType=="formula"){
		document.getElementById('SPAN_OptSelectType1').style.display='none';
		document.getElementById('SPAN_OptSelectType2').style.display='';
		document.getElementById('SPAN_OptSelectType3').style.display='none';
		document.getElementById('SPAN_OptSelectType4').style.display='none';
		document.getElementById('DIV_OptHandlerCalType').style.display='none';
		document.getElementsByName("wf_optHandlerNames")[0].style.display="";
	} else if (!optHandlerSelectType || optHandlerSelectType=="org"){
		document.getElementById('SPAN_OptSelectType1').style.display='';
		document.getElementById('SPAN_OptSelectType2').style.display='none';
		document.getElementById('SPAN_OptSelectType3').style.display='none';
		document.getElementById('SPAN_OptSelectType4').style.display='none';
		document.getElementById('DIV_OptHandlerCalType').style.display='';
		document.getElementsByName("wf_optHandlerNames")[0].style.display="";
	}  else if(optHandlerSelectType=="rule"){
		document.getElementById('SPAN_OptSelectType1').style.display='none';
		document.getElementById('SPAN_OptSelectType2').style.display='none';
		document.getElementById('SPAN_OptSelectType3').style.display='';
		document.getElementById('SPAN_OptSelectType4').style.display='none';
		document.getElementById('DIV_OptHandlerCalType').style.display='none';
		document.getElementsByName("wf_optHandlerNames")[0].style.display="none";
		$(".rule.optHandler").eq(0).show();
	}  else if(optHandlerSelectType=="otherOrgDept"){
		document.getElementById('SPAN_OptSelectType1').style.display='none';
		document.getElementById('SPAN_OptSelectType2').style.display='none';
		document.getElementById('SPAN_OptSelectType3').style.display='none';
		document.getElementById('SPAN_OptSelectType4').style.display='';
		document.getElementById('DIV_OptHandlerCalType').style.display='none';
		document.getElementsByName("wf_optHandlerNames")[0].style.display="";
	} else {
		document.getElementById('SPAN_OptSelectType1').style.display='none';
		document.getElementById('SPAN_OptSelectType2').style.display='none';
		document.getElementById('SPAN_OptSelectType3').style.display='none';
		document.getElementById('SPAN_OptSelectType4').style.display='none';
		document.getElementById('DIV_OptHandlerCalType').style.display='none';
		document.getElementsByName("wf_optHandlerNames")[0].style.display="none";
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
	return true;
});

function switchHandlerSelectType(value){
	if(handlerSelectType==value)
		return;
	handlerSelectType = value;
	SPAN_SelectType1.style.display=handlerSelectType=="org"?"":"none";
	SPAN_SelectType2.style.display=handlerSelectType=="formula"?"":"none";
	SPAN_SelectType3.style.display=handlerSelectType=="matrix"?"":"none";
	SPAN_SelectType4.style.display=handlerSelectType=="rule"?"":"none";
	document.getElementsByName("wf_handlerIds")[0].value = "";
	document.getElementsByName("wf_handlerNames")[0].value = "";
	if (handlerSelectType == "matrix" || handlerSelectType == "rule") {
		document.getElementsByName("wf_handlerNames")[0].style.display = "none";
	} else {
		document.getElementsByName("wf_handlerNames")[0].style.display = "";
	}
	if(handlerSelectType=="rule"){
		$(".rule.handler").eq(0).show();
		$(".rule.handler").eq(0).find(".alreadyMapType").eq(0).hide();
		$(".rule.handler").eq(0).find(".mapArea").eq(0).hide();
	}else{
		$(".rule.handler").eq(0).hide();
		//记录需要该引用id
		window.sysRuleQuoteToHandler.recordDelMapContentIds(0);
		//清空规则信息
		$(".rule.handler").eq(0).find("[name='ruleId']").eq(0).val("");
		$(".rule.handler").eq(0).find("[name='ruleName']").eq(0).val("");
		
		$(".rule.handler").eq(0).find("[name='mapContent']").eq(0).val("");
		$(".rule.handler").eq(0).find("[name='alreadyMapId']").eq(0).val("");
		$(".rule.handler").eq(0).find("[name='alreadyMapName']").eq(0).val("");
	}
}

//备选审批人选择方式
function switchOptHandlerSelectType(value) {
	if(optHandlerSelectType==value)
		return;
	optHandlerSelectType = value;
	document.getElementById('SPAN_OptSelectType1').style.display=optHandlerSelectType=="org"?"":"none";
	document.getElementById('SPAN_OptSelectType2').style.display=optHandlerSelectType=="formula"?"":"none";
	document.getElementById('SPAN_OptSelectType3').style.display=optHandlerSelectType=="rule"?"":"none";
	document.getElementById('SPAN_OptSelectType4').style.display=optHandlerSelectType=="otherOrgDept"?"":"none";
	document.getElementById('DIV_OptHandlerCalType').style.display=optHandlerSelectType=="org"?"":"none";
	document.getElementsByName("wf_optHandlerNames")[0].style.display=(optHandlerSelectType == "formula" || optHandlerSelectType == "org")?"":"none";
	document.getElementsByName("wf_optHandlerIds")[0].value = "";
	document.getElementsByName("wf_optHandlerNames")[0].value = "";
	if (optHandlerSelectType=="rule") {
		document.getElementsByName("wf_optHandlerNames")[0].style.display = "none";
		$(".rule.optHandler").eq(0).show();
		$(".rule.optHandler").eq(0).find(".alreadyMapType").eq(0).hide();
		$(".rule.optHandler").eq(0).find(".mapArea").eq(0).hide();
	} else {
		document.getElementsByName("wf_optHandlerNames")[0].style.display = "";
		$(".rule.optHandler").eq(0).hide();
		//记录需要该引用id
		window.sysRuleQuoteToOptHandler.recordDelMapContentIds(0);
		//清空规则信息
		$(".rule.optHandler").eq(0).find("[name='ruleId']").eq(0).val("");
		$(".rule.optHandler").eq(0).find("[name='ruleName']").eq(0).val("");
		
		$(".rule.optHandler").eq(0).find("[name='mapContent']").eq(0).val("");
		$(".rule.optHandler").eq(0).find("[name='alreadyMapId']").eq(0).val("");
		$(".rule.optHandler").eq(0).find("[name='alreadyMapName']").eq(0).val("");
	}

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
function selectByFormula(idField, nameField){
	Formula_Dialog(idField,
			nameField,
			FlowChartObject.FormFieldList, 
			"com.landray.kmss.sys.organization.model.SysOrgElement[]",
			null,
			"com.landray.kmss.sys.lbpm.engine.formula.LbpmFunction",
			FlowChartObject.ModelName);
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
});

//<---------------- 矩阵组织相关 begin------------------->
DocList_Info.push("conditionParamList");
MatrixConditions = null; //矩阵组织条件字段集
ConditionInfo = new Object(); //矩阵组织条件字段信息集

// 选择矩阵组织
function selectOrgMatrix() {
	var dialog = new KMSSDialog(false, false);
	dialog.winTitle = '<kmss:message key="FlowChartObject.Lang.Node.orgMatrix.select" bundle="sys-lbpmservice" />';
	var node = dialog.CreateTree('<kmss:message key="FlowChartObject.Lang.Node.orgMatrix" bundle="sys-lbpmservice" />');
	node.AppendBeanData("sysOrgMatrixService&parent=!{value}", null, null, true, null);
	dialog.notNull = true;
	dialog.BindingField('orgMatrixId', 'orgMatrixName');
	dialog.SetAfterShow(function(rtnData){
		if(rtnData!=null){
			initMatrixConditionInfo(this.rtnData.GetHashMapArray()[0].id);
			clearOrgMatrixParamInfo();
		}
	});
	dialog.Show();
}
// 选择结果字段
function selectMatrixResultField() {
	var sysOrgMatrixFdId = $("input[name=orgMatrixId]")[0].value;
	if (!sysOrgMatrixFdId) {
		alert('<kmss:message key="FlowChartObject.Lang.Node.orgMatrix.checkIfEmpty" bundle="sys-lbpmservice" />');
		return;
	}
	var treeBean = 'sysOrgMatrixService&id={sysOrgMatrixFdId}&rtnType=2';
	Dialog_Tree(true, "matrixResultId", "matrixResultName", null, treeBean.replace("{sysOrgMatrixFdId}",sysOrgMatrixFdId), '选择矩阵结果');
}

// 添加条件字段赋值设置行
function addMatrixOrgConditionParamRow(){
	if (MatrixConditions == null || MatrixConditions.length == 0) {
		alert('<kmss:message key="FlowChartObject.Lang.Node.orgMatrix.checkIfEmpty" bundle="sys-lbpmservice" />');
		return;
	}
	var fieldValues = new Object();
	var row = DocList_AddRow("conditionParamList",null,fieldValues);
	var conditionParamSelect = $(row).find("select[name='conditionParam']");
	MatrixConditions.forEach(function(condition, index){
		conditionParamSelect.append("<option value='"+condition.value+"'>"+condition.text+"</option>");
		if (index == 0) {
			conditionParamChange(conditionParamSelect[0]);
		}
	});
	return row;
}

//条件字段赋值设置
function assignConditionParamValue(target) {
	var tr = $(target).closest("tr");
	var idField = $(tr).find("input[name='conditionParam.expression.value']")[0];
	var nameField = $(tr).find("input[name='conditionParam.expression.text']")[0];
	Formula_Dialog(idField, nameField, FlowChartObject.FormFieldList, "String", null, "com.landray.kmss.sys.lbpm.engine.formula.LbpmFunction", FlowChartObject.ModelName);
}

//条件字段切换
function conditionParamChange(self,type){
	if($(self).length == 0)
		return;
	var conditionTypeSelect = $(self).next("select");
	if (type) {
		conditionTypeSelect.val(type);
	} else {
		//切换条件字段时自动还原ID/Name下拉框的默认值
		conditionTypeSelect.val("fdId");
	}
	if (ConditionInfo[self.value]["type"] == "constant") {
		conditionTypeSelect.val("fdName");
		//常量类型不需要出现ID/Name的下拉选择
		conditionTypeSelect[0].style.display = "none";
		self.style.width = "100%";
	} else {
		//对象类型显示出ID/Name下拉框供选择
		conditionTypeSelect[0].style.display = "";
		self.style.width = "60%";
	}
	//悬浮可查看该条件字段的具体类型
	self.title = ConditionInfo[self.value]["type"];
}

// 清除矩阵组织相关参数的设置
function clearOrgMatrixParamInfo() {
	$("input[name=matrixResultId]").val("");
	$("input[name=matrixResultName]").val("");
	var rows = $("#conditionParamList")[0].rows;
	for (var i = rows.length - 1; i > 0; i --) {
		DocList_DeleteRow(rows[i]);
	}
}

// 矩阵设置保存校验
AttributeObject.CheckDataFuns.push(function(data) {
	if (data.handlerSelectType == "matrix") {
		if ($("input[name=orgMatrixId]")[0].value != "") {
			if (!$("input[name=matrixResultId]")[0].value) {
				alert('<kmss:message key="FlowChartObject.Lang.Node.orgMatrix.checkIfResultEmpty" bundle="sys-lbpmservice" />');
				return false;
			}
			if ($("select[name='conditionParam']").length == 0) {
				alert('<kmss:message key="FlowChartObject.Lang.Node.orgMatrix.checkIfConditionEmpty" bundle="sys-lbpmservice" />');
				return false;
			} else {
				var conditionValues = $("input[name='conditionParam.expression.value']");
				for (var i=0;i<conditionValues.length;i++) {
					if (conditionValues[i].value == "" || conditionValues[i].value == null) {
						alert('<kmss:message key="FlowChartObject.Lang.Node.orgMatrix.checkIfConditionValueEmpty" bundle="sys-lbpmservice" />');
						return false;
					}
				}
			}
		}
	}
});
// 加载矩阵组织条件字段信息
function initMatrixConditionInfo(sysOrgMatrixFdId){
	if (!sysOrgMatrixFdId) {
		sysOrgMatrixFdId = $("input[name=orgMatrixId]")[0].value;
	}
	var dataBean = 'sysOrgMatrixService&id={sysOrgMatrixFdId}&rtnType=1';
	MatrixConditions = new KMSSData().AddBeanData(dataBean.replace("{sysOrgMatrixFdId}",sysOrgMatrixFdId)).GetHashMapArray();
	ConditionInfo = new Object();
	MatrixConditions.forEach(function(condition, index){
		ConditionInfo[condition.value] = condition;
	});
	// 获取版本号
	$.post('<c:url value="/sys/organization/sys_org_matrix/sysOrgMatrix.do?method=getVersions" />', {'fdId': sysOrgMatrixFdId}, function(res) {
		if(res && res.length > 0) {
			for(var i = res.length-1; i>=0; i--){
				var marxVersionInfo = res[i];
				//防止未和sys-organization集成构建时项目报错
				var isEnable = marxVersionInfo.fdIsEnable;
				if(isEnable == true || isEnable == undefined){
					$("input[name='matrixVersion']").val(res[i].fdName);
					break;
				}
			}
		}
	}, 'json');
}

// 填充矩阵设置信息
function initOrgMatrixInfo() {
	if(FlowChartObject.IsEmbedded){
		//Doc_HideLabelById("Label_Tabel","node_right_tr");
	}
	if (AttributeObject.NodeData.handlerSelectType != "matrix") {
		return;
	}
	var config = AttributeObject.NodeData['handlerIds'];
	if (config != null && config != "") {
		var json = (new Function("return ("+ config + ")"))();
		//矩阵组织
		$("input[name=orgMatrixId]")[0].value = json.id;
		$("input[name=orgMatrixName]")[0].value = json.idText;
		$("input[name='matrixVersion']").val(json.version);
		initMatrixConditionInfo(json.id);
		//结果字段
		$("input[name=matrixResultId]")[0].value = json.results;
		$("input[name=matrixResultName]")[0].value = json.resultsText;
		$("input:radio[name=matrixResultOption][value="+json.option+"]").attr('checked','true');
		//条件字段参数表
		json.conditionals.forEach(function(obj,index){
			var row = addMatrixOrgConditionParamRow();
			var conditionParamSelect = $(row).find("select[name='conditionParam']");
			conditionParamSelect.val(obj.id);
			conditionParamChange(conditionParamSelect[0],obj.type);
			var expressionValue = $(row).find("input[name='conditionParam.expression.value']");
			expressionValue.val(obj.value);
			var expressionText = $(row).find("input[name='conditionParam.expression.text']");
			expressionText.val(obj.text);
		});
	}
}

AttributeObject.Init.AllModeFuns.push(initOrgMatrixInfo);

// 保存矩阵组织相关配置信息到节点定义里
function writeMatrixOrgJSON(data) {
	if (data.handlerSelectType != "matrix" || $("input[name=orgMatrixId]")[0].value == "") {
		return;
	}
	var config = [];
	config.push("{\"id\":");
	config.push("\"" + $("input[name=orgMatrixId]")[0].value + "\"");
	config.push(",\"idText\":");
	config.push("\"" + $("input[name=orgMatrixName]")[0].value + "\"");
	config.push(",\"version\":");
	config.push("\"" + $("input[name=matrixVersion]")[0].value + "\"");
	config.push(",\"results\":");
	config.push("\"" + $("input[name=matrixResultId]")[0].value + "\"");
	config.push(",\"resultsText\":");
	config.push("\"" + $("input[name=matrixResultName]")[0].value + "\"");
	config.push(",\"option\":");
	config.push("\"" + $('input[type=radio][name=matrixResultOption]:checked').val() + "\"");
	
	config.push(",\"conditionals\":");
	config.push(getConditionsParamJson());
	config.push("}");
	
	data['handlerIds'] = config.join("");
	data['handlerNames'] = $("input[name=orgMatrixName]")[0].value + "{" + $("input[name=matrixResultName]")[0].value + "}";
}

function getConditionsParamJson() {
	var rtn = [];
	
	var conditionParams = $("select[name='conditionParam']");
	var conditionTypes = $("select[name='conditionType']");
	var expressionValues = $("input[name='conditionParam.expression.value']");
	var expressionTexts = $("input[name='conditionParam.expression.text']");
	
	conditionParams.each(function(index,obj){
		rtn.push("{\"id\":\"" + conditionParams[index].value + "\""
				+ ",\"type\":\"" + conditionTypes[index].value + "\""
				+ ",\"value\":\"" + formatJson(expressionValues[index].value) + "\""
			    + ",\"text\":\"" + formatJson(expressionTexts[index].value) + "\"}");
	});
	
	return "[" + rtn.join(",") + "]";
}

function formatJson(value) {
	return value.replace(/"/ig,'\\"').replace(/\r\n/ig,'\\r\\n');
}

AttributeObject.AppendDataFuns.push(writeMatrixOrgJSON);
//<---------------- 矩阵组织相关 end------------------->
//<-----规则引擎----->
//审批人start
function initRuleQuoteToHandler(){
	var isShow = isShowRule('handler');
	if(isShow){
		if(!window.sysRuleQuoteToHandler){
			window.sysRuleQuoteToHandler = window.SysRuleQuote("wf_handlerIds","wf_handlerNames","handler",FlowChartObject.SysRuleTemplate,FlowChartObject.LbpmTemplateKey);
		}
		var config = AttributeObject.NodeData['handlerIds'];
		if (config != null && config != "") {
			var isEdit = AttributeObject.isEdit ? AttributeObject.isEdit() : FlowChartObject.IsEdit;
			var index;
			if(isEdit){
				index = window.sysRuleQuoteToHandler.initRuleQuote(config, 0, 'wf_handlerIds', 'wf_handlerNames', 'edit');
			}else{
				index = window.sysRuleQuoteToHandler.initRuleQuote(config, 0, 'wf_handlerIds', 'wf_handlerNames', 'view');
			}
			//进行了规则初始化就是规则
			$(".rule.handler").eq(0).show();
		}
	}else{
		//不存在机制内容，隐藏入口
		$("[name='wf_handlerSelectType'][value='rule']").parent().hide();
	}
}
AttributeObject.Init.AllModeFuns.push(initRuleQuoteToHandler);

function isShowRule(type){
	var isShow = false;
	if(FlowChartObject.SysRuleTemplate || (type=='handler' && AttributeObject.NodeData.handlerSelectType === "rule") || (type=='optHandler' && AttributeObject.NodeData.optHandlerSelectType === "rule")){
		isShow = true;
	}else{
		if(FlowChartObject && FlowChartObject.ModelId){
			var modelId = FlowChartObject.ModelId;
			//请求后台来确认是否显示（主要解决流程文档页面节点看不到选项的问题）
			$.ajax({
				  url: Com_Parameter.ContextPath+"sys/lbpm/engine/jsonp.jsp?s_bean=lbpmRuleHandlerService",
				  type:'GET',
				  async:false,//同步请求
				  data:{modelId: modelId},
				  success: function(json){
					  var data = eval('('+json+')');
					  if(data.isShow){
						 isShow = true;
					  }	
				  }
			});
		}
	}
	return isShow;
}
//校验
function checkRuleDataToHandler(data){
	if (data.handlerSelectType != "rule") {
		return true;
	}
	return window.sysRuleQuoteToHandler.checkData();
}
AttributeObject.CheckDataFuns.push(checkRuleDataToHandler);
//写回数据
function writeRuleMapDataToHandler(data){
	if(!window.sysRuleQuoteToHandler){
		return;
	}
	window.sysRuleQuoteToHandler.writeData("wf_handlerSelectType",data);
}
AttributeObject.AppendDataFuns.push(writeRuleMapDataToHandler);
//审批人end
//备选人start
function initRuleQuoteToOptHandler(){
	var isShow = isShowRule('optHandler');
	if(isShow){
		if(!window.sysRuleQuoteToOptHandler){
			window.sysRuleQuoteToOptHandler = window.SysRuleQuote("wf_optHandlerIds","wf_optHandlerNames","optHandler",FlowChartObject.SysRuleTemplate,FlowChartObject.LbpmTemplateKey);
		}
		var config = AttributeObject.NodeData['optHandlerIds'];
		if (config != null && config != "") {
			var isEdit = AttributeObject.isEdit ? AttributeObject.isEdit() : FlowChartObject.IsEdit;
			var index;
			if(isEdit){
				index = window.sysRuleQuoteToOptHandler.initRuleQuote(config, 0, 'wf_optHandlerIds', 'wf_optHandlerNames', 'edit');
			}else{
				index = window.sysRuleQuoteToOptHandler.initRuleQuote(config, 0, 'wf_optHandlerIds', 'wf_optHandlerNames', 'view');
			}
			//进行了规则初始化就是规则
			$(".rule.optHandler").eq(0).show();
		}
	}else{
		//不存在机制内容，隐藏入口
		$("[name='wf_optHandlerSelectType'][value='rule']").parent().hide();
	}
}
AttributeObject.Init.AllModeFuns.push(initRuleQuoteToOptHandler);
//校验
function checkRuleDataToOptHandler(data){
	if (data.handlerSelectType != "rule") {
		return true;
	}
	return window.sysRuleQuoteToOptHandler.checkData();
}
AttributeObject.CheckDataFuns.push(checkRuleDataToOptHandler);
//写回数据
function writeRuleMapDataToOptHandler(data){
	if(!window.sysRuleQuoteToOptHandler){
		return;
	}
	window.sysRuleQuoteToOptHandler.writeData("wf_optHandlerSelectType");
}
AttributeObject.AppendDataFuns.push(writeRuleMapDataToOptHandler);
//备选人end
//选择
function selectRule(returnType,mode,key){
	if(key == 'handler'){
		window.sysRuleQuoteToHandler.selectRule(returnType,mode);
	}else if(key == 'optHandler'){
		window.sysRuleQuoteToOptHandler.selectRule(returnType,mode);
	}
}
//更新映射
function updateMapContent(value, obj, key){
	if(key == 'handler'){
		window.sysRuleQuoteToHandler.updateMapContent(value, obj);
	}else if(key == 'optHandler'){
		window.sysRuleQuoteToOptHandler.updateMapContent(value, obj);
	}
}

AttributeObject.AppendDataFuns.push(writeMapContentJSON);

function writeMapContentJSON(data){
	if (data.handlerSelectType === "rule") {
		var mapContent = $("[name='mapContent']").val();
		if (mapContent) {
			mapContent = JSON.parse(mapContent);
			var maps = mapContent.maps;
			data['ruleMaps'] = JSON.stringify(maps);
		}
	}
}
//<-----规则引擎----->
</script>
<script type="text/javascript" src='<c:url value="/sys/rule/resources/js/common.js"/>'></script>
<script type="text/javascript" src='<c:url value="/sys/rule/resources/js/rule_quote.js"/>'></script>