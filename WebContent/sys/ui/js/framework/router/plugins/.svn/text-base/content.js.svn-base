define(function(require, exports, module) {
	
	var $ = require('lui/jquery');
	var topic = require('lui/topic');
	var BasePlugin = require('./base');
	
	var ContentPlugin = BasePlugin.extend({
		action : function(options){
			var self = this;
			if(!$.isPlainObject(options)){
				return;
			}
			LUI.pageHide("_rIframe");
			var cri = $.extend({},options.cri, options.$paramsCri);
			if(options.$isInit){
				return;
			}
			topic.publish('spa.change.reset', {
				value : cri,
				target : self
			});
		}
	});
	
	module.exports = ContentPlugin;
	
});