/**
 * 地址本channel Mixin
 * 一个页面可能拥有多个地址本，所以我们通过传入key值区分不同的地址本
 * 一个地址本内有多个列表视图，我们通过传入不同的channel值区分不同的列表视图，防止事件互窜
 */
define([ "dojo/_base/declare" ], function(declare) {
	return declare("mui.address.AddressChannelMixin", null, {

		channel : null,
		
		isSameChannel : function(srcObj) {
			if (srcObj && srcObj.channel) {
				return this.channel == srcObj.channel;
			}
			return false;
		}

	});
});