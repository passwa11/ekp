define(function(require, exports, module) {
	var lang = require('lang!sys-ui');
	require("theme!dialog");
	var $ = require("lui/jquery");
	var layout = require("lui/view/layout");
	var base = require("lui/base");
	var env = require("lui/util/env");
	var overlay = require("lui/overlay");
	var actor = require("lui/dialog/actor");
	var trigger = require("lui/dialog/trigger");
	var content = require("lui/dialog/content");
	var strutil = require('lui/util/str');
	var dragdrop = require('lui/dragdrop');
	var upperFirst = strutil.upperFirst;

	var top = Com_Parameter.top || window.top;
	// 父窗口弹框
	var topDialog;
	var topBase;
	var win = top;
	try {
		typeof (win['seajs']);// 跨域错误判断
	} catch (e) {
		win = window;
	}
	if (win != window && typeof (win['seajs']) != 'undefined') {
		win['seajs'].use([ 'lui/dialog', 'lui/base' ], function(dialog, base) {
			topDialog = dialog.Dialog;
			topBase = base;
		});
	}

	// 弹出框
	var Dialog = base.Component
			.extend({

				initialize : function($super, config) {
					$super(config);
					this.startup();
				},
				initProps : function(_config) {
					this.config = _config.config; 
					// 是否缓存
					this.cache = this.config.cache || false;
					// 是否锁屏
					this.lock = this.config.lock ? true
							: (this.config.lock == false ? false : true);
					this.width = this.config.width || $(window).width() / 2;
					this._height = this.config.height || $(window).height() / 2;
					this.height = this._height > $(window).height() ? $(window)
							.height() : this._height;
					this.top = this.config.top;
					this.left = this.config.left;
					this.titleConfig = this.config.title;
					this.elem = this.config.elem;
					this.close = this.config.close != null ? this.config.close
							: true;
					
					this.layoutConfig = this.config.layout
							|| 'sys/ui/extend/dialog/dialog.tmpl';

					var mainClass = 'lui_dialog_main';
					this.mainClass = this.config.mainClass ? this.config.mainClass : mainClass;
					//钉钉套件样式
					this.mainClass = Com_Parameter.dingXForm=='true' ? this.mainClass + ' lui_dialog_main_dingsuit':this.mainClass
					this.element.addClass(this.mainClass);
					
					var maskClass = '';
					if (this.layoutConfig == 'sys/ui/extend/dialog/dialog.tmpl') {
						maskClass = 'lui_dialog_mask';
					}
					
					
					this.maskClass = this.config.maskClass ? this.config.maskClass
							: maskClass;
					
					this.triggerConfig = $.extend({
						dialog : this
					}, _config.trigger);
					this.triggerType = this.triggerConfig
							&& this.triggerConfig.type ? upperFirst(this.triggerConfig.type)
							: "Default";

					this.actorConfig = $.extend({
						'dialog' : this,
						'lock' : this.lock,
						'cache' : this.cache,
						'elem' : this.elem,
						maskClass : this.maskClass
					}, _config.actor);
					this.actorType = this.actorConfig && this.actorConfig.type ? upperFirst(this.actorConfig.type)
							: 'Default';

					this.contentConfig = this.config.content;
					this.contentType = upperFirst(this.contentConfig.type);

					this.positionConfig = _config.position;

					this.callback = _config.callback || new Function();
				},

				getPosition : function() { 
					if (this.top || this.left) {
						this.position = new overlay.CustomPosition({
							top : this.top,
							left : this.left
						});
					} else if (this.positionConfig) {
						this.position = new overlay[upperFirst(this.positionConfig.type)](
								{
									elem : this.elem
								});
					} else {
						this.position = new overlay.DefaultPosition()
					}
					return this.position;
				},

				startup : function() {
					this.addChild();
					this.overlay = new overlay.Overlay({
						"trigger" : new trigger[this.triggerType](
								this.triggerConfig),
						"position" : this.getPosition(),
						"content" : this.content,
						"actor" : new actor[this.actorType](this.actorConfig)
					});
					
				},

				addChild : function() {
					if (this.layoutConfig) {
						this.layout = new layout.Template({
							datas : [],
							src : (require.resolve(this.layoutConfig + '#')),
							parent : this
						});
						this.children.push(this.layout);
						this.layout.startup();
					}
					if (this.contentConfig) {
						this.content = new content[this.contentType]($.extend({
							parent : this
						}, this.contentConfig));
					}
				},

				drawFrame : function() {
					if (this.isDrawed) {
						return;
					}
					var self = this;
					this.layout.get(this, function(obj) {
						self.layoutDone(obj);
						self.isDrawed = true;
					});
				},

				layoutDone : function(obj) {
					var self = this;
					this.element.hide();
					if($('.lui_dialog_main').length){
						$('.lui_dialog_main').eq($('.lui_dialog_main').length-1).after(this.element);

					}else {
						$(document.body).prepend(this.element);
					}
					this.element.css({
						'width' : this.width
					});

					this.frame = $(obj);
					// dialog头部
					this.head = this.frame
							.find('[data-lui-mark="dialog.nav.head"]');

					if (this.head.length > 0) {
						this.title = this.head
								.find("[data-lui-mark='dialog.nav.title']");
						var title = this.titleConfig;
						if(typeof title === 'object')
							title = title.text();
						title = $.trim( env.fn.clearHtml(title).replace(" ","") );
						this.title.attr("title",title);
						this.title.append(this.titleConfig);
					}
					this.element.append(this.frame);

					this.frame.find('[data-lui-mark="dialog.content.frame"]')
							.append(this.content.element);

					// 关闭事件
					if (this.close) {
						var closeNode = this.element
								.find('[data-lui-mark="dialog.nav.close"]');
						closeNode.click(function() {
							self.hide(null);
						});
						// 防止在页面有控件的情况下关闭不了对话框的问题
						closeNode.mousedown(function(evt) {
							evt.stopPropagation();
						});
					} else {
						this.element.find('[data-lui-mark="dialog.nav.close"]')
								.remove();
					}
					
					// 进度条
					this.setProgressText();
					this.setProgress();

					this.content.drawFrame();
					this.emit('layoutDone');
				},
				
				// 设置进度条文本
				setProgressText : function(text) {
					text = text || this.titleConfig || lang['ui.dialog.progress.text'];
					this.element.find('.lui_dialog_progress_title').html(text);
				},
				// 设置进度值（百分比）。只有1个参数时，显示百分比；有2个参数时，需要计算百分比
				// 参数isPercent：是否按百分比显示，默认true。
				setProgress : function(num, total, isPercent) {
					if(isPercent == undefined) {
						isPercent = true;
					}
					var progress = 0;
					var s_progress = '';
					if (arguments.length == 1) {
						progress = num;
					} else if (arguments.length > 1) {
						if(total == 0) {
							progress = 0;
						} else {
							progress = Math.round(num * 100 / total);
						}
						if(isPercent) {
							s_progress = progress + "%";
						} else {
							s_progress = num + "/" + total;
						}
					}
					if($('.lui_dialog_progress_bar').length>0){
					this.element.find('.lui_dialog_progress_bar').css({
						"width" : progress + "%"
					});
					this.element.find('.lui_dialog_progress_bar em').html(s_progress);
					
					// 当进度走到100时，自动关闭
					if(this.close == true && progress == 100) {
						this.hide();
					}
					}
				},

				callback : function(value, dialog, ctx) {
					this.callback.call(ctx || window, value, dialog);
				},

				show : function() {
					try {
						if (navigator.userAgent.indexOf("Chrome") >= 0) {
							// #114532 iwebPDF2018控件的优先级过高，会将弹出框和后面的按钮遮挡住value=1显示 value=0 隐藏
							var pdfFrame = document.getElementById('pdfFrame');
							if(pdfFrame != null){
								pdfFrame.contentWindow.document.getElementById('JGWebPdf_mainonline').HidePlugin(0);
							}
							$("object[id*='surread']").each(function(i,_obj){
								if(_obj.value == "1"){
									_obj.HidePlugin(0);
								}
							});
							if (null != jgBigVersionParam && jgBigVersionParam == "2015") {
								$("object[id*='JGWebOffice_']").each(function(i,_obj){
									if(_obj.value == "0"){
										_obj.value = "2";
									}else{
										_obj.HidePlugin(0);
									}
								});	
							}
						}
					} catch (e) {}					
					this.drawFrame();
					this.overlay.show();
					return this;
				},

				hide : function(value) {
					try {
						if (navigator.userAgent.indexOf("Chrome") >= 0) {
							// #114532 iwebPDF2018控件的优先级过高，会将弹出框和后面的按钮遮挡住value=1显示 value=0 隐藏
							var pdfFrame = document.getElementById('pdfFrame');
							if(pdfFrame != null){
								pdfFrame.contentWindow.document.getElementById('JGWebPdf_mainonline').HidePlugin(1);
							}
							var JGFlag = false;
							var surFlag=false;
							var JGSurreadLen =  $("object[id*='surread']").length;
							//判断是否使用超阅，没有则走原本的逻辑
							if(JGSurreadLen>0){
								JGFlag = true;
								surFlag=true;
							}else{
								if(null !=jgBigVersionParam && jgBigVersionParam == "2015"){
									JGFlag = true;
								}
							}
							
							if (JGFlag) {
								//修复传阅机制弹框会被金格控件挡住 #102483
								if($(".lui_dialog_main").length&&$('.lui_dialog_main').length>1){
									var JGWebOfficeArr= $('.lui_dialog_main').find("object[id*='JGWebOffice_']");
									if(JGWebOfficeArr!=undefined&&JGWebOfficeArr.length>0){
										$("object[id*='JGWebOffice_']").each(function(i,_obj){
											if(_obj.value == "2"){
												_obj.value = "0"
											}else{
												_obj.HidePlugin(1);
											}
										});
									}
									var JGSurreadArr= $('.lui_dialog_main').find("object[id*='surread']");
									if(JGSurreadArr!=undefined&&JGSurreadArr.length>0){
										$("object[id*='surread']").each(function(i,_obj){
											if(_obj.value == "1"){
												_obj.HidePlugin(1);
											}
										});		
									}
								}else if($("input[type=hidden][name=fdNeedContent]") && $("input[type=hidden][name=fdNeedContent]").val()==0){
									//#111732 切换页签后选择地址本不应该触发金格的显示隐藏，这里维持原状
								}else if($(".lui_tabpanel_multiCollapse_selected") && $(".lui_tabpanel_multiCollapse_selected").length > 0){
									var item = $(".lui_tabpanel_multiCollapse_selected").find(".lui_tabpanel_view_navs_item_l")[2];
									if(JGFlag&&surFlag){
										$("object[id*='surread']").each(function(i,_obj){
											if(_obj.value == "1"){
												_obj.HidePlugin(1);
											}
										});	
									} else if(item && item.hasClass("lui_tabpanel_view_navs_item_selected")){
										if(_obj.value == "2"){
											_obj.value = "0"
										}else{
											_obj.HidePlugin(1);
										}
									}else{
										if ('com.landray.kmss.km.agreement.model.KmAgreementApply' == jg_attachmentObject_mainOnline.fdModelName) {
											//合同模块，左右分屏模式下，页签切换后，调用对话框时对金格控件的处理
											var tabItem = $(".lui_tabpanel_sucktop_navs_r").find(".lui_tabpanel_sucktop_navs_item_l");
											if (tabItem && tabItem.length >= 2) {
												var mainOnlineItem = tabItem[tabItem.length-2];
												if (mainOnlineItem && $(mainOnlineItem).hasClass("lui_tabpanel_sucktop_navs_item_selected")){
													//页签切换后，调用对话框，关闭对话框，则显示金格控件
													$("object[id*='JGWebOffice_']").each(function(i,_obj){
														if(_obj.value == "2"){
															_obj.value = "0";
														}else{
															_obj.HidePlugin(1);
														}
													});
													$("object[id*='surread']").each(function(i,_obj){
														if(_obj.value == "2"){
															_obj.value = "0";
														}else{
															_obj.HidePlugin(1);
														}
													});
												}
											}
										}
									}
								}else{
									$("object[id*='JGWebOffice_']").each(function(i,_obj){
										if(_obj.value == "2"){
											_obj.value = "0"
										} 
										else if(_obj.getAttribute("value") != undefined
												&& _obj.getAttribute("value") == "0"){
											//#129703 【日常缺陷】【新闻管理】上传js附件提示“非法格式”，点击确定后弹出了金格框
										}
										else{
											_obj.HidePlugin(1);
										}
									});
									$("object[id*='surread']").each(function(i,_obj){
										if(_obj.value == "2"){
											_obj.value = "0"
										}
										else if(_obj.getAttribute("value") != undefined
												&& _obj.getAttribute("value") == "0"){
											//#129703 【日常缺陷】【新闻管理】上传js附件提示“非法格式”，点击确定后弹出了金格框
										}
										else{
											_obj.HidePlugin(1);
										}
									});
								}
							}
						}
					} catch (e) {}	
					var self = this;
					// ie9下在异步回调函数中调用hide函数jquery对象被提前销毁报错
					setTimeout(function() {						
						self.content.hide();
						self.overlay.hide();
						self.callback(value, self);
						
					}, 1);
				}
			});
	

	function build(_config) {
		_config.config.cache = _config.id ? true && _config.config.cache
				: false;
		//ie中此处top为undefined??
		//top = (top ? top : window);
		if(top === undefined){
			top = window;
		}
		// 控制弹出层对应window对象--loading需要使用

		var win = top;
		if (!_config.config.win) {
			// 如果自定义了遮罩区域，则判断区域节点的上下文
			if (_config.config && _config.config.elem) {
				var elem = _config.config.elem;
				if (elem.context == window.document) {
					win = window;
				}
			}
		} else {
			win = _config.config.win;
		}
		
		if (win == window || !topDialog) {
			topDialog = Dialog;
			topBase = base;
		}
		try {
			typeof (win['seajs']);// 跨域错误判断
		} catch (e) {
			win = window;
		}
		if (top != window && typeof (win['seajs']) != 'undefined') {
			win['seajs'].use([ 'lui/dialog', 'lui/base' ], function(dialog,
					base) {
				topDialog = dialog.Dialog;
				topBase = base;
			});
		}
		if (_config.config.cache) {
			var dialog = topBase.byId(_config.id) ? topBase.byId(_config.id)
					: new topDialog(_config);
		} else {
			dialog = new topDialog(_config);
		}
		return dialog;
	}

	function dialogAlert(html, callback, iconType, buttons, autoClose,elem,slide) {
		//以object形式传参
		var title = lang['ui.dialog.operation.title'];
		if (html != null && !Object.isString(html)) {
			var _cfg = html;
			html = _cfg.html;
			callback = _cfg.callback;
			iconType = _cfg.iconType;
			buttons = _cfg.buttons;
			autoClose = _cfg.autoClose;
			title = _cfg.title;
		}
		var data = {
			config : {
				width : 436,
				lock : true,
				cahce : false,
				title : title,
				content : {
					type : "common",
					html : html,
					iconType : iconType || 'warn',// warn,question,success,failure
					buttons : buttons && buttons.length > 0 ? buttons : [ {
						name : lang['ui.dialog.button.ok'],
						value : true,
						focus : true,
						fn : function(value, dialog) {
							dialog.hide(value);
						}
					} ]
				}
			},
			callback : callback
		}
		if(elem){
			data.config.elem = elem;
		}
		// 是否自动关闭
		if (autoClose) {
			if(slide){
				data['actor'] = {
						type : "Slide"
					};
			}			
			data['trigger'] = {
				type : "AutoClose",
				timeout : 3
			};
		}
		return build(data).show();
	}

	/**
	 * config包括width.height和buttons
	 */
	function dialogIframe(url, title, _callback, _config) {
		// 兼容数据权限管理
		if (window.appendDatamngToken) {
			url = window.appendDatamngToken(url);
		}
		var data = {
			config : {
				lock : true,
				cache : false,
				close : _config.close,
				width : _config.width,
				height : _config.height,
				top : _config.top,
				left : _config.left,
				title : title || '',
				win : _config.topWin ? _config.topWin : top,
				opener : window,		
				content : {
					id : 'dialog_iframe',
					scroll : true,
					type : "iframe",
					url : url,
					buttons : _config.buttons || [],
					params : _config.params || ''
				}
			},
			callback : _callback || new Function()
		};

		var dia = build(data);
		dia.on('layoutDone', function() {
			new dragdrop.Draggable({
				handle : dia.head,
				element : dia.element,
				isDrop : false,
				isClone : false
			});
		});
		dia.show();
		return dia;
	}

	function dialogFailure(html, elem, callback, iconType, buttons, _config) {
		_config = _config || {};
		var data = {
			config : {
				width : 'auto',
				lock : true,
				cache : false,
				elem : elem,
				layout : 'sys/ui/extend/dialog/dialog_tip.tmpl',
				content : {
					type : "tip",
					html : html,
					iconType : iconType || 'failure',// warn,question,success,failure
					buttons : buttons
				}
			},
			callback : callback,
			actor : {
				type : "Slide"
			},
			trigger : {
				type : "AutoClose",
				timeout : _config.autoCloseTimeout || 2
			},
			position : {
				type : 'ElementPosition'
			}
		}
		return build(data).show();
	}

	function dialogSuccess(html, elem, callback, iconType, buttons, _config) {
		_config = _config || {};
		var data = {
			config : {
				width : 'auto',
				lock : true,
				cache : false,
				elem : elem,
				layout : 'sys/ui/extend/dialog/dialog_tip.tmpl',
				win : _config.topWin,
				content : {
					type : "tip",
					html : html,
					iconType : iconType || 'success',// warn,question,success,failure
					buttons : buttons
				}
			},
			callback : callback,
			actor : {
				type : "Slide"
			},
			trigger : {
				type : "AutoClose",
				timeout : _config.autoCloseTimeout || 2
			},
			position : {
				type : 'ElementPosition'
			}
		};
		return build(data).show();
	}

	function dialogConfirm(html, callback, iconType, buttons, win) {
		//以object形式传参
		var title = lang['ui.dialog.operation.title'];
		var width = 436;
		if (html != null && !Object.isString(html)) {
			var _cfg = html;
			html = _cfg.html;
			callback = _cfg.callback;
			iconType = _cfg.iconType;
			buttons = _cfg.buttons;
			win = _cfg.win;
			title = _cfg.title;
			width=_cfg.width;
		}
		var data = {
			config : {
				width : width,
				lock : true,
				cahce : false,
				title : title,
				win : win,
				content : {
					type : "common",
					html : html,
					iconType : iconType || 'question',// warn,question,success,failure
					buttons : buttons && buttons.length > 0 ? buttons : [ {
						name : lang['ui.dialog.button.ok'],
						value : true,
						focus : true,
						fn : function(value, dialog) {
							dialog.hide(value);
						}
					}, {
						name : lang['ui.dialog.button.cancel'],
						value : false,
						styleClass : 'lui_toolbar_btn_gray',
						fn : function(value, dialog) {
							dialog.hide(value);
						}
					} ]
				}
			},
			callback : callback
		}
		return build(data).show();
	}

	function dialogLoading(html, elem, win, zIndex) {
		var data = {
			config : {
				width : 200,
				lock : true,
				cahce : false,
				elem : elem,
				win : win || window,
				layout : 'sys/ui/extend/dialog/dialog_loading.tmpl',
				content : {
					type : "loading",
					html : html
				}
			},
			actor : {
				type : "Loading",
				zIndex : zIndex
			},
			position : {
				type : 'ElementPosition'
			}
		}
		return build(data).show();
	}
	
	function dialogProgress(close, html, elem, win, zIndex) {
		var data = {
			config : {
				width : 300,
				height : 100,
				close : close,
				lock : true,
				cahce : false,
				elem : elem,
				win : win || window,
				layout : 'sys/ui/extend/dialog/dialog_progress.tmpl',
				content : {
					type : "loading",
					html : html
				}
			},
			actor : {
				type : "Loading",
				zIndex : zIndex
			},
			position : {
				type : 'ElementPosition'
			}
		}
		return build(data).show();
	}

	function resolveUrl(url, urlParam) {
		if (url.indexOf('?') > 0) {
			url += "&" + urlParam;
		} else {
			url += "?" + urlParam;
		}
		return url;
	}

	function __paramByKey(url, param) {
		var re = new RegExp();
		re.compile("[\\?&]" + param + "=([^&]*)", "i");
		var arr = re.exec(url);
		if (arr == null)
			return null;
		else
			return decodeURIComponent(arr[1]);
	}

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

	function dialogSimpleCategory(modelName, idField, nameField, mulSelect,
			action, winTitle, canClose, ___urlParam, notNull, exceptValue) {
		var authType = null, noFavorite = null, url = null,alertText=null;
		if (modelName != null && !Object.isString(modelName)) {
			var _cfg = modelName;
			modelName = _cfg.modelName;
			idField = _cfg.idField;
			nameField = _cfg.nameField;
			mulSelect = _cfg.mulSelect;
			action = _cfg.action;
			winTitle = _cfg.winTitle;
			canClose = _cfg.canClose;
			___urlParam = _cfg.___urlParam;
			authType = _cfg.authType;
			noFavorite = _cfg.noFavorite;
			notNull = _cfg.notNull;
			url = _cfg.url;
			alertText = _cfg.alertText;
			exceptValue = _cfg.exceptValue;
		}
		// 如果传入的authType=0，这里会认为是“否”，而强制使用authType=2
		if(authType == null || authType == undefined) {
			authType = 2;
		}
		var size = getSizeForNewFile()
		var w = size.width, h = size.height + 30;
		if (mulSelect) {
			h += 30;
		}
		url = url ? url : '/sys/ui/js/category/simple-category.jsp';
		canClose = canClose == false ? false : true;
		mulSelect = mulSelect == true ? true : false;
		notNull = notNull == false ? false : true;
		if (modelName) {
			url = resolveUrl(url, "modelName=" + modelName);
		}
		if (noFavorite) {
			url = resolveUrl(url, "noFavorite=" + noFavorite);
		}
		if (authType != null) {
			url = resolveUrl(url, "authType=" + authType);
		}
		url = resolveUrl(url, "mulSelect=" + mulSelect);

		var fields = rtfield(idField, nameField);

		var idObj = fields.idObj;
		if (idObj && idObj.value)
			url = resolveUrl(url, "currId=" + idObj.value);

		var nameObj = fields.nameObj;

		var buttons = [ {
			name : lang['ui.dialog.button.ok'],
			value : true,
			focus : true,
			fn : function(value, dialog) {
				var params = {};
				var iframe = dialog.element.find('iframe').get(0);
				var urlParams = iframe.contentWindow.urlParams;
				if ((!urlParams || urlParams.length == 0)) {
					if (notNull) {
						if(typeof(alertText)!='undefined' && alertText != null){
							dialogAlert(alertText);
						} else {
							dialogAlert(lang['ui.dialog.category.message']);
						}
						return;
					} else
						urlParams = [ '', '' ];
				}
				params = {
					id : urlParams[0],
					name : urlParams[1]
				};
				if (idObj)
					idObj.value = urlParams[0];
				if (nameObj) {
					nameObj.value = urlParams[1];
					// 触发校验事件
					if (nameObj.type != 'hidden') {
						nameObj.focus();
						nameObj.blur();
					}
				}
				dialog.hide(params);
			}
		} ];
		if (canClose)
			buttons.push({
				name : lang['ui.dialog.button.cancel'],
				value : false,
				styleClass : 'lui_toolbar_btn_gray',
				fn : function(value, dialog) {
					dialog.hide(value);
				}
			});
		if (!notNull)
			buttons.push({
				name : lang['ui.dialog.button.cancelSelect'],
				value : false,
				styleClass : 'lui_toolbar_btn_gray',
				fn : function(value, dialog) {
					var params = {};
					params = {
						id : '',
						name : ''
					};
					if (idObj)
						idObj.value = '';
					if (nameObj) {
						nameObj.value = '';
					}
					dialog.hide(params);
				}
			});
		
		var data = {
			config : {
				width : w,
				height : h,
				lock : true,
				cache : false,
				title : winTitle || lang['ui.dialog.category'],
				scroll : true,
				close : canClose,
				content : {
					id : 'dialog_category',
					scroll : true,
					type : "iframe",
					url : url,
					params : ___urlParam,
					buttons : buttons,
					extendCfg : { "exceptValue"  : exceptValue }
				}
			},
			callback : action
		}
		var dia = build(data).show();
		dia.on('layoutDone', function() {
			new dragdrop.Draggable({
				handle : dia.head,
				element : dia.element,
				isDrop : false,
				isClone : false
			});
		});
		
		// 防止点击弹出框后，鼠标闪烁
		DialogFunc_BlurFieldObj([idObj, nameObj]);
		return dia;
	}

	// 模块名、点击确定跳转路径、是否多选、回调、窗口标题、当前分类id、打开新窗口^、是否允许关闭、扩展字段（字面量）
	function dialogSimpleCategoryForNewFile(modelName, urlParam, mulSelect,
			action, winTitle, idVal, target, canClose, ___urlParam,notNull) {
		var authType = null;
		if (modelName != null && !Object.isString(modelName)) {
			var _cfg = modelName;
			modelName = _cfg.modelName;
			mulSelect = _cfg.mulSelect;
			action = _cfg.action;
			winTitle = _cfg.winTitle;
			canClose = _cfg.canClose;
			___urlParam = _cfg.___urlParam;
			authType = _cfg.authType;
			urlParam = _cfg.urlParam;
			__url = _cfg.url;
            notNull = _cfg.notNull;
		}
		// 如果传入的authType=0，这里会认为是“否”，而强制使用authType=2
		if(authType == null || authType == undefined) {
			authType = 2;
		}

		// 请求数据源
		var sourceurl = '/sys/simplecategory/criteria/sysSimpleCategoryCriteria.do?method=select&type=02';

		var url = typeof __url == "undefined" || __url == null ? '/sys/ui/js/category/simple-category.jsp'
				: __url;

		sourceurl = resolveUrl(sourceurl, "authType=" + authType);

		if (modelName) {
			sourceurl = resolveUrl(sourceurl, "modelName=" + modelName);
			url = resolveUrl(url, "modelName=" + modelName);
		}

		mulSelect = mulSelect == true ? true : false;

		function openDialog() {
			
			if (idVal) {
				url = resolveUrl(url, "currId=" + idVal);
			}
			canClose = canClose == false ? false : true;

			url = resolveUrl(url, "mulSelect=" + mulSelect);
			url = resolveUrl(url, "authType=" + authType);
			var size = getSizeForNewFile()
			var w = size.width, h = size.height;
			if (mulSelect) {
				h += 60;
			}
			if (window.console)
				console.log('dialogSimpleCategoryForNewFile:url=' + url);
			var buttons = [ {
				name : lang['ui.dialog.button.ok'],
				value : true,
				focus : true,
				fn : function(value, dialog) {
					var params = {};
					var iframe = dialog.element.find('iframe').get(0);
					var urlParams = iframe.contentWindow.urlParams;
					if (!urlParams || urlParams.length == 0) {
                        if (notNull==true || typeof notNull === "undefined")  {
                            dialogAlert(lang['ui.dialog.category.message'], '', '',
                                '', true,window);
                            return;
                        } else
                            urlParams = [ '', '' ];
					}
					params = {
						id : urlParams[0],
						name : encodeURIComponent(urlParams[1])
					}
					if (urlParam) {

						var openUrl = strutil
								.variableResolver(urlParam, params);
						window.open(env.fn.formatUrl(openUrl),
								target ? target : '_blank');
					}
					dialog.hide(params);
				}
			} ];
			if (canClose)
				buttons.push({
					name : lang['ui.dialog.button.cancel'],
					value : false,
					styleClass : 'lui_toolbar_btn_gray',
					fn : function(value, dialog) {
						dialog.hide(value);
					}
				});
			var data = {
				config : {
					width : w,
					height : h,
					lock : true,
					cache : false,
					title : winTitle || lang['ui.dialog.category'],
					scroll : true,
					close : canClose,
					content : {
						id : 'dialog_category',
						scroll : true,
						type : "iframe",
						url : url,
						params : ___urlParam,
						buttons : buttons
					}
				},
				callback : action
			}
			var dia = build(data).show();
			dia.on('layoutDone', function() {
				new dragdrop.Draggable({
					handle : dia.head,
					element : dia.element,
					isDrop : false,
					isClone : false
				});
			});
			return dia;
		}

		if (idVal && !mulSelect && !___urlParam && urlParam && target!='_self') {
			sourceurl = resolveUrl(sourceurl, "currId=" + idVal);
			var flag = true;
			$.ajax({
				url : env.fn.formatUrl(sourceurl),
				dataType : 'json',
				async : false,
				success : function(data, textStatus, jqXHR) {

					outer: for (var j = 0; j < data.length; j++) {
						for (var i = 0; i < data[j].length; i++) {
							var val = data[j][i];
							if (val.value == idVal) {
								if (!val.nodeType
										|| val.nodeType == 'template') {
									flag = false;
								}
								break outer;
							}
						}
					}
				}
			});
			if (flag) {
				openDialog();
				return;
			}
			var _openUrl = strutil.variableResolver(urlParam, {
				id : idVal
			});
			window.open(env.fn.formatUrl(_openUrl), '_blank');
		} else
			return openDialog();
	}

	function dialogCategory(modelName, idField, nameField, mulSelect, action,
			winTitle, canClose, isShowTemp, areaId, notNull,tempKey,___urlParam) {
		var authType = null, noFavorite = null;
		if (modelName != null && !Object.isString(modelName)) {
			var _cfg = modelName;
			modelName = _cfg.modelName;
			idField = _cfg.idField;
			nameField = _cfg.nameField;
			mulSelect = _cfg.mulSelect;
			action = _cfg.action;
			winTitle = _cfg.winTitle;
			canClose = _cfg.canClose;
			isShowTemp = _cfg.isShowTemp;
			areaId = _cfg.areaId;
			authType = _cfg.authType;
			noFavorite = _cfg.noFavorite;
			notNull = _cfg.notNull;
			tempKey = _cfg.tempKey;
			___urlParam=_cfg.___urlParam;
		}
		var size = getSizeForNewFile()
		var w = size.width, h = size.height;
		var url = '/sys/ui/js/category/category.jsp';
		canClose = canClose == false ? false : true;
		mulSelect = mulSelect == true ? true : false;
		isShowTemp = isShowTemp == false ? 0 : 1;
		notNull = notNull == false ? false : true;
		if (modelName) {
			url = resolveUrl(url, "modelName=" + modelName);
		}
		if (tempKey) {
			url = resolveUrl(url, "tempKey=" + tempKey);
		}
		// 如果传入的authType=0，这里会认为是“否”，而强制使用authType=2
		if(authType == null || authType == undefined) {
			authType = 2;
		}
		if (authType != null) {
			url = resolveUrl(url, "authType=" + authType);
		}
		if (noFavorite) {
			url = resolveUrl(url, "noFavorite=" + noFavorite);
		}
		if (mulSelect) {
			h += 60;
		}
		url = resolveUrl(url, "isShowTemp=" + isShowTemp);
		url = resolveUrl(url, "mulSelect=" + mulSelect);
		var fields = rtfield(idField, nameField);
		var idObj = fields.idObj;
		if (idObj && idObj.value)
			url = resolveUrl(url, "currId=" + idObj.value);

		var nameObj = fields.nameObj;
		var buttons = [ {
			name : lang['ui.dialog.button.ok'],
			value : true,
			focus : true,
			fn : function(value, dialog) {
				var params = {};
				var iframe = dialog.element.find('iframe').get(0);
				var urlParams = iframe.contentWindow.urlParams;
				if ((!urlParams || urlParams.length == 0)) {
					if (notNull) {
						dialogAlert(lang['ui.dialog.template.message'], '', '',
								'', true);
						return;
					} else
						urlParams = [ '', '' ];
				}
				params = {
					id : urlParams[0],
					name : urlParams[1]
				};
				if (idObj)
					idObj.value = urlParams[0];
				if (nameObj) {
					nameObj.value = urlParams[1];
					// 触发校验事件
					if (nameObj.type != 'hidden') {
						nameObj.focus();
						nameObj.blur();
					}
				}
				dialog.hide(params);
			}
		} ];
		if (canClose)
			buttons.push({
				name : lang['ui.dialog.button.cancel'],
				value : false,
				styleClass : 'lui_toolbar_btn_gray',
				fn : function(value, dialog) {
					dialog.hide(value);
				}
			});
		if (!notNull)
			buttons.push({
				name : lang['ui.dialog.button.cancelSelect'],
				value : false,
				styleClass : 'lui_toolbar_btn_gray',
				fn : function(value, dialog) {
					var params = {};
					params = {
						id : '',
						name : ''
					};
					if (idObj)
						idObj.value = '';
					if (nameObj) {
						nameObj.value = '';
					}
					dialog.hide(params);
				}
			});
		var data = {
			config : {
				width : w,
				height : h,
				lock : true,
				cache : false,
				title : winTitle || lang['ui.dialog.template'],
				scroll : true,
				close : canClose,
				content : {
					id : 'dialog_category',
					scroll : true,
					type : "iframe",
					url : url,
					params : ___urlParam,
					buttons : buttons
				}
			},
			callback : action
		}
		var dia = build(data).show();
		dia.on('layoutDone', function() {
			new dragdrop.Draggable({
				handle : dia.head,
				element : dia.element,
				isDrop : false,
				isClone : false
			});
		})
		
		// 防止点击弹出框后，鼠标闪烁
		DialogFunc_BlurFieldObj([idObj, nameObj]);
		return dia;
	}

	// ___urlParam：自定义参数（json格式），针对的是模板的字段过滤，而不是全局分类的字段，例如：kmReviewTemplate
	function dialogCategoryForNewFile(modelName, urlParam, mulSelect, winTitle,
			action, idVal, target, canClose, isShowTemp, areaId, ___urlParam, tempKey) {
		var size = getSizeForNewFile()
		var w = size.width, h = size.height;
		var url = '/sys/ui/js/category/category.jsp';

		var sourceurl = '/sys/category/criteria/sysCategoryCriteria.do?method=select&type=02';
		sourceurl = resolveUrl(sourceurl, "authType=2");

		isShowTemp = isShowTemp == false ? 0 : 1;

		if (modelName) {
			url = resolveUrl(url, "modelName=" + modelName);
			sourceurl = resolveUrl(sourceurl, "modelName=" + modelName);
		}
		if (idVal) {
			url = resolveUrl(url, "currId=" + idVal);
			sourceurl = resolveUrl(sourceurl, "modelName=" + modelName);
		}
		url = resolveUrl(url, "isShowTemp=" + isShowTemp);
		if(tempKey){
			url = resolveUrl(url, "tempKey=" + tempKey);
		}
		sourceurl = resolveUrl(sourceurl, "getTemplate=" + isShowTemp);

		function openDialog() {
			canClose = canClose == false ? false : true;
			mulSelect = mulSelect == true ? true : false;
			if (mulSelect) {
				h += 60;
			}
			url = resolveUrl(url, "mulSelect=" + mulSelect);
			url = resolveUrl(url, "authType=2");
			if (window.console) {
				console.log('dialogCategoryForNewFile:url=' + url);
			}

			var buttons = [ {
				name : lang['ui.dialog.button.ok'],
				value : true,
				focus : true,
				fn : function(value, dialog) {
					var params = {};
					var iframe = dialog.element.find('iframe').get(0);
					var urlParams = iframe.contentWindow.urlParams;
					if (!urlParams || urlParams.length == 0) {
						dialogAlert(lang['ui.dialog.template.message'], '',
								'', '', true,window);
						return;
					}
					params = {
						id : urlParams[0],
						name : urlParams[1]
					};
					if (urlParam) {
						var openUrl = strutil
								.variableResolver(urlParam, params);
						if (window.console) {
							console.log(urlParams);
							console.log(openUrl);
						}
						window.open(env.fn.formatUrl(openUrl),
								target != null ? target : '_blank');
					}
					dialog.hide(params);
				}
			} ];
			if (canClose)
				buttons.push({
					name : lang['ui.dialog.button.cancel'],
					value : false,
					styleClass : 'lui_toolbar_btn_gray',
					fn : function(value, dialog) {
						dialog.hide(value);
					}
				});
			var data = {
				config : {
					width : w,
					height : h,
					lock : true,
					cache : false,
					title : winTitle || lang['ui.dialog.template'],
					scroll : true,
					close : canClose,
					content : {
						id : 'dialog_category',
						scroll : true,
						type : "iframe",
						url : url,
						params : ___urlParam,
						buttons : buttons
					}
				},
				callback : action
			}

			var dia = build(data).show();
			return dia;
		}

		if (idVal && !mulSelect && !___urlParam && urlParam && target!='_self') {
			sourceurl = resolveUrl(sourceurl, "currId=" + idVal);
			var flag = true;
				$.ajax({
					url : env.fn.formatUrl(sourceurl),
					dataType : 'json',
					async:false,
					success : function(data, textStatus, jqXHR) {
						outer: for (var j = 0; j < data.length; j++) {
							for (var i = 0; i < data[j].length; i++) {
								var val = data[j][i];
								if (val.value == idVal) {
									if (!val.nodeType
											|| val.nodeType == 'template') {
										flag = false;
									}
									break outer;
								}
							}
						}
						
					}
				});
				if (flag) {
					openDialog();
					return;
				}
				var _openUrl = strutil.variableResolver(urlParam, {
					id : idVal
				});
				window.open(env.fn.formatUrl(_openUrl), '_blank');
		} else
			return openDialog();
	}

	function dialogResult(data) {
		if (data.status == true) {
			dialogSuccess({
				title : data.title,
				message : data.message
			});
		} else if (data.status == 500) {
			// 针对后台返回异常信息的提示
			if(!data.responseJSON && data.responseText) {
				try {
					data.responseJSON = JSON.parse(data.responseText);
				}catch (e){

				}
			}
			if(data.responseJSON) {
				dialogFailure(data.responseJSON);
			} else {
				dialogFailure({
					title : lang['ui.dialog.operation.title'],
					message : [{'isOk': false,'msg': lang['ui.dialog.operation.failure']}]
				});
			}
		} else {
			dialogFailure({
				title : data.title,
				message : data.message
			});
		}
	}
	;
	function dialogTree(serviceBean, title, _callback, _config) {
		var dconfig = {
			width : 500,
			height : 500
		};
		var url = "/sys/ui/js/dialog/tree.jsp?service="
				+ encodeURIComponent(serviceBean);
		$.extend(dconfig, _config);
		var data = {
			config : {
				lock : true,
				cache : false,
				width : dconfig.width,
				height : dconfig.height,
				top : dconfig.top,
				left : dconfig.left,
				title : title || '',
				content : {
					id : 'dialog_iframe',
					scroll : true,
					type : "iframe",
					url : url
				}
			},
			callback : _callback || new Function()
		};
		var dia = build(data).show();
		// debugger;
		dia.on('layoutDone', function() {
			new dragdrop.Draggable({
				handle : dia.head,
				element : dia.element,
				isDrop : false,
				isClone : false
			});
		})
		return dia;
	}
	;
	
	// 根据屏幕分辨率计算宽度和高度，适用于地址本
	function getSizeForAddress() {
		//根据当前页面的宽度和屏幕分辨率对比。如果结果比当前页面宽度都大，则直接用当前页面宽度
		var parentWidth =$(document).width();
		var parentHeight =$(document).height();
		var width = screen.width * 0.55;  
		if(width > parentWidth){
			width= parentWidth-50;
		}
		if(width < 800){			
			width = 800;
		}
		
		var height = screen.height * 0.63;
		if(height > parentHeight){
			height=parentHeight -20;
		}
		if(height < 540){			
			height = 540; 
		}
		return {width: width, height: height};
	};
	
	// 根据屏幕分辨率计算宽度和高度，适用于分类选择框
	function getSizeForNewFile() {
		
		var parentWidth =$(document).width();
		var parentHeight =$(document).height();
		var width = screen.width * 0.6;  
		if(width > parentWidth){
			width= parentWidth-50;
		}
		if(width < 800){			
			width = 800;
		}
		
		var height = screen.height * 0.58;
		if(height > parentHeight){
			height=parentHeight -20;
		}
		if(height < 540){			
			height = 540; 
		} 
		return {width: width, height: height};
	};
	
	
	// 取消指定元素的选中状态(移除光标)
	function DialogFunc_BlurFieldObj(_fieldList) {
		for(var i =0;i< _fieldList.length;i++){
			if(typeof(_fieldList[i])!='undefined'){
			if(_fieldList[i].name){
				var fieldObj=document.getElementsByName(_fieldList[i].name);
				if(fieldObj.length>0){
					try{
						fieldObj[0].blur();
					}catch(e){}
				}
			}
		}
		}
	}
	
	function dialogForNewFile(config) {
		var id = config.id,
			sourceurl = config.sourceurl || '',
			url = config.url,
			modelName = config.modelName,
			urlParam = config.urlParam,
			mulSelect = config.mulSelect,
			winTitle = config.winTitle,
			callback = config.callback,
			idVal = config.idVal,
			target = config.target,
			canClose = config.canClose,
			sourceType = config.sourceType,
			isShowTemp = config.isShowTemp,
			okBtnMsg = config.okBtnMsg,
			cancelBtnMsg = config.cancelBtnMsg;
		
		var size = getSizeForNewFile()
		var w = size.width, h = size.height;
		sourceurl = resolveUrl(sourceurl, "authType=2");

		isShowTemp = isShowTemp == false ? 0 : 1;

		if (modelName) {
			url = resolveUrl(url, "modelName=" + modelName);
			sourceurl = resolveUrl(sourceurl, "modelName=" + modelName);
		}
		if (idVal) {
			url = resolveUrl(url, "currId=" + idVal);
			sourceurl = resolveUrl(sourceurl, "modelName=" + modelName);
		}
		if (sourceType) {
			url = resolveUrl(url, "sourceType=" + sourceType);
			sourceurl = resolveUrl(sourceurl, "sourceType=" + sourceType);
		}
		url = resolveUrl(url, "isShowTemp=" + isShowTemp);
		sourceurl = resolveUrl(sourceurl, "getTemplate=" + isShowTemp);

		function openDialog() {
			canClose = canClose == false ? false : true;
			mulSelect = mulSelect == true ? true : false;
			if (mulSelect) {
				h += 60;
			}
			url = resolveUrl(url, "mulSelect=" + mulSelect);
			url = resolveUrl(url, "authType=2");
			if (window.console) {
				console.log('dialogForNewFile:url=' + url);
			}

			var buttons = [ {
				name : okBtnMsg || lang['ui.dialog.button.ok'],
				value : true,
				focus : true,
				fn : function(value, dialog) {
					var params = {};
					var iframe = dialog.element.find('iframe').get(0);
					var urlParams = iframe.contentWindow.urlParams;
					if (!urlParams || urlParams.length == 0) {
						var _alert = dialogAlert(lang['ui.dialog.template.message'], '', '', '', false);
						$(_alert.element).css({'z-index':10015,'top':(getSizeForNewFile().height/2)+'px'});
						return;
					}
					params = {
						id : urlParams[0],
						name : urlParams[1]
					};
					if (urlParam) {
						var openUrl = strutil.variableResolver(urlParam, params);
						if (window.console) {
							console.log(urlParams);
							console.log(openUrl);
						}
						window.open(env.fn.formatUrl(openUrl), target != null ? target : '_blank');
					}
					dialog.hide(params);
				}
			} ];
			if (canClose)
				buttons.push({
					name : cancelBtnMsg || lang['ui.dialog.button.cancel'],
					value : false,
					styleClass : 'lui_toolbar_btn_gray',
					fn : function(value, dialog) {
						dialog.hide(value);
					}
				});
			var data = {
				config : {
					width : w,
					height : h,
					lock : true,
					cache : false,
					title : winTitle || lang['ui.dialog.template'],
					scroll : true,
					close : canClose,
					content : {
						id : id || 'dialog_category',
						scroll : true,
						type : "iframe",
						url : url,
						buttons : buttons
					}
				},
				callback : callback
			}

			var dia = build(data).show();
			return dia;
		}

		if (idVal && !mulSelect && urlParam && target!='_self') {
			sourceurl = resolveUrl(sourceurl, "currId=" + idVal);
			$.ajax({
				url : env.fn.formatUrl(sourceurl),
				dataType : 'json',
				success : function(data, textStatus, jqXHR) {
					var flag = true;
					outer: for (var j = 0; j < data.length; j++) {
						for (var i = 0; i < data[j].length; i++) {
							var val = data[j][i];
							if (val.value == idVal) {
								if (!val.nodeType || val.nodeType == 'template') {
									flag = false;
								}
								break outer;
							}
						}
					}
					if (flag) {
						openDialog();
						return;
					}

					var _openUrl = strutil.variableResolver(urlParam, {
						id : idVal
					});
					window.open(env.fn.formatUrl(_openUrl), '_blank');
				}
			});
		} else
			return openDialog();
	}

	exports.Dialog = Dialog;
	exports.build = build;

	exports.tree = dialogTree;
	exports.alert = dialogAlert;
	exports.result = dialogResult;
	exports.confirm = dialogConfirm;
	exports.success = dialogSuccess;
	exports.failure = dialogFailure;
	exports.loading = dialogLoading;
	exports.progress = dialogProgress;
	exports.iframe = dialogIframe;
	exports.simpleCategoryForNewFile = dialogSimpleCategoryForNewFile;
	exports.simpleCategory = dialogSimpleCategory;
	exports.categoryForNewFile = dialogCategoryForNewFile;
	exports.category = dialogCategory;
	exports.getSizeForAddress = getSizeForAddress;
	exports.getSizeForNewFile = getSizeForNewFile;
	exports.dialogForNewFile = dialogForNewFile;

});