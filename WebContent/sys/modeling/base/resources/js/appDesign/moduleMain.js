define(function(require, exports, module) {
	
	var $ = require('lui/jquery'),
		base = require('lui/base'),
		topic = require('lui/topic'),
		env = require('lui/util/env');
	var dialog = require('lui/dialog');
	
	var ModuleMain = base.Container.extend({
		draw : function(evt){
			if(evt && evt.url){
				window.ModuleMain_load = dialog.loading();
				var that = this;
				var iframe = this.iframe = $('<iframe class="lui_app_moduleMain_frame"  frameborder="no" border="0"/>'),
					url = env.fn.formatUrl(evt.url);
				iframe.appendTo(this.element);
				iframe.attr('src',url);
				iframe.load(function(){
					var contentWindow = iframe[0].contentWindow;
					if(contentWindow.LUI){
						// #41463 IE浏览器，通过URL跳转无效果。解决方法：通过延时发布事件
						setTimeout(function() {
							if(contentWindow.LUI.luihasReady){
								contentWindow.LUI.fire({ type: "topic", name: "sys.profile.moduleMain.loaded" , key : that.selectedItem ,parentWin : window });
							}else{
								contentWindow.LUI.ready((function(cw){
									return function(){
										cw.LUI.fire({ type: "topic", name: "sys.profile.moduleMain.loaded" , key : that.selectedItem ,parentWin : window });
									};
								})(contentWindow));
							}
							window.ModuleMain_load.hide();
						}, 5);
					}else {
						window.ModuleMain_load.hide();
					}
				});
				this.element.show();
				this.isDrawed = true;				
			}
			
		},
		get : function(evt){
			if(!this.isDrawed){
				this.draw(evt);
			}else{
				var url = env.fn.formatUrl(evt.url);
				this.iframe.attr('src',url);
			}
		}
	});
	
	exports.ModuleMain = ModuleMain;
	
});