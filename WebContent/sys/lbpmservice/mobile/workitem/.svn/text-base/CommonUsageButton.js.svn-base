define(["dojo/_base/declare","mui/tabbar/TabBarButton","dojo/_base/window","dojox/mobile/TransitionEvent",
	"dojox/mobile/ViewController","dojox/mobile/viewRegistry","mui/util","dijit/registry","dojo/topic",
	"dojo/dom-style","dojo/query","mui/history/listener","dojo/html","dojo/dom-construct"
	],function(declare,TabBarButton,win,TransitionEvent,ViewController,viewRegistry,util,registry,topic,domStyle,query,listener,html,domConstruct){
	return declare("sys.lbpmservice.mobile.workitem.CommonUsageButton",[TabBarButton],{
		
		viewId:'commonUsageAddView',
		
		viewUrl:"/sys/lbpmservice/mobile/workitem/commonUsageView.jsp",
		
		startup : function() {
			this.inherited(arguments);
		},
		
		getBackToView:function(){
			var view = null;
			if(this.backTo){
				view = viewRegistry.hash[this.backTo];
			}
			if(!view){
				view = viewRegistry.getEnclosingView(this.domNode);
				view = viewRegistry.getParentView(view) || view;
			}
			return view;
		},
		
		onClick:function(){
			var _self = this;
			//先判断是否viewid对应的view是否初始化了，未初始化先进行初始化
			if(!viewRegistry.hash[this.viewId]){
				this.initView();
			}else{
				this.transitionView();
			}
		},
		
		initView:function(){
			var _self = this;
			var backView = this.getBackToView();
			var url = util.formatUrl(this.viewUrl);
			url = "dojo/text!" + url;
      	  	require([url], function(tmplStr) {
      	  		var viewNode = domConstruct.create('div');
      	  		domConstruct.place(viewNode,backView.domNode.parentNode,'last');
      		  	var dhs = new html._ContentSetter({
	    	          node: viewNode,
	    	          parseContent: true,
	    	          cleanContent: false
	    	        })
	
	    	        dhs.set(tmplStr)
	    	        dhs.parseDeferred.then(function(results) {
	    	        	var view = viewRegistry.hash["commonUsageCreateView"];
	    	        	if(view){
	    	        		domStyle.set(view.domNode,{
	    	        			"display":"none"
	    	        		})
	    	        	}
	    	        }).then(function(){
	    	        	_self.transitionView();
	    	        });
	    	        dhs.tearDown();
      	  	})
		},
		
		transitionView:function(){
			var _self = this;
			var backToView = this.getBackToView();
			if(backToView)
				backToView.performTransition(this.viewId, 1, "slide");
			else
				new TransitionEvent(win.body(), {moveTo: this.viewId, transition: "slide", transitionDir: 1}).dispatch();
			
			if(this.viewId =='commonUsageManageView'){
				topic.publish("/lbpm/commonUsageView/move");
			}
			
			var view = viewRegistry.hash[this.viewId];
			listener.add({callback:function(){
				if(backToView)
					view.performTransition(_self.backTo, -1, "slide");
				else
					new TransitionEvent(win.body(), {moveTo: _self.backTo, transition: "slide", transitionDir: -1}).dispatch();
			}});
		}
		
	});
})