define([ 'dojo/_base/declare', 'dojo/_base/array', 'dojo/_base/lang' , 'dojo/topic', 'dojo/request', 
         'dojo/dom', 'dojo/dom-construct', 'dojo/dom-style','dojo/dom-class', 'dojo/dom-attr', 'dojo/html', 
         'mhui/list/ItemBase'],
	function(declare, array, lang, topic, request, dom, domCtr, domStyle, domClass, domAttr, html,
			ItemBase) {
		return declare('km.imeeting.maxhub.AttendPersonItem',[ ItemBase ],{
			
			initialize: function() {
				
				this.inherited(arguments);
				
				domClass.add(this.domNode, 'mhui-avatar-list-item');
				
				var avatarNode = this.avatarNode = domCtr.create('span', {
					className: 'mhui-avatar mhui-avatar-circle'
				}, this.domNode); 
				
				domCtr.create('img', {
					src: this.imgUrl
				}, avatarNode);
				
				domCtr.create('span', {
					className: 'txt',
					innerHTML: this.attendName
				}, this.domNode);
				
			}
			
			
		});
	}
);