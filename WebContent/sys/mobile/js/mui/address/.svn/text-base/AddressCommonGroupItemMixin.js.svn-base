define(	["dojo/_base/declare","mui/address/AddressGroupItemMixin"],
		function(declare, AddressGroupItemMixin) {
			var item = declare("mui.address.AddressCommonGroupItemMixin", [ AddressGroupItemMixin ], {
				
				_openCate:function(evt){
					if((this.type | window.ORG_TYPE_GROUP_CATE) === window.ORG_TYPE_GROUP_CATE){ //查找群组分类里的群组
						this.dataUrl = '/sys/organization/mobile/address.do?method=commonGroupCate&parent=!{parentId}&orgType=!{selType}';
					}else{// 查找群组里的组织架构
						this.dataUrl = '/sys/organization/mobile/address.do?method=commonGroupList&groupCate=!{parentId}&nodeType=group&orgType=!{selType}';
					}
					this.inherited(arguments);
				}
				
			});
			return item;
		});