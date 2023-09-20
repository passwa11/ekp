define( [ "dojo/_base/array", "dojo/_base/declare", "dojo/_base/lang","dojo/dom-class",
		"dojo/dom-construct", "dojo/dom-attr", "dojo/dom-style", "dijit/_WidgetBase","dojo/topic"], function(array,
		declare, lang, domClass, domConstruct, domAttr, domStyle, WidgetBase, topic) {
	return declare("sys.xform.mobile.controls.fSelect.FSelectListItem",
			[ WidgetBase ], {
				key:null,
				
				isMul:true,

				data:[],
				
				headers:[],
				
				selected: false,

				baseClass : "muiFSelectItem",
				
				CHECKED_EVENT : "/sys/xform/fSelect/checked",
				
				SELECTALL_EVENT : "/sys/xform/fSelect/selectAll",
				
				UNCHECKED_EVENT : "/sys/xform/fSelect/unchecked",
				
				uuid:null,

				//生成唯一标示
				guid:(function() {
	                var counter = 0;
	                return function( prefix ) {
	                    var guid = (+ new Date().getTime()).toString( 32 ),
	                        i = 0;
	                    for ( ; i < 5; i++ ) {
	                        guid += Math.floor( Math.random() * 65535 ).toString( 32 );
	                    }
	                    return (prefix || 'event_') + guid + (counter++).toString( 32 );                  
	                };
	            })(),
	            
				buildRendering : function() {
					this.inherited(arguments);
					this.contentNode = domConstruct.create("div", {className:"muiFSelectContent"}, this.domNode);
					this.uuid =  this.guid();
				},

				postCreate : function() {
					this.inherited(arguments);
					this.subscribe(this.CHECKED_EVENT, "_setSelected");
					this.subscribe(this.SELECTALL_EVENT, "_setSelectAll"); //全选按钮事件
					this.connect(this.contentNode, "click", function(evt){
						var now = new Date().getTime();
						if(this._lastClickTime){//避免两次连击
							if((now - this._lastClickTime) <500){
								return;
							}
							this._lastClickTime = now;
						}
						this._lastClickTime = now;
						this.defer(function(evt){
							this._selectData(evt);
						},350);
					});
				},

				startup : function() {
					this.inherited(arguments);
					array.forEach(this.data.itemVals,function(tmpVal,idx){
						var eItem = domConstruct.create("div", {className:"muiFSelectItem"}, this.contentNode);
						var info = domConstruct.create("div", {className:"muiFSelectInfo"}, eItem);
						if(idx==0){
							var areaDiv  = domConstruct.create("div", {className:"muiFSelectSelArea " + (this.isMul?"muiFSelectSelMul":"muiFSelectSelSgl")}, info);
							this.selNode = domConstruct.create("div", {className:"muiFSelectSel"}, areaDiv);
						}
						this.valDom = domConstruct.create("div", {className:"muiFSelectValue",innerHTML:tmpVal}, info);
					},this);
				},
				_setSelected:function(srcObj){
					if(this.key==srcObj.key){
						if(this.isMul==false && this.uuid!=srcObj.uuid){
							if(this.selected){
								this.selected=false;
								domConstruct.empty(this.selNode);
								domClass.remove(this.valDom,"muiFSelectSeled");
								domClass.remove(this.selNode,"muiFSelectSeled");
								topic.publish(this.UNCHECKED_EVENT,this);
							}
						}
					}
				},
				_selectData:function(evt){
					var parent = this.getParent();
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
							}, this.selNode);
						 domClass.add(this.valDom,"muiFSelectSeled");
						 domClass.add(this.selNode,"muiFSelectSeled");
						 //发布选中事件
						 topic.publish(this.CHECKED_EVENT,this);
					}else{
						domConstruct.empty(this.selNode);
						domClass.remove(this.valDom,"muiFSelectSeled");
						domClass.remove(this.selNode,"muiFSelectSeled");
						 //发布取消选中事件
						topic.publish(this.UNCHECKED_EVENT,this);
					}
				},
				_setSelectAll:function(srcObj){//监听全选按钮选中事件
					if(this.key==srcObj.key && this.contentNode.parentNode.style.display !='none'){
						if(srcObj.selected){ //如果全选,则设置没有选中的选项
							if(!this.selected){
								this.selected = !this.selected;
								domConstruct.create('i', {
									'className' : 'fontmuis muis-form-selected-cor'
								}, this.selNode);
								domClass.add(this.valDom,"muiFSelectSeled");
								domClass.add(this.selNode,"muiFSelectSeled");
								topic.publish(this.CHECKED_EVENT,this);
							}
						}else{
							if (this.selected){//如果取消全选,则取消选中的选项
								this.selected = !this.selected;
								domConstruct.empty(this.selNode);
								domClass.remove(this.valDom,"muiFSelectSeled");
								domClass.remove(this.selNode,"muiFSelectSeled");
								topic.publish(this.UNCHECKED_EVENT,this);
							}
						}
					}
				}
		});
});
