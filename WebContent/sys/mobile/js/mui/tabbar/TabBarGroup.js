define([
        "dojo/_base/declare",
        "dojo/dom-construct",
    	"dojo/_base/array",
    	"dijit/_WidgetBase",
		"dijit/_Contained", 
		"dijit/_Container",
		"mui/tabbar/SplitterButton",
		"dojox/mobile/TabBar",
		"dojo/topic"
	], function(declare, domConstruct, array, WidgetBase, Contained, Container,SplitterButton, TabBar, topic) {
	
	return declare("mui.tabbar.TabBarGroup", [WidgetBase, Contained, Container], {

		baseClass: "muiTabBarGroup",

        isFilterEmptyChild : false,
		
		buildRendering: function(){
			this.inherited(arguments);
		},
		
		postCreate: function() {
			this.inherited(arguments);
		},

        filterEmptyChild : function(children) {
            return array.filter(children,function(childWgt,idx){
                if(childWgt.isInstanceOf(TabBar)){
                    if (childWgt.getChildren().length > 0) {
                        return true;
                    } else {
                        childWgt.destroy();
                    }
                    return false;
                }
                return false;
            });
        },
		
		startup: function() {
			this.inherited(arguments);
			var children = this.getChildren();
			if (this.isFilterEmptyChild) {
                children = this.filterEmptyChild(children);
            }
			var childLen = children.length;
			if(childLen>1){
				var _self = this;
				var hasSplitter = true;
				array.forEach(children,function(childWgt,idx){
					if(childWgt.getChildren().length<=0) {
                        hasSplitter = false;
                    } else {
                        hasSplitter = true;
                    }
				});
				if(	hasSplitter){
                    array.forEach(children,function(childWgt,idx){
                        if(idx==0){
                            var cWgt = new SplitterButton({groupIndex:idx,flag:"right"});
                            childWgt.addChild(cWgt,'last');
                        }else{
                            if(idx+1==childLen){
                                var cWgt = new SplitterButton({groupIndex:idx,flag:"left"});
                                childWgt.addChild(cWgt,'first');
                            }else{
                                var cWgt = new SplitterButton({groupIndex:idx,flag:"left"});
                                childWgt.addChild(cWgt,'first');
                                cWgt = new SplitterButton({groupIndex:idx,flag:"right"});
                                childWgt.addChild(cWgt,'last');
                            }
                        }
                    });
				}
				this.switchTabBar(0);
			}
		},
		
		switchTabBar:function(tabbarIdx){
			if(tabbarIdx>-1){
				array.forEach(this.getChildren(),function(childWgt,idx){
					if(idx==tabbarIdx){
						childWgt.domNode.style.display = "";
						setTimeout(function(){
							if(childWgt.resize){
								childWgt.resize();
							}
						},20);
					}else{
						childWgt.domNode.style.display = "none";
					}
				});
				topic.publish("/mui/tabbar/switch", this, {
					tabbarIdx : tabbarIdx
				});
			}
		},
		
		// 重写，过滤事务组件TransactionTabBarBtn
		getChildren : function(){
			var children = this.inherited(arguments);
			return array.filter(children,function(childWgt,idx){
				if(childWgt.isInstanceOf(TabBar)){
					return true;
				}
				return false;
			});
		}
	});
});