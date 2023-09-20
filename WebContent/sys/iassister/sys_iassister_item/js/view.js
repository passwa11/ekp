define(function(require, exports, module) {
	var langM = require('lang!sys-iassister');// 模块多语言
	var langD = require('lang!');// 默认多语言
	var $ = require("lui/jquery");
	var lang = $.extend(langM, langD);// 合并多语言
	var dialog = require("lui/dialog");
	var topic = require("lui/topic");

	var paramsHere = {}

	var init = function(params) {
		initParams(params);
	}

	var initParams = function(params) {
		paramsHere = $.extend(paramsHere, params);
	}

	var loaded = function() {
		topic.publish("headLoaded");
	}

	var getMethodUrl = function(modelId, methodType) {
		var rtnUrl = Com_SetUrlParameter(paramsHere.actionUrl, "method",
				methodType);
		rtnUrl = Com_SetUrlParameter(rtnUrl, "fdId", modelId);
		return rtnUrl;
	}

	var edit = function(modelId) {
		var editUrl = getMethodUrl(modelId, "edit");
		Com_OpenWindow(editUrl, "_self");
	}

	module.exports.init = init;
	module.exports.lang = lang;
	module.exports.loaded = loaded;
	module.exports.edit = edit;
})