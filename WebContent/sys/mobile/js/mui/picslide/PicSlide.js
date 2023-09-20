define(
		[ "dojo/_base/array", "dojo/_base/lang","dojo/_base/declare", "dojo/query","dojo/dom-class",
				"dojo/dom-style", "dojo/dom-construct", "dojo/topic",
				"mui/store/JsonRest", "dijit/_WidgetBase", "dijit/_Contained",
				"dijit/_Container", "dojox/mobile/_StoreListMixin",
				"dojox/mobile/SwapView",  "mui/picslide/PicItem","mui/util" ,"mui/loadingMixin"],
		function(array,lang, declare, query,domClass, domStyle, domConstruct, topic,
				JsonRest, WidgetBase, Contained, Container, StoreListMixin,
			SwapView, PicItem, util, loadingMixin) {
			var picslide = declare("mui.picslide.PicSlide",[WidgetBase, Container, Contained, StoreListMixin, loadingMixin ],{ 

						itemRenderer:PicItem,
						
						width : "inherit",

						height : "inherit",
						//刷新时间，为0不刷新
						refreshTime : 0,
						
						showType: '',
						//是否显示标题
						showSubject:true,
						//图片数据请求URL，为空，标识无需通过数据请求构造图片播放
						url : "",
						//图片数据信息
						items : [],
						//图片是否拉伸
						picTensile : false,
						
						curIndex: 0,
						
						openByProxy:false,
						
						time : null,

						picResizeEvt : "/mui/picitem/resize",

						viewChanged : "/dojox/mobile/viewChanged",

						changeView : "/mui/picslide/changeview",

						_getSource : function() {
							if (!this.store && this.url != null && this.url != '')
								this.store = new JsonRest( {
									idProperty : 'fdId',
									target : util.formatUrl(this.url)
								});
						},

						
						buildRendering : function() {
							this.containerNode = domConstruct.create("div", {
								className : "muiListPicslideContainer",
								id : this.id + "_container"
							});
							this.inherited(arguments);
							var i, len;
							if (this.srcNodeRef) {
								for (i = 0, len = this.srcNodeRef.childNodes.length; i < len; i++) {
									this.containerNode.appendChild(this.srcNodeRef.firstChild);
								}
							}
							if(this.showType == 'dot'){
								this.switchNode = domConstruct.create("div", {
									className : "muiListPicslideSwitch"
								}, this.containerNode);
								if(this.showSubject){
									this.titleNode = domConstruct.create("div", {
										className : "muiListPicslideTitleDot"
									}, this.containerNode);
								}
							}else{
								var pagingNode = domConstruct.create("div", {
									className : "muiListPicslidePaging"
								}, this.containerNode);
								this.curPagingNode = domConstruct.create("span", {
									className : "muiListPicslideCur"
								}, pagingNode);
								this.totalPagingNode = domConstruct.create("span",
										{
											className : "muiListPicslideTotal"
										}, pagingNode);
								if(this.showSubject){
									this.titleNode = domConstruct.create("div", {
										className : "muiListPicslideTitle"
									}, this.containerNode);
								}
								
							}
							this.domNode.appendChild(this.containerNode);
							this._getSource();
							

							if (this.refreshTime == 0)
								return;

							this.connect(this.domNode, 'touchstart',this.doTouchStartHandler);
							this.connect(this.domNode, 'touchend', this.doTouchEndHandler);
							
						},

						doTouchStartHandler:function(){
							if(this.time){
								clearInterval(this.time);
								this.time = null;
							}
							
						},
						
						doTouchEndHandler:function(){
							var self = this;
							if(!this.time)
								this.time = setInterval(function(){
									self._changView();
								},this.refreshTime*1000);
							
						},
						postCreate : function() {
							this.inherited(arguments);
							this.subscribe(this.viewChanged, "_handleViewChanged");
							this.subscribe(this.changeView, "_changView");
						},

						startup : function() {
							if (this._started) {
								return;
							}
							//高宽处理
							var h, w;
							if (this.height === "inherit") {
								if (this.domNode.parentNode) {
									h = this.domNode.parentNode.offsetHeight + "px";
								}
							} else if (this.height) {
								h = this.height;
							}
							if (h) {
								this.domNode.style.height = h;
							}
	
							if (this.width === "inherit") {
								if (this.domNode.parentNode) {
									w = this.domNode.parentNode.offsetWidth + "px";
								}
							} else if (this.width) {
								w = this.width;
							}
							if (w) {
								this.domNode.style.width = w;
							}
	
							if (this.store) {
								this.buildLoading(this.domNode)
								this.setQuery( {}, {});
							} else {
								this.onComplete(this.items);
							}
							this.inherited(arguments);
					},

					//子对象resize
						resizeItems : function() {
							var h = this.domNode.offsetHeight, w = this.domNode.offsetWidth;
							topic.publish(this.picResizeEvt, this, {
								height : h,
								width : w,
								tensile : this.picTensile,
								id : this.id
							});
						},

						//数据请求回调
						onComplete : function(items) {
							array.forEach(this.getChildren(), function(child) {
								if (child instanceof SwapView) {
									child.destroyRecursive();
								}
							});

							this.destroyLoading(this.domNode);
							this.items = items;
							var currV, h = this.domNode.offsetHeight;
							if (items.length > 0) {
								if(items.length == 1 && !items[0].icon){  //有icon值的为移动端rtf中图片
									var w = domConstruct.create("a", {className:"mblView mblSwapView",href:util.formatUrl(items[0].href)}, this.containerNode);
									var item = domConstruct.create("div", {className:"muiListPicslideItem"}, w);
									 domConstruct.create("img", {
										 className:"muiListPicItemImg",
										 alt:items[0].label,
										 src: items[0].url,
										 width:"100%",
										 height:h+"px"
									 }, item);
									 if(this.showSubject){
										if (this.items[0].label || this.items[0].alt) {
											this.titleNode.innerHTML = '<div class="muiListPicItemTitle muiFontSizeM textEllipsis">'+(this.items[0].label ? this.items[0].label : this.items[0].alt)+'</div>';
											domStyle.set(this.titleNode, {
												display : 'block'
											});
										} else {
											domStyle.set(this.titleNode, {
												display : 'none'
											});
										}
									}
								}else{
									
									// 优化手动滑动体验，构建额外一个虚拟子view（用于向左滑动到第一张图片时，继续向左滑动能模拟循环到最后一张图片去）
									if (items.length > 0) {
										var w = new SwapView( {
											height : h + "px",
											lazy : true,
											w : 4
										});
										w.addChild(new this.itemRenderer(lang.mixin(items[items.length-1],{itemIndex:items.length-1,openByProxy:this.openByProxy})));
										this.addChild(w);
										w.hide();
									}
									
									// 构建子view
									for ( var i = 0; i < items.length; i++) {
										var w = new SwapView( {
											height : h + "px",
											lazy : true,
											w : 4
										});
										w.addChild(new this.itemRenderer(lang.mixin(items[i],{itemIndex:i,openByProxy:this.openByProxy})));
										this.addChild(w);
										if (i === 0) {
											w.show();
											currV = w;
										} else {
											w.hide();
										}
									}
									
									// 优化手动滑动体验，构建额外一个虚拟子view（用于向右滑动到最后一张图片时，继续向右滑动能模拟循环到第一张图片去）
									if (items.length > 0) {
										var w = new SwapView( {
											height : h + "px",
											lazy : true,
											w : 4
										});
										w.addChild(new this.itemRenderer(lang.mixin(items[0],{itemIndex:0,openByProxy:this.openByProxy})));
										this.addChild(w);
										w.hide();
									}
									
									if(this.showType == 'dot'){
										if(items.length > 1){
											for ( var i = 0; i < items.length; i++) {
												domConstruct.create("span", {
													className:this.id+"_muiListPicslideDotItem_"+i
												}, this.switchNode);
											}
										}
									}
								}
								
								this.resizeItems();
								this._handleViewChanged(currV);
								if(this.refreshTime > 0){
									var self = this;
									this.time = setInterval(function(){
										self._changView();
									},this.refreshTime*1000);
								}
							}else{
								if(this.showType == 'dot'){
									if(this.switchNode){
										domStyle.set(this.switchNode, "display", "none");
									}
									if(this.titleNode){
										domStyle.set(this.titleNode, "display", "none");
									}
								}								
								var imgUrl= util.formatUrl("/sys/mportal/mobile/css/imgs/nodata.png");
								var noDataArea = domConstruct.create("div", {className:"muiListNoDataArea"}, this.containerNode);
								var innerArea = domConstruct.create("div",{className : "muiListNoDataInnerArea"},noDataArea);
								domConstruct.create("div",{className : "muiListNoDataContainer muiListNoDataImg",innerHTML : "<img src="+imgUrl+">"},innerArea);
								domConstruct.create("div",{className : "muiListNoDataTxt muiFontSizeM muiFontColorInfo",innerHTML : "暂无内容"},noDataArea);
							}
						},

						//图片切换事件
						_handleViewChanged : function(evt) {
							var view = null;
							var imgIndex = 0;
							if (evt instanceof SwapView && evt.getParent()  === this) {
								view = evt;
								var viewIndex = this.getIndexOfChild(view);
								
								if(viewIndex == 0){ // 手动滚动第一张
									imgIndex = this.items.length-1;
									var lastview = this.getChildren()[this.items.length];
									lastview.show();
									this.currentView = lastview;
								}else if(viewIndex == this.items.length+1){ // 手动滚动最后一张
									imgIndex = 0 ;
									var firstview = this.getChildren()[1];
									firstview.show();
									this.currentView = firstview;
								}else{
									imgIndex = viewIndex - 1;
								}
								
								// 定时滚到最后一张
								if(viewIndex == this.items.length && view == this.currentView){
									imgIndex = 0 ;
									var firstview = this.getChildren()[1];
									this.currentView.goTo(1,firstview);
									this.currentView = firstview;
								}
								
								if (this.currentView != view) {
									this.curIndex = viewIndex;
										if(this.showType == 'dot'){
											if(this.items.length >1){
												for(var x = 0; x <this.items.length; x++){
													if(x == imgIndex){
														domClass.add(query("."+this.id+"_muiListPicslideDotItem_"+x)[0],"item_on");
													}else{
														domClass.remove(query("."+this.id+"_muiListPicslideDotItem_"+x)[0],"item_on");
													}
												}
											}
										}else{
											this.curPagingNode.innerHTML = (imgIndex + 1);
											this.totalPagingNode.innerHTML = "/" + this.items.length;
										}
									if(this.showSubject){
										if (this.items[imgIndex].label || this.items[imgIndex].alt) {
											this.titleNode.innerHTML = '<div class="muiListPicItemTitle muiFontSizeM textEllipsis">'+(this.items[imgIndex].label ? this.items[imgIndex].label : this.items[imgIndex].alt)+'</div>';
											domStyle.set(this.titleNode, {
												display : 'block'
											});
										} else {
											domStyle.set(this.titleNode, {
												display : 'none'
											});
										}
									}
									this.currentView = view;
								}
							}
							
							this._setSiblingViewsInMotion(false);
						},

						_setSiblingViewsInMotion : function(inMotion) {
							var inMotionAttributeValue = inMotion ? "true" : false;
							this.containerNode.setAttribute("data-dojox-mobile-swapview-inmotion",inMotionAttributeValue);
						},
						
						_changView:function(scrObj,evt){
							if(evt){
								var imgIndex = evt.curIndex;
								var viewIndex = imgIndex + 1;
								var view = this.getChildren()[viewIndex];
								if (this.currentView != view) {
									view.show();
									this._handleViewChanged(view);
								}
							}else{
								this._setSiblingViewsInMotion(true);
								if(this.curIndex == this.items.length){
									this.curIndex = 0;
								}
								var view = this.getChildren()[this.curIndex+1];
								if (this.currentView != view) {
									this.currentView.goTo(1,view); // 滑动至下一张图片
									this.curIndex++;
								}
							}
						}
					});
			return picslide;
		});