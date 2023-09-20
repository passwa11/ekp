define(function(require, exports, module) {

	var menuContent = require('sys/zone/address/resource/js/zoneAddressMenuContent');
	var lang = require('lang!sys-zone');

	var ____cfg = [ {
		id : 'zoneaddress.menu.org',
		text : lang['zoneaddress.org'],
		content : menuContent.OrgZoneAddressContent
	// 组织架构

	}, {
		id : 'zoneaddress.menu.find.inner',
		text : lang['zoneaddress.find.inner'],
		content : menuContent.InnerZoneAddressContent
	// 有事找人（内部）
	}, {
		id : 'zoneaddress.menu.find.outer',
		text : lang['zoneaddress.find.outer'],
		content : menuContent.OuterZoneAddressContent
	// 外部伙伴（外部）
	} ];

	var cfg = function(options) {
		var exceptType = options ? options.exceptType : '', cloneCfg = [];
		for (var i = 0; i < ____cfg.length; i++) {
			if (exceptType.indexOf(____cfg[i].id) < 0) {
				cloneCfg.push(____cfg[i]);
			}
		}
		return cloneCfg;
	};

	module.exports = cfg;

});