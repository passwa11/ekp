define([
  "dojo/_base/declare",
  "dojo/dom-style",
  "dojo/dom-construct",
  "dijit/_WidgetBase",
  "dijit/_Contained",
  "dijit/_Container",
  "mui/util",
  "dojo/window",
  "dojo/on",
  "dojo/request",
  "./ContentLoadingMixin",
], function(
  declare,
  domStyle,
  domConstruct,
  WidgetBase,
  Contained,
  Container,
  util,
  win,
  on,
  request,
  ContentLoadingMixin
) {
  var content = declare(
    "sys.mportal.IframeContent",
    [WidgetBase, Container, Contained, ContentLoadingMixin],
    {
      baseClass: "muiIframeContent",

      url: null,
      
      idPrefix: null,
      
      height: 0,
      
      width: '100%',

      buildRendering: function() {   	  
        this.inherited(arguments);    
        
        domStyle.set(this.domNode, "width", "100%");
        domStyle.set(this.domNode, "height", this.height + "px");  
        domStyle.set(this.domNode, "display",  "inline-block");
        domStyle.set(this.domNode, "-webkit-overflow-scrolling",  "touch");
        domStyle.set(this.domNode, "overflow-y",  "scroll");
        domStyle.set(this.domNode, "webkit-overflow",  "auto");
        this.showContentLoading();
      },
      
      startup: function(){   	  
    	  this.inherited(arguments);
    	  this.createIfame();
      },
      
      
      //创建iframe展示框
      createIfame: function(){   	
    	  var height = this.height;  
    	  var timeStr = new Date().getTime();
          this.iframeNode = domConstruct.create("iframe",{
              style: "display: none",
              frameborder: "no",
              id: this.idPrefix + "_" +timeStr,
              width: "100%",
              height: height + "px",
          },this.domNode);
          var self = this;
          var navBar = document.getElementById(this.idPrefix + "_" +timeStr).parentNode.parentNode.parentNode.parentNode.parentNode.parentNode;
          if(navBar&&navBar.getAttribute("id")=="content"){
              domStyle.set(self.iframeNode, "height", height-50 + "px");
              domStyle.set(document.getElementById(this.idPrefix + "_" +timeStr).parentNode, "height", height-50 + "px");
          }
    	  on(this.iframeNode, "load", function(e){       	
            	domStyle.set(self.iframeNode, "display", "block");
            	self.hideContentLoading();
            	//console.log("iframe 加载完毕 。。。");
            });  
    	  this.iframeNode.src = this.url;   
          
      },
      
      show: function(cb) {
        domStyle.set(this.domNode, "display", "block")
        if (cb) {
          cb()
        }
      },
      hide: function(cb) {
        domStyle.set(this.domNode, "display", "none")
        if (cb) {
          cb()
        }
      }
    }
  )

  return content
})
