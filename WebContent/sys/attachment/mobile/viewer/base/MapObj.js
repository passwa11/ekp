define([ "dojo/_base/declare", "dojo/topic", "dijit/_WidgetBase" ], function(
		declare, topic, _WidgetBase) {

	return declare("mui.attachment.mobile.MapObj", _WidgetBase, {
		keys : new Array(),
		data : new Object(),
		
		put : function(key, value) {
			if(this.data[key] == null){
				this.keys.push(key);
			}
			this.data[key] = value;
		},
    
		get : function(key) {
			return this.data[key];
		},
    
		size : function(){
			return this.keys.length;
		}
		
	});
})