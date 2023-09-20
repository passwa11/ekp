define( [ "dojo/_base/declare"], function(declare) {
	
	return declare("km.archives.mobile.js.ArchivesSelectorMixin", null, {
		
		
		templURL : "/km/archives/mobile/resource/tmpl/archselector.jsp",
		
		fdTemplatId:"",
		
		postCreate: function(){
			this.inherited(arguments);
			if(this.isMul) {
				this.templURL = "/km/archives/mobile/resource/tmpl/archselector_mul.jsp";
			}
			this.templURL +="?fdTemplatId="+this.fdTemplatId; 
		},
	});
	
});