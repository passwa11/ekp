define(["dojo/_base/declare",
		"dijit/_WidgetBase",
		"dojox/mobile/Container",
		"dojo/dom-construct",
		"dojo/_base/array",
		"./table/AttachmentTable"], 
		function(declare, WidgetBase, Container, domConstruct, array, AttachmentTable){
	
	return declare("sys.attachment.maxhub.js.AttachmentSelectList", [ WidgetBase, Container ] , {
		
		tables : [],
		
		values : [],
		
		selectedValues : [],	
			
		baseClass : 'mhAttachmentSelectList',
		
		_setSelectedValuesAttr : function(selectedValues){
			for(var i = 0; i < this.tables.length; i++){
				var __table = this.tables[i];
				__table.set('selectedValues',selectedValues);
			}
			this._set('selectedValues', selectedValues);
		},
		
		_setValuesAttr : function(_values){
			for(var i = 0; i < this.tables.length; i++){
				var __table = this.tables[i];
				__table.set('selectedValues',this.selectedValues);
				__table.set('values',_values);
				__table.render && __table.render();
			}
			this._set('values', _values);
		},
		
		buildRendering : function(){
			this.inherited(arguments);
			this.listNode = domConstruct.create('div',{ className : 'mhuiAttachmentList' },this.domNode);
		},
		
		addChild : function(w){
			this.inherited(arguments);
			if(w.isInstanceOf(AttachmentTable)){
				this.tables.push(w);
			}
		},
		
		startup : function(){
			this.inherited(arguments);
			var children = this.getChildren();
			for(var i = 0; i < children.length; i++){
				var __child = children[i];
				if(__child.isInstanceOf(AttachmentTable)){
					this.tables.push(__child);
					domConstruct.place(__child.domNode, this.listNode);
					__child.set('values',this.values);
					__child.set('fdKey', this.fdKey);
					__child.render && __child.render();
				}
			}
			if(this.tables.length > 0){
				var selectedTable = this.tables[0];
				this.switchMode(selectedTable.key);
			}
			this.subscribe('attachmentObj_' + this.fdKey + '_selectedChange' ,'_handleSelected')
		},
		
		switchMode : function(key){
			for(var i = 0; i < this.tables.length; i++){
				var __table = this.tables[i];
				__table.key === key ? __table.show() : __table.hide() ;
			}
		},
		
		_handleSelected : function(evt){
			var selectedIndex = -1,
				_has = false;
			//console.log('_handleSelected evt:' + JSON.stringify(evt) + '-------------------------------');
			console.log('this.selectedValues.length:' + this.selectedValues.length)
			var _has = array.some(this.selectedValues, function(value, index){
				//console.log('value.filePath:' + value.filePath);
				var equal = evt.filePath == value.filePath;
				if(equal){
					selectedIndex = index;
				}
				return equal;
			},this);
			//console.log('hasSelected:' + _has);
			if(_has){
				this.selectedValues.splice(selectedIndex);
			}else{
				this.selectedValues.push(evt);
			}
		}
		
	});
	
});