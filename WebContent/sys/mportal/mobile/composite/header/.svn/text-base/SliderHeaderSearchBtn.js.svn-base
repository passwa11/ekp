define(['dojo/_base/declare', 'dijit/_WidgetBase', 'dojo/dom-construct', 'mui/util', 'dojo/touch', "dojo/on","dojo/dom-class","dojo/query","dojo/dom-style"], 
		function(declare, WidgetBase, domConstruct, util, touch, on,domClass, query, domStyle) {
	
	return declare('sys.mportal.SliderHeaderSearchBtn', [WidgetBase], {
		canScroll: true,
		buildRendering: function() {
			
			this.inherited(arguments);
			
			
			if(this.getParent() && this.getParent().handleCompositeChange){
				this.getParent().handleCompositeChange(this);
			}
			
			var serachNode = domConstruct.create('i', {
				className : 'mui_ekp_portal_search fontmuis muis-search muiFontSizeXXXL muiFontColor'
			}, this.domNode);
			
			this.connect(serachNode, touch.press, 'toSearchPage');
			
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

