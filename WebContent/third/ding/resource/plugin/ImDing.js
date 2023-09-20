define(function(require, exports, module) { 
	
	var $ = require("lui/jquery");
	var strutil = require('lui/util/str');
	
	function ImDing(config) {
		this.dingPrefix = "dingtalk://dingtalkclient/action/open_profile?staff_id=";
	}
	//show事件,___params为communicate的参数
	ImDing.prototype.onShow =  function ($content, ___params) {
		if(!___params.dingUserid || !___params.dingCropid) {
			$content.addClass("disabled");
		}
	};
	
	ImDing.prototype.onClick = function ($content, ___params) {
		console.log("___params in ding=>" + JSON.stringify(___params));
		if(___params.dingUserid && ___params.dingCropid) {
			var dingUrl = this.dingPrefix + ___params.dingUserid + "&corp_id=" + ___params.dingCropid;
			console.log("dingUrl=>"+dingUrl);
			window.open(dingUrl, "_parent");
		}
	};
	module.exports = ImDing;
	
});