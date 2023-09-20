/**
 * 模块,定义一个EKP模块
 * TODO
 * 	1、子模块
 */
define(function(require, exports, module) {
	
	var base = require('lui/base');
	var Controller = require('./controller/controller.js');//控制器
	var viewUtils = require('./view/view-utils'); //视图工具类
	
	var rootModule = null, //根模块
		modules = []; //模块列表
		
	var Module = base.Base.extend({
		
		initProps : function($super){
			$super();
			var config = this.config;
			this.$moduleName = config.$moduleName;
			this.$var = config.$var;
			this.$lang = config.$lang;
		},
		
		startup : function(){
			this.install();
		},
		
		install : function(){
			//绑定controller
			this.$controller = new Controller({
				$moduleName : this.$moduleName,
				$var : this.$var,
				$lang : this.$lang
			});
			this.$controller.startup();
			//绑定view
			LUI.ready($.proxy(function(){
				var viewName = this.$moduleName,
					view = viewUtils.getView(viewName);
				if(view){
					this.$view = view;
					return;
				}
				//TODO 目前游离module先绑定到root view上，理论上应该绑定到最近一次parse结束后该范围内的"root view"...
				this.$view = viewUtils.getRootView();
			},this));
		},
		
		uninstall : function(callback){
			if(this.$controller){
				this.$controller.uninstall(callback);
			}
		},
		
		controller : function(callback){
			this.$controller.install(callback);
		}
		
	});
	
	/**
	 * 注册模块
	 */
	var install = function(moduleName,evt){
		var module = rootModule = new Module({
			$moduleName : moduleName,
			$var : evt.$var,
			$lang : evt.$lang
		});
		module.startup();
		modules.push(module);
		return module;
	};
	
	/**
	 * 取消模块的注册
	 */
	var uninstall = function(moduleName,evt){
		var callback = evt.callback || function(){},
			module = null;
		for(var i = 0; i < modules.length; i++){
			if(modules[i].$moduleName == moduleName){
				module = modules[i];
				module.uninstall(callback);
				modules.splice(i, 1);
				break;
			}
		}
		return module;
	};
	
	/**
	 * 找到指定名字的模块
	 */
	var find = function(moduleName){
		for(var i = 0; i < modules.length; i++){
			if(modules[i].$moduleName == moduleName){
				return modules[i];
			}
		}
		return null;
	};
	
	var getRootModule = function(){
		return rootModule;
	};
	
	exports.install = install;
	exports.uninstall = uninstall;
	exports.getRootModule = getRootModule;
	exports.find = find;
	
});
