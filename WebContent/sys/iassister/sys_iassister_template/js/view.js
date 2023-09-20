define(function(require, exports, module) {
	var langM = require('lang!sys-iassister');// 模块多语言
	var langD = require('lang!');// 默认多语言
	var $ = require("lui/jquery");
	var lang = $.extend(langM, langD);// 合并多语言
	var dialog = require("lui/dialog");
	var topic = require("lui/topic");
	var common = require("sys/iassister/resource/js/common.js");

	require("../check_templates/template.css");

	var paramsHere = {
		lbpmNodesUrl : "/sys/lbpmservice/support/lbpm_template/lbpmTemplate.do?method=findNodesByModels",
		nodes : []
	}

	var init = function(params) {
		initParams(params);
		initEvents();
	}

	var initEvents = function() {
		if ($("#Label_Tabel").length > 0 && Doc_AddLabelSwitchEvent) {
			Doc_AddLabelSwitchEvent($("#Label_Tabel")[0], "switchToIassister");
		}
	}

	var switchToIassister = function(tableName, index) {
		var trs = document.getElementById(tableName).rows;
		if (trs[index].id == "IA_" + paramsHere.fdKey) {
			var formTemplate = $("#IFrame_FormTemplate_" + paramsHere.fdKey);
			if (formTemplate.length > 0) {
				paramsHere.xformDesigner = formTemplate[0].contentWindow.Designer;
				topic.publish("switchToIassister");
			}
		}
	}

	var initParams = function(params) {
		paramsHere = $.extend(paramsHere, params);
		if (paramsHere.checkTemplates) {
			paramsHere.checkTemplates = paramsHere.checkTemplates.replace(
					/\+/g, '%20');
		}
		if (paramsHere.checkGroups) {
			paramsHere.checkGroups = paramsHere.checkGroups.replace(/\+/g,
					'%20');
		}
		paramsHere.nodes = initNodes();
	}

	var extractNodes = function(processData) {
		var rtn = [];
		if (processData && processData.nodes) {
			var eachFunc = function(node) {
				if ([ "draftNode", "reviewNode", "signNode", "voteNode" ]
						.indexOf(node.XMLNODENAME) > -1) {
					rtn.push({
						key : node.id,
						label : node.name + node.id,
						type : node.XMLNODENAME
					});
				}
			}
			common.arrForEach(processData.nodes, eachFunc)
		}
		return rtn;
	}

	var initNodes = function() {
		var rtnNodes = [];
		var getNodesUrl = paramsHere.ctxPath + paramsHere.lbpmNodesUrl;
		getNodesUrl = Com_SetUrlParameter(getNodesUrl, "fdModelIds",
				paramsHere.templateId);
		getNodesUrl = Com_SetUrlParameter(getNodesUrl, "fdModelName",
				paramsHere.templateName);
		getNodesUrl = Com_SetUrlParameter(getNodesUrl, "key", paramsHere.fdKey);
		getNodesUrl = Com_SetUrlParameter(getNodesUrl, "fdNames", "empty");
		var successHandler = function(rtnData) {
			var rtn = JSON.parse(rtnData);
			if (rtn && rtn.data && rtn.data.length > 0) {
				rtnNodes = extractNodes(WorkFlow_LoadXMLData(rtn.data[0].xml));
			}
		}
		$.ajax({
			url : getNodesUrl,
			async : false,
			success : successHandler
		})
		return rtnNodes;
	}

	var initContent = function() {
		initCheckTemplates();
		var vueConfig = {
			el : "#checkTemplateWrapper",
			data : {
				checkTemplates : [],
				checkGroups : []
			},
			created : function() {
				this.initData();
			},
			mounted : function() {
				// vue挂载之后的处理
				this.afterMounted();
			},
			methods : {
				initData : function() {
					this.checkTemplates = paramsHere.checkTemplates ? JSON
							.parse(decodeURIComponent(paramsHere.checkTemplates))
							: []
					this.checkGroups = paramsHere.checkGroups ? JSON
							.parse(decodeURIComponent(paramsHere.checkGroups))
							: []
				},
				afterMounted : function() {
					//
				}
			}
		}
		new Vue(vueConfig);
	}

	var initCheckTemplates = function() {
		var templateUrl = require
				.resolve("../check_templates/check-templates.html#");
		var template = "";
		$.ajax({
			async : false,
			url : templateUrl,
			success : function(rtn) {
				template = "" + rtn;
			}
		})
		var vueConfig = {
			template : template,
			// 属性都是小写
			props : {
				templates : Array,
				groups : Array
			},
			data : function() {
				return {
					editAuth : false,
					groupMode : false,
					nodes : paramsHere.nodes
				}
			},
			mounted : function() {
				this.afterMounted();
			},
			watch : {},
			computed : {
				mapGroups : function() {
					return common.arrayToMap(this.groups, "key");
				},
				mapNodes : function() {
					return common.arrayToMap(this.nodes, "key");
				}
			},
			methods : {
				afterMounted : function() {
					topic.subscribe("switchToIassister",
							this.onSwitchToIassister);
				},
				onSwitchToIassister : function() {
				},
				showGroups : function(groups) {
					var rtn = "";
					var self = this;
					common.arrForEach(groups, function(gk) {
						rtn += self.mapGroups[gk].label + "；";
					})
					if (rtn) {
						rtn = rtn.substring(0, rtn.length - 1);
					}
					return rtn;
				},
				showNodes : function(nodes) {
					var rtn = "";
					var self = this;
					var nodeEach = function(nk) {
						rtn += self.mapNodes[nk] ? (self.mapNodes[nk].label + "；")
								: "";
					}
					common.arrForEach(nodes, nodeEach);
					if (rtn) {
						rtn = rtn.substring(0, rtn.length - 1);
					}
					return rtn;
				},
				cellClick : function(row, col, cell, evt) {

				}
			}
		}
		Vue.component("check-templates", vueConfig);
	}

	module.exports.init = init;
	module.exports.initContent = initContent;
	module.exports.lang = lang;
	module.exports.switchToIassister = switchToIassister;
})