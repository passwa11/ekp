/**
 * 菜单支持单页面适配器
 */
define(function(require, exports, module) {

	var SpaConst = require('../const');
	var topic = require('lui/topic');
	var env = require('lui/util/env');
	var routerUtils = require('lui/framework/router/router-utils');
	var str = require('lui/util/str');
	var $ = require('lui/jquery');

	var menuItemAdapter = {

		initProps : function($super, cfg) {

			this.isSpa = env.fn.getConfig().isSpa;
			this.isExt = cfg.ext;

			$super(cfg);

		},

		"menuRouter/docCategory" : function() {

			var self = this;

			var router = routerUtils.getRouter();
			
			if (router) {
				router.addRoutes({
					path : this.router,
					action : function(value) {
						
						var isKeepCriParameter=value.isKeepCriParameter;
						
						if(isKeepCriParameter){
							topic.publish(SpaConst.SPA_CHANGE_ADD, {
								value : value,
								target : this
							});
						}else{
							topic.publish(SpaConst.SPA_CHANGE_RESET, {
								value : value,
								target : this
							});
						}

						topic.publish("nav.operation.clearStatus", null);
					}
				});
			}

			this.element.click(function() {
				
				var router = routerUtils.getRouter();
					if(!router && self.router && self.config.hierarchy){
						router = routerUtils.getRouterTop();
					}


				if (self.isExt) {
					var curRoute = router.curRoute;
					router.push(curRoute.fullPath, {});
					return;
				}

				topic.publish("nav.operation.clearStatus", null);

				// var router = routerUtils.getRouter();
				// 是否启用路由模式
				if (router) {
					if (self.config) {
						// 当前没有分类，默认为所有知识
						if (!self.config.value) {
							router.push('', {
								docCategory: self.config.value || '',
								j_path: self.router
							});
							return;
						}

						var data = {
							docCategory : self.config.value,
							j_path : self.router
						}
						var criProps = self.getCriProps();
						if (criProps) {
							$.extend(data, str.toJSON(criProps));
						}
						router.push(self.router, data);
					}
				}
			});
		},
		
		// 获取扩展筛选值
		getCriProps : function() {
			if (!this.parent || !this.parent.menuSouce
				|| this.parent.menuSouce.size == 0) {
				return;
			}
			if(this.parent.menuSouce[0]) {
				var source = this.parent.menuSouce[0].source;
				if (source.config.criProps) {
					return source.config.criProps;
				}
			}

		},

		startup : function($super) {

			if (this.isSpa && !this.href) {
				this.element.addClass("lui_item");
				return;
			}

			$super();

		},

		draw : function($super) {

			$super();

			if (this.parent) {

				var parent = this.parent;

				if (this.parent.popup && this.parent.popup.parent) {
					parent = this.parent.popup.parent;
					while(parent != null){
						if(parent.router){
							break;
						}
						parent = parent.parent;
					}
				}
				if(parent) {
					this.router = parent.router;
				}

			}

			if (this.router && !this.href) {

				this['menuRouter' + this.router].call(this);
				this.element.css('cursor', 'pointer');

			}

		}

	}

	module.exports = menuItemAdapter;
})
