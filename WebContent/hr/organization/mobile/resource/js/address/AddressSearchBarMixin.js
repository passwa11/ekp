define([ "dojo/_base/declare", "dojo/topic" ], function( declare, topic) {

	return declare("mui.address.AddressSearchBarMixin", null , {
		_onSearch: function() {
			// 点击搜索跳转至组织架构列表标签页
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
