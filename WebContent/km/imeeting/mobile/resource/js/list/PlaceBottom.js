define("km/imeeting/mobile/resource/js/list/PlaceBottom",  [ "dojo/_base/declare", "dijit/_WidgetBase","dijit/_TemplatedMixin",
				"dijit/_Contained", "dijit/_Container","dojo/_base/array","dojo/text!./tmpl/place_bottom.tmpl", "dojo/date",
				"dojo/date/locale", "dojo/string", "dojo/dom-construct","mui/form/Select", "dojo/_base/lang",
				"dojo/dom-style","dojo/dom-class", "dojo/dom-geometry", "dojo/query","mui/dialog/Tip",
				"dijit/registry","dojo/topic","dojo/ready","mui/util","mui/i18n/i18n!km-imeeting:mobile", "dojo/dom-attr", 
				"dojo/on"],
				function(declare, _WidgetBase, _TemplatedMixin,Contained, Container,array,template, dateClaz,
						locale, string, domConstruct, Select,lang,domStyle,domClass, domGeometry, query,Tip,
						registry,topic,ready,util,msg, domAttr, on) {
	var placeContnt = declare("km.imeeting.PlaceBottom", [ _WidgetBase,_TemplatedMixin, Container,Contained ], {
		//默认自适应
		width : "100%",

		height : "",

		baseClass : "",
		
		canCreateCloud : false,
		
		canCreateBook : false,
		
		createType :"",
		
		bookUrl : '/km/imeeting/km_imeeting_book/kmImeetingBook.do?method=add',
		
		meetingUrl : '/km/imeeting/km_imeeting_main/kmImeetingMain.do?method=add',
		
		// 时间区间显示
		timeZoneNode: null,
		//会议室名称
		placeNameNode: null,
		//会议室/占用详情
		detailNode: null,
		//操作
		operationNode: null,
		
		fdTime : '',
		
		start : [] ,
		
		finish : [],
		
		fdStartTime : '',
		fdFinishTime :'',
		//记录所选会议室id
		placeId :"",
		
		// 联系电话
		fdPhone : null,
		
		contractHandler : null,
		
		createHandler : null,
		
		templateString : template,
		// 渲染模板
		startup : function() {
			if (this._started)
				return;
			this.inherited(arguments);
			this.subscribe('/km/imeeting/placeitem/_selected','generatePlaceBottom');
			//监听时间和分类变化时，情况已经勾选的值
			this.subscribe('/km/imeeting/navitem/selected','handleTimeOrCateChanged');
			this.subscribe('/km/imeeting/category/change','handleTimeOrCateChanged');
			
			var ctx = this;
			on(ctx.cancelBtnNode, 'click', function() {
				ctx.clearAllSelected();
				ctx.showStatusArea();
				
				topic.publish('/km/imeeting/selected/change', null, {
					start: null,
					finish: null,
					placeId: ctx.placeId,
					isCancel: true
				});
			});
			topic.publish('/km/imeeting/PlaceBottom/ready',this);
		},
		
		buildRendering : function() {
			this.inherited(arguments);
			this.subscribe('/mui/form/valueChanged',
					'onDialogSelect');
			this.subscribe('mui/form/select/callback',
					'onDialogCallback');
			this.showStatusArea();
		},
		getPool : function() {
			var pool = [];
			if(this.canCreateBook){
				pool.push({
					value : 1,
					text : msg['mobile.kmImeetingBook.create'],
					callback : function() {
						this._createBook();
					}
				});
			}
			if(this.canCreateCloud){
				pool.push({
					value : 2,
					text : msg['mobile.kmImeetingMain.create'],
					callback : function() {
						this._createMeeting();
					}
				});
			}
			return pool;
		},
		
		onDialogSelect : function(obj, evt) {
			if (obj != this.select)
				return;
			var pool = this.getPool();
			for (var i = 0; i < pool.length; i++) {
				if (evt.value == pool[i].value) {
					lang.hitch(this, pool[i].callback)();
					this.select.destroy();
					return;
				}
			}
		},

		onDialogCallback : function(obj, evt) {
			if (obj == this.select)
				this.select.destroy();
		},
		
		showPlaceArea:function(){
			query('.muiMeetingBookInformation',this.domNode)[0].style.display = "block";
			query('.muiMeetingBookStatus',this.domNode)[0].style.display = "none";
		},
		
		showStatusArea:function(){
			query('.muiMeetingBookInformation',this.domNode)[0].style.display = "none";
			query('.muiMeetingBookStatus',this.domNode)[0].style.display = "block";
			query('.muiMeetingBookStatus>ul>li',this.domNode)[0].innerHTML = msg['mobile.oper.late'];
			query('.muiMeetingBookStatus>ul>li',this.domNode)[1].innerHTML = msg['mobile.oper.occupied'];
			query('.muiMeetingBookStatus>ul>li',this.domNode)[2].innerHTML = msg['mobile.oper.selected'];
			query('.muiMeetingBookStatus>ul>li',this.domNode)[3].innerHTML = msg['mobile.oper.exam'];
			query('.muiMeetingBookStatus>ul>li',this.domNode)[4].innerHTML = msg['mobile.oper.selectable'];
		},
		
		showPlaceOccupiedDetail:function(srcObj,detail,value){
			var allEle = query('[name ^="'+srcObj.fdId+'"]',srcObj.domNode);
			var self = this;
			array.forEach(allEle, function(item) {
				if(item.value == value){
					if(!domClass.contains(item, "place_nobook")){
						domClass.add(item,"place_nobook");
						self.showPlaceArea();
						
						//console.log("会议室占用详情");
						var ele = query('.muiMeetingBookInformation',self.domNode)[0];
						if(ele && domClass.contains(ele, "canBook")){
							domClass.remove(ele, "canBook");
						}
						var occupiedDetail = srcObj.fdOccupiedlist[detail];
						self.timeZoneNode.innerHTML= occupiedDetail.timeArea +":00-" + (occupiedDetail.timeArea+1)+":00";
						
//						self.placeNameNode.innerHTML= srcObj.fdName;
//						var detailStr = '<li><i class="mui mui-occupied"></i>' + msg['mobile.kmImeetingBook.occupied'] + '</li>';
//						detailStr += '<li><i class="mui mui-doc"></i>' + (occupiedDetail.fdName || '') + '</li>';
//						detailStr += '<li><i class="mui mui-people"></i>' + (occupiedDetail.fdHost || '') + '</li>';

						if(occupiedDetail.fdHasExam == 'wait') {
							self.placeNameNode.innerHTML= msg['mobile.meeting.review'];
							self.fdPhone = occupiedDetail.fdPlaceKeeperPhone;
							domAttr.set(self.infoMainNode, 'data-flag', 'callKeeper');
							self.operationTextNode.innerHTML = msg['mobile.custodian'];
						} else {
							self.placeNameNode.innerHTML= msg['mobile.conferenceRoomIsOccupied'];
							self.fdPhone = occupiedDetail.fdPhone;
							domAttr.set(self.infoMainNode, 'data-flag', 'callHost');
							self.operationTextNode.innerHTML = msg['mobile.reservation'];
						}
						
						var detailStr = '<li>' + msg['mobile.content'] + "：" + (occupiedDetail.fdName || '') + '</li>';
						detailStr += '<li>' + msg['mobile.reservation'] + "：" + (occupiedDetail.fdHost || '') + '</li>';
						detailStr += '<li>' + msg['mobile.place'] + "：" + (srcObj.fdAddressFloor || '') + '</li>';
						self.detailNode.innerHTML= detailStr;
						
					    
						if(self.contractHandler == null){
					    	self.contractHandler = self.connect(self.operationNode, "onclick", 'contractHost');
					    }
					    //
					    if(self.createHandler != null){
					    	self.disconnect(self.createHandler);
					    	self.createHandler = null;
					    }
					}else{
						domClass.remove(item,"place_nobook");
						self.showStatusArea();
					    if(self.createHandler != null){
					    	self.disconnect(self.createHandler);
					    	self.createHandler = null;
					    }
					    if(self.contractHandler != null){
					    	self.disconnect(self.contractHandler);
					    	self.contractHandler = null;
					    }
					}
				}else{
					if(domClass.contains(item, "place_nobook")){
						domClass.remove(item,"place_nobook");
					}
				}
			});
			
		},
		contractHost:function(){
			if(this.fdPhone){
				location.href="tel:"+this.fdPhone;
			}else{
				Tip.tip({icon:'mui mui-warn', text:msg['mobile.notInfo.tip'] + "!!",width:'260',height:'60'});
			}
		},
		
		showPlaceDetail:function(srcObj){
			this.showPlaceArea();

			domAttr.set(this.infoMainNode, 'data-flag', 'create');
			this.operationTextNode.innerHTML = msg['mobile.next'];

			//console.log("会议室详情");
			var ele = query('.muiMeetingBookInformation',this.domNode)[0];
			if(ele && !domClass.contains(ele, "canBook")){
				domClass.add(ele, "canBook");
			}
			this._setTime4Create();
			this.timeZoneNode.innerHTML= this.fdStartTime+":00 - "+this.fdFinishTime+":00";
			
//			this.placeNameNode.innerHTML= srcObj.fdName;
//			var detailStr = '<li><i class="mui mui-free"></i>' + msg['mobile.kmImeetingBook.free'] + 
//				(srcObj.fdNeedExamFlag == 'true' ? '<span>需审核</span>' : '') + '</li>';
//			detailStr += '<li><i class="mui mui-position"></i>' + (srcObj.fdAddressFloor || '') +  '</li>';
//			detailStr += '<li><i class="mui mui-asset"></i>' + (srcObj.fdDetail || '') +  '</li>';
			
			this.placeNameNode.innerHTML= msg['mobile.conferenceRoomIsFree'];
			var detailStr = '<li>' + msg['mobile.meetingRoom'] + "：" + srcObj.fdName + '</li>';
			detailStr += '<li>' + msg['mobile.place'] + "：" + (srcObj.fdAddressFloor || '') +  '</li>';
			detailStr += '<li>' + msg['mobile.device'] + "：" + (srcObj.fdDetail || '') +  '</li>';
			
			this.detailNode.innerHTML= detailStr;
		    
		    if(this.createHandler == null){
		    	  this.createHandler = this.connect(this.operationNode, "onclick", 'handleCreate');
		    }
		    //
		    if(this.contractHandler != null){
		    	this.disconnect(this.contractHandler);
		    	this.contractHandler = null;
		    }
		},
		
		//设置时间
		_setTime4Create:function(){
			if(this.start[0] && this.finish[0]){
				this.fdStartTime = this.start[0].value;
				this.fdFinishTime = (this.finish[0].value) + 1;
			}else if(this.start[0]){
				this.fdStartTime = this.start[0].value;
				this.fdFinishTime= (this.start[0].value) + 1;
			}else if(this.finish[0]){
				this.fdStartTime = this.finish[0].value;
				this.fdFinishTime = (this.finish[0].value) + 1;
			}
		},
		
		_createMeeting:function(){
			this._setTime4Create();
			this.meetingUrl += "&fdTime="+this.fdTime+"&fdStratTime="+this.fdStartTime+"&fdFinishTime="+this.fdFinishTime+"&resId="+this.placeId;
			window.open(util.formatUrl(this.meetingUrl),'_self');
			//location.replace(util.formatUrl(this.meetingUrl));
		},
		_createBook:function(){
			this._setTime4Create();
			this.bookUrl += "&fdTime="+this.fdTime+"&fdStratTime="+this.fdStartTime+"&fdFinishTime="+this.fdFinishTime+"&resId="+this.placeId;
			window.open(util.formatUrl(this.bookUrl),'_self');
			//location.replace(util.formatUrl(this.bookUrl));
		},
		
		handleCreate:function(){
			
			if(this.start[0] || this.finish[0]){
				if(this.createType){
					
					if(this.createType == 'meeting'){
					
						if(this.canCreateCloud) {
							this._createMeeting();
						} else {
							Tip.tip({icon:'mui mui-warn', text:'很抱歉，您没有新建会议权限！',width:'260',height:'60'});
						}
					}
					if(this.createType == 'book'){
						if(this.canCreateBook) {
							this._createBook();
						} else {
							Tip.tip({icon:'mui mui-warn', text:'很抱歉，您没有预约会议权限！',width:'260',height:'60'});
						}
					}
				}else{
					this.select = new Select({
						store : this.getPool(),
						mul : false,
						showClass:'muiDialogSelect meetingCreateDialog',
						subject : msg['mobile.kmImeeting.operateType']
					});
					this.select.startup();
					this.select._onClick();
				}
			}else{
				alert("请选择会议时间段");
			}
		},
		
		clearAllSelected:function(){
			this.start.splice(0,this.start.length);
			this.finish.splice(0,this.finish.length);
		},
		
		handleTimeOrCateChanged:function() {
			this.clearAllSelected();
			this.showStatusArea();
		},
		
		
		generatePlaceBottom: function(srcObj,evt) {
				//console.log(srcObj, evt);
				var startDetail = {};
				startDetail['fdId'] = srcObj.fdId;
				startDetail['fdName'] = srcObj.fdName;
				startDetail['value'] = evt.value;
				startDetail['fdSeats'] = srcObj.fdSeats;
				startDetail['docKeeper'] = srcObj.docKeeper;
				startDetail['fdDetail'] = srcObj.fdDetail;
				if(evt.detail){
					startDetail['occupied'] = srcObj.fdOccupiedlist[evt.detail];
				}
				
				if(evt.detail){
					if(this.start.length == 0 && this.finish.length == 0){
						//显示会议室占用详情
						this.placeId = srcObj.fdId;
						this.showPlaceOccupiedDetail(srcObj,evt.detail,evt.value);
					}else{
						this.clearAllSelected();
						if(srcObj.fdId == this.placeId){
							this.showStatusArea();
							Tip.tip({icon:'mui mui-warn', text:'所选时间时间包含非空闲时间！！',width:'260',height:'90'});
						}else{
							this.showPlaceOccupiedDetail(srcObj,evt.detail,evt.value);
						}
					}
				}else{
					if(this.start.length == 0 && this.finish.length == 0){
						this.start.push(startDetail);
						this.placeId = srcObj.fdId;
						this.fdTime = srcObj.fdTime;
						//显示会议室详情
						this.showPlaceDetail(srcObj);
					}else{
						if(srcObj.fdId == this.placeId){
							if(this.start.length == 0){
								var tempfinish = this.finish.pop();
								if(tempfinish.value != evt.value){
									if(tempfinish.value > evt.value){
										this.start.push(startDetail);
										this.finish.push(tempfinish);
									}else{
										this.start.push(tempfinish);
										this.finish.push(startDetail);
									}
									this.showPlaceDetail(srcObj);
								}else{
									this.showStatusArea();
								}
							}else if(this.finish.length == 0){
								var tempstart = this.start.pop();
								if(tempstart.value != evt.value){
									if(tempstart.value > evt.value){
										this.start.push(startDetail);
										this.finish.push(tempstart);
									}else{
										this.start.push(tempstart);
										this.finish.push(startDetail);
									}
									this.showPlaceDetail(srcObj);
								}else{
									this.showStatusArea();
								}
							}else{
								var tempstart = this.start.pop();
								var tempfinish = this.finish.pop();
								if(tempstart.value != tempfinish.value){
									if(tempstart.value == evt.value ){
										startDetail['value'] = evt.value+1;
										this.start.push(startDetail);
										this.finish.push(tempfinish);
									}else if(tempfinish.value == evt.value){
										startDetail['value'] = evt.value-1;
										this.start.push(tempstart);
										this.finish.push(startDetail);
									}else{
										if(evt.value < tempstart.value){
											this.start.push(startDetail);
											this.finish.push(tempfinish);
										}else if(evt.value > tempfinish.value){
											this.start.push(tempstart);
											this.finish.push(startDetail);
										}else{
											this.start.push(startDetail);
										}
									}
									this.showPlaceDetail(srcObj);
								}else{
									if(tempstart.value == evt.value ){
										this.showStatusArea();
									}else{
										if(evt.value < tempstart.value){
											this.start.push(startDetail);
											this.finish.push(tempfinish);
										}else if(evt.value > tempfinish.value){
											this.start.push(tempstart);
											this.finish.push(startDetail);
										}else{
											this.start.push(startDetail);
										}
										this.showPlaceDetail(srcObj);
									}
								}
							}
							//this.showPlaceDetail(srcObj);
						}else{
							this.clearAllSelected();
							this.start.push(startDetail);
							this.placeId = srcObj.fdId;
							this.fdTime = srcObj.fdTime;
							//显示会议室详情
							this.showPlaceDetail(srcObj);
						}
					}
					
				}
				var startV,finishV;
				if(this.start[0]){
					startV = this.start[0].value;
				}
				if(this.finish[0]){
					finishV = this.finish[0].value;
				}
				var hasOccupied = false;
				if(startV && finishV){
					for(var i = startV;i<= finishV;i++){
						var ele = query('[name="'+this.placeId+'_'+i+':00'+'"]',srcObj.domNode)[0];
						if(ele && domClass.contains(ele, "place_occupied")){
							hasOccupied = true;
						}
					}
				}else if(startV){
					var ele = query('[name="'+this.placeId+'_'+startV+':00'+'"]',srcObj.domNode)[0];
					if(ele && domClass.contains(ele, "place_occupied")){
						hasOccupied = true;
					}
				}else if(finishV){
					var ele = query('[name="'+this.placeId+'_'+finishV+':00'+'"]',srcObj.domNode)[0];
					if(ele && domClass.contains(ele, "place_occupied")){
						hasOccupied = true;
					}
				}
				if(hasOccupied){
					this.clearAllSelected();
					startV = null;
					finishV = null;
					Tip.tip({icon:'mui mui-warn', text:'所选时间时间包含非空闲时间！！',width:'260',height:'90'});
				}
				topic.publish("/km/imeeting/selected/change", null,{placeId:this.placeId,start:startV,finish:finishV});
				/*
				if(this.start[0]){
					console.log("start:"+this.start[0].value);
				}else{
					console.log("start:null");
				}
				if(this.finish[0]){
					console.log("finish:"+this.finish[0].value);
				}else{
					console.log("finish:null");
				}
				console.log(this.placeId);
				*/
		}
	});
	return placeContnt;
});
