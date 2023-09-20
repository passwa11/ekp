define([
    "dojo/_base/declare",
    "dojo/dom-construct",
    "dojo/dom-class",
	"dojo/dom-style",
	"dojo/dom-attr",
    "dojox/mobile/_ItemBase",
   	"mui/util",
   	"mui/calendar/CalendarUtil",
   	"mui/list/item/_ListLinkItemMixin",
   	"dojo/date",
	"dojo/date/locale" ,
	"mui/i18n/i18n!sys-mobile"
	], function(declare, domConstruct,domClass , domStyle , domAttr , ItemBase , util, cutil,_ListLinkItemMixin,dateUtil,locale,msg) {
	
	var item = declare("mui.calendar.item.CalendarItemMixin", [ItemBase, _ListLinkItemMixin], {
		tag:"li",
		
		baseClass:"muiCalendarListItem",
		
		href : '',
		
		buildRendering:function(){
			
			this.inherited(arguments);
			
			this.domNode = this.containerNode= this.srcNodeRef
				|| domConstruct.create(this.tag,{className:this.baseClass});
			
			var content=domConstruct.create("div",{className:"muiCalendarListContent"},this.containerNode);
			
			//时间轴
			var em=domConstruct.create("em",{className:"arrow_dot"},content);
			domConstruct.create("i",{className:"mui mui-meeting_date"},em);
			
			var format=dojoConfig.DateTime_format;//'yyyy-MM-dd HH:mm'
			if(this.allDay){
				format=dojoConfig.Date_format;//'yyyy-MM-dd'
			}
			var _start=locale.parse(this.start,{selector : 'time',timePattern : format}),
				_end=locale.parse(this.end,{selector : 'time',timePattern : format}),
				_date='';
				
			if(dateUtil.compare(_start,_end,'date') != 0 ){//跨天,显示MM-dd HH:mm ~ {MM-dd HH:mm}
				var tp=this.allDay?'':dojoConfig.Time_format;
				tp='MM-dd '+tp;
				_start=locale.format(_start,{selector : 'time',timePattern : tp });
				_end=locale.format(_end,{selector : 'time',timePattern : tp });
				_date=_start+' ~ '+_end;
			}else{//非跨天,显示HH:mm ~ {HH:mm}
				_start=locale.format(_start,{selector : 'time',timePattern : dojoConfig.Time_format });
				_end=locale.format(_end,{selector : 'time',timePattern : dojoConfig.Time_format });
				_date=_start+' ~ '+_end;
				if(this.allDay){
					_date='全天';
				}
			}
			
			
			var _p=domConstruct.create("p", { className: "date",innerHTML : _date }, content);//时间
			
			var title=domConstruct.create("p", { className: "title"}, content);//标题
			domConstruct.create("a", {innerHTML:this.title}, title);
			
		},
		
		startup:function(){
			if(this._started){ return; }
			this.inherited(arguments);
		},
	
		_setLabelAttr: function(text){
			if(text)
				this._set("label", text);
		},
		
		//url加入当前选中日期参数
		makeUrl:function(){
			var url=this.inherited(arguments),
				parent=this.getParent();
			if(parent){
				var _cd=cutil.formatDate(parent.currentDate);
				url=util.setUrlParameter(url,'currentDate',_cd);
			}
			return url;
		}
		
	});
	return item;
});