define(
		[ "dojo/_base/declare", "dojox/mobile/View", "dojo/dom-style",
				"dojo/dom-class", "dojo/_base/window", "dijit/registry",
				"dojo/_base/array", "dojo/topic" ,"dojo/ready","dojox/mobile/_css3","./CalendarBottom","./CalendarContent","./CalendarWeek"],
		function(declare, View, domStyle, domClass, win, registry, array, topic,ready,css3,CalendarBottom,CalendarContent,CalendarWeek) {
			var claz = declare(
					"mui.calendar.CalendarView",
					[ View ],
					{
						
						backforwardReload : true, //浏览器后退是否触发reload，默认为true
						
						defaultDisplay:'month',
						
						changeDisplay:true,
						
						isNotScroll : false, // 日程数据展现不用滚动的方式显示

						buildRendering : function() {
							this.inherited(arguments);
							domClass.add(this.domNode, 'muiCalendarScrollableView');
						},

						startup : function() {
							if (this._started)
								return;
							this.findAppBar();
							this.handleDisplay();
							this.subscribe('/mui/calendar/contentComplete','handleDisplay');
				            this.subscribe('/mui/calendar/weekComplete','handleDisplay');
							this.inherited(arguments);
							this.connect(window,'pageshow','_pageshow');
						},
						
						_pageshow : function(evt){
							// 显示页面
							domStyle.set(this.domNode,'display','');
							if(evt.persisted && this.backforwardReload){
								var children = this.getChildren();
								array.forEach(children,function(child){
									child.reload && child.reload();
								},this);
							}
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
										|| node.getAttribute("data-mobile-fixed")
										|| (registry.byNode(node) && registry.byNode(node).fixed);
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
							
							if(this.isNotScroll)
								return;
								
							this.footerHeight = (this.fixedFooter) ? this.fixedFooter.offsetHeight : 0;
							this.headerHeight = (this.fixedHeader) ? this.fixedHeader.offsetHeight : 0;
							domStyle.set(this.domNode, 'height', win.global.innerHeight - this.footerHeight - this.headerHeight + 'px');
							topic.publish('/mui/calendar/viewComplete', this, {
								fixedHeaderHeight : this.headerHeight,
								fixedFooterHeight : this.footerHeight
							})
							array.forEach(this.getChildren(), function(child) {
								if (child.resize)
									child.resize();
							});
						},
						
						handleDisplay : function(){
							var self = this;
							var calendarContent,calendarWeek,calendarBottom;
							array.forEach(self.getChildren(), function(child) {
								if(child.isInstanceOf(CalendarContent) ){
									calendarContent = child;
								}
								if(child.isInstanceOf(CalendarWeek) ){
									calendarWeek = child;
								}
								if(child.isInstanceOf(CalendarBottom) ){
									calendarBottom = child;
								}
							});
							if(self.defaultDisplay == 'week'){
								calendarBottom.scrollToWeek();
								
								//使用scale(0)‘隐藏’元素
								domStyle.set(calendarContent.domNode,css3.add({
									transform : 'scale(0)'
								}));
								
							}
							if( !self.changeDisplay ){
								calendarBottom.unBindEvent()
								calendarBottom._unBindEvent();
								if(self.defaultDisplay == 'week'){
									//使用scale(0)‘隐藏’元素
									domStyle.set(calendarContent.domNode,css3.add({
										height : '0'
									},{
										transform : 'scale(0)'
									}));
								}
							}
						}
						
					});
			return claz;
		});