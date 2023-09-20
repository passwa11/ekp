define(	["dojo/_base/declare" , "dojo/dom-class", "mui/iconUtils" , "mui/category/CategoryItemMixin" , "mui/util"],
		function(declare, domClass, iconUtils, CategoryItemMixin, util) {
			var item = declare("mui.selectdialog.DialogCateItemMixin", [CategoryItemMixin ], {

				buildRendering:function(){
					this.fdId = this.value;
					this.label = this.text;
					this.icon  = 'mui mui-organization';
					this.type = this.nodeType=='NOSHOW'?'NOSHOW':(this.nodeType=='CATEGORY'?window.SYS_CATEGORY_TYPE_CATEGORY:(this.nodeType=='TEMPLATE'?window.SYS_CATEGORY_TYPE_TEMPLATE:window.SYS_CATEGORY_TYPE_DOCUMENT));
					this.inherited(arguments);
					if (this.name && this.name!=""){
						this.titleNode.innerHTML = util.formatText(this.name);
					}
				},
				
				//获取分组标题信息
				getTitle:function(){
					return this.label;
				},
				
				//是否显示往下一级
				showMore : function(){
					if(this.type==window.SYS_CATEGORY_TYPE_CATEGORY||this.type==window.SYS_CATEGORY_TYPE_TEMPLATE){
						return true;
					}
					return false;
				},
				
				//是否显示选择框
				showSelect:function(){
					var pWeiget = this.getParent();
					if(pWeiget){
						return true;
					}
					return false;
				},
				
				//是否选中
				isSelected:function(){
					var pWeiget = this.getParent();
					if(pWeiget && pWeiget.curIds && (pWeiget.curIds.indexOf(this.fdId)>-1)){
						return true;
					}
					return false;
				},
				
				buildIcon:function(iconNode){					
					if(this.icon){
						iconUtils.setIcon(this.icon, null,
								this._headerIcon, null, iconNode);
					}
				}
			});
			return item;
		});