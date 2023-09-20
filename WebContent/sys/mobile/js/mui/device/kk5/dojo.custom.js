require(['dojo/_base/lang','mui/device/kk5/xhr','mui/device/kk5/kkHttpRequest','lib/kk5/kk5'],function(lang,kkxhr,kkHttpRequest,kk5){
	
	window.dojo4OfflineKK = true;
	window.FormData = null;//KK AJAX接口不支持FormData，暂时屏蔽
	window.CustomHttpRequest = kkHttpRequest;//暴露一个到window下
	
	
	var cacheBust = dojoConfig.cacheBust;
	function fixupUrl(url){
		url += ""; 
		return url + (cacheBust ? ((/\?/.test(url) ? "&" : "?") + cacheBust) : "");
	}
	function makeError(error,info){
		return lang.mixin(new Error(error), {src:"dojoLoader", info:info});
	}
	//override getText for kk5
	require.getText = function(url, async, onLoad){
		try{
			var xhr = require.getXhr();
			xhr.open('GET', fixupUrl(url), false);
			xhr.send(null);
			if(xhr.status == 200 || (!location.host && !xhr.status)){
				if(onLoad){
					onLoad(xhr.responseText, async);
				}
			}else{
				throw makeError("xhrFailed", xhr.status);
			}
		}catch(e){
			//从KK端中获取资源失败,尝试从服务端获取
			xhr = kkxhr._create();
			xhr.open('GET', fixupUrl(url), false);
			xhr.__startTime = new Date().getTime();
			xhr.send(null);
			xhr.addEventListener('load', function(){
				if(xhr.status == 200 || (!location.host && !xhr.status)){
					if(onLoad){
						onLoad(xhr.responseText, async);
					}
				}else{
					throw makeError("xhrFailed", xhr.status);
				}	
			}, false);
			xhr.addEventListener('error', function(){
				throw makeError("xhrFailed", xhr.status);
			}, false);
		}
		return xhr.responseText;
	};
});