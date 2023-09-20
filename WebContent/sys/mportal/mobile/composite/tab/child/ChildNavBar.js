define(
		[ "dojo/dom-construct", 'dojo/_base/declare', "dojo/dom-class",
				"dojox/mobile/_ScrollableMixin", "dojo/dom-style",
				"dojo/topic", "dojo/_base/lang", "dijit/_WidgetBase",
				"dijit/_Contained", "dijit/_Container", "dojo/_base/array",
				"mui/nav/_ShareNavBarMixin", "mui/nav/_StoreNavBarMixin",
				"./ChildNavItem", "dojox/mobile/sniff", "dojo/dom-geometry", 
				"./ChildNavBarMore", "mui/util", "dojo/window" ],

		function(domConstruct, declare, domClass, ScrollableMixin, domStyle,
				topic, lang, WidgetBase, Contained, Container, array,
				_ShareNavBarMixin, _StoreNavBarMixin, NavItem, has, domGeom, more, util, win) {
			var cls = declare(
					'mui.portal.NavBar',
					[ WidgetBase, Contained, Container, ScrollableMixin, _StoreNavBarMixin, _ShareNavBarMixin, more ],
					{
						
						baseClass: "et-portal-tabs main",
						
						scrollDir : "h",

						url : '/sys/mportal/sys_mportal_page/sysMportalPage.do?method=loadPages',

						width : '100%',

						height : '4rem',
						
						curIndex : 0,

						lastIndex : 0,

						itemRenderer : NavItem,

						// 不显示滚动条
						scrollBar : false,

						// 首次进入页面id
						pageId : null,
						
						forceRefresh : false,
						
						//当前页面id
						currentPageId : null,
						
						// 门户首页通过事件进行渲染
						drawByEven : false,
						
						// 第一次加载的时候不改变url
						firstLoad : true,

						postMixInProperties : function(){
							this.inherited(arguments);
							this.currentPageId = this.pageId;
						},
						
						// 格式化门户数据那些恶心格式
						_createItemProperties : function(item) {
							return {
								value : item[0],
								text : item[1],
								type : item[2],
								pageUrl : item[3],
								pageUrlOpenType: item[4],
								tabIndex : item.tabIndex,
								forceRefresh : this.forceRefresh
							}
						},
						
						onComplete : function(items, tabId) {
							if(tabId != this.tabId){
								return;
							}							
							if(!this.pageId && items) {
								this.pageId = items[0][0];
							}
							this.generateList(items);
							this.handleNavOnComplete(this, items);
							//this.inherited(arguments);
						},

						onShare : function(obj, evt) {
							var c_w = this.containerNode.offsetWidth, d_w = this.domNode.offsetWidth;
							if (c_w > d_w)
								return;
							var children = this.getChildren();
							var innerWidth = 0;
							array.forEach(children, function(item) {
								var node = item.domNode;
								innerWidth += node.offsetWidth;
							});
							var pd = (c_w - innerWidth) / (children.length * 2);
						},
						
						buildRendering : function() {

							this.inherited(arguments);
													
							this.buildBox();

							this.domNode.dojoClick = !has('ios');

							// 监听item渲染完毕事件
							//this.subscribe('/mui/nav/onComplete','handleNavOnComplete');
							// 监听item发生变化事件
							this.subscribe('/sys/mportal/composite/child/navItem/changed','handleItemChanged');
							// 首页缓存用事件触发渲染
							this.subscribe('/mui/nav/composite/child/drawNavBar', 'onComplete')
							
							domClass.add(this.domNode, "mblScrollableView");
							domClass.add(this.domNode, "muiPortalNavBar");

							// 外围容器，滚动需要该特定名对象
							this.containerNode = domConstruct.create("div", {
								className : "et-portal-tabs-container muiFontColorInfo"
							}, this.box);
							
						},

						refresh : function (){
							if(!this.drawByEven){
								this.inherited(arguments)
							}
						},
						
						buildBox : function() {
							
							this.inherited(arguments);

							if(!this.box) 
								
								this.box = domConstruct.create("div", {
									className : "et-portal-tabs-box"
								}, this.domNode);
							
							domConstruct.create("div", {
								className : "et-portal-tabs-box-fixed"
							}, this.box);
							
						},
						
						handleNavOnComplete : function(widget, items) {
							if(widget != this){
								return;
							}							
							var index = 0;
							var allWidth = 0;
							array.forEach(items, function(item, idx) {
								var child = widget.getChildren()[idx];
								topic.publish('/sys/mportal/composite/child/navBarMore/buildContent', {
									'child' : child,
									'item' : item,
									'tabId' : this.tabId,
									'isSelected' : this.pageId == item[0]
								});
								
								if(child){
									allWidth += child.domNode.offsetWidth;
								}
								if (this.pageId == item[0]) {
									index = idx;
									return false;
								}
							}, this);
							
							if(allWidth < this.domNode.offsetWidth){
								domStyle.set(this.containerNode,'padding-right','0px');
							}

							var selectedItem = widget.getChildren()[index];

							//selectedItem.setSelected();

							this.handleItemChanged(selectedItem);
							
							//发布门户导航初始事件
							topic.publish('/sys/mportal/composite/child/navItem/init',{index:index});
						},

						handleItemChanged : function(child,evt) {
							if(child.tabId && child.tabId != this.tabId){
								return;
							}
							var children = this.getChildren();
							
							array.forEach(children,function(_child){
								_child.setUnSelected();
							});
							child.setSelected();
							
							var index = this.getIndexOfChild(child);
							if(index == -1){
								index = this.getIndexOfChild(evt);
							}
							var x = this.getScrollX(children, index);
							
							var self = this;
							setTimeout(function(){
								self.slideTo({
									x : x
								}, 0.2, "linear");
							}, 1);
						},
						
						getScrollX : function(children, index) {
							// 页面宽度
							var pageWidth = document.documentElement.clientWidth;
							var parentWidth = children[0].getParent().containerNode.clientWidth;;
							var totalChildWidth = 0;
							var left = 0;
							for (var i = 0; i < children.length, i <= index; i++) {
								if (i != index) {
									left += children[i].containerNode.offsetWidth;
								} else {
									left += children[i].containerNode.offsetWidth / 2;
								}
								totalChildWidth += children[i].containerNode.offsetWidth;
							}
							var windowWidth = win.getBox().w;
							//子页签所占位置不超过屏幕宽度
							if(totalChildWidth < windowWidth){
								return 0;
							}
				
							var right = parentWidth - left;

							if(left <= (pageWidth / 2)){ // 选中块在最左边
								return 0;
							} else if (left >= (parentWidth - pageWidth / 2)) { // 选中块在最右边
								return - (parentWidth - pageWidth);
							} else { // 选中块在中间
								return - (left -pageWidth / 2);
							}
							
						},

						startup : function() {

							if (this._started)
								return;

							this.reparent();
							this.inherited(arguments);
							
							this.firstLoad = false;
							
						},

						// 移动内容到相应位置
						reparent : function() {
							var i, idx, len, c;
							for (i = 0, idx = 0, len = this.domNode.childNodes.length; i < len; i++) {
								c = this.domNode.childNodes[idx];
								if (c === this.containerNode) {
									idx++;
									continue;
								}
								//this.containerNode.appendChild(this.domNode.removeChild(c));
							}
						}
					});
			return cls;
		});