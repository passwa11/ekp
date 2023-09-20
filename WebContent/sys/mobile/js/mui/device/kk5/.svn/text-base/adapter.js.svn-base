/*
 * 用于kk5客户端对应功能接口调用
 */
define(["mui/device/device","mui/util",'mui/device/kk5/attachment','mui/device/kk5/_attachment',"mui/dialog/Tip","mui/picslide/ImagePreview","dojo/request","lib/kk5/kk5","dojo/Deferred","mui/map","dojo/_base/lang"],
		function(device,util, Attachment,_Attachment,Tip,ImagePreview,request,KK,Deferred,map,lang) {
	var adapter = {
			closeWindow : function() {
				KK.app.exit();
				return {};
			},
			
			enterVideoMeeting:function(options,successFn,errorFn){
				KK.app.enterMeeting({
					roomId:options.roomId
				},function () {
					var rtn={
						code:0,
						text:"成功进入会议"
					};
					successFn && successFn(rtn);
				}, function (code, msg) {
					//失败回调，错误码包括： -1:会议不存在,-2:会议未开始, -3:会议已经结束,-8:网络异常,其它情况返回会议云平台的其它错误码 
					var msg = "";
					if(code==-1){
						msg = '会议不存在';
					}
					if(code==-2){
						msg = '会议未开始';
					}
					if(code==-3){
						msg = '会议已经结束';
					}
					if(code==-8){
						msg = '网络异常';
					}
					if(window.console){
						window.console.error(msg);
					}
					if(window.console){
						console.log('错误代码是' + code + '，错误信息是' + msg);
					}
					var rtn={
						code:code,
						text:"错误信息："+msg
					};
					errorFn && errorFn(rtn);
				});
			},
			
			imagePreview : function(options){
				var v1 = KK.app.getClientInfo().semver;
				//KK5客户端大于5.2.6或者KK6客户端大于6.0.1才支持HTTP/HTTPS图片预览
				if(this.kkVersionCompare(v1,'6.0.1') >= 0 
						||( this.kkVersionCompare(v1,'5.2.6.6') >= 0 && this.kkVersionCompare(v1,'6.0.0') < 0 )  ){
					var srcList = options.srcList,
						index = 0;
					if(srcList && srcList.length > 0){
						for(var i = 0; i < srcList.length;i++){
							if(options.curSrc.indexOf(srcList[i])!=-1){
								index = i;
								break;
							}
						}
					}
					KK.ready(function(){
						KK.media.previewImage({
							paths : srcList,
							startIndex : index + 1,
							operations : true
						}, function(code, msg){
							  console.log('错误信息:' + msg + ',错误代码:' + code);
						});
					});
				}else{
					if(!this.preivew)
						this.preivew = new ImagePreview();
					this.preivew.play(options);
				}
				return {};
			},
			
			/**
			 * 附件预览
			 */
			fileView:function(options){
				KK.file.view(options.path, function () {
					  console.log('在Andriod设备下直接使用外部应用打开文件，在IOS设备下则根据控制台配置的打开方式打开');
					}, function (code) {
						if(window.console){
							console.log("打开失败："+code);
						}
						var msg = "";
						if(code==-1){
							msg = '文件不存在';
						}
						if(code==-2){
							msg = '系统未安装wps，无法在线编辑！';
						}
						if(code==-3){
							msg = '文档保存失败';
						}
						if(code==-6){
						}
						if(window.console){
							window.console.error(msg);
						}
						if(msg != ""){
							Tip.fail({
								text : msg
							});
						}
			    });
			},
			
			/**
			 * 设置标题栏
			 */
			setTitle : function(text){
				KK.ready(function(){
					KK.app.setTitle(text);
				});
				document.title = text;
			},
			
			doDownLoad: function(options,filepath,fileName) { 
				var progressing;
				var proxy = new KK.proxy.Download({
					url: util.formatUrl("/third/pda/attdownload.jsp?fdId="+options.fdId,true),
					path : filepath,
					isContinuous :false,
					appearInFileManager:true
				},function(res){
					if(res.progress == 100){
						if(progressing){
							progressing.hide();
						}
						KK.file.view({
							  filepath: res.path,
							  title: fileName
						 },function(){},function(){});
						
					}else{
						if(!progressing){
							progressing = Tip.progressing({
								text : '文件下载中'+res.progress+'%'
							});
						}else{
							progressing.containerNode.innerHTML='<div><div class="muiDialogTipIcon"><div class="mui mui-loading mui-spin"></div></div><div class="muiDialogTipText"><span>文件下载中'+res.progress+'%</span></div></div>';
						}
					}
				},function(code,msg){
					location.href = util.formatUrl(options.href);
				});
				proxy.start();
			},
			
			download : function(options) {
				var name = options.name.toLowerCase(),
					deviceType = device.getClientType();
				//kk5 ios
				if(name.lastIndexOf(".")>-1 && deviceType == 9){
					var fileExt = name.substring(name.lastIndexOf(".") + 1);
					if(fileExt == 'zip' || fileExt == 'rar'){
						Tip.fail({
							text : '暂不支持此文件类型打开'
						});
						return;
					}
				}
				var filepath = "sdcard://kkDownloadWps/"+options.name;
				var fileName = options.name;
				var fdFileType = fileName.substring(fileName.lastIndexOf("."));
				var _self = this;
				KK.file.exists(filepath, function(res){
					  if( res.exists ){
						 fileName = fileName.substring(0,fileName.lastIndexOf("."));
						 filepath = "sdcard://kkDownloadWps/"+fileName+"_"+(new Date()).getTime()+fdFileType;
						 _self.doDownLoad(options,filepath,fileName);
					  }else{
						  _self.doDownLoad(options,filepath,fileName);
					  }
				});
			},
			
			canUseJinGe:function(){
				var defer = new Deferred();
				var auth = {office:false,pdf:false};
				var v1 = KK.app.getClientInfo().semver;
				if(this.kkVersionCompare(v1,'6.0.4') >= 0 ){
					KK.ready(function(){
						KK.file.canUseJinGe(function (res) {
							auth.office = res.office;
							auth.pdf = res.pdf;
							defer.resolve(auth);
						}, function (code, msg) {
							console.log('获取用户使用金格的权限出错，错误信息：' + msg + '，错误代码:' + code);
						});
					});
				}else if(this.kkVersionCompare(v1,'6.0.2') >= 0 ){
					auth.office = true;
					auth.pdf = true;
					defer.resolve(auth);
				}else{
					defer.resolve(auth);
				}
				return defer;
			},
			
			openOfdFile:function(options,successFn,errorFn){
				var fileName = options.name;
				var fdFileType = fileName.substring(fileName.lastIndexOf("."));
				var _self = this;
				var downUrl = util.formatUrl("/third/pda/attdownload.jsp?fdId="+options.fdAttMainId,true); //获取文件的路径
				var proxy = new KK.proxy.Download({
					url: downUrl,
					path : "sdcard://kkDownloadWps/"+options.fdAttMainId+fdFileType,
					isContinuous :false
				},function(res){
					 var processing = Tip.processing({text : '文件打开中...'});
					if(res.progress != 100){
						processing.show();
						var rtn={
							code:0,
							text:"文件打开中..."
						};
						successFn && successFn(rtn);
					}else{
						processing.hide();
						var cfg = {
							filepath: res.path,
							via: 'auto'
						};
						KK.file.view(cfg,function(arg){
							//监听应用事务
							KK.app.on('resume', function(args){
								_self.removefile(res.path);
							});
						},function(code){
							var msg = "";
							if(code==-1){
								msg = '文件不存在';
							}
							if(code==-2){
								msg = '系统未安装ofd软件，无法在线打开！';
							}
							if(code==-6){
								msg = 'ofd发生错误';
							}
							if(msg != ""){
								if(errorFn){
									var rtn={
										code:code,
										text:"获取文件系统路径出错："+msg
									};
									errorFn && errorFn(rtn);
								}else{
									Tip.fail({
										text : msg
									});
								}
							}
						});
					}
				},function(code,msg){
					if(window.console){
						console.log("错误信息："+msg +",错误代码："+code);
					}
					var rtn={
						code:code,
						text:"错误信息："+msg
					};
					errorFn && errorFn(rtn);
				});
				proxy.start();
			},
				
			openfileByType:function(type,path,options,successFn,errorFn){
				var _self = this;
				var cfg = {
					filepath: path,
					via: type
				};
				if(type == 'wps'){
					cfg['mode'] = 'edit';
				}
				KK.file.view(cfg,function(arg){
					if(window.console){
						console.log("打开成功："+arg.operation);
					}
					if(arg.operation == 'close' || arg.operation == 'save'){
						KK.file.getOSPath(path, function(rtn){
							if(window.console){
								console.log("系统路径为："+rtn.OSPath);
							}
							var file={
									fdAttMainId:options.fdAttMainId,
									href : rtn.OSPath,
									path : path,
									name : options.name,
									edit : true,
									key : options.key,
									status : -1,
							};
							
							_self.doUploadFile(file,arg.operation,successFn,errorFn);
							
						}, function(code,msg){
							if(window.console){
								console.log("获取文件系统路径出错："+msg +",错误代码："+code);
							}
							var rtn={
								code:code,
								text:"获取文件系统路径出错："+msg
							};
							errorFn && errorFn(rtn);
						});
					}
				},function(code){
					if(window.console){
						console.log("打开失败："+code);
					}
					var msg = "";
					if(code==-1){
						msg = '文件不存在';
					}
					if(code==-2){
						msg = '系统未安装'+type+'软件，无法在线编辑！';
					}
					if(code==-3){
						msg = '文档保存失败';
					}
					if(code==-6){
						msg = 'wps发生错误';
					}
					if(window.console){
						window.console.error(msg);
					}
					if(msg != ""){
						if(errorFn){
							var rtn={
								code:code,
								text:"获取文件系统路径出错："+msg
							};
							errorFn && errorFn(rtn);
						}else{
							Tip.fail({
								text : msg
							});
						}
					}
				});
			},
			
			
			openfilePdf :function(path,options,successFn,errorFn){
				this.openfileByType('pdf',path,options,successFn,errorFn);
			},
			
			openfileWps :function(path,options,successFn,errorFn){
				this.openfileByType('wps',path,options,successFn,errorFn);
			},
			
			
			editfile :function(options,successFn,errorFn){
				var fileName = options.name;
				var fdFileType = fileName.substring(fileName.lastIndexOf("."));
				var _self = this;
				var downUrl = util.formatUrl("/third/pda/attdownload.jsp?fdId="+options.fdAttMainId,true); //获取文件的路径
				var proxy = new KK.proxy.Download({
					url: downUrl,
					path : "sdcard://kkDownloadWps/"+options.fdAttMainId+fdFileType,
					isContinuous :false
				},function(res){
					 var processing = Tip.processing({text : '文件打开中...'});
					if(res.progress != 100){
						processing.show();
						if(window.console){
							console.log("下载进度："+res.progress);
						}
						var rtn={
							code:0,
							text:"文件打开中..."
						};
						successFn && successFn(rtn);
						
					}else{
						processing.hide();
						if(window.console){
							console.log("文件地址："+res.path);
						}

						//添加支持ofd的文件类型
						if(options.fileType && options.fileType == 'pdf'){
							_self.openfilePdf(res.path,options,successFn,errorFn);
						}else{
							_self.openfileWps(res.path,options,successFn,errorFn);
						}
					}
				},function(code,msg){
					if(window.console){
						console.log("错误信息："+msg +",错误代码："+code);
					}
					var rtn={
						code:code,
						text:"错误信息："+msg
					};
					errorFn && errorFn(rtn);
				});
				proxy.start();
			},
			
			doUploadFile:function(file,opt,successFn,errorFn){
				var tokenurl = util.formatUrl("/sys/attachment/sys_att_main/sysAttMain.do?method=handleAttUpload&gettype=getuserkey&format=json",true);
				console.log("fdAttMainId:"+file.fdAttMainId);
				var extendData = "filesize=" + file.size +"&fdAttMainId="+file.fdAttMainId+ "&md5=";
				var self = this;
				request.post(tokenurl, {
					data : extendData,
					handleAs : 'json',
					sync: true
				}).then(function(data) {
					if (data.status == '1') {
						file.status = 1;
						var userKey = (data.token && data.token.header) ? data.token.header.userkey : '';
						self.uploadWpsFile(userKey,file,opt,successFn,errorFn);
					} 
				}, function(data) {
					if(window.console){
						console.log(data);
					}
					var rtn={
						text:'上传附件失败'
					};
					errorFn && errorFn(rtn);
				});
			},
			
			removefile:function(filepath){
				//file.path为相对路径
				KK.file.remove(filepath, function(){
					if(window.console){
						console.log("删除成功！！！");
					}
				}, function(code, msg){
					if(window.console){
					  console.log('错误信息：' + msg + ',错误代码:' + code);
					}
				});
			},
			
			uploadWpsFile:function(userKey,file,opt,successFn,errorFn){
				var _self = this;
				KK.proxy.uploadView({
					"token":userKey,
					"userkey": userKey,
					"url": util.formatUrl("/sys/attachment/uploaderServlet?gettype=upload&fdAttMainId="+file.fdAttMainId+"&format=json",true),
					"fullPath": file.href,  //file.href为系统全路径
				},function(fileInfo){
					if(window.console){
						console.log("上传成功："+fileInfo); 
					}
					if(opt == 'close'){
						_self.removefile(file.path);
					}
					
					var rtn={
						code:1,
						text:""
					};
					successFn && successFn(rtn);
					
				},function(code){
					_self.removefile(file.path);
					var msg = "";
					if(code==-1){
						msg = '附件上传网络不可用';
					}
					if(code==-2){
						msg = '附件上传调用参数错误';
					}
					if(code==-3){
						msg = '附件上传被取消';
					}
					if(code==-9){
						msg = '附件上传服务器端出错';
					}
					if(window.console){
						window.console.error(msg);
					}
					if(msg != ""){
						if(errorFn){
							var rtn={
								code:code,
								text:msg
							};
							errorFn && errorFn(rtn);
						}else{
							Tip.fail({
								text : msg
							});
						}
					}
				});
			},
			
			showTitleBar:function(isShow){
				if(isShow){
					KK.app.hideTitleBar();
				}else{
					KK.app.showTitleBar();
				}
				return {};
			},
			
			goBack : function() {
				KK.history.canGo(function(rtnObj){
					if(rtnObj.canGoBack){
						KK.history.back();
					}else{
						KK.app.exit();
					}
				});
				return {};
			},
		
			goForward : function(){
				KK.history.forward();
				return {};
			},
			
			getUserID : function(){
				var userinfo = KK.app.getUserInfo();
				if(userinfo!=null)
					return userinfo.loginName;
				return null;
			},
			
			
		
			_select : function(feature, context){
				var attSetting = context.options;
				if(!window.AttachmentList)
					window.AttachmentList = {};
				if(!window.feature)
					window.feature=feature;
				var attachmentObj = window.AttachmentList[attSetting.fdKey];
				if(!attachmentObj||window.feature!=feature){
					window.feature=feature;
					attSetting.uploadStream = true;
					attachmentObj = new Attachment(attSetting);
					window.AttachmentList[attSetting.fdKey] = attachmentObj;
				}
					var count = 1;
					if(attSetting.fdMulti&&feature=="album"){
						//拍照（单张时才能编辑）
						count = 9;
					}
					var _options = {
						sourceType:feature,
						count : count,
						quality : attSetting.quality,
						destinationType:'file',
						encodingType: attSetting.encodingType || 'png',
						showVideo : true,
						needCover : true
					};
					if(attSetting.targetWidth){
						_options.targetWidth = attSetting.targetWidth;
					}
					if(attSetting.targetHeight){
						_options.targetHeight = attSetting.targetHeight;
					}
					
					
					KK.media.getPicture(_options,function(datas, savePath){
						for(var i = 0; i < datas.length;i++){
							var __data = datas[i];
							//视频上传
							if(__data.mediaType == 'video')
							{
								var videoUrl = __data.videoPath;
								KK.file.readAsBase64(videoUrl,function(__args){
									attachmentObj.startUploadFile({
										href : 'data:image/png;base64,' + __args.content,
										size : '',
										name: "video" + new Date().getTime() + ".mp4",
										filePath: videoUrl
									});
								},function(code,errMsg){
									if(code){
										attachmentObj.uploadError(null,{
											rtn:{'status': code ,'msg':'附件转换为base64过程发生错误,信息为：' + errMsg}
										});
									}
								});
							}
							else if(__data.mediaType == 'image') //图片上传
							{
								var	imageURI = __data.imageURI;
								KK.file.readAsBase64(imageURI,function(__args){
									attachmentObj.startUploadFile({
										href : 'data:image/png;base64,' + __args.content,
										size : '',
										name: "image" + new Date().getTime() + ".png",
										filePath:imageURI
									});
								},function(code,errMsg){
									if(code){
										attachmentObj.uploadError(null,{
											rtn:{'status': code ,'msg':'附件转换为base64过程发生错误,信息为：' + errMsg}
										});
									}
								});
							}	
							
							
						}
					},function(errCode,errMsg){
						if(errCode && errCode!=-1 && errMsg!=null && errMsg!=''){
							attachmentObj.uploadError(null,{
								rtn:{'status':'-1','msg':'附件上传错误,信息为：' + errMsg}
							});
						}
					});
				return {};
			},
			
			
			openCamera:function(context){
				var v1 = KK.app.getClientInfo().semver;
				console.log(context.options);
				context.options.encodingType = 'jpeg';
				context.options.quality = 80;
				context.options.targetWidth = 1000;
				context.options.targetHeight = 2000;
				var feature ="camera";
				if(context.options.capture&&this.kkVersionCompare(v1,'6.0.4') >= 0)
					feature =context.options.capture;
				return this._select(feature,context);
			},

			selectFile:function(context){
				console.log(context.options);
				context.options.targetWidth = window.screen.width * 2;
				context.options.targetHeight = window.screen.height * 2;
				var feature= context.feature||"album";
				if(feature=="file_manager"){
					  return this._selectFile(feature,context);
				}else{
					 return this._select(feature,context);
				}
			},
			
			_selectFile:function(feature,context){
				var attSetting = context.options;
				if(!window.AttachmentList)
					window.AttachmentList = {};
				if(!window.feature)
					window.feature=feature;
				var attachmentObj = window.AttachmentList[attSetting.fdKey];
				if(!attachmentObj||window.feature!=feature){
					window.feature=feature;
					attSetting.uploadStream = true;
					attachmentObj = new _Attachment(attSetting);
					window.AttachmentList[attSetting.fdKey] = attachmentObj;
				}
				KK.file.chooseFile(function (res) {
					res.name=res.fileName;
					KK.file.getFileInfo( res.filePath, function (info) {
			    		res.size=info.size;
			    		attachmentObj.startUploadFile(res);
			    		}, function (code, msg) {
			    	});
			    }, function (code, msg) {
					  console.log('从文件管理器选择一个文件，错误信息：' + msg + '，错误代码:' + code);
					});
			},
			
			openSpeech:function(context){
				var attSetting = context.options;
				if(!window.AttachmentList)
					window.AttachmentList = {};
				var attachmentObj = window.AttachmentList[attSetting.fdKey];
				if(!attachmentObj){
					attachmentObj = new Attachment(attSetting);
					window.AttachmentList[attSetting.fdKey] = attachmentObj;
				}
				KK.media.captureAudio(function(file){
					attachmentObj.startUploadFile(file);
				},function(code){
					if(code==-3){
						if(window.console){
							window.console.log('录音取消..');
						}
					}
					if(code==-9){
						if(window.console){
							window.console.error('录音出错..');
						}
					}
				});
				return {};
			},
			
			playSpeech:function(voiceUrl){
				KK.media.playAudio(voiceUrl,function(code){
					if(code==-3){
						if(window.console){
							window.console.log('录音播放取消..');
						}
					}
					if(code==-9){
						if(window.console){
							window.console.error('录音播放出错..');
						}
					}
				});
				return {}; 
			},
		
			captureScreen:function(callback){
				KK.app.captureScreen({
					targetWidth:window.screen.width,  
					targetHeight:window.screen.height
				},function(imageInfo){
					callback(imageInfo.imageData);
				},function(code){
					if(window.console)
						window.console.error("截屏出错:" + code);
				});
				return {};
			},
			
			isEBEN:function(){
				var v1 = KK.app.getClientInfo().semver;
				if(this.kkVersionCompare(v1,'6.0.3')>=0){
					var deviceInfo = KK.app.getDeviceInfo();
					if(deviceInfo && deviceInfo.osModel && deviceInfo.osModel.indexOf('EBEN')>-1){
						return true;
					}else{
						return false;
					}
				}
				return false;				
			},
			getSignImage:function(context){
				var v1 = KK.app.getClientInfo().semver;
				var deviceType = device.getClientType();
				if(deviceType==9 && this.kkVersionCompare(v1,'6.0.2')<0){
					return false;
				}
				var attSetting = context.options;
				if(!window.AttachmentList)
					window.AttachmentList = {};
				var attachmentObj = window.AttachmentList[attSetting.fdKey];
				if(!attachmentObj){
					attSetting.uploadStream = false;
					attachmentObj = new Attachment(attSetting);
					window.AttachmentList[attSetting.fdKey] = attachmentObj;
				}
				var p = {width:window.screen.width,height:window.screen.height,penSize:3,penColor: 1};
				if(this.kkVersionCompare(v1,'6.0.2')>=0){
					p = {penSize:3,penColor:'black'};
				}
				KK.media.getSignImage(p,function(res){
					var resultPath = res.resultPath;
					if(!resultPath){
						resultPath = res.picUri;
					}
					attachmentObj.startUploadFile({
						fullPath : resultPath,
						href : resultPath,
						size : "",
						name: "image.png"
					});
				},function(code,msg){
					if(window.console){
						window.console.log('错误信息:' + msg + ',错误代码:' + code);
					}
				});
				return {};
			},
			
			// kk native初始化完成触发事件
			ready:function(func){
				KK.ready(func);
			},
			
			// KK横屏竖屏切换，1:竖屏，2:横屏
			setOrientation:function(orientation){
				KK.device.setOrientation(orientation);
			},
			
			getOrientation:function(callback, error) {
				KK.device.getOrientation(callback, error);
			},
			
			callPhone:function(phone){
				if(typeof phone !== 'number'){
					phone = parseFloat(phone);
				}
				KK.phone.call({
					number : phone,
					needCallSessionInfo : false
				});
			},
			sendMsg:function(phone){
				KK.phone.sms(phone);
			},
			
			view:function(url, target,contxt) {
				try{
					var v1 = KK.app.getClientInfo().semver;
					if('_blank' == target && this.kkVersionCompare(v1,'5.2.6') >= 0){
						KK.file.view(url,function(){
							
						},function(){
							window.open(url, '_self');
						});
						return;
					}
				}catch(e){}
				window.open(url, target);
				return;
			},
			
			open : function(url, target,contxt) {
				//本地存在对应文件
				var path = url.indexOf('?') > -1 ? url.substring(0,url.indexOf('?')) : url;
				if(dojoConfig.fileMapping && dojoConfig.fileMapping[path]){
					url = dojoConfig.fileMapping[path] + (url.indexOf('?') > -1 ? url.substring(url.indexOf('?'),url.length) : '');
					window.open(url,'_self');
				}
				try{
					var v1 = KK.app.getClientInfo().semver;
					if('_blank' == target && this.kkVersionCompare(v1,'5.2.6') >= 0){
						KK.app.callApp(url,function(){
							//监听应用事务 #144329 #163435
							if(url.indexOf("third/pad/") > -1 || url.indexOf("sys/notify/") > -1){
								console.log("resume注册");
								KK.app.on('resume', function( args ){
									window.location.reload();
									console.log(JSON.stringify(args));
								});
							}
						},function(code, msg ){
							console.log('kk.app.callApp 错误代码：' + code + ' , 错误信息：' + msg );
							window.open(url, '_self');
						});
						return;
					}
				}catch(e){
					if(target === '_top'){
						window.open(url, '_top');
						return;
					}
					window.open(url, '_self');
					return;
				}
				window.open(url, target);
				return;
			},
			kkVersionCompare : function(v1,v2){
				var s1 = v1.split('.'),
					s2 = v2.split('.');
				for(var i = 0; i < Math.max(s1.length, s2.length);i++){
					var n1 = parseInt(s1[i] || 0, 10),
				    	n2 = parseInt(s2[i] || 0, 10);
				    if (n1 > n2) 
				    	return 1;
				    if (n2 > n1) 
				    	return -1;
				}
				return 0;
			},
			scanQRCode : function(callback){
				KK.ready(function(){
					KK.scaner.scanTDCode(function(res){
						callback && callback({
							text : res.code
						});
					});
				});
				return true;
			},
			
			getCurrentCoord : function(callback,error,options){
				KK.ready(function(){
					KK.location.getLocation(function(res){
						console.log('获取地理坐标:'+res.latitude + ','+res.longitude);
						callback && callback({lat:res.latitude,lng:res.longitude,coordType:3});
					},function(err){
						error && error(err);
						Tip.fail({
							text : 'KK地理定位失败'
						});
						if(window.console)
							console.error('获取地理坐标失败:'+err);
					});
				});
			},
			
			getCurrentPosition: function(callback,error,options){
				options = options || {};
				this.getCurrentCoord(function(r){
					map.getMapLocation(lang.mixin(r,options),callback,error);
				},error,options);
			},
			
			/**
			 * options.userId
			 * options.loginName
			 * options.ekpId
			 */
			openChat : function(options,callback,error){
				var defer = this._fetchUserId(options);
				defer.then(function(__options){
					KK.ready(function(){
						KK.econtact.startChat(__options,function(){
							callback && callback();
						},function(code, msg){
							console('发起会话失败：code：' + code + ',错误信息：' + msg);
							error && error(code);
						});
					});
				});
			},
			
			openUserCard : function(options){
				var defer = this._fetchUserId(options),
					self = this;
				defer.then(function(__options){
					KK.ready(function(){
						KK.econtact.showECard(__options,function(){},function(code,msg){
							console.log(msg);
							self['$super$openUserCard'] && self['$super$openUserCard'].call(self,options);
						})
					});
				});
			},
			
			_fetchUserId : function(options){
				var defer = new Deferred();
				options.userID = options.userId;
				if(!options.userID && !options.loginName && options.ekpId){
					var url = util.formatUrl('/third/im/kk/user.do?method=getUserId&fdId=' + options.ekpId);
					request(url,{handleAs : 'json'}).then(function(result){
						options.loginName = result.userId;
						defer.resolve(options);
					});
				}else{
					defer.resolve(options);
				}
				return defer;
			},
			
			//KK客户端大于6.0.1支持指纹审批
			supportfingerPrint : function(){
				if(this.kkVersionCompare(v1,'6.0.1') >= 0){
					return 0;
				}else{
					return 2;
				}
			},
			
			validatefingerPrint : function(callback, failCallback){
				var v1 = KK.app.getClientInfo().semver;
				if(this.kkVersionCompare(v1,'6.0.1') >= 0){
					KK.ready(function(){
						KK.security.verifyFingerprint(function(){
							callback && callback();
						},function(errorcode,errorMsg){
							//不支持指纹的先绕过
							if(errorcode == 1){
								callback && callback();
							}else if(errorcode==4){
								failCallback && failCallback();
							}else if(errorcode == 2 || errorcode ==3 || errorcode==5 || errorcode==9){
								var text = {
									text : errorMsg
								};
								Tip.fail(text);
								failCallback && failCallback(text);
							}
							console.log("指纹错误码:" + errorcode + "信息:" + errorMsg);
						});
					});
				}else{
					callback && callback();
				}
			},
			
			//人脸识别  kk客户端6.0.4才支持
			supportFacePrint : function(){
				var v1 = KK.app.getClientInfo().semver;
				if(this.kkVersionCompare(v1,'6.0.4') >= 0){
					return 0;
				}else{
					return 2;
				}
			},
			
			checkFace : function(callback, failCallback){
				var v1 = KK.app.getClientInfo().semver;
				if(this.kkVersionCompare(v1,'6.0.4') >= 0){
					KK.ready(function(){
						KK.security.checkFace(function () {
						  callback && callback();
						}, function (code, msg) {
							if(code == 1 || code == 2 || code == 3 || code == 4 || code == 5 || code == 7 || code == 6){
								Tip.fail({
									text : msg
								});
								failCallback && failCallback({
									text : msg,
									code:code
								});
							}
							//alert('发起人脸检查失败，错误代码：' + code + '; 错误详情：' + msg);
							console.log('发起人脸检查失败，错误代码：' + code + '; 错误详情：' + msg);
						})
					});
				}else{
					callback && callback();
				}
			},
			
			/**
			 * 是否支持拍照;
			 * 0 : 支持拍照
			 * 1 : 当前客户端不支持拍照
			 * 2 : 当前客户端版本不支持拍照
			 */
			supportCamera : function(){
				return 0;
			},
			
			/**
			 * 拍照验证
			 */
			validateCamera : function(callback,failCallback,options){
				var context = options || {};
				console.log(context);
				context.encodingType = 'jpeg';
				context.quality = 80;
				context.targetWidth = 1000;
				context.targetHeight = 2000;
				
				var sourceType = 'camera';
				var v1 = KK.app.getClientInfo().semver;
				if(this.kkVersionCompare(v1,'6.0.4') >= 0){
					sourceType = 'sysCamera';
				}
				var _options = {
					sourceType:sourceType,
					count : 1,
					quality : 80,
					destinationType:'file',
					encodingType: 'png',
					showVideo : '1'
				};
				KK.media.getPicture(_options,function(datas){
					debugger;
					for(var i = 0; i < datas.length;i++){
						var __data = datas[i],
							imageURI = __data.imageURI;
						var imageFileOSPath = __data.imageFileOSPath;
						KK.file.readAsBase64(imageURI,function(__args){
							var file = {
									href : 'data:image/png;base64,' + __args.content,
									size : '',
									name: "image" + new Date().getTime() + ".png",
									imageFileOSPath:imageFileOSPath,
									filePath:imageURI
								}
							callback && callback(file);
						},function(code,errMsg){
							if(code){
								failCallback({
									rtn:{'status': code ,'msg':'附件转换为base64过程发生错误,信息为：' + errMsg}
								});
							}
						});
					}
					
				},function(errCode,errMsg){
					console.log('错误信息:' + errMsg + ', 错误代码:' + errCode);
					failCallback && failCallback(errCode);
				});
			},
			
			openViewMap : function(options, callback, failcallback){
				KK.ready(function() {
					if(options && options.lng && options.lat) {
						if(options.coordType == 'bd09'){
							options.__coordType = 2;
						} else {
							options.__coordType = 1;
						}
						KK.location.navigate({
							fromAddress : options.fromAddress || '我的位置',
							targetLongitude : options.lng,
							targetLatitude : options.lat,
							targetAddress : options.value,
							coordinateType : options.__coordType,
						}, function() {
							callback && callback();
						}, function(code, msg) {
							window.console && console.log('导航失败， code: ' + code + ' ,msg: ' + msg);
						});
					} else {
						window.console && console.log('导航失败，缺少参数');
						failcallback && failcallback();
					}
				});
			},
			
			getNetworkType : function(callback){
				var self = this;
				KK.ready(function() {
					var v1 = KK.app.getClientInfo().semver;
					//KK客户端大于6.0.2支持获取网络类型
					if(self.kkVersionCompare(v1,'6.0.2') >= 0){
						KK.device.getNetType(function (res) {
							callback && callback({
								networkType : res.netType
							});
						}, function(){
							callback && callback();
							window.console && console.log('未知网络环境');
						});
					} else {
						callback && callback();
					}
				});
			},
			
			getWifiInfo : function(callback){
				var self = this;
				KK.ready(function() {
					var v1 = KK.app.getClientInfo().semver;
					//KK客户端大于6.0.2支持获取Wifi信息
					if(self.kkVersionCompare(v1,'6.0.2') >= 0){
						KK.device.getWifiInfo(function (res) {
							callback && callback({
								ssid  : res.ssid,
								macIp : res.ap_mac
							});
						}, function (code, msg) {
							Tip.fail({
								text : '获取wifi失败:'+ msg
							});
							window.console && console.log('获取wifi失败，错误信息:' + msg + ', 错误代码:' + code);
							callback && callback();
						});
					} else {
						Tip.fail({
							text : '获取wifi失败，kk需升级到6.0.2以上'
						});
						callback && callback();
					}
				});
			},
			
			getDeviceInfo : function(callback){
				KK.ready(function() {
					var deviceInfo = KK.app.getDeviceInfo();
					callback && callback({
						deviceId : deviceInfo && deviceInfo.deviceID
					});
				});
			},
			
			/**
			 * 获取字体信息
			 */
			getFontSize : function(callback){
				KK.ready(function() {
					var clientInfo = KK.app.getClientInfo();
					callback && callback({
						fontSize : clientInfo && clientInfo.fontSize ? clientInfo.fontSize : 0 
					});
				});
			},
			
			//6.1根据业务分类和业务ID获取群ID
			checkGroup : function(options, callback){
				if(this.getKKClient()){
					KK.ready(function(){
						KK.config( 'detailLog', true );
						KK.econtact.isBizGroupExists({
							bizType : options.bizType,   //业务类型
							bizID : options.bizId   //业务ID
						}, function(res){
							console.log('是否存在boolean:' + res.exists + ',群ID，exist=true时有效:' + res.groupID);
							var groupID = null;
							if(res.exists){
								groupID = res.groupID;
							}
							callback && callback({
								groupID : groupID
							});
						}, function(code, msg){
							if(code == '-3' || code == '-4'){
								msg = '网络不可用！';
							}
							Tip.fail({
								text : msg
							});
							console.log('错误信息:' + msg + ',错误代码:' + code);
						});
					});
				}
			},
			
			//6.2 打开KK客户端界面准备创建群
			createGroup : function(options){
				console.log(options);
				if(this.getKKClient()){
					KK.ready(function(){
						KK.econtact.createBizGroup({
							bizType : options.bizType,   //业务类型
							bizTypeName : options.bizTypeName,		 //业务类型中文名称
							bizTypeNameEn : options.bizTypeName,
							bizID : options.bizId,   //业务ID
							bizURL : options.bizUrl,   //业务URL
							groupName : options.groupName,
							 //描述卡片信息
							title : options.groupName,
							user : options.descUser,	    //卡片模板中的用户user的loginName
							time :options.descTime,		//卡片模板中的时间time，1970-1-1 00:00:00以来的毫秒数
							 //描述卡片信息
							creater : options.creater,
							users : options.users    ////参与者loginName
						},function(res){
							//success
							 console.log('创建协作群成功，群id为' + res.groupID);
						},function(code, msg){
							//fail
							Tip.fail({
								text : msg
							});
							console('创建协作群失败：code：' + code + '错误信息：' + msg);
						});
					});
				}
			},
			
			//6.3 查看群聊记录
			showGroupMessage : function(options){
				if(this.getKKClient()){
					KK.ready(function(){
						KK.econtact.genBizGroupChatMsgUrl({
							groupID : options.groupId
						},function(res){
							//success
							deviceType = device.getClientType();
							if(deviceType == 9){
								//kk5 ios
								window.open(res.url,'_blank');
							}else{
								window.open(res.url,'_self');
							}
							//location.href=res.url;
							console.log('获取群协作聊天记录链接成功');
						},function(code, msg){
							//fail
							Tip.fail({
								text : msg
							});
							console('获取群协作聊天记录链接失败：code：' + code + '错误信息：' + msg);
						});
					});
				}
			},
			
			
			getKKClient : function(){
				var v1 = KK.app.getClientInfo().semver;
				//KK6客户端大于6.0.1才支持群协作功能
				if(this.kkVersionCompare(v1,'6.0.1') >= 0){
					return true;
				}
				return false;
			},
			
			// 创建一个有提醒的日程
			addCalendar : function(options){
				KK.calendar.addEvent(options, function () {
				  console.log('日程创建成功');
				}, function (code, msg) {
				  console.log('创建日程失败，失败回调 code: ' + code +' ,msg: ' + msg );
				});
			},
			//分享消息至指定客户端(如微信好友, kk等)
			shareTo : function(context,callback,error){
				var dest = context.dest;
				var options = {
					recieverType : context.recieverType,
					type:context.type,
					recieverList:context.recieverList,
					content:context.content,
					showChooseView:context.showChooseView,
					imageUri:context.imageUri,
					url:context.url,
					title:context.title,
					bizType:context.bizType,
					summary:context.summary,
					attachmentType:context.attachmentType,
					attachmentName:context.attachmentName,
					attachmentUrl:context.attachmentUrl,
					attachmentSize:context.attachmentSize
				};
				
				KK.ready(function(){
					KK.share.to(dest,options,function(res){
						callback && callback();
					},function(code, msg){
						if(window.console)
							console.log('KK分享失败,错误代码：' + code + '; 错误详情：' + msg);
						error && error(msg);
						Tip.fail({
							text : 'KK分享失败'
						});
					});
				});
			},
			
			downloadByUrl : function(context,callback,error){
				var options = {
						url : context.url,
						path:context.path,
						isContinuous:context.isContinuous || false,
						appearInFileManager:context.appearInFileManager || false
					};
					console.log('downloadByUrl:' + JSON.stringify(options));
					KK.ready(function(){
						var proxy = new KK.proxy.Download(options,function(res){
							var processing = Tip.processing({text : '文件下载中...'});
							if(res.progress != 100){
								processing.show();
								console.log("下载进度："+res.progress);
							}else{
								processing.hide();
								console.log("文件地址："+res.path);
								callback && callback(res);
							}
							
						},function(code, msg){
							if(window.console){
								console.log("错误信息："+msg +",错误代码："+code);
							}
							var rtn={
								code:code,
								text:"错误信息："+msg
							};
							error && error();
						});
						proxy.start();
					});
			},
			//通过客户端路径上传附件
			uploadFileByApp:function(context,callback,error){
				var attSetting = context.options;
				if(!window.AttachmentList)
					window.AttachmentList = {};
				var attachmentObj = window.AttachmentList[attSetting.fdKey];
				if(!attachmentObj){
					attSetting.uploadStream = true;
					attachmentObj = new _Attachment(attSetting);
					window.AttachmentList[attSetting.fdKey] = attachmentObj;
				}
				KK.ready(function(){
					KK.file.getOSPath(context.path, function(rtn){
						console.log("系统路径为："+rtn.OSPath);
						var fileName = rtn.OSPath.substring(rtn.OSPath.lastIndexOf("/") + 1);
						var res ={filePath:context.path,fileUri:rtn.OSPath,fileName:fileName,name:fileName};
						KK.file.getFileInfo( res.filePath, function (info) {
								console.log('getFileInfo1:' + JSON.stringify(info));
					    		res.size=info.size;
					    		attachmentObj.startUploadFile(res);
					    		callback && callback(res);
				    		},function (code, msg) {
				    			console.log("获取文件信息径出错："+msg +",错误代码："+code);
				    			error && error(code);
				    		}
				    	);
						
					}, function(code,msg){
						console.log("获取文件系统路径出错："+msg +",错误代码："+code);
						error && error(code);
					});
				});
			},
			//已知某条聊天消息，判断当前用户是否为该消息所在聊天会话的成员
			isSessionMemberByMsg:function(options,callback,error){
				KK.ready(function(){
					KK.econtact.isSessionMemberByMsg(options,function(res){
						callback && callback(res);
					},function(code, msg){
						console.log('判断当前用户是否为该消息所在聊天会话的成员失败,错误代码是：' + code + ',错误信息是：' + msg);
						error && error(code);
					});
				});
			},
			//进入协作群会话
			sendGroup:function(options,callback,error){
				KK.ready(function(){
					KK.econtact.sendGroup(options,function(res){
						callback && callback(res);
					},function(code, msg){
						console.log('进入群聊会话失败：code：' + code + ',错误信息：' + msg);
						error && error(code);
					});
				});
			},
			genSessionChatMsgUrl : function(options,callback,error){
				if(this.getKKClient()){
					KK.ready(function(){
						KK.econtact.genSessionChatMsgUrl(options,function(res){
							console.log('获取聊天记录链接成功:'+res.url);
							callback && callback(res)
						},function(code, msg){
							Tip.fail({
								text : msg
							});
							console.log('获取聊天记录链接失败：code：' + code + ',错误信息：' + msg);
							error && error(code,msg);
						});
					});
				}
			}
		};
	return adapter;
});
