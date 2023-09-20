define(["dojo/_base/declare", "dijit/_WidgetBase" , "dojo/dom-class"], 
		function(declare, WidgetBase, domClass) {
	
		var mixin = declare("mui.form._PlaceholderMixin", [WidgetBase], {		
			
			buildRendering : function(){
				this.inherited(arguments);
				if(this.edit){
					//显示placeholder的组件,默认先不显示标题
					domClass.remove(this.domNode,'showTitle');
				}
			},
			
			_readOnlyAction:function(value){
				this.inherited(arguments);
				var val = this._get("value");
				if(this.domNode){
					if(!val && this.edit){
						domClass.remove(this.domNode,"showTitle");
					}else{
						domClass.add(this.domNode,"showTitle");
					}
				}
			},
			
			_setValueAttr : function(value){
				this.inherited(arguments);
				if(this.domNode){
					if(!value && this.edit){
						domClass.remove(this.domNode,"showTitle");
					}else{
						domClass.add(this.domNode,"showTitle");
					}
				}
			}
		
		});
		return mixin;
});