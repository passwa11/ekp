/**
 * 路由信息表生成
 */
define(function(require, exports, module) {
	
	function createRouteMap(routes){
		var routeMap = {};
		for(var i = 0; i < routes.length; i++){
			var routeConfig = routes[i];
			_createRoute(routeMap, routeConfig);
		}
		return routeMap;
	};
	
	function _createRoute(routeMap, routeConifg, parentRouteConfig){
		var parentFullPath = parentRouteConfig ? parentRouteConfig.fullPath : null,
			fullPath = _normalizePath(routeConifg.path, parentFullPath),
			route = {
				path : routeConifg.path,
				fullPath : fullPath,
				action : _findAction(routeConifg)
			};
		if(parentRouteConfig){
			route.parentRoute = parentRouteConfig;
		}
		if(routeConifg.children){
			for(var i = 0; i< routeConifg.children.length; i++){
				_createRoute(routeMap, routeConifg.children[i], route);
			}
		}
		routeMap[route.fullPath] = route;
		return route;
	}
	
	function _findAction(routeConifg){
		if(!routeConifg.children || routeConifg.children.length == 0){
			return routeConifg.action || function(){};
		}
		var subRouteConfig = routeConifg.children[0];
		return _findAction(subRouteConfig);
	}
	
	function _normalizePath(path ,parentPath){
		if(!parentPath){
			return path;
		}
		var newPath = parentPath + path;
		return newPath.replace(/\/\//g, '/');
	}
	
	exports.createRouteMap = createRouteMap;
	
});