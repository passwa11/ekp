define([ "dojo/_base/declare", "dojo/topic" ], function( declare, topic) {

	return declare("mui.selectdialog.DialogSearchBarMixin", null , {
		_onSearch: function() {
			topic.publish('/mui/navitem/selected', this, {
				key : 'all'
			});
			return this.inherited(arguments);
		},
		startup: function() {
			this.inherited(arguments);
			topic.publish("/mui/category/changed", this, {fdId:this.curId});
		}
	});
});
