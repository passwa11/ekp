/**
 * 
 */
define(function(require, exports, module) {
	
	var $ = require("lui/jquery");
	var base = require("lui/base");
	var env = require("lui/util/env");
	var topic = require("lui/topic");
	
	var LeftNav = base.DataView.extend({
		
		initProps: function($super, cfg) {
			$super(cfg);
		},
		
		// 覆盖draw，不然首次就加载
		draw : function(){
			
		},
		
		resolveUrl : function(params){
			this.source.resolveUrl(params);
		},
		
		load : function($super, cfg){
			this.element.show();
			$super(cfg);
			this.isDrawed = true;
		},
		
		doRender : function($super,cfg){
			$super(cfg);
			// 添加事件
			this.element.find(".panel-left-content li").on("click",function(){
				$(this).siblings().removeClass("active");
				$(this).addClass("active");
				topic.channel("modeling").publish("dialog.cate.change",{"value":$(this).attr("data-cate-value")});
			});
			
			this.element.find(".panel-left-content li").first().trigger($.Event("click"));
		}
		
	})
	
	exports.LeftNav = LeftNav;
})