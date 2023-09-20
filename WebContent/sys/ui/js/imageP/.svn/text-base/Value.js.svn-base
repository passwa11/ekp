define(function(require, exports, module) {
	var base = require("lui/base");
	var topic = require('lui/topic');
	// 对外事件--当前图片发生改变
	var PREVIEW_SELECTED_CHANGE = "preview/change";
	var Value = base.Base.extend({

		index : 0,

		initProps : function($super, cfg) {
			$super(cfg);
			this.data = cfg.data;
			this.value = cfg.value;
			this.valueType=cfg.valueType;
		},

		startup : function() {
			this.setValue(this.value);
		},

		set : function(key, value) {
			this['set' + capitalize(key)].call(this, value);
		},

		get : function(key) {
			return this['get' + capitalize(key)].call(this);
		},

		getValue : function() {
			return this.selectedValue;
		},

		getIndex : function() {
			return this.index;
		},

		getLength : function() {
			return this.data.length;
		},

		setValue : function(val) {
			if (this.selectedValue == val)
				return;
			this.selectedValue = val;
			this.index = this.getIndexByValue(val);
			this.selectedChanged(val);
		},

		getSelectedData : function() {
			for (var i = 0; i < this.data.length; i++) {
				if (this.selectedValue == this.data[i].value)
					return this.data[i];
			}
		},

		setIndex : function(index) {
			this.index = index;
			this.selectedValue = this.getValueByIndex(index);
			this.selectedChanged(this.selectedValue);
		},

		getIndexByValue : function(val) {
			for (var i = 0; i < this.data.length; i++) {
				if (val == this.data[i].value)
					return i;
			}
		},

		getValueByIndex : function(index) {
			return this.data[index].value;
		},

		getData : function() {
			return this.data;
		},

		selectedChanged : function(val) {
			topic.publish(PREVIEW_SELECTED_CHANGE, {
				value : val,
				index : this.index,
				data : this.data
			});
		}

	});

	// 首字母大写
	var capitalize = function(str) {
		if (str == null || str.length == 0)
			return "";
		return str.substr(0, 1).toUpperCase() + str.substr(1);
	};

	module.exports = Value;
});
