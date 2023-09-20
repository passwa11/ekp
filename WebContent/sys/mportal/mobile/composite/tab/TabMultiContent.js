define([
  "dojo/_base/declare",
  "dojo/_base/lang",
  "dojo/_base/array",
  "dojo/dom-style",
  "dijit/_WidgetBase",
  "dijit/_Contained",
  "dijit/_Container",
  "dojo/request",
  "mui/util",
  "dojo/dom-construct",
  "dojo/parser",
  "dojo/topic"
], function(
  declare,
  lang,
  array,
  domStyle,
  WidgetBase,
  Contained,
  Container,
  request,
  util,
  domConstruct,
  parser,
  topic
) {
  var content = declare(
    "sys.mportal.TabMultiContent",
    [WidgetBase, Container, Contained],
    {
      baseClass: "muiPortalChildTabContent",

      width: "100%",
      
      //页签Id
      tabId: "",
      
      //页签数据
      tabInfo: null,
      
      //子页面数据
      data: null,
      
      //子页面ID
      pageId: "",
     
      //当前子页面ID
      currentPageId: "",
      
      //子页面列表数据
      data: null,

      buildRendering: function() {
        this.inherited(arguments)
        var self = this
        if (this.data) {
          self.render(this.data)
        }
        this.renderComplete();       
      },
      
      /**
       * 获取默认选中的子页面Id
       */
      getSelectPage: function(){  
    	  if(!this.data ||  this.data.length <= 0){
    		  return null;
    	  }
    	  var page = null;
      	  for(var i=0; i < this.data.length; i++){
      		  var item = this.data[i];
      		  if(this.currentPageId == item.fdId){
      			  return item;
      		  }
      		  if(!page){    			
				 var pageType = item.pageType;
      			 var pageUrlOpenType = item.pageUrlOpenType;
      			 //非外部链接
      			 if(pageType != 2){
      				page = item;
      			 }
      			 // 外部链接 并且 打开方式为本窗口内打开
      			 if(pageType == 2 && pageUrlOpenType == 1){
      				page = item;
      			 }
      		  }
      	  }
          if(!page){
        	 page = this.data[0];
          }
          this.currentPageId = page.fdId;
  		  return page;
      },
      
      render: function(data){
    	  this.inherited(arguments);
    	  this.currentPageId = util.getUrlParameter(location.href, "fdChildPageId") || '';
    	  var curentPageInfo = this.getSelectPage();
    	  if(curentPageInfo){
    		  this.startRender(curentPageInfo);
    	  }   	  	 
   	  	 this.renderComplete();
      },
      
      
      startRender: function(curentPageInfo){
    	//1.构建子页面navbar
    	  this.navtmpl = '<div class="mui_ekp_portal_title_list">'
  			+ '<section class="et-portal-tabs main" '
  			+ ' data-dojo-type="sys/mportal/mobile/composite/tab/child/ChildNavBar"'
  			+ ' data-dojo-props="height:\'5rem\',drawByEven:true,tabId:\'' + this.tabId + '\'">'
  			+ '</section>'
  			+ '</div>';   
    	  domStyle.set(this.domNode, "width", "100%");
    	  domStyle.set(this.domNode, "height", "100%");
  		  this.navNode = domConstruct.toDom(this.navtmpl);				 
  	  	  domConstruct.place(this.navNode, this.domNode, "first");
  	  	  var self = this;
  	  	  parser.parse(this.navNode).then(function(instances){
  	  		//设置默认选中ID
  	  		instances[0].pageId = self.currentPageId;
  	  		// 渲染navBar
  	  		 self.drawNavBar(self.data);
  	  	  });	

	    	//2.构建子页面pannel
	  	  	 
	  	  	var pageId = "";
	  	  	this.panelTmpl = '<div class="muiPortalView">'
				 + ' <div data-dojo-type="sys/mportal/mobile/composite/tab/child/ChildPanel" '
				 + ' data-dojo-props="drawByEven:true,tabId:\'' + this.tabId + '\'">'
				 + '</div>'
				 + '</div>';	 
			 this.panelNode = domConstruct.toDom(this.panelTmpl);			 
					 
	   	  	 domConstruct.place(this.panelNode, this.domNode, "last");
	   	  	 
	   	  	 parser.parse(this.panelNode).then(function(instances){
	   	  		instances[0].data = self.data;
	   	  		var useIframe = false;
	   	  		if(curentPageInfo.pageType == 2){
	   	  			useIframe = true;
	   	  		}
	   	  		//渲染panel
	   	  		var data = {
	   	  			pageId: self.currentPageId,
	   	  			tabId: self.tabId,
	   	  			pageUrl: curentPageInfo.pageUrl,
	   	  			useIframe: useIframe
	   	  		};
	   	  		topic.publish('/mui/nav/composite/child/drawPanpel', data );
	   	  	 });   
      },
      
      // 渲染navbar
		drawNavBar: function(selectPage){			
			var data = this.buildNavData(selectPage);
			topic.publish('/mui/nav/composite/child/drawNavBarMore', this.tabId);
			topic.publish('/mui/nav/composite/child/drawNavBar', data, this.tabId);	
		},
		
		// 包装navBar数据
		buildNavData : function (selectPage){	
			var arr = new Array();
			for(var i = 0; i < selectPage.length; i++) {
				var obj = selectPage[i];
				arr[i] = [obj.fdId, obj.fdName, obj.pageType, obj.pageUrl, obj.pageUrlOpenType];
			}
			return arr;
		},
	
		destroy: function(){
			this.inherited(arguments);
			if(this.navNode){
				this.navNode.domNode.outerHTML = "";
			}
			if(this.panelNode){
				this.panelNode.domNode.outerHTML = "";
			}
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
