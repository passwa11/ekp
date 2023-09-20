define([
  "dojo/_base/declare",
  "dojo/topic",
  "mui/list/JsonStoreList",
  "mui/util",
  "mui/dialog/Tip",
  "dojo/request",
  "dojo/_base/lang",
  "mui/i18n/i18n!sys-mobile"
], function(declare, topic, JsonStoreList, util, Tip, request, lang, Msg) {
	
  return declare("sys.lbpmservice.mobile.workitem.CommonUsageList", [JsonStoreList], {
    //数据请求URL
    dataUrl: "/sys/lbpmservice/support/lbpm_usage/lbpmUsage.do?method=getUsagesInfo&mobile=true",
    
    updateUrl: "/sys/lbpmservice/support/lbpm_usage/lbpmUsage.do?method=update4Mobile",

    baseClass: "commonUsageList",
    
    lazy:false,

    buildRendering: function() {
    	this.url = util.urlResolver(this.dataUrl, this)
    	this.inherited(arguments)
    },
    
    startup : function() {	
    	this.inherited(arguments);
	},
	
	postCreate:function(){
		this.subscribe("/lbpm/commonUsageView/move", "_reload");
		this.subscribe("/lbpm/commonUsageItem/delete","_deleteItem");
		this.subscribe("/lbpm/commonUsageItem/update","_updateItem");
	},
	
	_reload : function(){
		this.reload();
	},
	
	_deleteItem:function(item){
		this.type = "delete";
		//构建新的审批语
		var usage = this.getUsage(item);
		//进行更新
		this.updateUsage(usage);
	},
	
	_updateItem:function(item){
		this.type = "update";
		//构建新的审批语
		var usage = this.getUsage(item);
		//进行更新
		this.updateUsage(usage);
	},
	
	getUsage:function(item){
		var datas = this.listDatas || [];
		var fdUsageContent = "";
		for(var i=0; i<datas.length; i++){
			var data = datas[i];
			if(data.fdId != item.fdId)
				continue;
			if(data.fdId == item.fdId && data.index != item.index && this.type == "delete"){
				if(fdUsageContent)
					fdUsageContent += "\n"+data.fdContent;
				else
					fdUsageContent += data.fdContent;
			}
			if(data.fdId == item.fdId && this.type == "update"){
				var fdContent = "";
				if(data.index == item.index){
					fdContent += item.fdContent;
				}else{
					fdContent += data.fdContent;
				}
				if(fdUsageContent)
					fdUsageContent += "\n"+fdContent;
				else
					fdUsageContent += fdContent;
			}
		}
		var usage = {};
		usage.fdId = item.fdId;
		usage.fdUsageContent = fdUsageContent;
		return usage;
	},
	
	updateUsage:function(usage){
		var _self = this;
		var processing = Tip.processing();
		processing.show();
		var url = util.formatUrl(this.updateUrl);
		var promise = request.post(url, {
            data:usage,
            headers: {'Accept': 'application/json'},
            handleAs: 'json'
        }).then(function(result) {
        	processing.hide();
        	if (result['status'] === false) {
        		var msg = Msg["mui.return.failure"];
        		Tip.fail({text: msg, cover: true});
        		return;
        	}
        	Tip.success({
				text: Msg["mui.return.success"], 
				callback: lang.hitch(_self,_self.doBack),
				cover: true
			});
        }, function(result) {
        	processing.hide();
        	var msg = Msg["mui.return.failure"];
    		Tip.fail({text: msg, cover: true});
        });
	},
	
	doBack:function(){
		if(this.type == "update"){
			topic.publish("/lbpm/commonUsageItem/updateFinish");
		}
		this.reload();
	}
  })
})
