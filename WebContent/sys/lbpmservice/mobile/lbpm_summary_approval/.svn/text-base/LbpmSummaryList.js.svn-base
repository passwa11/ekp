define([
  "dojo/_base/declare",
  "dojo/topic",
  "dojo/dom-construct",
  "mui/util",
  "mui/list/JsonStoreList"
], function(declare, topic, domConstruct, util, JsonStoreList) {
	
  return declare("sys.lbpmservice.mobile.lbpmSummaryApproval.LbpmSummaryList", [JsonStoreList], {
    //数据请求URL
    dataUrl: "/sys/lbpmservice/support/actions/LbpmSummaryApproval.do?method=flushDatas&mobile=true&fdConfigId=!{fdConfigId}&fdNodeFactId=!{fdNodeFactId}&fdTime=!{fdTime}",

    baseClass: "lbpmSummaryLists",
    
    lazy:false,

    buildRendering: function() {
    	//从url中获取对应的参数
    	var href = location.href;	
    	this.fdConfigId = util.getUrlParameter(href, "fdConfigId");
    	this.fdNodeFactId = util.getUrlParameter(href, "fdNodeFactId");
    	this.fdTime = util.getUrlParameter(href, "fdTime");
    	
    	this.url = util.urlResolver(this.dataUrl, this)

    	this.inherited(arguments)
    },
    
    startup : function() {					
    	this.inherited(arguments);
	},
	
	postCreate:function(){
		this.subscribe("/mui/lbpmSummary/reloadList", "_reload");
	},
	
	_reload : function(){
		this.reload();
	}
  })
})
