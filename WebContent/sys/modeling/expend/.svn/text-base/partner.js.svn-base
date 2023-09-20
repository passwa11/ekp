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
					type : 'partner',
					partnerBaseName : 'ekp_cfeed2e689a5d4294986',//伙伴信息表名
					partnerStauts : 'fd_3979bd72e05202'//伙伴状态字段名
				}
			}).then(function(res) {
				if (res) {
					var results = eval("(" + res + ")");
					query("#value0")[0].innerText = results.value0;
					query("#value1")[0].innerText = results.value1;
					query("#value2")[0].innerText = results.value2;
				}
			}, function(err) {

			});

	    } 
    
  });
});