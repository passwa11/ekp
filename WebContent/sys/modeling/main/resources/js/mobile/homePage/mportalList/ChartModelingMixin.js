define(['dojo/_base/declare'
        ],function(declare){
	return declare('sys.modeling.main.resources.js.mobile.homePage.mportalList.ChartModelingMixin',null,{
		concatDefaultOptions : function(options){
			this.inherited(arguments);
			var toolbox = options.toolbox;
			if(toolbox && toolbox.feature){
				//低代码平台，图表统计移动端不显示保存为图片
				toolbox.feature.saveAsImage = false;
			}
		},
	});
	
});