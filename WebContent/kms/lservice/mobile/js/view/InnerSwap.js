define([
	'dojo/_base/declare', 
	'dojox/mobile/SwapView',
	'dojox/mobile/View',
	'dojo/topic',
	"mui/list/StoreScrollableView",
	"dojo/_base/array",
	"dojo/dom-geometry"
	], function(declare,
		SwapView, View, topic, StoreScrollableView, array, domGeometry) {
	
	return  declare("kms.lservice.InnerSwap",[SwapView], {
			onAfterTransitionIn: function() {
				this.inherited(arguments);
				// 提示：滑动切换并不会触发此方法
				topic.publish('/dojox/mobile/viewChanged', this);
				this.resize();
			},
			
			resize: function() {
				var h = 0, view = null;
				array.forEach(this.getChildren(), function(item) {
					if(!item.isInstanceOf(StoreScrollableView)) {
						var obj = domGeometry.getMarginBox(item.domNode);
						h += obj.h;
					} else {
						view = item;
					}
				});
				if(h >= 0) {
					view.height = (parseInt(this.height) -  h) + "px";
				}
				this.inherited(arguments); 
			}
	});
});