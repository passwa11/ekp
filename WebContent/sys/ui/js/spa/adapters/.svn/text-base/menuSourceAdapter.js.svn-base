/**
 * 菜单支持单页面适配器<br>
 * 主要是支持分类路径
 */
define(function(require, exports, module) {

	var SpaConst = require('../const');
	var topic = require('lui/topic');
	var env = require('lui/util/env');
	var source = require('lui/data/source');

	var menuSourceAdapter = {

		initProps : function($super, cfg) {

			this.isSpa = env.fn.getConfig().isSpa;

			$super(cfg);

			// 扩展字段
			this.extKey = cfg.extKey ? cfg.extKey.split(';') : cfg.extKey;

		},

		startup : function($super) {

			$super();

			if (!this.isSpa)
				return;

			if (this.parent) {
				this.router = this.parent.router;
			}

			if (this.router)
				topic.subscribe(SpaConst.SPA_CHANGE_VALUES, this.spaMenuValues,
						this);

		},

		destroyChildren : function() {

			if (this.children) {

				for (var i = 0; i < this.children.length; i++) {

					var child = this.children[i];

					if (child instanceof source.BaseSource) {
						continue;
					}

					child.destroy();
				}

				this.children.length = 1;

			}
		},

		buildCategory : function(docCategory) {

			if (this.docCategory == docCategory)
				return;

			this.docCategory = docCategory;

			this.destroyChildren();

			var _url = this.source._url, docCategory = docCategory;

			if (!docCategory)
				docCategory = "";

			_url = Com_SetUrlParameter(_url, 'categoryId', docCategory);

			this.source.setUrl(_url);

			if (!this.isDrawed)
				return;

			this.draw();

		},

		buildExt : function(value) {

			setTimeout($.proxy(function() {

				this.docCategory = null;

				value = value.split(',');

				this.destroyChildren();

				var data = {
					value : value[0],
					text : value[1],
					ext : true
				}

				var item = this.buildItem(data);

				this.parent.redrawItem(item, this, data, 1, 0);
				this.addChild(item);

			}, this), 1);

		},

		"router/docCategory" : function(value) {

			if ($.isEmptyObject(value)) {
				this.destroyChildren();
				// 清空条件置空分类参数，反正缓存不改变
				this.docCategory = null;
				return;
			}

			// 是否有互斥参数
			var mutex = false;

			for ( var key in value) {

				if ('docCategory' == key) {
					mutex = true;
					this.buildCategory(value[key]);
					break;

					// 扩展
				} else if ($.inArray(key, this.extKey) >= 0) {
					mutex = true;
					this.buildExt(value[key]);
					break;
				} else if('cri.q' == key && value[key] && value[key].indexOf(":")>-1){
					//#140421 兼容查询条件为分类时，会直接执行destroyChildren销毁子节点
					var categorys = value[key].split(":");
					if (categorys[0] === "dbEchartsTemplate" && categorys.length === 2) {
						mutex = true;
						//防止出现重复的分类menu
						if(!this.isDrawed){
							this.buildCategory(categorys[1]);
						}
						break;
					}
				}

			}

			if (!mutex) {
				this.destroyChildren();
			}

		},

		spaMenuValues : function(evt) {

			if (!evt)
				return;

			if (!evt.value)
				return;

			this['router' + this.router](evt.value, this);

		},

		draw : function($super, arguItem, isBefore) {

			$super(arguItem, isBefore);

		}

	}

	module.exports = menuSourceAdapter;
})
