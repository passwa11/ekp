define(	["dojo/_base/declare", 
       	 "dojox/mobile/_ItemBase", 
       	 "dojo/dom-construct",
       	 "dojo/dom-class", 
       	 "dojo/dom-style", 
       	 "dojo/topic",
       	 "mui/util"], function(
				declare, ItemBase, domConstruct, domClass, domStyle, topic, util) {

			return declare("mui.tabfilter.TabfilterLeftItemMixin", [ItemBase], {
				
				tag : 'li',
				
				isActive : false,
				
				itemsName : "tags",
				
				buildRendering : function() {
					this.domNode = this.containerNode = this.srcNodeRef
								|| domConstruct.create(this.tag, {
								className : 'tabfilterLeftItem'
							});
					this.inherited(arguments);
					if(this.categoryName) {
						
						domConstruct.create("span", {
							innerHTML : "<label>" + util.formatText(this.categoryName) +"</label>"
						}, this.domNode);
					}
					
					this.connect(this.domNode, "click","selectCate");
				},
				
				selectCate : function(evt) {
					if(evt){
						if (evt.stopPropagation)
							evt.stopPropagation();
						if (evt.cancelBubble)
							evt.cancelBubble = true;
						if (evt.preventDefault)
							evt.preventDefault();
						if (evt.returnValue)
							evt.returnValue = false;
					}
					if(this.isActive) {
						return;
					}
					var evt = {
						items : this[this.itemsName],
						categoryName : this.categoryName
					}
					topic.publish("/mui/tabfilter/left/change", this, evt);

					this.isActive = true;
					domClass.add(this.domNode, "active");
				},
				
				_otherCateSelected : function() {
					if(this.isActive) {
						this.isActive = false;
						domClass.remove(this.domNode, "active");
					}
				},
				
				postCreate : function() {
					this.inherited(arguments);
					this.subscribe("/mui/tabfilter/left/change", "_otherCateSelected");
				},
				
				startup : function() {
					if (this._started) {
						return;
					}
					this.inherited(arguments);
				},

				_setLabelAttr : function(text) {
					if (text)
						this._set("label", text);
				}
			});
		});