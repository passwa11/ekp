define(	["dojo/_base/declare", "dojo/_base/array", "mui/iconUtils" , "mui/category/CategoryItemMixin" ,
       	"dojo/topic", "dojo/dom-style", "dojo/dom", "dojo/dom-class", "dojo/dom-construct"],
		function(declare, array, iconUtils, CategoryItemMixin,topic,domStyle,dom,domClass,domConstruct) {
			var item = declare("sys.lbpmext.authorize.mobile.js.authorizescope.LbpmAuthorizeItemMixin", [CategoryItemMixin ], {
				//是否显示往下一级
				showMore : function(){
					var pWeiget = this.getParent();
					if(this.nodeType=="TEMPLATE"){
						return false;
					}
					return true;
				},
				
				//是否显示选择框
				showSelect:function(){
					return true;
				},
				
				//是否选中
				isSelected:function(){
					var pWeiget = this.getParent();
					if(pWeiget && pWeiget.curIds){
						var arrs = pWeiget.curIds.split(";");
						if (array.indexOf(arrs,this.fdId)>-1)
							return true;
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
				},
				
				_openCate:function(evt){
					this.set("entered", true);
					this.defer(function(){
						this.set("entered", false);
						topic.publish("/mui/category/changed",this,{
							'fdId':this.fdId,
							'label':this.label,
							'param':this.param
						});
					}, 200);
					return;
				},
				
				_setSelected:function(srcObj,evt){
					if(srcObj.key==this.key){
						if(evt && evt.fdId){
							if(evt.fdId==this.fdId){
								domClass.add(this.selectNode,"muiCateSeled");
						    	this.checkedIcon= domConstruct.create('i', {
										'className' : 'mui mui-checked muiCateSelected'
								}, this.selectNode);
								this.set("entered", true);
								this.defer(function(){
									this.set("entered", false);
								},200);
								topic.publish("/mui/category/selected",this,{
									'fdId':this.fdId,
									'label':this.label
								});
								topic.publish("/mui/category/cate_selected",this,{
									'label':this.label,
									'fdId':this.fdId,
									'icon':this.icon,
									'type':this.type
								});
							}
						}
					}
				}
			});
			return item;
		});