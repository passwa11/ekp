/**
 * 视图模式基类
 */
define(function(require, exports, module) {
	
	var base = require('lui/base');
	
	var BaseMode = base.Base.extend({
		
		startPage : null, 
		
		startTarget : null, 
		
		initProps : function($super,_config){
			$super(_config);
			this.config = _config || {};
			this.view = this.config.view;
			this.startPage = this.view.startPage;
			this.startTarget = this.view.startTarget;
		},
		
		/**
		 * 绘制视图
		 */
		draw : function(){
			//for override
		},
		
		/**
		 * 切换视图
		 */
		open : function(url, target, features){
			//for override
		},
		
		/**
		 * 隐藏视图
		 */
		hide : function(target, features){
			//for override
		},
		
		/**
		 * 调整视图
		 */
		resize : function(evt){
			//for override
		},
		
		/**
		 * 设置页面标题
		 */
		setPageTitle : function(){
			//for override
		}
		
	});
	
	module.exports = BaseMode;
	
});