/**
 * view工具类
 */
define(function(require, exports, module) {
	
	var viewconst = require('./const');
	
	/**
	 * 根据视图名获取对应视图
	 */
	var getView = function(viewName){
		var viewId = viewconst.ROOT_PREFIX + viewName,
			view = LUI(viewId);
		if(view){
			return view;
		}
		return null;
	};
	
	/**
	 * 获取根视图
	 */
	var getRootView = function(){
		
	};
	
	exports.getView = getView;
	exports.getRootView = getRootView;
	
});