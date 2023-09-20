define(function(require, exports, module) {	
	var $ = require("lui/jquery");
	var dialog = require("lui/dialog");
	var Base = require("./Base");
	var VBox = require('./VBox');
	var Editbox = Base.extend({
		initialize : function($super,config) {
			$super(config);
			this.align = config.align || "left";
			this.menuIconClass = config.menuIconClass || "";
			this.isShow = false;
			// 定义菜单明细项载体jQuery DOM对象
			this.eidtMenuItem = $("<div class='editMenuItemDiv_"+this.align+"'></div>").append(this.buildMenu());
			// 定义菜单入口设置图标jQuery DOM对象
			this.eidtMenuText = $("<div class='editMenuText "+this.menuIconClass+"'><span class='editMenuIcon'></span></div>");
			// 定义菜单载体jQuery DOM对象（并同时将入口设置图标和菜单明细载体追加成其子元素）
			this.editMenu = $("<div class='editMenu'></div>").append(this.eidtMenuText).append(this.eidtMenuItem.hide());
			var self = this;
            // 定义鼠标悬停事件
			var hoverEvent = {			
				"mouseenter":function(){  // 鼠标移入(穿过其子元素时不会被触发)
					self.onShow();
				},
				"mouseleave":function(){  // 鼠标移除(离开其子元素时不会被触发)	
					self.onHide();
				}
			}; 
			self.element.bind(hoverEvent); 
			self.editMenu.bind(hoverEvent); 
			this.eidtMenuItem.mouseover(function(evt){
				self.editMenu.show();
				self.eidtMenuItem.show();
				evt.stopPropagation();
			});
			// 点击设置图标，展开菜单列表
			this.eidtMenuText.click(function(evt){
				self.eidtMenuItem.show();
				evt.stopPropagation();
			});
			// 将菜单载体放入当前容器内（默认隐藏菜单,鼠标移入时才显示）
			this.body.append(this.editMenu.hide()); 
			this.element.addClass("editbox");
		},
		buildMenu : function(menuItems) {
			menuItems = menuItems || [];
			var self = this;
			var items = [];
			for(var i=0;i<menuItems.length;i++){
				var item = $("<div class='editMenuItem'></div>");
				item.append("<span class='eidtMenuItemIcon "+menuItems[i].icon+"'></span>");
				item.append("<span class='eidtMenuItemText'>"+menuItems[i].text+"</span>");
				item.click((function(index){
					return function(){
						menuItems[index].fn();
						self.onHide();
					}
				})(i));
				items.push(item);
			}
			return items;
		},
		onShow : function(){
			this.isShow = true;
			var pos = this.element.offset();
			this.element.addClass("editboxHover");
			if(this.align == "left"){
				this.editMenu.show().css({"left":pos.left,"top":(pos.top)});				
			}else{				
				this.editMenu.show().css({"left":(pos.left+this.element.outerWidth(true)-this.eidtMenuText.width()),"top":(pos.top)});
			}
		},
		onHide : function(){
			this.isShow = false; 
			this.element.removeClass("editboxHover");
			this.editMenu.hide();
			this.eidtMenuItem.hide();
		},
		startup:function(){
		},
		destroy : function($super){			
			this.editMenu.remove();
			$super();
		}
	});
	
	module.exports = Editbox;
});