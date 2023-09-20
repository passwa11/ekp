define([
  "dojo/_base/declare",
  "dojo/_base/lang",
  "dojo/_base/array",
  "dojo/dom-style",
  "dojo/dom-class",
  "dojo/dom-construct",
  "mui/util",
  "dojo/on",
  "dojo/topic",
  "mui/loadingMixin"
], function(
  declare,
  lang,
  array,
  domStyle,
  domClass,
  domConstruct,
  util,
  on,
  topic,
  loadingMixin
) {
  var contentLoading = declare(
    "sys.mportal.contentLoadingMixin",[loadingMixin],
    {     
    	showContentLoading: function(){
    		 var loadingStyle = "margin: 0 auto;width: 52px;height: 52px;font-size: 52px;" 
             	+	"-webkit-animation: loading 1s ease-in-out infinite;animation: loading 1s ease-in-out infinite;"
             	+ "border-radius: 50%;border-style: solid;border-color: #4285f4 #eee #eee;  margin-left: -26px; margin-top: -26px;"
             	+ " position: absolute; top: 50%; left: 50%;";
             if(!this.contentLoading){
            	 this.contentLoading = domConstruct.create("div",{
                  	innerHTML: "<div style='" + loadingStyle + "'></div>",       	
                  	style: "width: 100%;height: 100%;display: flex;align-items: center;justify-content: center;"
                  },this.domNode);
             }
            domStyle.set(this.contentLoading, "display", "flex");
    		//this.buildLoading(this.contentLoading);
    	},
    	 
    	hideContentLoading: function(){
    		//this.destroyLoading(this.contentLoading);
    		if(this.contentLoading){
    			domStyle.set(this.contentLoading, "display", "none"); 
    		}
    	}

    }
  )

  return contentLoading
})
