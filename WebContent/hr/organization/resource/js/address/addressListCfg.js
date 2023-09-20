define(function(require, exports, module) {
	
	var addressPanel =  require('hr/organization/resource/js/address/addressPanel');
	
	var ____cfg = [
	   {
			id :'address.list',
			text : '',
			style : '',
			panel: addressPanel.ListAddressPanel//备选列表面板
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