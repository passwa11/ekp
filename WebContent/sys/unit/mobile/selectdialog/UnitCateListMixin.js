define([ 
	"dojo/_base/declare", 
	"mui/util",
	"./DialogItemListMixin",
	"./DialogCateItemMixin",
	"mui/util"
	], function(declare, util, DialogItemListMixin, DialogCateItemMixin, util) {
	return declare("mui.selectdialog.UnitCateListMixin", [DialogItemListMixin], {
		
		itemRenderer: DialogCateItemMixin,
		
		// 数据请求URL
		dataUrl : '/sys/common/datajson.jsp?s_bean=sysUnitMobileAuthDataWithCateBeanService&parentId=!{parentId}&showCate=true',
		
		postMixInProperties: function() {
			this.inherited(arguments);
			var allType = util.getUrlParameter(this.allDataUrl, "type");
			if (allType) {
				this.dataUrl += "&type=" + allType;
			}
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
			
			item.type = item.nodeType;
			item.fdId = item.value;
			item.label = item.text;
					
			return item;
		}
		
	});
});