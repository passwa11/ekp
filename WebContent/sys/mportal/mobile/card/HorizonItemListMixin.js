define([ "dojo/_base/declare", "mui/list/_TemplateItemListMixin",
		"./item/HorizonItemMixin" ], function(declare, _TemplateItemListMixin,
		HorizonItemMixin) {

	return declare("sys.mportal.HorizonItemListMixin", [ _TemplateItemListMixin ], {
				itemTemplateString : null,
				itemRenderer : HorizonItemMixin,
				
				/**
				 * 重写formatDatas的目的：
				 * 判断请求返回的数据行数是奇数还是偶数
				 * 需求为显示在最后一行（一行最多显示两个元素）的元素要求加上特殊CSS类名，用于控制最后一行不显示下边框等特殊样式...
				 * (重写的父级文件路径: sys/mportal/mobile/card/_JsonStorCardListMixin.js)
				 */
				formatDatas: function(datas) {
					var dataed = this.inherited(arguments);
					var lastRowItemKey = "__IS_LAST_ROW_ITEM__"; // 是否为最后一行的元素标识名称
                    if(dataed.length>0){
                    	if(dataed.length%2 == 0){
                    		dataed[dataed.length-1][lastRowItemKey]=true;
                    		dataed[dataed.length-2][lastRowItemKey]=true;
                    	}else{
                    		dataed[dataed.length-1][lastRowItemKey]=true;
                    	}
                    }
                    return dataed;
				}
	});
});