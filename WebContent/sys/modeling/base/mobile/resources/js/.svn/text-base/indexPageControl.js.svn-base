/**
 * 移动首页设计页面的视图逻辑控制组件，与当前业务耦合度高
 * 
 */
define(function(require, exports, module) {
	
	var $ = require('lui/jquery'),
		base = require('lui/base'),
		topic = require('lui/topic');
	
	var IndexPageControl = base.Component.extend({
		
		initProps: function($super, cfg) {
			$super(cfg);
			this.curPage = cfg.type || "empty";
			this.page = {};
			for(var key in cfg.page){
				this.page[key] = $(cfg.page[key]);
			}
		},
		
		startup : function($super, cfg) {
			$super(cfg);
			topic.channel("modeling").subscribe("mobile.index.tmp.change", this.changeDesignPage,this)
		},
		
		draw : function($super, cfg){
			$super(cfg);
			this.changePage();
		},
		
		changePage : function(value){
			value = value || this.curPage;
			for(var key in this.page){
				if(key === value){
					this.page[key].show();
				}else{
					this.page[key].hide();
				}
			}
			
		},
		
		changeDesignPage : function(argu){
			this.curPage = "design";
			this.changePage();
			$("[name='fdIndex']").val(argu.value);
		}
		
	});
	
	exports.IndexPageControl = IndexPageControl;
		
})