define(function(require, exports, module) {
	var langM = require('lang!sys-iassister');// 模块多语言
	var langD = require('lang!');// 默认多语言
	var $ = require("lui/jquery");
	var lang = $.extend(langM, langD);// 合并多语言
	var dialog = require("lui/dialog");
	var topic = require("lui/topic");
	var common = require("sys/iassister/resource/js/common.js");
	require("../check_items/check_items.css");

	var paramsHere = {
		checkUrl: "/sys/iassister/sys_iassister_check/sysIassisterCheck.do",
		templateUrl: "/sys/iassister/sys_iassister_template/sysIassisterTemplate.do"
	}

	var init = function(params) {
		initParams(params);
		btnToFirst();
		if (paramsHere.hasAuth && paramsHere.hasCheckItems) {
			initTemplateInfos();
			initContent();
			$("#icheckBtn").show();
		} else {
			LUI("icheckBtn").erase();
		}
//		handleDrawPanelEvent();
	}

	var btnToFirst = function() {
		var btnTr = $("#icheckBtn").parent().parent();
		var preTr = btnTr.prev();
		while (preTr.length > 0) {
			preTr.insertAfter(btnTr);
			preTr = btnTr.prev();
		}
	}

	var handleDrawPanelEvent = function() {
		var eachFunc = function(ce) {
			if (ce.handler.toString().indexOf("self.slideIn()") > -1) {
				$(document).off("click", ce.handler);
				return "break";
			}
		}
		common.arrForEach($._data($(document)[0], "events").click, eachFunc);
	}

	var initParams = function(params) {
		paramsHere = $.extend(paramsHere, params);
		paramsHere.curNode = lbpm.globals.getCurrentNodeObj();
		paramsHere.hasCheckItems = "true" == params.hasCheckItems;
		paramsHere.hasAuth = "true" == params.hasAuth;
	}

	var initTemplateInfos = function() {
		var getTemplateInfoUrl = paramsHere.ctxPath + paramsHere.templateUrl;
		getTemplateInfoUrl = Com_SetUrlParameter(getTemplateInfoUrl, "method",
			"getTemplateInfo");
		getTemplateInfoUrl = Com_SetUrlParameter(getTemplateInfoUrl,
			"templateId", paramsHere.templateId);
		getTemplateInfoUrl = Com_SetUrlParameter(getTemplateInfoUrl,
			"templateModelName", paramsHere.templateModelName);
		getTemplateInfoUrl = Com_SetUrlParameter(getTemplateInfoUrl, "nodeId",
			paramsHere.curNode.id);
		$.ajax({
			async: false,
			url: getTemplateInfoUrl,
			dataType: "json",
			success: function(rtn) {
				if (rtn.success) {
					paramsHere.mappingFieldNames = rtn.mappingFields;
					paramsHere.showGroups = rtn.showGroups;
				}
			}
		})
	}

	var initContent = function() {
		var config = {
			el: "#resultContainer",
			data: {
				checkItem: null,
				info: null,
				infoVisible: false,
				checkResults: [],
				mapShowGroups: null,
				resultTitle: paramsHere.resultTitle,
				expandGroups: [],
				showResultLabel: true
			},
			created: function() {
				this.initData();
			},
			mounted: function() {
				// vue挂载之后的处理
				this.afterMounted();
			},
			computed: {
				infoTitle: function() {
					var rtn = "";
					if (this.info) {
						if (this.info.type == "pic") {
							rtn = lang["msg.check.content.hint.15"];
						} else if (this.info.type == "text") {
							rtn = lang["msg.check.content.hint.16"];
						} else if (this.info.type == "link") {
							rtn = lang["msg.check.content.hint.17"];
						}
					}
					return rtn;
				},
				checkResultLabel: function() {
					var rtn = "";
					if (this.checkItem) {
						rtn = lang["msg." + this.checkItem.result];
					}
					return rtn;
				},
				picRows: function() {
					var rtn = [[]];
					if (this.info && this.info.type == "pic") {
						var rowIdx = 0;
						var eachFunc = function(pic, idx) {
							if (idx > 0 && idx % 3 == 0) {
								rowIdx++;
								rtn.splice(rowIdx, 0, []);
							}
							rtn[rowIdx].push(pic);
						}
						common.arrForEach(this.info.fileList, eachFunc);
					}
					return rtn;
				}
			},
			methods: {
				initData: function() {
					topic.subscribe("showInfo", this.onShowInfo);
					Com_Parameter.event["submit"].push(this.submitFunc);
					this.mapShowGroups = common.arrayToMap(
						paramsHere.showGroups, "key");
				},
				afterMounted: function() {
					$("#resultContainer").show();
				},
				initResult: function(resultData) {
					var self = this;
					if (resultData && resultData.length > 0) {
						self.checkResults.splice(0, self.checkResults.length);
						var addedResultKeys = {};
						var groupEach = function(sg) {
							var gKey = sg.key;
							var newRD = resultData.filter(function(t) {
								var fit = t.groups.indexOf(gKey) > -1;
								if (fit && !addedResultKeys[t.key]) {
									self.$set(addedResultKeys, t.key, t.key);
								}
								return fit;
							})
							if (sg.templates && sg.templates.length > 0 && newRD.length > 0) {
								var mapNewRD = common.arrayToMap(newRD,
									"templateId");
								newRD = [];
								common.arrForEach(sg.templates, function(t) {
									if (mapNewRD.hasOwnProperty(t.key)) {
										newRD.push(mapNewRD[t.key]);
									}
								})
							}
							var cr = {
								key: gKey,
								isGroup: true,
								expand: true,
								label: sg.label,
								results: newRD
							}
							self.checkResults.push(cr);
						}
						common.arrForEach(paramsHere.showGroups, groupEach);
						var resultFunc = function(rd) {
							if (!addedResultKeys[rd.key]) {
								rd.isGroup = false;
								self.checkResults.push(rd);
							}
						}
						common.arrForEach(resultData, resultFunc);
					}
				},
				expandAllGroups: function() {
					var self = this;
					self.expandGroups.splice(0, self.expandGroups.length);
					common.arrForEach(paramsHere.showGroups, function(sg) {
						self.expandGroups.push(sg.key);
					})
				},
				onShowInfo: function(params) {
					this.checkItem = params.checkItem;
					this.info = params.info;
					this.infoVisible = true;
				},
				submitFunc: function(formObj, method, clearParameter,
					moreOptions) {
					var canSubmit = true;
					var docStatus = Com_GetUrlParameter(formObj.action,
						"docStatus");
					var curOpt = lbpm.globals.getCurrentOperation();
					//
					if (curOpt.type == "handler_pass"
						|| curOpt.type == "drafter_submit") {
						if (paramsHere.draftStatus != docStatus) {
							var checkResult = postCheck(getCheckUrl(), "submit")
								|| [];
							for (var i = 0, len = checkResult.length; i < len; i++) {
								var checkItem = checkResult[i];
								if (checkItem.result != "success") {
									if (checkItem.submitType == "stop") {
										canSubmit = false;
										break;
									}
								}
							}
						}
						if (!canSubmit) {
							common.msg({
								type: "error",
								message: lang["msg.icheck.error"]
							})
							showCheckResult(checkResult);
						}
					}
					return canSubmit;
				},
				switchExpand: function(cr) {
					this.$set(cr, "expand", !cr.expand);
				},
				expandIcon: function(cr) {
					var rtn = "el-icon-d-arrow-right";
					if (!cr.expand) {
						rtn = "el-icon-d-arrow-left";
					}
					return rtn;
				},
				resultIcon: function(ci) {
					var rtn = "el-icon-" + ci.result + " " + ci.result;
					return rtn;
				},
				resultLabel: function(ci) {
					var rtn = lang["msg." + ci.result];
					return rtn;
				},
				showInfo: function(ci, type, info) {
					var sinfo = $.extend({
						type: type
					}, info);
					topic.publish("showInfo", {
						info: sinfo,
						checkItem: ci
					})
				},
				previewImg: function(attId) {
					var previewUrl = "/sys/portal/sys_portal_material_main/import/preview_dialog.jsp";
					previewUrl = Com_SetUrlParameter(previewUrl, "imgUrl",
						attId);
					previewUrl = Com_SetUrlParameter(previewUrl, "open", 1);
					var previewFrame = dialog.iframe(previewUrl,
						lang["button.preview"], function(value) {

						}, {
						width: document.body.clientWidth,
						height: document.body.clientHeight
					})
					console.log(previewFrame);
					previewFrame.on("layoutDone", function(evt) {
						console.log(evt)
					})
					var self = this;
					setTimeout(function() {
						var dzidx = $(self.$refs["infoDialog"].$el).css(
							"z-index");
						console.log(dzidx)
						var zidx = dzidx + 1;
						previewFrame.element.css("z-index", zidx);
					}, 100)
				}
			}
		}
		paramsHere.resultVue = new Vue(config);
	}

	var getCheckUrl = function() {
		var checkUrl = paramsHere.ctxPath + paramsHere.checkUrl;
		checkUrl = Com_SetUrlParameter(checkUrl, "method", "execCheck");
		checkUrl = Com_SetUrlParameter(checkUrl, "fdTmplateModelId",
			paramsHere.templateId);
		checkUrl = Com_SetUrlParameter(checkUrl, "fdTemplateModelName",
			paramsHere.templateModelName);
		checkUrl = Com_SetUrlParameter(checkUrl, "fdModelId",
			paramsHere.mainModelId);
		checkUrl = Com_SetUrlParameter(checkUrl, "fdModelName",
			paramsHere.mainModelName);
		checkUrl = Com_SetUrlParameter(checkUrl, "nodeId",
			paramsHere.curNode.id);
		checkUrl = Com_SetUrlParameter(checkUrl, "fdKey", paramsHere.fdKey);
		if ("view" == paramsHere.method) {
			checkUrl = Com_SetUrlParameter(checkUrl, "fdPageMode", "view");
		}
		return checkUrl;
	}

	var getParamData = function() {
		var rtn = [];
		if (paramsHere.mappingFieldNames) {
			for (var i = 0, len = paramsHere.mappingFieldNames.length; i < len; i++) {
				var fn = paramsHere.mappingFieldNames[i];
				var pd = {
					name: fn,
					value: ""
				}
				var domExist = false;
				var domFN = fn;
				var isCustomDetail = false;
				if (fn.indexOf("fd_") > -1) {
					domFN = "extendDataFormInfo.value(" + fn + ")";
					if (fn.indexOf(".") > -1) {
						isCustomDetail = true;
					}
				}
				var pdvType = $("[name='" + domFN + "']").prop("type");
				var pdv = $("[name='" + domFN + "']").val();
				if (pdvType == 'radio') {
					pdv = $("[name='" + domFN + "']:checked").val();
				}

				if ($("[name='" + domFN + "']").length > 0) {
					domExist = true;
				}

				if (!pdv) {
					pdv = $("[name='" + domFN + "Id']").val();
					if ($("[name='" + domFN + "Id']").length > 0) {
						domExist = true;
					}
				}
				// TABLE_DL_fd_38a32713db4cfa
				if (isCustomDetail) {
					pdv = "";
					var dtid = "TABLE_DL_" + fn.split(".")[0];
					var eachFunc = function() {
						var trT = $(this).attr("type");
						if (!trT || "templateRow" == trT) {
							var nameS = "[name^='extendDataFormInfo.value("
								+ fn.split(".")[0] + "']"
							pdv += $(this).find(nameS).val() + "|";
							if ($(this).find(nameS).length > 0) {
								domExist = true;
							}
						}
					}
					$("#" + dtid).find("tr").each(eachFunc);
					if (pdv) {
						pdv = pdv.substring(0, pdv.length - 1);
					}
				}
				if (typeof pdv == 'undefined') {
					pdv = '';
				}
				pd.value = pdv;
				if (domExist) {
					rtn.push(pd);
				}
			}
		}
		if (window.console) {
			console.log('获取的表单数据：');
			for (var m = 0; m < rtn.length; m++) {
				console.log(rtn[m]);
			}
		}
		return rtn;
	}

	var icheck = function() {
		postCheck(getCheckUrl(), "icheck");
	}

	var showCheckResult = function(resultData) {
		paramsHere.resultVue.initResult(resultData);
		var panel = LUI(paramsHere.panelId);
		var curContent = LUI("check_items_content");
		panel.isOpen = false;
		setTimeout(function() {
			panel.setSelectedIndex(curContent.element.parent().parent().parent().index());
		}, 1)
	}

	var postCheck = function(checkUrl, trigger) {
		var rtnData = [];
		var postData = {};
		postData.paramData = JSON.stringify(getParamData());
		var rtn = common.postJsonData(checkUrl, postData);
		if (rtn.success) {
			rtnData = rtn.result;
		} else {
			console.log(rtn.error);
		}
		if ("icheck" == trigger) {
			showCheckResult(rtnData);
		}
		return rtnData;
	}

	module.exports.init = init;
	module.exports.lang = lang;
	module.exports.icheck = icheck;
	module.exports.params = paramsHere;
})