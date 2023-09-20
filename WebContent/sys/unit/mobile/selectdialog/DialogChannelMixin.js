define([ "dojo/_base/declare" ], function(declare) {
	return declare("mui.selectdialog.DialogChannelMixin", null, {

		channel : null,
		
		isSameChannel : function(srcObj) {
			if (srcObj && srcObj.channel) {
				return this.channel == srcObj.channel;
			}
			return false;
		}

	});
});