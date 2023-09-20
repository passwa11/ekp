define(["dojo/_base/declare","mui/tabbar/TabBarButton","dojo/dom-class","dojo/topic",
	"mui/history/listener","dojo/_base/window","dojox/mobile/TransitionEvent","dojo/dom-style",
	"dojo/query"],function(declare,TabBarButton,domClass,topic,listener,win,TransitionEvent,domStyle,query){
	return declare("sys.lbpmservice.mobile.workitem.CommonUsageAddButton",[TabBarButton],{
		postCreate:function(){
			this.subscribe("/lbpm/commonUsageItem/edit", "_moveView");
		},
		
		startup : function() {
			this.inherited(arguments);
			domClass.add(this.domNode, "addUsageButton");
		},
		
		//保存审批语
		onClick:function(){
			this.moveView();
		},
		
		_moveView:function(wgt){
			this.moveView();
		},
		
		moveView:function(){
			topic.publish("/lbpm/commonUsageView/changStatus");
			new TransitionEvent(win.body(), {moveTo: "commonUsageCreateView", transition: "slide", transitionDir: 1}).dispatch();
			
			listener.add({callback:function(){
				topic.publish("/lbpm/commonUsageView/changStatus");
				new TransitionEvent(win.body(), {moveTo: "commonUsageManageView", transition: "slide", transitionDir: -1}).dispatch();
			}});
		}
		
	});
})