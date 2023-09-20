define(
		[ "dojo/_base/declare","dojo/dom-style","dojo/query","dojo/dom-construct","dojo/ready",
			"mui/calendar/CalendarBottom",
			"dojo/dom-geometry",
			"dojo/dom-class","dojo/topic"],
		function(declare,domStyle,query,domConstruct,ready,_CalendarBottom,domGeometry,domClass,topic) {
			var claz = declare(
					"sys.modeling.main.calendar.CalendarBottom",
					[_CalendarBottom],
					{
						showMode:"",
						buildRendering: function() {
							this.inherited(arguments)
							this.subscribe("/mui/calendar/monthComplete", "monthComplete")
							this.subscribe("/sys/modeling/calendar/switchView","switchView");
							this.subscribe("/sys/modeling/calendar/preview/cancel","previewCancel");
							this.subscribe("/sys/modeling/calendar/preview/view","previewView");
							this.subscribe("/sys/modeling/calendar/list/load","dataLoad");
						},
						monthComplete: function(obj, evt) {
							if (this.monthtHeight == 0) {
								this.monthtHeight = document.querySelectorAll(".muiCalendarMonth ")[0].offsetHeight
							} else {
								this.monthtHeight = evt.height
							}
							this.resize()
						},
						scrollToMonth : function(){
							var y = 0,
							top = this.weekHeight + this.headerHeight;
							this._unBindEvent();
							this.bindEvent();
							this.publishStatus(true);
							this.scrollTo({
								y : y
							}, true);
							this.publishScroll({
								y : y,
								top : top
							});

							this.disconnects();

						},
						resize: function() {
							this.weekHeight = this.weekHeight || 80;
							var tabBarNode = query(".modeling_list_bottom_tab_bar")[0];
							var tabBarHeight = 0;
							if(tabBarNode){
								tabBarHeight = tabBarNode.clientHeight*2;
							}
							if(this.showMode == "0"){
								var viewNode = query(".mblView", this.domNode)[0];
								var optHeight = 0;
								if(this.optNode) {
									optHeight = this.optNode.offsetHeight;
								}

								var h =
									this.getScreenHeight() -
									optHeight -
									this.headerHeight -
									this.weekHeight -
									this.fixedHeaderHeight -
									this.fixedFooterHeight-
									tabBarHeight-100;//100为头部页签+日历头部的高度
								domStyle.set(viewNode, "height", h + "px")
							}else{
								var viewNode = query(".mblView", this.domNode)[0];
								var viewContainer = query(".mblScrollableViewContainer",this.domNode)[0];
								var optHeight = 0;
								var viewContainerHeight = 0;
								if(this.optNode) {
									optHeight = this.optNode.offsetHeight;
								}
								if(viewContainer){
									viewContainerHeight = viewContainer.offsetHeight;
								}
								var h =
									this.getScreenHeight() -
									optHeight -
									this.optNode.offsetHeight -
									this.headerHeight -
									this.weekHeight -
									this.fixedHeaderHeight -
									this.fixedFooterHeight-100-tabBarHeight;
								if(viewContainerHeight){
									h = viewContainerHeight>h?h:viewContainerHeight;
								}
								domStyle.set(viewNode, "height", h + "px");
								domStyle.set(this.domNode, "bottom", tabBarHeight + 15 + "px");
							}
						},
						switchView:function (obj,data){
							if(this.getEnclosingCalendarView(obj) != this.getEnclosingCalendarView(this))
								return;
							this.showMode = data || "0";
							this.resize();
							this.reloadPreviewView();
						},
						reloadPreviewView:function (){
							if(this.showMode == "0"){
								var y = -(this.contentHeight - this.weekHeight), top = 0;

								var muiDropDownShade = query(".muiDropDownShade i",this.domNode)[0];
								if(muiDropDownShade && muiDropDownShade.classList.contains("rotate")){
									y = 0;
									top = this.weekHeight + this.headerHeight;
									this._unBindEvent();
									this.bindEvent();
									this.publishStatus(true);
								}else{
									top = 0;
								}

								this.scrollTo({
									y : y
								}, true);

								this.publishScroll({
									y : y,
									top : top
								});

								this.disconnects();
							}else{
								this.scrollTo({
									y : 0
								}, true);
							}
						},
						previewCancel:function (obj){
							if(this.getEnclosingCalendarView(obj) != this.getEnclosingCalendarView(this))
								return;
							domClass.remove(this.domNode,"active");
						},
						previewView:function (obj){
							if(this.getEnclosingCalendarView(obj) != this.getEnclosingCalendarView(this))
								return;
							domClass.add(this.domNode,"active");
							this.resize();
						},
						dataLoad:function (evt){
							if(this.getEnclosingCalendarView(evt) != this.getEnclosingCalendarView(this))
								return;
							this.resize();
						},
						publishStatus : function(status) {
							// true代表默认模式
							topic.publish('/mui/calendar/bottomStatus', this, {
								status : status
							});
							if(this.showMode == "0"){
								topic.publish('/sys/modeling/calendar/bottomStatus', this, {
									status : status?"day":"week"
								});
							}

						}
					});
			return claz;
		});