/**
* @ignore  =====================================================================================
* @desc    门户页面容器组件
* @author  李勇
* @date    2013-09-16
* @ignore  =====================================================================================
*/
define(function(require, exports, module) {	 
	var $ = require("lui/jquery");
	var dialog = require('lui/dialog');
	var Editbox = require('./Editbox');
	var DragDrop = require('lui/dragdrop');
	var Util = require('./Util');
	var msg = require("lang!sys-portal:desgin.msg");
	var Container = Editbox.extend({
		initialize : function($super,config) {
			$super(config);
			var conf = Util.toJSON(unescape(this.element.attr("data-config")));
			this.config.vSpacing = conf.vSpacing;
		},
		buildMenu : function($super) {
			var self = this; 
			this.menuItems = [
								Util.addBeforeVBox(self), // 上方添加容器
								Util.addAfterVBox(self),  // 下方添加容器
			                 	Util.addVBox(self),       // 添加子级容器
			                 	Util.addWidget(self),     // 添加部件
			                 	Util.configVBox(self),    // 容器配置
			                 	{
			                 		"icon":"editMenuIconDeleteRow",
									"text":""+msg['desgin.msg.deleterow'], // 删除行
									fn:function(){
										dialog.confirm(msg['desgin.msg.cdeleterow'],function(value){
											if(value){
												self.parent.destroy();
											}
										});
									}
			                 	},
								{
									"icon":"editMenuIconDeleteCol",
									"text":""+msg['desgin.msg.deletecol'], // 删除列
									fn:function(){
										dialog.confirm(msg['desgin.msg.cdeletecol'],function(value){
											if(value){
												self.destroy(true);												
											}
										})
									}
								}
							];
		 
			return $super(this.menuItems);
		},
		destroy : function(delColumn){
			this.editMenu.remove();
			for(var i=this.children.length-1;i>=0;i=this.children.length-1){
				this.children[i].destroy();				
			}
			var x = $.inArray(this,this.parent.children);
			if(this.spacingElement != null){
				//要删除的列 不是第一列
				this.spacingElement.remove();
				this.spacingElement = null;
			}else{
				//删除的是第一列,并且该容器是多列容器，要删除该列后面的容器间距
				if(this.parent.children.length > 1){
					this.parent.children[x+1].spacingElement.remove();
					this.parent.children[x+1].spacingElement=null;
				}
			}			
			this.element.remove();
			this.parent.children.splice(x,1);
			if(delColumn!=null && delColumn===true){
				if(this.parent.children.length <=0)
					this.parent.destroy();
				else
					this.parent.alculateWidth();
			}
			try{delete this;}catch(e){};
		},
		startup : function($super){
			var self = this;
			this.spacingElement = (function(){
				if(self.element.prev("td").is(".containerSpacing")){ 
					return self.element.prev("td");
				}else{					 
					return null;
				}
			})();
			//this.dropElement = $("<div class='widgetDock' vSpacing='"+this.config.vSpacing+"'></div>").css("height",this.config.vSpacing);
			this.dropElement = (function(){
				if(self.element.children().length >0){
					var x = $(self.element.children()[0]);
					if(x.is(".widgetDock")){
						return x;
					}else{
						x = $("<div class='widgetDock'></div>").css("height",self.config.vSpacing);
						self.element.prepend(x);
						return x;
					}
				}else{
					var x = $("<div class='widgetDock'></div>").css("height",self.config.vSpacing);
					self.element.prepend(x);
					return x;
				}
			})();
			this.drop = new DragDrop.Droppable({
				body:self.body,
				element:self.dropElement,
				activeClass: "dropActiveClass",
				hoverClass: "dropHoverClass",
				over: function() {
					//this.element.height(30);
				},
				out : function() {
					this.element.css("height",self.config.vSpacing);
				},
				drop: function(moveObj) {
					moveObj.parent.children.splice($.inArray(moveObj,moveObj.parent.children),1);
					moveObj.parent = self;
					self.children.push(moveObj);
					this.element.after(moveObj.dropElement.css("height",self.config.vSpacing));
					this.element.after(moveObj.element);
				}
			});
		}
	}); 
	module.exports = Container;
});