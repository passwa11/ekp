define( [ "dojo/_base/declare", "dojo/_base/lang", "mui/util", "mui/category/CommonCategoryList"], function(declare,
		 lang, util, CategoryList) {
	return declare("mui.syscategory.SysCommonCategoryList", [ CategoryList ], {
		
		modelName : null,
		
		//数据请求URL
		dataUrl : '/sys/lbpmperson/SysLbpmPersonCreate.do?method=listUsualByModule&mainModelName=!{mainModelName}&extendPara=key:!{key}'
		
	});
});