define([
  "dojo/_base/declare",
  "dijit/_WidgetBase",
  "dijit/_Contained",
  "dijit/_Container",
  "dojo/dom-construct",
  "dojo/_base/lang",
  "dojo/parser",
  "dojo/request",
  "dojo/Deferred",
  "mui/util",
  "dojo/dom-style"
], function(
  declare,
  WidgetBase,
  Contained,
  Container,
  domConstruct,
  lang,
  parser,
  request,
  Deferred,
  util,
  domStyle
) {
  var header = declare(
    "sys.mportal.Header",
    [WidgetBase, Container, Contained],
    {
      width: "100%",

      url: "/sys/mportal/sys_mportal_page/sysMportalPage.do?method=loadPages",

      pageId: "",

      pageName: "",

      //是否显示更多
      showMore: false,

      forceRefresh: false,

      baseClass: "",

      buildRendering: function() {
        this.inherited(arguments)

        if (this.headerType == "1") domStyle.set(this.domNode, "height", "9rem")
        else domStyle.set(this.domNode, "height", "6rem")

        this.subscribe('/sys/mportal/header/drawTmpRender','tmpRender');
      },

      initParams : function(obj){
    	  this.userName = obj.user.name;
    	  this.imgUrl = obj.user.imgUrl;
    	  this.logo = util.formatUrl(obj.pageObj.pageLogo);
      },
      
      tmpRender: function(obj) {
    	  
    	  this.initParams(obj);
    	  this.buildTmpl();
    	  
    	  var self = this;
    	  var tmpl = this.tmpl;
    	  if (!tmpl) {
    		  return
    	  }
    	  var defer = new Deferred();
          defer.resolve();
          defer.then(lang.hitch(this, function() {
        	  if (this.headerType == 1) {
        		  var datas = {
        			  __searchHost: this.searchHost
        		  }
        	  } else {
        		  var datas = {
        		      __itEnabled: this.itEnabled,
        		      __itUrl: this.itUrl,
        		      __searchHost: this.searchHost
        		  }
        	  }
        	  var __html = lang.replace(tmpl.trim(), datas)
        	  this.headerNode = domConstruct.toDom(__html)
        	  domConstruct.place(this.headerNode, this.domNode, "last")
        	  parser.parse(this.headerNode)
          }))
      }
    }
  )

  return header
})
