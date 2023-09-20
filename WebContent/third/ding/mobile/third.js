/**
 * @Deprecated
 * 第三方JS-SDK重构后此文件废弃,JS-SDK的授权移植mui/device/ding/adapter.js
 * 
 * JS SDK 授权初始化
 */
define(['https://g.alicdn.com/ilw/ding/0.8.9/scripts/dingtalk.js','dojo/request','mui/device/device'],
		function(dd,request,device){
	
	var third = new Object(),//对外暴露
	 	deviceType = device.getClientType();
	
	/**
	 * 钉钉JS SDK授权初始化
	 */
	function init(signInfo){
		if (!dd) return;
		if (!signInfo) {
			var url = dojoConfig.baseUrl + 'third/ding/jsapi.do?method=jsapiSignature',
				option = {
					data : {
						url : location.href
					},
					handleAs : 'json'
				};
			request.post(url ,option)
				.response
				.then(function(rtn){
					var data = rtn.data;
					if(dd && data){
						_config(data);
					}
				});
		}else{
			_config(signInfo);
		}
	}
	
	function _config(signInfo){
		dd.config({
			appId: signInfo.appId,
			agentId:signInfo.appId,
			corpId: signInfo.corpId,
		    timeStamp: signInfo.timeStamp,
		    nonceStr: signInfo.nonceStr,
		    signature: signInfo.signature,
		    jsApiList: ['biz.chat.openSingleChat','biz.util.open']
		});
		dd.ready(function(){
			dojoConfig.ddjssdk = true;
			require(['mui/device/adapter','mui/device/ding/adapterEx','dojo/_base/lang'],function(defaultApi,dingapiEx,lang){
                lang.mixin(defaultApi, dingapiEx);
            });
		});
//		dd.error(function(errorMsg){
//			alert('errMsg:' + errorMsg['message']);
//		});
	}
	
	third.init = init;
	return third;
});
