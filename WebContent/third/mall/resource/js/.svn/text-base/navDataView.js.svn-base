/**
 * 
 */
define(function(require, exports, module) {
	
	var $ = require("lui/jquery");
	var base = require("lui/base");
	var dialog = require('lui/dialog');
	var topic = require("lui/topic");
	
	var navDataView = base.DataView.extend({
		
		ctx : {"curTriggerPopup":null},
		
		
		startup : function($super) {
			$super();
		},
		
		load : function($super){
			$super();
		},

		// 重新刷新列表
		doRefresh : function(){
			this.source.get();
		},
		
		// 渲染完毕之后添加事件
		doRender : function($super,cfg){
			$super(cfg);
			var modeList = LUI("modeList");
			modeList.on("nav-render-finish",modeList.doRefresh);
			var source = this.source;
			var url = modeList.source.url;
			var industryId = Com_GetUrlParameter(url,"industryId");
			if (source && !industryId) {
				var datas = this.data || [];
				var industry = datas[0];
				if (industry) {
					var defaultIndustryId = industry.fdId;
					url = Com_SetUrlParameter(url, "industryId", defaultIndustryId);
					modeList.source.url = url;
					modeList.source._url = url;
				}
				modeList.fire({name:"nav-render-finish"});
			}
		}
	});
	
	exports.navDataView = navDataView;
	
})