define([
    "dojo/_base/declare",
	"dojo/topic",
	"mui/i18n/i18n!sys-mobile",
	"dojo/dom-class",
	"dojox/mobile/viewRegistry",
	"dojo/_base/window",
	"dojo/dom-geometry",
	"dijit/registry",
	"dojox/mobile/View"
	], function(declare, topic, Msg, domClass, viewRegistry, win, geometry, registry, View) {
	
	//下拉刷新，大部分参考自mescroll.js
	return declare("mui.list.framework._ViewScrollCommonMixin", null, {
	
		isDownScrolling : false, //是否在执行下拉
		isUpScrolling : false, //是否显示上拉加载中
		
		os : {
			ios: !!navigator.userAgent.match(/\(i[^;]+;( U;)? CPU.+Mac OS X/),
			android: navigator.userAgent.indexOf('Android') > -1 || navigator.userAgent.indexOf('Adr') > -1
		},
		
		//mescroll
		startup : function() {
			this.inherited(arguments);
			this.findAppBars();
		},
		
		postCreate : function() {
			this.inherited(arguments);
			this.subscribe('/mui/list/loaded', 'endSuccess');
		},
		
		buildRendering : function() {
			this.inherited(arguments);
			if(!this.scrollDom) {
				this.scrollDom = this.domNode;
				domClass.add(this.scrollDom, "mescroll mui-scroll-view");
			}
		},
		
		
		
		resize : function() {
			
			this.inherited(arguments);

			// moved from init() to support dynamically added fixed bars
			this._appFooterHeight = (this._fixedAppFooter) ? this._fixedAppFooter.offsetHeight : 0;
			
			var top = geometry.position(this.domNode, true).y;
		
			var	h,
				screenHeight = this.getScreenSize().h,
				dh = screenHeight - top - this._appFooterHeight; // default height
			if(this.height) {
				h = this.height;
			}
			if(!h) {
				h = dh + "px";
			}
			if(h.charAt(0) !== "-" && h !== "default"){
				this.domNode.style.height = h;
			}
		},
		
		preventDefault : function (e) {
		    // cancelable:是否可以被禁用; defaultPrevented:是否已经被禁用
		    if (e && e.cancelable && !e.defaultPrevented) e.preventDefault();
		},
		
		getPoint :function (e) {
		    return {
		      x: e.touches ? e.touches[0].pageX : e.clientX,
		      y: e.touches ? e.touches[0].pageY : e.clientY
		    }
		},
		getBodyHeight : function () {
		    return document.body.clientHeight || document.documentElement.clientHeight;
		},
		
		getScrollTop : function () {
			return this.scrollDom.scrollTop;
		},
		
		getScrollHeight : function() {
			 return this.scrollDom.scrollHeight;
		},
		
		getClientHeight : function () {
			return this.scrollDom.clientHeight;
		},
		
		setScrollTop : function (y) {
		    if (typeof y === 'number') {
		    	if(this.scrollDom)
		    		this.scrollDom.scrollTop = y;
		    }
		},
		
		getScreenSize : function(){
			// summary:
			//		Returns the dimensions of the browser window.
			return {
				h: win.global.innerHeight || win.doc.documentElement.clientHeight,
				w: win.global.innerWidth || win.doc.documentElement.clientWidth
			};
		},

		isInSee : function (dom, offset) {
		    offset = offset || 0; // 可视区域上下偏移的距离
		    var topDom = this.getOffsetTop(dom);// 元素顶部到容器顶部的距离
		    var topSee = this.getScrollTop() - offset;// 滚动条的位置(可视范围的顶部)
		    var bottomDom = topDom + dom.offsetHeight;// 元素底部到容器顶部的距离
		    var bottomSee = topSee + offset + this.getClientHeight() + offset;// 滚动条的位置+容器高度(可视范围的底部)
		    // 图片顶部在可视范围内 || 图片底部在可视范围 ; 不考虑scrollLeft和translateY的情况
		    return (topDom < bottomSee && topDom >= topSee) || (bottomDom <= bottomSee && bottomDom > topSee);
		},
		
		isListInScrollView : function(obj) {
			if(obj && viewRegistry.getEnclosingView(obj) === this) {
				return true;
			}
			return false;
		},
		
		_buildHandle : function() {
			var self = this;
			return {
				work: function() {}, 
				done: function(obj) {
					self.endSuccess(obj);
				}, 
				error: function(obj) {
					self.endError(obj);
				}
			};
		},
		
		endSuccess : function(obj) {
			if(!this.isListInScrollView(obj.domNode)) return;
			if(this.endDownSuccess) this.endDownSuccess(obj);
			if(this.endUpSuccess) this.endUpSuccess(obj);
		},
		
		endError : function() {
			
		},
		
		findAppBars: function(){
			
			// summary:
			//		Search for application-specific header or footer.
			var i, len, c;
			for(i = 0, len = win.body().childNodes.length; i < len; i++){
				c = win.body().childNodes[i];
				this.checkFixedBar(c, false);
			}
			
			
			
			var parent = this.getParent();
			if(parent && parent instanceof View) {
				if(parent.domNode.parentNode) {
					for(i = 0, len = parent.domNode.parentNode.childNodes.length; i < len; i++) {
						c = parent.domNode.parentNode.childNodes[i];
						this.checkFixedBar(c, false);
					}
				}
			}
			
			if(this.domNode.parentNode){
				for(i = 0, len = this.domNode.parentNode.childNodes.length; i < len; i++){
					c = this.domNode.parentNode.childNodes[i];
					this.checkFixedBar(c, false);
				}
			}
			
			this.fixedFooterHeight = this.fixedFooter ? this.fixedFooter.offsetHeight : 0;
		},

		checkFixedBar: function(/*DomNode*/node, /*Boolean*/local){
			// summary:
			//		Checks if the given node is a fixed bar or not.
			if(node.nodeType === 1){
				var fixed = node.getAttribute("fixed") // TODO: Remove the non-HTML5-compliant attribute in 2.0
					|| node.getAttribute("data-mobile-fixed")
					|| (registry.byNode(node) && registry.byNode(node).fixed);
				if(fixed === "top"){
					domClass.add(node, "mblFixedHeaderBar");
					if(local){
						node.style.top = "0px";
						this.fixedHeader = node;
					}
					return fixed;
				}else if(fixed === "bottom"){
					domClass.add(node, "mblFixedBottomBar");
					if(local){
						this.fixedFooter = node;
					}else{
						this._fixedAppFooter = node;
					}
					return fixed;
				}
			}
			return null;
		}
		
	});
});