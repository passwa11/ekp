define([
    "dojo/_base/declare",
    "dojo/dom-construct",
    "dojo/dom-class",
	"dojo/dom-style",
	"dojo/dom-attr",
    "dojox/mobile/_ItemBase",
   	"mui/util",
   	"dojo/on",
   	"mui/dialog/Tip", 
   	"mui/list/item/_ListLinkItemMixin",
   	"dojo/date/locale",
   	"dojo/date",
   	"mui/i18n/i18n!km-imeeting:kmImeeting"
	], function(declare, domConstruct,domClass , domStyle , domAttr , ItemBase , util,on,Tip, _ListLinkItemMixin,locale,dateUtil,msg) {
	var item = declare("mui.list.item.CardItemMixin", [ItemBase, _ListLinkItemMixin], {
		
		buildRendering:function(){
			this.inherited(arguments);
			this.domNode = domConstruct.create('div', {className : 'meeting_dateList_item meeting_dateList_item_'+this.status}, this.containerNode);
			this.buildInternalRender();
		},
		buildInternalRender : function() {
			
			//日期
	        var dataNode = domConstruct.create('div', {className:'meeting_dateList_item_date' }, this.domNode);
	        domConstruct.create('h4', {className:'meeting_dateList_item_date_day',innerHTML:this.day }, dataNode);
	        var weekDayNode = domConstruct.create('p', {className:'meeting_dateList_item_date_week'}, dataNode);
	        if(this.weekday){
	        	var weekDay = this._getWeekDay(this.weekday);
		        weekDayNode.innerHTML = weekDay;
	        }else{
	        	weekDayNode.innerHTML = msg['kmImeeting.interDayMeeting'];
	        }
	        
	        
	        //内容
	        var contentNode = domConstruct.create('div', {className:'meeting_dateList_item_content' }, this.domNode);
	        
	        // 状态
	        var statusNode = domConstruct.create('span', {className:'muiMeetingStatus ' + this.status,innerHTML:util.formatText(this.statusText) }, contentNode);
			
			switch(this.fdHasExam) {
				case 'wait': 
					domAttr.set(statusNode, 'className', 'muiMeetingStatus wait');
					console.log(msg['kmImeetingCalendar.res.wait']);
					statusNode.innerHTML = msg['kmImeeting.res.wait'];
					break;
				case 'true':
					break;
				case 'false':
					domAttr.set(statusNode, 'className', 'muiMeetingStatus reject');
					statusNode.innerHTML = msg['kmImeeting.res.false'];
					break;
				default:
					break;
			}
			
			switch(this.docStatus){
				case '20':
					domAttr.set(statusNode, 'className', 'muiMeetingStatus examine');
					statusNode.innerHTML = msg['kmImeeting.status.append'];
					break;
				case '10':
					domAttr.set(statusNode, 'className', 'muiMeetingStatus draft');
					statusNode.innerHTML = msg['kmImeeting.status.draft'];
					break;
				case '11':
					domAttr.set(statusNode, 'className', 'muiMeetingStatus refuse');
					statusNode.innerHTML = msg['kmImeeting.status.reject'];
					break;
				case '00':
					domAttr.set(statusNode, 'className', 'muiMeetingStatus discard');
					statusNode.innerHTML = msg['kmImeeting.status.abandom'];
					break;
				case '41':
					domAttr.set(statusNode, 'className', 'muiMeetingStatus cancel');
					statusNode.innerHTML = msg['kmImeeting.status.cancel'];
					break;
				default:
					break;
			}
			
			
	        //标题
	        domConstruct.create('p', {className:'meeting_dateList_item_subject',innerHTML:this.title}, contentNode);
	        var tiemNode = domConstruct.create('p', {className:'meeting_dateList_item_info'}, contentNode);
			
	        var _start=locale.parse(this.start,{selector : 'time',timePattern : dojoConfig.DateTime_format }),
				_end=locale.parse(this.end,{selector : 'time',timePattern : dojoConfig.DateTime_format }),
				_format=dojoConfig.Time_format;
			if(dateUtil.compare(_start,_end,'date') != 0 ){//跨天,显示MM-dd HH:mm ~ {MM-dd HH:mm}
				_format= 'MM/dd HH:mm' ;
			}
			_start=locale.format(_start,{selector : 'time',timePattern :_format }),
			_end=locale.format(_end,{selector : 'time',timePattern : _format });
			//时间
			domConstruct.create('i', {className:"item_icon item_icon_date"}, tiemNode);
	        domConstruct.create('span', {className:"meeting_dateList_item_info_time",innerHTML:_start + ' - ' + _end}, tiemNode);
	        //历时
	        //domConstruct.create('span', {className:'meeting_dateList_item_info_dura',innerHTML:this.minDura + "min"}, tiemNode);
	        
	        // 历时，人数，主持人
	        var infoNode = domConstruct.create('p', {className:'meeting_dateList_item_info'}, contentNode);
	       
	        //参加人数
	        var personsNode = domConstruct.create('span', {}, infoNode);
	        domConstruct.create('i', {className:'item_icon item_icon_person'}, personsNode);
	        personsNode.appendChild(domConstruct.toDom(this.attendPersonSum + msg['kmImeeting.person']));
	        //主持人
	        var authorNode = domConstruct.create('span', {}, infoNode);
	        domConstruct.create('i', {className:'item_icon item_icon_author'}, authorNode);
	        authorNode.appendChild(domConstruct.toDom(this.fdHost));
	        //地点 
			if(this.fdPlaceName) {				
				if(this.fdVicePlacesNames || this.fdOtherVicePlace) {
					var placeNode = domConstruct.create("div", { className: 'meeting_dateList_item_info'}, contentNode);
					domConstruct.create("i", {className: 'item_icon item_icon_place'}, placeNode);
					domConstruct.create("span", {innerHTML: '主会场：' + util.formatText(this.fdPlaceName)}, placeNode);
					
					var t = [];
					if(this.fdVicePlacesNames) {
						t.push(this.fdVicePlacesNames);
					}
					if(this.fdOtherVicePlace) {
						t.push(this.fdOtherVicePlace);
					}
					
					var otherPlaceNode = domConstruct.create("div", { className: 'meeting_dateList_item_info'}, contentNode);
					domConstruct.create("p", {className:'item_other_place',innerHTML: '分会场：' + util.formatText(t.join(' '))}, otherPlaceNode);
				} else {
					var info = domConstruct.create("p", { className: 'meeting_dateList_item_info'}, contentNode);
					domConstruct.create("i", {className: 'item_icon item_icon_place'}, info);
					domConstruct.create("span", {innerHTML:util.formatText(this.fdPlaceName)}, info);
				}
			}
			
		},
		
		_getWeekDay:function(weekDay){
			switch(weekDay){
				case 1:
					return msg['kmImeeting.weekDay.0'];
				case 2:
					return msg['kmImeeting.weekDay.1'];
				case 3:
					return msg['kmImeeting.weekDay.2'];
				case 4:
					return msg['kmImeeting.weekDay.3'];
				case 5:
					return msg['kmImeeting.weekDay.4'];
				case 6:
					return msg['kmImeeting.weekDay.5'];
				case 7:
					return msg['kmImeeting.weekDay.6'];
			}
		},
		
		makeLockLinkTip:function(linkNode){
			this.href='javascript:void(0);';
			on(linkNode,'click',function(evt){
				Tip.tip({icon:'mui mui-warn', text:'暂不支持移动访问'});
			});
		},
		
		startup:function(){
			if(this._started){ return; }
			this.inherited(arguments);
		},
	
		_setLabelAttr: function(text){
			if(text)
				this._set("label", text);
		}
	});
	return item;
});