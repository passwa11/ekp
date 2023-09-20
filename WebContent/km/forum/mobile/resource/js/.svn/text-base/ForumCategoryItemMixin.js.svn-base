define(	["dojo/_base/declare" , "dojo/dom-class", "mui/iconUtils" , "mui/simplecategory/SimpleCategoryItemMixin"],
		function(declare, domClass, iconUtils, SimpleCategoryItemMixin) {
			var item = declare("km.forum.mobile.resource.js.ForumCategoryItemMixin", [SimpleCategoryItemMixin], {

				buildRendering:function(){
					this.inherited(arguments);
					this.type = this.nodeType=="CATEGORY"?window.SIMPLE_CATEGORY_TYPE_CATEGORY:this.nodeType=="NOCATEGORY"?"NOCATEGORY":1;
				},
				
				//是否显示往下一级
				showMore : function(){
					var more=false;
					if(this.type == "1"){
						return true;
					}
					var pWeiget = this.getParent();
					if(pWeiget.authCateIds){
						if(pWeiget.authCateIds.indexOf(this.fdId)>-1){
							more = true;
						}
					}else more = true;
					if(this.type == window.SIMPLE_CATEGORY_TYPE_CATEGORY && more){
						return true;
					}
					return false;
				},
				
				//是否显示选择框
				showSelect:function(){
					if(this.type==window.SIMPLE_CATEGORY_TYPE_CATEGORY||this.type=="NOCATEGORY"){
						return true;
					}
					return false;
				}
			});
			return item;
		});