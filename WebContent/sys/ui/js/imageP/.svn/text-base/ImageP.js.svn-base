define(function(require, exports, module) {
	var $ = require("lui/jquery");
	var env = require('lui/util/env');
	var base = require("lui/base");
	var layout = require('lui/view/layout');
	var topic = require('lui/topic');

	var Play = require('./Play');
	var Panel = require('./Panel');
	var Thumb = require('./Thumb');
	var Value = require('./Value');
	var Path = require('./Path');
	var Toolbar = require('./Toolbar');
	var lang = require('lang!sys-ui');

	// 容器
	var ImageP = base.Container
			.extend({

				url : '/sys/attachment/sys_att_main/sysAttMain.do?method=download&fdId=!{fdId}',

				thumbUrl : '/sys/attachment/sys_att_main/sysAttMain.do?method=download&fdId=!{fdId}',

				initialize : function($super, config) {
					$super(config);
					this.startup();
					this.draw();
				},

				initProps : function($super, cfg) {
					$super(cfg);
					this.data = cfg.data;
					this.url = env.fn.formatUrl(cfg.url || this.url);
					this.thumbUrl = env.fn.formatUrl(cfg.thumbUrl
							|| this.thumbUrl);
					this.panelConfig = cfg.panel;
					this.pathConfig = cfg.path;
					this.thumbConfig = cfg.thumb || {};
					this.canDownload = cfg.canDownload;
					this.canDel = cfg.canDel;
				},
				
				formatSrc : function(value) {
					var _src=this.url.replace('!{fdId}', value);
					if( this.data.valueType && this.data.valueType =='url' ){
						_src=value;
					}
					return _src;
				},

				layoutedCall : [],

				startup : function() {
					if (this.isStartup)
						return;
					var self = this;
					if (!this.layout) {
						this.layout = new layout.Template({
							src : require.resolve('./tmpl/imageP.jsp#'),
							parent : this
						});
						this.layout.startup();
						this.children.push(this.layout);
					}

					if (!this.Value) {
						this._value_ = new Value(this.data);
						this.children.push(this._value_);
					}

					if (!this.play) {
						this.play = new Play({
							parent : this,
							data : this._value_,
							url : this.url
						});
						this.children.push(this.play);
					}

					if (!this.thumb) {
						this.thumb = new Thumb({
							parent : this,
							data : this._value_,
							url : this.thumbUrl,
							thumb : this.thumbConfig
						});
						this.children.push(this.thumb);
					}

					if (!this.panel && this.panelConfig) {
						this.panel = new Panel({
							parent : this,
							panel : this.panelConfig,
							data : this._value_
						});
						this.children.push(this.panel);
					} else
						// 无panel配置信息时强制调用预览撑开函数
						this.layoutedCall.push(function() {
							self.panelToggle({
								show : false
							}, true);
						});

					if (!this.path) {
						this.path = new Path({
							parent : this,
							path : this.pathConfig
						});
						this.children.push(this.path);
					}

					if (!this.toolbar) {
						this.toolbar = new Toolbar({
							parent : this,
							buttonConfigs : [
									{
										text : lang['imageP.viewOriginal'],
										icon : '',
										click : function() {
											var url=self.formatSrc(self._value_.get('value'));
											window.open(env.fn.formatUrl('/sys/ui/js/imageP/tmpl/preview.jsp?url=')+ encodeURIComponent(url),'_blank');
										}
									},
									{
										text : lang['imageP.downloadOriginal'],
										icon : '',
										click : function() {
											var url=null;
											if(self.data.downloadUrl){
												url=self.formatSrc(self.data.downloadUrl);
											}else{
												url=self.formatSrc(self._value_.get('value'));
											}
											window.open(url,'_self');
										}
									}, 
									{
										text : lang['imageP.close'],
										icon : '',
										click : function() {
											self.destroy();
										}
									} 
							]
						});
						this.children.push(this.toolbar);
					}

					// 初始化数据源
					this._value_.startup();
					this.overlay = $('<div class="lui-imageP-overlay" />')
							.appendTo($(document.body));

					this.element.appendTo($(document.body));
					// 更改布局
					topic.subscribe('preview/thumb/toggle', this.thumbToggle,
							this);
					topic.subscribe('preview/panel/toggle', this.panelToggle,
							this);
					// 父页面滚动设置
					this.scrollable(false);
					this.isStartup = true;
				},

				scrollable : function(flag) {
					var overflow = flag ? 'auto' : 'hidden';
					$('body').css('overflow-y', overflow);
				},

				thumbToggle : function(evt) {
					var height = this.playNode.height();
					if (!evt.show)
						height += evt.height;
					else
						height -= evt.height;
					this.playNode.animate({
						height : height + 'px',
						'line-height' : height + 'px'
					})
				},

				// fire为true表示不使用动画
				panelToggle : function(evt, fire) {
					var margin = 0, topicType = 'preview/reset';
					if (!evt)
						return;
					if (evt.show)
						margin = this.margin;

					if (!fire)
						this.contentNode.animate({
							'margin-right' : margin + 'px',
						}, function() {
							topic.publish(this.topicType);
						});
					else {
						this.contentNode.css('margin-right', margin + 'px');
						topic.publish(this.topicType);
					}
				},

				doLayout : function(obj) {
					this.element.append($(obj));
					this.contentNode = this.element.find('.lui_imageP_content');
					this.margin = parseInt(this.contentNode.css('margin-right'));
					this.playNode = this.element.find('.lui_imageP_play');
					var pathNode = this.element.find('.lui_imageP_path');
					var thumbNode = this.element.find('.lui_imageP_thumb');
					var panelNode = this.element.find('.lui_imageP_panel');
					var toolbarNode = this.element.find('.lui_imageP_toolbar');
					for (var i = 0; i < this.children.length; i++) {
						var child = this.children[i];
						if (child instanceof Play)
							child.setParentNode(this.playNode);
						if (child instanceof Thumb)
							child.setParentNode(thumbNode);
						if (child instanceof Panel)
							child.setParentNode(panelNode);
						if (child instanceof Path)
							child.setParentNode(pathNode);
						if (child instanceof Toolbar)
							child.setParentNode(toolbarNode);
						if (child.draw)
							child.draw();
					}
					var self = this;
					$.each(this.layoutedCall, function() {
						this.call(self);
					});
					if(this.data.imageBgColor){//背景色
						this.playNode.find('img').css('background',this.data.imageBgColor);
					}
				},

				erase : function($super) {
					this.element.html("");
					// 销毁遮罩
					this.overlay.remove();
					this.scrollable(true);
					$super();
				}
			});

	module.exports = ImageP;

});