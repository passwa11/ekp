/*
 * 用于KK客户端对应功能接口调用
 */
define(function() {
	var kkApi = {
		_cacheId : 0,
		
		_cachePrefix : '_device_context_',
		
		_caches : {},
		
		_generateCacheId:function(){
			this._cacheId = this._cacheId + 1;
			return this._cachePrefix + this._cacheId;
		},
		
		/******************************************************
		 * ‘device_’开头函数为客户端调用js接口函数
		 *****************************************************/
		/*
		 * 功能：执行对应方法
		 * 参数：
		 * 		cacheId     缓存ID  客户端通过prompt第一个参数中获取cacheId信息
		 * 		methodName	方法名
		 *		params		方法对应传递参数
		 * 返回值：
		 * 		无
		 */
		device_execute : function(cacheId, methodName, params) {
			if(this._caches[cacheId]!=null){
				this._caches[cacheId][methodName](cacheId, params);
			}
		},
		
		
		/*******************************************************
		 * kk对外接口
		 * 参数：
		 * 		signStr   功能特性标识
		 *      cacheObj  功能对应参数信息
		 * 返回值：
		 *      prompt返回的字符串为json类型的格式信息，信息对应如下：
		 *	{
		 *    status:-1/0/1             -1：不支持该功能特性。   0：调用失败。  1：调用成功。
		 *    message:string/json/null  当status为-1时，返回空。
		 *								当status为0时，返回错误信息。
		 *								当status为1时成功，返回调用后所需信息，由具体接口处理 
		 *	}
		 *	处理后的返回值为，成功后的返回信息或空值
		 ******************************************************/
		 _appCallback:function(signStr , cacheObj){
			var cacheId = this._generateCacheId();
			var apiUrl = "kkapi://" + signStr ;
			var extArgu = '';
			if(cacheObj!=null){
				this._caches[cacheId] = cacheObj;
				apiUrl += "?cacheId=" + cacheId;
				extArgu = this._formatArgu(cacheObj);
			}
			var rtnStr = window.prompt(apiUrl , extArgu);
			if(rtnStr!=null && rtnStr!=''){
				var rtnObj = this.formatJson(rtnStr);
				if(rtnObj.status == 1){
					if(window.console){
						window.console.log(signStr + '调用成功，返回信息为：' + rtnObj.message);
					}
					return {result:rtnObj.message};
				}else if(rtnObj.status == 0){
					if(window.console){
						window.console.error(signStr + '调用失败，错误信息为：' + rtnObj.message);
					}
					this.clearCache(cacheId);
				}else if(rtnObj.status == -1){
					if(window.console){
						window.console.error(signStr + '接口，当前客户端暂不支持。');
					}
					this.clearCache(cacheId);
				}
			}
			return null;
		},
		/*******************************************************
		 * json字符串转化为json对象
		 * 参数：
		 * 		jsonStr    json格式字符串
		 * 返回值：
		 *      json对象或数组。
		 ******************************************************/
		formatJson:function(jsonStr){
			var rtnObj = null;
			if(window.JSON){
				rtnObj = JSON.parse(jsonStr);
			}else if(rtnStr.parseJSON){
				rtnObj = jsonStr.parseJSON();
			}else{
				rtnObj = eval("(" + jsonStr + ")");
			}
			return rtnObj;
		},
		/*******************************************************
		 * json对象转化为url参数格式
		 * 参数：
		 * 		jsonObj    json对象
		 * 返回值：
		 *      url参数格式的字符串
		 ******************************************************/
		_formatArgu:function(jsonObj){
			var extArgu = '';
			for(var key in jsonObj){
				if(typeof(jsonObj[key])=='string'){
					extArgu += "&" + key + '=' +  window.encodeURIComponent(jsonObj[key]);
				}else if(typeof(jsonObj[key])=='array'){
					var vlas = jsonObj[key];
					for(var i =0;i<vlas.length;i++){
						extArgu += "&" + key + '=' + window.encodeURIComponent(vlas[i]);
					}
				}else if(typeof(jsonObj[key])=='function'){//do nothing
				}else{
					extArgu += "&" + key + '=' +  window.encodeURIComponent(jsonObj[key]);
				}
			}
			return extArgu;
		},
		/*
		 * 功能：清理缓存
		 * 参数：
		 * 		cacheId     缓存ID
		 * 返回值：
		 * 		无
		 */
		clearCache : function(cacheId){
			if(this._caches[cacheId]!=null){
				delete this._caches[cacheId];
			}
		},
		
		/*
		 * 功能：	
		 * 			关闭appView页面
		 * 参数说明：
		 * 			无参
		 * 返回说明：
		 * 			返回调用信息，为空表示无此特性或调用出错
		 */
		closeWindow : function(){
			return this._appCallback("closeWindow");
		},
		/*
		 * 功能：	
		 * 			显示隐藏banner
		 * 参数说明：
		 * 			isShow 	是否显示
		 * 返回说明：
		 * 			返回调用信息，为空表示无此特性或调用出错
		 * 
		 */
		showTitleBar:function(isShow){
			return this._appCallback("showTitleBar",{'show':isShow});
		},
		/*
		 * 功能：	
		 * 			是否可返回
		 * 参数说明：
		 * 			无参
		 * 返回说明：
		 * 			是否可进行返回操作，为空表示无此特性或调用出错，true表示支持，false表示不支持
		 */
		_canGoBack : function(){
			var res = this._appCallback("canGoBack");
			if(res!=null)
				return res.result;
			 return null;
		},
		/*
		 * 功能：	
		 * 			返回
		 * 参数说明：
		 * 			无参
		 * 返回说明：
		 * 			返回调用信息，为空表示无此特性或调用出错
		 */
		goBack : function(){
			var rtn = null;
			var canBack = this._canGoBack();
			if(canBack == true){		//可后退
				rtn = this._appCallback("goBack");
			}else if(canBack == false){	//不可后退
				rtn = this.closeWindow();
			}else{ 						//功能不支持
				rtn = null;
			}
			return rtn;
		},
		/*
		 * 功能：	
		 * 			前进
		 * 参数说明：
		 * 			无参
		 * 返回说明：
		 * 			返回调用信息，为空表示无此特性或调用出错
		 */
		goForward : function(){
			return this._appCallback("goForward");
		},
		/*
		 * 功能：	
		 * 			获取当前用户ID即登陆名
		 * 参数说明：
		 * 			无参
		 * 返回说明：
		 * 			当前用户名称，为空表示无此特性或调用出错
		 */
		getUserID: function(){
			var res = this._appCallback("getUserID");
			if(res!=null)
				return res.result;
			 return null;
		},
		
		/*
		 * 功能：	
		 * 			打开语音
		 * 参数说明：
		 * 		options
		 * 			{
		 *				"complete":function //必须参数,用于语音完毕后回调。
		 *									//接受参数：		cacheId：缓存ID		
		 *													fileInfo：文件相关信息（json格式字符串）
		 *				"cancel":function   //必须参数，用于语音取消回调
		 *									//接受参数：		cacheId：缓存ID	
		 *				"error":function   //必须参数，用于语音错误时回调
		 *									//接受参数：		cacheId：缓存ID	
		 *													msg:错误信息
		 * 			}
		 * 返回说明：
		 * 			返回调用信息，为空表示无此特性或调用出错
		 */
		openSpeech:function(options){
			 return this._appCallback("openSpeech",options);
		},
		
		/*
		 * 功能：	
		 * 			打开相机
		 * 参数说明：
		 * 			{
		 *				"complete":function //必须参数,用于相机拍摄完毕回调。
		 *									//接受参数：		cacheId：缓存ID		
		 *													fileInfo：图片文件相关信息（json格式字符串）
		 *				"cancel":function   //必须参数，用于拍摄取消时回调
		 *									//接受参数：		cacheId：缓存ID	
		 *				"error":function   //必须参数，用于拍摄过程错误时回调
		 *									//接受参数：		cacheId：缓存ID	
		 *													msg:错误信息
		 * 			}
		 * 返回说明：
		 * 			返回调用信息，为空表示无此特性或调用出错
		 */
		openCamera:function(options){
			 return this._appCallback("openCamera",options);
		},
		
		/*
		 * 功能：	
		 * 			选择附件
		 * 参数说明：
		 * 		options
		 * 			{
		 *				"complete":function //必须参数,用于选择文件完毕后回调。
		 *									//接受参数：		cacheId：缓存ID		
		 *													files：图片文件相关信息（json格式数组）
		 *                                                  	size, name, type, fullpath, lastModifiedDate
		 *				"cancel":function   //必须参数，用于取消文件选择时回调
		 *									//接受参数：		cacheId：缓存ID	
		 *				"error":function   //必须参数，用于选择文件过程错误时回调
		 *									//接受参数：		cacheId：缓存ID	
		 *													msg:错误信息
		 * 			}
		 * 返回说明：
		 * 			返回调用信息，为空表示无此特性或调用出错
		 */
		selectFile:function(options){
			 return this._appCallback("selectFile",options);
		},
		
		/*
		 * 功能：	
		 * 			上传指定标识附件
		 * 参数说明：
		 * 			options
		 *{
		 *				"filepath"：string,	//必须参数，附件路径
		 *				"userkey":token,	//必须参数，附件上传必须参数
		 *				"uploadurl":string,	//必须参数，附件上传URL
		 *				"complete":function //必须参数，用于附件上传完毕后回调。
		 *									//接受参数：		cacheId：缓存ID		
		 *                                                  filekey:附件标示，由服务器上传后返回
		 *													file：附件信息（json格式字符串, size, name, type, fullpath, lastModifiedDate）
		 *				"progress":function //必须参数，返回进度信息.
		 *									//接受参数：		cacheId：缓存ID
		 *                                                  loaded:附件已上传数
		 *                                                  file：附件信息（json格式字符串: size, name, type, fullpath, lastModifiedDate）
		 *				"cancel":function   //必须参数，用于附件取消上传后回调
		 *									//接受参数：		cacheId：缓存ID	
		 *				"error":function   //必须参数，用于附件上传错误时回调
		 *									//接受参数：		cacheId：缓存ID	
		 *													msg:错误信息
		 *}
		 * 			
		 * 返回说明：
		 * 			返回调用信息，为空表示无此特性或调用出错
		 */
		 uploadFile:function(options){
			return this._appCallback("uploadFile",options);
		 },
		
		/*
		 * 功能：	
		 * 			播放语音
		 * 参数说明：
		 * 			options
		 * 			{
		 *				"downloadurl":string//语音播放地址
		 *				"complete":function //必须参数，用于语音播放完毕后回调。
		 *									//接受参数：		cacheId：缓存ID		
		 *													fileInfo：附件相关信息（json格式字符串）
		 *				"cancel":function   //必须参数，用于语音播放取消后回调
		 *									//接受参数：		cacheId：缓存ID	
		 *				"error":function    //必须参数，用于语音播放错误时回调
		 *									//接受参数：		cacheId：缓存ID	
		 *													msg:错误信息
		 * 			}
		 * 返回说明：
		 * 			返回调用信息，为空表示无此特性或调用出错
		 */
		playSpeech:function(voiceUrl){
			var _self = this;
			this._appCallback("playSpeech",{
				downloadurl: voiceUrl,
				complete:function(cacheId){
					_self.clearCache(cacheId);
				},
				cancel:function(cacheId){
					_self.clearCache(cacheId);
				},
				error:function(cacheId, msg){
					if(window.console)
						window.console.error("语音播放错误:" + msg);
					_self.clearCache(cacheId);
				}
			});
			return {};
		},
		/*
		 * 功能：	
		 * 			播放图片
		 * 参数说明：
		 * 			options
		 * 			{
		 *				"curSrc":string		//当前展示图片地址
		 *				"srcList":[string]  //所有图片列表
		 * 			}
		 * 返回说明：
		 * 			返回调用信息，为空表示无此特性或调用出错
		 */
		 //	imagePreview:function(options){
		 //		return this._appCallback("imagePreview" , options); 
		 //	},
		/*
		 * 功能：	
		 * 			截取屏幕
		 * 参数说明：
		 * 			options
		 * 			{
		 *				"complete":function //必须参数，用于截屏完毕后回调。
		 *									//接受参数：		cacheId：缓存ID		
		 *													src：截屏后的图片地址
		 *				"cancel":function   //必须参数，用于截屏取消后回调
		 *									//接受参数：		cacheId：缓存ID	
		 *				"error":function    //必须参数，用于语音播放错误时回调
		 *									//接受参数：		cacheId：缓存ID	
		 *													msg:错误信息
		 * 			}
		 * 返回说明：
		 * 			返回调用信息，为空表示无此特性或调用出错
		 */
		captureScreen:function(options){
			return this._appCallback("captureScreen",options);
		},
		
		/*
		 * 功能：	
		 * 			图片转义为dataURL信息
		 * 参数说明：
		 * 			options
		 * 			{
		 *				fullpath：“”   图片位于手机的路径信息
		 * 			}
		 * 返回说明：
		 * 			返回图片base64信息,格式：
		 *{
		 *	  status:-1/0/1             -1：不支持该功能特性。   0：调用失败。  1：调用成功。
		 *    message:string/json/null  当status为-1时，返回空。
		 *								当status为0时，返回错误信息。
		 *								当status为1时成功，返回图片base64字符信息。
		 *}
		 */
		readAsDataURL:function(file){
			return this._appCallback("readAsDataURL" , file);
		}
	};
	window.kkApi = kkApi;
	return kkApi;
});
