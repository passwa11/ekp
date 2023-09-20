define([ 
	"dojo/_base/declare", 
	"mui/util",
	"./DialogItemListMixin",
	"./DialogItemMixin"
	], function(declare, util, DialogItemListMixin, DialogItemMixin) {
	return declare("mui.selectdialog.UnitGroupListMixin", [DialogItemListMixin], {
		
		itemRenderer: DialogItemMixin,
		
		// 数据请求URL
		dataUrl : '/sys/common/datajson.jsp?s_bean=sysUnitMobileAuthDataWithDecBeanService&parentId=!{parentId}&type=allUnit',
		
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
			
			item.type = item.nodeType;
			item.fdId = item.value;
			item.label = item.text;
					
			return item;
		}
		
	});
});