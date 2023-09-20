define(	["dojo/_base/declare", 
       	 "dojox/mobile/_ItemBase", 
       	 "dojo/dom-construct",
       	 "dojo/dom-class", 
       	 "dojo/dom-style", 
       	 "dojo/topic",
       	 "mui/util",
       	"dojo/_base/array"], function(
				declare, ItemBase, domConstruct, domClass, domStyle, topic, util, array) {

			return declare("mui.tabfilter.TabfilterRightItemMixin", [ItemBase], {
				
				tag : 'li',
				
				selected : false,
				
				_enterClass : "mblListItemSelected",
					
				_selectClass : "selected",
				
				buildRendering : function() {
					this.domNode = this.containerNode = this.srcNodeRef
								|| domConstruct.create(this.tag, {
								className : 'tabfilterRightItem'
							});
					this.inherited(arguments);
					if(this.text) {
						domConstruct.create("label", {
							innerHTML :  util.formatText(this.text)
						}, this.domNode);
						domConstruct.create("span", {
							innerHTML :"<i class= 'mui mui-right'></i>",
						},this.domNode);
					}
					
					this.connect(this.domNode, "click", "_selectNode");
				},
				
				postCreate:function() {
					this.inherited(arguments);
					this.subscribe("/mui/tabfilter/item/unselect", "_unselect");
				},
				
				_selectNode : function() {
					this.set("entered", true);
					this.defer(function(){
						this.set("entered", false);
					},150);
					
					if(this.selected) {
						this._unselect();
 					} else {
 						this._select();
 					}
				},
				
				//选中
				_select : function(obj, evt) {
					if(obj && obj === this) {//自身发的事件
						return;
					}
					if(obj && obj !== this) { //接收事件的
						if(evt && evt.value == this.value) {
							 this._selectWithoutPublish();
						}
					} else {//自身点击的
						this._selectWithoutPublish();
						topic.publish("/mui/tabfilter/item/select", this , {
							text : this.text,
							value : this.value
						});
					}
				},
				
				//取消选中
				_unselect : function(obj , evt) {
					if(obj && obj === this) {//自身发的事件
						return;
					}
					if(obj && obj !== this) {//接收事件
						if(evt && evt.value == this.value) {
							this._unselectWithoutPublish();
						}
					} else {//自身点击
						this._unselectWithoutPublish();
						topic.publish("/mui/tabfilter/item/unselect", this , {
							text : this.text,
							value : this.value
						});
					}
				},
				
				_selectWithoutPublish : function() {
					this.selected = true;
					domClass.add(this.domNode, this._selectClass);
				},
				
				_unselectWithoutPublish : function() {
					this.selected = false;
					domClass.remove(this.domNode, this._selectClass);
				},
				
				isSelected : function() {
					var pWeiget = this.getParent();
					if(pWeiget && pWeiget.curValues){
						var arrs = pWeiget.curValues.split(/[\s+;,]/);
						if (array.indexOf(arrs, this.value) > -1) {
							return true;
						}
					}
					return false;
				},
				
				startup : function() {
					if (this._started) {
						return;
					}
					this.inherited(arguments);
					//初始化
					if(this.isSelected()) {
						this._selectWithoutPublish();
					}
				},

				_setLabelAttr : function(text) {
					if (text)
						this._set("label", text);
				},
				
				_setEnteredAttr: function(entered){
					domClass.toggle(this.domNode, this._enterClass, entered);
				}
			});
		});