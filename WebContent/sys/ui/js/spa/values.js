/**
 * 值之存储<br>
 */
define(function(require, exports, module) {

	var topic = require('lui/topic');
	var SpaConst = require('./const');

	var values = {

		values : {},

		initProps : function($super) {

			// 数据监听
			topic.subscribe(SpaConst.SPA_CHANGE_CLEAR, this.valueClear, this);
			topic.subscribe(SpaConst.SPA_CHANGE_ADD, this.valueAdd, this);
			topic.subscribe(SpaConst.SPA_CHANGE_REMOVE, this.valueRemove, this);
			topic.subscribe(SpaConst.SPA_CHANGE_RESET, this.valueReset, this);

			$super();

		},

		valueReset : function(evt) {

			if (!evt)
				return;

			this.values = evt.value;

			this.publisValues(evt);

		},

		// 重置值，对外API
		reset : function($super, params) {

			this.valueReset({
				value : params,
				target : this
			});
		},

		// 获取值，对外API
		getValue : function(key) {

			if (!key)
				return this.values;

			return this.values[key] || '';

		},
		
		//获取筛选器值的便民API
		getCriteriaValue : function(key, channel) {
			var criteriaKey;

			if (!channel) {
				criteriaKey = "cri.q";
			} else {
				criteriaKey = "cri." + channel + ".q";
			}
			
			var hashStr = this.values[criteriaKey];

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
		},
		

		// 设置值，对外API
		setValue : function($super, key, value) {

			var param = {};
			param[key] = value;

			this.valueAdd({
				value : param
			});

		},

		// 清除所有值
		clear : function() {

			this.valueClear({
				target : this
			});
		},

		/**
		 * 发送值改变事件<br>
		 */
		publisValues : function(evt) {

			$.extend(evt, {
				value : this.values
			});

			topic.publish(SpaConst.SPA_CHANGE_VALUES, evt);

		},

		/**
		 * 群组过滤<br>
		 * 群组里面条件进行互斥过滤
		 */
		valueGroupFilter : function(key) {

			if (!this.groups)
				return;

			var self = this;

			$.each(this.groups, function(index, group) {

				if ($.inArray(key, group) >= 0) {

					$.each(group, function(i, k) {

						delete self.values[k];

					})

				}

			});

		},

		// 增加值
		valueAdd : function(evt) {

			if (!evt)
				return;

			if (!evt.value)
				return;

			var value = evt.value;
			// 条件是否发生变化
			var hasChange = false;

			for ( var key in value) {

				if (this.values[key] == value[key])
					continue;

				this.valueGroupFilter(key);

				hasChange = true;

				this.values[key] = value[key];

			}
			// 条件不为空且条件未发生改变不触发
			if (!hasChange && !$.isEmptyObject(this.values))
				return;
			
			evt.target = this;

			this.publisValues(evt);

		},

		// 清空所有值
		valueClear : function(evt) {

			if (!evt)
				return;

			this.values = {};

			this.publisValues(evt);

		},

		// 移除指定值
		valueRemove : function(evt) {

			if (!evt)
				return;

			if (!evt.value)
				return;

			$.each(evt.value, function(index, key) {

				delete this.values[key];
			});

			this.publisValues(evt);

		},

		destroy : function($super) {

			$super();

			topic.unsubscribe(SpaConst.SPA_CHANGE_CLEAR, this.valueClear);
			topic.unsubscribe(SpaConst.SPA_CHANGE_ADD, this.valueAdd);
			topic.unsubscribe(SpaConst.SPA_CHANGE_REMOVE, this.valueRemove);

		}

	};

	module.exports = values;

});