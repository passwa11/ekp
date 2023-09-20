define( [ "dojo/_base/declare","dojox/mobile/ScrollableView", "dojo/dom-style", "dojo/ready","dojox/mobile/_css3"],
		function(declare, ScrollableView, domStyle, ready, css3) {
	
	return declare("mui.table.View", [ScrollableView], {
		
		scrollDir:'h',//默认为h，可以切为v
		
		height:'100%',
		
		scrollBar:false,
		
		appBars: false,
		
		postCreate : function() {
			this.inherited(arguments);
			this.subscribe("/mui/list/resize","resize");
		},

		resize : function() {
			// 性能优化，避免offsetHeight这个参数的频繁获取
			if (this.timer) {
				clearTimeout(this.timer);
			}
			var self = this;
			var args = arguments;
			this.timer = setTimeout(function() {
				var arguH = self.containerNode.offsetHeight;
				var parentW = self.getParent();
				if (parentW && parentW.resizeH) {
					parentW.resizeH(arguH);
				}
				self.inherited(args);
			}, 10);
		},
		
		isFormElement : function(/* DOMNode */node) {
			var userSelectName = css3.name('userSelect'),
				userSelect = domStyle.getComputedStyle(node).userSelect 
					||  domStyle.getComputedStyle(node).webkitUserSelect;
			if(userSelect === 'text'){
				return true;
			}
			return false;
		}
		
	});
});