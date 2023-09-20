define(function(require, exports, module) {
	
	var $ = require('lui/jquery');
	var env = require('lui/util/env');
	
	//编辑
	window.toEditView = function (){
		var fdId = $("input[name='fdId']").val();
		window.location.href=env.fn.formatUrl("/sys/sender/sender_email_info/sysSenderEmailInfo.do?method=edit&fdId="+fdId);
	}
	
});