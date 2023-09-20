define([
  "dojo/_base/declare",
  "dojo/_base/lang",
  "dojo/_base/array",
  "dojo/dom-style",
  "dojo/dom-construct",
  "dijit/_WidgetBase",
  "dijit/_Contained",
  "dijit/_Container",
  "dojo/request",
  "mui/util",
  "dojo/window",
  "dojo/query",
  "dojo/on",
  "../IframeContent"
], function(
  declare,
  lang,
  array,
  domStyle,
  domConstruct,
  WidgetBase,
  Contained,
  Container,
  request,
  util,
  win,
  query,
  on,
  IframeContent
) {
  var content = declare(
    "sys.mportal.CommonIframe",
    [WidgetBase, Container, Contained],
    {
      baseClass: "muiPortalContent",

      tabId: "",
      
      tabInfo: "",

      data: null,

      url: null,
      
      iframeHeight: 0,

      buildRendering: function() { 	  
        this.inherited(arguments);
        var self = this;
        //加速渲染，避免网络缓慢时产生延迟
        setTimeout(function(){
          	self.startRender();
        },50)
        this.renderComplete && this.renderComplete();
      },
      
      startRender: function(){
          var topHeight = 0;
          var bottomHeight = 0;
          if(query(".muiHeaderBox")[0]){
          	topHeight = domStyle.get(query(".muiHeaderBox")[0], "height");
          }
          if(query(".muiBottomTabBarBox")[0]){
      	   bottomHeight  = domStyle.get(query(".muiBottomTabBarBox")[0], "height");
          }
         
          var height = win.getBox().h;
          height = height - topHeight - bottomHeight;        
          this.iframeContent = new IframeContent({
          	url: this.url,
          	idPrefix: this.tabId,
          	width: win.getBox().w,
          	height: height
          });
          this.iframeContent.placeAt(this.domNode); 
      },
      
      startup: function(){   	  
    	  this.inherited(arguments);   	
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
