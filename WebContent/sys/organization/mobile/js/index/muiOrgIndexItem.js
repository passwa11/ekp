define([
  "dojo/_base/declare",
  "dijit/_WidgetBase",
  "mui/util",
  "dojo/dom-construct"
], function(declare, WidgetBase, util, domConstruct) {
	var orgIndexItem = declare("mui.org.index.item", [WidgetBase], {
		
	    buildRendering: function() {
	    	
	    	this.inherited(arguments);
	    	
	    	this.buildLeft();
	    	
	    	this.buildRight();
	    	
    		this.connect(this.domNode, 'click', 'onClick');
	    	
	    },
	    
	    buildLeft : function() {
	    	var muiSysOrgEcoLeft = domConstruct.create("div" ,{
	    		className: 'muiSysOrgEcoLeft'
	    	}, this.domNode);
	    	
	    	domConstruct.create("div" ,{
	    		className: this.icon
	    	}, muiSysOrgEcoLeft);
	    },
	    
	    buildRight : function() {
	    	var muiSysOrgEcoRight = domConstruct.create("div" ,{
	    		className: 'muiSysOrgEcoRight'
	    	}, this.domNode);
	    	
	    	domConstruct.create("div" ,{
	    		className: 'label',
	    		innerHTML: this.label
	    	}, muiSysOrgEcoRight);
	    	
	    	domConstruct.create("div" ,{
	    		className: 'desc',
	    		innerHTML: this.desc
	    	}, muiSysOrgEcoRight);
	    	
	    },
	    
	    onClick : function () {
	    	if(this.href)
	    		window.location.href = util.formatUrl(this.href);
	    }
	});

	return orgIndexItem;
})
