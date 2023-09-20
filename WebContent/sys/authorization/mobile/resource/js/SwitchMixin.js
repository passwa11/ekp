define(['dojo/_base/declare','dojo/topic','dojo/dom-construct'],
		function(declare,topic,domConstruct){
	
	return declare('sys.authorization.mobile.resource.js.SwitchMixin',null,{
		
		property:'',
		
		buildRendering:function(){
			this.inherited(arguments);
			this.domNode.dojoClick = true;
			//this.propDom=domConstruct.create('input',{type:'hidden',name:this.property,value:this.value=='on'?true:false},this.domNode);
			this.propDom.value=this.value=='on'?true:false;
		},
		//添加事件,供外部监听
		onStateChanged:function(/*String*/newState){
			this.inherited(arguments);
			var _value=newState=='on'?true:false;
			this.propDom.value=_value;
			topic.publish('sys/authorization/AuthChanged',this,_value);
		}
		
		
	});
	
	
	
});