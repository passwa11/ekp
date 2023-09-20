define([
  "dojo/_base/declare",
  "dojo/dom-style",
  "dijit/_WidgetBase",
  "dijit/_Contained",
  "dijit/_Container",
  "dojo/topic",
  "dojo/Deferred",
  "dojo/_base/lang",
  "./tab/TabCommonContent",
  "./tab/TabMultiContent",
  "./tab/TabIframeContent",
  "./tab/TabIframeSinglePageContent",
  "dojo/query",
  "dojo/dom-geometry",
  "dojo/dom-class",
  "./FixNavBarPositionMixin",
], function (
  declare,
  domStyle,
  WidgetBase,
  Contained,
  Container,
  topic,
  Deferred,
  lang,
  TabCommonContent,
  TabMultiContent,
  TabIframeContent,
  TabIframeSinglePageContent,
  query,
  domGeom,
  domClass,
  FixNavBarPositionMixin
) {
  var panel = declare(
    "sys.mportal.CommonPanel",
    [WidgetBase, Container, Contained, FixNavBarPositionMixin],
    {
      maxNum: 5,

      width: "100%",

      baseClass: "muiPortalPanel",

      contentMap: null,

      pageId: null,

      drawByEven: false,

      buildRendering: function () {
        this.inherited(arguments);
        this.contentMap = this.contentMap || {};
        if (this.drawByEven) {
          // 首页缓存用事件触发渲染
          this.subscribe("/mui/nav/drawPanpel", "drawByEvenFunction");
        } else {
          this.refresh();
          this.resize();
        }
      },     

      // 重写获取滚动位置方法
      getScrollTop: function () {
        return (
          document.documentElement.scrollTop ||
          document.body.scrollTop
        );
      },

      drawByEvenFunction: function (pageId) {
        if (!this.pageId) this.pageId = pageId;
        this.refresh();
        this.resize();
      },

      startup: function () {
        this.inherited(arguments);
        topic.subscribe(
          "/sys/mportal/tabItem/changed",
          lang.hitch(this, this.refresh)
        );
        topic.subscribe(
          "/sys/mportal/commonpanel/refresh",
          lang.hitch(this, this.refresh)
        );
      },

      // TODO 兼容静态数据源
      refresh: function (evt) { 
        var self = this;
        var prePageId = this.pageId;
        var contentMap = this.contentMap,
          pageId = (this.pageId = (evt && evt.pageId) || this.pageId),
          deferred = new Deferred();
        var curentPageInfo = this.getParent().getSelectPage(pageId);
        if(!curentPageInfo){
        	curentPageInfo = {};
        }  
        
        if(this.contentMap && this.contentMap[prePageId]){
        	var preContent = this.contentMap[prePageId];
        	var currentScrollTop = this.getScrollTop();
        	if(preContent){
        		preContent.currentScrollTop = currentScrollTop;
        	}
        }
        
        
        var canDrag = true;
        //页签或者外部链接
        if(curentPageInfo.fdType == 2 || curentPageInfo.pageType == 2){
        	canDrag = false;
        }
        var tabId = pageId;
        
        if (!this.contentMap[pageId] || (evt && evt.forceRefresh)) {
          //强制刷新，移除原来的
        	
          if (this.contentMap[pageId]) {
            this.contentMap[pageId].destroyRecursive(); 
            this.contentMap[pageId] = null;         
          }
          var content = null;
          
          //构建页签
          if(curentPageInfo.fdType == 2){
        	  //当前页签包含页面的个数
        	  var tabPageSize=curentPageInfo.childs.length;
        	  if(tabPageSize>1){
        		  content = new TabMultiContent({
        			  tabId: tabId,
                      data: curentPageInfo.childs,
                      tabInfo: curentPageInfo,
                      fixNavBarPosition: function(){
                    	  self.__fixNavBarPositionWithTabBarBottom();
                      },
                      renderComplete: function () {
                        deferred.resolve();
                      },
            	  });
        	  }else{
        		  //构建Iframe页面（外部链接）
            	  if(curentPageInfo.childs[0].pageType == 2){
            		  //链接打开方式
            		  if(curentPageInfo.childs[0].pageUrlOpenType=='2'){//跳转
            			  content = new TabIframeSinglePageContent({
                			  tabId: tabId,
                    		  url: curentPageInfo.childs[0].pageUrl,
                    		  pageInfo: curentPageInfo,
                              renderComplete: function () {
                                deferred.resolve();
                              },
                    	  });
            		  }else{//内嵌
            			  content = new TabIframeContent({
                			  tabId: tabId,
                    		  url: curentPageInfo.childs[0].pageUrl,
                    		  pageInfo: curentPageInfo,
                              renderComplete: function () {
                                deferred.resolve();
                              },
                    	  });
            		  }
            		  
            	  }else{
            		  //构建公共门户页面
            		  content = new TabCommonContent({
            			  tabId: tabId,
                          pageInfo: curentPageInfo.childs[0],
                          data: typeof memory != "undefined" ? memory[curentPageInfo.childs[0].pageId] : null,
                          renderComplete: function () {
                            deferred.resolve();
                          },
                        });  
            	  }
        	  }	  
          }
          //构建页面
          if(curentPageInfo.fdType == 1){
        	  //构建Iframe页面（外部链接）
        	  if(curentPageInfo.pageType == 2){
        		  content = new TabIframeContent({
        			  tabId: tabId,
            		  url: curentPageInfo.pageUrl,
            		  pageInfo: curentPageInfo,
                      renderComplete: function () {
                        deferred.resolve();
                      },
            	  });
        	  }else{
        		  //构建公共门户页面
        		  content = new TabCommonContent({
        			  tabId: tabId,
                      pageInfo: curentPageInfo,
                      data: typeof memory != "undefined" ? memory[curentPageInfo.pageId] : null,
                      renderComplete: function () {
                        deferred.resolve();
                      },
                    });  
        	  }
          }
          this.contentMap[content.tabId] = content;
          this.addChild(content); 
          
          //设置底部导航栏高度
          if( this.getParent().compositeNavLayout == 2){
        	  domStyle.set(content.domNode, "padding-bottom", "5rem");
          }
          
        } else {
          deferred.resolve();
        }
       
        deferred.promise.then(function () {
          self.refreshComplete(tabId);
        });
      },

      refreshComplete: function (pageId) {
        var contentMap = this.contentMap;
        //隐藏所有的content
        for (_pageId in contentMap) {
          contentMap[_pageId].hide();
        }
        //显示指定的content
        if(contentMap[pageId]){
        	this.resetScrollTop(contentMap[pageId].currentScrollTop || 0);
        }
        contentMap[pageId].show();
        
        this.endDownSuccess();
      },
      
      //重置滚动条高度
      resetScrollTop: function(scrollTop){
    	  //android重置滚动位置有问题
    	  setTimeout(function(){
    		  if(document.documentElement &&  document.documentElement.scrollTo){
    			  document.documentElement.scrollTo(0,scrollTop);
    		  }
    		  document.body.scrollTo(0,scrollTop);
          },20);
      },

      destoryContent: function (pageId) {
        var contentMap = this.contentMap,
        child = contentMap[pageId];
        child.destroyRecursive();
        delete contentMap[pageId];
      },

      resize: function () {	  	
        return;               
      }
     
    }
  );
  return panel;
});
