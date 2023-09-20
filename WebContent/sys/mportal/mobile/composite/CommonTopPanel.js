
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
	  "dojo/query",
	  "dojo/dom-geometry",
	  "dojo/dom-class",
	  "mui/list/_ViewDownReloadMixin",
	  "./BoxHeightMixin"
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
	  query,
	  domGeom,
	  domClass,
	  _ViewDownReloadMixin,
	  BoxHeightMixin
	  
	) {
	  var panel = declare(
	    "sys.mportal.CommonPanelWithViewDownReload",
	    [WidgetBase, Container, Contained, _ViewDownReloadMixin, BoxHeightMixin],
	    {
	      maxNum: 5,

	      width: "100%",

	      baseClass: "muiPortalPanel",

	      contentMap: null,

	      pageId: null,

	      drawByEven: false,

	      buildRendering: function () {
	        this.inherited(arguments);
	        this.setTopLayoutMinHeight && this.setTopLayoutMinHeight(this.domNode);
	        this.contentMap = this.contentMap || {};
	        if (this.drawByEven) {
	          // 首页缓存用事件触发渲染
	          this.subscribe("/mui/nav/drawPanpel", "drawByEvenFunction");
	        } else {
	          this.refresh();
	          this.resize();
	        }
	      },
	      
	      // 下拉刷新出发回调
	      onPull: function () {
	        this.refresh({ forceRefresh: true });
	        this.resize();
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
	         "/sys/mportal/navItem/changed",
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
	        var contentMap = this.contentMap;
	          pageId = (this.pageId = (evt && evt.pageId) || this.pageId),
	          deferred = new Deferred();
	        var curentPageInfo = this.getParent().getSelectPage(pageId);
	        if(!curentPageInfo){
	        	curentPageInfo = {};
	        }     
	       
	        var canDrag = true;
	        //外部链接
	        if(curentPageInfo.pageType == 2){
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
	          this.contentMap[content.tabId] = content;
	          this.addChild(content); 
	          
	        } else {
	          deferred.resolve();
	        }
	        
	        if(canDrag){
	        	if(this.optDown) this.optDown.use = true;
	        }else{
	        	if(this.optDown) this.optDown.use = false;
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
	        contentMap[pageId].show();
	        this.endDownSuccess();
	      },

	      destoryContent: function (pageId) {
	        var contentMap = this.contentMap,
	        child = contentMap[pageId];
	        child.destroyRecursive();
	        delete contentMap[pageId];
	      },

	      resize: function () {         
		    	var self = this;
		        setTimeout(function () {
		          var topHeight = domStyle.get(query(".muiHeaderBox")[0], "height");
		          var clientHeight = document.documentElement.clientHeight;		         
		          domStyle.set(
		            query("body")[0],
		            "min-height",
		            topHeight + clientHeight + "px"
		          );
		        }, 200);
	      },
	     
	    }
	  );
	  return panel;
	});
