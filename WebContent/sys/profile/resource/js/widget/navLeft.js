define( function(require, exports, module) {
	
	var $ = require('lui/jquery'),
		base = require('sys/profile/resource/js/widget/base'),
		topic = require('lui/topic');
	
	var NavLeft = base.NavLeft.extend({
		
		initProps : function($super,_config){
			$super(_config);
			this.config = _config;
		},
		get : function(evt){
			var key = evt.key,
				source = this.source,
				url = Com_SetUrlParameter(source.url,"type",key);
			source.setUrl(url);
			if(!this.isDrawed){
				this.draw();
			}else{
				source.get();
			}
		},
		doRender : function($super,html){
			$super(html);
			var scrolldom = this.element.parent();
			scrolldom.scrollTop(0);
		}
	});
	
	exports.NavLeft = NavLeft;
	
});