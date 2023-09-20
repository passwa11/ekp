define( "km/imeeting/mobile/resource/js/list/PlaceNavBar", 	[ "dojo/dom-construct", 'dojo/_base/declare', "dojo/dom-class",
			    "dojo/dom-style", "dojo/topic", "dojo/_base/lang", "dijit/_WidgetBase",
				"dijit/_Contained", "dijit/_Container",
				"dojox/mobile/SwapView", "dojo/_base/array",
				"mui/nav/_ShareNavBarMixin" ],
		function(domConstruct, declare, domClass, domStyle,
				topic, lang, WidgetBase, Contained, Container, SwapView, array,
				_ShareNavBarMixin) {
			var cls = declare(
					'km.imeeting.PlaceNavBar',
					[ WidgetBase, Contained, Container,
							_ShareNavBarMixin ],
					{

						height : 'inherit',

						width : '100%',
						
						scrollDir:'h',

						// 不显示滚动条
						scrollBar : false,
						
						transformMaxWidth:"",

						buildRendering : function() {
							this.inherited(arguments);
							domClass.add(this.domNode, "muiMeetingBookArea");
							domStyle.set(this.domNode, {
								overflow : 'hidden',
								top : 0
							});
							// 外围容器，滚动需要该特定名对象
							this.containerNode = domConstruct.create("ul", {
								className : ""
							}, this.domNode);
							this.transformMaxWidth = this.containerNode.offsetWidth - this.domNode.offsetWidth;
							this.subscribe('/km/imeeting/content/moving','handlePlaceContentMoving');
							this.subscribe('/km/imeeting/placeNavBar/resetTransform', 'handleSetTransformWidth');
						},
						
						_setWidthAttr : function(width) {
							this.width = width;
						},

						startup : function() {
							if (this._started)
								return;
							this.inherited(arguments);
							if (this.height == "inherit") {
								if (this.domNode.parentNode) {
									h = this.domNode.parentNode.style.height;
									this.height = h;
								}
							} else if (this.height) {
								h = this.height;
							}
							if (h && h != '') {
								var styleVar = {
									'height' : h,
									'line-height' : h
								};
								domStyle.set(this.domNode, styleVar);
								if (this.domNode != this.containerNode) {
									domStyle.set(this.containerNode, styleVar);
								}
							}
							this.subscribe('/km/imeeting/place/onComplete', "handleSetTransformWidth");
						},
						
						handleSetTransformWidth : function(){
							domStyle.set(this.containerNode,{
								"transform": "translate3d(0px, 0px, 0px)"
							});
							this.transformMaxWidth = this.containerNode.offsetWidth - this.domNode.offsetWidth;
						},
						
						handlePlaceContentMoving:function(srcObj,evt){
							if(evt.x != null){
								/*
								if(this.translatevalue == 0){
									this.translatevalue = evt.x;
								}else{
									this.translatevalue = this.translatevalue + evt.x - this.lastvalue;
								}
								this.lastvalue = evt.x;
								var stylexxx = {
										"transform": "translate3d("+this.translatevalue+"px, 0px, 0px)"
								};
								if(this.translatevalue > 0){
									stylexxx = {
											"transform": "translate3d(0px, 0px, 0px)"
									};
								}
								if(this.translatevalue + this.transformMaxWidth < 0){
									stylexxx = {
											"transform": "translate3d(-"+this.transformMaxWidth+"px, 0px, 0px)"
									};
								}
								*/
								
								var stylexxx = {
										"transform": "translate3d("+evt.x+"px, 0px, 0px)"
								};
								
								/*
								if(evt.x >= 0){
									stylexxx = {
											"transform": "translate3d(0px, 0px, 0px)"
									};
								}
								if(evt.x + this.transformMaxWidth <= 0){
									stylexxx = {
											"transform": "translate3d(-"+this.transformMaxWidth+"px, 0px, 0px)"
									};
								}
								*/
								
								//console.log(this.translatevalue);
								domStyle.set(this.containerNode,stylexxx);
							}
						}
					});
			return cls;
		});