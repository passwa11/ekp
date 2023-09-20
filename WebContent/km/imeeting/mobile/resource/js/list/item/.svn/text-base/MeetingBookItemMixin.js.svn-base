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
   	'dojo/date/locale'
	], function(declare, config, domCtr, domClass, domStyle, domAttr, ItemBase, util, on, Tip, 
			topic, locale) {
	var item = declare('mui.list.item.MeetingBookItemMixin', [ItemBase], {
		
		tag:'li',
		
		baseClass:'muiCardItem muiListItem muiMeetingBookItem',

		buildRendering:function(){
			
			this.domNode = domCtr.create(this.tag, {className : this.baseClass}, this.containerNode);
			this.inherited(arguments);
			
			var infoNode = domCtr.create('div', {
				className: 'info'
			}, this.domNode);
			
			this.connect(infoNode, 'onclick', '_onClick');
			
			var infoLeftNode = domCtr.create('div', {
				className: 'infoLeft'
			}, infoNode);
			
			var imgNode = domCtr.create('div', {
				className: 'img'
			}, infoLeftNode);
			if(this.placeImg) {
				domStyle.set(imgNode, 'background-image', 'url(' + config.baseUrl + this.placeImg + ')');
			}
			
			var infoRightNode = domCtr.create('div', {
				className: 'infoRight'
			}, infoNode);
			
			domCtr.create('div', {
				className: 'title',
				innerHTML: this.fdPlace || ''
			}, infoRightNode);
			
			var detailNode = domCtr.create('ul', {
				className: 'detail'
			}, infoRightNode);
			
			domCtr.create('li', {
				className: 'purpose',
				innerHTML: '用途：' + (this.fdName || '')
			}, detailNode);
			
			
			var holdDate = new Date(this.fdHoldDate.replace(/-/g, '/'));
			var finishDate = new Date(this.fdFinishDate.replace(/-/g, '/'));
			
			var duration = '';
			
			if(holdDate.getFullYear() == finishDate.getFullYear() &&
					holdDate.getMonth() == finishDate.getMonth() &&
						holdDate.getDate() == finishDate.getDate()) {
				
				duration = locale.format(holdDate, {
					formatLength: 'short',
					datePattern: 'yyyy-MM-dd',
					timePattern: 'HH:mm'
				}) + ' ~ ' + locale.format(finishDate, {
					formatLength: 'short',
					selector: 'time',
					timePattern: 'HH:mm'
				});
				
			} else {
				//duration = this.fdHoldDate + ' ~ ' + this.fdFinishDate;
				
				duration = locale.format(holdDate, {
					datePattern: 'MM-dd',
					timePattern: 'HH:mm'
				}) + ' ~ ' + locale.format(finishDate, {
					datePattern: 'MM-dd',
					timePattern: 'HH:mm'
				});
				
			}
				
			domCtr.create('li', {
				className: 'date',
				innerHTML: '时间：' + (duration || '')
			}, detailNode);
			domCtr.create('li', {
				className: 'proposer',
				innerHTML: '申请人：' + (this.docCreator || '')
			}, detailNode);
			
			if(!this.fdHasExam) {
				var btnsNode = domCtr.create('div', {
					className: 'btns'
				}, this.domNode);
				
				var agreeBtn = domCtr.create('div', {
					className: 'btn-agree',
					innerHTML: '通过'
				}, btnsNode);
				
				this.connect(agreeBtn, 'onclick', '_onAgree');
				
				var rejectBtn = domCtr.create('div', {
					className: 'btn-reject',
					innerHTML: '驳回'
				}, btnsNode);
				this.connect(rejectBtn, 'onclick', '_onReject');
			
			} 
			
//			if(this.fdHasExam == 'true') {
//				domCtr.create('div', {
//					className: 'infoSign',
//					'data-exam': this.fdHasExam
//				}, infoNode);
//			} else if(this.fdHasExam == 'false') {
//				domCtr.create('div', {
//					className: 'infoSign',
//					'data-exam': this.fdHasExam
//				}, infoNode);
//			}
			
		},

		startup:function(){
			if(this._started){ return; }
			this.inherited(arguments);
		},
		
		_setLabelAttr: function(text){
			if(text)
				this._set("label", text);
		},
		
		_onClick: function(e) {
			topic.publish('km/imeeting/clickbook', this);
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