define([ "dojo/_base/declare", "dojo/topic", "dojo/query", "dojo/dom-style",
		"dojo/_base/array", "mui/util", "dojox/mobile/viewRegistry" ],
		function(declare, topic, query, domStyle, array, util, viewRegistry) {
			var claz = declare("km.forum.ForumListScrollableViewMixin", null, {

				SCROLL_UP : '/km/forum/scrollup',
				SCROLL_DOWN : '/km/forum/scrolldown',

				buildRendering : function() {
					this.inherited(arguments);
					this.subscribe(this.SCROLL_UP, 'scrollup');
					this.subscribe(this.SCROLL_DOWN, 'scrolldown')
				},

				scrollup : function(obj, evt) {
					this.resize();
					this.defer(function() {
						this.enableTouchScroll(false);
					}, 1);
				},

				resize : function() {
					this.inherited(arguments);
					var viewNodes = query('.mblView', this.domNode);
					array.forEach(viewNodes, function(item) {
						var view = viewRegistry.getEnclosingView(item);
						view.height = util.getScreenSize().h -_footerHeight - nav_height + 'px';
					}, this);
				},

				scrolldown : function(obj, evt) {
					this.defer(function() {
						this.enableTouchScroll(true);
					}, 100);
				},

				generateSwapList : function() {
					this.inherited(arguments);
					this.enableTouchScroll(true);
				},

				enableTouchScroll : function(fire) {
					var c = this.getChildren();
					for (var j = 0; j < c.length; j++) {
						var children = c[j].getChildren();
						for (var i = 0; i < children.length; i++) {
							children[i].disableTouchScroll = fire;
						}
					}
				}
			});
			return claz;
		});