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
   	"mui/i18n/i18n!km-imeeting:date"
	], function(declare, domConstruct,domClass , domStyle , domAttr , ItemBase , util,on,Tip, _ListLinkItemMixin, 
			locale,msg) {
	var item = declare("mui.list.item.SummaryCardItemMixin", [ItemBase, _ListLinkItemMixin], {
		
		buildRendering:function(){
			this.inherited(arguments);
			this.domNode = domConstruct.create('li', {className : 'meeting_cardList_item'}, this.containerNode);
			this.buildInternalRender();
		},
		
		buildInternalRender : function() {
			//标题
			var headNode = domConstruct.create("div", {className: "meeting_cardList_item_head"}, this.domNode);
			domConstruct.create("p", {className: "meeting_cardList_subject",innerHTML:this.label}, headNode);
			
			//var footerNode = domConstruct.create("div", {className: "meeting_cardList_item_footer"}, this.domNode);
			
			//主持人
			var footerRNode = domConstruct.create("div", {className: "meeting_cardList_item_footerR"}, this.domNode);
			domConstruct.create("span", {className: "meeting_cardList_author",innerHTML:this.host}, footerRNode);
			
			//时间
			var footerLNode = domConstruct.create("div", {className: "meeting_cardList_item_footerL"}, this.domNode);
			var date = this._getDate(this.created);
			domConstruct.create("p", {innerHTML:date}, footerLNode);
			
			
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