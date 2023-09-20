/*******************************************************************************
 * 功能：已处理人“撤回”操作的审批所用JSP 创建时间：2014-05-12
 ******************************************************************************/
( function(operations) {
	operations['history_handler_back'] = {
		click : OperationClick,
		check : OperationCheck,
		setOperationParam : setOperationParam
	};
	// 已处理人操作：撤回审批
	function OperationClick(operationName) {
		$("#commonUsagesRow").hide();
		lbpm.globals.setOperationParameterJson(false, "Back_keepAuditNote", "param");
		if (Lbpm_SettingInfo) {
			if(Lbpm_SettingInfo.forceKeepAuditNote=="true"){
				lbpm.globals.setOperationParameterJson(true, "Back_keepAuditNote", "param");
			} else if (Lbpm_SettingInfo.isKeepAuditNoteOptional=="true"){
				lbpm.globals.setOperationParameterJson(false, "Back_keepAuditNote", "param");
				$("#keepAuditNoteRow").show();
			}
		}
	}
	// “撤回审批”操作的检查
	function OperationCheck(workitemObjArray) {
		return true;
	}
	// 设置"撤回审批"操作的参数
	function setOperationParam() {
	}
})(lbpm.operations);