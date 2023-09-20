define(
		[ "dojo/_base/declare", "dojox/mobile/View", "dojo/dom-style",
				"dojo/dom-class", "dojo/_base/window", "dijit/registry",
				"dojo/_base/array", "dojo/topic",
				"hr/ratify/mobile/resource/js/ReviewScrollableViewMixin" ],
		function(declare, View, domStyle, domClass, win, registry, array,
				topic, ReviewScrollableViewMixin) {
			var claz = declare(
					"km.review.ReviewView",
					[ View, ReviewScrollableViewMixin ],
					{

						buildRendering : function() {
							this.inherited(arguments);
							//修复内容过长溢出容器导致可拖动底部按钮的问题
							domStyle.set(this.domNode, 'overflow', 'hidden');
						},

						startup : function() {
							if (this._started)
								return;
							this.findAppBar();
							this.inherited(arguments);
						},

						// 搜索fixed为bottom或top的节点
						findAppBar : function() {
							if (this.domNode.parentNode) {
								for (var i = 0, len = this.domNode.parentNode.childNodes.length; i < len; i++) {
									c = this.domNode.parentNode.childNodes[i];
									this.checkFixedBar(c);
								}
							}
						},

						checkFixedBar : function(node) {
							if (node.nodeType === 1) {
								var fixed = node.getAttribute("fixed")
										|| node
												.getAttribute("data-mobile-fixed")
										|| (registry.byNode(node) && registry
												.byNode(node).fixed);
								if (fixed === "top") {
									domClass.add(node, "mblFixedHeaderBar");
									this.fixedHeader = node;
								} else if (fixed === "bottom") {
									domClass.add(node, "mblFixedBottomBar");
									this.fixedFooter = node;
								}
							}
						},

						width : '100%',
						resize : function() {
							this.footerHeight = window._footerHeight = (this.fixedFooter) ? this.fixedFooter.offsetHeight
									: 0;
							this.headerHeight = (this.fixedHeader) ? this.fixedHeader.offsetHeight
									: 0;
							domStyle.set(this.domNode, 'height',
									win.global.innerHeight - this.footerHeight
											- this.headerHeight + 'px');
							topic.publish('/mui/calendar/viewComplete', this, {
								fixedHeaderHeight : this.headerHeight,
								fixedFooterHeight : this.footerHeight
							})
							array.forEach(this.getChildren(), function(child) {
								if (child.resize)
									child.resize();
							});
						}
					});
			return claz;
		});