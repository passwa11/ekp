define([ 'dojo/_base/declare', 'dojo/_base/array', 'dojo/_base/lang' , 'dojo/topic', 'dojo/request', 
         'dojo/dom', 'dojo/dom-construct', 'dojo/dom-style','dojo/dom-class', 'dojo/dom-attr', 'dojo/html', 
         'mhui/list/ItemBase'],
	function(declare, array, lang, topic, request, dom, domCtr, domStyle, domClass, domAttr, html,
			ItemBase) {
		return declare('km.imeeting.maxhub.AddressItem',[ ItemBase ],{
			
			initialize: function() {
				
				this.inherited(arguments);
				
				domClass.add(this.domNode, 'mhuiAddressItem');
				
				var avatarNode = this.avatarNode = domCtr.create('div', {
					className: 'mhuiAddressItemAvatar'
				}, this.domNode); 
				domStyle.set(avatarNode, 'background-image', 'url(' + this.avatar + ')');
				
				domCtr.create('div', {
					"data-dojo-id":this.fdId,
					className: 'mhuiAddressItemName',
					innerHTML: this.name
				}, this.domNode);
				
			}
			
			
		});
	}
);