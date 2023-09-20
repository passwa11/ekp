
define(function(require, exports, module) {
	var $ = require('lui/jquery');
	var env = require('lui/util/env');
	
	var Communicate  = function (config) {
		this.elementId = config.elementId || null;
		this.navs = [];
		this.icons = [];
		this.titles = [];
		this.classNames = [];
		this.params = $.extend({}, config);
	};
	Communicate.prototype.push = function(icon,className,title,nav){
		 this.icons.push(icon);
		 this.titles.push(title);
		 this.classNames.push(className);
		 this.navs.push(nav);
	};
	Communicate.prototype.show = function() {
		var self = this;
		if(self.params.isSelf) {
			return;
		}
		if(self.params.isCard){
			var container = $("<ul class='sys_zone_card_shortcut_list'/>");
			$("#" + self.elementId).append(container);
			
			for(var i = 0 ; i < self.navs.length; i++) {
				~function(__i) {
					var title = self.titles[__i] ? " title='" + self.titles[__i] + "'" : "";
					var className = self.classNames[__i] ? self.classNames[__i] : "";
					var __$content = $("<li class='"+className+"'><a href='javascript:;'" + title
													+ "><img src='" + env.fn.formatUrl(self.icons[__i]) +"'/></a><span>"+self.titles[__i]+"</span></li>");
					container.append(__$content);
					self.navs[__i].onShow(__$content, self.params);
					if(self.navs[__i].onClick) {
						__$content.on("click", function() {
							self.navs[__i].onClick(__$content, self.params);
						});
					}
				}(i);
			}
		}else{
			var container = $("<ul class='lui_zone_contact clearfloat'/>");
			$("#" + self.elementId).append(container);
			
			for(var i = 0 ; i < self.navs.length; i++) {
				~function(__i) {
					var title = self.titles[__i] ? " title='" + self.titles[__i] + "'" : "";
					var __$content = $("<li class='lui_zone_contact_item'><a href='javascript:;'" + title
													+ "><img src='" + env.fn.formatUrl(self.icons[__i]) +"'></a></li>");
					container.append(__$content);
					self.navs[__i].onShow(__$content, self.params);
					if(self.navs[__i].onClick) {
						__$content.on("click", function() {
							self.navs[__i].onClick(__$content, self.params);
						});
					}
				}(i);
			}
			
		}
		
	};
	
	
	module.exports = Communicate;
});