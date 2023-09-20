define(function(require, exports, module) {	 
	var $ = require("lui/jquery");
	var dialog = require('lui/dialog');
	var Editbox = require('./Editbox');
	var Util = require('./Util');
	var msg = require("lang!sys-portal:desgin.msg");
	
	module.exports = Editbox.extend({
		initialize : function($super,config) {
			config.align = 'right';
			config.menuIconClass = 'widgetIcon';
			this.preview = config.preview ? new config.preview(this) : null;
			$super(config);
			if(this.element.children("script[type='text/config']").length > 0){
				this.setting = Util.toJSON($(this.element.children("script[type='text/config']")[0]).html());
			}
		},
		buildMenu : function($super,menuItems) {
			this.menuItems = menuItems || [];
			return $super(this.menuItems);
		},
		rebuildMenu : function(opt){
			this.eidtMenuItem.html(this.buildMenu(opt));
		},
		onShow : function($super){
			$super();
			if(!this.menuItems || this.menuItems.length == 0){
				this.editMenu.hide();
			}
		},
		destroy : function($super){
			this.editMenu.remove();
			this.element.remove();
			var x = $.inArray(this,this.parent.children);
			this.parent.children.splice(x,1);
			try{
				delete this;
			}catch(e){
				
			};
		},
		startup : function(){
			var self = this;
			this.element.bind("contextmenu",function(evt){ 
				self.editMenu.css({
					top : evt.pageY-10,
					left : evt.pageX-10
				});
				self.eidtMenuItem.show();
				evt.preventDefault();
				evt.stopPropagation();
			});
			this.element.addClass("widget");
			if(this.preview){
				this.preview.render();
			}
		}
	});
});