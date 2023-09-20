define([ 
	"dojo/_base/declare", 
	"mui/category/_ViewScrollResizeMixin",
	"./AddressChannelMixin" 
	], function(declare, _ViewScrollResizeMixin, AddressChannelMixin) {
	return declare("mui.address._NewViewScrollResizeMixin", [ _ViewScrollResizeMixin, AddressChannelMixin ], {

		_scrollTo : function(srcObj, evt) {
			this.isSameChannel(srcObj) && this.inherited(arguments);
		},
		
		_scrollResize: function(srcObj){
			this.isSameChannel(srcObj) && this.inherited(arguments);
		}
		
	});
});