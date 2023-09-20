/**
 * Router组件
 */
define(function(require, exports, module) {
	
	var $ = require('lui/jquery');
	var base = require('lui/base');
	var topic = require('lui/topic');
	var routerconst = require('./const');
	var routeMapUtils = require('./utils/create-route-map');
	
	var noop = function(){};
	
	var Router = base.Base.extend({
		
		//路由定义
		define : function(options){
			this.options = options;
			this._startpath = options.startpath;
			this.routeMap = routeMapUtils.createRouteMap(options.routes || []);
			var startPath = this._getHashPath();
			if(!startPath){
				startPath = options.startpath;
			}
			if(startPath){
				this.push(startPath,{ $isInit : true },null,null);
			}
		},
		
		//执行一条路由
		push : function(path, params ,onComplete, onError){
			path = path || this._startpath;
			var self = this;
			var route = this.routeMap[path],
				params = params || {};
			if(!route){
				onError && onError();
				return;
			}
			if(LUI.luihasReady){
				self._push(path, params ,onComplete, onError);
			}else{
				LUI.ready(function(){
					self._push(path, params ,onComplete, onError);
				});
			}
			this.curRoute = route;
		},
		
		//添加路由
		addRoutes : function(routes){
			this.routeMap = this.routeMap || {};
			if(routes && !Object.isArray(routes)){
				routes = [routes];
			}
			$.extend(this.routeMap, routeMapUtils.createRouteMap(routes || []) );
		},
		
		//获得指定路由的查询参数
		getCriValue : function(path){
			path = path || this._getHashPath() || this._startpath;
			var defer = $.Deferred(),
				route = this.routeMap[path],
				action = route && route.action || function(){};
			if(typeof(action) == 'function'){
				defer.resolve({});
			}else{
				var pluginType = action.type,
					pluginPath = 'lui/framework/router/plugins/' + pluginType;
				seajs.use([pluginPath],function(pluginClz){
					var plugin = new pluginClz();
					var value = plugin.getCriValue && plugin.getCriValue(action.options);
					defer.resolve(value || {});
				});
			}
			return defer.promise();
		},
		
		_push : function(path, params ,onComplete, onError){
			var self = this;
			var route = this.routeMap[path],
				action = route.action,
				params = params || {};
			if(!params.$isInit){
				this._setHashPath(route.fullPath);
			}
			if(typeof(action) == 'function'){
				var promise = action && action(params);
				if(promise && promise.then){
					promise.then(function(){
						onComplete && onComplete();
					});
					return;
				}
				onComplete && onComplete();
			}else{
				var pluginType = action.type,
					pluginPath = 'lui/framework/router/plugins/' + pluginType;
				seajs.use([pluginPath],function(pluginClz){
					var plugin = new pluginClz();
					var $initCri = self._getHashParams(window), //hash上的cri参数
						$paramsCri = params.cri,  //params上的cri参数
						_params = $.extend({
							$initCri : $initCri,
							$paramsCri : $paramsCri
						} , params, action.options);
					plugin.action && plugin.action(_params);
					onComplete && onComplete();
				});
			}
		},
		
		_getHashParams : function(win){
			win = win || Com_Parameter.top || window.top;
			var hash = win.location.hash,
				params = hash ? hash.substr(1).split("&") : [],
				paramObject = {};
			for(var i = 0; i < params.length; i++){
				var argObj = params[i].split("=");
				if(argObj.length == 2){
					paramObject[argObj[0]] = decodeURIComponent(argObj[1]);
				}
			}
			return paramObject;
		},
		
		_getHashPath : function(){
			var topParamObject = this._getHashParams(Com_Parameter.top || window.top),
				winParamObject = this._getHashParams(window)
			if(topParamObject['j_path']){
				return topParamObject['j_path'];
			}
			if(winParamObject['j_path']){
				return winParamObject['j_path'];
			}
			return null;
		},
		
		_setHashPath : function(path){
			var hashParamers = ['j_start','j_target'].join(';'); //bad hack
			var hash = window.location.hash,
				params = hash ? hash.substr(1).split("&") : [],
				str = [];
			for(var i = 0; i < params.length; i++){
				var argObj = params[i].split("=");
				if(argObj.length == 2 
						&& hashParamers.indexOf(argObj[0]) > -1){
					str.push(argObj[0] + "=" + argObj[1]);
				}
			}
			str.push('j_path=' + encodeURIComponent(path) );
			window.location.hash = str.join('&');
			topic.publish('spa.change.hash',{
				value : {
					'j_path' : path
				}
			});
		}
		
	});
	
	module.exports = Router;
	
});