define([
  "dojo/_base/declare",
  "dojo/_base/lang",
  "dojo/_base/array",
  "dojo/dom-style",
  "dijit/_WidgetBase",
  "dijit/_Contained",
  "dijit/_Container",
  "../../../Card",
  "../../../TabCard",
  "../../ContentLoadingMixin",
  "dojo/request",
  "mui/util"
], function(
  declare,
  lang,
  array,
  domStyle,
  WidgetBase,
  Contained,
  Container,
  Card,
  TabCard,
  ContentLoadingMixin,
  request,
  util
) {
  var content = declare(
    "sys.mportal.ChildCommonContent",
    [WidgetBase, Container, Contained, ContentLoadingMixin],
    {
      baseClass: "muiPortalTabChildContent",
      
      tabId: "",

      pageId: "",

      data: null,

      pluginUrl:
        "/sys/mportal/sys_mportal_cpage/sysMportalCpage.do?method=loadPortlets&fdId=!{fdId}",

      buildRendering: function() {
        this.inherited(arguments)
        var self = this;
        
        if (this.data) {
           self.render(this.data)         
        }else{
    	  this.showContentLoading();  
    	  setTimeout(function(){
           	self.startRender();
          },50)
        } 
        
        this.renderComplete();          
      },
      
      startRender: function(){
    	var self = this;    	 
        var url = util.urlResolver(this.pluginUrl, {
          fdId: this.pageId
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
        this.values = data;   
        this.hideContentLoading();
        array.forEach(
          this.values,
          function(item) {
        	  var _item = null;
        	  try{
        		  _item = this.createListItem(this.parseConfig(item));
        	  }catch(e){
        		  console.log(e)
        	  }
            
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
