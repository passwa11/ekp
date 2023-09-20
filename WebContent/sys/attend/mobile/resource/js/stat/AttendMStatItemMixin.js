define([
    "dojo/_base/declare","dojo/_base/lang","dojo/parser","dojox/mobile/TransitionEvent",
    "dojo/dom-construct","dojo/_base/array","dijit/registry","dojo/query",
    "dojo/dom-class","dojo/topic","dojo/date/locale",
	"dojo/dom-style","mui/i18n/i18n!sys-mobile",
	"dojo/dom-attr",
    "dojox/mobile/_ItemBase",
   	"mui/util",
   	"mui/list/item/_ListLinkItemMixin","dojo/text!sys/attend/mobile/stat/attendRecordView.tmpl","mui/i18n/i18n!sys-attend",
	"dojo/on","dojo/touch",
   	"dojox/mobile/TransitionEvent",
	"dojo/text!sys/attend/mobile/stat/attendMainView.tmpl",
	"dojo/_base/array",'dojo/parser',"mui/history/listener",
   	"dojo/query",
   	'dijit/registry',"dojo/topic"
	], function(declare,lang,parser,TransitionEvent, domConstruct,array,registry,query,domClass,topic,locale,
			domStyle,muiMsg,domAttr,ItemBase,util,_ListLinkItemMixin,templateString,Msg,
			on,touch,TransitionEvent,attendMainViewTmpl,array,parser,listener,query,registry,topic) {
	
	var item = declare("sys.attend.AttendMStatItemMixin", [ItemBase, _ListLinkItemMixin], {
		
		tag:"li",
		
		baseClass:"muiAttendChartListItem muiListItem",
		
		href:'',
		
		buildRendering:function(){
			this.inherited(arguments);
			this.domNode = this.containerNode= this.srcNodeRef
				|| domConstruct.create(this.tag,{className:this.baseClass});
			this.domNode.dojoClick = true;
			if(this.fdType=='2'||this.fdType=='3'||this.fdType=='4'){//this.fdType=='1'||
				on(this.domNode,'click',lang.hitch(this,this._onItemClick));
			}
			var imgContainer = domConstruct.create('div',{className : 'muiChartImgContainer'},this.domNode),
				contentContainer =  domConstruct.create('div',{className : 'muiChartContentContainer'},this.domNode),
				rightContainer = domConstruct.create('div',{className : 'muiRightContainer'},this.domNode);
			
			//头像
			domConstruct.create('img',{className : 'muiAttendPersonImg', src : this.docCreatorImg }, imgContainer);
			//人员+部门
			domConstruct.create('h2',{className : 'muiAttendPerson', innerHTML : this.docCreatorName }, contentContainer);
			domConstruct.create('div',{className : 'muiAttendPersonDept', innerHTML : this.dept ?  this.dept : '' }, contentContainer);
			//
			
			this.fdLateTime = this.fdLateTime || 0;
			this.fdLeftTime = this.fdLeftTime || 0;
			var className = 'muiStatInfo ';
			var info ="";
			if(this.fdType=='0'){
				info = lang.replace(Msg['mui.missed.count'],[this.fdMissedCount]);
				className += "muiMissed";
			}
			if(this.fdType=='1'){
				info = lang.replace(Msg['mui.normal.day'],[this.fdStatusDays]);
				className += "muiStatus";
			}
			if(this.fdType=='2'){
				info = lang.replace(Msg['mui.late.count'],[this.fdLateCount]);
				className += "muiLight";
			}
			if(this.fdType=='3'){
				info = lang.replace(Msg['mui.left.count'],[this.fdLeftCount]);
				className += "muiLight";
			}
			if(this.fdType=='4'){
				info = lang.replace(Msg['mui.outside.count'],[this.fdOutsideCount]);
				className += "muiLight";
			}
			if(this.fdType=='5'){
				info = lang.replace(Msg['mui.absent.day'],[this.fdAbsentDays]);
				className += "muiLight";
			}
			if(this.fdType=='6'){
				info = lang.replace(Msg['mui.trip.day'],[Math.round(10 * this.fdTripDays)/10]);
				className += "muiLight";
			}
			if(this.fdType=='9'){
				if(this.fdOutgoingTime && parseInt(this.fdOutgoingTime) > 0){
					info = Msg['mui.outgoing'] + parseInt(this.fdOutgoingTime) + Msg['mui.hour'];
				}
				className += "muiLight";
			}
			domConstruct.create('div',{className : className , innerHTML : info }, rightContainer);
		},
		_onItemClick : function(){
			var self = this;
			if(window.attendStatItem){
				window.attendStatItem.destroyRecursive();
			}
			var dateObj = registry.byId('mStatList_statDate');
			var nowDate = dateObj.get('value');
			var __attendMainViewTmpl = lang.replace(attendMainViewTmpl,{
				attendMainId : "",
				attendType:"3",
				attendFdType:this.fdType,
				creatorId:this.docCreatorId,
				time:nowDate
			});
			parser.parse(domConstruct.create('div',{ innerHTML:__attendMainViewTmpl,style:'display:none' },query('#content')[0] ,'last'))
			  .then(function(widgetList){
				  array.forEach(widgetList, function(widget, index) {
						if(index == 0){
							self.afterStatListParse(widget);
							window.attendStatItem= widget;
						}
					});
				  self.moveToAttendStatItem();
			  });
		},
		moveToAttendStatItem:function(){
			var mainView=registry.byId('mAttendStatListView');
			var self = this;
			listener.add({callback:function(){
				var opts = {
						transition : 'slide',
						moveTo:mainView.id,
						transitionDir:-1
					};
				self.hideAttendStatItem();
				new TransitionEvent(document.body,  opts ).dispatch();
			}});
		},
		afterStatListParse : function(widget){
			widget.hide();//隐藏
			widget.placeAt(widget.domNode.parentNode,'after');//移除不必要的父节点,保证view的节点与其他view在同一层
		},
		hideAttendStatItem: function(){
			domStyle.set(query('.muiLocationDialog')[0],'display','none');
		},
		startup:function(){
			if(this._started){ return; }
			this.inherited(arguments);
		},
	
		_setLabelAttr: function(text){
			if(text){
				this._set("label", text);
			}
		},
		
		makeUrl:function(){
			//暂时屏蔽入口
//			if(this.fdType=='0' || this.fdType=='2' || this.fdType=='3' || this.fdType=='4'){
//				var dateObj = registry.byId('mStatList_statDate');
//				var statDate = locale.parse(dateObj.get('value')+' 00:00',{selector : 'date',datePattern : muiMsg['mui.date.format.datetime']});
//				this.genAttendRecordList({fdType:this.fdType,docCreatorId:this.docCreatorId,fdMonth:statDate.getTime()});
//				return;
//			}
			if(!this.href){
				return '';
			}
			return this.inherited(arguments);
		},
		
		genAttendRecordList:function(evt){
			var self = this;
			if(!window._attendRecordList){
				parser.parse(domConstruct.create('div',{ innerHTML:templateString,style:'display:none' },query('#content')[0] ,'last'))
				  .then(function(widgetList){
					  array.forEach(widgetList, function(widget, index) {
							if(index == 0){
								self.afterAttendRecordListParse(widget);
								window._attendRecordList = widget;
							}
						});
						self.moveToAttendRecordListView(evt);
				  });
			}else{
				this.moveToAttendRecordListView(evt);
			}
			
		},
		moveToAttendRecordListView : function(evt){
			var opts = {
				transition : 'slide',
				moveTo : window._attendRecordList.id
			};
			new TransitionEvent(document.body,opts).dispatch();
			topic.publish("/mui/attendRecord/list/show",this,evt);
		},
		afterAttendRecordListParse : function(widget){
			widget.hide();//隐藏
			widget.placeAt(widget.domNode.parentNode,'after');//移除不必要的父节点,保证view的节点与其他view在同一层
			this.backView=widget.getShowingView();
			var backBtn = registry.byNode(query('.muiAttendRecordBack')[0]);
			
			backBtn.doBack = lang.hitch(this,function(){
				var opts = {
						transition : 'slide',
						moveTo:this.backView.id,
						transitionDir:-1
					};
				new TransitionEvent(document.body,  opts ).dispatch();
			});
			
		}
	});
	return item;
});