define(["dojo/_base/declare", 
        "dojo/store/Memory" , 
        "dojo/topic", 
        "dijit/registry",
        "mui/i18n/i18n!sys-news:mobile"
], function(declare, Memory, topic, registry, Msg) {
	
      return declare("kms.multidoc.MultidocNavMixin", null, {
    	  
		    store: new Memory({
		      data: [
		        {
		          text: Msg["mobile.sysNews.add.newsContent"],
		          'moveTo' : 'scrollView',
		          'selected' : true
		        },
		        {
		          text: Msg["mobile.sysNews.add.review"],
		          'moveTo' : 'lbpmView'
		        }
		      ]
		    })
      
	  });
      
})