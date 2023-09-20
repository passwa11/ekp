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
	"mui/i18n/i18n!sys-mobile",
	"mui/i18n/i18n!km-calendar:kmCalendarMain.allDay"
	], function(declare, domConstruct,domClass , domStyle , domAttr , ItemBase , util, cutil,_ListLinkItemMixin,dateUtil,locale,msg,calendarMsg) {
	
	var item = declare("km.imeeting.list.item.CalendarItemMixin", [ItemBase, _ListLinkItemMixin], {
		tag : "li",
		
		baseClass : "",
		hrefTarget : '_top',
		href : '',
		
		buildRendering:function(){
			
			this.inherited(arguments);
			
			var dateNow = new Date();
			
			this.domNode = this.containerNode= this.srcNodeRef || domConstruct.create(this.tag,{className:this.baseClass});

			var format = dojoConfig.DateTime_format;//'yyyy-MM-dd HH:mm'
			
			if(this.allDay){
				format = dojoConfig.Date_format;//'yyyy-MM-dd'
			}
			var _start = locale.parse(this.start, {
				selector : 'time',
				timePattern : format
			});
			var _end = locale.parse(this.end,{
				selector : 'time',
				timePattern : format
			});
			var _date='';
			
			if(dateUtil.compare(_start, _end, 'date') != 0 ){//跨天,显示MM-dd HH:mm ~ {MM-dd HH:mm}
				var tp = this.allDay ? '' : dojoConfig.Time_format;
				tp = 'MM/dd ' + tp;
				_start = locale.format(_start, {
					selector : 'time',
					timePattern : tp 
				});
				_end = locale.format(_end, {
					selector : 'time',
					timePattern : tp 
				});
				_date = _start + ' - ' + _end;
			} else {
				//非跨天,显示HH:mm ~ {HH:mm}
				_start = locale.format(_start, {
					selector : 'time',
					timePattern : dojoConfig.Time_format 
				});
				_end = locale.format(_end, {
					selector : 'time',
					timePattern : dojoConfig.Time_format
				});
				_date = _start + ' - ' + _end;
				if(this.allDay){
					_date = calendarMsg['kmCalendarMain.allDay'];
				}
			}
			
			if(dateNow > new Date(this.end.replace(/-/g,'/'))) {
				domClass.add(this.domNode, 'outdate');
			}
			//时间
			var _p = domConstruct.create("span", {
				innerHTML : _date ,
				className : 'muiFontSizeL'
			}, this.domNode);
			
			//标签
			var tab = domConstruct.create("div", {
				className: "mui_ekp_portal_date_reader classification muiFontSizeS"
			}, this.domNode);
			if(this.labelId){
				if(this.labelId==='myGroupEvent'){
					color = 'rgba(70, 214, 219, 0.2)';
					var title = '群组日程';
				}else {
					color = this.color.replace('rgb', 'rgba').replace(')', ', 0.2)');
					var title = this.labelName;
				}
			}else if(this.type === 'note'){
				color = 'rgba(95, 183, 193, 0.2)';
				var title = '我的笔记';
			}else{
				if(this.isGroup && this.isGroup == true) {
					color = 'rgba(70, 214, 219, 0.2)';
					var title = '群组日程';
				} else {
					color = 'rgba(193, 156, 83, 0.2)';
					var title = '我的日历';
				}
			}
			domStyle.set(tab, 'background-color', color);
			domStyle.set(tab, 'color', this.color);
			tab.innerHTML = title;
			
			//标题
			var domTitle= domConstruct.create("p", {
				innerHTML : util.formatText(this.title),
				className : 'muiFontSizeM'
			}, this.domNode);
			if(this.isPrivate){
				domConstruct.create("span", {
					className : 'icon fontmuis muis-lock'
				}, domTitle,"first");
			}
			if(!this.href){
				this.href='/km/calendar/km_calendar_main/kmCalendarMain.do?method=view&fdId='+this.id;
			}
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
		makeUrl : function(){
			var url = this.inherited(arguments),
			parent = this.getParent();
			if(parent){
				var _cd = cutil.formatDate(parent.currentDate);
				url = util.setUrlParameter(url,'currentDate',_cd);
			}
			return url;
		}
	});
	return item;
});