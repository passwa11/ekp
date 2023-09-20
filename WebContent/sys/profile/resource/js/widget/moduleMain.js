define(function(require, exports, module) {
	
	var $ = require('lui/jquery'),
		base = require('sys/profile/resource/js/widget/base'),
		topic = require('lui/topic'),
		env = require('lui/util/env');
	
	
	var ModuleMain = base.ModuleMain.extend({
		draw : function(evt){
			var that = this;
			var iframe = this.iframe = $('<iframe class="lui_profile_moduleMain_frame"  frameborder="no" border="0"/>'),
				url = env.fn.formatUrl(evt.url);
			iframe.appendTo(this.element);
			iframe.attr('id','moduleMain');
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
					}, 5);
				}
				//that._view.hasInited = true;
			});
			this.element.css({
				'width' : '100%',
				'height' : '100%'
			}).show();
			this.isDrawed = true;
			
		},
		get : function(evt){
			//添加此处，当打开系统概览页面隐藏左侧导航
			if(evt.key=="systemview"){
				$(top.document.body).removeClass("lui_porfile_hasNavLeft_body");
			}else{
				$(top.document.body).addClass("lui_porfile_hasNavLeft_body");
			}
			
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