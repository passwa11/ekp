define(['dojo/_base/declare'], function( declare ){
	
	return declare('mui.ChannelMixin', [], {
		
		// 频道属性，默认为key
		channelPropName: 'key',
		
		// 判断是否处于同一个频道
		isSameChannel: function(channel){
			var propName = this.channelPropName;
			var isNull = function(obj){
				return typeof(obj) === 'undefined' || this[obj] === null;
			};
			if(isNull(this[propName]) && isNull(channel)){
				// 无频道时认为是同一个频道,即全局频道
				return true;
			}
			return this[propName] == channel;
		}
		
	});
	
});