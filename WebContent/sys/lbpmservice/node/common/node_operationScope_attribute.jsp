<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" errorPage="/resource/jsp/jsperror.jsp"%>
<%@ include file="/resource/jsp/common.jsp"%>
<script>
var customHandlerSelectTypeArray = new Array();

function operationScopeCheck(data) {
	data.operationScope = new Array();
	scopeCheck("handler_commission", data);
	scopeCheck("handler_communicate", data);
	scopeCheck("handler_additionSign", data);
	scopeCheck("handler_assign", data);
	
	return true;
}

function scopeCheck(operationName, data){
	var scope = $("input[name='"+operationName+"_scope']:checked").val();
	data.operationScope[operationName] = scope;
	if(data.operationScope[operationName] == "custom"){
		data.operationScope[operationName + "_customIds"] = document.getElementsByName(operationName + "_customHandlerIds")[0].value;
		data.operationScope[operationName + "_customNames"] = document.getElementsByName(operationName + "_customHandlerNames")[0].value;
		data.operationScope[operationName + "_customHandlerSelectType"] = customHandlerSelectTypeArray[operationName];
	}
	if (data.operationScope[operationName] == "all" && operationName == 'handler_communicate') {
		data.operationScope[operationName + "_defaultHandlerIds"] = document.getElementsByName(operationName + "_defaultHandlerIds")[0].value;
		data.operationScope[operationName + "_defaultHandlerNames"] = document.getElementsByName(operationName + "_defaultHandlerNames")[0].value;
	}
}

AttributeObject.CheckDataFuns.push(operationScopeCheck);

function initOperationScopeData(data) {
	var NodeData = data || AttributeObject.NodeData;
	initScopeData("handler_commission", NodeData);
	initScopeData("handler_communicate", NodeData);
	initScopeData("handler_additionSign", NodeData);
	initScopeData("handler_assign", NodeData);
}

function initScopeData(operationName, data){
	var NodeData = data;
	if(!FlowChartObject.isFreeFlow && NodeData.operationScope && NodeData.operationScope[operationName]){
		$("input[name='" + operationName + "_scope']").each(function(){
			if ($(this).attr("value") == NodeData.operationScope[operationName]) {    
				$(this).attr("checked",'checked');
				if(NodeData.operationScope[operationName]=="custom"){
					if (NodeData.operationScope[operationName + "_customIds"] && NodeData.operationScope[operationName + "_customNames"]) {
						document.getElementsByName(operationName + "_customHandlerIds")[0].value = NodeData.operationScope[operationName + "_customIds"];
						document.getElementsByName(operationName + "_customHandlerNames")[0].value = NodeData.operationScope[operationName + "_customNames"];
					} else {
						document.getElementsByName(operationName + "_customHandlerIds")[0].value = "";
						document.getElementsByName(operationName + "_customHandlerNames")[0].value = "";
					}
					document.getElementById('DIV_CustomHandlerView_'+ operationName +'_scope').style.display="";
					var customHandlerSelectType = NodeData.operationScope[operationName + "_customHandlerSelectType"];
					customHandlerSelectTypeArray[operationName] = customHandlerSelectType;
					if (!customHandlerSelectType || customHandlerSelectType!="formula"){
						document.getElementById('SPAN_CustomSelectType1_' + operationName).style.display='';
						document.getElementById('SPAN_CustomSelectType2_' + operationName).style.display='none';
					} else {
						document.getElementById('SPAN_CustomSelectType1_' + operationName).style.display='none';
						document.getElementById('SPAN_CustomSelectType2_' + operationName).style.display='';
					}
					$("input[name='"+operationName+"_customHandlerSelectType']").each(function(){
						if ($(this).attr("value") == customHandlerSelectType) {  
							$(this).attr("checked",'checked');
						}
					});
				}
				if (operationName == 'handler_communicate') {
					if (NodeData.operationScope[operationName] == 'all') {
						if (data.operationScope[operationName + "_defaultHandlerIds"] && data.operationScope[operationName + "_defaultHandlerNames"]) {
							document.getElementsByName(operationName + "_defaultHandlerIds")[0].value = data.operationScope[operationName + "_defaultHandlerIds"];
							document.getElementsByName(operationName + "_defaultHandlerNames")[0].value = data.operationScope[operationName + "_defaultHandlerNames"];
						} else {
							document.getElementsByName(operationName + "_defaultHandlerIds")[0].value = "";
							document.getElementsByName(operationName + "_defaultHandlerNames")[0].value = "";
						}
					} else {
						document.getElementById('DIV_DefaultHandlerView_'+ operationName +'_scope').style.display="none";
						document.getElementsByName(operationName + "_defaultHandlerIds")[0].value = "";
						document.getElementsByName(operationName + "_defaultHandlerNames")[0].value = "";
					}
				}
            }    
		});
	} else {
		var settingInfo = getSettingInfo();
		var splitIndex = operationName.indexOf("_");
		var operationKey = operationName.substring(0,splitIndex).concat(operationName.charAt(splitIndex+1).toUpperCase()).concat(operationName.substring(splitIndex+2,operationName.length));
		var defaultScope = settingInfo[operationKey + 'DefaultScope'];
		if (defaultScope) {
			$("input[name='" + operationName + "_scope']").each(function(){
				if ($(this).attr("value") == defaultScope) {    
					$(this).attr("checked",'checked');
					if (defaultScope == 'custom') {
						document.getElementById('DIV_CustomHandlerView_'+ operationName +'_scope').style.display="";
					}
					if (operationName == 'handler_communicate' && defaultScope != 'all') {
						document.getElementById('DIV_DefaultHandlerView_'+ operationName +'_scope').style.display="none";
					}
				}
			});
		}
	}
}
AttributeObject.Init.AllModeFuns.push(initOperationScopeData);

//自定义可选人员选择方式
function switchCustomHandlerSelectType(operationName, value) {
	if(customHandlerSelectTypeArray[operationName]==value){
		return;
	}
	customHandlerSelectTypeArray[operationName] = value;
	document.getElementById('SPAN_CustomSelectType1_' + operationName).style.display=value!="formula"?"":"none";
	document.getElementById('SPAN_CustomSelectType2_' + operationName).style.display=value=="formula"?"":"none";
	document.getElementsByName(operationName + "_customHandlerIds")[0].value = "";
	document.getElementsByName(operationName + "_customHandlerNames")[0].value = "";
}

function displayHandlerView(operationName, value) {
	if(value == 'custom'){
		document.getElementById('DIV_CustomHandlerView_'+operationName +'_scope').style.display="";
		customHandlerSelectTypeArray[operationName] = $("input[name='"+operationName+"_customHandlerSelectType']:checked").val();
	} else {
		document.getElementById('DIV_CustomHandlerView_'+operationName + '_scope').style.display="none";
	}
	if (operationName == 'handler_communicate') {
		if (value == 'all') {
			document.getElementById('DIV_DefaultHandlerView_'+ operationName +'_scope').style.display="";
		} else {
			document.getElementById('DIV_DefaultHandlerView_'+ operationName +'_scope').style.display="none";
		}
	}
}
</script>

<tr>
	<td width="100px" rowspan="4"><kmss:message key="FlowChartObject.Lang.Node.operationScope" bundle="sys-lbpmservice" /></td>
	<!-- 转办 -->
	<td width="12%"><kmss:message key="lbmp.operation.handler_commission" bundle="sys-lbpmservice" /></td>
	<td>
		<label>
			<input type="radio" name="handler_commission_scope" onclick="displayHandlerView('handler_commission',value);" value="all" checked = "checked">
			<kmss:message key="FlowChartObject.Lang.Node.operationScope.all" bundle="sys-lbpmservice" />
		</label>
		&nbsp;&nbsp;
		<label>
			<input type="radio" name="handler_commission_scope" onclick="displayHandlerView('handler_commission',value);" value="org">
			<kmss:message key="FlowChartObject.Lang.Node.operationScope.org" bundle="sys-lbpmservice" />
		</label>
		&nbsp;&nbsp;
		<label>
			<input type="radio" name="handler_commission_scope" onclick="displayHandlerView('handler_commission',value);" value="dept">
			<kmss:message key="FlowChartObject.Lang.Node.operationScope.dept" bundle="sys-lbpmservice" />
		</label>
		&nbsp;&nbsp;
		<label>
			<input type="radio" name="handler_commission_scope" onclick="displayHandlerView('handler_commission',value);" value="custom">
			<kmss:message key="FlowChartObject.Lang.Node.operationScope.custom" bundle="sys-lbpmservice" />
		</label>
		<br/>
		<div id="DIV_CustomHandlerView_handler_commission_scope" style="display:none">
			<input name="handler_commission_customHandlerIds" type="hidden" orgattr="handler_commission_customHandlerIds:handler_commission_customHandlerNames">
			<input name="handler_commission_customHandlerNames" class="inputsgl" style="width:85%" readonly>
			<span id="SPAN_CustomSelectType1_handler_commission">
			<a href="#" onclick="Dialog_Address(true, 'handler_commission_customHandlerIds', 'handler_commission_customHandlerNames', null, ORG_TYPE_POSTORPERSON | ORG_TYPE_DEPT | ORG_TYPE_ROLE | ORG_TYPE_GROUP);"><kmss:message key="FlowChartObject.Lang.select" bundle="sys-lbpm-engine" /></a>
			</span>
			<span id="SPAN_CustomSelectType2_handler_commission" style="display:none ">
			<a href="#" onclick="selectByFormula('handler_commission_customHandlerIds', 'handler_commission_customHandlerNames');"><kmss:message key="FlowChartObject.Lang.select" bundle="sys-lbpm-engine" /></a>
			</span><br/>
			<label><input type="radio" name="handler_commission_customHandlerSelectType" value="org" onclick="switchCustomHandlerSelectType('handler_commission',value);" checked><kmss:message key="FlowChartObject.Lang.Node.selectAddress" bundle="sys-lbpmservice" /></label>
			<label><input type="radio" name="handler_commission_customHandlerSelectType" value="formula" onclick="switchCustomHandlerSelectType('handler_commission',value);"><kmss:message key="FlowChartObject.Lang.Node.selectFormList" bundle="sys-lbpmservice" /></label>
		</div>
	</td>
</tr>
<!-- 沟通 -->
<tr>
	<td><kmss:message key="lbmp.operation.handler_communicate" bundle="sys-lbpmservice" /></td>
	<td>
		<label>
			<input type="radio" name="handler_communicate_scope" onclick="displayHandlerView('handler_communicate',value);" value="all" checked = "checked">
			<kmss:message key="FlowChartObject.Lang.Node.operationScope.all" bundle="sys-lbpmservice" />
		</label>
		&nbsp;&nbsp;
		<label>
			<input type="radio" name="handler_communicate_scope" onclick="displayHandlerView('handler_communicate',value);" value="org">
			<kmss:message key="FlowChartObject.Lang.Node.operationScope.org" bundle="sys-lbpmservice" />
		</label>
		&nbsp;&nbsp;
		<label>
			<input type="radio" name="handler_communicate_scope" onclick="displayHandlerView('handler_communicate',value);" value="dept">
			<kmss:message key="FlowChartObject.Lang.Node.operationScope.dept" bundle="sys-lbpmservice" />
		</label>
		&nbsp;&nbsp;
		<label>
			<input type="radio" name="handler_communicate_scope" onclick="displayHandlerView('handler_communicate',value);" value="custom">
			<kmss:message key="FlowChartObject.Lang.Node.operationScope.custom" bundle="sys-lbpmservice" />
		</label>
		<br/>
		<div id="DIV_CustomHandlerView_handler_communicate_scope" style="display:none">
			<input name="handler_communicate_customHandlerIds" type="hidden" orgattr="handler_communicate_customHandlerIds:handler_communicate_customHandlerNames">
			<input name="handler_communicate_customHandlerNames" class="inputsgl" style="width:85%" readonly>
			<span id="SPAN_CustomSelectType1_handler_communicate">
			<a href="#" onclick="Dialog_Address(true, 'handler_communicate_customHandlerIds', 'handler_communicate_customHandlerNames', null, ORG_TYPE_POSTORPERSON | ORG_TYPE_DEPT | ORG_TYPE_ROLE | ORG_TYPE_GROUP);"><kmss:message key="FlowChartObject.Lang.select" bundle="sys-lbpm-engine" /></a>
			</span>
			<span id="SPAN_CustomSelectType2_handler_communicate" style="display:none ">
			<a href="#" onclick="selectByFormula('handler_communicate_customHandlerIds', 'handler_communicate_customHandlerNames');"><kmss:message key="FlowChartObject.Lang.select" bundle="sys-lbpm-engine" /></a>
			</span><br/>
			<label><input type="radio" name="handler_communicate_customHandlerSelectType" value="org" onclick="switchCustomHandlerSelectType('handler_communicate',value);" checked><kmss:message key="FlowChartObject.Lang.Node.selectAddress" bundle="sys-lbpmservice" /></label>
			<label><input type="radio" name="handler_communicate_customHandlerSelectType" value="formula" onclick="switchCustomHandlerSelectType('handler_communicate',value);"><kmss:message key="FlowChartObject.Lang.Node.selectFormList" bundle="sys-lbpmservice" /></label>
		</div>
		<div id="DIV_DefaultHandlerView_handler_communicate_scope">
			<kmss:message key="FlowChartObject.Lang.Node.operationScope.defaultCommunicateHandler" bundle="sys-lbpmservice" />
			<input name="handler_communicate_defaultHandlerIds" type="hidden" orgattr="chandler_communicate_defaultHandlerIds:handler_communicate_defaultHandlerNames">
			<input name="handler_communicate_defaultHandlerNames" class="inputsgl" style="width:68%" readonly>
			<span>
				<a href="#" onclick="Dialog_Address(true, 'handler_communicate_defaultHandlerIds', 'handler_communicate_defaultHandlerNames', null, ORG_TYPE_POSTORPERSON);"><kmss:message key="FlowChartObject.Lang.select" bundle="sys-lbpm-engine" /></a>
			</span>
		</div>
	</td>
</tr>
<!-- 补签 -->
<tr>
	<td><kmss:message key="lbpmOperations.fdOperType.processor.additionSign" bundle="sys-lbpmservice-operation-handler" /></td>
	<td>
		<label>
			<input type="radio" name="handler_additionSign_scope" onclick="displayHandlerView('handler_additionSign',value);" value="all" checked = "checked">
			<kmss:message key="FlowChartObject.Lang.Node.operationScope.all" bundle="sys-lbpmservice" />
		</label>
		&nbsp;&nbsp;
		<label>
			<input type="radio" name="handler_additionSign_scope" onclick="displayHandlerView('handler_additionSign',value);" value="org">
			<kmss:message key="FlowChartObject.Lang.Node.operationScope.org" bundle="sys-lbpmservice" />
		</label>
		&nbsp;&nbsp;
		<label>
			<input type="radio" name="handler_additionSign_scope" onclick="displayHandlerView('handler_additionSign',value);" value="dept">
			<kmss:message key="FlowChartObject.Lang.Node.operationScope.dept" bundle="sys-lbpmservice" />
		</label>
		&nbsp;&nbsp;
		<label>
			<input type="radio" name="handler_additionSign_scope" onclick="displayHandlerView('handler_additionSign',value);" value="custom">
			<kmss:message key="FlowChartObject.Lang.Node.operationScope.custom" bundle="sys-lbpmservice" />
		</label>
		<br/>
		<div id="DIV_CustomHandlerView_handler_additionSign_scope" style="display:none">
			<input name="handler_additionSign_customHandlerIds" type="hidden" orgattr="handler_additionSign_customHandlerIds:handler_additionSign_customHandlerNames">
			<input name="handler_additionSign_customHandlerNames" class="inputsgl" style="width:85%" readonly>
			<span id="SPAN_CustomSelectType1_handler_additionSign">
			<a href="#" onclick="Dialog_Address(true, 'handler_additionSign_customHandlerIds', 'handler_additionSign_customHandlerNames', null, ORG_TYPE_POSTORPERSON | ORG_TYPE_DEPT | ORG_TYPE_ROLE | ORG_TYPE_GROUP);"><kmss:message key="FlowChartObject.Lang.select" bundle="sys-lbpm-engine" /></a>
			</span>
			<span id="SPAN_CustomSelectType2_handler_additionSign" style="display:none ">
			<a href="#" onclick="selectByFormula('handler_additionSign_customHandlerIds', 'handler_additionSign_customHandlerNames');"><kmss:message key="FlowChartObject.Lang.select" bundle="sys-lbpm-engine" /></a>
			</span><br/>
			<label><input type="radio" name="handler_additionSign_customHandlerSelectType" value="org" onclick="switchCustomHandlerSelectType('handler_additionSign',value);" checked><kmss:message key="FlowChartObject.Lang.Node.selectAddress" bundle="sys-lbpmservice" /></label>
			<label><input type="radio" name="handler_additionSign_customHandlerSelectType" value="formula" onclick="switchCustomHandlerSelectType('handler_additionSign',value);"><kmss:message key="FlowChartObject.Lang.Node.selectFormList" bundle="sys-lbpmservice" /></label>
		</div>
	</td>
</tr>
<!-- 加签 -->
<tr>
	<td><kmss:message key="lbpmOperations.fdOperType.processor.assign" bundle="sys-lbpmservice-operation-assignment" /></td>
	<td>
		<label>
			<input type="radio" name="handler_assign_scope" onclick="displayHandlerView('handler_assign',value);" value="all" checked = "checked">
			<kmss:message key="FlowChartObject.Lang.Node.operationScope.all" bundle="sys-lbpmservice" />
		</label>
		&nbsp;&nbsp;
		<label>
			<input type="radio" name="handler_assign_scope" onclick="displayHandlerView('handler_assign',value);" value="org">
			<kmss:message key="FlowChartObject.Lang.Node.operationScope.org" bundle="sys-lbpmservice" />
		</label>
		&nbsp;&nbsp;
		<label>
			<input type="radio" name="handler_assign_scope" onclick="displayHandlerView('handler_assign',value);" value="dept">
			<kmss:message key="FlowChartObject.Lang.Node.operationScope.dept" bundle="sys-lbpmservice" />
		</label>
		&nbsp;&nbsp;
		<label>
			<input type="radio" name="handler_assign_scope" onclick="displayHandlerView('handler_assign',value);" value="custom">
			<kmss:message key="FlowChartObject.Lang.Node.operationScope.custom" bundle="sys-lbpmservice" />
		</label>
		<br/>
		<div id="DIV_CustomHandlerView_handler_assign_scope" style="display:none">
			<input name="handler_assign_customHandlerIds" type="hidden" orgattr="handler_assign_customHandlerIds:handler_assign_customHandlerNames">
			<input name="handler_assign_customHandlerNames" class="inputsgl" style="width:85%" readonly>
			<span id="SPAN_CustomSelectType1_handler_assign">
			<a href="#" onclick="Dialog_Address(true, 'handler_assign_customHandlerIds', 'handler_assign_customHandlerNames', null, ORG_TYPE_POSTORPERSON | ORG_TYPE_DEPT | ORG_TYPE_ROLE | ORG_TYPE_GROUP);"><kmss:message key="FlowChartObject.Lang.select" bundle="sys-lbpm-engine" /></a>
			</span>
			<span id="SPAN_CustomSelectType2_handler_assign" style="display:none ">
			<a href="#" onclick="selectByFormula('handler_assign_customHandlerIds', 'handler_assign_customHandlerNames');"><kmss:message key="FlowChartObject.Lang.select" bundle="sys-lbpm-engine" /></a>
			</span><br/>
			<label><input type="radio" name="handler_assign_customHandlerSelectType" value="org" onclick="switchCustomHandlerSelectType('handler_assign',value);" checked><kmss:message key="FlowChartObject.Lang.Node.selectAddress" bundle="sys-lbpmservice" /></label>
			<label><input type="radio" name="handler_assign_customHandlerSelectType" value="formula" onclick="switchCustomHandlerSelectType('handler_assign',value);"><kmss:message key="FlowChartObject.Lang.Node.selectFormList" bundle="sys-lbpmservice" /></label>
		</div>
	</td>
</tr>