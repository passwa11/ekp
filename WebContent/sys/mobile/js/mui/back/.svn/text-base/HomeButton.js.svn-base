define([
    "mui/tabbar/TabBarButton",
	"dojo/_base/declare",
	"mui/i18n/i18n!sys-mobile",
	"mui/device/adapter",
	"dojo/dom-style",
	"dojo/dom-class",
	"dojo/dom-attr"
	], function(TabBarButton, declare, Msg , adapter, domStyle,domClass,domAttr) {
	
	var goHome = function() {
		var rtn = adapter.closeWindow();
		if(rtn==null){//无接口、接口调用不存在或失败情况
			location = dojoConfig.baseUrl?dojoConfig.baseUrl:'/';
		}
	};
	
	return declare("mui.back.HomeButton", [TabBarButton], {
		icon1: "mui mui-home",
		
		buildRendering:function(){
			this.inherited(arguments);
			
			domClass.add(this.domNode,'muiBarFloatRightButton');
			
			this.labelNode.innerHTML = Msg['mui.back.home'];
			domAttr.set(this.domNode, 'title', this.labelNode.innerHTML); 
		},
		
		startup: function(){
			this.inherited(arguments);
			if(this.iconDivNode){
				if(!this.icon1){
					domStyle.set(this.iconDivNode,{'width':'0px'});
				}
				//旧页面按钮写了行内样式，如果模块没改造，会导致按钮偏右
				if(domStyle.get(this.iconDivNode,"float") == 'right'){
					domStyle.set(this.iconDivNode,{'float':'none'});
				}
			}
		},
		
		_onClick : function(evt) {
			setTimeout(function(){
				goHome();
			}, 350);
		}
	});
});