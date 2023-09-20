/**
 * 控制器
 */
define(function(require, exports, module) {
	
	var base = require('lui/base');
	var env = require('lui/util/env');
	var routerUtils = require('../router/router-utils');
	
	var Controller = base.Base.extend({
		
		initProps : function($super){
			$super();
			var config = this.config;
			this.$moduleName = config.$moduleName;
			this.$var = config.$var || {};
			this.$lang = config.$lang || {};
			this.$function = {};
		},
		
		install : function(callback){
			if(Object.isFunction(callback)){
				var argumentNames = callback.argumentNames(),
					_arguments = [];
				for(var i = 0; i < argumentNames.length; i++){
					var argumentName = argumentNames[i],
						_argument = null;
					switch(argumentName){
						case '$moduleName' : 
							_argument = this.$moduleName;
							break;
						case '$var' : 
							this.$var.$contextPath = env.fn.getConfig().contextPath;
							_argument = this.$var;
							break;
						case '$lang' :
							_argument = this.$lang;
							break;
						case '$function' : 
							var moduleAPI = window.moduleAPI = window.moduleAPI || {},
								curModuleAPI = moduleAPI[this.$moduleName] = this.$function;
							_argument = curModuleAPI;
							break;
						case '$router' : 
							_argument = routerUtils.getRouter(true);
							break;
						default : 
							break;
					}
					_arguments.push(_argument);
				}
				return callback.apply(this, _arguments);
			}
		},
		
		uninstall : function(callback){
			//TODO
		}
		
	});
	
	module.exports = Controller;
});