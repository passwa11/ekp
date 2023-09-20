define(function(require, exports, module) {
	var $ = require("lui/jquery");
	var confg = {
			leftShow : false,
			rightShow : false,
			leftPadding : 0,
			rightPadding : 0,
			right : false,
			left : false
	}
	
	
	function slideInit(cfg) {
		config = $.extend({}, cfg);
		var contentEle = $("[data-lui-silde-content]");
		var mainEle = $("[data-lui-mark-main]");
		var btn = $("[data-lui-slide-btn]");
		btn.on("click", function(evt) {
			
			var dir = $(evt.target).attr("data-lui-slide-btn");
			
			if(config[dir]) {
				if(config[dir + "Show"]) {
					//收缩变展开
					mainEle.addClass("lui-slide-main-animate-ing");
					
					var barCfg = {};
					barCfg[dir] = (-config[dir + "Padding"] + "px");
					$("[data-lui-slide-bar='"+ dir +"']").animate(barCfg, 300);
					
					var contentCfg = {}
					contentCfg["padding-" + dir] = 0;
					contentEle.animate(contentCfg, 300 , function() {
						mainEle.addClass("lui-slide-main-spread");
						mainEle.removeClass("lui-slide-main-animate-ing");
					});
					
					config[dir + "Show"] = false;
				} else {
					//展开变收缩
					mainEle.removeClass("lui-slide-main-spread");
					mainEle.addClass("lui-slide-main-animate-ing");
					
					var barCfg = {};
					barCfg[dir] = 0;
					$("[data-lui-slide-bar='"+ dir +"']").animate(barCfg  , 300);
						
					var contentCfg = {}
					contentCfg["padding-" + dir] = config[dir + "Padding"] + "px";
					contentEle.animate(contentCfg,  300, function() {
						mainEle.removeClass("lui-slide-main-animate-ing");
					});
					
					config[dir + "Show"] = true;
				}
			}
		}); 
		
	}
			

	module.exports = slideInit;
});
