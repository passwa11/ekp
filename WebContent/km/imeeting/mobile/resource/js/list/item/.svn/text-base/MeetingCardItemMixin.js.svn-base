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
   	'dojo/date/locale',
   	"mui/i18n/i18n!km-imeeting:*",
   	"mui/list/item/_ListLinkItemMixin"
	], function(declare, domConstruct,domClass , domStyle , domAttr , ItemBase , util,on,Tip,locale,msg, _ListLinkItemMixin) {
	var item = declare("mui.list.item.CardItemMixin", [ItemBase, _ListLinkItemMixin], {

		buildRendering:function(){
			this.inherited(arguments);
			this.domNode = domConstruct.create('li', {className : 'meeting_cardList_item'}, this.containerNode);
			this.buildInternalRender();
		},
		buildInternalRender : function() {
	      	//标题
			var headNode = domConstruct.create("div", {className: "meeting_cardList_item_head meeting_cardList_item_head_hasBtn"}, this.domNode);
			domConstruct.create("p", {className: "meeting_cardList_subject",innerHTML:this.label}, headNode);
			//domConstruct.create("span", {className: "meeting_btn " + statusClass,innerHTML:this.statusText}, headNode);
			
			var statusNode = domConstruct.create("span", { 
				className: "muiMeetingStatus " + this.statusNew,
				style:"white-space: nowrap;",
				innerHTML: util.formatText(this.statusText) 
			}, headNode);
			
			switch(this.docStatusNew){
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
			
			//var footerNode = domConstruct.create("div", {className: "meeting_cardList_item_footer"}, this.domNode);
			//主持人
			var footerRNode = domConstruct.create("div", {className: "meeting_cardList_item_footerR"}, this.domNode);
			domConstruct.create("span", {className: "meeting_cardList_author",innerHTML:this.host}, footerRNode);
			
			var footerLNode = domConstruct.create("div", {className: "meeting_cardList_item_footerL"}, this.domNode);
			//地点
			if(this.place){
				domConstruct.create("span", {className: "meeting_cardList_address",innerHTML:this.place}, footerLNode);
				//时间
				var date = this._getDate(this.created);
				domConstruct.create("p", {className: "meeting_cardList_date",style:"margin-top:0.5rem;",innerHTML:date}, this.domNode);
			}else{
				//时间
				var date = this._getDate(this.created);
				domConstruct.create("p", {className: "meeting_cardList_date",innerHTML:date}, footerLNode);
			}
			
		},
		
		_getDate:function(created){
			var t = created.split('~');
			for(var i = 0; i < t.length; i++) {
				t[i] = t[i].trim();
			}
			var res = '';
			var start = new Date(t[0].replace(/\-/g, '/'));
			var end = new Date(t[1].replace(/\-/g, '/'));
			
			var dateFormatter = {
				selector: 'date',
				datePattern: msg['date.format.date']
			};
			
			var timeFormatter = {
				selector: 'time',
				timePattern: msg['date.format.time']	
			};
			
			var _dateFormatter = {
				datePattern: msg['date.format.date'],
				timePattern: msg['date.format.time']
			};
			
			var isSameDate = start.getDate() == end.getDate();
			var isSameMonth = start.getMonth() == end.getMonth();
			var isSameYear = start.getFullYear() == end.getFullYear();
			
			if(isSameDate && isSameMonth && isSameYear) {
				res = locale.format(start, dateFormatter) + ' ' + 
					locale.format(start, timeFormatter) + ' ~ ' + locale.format(end, timeFormatter);
			} else if(isSameYear && (!isSameDate || !isSameMonth)){
				res = locale.format(start, _dateFormatter) + ' ~ ' + locale.format(end, _dateFormatter);
			} else {
				res = t[0] + ' ~ ' + t[1];
			}
			return res;
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