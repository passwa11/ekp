
/**
 * hash工具类<br>
 */
define(function(require, exports, module) {

	var hackKeys = ['j_start','j_target','j_path'];
	
	var hackParamsFromHash = function(){
		var paramsObject = fromHash(),
			hackParams = {};
		for(var i = 0; i < hackKeys.length; i++){
			var key = hackKeys[i];
			if(paramsObject[key]){
				hackParams[key] = paramsObject[key];
			}
		}
		return hackParams;
	};
	
	var fromHash = function() {

		var params = window.location.hash ? window.location.hash.substr(1)
				.split("&") : [], paramsObject = {};

		for (var i = 0; i < params.length; i++) {

			if (!params[i])
				continue;

			var a = params[i].split("=");
			paramsObject[a[0]] = decodeURIComponent(a[1]);
		}
		return paramsObject;
	};
	
	var toHash = function(params) {

		params = $.extend({}, hackParamsFromHash() ,params);
		
		var str = [];
		for ( var p in params) {
			if (params[p])
				str.push(p + "=" + encodeURIComponent(params[p]));
		}

		var scrollTop = $(document).scrollTop();
		if (!(window.top != window && str.length == 0)) {
			window.location.hash = str.join("&");
		}
		$(document).scrollTop(scrollTop);
	};

	var hash = {

		get : function(param) {

			var params = fromHash();
			if (param) {
				return params[param];
			} else {
				return params;
			}
		},

		add : function(newParams) {

			var params = fromHash();
			for ( var p in newParams) {
				params[p] = newParams[p];
			}
			toHash(params);

		},

		set : function(params) {

			toHash(params);
		},

		remove : function(removeParams) {

			removeParams = (typeof (removeParams) == 'string') ? [ removeParams ]
					: removeParams;
			var params = fromHash();
			for (var i = 0; i < removeParams.length; i++) {
				delete params[removeParams[i]];
			}
			toHash(params);
		},

		clear : function() {

			toHash({});
		}

	};

	module.exports = hash;

});
