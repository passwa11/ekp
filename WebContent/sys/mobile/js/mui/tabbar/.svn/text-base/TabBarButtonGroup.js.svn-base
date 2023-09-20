define(	["dojo/_base/declare", "mui/tabbar/TabBarButton", "dojo/dom-class",
				"dojo/dom-style", "dojox/mobile/Tooltip", "dojo/dom-construct",
				"dijit/registry", "dojo/_base/lang", "dojo/dom-attr"], function(declare,
				TabBarButton, domClass, domStyle, Tooltip, domConstruct,
				registry, lang, domAttr) {

			return declare("mui.tabbar.TabBarButtonGroup", [TabBarButton], {

				// 列表滚动事件，用于隐藏弹出层
				adjustDestination : '/mui/list/adjustDestination',

				buildRendering : function() {
					// 构建弹出层
					this.openerContainer = new Tooltip();
					domConstruct.place(this.openerContainer.domNode,
							document.body, "last");
					domClass
							.add(this.openerContainer.domNode, 'muiNavBarGroup');
					var cover = this.openerContainer.containerNode;

					this.tabGroupContainer = domConstruct.create('div', {
								className : 'muiNavBarGroupContainer'
							}, cover);
					var children = this.srcNodeRef.children;
					while (children.length > 0) {
						domConstruct.place(children[0], this.tabGroupContainer,
								"last");
					}

					this.inherited(arguments);
					
					domClass.add(this.domNode,'muiBarFloatRightButton');
					
					if (!this.label)
						domClass.add(this.getParent().domNode,
								'muiNavBarButton');
					domAttr.set(this.domNode, 'title', '更多');
				},
				startup : function() {
					if (this._started)
						return;

					this.inherited(arguments);
					
					
					if (this.align != 'center')
						domStyle.set(this.iconDivNode, {
							"float" : 'none'
					});
					if(!this.icon1){
						domStyle.set(this.iconDivNode,{'width':'0px','display':'block'});
					}
					this.subscribe(this.adjustDestination, lang.hitch(this,
									this.hideOpener));
					
				},

				// 点击打开Opener
				_onClick : function(evt) {
					var opener = this.openerContainer;
					if (opener.resize){
						domClass.add(opener.domNode, 'muiTooltipHidden');
						this.defer(function(){
							this.hideOpener(this);
							domClass.remove(opener.domNode, 'muiTooltipHidden');
						},300);
					}else{
						opener.show(this.iconDivNode?this.iconDivNode:this.domNode, ['above']);
					}
					this.defaultClickAction(evt);
					this.handle = this.connect(document.body, 'touchend', 'unClick');
				},

				hideOpener : function(srcObj) {
					this.openerContainer.hide();
				},
				
				addExpButton:function(wgtBun, pos){
					if(wgtBun instanceof TabBarButton){
						domConstruct.place(wgtBun.domNode, this.tabGroupContainer, pos);
					}
				},
				
				// 点击页面其他地方隐藏弹出层
				unClick : function(evt) {
					var target = evt.target, isHide = true;
					while(target) {
						if (target == this.domNode) {
							isHide = false;
							break;
						}
						target = target.parentNode;
					}
					if (isHide){ 
						this.defer(function() {
							this.hideOpener();
							this.disconnect(this.handle);
						}, 400);
					}
				}
			});
		});