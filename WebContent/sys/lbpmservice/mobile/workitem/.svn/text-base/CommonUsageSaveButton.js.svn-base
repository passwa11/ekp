define(["dojo/_base/declare","mui/tabbar/TabBarButton","dojo/dom-class","dojo/topic","mui/util",
	"mui/dialog/Tip", "dojo/request", "dojo/query", "mui/i18n/i18n!sys-mobile","dijit/registry",
	"mui/history/listener","mui/i18n/i18n!sys-lbpmservice:mui.commonUsage"
	],function(declare,TabBarButton,domClass,topic,util,Tip,request,query,Msg,registry,listener,Msg1){
	return declare("sys.lbpmservice.mobile.workitem.CommonUsageSaveButton",[TabBarButton],{
		
		saveUrl:"/sys/lbpmservice/support/lbpm_usage/lbpmUsage.do?method=save4Mobile",
		
		postCreate : function(){
			this.subscribe("/mui/form/valueChanged","switchClickStatus");
			this.subscribe("/mui/textarea/onInput","switchClickStatus");
			this.subscribe("/lbpm/commonUsageItem/edit","_doInit");
			this.subscribe("/lbpm/commonUsageItem/updateFinish","_doBack");
		},
		
		startup : function() {
			this.inherited(arguments);
			domClass.add(this.domNode, "saveUsageButton unSelected");
		},
		
		//保存审批语
		onClick:function(){
			if(!this.clickStatus){
				return;
			}
			this.doSubmit();
		},
		
		switchClickStatus:function(wgt,data){
			if(wgt.id != "commonUsageContent")
				return;
			if(data == "" || data.value == ""){
				this.clickStatus = false;
				domClass.add(this.domNode, "unSelected");
			}else{
				this.clickStatus = true;
				domClass.remove(this.domNode, "unSelected");
			}
		},
		
		_doInit:function(wgt,data){
			this.currentItem = wgt;
		},
		
		doSubmit:function(){
			var fdUsageContentWgt = registry.byId("commonUsageContent");
			if(!fdUsageContentWgt && !fdUsageContentWgt.value){
				Tip.warn({text:Msg1['mui.commonUsage.tip.empty']});
				return;
			}
			var commonUsageIdWgt = registry.byId("commonUsageId");
			if(commonUsageIdWgt && commonUsageIdWgt.value){//更新
				if(this.currentItem.fdContent == fdUsageContentWgt.value){
					Tip.warn({text:Msg1['mui.commonUsage.tip.same']});
					return;
				}
				this.currentItem.fdContent = fdUsageContentWgt.value;
				topic.publish("/lbpm/commonUsageItem/update",this.currentItem);
				return;
			}
			var _self = this;
			var processing = Tip.processing();
			processing.show();
			var url = util.formatUrl(this.saveUrl);
			var data = {
            	"fdUsageContent":fdUsageContentWgt.value,
            	"fdIsSysSetup":0,
            	"fdIsAppend":1
			};
			var promise = request.post(url, {
                data: data,
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
					callback: _self.doBack,
					cover: true
				});
            }, function(result) {
            	processing.hide();
            	var msg = Msg["mui.return.failure"];
        		Tip.fail({text: msg, cover: true});
            });
		},
		
		_doBack:function(){
			//回退视图
			listener.go({step:-1});
		},
		
		doBack:function(){
			topic.publish("/lbpm/commonUsageView/move");
			//回退视图
			listener.go({step:-1});
		}
		
	});
})