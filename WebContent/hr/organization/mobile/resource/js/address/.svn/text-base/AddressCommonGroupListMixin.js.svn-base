/**
 * 公共群组列表Mixin
 */
define([ 
	"dojo/_base/declare", 
	"mui/util",
	"./AddressItemListMixin",
	"./AddressCommonGroupItemMixin"
	], function(declare, util, AddressItemListMixin, AddressCommonGroupItemMixin) {
	
	return declare("mui.address.AddressCommonGroupListMixin", [ AddressItemListMixin], {
		
		itemRenderer: AddressCommonGroupItemMixin,
		
		// 数据请求URL
		dataUrl : '/sys/organization/mobile/address.do?method=commonGroupCate&parent=!{parentId}&orgType=!{selType}',
		
		_cateChange: function(srcObj,evt){
			if(srcObj.key == this.key){
				if((evt.type | window.ORG_TYPE_GROUP_CATE) === window.ORG_TYPE_GROUP_CATE){ //查找群组分类里的群组
					this.dataUrl = '/sys/organization/mobile/address.do?method=commonGroupCate&parent=!{parentId}&orgType=!{selType}';
				}else{// 查找群组里的组织架构
					this.dataUrl = '/sys/organization/mobile/address.do?method=commonGroupList&groupCate=!{parentId}&nodeType=group&orgType=!{selType}';
				}
			}
			this.inherited(arguments);
		},
		
		resolveItems: function(){
			var list = this.inherited(arguments);
			if(list && list.length > 0){
				for(var i = 0; i < list.length; i++){
					list[i] = this._transformItemProperties(list[i]);
				}
			}
			return list
		},
		
		_transformItemProperties: function(item){
			if(item.nodeType){ // 群组分类数据处理
				item.type =  item.nodeType === 'cate' ? window.ORG_TYPE_GROUP_CATE :  window.ORG_TYPE_GROUP; 
				item.fdId = item.value;
				item.label = item.text;
			}else{ // 组织架构数据处理
				item.type = item.orgType;
				item.fdId = item.id;
				item.label = item.name;
				if((item.type | window.ORG_TYPE_PERSON) ==  window.ORG_TYPE_PERSON){
					item.icon = util.urlResolver(this.iconUrl,{
						orgId: item.fdId
					});
				}
			}
			return item;
		}

	});
});