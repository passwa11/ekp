define([
    "dojo/_base/declare",
    "dojo/dom-construct",
    "dojo/dom-class",
	"dojo/dom-style",
	"dojo/dom-attr",
    "dojox/mobile/_ItemBase",
   	"mui/util","mui/history/listener",
   	"mui/calendar/CalendarUtil",
   	"mui/dialog/Tip",
   	"dojo/query",
   	"dojo/date",
	"dojo/date/locale" ,
	"dojo/_base/lang",
	"dojo/on","dojo/touch",
	'dojo/parser',"dojo/_base/array","dojox/mobile/TransitionEvent",
	"dojo/text!sys/attend/mobile/stat/attendMainView.tmpl",
	"dojo/request","mui/i18n/i18n!sys-attend:*"
	], function(declare, domConstruct,domClass , domStyle , domAttr , ItemBase , util, listener,cutil,Tip,query,dateUtil,locale,
			lang,on,touch,parser,array,TransitionEvent,attendMainViewTmpl,request,Msg) {
	
	var item = declare("sys.attend.list.item.GroupItemMixin", [ItemBase], {
		
		tag: 'li',
		
		baseClass: '',
		
		buildRendering:function(){
			this.inherited(arguments);
			
			this.domNode = this.containerNode= this.srcNodeRef || domConstruct.create(this.tag,{className:this.baseClass});
			
			this.buildItem();
			
		},
		
		buildItem : function () {
			this.domNode.dojoClick = true;
			if(this.fdSignedLocation){
				this.connect(this.domNode, touch.press, '_onItemClick');
			}

			var muiEkpSubClockInScheduleStatus = domConstruct.create('div', {
				className : 'muiEkpSubClockInScheduleStatus'
			}, this.domNode);

			// 左侧蓝色上班下班图标
			var fdWorkTxt = this.fdType==1 ? (this.fdWorkType=='0'? Msg['mui.fdWorkType.onwork']:Msg['mui.fdWorkType.offwork']) : '';
			domConstruct.create('span', {
				innerHTML : fdWorkTxt
			}, muiEkpSubClockInScheduleStatus);

			var muiEkpSubClockInScheduleInfo = domConstruct.create('div', {
				className : 'muiEkpSubClockInScheduleInfo'
			}, this.domNode);

			var muiEkpSubClockInRecord = domConstruct.create('div', {
				className : 'muiEkpSubClockInRecord'
			}, muiEkpSubClockInScheduleInfo);


			// 打卡时间
			var htmlText = '';
			var _signTime = locale.format(new Date(this.fdSignedTime),{selector : 'time',timePattern : 'HH:mm' });
			if(this.fdSignedStatus=='0'){
				var info = this.fdWorkType=='1'? Msg['mui.fdWorkType.offwork']: Msg['mui.fdWorkType.onwork'];
				var htmlText = this.fdSignedAcross ? "(" + _signTime + ")"+  '&nbsp;' +Msg['mui.second.day'] : "(" + _signTime + ")";
			}else{
				if(this.isAttendStatusBuss() && !this.isAttendedByBuss()){
					var htmlText = this.fdWorkType=='1'? Msg['sysAttendMain.status.offMissTxt']: Msg['sysAttendMain.status.onMissTxt'];
				}else{
					var htmlText = Msg['sysAttendMain.docCreateTime'] + '：' + (this.fdSignedAcross ? _signTime + '&nbsp;' +Msg['mui.second.day']  : _signTime);
				}
			}
			domConstruct.create('span', {
				innerHTML : htmlText
			}, muiEkpSubClockInRecord);

			// 状态标签
			if(this.fdType != 2){

				var tagClass = '';
				var message = '';
				if(this.fdSignedStatus=='4' || this.fdSignedStatus=='5' || this.fdSignedStatus=='6'){
					tagClass = 'muiEkpSubClockInTag normal';
					message = this.fdSignedStatusTxt;
				} else if(this.fdSignedStatus=='1' && this.fdSignedOutside !=true || this.fdState=='2'){
					tagClass = 'muiEkpSubClockInTag normal';
					message = Msg['mui.fdStatus.ok'];
				} else if((this.fdSignedStatus=='0' || this.fdSignedStatus=='2' || this.fdSignedStatus=='3' ) && this.fdState!='2'){
					tagClass = 'muiEkpSubClockInTag late';
					message = this.fdSignedStatusTxt;
				}
				domConstruct.create('i', {
					className : tagClass,
					innerHTML : message
				}, muiEkpSubClockInRecord);

				if(parseInt(this.fdSignedStatus)>0 && this.fdSignedOutside==true && this.fdState!='2'){
					var outsideClass = this.fdOsdReviewType == 1 ? 'muiEkpSubClockInTag late' : 'muiEkpSubClockInTag outSide';
					domConstruct.create('i', {
						className : outsideClass,
						innerHTML : Msg['mui.outside']
					}, muiEkpSubClockInRecord);
				}

				// 提交异常
				if((this.fdSignedStatus=='4' || this.fdSignedStatus=='5' || this.fdSignedStatus=='6') && this.fdBusUrl){//出差,请假,外出
					//var busBtnNode = domConstruct.toDom('<button type="button" class="muiSignInBtn muiSignInBtnPrimaryOutline">' + Msg['mui.bussiness.form'] + '<i class="mui mui-forward"></i></button>');
					//domConstruct.place(busBtnNode,this.head);
					//busBtnNode.dojoClick = true;
					//on(busBtnNode,'click',lang.hitch(this,this._onBusinessClick));
				} else if(this.fdSignedStatus=='0' || this.fdSignedStatus=='2' || this.fdSignedStatus=='3' ||
						(this.fdSignedStatus=='1' && this.fdSignedOutside==true && this.fdOsdReviewType == 1)
						|| this.fdMainExcId && this.fdSigned || !this.fdSigned){
					var excBtnText = Msg['mui.submit.exc'];
					var excBtn="";
					if(this.fdState=='1' || this.fdState==0){
						excBtnText = Msg['mui.exception.doing'] + '...';
					}else if(this.fdState=='2'){
						excBtnText = Msg['mui.exception.done'] + '...';
					}else if(this.fdState=='3'){
						excBtn = Msg['mui.exception.reject'];
					}else if(!this.fdState){
						excBtnText = Msg['mui.submit.exc'];
					}
					var muiEkpSubClockInSubmit = domConstruct.create('div', {
						className : 'muiEkpSubClockInSubmit',
						innerHTML : excBtnText
					}, muiEkpSubClockInRecord);

					this.connect(muiEkpSubClockInSubmit, touch.press, '_onExcSignClick');
					var sendError = domConstruct.create('i', {
						className : 'icon fontmuis muis-to-right'
					}, muiEkpSubClockInSubmit);
					if(excBtn){
						var muiEkpExcInSubmit = domConstruct.create('div', {
							className : 'muiEkpSubClockInSubmit',
							innerHTML : excBtn
						}, muiEkpSubClockInRecord);

						this.connect(muiEkpExcInSubmit, touch.press, '_onExcViewClick');
					}
				}
			}
			// 地址
			if(this.fdSignedLocation) {
				var muiEkpSubClockInPositioned = domConstruct.create('div', {
					className : 'muiEkpSubClockInPositioned'
				}, muiEkpSubClockInRecord);

				var p = domConstruct.create('p', {
					innerHTML : '<i class="icon fontmuis muis-position"></i>' + this.fdSignedLocation
				}, muiEkpSubClockInPositioned);
			}
		},

		//判断是否出差/请假/外出
		isAttendStatusBuss : function(){
			if(this.fdSignedStatus=='4' || this.fdSignedStatus =='5' || this.fdSignedStatus =='6'){
				return true;
			}
			return false;
		},
		//判断出差/请假/外出是否已经打过卡
		isAttendedByBuss : function(){
			if(this.fdSignedStatus=='4' || this.fdSignedStatus =='5' || this.fdSignedStatus =='6'){
				if(this.fdSignedLocation || this.fdSignedWifi || this.fdAppName){
					return true;
				}
				return false;
			}
			return false;
		},

		_onItemClick : function(){
			var self = this;
			if(window.attendMainView){
				window.attendMainView.destroyRecursive();
			}

			var __attendMainViewTmpl = lang.replace(attendMainViewTmpl,{
				attendMainId : this.fdId
			});

			parser.parse(domConstruct.create('div',{ innerHTML:__attendMainViewTmpl,style:'display:none' },query('#content')[0] ,'last'))
			  .then(function(widgetList){
				  array.forEach(widgetList, function(widget, index) {
						if(index == 0){
							self.afterMainViewParse(widget);
							window.attendMainView = widget;
						}
					});

					var opts = {
						transition : 'slide',
						moveTo : window.attendMainView.id
					};

					var locationDialog = window['muiLocationDialog'];
					if(locationDialog){
						domStyle.set(locationDialog.domNode,'display','block');
					}
					domStyle.set(query('.muiHeader')[0],'display','none');
					new TransitionEvent(document.body ,  opts ).dispatch();
			  });
		},

		afterMainViewParse : function(widget) {
			widget.hide();//隐藏
			widget.placeAt(widget.domNode.parentNode,'after');//移除不必要的父节点,保证view的节点与其他view在同一层
			this.backView = widget.getShowingView();
			var self = this;
			listener.add({callback:function(){
				var opts = {
						transition : 'slide',
						moveTo:self.backView.id,
						transitionDir:-1
					};
				var locationDialog = window['muiLocationDialog'];
				if(locationDialog){
					domStyle.set(locationDialog.domNode,'display','none');
				}
				domStyle.set(query('.muiHeader')[0],'display','table');
				new TransitionEvent(document.body,  opts ).dispatch();
			}});
		},

		_onExcSignClick : function(e){

			event.stopPropagation();
			var self = this;
			var fdAttendMainId = this.fdId || '';
			var curUrl = util.setUrlParameter(window.location.href.replace(/#/g,""),'categoryId', this.categoryId);
			var viewExcUrl = util.formatUrl("/sys/attend/sys_attend_main_exc/sysAttendMainExc.do?method=view" + "&fdId="+ this.fdMainExcId +"&fdAttendMainId=" 
				+ fdAttendMainId + "&_referer="+encodeURIComponent(curUrl));

			if(this.fdState && this.fdState!=3 || this.fdState==0) {
				location.href = viewExcUrl;
				return;
			}
			
			if(!this.fdState || this.fdState==3) {
				if(this.fdId) {
					if (this.fdBusSetting && this.fdBusSetting.length > 0) {
						this.buildExcMask(function(){
							self._openExcWin(self.fdId);
						});
					} else {
						this._openExcWin(self.fdId);
					}
				} else {
					// 补全记录
					var fdSignedTime = this.fdSignedTime;
					var _signTime = ''
					if(fdSignedTime){
						var _date = new Date(fdSignedTime);
						_signTime = _date.getHours()*60+_date.getMinutes();
					}
					var proc = Tip.processing();
					proc.show();
					request(util.formatUrl("/sys/attend/sys_attend_main_exc/sysAttendMainExc.do?method=fixAttendMain"
							+"&categoryId=" + self.categoryId + "&fdWorkTimeId="+ self.fdWorkTimeId + "&fdWorkType=" + self.fdWorkType+"&fdSignTime="+_signTime), {
						handleAs : 'json',
					}).then(function(data){
						proc.hide();
						if(data && data.fdAttendMainId) {
							self.fdId = data.fdAttendMainId;
							
							if (self.fdBusSetting && self.fdBusSetting.length > 0) {
								self.buildExcMask(function(){
									self._openExcWin(self.fdId);
								});
							} else {
								self._openExcWin(self.fdId);
							}
						} else {
							Tip.fail({
								text:Msg['mui.submit.exc.fail']
							});
						}
					},function(err){
						proc.hide();
						Tip.fail({
							text:Msg['mui.submit.exc.fail']
						});
					});
				}
				
			}
		},
		
		_onExcViewClick : function(){
			event.stopPropagation();
			event.cancelBubble=true;
			var self = this;
			var fdAttendMainId = this.fdId || '';
			var curUrl = util.setUrlParameter(window.location.href.replace(/#/g,""),'categoryId', this.categoryId);
			var viewExcUrl = util.formatUrl("/sys/attend/sys_attend_main_exc/sysAttendMainExc.do?method=view" + "&fdId="+ this.fdMainExcId +"&fdAttendMainId=" 
				+ fdAttendMainId + "&_referer="+encodeURIComponent(curUrl));

			if(this.fdState) {
				location.href = viewExcUrl;
			}
		},
		
		_openBusWin : function(url){
			location.href = url;
		},
		
		_openExcWin : function(mainId) {
			if(!mainId){
				return;
			}
			var curUrl = util.setUrlParameter(window.location.href.replace(/#/g,""),'categoryId', this.categoryId);
			var method="addExc";
			var addExcUrl = util.formatUrl("/sys/attend/sys_attend_main_exc/sysAttendMainExc.do?method="+method+"&fdAttendMainId=" + mainId 
					+ "&_referer="+encodeURIComponent(curUrl));
			
			var proc = Tip.processing();
			proc.show();
			request(util.formatUrl("/sys/attend/sys_attend_main_exc/sysAttendMainExc.do?method=isAllowPatch&fdAttendMainId=" + mainId), {
				handleAs : 'json',
			}).then(function(data){
				proc.hide();
				 if(data) {
					  if(data.status == 0 && data.msg){
						  var text = '';
						  switch(data.msg) {
							  case 'notAllow' : text = Msg['sysAttendMainExc.error.notAllow'];break;
							  case 'overdue' : text = Msg['sysAttendMainExc.error.overdue'];break;
							  case 'overTimes': text = Msg['sysAttendMainExc.error.overdue'];break;
							  default : Msg['mui.submit.exc.fail'];
						  }
						  Tip.fail({
							  text:text
						  });
						  return;
					  }  else if(data.status == 3 && data.msg){
						  //补卡超过次数按事假半天计算
						  var text = '';
						  switch(data.msg) {
							  case 'overTimes': text = Msg['sysAttendMainExc.error.overdue.overTimes'];break;
							  default : Msg['mui.submit.exc.fail'];
						  }
						  Tip.fail({
							  text:text
						  });
					  }
				 }
				 location.href = addExcUrl;
			},function(err){
				proc.hide();
				console.error(err);
				location.href = addExcUrl;
			});
		},
		
		buildExcMask : function(excFunc) {
			var self = this;
			if (this.excContainer) {
				this._showExcMask();
				return;
			}
			
			this.excContainer = domConstruct.create('div', {
				'className' : 'muiAttendExcMask'
			}, document.body, 'last');
			
			this.excContainerUl = domConstruct.create('div', {
				'className' : 'muiAttendExcUl'
			},  this.excContainer);
			
			var excPool = this._getExcPool(excFunc);
			if(!this.itemObjs) {
				this.itemObjs = [];
			}
			
			for(var i=0; i<excPool.length; i++) {
				var exc = excPool[i];
				var item = domConstruct.create("div", {
					'className' : 'muiAttendExc'
				}, this.excContainerUl);
				var citem  = domConstruct.create("span", {
					'className' : 'muiAttendExcItem'+  " muiAttendExcItemBgColor" + (i % 6),
				}, item);
				var attText  = domConstruct.create("span", {
					'className' : 'muiAttendExcItemText',
					'innerHTML' :  exc.text,
				},item);
				var itemIcon = domConstruct.create("i", {
					'className' : 'mui ' +  exc.icon
				}, citem);
				(function(exc){
					self.connect(item, "onclick", function(){
						exc.func && exc.func();
					});
				})(exc);
				
				this.itemObjs.push(item);
			}
			
			this._showExcMask();
			
		},
		
		_showExcMask : function() {
			domStyle.set(this.excContainer, {
				'display' : 'block',
				'opacity' : 1
			});
			if(!this.handleHide){
				this.handleHide = this.connect(this.excContainer, "onclick", function(){
					this._hideExcMask();
				});
			}
			this.defer( function() {
				for ( var i = 0; i < this.itemObjs.length; i++) {
					domStyle.set(this.itemObjs[i], {
						'-webkit-transform' : 'translate3d(0px, 0px, 0px)',
						'opacity' : 1
					});
				}
			},200);
		},
		
		_hideExcMask : function() {
			for (var i = 0; i < this.itemObjs.length; i++) {
				domStyle.set(this.itemObjs[i],{
						'-webkit-transform' : 'translate3d(0px, 500px, 0px)',
						'opacity' : 0
					});
			}
			
			this.defer(function() {
				domStyle.set(this.excContainer, {
					'opacity' : 0
				});
			}, 500);
			this.defer(function() {
				domStyle.set(this.excContainer, {
					'display' : 'none'
				});
			}, 700);
		},
		
		_getExcPool : function(excFunc) {
			var self = this;
			if(!this.excPool) {
				var pool = [];
				var getIcon = function(excType){
					switch(excType){
						case '4': return 'mui-airplane';break;
						case '5': return 'mui-dateTime';break;
						case '7': return 'mui-airplane';break;
						default : return 'mui-wrong';break;
					}
				};
				
				for(var k=0; k<this.fdBusSetting.length; k++) {
					var bus = this.fdBusSetting[k];
					var exc = {
						"text" : bus.fdBusName || '',
						"func" : (function(url) {
							return function() {
								self._openBusWin(url);
							}
						})(util.formatUrl(bus.fdReviewUrl)),
						"icon" : getIcon(bus.fdBusType),
					};
					pool.push(exc);
				}
				pool.push({
					"text" : Msg['mui.exception.form'],
					"func" :  excFunc || function() {
					},
					"icon" : 'mui-wrong',
				});
				
				this.excPool = pool;
			}
			return this.excPool;
		},
		
		_onBusinessClick : function(){
			event.stopPropagation();
			if(this.fdBusUrl){
				var url = util.formatUrl(this.fdBusUrl);
				location.href=url;
			}
		},
		
		startup : function(){
			if(this._started){ return; }
			this.inherited(arguments);
		},
	
		_setLabelAttr : function(text){
			if(text)
				this._set("label", text);
		},
		
		//url加入当前选中日期参数
		makeUrl : function(){
			var url=util.formatUrl(this.href);
			return url;
		}
		
		
	});
	return item;
});