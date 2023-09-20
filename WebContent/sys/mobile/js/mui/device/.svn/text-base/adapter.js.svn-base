/*******************************************************************************
 * 功能：外部设备接入适配器
 * 
 ******************************************************************************/
define(["mui/device/device", "dojo/_base/lang", "mui/device/web/adapter","dojo/dom-class"], 
		function(device, lang, webapi, domClass) {
	var defaultApi = lang.clone(webapi);
	var rtnApi = defaultApi;
	var deviceType = device.getClientType();
	var	isApp = false;
	
	//添加设备class，为兼容某些客户端的特殊样式作预留
	domClass.add(document.body, 'clientType' + device.getClientType());
	
	/**
	 * 第三方API _mixin的过程是异步的,调用mixinReady可以保证调用到正确的第三方API
	 * 开发通常不需要使用该方法，除了一些特殊情况，如在页面加载过程就需要调用第三方API
	 */
	var ____ready___ = false;
		____readyCallbacks = [];

	rtnApi.resume = function(callback){
	};

	rtnApi.mixinReady = function(callback){
		if(____ready___){
			callback && callback.call(this);
			return;
		}
		____readyCallbacks.push(callback);
	};
	
	rtnApi._mixinReady = function(context){
		____ready___ = true;
		var cb = null;
		while(cb = ____readyCallbacks.shift()){
			cb && cb.call(context || this);
		}
	};
	
	if(deviceType == 6 || deviceType == 12 ){ //6:企业号或企业微信插件、12:企业微信
		isApp = true;
		require({async: false},["mui/device/weixin/adapter"],function(weixinapi){
			rtnApi = _mixin(defaultApi, weixinapi);
			rtnApi._mixinReady();
		});
	}
	
	if (deviceType == 7 || deviceType == 8) {	//kk
		isApp = true;
		require({async: false},["mui/device/kk/adapter"],function(kkapi){
			rtnApi = _mixin(defaultApi, kkapi);
			rtnApi._mixinReady();
		});
	}
	
	if(deviceType == 9 || deviceType == 10){  //kk5
		isApp = true;
		require({async: false},["mui/device/kk5/adapter"],function(kk5api){
			rtnApi = _mixin(defaultApi, kk5api);
			rtnApi.ready(function(){
				if(rtnApi.isEBEN()){
					require({async: false},["mui/device/ereb/adapter"],function(erebapi){
						rtnApi = _mixin(rtnApi, erebapi);
						rtnApi._mixinReady();
					});
				}else{
					rtnApi._mixinReady();
				}
			});
		});
	}

	if(deviceType == 11 ){  //钉钉
		isApp = true;
		require({async: false},["mui/device/ding/adapter"],function(dingapi){
			rtnApi = _mixin(defaultApi, dingapi);
			rtnApi._mixinReady();
		});
	}
	
	if(deviceType == 14 ){  //政务钉钉
		isApp = true;
		require({async: false},["mui/device/govding/adapter"],function(govdingapi){
			rtnApi = _mixin(defaultApi, govdingapi);
			rtnApi._mixinReady();
		});
	}
	if(deviceType == 22 ){  //飞书
		isApp = true;
		require({async: false},["mui/device/feishu/adapter"],function(feishuapi){
			rtnApi = _mixin(defaultApi, feishuapi);
			rtnApi._mixinReady();
		});
	}
	
	if(!isApp){
		rtnApi._mixinReady();
	}
	
	/**
	 * 与lang.mixin差别:当存在同名函数覆盖时,被覆盖函数会以$super$XXX函数保留下来
	 */
	function _mixin(dest, source){
		var name, s, i, empty = {};
		for(name in source){
			s = source[name];
			var $superName = '$super$' + name;
			dest[$superName] = dest[name];
			if(!(name in dest) || (dest[name] !== s && (!(name in empty) || empty[name] !== s))){
				dest[name] = s;
			}
		}
		return dest; // Object
	}
	
	/**
	 * 字体切换
	 */
	rtnApi.mixinReady(function(){
		rtnApi.getFontSize(function(result){
			var fontSize = result && result.fontSize ? result.fontSize : 0;
			domClass.add(document.documentElement, 'fontSize' + fontSize);
		});
	});

	return rtnApi;
});