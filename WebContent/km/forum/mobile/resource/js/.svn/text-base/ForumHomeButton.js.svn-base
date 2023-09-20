define([ "dojo/_base/declare", "dojo/topic", "dojo/dom-class" ], function(
		declare, topic, domClass) {
	return declare("km.forum.mobile.ForumHomeButton", null, {

		SCROLL_DOWN : '/km/forum/scrolldown',

		icon : 'mui mui-home-opposite',

		buildRendering : function() {
			this.inherited(arguments);
			domClass.add(this.domNode, "muiHrefBack");
		},

		show : function(evt) {
			topic.publish(this.SCROLL_DOWN, this, {
				evt : evt
			});
		}
	});
});