define([
    "dojo/_base/declare",
	"dojox/mobile/ScrollableView",
	"mui/scrollable/IPhoneXScrollableMixin",
	"./_ViewPullReloadMixin",
	"./_ViewPushAppendMixin",
	"./_ViewScrollEventPublisherMixin",
	"mui/view/AddBottomTipMixins"
	], function(declare, ScrollableView, IPhoneXScrollableMixin, _ViewPullReloadMixin, _ViewPushAppendMixin, _ViewScrollEventPublisherMixin,AddBottomTipMixins) {
	
	
	return declare("mui.list.StoreScrollableView", [ScrollableView, _ViewPullReloadMixin, _ViewPushAppendMixin, _ViewScrollEventPublisherMixin,IPhoneXScrollableMixin,AddBottomTipMixins], {
		
		scrollBar : false,
		
		threshold : 50,
		
		hideTopBottom: true,
		startup : function(){
			this.inherited(arguments);
		}
	});
});