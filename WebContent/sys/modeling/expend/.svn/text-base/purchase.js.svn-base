define([
    "dojo/_base/declare",
    "dojo/query",
    "dojo/request",
    "mui/util",
    "dijit/_WidgetBase"
  ], function( declare,query, request, util,WidgetBase) {
  
  return declare("sys.mportal.kmsCreditMportletInfo.wesg", [ WidgetBase ], {
      
	  buildRendering : function() {
		  var url = util.formatUrl("/sys/modeling/base/mobile/modelingAppMobile.do?method=getPortalDatas");
	      request(url, {
				method : 'post',
				data : {
					type : 'purchase',
					planBaseName : 'ekp_plan',//计划表名
					planPrice : 'fd_397a669772a132',//计划金额字段名
					planDate : 'fd_3974dca7245534',//计划时间字段名
					realBaseName : 'ekp_order',//采购表名
					realPrice : 'fd_397996508d3a4c',//采购金额字段名
					realDate : 'fd_3974e1a04651b4'//采购时间字段名
				}
			}).then(function(res) {
				if (res) {
					var results = eval("(" + res + ")");
					query("#plan")[0].innerText = results.plan;
					query("#real")[0].innerText = results.real;
				}
			}, function(err) {

			});

	    } 
    
  });
});