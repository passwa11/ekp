define( [ "dojo/_base/declare", "dijit/_WidgetBase" , "dojo/dom-construct" ,
          "dojo/dom-style" , "dojo/request" , "dojo/topic" , "mui/util","mui/i18n/i18n!sys-mobile"], function(declare,
		WidgetBase, domConstruct, domStyle, request, topic, util, Msg) {
		var header = declare("mui.commondialog.template.DialogHeader", [ WidgetBase], {
				//标示
				key : null,
			
				height: "inherit",
				
				title: Msg['mui.category.pselect'],
				
				baseClass:"muiCateHeader",
				
				buildRendering : function() {
					this.inherited(arguments);
					this.contentNode = domConstruct.create('div',{"className":"muiCateHeaderContent"},this.domNode);
					this.titleDom = domConstruct.create('div',{"className":"muiCateHeaderTitle",innerHTML:this.title},this.contentNode);
					this.cancelNode = domConstruct.create('div',{"className":"muiCateHeaderCancel",innerHTML:Msg['mui.category.button.cancel']},this.contentNode);
				},

				postCreate : function() {
					this.inherited(arguments);
					this.connect(this.cancelNode,'click', function(){
						this.defer(function(){
							this._goBack();
						},350);
					});
				},
				
				startup : function() {
					if (this._started) {
						return;
					}
					this.inherited(arguments);
					if (this.domNode.parentNode) {
						var h;
						if (this.height === "inherit") {
							if (this.domNode.parentNode) {
								h = this.domNode.parentNode.offsetHeight + "px";
							}
						} else if (this.height) {
							h = this.height;
						}
						domStyle.set(this.domNode,{'height':h,'line-height':h});
					}
					
				},
				
				_goBack : function(){
					topic.publish("/mui/category/cancel" , this);
				}
			});
			return header;
});