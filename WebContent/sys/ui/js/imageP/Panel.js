define(function(require, exports, module) {
	var base = require("lui/base");
	var $ = require("lui/jquery");
	var layout = require('lui/view/layout');
	var topic = require('lui/topic');
	var loader = require('lui/util/loader');
	var env = require('lui/util/env');
	// 右侧图片信息面板
	var Panel = base.Container.extend(loader.ResourceLoadMixin, {
		initProps : function($super, cfg) {
			$super(cfg);
			this.show = true;
			this.data = cfg.data;
			this.type = cfg.panel.type || 'text';
			this.__src = cfg.panel.url || '';
			this.src = cfg.panel.url || '';
			this.startup();
		},
		startup : function() {
			if (this.isStartup)
				return;
			if (!this.layout) {
				this.layout = new layout.Template({
					src : require.resolve('./tmpl/panel.jsp#'),
					parent : this
				});
				this.layout.startup();
				this.children.push(this.layout);
			}
			this.isStartup = true;
		},

		serURl : function(value) {
			this[this.type + 'SetUrl'](env.fn.variableResolver(this.__src,
					this.data.get('selectedData') || {}));
		},

		change : function(evt) {
			if (!evt || !evt.value)
				return;
			this.serURl(evt.value);
		},

		doLayout : function(obj) {
			this.element.append($(obj));
			this.toggleNode = this.element.find('.lui_imageP_panle_toggle');
			this.contentNode = this.element.find('.lui_imageP_panel_content');
			this.c_width = this.contentNode.width();
			this.bindEvent();
			// 构建面板url
			if (!this[this.type + 'Panel'])
				if (window.console) {
					console.log('未定义类型')
					return;
				}
			this[this.type + 'Panel'].call(this);
			this.serURl(this.data.get('value'));
			topic.subscribe('preview/change', this.change, this);
		},

		bindEvent : function() {
			var self = this;
			this.toggleNode.on('click', function(evt) {
				self.onToggle(evt);
			});
		},

		onToggle : function(evt) {
			if (!this.show)
				this.contentNode.show(400);
			else
				this.contentNode.hide(400);
			this.toggleText();
			this.show = this.show == true ? false : true;
			topic.publish('preview/panel/toggle', {
				show : this.show,
				width : this.c_width
			});
		},

		toggleText : function() {
			var toggle_symbol = 'data-lui-toggle', btn = this.toggleNode
					.find('[' + toggle_symbol + ']'), text = btn
					.attr(toggle_symbol), oldText = btn.html();
			btn.html(text).attr(toggle_symbol, oldText);
		},

		iframeSetUrl : function(src) {
			if (this.lastUrl == src)
				return;
			this.lastUrl = src;
			this.panelNode.attr('src', env.fn.formatUrl(src));
		},

		textSetUrl : function() {
			topic.publish('preview/panel/change', this.get('currentData'));
		},

		iframePanel : function() {
			this.panelNode = $(
					'<iframe class="lui_imageP_panel_iframe" scrolling="auto">')
					.appendTo(this.contentNode);

		},

		textPanel : function() {
			this._initResource({
				src : src
			}, this);
		},

		_onLoad : function(text) {
			this.contentNode.html(text);
		}
	});
	module.exports = Panel;
});
