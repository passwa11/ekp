define(["sys/lbpmservice/mobile/operation/operation_common_passtype"],function(common){
	var drafterAbandon={};
	
	drafterAbandon['drafter_refuse_abandon'] = lbpm.operations['drafter_refuse_abandon'] = {
		click : function() {
		},
		check : function() {
			return true;
		},
		setOperationParam : function() {
		}
	};
	
	drafterAbandon.drafterRefuseAbandonOpt = lbpm.globals.drafterRefuseAbandonOpt = function() {
		if (lbpm.globals.validateMustSignYourSuggestion()) {
			if (confirm(lbpm.workitem.constant['draft_opr_abandon_confirm'])) {
				var group = $("#operationMethodsGroup");
				if (group.attr('view-type') == 'select') {
					// select
					var data = group.data("drafter_refuse_abandon");
					if (data) {
						group.append(data); // 还原操作并选中
						group.removeData("drafter_refuse_abandon");
					}
					group.find("option[value^='drafter_refuse_abandon']").attr('selected', true);
				} else {
					// radio
					group.find("input[name='oprGroup'][value^='drafter_refuse_abandon']").click(); // 选中操作
				}
				$("#process_review_button").click();
			}
		}
	};
	return drafterAbandon;
});
