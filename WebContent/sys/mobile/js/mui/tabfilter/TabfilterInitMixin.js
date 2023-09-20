define([ "dojo/_base/declare"], function(declare) {
	return declare("mui.tabfilter.TabfilterInitMixin", null, {
		onComplete : function(items) {
			this.inherited(arguments);
			var children = this.getChildren();
			if(children && children[0]) {
				if(children[0].selectCate) {
					children[0].selectCate();
				}
			}
		}
	});
});
