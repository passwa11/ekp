define(	["dojo/_base/declare", "dijit/_WidgetBase", "dojo/_base/lang",
				"dojo/dom-class", "dojo/dom-construct", "dojo/dom-style",
				"mui/dialog/_DialogBase",
				"dojo/html","dojo/window",
		        "dojo/touch",
		        "dojo/_base/event",
		        "dojo/on"], function(declare, WidgetBase, lang, domClass,
				domConstruct, domStyle, _DialogBase, html, win, touch, event, on) {

			var claz = declare('mui.dialog.Tip', [_DialogBase], {

						icon : null,
						
						text : null,
						
						time : 2300,
						
						callback : null,
						
						cover: false,
						
						coverNode: null,
						
						width : null,
						
						height : null,

						buildRendering : function() {
							this.textNode = domConstruct.create('span', {
										innerHTML : this.text
									});
							if(this.icon){
								this.iconNode = domConstruct.create('div', {
											className : this.icon + ' muiDialogBaseStyle'
										});
							}
							this.inherited(arguments);
						},
						
						_initCoverNode: function() {
							if (this.coverNode) {
								return;
							}
							this.coverNode = domConstruct.create("div",{className:'muiButtonAfter'}, document.body,'last');
							on(this.coverNode, touch.press, event.stop);
							on(this.coverNode, touch.move, event.stop);
							on(this.coverNode, touch.release, event.stop);
							on(this.coverNode, touch.cancel, event.stop);
						},

						_create:function(){
							var self = this;
							this.containerNode = domConstruct.create('div', {
								'className' : 'muiDialogTip'
								}, document.body, 'last');
							domStyle.set(this.containerNode, {
								opacity : 0
							});
							var winBox = win.getBox();
							require(["dojo/text!mui/dialog/tip.tmpl"], function(tmpl){
								var dhs = new html._ContentSetter({
									parseContent : true,
									onBegin : function() {
										this.content = this.content.replace(/!{text}/g,
												self.textNode.outerHTML);
										if(self.iconNode){
											this.content = this.content.replace(/!{icon}/g,
													self.iconNode.outerHTML);
										}else{
											this.content = this.content.replace(/!{icon}/g,'');
										}
										this.inherited("onBegin",arguments);
									}
								});
								dhs.node = self.containerNode;
								dhs.set(tmpl);
								domStyle.set(self.containerNode,{'max-width':(winBox.w*0.75) + 'px','max-height':winBox.h + 'px'});
								if(self.width){
									domStyle.set(self.containerNode,{'width':self.width + 'px'});
								}
								if(self.height){
									domStyle.set(self.containerNode,{'height':self.height + 'px'});
								}
								
								dhs.parseDeferred.then(function() {
									/**
									 * 采用css进行居中定位，并采用flex布局
									 * 修改文件 /sys/mobile/css/themes/default/dialog.css
									 * 2018-02-24 彭伟聪
									 */ 
//									self.defer(function(){//bug 加延时的原因是ios渲染速度慢致使left,top计算不准确
//										
//										var left = (winBox.w - self.containerNode.offsetWidth)/2;
//										var top = (winBox.h - self.containerNode.offsetHeight)/2;
//										domStyle.set(self.containerNode,{'top':top + 'px', 'left': left + 'px'});
//									},320);
									
								});
								dhs.tearDown();
							});
						},
						
						show : function() {
							if(!this.containerNode){
								this._create();
							}
							domStyle.set(this.domNode, {
								display : ""
							});
							domStyle.set(this.containerNode, {
										"opacity": 1,
										"z-index":99999
									});
							if (this.cover) {
								this._initCoverNode();
								domStyle.set(this.coverNode, "display", "");
							}
							if (this.time > -1) {
								setTimeout(lang.hitch(this, this.hide), this.time);
							}
							return this.inherited(arguments);
						},

						hide : function(destroy) {
							if(this.containerNode)
								domStyle.set(this.containerNode, {
										"opacity": 0,
										"z-index":-1
									});
							domStyle.set(this.domNode, {
								display : "none"
							});
							if (this.cover) {
								this._initCoverNode();
								domStyle.set(this.coverNode, "display", "none");
							}
							if (this.callback)
								this.callback.call();
							this.inherited(arguments);
							if (destroy === false)
								return;
							setTimeout(lang.hitch(this, this.destroy), 1000);
						}
					});
			
			var pTip = declare([claz], {
				icon: "fontmuis muis-tips-loading mui-spin",
				time: -1, 
				cover: true,
				text: "处理中...",
				hide : function() {
					this.inherited(arguments, [false]);
				}
			});
			
			var _processing = new pTip();
			
			setTimeout(function() {_processing.hide();}, 500); // 需要保证计算位置准确

			return {
				tip : function(options) {
					return new claz(options).show();
				},
				success : function(options) {
					return new claz(lang.mixin(options, {
								icon : 'fontmuis muis-tips-success '
							})).show();
				},
				fail : function(options) {
					return new claz(lang.mixin(options, {
								icon : 'fontmuis muis-tips-failed '
							})).show();
				},
				warn : function(options) {
					return new claz(lang.mixin(options, {
								icon : 'fontmuis muis-tips-warning '
							})).show();
				},
				processing: function(text) {
					if (text)
						_processing.text = text;
					return _processing;
				},
				progressing: function(options) {
					return new pTip(lang.mixin(options, {})).show();
				},
				Tip: claz 
			};

		})