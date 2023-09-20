/**
 * 
 */
define(function(require, exports, module) {
	
	var $ = require("lui/jquery");
	var base = require("lui/base");
	var dialog = require('lui/dialog');
	var topic = require("lui/topic");
	
	var ThirdPortalListDataView = base.DataView.extend({
		
		ctx : {"curTriggerPopup":null},
		
		
		startup : function($super) {
			$super();			
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
	
	exports.ThirdPortalListDataView = ThirdPortalListDataView;
	
})