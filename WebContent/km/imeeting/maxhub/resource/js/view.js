require(['dojo/dom', 'dojo/dom-construct', 'dojo/dom-class', 'dojo/_base/array', 'dojo/html', 'dojo/on', 'dojo/dom-attr',
         'dojo/query', 'dojo/topic', 'dijit/registry', 'mhui/device/jssdk', 'mhui/dialog/Dialog', 
         'dojo/date/locale', 'mui/util', 'dojo/request', 'dojo/_base/lang', 'mui/dialog/Tip', 'mhui/api', 'dojo/ready'], 

	function(dom, domCtr, domClass, array, html, on, domAttr, query, topic, registry, jssdk, Dialog, locale, util, 
			req, lang, Tip, api, ready) {
	
		jssdk.setMeetingOptions({ meetingId : window.__fdMettingId__ });
	
		topic.subscribe('EVENT_NAV_CHANGE', function(payload) {
			var viewSecs = query('.mhuiViewSec');
			array.forEach(viewSecs, function(viewSec) {
				if(!domClass.contains(viewSec, 'mhui-hidden')) {
					domClass.add(viewSec, 'mhui-hidden');
				}
			});
			
			var viewNode = dom.byId(payload.viewId);
			if(viewNode) {
				domClass.remove(dom.byId(payload.viewId), 'mhui-hidden');
			}
			
		});
		
		// 发起签到
		window.launchSignIn = function() {
			
			if(new Date() > new Date(__fdMettingFinishDate__)) {
				Tip.fail({
					text: '会议已结束，禁止发起签到！'
				});
				return;
			}
			
			var signInUrl = util.urlResolver(dojoConfig.baseUrl + 
					'sys/attend/sys_attend_category/sysAttendCategory.do?method=add&fdModelName=!{fdModelName}&fdModelId=!{fdModelId}', {
				fdModelName: window.__fdModelName4sysAttend__,
				fdModelId: window.__fdModelId4sysAttend__
			});
			
			Dialog.show({
				title: '发起签到',
				iframe: signInUrl,
				width: '52.65rem',
				iframeHeight: '17.5rem',
				buttons: [
					{
						text: '取消',
						onClick: function(dialog) {
							dialog.hide();
						}
					},
		            {
		            	text: '确定',
		            	className: 'mhuiDialogPrimaryBtn',
		            	onClick: function(dialog) {
		            		
		            		var processTip = Tip.tip({				
	    						icon: "mui mui-loading mui-loading-b",
	    						time: -1, 
	    						cover: true,
	    						text: "发起签到中"
	    					});
		            		
		    				dialog.dialogContent.contentWindow.submitForm(function(res){
		    					dialog.hide();
		    					processTip.hide();
		    				});
		            	}
		            }
	          	]
			});
		}
		
		// 打开白板
		window.goBoard = function() {
			console.log('goBoard');
			jssdk.board();
		}
		
		window.goNotes = function(){
			console.log('goNotes');
			jssdk.notes();
		};
		
		// 返回
		window.goBack = function() {
			console.log('goBack');
			jssdk.goBack();
		}
		
		//同步文件到EKP服务器
		window.synFiles = function() {
			topic.publish('attachmentObj_tmpAttachment_selectFile',{
				complete : function(){
					//注册附件信息
					topic.publish('attachmentObject_tmpAttachment_submit', {
						success: function() {
							var attachmentObject = registry.byId('attachmentObject_tmpAttachment');
							var openBoardPanel = dom.byId('openBoardPanel');
							
							if(attachmentObject) {
								
								var count = attachmentObject.getCount() || 0;
								if(count < 1) {
									openBoardPanel && domClass.remove(openBoardPanel, 'mhui-hidden');
								} else {
									openBoardPanel && domClass.add(openBoardPanel, 'mhui-hidden');
								}
								
							}
							
						}
					});
					//刷新附件查看区域
					//topic.publish('attachmentObject_tmpAttachment_refresh');
				}
			});
		};
		
		// 结束会议
		window.finishMeeting = function() {

			var processTip = null;
			
			topic.publish('attachmentObj_tmpAttachment_selectFile',{
				close: function(opt) {
					if(opt && !opt.isCancel) {
						processTip = Tip.tip({				
							icon: "mui mui-loading mui-uploading",
							time: -1, 
							cover: true,
							text: "附件上传中"
						});console.log('show')
					}
				},
				complete : function(){
					topic.publish('attachmentObject_tmpAttachment_submit', {
						success: function() {
							
							if(processTip) {
								processTip.hide();
								processTip = null;
							}
							
							if(new Date() < new Date(__fdMettingFinishDate__)) {
								
								var tips = domCtr.create('div', {
									innerHTML: '是否确认结束会议？',
									style: 'text-align: center; padding: 6rem 12rem;'
								});
								
								Dialog.show({
									content: tips,
									title: '提示',
									showClose: false,
									buttons: [
										{
											text: '取消',
											onClick: function(dialog) {
												dialog.hide();
											}
										},
							            {
							            	text: '确定',
							            	className: 'mhuiDialogPrimaryBtn',
							            	onClick: function(dialog) {
							            		dialog.hide();
							            		
							            		processTip = Tip.tip({				
							            			icon: "mui mui-loading mui-loading-b",
							            			time: -1, 
							            			cover: true,
							            			text: "结束会议中"
							            		});
							            		
							            		var earlyEndTime = locale.format(new Date, {
							            			datePattern: 'yyyy-MM-dd',
							            			timePattern: 'HH:mm'
							            		});
							            		
							            		api.finishMeeting(__fdMettingId__, earlyEndTime)
								            		.then(function(res) {
								            			processTip.hide();
								            			window.goBack();
								            		}, function(err) {
								            			processTip.hide();
								            			Tip.fail({				
								            				text: "会议结束失败！"
								            			});
								            		});
							            	}
						            	}
						          ]
								});
								
									
							} else {
								Tip.fail({				
									text: "会议已结束！"
								});
								
								var btnFinishMeeting = dom.byId('btnFinishMeeting');
								btnFinishMeeting && domClass.add(btnFinishMeeting, 'mhui-hidden');
							}
						},
						fail: function() {
							if(processTip) {
								processTip.hide();
								processTip = null;
							}
							
							Tip.fail({
								text: '附件上传失败！'
							});
						}
					});
					
				}
			});
			
		}
		
		// 邀请入会议
		window.inviteIMeeting = function() {

			Dialog.show({
				baseClass: 'mhuiInviteDialog',
				title: '邀请加入会议',
				iframe: dojoConfig.baseUrl + 'km/imeeting/maxhub/edit_invite.jsp?fdId='+__fdMettingId__,
				width: '81rem',
				iframeId:'inviteIframe',
				iframeHeight: '45rem',
				showClose: false,
				buttons: [
		            {
		            	text: '取消',
		            	onClick: function(dialog) {
		            		api.cleanAttendCache(__fdMettingId__)
		        				.then(lang.hitch(this, function(data) {
			        				if (true==data){
			        					dialog.hide();
			        				}
			        			}));
		            	}
		            }, {
		            	text: '确定',
		            	className: 'mhuiDialogPrimaryBtn',
		            	onClick: function(dialog) {
		            		
		            		var processTip = Tip.tip({				
		            			icon: "mui mui-loading mui-loading-b",
		            			time: -1, 
		            			cover: true,
		            			text: "邀请中"
		            		});
		            		
		            		dialog.dialogContent.contentWindow.submit(function(res){
		            			
		            			processTip.hide();
		            			
		            			if('success'==res){
		            				Tip.success({
		            					text: '邀请成功！'
		            				});
		            				
	    							dialog.hide();
	    						} else {
	    							Tip.fail({
	    								text: '邀请失败！'
	    							});
	    						}
		    				});
		            	}
		            }
	          	]
			});
		}
		
		function readyFunc(args) {
			
			var holdDate = new Date(__fdMettingHoldDate__);
			var finishDate = new Date(__fdMettingFinishDate__);
			
			// 初始化会议时间
			var imeetingDurationNode = dom.byId('imeetingDuration');

			var localeOpt = {};
			var splitFlag = true;
			if(finishDate.getFullYear() > holdDate.getFullYear()) {
				localeOpt.selector = 'datetime';
				localeOpt.datePattern = 'yyyy年MM月dd日';
				localeOpt.timePattern = 'HH:mm';
			} else {
				if(finishDate.getDate() > holdDate.getDate()) {
					localeOpt.selector = 'datetime';
					localeOpt.datePattern = 'MM月dd日';
					localeOpt.timePattern = 'HH:mm';					
				} else {
					splitFlag = false;
					localeOpt.selector = 'time';
					localeOpt.timePattern = 'HH:mm';	
				}
			}
			var durationLabel = locale.format(holdDate, localeOpt) + ' 至 ' +
				(splitFlag ? '<br/>' : '') +
				locale.format(finishDate, localeOpt);
			html.set(imeetingDurationNode, durationLabel);

			// 刷新参加人员列表
			var attendPersonList = registry.byId('attendPersonList');
			function loadAttendPersons() {
				if(attendPersonList) {
					attendPersonList.reload();
				}
			}
			loadAttendPersons();
			
			// 设置当前时间
			var currentTimeNode = dom.byId('currentTime');
			function setCurrentTime() {
				html.set(currentTimeNode, locale.format(new Date(), {
					selector: 'time',
					timePattern: 'HH:mm:ss'
				}));
			}
			setCurrentTime();
			
			// 计算当前会议是否召开或结束，动态设置按钮
			var btnFinishMeeting = dom.byId('btnFinishMeeting');
			var btnInviteMeetingLeft = dom.byId('btnInviteMeetingLeft');
			var btnInviteMeetingRight = dom.byId('btnInviteMeetingRight');
			var mhuiImeetingStatus = dom.byId('mhuiImeetingStatus');
			var mhuiImeetingStatusText = dom.byId('mhuiImeetingStatusText');
			
			function checkMeetingStatus() {
				
				var _now = new Date();
				if(_now < finishDate) {
					btnInviteMeetingLeft && domClass.remove(btnInviteMeetingLeft, 'mhui-hidden');
					btnInviteMeetingRight && domClass.remove(btnInviteMeetingRight, 'mhui-hidden');
					if(_now > holdDate) {
						btnFinishMeeting && domClass.remove(btnFinishMeeting, 'mhui-hidden');
					}
					
				} else {
					btnFinishMeeting && domClass.add(btnFinishMeeting, 'mhui-hidden');
					btnInviteMeetingLeft && domClass.add(btnInviteMeetingLeft, 'mhui-hidden');
					btnInviteMeetingRight && domClass.add(btnInviteMeetingRight, 'mhui-hidden');
				}
				
				// 会议状态
				if(_now < holdDate) {
					mhuiImeetingStatus && domClass.remove(mhuiImeetingStatus, 'mhui-hidden');
					mhuiImeetingStatusText && html.set(mhuiImeetingStatusText, '未召开');
					mhuiImeetingStatus && domAttr.set(mhuiImeetingStatus, 'data-status', 'unHold');
				} else if(_now > holdDate && _now < finishDate) {
					mhuiImeetingStatus && domClass.add(mhuiImeetingStatus, 'mhui-hidden');
				} else if(_now > finishDate) {
					mhuiImeetingStatus && domClass.remove(mhuiImeetingStatus, 'mhui-hidden');
					mhuiImeetingStatusText && html.set(mhuiImeetingStatusText, '已召开');
					mhuiImeetingStatus && domAttr.set(mhuiImeetingStatus, 'data-status', 'hold');
				}
				
			}
			checkMeetingStatus();

			// 循环执行任务
			setInterval(function() {
				checkMeetingStatus();
				loadAttendPersons();
			}, 4000);
			
			setInterval(function() {
				setCurrentTime();
			}, 1000);
			
		}
		
		// 初始化逻辑
		ready(function(){
			// readyFunc();
			jssdk.ready(readyFunc);
		});
		
	}
);