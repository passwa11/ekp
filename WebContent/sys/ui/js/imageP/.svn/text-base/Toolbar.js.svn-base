define(function(require, exports, module) {
	var base = require("lui/base");
	var env = require("lui/util/env");
	var topic = require('lui/topic');
	var $ = require("lui/jquery");
	var layout = require('lui/view/layout');
	var Toolbar = base.Container.extend({

		initProps : function($super, cfg) {
			$super(cfg);
			this.url = env.fn.formatUrl(cfg.url);
			this.buttonConfigs = cfg.buttonConfigs || [];
			this.startup();
		},

		startup : function() {
			if (this.isStartup)
				return;
			for (var i = 0; i < this.buttonConfigs.length; i++) {
				var config = this.buttonConfigs[i];
				var button = new Button({
					text : config.text,
					icon : config.icon,
					click : config.click
				});
				this.children.push(button);
				this.element.append(button.element);
			}
			this.isStartup = true;
		}
	})

	var Button = base.Container.extend({
		initProps : function($super, cfg) {
			$super(cfg);
			this.icon = cfg.icon;
			this.text = cfg.text;
			this.click = cfg.click;
			this.startup();
		},

		startup : function() {
			if (this.isStartup)
				return;
			if (!this.layout) {
				this.layout = new layout.Template({
					src : require.resolve('./tmpl/button.jsp#'),
					parent : this
				});
				this.layout.startup();
				this.children.push(this.layout);
			}
			this.element.addClass('lui_imageP_btn');
			this.isStartup = true;
		},

		doLayout : function(obj) {
			this.element.append($(obj));
			this.textNode = this.element.find('.lui_imageP_btn_txt');
			this.textNode.html(this.text);
			this.iconNode = this.element.find('.lui_imageP_btn_icon');

			var self = this;
			this.element.on('click', function(evt) {
				self.click.call(self, evt);
			});
		}
	});

	module.exports = Toolbar;
});
