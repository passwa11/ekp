define([
    "dojo/_base/declare",
    "dojo/dom-construct",
    "dojo/dom-class",
	"dojo/dom-style",
	"dojo/dom-attr",
    "dojox/mobile/_ItemBase",
   	"mui/util",
   	"mui/list/item/_ListLinkItemMixin","mui/i18n/i18n!sys-attend" 	
	], function(declare, domConstruct,domClass , domStyle , domAttr , ItemBase , util, _ListLinkItemMixin,Msg) {
	
	var item = declare("sys.attend.AttendChartItemMixin", [ItemBase, _ListLinkItemMixin], {
		
		tag:"li",
		
		baseClass:"muiAttendChartListItem muiListItem",
		
		href:'',
		
		buildRendering:function(){
			this.inherited(arguments);
			this.domNode = this.containerNode= this.srcNodeRef
				|| domConstruct.create(this.tag,{className:this.baseClass});
			
			var numContainer = domConstruct.create('div',{className : 'muiChartNumContainer'},this.domNode),
				imgContainer = domConstruct.create('div',{className : 'muiChartImgContainer'},this.domNode),
				contentContainer =  domConstruct.create('div',{className : 'muiChartContentContainer'},this.domNode),
				tagContainer = domConstruct.create('div',{className : 'sysAttendListTagContainer'},this.domNode);
			//序号
			domConstruct.create('span',{className : 'muiChartNum', innerHTML : this.index }, numContainer);
			//头像
			domConstruct.create('img',{className : 'sysAttendListImg', src : this.docCreatorImg }, imgContainer);
			//人员+时间
			domConstruct.create('h2',{className : 'sysAttendListTitle', innerHTML : this.docCreatorName }, contentContainer);
			domConstruct.create('div',{className : 'sysAttendListTime', innerHTML : this.dept ?  this.dept : '' }, contentContainer);
			//签到状态
			var className = 'sysAttendListStatus ';
			var fdTotalTime = this.fdTotalTime || 0 ;
			var infoTxt = "";
			if(fdTotalTime>=60){
				var h = parseInt(fdTotalTime/60);
				var m = fdTotalTime%60;
				infoTxt = h + Msg['mui.hour'] + (m>0 ? m+Msg['mui.minute']:"");
			}else{
				infoTxt = fdTotalTime + Msg['mui.minute'];
			}
			domConstruct.create('div',{className : className , innerHTML : infoTxt }, tagContainer);
			
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