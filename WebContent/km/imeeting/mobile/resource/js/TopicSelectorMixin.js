define( [ "dojo/_base/declare"], function(declare) {
	
	return declare("km.imeeting.mobile.js.TopicSelectorMixin", null, {
		templURL : "/km/imeeting/mobile/resource/tmpl/topicselector.jsp",
		
		postCreate: function(){
			this.inherited(arguments);
			
			if(this.isMul) {
				this.templURL = "/km/imeeting/mobile/resource/tmpl/topicselector_mul.jsp";
			}
			
		},
		
	});
	
});