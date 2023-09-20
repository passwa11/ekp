define(function(require, exports, module) {
	var $ = require("lui/jquery");
	var dialog = require("lui/dialog");
	var topic = require("lui/topic");

	var paramsHere = {}

	var init = function(params) {
		initParams(params);
		initContent();
	}

	var initParams = function(params) {
		paramsHere = $.extend(paramsHere, params);
	}

	var initContent = function() {
		var contentConfig = {
			el : "#ruleInfo",
			data : {
				ruleChoosed : paramsHere.choosedRuleId != "",
				showParam : true,
				showRule : true,
				params : ruleInfo.params,
				rules : ruleInfo.rules
			},
			computed : {},
			mounted : function() {
				// vue挂载之后的处理
				this.afterMounted();
			},
			methods : {
				afterMounted : function() {
					$("#ruleInfo").show();// 确保加载完后再显示
					topic.subscribe("ruleChoosed", this.onRuleChoosed);
				},
				onRuleChoosed : function(ruleInfo) {
					this.params = ruleInfo.params;
					this.rules = ruleInfo.rules;
					this.ruleChoosed = true;
				}
			}
		}
		new Vue(contentConfig);
	}

	module.exports.init = init;
})