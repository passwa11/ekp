/**
 * 公共群组列表Mixin
 */
define([ 
	"dojo/_base/declare", 
	"mui/util",
	"./AddressItemListMixin",
	"./AddressGroupItemMixin"
	], function(declare, util, AddressItemListMixin, AddressGroupItemMixin) {
	return declare("mui.address.AddressMyGroupListMixin", [ AddressItemListMixin], {
		
		itemRenderer: AddressGroupItemMixin,
		
		// 数据请求URL
		dataUrl : '/sys/organization/mobile/address.do?method=myGroupList&id=!{parentId}&orgType=!{selType}',
		
		buildRendering : function() {

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
			switch(item.nodeType){
				case 'cate':
					item.type =  window.ORG_TYPE_GROUP_CATE;
					item.fdId = item.value;
					item.label = item.text;
					break;
				default:
					item.type = item.orgType;
					item.fdId = item.id;
					item.label = item.name;
					if((item.type | window.ORG_TYPE_PERSON) ==  window.ORG_TYPE_PERSON){
						item.icon = util.urlResolver(this.iconUrl,{
							orgId: item.fdId
						});
					}
					break;
			}
			return item;
		}

	});
});