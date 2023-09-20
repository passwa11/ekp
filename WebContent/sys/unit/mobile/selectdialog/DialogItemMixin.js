define(	["dojo/_base/declare" , "dojo/dom-class","dojo/topic",  "mui/iconUtils" , "mui/category/CategoryItemMixin" , "mui/util"],
		function(declare, domClass,topic, iconUtils, CategoryItemMixin, util) {
			var item = declare("mui.selectdialog.DialogItemMixin", [CategoryItemMixin ], {

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
					if(this.type==window.SYS_CATEGORY_TYPE_CATEGORY||this.type==window.SYS_CATEGORY_TYPE_TEMPLATE || this.type=='NOSHOW'){
						return true;
					}
					return false;
				},
				
				//是否显示选择框
				showSelect:function(){
					var pWeiget = this.getParent();
					if(pWeiget && (this.type==window.SYS_CATEGORY_TYPE_DOCUMENT || (this.fdId.indexOf("_cate") > -1&&this.type!='NOSHOW'))){
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
				},
				
				//重写此方法，展开下级之前请求是否存在数据
				_openCate:function(evt){
					evt && evt.stopPropagation();
					
					var self = this;
					
					this.set("entered", true);
					
					this.defer(function(){
						this.set("entered", false);
						
						topic.publish("/mui/view/scrollTo",this,{y:(0)});
						topic.publish("/mui/category/changed", self, {
							'fdId': self.fdId,
							'label': self.label,
							'type': self.type
						});
					}, 200);
					return;
				}
			});
			return item;
		});