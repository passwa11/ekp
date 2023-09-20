/**
 * 路由<br>
 * 现仅支持hash方式
 */
define(function(require, exports, module) {

	var topic = require('lui/topic');
	var hash = require('./router/hash');
	var SpaConst = require('./const');
	var $ = require('lui/jquery');
	var routerUtils = require('lui/framework/router/router-utils');

	var router = {

		// 路由模式
		mode : hash,

		// 是否启用
		routerEnable : true,

		initProps : function($super, cfg) {

			$super(cfg);

			if (!this.routerEnable)
				return;

			/**
			 * 数据监听，改变hash
			 */
			topic.subscribe(SpaConst.SPA_CHANGE_CLEAR, this.routerClear, this);
			topic.subscribe(SpaConst.SPA_CHANGE_ADD, this.routerAdd, this);
			topic
					.subscribe(SpaConst.SPA_CHANGE_REMOVE, this.routerRemove,
							this);
			topic.subscribe(SpaConst.SPA_CHANGE_RESET, this.routerReset, this);

		},

		startup : function() {

			/**
			 * 初始化数据<br>
			 * 通过hash还原当前页面状态
			 */
			var self = this;
			setTimeout(function() {
				self.recover();
			},200);
		},

		routerReset : function(evt) {

			if (!evt)
				return;

			this.mode.set(evt.value);

		},

		// 重置值，对外API
		reset : function($super, params) {

			this.routerReset({
				value : params,
				target : this
			});

			$super(params);

		},

		// 清除所有值
		clear : function($super) {

			this.routerClear();
			$super();

		},

		// 还原当前hash状态
		recover : function() {
			var self = this;
			var $router = routerUtils.getRouter();
			console.log($router, '---$router--')
			if($router && $router.getCriValue){//从router定义的·cri·选项中取值
				var promise = $router.getCriValue();
				promise.then(function(value){
					topic.publish(SpaConst.SPA_CHANGE_ADD, {
						value : $.extend({}, value , self.mode.get()),
						target : self
					});
				});
			}else{
				topic.publish(SpaConst.SPA_CHANGE_ADD, {
					value : this.mode.get(),
					target : this
				});
			}
		},

		// 设置值，对外API
		setValue : function($super, key, value) {

			var param = {};
			param[key] = value;

			this.routerAdd({
				value : param
			});

			$super(key, value);
		},

		/**
		 * 群组过滤<br>
		 * 群组里面条件进行互斥过滤
		 */
		routerGroupFilter : function(value) {

			var self = this;

			if (!this.groups)
				return;

			for ( var key in value) {

				$.each(this.groups, function(index, group) {

					if ($.inArray(key, group) >= 0) {

						self.routerRemove({
							value : group
						});
					}

				});
			}

		},

		// 增加值
		routerAdd : function(evt) {

			if (!evt)
				return;

			if (!evt.value)
				return;

			var value = evt.value;

			this.routerGroupFilter(value);

			this.mode.add(value);

		},

		// 移除指定值
		routerRemove : function(evt) {

			if (!evt)
				return;

			if (!evt.value)
				return;

			this.mode.remove(evt.value);

		},

		// 清空所有值
		routerClear : function() {

			this.mode.clear();

		},

		destroy : function($super) {

			$super();

			if (!this.routerEnable)
				return;

			topic.unsubscribe(SpaConst.SPA_CHANGE_CLEAR, this.routerClear);
			topic.unsubscribe(SpaConst.SPA_CHANGE_ADD, this.routerAdd);
			topic.unsubscribe(SpaConst.SPA_CHANGE_REMOVE, this.routerRemove);

		}

	};

	module.exports = router;

});