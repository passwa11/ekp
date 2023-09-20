define([
  "dojo/_base/declare",
  "mui/util",
  "mui/list/JsonStoreList"
], function(declare, util, JsonStoreList) {
	
  return declare("sys.lbpmservice.mobile.lbpmSummaryApproval.LbpmSummaryList", [JsonStoreList], {
    //数据请求URL
    dataUrl: "/sys/readlog/sys_read_log/sysReadLog.do?method=listdata&pagingtype=simple&mobile=true&modelId=!{modelId}&modelName=!{modelName}",

    baseClass: "accessLogItemList",
    
    lazy:false,

    buildRendering: function() {
    	this.url = util.urlResolver(this.dataUrl, this)
    	this.inherited(arguments)
    },
    
    startup : function() {					
    	this.inherited(arguments);
	}
  })
})
