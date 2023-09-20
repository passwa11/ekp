define(	["dojo/_base/declare", "dojox/mobile/TabBarButton", "dojo/dom-class",
				"dojo/dom-style", "dojox/mobile/Badge", "mui/util",
				"dojo/_base/lang", "dojo/dom-attr","dojox/mobile/viewRegistry", "dojo/query", "dojo/_base/array"], 
				function(declare, TabBarButton, domClass, domStyle, Badge, util, lang, domAttr, viewRegistry, query, array) {

			return declare("mui.tabbar.TabBarButton", [TabBarButton], {
						inheritParams : function() {
							this.inherited(arguments);
							// 修复疑似dojo重复绘制icon缺陷
							return !!this.getParent();
						},
						
						transition:"slide",
						
						colSize: 1,

						href : null,

						align : 'center',
						
						tabIndex : '',

						_setAlignAttr : function(align) {
							this.align = align;
						},

						buildRendering : function() {
							this.inherited(arguments);
							if (!this.label && this.getParent() != null){
								domClass.add(this.getParent().domNode,
								'muiNavBarButton');
							}
							if(this.icon1 && this.label){
								domClass.add(this.domNode,'muiButtonVertical');
							}
							if(this.label && !this.icon1){
								this.labelNode.innerHTML =this.label;	
							}
						},
						
//						setTitle : function() {
//							var title = domAttr.get(this.domNode, 'title');
//							// 如果没有title，则做下面的处理
//							if(this.isNull(title)) {
//								// 判断有没有text属性
//								title = this.text;
//								if(this.isNull(title)) {
//									// 判断有没有文本内容
//									if(window.navigator.userAgent.toLowerCase().indexOf("firefox") != -1) {
//										// firefox浏览器使用textContent属性
//									     title = this.domNode.textContent;
//									} else {
//										title = this.domNode.innerText;
//									}
//									if(this.isNull(title)) {
//										// 获取ID
//										title = domAttr.get(this.domNode, 'id');
//									}
//								}
//								domAttr.set(this.domNode, 'title', title);
//							}
//						},
						
						isNull : function(str) {
							return str == undefined || str == null || str == "";
						},

						startup : function() {
							if (this._started)
								return;

							if (this.align != 'center'&&this.iconDivNode)
								domStyle.set(this.iconDivNode, {
											"float" : this.align
										});
							if (this.iconDivNode) {
								domClass.add(this.iconDivNode, 'mui-scale');
								this.defer(lang.hitch(this, function() {
													domClass.remove(
															this.iconDivNode,
															'mui-scale');
												}), 500);
								if(!this.icon1){
									domStyle.set(this.iconDivNode,{'width':'0px'});
								}
							}
							this.inherited(arguments);
							
							
							// 设置title属性
//							this.setTitle();
							
							//修复iPhoneX点击穿透问题
							var self = this;
							setTimeout(function() {
								self._CLICK_FLAG = true;
							}, 300);
						},

						// 重写数字结构
						_setBadgeAttr : function(value) {
							if (!this.badgeObj) {
								this.badgeObj = new Badge();
								domStyle.set(this.badgeObj.domNode, {
											position : 'absolute',
											left : '50%',
											'margin-left':'0.6rem',
											top: 0
										});
							}
							// 出现三位数字显示为99+
							if (parseInt(value) >= 100)
								this.value = value = '99+';
							this.badgeObj.setValue(value);
							if (value) {
								this.iconDivNode
										.appendChild(this.badgeObj.domNode);
							} else {
								if (this.iconDivNode === this.badgeObj.domNode.parentNode) {
									this.iconDivNode
											.removeChild(this.badgeObj.domNode);
								}
							}
						},

						onClick : function() {
							
							//修复iPhoneX点击穿透问题
							if(!this._CLICK_FLAG) {
								return;
							}
							
							//让当前聚焦的dom失焦(单行失焦后才会重设value)，防止校验时获取的value不准确
							var activeElement = document.activeElement;
							if(activeElement && activeElement.blur){
								activeElement.blur();
							}
							var args = arguments;
							this.defer(function(){
								if (this.href) {
									location.href = util.formatUrl(this.href);
								}
								this.inherited(args);
							},350);
							if(this.href)
								return false;
						},
						
						_onTouchStart: function(e){
							var enclosingScrollable = viewRegistry.getEnclosingScrollable(this.domNode);
							if(enclosingScrollable &&
									domClass.contains(enclosingScrollable.containerNode, "mblScrollableScrollTo2")){
								domClass.remove(enclosingScrollable.containerNode, "mblScrollableScrollTo2");
								}
							this.inherited(arguments);
						},
						
						
						setDisabled :function(isDisabled){
							this.disabled = isDisabled;
							if(this.disabled){
								if(!this.__onClick)
									this.__onClick = this.onClick;
								this.onClick = function(){
									return false;
								};
								domClass.add(this.domNode,'muiBtnDisabled');
							}else{
								if(this.__onClick)
									this.onClick = this.__onClick;
								domClass.remove(this.domNode,'muiBtnDisabled');
							}
						},
						
						_onTouchMove: function(e){
							//覆盖_ItemBase的_onTouchMove方法，取消滚动超过4px则不触发_onClick的限制（部分机型点击很容易触发超过4px像素的滚动）
							
							//再次重写此方法，滚动超过8px则不触发_onClick事件
							var x = e.touches ? e.touches[0].pageX : e.clientX;
							var y = e.touches ? e.touches[0].pageY : e.clientY;
							
							if(Math.abs(x - this.touchStartX) >= 8 ||
							   Math.abs(y - this.touchStartY) >= 8){ // dojox/mobile/scrollable.threshold
								this.cancel();
								var p = this.getParent();
								if(p && p.selectOne){
									this._prevSel && this._prevSel.set("selected", true);
								}else{
									this.set("selected", false);
								}
							}
							
						},
						
						_onTouchEnd: function(e){
							var self = this;
							
							if(!self._selTimer && self._delayedSelection){ return; }
							
							self.cancel();

							//ios键盘弹出会导致窗口高度改变，因此先隐藏键盘再触发点击事件
							self._hideKeyBoard();
							// 兼容自定义表单的写法，相当恶心
							window.event = e;
							if (dojoConfig._native) {
								this.defer(function() {
									self._onClick(e);
								}, 300)
							} else {
								self._onClick(e);
							}	
							
						},
						
						_hideKeyBoard: function() {
							array.forEach(query('input,textarea') || [], function(ipt) {
								try {
									ipt.blur();
								} catch(e) { }
							});
						}
					});
		});