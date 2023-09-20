/**
 * 
 */
define(function(require, exports, module) {
	
	var $ = require("lui/jquery");
	var base = require("lui/base");
	var dialog = require('lui/dialog');
	var topic = require("lui/topic");
	
	var thirdDingXFormTemplateDataView = base.DataView.extend({
		
		ctx : {"curTriggerPopup":null},
		
		
		startup : function($super) {
			$super();			
		},
		
		load : function($super){
			var source = this.source;
			var url = source.url;
			var category = Com_GetUrlParameter(url,"category");
			if (category) {
				$super();
			}
		},

		// 重新刷新列表
		doRefresh : function(){
			this.source.get();
		},
		
		// 渲染完毕之后添加事件
		doRender : function($super,cfg){
			$super(cfg);
		}
	});
	
	exports.thirdDingXFormTemplateDataView = thirdDingXFormTemplateDataView;
	
})