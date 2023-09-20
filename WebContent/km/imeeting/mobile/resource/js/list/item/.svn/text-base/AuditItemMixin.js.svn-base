define([
    'dojo/_base/declare',
    'dojo/_base/config',
    'dojo/dom-construct',
    'dojo/dom-class',
	'dojo/dom-style',
	'dojo/dom-attr',
    'dojox/mobile/_ItemBase',
   	'mui/util',
   	'dojo/on',
   	'mui/dialog/Tip', 
   	'dojo/topic',
   	'dojo/date/locale',
   	"mui/openProxyMixin",
   	"mui/i18n/i18n!km-imeeting:*"
	], function(declare, config, domConstruct, domClass, domStyle, domAttr, ItemBase, util, on, Tip, 
			topic, locale,openProxyMixin,msg) {
	var item = declare('mui.list.item.AuditItemMixin', [ItemBase,openProxyMixin], {
		
		buildRendering:function(){
			this.inherited(arguments);
			if("com.landray.kmss.km.imeeting.model.KmImeetingMain" == this.modelName){
				this._bulidMeetingRendering();
			}else if("com.landray.kmss.km.imeeting.model.KmImeetingSummary" == this.modelName){
				this._bulidSummaryRendering();
			}else if("com.landray.kmss.km.imeeting.model.KmImeetingTopic" == this.modelName){
				this._bulidTopicRendering();
			}else if("com.landray.kmss.km.imeeting.model.KmImeetingBook" == this.modelName){
				this._bulidBookRendering();
			}
		},
		
		_bulidMeetingRendering:function(){
			this.domNode = domConstruct.create('li', {className : 'meeting_cardList_item'}, this.containerNode);
			this.domNode.dojoClick = true;
			if(this.href){
				this.proxyClick(this.domNode, this.href, '_blank');
			}
	      	//标题
			var headNode = domConstruct.create("div", {className: "meeting_cardList_item_head meeting_cardList_item_head_hasBtn"}, this.domNode);
			domConstruct.create("p", {className: "meeting_cardList_subject",innerHTML:this.label}, headNode);
			var statusClass = this._getStatusClass(this.docStatusNew);
			domConstruct.create("span", {className: "meeting_btn " + statusClass,innerHTML:this.docStatus}, headNode);
			
			//主持人
			var footerRNode = domConstruct.create("div", {className: "meeting_cardList_item_footerR"}, this.domNode);
			domConstruct.create("span", {className: "meeting_cardList_author",innerHTML:this.host}, footerRNode);
			//var footerNode = domConstruct.create("div", {className: "meeting_cardList_item_footer"}, this.domNode);
			
			var footerLNode = domConstruct.create("div", {className: "meeting_cardList_item_footerL"}, this.domNode);
			//地点
			domConstruct.create("p", {className: "meeting_cardList_address",innerHTML:this.place}, footerLNode);
			
			//时间
			var date = this._getDate(this.created);
			domConstruct.create("p", {className: "meeting_cardList_date",style:"margin-top:1rem;",innerHTML:date}, this.domNode);
		},
		
		_bulidSummaryRendering:function(){
			this.domNode = domConstruct.create('li', {className : 'meeting_cardList_item'}, this.containerNode);
			this.domNode.dojoClick = true;
			if(this.href){
				this.proxyClick(this.domNode, this.href, '_blank');
			}
	      	//标题
			var headNode = domConstruct.create("div", {className: "meeting_cardList_item_head meeting_cardList_item_head_hasBtn"}, this.domNode);
			domConstruct.create("p", {className: "meeting_cardList_subject",innerHTML:this.label}, headNode);
			var statusClass = this._getStatusClass(this.docStatusNew);
			domConstruct.create("span", {className: "meeting_btn " + statusClass,innerHTML:this.docStatus}, headNode);
			
			//主持人
			var footerRNode = domConstruct.create("div", {className: "meeting_cardList_item_footerR"}, this.domNode);
			domConstruct.create("span", {className: "meeting_cardList_author",innerHTML:this.host}, footerRNode);
			
			var footerLNode = domConstruct.create("div", {className: "meeting_cardList_item_footerL"}, this.domNode);
			//时间
			var date = this._getDate(this.created);
			domConstruct.create("p", {className: "meeting_cardList_date",innerHTML:date}, footerLNode);
			
		},
		
		_bulidTopicRendering:function(){
			this.domNode = domConstruct.create('li', {className : 'meeting_cardList_item'}, this.containerNode);
			this.domNode.dojoClick = true;
			if(this.href){
				this.proxyClick(this.domNode, this.href, '_blank');
			}
	      	//标题
			var headNode = domConstruct.create("div", {className: "meeting_cardList_item_head meeting_cardList_item_head_hasBtn"}, this.domNode);
			domConstruct.create("p", {className: "meeting_cardList_subject",innerHTML:this.label}, headNode);
			var statusClass = this._getStatusClass(this.docStatusNew);
			domConstruct.create("span", {className: "meeting_btn " + statusClass,innerHTML:this.docStatus}, headNode);
			
			//汇报人
			var footerRNode = domConstruct.create("div", {className: "meeting_cardList_item_footerR"}, this.domNode);
			domConstruct.create("span", {className: "meeting_cardList_author",innerHTML:this.fdReporter}, footerRNode);
			
			var footerLNode = domConstruct.create("div", {className: "meeting_cardList_item_footerL"}, this.domNode);
			//创建时间
			domConstruct.create("p", {className: "meeting_cardList_date",innerHTML:this.created}, footerLNode);
			
			
		},
		
		_bulidBookRendering:function(){
			
			this.domNode = domConstruct.create('li', {className : 'meeting_room_cardList_item'}, this.containerNode);
			var headNode = domConstruct.create("div", {className: "meeting_room_cardList_item_head"}, this.domNode);
			var iconNode = domConstruct.create("div", {className: "meeting_room_cardList_item_icon"}, headNode);
			var contentNode = domConstruct.create("div", {className: "meeting_room_cardList_item_content"}, headNode);
			domConstruct.create("p", {className: "meeting_room_cardList_subject",innerHTML:this.fdPlace}, contentNode);
			
			var infoNode2 = domConstruct.create("p", {className: "meeting_room_cardList_info"}, contentNode);
			var spanNode2 = domConstruct.create("span", {}, infoNode2);
			spanNode2.appendChild(domConstruct.create("em", {innerHTML:msg['kmImeeting.res.purpose']+"："}));
			spanNode2.appendChild(domConstruct.toDom(this.fdName));
			
			var infoNode3 = domConstruct.create("p", {className: "meeting_room_cardList_info"}, contentNode);
			var spanNode3 = domConstruct.create("span", {}, infoNode3);
			spanNode3.appendChild(domConstruct.create("em", {innerHTML:msg['kmImeeting.res.time']+"："}));
			var duration = '';
			var holdDate = new Date(this.fdHoldDate.replace(/-/g, '/'));
			var finishDate = new Date(this.fdFinishDate.replace(/-/g, '/'));
			if(holdDate.getFullYear() == finishDate.getFullYear() &&
					holdDate.getMonth() == finishDate.getMonth() &&
						holdDate.getDate() == finishDate.getDate()) {
				
				duration = locale.format(holdDate, {
					formatLength: 'short',
					datePattern: msg['date.format.date'],
					timePattern: msg['date.format.time']
				}) + ' ~ ' + locale.format(finishDate, {
					formatLength: 'short',
					selector: 'time',
					timePattern: msg['date.format.time']
				});
				
			} else {
				duration = locale.format(holdDate, {
					datePattern: msg['date.format.date'],
					timePattern: msg['date.format.time']
				}) + ' ~ ' + locale.format(finishDate, {
					datePattern: msg['date.format.date'],
					timePattern: msg['date.format.time']
				});
				
			}
			if (duration.indexOf(",") > -1) {
				duration = duration.replace(/,/, " ");
			}
			spanNode3.appendChild(domConstruct.toDom(duration));
			
			var infoNode1 = domConstruct.create("p", {className: "meeting_room_cardList_info"}, contentNode);
			var spanNode1 = domConstruct.create("span", {}, infoNode1);
			spanNode1.appendChild(domConstruct.create("em", {innerHTML:msg['kmImeeting.res.creator']+"："}));
			spanNode1.appendChild(domConstruct.toDom(this.docCreator));
			
			if(!this.fdHasExam) {
				var footerNode = domConstruct.create("div", {className: "meeting_room_cardList_item_footer"}, this.domNode);
				var rejectBtn = domConstruct.create("span", {className: "meeting_btn meeting_btn_reject",innerHTML:msg['kmImeeting.res.btn.reject']}, footerNode);
				this.connect(rejectBtn, 'onclick', '_onReject');
				var agreeBtn = domConstruct.create("span", {className: "meeting_btn meeting_btn_primary",innerHTML:msg['kmImeeting.res.btn.agree']}, footerNode);
				this.connect(agreeBtn, 'onclick', '_onAgree');
			}
			
		},
		
		_getStatusClass:function(docStatus){
			if("00" == docStatus){
				return "meeting_btn_discard";
			}else if("11" == docStatus){
				return "meeting_btn_refuse";
			}else if("20" == docStatus){
				return "meeting_btn_primary";
			}else if("30" == docStatus){
				return "meeting_btn_publish";
			}else if("41" == docStatus){
				return "meeting_btn_publish";
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

		startup:function(){
			if(this._started){ return; }
			this.inherited(arguments);
		},
		
		_setLabelAttr: function(text){
			if(text)
				this._set("label", text);
		},
		
		_onAgree: function(e) {
			var self = this;
			if(!self._clickHandler) {
				self._clickHandler = setTimeout(function() {
					topic.publish('km/imeeting/agreebook', self);
					self._clickHandler = null;
				});
			}
		},
		
		_onReject: function(e) {
			var self = this;
			if(!self._clickHandler) {
				self._clickHandler = setTimeout(function() {
					topic.publish('km/imeeting/rejectbook', self);
					self._clickHandler = null;
				});
			}
		}

	});
	
	return item;
});