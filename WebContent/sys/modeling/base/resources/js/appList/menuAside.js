/**
 * 左侧栏
 */
define(function(require, exports, module) {
	
	var $ = require("lui/jquery");
	var base = require("lui/base");
	var topic = require("lui/topic");
	var modelingLang = require("lang!sys-modeling-base");
	var MenuAside = base.DataView.extend({
		
		startup : function($super,cfg){
			$super(cfg);
			var self = this;
			topic.channel("modelingAppList").subscribe("app.update",function(){
				if(self.currentType){
					self.currentType.trigger($.Event("click"))
				}
			});
		},
		
		// 渲染完毕之后添加事件
		doRender : function($super,html){
			$super(html);
			var self = this;
			self.currentType=null ;
			// 添加事件
			self.element.find("li[data-aside-type]").each(function(index,dom){
				$(dom).on("click",function(){
					self.currentType = $(dom);
					$(dom).siblings().removeClass("active");
					$(dom).addClass("active");
					//判断所有应用个数
					var type = $(dom).attr("data-aside-type");
					if(LUI("menuAside").data[type] <= 0){//空页面
						if(type == "allApps" || type == "myApps"){//所有应用或者我创建的
							$("#allAppsMain").show();
							$("#editorAppsMain").hide();
							$("#defaultMain").hide();
							$("#installedAppsMain").hide();
						}else if(type == "editorApps"){
							$("#editorAppsMain").show();
							$("#installedAppsMain").hide();
							$("#allAppsMain").hide();
							$("#defaultMain").hide();
						}else if(type == "installedApps"){
							$("#installedAppsMain").show();
							$("#editorAppsMain").hide();
							$("#allAppsMain").hide();
							$("#defaultMain").hide();
						}
					}else{//非空页面
						$("#editorAppsMain").hide();
						$("#allAppsMain").hide();
						$("#installedAppsMain").hide();
						$("#defaultMain").show();
						// 发布事件
						topic.channel("modelingAppList").publish("app.type.change",{type: $(dom).attr("data-aside-type")});
					}
				});
			});
			
			// 首次自动选中第一项
			self.triggerByType("allApps");
		},
		
		reRender : function(){
			this.source.get();
		},
		
		triggerByType : function(type){
			this.element.find("li[data-aside-type='"+ type +"']").trigger($.Event("click"));
		},
		getModelingLang :function (){
			return modelingLang;
		}
		
	})
	
	exports.MenuAside = MenuAside;
})