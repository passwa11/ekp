define([
  "dojo/_base/declare",
  "dojo/_base/lang",
  "dojo/_base/array",
  "dojo/dom-style",
  "dojo/dom-construct",
  "dijit/_WidgetBase",
  "dijit/_Contained",
  "dijit/_Container",
  "../../Card",
  "../../TabCard",
  "../ContentLoadingMixin",
  "../ClearIOSBounceMixin",
  "../BoxHeightMixin",
  "sys/mobile/js/mui/list/_ViewDownReloadMixin",
  "dojo/request",
  "mui/util",
  "dojo/window"
], function(
  declare,
  lang,
  array,
  domStyle,
  domConstruct,
  WidgetBase,
  Contained,
  Container,
  Card,
  TabCard,
  ContentLoadingMixin,
  ClearIOSBounceMixin,
  BoxHeightMixin,
  _ViewDownReloadMixin,
  request,
  util,
  win
) {
  var content = declare(
    "sys.mportal.TabCommonContent",
    [WidgetBase, Container, Contained, ContentLoadingMixin, _ViewDownReloadMixin, BoxHeightMixin],
    {
      baseClass: "muiPortalTabCommonContent",

      tabId: "",      

      data: null,
      
      useParentScroll: false,

      pluginUrl:
        "/sys/mportal/sys_mportal_cpage/sysMportalCpage.do?method=loadPortlets&fdId=!{fdId}",

      buildRendering: function() {
        this.inherited(arguments);
        if(this.optDown){
        	this.optDown.use = false;
        }
     
        var self = this;               
        if (this.data) {
           self.render(this.data)         
        }else{
    	  this.showContentLoading();  
    	  setTimeout(function(){
           	self.startRender();
          },50)
        }              
        if(this.renderComplete){
        	this.renderComplete();       
        }
      },
//      
//      initDownScroll: function(){
//    	  if(!this.useParentScroll){
//    		  this.inherited(arguments);
//    	  }
//      },
      
      
     //判断是够为ios设备
	 isIOS: function (){
		var iOS = /iPad|iPhone|iPod/.test(navigator.userAgent) && !window.MSStream;
		if (iOS) {
			return true;
		}
		return false;
	 },
      
      optUp: {
    	  isBounce: false
      },
      
      getScrollTop: function() {
        return (
          document.documentElement.scrollTop ||
          document.body.scrollTop
        );
      },
      
      // 滚动容器的高度
      getClientHeight: function () {
        return win.getBox().h;
      },
      
      // 滚动内容的高度
      getScrollHeight: function(){
     	 return document.body.scrollHeight;
      },
      
      
      startup: function(){
    	  if(!this.isIOS()){
          	this.scrollDom = false;          
          }
    	 this.inherited(arguments);  
      },
      
      startRender: function(){ 
    	  var self = this;
          var url = util.urlResolver(this.pluginUrl, {
            fdId: this.tabId
          })
          var promise = request.get(util.formatUrl(url), {
            headers: {
              Accept: "application/json"
            },
            handleAs: "json"
          })
          promise.response.then(function(data) {
        	self.hideContentLoading();
            if (!data || !data.data) return
            if (data.data) self.render(data.data)
          })
      },
      
      
      parseConfig: function(item) {
        return lang.mixin(
          {
            close: false
          },
          item
        )
      },
      
      render: function(data) {   	  
    	this.hideContentLoading();  
        this.values = data;
        array.forEach(
          this.values,
          function(item) {
            var _item = this.createListItem(this.parseConfig(item))

            if (_item) {
              this.addChild(_item)
            }
          },
          this
        )      
      },

      createListItem: function(item) {
        if (item.configs.length <= 1) return new Card(item)
        else return new TabCard(item)
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
