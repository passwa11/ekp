define(function(require, exports, module) {
    var $ = null;
    if (window.$) {
        $ = window.$;
    } else {
        $ = require('lui/jquery');
    }
    var dialog = require('lui/dialog');
    var topic = require('lui/topic');

	window.InputCountEventList = {};
	window.InputCountEventList["pass"] = new Array;
	window.InputCountEventList["fail"] = new Array;
	
	window.inputCount = function (target, limit, countEl) {
		var el = $(target);
		var value = el.val();
	  	var num = value.replace(/[^\x00-\xff]/g, "***").length;
	  	if(num > limit) {
	  		var fail = window.InputCountEventList["fail"];
	  		if(fail.length == 0) {
	  			$(countEl).css({color: "#ff0000"});
	  			$(countEl).addClass("colorFail");
	  		} else {
	  			for(var i=0; i<fail.length; i++) {
	  				fail[i]();
		  		}
	  		}
	  	} else {
	  		var pass = window.InputCountEventList["pass"];
	  		if(pass.length == 0) {
	  			$(countEl).css({color: "#C5C5C5"});
	  			$(countEl).removeClass("colorFail");
	  		} else {
	  			for(var i=0; i<pass.length; i++) {
		  			pass[i]();
		  		}
	  		}
	  	}
	  	$(countEl).html("<span style='color:#333333'>"+num+"</span>"+" / "+limit);
	}
	
	
	
	window.initInputCountEvent = function() {
		checkAttrTriggerEvent('oninput', 'inputCount');
	}
	
	window.checkAttrTriggerEvent = function(attr, funcName) {
		$.each($("["+attr+"]"), function(index, item){
			var el = $(item);
			var attrStr = el.attr(attr);
			if(attrStr.indexOf(funcName) > -1){
				el.trigger(attr);
			}
		})
	}
	
	window.getByteLength = function(value) { 
	    var b = 0; var l = value.length;  
	    if(l) {  
	        for(var i = 0; i < l; i ++) { 
	            if(value.charCodeAt(i) > 255) { 
	                b += 2; 
	            }else {
	                b ++;  
	            }
	        }
	        return b; 
	    } else {
	        return 0;  
	    }
	}
	
})
