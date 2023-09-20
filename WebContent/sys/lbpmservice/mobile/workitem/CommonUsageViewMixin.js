define(["dojo/_base/declare","mui/NativeView", "dojox/mobile/ScrollableView", "dojo/topic",
	"mui/list/StoreElementScrollableView","dojo/dom-style"],function(declare,NativeView,ScrollableView,topic,StoreElementScrollableView,domStyle){
	
	return declare("sys.lbpmservice.mobile.workitem.CommonUsageView",null,{
		postCreate:function(){
			this.subscribe("/lbpm/commonUsageView/changStatus", "changeStatus");
			this.inherited(arguments);
		},
		
		changeStatus:function(){
			this.status = true;
		},
		
		onBeforeTransitionIn:function(){
			if(!this.status){
				topic.publish("/lbpm/operation/hideDialog");
				topic.publish("/workitem/commonUsage/hideDialog");
			}else{
				this.status = false;
			}
			this.inherited(arguments);
		},
		
		onAfterTransitionOut:function(){
			if(!this.status){
				topic.publish("/lbpm/operation/showDialog");
				topic.publish("/workitem/commonUsage/showDialog");
			}else{
				this.status = false;
			}
			this.inherited(arguments);
		}
	})
})