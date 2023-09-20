define(['dojo/_base/declare', 'dijit/_WidgetBase', 'dojo/dom-construct', 'mui/util', 'dojo/touch'], 
		function(declare, WidgetBase, domConstruct, util, touch) {
	
	return declare('sys.mportal.SliderHeaderSearchBtn', [WidgetBase], {
		
		buildRendering: function() {
			
			this.inherited(arguments);
			
			domConstruct.create('i', {
				className : 'mui_ekp_portal_search fontmuis muis-search muiFontSizeXXXL muiFontColor'
			}, this.domNode);
			
			this.connect(this.domNode, touch.press, 'toSearchPage');
			
	    },
	    
	    
	    toSearchPage : function(){
	    	
	    	if(this.searchHost != null && this.searchHost != ''){
	    		
	    		location.href = this.searchHost;

	    	} else {
	    		
	    		location.href = util.formatUrl('/sys/ftsearch/mobile/index.jsp');
	    		
	    	}
	    	
	    }
	})
})

