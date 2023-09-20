define(function(require, exports, module) {
	
	var $ = require('lui/jquery');
	var topic = require('lui/topic');
	var BasePlugin = require('./base');
	
	var PageOpenPlugin = BasePlugin.extend({
		action : function(options){
			var self = this;
			if(!$.isPlainObject(options)){
				return;
			}
			LUI.pageOpen(options.url,options.target || '_rIframe', options);
		}
	});
	
	module.exports = PageOpenPlugin;
	
});