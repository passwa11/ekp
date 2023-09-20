define([
	"dojo/_base/declare", 
	"dojo/topic",
	"dojo/dom-construct", 
	"dojo/dom-class",
	"dojo/dom-style",
	"dijit/registry",
	"dojox/mobile/_ItemBase"
	],function(declare, topic, domConstruct, domClass,domStyle, registry, ItemBase) {
	
	return declare('mui.address.AddressHistoryItemMixin', [ItemBase], {
		
		buildRendering: function(){
			this.inherited(arguments);
			this.domNode = this.containerNode = domConstruct.create('div',{
				className: 'muiAddressHistoryItem'
			});
			this.contentNode = domConstruct.create('span',{
				className: 'muiAddressHistoryItemContent',
				innerHTML: this.keyword
			},this.domNode);
			this.deleteNode = domConstruct.create('span',{
				className: 'muiAddressHistoryItemDel',
			},this.domNode);
			domConstruct.create('i',{ className: 'mui mui-fail' },this.deleteNode);
		},
		
		postCreate: function(){
			this.connect(this.domNode, "click", "_handleSearch");
			this.connect(this.deleteNode, "click", "_handleDel");
		},
		
		_handleSearch: function(){
			topic.publish('/mui/address/search/submit',this, {
				keyword: this.keyword
			});
		},
		
		_handleDel: function(evt){
			evt.stopPropagation();
			topic.publish('/mui/address/search/history/remove',this, this.keyword);
		}
		
	});
	
});