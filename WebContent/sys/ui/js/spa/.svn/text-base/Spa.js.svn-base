/**
 * 单页面应用容器 <br>
 * 由“值存储”、“路由”、”指令“构成
 */
define(function(require, exports, module) {

	var base = require('lui/base');
	var router = require('./router');
	var values = require('./values');
	var direction = require('./direction');
	var str = require('lui/util/str');

	var Spa = base.Base.extend(values).extend(router).extend(direction).extend(
			{

				_init : function($super, cfg) {

					seajs.data.env.isSpa = true;

					this.groups = cfg.groups;

					if (this.groups)
						this.groups = str.toJSON(this.groups);

					$super(cfg);

				},

				initProps : function($super, cfg) {

					$super(cfg);
					exports.spa = this;
				},

				clear : function($super) {

					$super();
				},

				getValue : function($super, key) {

					return $super(key);
				},
				
				getCriteriaValue : function($super, key, channel) {
					return $super(key, channel);
				},
				

				setVaule : function($super, key, value) {

					$super(key, value);
				},

				reset : function($super, params) {

					$super(params);
				}
				
				

			});

	exports.Spa = Spa;

});