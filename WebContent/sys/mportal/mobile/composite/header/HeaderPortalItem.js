define([
  "dojo/_base/declare",
  "dojo/_base/lang",
  "dojo/_base/array",
  "dojo/dom-style",
  "dojo/dom-class",
  "dojo/dom-construct",
  "dijit/_WidgetBase",
  "dijit/_Contained",
  "dijit/_Container",
  "dojo/request",
  "mui/util",
  "dojo/on",
  "dojo/topic"
], function(
  declare,
  lang,
  array,
  domStyle,
  domClass,
  domConstruct,
  WidgetBase,
  Contained,
  Container,
  request,
  util,
  on,
  topic
) {
  var headerPortalItem = declare(
    "sys.mportal.HeaderPortalItem",
    [WidgetBase, Container, Contained],
    {
    	
    	isSelected: false,
    	
    	data: null,   	
     
	    buildRendering: function() {    	  
	        this.inherited(arguments);
	        
	        var item = this.data;
	        var self = this;
			var style = "";
			
			if(this.isSelected){
				style = "color:#4285F4;background-color:rgba(66,133,244,0.10)";
			}			
		    var childNode = domConstruct.create("div",{ 
			  style: "padding:1rem;line-height: 1.5rem;word-break:break-word;" + style,
			  "composite-id":item.compositeId,
		      innerHTML: item.compositeName
		    },this.domNode);

	    	on(childNode,"click",function(e){		
				  e.preventDefault();
				  e.stopPropagation();
				  self._onClick(e);
			})

			
	     },
	     
	     _onClick: function(e){
	    	 if(this.isClicking){
	    		 return;
	    	 }
	    	 this.isClicking = true;
	    	 var self = this;
	    	 setTimeout(function(){
	    		 self.isClicking = false;
	    	 },100);
	    	 if(this.onClick){
	    		 this.onClick(e,this.data);
	    	 }
	     },
    }
  )

  return headerPortalItem
})
