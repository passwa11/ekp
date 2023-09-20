/**
 * 返回顶部适配器
 */
define(function(require, exports, module) {
	
	var $ = require('lui/jquery');
	var topic = require('lui/topic');
	
	var topAdapter = {
		
		initProps : function($super, _config){
			var self = this;
			$super(_config);
			topic.subscribe('lui.page.open',function(evt){
				if(self.gototop){
					self.__elementHide();
				}
				if(evt.target !== '_content'){
					return;
				}
				var content = $('[data-lui-page-type="content"]');
				if(content && content.length > 0){
					content.bind('scroll',function(){
						self.isShowInQuickMode();
					});
				}
			});
		},
		
		isShowInQuickMode : function(){
			var self = this;
			var content = $('[data-lui-page-type="content"]');
			if ($(content).scrollTop() > 30) {
				if (!$("html,body").is(":animated")) {
					if (self.gototop.css('visibility') != 'visible') {
						self.__elementShow();
					}
				} else {
					// 动画中
					// $(document.body).stop();
				}
			} else {
				self.__elementHide();
			}
		},
		
		__toTop : function($super){
			$super();
			var content = $('[data-lui-page-type="content"]');
			if(content && content.length > 0){
				setTimeout(function(){
					content.animate({
						scrollTop : 0
					}, 400, function() {
						content.scrollTop(0);
					});
				},200);
			}
		}
			
	};
	
	module.exports = topAdapter;
	
});
