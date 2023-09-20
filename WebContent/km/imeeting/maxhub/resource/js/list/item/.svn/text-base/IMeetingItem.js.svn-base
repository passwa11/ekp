define([ 'dojo/_base/declare', 'dojo/_base/array', 'dojo/_base/lang' , 'dojo/topic', 'dojo/request', 'dojo/on',
         'dojo/dom', 'dojo/dom-construct', 'dojo/dom-style','dojo/dom-class', 'dojo/dom-attr', 'dojo/html', 
         'mhui/list/ItemBase', 'dojo/date/locale', 'dojo/date', 'mhui/message/MessageBox', 'mui/dialog/Tip'],
	function(declare, array, lang, topic, request, on, dom, domCtr, domStyle, domClass, domAttr, html,
			ItemBase, locale, date, MessageBox, Tip) {
		return declare('km.imeeting.maxhub.IMeetingItem',[ ItemBase ],{
			
//			{
//			  "fdId": "16339297dfbf3ceab47eaf44bf594baf",
//			  "title": "Test",
//			  "start": "2018-05-07 16:00",
//			  "end": "2018-05-07 16:30",
//			  "allDay": false,
//			  "status": "unHold",
//			  "statusText": "未召开",
//			  "fdHost": "管理员",
//			  "hostsrc": "/ekp/sys/person/image.jsp?personId=1183b0b84ee4f581bba001c47a78b2d9&size=null",
//			  "creatorId": "1183b0b84ee4f581bba001c47a78b2d9",
//			  "creator": "管理员",
//			  "fdPlaceId": "1630a4af87d91a07fa1973e47c291b7e",
//			  "fdPlaceName": "1A",
//			  "type": "meeting",
//			  "href": "/km/imeeting/km_imeeting_main/kmImeetingMain.do?method=view&fdId=16339297dfbf3ceab47eaf44bf594baf",
//			  "priority": 2
//			}
			
			countDown: 600, // 单位秒
			
			isEmpty: false,
			
			beforeInit: function() {
				this.containerNode = this.domNode = domCtr.create('div', {
					className: 'mhuiImeetingContainer'
				});
			},
			
			initialize: function(){
				this.inherited(arguments);
				this.doAction();
			},
			
			renderItem: function() {
				if(this.isEmpty) {
					this.renderEmptyTips();
				} else {
					this.renderIMeeting();
				}
			},
			
			renderIMeeting: function() {
				
				var ctx = this;

				// 会议简介
				var wrapper = ctx.wrapper = domCtr.create('div', {
					className: 'mhuiImeetingWrapper'
				}, ctx.containerNode);
				
				var head = domCtr.create('div', {
					className: 'mhuiImeetingHead'
				}, wrapper);
				
				var flag = domCtr.create('div', {
					className: 'mhuiImeetingFlag',
					innerHTML: ctx.type == 'meeting' ? '会议安排' : (ctx.type == 'book' ? '会议预约' : '其他')
				}, head);
				
				domAttr.set(flag, 'data-type', ctx.type);
				
				if(ctx.type == 'meeting' && !ctx.permission) {
					domCtr.create('span', {
						className: 'mui mui-todo_lock mhuiIMeetingLock'
					}, head);
				}
				
//				domCtr.create('div', {
//					className: 'mhuiImeetingSwitch'
//				}, head);
				
				var detail = domCtr.create('div', {
					className: 'mhuiImeetingDetail'
				}, wrapper);
				
				domCtr.create('div', {
					className: 'mhuiImeetingStartTime',
					innerHTML: locale.format(new Date(ctx.start), {
						selector: 'time',
						timePattern: 'HH:mm'
					})
				}, detail);
				
				domCtr.create('div', {
					className: 'mhuiImeetingTitle',
					innerHTML: ctx.title
				}, detail);
				
				
				var startDate = new Date(ctx.start);
				var endDate = new Date(ctx.end);

				var localeFormat = {};
				var splitFlag = true;
				if(endDate.getFullYear() > startDate.getFullYear()) {
					localeFormat.selector = 'datetime';
					localeFormat.datePattern = 'yyyy年MM月dd日';
					localeFormat.timePattern = 'HH:mm';
				} else {
					if(endDate.getDate() > startDate.getDate()) {
						localeFormat.selector = 'datetime';
						localeFormat.datePattern = 'MM月dd日';
						localeFormat.timePattern = 'HH:mm';					
					} else {
						splitFlag = false;
						localeFormat.selector = 'time';
						localeFormat.timePattern = 'HH:mm';	
					}
				}
				
				var startTime = locale.format(new Date(ctx.start), localeFormat);
				var endTime = locale.format(new Date(ctx.end), localeFormat);
				
				domCtr.create('div', {
					className: 'mhuiImeetingDuration',
					innerHTML: '<span>会议时间：</span><span>' + startTime + ' 至 ' +
						(splitFlag ? '<br/>' : '') +
						endTime + '</span>'
				}, detail);
				domCtr.create('div', {
					className: 'mhuiImeetingHost',
					innerHTML: '<span>主持人：</span></span>' + ctx.fdHost + '</span>'
				}, detail);
				
				
				// 会议状态
				ctx.statusSign = domCtr.create('div', {
					className: 'mhuiImeetingStatus'
				}, wrapper);
				ctx.statusSignIcon = domCtr.create('i', {
					className: 'mui mui-stamp'
				}, ctx.statusSign);
				ctx.statusSignLabel = domCtr.create('span', {
					innerHTML: ctx.statusText
				}, ctx.statusSign);
				
				ctx.timerSign = domCtr.create('div', {
					className: 'mhuiImeetingTimer',
					innerHTML: '-:-'
				}, wrapper);
				
			},
			
			renderEmptyTips: function() {
				var ctx = this;
				
				var tipsNode = domCtr.create('div', {
					className: 'mhuiImeetingTips'
				}, ctx.containerNode);
				
				new MessageBox({
					icon: 'mhui-icon-group',
					message: '您可以使用“面对面建会议”<br/>同步会议中的白板和批注'
				}).placeAt(tipsNode);

			},
			
			bindEvents: function() {
				
				var ctx = this;
				
				if(ctx.isEmpty) {
					return;
				}
				
				on(ctx.containerNode, 'click', function(){
					
					if(ctx.type == 'book') {
						
						var endTime = new Date(ctx.end);
						
						if(new Date() > endTime) {
							
							Tip.fail({
								text: '会议已召开！'
							});
							
						} else {
							topic.publish('EVENTS_BOOKMEETING_CLICK', ctx.fdId);
						}
						
					} else if(ctx.type == 'meeting') {
						if(ctx.permission) {
							location.href = dojoConfig.baseUrl + 'km/imeeting/km_imeeting_main/kmImeetingMain.do?method=view&fdId=' + ctx.fdId;
						} else {
							Tip.fail({
								text: '您没有查看权限！'
							});
						}
					}
				});
				
			},
			
			doAction: function() {
				
				var ctx = this;
				
				if(ctx.isEmpty) {
					return;
				}
				
				domAttr.set(ctx.containerNode, 'data-status', ctx.status);
				
				if(ctx.status != 'unHold') {
					return;
				}
				
				var startTime = new Date(ctx.start);
				var endTime = new Date(ctx.end);
				
				var t = date.difference(new Date(), startTime, 'millisecond');
				setTimeout(function() {
					ctx.startCountDown();
				}, t - ctx.countDown * 1000);
				
			},
			
			startCountDown: function() {
				
				var ctx = this;
				
				var startTime = new Date(ctx.start);
				var endTime = new Date(ctx.end);
				
				domAttr.set(ctx.containerNode, 'data-status', 'ready');
				
				var timer = setInterval(function() {
					
					if(date.difference(new Date(), startTime, 'second') <= 0) {
						
						domAttr.set(ctx.containerNode, 'data-status', 'holding');
						html.set(ctx.statusSignLabel, '进行中');
						
						if(timer) {
							clearInterval(timer);
						}
						
						// 会议结束状态
						setTimeout(function() {
							domAttr.set(ctx.containerNode, 'data-status', 'hold');
							html.set(ctx.statusSignLabel, '已召开');
						}, date.difference(new Date(), endTime, 'millisecond'))
						
					} else {
						var timerText = locale.format(new Date(date.difference(new Date(), startTime, 'millisecond')), {
							selector: 'time',
							timePattern: 'mm:ss'
						});
						html.set(ctx.timerSign, timerText);
					}
					
				}, 1000);
			}
			
			
		});
	}
);