define(["sys/lbpmservice/mobile/operation/operation_common_passtype","dijit/registry"], function(operationCommon,registry) {
	
	function OperationBlur() {
		lbpm.globals.clearDefaultUsageContent('handler_giveup');
		// 从通过操作切换到其它操作时，去掉设置过的下一步并隐藏自由子流程行
		lbpm.globals.hiddenObject(document.getElementById("freeSubFlowNodeRow"), true);
		if (lbpm.myAddedSubNodes.length > 0) {
			var iframe = document.getElementById('WF_IFrame');
			var FlowChartObject = iframe.contentWindow.FlowChartObject;
			FlowChartObject.Nodes.deleteSubNode(lbpm.myAddedSubNodes[0]);
			lbpm.myAddedSubNodes = new Array();
			lbpm.subNodeHandlerIds = new Array();
			lbpm.subNodeHandlerNames = new Array();
			var flowXml = FlowChartObject.BuildFlowXML();
			if (!flowXml){
				return;
			}
			var processXMLObj = document.getElementsByName("sysWfBusinessForm.fdFlowContent")[0];
			processXMLObj.value = flowXml;
			lbpm.globals.parseXMLObj();
			lbpm.modifys = {};
			$("input[name='sysWfBusinessForm.fdIsModify']")[0].value = "1";
			var widget = registry.byId("freeSubFlowNodes");
			if (widget) {
				widget.buildValue(widget.contentNode);
			}
		}
	}
	// 处理人操作：通过
	function OperationClick(operationName) {
		// 点击通过操作时用户没有 输入审批内容时设置默认审批内容 @作者：曹映辉 @日期：2011年12月15日
		lbpm.globals.setDefaultUsageContent('handler_giveup');
		var operationsRow = document.getElementById("operationsRow");
		var operationsTDTitle = document.getElementById("operationsTDTitle");
		var operationsTDContent = document.getElementById("operationsTDContent");
		if (operationsTDContent) {
			operationsTDTitle.innerHTML = lbpm.workitem.constant.handlerOperationTypepass;
			lbpm.globals.hiddenObject(operationsRow, false);
			var operatorInfo = lbpm.globals.analysisProcessorInfoToObject();
			if (operatorInfo != null) {
				var html = lbpm.globals.generateNextNodeInfo();
				lbpm.globals.innerHTMLGenerateNextNodeInfo(html, operationsTDContent);
			}
		}
	}
	// “通过”操作的检查
	function OperationCheck(workitemObjArray) {
		if(lbpm.globals.common_operationCheckForPassType(workitemObjArray)){
			if(lbpm.globals.isUsageContenRequired(lbpm.currentOperationType)){
				return lbpm.globals.validateMustSignYourSuggestion();
			}
			return true;
		} else {
			return false;
		}
	}
	// 设置"通过"操作的参数
	function setOperationParam() {
	}

	var handlerPassOperation={};
	handlerPassOperation['handler_giveup'] = lbpm.operations['handler_giveup'] = {
		isPassType : true,
		isHideOperationsRow : true,
		click : OperationClick,
		check : OperationCheck,
		blur : OperationBlur,
		setOperationParam : setOperationParam
	};
	
	return handlerPassOperation;
});
