define(["dojo/_base/declare", "dijit/_WidgetBase" , "dojo/dom-class","dojo/query","dojo/dom"], 
		function(declare, WidgetBase, domClass,query,dom) {
	
		var mixin = declare("sys/lbpmservice/mobile/common/_MaxApprovalMixin", null, {		
			
			buildRendering : function(){
				this.inherited(arguments);		
			},
			startup: function () {
			     this.inherited(arguments);
			     // 给图标绑定点击事件
			     this.connect(query(".textarea_dropbox")[0], "click", "_handleOpenMax");
			     this.connect(query(".textarea_dropbox_icon")[0], "click", "_handleCloseMax");
			     this.connect(query(".textarea_dropbox_content")[0], "click", "_handleStopEvent");
			 },	
			 _handleOpenMax: function(evt){
				 var oriVal = query(".muiOriginalTextarea")[0].value;
				 var mask = query('.muiDialogElementMask')[0];
				 if(!domClass.contains(mask,"show")){
					 domClass.add(mask,"show");
					 if(oriVal){
						 query('.muiScaleTextarea')[0].value = oriVal;
					 }
				 }
				
			 },
			 _handleCloseMax: function(evt){
				 var scalValue = query('.muiScaleTextarea')[0].value;
				 if(scalValue){
					 query('.muiOriginalTextarea')[0].value = scalValue;
					 domClass.remove(query('.muiDialogElementMask')[0],"show");
				 }
			 },
			 _handleStopEvent: function(evt){
				 dojo.stopEvent(evt);
			 }
		
		});
		return mixin;
});