define([
	"dojo/_base/declare", 
	"dojo/_base/lang", 
	'dojo/topic', 
	'dojo/query',
	'dojo/_base/array',
	"dijit/registry",
	"dojo/dom-class",
	"dojo/dom-style",
	"mui/util",
	"dojox/mobile/viewRegistry",
	"mui/list/StoreScrollableView"
	], function(declare, lang, topic,  query, array,
				registry,domClass, domStyle,util,viewRegistry, StoreScrollableView) {
	
	return declare("kms.lservice._ResizeViewMixin", null, {
		
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
			this.footerHeight = window._footerHeight = (this.fixedFooter) 
					? this.fixedFooter.offsetHeight
					: 0;
			this.headerHeight = (this.fixedHeader) ? this.fixedHeader.offsetHeight
					: 0;
			var height = util.getScreenSize().h
						    - this.footerHeight
							- this.headerHeight + 'px';
			domStyle.set(this.domNode, 'height', height);
			
			var viewNodes = query('.mblView', this.domNode);
			array.forEach(viewNodes, function(item) {
				var view = viewRegistry.getEnclosingView(item);
				if(view.isInstanceOf(StoreScrollableView)) {
					return;
				}
				view.height = height;
			}, this);
			var  _fixedAppFooter = this.fixedFooter;
			array.forEach(this.getChildren(), function(child) {
				if(child.resize){
					child.resize(); 
				}
			});
		}
	});
});