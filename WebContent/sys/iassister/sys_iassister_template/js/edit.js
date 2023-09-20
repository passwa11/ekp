define(function(require, exports, module) {
	var langM = require('lang!sys-iassister');// 模块多语言
	var langD = require('lang!');// 默认多语言
	var $ = require("lui/jquery");
	var lang = $.extend(langM, langD);// 合并多语言
	var dialog = require("lui/dialog");
	var topic = require("lui/topic");
	var selectionDialog = require("sys/iassister/resource/js/selection_dialog.js");
	var common = require("sys/iassister/resource/js/common.js");

	require("../check_templates/template.css");

	var paramsHere = {
		choosedItemIds : "",
		lbpmNodesUrl : "/sys/lbpmservice/support/lbpm_template/lbpmTemplate.do?method=findNodes"
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
			paramsHere.xformDesigner = $("#IFrame_FormTemplate_"
					+ paramsHere.fdKey)[0].contentWindow.Designer;
			topic.publish("switchToIassister");
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
	}

	var getNodesUrl = function(lbpmType, commonId) {
		var rtnUrl = paramsHere.ctxPath + paramsHere.lbpmNodesUrl;
		if ("1" == lbpmType) {
			rtnUrl = Com_SetUrlParameter(rtnUrl, "modelName",
					paramsHere.templateName);
			rtnUrl = Com_SetUrlParameter(rtnUrl, "key", paramsHere.fdKey);
		} else {
			rtnUrl = commonId ? Com_SetUrlParameter(rtnUrl, "tempId", commonId)
					: "";
		}
		return rtnUrl;
	}

	var initNodes = function() {
		var rtnNodes = [];
		var typeSelector = "input[name='sysWfTemplateForms." + paramsHere.fdKey
				+ ".fdType']:checked";
		var lbpmType = $(typeSelector).val();// 1默认2引用通用模板3自定义
		if ("3" == lbpmType) {
			var flowIframe = document.getElementById("sysWfTemplateForms."
					+ paramsHere.fdKey + ".WF_IFrame");
			var flowChart = flowIframe.contentWindow.FlowChartObject;
			rtnNodes = extractNodes(flowChart.BuildFlowData());
		} else if ([ "2", "1" ].indexOf(lbpmType) > -1) {
			var kmssD = new KMSSData();
			var commonSelector = "input[name='sysWfTemplateForms."
					+ paramsHere.fdKey + ".fdCommonId']";
			var commonId = $(commonSelector).val();
			var nodesUrl = getNodesUrl(lbpmType, commonId);
			if (nodesUrl) {
				var nodesCallBack = function(rq) {
					var xml = rq.responseText;
					if (xml.indexOf('<error>') > -1) {
						console.log(xml);
					} else {
						rtnNodes = extractNodes(WorkFlow_LoadXMLData(xml));
					}
				}
				kmssD.SendToUrl(nodesUrl, nodesCallBack, false);
			}
		}
		return rtnNodes;
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
					// common.arrForEach(this.checkTemplates, function(ct) {
					// paramsHere.choosedItemIds += ct.itemKey + ";";
					// })
					var self = this;
					if ("clone" == paramsHere.method) {
						common.arrForEach(this.checkTemplates, function(ct) {
							self.$set(ct, "handleType", "add");
						})
					}
					paramsHere.oldCheckTemplates = "clone" == paramsHere.method ? {}
							: common.arrayToMap(common
									.cloneObject(this.checkTemplates), "key");
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
					editAuth : true,
					curEditCol : null,
					groupMode : false,
					nodes : [],
					groupRules : {
						label : [ {
							validator : this.validateGroupLabel,
							trigger : [ 'blur', 'change' ]
						} ]
					},
					checkRules : {
						label : [ {
							validator : this.validateCheckLabel,
							trigger : [ 'blur', 'change' ]
						} ]
					},
					repeatGroupLabel : false
				}
			},
			mounted : function() {
				this.afterMounted();
			},
			watch : {
				curEditCol : function(newV, oldV) {
					if (oldV) {
						if (!newV || newV.row.key != oldV.row.key
								|| newV.type != oldV.type) {
							if (oldV.type == "groups") {
								oldV.row.groupwritable = false;
							} else if (oldV.type == "nodes") {
								oldV.row.nodewritable = false;
							}
						}
					}
				},
				groupMode : function(newV, oldV) {
					this.curEditCol = null;
				},
				templates : function(newV, oldV) {
					this.updateGroups();
				}
			},
			computed : {
				mapGroups : function() {
					return common.arrayToMap(this.groups, "key");
				},
				mapNodes : function() {
					return common.arrayToMap(this.nodes, "key");
				},
				mapTemplates : function() {
					return common.arrayToMap(this.templates, "key");
				},
				groupTemplates : function() {
					var rtn = [];
					var self = this;
					var eachFunc = function(group, idx) {
						var groupTemplates = self.templates.filter(function(t) {
							return t.groups.indexOf(gKey) > -1;
						})
					}
					return rtn;
				}
			},
			methods : {
				afterMounted : function() {
					topic.subscribe("switchToIassister",
							this.onSwitchToIassister);
					Com_Parameter.event["submit"].push(this.onSubmit);
					this.rowDrop();
				},
				rowDrop : function() {
					// 此时找到的元素是要拖拽元素的父容器
					var tbody = $('.group_mode .el-table__body-wrapper tbody')[0];
					var self = this;
					var sortOptions = {
						ghostClass : "ghost",
						draggable : ".el-table__row",
						onEnd : function(evt) {
							var currRow = self.groups.splice(evt.oldIndex, 1)[0];
							self.groups.splice(evt.newIndex, 0, currRow);
						}
					}
					Sortable.create(tbody, sortOptions);
				},
				onSubmit : function() {
					var templatesData = this.handleTemplatesData();
					$("input[name='checkTemplates']").val(
							encodeURIComponent(JSON.stringify(templatesData)));
					var groupsData = this.handleGroupsData();
					$("input[name='checkGroups']").val(
							encodeURIComponent(JSON.stringify(groupsData)));
					return true;
				},
				handleGroupsData : function() {
					var rtn = [];
					if (this.templates.length > 0) {
						var eachFunc = function(group, idx) {
							var groupData = {
								key : group.key,
								label : group.label,
								nodes : group.nodes,
								templates : []
							}
							group.templates.forEach(function(t) {
								var tData = {
									key : t.key
								}
								groupData.templates.push(tData);
							})
							rtn.push(groupData);
						}
						common.arrForEach(this.groups, eachFunc);
					}
					return rtn;
				},
				handleTemplatesData : function() {
					var rtn = {
						add : [],
						update : [],
						del : []
					}
					var newCheckTemplateIds = [];
					for (var i = 0, len = this.templates.length; i < len; i++) {
						var ct = this.templates[i];
						newCheckTemplateIds.push(ct.key);
						if (ct.handleType) {
							// 新增的
							rtn[ct.handleType].push(ct);
						} else {
							// 看下是否更新
							var oldCtS = JSON
									.stringify(paramsHere.oldCheckTemplates[ct.key]);
							var ctS = JSON.stringify(ct);
							if (oldCtS != ctS) {
								rtn.update.push(ct);
							}
						}
					}
					for ( var key in paramsHere.oldCheckTemplates) {
						if (newCheckTemplateIds.indexOf(key) < 0) {
							rtn.del.push(key);
						}
					}
					return rtn;
				},
				onSwitchToIassister : function() {
					if (paramsHere.xformDesigner) {
					}
					this.nodes = initNodes();
				},
				delTemplate : function(idx) {
					var t = this.templates[idx];
					if (paramsHere.oldCheckTemplates.hasOwnProperty(t.key)) {
						paramsHere.oldCheckTemplates[t.key].handleType = "delete";
					}
					this.templates.splice(idx, 1);
				},
				chooseField : function(template, pm) {
					var self = this;
					console.log(paramsHere.modelName);
					var varInfo = paramsHere.xformDesigner ? paramsHere.xformDesigner.instance
							.getObj(true)
							: Formula_GetVarInfoByModelName(paramsHere.modelName);
					Formula_Dialog(template.key + pm.pKey + "_key",
							template.key + pm.pKey + "_label", varInfo, null,
							function(rtnData) {
								self.afterFieldChoosed({
									choosed : rtnData,
									pm : pm
								})
							});
				},
				afterFieldChoosed : function(rtn) {
					var cd = rtn.choosed.data[0];
					rtn.pm.fieldLabel = cd.name;
					rtn.pm.fieldKey = cd.id;
				},
				add : function() {
					var self = this;
					var treeParams = {
						action : function(rtnData) {
							self.afterItemChoosed({
								choosed : rtnData
							})
						},
						multi : true,
						dialogTitle : lang["msg.check.item.choose"],
						treeBean : "sysIassisterItemTreeBean&parentId=!{value}",
						dataBean : "sysIassisterItemDataBean&categoryId=!{value}&choosedItemIds="
								+ paramsHere.choosedItemIds,
						searchBean : "sysIassisterItemSearchBean&key=!{keyword}&choosedItemIds="
								+ paramsHere.choosedItemIds
					}
					selectionDialog.treeSelect(treeParams);
				},
				afterItemChoosed : function(rtn) {
					var self = this;
					if (rtn.choosed && rtn.choosed.data) {
						var choosedItems = rtn.choosed.data;
						var choosedEach = function(ci, idx) {
							// paramsHere.choosedItemIds += ci.fdId + ";";
							var templateData = {
								key : common.genID(),
								handleType : "add",
								label : ci.fdName,
								itemKey : ci.fdId,
								itemLabel : ci.fdName,
								paramsMapping : [],
								groups : [],
								groupwritable : false
							}
							var ruleInfo = JSON.parse(ci.ruleInfo);
							common.arrForEach(ruleInfo.params, function(cp) {
								var pm = {
									pKey : cp.key,
									pName : cp.label,
									pType : cp.type,
									pTypeLabel : cp.typeLabel,
									fieldKey : "",
									fieldLabel : ""
								}
								templateData.paramsMapping.push(pm);
							})
							self.templates.push(templateData);
						}
						common.arrForEach(choosedItems, choosedEach);
						this.groupMode = false;
					}
				},
				groupsChanged : function(groups) {
					var self = this;
					var needReplaceIdx = -1;
					var needReplaceKey = "";
					common.arrForEach(groups, function(gk, idx) {
						if (!self.mapGroups.hasOwnProperty(gk)) {
							var group = {
								key : common.genUUID(),
								label : gk,
								nodes : [],
								nodewritable : false
							}
							self.groups.push(group);
							needReplaceIdx = idx;
							needReplaceKey = group.key;
						}
					})
					setTimeout(function() {
						if (needReplaceIdx > -1) {
							groups.splice(needReplaceIdx, 1, needReplaceKey);
						}
						self.updateGroups();
					}, 60)
				},
				showGroups : function(groups) {
					var rtn = "";
					var self = this;
					common.arrForEach(groups, function(gk) {
						var gr = self.mapGroups[gk];
						rtn += gr ? (gr.label + "；") : "";
					})
					if (rtn) {
						rtn = rtn.substring(0, rtn.length - 1);
					}
					return rtn;
				},
				focusGroupEditor : function() {
					var self = this;
					setTimeout(function() {
						var groupEditor = self.$refs["groupEditor"];
						if (groupEditor) {
							groupEditor.focus();
						} else {
							self.focusGroupEditor();
						}
					}, 100);
				},
				editGroup : function(row) {
					row.groupwritable = true;
					this.curEditCol = {
						row : row,
						type : "groups"
					}
					this.focusGroupEditor();
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
				editNode : function(row) {
					row.nodewritable = true;
					this.curEditCol = {
						row : row,
						type : "nodes"
					}
				},
				delGroup : function(idx) {
					var self = this;
					dialog.confirm(lang["msg.delgroup.confirm"], function(
							confirm) {
						if (confirm) {
							var eachFunc = function(t) {
								var findCond = function(item, value) {
									return item == value;
								}
								var templateData = self.mapTemplates[t.key];
								common.arrDel(templateData.groups, findCond,
										self.groups[idx].key);
							}
							common.arrForEach(self.groups[idx].templates,
									eachFunc)
							self.groups.splice(idx, 1);
						}
					})
				},
				cellClick : function(row, col, cell, evt) {
					if ([ "groups", "nodes" ].indexOf(col.type) < 0) {
						this.curEditCol = null;
					}
					if (this.curEditCol
							&& (this.curEditCol.row.key != row.key || this.curEditCol.type != col.type)) {
						this.curEditCol = {
							row : row,
							type : col.type
						}
					}
				},
				updateGroups : function() {
					var self = this;
					var eachFunc = function(group, idx) {
						var newGTS = self.templates.filter(function(t) {
							return t.groups.indexOf(group.key) > -1;
						})
						if (group.templates && group.templates.length > 0) {
							var mapNewGTS = common.arrayToMap(newGTS, "key");
							var mapOldGTS = common.arrayToMap(group.templates,
									"key");
							for ( var tk in mapOldGTS) {
								if (!mapNewGTS.hasOwnProperty(tk)) {
									delete mapOldGTS[tk];
								}
							}
							for ( var tk in mapNewGTS) {
								if (mapOldGTS.hasOwnProperty(tk)) {
									mapOldGTS[tk].label = mapNewGTS[tk].label;
								} else {
									mapOldGTS[tk] = mapNewGTS[tk];
								}
							}
							newGTS = [];
							for ( var tk in mapOldGTS) {
								newGTS.push(mapOldGTS[tk]);
							}
						}
						self.$set(group, "templates", newGTS);
					}
					common.arrForEach(this.groups, eachFunc);
				},
				validateGroupLabel : function(rule, value, callback) {
					var labelCount = 0;
					common.arrForEach(this.groups, function(gp) {
						if (labelCount > 1) {
							return "break";
						} else if (gp.label == value) {
							labelCount++;
						}
					})
					if (labelCount > 1) {
						callback(lang["msg.group.label.repeat"]);
					}
				},
				validateCheckLabel : function(rule, value, callback) {
					if (value.length > 200) {
						callback(lang["msg.check.template.label.toolong"]);
					}
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