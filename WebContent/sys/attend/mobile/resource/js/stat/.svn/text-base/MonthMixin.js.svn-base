define(["dojo/_base/declare","dojo/dom-style","dojo/query","mui/i18n/i18n!sys-attend:mui","mui/i18n/i18n!sys-mobile",
        "dojo/date/locale","dojo/dom-construct","mui/dialog/Dialog","mui/i18n/i18n!sys-mobile:mui"],
		function(declare,domStyle,query,Msg,muiMsg,locale,domConstruct,Dialog,msgMobile){
	var claz = declare('sys.attend.mobile.MonthMixin',[],{
		
		extType :'month',

		title : Msg['mui.month.select'],
		
		// 弹出时间选择框
		openDateTime : function(evt) {
			var self = this;
			this.inherited(arguments);
			if(this.extType=='month'){
				this.defer(function(){
					var dateNode = query('.mblSpinWheelSlot',this.dialog);
					if(dateNode){
						domStyle.set(dateNode[2],'visibility','hidden');
					}
				},300);
			}
		},
		
		hideDaySlot:function(){
			if(this.extType=='month'){
				var slots = query('.mblSpinWheelSlot',this.dialogContentDiv)
				if(slots.length==3){
					domStyle.set(slots[2],'opacity',0);
					domStyle.set(slots[2],'z-index','0');
					domStyle.set(slots[0],'width','50%');
					domStyle.set(slots[1],'width','50%');
				}
			}
		},
		destroyDialog:function(){
			this.inherited(arguments);
			if(this.extType=='month'){
				var statDate = locale.parse(this.get('value')+' 00:00',{selector : 'date',datePattern : dojoConfig.DateTime_format});
				var monthTxt = locale.format(statDate,{selector : 'date',datePattern : Msg['mui.date.format.month'] });
				query('.muiSelInput',this.domNode)[0].innerHTML=monthTxt;
			}
		}
	});
	return claz;
});