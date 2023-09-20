/**
 * @Deprecated
 * 第三方JS-SDK重构后此文件废弃,所有功能均已移回同层级的adapter.js
 * 
 * 用于微信客户端对应功能接口调用(该JS中的接口需要通过授权才能调用)
 */
define([ "https://res.wx.qq.com/open/js/jweixin-1.0.0.js", "dojo/request",
		"mui/util", "mui/i18n/i18n!sys-attachment",'mui/dialog/Tip','mui/device/device',"dojo/Deferred"], 
		function(wx, request, util, Msg ,Tip ,device,Deferred) {
	var adapter = {

		closeWindow : function() {
			wx.closeWindow();
			return {};
		},

		imagePreview : function(options) {
			wx.previewImage({
				current : options.curSrc, // 当前显示图片的http链接
				urls : options.srcList
			// 需要预览的图片http链接列表
			});
			return {};
		},

		startRecord : function(cb) {

			function failed(e) {
				// 只支持企业号，简单微信扫码直接提示错误信息
				if (this.failTipTimer)
					clearTimeout(this.failTipTimer);

				this.failTipTimer = setTimeout(function() {

					alert(e.errMsg);

				}, 100);
			}

			// 首次触发用户认证，不当做录音操作
			if (!localStorage.__allowRecord
					|| localStorage.__allowRecord !== 'true') {

				wx.startRecord({
					success : function() {

						localStorage.__allowRecord = 'true';
						wx.stopRecord();

					},

					fail : function(e) {
						
						failed(e);
						
					}

				});

				return;
			}

			this.timer = setTimeout(function() {
				wx.startRecord({

					fail : function(e) {
						
						failed(e);
						
					}

				});
				cb();

			}, 200)

		},

		stopRecord : function(sc, fc) {

			if (this.timer)
				clearTimeout(this.timer);

			setTimeout(function() {

				wx.stopRecord({

					success : function(res) {
						if (sc)
							sc(res);
					},

					fail : function(res) {
						if (fc)
							fc(res);
					}

				});

			}, 200);

		},

		stopRecordByTranslate : function(beforeTranslate, afterTranslate) {

			if (beforeTranslate)
				beforeTranslate();

			this.stopRecord(function(res) {

				var localId = res.localId;

				if (localId)
					wx.translateVoice({

						localId : localId,
						isShowProgressTips : 1,

						success : function(res) {

							if (afterTranslate)
								afterTranslate(res);

						}
					});
			})

		},
		
		scanQRCode : function(callback){
			wx.ready(function(){
				wx.scanQRCode({
				    desc: 'scanQRCode desc',
				    needResult : 1,
				    scanType: ["qrCode","barCode"],//一维码、二维码
				    success: function (res) {
				    	callback && callback({
				    		text : res.text || res.resultStr
				    	});
				    },
				    error: function(res){
				    	if(res.errMsg.indexOf('function_not_exist') > 0){
				    		Tip.tip({
				    			icon : 'mui mui-warn',
								text : '微信版本过低请升级'
							});
				    	}
				     }
				});
			});
			return true;
		},
		
		getCurrentCoord : function(callback,error){
			this._loadScript(function(){
				wx.getLocation({
					success:function(res){
				        var rs ={latitude:res.latitude,longitude:res.longitude};
				        callback && callback(rs);
					},
					cancel:function(){
						Tip.tip({
			    			icon : 'mui mui-warn',
							text : '只有授权才允许地理定位'
						});
					},
					fail: function(res){
						if(window.console)
							window.console.log(res.errMsg);
						Tip.tip({
			    			icon : 'mui mui-warn',
							text : '地理定位失败'
						});
				     }
				});
			});
		},
		
		getCurrentPosition: function(callback,error){
			var self = this;
			wx.ready(function(){
				self.getCurrentCoord(function(res){
				 var point = new BMap.Point(res.longitude,res.latitude);
				 var convertor = new BMap.Convertor();
			     var pointArr = [];
			     pointArr.push(point);
			     convertor.translate(pointArr, 1, 5, function(data){
			    	 if(data.status === 0) {
			    		 var geoc = new BMap.Geocoder();
			            	geoc.getLocation(data.points[0],function(rs){
			            		callback && callback(rs);
							});
			    	 }
			     });
			    
			},error);
			});
		},
		
		/** 企业微信的打开会话窗口返回后跳到工作台，体验极差，暂时不启用
		 * 
		openChat : function(options){
			var clientType = device.getClientType(),
			 	defer = new Deferred();
			options.userId = options.loginName;
			if(!options.userId && options.ekpId){
				var url = (clientType == 6 ? '/third/weixin/user' : '/third/weixin/work/user' );
				url = util.formatUrl(url + '.do?method=getUserId&fdId=' +options.ekpId);
				request(url,{handleAs : 'json'}).then(function(result){
					options.userId = result.userId;
					defer.resolve(options);
				});
			}else{
				defer.resolve(options);
			}
			defer.then(function(__options){
				wx.openEnterpriseChat({
				    userIds: __options.userId,    
				    groupName: ''
				});
			});
		}
		*/

	};

	return adapter;
});
