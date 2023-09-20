define(function(require, exports, module) {
	var langM = require('lang!sys-iassister');// 模块多语言
	var langD = require('lang!');// 默认多语言
	var $ = require("lui/jquery");
	var lang = $.extend(langM, langD);// 合并多语言

	var showTypes = [ {
		key : "pic",
		label : lang["msg.check.content.hint.11"],
		simpleL : lang["msg.check.content.hint.15"]
	}, {
		key : "text",
		label : lang["msg.check.content.hint.12"],
		simpleL : lang["msg.check.content.hint.16"]
	}, {
		key : "link",
		label : lang["msg.check.content.hint.13"],
		simpleL : lang["msg.check.content.hint.17"]
	} ]

	var condTypes = [ {
		key : "check",
		label : lang["msg.check.content.hint.22"]
	}, {
		key : "config",
		label : lang["msg.check.content.hint.23"]
	} ]

	var configTabs = [ {
		key : "error",
		label : lang["msg.error"]
	}, {
		key : "success",
		label : lang["msg.success"]
	}, {
		key : "warning",
		label : lang["msg.warning"]
	}, {
		key : "default",
		label : lang["msg.default"]
	} ]

	var ruleTypes = [ {
		key : "one",
		label : lang["msg.check.content.hint.18"]
	}, {
		key : "all",
		label : lang["msg.check.content.hint.19"]
	} ]

	var submitTypes = [ {
		key : "continue",
		label : lang["msg.check.content.hint.8"]
	}, {
		key : "stop",
		label : lang["msg.check.content.hint.9"]
	} ];

	var configData = {
		curTabKey : "error",
		"default" : "",
		error : {
			condition : {
				type : "check",// check或者config
				check : {
					ruleType : "all",// one或者all
					ruleIds : []
				},
				config : {
					content : ""
				}
			},
			submitType : "stop",// stop或者continue
			showInfos : {
				showNone : false,
				showTypes : [],
				showType : "",
				text : {
					content : ""
				},
				pic : {
					fileList : []
				},
				link : {
					linkList : []
				}
			}
		},
		success : {
			condition : {
				type : "check",// check或者config
				check : {
					ruleType : "all",// one或者all
					ruleIds : []
				},
				config : {
					content : ""
				}
			}
		},
		warning : {
			condition : {
				type : "check",// check或者config
				check : {
					ruleType : "all",// one或者all
					ruleIds : []
				},
				config : {
					content : ""
				}
			},
			submitType : "continue",// stop或者continue
			showInfos : {
				showNone : false,
				showTypes : [],
				showType : "",
				text : {
					content : ""
				},
				pic : {
					fileList : []
				},
				link : {
					linkList : []
				}
			}
		}
	}

	var init = function(checkConfig) {
		if (checkConfig) {
			configData = $.extend(configData, checkConfig);
		}
		return configData;
	}

	module.exports.init = init;
	module.exports.submitTypes = submitTypes;
	module.exports.configTabs = configTabs;
	module.exports.ruleTypes = ruleTypes;
	module.exports.showTypes = showTypes;
	module.exports.condTypes = condTypes;
})