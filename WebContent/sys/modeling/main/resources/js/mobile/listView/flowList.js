define( [ "dojo/_base/declare", "dojo/_base/lang", "mui/util", "mui/syscategory/SysCategoryList","mui/category/CategorySelection"], function(declare,
		 lang, util, CategoryList, CategorySelection) {
	return declare("sys.modeling.main.resources.js.mobile.listView.flowList", [ CategoryList ], {
		
		modelName : null,
		
		//数据请求URL
		dataUrl : '/sys/modeling/main/modelingAppFlowMain.do?method=findFlows&fdAppModelId=!{fdAppModelId}',
		
		resolveItems : function(sourceData){
			var items = [];
			if(sourceData && sourceData.data && sourceData.data.length){
				items = sourceData.data;
			}
			this.listDatas = items;
			this.totalSize = items.length;
			this.pageno = 1;
			this._loadOver = true;
			return items;
		}
		
	});
});