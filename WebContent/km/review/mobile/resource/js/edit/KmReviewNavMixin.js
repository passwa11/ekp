define(["dojo/_base/declare", 
        "dojo/store/Memory" , 
        "dojo/topic", 
        "dijit/registry",
        "mui/i18n/i18n!km-review:mui.kmReviewMain.mobile.info",
        "mui/i18n/i18n!km-review:mui.kmReviewMain.mobile.review"
], function(declare, Memory, topic, registry, Msg1, Msg2) {
	
      return declare("kms.multidoc.MultidocNavMixin", null, {
    	  
		    store: new Memory({
		      data: [
		        {
		          text: Msg1["mui.kmReviewMain.mobile.info"],
		          'moveTo' : 'scrollView',
		          'selected' : true
		        },
		        {
		          text: Msg2["mui.kmReviewMain.mobile.review"],
		          'moveTo' : 'lbpmView'
		        }
		      ]
		    })
      
	  });
      
})