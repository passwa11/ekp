define(function(require, exports, module) { 
	
	var $ = require("lui/jquery");
	var strutil = require('lui/util/str');
	var env = require("lui/util/env");
	var dialog = require("lui/dialog");
	function ImSms(config) {
		if(!config)
			 config = {};
		this.contextPath = config.contextPath || "";
		this.href = "/sys/sms/sys_sms_main/sysSmsMain.do?method=add&fdRecPersonIds=";
	}
	//show事件,___params为communicate的参数
	ImSms.prototype.onShow =  function ($content, ___params) {
		if(!___params.mobileNo) {
			$content.addClass("disabled");
		}
	};
	
	ImSms.prototype.onClick = function ($content, ___params) {
		if(___params.personId) {
			var url =(this.contextPath ? this.contextPath : "") + this.href + ___params.personId;
			console.log("url:", url);
		    dialog.iframe(url,' ',null,{width:640,height:520});
		}
	};
	module.exports = ImSms;
	
});