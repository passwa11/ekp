define(function(require, exports, module) {
	var $ = require("lui/jquery");
	var dialog = require('lui/dialog');
	function SelectIntegralAndCreditAndLearnTime(id, name) {
		var fdSelectedIds = $('input[name="' + id + '"]').val();

		var showCredit = true;
		var showLearnTime = true;
		var showIntegral = true;
		var integralShowProp = "fdTotalScore";
		
		if(fdSelectedIds){
			if(fdSelectedIds.indexOf("credit") < 0){
				showCredit = false;
			}
			if(fdSelectedIds.indexOf("learnTime") < 0){
				showLearnTime = false;
			}
			if(fdSelectedIds.indexOf("integral") < 0){
				showIntegral = false;
			}else{
				showIntegral = true;
				if(fdSelectedIds.indexOf("integral_fdTotalScore") > -1){				
					integralShowProp = "fdTotalScore";
				}			
				if(fdSelectedIds.indexOf("integral_fdTotalRiches") > -1){
					showIntegral = true;
					integralShowProp = "fdTotalRiches";
				}
			}
		}	
		var url = "/kms/lservice/kms_lservice_module_select/integralAndCreditAndLearnTime_selectpage.jsp?";
		url += "showCredit=" + showCredit;
		url += "&showLearnTime=" + showLearnTime;
		url += "&showIntegral=" + showIntegral;
		url += "&integralShowProp=" + integralShowProp;
		console.log("url",url);
		dialog
				.iframe( url,
						'选择展示的数据',
						function(value) {
							if (!value) {
								return;
							} else {
								console.log(value);
								$('input[name="' + id + '"]').val(value.fdId);
								$('input[name="' + name + '"]').val(value.fdName);
								
								
							}
						}, {"width":750,"height":550});
	}
	module.exports = SelectIntegralAndCreditAndLearnTime;
});