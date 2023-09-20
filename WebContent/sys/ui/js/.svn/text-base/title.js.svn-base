define(function(require, exports, module) {
	
	var $ = require('lui/jquery');
	var base = require('lui/base');
	var topic = require('lui/topic');
	
	var Title = base.Base.extend({
		
		startup : function(){
			var config = this.config,
				element = config.element,
				title = $(element).html();
			var self = this;
			if(window.LUI.luihasReady){
				self.setTitle(title);
			}else{
				LUI.ready(function(){
					self.setTitle(title);
				});
			}
		},
		
		setTitle : function(title){
			var mode = LUI.pageMode();
			if(mode == 'quick'){//暂时只支持在极速门户下该组件生效
				LUI.setPageTitle(title);
				topic.publish('lui.page.title.changed',{
					title : title
				});
			}
		}
		
	});
	
	var portalPageTitle = base.Base.extend({
		startup : function(){
			var config = this.config,
				element = config.element,
				pageTitle = $(element).html();
			var self = this;
			if(window.LUI.luihasReady){
				topic.publish('lui.portalPage.title.changed',{
					title : pageTitle
				});
			}else{
				LUI.ready(function(){
					topic.publish('lui.portalPage.title.changed',{
						title : pageTitle
					});
				});
			}
		}
	});
	
	exports.Title = Title;
	exports.portalPageTitle = portalPageTitle;
});