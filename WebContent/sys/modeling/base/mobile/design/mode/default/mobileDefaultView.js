/**
 * 移动设计默认视图
 */
define(function(require, exports, module) {
	
	var $ = require("lui/jquery");
	var base = require("lui/base");
	var layout = require("lui/view/layout");
	var baseView = require("sys/modeling/base/mobile/design/js/mobileBaseView");
	var header = require("sys/modeling/base/mobile/design/mode/common/header");
	var defaultNotify = require("sys/modeling/base/mobile/design/mode/default/defaultNotify");
	var defaultStatistics = require("sys/modeling/base/mobile/design/mode/default/defaultStatistics");
	var defaultListView = require("sys/modeling/base/mobile/design/mode/default/defaultListView");
	
	var MobileDefaultView = baseView.MobileBaseView.extend({
		
		renderHtml : "/sys/modeling/base/mobile/design/mode/default/defaultView.html#",
		
		initProps: function($super,cfg) {
			$super(cfg);
			this.layout = new layout.Template({src:this.renderHtml,parent : this});
	    },
	    
		startup : function($super,cfg){
			$super(cfg);
			this.layout.startup();
		},
		
		doLayout : function($super,obj){
			this.element.append($(obj));
			
			this.doInitChild(this.element.find(".defaultView_head_notify"), defaultNotify, true);
			this.doInitChild(this.element.find(".defaultView_head_statistics"), defaultStatistics);
			this.doInitChild(this.element.find(".defaultView_head_listView"), defaultListView);
			
			$super(obj);
		}
	});
	
	module.exports = MobileDefaultView;
})