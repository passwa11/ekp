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
			var templateList = LUI("templateList");
			templateList.on("category-render-finish",templateList.doRefresh);
			var source = this.source;
			var url = templateList.source.url;
			var categoryId = Com_GetUrlParameter(url,"categoryId");
			if (source && !categoryId) {
				var datas = this.data || [];
				var category = datas[0];
				if (category) {
					var defaultCategoryId = category.fdId;
					url = Com_SetUrlParameter(url, "categoryId", defaultCategoryId);
					templateList.source.url = url;
					templateList.source._url = url;
				}
				templateList.fire({name:"category-render-finish"});
			}
		}
	});
	
	exports.navDataView = navDataView;
	
})