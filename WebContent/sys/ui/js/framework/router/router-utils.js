/**
 * router工具类
 */
define(function(require, exports, module) {
	
	var Router = require('./router');
	var routerconst = require('./const');
	
	/**
	 * 获取全局Router对象
	 * @param create : 如果为空则创建一个router对象
	 */
	var getRouter = function(create){
		var routerName = routerconst['GLOBAL_ROUTER_NAME'],
			router = window[routerName];
		if(!router && create){
			router = new Router();
			router.startup();
			window[routerName] = router;
		}
		return router;
	};

	var getRouterTop = function(){
			var routerName = routerconst['GLOBAL_ROUTER_NAME'],router=null;
			var pWin = window;
			while(pWin){
				if(pWin["$ekp$global$router$"]){
					break;
				}
				pWin = pWin.parent;
			}
			if(pWin){
				router = pWin[routerName];
			}
			return router;
	};
	/**
	 * 指定path是否与当前j_path相等
	 */
	var equalPath = function(path){
		var $router = getRouter();
		if(!$router)
			return false;
		var curRoute = $router.curRoute;
		if(!curRoute)
			return false;
		var curPath = curRoute.fullPath;
		return curPath === path;
	}
	
	/**
	 * 指定path是否为当前j_path的祖先路由(j_path为path的嵌套路由结果)
	 */
	var isParentPath = function(path){
		var $router = getRouter();
		if(!$router)
			return false;
		var curRoute = $router.curRoute;
		if(!curRoute)
			return false;
		var	parentRoute = curRoute.parentRoute;
		while(parentRoute){
			var parentPath = parentRoute.fullPath;
			if(parentPath == path){
				return true;
			}
			parentRoute = parentRoute.parentRoute;
		}
		return false;
	};
	
	
	exports.getRouter = getRouter;
	exports.equalPath = equalPath;
	exports.isParentPath = isParentPath;
	exports.getRouterTop = getRouterTop;
	
});