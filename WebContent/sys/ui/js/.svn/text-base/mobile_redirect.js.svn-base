(function() {
	
	var getParams = function() {
		
		var params = window.location.search ? window.location.search.substr(1).split("&") : [];
		var hashParams = window.location.hash ? window.location.hash.substr(1).split("&") : [];
		
		if(hashParams && hashParams.length > 0) {
			params = params.concat(hashParams);
		}
		
		var paramsObject = {};

		for (var i = 0; i < params.length; i++) {
			if (!params[i])
				continue;

			var a = params[i].split("=");
			if(a && a.length == 2) {
				paramsObject[a[0]] = decodeURIComponent(a[1]);
			}
		}
		return paramsObject;
	};
	
	
	var variableResolver = function(str, data) {  
	     return str.replace(/\!\{([\w\.]*)\}/gi, function (_var, _key) {
	          var value = data[_key];  
	          
	          if (Object.prototype.toString.call(value) === "[object Array]" && value.length > 0) {
	        	  value = value[0];  
	          }
				
	          return (value === null || value === undefined) ? "" : value;  
	     });
	}; 
	
	var redirect = function(mobileurl, contextPath) {
		var redirectUrl = mobileurl, params = getParams();
		
		//兼容一些已经配好的链接，categoryId和docCategory认为是相同的
		if(params.categoryId && !params.docCategory) {
			params.docCategory = params.categoryId;
		}
		if(params.docCategory && !params.categoryId) {
			params.categoryId = params.docCategory;
		}
		
		// cri.[channel].q OR cri.q
		var regx = /cri([\w\.]*)\.q/gi;
		for (var key in params){
			if(regx.test(key)){
				var criArr = params[key].split(";");
				var newCri = "";
				for(var i=0; i<criArr.length; i++) {
					newCri += "q."+criArr[i]+";";
				}
				params[key] = newCri;;
			}
	    }
		if(params) {
			redirectUrl = variableResolver(redirectUrl, params);
		}
		
		window.location.replace(contextPath + redirectUrl);
	}
	
	window.luiMobileRedirect = redirect;
})();