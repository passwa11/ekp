/**
 * 移动设计默认视图
 */
define(function(require, exports, module) {
	
	var $ = require("lui/jquery");
	var base = require("lui/base");
	var layout = require("lui/view/layout");
	var baseView = require("sys/modeling/base/mobile/design/js/mobileBaseView");
	var ListBlock = require("sys/modeling/base/mobile/design/mode/list/listBlock");
	
	var MobileList = baseView.MobileBaseView.extend({
		
		initProps: function($super,cfg) {
			$super(cfg);
	    },
	    
		startup : function($super,cfg){
			$super(cfg);
			
			this.doInitChild($("<div />").appendTo(this.element), ListBlock, true);
		},
		
		draw : function($super,obj){
			$super(obj);
			this.doLayout();
		}
	});
	
	module.exports = MobileList;
})