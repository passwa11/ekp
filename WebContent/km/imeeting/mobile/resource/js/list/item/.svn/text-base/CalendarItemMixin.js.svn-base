define([
    "dojo/_base/declare",
    "dojo/dom-construct",
    "dojo/dom-class",
	"dojo/dom-style",
	"dojo/dom-attr",
    "dojox/mobile/_ItemBase",
   	"mui/util",
   	"mui/list/item/_ListLinkItemMixin",
	"dojo/date/locale" ,
	"dojo/date",
	"mui/i18n/i18n!km-imeeting:kmImeeting",
	"mui/util"
	], function(declare, domConstruct,domClass , domStyle , domAttr , ItemBase , util, _ListLinkItemMixin,locale,dateUtil,msg,util) {
	
	var item = declare("km.imeeting.list.item.CalendarItemMixin", [ItemBase, _ListLinkItemMixin], {
		tag:"li",
		
		baseClass:"meeting_timeline_item",
		
		href : '',
		
		//主持人
		fdHost:"",
		
		//地点
		fdPlaceId:"",
		
		fdPlaceName:"",
		
		status:"",
		
		buildRendering:function(){
			this.inherited(arguments);
			
			this.domNode = this.containerNode= this.srcNodeRef
				|| domConstruct.create(this.tag,{className:this.baseClass + " meeting_timeline_item_"+this.status});
			
			domConstruct.create("i",{className:"meeting_timeline_item_icon"},this.containerNode);
			
			domConstruct.create("div",{className:"meeting_timeline_item_tail"},this.containerNode);
			
			var content=domConstruct.create("div",{className:"meeting_timeline_item_content"},this.containerNode);
			
			// 状态
			var statusNode = domConstruct.create("div", { 
				className: "muiMeetingStatus " + this.status, 
				innerHTML: util.formatText(this.statusText) 
			}, content);
			
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
				default:
					break;
			}
			
			//标题
			domConstruct.create("div", { className: 'meeting_timeline_subject',innerHTML:this.title }, content);
			
			// 时间
			var _start=locale.parse(this.start,{selector : 'time',timePattern : dojoConfig.DateTime_format }),
				_end=locale.parse(this.end,{selector : 'time',timePattern : dojoConfig.DateTime_format }),
				_format=dojoConfig.Time_format;
			if(dateUtil.compare(_start,_end,'date') != 0 ){//跨天,显示MM-dd HH:mm ~ {MM-dd HH:mm}
				_format= 'MM/dd HH:mm' ;
			}
			
			_start=locale.format(_start,{selector : 'time',timePattern :_format }),
			_end=locale.format(_end,{selector : 'time',timePattern : _format });
			
			var infoNode = domConstruct.create("div", { className: 'meeting_timeline_info'}, content);
			var dateNode = domConstruct.create("span", { className: 'meeting_timeline_date'}, infoNode);
			dateNode.appendChild(domConstruct.create('i', {className:'item_icon item_icon_date'}));
			dateNode.appendChild(domConstruct.toDom(_start));
			dateNode.appendChild(domConstruct.create("i", {innerHTML: "-"}, dateNode));
			dateNode.appendChild(domConstruct.toDom(_end));
			
			//主持人 
			if(this.fdHost) {
				var hostNode = domConstruct.create("span", { className: 'meeting_timeline_person'}, infoNode);
				hostNode.appendChild(domConstruct.create('i', {className:'item_icon item_icon_person'}));
				hostNode.appendChild(domConstruct.toDom(util.formatText(this.fdHost)));
			}
			//地点
			if(this.fdPlaceName) {				
				if(this.fdVicePlacesNames || this.fdOtherVicePlace) {
					var placeNode = domConstruct.create("div", { className: 'meeting_timeline_info'}, content);
					domConstruct.create("i", {className: 'item_icon item_icon_place'}, placeNode);
					domConstruct.create("span", {innerHTML: msg['kmImeetingMain.fdMainPlace'] + '：' + util.formatText(this.fdPlaceName)}, placeNode);
					
					var t = [];
					if(this.fdVicePlacesNames) {
						t.push(this.fdVicePlacesNames);
					}
					if(this.fdOtherVicePlace) {
						t.push(this.fdOtherVicePlace);
					}
					
					var otherPlaceNode = domConstruct.create("div", { className: 'meeting_timeline_info'}, content);
					domConstruct.create("p", {className:'item_other_place',innerHTML: msg['kmImeetingMain.fdVicePlace'] + '：' + util.formatText(t.join(' '))}, otherPlaceNode);
				} else {
					var info = domConstruct.create("p", { className: 'meeting_timeline_info'}, content);
					domConstruct.create("i", {className: 'item_icon item_icon_place'}, info);
					domConstruct.create("span", {innerHTML:util.formatText(this.fdPlaceName)}, info);
				}
			}
			
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