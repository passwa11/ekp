define(["sys/lbpmservice/mobile/operation/operation_common_passtype"],
	function(operationCommon) {
		lbpm.operations['handler_sign'] = {
			isPassType : true,
			isHideOperationsRow : true,
			click : OperationClick,
			check : OperationCheck,
			blur : OperationBlur,
			setOperationParam : setOperationParam
		};
		
		function OperationBlur() {
			lbpm.globals.clearDefaultUsageContent('handler_sign');
		}

		// 处理人操作：签字
		function OperationClick(operationName) {
			lbpm.globals.setDefaultUsageContent('handler_sign');
			var operationsRow = document.getElementById("operationsRow");
			var operationsTDTitle = document.getElementById("operationsTDTitle");
			var operationsTDContent = document.getElementById("operationsTDContent");
			operationsTDTitle.innerHTML = lbpm.workitem.constant.handlerOperationTypepass;
			lbpm.globals.hiddenObject(operationsRow, false);
			var operatorInfo = lbpm.globals.analysisProcessorInfoToObject();
			if (operatorInfo != null) {
				var html = lbpm.globals.generateNextNodeInfo();
				lbpm.globals.innerHTMLGenerateNextNodeInfo(html, operationsTDContent);
			}
		}
		// “通过”操作的检查
		function OperationCheck() {
			if(lbpm.globals.isUsageContenRequired(lbpm.currentOperationType)){
				if(lbpm.globals.validateMustSignYourSuggestion()){
					return lbpm.globals.common_operationCheckForPassType();
				}
				return false;
			}
			return lbpm.globals.common_operationCheckForPassType();
		}
		// 设置"通过"操作的参数
		function setOperationParam() {

		}
	});
