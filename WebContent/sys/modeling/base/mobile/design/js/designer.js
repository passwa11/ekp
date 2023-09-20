/**
 * 移动页面布局设计器
 */
define(function(require, exports, module) {
	
	var $ = require("lui/jquery");
	var base = require("lui/base");
	var mobileBaseView = require("sys/modeling/base/mobile/design/js/mobileBaseView");
	var topic = require("lui/topic");
	
	var Designer = base.Container.extend({
		
		mode : {},	// 所有模式
		
		initProps: function($super,cfg) {
			$super(cfg);
			this.data = cfg.data;
		},
		
		startup : function($super,cfg){
			topic.channel("modeling").subscribe("mobile.index.tmp.change", this.changeMobileView,this);
			if(this.panel){
				this.panel.startup();
			}
			$super(cfg);
		},
		
		// 注册模式
		register : function(modeId,mode){
			if(mode instanceof mobileBaseView.MobileBaseView){
				this.mode[modeId] = mode;
			}
		},
		
		// 更新视图，每次更新视图，把当前视图的data设置到切换的模板
		_setMobileView : function(view){
			for(var key in this.mode){
				this.mode[key].hide();
			}
			if(this.mobileView){
				// 更新data，后续是否可以考虑不更新？
				this.data = this.getKeyData();
			}
			// 更新当前模板视图
			this.mobileView = view;
			this.mobileView.setData(this.data);
			// 由于每次切换视图，都会更新data，所以此处必须更新视图，除非切换视图时，不更新data
			this.mobileView.refresh();
			this.mobileView.show();
		},
		
		setAttrPanel : function(panel){
			this.panel = panel;
		},
		
		getAttrPanel : function(){
			return this.panel;
		},
		
		// 切换模式
		changeMode : function(modeId){
			if(this.mode.hasOwnProperty(modeId)){
				this._setMobileView(this.mode[modeId]);
			}
		},
		
		changeMobileView : function(argu){
			this.changeMode(argu.value);
		},
		
		getKeyData : function(){
			var keyData = {};
			if(this.mobileView){
				keyData = this.mobileView.getKeyData();
			}
			return keyData;
		},
		
		validate : function(){
			return this.mobileView.validate();
		},
		
		updateViewByData : function(data){
			data = data || {};
			this.mobileView.setData(data);
			this.mobileView.refresh();
			this.mobileView.show();
		}
		
	});
	
	exports.Designer = Designer;
})