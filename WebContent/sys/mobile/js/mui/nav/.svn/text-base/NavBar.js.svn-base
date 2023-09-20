define("mui/nav/NavBar",[ 
        "dojo/dom-construct", 
        "dojo/_base/declare", 
        "dojo/dom-class",
		"dojox/mobile/_ScrollableMixin", 
		"dojo/dom-style",
		"dojo/topic", 
		"dijit/_WidgetBase",
		"dijit/_Contained", 
		"dijit/_Container",
		"dojox/mobile/SwapView", 
		"dojo/_base/array",
		"mui/nav/_ShareNavBarMixin",
		"dojox/mobile/sniff" 
],function(domConstruct, declare, domClass, ScrollableMixin, domStyle, topic, WidgetBase, Contained, Container, SwapView, array, _ShareNavBarMixin, has) {
	
			var cls = declare( 'mui.nav.NavBar', [ WidgetBase, Contained, Container, ScrollableMixin ,_ShareNavBarMixin], {

						scrollDir : "h",

						curIndex : 0,

						lastIndex : 0,

						height : 'inherit',

						width : '100%',

						// 不显示滚动条
						scrollBar : false,
						
						appBars: false,

						buildRendering : function() {
							this.inherited(arguments);
							this.domNode.dojoClick = !has('ios') || has('ios')>13;
							domClass.add(this.domNode, "mblScrollableView");
							domClass.add(this.domNode, "muiNavbar");
							domStyle.set(this.domNode, { top : 0 });
							if(this.height.toLowerCase().indexOf("px")!=-1||this.height.toLowerCase().indexOf("rem")!=-1){
								domStyle.set(this.domNode, 'line-height' , this.height );
							}
							
							// 外围容器，滚动需要该特定名对象
							this.containerNode = domConstruct.create("ul", {
								className : "muiNavbarContainer"
							}, this.domNode);
							// 监听item改变事件
							this.subscribe('/mui/navbar/slideBar','handleSlideNavBar');
						},

						handleSlideNavBar : function(srcObj, evt) {
							var children = this.getChildren(), child = evt.target;
							var index = this.getIndexOfChild(child);
							if (index > -1) {
								// 上一次点击索引
								this.lastIndex = this.curIndex;
								// 这次点击索引
								this.curIndex = index;

								var idx = this.curIndex - this.lastIndex;
								var dim = this._dim || this.getDim(), x = 0, scroll = false;
								if (idx > 0) {// 往后点
									var len = children.length;
									if (len - index >= 2) {
										var domNode_next = children[index + 1].domNode;
										x = -domNode_next.offsetLeft + (dim.v.w - domNode_next.offsetWidth);
										if (x < 0)
											scroll = true;
									} else {
								        var style = domStyle.getComputedStyle(this.domNode),
							            paddingLeft = domStyle.toPixelValue(this.domNode, style.paddingLeft),
							            paddingRight = domStyle.toPixelValue(this.domNode, style.paddingRight);
										x = -(this.containerNode.offsetWidth - this.domNode.offsetWidth + paddingLeft + paddingRight);
										scroll = true;
									}

								} else if (idx < 0) {// 往前点
									if (index >= 1) {
										var domNode_next = children[index - 1].domNode;
										x = -domNode_next.offsetLeft;
										if (x > -dim.c.w && x > -(dim.c.w - dim.v.w))
											scroll = true;
									} else {
										x = 0;
										scroll = true;
									}
								}
								evt = this.formatEvt(evt);
								if (scroll) {
									this.slideEvt = evt;
									this.slideTo({
										x : x
									}, 0.3, "ease-out");
								} 

							}
						},

						// 格式化参数，用于做最大宽限制
						formatEvt : function(evt) {
							var width = evt.width;
							evt.left = evt.left + (width) / 2;
							return evt;
						},

						slideTo : function() {
							this.inherited(arguments);
							if (this.slideEvt) {
								this.slideEvt = null;
							}
						},

						_setWidthAttr : function(width) {
							this.width = width;
						},

						startup : function() {
							if (this._started)
								return;
							this.reparent();
							this.inherited(arguments);
						},

						// 移动内容到相应位置
						reparent : function() {
							var i, idx, len, c;
							for (i = 0, idx = 0, len = this.domNode.childNodes.length; i < len; i++) {
								c = this.domNode.childNodes[idx];
								if (c === this.containerNode || c === this.moreNode) {
									idx++;
									continue;
								}
								this.containerNode.appendChild(this.domNode.removeChild(c));
							}
						}
					});
			
			return cls;
		});