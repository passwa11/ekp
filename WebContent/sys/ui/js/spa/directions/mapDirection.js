/**
 * 数据映射指令<br>
 * 例： cfg-map="{\"docCategory\":\"criteria('docCategoryId')\"}"
 */
define(function(require, exports, module) {

	var SpaConst = require('../const');
	var topic = require('lui/topic');
	var str = require('lui/util/str');

	var mapDirection = {

		initProps : function($super, cfg) {

			$super(cfg);

			this.cfgSpaMap = cfg["map"];

			if (this.cfgSpaMap) {
				this.cfgSpaMap = str.toJSON(this.cfgSpaMap);
				topic.subscribe(SpaConst.SPA_CHANGE_VALUES,
						this.onMapDirection, this);
			}

		},

		onMapDirection : function(evt) {

			if (!evt)
				return;

			this.spaValues = evt.value;

			var obj = this.cfgSpaMap;

			for ( var key in obj) {

				var val = new Function('param', 'criteria', 'return '
						+ obj[key]).apply(this, [
						evt.values || {},
						this._criteria.call(this) ]);

				if (val)
					this[key] = val;
				else
					delete this[key];
			}

		}

	}

	module.exports = mapDirection;
})
