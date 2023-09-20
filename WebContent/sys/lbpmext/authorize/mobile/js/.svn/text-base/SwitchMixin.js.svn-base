define(['dojo/_base/declare','dojo/topic','dojo/dom-construct'],
		function(declare,topic,domConstruct){
	
	return declare('lbpmext.authorize.mobile.resource.js.SwitchMixin',null,{
		
		property:'',
		
		buildRendering:function(){
			this.inherited(arguments);
			if(this.propDom){
				this.propDom.value = (this.value=='on')?true:false;
			}
			if(this.mode == 'view'){
			  this.disabled = true;
			}
		},
		//添加事件,供外部监听
		onStateChanged:function(newState){
			this.inherited(arguments);
			this.propDom.value = (newState=='on')?true:false;
		}
	});
});