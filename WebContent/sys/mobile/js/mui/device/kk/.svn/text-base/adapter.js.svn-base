/*
 * 用于KK客户端对应功能接口调用
 */
define(['mui/device/device','sys/mobile/js/lib/kk/kkapi', 'dojo/_base/lang', 'mui/device/kk/attachment','mui/mime/mime'],
		function(device, kkapi, lang, Attachment, mime) {
	var adapter = {
		closeWindow: function(){
			return kkapi.closeWindow();
		},
		
		showTitleBar:function(isShow){
			return kkapi.showTitleBar(isShow);
		},
		
		goBack:function(){
			return kkapi.goBack();
		},
		
		goForward:function(){
			return kkapi.goForward();
		},
		
		getUserID:function(){
			return kkapi.getUserID();
		},
		
		_select: function(feature, context){
			if(window.console){
				window.console.log(feature + ' begin..');
			}
			var attSetting = context.options;
			if(!window.AttachmentList)
				window.AttachmentList = {};
			var attachmentObj = window.AttachmentList[attSetting.fdKey];
			if(!attachmentObj){
				attachmentObj = new Attachment(attSetting);
				window.AttachmentList[attSetting.fdKey] = attachmentObj;
			}
			var _self = this;
			kkapi[feature]({
				"complete":function(cacheId,files){
					var uploadingFiles = files;
					if(typeof(files)=='string'){
						uploadingFiles = kkapi.formatJson(files);
					}
					if(window.console){
						window.console.log(feature + " complete cacheId = "+cacheId+",files=" + files );
					}
					if(lang.isArray(uploadingFiles)){
						for ( var i = 0; i < uploadingFiles.length; i++) {
							if(feature=="openCamera" || feature=="selectFile"){
								uploadingFiles[i].href = _self.readAsDataURL(uploadingFiles[i]);
							}
							attachmentObj.startUploadFile(uploadingFiles[i]);
						}
					}else{
						if(feature=="openCamera" || feature=="selectFile"){
							uploadingFiles.href = _self.readAsDataURL(uploadingFiles);
						}
						attachmentObj.startUploadFile(uploadingFiles);
					}
					kkapi.clearCache(cacheId);
				},
				"cancel":function(cacheId){
					kkapi.clearCache(cacheId);
				},
				"error":function(cacheId , msg){
					attachmentObj.uploadError(null,{rtn:{'status':'-1','msg':'附件错误:' + msg}});
					kkapi.clearCache(cacheId);
				}
			});
			return {};
		},
		
		openSpeech:function(context){
			return this._select("openSpeech",context);
		},
		
		openCamera:function(context){
			 return this._select("openCamera",context);
		},
		
		selectFile:function(context){
			return this._select("selectFile",context);
		},
		
		playSpeech:function(voiceUrl){
			return kkapi.playSpeech(voiceUrl);
		},
		
		captureScreen:function(callback){
			 var _self = this;
			 kkapi.captureScreen({
					complete:function(cacheId, fileInfo){
						var uploadingFile = fileInfo;
						if(typeof(fileInfo)=='string'){
							uploadingFile = kkapi.formatJson(fileInfo);
						}
						var rtn = _self.readAsDataURL({'fullpath':uploadingFile["src"]});
						if(rtn){
							callback(rtn);
						}
						kkapi.clearCache(cacheId);
					},
					cancel:function(cacheId){
						kkapi.clearCache(cacheId);
					},
					error:function(cacheId, msg){
						if(window.console)
							window.console.error("截屏出错:" + msg);
						kkapi.clearCache(cacheId);
					}
			 });
			 return {};
		},
		
		readAsDataURL:function(file){
			var rtn = kkapi.readAsDataURL(file);
			if(rtn!=null){//检查
				var base64Url = rtn.result;
				var fileType = mime.getMime(file['fullpath']);
				if(fileType!=null && fileType!=''){
					base64Url = "data:" + fileType+ ";base64," + base64Url;
				}else{
					base64Url = "data:image/jpeg;base64," + base64Url;
				}
				return base64Url;
			}
			return null;
		}
	};
	if(device.getClientType()==device.KK_IPHONE){//iphone kk 无语音和签批功能
		delete adapter.openSpeech;
		delete adapter.captureScreen;
	}
	return adapter;
});
