// JavaScript Document
define(function(require, exports, module) {
	var $ = require("lui/jquery");
	var base = require("lui/base");

	var HoverTriggerSearch = base.Base.extend({
		initProps : function(_config){
			this.element = $(_config.element);
			this.event = _config.event;
			this.position = _config.position;
		},
		
		startup : function(){
			var self = this;
			var parentTrigger = self.overlay.parent==null?null:self.overlay.parent.trigger;
			self.overlay.hide();
			self.show = false;
			var showTimeout = -1, hideTimeout = -1;
			
			//监听事件触发显示
			self.element.bind("click",function(evt){
				if(self.show || showTimeout>-1)
					return;
				showTimeout = window.setTimeout(function(){
					self.show = true;
					showTimeout = -1;
					self.overlay.show();
				},300);
			});
			self.element.bind("mouseout",function(evt){
				if(showTimeout>-1){
					window.clearTimeout(showTimeout);
					showTimeout = -1;
				}
			});
			
			//监听事件触发隐藏
			self.on('mouseover', function(){
				if(hideTimeout>-1){
					window.clearTimeout(hideTimeout);
					hideTimeout = -1;
				}
				$(window).bind("scroll", function() {
					self.emit('mouseout',{timer:1});
				});
				if(parentTrigger)
					parentTrigger.emit('mouseover');
			});
			self.on('mouseout', function(evt){
				if(hideTimeout>-1){
					window.clearTimeout(hideTimeout);
					hideTimeout = -1;
				}
				if(self.show){
					var timer = 300;
					if(evt && evt.timer)
						timer = evt.timer;
					hideTimeout = window.setTimeout(function(){
						self.show = false;
						hideTimeout = -1;
						self.overlay.hide();
						$(window).unbind("scroll");
					},timer);
				}
				if(parentTrigger)
					parentTrigger.emit('mouseout');
			});
			
			self.hoverEvent = {
					"mouseover" : function(){
						self.emit('mouseover');
					},
					"mouseout":function(){
						self.emit('mouseout');
					}
			}; 
			if(self.position){
				self.position.bind(self.hoverEvent);
			}
			self.element.bind(self.hoverEvent);
			self.overlay.getContent().bind(self.hoverEvent);
		}
	});
	
	exports.HoverTriggerSearch = HoverTriggerSearch;
	
});