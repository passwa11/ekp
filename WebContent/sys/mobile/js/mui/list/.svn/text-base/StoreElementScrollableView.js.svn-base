define([
    "dojo/_base/declare",
	"dojox/mobile/View",
	"mui/list/_ViewDownReloadMixin",
	"mui/list/_ViewUpAppendMixin",
	"dojo/topic"
	], function(declare, View, _ViewDownReloadMixin, _ViewUpAppendMixin, topic) {
	
	
	//
	return declare("mui.list.StoreElementScrollableView", [View, _ViewDownReloadMixin, _ViewUpAppendMixin], {
		
		
		rel : null,
		
		//mescroll
		startup : function(){
			this.inherited(arguments);
		},
		
		buildRendering : function() {
			this.inherited(arguments);
		},
		
		onAfterTransitionIn: function() {
			this.inherited(arguments);
			// 提示：滑动切换并不会触发此方法
			topic.publish('/dojox/mobile/viewChanged', this);
			this.resize();
		}
	
	});
});