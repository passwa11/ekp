define(
		"km/imeeting/mobile/resource/js/list/TimeNavBar",
		[ "dojo/dom-construct", 'dojo/_base/declare', "dojo/dom-class",
			    "dojo/dom-style",
				"dojo/topic", "dojo/_base/lang", "dijit/_WidgetBase",
				"dijit/_Contained", "dijit/_Container",
				"dojox/mobile/SwapView", "dojo/_base/array",
				"mui/nav/_ShareNavBarMixin" ],
		function(domConstruct, declare, domClass, domStyle,
				topic, lang, WidgetBase, Contained, Container, SwapView, array,
				_ShareNavBarMixin) {
			var cls = declare(
					'km.imeeting.TimeNavBar',
					[ WidgetBase, Contained, Container,
							_ShareNavBarMixin ],
					{

						height : 'inherit',

						width : '100%',
						
						// 不显示滚动条
						scrollBar : false,

						buildRendering : function() {
							this.inherited(arguments);
							domClass.add(this.domNode, "muiMeetingBookTimeBar");
							domStyle.set(this.domNode, {
								overflow : 'hidden',
							});
							// 外围容器，滚动需要该特定名对象
							this.containerNode = domConstruct.create("ul", {
								className : ""
							}, this.domNode);
							for(var i = 8;i<=24;i++){
								this.textNode = domConstruct.create('li', {
									value:i,
									className:'',
									innerHTML:i+":00"
								}, this.containerNode);
							}
						
							this.setTransformMaxHeight();
							
							this.subscribe('/km/imeeting/content/moving','handlePlaceContentMoving');
							this.subscribe('/km/imeeting/place/onComplete', "handleSetTransformWidth");
							this.subscribe('/km/imeeting/timeNavBar/resetTransform', 'handleSetTransformWidth');
						},
						
						handleSetTransformWidth : function(){
							domStyle.set(this.containerNode,{
								"transform": "translate3d(0px, 0px, 0px)"
							});
						},
						
						setTransformMaxHeight:function(){
							this.transformMaxHeight = this.domNode.parentElement.offsetHeight - this.containerNode.offsetHeight - 40;
						},

						_setWidthAttr : function(width) {
							this.width = width;
						},

						startup : function() {
							if (this._started)
								return;
							this.inherited(arguments);
							var styleVar = {
								'height' : this.height+"px",
								'line-height' : this.height+"px",
							};
							domStyle.set(this.domNode, styleVar);
						},
						handlePlaceContentMoving:function(srcObj,evt){
							if(evt.y != null){
								/*
								if(this.translatevalue == 0){
									this.translatevalue = evt.y;
								}else{
									this.translatevalue = this.translatevalue + evt.y - this.lastvalue;
								}
								this.lastvalue = evt.y;
								var stylexxx = {
										"transform": "translate3d(0px, "+this.translatevalue+"px, 0px)"
								};
								if(this.translatevalue > 0){
									stylexxx = {
											"transform": "translate3d(0px, 0px, 0px)"
									};
								}
								if(this.translatevalue + 312 < 0){
									stylexxx = {
											"transform": "translate3d(0px, -312px, 0px)"
									};
									//domStyle.set(this.domNode, {"height":"335px"});
								}
								*/
								var stylexxx = {
										"transform": "translate3d(0px, "+evt.y+"px, 0px)"
								};
								
								/*
								if(evt.y >= 0){
									stylexxx = {
											"transform": "translate3d(0px, 0px, 0px)"
									};
								}
								if(evt.y <= this.transformMaxHeight){
									stylexxx = {
											"transform": "translate3d(0px,"+this.transformMaxHeight+"px, 0px)"
									};
								}
								*/
								
								domStyle.set(this.containerNode,stylexxx);
							}
						}
					});
			return cls;
		});