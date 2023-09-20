/**
 * 移动设计默认视图
 */
define(function(require, exports, module) {
	
	var $ = require("lui/jquery");
	var base = require("lui/base");
	var layout = require("lui/view/layout");
	var render = require("lui/view/render");
	var baseView = require("sys/modeling/base/mobile/design/js/mobileBaseView");
	var mportalListStatistics = require("sys/modeling/base/mobile/design/mode/mportalList/mportalListStatistics");	// 统计区
	var mportalListDataList = require("sys/modeling/base/mobile/design/mode/mportalList/mportalListDataList");	// 数据展示区
	var mportalListChartList = require("sys/modeling/base/mobile/design/mode/mportalList/mportalListChartList");	// 图表区
	var modelingLang = require("lang!sys-modeling-base");
	var MobileMportalList = baseView.MobileBaseView.extend({
		
		renderHtml : "/sys/modeling/base/mobile/design/mode/mportalList/mportalList.html#",
		
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
			
			this.doInitChild(this.element.find(".mportalList-statistics"), mportalListStatistics, true);
			this.doInitChild(this.element.find(".mportalList-dataList"), mportalListDataList);
			this.doInitChild(this.element.find(".mportalList-chartList"), mportalListChartList);
			
			$super(obj);
		},
		//获取多语言资源
		getModelingLang :function (){
			return modelingLang;
		}
	});
	
	module.exports = MobileMportalList;
})