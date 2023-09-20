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
			// 生态组织
			if(this.isExt != '' && this.isExt != undefined && this.isExt == 'true')
				this.dataUrl += '&isExternal=false';
			this.inherited(arguments);
		},
		
		resolveItems: function(){
			var list = this.inherited(arguments);
			var newList = [];
			if(list && list.length > 0){
				var exceptValue;
				if(this.params && this.params.exceptValue){
					exceptValue = this.params.exceptValue;
				}
				for(var i = 0; i < list.length; i++){
					//过滤排除的值
					if(exceptValue && exceptValue.indexOf(list[i].id) != -1){
						continue;
					}
					newList.push(this._transformItemProperties(list[i]));
				}
			}
			return newList;
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
					if(!item.dingImg || item.dingImg !== "true"){
						item.icon = util.urlResolver(this.iconUrl,{
							orgId: item.fdId
						});						
					}
				}
			}
			return item;
		}

	});
});