define(
		[ "dojo/_base/declare", "dojo/dom-geometry", "dojo/touch","mui/device/adapter",
				"dojo/_base/window", "dojo/topic", "dojo/_base/array",
				"mui/util",'dojo/dom-style' ,'dojo/dom-construct','mui/calendar/CalendarUtil', 'dojo/query'],
		function(declare, domGeometry, touch, adapter, win, topic, array, util,domStyle,domConstruct,cutil, query) {
			var claz = declare(
					"mui.calendar._BottomEventMixin",
					null,
					{

						eventNode : null,

						// 事件数组
						connects : [],

						dy : 0,

						url : null,
						addName:null,

						buildRendering : function() {
							this.inherited(arguments);
							this.startPos = domGeometry.position(this.domNode);
							this.bindEvent();
							if(this.url){
								//this.eventNode.innerHTML='添加事件';
								//domConstruct.create('div',{className:'mui-plus mui'},this.eventNode,'first');
								this.connect(this.eventNode, touch.release,'eventClick');
							}
						},

						eventClick : function() {
							if (this.url){
								var url = util.formatUrl(this.url,true);
								var myDateFormat = dojoConfig.Date_format;
								// 英文下mmddyyyy传入后台，convertStringToDate无法正常解析格式-->mmddhhhh #170547
								if(myDateFormat == "MM/dd/yyyy"){
									myDateFormat = "yyyy/MM/dd";
								}
								var currentStr = cutil.formatDate(this.currentDate,myDateFormat);
								url = util.setUrlParameter(url,'startTime',currentStr);
								url = util.setUrlParameter(url,'endTime',currentStr);
								url = util.setUrlParameter(url,'ownerId',dojoConfig.CurrentUserId);
								adapter.open(url,'_blank');
								//location.href = url;
							}
						},

						eventStop : function(evt) {
							evt.preventDefault();
							evt.stopPropagation();
						},

						bindEvent : function() {
							//console.log('bindEvent');
							this.touchStartHandle = this.connect(this.domNode,
									touch.press, "onTouchStart");
						},

						unBindEvent : function() {
							//console.log('unBindEvent');
							this.disconnect(this.touchStartHandle);
						},

						onTouchStart : function(e) {
							//console.log('onTouchStart');
							this.dy = 0;
							this.eventStop(e);
							this.connects.push(this.connect(win.doc,
									touch.move, "onTouchMove"));
							this.connects.push(this.connect(win.doc,
									touch.release, "onTouchEnd"));
							
							//拖动optNode是由于未知因素导致了cancel，因此监听这个事件解决optNode不可拖动的问题
							this.connects.push(this.connect(win.doc,
									touch.cancel, 'onTouchCancel'));
							
							this.touchStartY = e.touches ? e.touches[0].pageY
									: e.clientY;
							this.startPos = domGeometry.position(this.domNode);
						},
						
						onTouchCancel: function(e) {
							//console.log('onTouchCancel');
							this.eventStop(e);
							
							var y = -(this.contentHeight - this.weekHeight), top = 0;
							
							var t = this.startPos.y - domGeometry.position(this.domNode).y;
							
							if(t > 0) {//上拖动
								top = 0;//还原
								this.unBindEvent();
								this._bindEvent();
							} else if(t < 0) {//下拖动
								y = 0;
								top = this.weekHeight + this.headerHeight;
								this.publishStatus(true);
							}
							
							if(t!=0) {
								this.scrollTo({
									y : y
								}, true);
								
								this.publishScroll({
									y : y,
									top : top
								});
							}
							
							
							this.disconnects();
						},

						onTouchMove : function(e) {
							//console.log('onTouchMove');
							this.eventStop(e);
							var y = e.touches ? e.touches[0].pageY : e.clientY;
							this.dy = y - this.touchStartY;
							if (this.dy > 0)
								return;

							var _y = this.startPos.y - this.weekHeight
									- this.headerHeight;
							if (Math.abs(this.dy) > _y)
								return;

							this.scrollTo({
								y : this.dy
							});

							this.publishScroll({
								y : this.dy,
								top : (this.weekHeight + this.headerHeight)
										* (1 - Math.abs(this.dy) / _y)
							});
						},

						onTouchEnd : function(e) {
							//console.log('onTouchEnd');
							var y = 0, top = 0;
							if (this.contentHeight >> 1 > Math.abs(this.dy)) {
								top = this.weekHeight + this.headerHeight;
							} else {
								y = -(this.contentHeight - this.weekHeight);
								top = 0;
								this.unBindEvent();
								this._bindEvent();
								this.publishStatus(false);
							}
							this.scrollTo({
								y : y
							}, true);
							this.publishScroll({
								y : y,
								top : top
							});

							this.disconnects();
						},
						
						scrollToWeek : function(){
							var y = -(this.contentHeight - this.weekHeight),
								top = 0;
							this.unBindEvent();
							this._bindEvent();
							this.publishStatus(false); 
							
							this.scrollTo({
								y : y
							}, true);
							this.publishScroll({
								y : y,
								top : top
							});

							this.disconnects();
							
						},

						_bindEvent : function() {
							//console.log('_bindEvent');
							this._touchStartHandle = this.connect(this.domNode,
									touch.press, "_onTouchStart");
						},

						_unBindEvent : function() {
							//console.log('_unBindEvent');
							if(this._touchStartHandle){
								this.disconnect(this._touchStartHandle);
							}
						},

						_onTouchStart : function(e) {
							//console.log('_onTouchStart');
							this.dy = 0;
							this.eventStop(e);
							this.connects.push(this.connect(win.doc,
									touch.move, "_onTouchMove"));
							this.connects.push(this.connect(win.doc,
									touch.release, "_onTouchEnd"));
							
							//拖动optNode是由于未知因素导致了cancel，因此监听这个事件解决optNode不可拖动的问题
							this.connects.push(this.connect(win.doc,
									touch.cancel, '_onTouchCancel'));
							this.subscribe('/mui/calendar/listScrollableTop',
									'listScrollableTop');
							this.touchStartY = e.touches ? e.touches[0].pageY
									: e.clientY;
							
							this.startPos = domGeometry.position(this.domNode);
						},

						// 列表滚动是否到顶
						listTop : true,

						listScrollableTop : function(obj, to) {
							this.listTop = (to.y >= 0);
						},
						
						_onTouchCancel: function(e) {
							//console.log('_onTouchCancel');
							this.eventStop(e);
							
							var y = -(this.contentHeight - this.weekHeight), top = 0;
							
							var t = this.startPos.y - domGeometry.position(this.domNode).y;
							
							if(t > 0) {//上拖动
								top = 0;//还原
							} else if(t < 0) {//下拖动
								y = 0;
								top = this.weekHeight + this.headerHeight;
								this._unBindEvent();
								this.bindEvent();
								this.publishStatus(true);
							}
							
							if(t != 0) {
								this.scrollTo({
									y : y
								}, true);
								
								this.publishScroll({
									y : y,
									top : top
								});
							}
							
							this.disconnects();
						},

						_onTouchMove : function(e) {
							//console.log('_onTouchMove');
							this.eventStop(e);
							var y = e.touches ? e.touches[0].pageY : e.clientY;
							this.dy = y - this.touchStartY;

							if (this.dy < 0
									|| this.dy >= this.contentHeight
											- (this.weekHeight))
								return;

							
							var optNode = query(e.target).closest('.muiCalendarOpt')[0];
							//拖动optNode不校验是否到顶部
							if (!this.listTop && !optNode)
								return;

							var dy = -(this.contentHeight - this.weekHeight)
									+ this.dy;
							this.scrollTo({
								y : dy
							});

							var toTop = (this.weekHeight + this.headerHeight)
											* (1 - Math.abs(dy)
													/ (this.startPos.y
															- this.weekHeight - this.headerHeight));
							
							this.publishScroll({
								y : dy,
								top : toTop
							});

						},

						_onTouchEnd : function(e) {
							//console.log('_onTouchEnd');
							if (!this.listTop)
								return;

							var y = -(this.contentHeight - this.weekHeight), top = 0;

							if (this.contentHeight >> 1 > Math.abs(this.dy)) {
								top = 0;// 还原
							} else {
								y = 0;
								top = this.weekHeight + this.headerHeight;
								this._unBindEvent();
								this.bindEvent();
								this.publishStatus(true);
							}
							this.scrollTo({
								y : y
							}, true);

							this.publishScroll({
								y : y,
								top : top
							});
							this.disconnects();
						},

						publishScroll : function(to) {
							topic.publish('/mui/calendar/bottomScroll', this,
									to);
						},

						publishStatus : function(status) {
							// true代表默认模式
							topic.publish('/mui/calendar/bottomStatus', this, {
								status : status
							});
						},

						disconnects : function() {
							//console.log('disconnects');
							array.forEach(this.connects, function(item) {
								this.disconnect(item);
							}, this);
							this.connects = [];
							
						}
					});
			return claz;
		});