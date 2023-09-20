define(["dojo/_base/declare","dojox/mobile/View","dojo/topic","dojo/dom-style","dojo/query","dijit/registry"
	],function(declare,View,topic,domStyle,query,registry){
	
	return declare("sys.lbpmservice.mobile.workitem.CommonUsageCreateView",[View],{
		startup:function(){
			this.inherited(arguments);
		},
		
		postCreate : function(){
			this.subscribe("/lbpm/commonUsageItem/edit","doInit");
		},
		
		onBeforeTransitionIn:function(){
			domStyle.set(query("body")[0],{
				"background": "#F5F6FB",
				"height":"100%"
			})
			this.inherited(arguments);
		},
		
		onAfterTransitionOut:function(){
			domStyle.set(query("body")[0],{
				"background": "white",
				"height":"auto"
			})
			this.doRest();
			this.inherited(arguments);
		},
		
		doRest:function(){
			topic.publish("/lbpm/commonUsageView/rest",{value:""});
			var commonUsageIdWgt = registry.byId("commonUsageId");
			if(commonUsageIdWgt){
				commonUsageIdWgt._setValueAttr("");
			}
		},
		
		doInit:function(wgt,data){
			topic.publish("/lbpm/commonUsageView/rest",{value:wgt.fdContent});
			var commonUsageIdWgt = registry.byId("commonUsageId");
			if(commonUsageIdWgt){
				commonUsageIdWgt._setValueAttr(wgt.fdId);
			}
		}
	})
})