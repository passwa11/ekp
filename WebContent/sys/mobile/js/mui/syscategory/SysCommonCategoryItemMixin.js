define(	["dojo/_base/declare", "dojo/_base/array","dojo/topic", "dojo/dom-construct", "dojo/dom-class","dojo/dom-style", "mui/iconUtils" , "mui/category/CategoryItemMixin"],
		function(declare, array, topic, domConstruct,domClass, domStyle, iconUtils, CategoryItemMixin) {
			var item = declare("mui.syscategory.SysCommonCategoryItemMixin", [CategoryItemMixin], {

				buildRendering:function(){
					this.fdId = this.value;
					this.label = this.text;
					this.canSelect = this.isShowCheckBox;
					this.icon  = '';
					this.type = this.nodeType=='CATEGORY'?window.SYS_CATEGORY_TYPE_CATEGORY:window.SYS_CATEGORY_TYPE_TEMPLATE;
					this.inherited(arguments);
				},
				
				getTitle:function(){
					return this.label;
				},
				
				postCreate : function() {
					this.inherited(arguments);
					this.subscribe('/mui/category/cate_selected','selected');
					this.subscribe('/mui/category/cate_unselected','unselected');
				},
				
				selected:function(srcObj,evt){
					if(srcObj.key==this.key){
						if(evt && evt.fdId){
							if(evt.fdId==this.fdId){
							 	// domClass.add(this.selectNode,"muiCateSeled");
								// 存储在当前选中的fdId，在window中_curIds，供别处调用
								window._curIds = this.fdId;
							 	domClass.add(this.selectNode,"muiCateSeled");
								 if(!this.checkedIcon){
									 this.checkedIcon= domConstruct.create('i', {
											'className' : 'mui mui-checked muiCateSelected'
										}, this.selectNode);
								 }
								this.set("entered", true);
								this.defer(function(){
									this.set("entered", false);
								},200);
							}
						}
					}
				},
				unselected:function(srcObj,evt){
					if(srcObj.key==this.key){
						if(evt && evt.fdId){
							if(evt.fdId.indexOf(this.fdId)>-1){
								//取消选中，清除window._curIds值
								window._curIds = "-";
								if(this.checkedIcon){
									 domClass.remove(this.selectNode,"muiCateSeled");
									 domConstruct.destroy(this.checkedIcon);
									 this.checkedIcon= null;
								}
							}
						}
					}
				},
				
				
				_setSelected:function(srcObj,evt){
					if(srcObj.key==this.key){
						if(evt && evt.fdId){
							if(evt.fdId==this.fdId){
								// 存储在当前选中的fdId，在window中_curIds，供别处调用
								window._curIds = this.fdId;
								domClass.add(this.selectNode,"muiCateSeled");
								if(!this.checkedIcon){
									 this.checkedIcon= domConstruct.create('i', {
											'className' : 'mui mui-checked muiCateSelected'
										}, this.selectNode);
								}
								this.set("entered", true);
								this.defer(function(){
									this.set("entered", false);
								},200);
								topic.publish("/mui/category/selected",this,{
									'label':this.label,
									'fdId':this.fdId,
									'icon':this.icon,
									'type':this.type
								});
								topic.publish("/mui/category/commonSelected",this,{
									'label':this.label,
									'fdId':this.fdId,
									'icon':this.icon,
									'type':this.type
								});
							}
						}
					}
				},
				
				_cancelSelected:function(srcObj , evt){
					if(srcObj.key==this.key){
						if(evt && evt.fdId){
							if(evt.fdId.indexOf(this.fdId)>-1){
								//取消选中，清除window._curIds值
								window._curIds = null;
								if(this.checkedIcon){
									 domClass.remove(this.selectNode,"muiCateSeled");
									 domConstruct.destroy(this.checkedIcon);
									 this.checkedIcon= null;
									 topic.publish("/mui/category/unselected",this,{
											'label':this.label,
											'fdId':this.fdId,
											'icon':this.icon,
											'type':this.type
									});
									 topic.publish("/mui/category/commonUnSelected",this,{
											'label':this.label,
											'fdId':this.fdId,
											'icon':this.icon,
											'type':this.type
									});
								}
							}
						}
					}
				},
				
				//是否显示往下一级
				showMore : function(){
					return false;
				},
				
				//是否选中
				isSelected:function(){
					var pWeiget = this.getParent();
					if(pWeiget && pWeiget.curIds){
						var arrs = pWeiget.curIds.split(";");
						if (array.indexOf(arrs,this.fdId)>-1){
							return true;
						}
					}
					return false;
				},
				
				buildIcon:function(iconNode){
					if(this.icon){
						iconUtils.setIcon(this.icon, null,
								this._headerIcon, null, iconNode);
					}else{
						domStyle.set(iconNode, {"display":"none"});
					}
				}
			});
			return item;
		});