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
					type : 'contract',
					contractBaseName : 'ekp_plan',//合同流程表名
					contractPersonId : 'fd_3974dc1862463a',//合同属于人员
					contractPrice : 'fd_397a669772a132'//合同金额
				}
			}).then(function(res) {
				if (res) {
					var results = eval("(" + res + ")");
					query("#name0")[0].innerText = results.name0 ? results.name0 : '';
					query("#name1")[0].innerText = results.name1 ? results.name1 : '';
					query("#name2")[0].innerText = results.name2 ? results.name2 : '';
					query("#price0")[0].innerText = results.price0 ? results.price0 : '';
					query("#price1")[0].innerText = results.price1 ? results.price1 : '';
					query("#price2")[0].innerText = results.price2 ? results.price2 : '';
				}
			}, function(err) {

			});

	    } 
    
  });
});