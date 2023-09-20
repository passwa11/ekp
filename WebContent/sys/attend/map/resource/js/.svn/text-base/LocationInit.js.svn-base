define( function(require, exports, module) {
	
	var $ = require('lui/jquery');
	var Location = require('./Location.js');
	
	module.exports = function(cfg){
		var container = $('[data-location-container="'+ cfg.propertyName +'"][mark-loaded!="true"]');
		if(container){
			var location = new Location.Location(cfg);
			location.startup();
			location.draw();
			container.append(location.element);
			if(cfg.propertyName.indexOf('!{index}')==-1){//兼容明细表
				container.attr('mark-loaded','true');
			}
		}
	};
});