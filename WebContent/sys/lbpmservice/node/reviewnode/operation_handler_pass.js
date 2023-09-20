/*******************************************************************************
 * 功能：处理人“通过”操作的审批所用JSP，此JSP路径在处理人“通过”操作扩展点定义的reviewJs参数匹配 使用： 作者：罗荣飞
 * 创建时间：2012-06-06
 ******************************************************************************/
( function(operations) {
	operations['handler_pass'] = {
			isPassType:true,
			isHideOperationsRow : true,
			click:OperationClick,
			check:OperationCheck,
			blur:OperationBlur,
			setOperationParam:setOperationParam
	};
	
	function OperationBlur() {
		lbpm.globals.clearDefaultUsageContent('handler_pass');
		lbpm.globals.removeMyAddedSubNodes();
		lbpm.globals.hiddenObject(document.getElementById("freeSubFlowNodeRow"), true);
	}

	// 处理人操作：通过
	function OperationClick(operationName) {
		//点击通过操作时用户没有 输入审批内容时设置默认审批内容 @作者：曹映辉 @日期：2011年12月15日
		lbpm.globals.setDefaultUsageContent('handler_pass');

		var operationsRow = document.getElementById("operationsRow");
		var operationsTDTitle = document.getElementById("operationsTDTitle");
		var operationsTDContent = document
				.getElementById("operationsTDContent");
		if(operationsTDContent){		
			operationsTDTitle.innerHTML = lbpm.constant.opt.handlerOperationTypepass;
			var nextNodeObj = lbpm.globals.getNextNodeObj(lbpm.nowNodeId);
			if(nextNodeObj.nodeDescType=="autoBranchNodeDesc"){
				var calcBranchLabel = '&nbsp;<label id="calc" style="color:#dd772c;cursor:pointer;" onclick="lbpm.globals.calcBranch();">'
					+ lbpm.constant.opt.calcBranch;
				operationsTDTitle.innerHTML=operationsTDTitle.innerHTML + calcBranchLabel;
			}
			lbpm.globals.hiddenObject(operationsRow, false);
			var operatorInfo = lbpm.globals.analysisProcessorInfoToObject();
			if (operatorInfo != null) {
				var html = lbpm.globals.generateNextNodeInfo();
				if (window.dojo) {
					lbpm.globals.innerHTMLGenerateNextNodeInfo(html, operationsTDContent);
				} else {
					operationsTDContent.innerHTML = html;
				}
			}
		}	
	};
	//“通过”操作的检查
	function OperationCheck(workitemObjArray){
		if(lbpm.globals.common_operationCheckForPassType(workitemObjArray)){
			if(lbpm.globals.isUsageContenRequired(lbpm.currentOperationType)){
				return lbpm.globals.validateMustSignYourSuggestion();
			}
			return true;
		} else {
			return false;
		}
	};	
	//设置"通过"操作的参数
	function setOperationParam()
	{

	};
})(lbpm.operations);