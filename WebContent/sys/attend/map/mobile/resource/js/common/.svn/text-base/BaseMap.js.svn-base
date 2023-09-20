/**
 * 基类地图接口入口
 */
define([], function() {

	return base = {
		
		_loadScript : function(url, callback){
			if(window['_$isMapJsLoaded$_']){
				callback && callback();
				return;
			}
	        var head = document.getElementsByTagName('head')[0];
	        var script = document.createElement('script');
	        script.type = 'text/javascript';
	        script.src = url;
	        head.appendChild(script);
	        script.onload = script.onreadystatechange = function () {
	            if ((!this.readyState || this.readyState === "loaded" || this.readyState === "complete")) {
	                callback && callback();
	                window['_$isMapJsLoaded$_'] = true;
	                script.onload = script.onreadystatechange = null;
	            }
	        };
		}
		
	};

});