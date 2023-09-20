/*
 * 用于web客户端对应功能接口调用
 */
define(["mui/picslide/ImagePreview", "mui/device/web/attachment", "mui/util", "dojo/request", "dojo/Deferred","mui/dialog/Tip","dojo/_base/array","mui/map","dojo/_base/lang","dojo/request/script"],
	function(ImagePreview,Attachment,util,request,Deferred,Tip,array,map,lang,ioScript) {
		var adapter = {
			resetTempVal: {x:0,y:0,flag:false},
			/**
			 * 关闭当前窗口，浏览器表现为回到门户首页、第三方APP表现为回到工作台
			 */
			closeWindow : function() {
				location = dojoConfig.baseUrl ? dojoConfig.baseUrl : '/';
				return {};
			},

			/**
			 * 设置标题栏
			 */
			setTitle : function(text){
				document.title = text;
			},

			/**
			 * 后退，如果没得后退则调用关闭当前窗口
			 */
			goBack : function(spa) {
				var self = this;
				if(history.length > 1){

					var mdingclose = util.getUrlParameter(window.location.href, "mdingclose");
					if(mdingclose=="1"){
						this.closeWindow();
					}else{
						history.back();
					}
					/*if(!spa){
						//由于目前没有办法知道history.back是否生效,执行closeWindow还能生效，说明后退未能生效
						setTimeout(function(){
							self.closeWindow();
						},500);
					}*/
				}else{
					this.closeWindow();
					//location = dojoConfig.baseUrl ? dojoConfig.baseUrl : '/';
				}
				return {};
			},

			/**
			 * 前进
			 */
			goForward : function(){
				history.forward();
				return {};
			},

			/**
			 * 获取当前用户的userId
			 */
			getUserID : function(){
				window.building();
				return null;
			},

			/**
			 * 录音
			 */
			openSpeech:function(options){
				window.building();
				return null;
			},

			/**
			 * 相机
			 */
			openCamera:function(options){
				window.building();
				return null;
			},

			/**
			 * 选择附件，底层其实就是上传附件
			 */
			selectFile:function(options){
				this.uploadFile(options);
				return {};
			},

			uploadFile:function(context){
				var options = context.options;
				if(!window.AttachmentList)
					window.AttachmentList = {};
				var attachmentObj = window.AttachmentList[options.fdKey];
				if(!attachmentObj){
					attachmentObj = new Attachment(options);
					window.AttachmentList[options.fdKey] = attachmentObj;
				}
				if(context.evt.target){
					var files = context.evt.target.files;
					for(var i=0 ; i < files.length ; i++){
						var fileName = files[i].name;
						var fileType =files[i].type;
						// 需求要求不允许上传同名文件，同pc端保持一致 by zhugr 2017-09-07
						// 使用手机拍照功能上传附件时候，图片命名是一样的，因此上传图片可同名
						if(options && options.nameAtts ){
							if(array.indexOf(options.nameAtts, fileName) > -1 && fileType.indexOf("image")== -1){
								Tip.fail({'text':'不允许上传同名文件！'});
								continue;
							}else{
								options.nameAtts.push(fileName);
							}
						}
						var url =  this.readAsDataURL(files[i]);
						if(url){
							files[i].href = url;
						}
						if("false" === attachmentObj.startUploadFile(files[i])){
							options.nameAtts.pop();
						}
					}
				}else{
					attachmentObj.startUploadFile({
						href : context.evt.dataURL,
						size : context.evt.dataURL.size,
						name: "image.png"
					});
				}
				return {};
			},

			/**
			 * 播放录音
			 */
			playSpeech:function(options){
				window.building();
				return null;
			},

			/**
			 * 图片预览
			 */
			imagePreview:function(options){
				if(!this.preivew)
					this.preivew = new ImagePreview();
				this.preivew.play(options);
				return {};
			},

			/**
			 * 附件预览
			 */
			fileView:function(options){
			},

			/**
			 * 下载附件
			 */
			download : function(options) {
				//修复#87605
				window.open(util.formatUrl(options.href),'_blank')
				//location.href = util.formatUrl(options.href);
			},

			/**
			 * 截屏
			 */
			captureScreen:function(options){
				window.building();
				return null;
			},

			readAsDataURL:function(file){
				if(window.URL){
					return window.URL.createObjectURL(file);
				}else if(window.webkitURL){
					return window.webkitURL.createObjectURL(file);
				}
				return null;
			},

			// 加载完触发事件
			ready:function(callback,failcallback){
			},

			// 横屏竖屏切换，1:竖屏，2:横屏
			setOrientation:function(orientation){
			},

			// 横屏竖屏切换，1:竖屏，2:横屏
			getOrientation:function(callback, error){
			},

			/**
			 * coordType:1 GPS坐标,2 sogou经纬度,3 baidu经纬度,4 mapbar经纬度,5 腾讯、高德坐标
			 * 异步获取当前位置坐标
			 */
			getCurrentCoord : function(callback,error,options){
				var self = this;
				this._loadScript(function(result,parentCallBack) {
					var container = document.createElement('div');
					container.id = "Map_" + new Date().getTime();
					document.body.appendChild(container);
					if(window.AMap) {
						// 高德
						var map = new AMap.Map(container.id, {
							resizeEnable: true
						});
						AMap.plugin('AMap.Geolocation', function() {
							var geolocation = new AMap.Geolocation({
								enableHighAccuracy: true,//是否使用高精度定位，默认:true
								timeout: 10000,          //超过10秒后停止定位，默认：5s

							});
							map.addControl(geolocation);
							geolocation.getCurrentPosition(function(status,result){
								if(status == 'complete'){
									///data.position.lat,data.position.lng
									callback && callback({lat:result.position.lat,lng:result.position.lng,coordType:5});
								}else{
									if(window.console){
										window.console.error(result);
									}
								}
							});
						});
					} else if((result && result.mapType === 'qmap') ||  (window.qq && window.qq.maps)) {
						var tempCallBack =function(event){
							let r = event.data;
							// 接收位置信息
							if(r  && r.module == 'geolocation') {
								let temp=self.resetTempVal;
								if(temp && temp.flag===true){
									//点击一次刷新，执行一次
									let lat =r.lat;
									let lng =r.lng;
									if(lat !=temp.x && lng !=temp.y){
										//重复的值不进行赋值
										self.resetTempVal ={
											x:lat,
											y:lng,
											flag:false
										}
										//定位成功,防止其他应用也会向该页面post信息，需判断module是否为'geolocation'
										callback && callback({lat:lat,lng:lng,coordType:5});
									}
								}
							}
						}
						window.removeEventListener('message', tempCallBack ,false);
						//监听定位组件的message事件
						window.addEventListener('message', tempCallBack ,false);

					} else {
						// 百度
						var geolocation = new BMap.Geolocation();
						geolocation.getCurrentPosition(function(r){
							if(this.getStatus() == BMAP_STATUS_SUCCESS){
								callback && callback({lat:r.point.lat,lng:r.point.lng,coordType:3});
							}else {
								error && error(this.getStatus());
							}
						},{
							enableHighAccuracy: true
						});
					}
				},callback);
			},
			/**
			 *
			 * 异步获取地理位置信息
			 */
			getCurrentPosition: function(callback,error,options){
				options = options || {};
				this.getCurrentCoord(function(r){
					map.getMapLocation(lang.mixin(r,options),callback,error);
				},error,options);
			},

			/**
			 * 异步获取附近景点
			 */
			getPois : function(callback,error) {
				this.getCurrentCoord(function(r){
					var url = util.formatUrl("/sys/mobile/bapi.do?method=getPoisList");
					request.post(url, {
						data : {
							coord : r.point.lat + ',' + r.point.lng
						},
						handleAs : 'json'
					}).response.then(
						function(data){
							if(data.status == '200'){
								if(callback)
									callback(data);
							} else {
								if(window.console)
									console.error('errorCode:'+data);
							}
						},
						function(err){
							if(window.console)
								console.error(err);
						});
				},error);
			},
			//浏览器定位默认使用百度定位
			_loadScript:function(callback,parentCallBack) {
				var self= this;
				if(window.BMap || window.AMap) {
					if(callback)
						callback(window.Map_Result || {});
					return;
				}
				var url = util.formatUrl("/sys/attend/map/sysAttendMapConfig.do?method=getCurrentMap");
				request.post(url, {
					handleAs : 'json'
				}).then(function(result) {
					window.Map_Result = result;
					var script = document.createElement('script');
					script.type = 'text/javascript';
					if(result.mapType === 'amap') {
						script.src = "https://webapi.amap.com/maps?v=1.4.15&key=" + result.mapKey + "&plugin=AMap.Geolocation&callback=bInitCallback";
						window.bInitCallback = function() {
							callback && callback(result);
						};
						document.body.appendChild(script);
					} else if(result.mapType === 'qmap') {
						var qMapGeolocation = document.getElementById("qMapGeolocation");
						self.resetTempVal={x:0,y:0,flag:true};
						if(qMapGeolocation ==null){
							var iframe = document.createElement('iframe');
							iframe.id = 'qMapGeolocation';
							iframe.style.display='none'
							iframe.src ='https://apis.map.qq.com/tools/geolocation?key='+result.mapKey+'&referer='+result.mapKeyName;
							document.body.appendChild(iframe);

							callback && callback(result);
						} else {
							qMapGeolocation.contentWindow.postMessage('getLocation', '*');
						}
					} else {
						script.src = "https://api.map.baidu.com/api?v=2.0&ak=" + result.mapKey + "&s=1&callback=bInitCallback";
						window.bInitCallback = function() {
							callback && callback(result);
						};
						document.body.appendChild(script);
					}


				}, function(result) {

					var script = document.createElement('script');
					script.type = 'text/javascript';
					var key = dojoConfig.map.bMapKey || 'cnG6G1wW70lQ36H693uVOyOXiwvMaph3';
					script.src = "https://api.map.baidu.com/api?v=2.0&ak=" + key + "&s=1&callback=bInitCallback";
					window.bInitCallback = function(){
						callback && callback();
					};
					document.body.appendChild(script);
				});
			},

			/**
			 * 打电话
			 */
			callPhone:function(phone){
				location.href="tel:" + phone;
			},

			/**
			 * 发短信
			 */
			sendMsg:function(phone){
				location.href="sms:" + phone;
			},

			/**
			 * 打开新窗口（新应用）
			 */
			open : function(url,target,contxt) {
				if(target === '_top'){
					try {
						window.open(url, '_top');
					}catch (e) {
						if(window.console){
							console.error(e);
						}
						Tip.tip({
							icon : 'mui mui-warn',
							text : 'URL跳转失败！'
						});

					}
					return;
				}
				window.open(url, '_self');
			},

			/**
			 * 调起扫码
			 */
			scanQRCode : function(callback){
				Tip.tip({
					icon : 'mui mui-warn',
					text : 'web端暂不支持扫码功能！'
				});
				return false;
			},

			/**
			 * 打开APP会话窗口
			 */
			openChat : function(){
				return;
			},

			canUseJinGe:function(cfg,callback){
				return;
			},

			/**
			 * 打开员工详细信息页面
			 */
			openUserCard : function(options){
				if(options.ekpId){
					window.location.href = util.formatUrl('/sys/zone/index.do?userid=' + options.ekpId);
				}
			},

			/**
			 * 是否支持指纹识别;
			 * 0 : 支持指纹审批
			 * 1 : 当前客户端不支持指纹审批
			 * 2 : 当前客户端版本不支持指纹审批
			 */
			supportfingerPrint : function(){
				return 1;
			},

			/**
			 * 指纹识别
			 */
			validatefingerPrint : function(callback,failCallback){
				callback && callback();
			},

			/**
			 * 是否支持刷脸审批;
			 * 0 : 支持刷脸审批
			 * 1 : 当前客户端不支持刷脸审批
			 * 2 : 当前客户端版本不支持刷脸审批
			 */
			supportFacePrint : function(){
				return 1;
			},

			/**
			 * 刷脸审批
			 */
			checkFace : function(callback,failCallback){
				callback && callback();
			},

			/**
			 * 是否支持拍照;
			 * 0 : 支持拍照
			 * 1 : 当前客户端不支持拍照
			 * 2 : 当前客户端版本不支持拍照
			 */
			supportCamera : function(){
				return 1;
			},

			/**
			 * 拍照验证
			 */
			validateCamera : function(callback,failCallback,options){
				callback && callback();
			},

			/**
			 * 打开对应客户端支持的地图(查看)
			 */
			openViewMap : function(options,callback,failcallback){
				failcallback && failcallback();
			},

			/**
			 * 打开对应客户端支持的地图(编辑)
			 */
			openEditMap : function(options,callback,failcallback){
				failcallback && failcallback();
			},

			/**
			 * 获取网络类型
			 */
			getNetworkType : function(callback){
				callback && callback({});
			},

			/**
			 * 获取接入热点信息
			 */
			getWifiInfo : function(callback){
				callback && callback({});
			},

			/**
			 * 获取设备信息，包含唯一设备号
			 */
			getDeviceInfo : function(callback){
				callback && callback({});
			},

			/**
			 * 获取字体信息
			 */
			getFontSize : function(callback){
				callback && callback({
					fontSize : 0
				});
			},

			/**
			 *创建一个有提醒的日程
			 */
			addCalendar : function(options){
				if(options && typeof options.webAdapterCallback === "function"){
					options.webAdapterCallback();
				}
			}
		};
		return adapter;
	});