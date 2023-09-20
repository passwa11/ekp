/**
 * JS SDK 授权初始化
 */
define(['https://res.wx.qq.com/open/js/jweixin-1.0.0.js','dojo/request','mui/device/device'],
		function(wx,request,device){
	
	var third = new Object(),//对外暴露
	 	deviceType = device.getClientType();
	
	/**
	 * 微信JS SDK授权初始化
	 */
	function init(signInfo){
		//微信模式下
		if (!wx) return;
		if (!signInfo) {
			var url = dojoConfig.baseUrl + 'third/wxwork/jsapi/wxJsapi.do?method=jsapiSignature',
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
					if(wx && data && data.appId){
						_config(data);
					}
				});
		}else{
			_config(signInfo);
		}
	}
	
	function _config(signInfo){
		wx.config({
			appId : signInfo.appId,
			timestamp : signInfo.timestamp,
			nonceStr : signInfo.noncestr,
			signature : signInfo.signature,
			jsApiList : ['startRecord','stopRecord','translateVoice','scanQRCode','getLocation']
		});
		wx.ready(function(){
			dojoConfig.wxjssdk = true;
			require(['mui/device/adapter','mui/device/weixin/adapterEx','dojo/_base/lang'],function(defaultApi,adapterEx,lang){
                lang.mixin(defaultApi, adapterEx);
            });
		});
	}
	
	third.init = init;
	return third;
});
