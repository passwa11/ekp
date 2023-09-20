define([ "dojo/_base/declare", "dojo/dom-construct", "dojo/html",
		"dojox/mobile/_css3", "dojo/dom-style", "dojo/_base/lang",
		"dojo/topic", "dojo/_base/array",
		"mui/tabfilter/TabfilterListSelection" ], function(declare,
		domConstruct, html, css3, domStyle, lang, topic, array,
		TabfilterListSelection) {
	var claz = declare("mui.tabfilter.TabfilterTagDialogMixin", null, {
		modelName : null,

		key : "",

		values : "",

		texts : "",

		buildRendering : function() {
			this.inherited(arguments);
			this.subscribe('/mui/tabfilter/cancel', 'hideFilter');
			this.subscribe("/mui/tabfilter/submit", "_submitSelect");
			this.subscribe("/mui/tabfilter/selChanged/delete", "_selDel");
		},

		startup : function() {
			this.inherited(arguments);
			this._init();
		},

		_clearVal : function() {
			this.values = "";
			this.texts = "";
		},

		_submitSelect : function(obj, evt) {
			if (evt) {
				this.values = evt.values;
				this.texts = evt.texts;
			}

			this.hideFilter();
			topic.publish("/mui/tabfilter/dialog/submit", this, {
				texts : this.texts,
				values : this.values
			});
		},

		//进入页面直接弹出选择框
		_init : function() {
			if (this.isInit == "true") {
				this.show();
			}
		},

		_selDel : function(obj, evt) {
			if (obj.isInstanceOf(TabfilterListSelection)) {
				if (evt) {
					this.values = evt.values;
					this.texts = evt.texts;
				}
			}
		},

		//显示
		show : function(evt) {
			this.openFilter(true);
		},

		hideFilter : function() {
			if (!this.dialogDiv)
				return;
			var tmpStyle = {};
			tmpStyle[css3.name('transform')] = 'translate3d(100%, 0, 0)';
			domStyle.set(this.dialogDiv, tmpStyle);
			this.defer(function() {
				if (this.parseResults && this.parseResults.length) {
					array.forEach(this.parseResults, function(w) {
						if (w.destroy) {
							w.destroy();
						}
					});
					delete this.parseResults;
				}
				domConstruct.destroy(this.dialogDiv);
				this.dialogDiv = null;
				this._working = false;
			}, 450);
		},

		showFilter : function(isNotAnimation) {
			this._working = true;
			if (!this.dialogDiv)
				return;
			var tmpStyle = {};
			tmpStyle[css3.name('transform')] = 'translate3d(0, 0, 0)';
			domStyle.set(this.dialogDiv, tmpStyle);
		},

		openFilter : function(isNotAnimation) {
			var self = this;
			require([ 'dojo/text!mui/tabfilter/tag/filter.jsp?modelName='
					+ this.modelName + '&key=' + this.key ], function(tmpl) {
				self.dialogDiv = domConstruct.create("div", {
					className : 'muiTabfilterDialog'
				}, document.body, 'last');
				var dhs = new html._ContentSetter({
					node : self.dialogDiv,
					parseContent : true,
					cleanContent : true,
					onBegin : function() {
						this.content = lang.replace(this.content, {
							curValues : self.values,
							curTexts : self.texts
						});
						this.inherited("onBegin", arguments);
					}
				});
				dhs.set(tmpl);
				dhs.parseDeferred.then(function(results) {
					self.parseResults = results;
					self.showFilter(isNotAnimation);
				});
				dhs.tearDown();
			});
		}
	});
	return claz;
});