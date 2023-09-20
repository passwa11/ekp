define( [ "dojo/_base/array", "dojo/_base/declare", "dojo/_base/lang","dojo/dom-class",
		"dojo/dom-construct", "dojo/dom-attr", "dojo/dom-style", "dijit/_WidgetBase","dojo/topic",
		"dojo/query","dijit/registry","mui/i18n/i18n!sys-xform-base:mui"], function(array,
		declare, lang, domClass, domConstruct, domAttr, domStyle, WidgetBase, topic,query,registry,Msg) {
	return declare("sys.xform.mobile.controls.event.EventDataSelectAllCheckBox",
			[ WidgetBase ], {
				key:null,
				
				isMul:false,
				
				selected: false,
				
				isSelectAll : true,
	            
				buildRendering : function() {
					this.inherited(arguments);
					this.selectAllCheckboxArea = domConstruct.create("div",{'className':'muiFSelectSelectAllCheckboxArea'},this.domNode);
					this.selectAllCheckbox = domConstruct.create("div",{'className':'muiFSelectSelectAllCheckbox'},this.selectAllCheckboxArea);
					this.selectAll = domConstruct.create("span",{
						'innerHTML' : Msg["mui.event.selectAll"],
						className : "muiFSelectAllText"
					},this.selectAllCheckboxArea);
				},

				postCreate : function() {
					this.inherited(arguments);
					this.subscribe("/sys/xform/fSelect/checked","_checked");
					this.subscribe("/sys/xform/fSelect/unchecked","unchecked");
					this.subscribe("/sys/xform/fSelect/searchData","unchecked");
					this.subscribe("/sys/xform/fSelect/unSelectAll","unchecked");
					this.connect(this.selectAllCheckboxArea, "click", function(evt){
						var now = new Date().getTime();
						if(this._lastClickTime){//避免两次连击
							if((now - this._lastClickTime) <500){
								return;
							}
							this._lastClickTime = now;
						}
						this._lastClickTime = now;
						this.defer(function(evt){
							this._select(evt);
						},350);
					});
				},

				startup : function() {
					this.inherited(arguments);
				},
				_checked : function(srcObj){
					var parent = srcObj.getParent();
					if (parent == null){
						return;
					}
					var isCheckAll = true;
					if (srcObj.key == this.key && srcObj != this){
						var domNode = parent.get("domNode") || {};
						var listItem = domNode.children || [];
						for (var i = 0;i < listItem.length; i++){
							var wgt = registry.byNode(listItem[i]);
							if (wgt && wgt.get("selected") === false ){
								isCheckAll = false;
								break;
							}
						}
						if (isCheckAll && !this.selected ){ //如果全部选中,设置全选按钮勾选
							this.selected = !this.selected;
							domConstruct.create('i', {
								'className' : 'fontmuis muis-form-selected-cor'
							}, this.selectAllCheckbox);
							domClass.add(this.selectAllCheckbox,"selected");
						}
					}
					
				},
				unchecked : function(srcObj){ //如果有选项取消选中,并且全选按钮又是选中,则取消选中
					if (srcObj.key == this.key && this.selected){
						this.selected = !this.selected;
						domConstruct.empty(this.selectAllCheckbox);
						domClass.remove(this.selectAllCheckbox,"selected");
					}
				},
				_select:function(evt){ //全选按钮点击事件
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
					this.selected = !this.selected;
					if(this.selected){
						 domConstruct.create('i', {
								'className' : 'fontmuis muis-form-selected-cor'
							}, this.selectAllCheckbox);
						 domClass.add(this.selectAllCheckbox,"selected");
					}else{
						domConstruct.empty(this.selectAllCheckbox);
						domClass.remove(this.selectAllCheckbox,"selected");
					}
					topic.publish("/sys/xform/fSelect/selectAll",this); //发布全选事件
				}
		});
});
