/**
 * 多页签组件，继承NavBarStore
 */
define([
	"dojo/_base/declare",
	"mui/nav/StaticNavBar",
	"sys/xform/mobile/controls/mutiTab/TabItem",
	 "dijit/registry",
	 "dojo/topic",
	 "dojo/query"
	],function(declare,StaticNavBar,TabItem,registry,topic,query){
	return declare("sys.xform.mobile.controls.MutiTab",[StaticNavBar],{
		itemRenderer:TabItem,
		
		postCreate:function(){
			this.subscribe("/mui/validate/afterValidate","_afterValidate");
		},
		
		_afterValidate:function(wgt,errors){
			var memory = this.memory;
			var target;
			for(var i=0; i<memory.length; i++){
				var item = memory[i];
				var viewNode = query("[name='"+item.moveTo+"']",this.domNode.parentNode)[0];
				if(!viewNode)
					continue;
				var view = registry.byNode(viewNode);
				if(!view)
					continue;
				var isExit = false;
				for(var j=0; j<errors.length; j++){
					var index = view.getIndexOfChild(errors[j]);
					if(index != -1){
						isExit = true;
						break;
					}
				}
				if(isExit){
					target = item.moveTo;
					break;
				}
			}
			if(target){
				//模拟点击
				topic.publish("/mui/navitem/selected",this,{key:target});
			}
		}
	})
})