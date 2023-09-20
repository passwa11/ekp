define(function(require, exports, module) {
	//通用对话框
	var lang = require('lang!sys-ui');
	var $ = require("lui/jquery");
	var env = require("lui/util/env");
	var strutil = require('lui/util/str');
	var dialog = require("lui/dialog");
	
	function rtfield(idField, nameField) {
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
	
	var dialogSelectForNewFile = function(modelName, source ,params, urlPattern, winTitle, action, target){
		var dialogObj = dialog.build({
			config:{
				width: 800,
				height: 500,
				lock: true,
				cache: false,
				title : winTitle?winTitle:lang['ui.dialog.template'],
				content : {
					id : 'template_select_div',
					scroll : false,
					type : "iframe",
					url : '/sys/ui/js/category/common-template.jsp?_key=template_select',
					params:null
				}
			},
			callback : action
		});
		domain.register("template_select",function(){
			var iframe = dialogObj.element.find('iframe').get(0);
			if(!iframe.contentWindow._getSelectedData){
				return;
			}
			var rtnData = iframe.contentWindow._getSelectedData();
			if(rtnData==null) {
				return;
			}
			var rowData = domain.toJSON(rtnData.data);
			var tmpParams={};
			for(var key in rowData){
				var tmpKey = key;
				if(key.indexOf("fd")==0){
					tmpKey = key.substring(2);
					tmpKey = tmpKey.substring(0,1).toLowerCase() + tmpKey.substring(1);
				}
				tmpParams[tmpKey] = rowData[key];
			}
			if(urlPattern){
				var openUrl = strutil
					.variableResolver(urlPattern, tmpParams);
				if(target=="_blank"){
					window.open(env.fn.formatUrl(openUrl));
				}else{
					location.href = env.fn.formatUrl(openUrl);
				}
			}
			if(action){
				action(rowData);
			}
			dialogObj.hide();
		});
		window["__template_select_dataSource"] = function(){
			return strutil.variableResolver(source ,params);
		}
		window.parent["__template_select_dataSource"] = function(){
			return strutil.variableResolver(source ,params);
		}
		dialogObj.show();
	};
	var dialogSelect = function(modelName, mulSelect, source, params, idField, nameField, winTitle, action){
		var buttons = [ {
			name : lang['ui.dialog.button.ok'],
			value : true,
			focus : true,
			fn : function(value, dialog) {
				var iframe = dialog.element.find('iframe').get(0);
				if(!iframe.contentWindow._getSelectedData){
					return;
				}
				var rtnInfo = iframe.contentWindow._getSelectedData();
				if(rtnInfo==null) {
					return;
				}
				var datas = rtnInfo.data;
				var rtnDatas=[];
				var ids = [];
				var names=[];
				for(var i=0;i<datas.length;i++){
					var rowData = domain.toJSON(datas[i]);
					rtnDatas.push(rowData);
					ids.push($.trim(rowData[rtnInfo.idField]));
					names.push($.trim(rowData[rtnInfo.nameField]));
				}
				var fields = rtfield(idField, nameField);
				if(fields.idObj){
					fields.idObj.value = ids.join(";");
					$(fields.idObj).trigger('change');
				}
				if(fields.nameObj){
					fields.nameObj.value = names.join(";");
					$(fields.nameObj).trigger('change');
					fields.nameObj.focus();
				}
				if(action){
					action(rtnDatas);
				}
				dialog.hide(value);
			}
		} ];
		buttons.push({
			name : lang['ui.dialog.button.cancel'],
			value : false,
			styleClass : 'lui_toolbar_btn_gray',
			fn : function(value, dialog) {
				dialog.hide(value);
			}
		});
		var tempUrl = '/sys/ui/js/category/common-template.jsp?modelName=' + modelName + '&_key=dialog_' + idField;
		if(mulSelect==true){
			tempUrl += '&mulSelect=true';
		}else{
			tempUrl += '&mulSelect=false';
		}
		var dialogObj = dialog.build({
			config:{
				width: 800,
				height: 500,
				lock: true,
				cache: false,
				title : winTitle?winTitle:lang['ui.vars.select'],
				content : {
					id : idField + '_dialog_div',
					scroll : false,
					type : "iframe",
					url : tempUrl,
					params:null,
					buttons:buttons
				}
			}
		});
		domain.register('dialog_' + idField,function(){
			buttons[0].fn(null,dialogObj);
		});
		window['__dialog_' + idField + '_dataSource'] = function(){
			if(idField==null){
				return strutil.variableResolver(source ,params);
			}else{
				return {url:strutil.variableResolver(source ,params),init:document.getElementsByName(idField)[0].value};
			}
			//return strutil.variableResolver(source ,params);
		}
		dialogObj.show();
	};
	//对话框用于新建
	exports.dialogSelectForNewFile = dialogSelectForNewFile;
	//对话框用于选择
	exports.dialogSelect= dialogSelect;
	//简单分类新建
	exports.simpleCategoryForNewFile = dialog.simpleCategoryForNewFile;
	//简单分类选择
	exports.simpleCategory = dialog.simpleCategory;
	//全局分类新建
	exports.categoryForNewFile = dialog.categoryForNewFile;
	//全局分类选择
	exports.category = dialog.category;
});