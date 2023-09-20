define(function(require, exports, module) {
	
	var addressPanel =  require('hr/organization/resource/js/address/addressPanel');
	var lang = require('lang!sys-ui');
	
	var ____cfg = [
	   {
			id :'address.tabs.recent',
			text : lang['address.recent'],
			style : 'icon-recently',
			panel: addressPanel.RecentAddressPanel//常用联系人
		},{
			id :'address.tabs.org',
			text : lang['address.org'],
			style : 'icon-org',
			panel: addressPanel.OrgAddressPanel//组织架构
		}/*,{
			id :'address.tabs.group',
			text :  lang['address.group'],
			style : 'icon-group',
			panel: addressPanel.GroupAddressPanel//常用群组
		},{
			id :'address.tabs.sysRole',
			text : lang['address.sysRole'],
			style : 'icon-sysRole',
			panel: addressPanel.SysRoleAddressPanel//角色线
		}*/,{
			id :'address.tabs.search',
			text : lang['address.search'],
			style : 'icon-search',
			panel: addressPanel.SearchAddressPanel//高级搜索
		}
	];
	
	var cfg = function(options){
		var cloneCfg = []
		var includeType= options ? options.includeType : '';
		if(includeType){
			for(var i = 0; i < ____cfg.length; i++){
				if(includeType.indexOf(____cfg[i].id) > -1){
					cloneCfg.push(____cfg[i]);
				}
			}
			return cloneCfg
		}
		var exceptType  = options ? options.exceptType : '';
		for(var i = 0 ;i < ____cfg.length; i++){
			if(exceptType.indexOf( ____cfg[i].id ) < 0 ){
				cloneCfg.push(____cfg[i]);
			}
		}
		return cloneCfg;
	};
	
	
	module.exports = cfg;
	
});