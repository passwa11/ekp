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
    "sys.mportal.TabIframeSinglePageContent",
    [WidgetBase, Container, Contained],
    {
      baseClass: "",

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
          var detailWarp=domConstruct.create("div", {
        	  className: "muiPortalSinglePageContent",
        	  style: {
        		  width : win.getBox().w+'px',
        		  height : height+'px'
			  }
          }, this.domNode);
          var centerDiv =  domConstruct.create("div", {className: ""}, detailWarp);
          domConstruct.create("div", {className: "",innerHTML:"当前链接打开方式为新窗口"}, centerDiv);
          var clickButton=domConstruct.create("div", {className: "clickButton",innerHTML:"点击跳转"}, centerDiv);
          this.connect(clickButton, "click", function(){
				if(self.click_doing){
					return;
				}
				self.click_doing = true;
				setTimeout(function(){
					self.click_doing = false;
				},100)
				window.open(this.url,'_self')
		    }); 
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
