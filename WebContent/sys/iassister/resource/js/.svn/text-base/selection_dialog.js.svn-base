define(function(require, exports, module) {
	var $ = require("lui/jquery");
	var dialog = require("lui/dialog");
	var topic = require("lui/topic");
	var strutil = require('lui/util/str');

	var paramsHere = {

	}

	var rtField = function(idField, nameField) {
		var idObj, nameObj;
		if (idField != null) {
			if (typeof (idField) == "string")
				idObj = document.getElementsByName(idField)[0];
			else
				idObj = idField;
		}
		if (nameField != null) {
			if (typeof (nameField) == "string")
				nameObj = document.getElementsByName(nameField)[0];
			else
				nameObj = nameField;
		}
		return {
			idObj : idObj,
			nameObj : nameObj
		}
	}

	var handleOKFunc = function(value, dialogObj) {
		var iframe = dialogObj.element.find('iframe').get(0);
		if (!iframe.contentWindow._getSelectedData) {
			return;
		}
		var rtnInfo = iframe.contentWindow
				._getSelectedData(paramsHere.emptyMsg);
		if (rtnInfo == null) {
			return;
		}
		var datas = rtnInfo.data;
		var rtnDatas = [];
		var ids = [];
		var names = [];
		for (var i = 0; i < datas.length; i++) {
			var rowData = domain.toJSON(datas[i]);
			rtnDatas.push(rowData);
			ids.push($.trim(rowData[rtnInfo.idField]));
			names.push($.trim(rowData[rtnInfo.nameField]));
		}
		var fields = rtField(paramsHere.idField, paramsHere.nameField);
		if (fields.idObj) {
			fields.idObj.value = ids.join(";");
			$(fields.idObj).trigger('change');
		}
		if (fields.nameObj) {
			fields.nameObj.value = names.join(";");
			$(fields.nameObj).trigger('change');
			fields.nameObj.focus();
		}
		if (paramsHere.action) {
			paramsHere.action(rtnDatas);
		}
		dialogObj.hide(value);
	}

	var select = function(params) {
		var buttons = [];
		paramsHere = params;
		if (true == params.multi) {
			// 添加确定按钮，单选不需要
			buttons.push({
				name : langUI['ui.dialog.button.ok'],
				value : true,
				focus : true,
				fn : handleOKFunc,
				styleClass : "selection_dialog_btn_ok lui_toolbar_btn_def"
			})
		}
		if (!params.hasOwnProperty("showCancel") || params.showCancel == true) {
			buttons.push({
				name : langUI['ui.dialog.button.cancel'],
				value : false,
				styleClass : 'lui_toolbar_btn_gray',
				fn : function(value, dialog) {
					dialog.hide(value);
				}
			})
		}
		var dialogUrl = '/km/clause/resource/jsp/selection_dialog.jsp?modelName='
				+ params.modelName
				+ '&_key=dialog_'
				+ params.idKey
				+ '&props='
				+ (params.props ? params.props : '');
		if (params.multi == true) {
			dialogUrl += '&mulSelect=true';
		} else {
			dialogUrl += '&mulSelect=false';
		}
		var dialogObj = dialog.build({
			config : {
				width : 860,
				height : 520,
				lock : true,
				cache : false,
				title : params.dialogTitle || "",
				content : {
					id : params.idField + '_dialog_div',
					scroll : false,
					type : "iframe",
					url : dialogUrl,
					params : null,
					buttons : buttons
				}
			}
		})
		domain.register('dialog_' + params.idKey, function() {
			handleOKFunc(null, dialogObj);
		});
		window['__dialog_' + params.idKey + '_dataSource'] = function() {
			if (params.idField == null) {
				return strutil.variableResolver(params.source, params.params);
			} else {
				return {
					url : strutil
							.variableResolver(params.source, params.params),
					init : document.getElementsByName(params.idField)[0].value
				};
			}
		}
		dialogObj.show();
	}

	var treeSelect = function(params) {
		Dialog_TreeList(params.multi, params.idField, params.nameField, ';',
				params.treeBean, params.dialogTitle, params.dataBean,
				params.action, params.searchBean, null, false, true);
	}

	module.exports.treeSelect = treeSelect;
	module.exports.select = select;
})