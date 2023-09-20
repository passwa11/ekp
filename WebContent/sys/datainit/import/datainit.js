define(function(require, exports, module) {
	var layout = require('lui/view/layout');
	var env = require('lui/util/env');
	var $ = require('lui/jquery');
	var lang = require('lang!');
	var dataInitlang = require('lang!sys-datainit');

	var ___isDrawed = false;
	var getCfg = function() {

		return cfg = {
			click : '___datainitSubmit()',
			styleClass : 'com_export',
			text:dataInitlang['sysDatainitMain.data.export'],
			order:1,
			title : dataInitlang['sysDatainitMain.data.export']
		};
	};

	var surroundBtn = function(btn) {
		if (!LUI('top'))
			return;
		btn.startup();
		LUI('top').addButton(btn);
	};

	var getIsDrawed = function() {
		return ___isDrawed;
	};

	var setIsDrawed = function(drawed) {
		___isDrawed = drawed;
	};

	/**
	 * 判断该请求的数据是否支持导出
	 * @param param	参数：{'url':'目标请求的URL，通过该URL反向查询SpringMVC配置中的formType', 'formName':'表单中的formName，如：kmReviewMainForm'}
	 * @param callback 结果回调
	 * @returns {boolean}
	 */
	var isSupport = function (param, callback) {
		if (param && param.url) {
			$.post(env.fn.formatUrl('/sys/datainit/sys_datainit_main/sysDatainitMain.do?method=check'), $.param(param, true), function (data) {
				if (callback) {
					callback(data.state);
				} else {
					return data.state;
				}
			}, 'json');
		}
		if (callback) {
			callback(false);
		} else {
			return false;
		}
	};

	window.___datainitSubmit = function() {
		seajs.use(['lui/dialog', 'lui/jquery'], function(dialog, $) {
			var select = document.getElementsByName("List_Selected");
			var values = [];
			var selected;
			for (var i = 0; i < select.length; i++) {
				if (select[i].checked) {
					values.push(select[i].value);
					selected = true;
				}
			}

			var __url = location.pathname;
			var __cxt = Com_Parameter.ContextPath;
			if (__url.indexOf(__cxt) == 0)
				__url = __url.substring(__cxt.length - 1);
			if (!selected) {
				var __fdId = Com_GetUrlParameter(location.search, 'fdId');
				if (__fdId) {
					values.push(__fdId);
					selected = true;
				}
			}
			if (selected) {
				var loading = dialog.loading();
				var __listview;
				$(select).parents("[data-lui-type]").each(function() {
							var elem = $(this);
							var module = elem.attr('data-lui-type');
							var clazz = module.split("!")[1];
							if ('ListView' == clazz) {
								__listview = LUI(elem.attr('data-lui-cid'));
							}
						});
				if (__listview)
					__url = __listview.source.url;
				$.post(env.fn.formatUrl('/sys/datainit/sys_datainit_main/sysDatainitMain.do?method=export&url='
												+ encodeURIComponent(__url)),
								$.param({
									"List_Selected" : values
								}, true), function(data, textStatus, xhr) {
									loading.hide();
									dialog.result(data);
								}, 'json').error(function(data){
									loading.hide();
									dialog.result(data);
								});
			} else {
				dialog.alert(lang['page.noSelect']);
			}
		})
	};

	exports.surroundBtn = surroundBtn;
	exports.getCfg = getCfg;
	exports.getIsDrawed = getIsDrawed;
	exports.setIsDrawed = setIsDrawed;
	exports.isSupport = isSupport;
});