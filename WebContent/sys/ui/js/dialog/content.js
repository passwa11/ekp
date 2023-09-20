define(function(require, exports, module) {
	var lang = require('lang!sys-ui');
	var $ = require("lui/jquery");
	var base = require("lui/base");
	var env = require("lui/util/env");
	var overlay = require("lui/overlay");
	var layout = require("lui/view/layout");
	var toolbar = require('lui/toolbar');
	var topic = require('lui/topic');
	var Default = base.Component.extend({
		initProps : function($super, _config) {
			$super(_config);
			this.iconType = _config.iconType;
			this.typeConfig = _config.type;
			//初始化button方法
			this._buttonInit();
			if (typeof(_config.buttons) === 'string'
					&& window.BUTTONS[_config.buttons])
				this.buttonsConfig = window.BUTTONS[_config.buttons];
			else
				this.buttonsConfig = _config.buttons;

		},

		_buttonInit : function (){
			if(!window.BUTTONS){
				window.BUTTONS = {
					BTN_OK : [{
						name : lang['ui.dialog.button.ok'],
						value : true,
						focus : true,
						fn : function(value, dialog) {
							dialog.hide(value);
						}
					}],
					BTN_CANCEL : [{
						name : lang['ui.dialog.button.cancel'],
						value : false,
						focus : true,
						fn : function(value, dialog) {
							dialog.hide(value);
						}
					}],
					BTN_OK_CANCEL : [{
						name : lang['ui.dialog.button.ok'],
						value : true,
						focus : true,
						fn : function(value, dialog) {
							dialog.hide(value);
						}
					}, {
						name : lang['ui.dialog.button.cancel'],
						value : false,
						focus : true,
						fn : function(value, dialog) {
							dialog.hide(value);
						}
					}]
				}
			}
		},

		drawFrame : function() {
			var self = this;
			this.layout.get(this, function(obj) {
						self.layoutDone(obj);
						self.emit('layoutDone');
						if (self.buttonsConfig && self.buttonsConfig.length > 0)
							self.bindButtons();
					});
			this.children.push(this.layout);
		},

		get : function() {
			return this.parent.element;
		},

		hide : function() {
		},

		__event : function(evt) {
			var $target = $(evt.target);
			var self = this;
			while ($target.length > 0) {
				if ($target.hasClass('lui_widget_btn')) {
					var cid = $target.attr('data-lui-cid');
					for (var i = 0; i < self.buttonsConfig.length; i++) {
						if (cid === self.buttonsConfig[i]['lui_id']) {
							self.buttonsConfig[i].fn(
									self.buttonsConfig[i].value, self.parent);
							break;
						}
					}
					break;
				}
				$target = $target.parent();
			}
		},

		bindButtons : function() {
			var self = this;
			if (this.buttons) {
				this.buttons.bind('click', function(evt) {
							self.__event(evt);
						});
				this.buttons.bind('keydown', function(evt) {
							if (evt.keyCode != 13)
								return;
							self.__event(evt);
						});
			}
			this.buttons.find('.lui_widget_btn').each(function(i) {
						if (self.buttonsConfig[i].focus) {
							$(this).focus();
						}
					})
		}
	});

	var Loading = Default.extend({
		initProps : function($super, _config) {
			$super(_config);
			this.html = this.config.html;
		},
		startup : function() {
			this.layout = new layout['Template']({
						src : require
								.resolve('sys/ui/extend/dialog/content_loading.tmpl#'),
						parent : this
					});
			this.children.push(this.layout);
			this.layout.startup();
		},

		layoutDone : function(obj) {
			this.frame = $(obj);
			this.element.append(this.frame);
			this.inside = this.frame.find('.load_txt').html(this.html);
		}
	});

	var Html = Default.extend({
		initProps : function($super, _config) {
			$super(_config);
			this.html = env.fn.formatUrl(_config.html);
		},
		startup : function() {
			this.layout = new layout['Template']({
						src : require
								.resolve('sys/ui/extend/dialog/content.tmpl#'),
						parent : this
					});
			this.children.push(this.layout);
			this.layout.startup();
		},

		layoutDone : function(obj) {
			this.frame = $(obj);
			this.element.append(this.frame);
			this.inside = this.frame
					.find('[data-lui-mark="dialog.content.inside"]')
					.html(this.html);
			if (this.buttonsConfig && this.buttonsConfig.length > 0) {
				this.buttons = this.frame
						.find('[data-lui-mark="dialog.content.buttons"]');
				this.buttonsContainer = $('<div class="lui_dialog_buttons_container"/>');
				for (var i = 0; i < this.buttonsConfig.length; i++) {
					var button = toolbar.buildButton({
								title : this.buttonsConfig[i]['title'],
								text : this.buttonsConfig[i]['name'],
								styleClass : this.buttonsConfig[i]['styleClass'],
								disabled : this.buttonsConfig[i]['disabled']
							});
					this.buttonsConfig[i]['lui_id'] = button.cid;
					this.buttonsContainer.append(button.element.css({
								margin : '0 10px',
								display : 'inline-block'
							}));
					button.draw();
				}
				this.buttons.append(this.buttonsContainer);
			}
		}
	});

	var Element = Default.extend({
		initProps : function($super, _config) {
			$super(_config);
			this.elem = $(_config.elem);
		},
		startup : function() {
			this.layout = new layout['Template']({
						src : require
								.resolve('sys/ui/extend/dialog/content.tmpl#'),
						parent : this
					});
			this.children.push(this.layout);
			this.layout.startup();
		},

		layoutDone : function(obj) {
			this.frame = $(obj);
			this.element.append(this.frame);
			// 修复jquery\clone无法克隆原生dom事件，改为移动方式
			this.tmpl = $('<div />').hide().insertAfter(this.elem);
			this.inside = this.frame
					.find('[data-lui-mark="dialog.content.inside"]')
					.append(this.elem.show());
			var height = this.parent.height;
			if (height) {
				this.inside.css('height', height);
				this.inside.css('overflow', 'auto');
			}

			if (this.buttonsConfig && this.buttonsConfig.length > 0) {
				this.buttons = this.frame
						.find('[data-lui-mark="dialog.content.buttons"]');
				this.buttonsContainer = $('<div class="lui_dialog_buttons_container"/>');
				for (var i = 0; i < this.buttonsConfig.length; i++) {
					var button = toolbar.buildButton({
								title : this.buttonsConfig[i]['title'],
								text : this.buttonsConfig[i]['name'],
								styleClass : this.buttonsConfig[i]['styleClass']
							});
					this.buttonsConfig[i]['lui_id'] = button.cid;
					this.buttonsContainer.append(button.element.css({
								margin : '0 10px',
								display : 'inline-block'
							}));
					button.draw();
				}
				this.buttons.append(this.buttonsContainer);
			}
		},
		hide : function() {
			if (this.tmpl && this.tmpl.length > 0) {
				this.elem.hide().insertBefore(this.tmpl);
				this.tmpl.remove();
			}
		}

	});
	var Iframe = Default.extend({
		initProps : function($super, _config) {
			$super(_config);
			this.url = env.fn.formatUrl(_config.url);
			this.scroll = _config.scroll || false;
			this.params = this.config.params;
			this.extendCfg = _config.extendCfg;
		},
		startup : function() {
			this.layout = new layout['Template']({
						src : require
								.resolve('sys/ui/extend/dialog/content.tmpl#'),
						parent : this
					});
			this.children.push(this.layout);
			this.layout.startup();
			// 接收Iframe里的事件发布
			var _self = this;
			topic.subscribe("category.selected.changed", function(evt) {
				if (evt && evt.data && evt.data.length > 0) {
					var obj = evt.data[0].data[0];
					var desc = "";
					if (obj)
						desc = obj.desc;
		            if(evt.dialogDom && _self.element &&_self.element[0]==evt.dialogDom){
		        	    $(evt.dialogDom).find('[data-lui-mark="dialog.content.desc"]').text(desc);
		            }
				}
			});
		},

		layoutDone : function(obj) {
			var self = this;
			this.frame = $(obj);
			this.element.append(this.frame);
			
			// 分类/模板描述信息动态宽度
			var width = this.parent.width;
			if(width && (width - 220) > 580) {
				this.frame.find('.lui_category_desc').css('width', (width - 220));
			}
			
			if (this.buttonsConfig && this.buttonsConfig.length > 0) {

				this.buttons = this.frame
						.find('[data-lui-mark="dialog.content.buttons"]');
				this.buttonsContainer = $('<div class="lui_dialog_buttons_container"/>');
				for (var i = 0; i < this.buttonsConfig.length; i++) {
					var button = toolbar.buildButton({
								title : this.buttonsConfig[i]['title'],
								text : this.buttonsConfig[i]['name'],
								styleClass : this.buttonsConfig[i]['styleClass']
							});
					this.buttonsConfig[i]['lui_id'] = button.cid;
					this.buttonsContainer.append(button.element.css({
								margin : '0 10px',
								display : 'inline-block'
							}));
					button.draw();
				}
				this.buttons.append(this.buttonsContainer);
			}

			if (this.url) {
				// 滚动条
				var height = this.parent.height;
				var _height = this.parent._height;

				this.inside = this.frame
						.find('[data-lui-mark="dialog.content.inside"]');

				if (this.scroll && 'auto' != height) {
					this.inside.css('overflow', 'hidden');
				}
				var __height = this.parent.element.height();
				this.inside
						.css('height', height - __height);
				var self = this;
				this.iframeObj = $('<iframe width="100%" height="100%" src="'
//						+ this.url
						+ '" scrolling="auto" frameborder="0"></iframe>');

				function resizeFun(argu) {
					this.iframeObj.css({
								"height" : argu.height
							});
				}
			
				this.on('resize', resizeFun);
				this.onErase(function() {
							self.off('resize', resizeFun);
						});
				this.parent.on('resize', function() {
					var resizeH = _height - __height;
					if ($(window).height() <= _height)
						resizeH = $(window).height() - __height;
					self.inside.css('height', resizeH);
				});
				this.inside.append(this.iframeObj.show());
				setTimeout(function(){
					self.iframeObj.attr('src',self.url);
					self.iframeObj.bind('load', function(evt) {
						this.contentWindow._parent_dialog_dom = self.element[0];
						self.iframeLoaded(evt);
					});
				},1);
			}
		},

		iframeLoaded : function(evt) {
			if (this.params)
				this.parent.___params = this.params;
			if(this.extendCfg) 
				this.parent.extendCfg = this.extendCfg;
			if (evt.target){
				try{
					evt.target.contentWindow['$dialog'] = this.parent;
					evt.target.contentWindow['$contentHeight'] = $(evt.target).height();
				}catch(e){
					domain.call(evt.target.contentWindow,"dialogAgent",[this.parent.id]);
				}				
			}
		},

		// 销毁iframe中内容
		hide : function() {
			try{
				var $body = $(this.iframeObj[0].contentWindow.document.body);
				// 清除子节点信息，jquery默认empty在清除object对象会报错
				this.iframeDestroy($body);
				// 清楚剩余多于事件对象等
				$body.remove();
			}catch(e){
				if (window.console)
					console.error("dialog.hide();"+this.parent.id, e, e.message, e.stack);
			}
		},

		iframeDestroy : function($obj) {
			var self = this;
			$obj.children().each(function(i, obj) {
						if (obj.getAttribute && $(obj).children().length > 0)
							self.iframeDestroy($(obj));
						if(obj.contentWindow)
							self.iframeDestroy($(obj.contentWindow.document.body));
						obj.parentNode.removeChild(obj);
					});
		}
	});

	var Common = Default.extend({
		initProps : function($super, _config) {
			$super(_config);
			this.html = env.fn.formatUrl(_config.html);
		},
		startup : function() {
			this.layout = new layout['Template']({
						src : require
								.resolve('sys/ui/extend/dialog/content_common.jsp#'),
						parent : this
					});
			this.children.push(this.layout);
			this.layout.startup();
		},

		layoutDone : function(obj) {
			var self = this;
			this.frame = $(obj);
			this.element.append(this.frame);
			this.inside = this.frame
					.find('[data-lui-mark="dialog.content.inside"]')
					.html(this.html);
			if (this.buttonsConfig && this.buttonsConfig.length > 0) {

				this.buttons = this.frame
						.find('[data-lui-mark="dialog.content.buttons"]');
				this.buttonsContainer = $('<div class="lui_dialog_buttons_container"/>');
				for (var i = 0; i < this.buttonsConfig.length; i++) {
					var button = toolbar.buildButton({
								title : this.buttonsConfig[i]['title'],
								text : this.buttonsConfig[i]['name'],
								styleClass : this.buttonsConfig[i]['styleClass']
							});
					this.buttonsConfig[i]['lui_id'] = button.cid;
					this.buttonsContainer.append(button.element.css({
								margin : '0 10px',
								display : 'inline-block'
							}));
					button.draw();
				}

				this.buttons.append(this.buttonsContainer);
			}
		}
	});

	var Tip = Default.extend({
		initProps : function($super, _config) {
			$super(_config);
			this.html = typeof(_config.html) == 'object' ? _config.html : {
				title : _config.html,
				message : ''
			};
		},
		startup : function() {
			this.layout = new layout['Template']({
				src : require.resolve('sys/ui/extend/dialog/content_tip.tmpl#'),
				parent : this
			});
			this.children.push(this.layout);
			this.layout.startup();
		},

		layoutDone : function(obj) {
			var self = this;
			this.frame = $(obj);
			this.element.append(this.frame);
			this.frame.find('[data-lui-mark="dialog.content.inside.title"]')
					.html(this.html.title);
			if (this.html.message) {
				var $message = this.frame
						.find('[data-lui-mark="dialog.content.inside.message"]');
				for (var i = 0; i < this.html.message.length; i++) {

					var $span = $('<span />'), $icon = $('<span />'), $div = $('<div />');
					$span.text(this.html.message[i]['msg'] || '');
					// 成功失败图标
					$icon.addClass('lui_icon_s');
					if (this.html.message[i]['isOk'])
						$icon.addClass('lui_icon_s_icon_ok');
					else
						$icon.addClass('lui_icon_s_icon_remove');
					$div.append($icon).append($span);
					$message.append($div);
				}
			}
			if (this.buttonsConfig && this.buttonsConfig.length > 0) {
				this.buttons = this.frame
						.find('[data-lui-mark="dialog.content.buttons"]');
				this.buttonsContainer = $('<div class="lui_dialog_buttons_container"/>');
				for (var i = 0; i < this.buttonsConfig.length; i++) {
					var button = toolbar.buildButton({
								title : this.buttonsConfig[i]['title'],
								text : this.buttonsConfig[i]['name'],
								styleClass : this.buttonsConfig[i]['styleClass']
							});
					this.buttonsConfig[i]['lui_id'] = button.cid;
					this.buttonsContainer.append(button.element.css({
								margin : '0 10px',
								display : 'inline-block'
							}));
					button.draw();
				}
				this.buttons.append(this.buttonsContainer);
			}
		}
	});

	exports.Iframe = Iframe;
	exports.Element = Element;
	exports.Html = Html;
	exports.Common = Common;
	exports.Tip = Tip;
	exports.Loading = Loading;

});