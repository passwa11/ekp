define(	["dojo/_base/declare","mui/address/AddressItemMixin"],
		function(declare, AddressItemMixin) {
			var item = declare("mui.address.AddressGroupItemMixin", [ AddressItemMixin ], {
				
				//是否显示往下一级
				showMore : function(){
					// 服务器端返回了canShowMore则直接不可以前往下一级
					if(this.canShowMore === 'false'){
						return false;
					}
					// 群组分类
					if((this.type | window.ORG_TYPE_GROUP_CATE) ==  window.ORG_TYPE_GROUP_CATE){
						return true;
					}
					// 群组
					if((this.type | window.ORG_TYPE_GROUP) ==  window.ORG_TYPE_GROUP){
						return true;
					}
					return false;
				}
			});
			return item;
		});