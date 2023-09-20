/**
 * 单页面布尔指令<br>
 * param可以获取当前hash的值，扩展函数有筛选criteria，权限auth<br>
 * 例：
 * cfg-if="criteria('docStatus')[0]=='30'&&auth(!{url})&&param.docCategory=='!{id}'"
 */
define(function(require, exports, module) {

	var SpaConst = require('../const');
	var topic = require('lui/topic');
	var Spa = require('lui/spa/Spa');

	var ifDirection = {

		initProps : function($super, cfg) {

			$super(cfg);

			/**
			 * 获取标签中cfg-if属性值<br>
			 * 支持使用param，获取当前单页面的值
			 * 
			 */

			this.cfgSpaIf = cfg["if"];

			if (this.cfgSpaIf) {

				// 解析表达式里面的权限信息
				var result = /auth\('([^)]*)'\)/.exec(this.cfgSpaIf);

				if (!result || result.length <= 1) {
					result = /auth\(([^)]*)\)/.exec(this.cfgSpaIf);
				}

				if (result && result.length > 1) {

					this.cfgSpaAuth = result[1];
					Spa.spa.regist(this);
					this.cfgSpaIf = this.cfgSpaIf.replace(/auth\([^)]*\)/ig,
							"_auth()");

					topic.subscribe(SpaConst.SPA_DIRECTION_DONE, this.spaIf,
							this);

				} else {

					topic.subscribe(SpaConst.SPA_CHANGE_VALUES, this.spaIf,
							this);
				}

			}

		},

		_auth : function() {

			return $.proxy(function() {

				return this.hasAuth();
			}, this)
		},

		// 筛选器获取值扩展
		_criteria : function() {

			var self = this;

			return function(key, channel) {

				var criteriaKey;

				if (!channel) {
					criteriaKey = "cri.q";
				} else {
					criteriaKey = "cri." + channel + ".q";
				}

				var hashStr = self.spaValues[criteriaKey];

				if (!hashStr)
					return [ '' ];

				var hashArray = hashStr.split(';');

				var criMap = {};

				for (var i = 0; i < hashArray.length; i++) {

					var p = hashArray[i].split(':');
					var val = criMap[p[0]];

					if (val == null) {
						val = [];
						criMap[p[0]] = val;
					}

					val.push(p[1]);
				}

				var result = criMap[key];

				return result && result.length >= 1 ? result : [ '' ];

			};
		},

		// 判断是否隐藏
		spaIf : function(evt) {

			if (!evt)
				return;

			this.spaValues = evt.value;
			this.spaAuthDatas = evt.auth;

			var ifResult = new Function('param', 'criteria', '_auth', 'return '
					+ this.cfgSpaIf).apply(this, [
					evt.value || {},
					this._criteria.call(this),
					this._auth.call(this) ]);

			this.setSpaShow(ifResult);

		},

		setSpaShow : function(show) {

		}

	}

	module.exports = ifDirection;
})
