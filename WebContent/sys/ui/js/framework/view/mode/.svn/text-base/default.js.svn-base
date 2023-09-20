/**
 * 默认模式
 */
define(function(require, exports, module) {
	
	var $ = require('lui/jquery');
	
	var BaseMode = require('./base'); 
	
	var DefaultMode = BaseMode.extend({
		
		draw : function(){
			$(this.view.element).addClass('lui_portal_container');
			// TODO 默认模式下渲染
			topic.publish('lui.page.default.draw');
		},
		
		open : function(url, target, features, customHashParams){
			var targets = '_blank;_self;_parent;_top';
			if(targets.indexOf(target) == -1){
				target = '_top';
			}
			window.open(url, target);
		}
		
	});
	
	
	module.exports = DefaultMode;
	
	
});