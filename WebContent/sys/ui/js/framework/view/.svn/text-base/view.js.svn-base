/**
 * 视图
 */
define(function(require, exports, module) {
	
	var $ = require("lui/jquery");
	var base = require("lui/base");
	var Spa = require('lui/spa');
	var env = require('lui/util/env');
	var topic = require('lui/topic');
	
	var viewconst = require('./const');
	var DefaultMode = require('./mode/default');
	var QuickMode = require('./mode/quick');
	
	var View = base.Container.extend({
		
		viewMode : null, //视图内部组件（由模式决定）
		
		initProps : function($super,_config){
			$super(_config);
			this.config = _config || {};
			this.name = this.config.name;
			this.mode = this.config.mode;
			this.startPage = this.config.startPage;
			this.startTarget =this.config.startTarget;
			this._setId();
		},
		
		startup : function($super){
			$super();
			if(this.mode == 'quick'){
				var spa = new Spa.Spa(); //极速模式启动SPA
				spa.startup();
				this.viewMode = new QuickMode({
					view : this
				});
			}else{ 
				this.viewMode = new DefaultMode({
					view : this
				});
			}
			this.viewMode.startup();
			if(this.isRootView){
				topic.subscribe('lui.page.resize',this.resize,this);
			}
		},
		
		/**
		 * 绘制组件
		 */
		draw : function(){
			var self = this;
			if(this.isDrawed)
				return;
			this.viewMode.draw();
			this.viewMode.resize();  
			this.element.show();
			this.isDrawed = true;
			return this;
		},
		

		/**
		* 打开页面
		* @param url {String} 页面URL
		* @param target {String} 打开方式 _blank(打开新窗口)、_self(刷新当前页面)、_content(刷新content)、_iframe(刷新Iframe)、_rIframe(刷新右侧Iframe)
		* @param features {Object} 特性，如{ transition : 'slideDown' , pageClass : 'my custom pageClass' }
		* @param customHashParams {Object} 自定义hash参数，除j_start、j_target、j_path等系统公共hash参数之外的自定义hash参数，参数名必须以"c_"作为起始，如c_app_title、c_app_url
		* @return
		*/
		open : function(url, target, features, customHashParams ){
			var	evt = {
				url : url,
				target : target,
				features : features,
				view : this,
				mode : this.mode
			},
				promise = this.viewMode.open(url, target, features, customHashParams);
			if(promise && promise.then){
				promise.then(function(){
					topic.publish('lui.page.open',evt);
				});
			}else{
				topic.publish('lui.page.open',evt);
			}
			return this;
		},
		
		/**
		 * 隐藏页面
		 */
		hide : function(target, features){
			this.viewMode.hide(target, features);
			return this;
		},
		
		/**
		 * 页面调整
		 */
		resize : function(evt){
			evt = evt || {};
			this.viewMode.resize(evt);
			return this;
		},
		
		setPageTitle : function(value){
			if(!value){
				return;
			}
			this.viewMode.setPageTitle(value);
			return this;
		},
		
		_setId : function(){
			if(this.name){
				this.cid = viewconst.ROOT_PREFIX + this.name;
				return;
			}
			//申请成为根节点
			var element = this.element,
				parentView = element.parent('[data-lui-type="sys/ui/js/framework/view/view!View"]');
			if(parentView.length > 0){
				//申请失败
				this.name = '$none' + new Date().getTime();
				this.cid = viewconst.ROOT_PREFIX + this.name;
				return;
			}
			this.isRootView = true;
			this.name = viewconst.ROOT_VIEW_NAME;
			this.cid = viewconst.ROOT_PREFIX + viewconst.ROOT_VIEW_NAME;
		},
		
		destory : function($super){
			if(this.isRootView){
				topic.unsubscribe('lui.page.resize',this.resize,this);
			}
			$super();
		}
		
	});
	
	if(window.LUI){
		window.LUI.$GetRootView$ = function(){
			return LUI(viewconst.ROOT_PREFIX + viewconst.ROOT_VIEW_NAME);
		};
	}
	
	exports.View = View;
	
});
