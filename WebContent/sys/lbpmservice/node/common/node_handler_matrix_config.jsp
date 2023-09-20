<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" errorPage="/resource/jsp/jsperror.jsp"%>
<%@ include file="/resource/jsp/common.jsp"%>
<%@ include file="/resource/jsp/htmlhead.jsp" %>

<script>
Com_IncludeFile("document.js", "style/"+Com_Parameter.Style+"/doc/");
Com_IncludeFile("jquery.js|doclist.js|docutil.js|dialog.js|formula.js");

var dialogRtnValue = null;
if(window.showModalDialog){
	dialogObject = window.dialogArguments;
}else{
	dialogObject = opener.Com_Parameter.Dialog;
}
</script>
<style>
body { padding-bottom: 43px;}
#DIV_EditButtons{position: fixed;    left: 0;    right: 0;    text-align: center;    padding-top: 10px;    padding-bottom: 10px;    bottom: 0;    background-color: #fff;    border-top: 1px solid #d5d5d5;    z-index: 1070;}
</style>
</head>
<body>
<br>
<center>
<span>
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
	<div style="margin-top:6px;padding:2px;width:90%;height:80%;">
		<table id="conditionParamList" class="tb_normal" width="97%">
			<tr>
				<td width="45%" class="td_normal_title"><kmss:message key="FlowChartObject.Lang.Node.orgMatrix.conditionParam" bundle="sys-lbpmservice" /></td>
				<td width="45%" class="td_normal_title"><kmss:message key="FlowChartObject.Lang.Node.orgMatrix.conditionValue" bundle="sys-lbpmservice" /></td>
				<td width="10%" class="td_normal_title" align="center">
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
<script>
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
	Formula_Dialog(idField, nameField, dialogObject.FormFieldList, "String", null, "com.landray.kmss.sys.lbpm.engine.formula.LbpmFunction", dialogObject.ModelName);
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
function checkMatrixConfig() {
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
	return true;
}
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
			$("input[name='matrixVersion']").val(res[res.length-1].fdName);
		}
	}, 'json');
}

// 填充矩阵设置信息
function initOrgMatrixInfo(config) {
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

window.onload = function(){
	var matrixConfig = dialogObject.valueData.GetHashMapArray()[0];
	var config = matrixConfig ? matrixConfig.id : "";
	initOrgMatrixInfo(config);
}

// 保存矩阵组织相关配置信息
function writeMatrixOrgJSON() {
	if ($("input[name=orgMatrixId]")[0].value == "") {
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
	var rtnVal = {};
	rtnVal['id'] = config.join("");
	rtnVal['name'] = $("input[name=orgMatrixName]")[0].value + "{" + $("input[name=matrixResultName]")[0].value + "}";
	dialogObject.rtnData = [rtnVal];
	dialogObject.AfterShow();
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

</script>
<br><br>
<div id="DIV_EditButtons">
	<input name="btnOK" type="button" class="btnopt" onclick="if(checkMatrixConfig()){writeMatrixOrgJSON();window.close();}" 
		value="  <kmss:message key="FlowChartObject.Lang.OK" bundle="sys-lbpm-engine" />  ">
	&nbsp;&nbsp;&nbsp;&nbsp;
	<input name="btnCancel" type="button" class="btnopt" onclick="window.close();" 
		value="  <kmss:message key="FlowChartObject.Lang.Cancel" bundle="sys-lbpm-engine" />  ">
</div>
<br><br>
</center>
</body>
</html>