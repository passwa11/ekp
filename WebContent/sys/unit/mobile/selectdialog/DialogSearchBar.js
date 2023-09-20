define(
		[ "dojo/_base/declare","dojo/topic", "dijit/registry",
			"dojo/dom-style",
			"dojo/dom-attr",
				"dojox/mobile/TransitionEvent", "mui/search/SearchBar",
				"./DialogChannelMixin", "mui/i18n/i18n!sys-mobile" ],
		function(declare,topic, registry, domStyle, domAttr, TransitionEvent, SearchBar,
				DialogChannelMixin, Msg) {
			function debounce(func, wait, options) {
				var timer = null;
				return function() {
					var context = options.context || this;
					var args = arguments;
					if (timer) {
						clearTimeout(timer)
					}
					timer = setTimeout(function() {
						func.apply(context, args)
					}, wait)
				}
			}
			return declare(
					"mui.selectdialog.DialogSearchBar",
					[ SearchBar, DialogChannelMixin ],
					{

						// 搜索请求地址
						searchUrl : null,
						// 搜索结果直接挑转至searchURL界面
						jumpToSearchUrl : false,

						// 搜索关键字
						keyword : "",

						// 例外类别id
						exceptValue : '',

						// 提示文字
						placeHolder : Msg['mui.search.search'],

						// 是否需要输入提醒
						needPrompt : false,

						orgType : null,
						// 前视图
						previousView : null,

						buildRendering : function() {
							this.inherited(arguments);
						},

						postMixInProperties : function() {
							this.inherited(arguments);
							this.placeHolder = this.getPlaceHolder();
						},

						postCreate : function() {
							this.inherited(arguments);
							this.connect(this.searchNode, "onclick",
									"_onOpenSearchView");
							this.connect(this.searchNode, "oninput", debounce(
									this._onSearch, 500, {
										context : this
									}));
							this.subscribe('/mui/unit/swapView',
									"_onChangeView");
							this.subscribe('/mui/unit/search/submit',
									"_onHandleSearchNode");
						},

						_onOpenSearchView : function() {
							var searchView = this.getSearchView();
							var showingView = searchView.getShowingView();
							var showingViewID = showingView.id;
							// 默认视图才需要切换搜索至搜索视图，其余视图停留在原地。这里的处理有点拙劣，要改...
							if (showingViewID.startsWith('defaultView')) {
								new TransitionEvent(document.body, {
									moveTo : searchView.id
								}).dispatch();
							}
							if (searchView !== showingView) {
								this.previousView = showingView;
								topic.publish("/mui/unit/search/init", this);
							}
						},

						_onChangeView : function(srcObj, evt) {
							if (srcObj.key === this.key) {
								if (evt && evt.viewKey != 'allUnitView') {
									domAttr
											.set(
													this.searchNode,
													'placeHolder',
													Msg['mui.mobile.address.search.simple']);
								} else {
									domAttr.set(this.searchNode, 'placeHolder',
											this.getPlaceHolder());
								}
							}
						},

						_onCancel : function(evt) {
							this._onClear(evt)
							domStyle.set(this.buttonArea, {
								display : "none"
							});
							if (this.previousView) {
								new TransitionEvent(document.body, {
									moveTo : this.previousView.id
								}).dispatch();
								topic.publish("/mui/unit/search/cancel", this);
							}
						},

						_onSearch : function(evt) {
							this._eventStop(evt);
							this.set("keyword", this.searchNode.value);
							if (this.keyword) {
								topic
										.publish(
												"/mui/unit/search/submit",
												this,
												{
													// 关键字
													keyword : decodeURIComponent(this.keyword
															.replace(/%/g,
																	'%25'))
												});
							}
							return false
						},

						_onHandleSearchNode : function(evt) {
							var value = this.searchNode.value;
							if (evt.keyword !== value) {
								this.searchNode.value = evt.keyword;
							}
						},

						getSearchView : function() {
							var searchViewID = 'searchView_' + this.key;
							var searchView = registry.byId(searchViewID);
							return searchView;
						},

						getPlaceHolder : function() {
							// 根据orgType来决定placeHolder提示语
							var placeHolder = Msg['mui.form.please.input'];
							return placeHolder.replace(/\/$/, '');
						}
					});
		});
