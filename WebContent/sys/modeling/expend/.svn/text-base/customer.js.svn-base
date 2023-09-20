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
					type : 'customer',
					customBaseName : 'ekp_47b802bd9df4e69b63eb',//客户信息表名
					clueBaseName : 'ekp_xiansuo',//线索表名
					bussBaseName : 'ekp_7ee8f9f53b5f74d3eba5'//商机表名
				}
			}).then(function(res) {
				if (res) {
					var results = eval("(" + res + ")");
					query("#clue")[0].innerText = results.clue;
					query("#custom")[0].innerText = results.custom;
					query("#buss")[0].innerText = results.buss;
				}
			}, function(err) {

			});

	    } 
    
  });
});