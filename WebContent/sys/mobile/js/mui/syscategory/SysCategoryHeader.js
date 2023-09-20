define( [ "dojo/_base/declare", "mui/category/CategoryHeader","mui/i18n/i18n!sys-mobile"], function(declare,CategoryHeader,Msg) {
		var header = declare("mui.syscategory.SysCategoryHeader", [ CategoryHeader], {
				
				modelName: null ,
				
				title:Msg['mui.category.title'],
				
				//获取详细信息地址
				detailUrl : '/sys/category/mobile/sysCategory.do?method=detailList&cateId=!{curId}&modelName=!{modelName}'
			});
			return header;
});