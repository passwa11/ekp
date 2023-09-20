/**
 * 仅用于业务建模的弹出层
 */

define(function(require, exports, module) {
	
	var $ = require("lui/jquery");
	var base = require("lui/base");
	var topic = require("lui/topic");
	require("sys/modeling/base/resources/css/popup.css");
	
	var Popup = base.Component.extend({
		
		initProps : function($super,cfg){
			$super(cfg);
			this.triggerObjects = cfg.triggerObjects;
			this.contentElement = cfg.contentElement || $("<div />");
			this.parent = cfg.parent;
		},
		
		startup : function($super,cfg){
			$super(cfg);
			var self = this;
			topic.channel("modelingPopup").subscribe("app.popup.hide",function(ctx){
				self.hide();
			});
		},
		
		draw : function(){
			// 
			var self = this;
			self.drawPopup();
			var zIndex = LUI.zindex();
			var rotate = 0;
			self.triggerObjects.on("click",function(event){
				event.stopPropagation();
				self.hide();
				if(self.parent.isExpandPopup && self.parent.isExpandPopup(this)){
					
					self.emit("beforeExpand",{"popup":self,"dom":this});
					// 获取位置
					var pos = self.getDomPosition(this);
					// 默认在下侧显示
					pos.top += $(this).outerHeight() + 5;
					self.popupElement.css({"left":pos.left,"top":pos.top,"z-index":zIndex});
					self.popupElement.show();
					self.emit("afterExpand",{"popup":self,"dom":this});
				}
			});
		},
		
		drawPopup : function(){
			this.element.appendTo($(document.body));
			this.popupElement = $("<div class='form_popup' />");
			this.popupElement.append(this.contentElement);
			this.element.append(this.popupElement);
			this.hide();
		},
		
		getDomPosition : function(dom){
			return $(dom).offset();
		},
		
		hide : function(){
			this.popupElement.hide();
		}
		
	});
	
	exports.Popup = Popup;
	
})