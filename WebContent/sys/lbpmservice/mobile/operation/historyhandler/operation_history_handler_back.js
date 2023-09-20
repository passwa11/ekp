define(['dojo/query','dojo/NodeList-dom'],function(query){
	var extOperation={};
	extOperation['history_handler_back'] = {
		click: function(){
			//query("#OperationView #commonUsagesArea").style({display:"none"});
			
			if(Lbpm_SettingInfo){
				if(Lbpm_SettingInfo.forceKeepAuditNote=="true"){
					// 强制保留流程意见
					$("input[name='keepAuditNote']")[0].value = "true";
				} else if (Lbpm_SettingInfo.isKeepAuditNoteOptional=="true"){
					// 可选择是否保留流程意见
					query("#OperationView #keepAuditNoteArea").style({display:""});
				}
			}
		},
		check: function(){
			return true;
		},
		setOperationParam : function(param){
			param["notifyLevel"] = "3";
			param["auditNote"] = "";
			// 是否保留审批意见
			if ($("input[name='keepAuditNote']")[0] && $("input[name='keepAuditNote']")[0].value=="true") {
				param["Back_keepAuditNote"] = true;
			} else {
				param["Back_keepAuditNote"] = false;
			}
		},
		blur : function(){
			query("#OperationView #keepAuditNoteArea").style({display:"none"});
		}
	};
	
	extOperation.init= function(){
		
	};
	return extOperation;
});

