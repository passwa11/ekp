<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include ref="default.dialog">
<template:replace name="content">
<%@ page import="com.landray.kmss.sys.lbpmservice.constant.LbpmConstants" %>
<script src="<c:url value="/sys/lbpm/flowchart/js/workflow.js"/>"></script>
<script type="text/javascript"> 
var lbpm = new Object();
lbpm.globals = new Object();
Com_Parameter.CloseInfo=null;
Com_IncludeFile("jquery.js|dialog.js|formula.js");
//Com_IncludeFile("workflow.js", "workflow/js/");
Com_IncludeFile("validation.js|validation.jsp|validator.jsp|plugin.js");
window.onload = function(){$KMSSValidation();};
// 类似sysWfProcess_script.jsp中的同名函数
lbpm.globals.setHandlerFormulaDialog_=function(idField, nameField, modelName, action, personOnly) {
	if (personOnly) {
		Formula_Dialog(idField,
				nameField,
				FormFieldList, 
				"com.landray.kmss.sys.organization.model.SysOrgPerson",
				action,
				"com.landray.kmss.sys.lbpm.engine.formula.LbpmFunction",
				modelName);
	} else {
		Formula_Dialog(idField,
				nameField,
				FormFieldList, 
				"com.landray.kmss.sys.organization.model.SysOrgElement[]",
				action,
				"com.landray.kmss.sys.lbpm.engine.formula.LbpmFunction",
				modelName);
	}	
}
	
// 是公式的情况下，为不让在对话框中出现公式，清除公式信息;不是公式的情况下，为不让在公式对话框中出现组织架构，清除组织架构信息
function WorkFlow_ClearValueForFurtureHandler(id,isFormula) {
	_now_nodeId = id;
	var handlerIdsObj = document.getElementsByName("handlerIds_" + id)[0];
	var handlerNamesObj = document.getElementsByName("handlerNames_" + id)[0];
	if (handlerIdsObj.getAttribute("isFormula") != isFormula) {
		handlerIdsObj.value = '';
		handlerNamesObj.value = '';
	}
	//如果是规则，也进行清空
	if(handlerIdsObj.getAttribute("isrule") == true || handlerIdsObj.getAttribute("isrule") == "true"){
		handlerIdsObj.value = '';
		//handlerNamesObj.value = '';
	}
}

// 刷新当前值 limh 2011年3月31日
lbpm.globals.setFurtureHandlerInfoes=function(rtv,isFormula,isMatrix,isRule){
	var id = _now_nodeId;
	var handlerIdsObj = document.getElementsByName("handlerIds_" + id)[0];
	var handlerNamesObj = document.getElementsByName("handlerNames_" + id)[0];
	if(rtv){
		if(rtv.GetHashMapArray()){
		  handlerIdsObj.setAttribute("isFormula",isFormula);
		  handlerIdsObj.setAttribute("isMatrix",(isMatrix==null?"false":isMatrix));
		  handlerIdsObj.setAttribute("isRule",(isRule==null?"false":isRule));
		  var ids="";
		  var names="";
		  for(var i=0;i<rtv.GetHashMapArray().length;i++){
			  ids+=rtv.GetHashMapArray()[i].id;
			  names+=rtv.GetHashMapArray()[i].name;
			  if(i!=rtv.GetHashMapArray().length-1){
				  ids+=";";
				  names+=";";
			  }
		  }
		  handlerIdsObj.value = ids;
		  handlerNamesObj.value = names;
		}
		_now_nodeId = null;
	}
}

function WorkFlow_ChangeProcessorSubmitForm(){
	var rtnNodesMapJSON= new Array();
	var mustModifyNodeArr = new Array();
	var hasValidate = false;
	var inputObjs = document.getElementsByTagName("input");
	for(var i = 0; i < inputObjs.length; i++){
		var input = inputObjs[i];
		var _name = input.getAttribute("name");
		if(_name && _name.indexOf("handlerIds_") == 0) {
			var temp = _name.split("_");
			$.each(parent.lbpm.nodes, function(index, nodeData) {
				if(temp[1] == nodeData.id){
					if(temp[0]=="handlerIds"){
						var nameFieldValue = document.getElementsByName("handlerNames_"+temp[1])[0].value;
						rtnNodesMapJSON.push({
							id:temp[1],
							handlerSelectType:input.getAttribute("isFormula")=="true" ? "<%=LbpmConstants.HANDLER_SELECT_TYPE_FORMULA%>" : (input.getAttribute("isMatrix")=="true" ? "<%=LbpmConstants.HANDLER_SELECT_TYPE_MATRIX%>" : (input.getAttribute("isRule")=="true" ? "<%=LbpmConstants.HANDLER_SELECT_TYPE_RULE%>" : "<%=LbpmConstants.HANDLER_SELECT_TYPE_ORG%>")),
							handlerIds:input.value,
							handlerNames:nameFieldValue
						});
						//获取所有的必须修改处理人节点
						var validate = $("input[name='handlerNames_" + temp[1] +"']").attr("validate");
						if (validate){
							mustModifyNodeArr.push(nodeData.id);
						}
					}
				}
				
			});
		}
	};
	
	var param={};
	param.nodeInfos=rtnNodesMapJSON;
	for (var i = 0; i < rtnNodesMapJSON.length; i++){
		var nodeInfo = rtnNodesMapJSON[i];
		if (mustModifyNodeArr.join(";").indexOf(nodeInfo.id) > -1 && nodeInfo.handlerIds == ""){
			hasValidate = true;
			break;
		}
	}
	if (!hasValidate){
		if (typeof(parent.$dialog) != 'undefined') {
			parent.$dialog.hide(param);
		} else {
			parent.returnValue =param;
			parent.close();
		}
	}else{
		alert('<bean:message bundle="sys-lbpmservice" key="lbpmNode.processingNode.changeProcessor.processorNotAlllowEmpty" />');
	} 
	
}

function _switchOp(switchFlag){
	var topFrame = parent.document.getElementById("topFrame");
	if(switchFlag=="all"){
		parent._switchLoadFrame(topFrame,"all");
	}else{
		parent._switchLoadFrame(topFrame);
	}
}

</script>
<form>
	<ui:toolbar layout="sys.ui.toolbar.float">
		
		<c:if test="${param.switchFlag ne 'all'}">
		<ui:button id="switchOpBtn1" text="${ lfn:message('sys-lbpmservice:lbpmNode.processingNode.changeProcessor.filterNode.clear') }" styleClass="lui_toolbar_btn_gray" onclick="_switchOp('all');">
		</ui:button>
		</c:if>

		<c:if test="${param.switchFlag eq 'all'}">
		<ui:button  id="switchOpBtn0" text="${ lfn:message('sys-lbpmservice:lbpmNode.processingNode.changeProcessor.filterNode') }" styleClass="lui_toolbar_btn_gray" onclick="_switchOp();">
		</ui:button>
		</c:if>

		<ui:button text="${ lfn:message('button.ok') }" styleClass="lui_toolbar_btn_gray" onclick="WorkFlow_ChangeProcessorSubmitForm();">
		</ui:button>
		<ui:button text="${ lfn:message('button.cancel') }" styleClass="lui_toolbar_btn_gray" onclick="Com_CloseWindow();">
		</ui:button>
	</ui:toolbar>
	<p class="txttitle">
		<bean:message  bundle="sys-lbpmservice" key="lbpmNode.processingNode.changeProcessor.title"/>
	</p>
	<input type="hidden" name="nodeId" />
	<input type="hidden" name="handlerIdentity" />
	<input type="hidden" name="switchFlag" value="1" />
	<center>
		<table class="tb_normal" width=95%>
			<tbody id="workflowNodeTB">   
				<tr class="tr_normal_title">
					<td width="25%">
						<bean:message bundle="sys-lbpmservice" key="lbpmNode.processingNode.changeProcessor.nodeIndex" />
					</td>
					<td width="50%">
						<bean:message bundle="sys-lbpmservice" key="lbpmNode.processingNode.changeProcessor.handlerIds" />
					</td>
					<td width="25%">
						<bean:message bundle="sys-lbpmservice" key="lbpmNode.processingNode.changeProcessor.nodeHelpInfo" />
					</td>
				</tr>
			</tbody>
		</table>
	</center>
</form>
</template:replace>
</template:include>

<c:if test="${param.switchFlag eq 'all'}">
<script>
	var topFrame = parent.document.getElementById("topFrame");
	parent.WorkFlow_InitialWindow(topFrame,"all");
</script>
</c:if>
<c:if test="${param.switchFlag ne 'all'}">
<script>
	var topFrame = parent.document.getElementById("topFrame");
	parent.WorkFlow_InitialWindow(topFrame);
</script>
</c:if>