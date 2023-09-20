define([
    "dojo/_base/declare","dojo/_base/lang",
    "dojo/dom-construct",
    "dojo/dom-class",
	"dojo/dom-style",
	"dojo/dom-attr",
    "dojox/mobile/_ItemBase",
   	"mui/util",
   	"mui/list/item/_ListLinkItemMixin","mui/i18n/i18n!sys-attend",
	"dojo/on","dojo/touch",
   	"dojox/mobile/TransitionEvent",
	"dojo/text!sys/attend/mobile/stat/attendMainView.tmpl",
	"dojo/_base/array",'dojo/parser',"mui/history/listener",
   	"dojo/query",
   	'dijit/registry',"dojo/topic"
	], function(declare,lang, domConstruct,domClass , domStyle , domAttr , ItemBase , util, _ListLinkItemMixin,Msg,
			on,touch,TransitionEvent,attendMainViewTmpl,array,parser,listener,query,registry,topic) {
	
	var item = declare("sys.attend.AttendStatItemMixin", [ItemBase, _ListLinkItemMixin], {
		
		tag:"li",
		
		baseClass:"muiAttendChartListItem muiListItem",
		
		href:'',
		
		buildRendering:function(){
			this.inherited(arguments);
			this.domNode = this.containerNode= this.srcNodeRef
				|| domConstruct.create(this.tag,{className:this.baseClass});
			this.domNode.dojoClick = true;
			if(this.fdType=='2'||this.fdType=='3'||this.fdType=='4'){
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
				info = Msg['mui.fdStatus.unSign'];
				className += "muiMissed";
			}
			if(this.fdType=='1'){
				info = Msg['mui.fdStatus.ok'];
				className += "muiStatus";
			}
			if(this.fdType=='2'){
				if(this.fdLateTime>=60){
					var hour = parseInt(this.fdLateTime/60);
					var mins = this.fdLateTime%60;
					info = hour + Msg['mui.hour'] + (mins>0 ? mins+Msg['mui.minute']:"");
				}else{
					info = this.fdLateTime + Msg['mui.minute'];
				}
				info = lang.replace(Msg['mui.late.hour'],[info]);
				className += "muiLight";
			}
			if(this.fdType=='3'){
				if(this.fdLeftTime>=60){
					var hour = parseInt(this.fdLeftTime/60);
					var mins = this.fdLeftTime%60;
					info = hour + Msg['mui.hour'] + (mins>0 ? mins+Msg['mui.minute']:"");
				}else{
					info = this.fdLeftTime + Msg['mui.minute'];
				}
				info = lang.replace(Msg['mui.left.hour'],[info]);
				className += "muiLight";
			}
			if(this.fdType=='4'){
				info = Msg['mui.outside'];
				className += "muiLight";
			}
			if(this.fdType=='5'){
				info = Msg['mui.absent'];
				className += "muiLight";
			}
			if(this.fdType=='8'){
				if(this.fdOverTime>=60){
					var hour = parseInt(this.fdOverTime/60);
					var mins = this.fdOverTime%60;
					info = hour + Msg['mui.hour'] + (mins>0 ? mins+Msg['mui.minute']:"");
				}else{
					info = this.fdOverTime + Msg['mui.minute'];
				}
				info = lang.replace(Msg['mui.overtime.hour'],[info]);
				className += "muiStatus";
			}
			if(this.fdType=='9'){
				if(this.fdOutgoingTime && parseInt(this.fdOutgoingTime) > 0){
					info = Msg['mui.outgoing'] + parseInt(this.fdOutgoingTime) + Msg['mui.hour'];
				}
				className += "muiStatus";
			}
			domConstruct.create('div',{className : className , innerHTML : info }, rightContainer);
			
		},
		_onItemClick : function(){
			var self = this;
			if(window.attendStatItem){
				window.attendStatItem.destroyRecursive();
			}
			var dateObj = registry.byId('statList_statDate');
			var nowDate = dateObj.get('value');
			var __attendMainViewTmpl = lang.replace(attendMainViewTmpl,{
				attendMainId : "",
				attendType:"2",
				attendFdType:this.fdType,
				creatorId:this.docCreatorId,
				time:nowDate
			});
			parser.parse(domConstruct.create('div',{ innerHTML:__attendMainViewTmpl,style:'display:none' },query('#content')[0] ,'last'))
			  .then(function(widgetList){
				  array.forEach(widgetList, function(widget, index) {
						if(index == 0){
							self.afterStatListParse(widget);
							window.attendStatItem = widget;
						}
					});
					self.moveToAttendStatItem();
					
			  });
		},
		moveToAttendStatItem:function(){
			var mainView=registry.byId('attendStatListView');
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
			if(!this.href){
				return '';
			}
			return this.inherited(arguments);
		}
	});
	return item;
});