/**
 * 用于飞书客户端对应功能接口调用
 */
define(
		[
				"https://sf6-scmcdn-tos.pstatp.com/goofy/ee/lark/h5jssdk/js_sdk/h5-js-sdk-1.4.15.js",
				"dojo/_base/lang", "mui/device/device", "dojo/request",
				"mui/util", "mui/coordtransform",
				"mui/i18n/i18n!sys-attachment", "mui/dialog/Tip",
				"dojo/Deferred", "dojo/topic", "mui/map" ], function(h5sdk,
				lang, device, request, util, coordtransform, Msg, Tip,
				Deferred, topic, map) {

			var ___ready___ = false, ___readyfail___ = false;

			var adapter = {

				__readyCallbacks : [],

				__readyfailCallbacks : [],

				__hasListener__ : false,

				/**
				 * 内部ready,需要授权的功能都要先调用此ready,回调中this已`修正`
				 * 
				 * @param callback
				 *            飞书授权成功回调函数
				 * @param failcallback
				 *            飞书授权失败回调函数，该回调函数尝试给开发者一个机会去调用父类(web端)的基座能力,父类基座能力可以采用$super$XXX的方式调用
				 */
				ready : function(callback, failcallback) {
					if (___readyfail___) {
						failcallback && failcallback.call(this);
						return;
					}
					if (___ready___) {
						callback && callback.call(this);
						return;
					}
					this.__readyCallbacks.push(callback);
					this.__readyfailCallbacks.push(failcallback);
					if (!this.__hasListener__) {
						this.__hasListener__ = true;
						topic.subscribe('/third/feishu/ready', lang.hitch(this,
								function() {
									var cb = null;
									while (cb = this.__readyCallbacks.shift()) {
										cb && cb.call(this);
									}
								}));
						topic.subscribe('/third/feishu/readyfail', lang.hitch(
								this, function() {
									var failcb = null;
									while (failcb = this.__readyfailCallbacks
											.shift()) {
										failcb && failcb.call(this);
									}
								}));
					}
				},

				/**
				 * 关闭当前窗口，表现为回到飞书工作台
				 */
				closeWindow : function() {
					window.h5sdk.ready(function() {
						window.h5sdk.biz.navigation.close({
							onSuccess : function(result) {
								console.log(result);
							},
							onFail : function(e) {
								// 失败回调
								console.log(JSON.stringify(e));
							}
						});
					})
					return {};
				},

				/**
				 * 设置标题；服气，设置个标题都要调api
				 */
				setTitle : function(title) {
					if (!title) {
						title = "";
					}
					title = title.trim();
					window.h5sdk.biz.navigation.setTitle({
						title : title,
						onSuccess : function(result) {
						},
						onFail : function(err) {
						}
					});
					// 这个也得手动设置下，否则调用setTitle后使用document.title获取标题错误
					document.title = title;
				},

				/**
				 * 调用飞书图片预览接口
				 */
				imagePreview : function(options) {
					if (options.curSrc.indexOf("blob:") == 0) {
						this['$super$imagePreview']
								&& this['$super$imagePreview'].call(this,
										options);
					} else {
						window.h5sdk.biz.util.previewImage({
							current : options.curSrc, // 当前显示图片的http链接
							urls : options.srcList, // 需要预览的图片http链接列表
							onSuccess : function(result) {
								// alert("success");
							},
							onFail : function(err) {
								// alert(fail);
								this['$super$imagePreview']
										&& this['$super$imagePreview'].call(
												this, options);
							}
						});
					}
					return {};
				},

				/**
				 * 打开页面
				 */
				open2 : function(url, target, context) {
					if ('_blank' == target) {
						this.ready(function() {
							try {
								window.h5sdk.biz.util.openLink({
									url : url,
									title : 'ekp',
									newTab : false,
									onFail : function(err) {
										window.open(url, '_self');
									}
								});
								return;
							} catch (e) {
								window.open(url, '_self');
								return;
							}
						}, function() {
							window.open(url, '_self');
							return;
						});
					} else {
						window.open(url, target);
						return;
					}
				},

				getCurrentCoord2 : function(callback, error, options) {
					this.ready(function() {
						window.h5sdk.device.geolocation.get({
							useCache : false,
							onSuccess : function(res) {
								var rs = {
									lat : res.latitude,
									lng : res.longitude,
									coordType : 5
								};
								callback && callback(rs);
							},
							onFail : function(err) {
								if (window.console)
									window.console.log(err);
								Tip.tip({
									icon : 'mui mui-warn',
									text : '飞书地理定位失败'
								});
								error && error(err);
							}
						});
					}, function() {
						if (window.console)
							window.console.log('调用飞书坐标定位接口授权失败');
						this['$super$getCurrentCoord']
								&& this['$super$getCurrentCoord'].call(this,
										callback, error);
					});
				}

			};

			/**
			 * 签名校验验证
			 */
			(function() {
				var deviceType = device.getClientType();
				if (deviceType != 22)
					return;
				if (!h5sdk)
					return;
				var url = dojoConfig.baseUrl;
				option = {
					data : {
						url : location.href
					},
					handleAs : 'json'
				};
				url = url + 'third/feishu/jsapi.do?method=jsapiSignature';
				request.post(url, option).response.then(function(rtn) {
					var signInfo = rtn.data;
					if (signInfo) {
						window.h5sdk.config({
							appId : signInfo.appId,
							timeStamp : signInfo.timeStamp,
							nonceStr : signInfo.nonceStr,
							signature : signInfo.signature,
							jsApiList : [ 'tt.getClipboardData',
									'tt.getLocation', 'tt.chooseLocation',
									'tt.getLocation',
									'biz.navigation.setTitle',
									'biz.navigation.setLeft',
									'biz.navigation.setRight',
									'biz.navigation.setMenu',
									'biz.navigation.goBack',
									'biz.navigation.close',
									'biz.util.previewImage',
									'biz.util.openLink', 'biz.util.scan',
									'device.geolocation.get',
									'device.geolocation.start',
									'device.geolocation.stop',
									'device.notification.confirm',
									'device.notification.alert',
									'device.geolocation.get',
									'device.geolocation.start',
									'device.geolocation.stop' ],
							onSuccess : function(result) {
								___ready___ = true;
								topic.publish('/third/feishu/ready');
							},
							onFail : function(error) {
								___readyfail___ = true;
								topic.publish('/third/feishu/ready');
								console.log(JSON.stringify(signInfo));
								console.log('location.href:' + location.href);
								console.log('errCode:' + error['errorCode']);
								console.log('-------- errMsg:'
										+ error['message']);
							}
						});

					} else {
						___readyfail___ = true;
						topic.publish('/third/feishu/readyfail');
						console.log('signInfo empty(feishu)');
					}
				}, function() {
					___readyfail___ = true;
					topic.publish('/third/feishu/readyfail');
					console.log(JSON.stringify(arguments));
				});

			})();

			return adapter;
		});
