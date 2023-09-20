define(function(require, exports, module) {
	var langM = require('lang!sys-iassister');// 模块多语言
	var langD = require('lang!');// 默认多语言
	var $ = require("lui/jquery");
	var lang = $.extend(langM, langD);// 合并多语言
	var dialog = require("lui/dialog");
	var topic = require("lui/topic");
	var common = require("sys/iassister/resource/js/common.js");

	require("../css/configinfo.css");

	var configDataFactory = require("../js/configData.js");

	var paramsHere = {}

	var init = function(params) {
		initParams(params);
		initConfigInfo();
		initContent();
	}

	var initConfigInfo = function() {
		var templateUrl = require.resolve("../templates/config-info.html#");
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
				config : Object
			},
			data : function() {
				return {
					configTabs : configDataFactory.configTabs,
					condTypes : configDataFactory.condTypes,
					submitTypes : configDataFactory.submitTypes,
					showTypes : configDataFactory.showTypes,
					ruleTypes : configDataFactory.ruleTypes,
					picAction : paramsHere.picAction,
					rules : ruleInfo.rules,
					ruleChoosed : paramsHere.choosedRuleId != "",
					editAuth : "view" != paramsHere.method,
					curLinkRow : null,
					configVisible : false,
					activeGroupName : "rules",
					condContent : "",
					ruleForm : {},
					configRules : {
						config : [ {
							validator : this.validateConfig,
							trigger : 'blur'
						} ]
					}
				}
			},
			mounted : function() {
				this.afterMounted();
			},
			updated : function() {
				if ("view" == paramsHere.method) {
					$(".el-upload").hide();
				}
			},
			watch : {
				curShowInfos : function(newV, oldV) {
					this.destroyTxtEditor(oldV);
				}
			},
			computed : {
				curTab : function() {
					return this.config[this.config.curTabKey];
				},
				curCondition : function() {
					return this.curTab && this.curTab.condition;
				},
				curCondContent : function() {
					return this.curCondition
							&& this.curCondition[this.curCondition.type];
				},
				curShowInfos : function() {
					return this.curTab && this.curTab.showInfos;
				},
				curShowInfo : function() {
					return this.curShowInfos
							&& this.curShowInfos[this.curShowInfos.showType];
				},
				mapShowTypes : function() {
					var rtn = {};
					for (var i = 0, len = this.showTypes.length; i < len; i++) {
						var st = this.showTypes[i];
						this.$set(rtn, st.key, st);
					}
					return rtn;
				},
				curShowTypes : function() {
					var rtn = [];
					var self = this;
					if (this.curShowInfos.showTypes) {
						this.curShowInfos.showTypes.forEach(function(stk) {
							rtn.push(self.mapShowTypes[stk]);
						})
						if (this.curShowInfos.showTypes.indexOf("text") > -1) {
							self.showCKEditor();
						}
					}
					return rtn;
				},
				filterRules : function() {
					var rtn = [];
					var choosedRuleIds = [];
					var self = this;
					var eachFunc = function(ct) {
						var ctc = self.config[ct.key].condition;
						if (ctc) {
							choosedRuleIds = choosedRuleIds
									.concat(ctc.check.ruleIds);
						}
					}
					this.configTabs.forEach(eachFunc);
					var isDisabled = function(ruleId) {
						var disabled = false;
						if (self.curCondContent.ruleIds.indexOf(ruleId) < 0
								&& choosedRuleIds.indexOf(ruleId) > -1) {
							disabled = true;
						}
						return disabled;
					}
					for (var i = 0, len = this.rules.length; i < len; i++) {
						var rule = common.cloneObject(this.rules[i]);
						this.$set(rule, "disabled", isDisabled(rule.key));
						rtn.push(rule);
					}
					return rtn;
				},
				rulesData : function() {
					var rtn = [];
					this.rules.forEach(function(rule) {
						var ruleD = common.cloneObject(rule);
						rtn.push(ruleD);
					})
					return rtn;
				},
				mapRulesData : function() {
					var rtn = common.arrayToMap(this.rulesData, "key");
					return rtn;
				}
			},
			methods : {
				validateConfig : function(rule, value, callback) {
					var errorInfo = "";
					var condConfiged = false;
					var self = this;
					var eachFuncForCond = function(ct) {
						if (ct.key != "default") {
							var tabCond = self.config[ct.key].condition;
							if (tabCond.type == "check") {
								if (tabCond.check.ruleIds.length > 0) {
									condConfiged = true;
									return "break";
								}
							} else if (tabCond.type == "config") {
								if (tabCond.config.content) {
									condConfiged = true;
									return "break";
								}
							}
						}
					}
					var eachFuncForShowInfos = function(ct) {
						if (ct.key != "success" && ct.key != "default") {
							// 校验link
							var showInfos = self.config[ct.key].showInfos;
							var linkEach = function(link, idx) {
								if (link.title == "") {
									errorInfo = lang["msg.check.item.link.title.required"];
									return "break";
								} else if (link.title.length > 200) {
									errorInfo = lang["msg.check.item.link.title.length"];
									return "break";
								}
							}
							common
									.arrForEach(showInfos.link.linkList,
											linkEach);
						}
					}
					common.arrForEach(this.configTabs, eachFuncForCond);
					common.arrForEach(this.configTabs, eachFuncForShowInfos);
					if (!condConfiged) {
						errorInfo = lang["msg.tab.cond.empty"];
					}
					if (!errorInfo) {
						if (!this.config["default"]) {
							errorInfo = lang["msg.tab.default.empty"];
						}
					}
					callback(errorInfo);
				},
				checkConfig : function() {
					var self = this;
					var rtn = {
						success : false
					}
					var ruleForm = this.$refs["ruleForm"];
					ruleForm.validate(function(valid) {
						rtn.success = valid;
						if (valid) {
							rtn.config = self.config;
						}
					})
					var delEach = function(ct) {
						if (ct.key != "default") {
							if (rtn.config && rtn.config[ct.key]
									&& rtn.config[ct.key].showInfos
									&& rtn.config[ct.key].showInfos.text) {
								self
										.destroyTxtEditor(rtn.config[ct.key].showInfos);
								delete rtn.config[ct.key].showInfos.text.txtEditor;
							}
						}
					}
					common.arrForEach(this.configTabs, delEach);
					return rtn;
				},
				afterMounted : function() {
					topic.subscribe("ruleChoosed", this.onRuleChoosed);
				},
				onRuleChoosed : function(ruleInfo) {
					this.rules = ruleInfo.rules;
					this.ruleChoosed = true;
				},
				showCKEditor : function() {
					setTimeout(this.showCKEditorTimeout, 100);
				},
				showCKEditorTimeout : function() {
					var self = this;
					var editDiv = $("#txtEditor_" + this.config.curTabKey);
					var txtEditor = this.curShowInfos.text.txtEditor;
					if (editDiv.length > 0 && !txtEditor) {
						txtEditor = CKEDITOR.replace(editDiv[0], {
							"toolbar" : "Wiki",
							"toolbarStartupExpanded" : false,
							"toolbarCanCollapse" : true
						})
						txtEditor.setData(this.curShowInfos.text.content);
						this.curShowInfos.text.txtEditor = txtEditor;
					}
				},
				showTypesChanged : function(showTypes) {
					if (showTypes.length > 0) {
						this.curShowInfos.showType = showTypes[showTypes.length - 1];
						if (showTypes.indexOf("text") < 0) {
							this.destroyTxtEditor(this.curShowInfos);
						}
					}
				},
				destroyTxtEditor : function(showInfos) {
					if (showInfos && showInfos.text && showInfos.text.txtEditor) {
						showInfos.text.content = showInfos.text.txtEditor
								.getData();
						showInfos.text.txtEditor.destroy();
						showInfos.text.txtEditor = null;
					}
				},
				onSuccess : function(resp, file, fileList) {
					if (resp.success) {
						this.$set(file, "attId", resp.attId);
						this.$set(file, "url", formatImgUrl("download",
								resp.attId));
						this.curShowInfo.fileList = fileList;
					} else {
						this.onError({
							"error" : resp.error,
							"from" : "server"
						}, file, fileList);
					}
				},
				onError : function(err, file, fileList) {
					if ("server" == err.from) {
						this.$set(file, "status", "error");
					}
				},
				onRemove : function(file, fileList) {
					// deleteImg(file.attId);
					this.curShowInfo.fileList = fileList;
				},
				delLink : function(idx) {
					if (this.curLinkRow
							&& this.curLinkRow.key == this.curShowInfo.linkList[idx].key) {
						this.curLinkRow = null;
					}
					this.curShowInfo.linkList.splice(idx, 1);
				},
				addLink : function() {
					if (this.curLinkRow && this.curLinkRow.title != "") {
						this.curLinkRow.writable = false;
					}
					var newLink = {
						key : common.genUUID(),
						title : "",
						href : "",
						writable : true
					}
					this.curShowInfo.linkList.push(newLink)
					this.curLinkRow = newLink;
					this.focusInput("title_" + newLink.key);
				},
				enterKeydown : function(evt) {
					if (this.curLinkRow) {
						this.curLinkRow.writable = false;
					}
				},
				cellClick : function(row, col, cell, evt) {
					if (this.editAuth) {
						var canChange = true;
						if (this.curLinkRow && this.curLinkRow.title == "") {
							canChange = false;
						}
						if (this.curLinkRow && this.curLinkRow.key != row.key) {
							if (canChange) {
								this.curLinkRow.writable = false;
							}
						}
						if (canChange) {
							row.writable = true;
							this.curLinkRow = row;
						} else {
							if (this.curLinkRow
									&& this.curLinkRow.key != row.key) {
								this.$refs["ruleForm"]
										.validate(function(valid) {
										});
							}
						}
						this.focusInput(col.type + "_" + this.curLinkRow.key);
					}
				},
				focusInput : function(inputRef) {
					if (this.editAuth) {
						var self = this;
						setTimeout(function() {
							var inputVue = self.$refs[inputRef];
							if (inputVue) {
								if (inputVue instanceof Array) {
									inputVue = inputVue[0];
								}
								inputVue.$refs["input"].focus();
							} else {
								self.focusInput(inputRef);
							}
						}, 100);
					}
				},
				editCond : function() {
					this.condContent = this.curCondContent.content;
					this.configVisible = true;
				},
				showCond : function(content) {
					var rtn = content;
					if (rtn) {
						for ( var key in this.mapRulesData) {
							rtn = rtn.replace(new RegExp(key, "gm"),
									this.mapRulesData[key].label)
						}
					}
					return rtn;
				},
				submitCondConfig : function() {
					this.curCondContent.content = this.condContent;
					this.configVisible = false;
				},
				clearCond : function() {
					this.condContent = "";
				},
				addSymbol : function(symbol) {
					if ([ "(", ")" ].indexOf(symbol) < 0) {
						symbol = " " + symbol + " ";
					}
					this.handleCondition(symbol);
				},
				handleNodeClick : function(data, node, ele) {
					if (this.editAuth) {
						this.handleCondition(data.key);
					}
				},
				handleCondition : function(content) {
					var condEditor = this.$refs["condEditor"];
					if (condEditor) {
						var textArea = condEditor.$refs["textarea"];
						if (textArea) {
							var readonly = true;
							if (readonly) {
								this.condContent += content;
							} else {
								if (typeof textArea.selectionStart === 'number'
										&& typeof textArea.selectionEnd === 'number') {
									var beforeSelect = this.condContent
											.substring(0,
													textArea.selectionStart);
									var afterSelect = this.condContent
											.substring(textArea.selectionEnd,
													this.condContent.length);
									this.condContent = beforeSelect + content
											+ afterSelect;
								}
							}
							textArea.focus();
						}
					}
				},
				linkTitleBlured : function() {
					this.$refs["ruleForm"].validate(function(valid) {
					});
				}
			}
		}
		Vue.component("config-info", vueConfig);
	}

	var initContent = function() {
		var vueConfig = {
			el : "#configInfoContainer",
			data : {
				configData : null,
				ruleChoosed : paramsHere.choosedRuleId != "",
				showConfig : true
			},
			created : function() {
				// 创建之后
				this.initData();
			},
			computed : {},
			mounted : function() {
				// vue挂载之后的处理
				this.afterMounted();
			},
			methods : {
				initData : function() {
					this.configData = configDataFactory
							.init(paramsHere.checkConfig);
				},
				afterMounted : function() {
					topic.subscribe("ruleChoosed", this.onRuleChoosed);
					this.$nextTick(function() {
						if ("view" == paramsHere.method) {
							$(".el-upload").hide();
						}
						$("#configInfoContainer").show();// 确保加载完后再显示
					})
				},
				onRuleChoosed : function() {
					this.ruleChoosed = true;
				},
				checkConfig : function(v, e, o) {
					var configInfo = this.$refs["configInfo"];
					var rtn = {
						success : true
					}
					if (this.ruleChoosed) {
						var rtn = configInfo.checkConfig();
						if (rtn.success) {
							var checkConfig = JSON.stringify(rtn.config);
							$("input[name='checkConfig']").val(checkConfig);
						}
					}
					return rtn.success;
				}
			}
		}
		paramsHere.vueEle = new Vue(vueConfig);
		if ("view" != paramsHere.method) {
			$KMSSValidation().addValidator("checkConfig",
					lang["msg.check.config.error"],
					paramsHere.vueEle.checkConfig);
		}
	}

	var initParams = function(params) {
		paramsHere = $.extend(paramsHere, params);
		paramsHere.picAction = Com_SetUrlParameter(paramsHere.actionUrl,
				"method", "addPicFile")
	}

	var formatImgUrl = function(method, attId) {
		var rtnUrl = paramsHere.ctxPath
				+ "/sys/attachment/sys_att_main/sysAttMain.do";
		rtnUrl = Com_SetUrlParameter(rtnUrl, "method", method);
		if (attId) {
			rtnUrl = Com_SetUrlParameter(rtnUrl, "fdId", attId);
		}
		return rtnUrl;
	}

	var deleteImg = function(attId) {
		var delUrl = formatImgUrl("deleteall");
		var param = {
			"List_Selected" : [ attId ]
		}
		$.ajax({
			url : delUrl,
			data : $.param(param, true),
			dataType : 'json',
			type : 'POST',
			success : function(data) {

			},
			error : function(req) {
				if (req.responseJSON) {
					var data = req.responseJSON;
					dialog.failure(data.title);
				} else {
					dialog.failure(langD["return.optFailure"]);
				}
			}
		});
	}

	module.exports.init = init;
	module.exports.params = paramsHere;
})