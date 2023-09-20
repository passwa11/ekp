/**
 * 单页面权限指令
 */
define(function(require, exports, module) {

	var SpaConst = require('../const');
	var topic = require('lui/topic');
	var $ = require('lui/jquery');
	var Spa = require('lui/spa/Spa');

	var authDirection = {

		initProps : function($super, cfg) {

			$super(cfg);

			if (cfg['auth']) {

				this.cfgSpaAuth = cfg["auth"];

				topic
						.subscribe(SpaConst.SPA_DIRECTION_DONE, this.spaAuth,
								this);

				Spa.spa.regist(this);

			}

		},

		// 判断当前对象是否有权限
		hasAuth : function() {

			var self = this;
			var auth = false;

			$.each(this.spaAuthDatas, function(i, data) {

				if (data[self.id]) {

					if ("true" == data[self.id]) {
						auth = true;
					}

					return false;
				}
			});

			return auth;

		},

		spaAuth : function(evt) {

			if (!evt)
				return;

			this.spaAuthDatas = evt.auth;
			this.setSpaShow(this.hasAuth());

		},

		// 设置组件隐藏显示
		setSpaShow : function(show) {

		}

	}

	module.exports = authDirection;
})
