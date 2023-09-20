/**
 * 用于钉钉客户端对应功能接口调用
 */
define(["https://g.alicdn.com/dingding/dingtalk-jsapi/2.10.3/dingtalk.open.js",
		"dojo/_base/lang","mui/device/device","dojo/request","mui/util","mui/coordtransform",
		"mui/i18n/i18n!sys-attachment","mui/dialog/Tip","dojo/Deferred","dojo/topic","mui/map","mui/device/web/attachment","mui/base64"],
		function(dd,lang,device,request, util, coordtransform, Msg ,Tip ,Deferred,topic,map,Attachment,base64) {

		var ___ready___ = false,
			___readyfail___ = false;

		var adapter = {

			__readyCallbacks : [],

			__readyfailCallbacks : [],

			__hasListener__ : false,

			/**
			 * 内部ready,需要授权的功能都要先调用此ready,回调中this已`修正`
			 * @param callback
			 * 		钉钉授权成功回调函数
			 * @param failcallback
			 * 		钉钉授权失败回调函数，该回调函数尝试给开发者一个机会去调用父类(web端)的基座能力,父类基座能力可以采用$super$XXX的方式调用
			 */
			ready : function(callback,failcallback){
				if(___readyfail___){
					failcallback && failcallback.call(this);
					return;
				}
				if(___ready___){
					callback && callback.call(this);
					return;
				}
				this.__readyCallbacks.push(callback);
				this.__readyfailCallbacks.push(failcallback);
				if(!this.__hasListener__){
					this.__hasListener__ = true;
					topic.subscribe('/third/ding/ready',lang.hitch(this,function(){
						var cb = null;
						while(cb = this.__readyCallbacks.shift()){
							cb && cb.call(this);
						}
					}));
					topic.subscribe('/third/ding/readyfail',lang.hitch(this,function(){
						var failcb = null;
						while(failcb = this.__readyfailCallbacks.shift()){
							failcb && failcb.call(this);
						}
					}));
				}
			},

			/**
			 * 关闭当前窗口，表现为回到钉钉工作台
			 */
			closeWindow : function(){
				this.ready(function(){
					dd.biz.navigation.close();
				},function(){
					dd.biz.navigation.close(); //修复 #100907
					//this['$super$closeWindow'] && this['$super$closeWindow'].apply(this);
				});
				return {};
			},

			/**
			 * 分享内容到钉钉
			 */
			share2ding : function(options){
				console.log(options);
				dd.biz.chat.pickConversation({
					corpId: options.corpId, //企业id
					isConfirm:'true', //是否弹出确认窗口，默认为true
					onSuccess : function(rs) {
						$.post(dojoConfig.baseUrl+'third/ding/ThirdDingShare.do?method=share',{
							'cid':rs.cid,
							'title':rs.title,
							'reqUrl':options.reqUrl,
							'fdSubject': options.fdSubject,
							'fdContent':options.fdContent,
							'fdContentPro':options.fdContentPro,
							'fdModelName':options.fdModelName,
							'fdModelId':options.fdModelId
						},function(result){
							var data = $.parseJSON(result);
							if(data.error == 0){
								Tip.success({
									text : '分享成功'
								});
							}else{
								Tip.fail({
									text : '分享失败:'+data.msg
								});
							}

						});
					},
					onFail : function(err) {
						console.log("error:",err);
						if(err.errorCode && err.errorCode != 3 ){
							Tip.fail({
								text : 'fail:'+err.errorMessage
							});
						}
					}
				});

			},

			/**
			 * 注册钉钉resume事件：当页面重新可见并可交互时，钉钉会产生回调，开发者可监听此resume事件，并处理开发者自己的业务逻辑。
			 * @param callback
			 */
			resume: function(callback){
				console.log("注册钉钉resume事件监听")
				document.addEventListener('resume', callback);
			},

			/**
			 * 设置标题；服气，设置个标题都要调api
			 */
			setTitle: function(title){

				if (!title) {
					title = "";
				}
				title = title.trim();
				dd.biz.navigation.setTitle({
					title : title,
					onSuccess : function(result) {
					},
					onFail : function(err) {
					}
				});
				this.ready(function(){  //修复 #107617
					dd.biz.navigation.setTitle({
						title : title,
						onSuccess : function(result) {
						},
						onFail : function(err) {
						}
					});
				},function(){
					//#114854
					console.log("ready失败！")
					dd.biz.navigation.setTitle({
						title : title,
						onSuccess : function(result) {
						},
						onFail : function(err) {
						}
					});
				});
				// 这个也得手动设置下，否则调用setTitle后使用document.title获取标题错误
				document.title = title;
			},

			imagePreview3 : function(options){
				this['$super$imagePreview'] && this['$super$imagePreview'].call(this,options);
				return {};
			},

			/**
			 * 调用钉钉图片预览接口
			 */
			imagePreview : function(options){
				this.ready(function(){
					if(options.curSrc.indexOf("blob:")==0){
						this['$super$imagePreview'] && this['$super$imagePreview'].call(this,options);
					}else{
						dd.biz.util.previewImage({
						    current: options.curSrc , // 当前显示图片的http链接
						    urls: options.srcList, // 需要预览的图片http链接列表
						    onSuccess : function(result) {
		                        /**/
		                    },
		                    onFail : function(err) {
		                    	this['$super$imagePreview'] && this['$super$imagePreview'].call(this,options);
		                    }
						});
					}
				},function(){
					this['$super$imagePreview'] && this['$super$imagePreview'].call(this,options);
				});
				return {};
			},

			sleep : function(time) {
			    var startTime = new Date().getTime() + parseInt(time, 10);
			    while(new Date().getTime() < startTime) {}
			},

			downloadToDing : function(options,repeat){
				var _url='';
				var _corpid='';
				var errmsg='';
				var timestamp = options.time;

				$.ajaxSettings.async = false;
	            $.post(dojoConfig.baseUrl+'sys/attachment/sys_att_main/sysAttMain.do?method=downloadToOther',{
	            	'fdId': options.fdId,
	            	'key': 'ding',
	            	't': timestamp
		        },null);
	            $.ajaxSettings.async = true;

	            if(repeat){
		            var isSuccess = false;
		            var count=0;
		            while(count < 3){
		            	$.ajaxSettings.async = false;
			            $.post(dojoConfig.baseUrl+'sys/attachment/sys_att_main/sysAttMain.do?method=isSuccess',{
			            	'fdId': options.fdId,
			            	'key': 'ding',
			            	't': timestamp,
			            	'type': '0'
				        },function(result){
				        	if(result.status == "0"){
				        		isSuccess = true;
				        		_url=result['msg'].url;
				        		_corpid=result['msg'].corpId;
					        	_name=result['msg'].name;
				        	}
				        });
			            $.ajaxSettings.async = true;
			            if(isSuccess){
			            	break;
			            }
			            count=Number(count)+1;
			            if(count==3){
			            	Tip.tip({
			            		time : 5000,
								text : Msg['mui.sysAttMain.download.oversize']
							});
			            }else{
			            	this.sleep(5000);
			            }
		            }

		            if(_url != '' && _corpid != ''){
		            	var options = {
		    					corpId:_corpid,
		    					url:_url,
		    				    name:_name
		    			};
		    			this.saveFile(options);
		            }else{
		            	//alert("保存失败！");
		            }
	            }
			},

			isSuccess : function(fdId,timestamp){
		    	var isSuccess = false;
		    	$.ajaxSettings.async = false;
		        $.post(dojoConfig.baseUrl+'sys/attachment/sys_att_main/sysAttMain.do?method=isSuccess',{
		        	'fdId': fdId,
		        	'key': 'ding',
		        	't': timestamp,
		        	'type': '0'
		        },function(result){
		        	if(result.status == "0"){
		        		isSuccess = true;
		        	}
		        });
		        $.ajaxSettings.async = true;

		        return isSuccess;
			},

			// 校验文件类型
			checkFileTypeByDing : function(fileName,fileType) {
				var fileTypes= new Array(".js",".bat",".exe",".sh");
				if(fileName && fileType){
					var type = "."+fileType;
					var isPass = true;
					for (i=0;i<fileTypes.length ;i++ ){
						if(type == fileTypes[i]){
							isPass = false;
							break;
						}
					}
					if(!isPass){
						Tip.tip({text:'系统不允许上传“'+fileTypes+'”类型附件.'});
						return true;
					}
				}
				return false;
			},

			// 是否为禁止文件
			isTypeDisable : function(file,jsonParams) {
				if(file.fileName && typeof(jsonParams.fdDisabledFileType) != "undefined"){
					var fileType = null;
					var _fileName = file.fileName;
					fileType = _fileName.substring(_fileName.lastIndexOf("."));
					fileType = fileType.toLowerCase();
					var fileTypes= new Array();
					fileTypes = jsonParams.fdDisabledFileType.split(';');
					if("1"==jsonParams.fileLimitType){
						var isPass = true;
						for (i=0;i<fileTypes.length ;i++ )
						{
							if(fileType == fileTypes[i]){
								isPass = false;
								break;
							}
						}
						if(!isPass){
							Tip.tip({text:'系统不允许上传“'+this.fdDisabledFileType+'”类型附件.'});
							return true;
						}
					}else if("2"==jsonParams.fileLimitType){
						var isPass = false;
						for (i=0;i<fileTypes.length ;i++ )
						{
							if(fileType == fileTypes[i]){
								isPass = true;
								break;
							}
						}
						if(!isPass){
							Tip.tip({text:'系统只允许上传“'+this.fdDisabledFileType+'”类型附件.'});
							return true;
						}
					}
				}
				return false;
			},
			// 是否超过文件大小限制
			isSizeDisable : function(file,jsonParams) {
				if (jsonParams.fdSmallMaxSize && null != jsonParams.fdSmallMaxSize
					&& "pic" != jsonParams.fdAttType) {
					if (file.size > (jsonParams.fdSmallMaxSize * 1024 * 1024)) {
						Tip.tip({
							text : '单个附件上传最大不超过'
								+ jsonParams.fdSmallMaxSize
								+ 'MB,请重新选择文件'
						});
						return true;
					}
				}
				return false;
			},
			// 该上传图片是否合规
			isPicDisable : function(file,jsonParams) {
				var _fileName = file.fileName;
				if ("pic" == jsonParams.fdAttType) {
					if (_fileName.lastIndexOf(".") > -1) {
						var fileType = _fileName
							.substring(_fileName
								.lastIndexOf("."));
						fileType = fileType.toLowerCase();
						if (".gif" != fileType
							&& ".jpg" != fileType
							&& ".jpeg" != fileType
							&& ".png" != fileType) {
							Tip.tip({
								text : '仅支持gif、jpg、jpeg、png格式'
							});
							return true;
						}
					}
					if (null != jsonParams.fdImageMaxSize) {
						if (file.fileSize > (jsonParams.fdImageMaxSize * 1024 * 1024)) {
							Tip.tip({
								text : '图片上传最大不超过'
									+ jsonParams.fdImageMaxSize
									+ 'MB,请重新选择图片'
							});
							return true;
						}
					}
				}
				return false;
			},

			getToken: function (file) {
				var tokenUrl = util.formatUrl("/sys/attachment/sys_att_main/sysAttMain.do?method=handleAttUpload&gettype=getuserkey&format=json",true);
				var fileName = encodeURIComponent(file.fileName);
				var extendData = "filesize=" + file.fileSize + "&fileName=" + fileName + "&fdAttMainId=" + "&md5=";
				var userKey;
				request.post(tokenUrl, {
					data : extendData,
					handleAs : 'json',
					sync: true
				}).then(function(data) {
					if (data.status == '1') {
						file.status = 1;
						var token = data.token;
						userKey = (token && token.header) ? token.header.userkey : '';
					} else {
						Tip.fail({
							text : '上传附件失败'
						});
					}
				}, function(data) {
					Tip.fail({
						text : '上传附件失败'
					});
				});
				return userKey;
			},

			downloadFromDing : function(options,jsonParams){
				var _this = this;
				this.ready(function() {
				    dd.runtime.permission.requestAuthCode({
				        corpId:options.corpId, // 企业id
				        onSuccess: function (info) {
				                  var code = info.code // 通过该免登授权码可以获取用户身份
				                  dd.biz.util.uploadAttachment({
				      			    space:{corpId:options.corpId,isCopy:0 , max:1},
				      			    types:["space"],//PC端支持["photo","file","space"]
				      			    onSuccess : function(result) {
				      			    	//点击确定后
										// 类型校验
										if (_this.checkFileTypeByDing(result.data[0].fileName,result.data[0].fileType)) {
											return;
										}
										// 类型不允许
										if (_this.isTypeDisable(result.data[0],jsonParams)) {
											return;
										}
										// 大小不允许
										if (_this.isSizeDisable(result.data[0],jsonParams)) {
											return;
										}
										// 图片不合规
										if (_this.isPicDisable(result.data[0],jsonParams)) {
											return;
										}
										var token = _this.getToken(result.data[0]);
										if (!token) {
											return;
										}

										if(!window.AttachmentList)
											window.AttachmentList = {};

										var attachmentObj = window.AttachmentList[options.fdKey];
										if(!attachmentObj){
											attachmentObj = new Attachment(options);
											window.AttachmentList[options.fdKey] = attachmentObj;
										}

										//选中文件，点击确定后...
										var fdSign = base64.encode(result.data[0].fileName);
										fdSign = fdSign.replace(/\+/g,"");
										fdSign = fdSign.replace(/\//g,"");
										fdSign = fdSign.replace(/\=/g,"");

										var procTip = Tip.processing();
										procTip.show();
										$.post(dojoConfig.baseUrl+'sys/attachment/sys_att_main/sysAttMain.do?method=uploadToEkp',{
											'code':code,
											'key': 'ding',
											'fdSign': fdSign,
											'token': token,
											'spaceId':result.data[0].spaceId,
											'fileId':result.data[0].fileId,
											'fileName':result.data[0].fileName,
											'fileSize':result.data[0].fileSize,
											'fileType':result.data[0].fileType,
											'fdAttType':options.fdAttType,
											'fdKey':options.fdKey,
											'fdModelId':options.fdModelId,
											'fdModelName':options.fdModelName
				      			        },function(res){
											//调用接口响应后...isSuccess

											topic.publish('/third/ding/status/' + options.fdKey,{
												url : dojoConfig.baseUrl+'sys/attachment/sys_att_main/sysAttMain.do?method=isSuccess',
												fdId : res.fdAttId+";"+res.fileId,
												size : result.data[0].fileSize,
												name : result.data[0].fileName,
												attachment : attachmentObj,
												obj : _this,
												tip : procTip,
												_fdAttId: res.fdAttId
											});
				      			        });
				      			    },
									  onFail : function(err) {

									  }
				      			});
				        }});
				},function () {
					Tip.fail({
						text : 'ready失败'
					});
				});

			},

			saveFile : function(options){
				this.ready(function(){
					dd.biz.cspace.saveFile({
				        corpId:options.corpId,
				        url:options.url,  // 文件在第三方服务器地址， 也可为通过服务端接口上传文件得到的media_id，详见参数说明
				        name:options.name,
				        onSuccess: function(data) {
				                },
				        onFail: function(err) {
				            // alert(JSON.stringify(err));
				        }
				    });
				},function(){
					Tip.fail({
						text : 'ready失败'
					});
				});
			},

			/**
			 * 打开页面
			 */
			open : function(url, target, context){
				if(url.indexOf("/")==0){
					url=util.getHost()+url;
				}
				// #168054 页面在iframe里，调钉钉api不生效
				if(!(window.self === window.top)){
					window.open(url, '_self');
					return;
				}
				try{
					// #147454 使用window.open在钉钉上打开，返回时有兼容问题，所以open页面全部使用openLink打开
					dd.biz.util.openLink({
						url : url,
						onFail : function(err){
							window.open(url, '_self');
						}
					});
					return;
				}catch(e){
					window.open(url, '_self');
					return;
				}
			},

			/**
			 * 拉起钉钉会话窗口
			 */
			openChat : function(options){
				this.ready(function(){
					var defer = this._fetchUserId(options);
					defer.then(function(__options){
						dd.biz.chat.openSingleChat(__options);
					});
				},function(){
					this['$super$openChat'] && this['$super$openChat'].call(this,options);
				});
			},

			/**
			 * 打开钉钉员工信息页面
			 */
			openUserCard : function(options){
				this.ready(function(){
					var defer = this._fetchUserId(options),
						self = this;
					defer.then(function(__options){
						dd.biz.util.open({
							name : 'profile',
							params: {
								id : __options.userId,
								corpId : __options.corpId
							},
							onFail : function(err){
								console.log('errorMessage:' + err.errorMessage);
								self['$super$openUserCard'] && self['$super$openUserCard'].call(self,options);
							}
						});
					});
				},function(){
					this['$super$openUserCard'] && this['$super$openUserCard'].call(this,options);
				});
			},

			_fetchUserId : function(options){
				options.userId = options.loginName;
				var defer = new Deferred();
				if(!options.userId && options.ekpId){
					//--------获取当前使用的集成组件--------------
					var prefix = null;
					// 优化同步方法
					var getUrl = dojoConfig.baseUrl + 'sys/mobile/adapter.do?method=getDingPrefix',
					getOption = { data : { url : location.href },  handleAs : 'json'};
					request.post(getUrl ,getOption).response.then(function(rtn){
						var info = rtn.data;
						prefix = info.url;

						var url = util.formatUrl("/" + prefix + '/user.do?method=getUserId&fdId=' + options.ekpId);
						request(url,{handleAs : 'json'}).then(function(result){
							options.userId = result.userId;
							options.corpId = result.corpId;
							defer.resolve(options);
						});

					},function(){
						console.log("请求失败！");
					});


				}else{
					defer.resolve(options);
				}
				return defer;
			},

			/**
			 * 钉钉客户端下载
			 */
			download : function(options) {
				var isIOS = function(){
					var ua = navigator.userAgent.toLowerCase();
					if (/(iPhone|iPad|iPod|iOS)/i.test(ua)) {
						return true;
					}
					return false;
				};
				var isAndriod = function() {
					var ua = navigator.userAgent.toLowerCase();
					if (ua.indexOf('android') > -1) {
						return true;
					}
					return false;
				};
				if(isIOS()){
					var name = options.name.toLowerCase(),
						fileExt = name.substring(name.lastIndexOf(".") + 1);
					if(fileExt == 'zip' || fileExt == 'rar'){
						Tip.fail({
							text : '暂不支持此文件类型打开'
						});
						return;
					}
				}
				if (!isAndriod()){
					location.href = util.formatUrl(options.href);
					return;
				}

				var downloadUrl = util.formatUrl(options.href);
				$.ajaxSettings.async = false;
				$.post(dojoConfig.baseUrl+'sys/attachment/sys_att_main/sysAttMain.do?method=getSignDownload',{
					'fdAttMainId': options.fdId
				},function(result){
					if(result.hasAtt == true){
						if(result.hasRest){
							//1次性下载
							downloadUrl=util.formatUrl("/resource/jsp/download.jsp?url="+encodeURIComponent(result.downloadUrl));
						}else{
							Tip.fail({
								text : "未配置附件rest服务访问策略"
							});
						}
					}else{
						Tip.fail({
							text : "附件不存在"
						});
					}
				});
				$.ajaxSettings.async = true;
				location.href = downloadUrl;
				return;
			},

			/**
			 * 拉起钉钉扫码
			 */
			scanQRCode : function(callback){
				this.ready(function(){
					dd.biz.util.scan({
						type: 'qrCode',
					    onSuccess: function(data) {
					    	callback && callback({
					    		text : data.text
					    	});
					    },
					   onFail : function(err) {
					   }
					});
				},function(){
					this['$super$scanQRCode'] && this['$super$scanQRCode'].call(this,callback);
				});
				return true;
			},

			openViewMap : function(options, callback, failcallback){
				this.ready(function(){
					var tCoord = [options.lng, options.lat];
					//如果坐标系为百度,尝试做下非标准转换让坐标在高德地图上显示位置更精准
					if(options.coordType == 'bd09'){
						tCoord = coordtransform.bd09togcj02(options.lng, options.lat);
					}
					dd.biz.map.view({
						longitude : tCoord[0],
						latitude : tCoord[1],
						title : options.value
					});
					callback && callback();
				},function(){
					this['$super$openViewMap'] && this['$super$openViewMap'].call(this,options, callback, failcallback);
				});
			},

			openEditMap : function(options,callback, failcallback){
				this.ready(function(){
					var tCoord = [options.lng, options.lat];
					//如果坐标系为百度,尝试做下非标准转换让坐标在高德地图上显示位置更精准
					if(options.coordType == 'bd09' && options.lng && options.lat){
						tCoord = coordtransform.bd09togcj02(options.lng, options.lat);
					}
					dd.biz.map.locate({
						longitude : tCoord[0],
						latitude : tCoord[1],
						onSuccess : function(result){
							var point = {
								lat : result.latitude,
								lng : result.longitude
							};
							if(options.coordType == 'bd09' ){
								var coord = coordtransform.gcj02tobd09(point.lng, point.lat);
								point.lng = coord[0];
								point.lat = coord[1];
							}
							callback && callback({
								point : point,
								value : result.title,
								detail : result.snippet
							})
						}
					})
				},function(){
					this['$super$openEditMap'] && this['$super$openEditMap'].call(this,options, callback, failcallback);
				});
			},

			/**
			 * 获取网络类型(wifi 2g 3g 4g unknown none, none表示离线)
			 */
			getNetworkType : function(callback){
				this.ready(function(){
					dd.device.connection.getNetworkType({
						onSuccess : function(data){
							callback && callback({
								networkType : data.result
							});
						},
						onFail : function(err) {
							callback && callback();
						}
					});
				},function(){
					this['$super$getNetworkType'] && this['$super$getNetworkType'].call(this,callback);
				});
			},

			/**
			 * 获取接入热点信息
			 */
			getWifiInfo : function(callback){
				var self = this;
				this.ready(function(){
					dd.device.base.getInterface({
						onSuccess : function(data){
							callback && callback({
								ssid  : data.ssid,
								macIp : self._fixMacIp(data.macIp)
							});
						},
						onFail : function(err) {
							Tip.fail({
								text : '获取WIFI信息失败:'+err
							});
							callback && callback();
						}
					});
				},function(){
					Tip.fail({
						text : '调用钉钉wifi接口授权失败'
					});
					this['$super$getWifiInfo'] && this['$super$getWifiInfo'].call(this,callback);
				});
			},

			// IOS端获取的wifi mac地址少了低位0
			_fixMacIp : function(macIp){
				var formatMapIp = '';
				if(macIp){
					var blocks = macIp.split('\:');
					var bitsArr = [];
					for(var i in blocks){
						var bits = blocks[i] || '';
						if(bits.length == 0){
							bits = '00';
						} else if(bits.length == 1){
							bits = '0' + bits[0];
						}
						bitsArr.push(bits);
					}
					formatMapIp = bitsArr.join(':')
				}
				return formatMapIp;
			},

			getDeviceInfo : function(callback){
				this.ready(function(){
					dd.device.base.getUUID({
					    onSuccess : function(data) {
					       callback && callback({
								deviceId : data.uuid
							});
					    },
					    onFail : function(err) {
							callback && callback();
						}
					});
				});
			},

			//创建钉钉群聊  第一步 调用钉钉 选择选择部门和人员jsapi  第二步发起群聊
			complexPicker : function(options, callback){
				this.ready(function(){
					dd.biz.contact.complexPicker({
					    title:options.title,            //标题
					    corpId:options.corpId,              //企业的corpId
					    multiple:true,            //是否多选
					    limitTips:"超出了",          //超过限定人数返回提示
					    maxUsers:1000,            //最大可选人数
					    pickedUsers:options.pickedUsers,            //已选用户
					    pickedDepartments:[],          //已选部门
					    disabledUsers:[],            //不可选用户
					    disabledDepartments:[],        //不可选部门
					    requiredUsers:[],            //必选用户（不可取消选中状态）
					    requiredDepartments:[],        //必选部门（不可取消选中状态）
					    appId:options.appId,              //微应用的Id
					    permissionType:"GLOBAL",          //选人权限，目前只有GLOBAL这个参数
					    responseUserOnly:true,        //返回人，或者返回人和部门 true表示返回人，false返回人和部门
					    startWithDepartmentId:0 ,   // 0表示从企业最上层开始
					    onSuccess: function(result) {
					        /**
					        {
					            selectedCount:1,                              //选择人数
					            users:[{"name":"","avatar":"","emplId":""}]，//返回选人的列表，列表中的对象包含name（用户名），avatar（用户头像），emplId（用户工号）三个字段
					            departments:[{"id":,"name":"","number":}]//返回已选部门列表，列表中每个对象包含id（部门id）、name（部门名称）、number（部门人数）
					        }
					        */
					    	callback && callback({
					    		users : result.users
							});
					    },
					   onFail : function(err) {
					    	console.log(err)
						    /*Tip.fail({
								text : err.errorMessage
							});*/
					   }
					});
				},function(){
					Tip.tip({
		    			icon : 'mui mui-warn',
						text : '调钉钉jsapi:dd.biz.contact.complexPicker失败'
					});
				});
			},

			doCreageScenegroup : function(options, callback){
				var url = baseUrl + "third/ding/scenegroup/third_ding_scenegroup_mapp/thirdDingScenegroupMapp.do?method=createScenegroup";
				var data = {"title":options.title, "fdId" : options.fdId, "modelName" : options.modelName, "fdKey" : options.fdKey, "moduleKey" : options.moduleKey, "ownerPersonId" : options.ownerPersonId, "users" : options.users };
				request.post(url, {
					data : options,
					handleAs : 'json',
					sync: true
				}).then(function(data){
					Tip.fail({
						text : "场景群创建成功"
					});
					callback && callback({
			    		data : data
					});
				},function(data){
					Tip.fail({
						text : "调用createScenegroup接口失败"
					});
					return;
				}
				)
			},

			// 创建场景群
			creageScenegroup : function(options, callback){
				var baseUrl = dojoConfig.baseUrl;
				var device_this = this;
				var url = baseUrl + "third/ding/scenegroup/third_ding_scenegroup_mapp/thirdDingScenegroupMapp.do?method=getCreateScenegroupInfo";
				var data = { "fdId" : options.fdId, "modelName" : options.modelName, "fdKey" : options.fdKey, "moduleKey" : options.moduleKey, "ownerPersonId" : options.ownerPersonId, "personIds" : options.personIds };
				request.post(url, {
					data : data,
					handleAs : 'json',
					sync: true
				}).then(function(data_res){
					if(data_res != null){
						var result = data_res.result;
						if(result==true){
							var data = data_res.data;
							options.appId = data.appId;
							options.corpId = data.corpId;
							options.pickedUsers = data.pickedUsers;
							var device_this2 = device_this;
							device_this.complexPicker(options, function(users){
								users = users.users;
								var usersStr = "";
								$.each(users, function (n, value) {
						               usersStr += value.emplId+";";
						           });
								var baseUrl = dojoConfig.baseUrl;
								var url = baseUrl + "third/ding/scenegroup/third_ding_scenegroup_mapp/thirdDingScenegroupMapp.do?method=createScenegroup";
								var data = {"title":options.title, "fdId" : options.fdId, "modelName" : options.modelName, "fdKey" : options.fdKey, "moduleKey" : options.moduleKey, "ownerPersonId" : options.ownerPersonId, "users" : usersStr };
								request.post(url, {
									data : data,
									handleAs : 'json',
									sync: true
								}).then(function(data){
									var result_ = data.result;
									if(result_==true){
										Tip.success({
											text : "场景群创建成功"
										});
										callback && callback({
								    		data : data
										});
									}else{
										Tip.fail({
											text : data.errorMsg
										});
									}
								},function(data){
									Tip.fail({
										text : "调用createScenegroup接口失败"
									});
									return;
								}
								)
							});
						}else{
							//alert(JSON.stringify(data_res));
							Tip.fail({
								text : data_res.errorMsg
							});
							return;
						}
					}else{
						alert(JSON.stringify(data_res));
						return;
					}
				},function(data){
					alert(JSON.stringify(data_res));
					Tip.fail({
						text : "调用thirdDingScenegroupMapp接口失败"
					});
					return;
				});
			},

			// 场景群加人
			addScenegroupMember : function(options, callback){
				var baseUrl = dojoConfig.baseUrl;
				var device_this = this;
				var url = baseUrl + "third/ding/scenegroup/third_ding_scenegroup_mapp/thirdDingScenegroupMapp.do?method=getScenegroupInfo";
				var data = { "fdId" : options.fdId, "modelName" : options.modelName, "fdKey" : options.fdKey, "personIds" : options.personIds };
				request.post(url, {
					data : data,
					handleAs : 'json',
					sync: true
				}).then(function(data_res){
					if(data_res != null){
						var result = data_res.result;
						if(result==true){
							var data = data_res.data;
							options.appId = data.appId;
							options.corpId = data.corpId;
							options.pickedUsers = data.pickedUsers;
							var device_this2 = device_this;
							device_this.complexPicker(options, function(users){
								users = users.users;
								var usersStr = "";
								$.each(users, function (n, value) {
						               usersStr += value.emplId+";";
						           });
								var baseUrl = dojoConfig.baseUrl;
								var url = baseUrl + "third/ding/scenegroup/third_ding_scenegroup_mapp/thirdDingScenegroupMapp.do?method=addScenegroupMember";
								var data = {"fdId" : options.fdId, "modelName" : options.modelName, "fdKey" : options.fdKey, "users" : usersStr };
								request.post(url, {
									data : data,
									handleAs : 'json',
									sync: true
								}).then(function(data){
									var result_ = data.result;
									if(result_==true){
										Tip.success({
											text : "场景群加人成功"
										});
										callback && callback({
								    		data : data
										});
									}else{
										Tip.fail({
											text : data.errorMsg
										});
									}
								},function(data){
									Tip.fail({
										text : "调用addScenegroupMember接口失败"
									});
									return;
								}
								)
							});
						}else{
							//alert(JSON.stringify(data_res));
							Tip.fail({
								text : data_res.errorMsg
							});
							return;
						}
					}else{
						alert(JSON.stringify(data_res));
						return;
					}
				},function(data){
					alert(JSON.stringify(data_res));
					Tip.fail({
						text : "调用thirdDingScenegroupMapp接口失败"
					});
					return;
				});
			},

			// 场景群删人
			delScenegroupMember : function(options, callback){
				var baseUrl = dojoConfig.baseUrl;
				var device_this = this;
				var url = baseUrl + "third/ding/scenegroup/third_ding_scenegroup_mapp/thirdDingScenegroupMapp.do?method=getScenegroupInfo";
				var data = { "fdId" : options.fdId, "modelName" : options.modelName, "fdKey" : options.fdKey, "personIds" : options.personIds };
				request.post(url, {
					data : data,
					handleAs : 'json',
					sync: true
				}).then(function(data_res){
					if(data_res != null){
						var result = data_res.result;
						if(result==true){
							var data = data_res.data;
							options.appId = data.appId;
							options.corpId = data.corpId;
							options.pickedUsers = data.pickedUsers;
							var device_this2 = device_this;
							device_this.complexPicker(options, function(users){
								users = users.users;
								var usersStr = "";
								$.each(users, function (n, value) {
						               usersStr += value.emplId+";";
						           });
								var baseUrl = dojoConfig.baseUrl;
								var url = baseUrl + "third/ding/scenegroup/third_ding_scenegroup_mapp/thirdDingScenegroupMapp.do?method=delScenegroupMember";
								var data = {"fdId" : options.fdId, "modelName" : options.modelName, "fdKey" : options.fdKey, "users" : usersStr };
								request.post(url, {
									data : data,
									handleAs : 'json',
									sync: true
								}).then(function(data){
									var result_ = data.result;
									if(result_==true){
										Tip.success({
											text : "场景群删人成功"
										});
										callback && callback({
								    		data : data
										});
									}else{
										Tip.fail({
											text : data.errorMsg
										});
									}
								},function(data){
									Tip.fail({
										text : "调用delScenegroupMember接口失败"
									});
									return;
								}
								)
							});
						}else{
							//alert(JSON.stringify(data_res));
							Tip.fail({
								text : data_res.errorMsg
							});
							return;
						}
					}else{
						alert(JSON.stringify(data_res));
						return;
					}
				},function(data){
					alert(JSON.stringify(data_res));
					Tip.fail({
						text : "调用thirdDingScenegroupMapp接口失败"
					});
					return;
				});
			},

			//发起群聊
			createGroup : function(options, callback){
				this.ready(function(){
					dd.biz.contact.createGroup({
					    corpId: options.corpId, //企业id，可选，配置该参数即在指定企业创建群聊天
					    users: options.emplIds, //默认选中的用户工号列表，可选；使用此参数必须指定corpId
					    onSuccess: function(result) {
					    	callback && callback({
					    		groupID : result.id
							});
					    },
					    onFail: function(err) {
						   Tip.fail({
								text : err.errorMessage
							});
					    }
					});
				});
			},

			//打开钉钉群
			openGroup : function(options){
				this.ready(function(){
					dd.biz.chat.toConversation({
					    corpId: options.corpId, //企业id
					    chatId: options.chatId,//会话Id
					    onSuccess : function() {},
					    onFail : function() {}
					})
				});
			},

			//根据corpid选择会话
			chooseConversationByCorpId : function(corpId, callback){
				this.ready(function(){
					dd.biz.chat.chooseConversationByCorpId({
					    corpId: corpId, //企业id
					    onSuccess : function() {
					        /*{
					            chatId: 'xxxx',
					            title:'xxx'
					        }*/
					    	callback && callback({
					    		chatId : result.chatId
							});
					},
					    onFail : function() {}
					})
				});
			},

			//开始录音
			startRecord : function(){
				this.ready(function(){
					dd.device.audio.startRecord({
					    onSuccess : function () {
					    },
					    onFail : function (err) {
					    	Tip.fail({
								text : err.errorMessage
							});
					    }
					});
				});
			},

			//结束录音
			stopRecord : function(){
			/*	var attSetting = context.options;
				if(!window.AttachmentList)
					window.AttachmentList = {};
				var attachmentObj = window.AttachmentList[attSetting.fdKey];
				if(!attachmentObj){
					attachmentObj = new Attachment(attSetting);
					window.AttachmentList[attSetting.fdKey] = attachmentObj;
				}*/
				this.ready(function(){
					dd.device.audio.stopRecord({
					    onSuccess : function(res){
					        //res.mediaId; // 返回音频的MediaID，可用于本地播放和音频下载
					        //res.duration; // 返回音频的时长，单位：秒
					    	download(res.mediaId);
					        //attachmentObj.startUploadFile(file);
					    },
					    onFail : function (err) {
					    	Tip.fail({
								text : err.errorMessage
							});
					    }
					});
				});
			},

			//下载录音
			audioDownload : function(mediaId){
				this.ready(function(){
					dd.device.audio.download({
					    mediaId : mediaId,
					    onSuccess : function(res) {
					        res.localAudioId;
					    },
					    onFail : function (err) {
					    	Tip.fail({
								text : err.errorMessage
							});
					    }
					});
				});
			},

			//播放录音
			play : function(localAudioId){
				this.ready(function(){
					dd.device.audio.play({
					    localAudioId : localAudioId,
					    onSuccess : function () {

					    },
					    onFail : function (err) {
					    	Tip.fail({
								text : err.errorMessage
							});
					    }
					});
				});
			},

			//创建Ding
			createDing : function(options){
				this.ready(function(){
					dd.biz.ding.create(options);
				});
			},


			//拨打钉钉电话
			call : function(users, corpId){
				this.ready(function(){
					dd.biz.telephone.call({
					    users: users, //用户列表，工号
					    corpId: corpId, //企业id
					    onSuccess : function() {},
					    onFail : function() {}
					})
				});
			},

			getCurrentCoord : function(callback,error,options){
				this.ready(function(){
					dd.device.geolocation.get({
					    targetAccuracy : 200,
					    coordinate : 1,
					    withReGeocode : false,
					    useCache: false,
					    onSuccess : function(res) {
					    	var rs = { lat:res.latitude, lng:res.longitude,coordType:5 };
					    	callback && callback(rs);
					    },
					    onFail : function(err) {
					    	if(window.console)
								window.console.log(err);
							Tip.tip({
				    			icon : 'mui mui-warn',
								text : '钉钉地理定位失败'
							});
					    	error && error(err);
					    }
					});
				}, function(){
					if(window.console)
						window.console.log('调用钉钉坐标定位接口授权失败');
					this['$super$getCurrentCoord'] && this['$super$getCurrentCoord'].call(this,callback,error);
				});
			},

			getCurrentPosition: function(callback,error,options){
				options = options || {};
				this.ready(function(){
					this.getCurrentCoord(function(res){
						map.getMapLocation(lang.mixin(res,options),callback,error);
					},error,options);
				},function(){
					if(window.console)
						window.console.log('调用钉钉定位接口失败');
					this['$super$getCurrentPosition'] && this['$super$getCurrentPosition'].call(this,callback,error);
				});
			},

			editOffice: function (options,callback){
				var uploadOffice = this.uploadOffice;
				try{
					dd._invoke(
						"exclusive.sdk.invoke",
						{
							bundle_id:'landray-officesdk',
							api:'office.edit',
							request_params: {
								'downloadUri' : options.downloadUri,
								'fileId' : options.fileId,
								'fileName' : options.fileName,
								'wpsSerNum' : options.wpsSerNum
							},
							onSuccess:function(event){
								//alert(JSON.stringify(event));
								var uploadOptions = {};
								uploadOptions.uploadUri = options.uploadUri;
								uploadOptions.fileId = options.fileId;
								uploadOffice(uploadOptions,callback);
							},
							onFail:function(failErr){
								console.log(failErr);
								callback && callback.call(false,failErr);
							}
						}
					).then(function(){
						console.log('done');
					});
				} catch(err) {
					//alert("editOffice调用失败,"+JSON.stringify(err));
					console.log("editOffice调用失败,"+JSON.stringify(err));
					callback && callback.call(false,err);
				}
			},

			uploadOffice: function (options,callback){
				try {
					dd._invoke(
						"exclusive.sdk.invoke",
						{
							bundle_id:'landray-officesdk',
							api:'office.upload',
							request_params: {
								'uploadUri' : options.uploadUri,
								'uploadMethod': "post",
								'fileId' : options.fileId,
								'uploadConfig' : {
									'fileStreamKey' :'file',
									'fileSizeKey' : 'fileSize'
								}
							},
							onSuccess:function(event){
								//alert(JSON.stringify(event));
								callback && callback.call(true,event);
							},
							onFail:function(failErr){
								console.log(JSON.stringify(failErr));
								//alert(failErr);
								callback && callback.call(false,failErr);
							}
						}
					).then(function(){
						console.log('done');
					});
				} catch(err){

					console.log("uploadOffice调用失败,"+JSON.stringify(err));
					callback && callback.call(false,err);
				}
			},

			viewOffice: function (options,callback){
				try {
					dd._invoke(
						"exclusive.sdk.invoke",
						{
							bundle_id:'landray-officesdk',
							api:'office.view',
							request_params: {
								'downloadUri' : options.downloadUri,
								'fileId' : options.fileId,
								'fileName' : options.fileName
							},
							onSuccess:function(event){
								alert(JSON.stringify(event));
							},
							onFail:function(failErr){
								console.log(JSON.stringify(failErr));
								//alert(failErr);
							}
						}
					).then(function() {
						console.log('done');
					});
				} catch(err) {
					console.log("viewOffice调用失败,"+JSON.stringify(err));
					callback && callback.call(false,err);
				}

			}
		};

		/**
		 * 签名校验验证
		 */
		(function(){
			var deviceType = device.getClientType();
			if (deviceType != 11) return;
			if (!dd) return;
			var url = dojoConfig.baseUrl;

			var queryUrl = location.href;

			var isAndriod = function() {
				var ua = navigator.userAgent.toLowerCase();
				if (ua.indexOf('android') > -1) {
					return true;
				}
				return false;
			};

			if(isAndriod()&&window.oldLocaHrefUrl) {
				console.log("取window.oldLocaHrefUrl的值：",window.oldLocaHrefUrl);
				queryUrl = window.oldLocaHrefUrl;
			}
			//alert("window.dingHref:"+window.dingHref);
			option = { data : { url : queryUrl },  handleAs : 'json' };

			console.log("queryUrl:"+queryUrl);

			//--------获取当前使用的集成组件--------------
			var getUrl = dojoConfig.baseUrl + 'sys/mobile/adapter.do?method=getDingPrefix',
			getOption = { data : { url : location.href },  handleAs : 'json'};
			request.post(getUrl ,getOption).response.then(function(rtn){
				var info = rtn.data;
				url = url + info.url + '/jsapi.do?method=jsapiSignature';
				request.post(url ,option).response.then(function(rtn){
					var signInfo = rtn.data;
					if(signInfo){
						dd.config({
							appId: signInfo.appId,
							agentId:signInfo.appId,
							corpId: signInfo.corpId,
						    timeStamp: signInfo.timeStamp,
						    nonceStr: signInfo.nonceStr,
						    signature: signInfo.signature,
						    jsApiList: ['biz.chat.openSingleChat','biz.util.open','biz.util.openLink','biz.map.locate','biz.map.view','device.connection.getNetworkType','device.base.getInterface','device.base.getUUID','biz.contact.complexPicker','biz.contact.createGroup','biz.chat.toConversation','biz.chat.chooseConversationByCorpId','device.audio.startRecord','device.audio.stopRecord','device.audio.download','device.audio.play','biz.ding.create','biz.telephone.call','device.geolocation.get','biz.chat.pickConversation','biz.navigation.quit','biz.navigation.setTitle','biz.cspace.saveFile','biz.util.uploadAttachment','biz.util.downloadFile']
						});
						dd.ready(function(){
							___ready___ = true;
							topic.publish('/third/ding/ready');
						});
						dd.error(function(error){
							console.log(error);
							var error_message = JSON.stringify(error.errorMessage);
							var start = error_message.indexOf('url:[')
							var end = error_message.indexOf('],ticketList:')
							var error_url = error_message.substring(start+5,end);
							console.log("error_url:"+error_url);
							console.log('location.href:' + location.href);

							if(error_url != ""){
								option = { data : { url : error_url},  handleAs : 'json' };
								request.post(url ,option).response.then(function(rtn){
									var signInfo = rtn.data;
									if(signInfo){
										dd.config({
											appId: signInfo.appId,
											agentId:signInfo.appId,
											corpId: signInfo.corpId,
											timeStamp: signInfo.timeStamp,
											nonceStr: signInfo.nonceStr,
											signature: signInfo.signature,
											jsApiList: ['biz.chat.openSingleChat','biz.util.open','biz.util.openLink','biz.map.locate','biz.map.view','device.connection.getNetworkType','device.base.getInterface','device.base.getUUID','biz.contact.complexPicker','biz.contact.createGroup','biz.chat.toConversation','biz.chat.chooseConversationByCorpId','device.audio.startRecord','device.audio.stopRecord','device.audio.download','device.audio.play','biz.ding.create','biz.telephone.call','device.geolocation.get','biz.chat.pickConversation','biz.navigation.quit','biz.navigation.setTitle','biz.cspace.saveFile','biz.util.uploadAttachment','biz.util.downloadFile']
										});
										dd.ready(function(){
											___ready___ = true;
											topic.publish('/third/ding/ready');
											console.log('二次尝试鉴权ready成功');
										});
										dd.error(function(error){
											___readyfail___ = true;
											topic.publish('/third/ding/readyfail');
											console.log("error",error);
											console.log("signInfo",signInfo);
										});
									}else{
										___readyfail___ = true;
										topic.publish('/third/ding/readyfail');
										console.log('signInfo empty(ding)');
									}
								},function(){
									___readyfail___ = true;
									topic.publish('/third/ding/readyfail');
									console.log(JSON.stringify(arguments));
								});

							}else{
								___readyfail___ = true;
								topic.publish('/third/ding/readyfail');
							}
						});
					}else{
						dd.ready(function(){
							console.log('dd.ready')
						});
						___readyfail___ = true;
						topic.publish('/third/ding/readyfail');
						console.log('signInfo empty(ding)');
					}
				},function(){
					___readyfail___ = true;
					topic.publish('/third/ding/readyfail');
					console.log(JSON.stringify(arguments));
				});
			},function(e){
				console.log("请求失败！"+e);
			});
			//后端获取签名信息
		})();

		return adapter;
	});
