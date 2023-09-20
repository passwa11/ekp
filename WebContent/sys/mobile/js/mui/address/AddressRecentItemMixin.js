define([ 
	"dojo/_base/declare", 
	"./AddressItemMixin"
	], function(declare, AddressItemMixin) {
	
	return declare("mui.address.AddressRecentItemMixin", [ AddressItemMixin ], {
		// 最近联系人列表不显示下级
		showMore : function() {
			return false;
		}
	});
});