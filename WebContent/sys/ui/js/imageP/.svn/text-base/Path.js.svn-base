define(function(require, exports, module) {
	var base = require("lui/base");
	var env = require("lui/util/env");
	var topic = require('lui/topic');
	var $ = require("lui/jquery");
	var layout = require('lui/view/layout');

	var Path = base.Container.extend({
		initProps : function($super, cfg) {
			$super(cfg);
			this.path = cfg.path;
			topic.subscribe('preview/pathChange', this.pathChange, this);
			this.startup();
		},

		pathChange : function(evt) {
			if (!evt.data)
				return;
			this.data = evt.data;
			this.doLay();
		},

		doLay : function() {
			if (!this.layout) {
				this.layout = new layout.Template({
					src : require.resolve('./tmpl/path.jsp#'),
					parent : this
				});
				this.layout.startup();
				this.children.push(this.layout);
			} else {
				var self = this;
				this.layout.get(this, function(obj) {
					self.doLayout(obj);
				});
			}

		},

		startup : function() {
			if (this.isStartup)
				return;

			if (!this.path)
				return;

			this.pathId = this.path.pathId;
			this.data = this.path.data || [];

			// 获取路径对象
			if (this.pathId) {
				var pathClaz = LUI(this.pathId);
				if (!pathClaz)
					return;
				var children = pathClaz.children;
				for (var i = 1; i < children.length; i++) {
					this.data.push({
						href : children[i].href,
						text : children[i].text
					});
				}
			}
			this.doLay();

			this.isStartup = true;
		},

		doLayout : function(obj) {
			this.element.html($(obj));
		}

	})
	module.exports = Path;
});
