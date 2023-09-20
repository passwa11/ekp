define([ 'dojo/_base/declare', 'mui/category/CategorySelection', 'dojo/dom-style'],
	function(declare, CategorySelection, domStyle) {
		return declare('km.imeeting.EquipmentSelection',[ CategorySelection ], {
			
			isMul: true,
			
			startup: function() {
				
				this.inherited(arguments);
				
				if(!this.isMul) {
					domStyle.set(this.domNode, 'display', 'none');
				}
			}
			
		});
});