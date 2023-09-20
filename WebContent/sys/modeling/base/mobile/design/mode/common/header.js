/**
 * 已废弃
 */
define(function(require, exports, module) {
	
	var $ = require("lui/jquery");
	var base = require("lui/base");
	var mobileBaseWidget = require("sys/modeling/base/mobile/design/mode/common/mobileBaseWidget");
	
	var tmpData = {
			attr : {
				title : {
					text : "标题",
					type : "String",
					value : ""
				}
			}
	};
	
	var Header = mobileBaseWidget.MobileBaseWidget.extend({
		
		initProps: function($super,cfg) {
			$super(cfg);
			this.widgetKey = "header";
			this.data = $.extend(true, {},tmpData,(cfg.data && cfg.data[this.widgetKey])|| {});
	    },
		
		draw : function($super,cfg){
			$super(cfg);
		}
		
	});
	
	module.exports = Header;
})