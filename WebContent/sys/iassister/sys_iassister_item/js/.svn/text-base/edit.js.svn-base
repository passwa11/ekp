define(function(require, exports, module) {
	var langM = require('lang!sys-iassister');// 模块多语言
	var langD = require('lang!');// 默认多语言
	var $ = require("lui/jquery");
	var lang = $.extend(langM, langD);// 合并多语言
	var dialog = require("lui/dialog");
	var topic = require("lui/topic");
	var selectionDialog = require("sys/iassister/resource/js/selection_dialog.js");

	var paramsHere = {}

	var validationHere = null;

	var init = function(params) {
		initParams(params);
		initValidation();
	}

	var initParams = function(params) {
		paramsHere = $.extend(paramsHere, params);
		if (!params.categoryId) {
			var addUrl = Com_SetUrlParameter(paramsHere.actionUrl, "method",
					"add");
			addUrl += "&i.docCategory=!{id}";
			dialog.categoryForNewFile(
					"com.landray.kmss.sys.iassister.model.SysIassisterItem",
					addUrl, false, "", function(rtn) {
						if (!rtn) {
							Com_Parameter.CloseInfo = null;
							Com_CloseWindow();
						}
					}, null, "_blank", true, false);
		}
	}

	var initValidation = function() {
		validationHere = $KMSSValidation();
	}

	var ruleInfoUrl = function(ruleId) {
		var rtnUrl = Com_SetUrlParameter(paramsHere.actionUrl, "method",
				"getRuleInfo");
		rtnUrl = Com_SetUrlParameter(rtnUrl, "ruleId", ruleId);
		return rtnUrl;
	}

	var afterRuleChoosed = function(params) {
		var self = this;
		if (params.choosed) {
			var choosedRule = params.choosed.data[0];
			paramsHere.choosedRuleId = choosedRule.fdId;
			var oldName = $("input[name='fdName']").val();
			if (!oldName) {
				$("input[name='fdName']").val(choosedRule.fdName);
			}
			validationHere.validateElement($("input[name='ruleName']")[0]);
			validationHere.validateElement($("input[name='fdName']")[0]);
			$.ajax({
				url : ruleInfoUrl(choosedRule.fdId),
				success : function(rtn) {
					topic.publish("ruleChoosed", rtn);
				}
			})
		}
	}

	var chooseRuleSet = function() {
		var treeParams = {
			action : function(rtnData) {
				afterRuleChoosed({
					choosed : rtnData
				})
			},
			multi : false,
			idField : "ruleId",
			nameField : "ruleName",
			dialogTitle : lang["msg.rule.choose"],
			treeBean : "sysRuleSetTreeBean&parentId=!{value}",
			dataBean : "sysRuleSetDataBean&categoryId=!{value}&choosedRuleId="
					+ paramsHere.choosedRuleId,
			searchBean : "sysRuleSetSearchBean&key=!{keyword}&choosedRuleId="
					+ paramsHere.choosedRuleId
		}
		selectionDialog.treeSelect(treeParams);
	}

	var loaded = function() {
		topic.publish("headLoaded");
	}

	var save = function() {
		var form = document.forms["sysIassisterItemForm"];
		validationHere.resetElementsValidate(form);
		Com_Submit(form, "edit" == paramsHere.method ? "update" : "save");
	}

	var saveAdd = function() {
		var form = document.forms["sysIassisterItemForm"];
		validationHere.resetElementsValidate(form);
		Com_Submit(form, "saveadd");
	}

	module.exports.init = init;
	module.exports.lang = lang;
	module.exports.loaded = loaded;
	module.exports.save = save;
	module.exports.saveAdd = saveAdd;
	module.exports.chooseRuleSet = chooseRuleSet;
})