
/**
 * window.onload后面的定义会覆盖前面的定义，导致前面的不会执行。
 * 此工具类是将所有的onload事件组合在一起；
 * @param require
 * @param exports
 * @param module
 * @returns
 */
define(function(require, exports, module) {
	
	var $ = require('lui/jquery');  
	exports.resetOnLoad = function(callBack) {
		var oldonload = window.onload;
		if (typeof window.onload != 'function') {
			window.onload = callBack;
		} else {
			window.onload = function() {
				oldonload();
				callBack();
			}
		} 
	} 
});