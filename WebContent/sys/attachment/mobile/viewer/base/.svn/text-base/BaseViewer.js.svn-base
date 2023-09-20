define(
		[ "dojo/_base/declare", "dojo/_base/lang", "dijit/_TemplatedMixin",
				"dojo/_base/window", "dojo/dom-style", "dijit/_WidgetBase",
				"mui/util", "mui/device/device", "mui/device/adapter",
				"dojo/topic", "dojox/mobile/View", "dojo/query",
				"dojo/dom-construct", "dojo/touch" ],
		function(declare, lang, _TemplatedMixin, win, domStyle, _WidgetBase,
				util, device, adapter, topic, View, query, domConstruct, touch) {

			return declare(
					"sys.attachment.mobile.BaseViewer",
					[ _WidgetBase, _TemplatedMixin ],
					{

						templateString : null,

						fdId : null,

						viewerParam : null,

						viewerStyle : null,

						converterKey : null,

						waterMarkConfig : null,

						fileKeySufix : null,

						highFidelity : null,
						
						scaleStr : null,

						isShow : true,

						//backTo : null,

						scalable : true,

						_initViewportFlag : 0,//是否已初始化

						buildRendering : function() {
							this.viewPortChange(this, 1);
							this._initViewportFlag = 1;
							this.inherited(arguments);
							if (this.pageBackScale) {
								this.connect(this.pageBackScale, touch.press,
										'onBack');
							}
						},

						postCreate : function() {
							this.inherited(arguments);
							// this.subscribe('/mui/attachment/viewer/viewport/change',
							// 'viewPortChange');
						},

						startup : function() {
							this.inherited(arguments);
							this.calcPageBarStyle();
						},

						getUrl : function() {
							return util
									.formatUrl('/sys/attachment/sys_att_main/sysAttMain.do?method=view&viewer=mobilehtmlviewer&fdId=' + this.fdId);
						},

						getFileName : function(i) {
							return this.converterKey
									+ "_page-"
									+ i
									+ ((this.fileKeySufix == null
											|| this.fileKeySufix == "null" || this.fileKeySufix == "") ? ""
											: this.fileKeySufix);
						},

						getSrc : function(num) {
							return this.getUrl() + '&filekey='
									+ this.getFileName(num ? num : 1) + '&pageNum=' + (num?num:1);
						},
						//缩放时头部操作区域适应
						calcPageBarStyle : function() {
							if (this.pageBarScale && this.scalable) {
								if (device.getClientType() > 1) {//非web页
									domStyle.set(this.pageBarScale, {
										"display" : 'none'
									});
									return;
								}
								var scale = (window.devicePixelRatio + 0.2);
								if (!scale)
									scale = 3;
								if (scale != 1) {//andriod下不同
									domStyle
											.set(
													this.pageBarScale,
													{
														"height" : this.pageBarScale.offsetHeight
																* scale + 'px'
													});
									query("*[mui_scale_mark='scale']",
											this.pageBarScale)
											.forEach(
													lang
															.hitch(
																	this,
																	function(
																			tmpNode) {
																		this
																				.reStyleDom(
																						tmpNode,
																						scale);
																	}));
								}
							}
						},
						reStyleDom : function(dom, scale) {
							//pageBarScale  width  ,height line-height font-size
						var width = dom.offsetWidth, height = dom.offsetHeight, fontSize = parseInt(
								domStyle.get(dom, 'fontSize').replace("px", ''),
								10);
						domStyle.set(dom, {
							'width' : width * scale + 'px',
							'height' : height * scale + 'px',
							"line-height" : height * scale + 'px',
							"font-size" : fontSize * scale + 'px'
						});
					},
					//chgFlag 1 缩放  ， 0适配
						viewPortChange : function(srcObj, chgFlag) {
							if (this.scalable) {//强制允许缩放
								if (srcObj == this
										|| this._initViewportFlag == 1) {
									var viewPort = document
											.getElementsByName("viewport");
									if (chgFlag == 0) {
										if (window.__last_viewport_) {
											viewPort[0].content = window.__last_viewport_;
											window.__last_viewport_ = "";
											topic
													.publish(
															"/mui/attachment/viewer/reset",
															this);
										}
									} else if (chgFlag == 1) {
										window.__last_viewport_ = viewPort[0].content;
										viewPort[0].content = "";
										if (this.onScroll)
											this.scrollEvent = this.connect(
													window, 'scroll',
													'onScroll');
									}
								}
							}
						},
						//后退
						onBack : function(evt) {
							/*var tmpParent = this.getParent();
							if(tmpParent && (tmpParent instanceof View) && this.backTo){
								this.defer(function(){
									tmpParent.performTransition(this.backTo, -1, "slide");
									this.viewPortChange(this,0);
								},350);
							}*/
							var rtn = adapter.goBack();
							if (rtn == null) {
								history.back();
							}
						}
					});
		});
