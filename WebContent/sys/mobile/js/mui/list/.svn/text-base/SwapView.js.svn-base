define([
	'dojo/_base/declare', 
	'dojox/mobile/SwapView',
	'dojo/topic'
	], function(declare, SwapView, topic) {
	
	return declare('mui.list.SwapView',[SwapView], {
		onAfterTransitionIn: function() {
			this.inherited(arguments);
			// 提示：滑动切换并不会触发此方法
			topic.publish('/dojox/mobile/viewChanged', this);
			this.resize();
		}
	});
	
});