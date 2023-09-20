/**
 * 移动设计默认视图
 */
define(function(require, exports, module) {
	
	var $ = require("lui/jquery");
	var base = require("lui/base");
	var layout = require("lui/view/layout");
	var baseView = require("sys/modeling/base/mobile/design/js/mobileBaseView");
	var mportalStatistics = require("sys/modeling/base/mobile/design/mode/mportal/mportalStatistics");	// 统计区
	var mportalIconArea = require("sys/modeling/base/mobile/design/mode/mportal/mportalIconArea");	// 图标区
	var mportalListView = require("sys/modeling/base/mobile/design/mode/mportal/mportalListView");	// 列表区
	
	var MobileMportal = baseView.MobileBaseView.extend({
		
		renderHtml : "/sys/modeling/base/mobile/design/mode/mportal/mportal.html#",
		
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
			
			this.doInitChild(this.element.find(".mportal-statistics"), mportalStatistics, true);
			this.doInitChild(this.element.find(".mportal-iconArea"), mportalIconArea);
			this.doInitChild(this.element.find(".mportal-listView"), mportalListView);
			
			$super(obj);
		}
		
	});
	
	module.exports = MobileMportal;
})