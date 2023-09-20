define( [ "dojo/_base/declare", "mui/category/ScrollableCategoryPath","mui/i18n/i18n!sys-mobile"], function(declare,CategoryPath,Msg) {
		var path = declare("mui.syscategory.SysCategoryPath", [ CategoryPath], {
				
				modelName: null ,
				
				//获取详细信息地址
				detailUrl : '/sys/category/mobile/sysCategory.do?method=pathList&cateId=!{curId}&modelName=!{modelName}'
			});
			return path;
});