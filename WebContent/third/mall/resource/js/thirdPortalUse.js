/**
 * 使用模板
 */
define(function(require, exports, module) {

	var $ = require("lui/jquery");
	var dialog = require('lui/dialog');
	var lang = require('lang!third-mall');
	
	// 关闭页面
	closeDialog = function(closeWin) {
		if(window.parent && window.parent.$dialog) {
			window.parent.$dialog.hide();
		}
		if(closeWin) {
			setTimeout(function() {Com_CloseWindow();}, 1500);
		}
	}
	
	// 登录模板
	useLoginTpl = function(data, callback) {
		var closeWin =data.successClose;
		let useLoding = null;
		if(closeWin==undefined){
			closeWin =true;
		}
		if(!data.templateIsExists && data.successMessage) {
			// 成功
			dialog.success(lang["thirdMall.portal.success"]);
			if(callback) {
				callback(true);
			}
			closeDialog(closeWin);
		} else {
			// 失败
			if(data.isCriterionTemplate) {
				dialog.failure(lang["thirdMall.portal.login.failure"]);
				if(callback) {
					callback(false);
				}
				closeDialog();
			} else {
				// 自定义包有重复，是否替换
				dialog.confirm(lang["thirdMall.portal.confirm"], function(value) {
					if(value) {
						useLoding = dialog.loading();
						// 继续更新
						$.ajax({
							url: Com_Parameter.ContextPath + "sys/profile/sys_login_template/sysLoginTemplate.do?method=replaceTemplate",
							data: {"templateId": data.templateId,"folderName": data.folderName,"isDefault": data.isDefault},
							type: "POST",
							dataType: 'json',
							success: function (res) {
								useLoding.hide();
								if(res == '1') {
									dialog.success(lang["thirdMall.opt.success"]);
									closeDialog(closeWin);
								} else {
									dialog.failure(lang["thirdMall.opt.failure"]);
									closeDialog();
								}
								if(callback) {
									callback(true);
								}
							}
						});
					} else {
						if(callback) {
							callback(false);
						}
						// closeDialog();
					}
				});
			}
		}
	}
	
	// 使用主题
	useThemeTpl = function(data, callback) {
		var closeWin =data.successClose;
		let useLoding = null;


		if(closeWin ==undefined){
			closeWin =true;
		}
		if(!data.themeIsExists && data.successMessage) {
			// 成功
			dialog.success(lang["thirdMall.theme.success"]);
			if(callback) {
				callback(true);
			}
			closeDialog(closeWin);
		} else {
			// 自定义包有重复，是否替换
			dialog.confirm(lang["thirdMall.theme.confirm"], function(value) {
				if(value) {
					useLoding = dialog.loading();
					// 继续更新
					$.ajax({
						url: Com_Parameter.ContextPath + "sys/ui/sys_ui_extend/sysUiExtend.do?method=replaceExtend",
						data: {"extendId": data.extendId,"folderName": data.folderName},
						type: "POST",
						dataType: 'json',
						success: function (res) {
							useLoding.hide() ;
							if(res == '1') {
								dialog.success(lang["thirdMall.opt.success"]);
								closeDialog(closeWin);
							} else {
								dialog.failure(lang["thirdMall.opt.failure"]);
								closeDialog();
							}
							if(callback) {
								callback(true);
							}
						}
					});
				} else {
					if(callback) {
						callback(false);
					}
					// closeDialog();
				}
			});
		}
	}
	// 使用部件
	useComponentTpl = function(data, callback) {
		var closeWin =data.successClose;
		let useLoding = null;

		if(closeWin ==undefined){
			closeWin =true;
		}

		if(!data.themeIsExists && data.successMessage) {
			// 成功
			dialog.success(lang["thirdMall.component.success"]);
			closeWindow(closeWin);
		} else {
			// 自定义包有重复，是否替换
			dialog.confirm(lang["thirdMall.component.confirm"], function(value) {
				if(value) {
					useLoding = dialog.loading();
					// 继续更新
					$.ajax({
						url: Com_Parameter.ContextPath + "sys/ui/sys_ui_component/sysUiComponent.do?method=replaceExtend",
						data: {"extendId": data.extendId,"folderName": data.folderName},
						type: "POST",
						dataType: 'json',
						success: function (res) {
							useLoding.hide();
							if(res == '1') {
								dialog.success(lang["thirdMall.opt.success"]);
								closeWindow(closeWin);
							} else {
								dialog.failure(lang["thirdMall.opt.failure"]);
								closeWindow();
							}
							if(callback) {
								callback(true);
							}
						}
					});
				} else {
					if(callback) {
						callback(false);
					}
					// closeDialog();
				}
			});
		}
	}
	// 关闭部件
	closeWindow = function(closeWin) {
		if(window.parent && window.parent.$dialog) {
			window.$dialog.hide();
		}
		if(closeWin) {
			setTimeout(function() {
				Com_CloseWindow();}, 1500);
		}
	}

	//使用
	exports.useTpl = function(fdId, type, callback) {
		if(fdId && type) {
			$.ajax({
				url: Com_Parameter.ContextPath + "third/mall/thirdMallPortal.do?&method=downloadTmpl&fdId=" + fdId + "&type=" + type,
				type: "POST",
				dataType: 'json',
				success: function (data) {
					if(type == 'login') {
						// 使用登录页
						useLoginTpl(data, callback);
					} else if(type == 'theme') {
						// 使用主题包
						useThemeTpl(data, callback);
					}else{
						//使用部件包
						useComponentTpl(data, callback);
					}
				}
			});
		} else {
			dialog.failure("未知的操作！");
		}
	}
	//使用
	exports.useTplNew = function(fdId, type, callback,successClose) {
		if(fdId && type) {
			$.ajax({
				url: Com_Parameter.ContextPath + "third/mall/thirdMallPortal.do?&method=downloadTmpl&fdId=" + fdId + "&type=" + type,
				type: "POST",
				dataType: 'json',
				success: function (data) {
					data.successClose =successClose;
					if(type == 'login') {
						// 使用登录页
						useLoginTpl(data, callback);
					} else if(type == 'theme') {
						// 使用主题包
						useThemeTpl(data, callback);
					}else{
						//使用部件包
						useComponentTpl(data, callback);
					}
				}
			});
		} else {
			dialog.failure("未知的操作！");
		}
	}
})