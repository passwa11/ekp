/**
 * 移动列表视图的视图组件
 */
define(function(require, exports, module) {
	
	var $ = require('lui/jquery'),
		base = require('lui/base'),
		viewContainer = require('sys/modeling/base/mobile/resources/js/viewContainer'),
		view = require('sys/modeling/base/mobile/resources/js/view'),
		pcCalendarView = require('sys/modeling/base/views/business/res/calendar/pcCalendarView'),
		mobileCalendarView = require('sys/modeling/base/views/business/res/calendar/mobileCalendarView');
	var topic = require("lui/topic");
	var dialog = require("lui/dialog");

	var CalendarViewContainer = viewContainer["ViewContainer"].extend({

		initProps: function($super, cfg) {
			cfg.storeData = cfg.storeData || '{}';
			cfg.storeData = JSON.parse(cfg.storeData) || {};
			$super(cfg);
			this.xformId = cfg.xformId || "";
		},
		
		startup : function($super, cfg) {
			$super(cfg);
		},

		draw:function($super,cfg){
			$super(cfg);
		},

		/****************** 头部 end **********************/
		
		// isSwitchToChild:是否切换到当前视图
		createChildView : function(viewData,isSwitchToChild){
			// 设计基础，标题页面和视图组件通过data-wgt-id进行绑定
            if (this.mode === "pc") {
                var pcViewData = viewData.fdPcCfg;
				var viewWgt = new pcCalendarView["PcCalendarView"]({data:pcViewData,parent:this,container:this.viewContainer,fdType:this.fdType,xformId:this.xformId});
            } else if (this.mode === "mobile") {
                var mobileViewData = viewData.fdMobileCfg
				var viewWgt = new mobileCalendarView["MobileCalendarView"]({data:mobileViewData,parent:this,container:this.viewContainer,xformId:this.xformId});
            } else {
                var viewWgt = new view["View"]({data:viewData,parent:this,container:this.viewContainer});
            }
			viewWgt.startup();
            this.views.push(viewWgt);
            viewWgt.draw();
			return viewWgt;
		},
		
		getKeyData : function(){
			var keyData = {};
			keyData.views = [];
			for(var i = 0;i < this.views.length;i++){
				var viewWgt = this.views[i];
				keyData.views.push(viewWgt.getKeyData());
			}
			return keyData;
		}
	});
	
	exports.CalendarViewContainer = CalendarViewContainer;
})