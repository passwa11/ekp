define(function(require, exports, module) {
	var lang = require('lang!sys-iassister');
	var langDefault = require('lang!');
	var $ = require("lui/jquery");
	var dialog = require("lui/dialog");
	var topic = require("lui/topic");

	var paramsHere = {}

	var init = function(params) {
		initParams(params);
		subscribeEvents();
	}

	var initParams = function(params) {
		paramsHere = $.extend(paramsHere, params);
		paramsHere.actionUrl = "/sys/iassister/sys_iassister_item/sysIassisterItem.do";
	}

	var subscribeEvents = function() {
		topic.subscribe('successReloadPage', function() {
			refreshList();
		})
	}

	var refreshList = function() {
		topic.publish('list.refresh');
	}

	var add = function() {
		var addUrl = Com_SetUrlParameter(paramsHere.actionUrl, "method", "add");
		if (paramsHere.categoryId) {
			addUrl = Com_SetUrlParameter(addUrl, "i.docCategory",
					paramsHere.categoryId);
			Com_OpenWindow(paramsHere.ctxPath + addUrl, "_blank");
		} else {
			addUrl += "&i.docCategory=!{id}";
			dialog.categoryForNewFile(
					"com.landray.kmss.sys.iassister.model.SysIassisterItem",
					addUrl, false, "", null, null, "_blank", true, false);
		}
	}

	var batchDel = function() {
		var selectedIds = [];
		$("input[name='List_Selected']:checked").each(function() {
			selectedIds.push($(this).val());
		});
		if (selectedIds.length == 0) {
			dialog.alert(lang["msg.del.choose.item.empty"]);
			return;
		}
		var delUrl = paramsHere.ctxPath + paramsHere.actionUrl
				+ "?method=deleteall";
		dialog.confirm(lang["msg.del.confirm"], function(ok) {
			if (ok) {
				var del_load = dialog.loading();
				var param = {
					"List_Selected" : selectedIds
				}
				$.ajax({
					url : delUrl,
					data : $.param(param, true),
					dataType : 'json',
					type : 'POST',
					success : function(data) {
						if (del_load != null) {
							del_load.hide();
							topic.publish("list.refresh");
						}
						dialog.result(data);
					},
					error : function(req) {
						if (req.responseJSON) {
							var data = req.responseJSON;
							var failureHint = data.title;
							if (data.message && data.message.length > 0) {
								failureHint = data.message[0].msg;
							}
							dialog.failure(failureHint);
						} else {
							dialog.failure(langDefault["return.optFailure"]);
						}
						del_load.hide();
					}
				});
			}
		});
	}

	module.exports.init = init;
	module.exports.batchDel = batchDel;
	module.exports.add = add;
})