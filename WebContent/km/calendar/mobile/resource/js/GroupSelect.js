define(['dojo/_base/declare','dijit/_WidgetBase','mui/form/_StoreFormMixin','dojox/mobile/Tooltip', 'dojo/on', 'dojo/dom-style', 'mui/dialog/Dialog',
        'dojo/dom-construct','dojo/dom-class','dojo/dom-attr','dojo/query','mui/util','dojo/dom-class', 'mui/i18n/i18n!km-calendar:table.kmCalendarRequestAuth'],
		function(declare,WidgetBase,_StoreFormMixin,Tooltip,on,domStyle,Dialog,domConstruct,domClass,domAttr,query,util,domClass, msg){
	
	return declare('km.calendar.mobile.resource.js.GroupSelect',[WidgetBase,_StoreFormMixin],{
		
		prefix:'_GroupSelectItem_',
		
		openUrl:'',
		
		type:'', 
		
		buildRendering : function() {
			this.inherited(arguments);
			
			var muiCalendarGroupBox = domConstruct.create('div', {
				className : 'muiCalendarGroupBox'
			}, this.domNode);
			
			var muiCalendarGroupLeft = domConstruct.create('div', {
				className : 'muiCalendarGroupLeft'
			}, muiCalendarGroupBox);
			
			this.connect(muiCalendarGroupLeft, 'touchend', 'onClick');
			
			var muiCalendarGroupShare = domConstruct.create('div', {
				className : 'muiCalendarGroupShare'
			}, muiCalendarGroupLeft);
			
			this.muiCalendarGroupShareTitle = domConstruct.create('span', null, muiCalendarGroupShare);
			
			domConstruct.create('i', {
				className : 'fontmuis muis-spread'
			}, muiCalendarGroupShare);
			
			if(this.isShowAuthBtn) {
				var muiCalendarGroupRight = domConstruct.create('div', {
					className : 'muiCalendarGroupRight',
					innerHTML : msg['table.kmCalendarRequestAuth']
				}, muiCalendarGroupBox);
				this.connect(muiCalendarGroupRight, 'touchend', function(){
					window.requestauth();
				});
			}
		},
		
		buildMask : function() {

			this.mask = domConstruct.create('div', {
				className : 'muiCalendarOpenerMask'
			}, query('body')[0]);

			this.connect(this.mask, 'onclick', 'openSelectDom');
		},
		
		clickNode : function(data) {

			var url = '';
			
			if(data.type == 'group') {
				
				url = util.setUrlParameter(this.openUrl, 'groupId', data.id);
				
			} else if(data.type == 'personGroup') {
				
				url = util.setUrlParameter(this.openUrl, 'personGroupId', data.id);
			}
			
			if(url)
				window.location = url;
			
		},
		
		onClick : function () {
			
			var selectContent = domConstruct.create('ul', {
				className : 'muiCalendarContent'
			}, this.domNode);
			
			var self = this;
			
			for(var i = 0; i < self.values.length; i++){
				
				var li = domConstruct.create('li', {
					className : self.values[i].id == self.value ? 'selected' : '',
					innerHTML : self.values[i].name
				}, selectContent);
				
				var id = self.values[i].id;
				var type = 'group';
				if(this.type)
					type = self.type;
				
				on(li, 'click', function(type, id){
					return function(){
						self.clickNode({
							type : type,
							id : id
						});
					}
				}(type, id))
			}
			Dialog.element({
				element : selectContent,
				scrollable : false,
				position : 'bottom'
			});
			
			// 不知道公共组件最小高度干啥的，硬去掉算了
			domStyle.set(selectContent.parentNode.parentNode, 'min-height', 0);
		},
		
		generateList : function(items) {
			
			this.formatValues(items);
			
			this.set('value', this.value);
			
		},
		
		// 格式化数据
		formatValues : function(values) {
			
			this.values=values;
		},
		
		_setValueAttr:function(value){
			
			this.muiCalendarGroupShareTitle.innerHTML = this.getTextByValue(value);
		},
		
		getTextByValue:function(value){

			var text = '';
			
			if (value == undefined)
				return text;
			
			for(var i=0;i<this.values.length;i++){
				
				if(value==this.values[i].id){
					
					text=this.values[i].name;
					
					break;
				}
				
			}
			if (text.length>10){
				text = text.substring(0,9)+'...';
			}
			return text;
		},
	});
});