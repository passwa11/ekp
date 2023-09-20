define([ 'dojo/_base/declare', 'dojo/dom', 'dojo/dom-construct', 'dojo/dom-style','dojo/dom-class', 'dojo/on',
         'mhui/list/ItemBase'],
	function(declare, dom, domCtr, domStyle, domClass, on, ItemBase) {
		return declare('sys.attend.maxhub.AttendPersonItem',[ ItemBase ],{
			
			tagName: 'div',
			
			beforeInit: function() {
				this.inherited(arguments);
				domClass.add(this.containerNode, 'mhuiAttendPersonItem');
			},
			
			renderItem: function() {
				
				var ctx = this;
				
				domCtr.create('img', {
					src: ctx.docCreatorImg
				}, ctx.containerNode);
				
				domCtr.create('div', {
					innerHTML: ctx['docCreator.fdName']
				}, ctx.containerNode);
				
			}
			
			
		});
	}
);