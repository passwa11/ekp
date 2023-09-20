define([ 'dojo/_base/declare', 'dojo/_base/array', 'dojo/_base/lang' , 'dojo/topic', 'dojo/request', 'dojo/on',
         'dojo/dom', 'dojo/dom-construct', 'dojo/dom-style','dojo/dom-class', 'dojo/dom-attr', 'dojo/html', 
         'mhui/list/ItemBase', 'dojo/date/locale', 'dojo/date', 'mhui/message/MessageBox', 'mui/dialog/Tip'],
	function(declare, array, lang, topic, request, on, dom, domCtr, domStyle, domClass, domAttr, html,
			ItemBase, locale, date, MessageBox, Tip) {
		return declare('km.imeeting.maxhub.MeetingMapItem', [ ItemBase ], {
			
			countDown: 604800, // 单位秒
			
			isFirst: false,
			isEmpty: false,
			
			datetimeFormat: {
				selector: 'datetime',
				datePattern: 'yyyy-MM-dd',
				timePattern: 'HH:mm'
			},			
			 
			dateFormat: {
				selector: 'date',
				datePattern: 'yyyy-MM-dd'
			},
			
			monthAndDateTimeFormat: {
				selector: 'datetime',
				datePattern: 'MM-dd',
				timePattern: 'HH:mm'
			},
			
			timeFormat: {
				selector: 'time',
				timePattern: 'HH:mm'
			},
			
			beforeInit: function() {
				this.domNode = domCtr.create('div', {
					className: 'mhuiMeetingMapItem'
				});
				
				if(this.isEmpty) {
					domClass.add(this.domNode, 'mhuiMeetingMapEmptyItem')
				}
				
				if(this.isFirst) {
					domClass.add(this.domNode, 'mhuiMeetingMapFirstItem')
				}
				
				this.containerNode = domCtr.create('div', {
					className: 'mhuiMeetingMapItemWrapper'
				}, this.domNode);
			},
			
			initialize: function() {
				this.inherited(arguments);
				this.doAction();
			},
			
			bindEvents: function() {
				
				var ctx = this;
				
				if(!ctx.isEmpty && !ctx.isFirst) {
					on(ctx.containerNode, 'click', function(){
						location.href = dojoConfig.baseUrl + 'km/imeeting/km_imeeting_main/kmImeetingMain.do?method=view&fdId=' + ctx.fdId;
					});
				}
				
			},
			
			renderItem: function() {
				
				var ctx = this;
				
				if(ctx.isEmpty) {
					
					domCtr.create('div', {
					}, ctx.containerNode);
					
					domCtr.create('span', {
						innerHTML: '此条件下暂无会议记录！'
					}, ctx.containerNode);
					
					return;
				}
				
				if(ctx.isFirst) {
					
					domCtr.create('span', {
						innerHTML: '我的会议'
					}, ctx.containerNode);
					
					return;
				}
				
				domCtr.create('div', {
					innerHTML: ctx.fdName,
					className: 'title'
				}, ctx.containerNode);

				var detailNode = ctx.detailNode = domCtr.create('div', {
					className: 'detail'
				}, ctx.containerNode);
				
				var timeNode = domCtr.create('div', {
					className: 'detailItem',
					innerHTML: '<i class="mui mui-meeting_date"></i>'
				}, detailNode);
				
				var startDate = new Date(ctx.fdHoldDate);
				var endDate = new Date(ctx.fdFinishDate);
				var timeSpanNode = domCtr.create('span', { }, timeNode);

				if(startDate.getFullYear() != endDate.getFullYear()) {
					html.set(timeSpanNode, locale.format(startDate, ctx.datetimeFormat) + ' - ' +
							locale.format(endDate, ctx.datetimeFormat));
				} else if(startDate.getDate() != endDate.getDate()){
					
					html.set(timeSpanNode, locale.format(startDate, ctx.monthAndDateTimeFormat) + ' - ' + 
							locale.format(endDate, ctx.monthAndDateTimeFormat));
					
				} else {
					html.set(timeSpanNode, locale.format(startDate, ctx.dateFormat) + ' ' +
							locale.format(startDate, ctx.timeFormat) + '-' +
							locale.format(endDate, ctx.timeFormat));
				}
				
				if(ctx.fdHost) {
					domCtr.create('div', {
						className: 'detailItem',
						innerHTML: '<i class="mui mui-person"></i><span>' + ctx.fdHost + '</span>'
					}, detailNode);
				}
				
				if(ctx.fdPlace) {
					domCtr.create('div', {
						className: 'detailItem',
						innerHTML: '<i class="mui mui-meeting_path"></i><span>' + ctx.fdPlace + '</span>'
					}, detailNode);
				}
				
				var tipNode = ctx.tipNode = domCtr.create('div', {
					className: 'tip'
				}, ctx.containerNode);
				
				var statusNode = ctx.statusNode = domCtr.create('div', {
					className: 'status'
				}, ctx.tipNode);
				domCtr.create('i', {
					className: 'mui mui-stamp'
				}, statusNode);
				ctx.statusLabelNode = domCtr.create('span', {
					innerHTML: ctx.docStatus
				}, statusNode);
				
				var countDownNode = ctx.countDownNode = domCtr.create('div', {
					className: 'countDown mhui-hidden'
				}, ctx.tipNode);
				
				ctx.countDownTipNode = domCtr.create('span', {
					className: 'countDownTip',
					innerHTML: ''
				}, countDownNode);
				ctx.countDownLabelNode = domCtr.create('span', {
					className: 'countDownLabel',
					innerHTML: '-:-'
				}, countDownNode);
				
			},
			
			getStatus: function() {
				var now = new Date();
				var startTime = new Date(this.fdHoldDate);
				var endTime = new Date(this.fdFinishDate);
				
				if(now < startTime) {
					return 'unHold';
				} else if (now >= startTime && now < endTime) {
					return 'holding';
				} else {
					return 'hold';
				}
				
			},
			
			doAction: function() {
				
				var ctx = this;
				
				if(ctx.isEmpty) {
					return;
				}
				
				if(ctx.docStatusValue != '30') {
					domAttr.set(ctx.containerNode, 'data-status', 'hold');
					return;
				}
				
				var status = ctx.getStatus();
				domAttr.set(ctx.containerNode, 'data-status', status);
				
				if(status != 'unHold') {
					return;
				}
				
				var startTime = new Date(ctx.fdHoldDate);
				var t = date.difference(new Date(), startTime, 'millisecond');
				setTimeout(function() {
					ctx.startCountDown();
				}, t - ctx.countDown * 1000);
				
			},
			
			startCountDown: function() {
				
				var ctx = this;
				
				var startTime = new Date(ctx.fdHoldDate);
				var endTime = new Date(ctx.fdFinishDate);
				
				domAttr.set(ctx.containerNode, 'data-status', 'ready');
				domClass.remove(ctx.countDownNode, 'mhui-hidden');
				domClass.add(ctx.statusNode, 'mhui-hidden');
				
				var timer = setInterval(function() {
					
					if(date.difference(new Date(), startTime, 'second') <= 0) {
						
						domAttr.set(ctx.containerNode, 'data-status', 'holding');
						html.set(ctx.statusLabelNode, '进行中');
						
						if(timer) {
							clearInterval(timer);
						}
						
						domClass.add(ctx.countDownNode, 'mhui-hidden');
						domClass.remove(ctx.statusNode, 'mhui-hidden');
						
						// 会议结束状态
						setTimeout(function() {
							domAttr.set(ctx.containerNode, 'data-status', 'hold');
							html.set(ctx.statusLabelNode, '已召开');
						}, date.difference(new Date(), endTime, 'millisecond'));
						
					} else {
						
						var t = date.difference(new Date(), startTime, 'second');
						var d = date.difference(new Date(), startTime, 'day');
						if(d >= 1 && d <= 7) {
							html.set(ctx.countDownLabelNode, d + '天');
							html.set(ctx.countDownTipNode, '剩余');
							domAttr.set(ctx.countDownNode, 'data-type', 'day');
						} else if(t > 3600 && t <= 86400) {
							html.set(ctx.countDownLabelNode, parseInt(t / 3600) + '小时');
							html.set(ctx.countDownTipNode, '剩余');
							domAttr.set(ctx.countDownNode, 'data-type', 'hour');
						} else {
							var countDownText = locale.format(new Date(t * 1000), {
								selector: 'time',
								timePattern: 'mm:ss'
							});
							html.set(ctx.countDownLabelNode, countDownText);
							html.set(ctx.countDownTipNode, '即将开始');
							domAttr.remove(ctx.countDownNode, 'data-type');
						}
						
					}
					
				}, 1000);
			},
			
			destroy: function() {
				console.log(destroy);
			}
			
		});
	}
);