/**
 * 
 */
define(function(require, exports, module) {
	
	var $ = require("lui/jquery");
	var base = require("lui/base");
	var dialog = require('lui/dialog');
	var topic = require("lui/topic");
	
	var ThirdTemplateListDataView = base.DataView.extend({
		
		ctx : {"curTriggerPopup":null},
		
		
		startup : function($super) {
			$super();			
		},
		
		load : function($super){
			var source = this.source;
			var url = source.url;
			var industryId = Com_GetUrlParameter(url,"industryId");
			if (industryId) {
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
	
	exports.ThirdTemplateListDataView = ThirdTemplateListDataView;
	
})