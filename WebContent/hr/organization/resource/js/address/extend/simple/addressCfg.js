define(function(require, exports, module) {
	
	var addressPanel =  require('./addressPanel');
	var lang = require('lang!sys-ui');
	
	var ____cfg = [
	   {
			id :'address.tabs.org',
			text : lang['address.org'],
			style : 'icon-org',
			panel: addressPanel.Panel//自定义组织架构
		}
	];
	
	var cfg = function(options){
		var exceptType  = options ? options.exceptType : '',
			cloneCfg = [];
		for(var i = 0 ;i < ____cfg.length; i++){
			if(exceptType.indexOf( ____cfg[i].id ) < 0 ){
				cloneCfg.push(____cfg[i]);
			}
		}
		return cloneCfg;
	};
	
	
	module.exports = cfg;
	
});