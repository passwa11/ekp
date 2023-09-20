define(['dojo/_base/declare','dojo/topic','dojo/dom-construct'],
		function(declare,topic,domConstruct){
	
	return declare('hr.ratify.mobile.resource.js.SwitchMixin',null,{
		
		property:'',
		
		buildRendering:function(){
			this.inherited(arguments);
			this.propDom.value=this.value=='on'?true:false;
		},
		//添加事件,供外部监听
		onStateChanged:function(newState){
			this.inherited(arguments);
			var _value=newState=='on'?true:false;
			this.propDom.value=_value;
			topic.publish('hr/ratify/statChanged',this,_value);
		}
		
		
	});
	
	
	
});