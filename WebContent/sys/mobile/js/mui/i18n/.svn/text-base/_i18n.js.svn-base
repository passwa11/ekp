/**
 * 压缩后代理请求国际化文件
 */
define([], function() {
	function getMessage(id, load) {
		require([ id + "_" + dojoConfig.locale + "._min_" ], load)
	}
	return {
		load : function(id, require, load) {
			getMessage(id, load)
		}
	}
})
