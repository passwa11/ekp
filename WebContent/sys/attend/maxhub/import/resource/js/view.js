require(['dojo/dom', 'dojo/dom-construct', 'dojo/dom-class', 'dojo/dom-style', 'dojo/_base/array', 'dojo/html', 'dojo/on', 'dojo/promise/all',
         'dojo/query', 'dojo/topic', 'dijit/registry', 'mhui/device/jssdk', 'mhui/dialog/Dialog', 'dojo/request',
         'dojo/date/locale', 'dojo/date', 'mui/util', 'mui/qrcode/QRcode', 'dojo/ready'], 

	function(dom, domCtr, domClass, domStyle, array, html, on, promiseAll, query, topic, registry, jssdk, Dialog, request, 
			locale, date, util, QRcode, ready) {
	
	
		window.genQRCode = function(node, url, timestamp){
			
			url = url + '&t=' + new Date().getTime();
			
			domCtr.empty(node);
			
			var obj = QRcode.make({
				url : url,
				width: 429,
				height: 429
			});
			
			domCtr.place(obj.domNode, node); 
		}
		
		
		// 初始化逻辑
		function startUpdateSignIn(appId, cateId) {

			/***************************************************
			 * 主要签到逻辑
			 ***************************************************/
			
			var signInTimer = null;
			
			// 获取签到信息
			var _signInInfoUrl = dojoConfig.baseUrl + 'sys/attend/sys_attend_category/sysAttendCategory.do?method=viewdata&appId=!{appId}';
			var signInInfoUrl = util.urlResolver(_signInInfoUrl, {
				appId: appId
			});
			
			function getSignInInfo() {
				return request(signInInfoUrl, {
					method: 'get',
					handleAs: 'json'
				}).then(function(res){
					res = res || [];
					if(res.length < 1) {
						return;
					}
					
					var data = res[res.length - 1];
					
					return data;

					
				}, function(err) {
					console.error(err);
				});
			}

			// 获取签到状态
			var _signInStateUrl = dojoConfig.baseUrl + 'sys/attend/sys_attend_category/sysAttendCategory.do?method=stat&appId=!{appId}';
			var signInStateUrl = util.urlResolver(_signInStateUrl, {
				appId: appId
			});
			
			function getSignInState() {
				return request(signInStateUrl, {
					method: 'get',
					handleAs: 'json'
				}).then(function(res){
					res = res || [];
					if(res.length < 1) {
						return;
					}
					
					var data = res[res.length - 1];
					
					return data;
					
				}, function(err) {
					console.error(err);
				});
			}
			
			// 获取签到人员
			
			var sysAttendPersonList = registry.byId('sysAttendPersonList');
			sysAttendPersonList.startUpdateAttendPerson(appId, cateId);
			
			var sysUnAttendPersonList = registry.byId('sysUnAttendPersonList');
			sysUnAttendPersonList.startUpdateAttendPerson(appId, cateId);
			
			// 更新签到详情逻辑
			var now = new Date();
			var fdStartTime = null;
			var fdEndTime = null;
			
			var qrcodeTime = 0;
			
			var sysAttendQRCodeMainNode = dom.byId('sysAttendQRCodeMain');
			var sysAttendQRCodeNode = dom.byId('sysAttendQRCode');
			var sysAttendQRCodeStatusNode = dom.byId('sysAttendQRCodeStatus');
			
			function initSignIn() {
				promiseAll([
		            getSignInInfo(),
		            getSignInState(),
	            ]).then(function(results) {
	            	
	            	if(results[0] && results[1]) {
	            		
		            	var info = results[0] || {};
		            	var state = results[1] || {};
		            	
		            	// 设置二维码状态
		            	var _now = new Date();
		            	
		            	if(!fdStartTime || !fdEndTime) {
		            		
		            		var localeOpt = {
	            				selector: 'date',
	            				datePattern: 'yyyy-MM-dd'
		            		};
		            		
		            		if((info.fdTimes || []).length == 1) {
		            			
		            			var d = locale.format(new Date(info.fdTimes[0]), localeOpt);
		            			
		            			fdStartTime = new Date(d + ' ' + info.fdStartTime);
		            			fdEndTime = new Date(d + ' ' + info.fdEndTime);
		            			
		            		} else if((info.fdTimes || []).length == 2) {
		            			var d1 = locale.format(new Date(info.fdTimes[0]), localeOpt);
		            			var d2 = locale.format(new Date(info.fdTimes[1]), localeOpt);
		            			
		            			fdStartTime = new Date(d1 + ' ' + info.fdStartTime);
		            			fdEndTime = new Date(d2 + ' ' + info.fdEndTime);	
		            		}
		            		
		            	}
		            	
		            	if(_now < fdStartTime) {
		            		if(domClass.contains(sysAttendQRCodeNode, 'active')) {
		            			domClass.remove(sysAttendQRCodeNode, 'active');
		            		}
		            		html.set(sysAttendQRCodeStatusNode, '未开始');
		            		
		            	} else if (_now >= fdStartTime && _now < fdEndTime) {
		            		if(!domClass.contains(sysAttendQRCodeNode, 'active')) {
		            			domClass.add(sysAttendQRCodeNode, 'active');
		            		}
		            	} else if (_now > fdEndTime) {
		            		if(domClass.contains(sysAttendQRCodeNode, 'active')) {
		            			domClass.remove(sysAttendQRCodeNode, 'active');
		            		}
		            		html.set(sysAttendQRCodeStatusNode, '已结束');
		            	}
		            	
						// 生成二维码
		            	var qrcodeMins = parseInt(util.getUrlParameter(info.fdQRCodeUrl, 'qrCodeTime') || 0) * 1000;
		            	var _nowTime = _now.getTime();
						if(_nowTime - qrcodeTime > qrcodeMins) {
							genQRCode(sysAttendQRCodeMainNode, info.fdQRCodeUrl || '');
							qrcodeTime = _nowTime;
						}
		            	
						// 设置签到总览
						
						html.set(dom.byId('sysAttendStartTime'), info.fdStartTime || '');
						html.set(dom.byId('sysAttendEndTime'), info.fdEndTime || '');
						html.set(dom.byId('sysAttendSignIn'), state.attendcount || 0);
						html.set(dom.byId('sysAttendUnSign'), state.unattendcount || 0);
						html.set(dom.byId('sysAttendTotal'), state.count || 0);

						if(info.fdTimes && info.fdTimes[0]) {
							
							var lateTime = date.add(new Date(info.fdTimes[0]), 'minute', 5);
							
							html.set(dom.byId('sysAttendLateTime'), locale.format(lateTime, {
								selector: 'time',
								timePattern: 'HH:mm'
							}) + '后为迟到');
						}
						
						// 若签到结束则取消刷新签到详情
						if(now > fdEndTime) {
							sysAttendPersonList.cancelUpdateAttendPerson();
							sysUnAttendPersonList.cancelUpdateAttendPerson();
							if(signInTimer) {
								clearInterval(signInTimer);
								signInTimer = null;
							}
						}
						
	            	}
					
	            });
				
			}
			
			initSignIn();
			signInTimer = setInterval(function() {
				initSignIn();
			}, 4000);
			
		}
		
		function readyFunc() {

			var appId = window.__fdModelId4sysAttend__;
			
			if(window.__cateId4sysAttend__) {
				startUpdateSignIn(appId, window.__cateId4sysAttend__);
			} else {
				
				var _sysAttendCategory = dojoConfig.baseUrl + 'sys/attend/sys_attend_category/sysAttendCategory.do?method=viewdata&appId=!{appId}';
				var sysAttendCategory = util.urlResolver(_sysAttendCategory, {
					appId: appId
				});
				
				var getSysAttendCategoryTimer = null;
				
				var getSysAttendCategory = window.getSysAttendCategory = function() {
					
					if(getSysAttendCategoryTimer) {
						clearTimeout(getSysAttendCategoryTimer);
						getSysAttendCategoryTimer = null;
					}
					
					request(sysAttendCategory, {
						method: 'get',
						handleAs: 'json'
					}).then(function(res){

						if((res || []).length > 0) {
							
							var contentView = dom.byId('sysAttendContentView');
							domClass.remove(contentView, 'mhui-hidden');
							
							var nodataView = dom.byId('sysAttendNoDataView');
							domClass.add(nodataView, 'mhui-hidden');
							
							startUpdateSignIn(appId, res[res.length - 1].fdId);
							
						} else {
							getSysAttendCategoryTimer = setTimeout(function(){
								getSysAttendCategory();
							}, 4000);
						}
						
						
					}, function(err) {
						console.error(err);
						getSysAttendCategoryTimer = setTimeout(function(){
							getSysAttendCategory();
						}, 4000);
					});
				}
				
				getSysAttendCategory();
				
			}
			
		}
		
		
		// 初始化
		ready(readyFunc);
		
	}
);