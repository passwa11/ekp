define(function(require, exports, module) {
	
	var Class = require("lui/Class");
	var Evented = require('lui/Evented');
	var topic = require('lui/topic');
	
	var BaseMap = new Class.create(Evented, {
		
		initialize : function(config){
			this.context = config.context;
			var context = this.context,
				uuId = context.uuId,
				self = this;
			/*topic.channel(uuId).subscribe('sys.attend.map.dialog.close',function(){
				self.destory();
			});*/
		},
		
		render : function(){
			// for override
		},
		
		destory : function(){
			// for override if need
		},
		
		loadScript : function(url,callback){
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
		
	});
	
	module.exports = BaseMap;
	
});