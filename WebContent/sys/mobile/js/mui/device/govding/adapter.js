/**
 * 用于政务钉钉客户端对应功能接口调用
 */
define(["https://g.alicdn.com/gdt/jsapi/1.8.2/index.js",
		"dojo/_base/lang","mui/device/device","dojo/request","mui/util","mui/coordtransform", 
		"mui/i18n/i18n!sys-attachment","mui/dialog/Tip","dojo/Deferred","dojo/topic","mui/map"], 
		function(dd,lang,device,request, util, coordtransform, Msg ,Tip ,Deferred,topic,map) {
		dd = dd['default'];
		var ___ready___ = false,
			___readyfail___ = false;
	
		var adapter = {
			
			__readyCallbacks : [],
			
			__readyfailCallbacks : [],
			
			__hasListener__ : false,
			
			/**
			 * 内部ready,需要授权的功能都要先调用此ready,回调中this已`修正`
			 * @param callback
			 * 		政务钉钉授权成功回调函数
			 * @param failcallback
			 * 		政务钉钉授权失败回调函数，该回调函数尝试给开发者一个机会去调用父类(web端)的基座能力,父类基座能力可以采用$super$XXX的方式调用
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
					topic.subscribe('/third/govding/ready',lang.hitch(this,function(){
						var cb = null;
						while(cb = this.__readyCallbacks.shift()){
							cb && cb.call(this);
						}
					}));
					topic.subscribe('/third/govding/readyfail',lang.hitch(this,function(){
						var failcb = null;
						while(failcb = this.__readyfailCallbacks.shift()){
							failcb && failcb.call(this);
						}
					}));
				}
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
						//history.back();
						window.location.href = document.referrer;
					}
				}else{
					this.closeWindow();
				}
				return {};
			},
			
			/**
			 * 关闭当前窗口，表现为回到政务钉钉工作台
			 */
			closeWindow : function(){
				this.ready(function(){
					dd.closePage().then(function(res){
						console.log(res);
					},function(err){
						console.log(err);	
					})
				},function(){
					dd.closePage().then(function(res){
						console.log(res);
					},function(err){
						console.log(err);
					})
				});
				return {};
			},
			
			/**
			 * 设置标题；
			 */
			setTitle: function(title){
				if (!title) {
					title = "";
				}
				title = title.trim();
				this.ready(function(){
					dd.setTitle({
					    title: title
					}).then(function(res){
						console.log(res);
					},function(err){
						console.log(err);
					})
				});
				// 这个也得手动设置下，否则调用setTitle后使用document.title获取标题错误
				document.title = title;
			},
			
			imagePreview3 : function(options){
				this['$super$imagePreview'] && this['$super$imagePreview'].call(this,options);
				return {};
			},
			
			/**
			 * 调用政务钉钉图片预览接口
			 */
			imagePreview : function(options){
				this.ready(function(){
					if(options.curSrc.indexOf("blob:")==0){
						this['$super$imagePreview'] && this['$super$imagePreview'].call(this,options);
					}else{
						dd.previewImage({
						  current: options.curSrc, // 当前显示图片的http链接
						  urls: options.srcList// 需要预览的图片http链接列表
						}).then(function(res){
							console.log(res);
						},function(err){
							console.log(err);
						})
					}
				},function(){
					this['$super$imagePreview'] && this['$super$imagePreview'].call(this,options);
				});
				return {};
			},
			
			/**
			 * 打开页面
			 */
			open : function(url, target, context){
				if('_blank' == target){
					this.ready(function(){
						try{
							dd.openLink({
							    url: url
							}).then(function(res){
								console.log(res);
							},function(err){
								window.open(url, '_self');
							})
							return;
						}catch(e){
							window.open(url, '_self');
							return;
						}
					});
				}else{
					window.open(url, target);
					return;
				}
			},
			
			/**
			 * 附件下载
			 */
			download : function(options) {
				Tip.warn({
					text : '根据国家政务安全规定，暂不支持下载文件到移动端'
				})
				return;
			},
			
			/**
			 * 扫码
			 */
			scanQRCode : function(callback){
				this.ready(function(){
					dd.scan({
					    type: "qrCode",
					}).then(function(data){
						callback && callback({
				    		text : data.text
				    	});
					},function(err){
						console.log(err);
					})
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
					dd.showOnMap({
					    latitude: tCoord[0], // 纬度
					    longitude: tCoord[1], // 经度
					    title: options.value // 地址/POI名称
					}).then(function(res){
						console.log(res);
					},function(err){
						console.log(err);
					})
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
					dd.locateOnMap({
					    latitude: tCoord[0], // 纬度，非必须
					    longitude: tCoord[1], // 经度，非必须
					}).then(function(result){
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
					},function(err){
						console.log(err);
					})
				},function(){
					this['$super$openEditMap'] && this['$super$openEditMap'].call(this,options, callback, failcallback);
				});
			},
			
			/**
			 * 获取网络类型('wifi' | '2g' | '3g' | '4g' | '5g' | 'unknown' | 'none', none表示离线)
			 */
			getNetworkType : function(callback){
				this.ready(function(){
					dd.getNetworkType({}).then(function(data){
						callback && callback({
							networkType : data.result
						});
					},function(err){
						callback && callback();
					})
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
					dd.getHotspotInfo().then(function(data){
						callback && callback({
							ssid  : data.ssid,
							macIp : self._fixMacIp(data.macIp)
						});
					},function(err){
						Tip.fail({
							text : '获取WIFI信息失败:'+err
						});
						callback && callback();
					})
				},function(){
					Tip.fail({
						text : '调用政务钉钉wifi接口授权失败'
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
				    dd.getUUID().then(function(data){
				    	callback && callback({
							deviceId : data.uuid
						});
					},function(err){
						callback && callback();
					})
				});
			},
			
			//创建钉钉群聊  第一步 调用钉钉 选择选择部门和人员jsapi  第二步发起群聊
			complexPicker : function(options, callback){
				this.ready(function(){
				   dd.chooseContactWithComplexPicker({
				    	title:options.title,            //标题	
				    	multiple:true,            //是否多选
				    	maxUsers:1000,            //最大可选人数
				    	pickedUsers:options.pickedUsers,            //已选用户
				    	disabledUsers:[],            //不可选用户
				    	requiredUsers:[],            //必选用户（不可取消选中状态）
				    	responseUserOnly:true,        //返回人，或者返回人和部门 true表示返回人，false返回人和部门
				    	limitTips:"超出了",          //超过限定人数返回提示
				    	pickedDepartments:[],          //已选部门
				    	disabledDepartments:[],        //不可选部门
				    	requiredDepartments:[],        //必选部门（不可取消选中状态）
				    }).then(function(result){
					   callback && callback({
				    		selectedCount:result.selectedCount,
				    		users : result.users,
				    		departments:result.departments
						});
					},function(err){
						Tip.fail({
							text : err.errorMessage
						});
					})
				},function(){
					Tip.tip({
		    			icon : 'mui mui-warn',
						text : '调钉钉jsapi:dd.biz.contact.complexPicker失败'
					});
				});
			},
			
			//发起群聊
			createGroup : function(options, callback){
				this.ready(function(){
					dd.createChatGroup({
					    corpId: options.corpId,//企业id，可选，配置该参数即在指定企业创建群聊天
					    users: options.emplIds, //默认选中的用户工号列表，可选；使用此参数必须指定corpId
					}).then(function(result){
						callback && callback({
				    		groupID : result.id
						});
					},function(err){
						Tip.fail({
							text : err.errorMessage
						});
					})
				});
			},
			
			//从会话列表中选择会话
			pickChat : function(corpId, callback){
				this.ready(function(){
					dd.pickChat({
					    corpId: corpId
					}).then(function(result){
						callback && callback({
				    		chatId : result.cid
						});
					},function(err){
						Tip.fail({
							text : err.errorMessage
						});
					})
				});
			},
			
			//开始录音
			startRecord : function(){
				this.ready(function(){
					dd.startRecordAudio().then(function(res){
						console.log(res);
					},function(err){
						Tip.fail({
							text : err.errorMessage
						});
					})
				});
			},
			
			//结束录音
			stopRecord : function(){
				this.ready(function(){
					dd.stopRecordAudio().then(function(res){
						//res.mediaId; // 返回音频的MediaID，可用于本地播放和音频下载
				        //res.duration; // 返回音频的时长，单位：秒
						audioDownload(res.mediaId);
					},function(err){
						Tip.fail({
							text : err.errorMessage
						});
					})
				});
			},
			
			//下载录音
			audioDownload : function(mediaId){
				this.ready(function(){
					dd.downloadAudio({
						mediaId: mediaId
					}).then(function(res){
						console.log(res.localAudioId)
					},function(err){
						Tip.fail({
							text : err.errorMessage
						});
					})
				});
			},
			
			//播放录音
			play : function(localAudioId){
				this.ready(function(){
					dd.playAudio({
					    localAudioId : localAudioId,
					}).then(function(res){
						console.log(res);
					},function(err){
						Tip.fail({
							text : err.errorMessage
						});
					})
				});
			},
			
			//创建Ding
			createDing : function(options){
				this.ready(function(){
					dd.createDing(options);
				});
			},
			
			//创建音视频会议
			createVideoMeeting : function(users, title){
				this.ready(function(){
					dd.createVideoMeeting({
					    title: title, //会议名称
					    calleeStaffIds: users//人员列表
					}).then(function(res){
						console.log(res)
					},function(err){
						console.log(err)
					})
				});
			},
			
			getCurrentCoord : function(callback,error,options){
				this.ready(function(){
					dd.getGeolocation({
					    targetAccuracy : 200,
					    coordinate : 1,
					    withReGeocode : false,
					    useCache:false, //默认是true，如果需要频繁获取地理位置，请设置false
					}).then(function(res){
						var rs = { lat:res.latitude, lng:res.longitude,coordType:5 };
				    	callback && callback(rs);
					},function(err){
						if(window.console)
							window.console.log(err);
						Tip.tip({
			    			icon : 'mui mui-warn',
							text : '政务钉钉地理定位失败'
						});
				    	error && error(err);
					})
				}, function(){
					Tip.tip({
		    			icon : 'mui mui-warn',
						text : '调用政务钉钉坐标定位接口授权失败'
					});
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
					Tip.tip({
		    			icon : 'mui mui-warn',
						text : '调用政务钉钉定位接口失败'
					});
					this['$super$getCurrentPosition'] && this['$super$getCurrentPosition'].call(this,callback,error);
				});
			},
			
			
		};
		
		/**
		 * 签名校验验证
		 */
		(function(){
			var deviceType = device.getClientType();
			if (deviceType != 14) return;
			if (!dd) return;
			var url = dojoConfig.baseUrl,
			option = { data : { url : location.href },  handleAs : 'json' };
			//--------获取当前使用的集成组件--------------
			var getUrl = dojoConfig.baseUrl + 'sys/mobile/adapter.do?method=getDingPrefix',
			getOption = { data : { url : location.href },  handleAs : 'json'};
			request.post(getUrl ,getOption).response.then(function(rtn){
				var info = rtn.data;
				url = url + info.url + '/jsapi.do?method=jsapiSignature';
				request.post(url ,option).response.then(function(rtn){
					var signInfo = rtn.data;
					if(signInfo){
						dd.ready(function(){
							___ready___ = true;
							topic.publish('/third/govding/ready');
							dd.authConfig({
							    ticket: signInfo.ticket,
							    jsApiList: ["showOnMap","locateOnMap","getUUID","chooseContactWithComplexPicker","createChatGroup","createDing","getGeolocation"]
							}).then(function(res){
								console.log(res)
							},function(err){
								console.log(err)
							})
						});
					}else{
						___readyfail___ = true;
						topic.publish('/third/govding/readyfail');
						console.log('signInfo empty(ding)');
					}
				},function(){
					___readyfail___ = true;
					topic.publish('/third/govding/readyfail');
					console.log(JSON.stringify(arguments));
				});
			},function(e){
				console.log("请求失败！"+e);
			});
			//后端获取签名信息
		})();
		
		return adapter;
	});
