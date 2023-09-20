/**
 * 移动首页模板的基类
 * 
 */
define(function(require, exports, module) {
	
	var $ = require("lui/jquery");
	var base = require("lui/base");

	var MobileBaseView = base.Container.extend({
		
		isInit : false,
		
		initProps: function($super,cfg) {
			$super(cfg);
			this.activeWgt = null;	// 当前选中的组件
		},
		
		hide : function(){
			this.element.hide();
		},
		
		show : function(){
			this.element.fadeIn();
			if(!this.isInit){
				this.draw();
				this.isInit = true;
			}else{
				this.triggerActiveWgt();				
			}
		},
		
		setData : function(data){
			this.data = data;
			// 更新子组件的data
			for(var i = 0;i < this.children.length;i++){
				var widget = this.children[i];
				if(widget.setInitData){
					widget.setInitData(data);
				}
			}
		},
		
		// 约定每个.mobileBlock对应一个组件MobileBaseWidget
		doLayout : function($super,obj){
			// 添加点击事件
			var self = this;
			// 一个mobileBlock对应一个组件MobileBaseWidget，点击时激活
			this.element.find(".mobileBlock").on("click", function(i,dom){
				var wgt = LUI(this.id);
				self.activeWgt = wgt;
				self.triggerActiveWgt($(this));
			});
			if(this.activeWgt){
				self.triggerActiveWgt();
			}
			$super(obj);
		},
		
		triggerActiveWgt: function(element){
			if(this.activeWgt){
				element = element ? element : this.activeWgt.element;
				element.siblings().removeClass("active");
				element.addClass("active");
				this.activeWgt.active();				
			}
		},
		
		getKeyData : function(){
			var keyData = {};
			for(var i = 0;i < this.children.length;i++){
				var widget = this.children[i];
				if(widget.getKeyData){
					var widgetData = widget.getKeyData();
					for(var key in widgetData){
						keyData[key] = widgetData[key];
					}					
				}
			}
			return keyData;
		},
		
		getSketchpad : function(){
			return this.parent.getAttrPanel();
		},
		
		validate : function(){
			var pass = true;
			var warningWgt = [];
			for(var i = 0;i < this.children.length;i++){
				var widget = this.children[i];
				if(widget.validateData){
					if(!widget.validateData()){
						warningWgt.push(widget);
					}
				}
			}
			if(warningWgt.length > 0){
				pass = false;
			}
			this.warnAction(warningWgt);
			return pass;
		},
		
		warnAction : function(wgts){
			// 清空状态
			this.clearAbnormalStatus();
			// 切换警告状态
			for(var i = 0;i < wgts.length;i++){
				wgts[i].toggleWarning(true);
			}
			// 选中第一个
			if(wgts.length > 0){
				wgts[0].element.trigger($.Event("click"));
			}
		},
		
		// 清空所有子组件的异常状态
		clearAbnormalStatus : function(){
			for(var i = 0;i < this.children.length;i++){
				if(this.children[i].clearAbnormalStatus){
					this.children[i].clearAbnormalStatus();
				}
			}
		},
		
		refresh : function($super){
			$super();
			this.clearAbnormalStatus();
		},
		
		doInitChild : function(element, claz, isActive){
			var clazInstance = new claz({element:element,data:this.data,parent:this});
			clazInstance.startup();
			this.addChild(clazInstance);
			if(isActive === true){
				this.activeWgt = clazInstance;				
			}
		}
		
	});
	
	exports.MobileBaseView = MobileBaseView;
})