define(["dojo/_base/declare","mui/tabbar/TabBar","./CommonUsageButton", "dojo/json",
	"dojo/dom-class"
	],function(declare,Tabbar,CommonUsageButton,JSON,domClass){
	return declare("sys.lbpmservice.mobile.workitem.CommonUsageTabbar",[Tabbar],{
		
		buttons:[],
		
		buildRendering:function(){
			this.inherited(arguments);
			this.buildButtons();
			
			domClass.add(this.domNode,'commonUsageTabbar');
		},
		
		buildButtons:function(){
			var _self = this;
			this.buttons.forEach(function(item,index){
				item.tabIndex = index;
				var button = new CommonUsageButton(item);
				domClass.add(button.domNode,'commonUsageButton');
				_self.addChild(button,index);
			})
		}
		
	});
})