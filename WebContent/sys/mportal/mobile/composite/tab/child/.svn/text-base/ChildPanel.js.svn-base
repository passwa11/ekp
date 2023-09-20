define([
  "dojo/_base/declare",
  "dojo/dom-style",
  "dijit/_WidgetBase",
  "dijit/_Contained",
  "dijit/_Container",
  "dojo/topic",
  "dojo/Deferred",
  "dojo/_base/lang",
  "./ChildCommonContent",
  "./ChildIframeContent",
  "dojo/query",
  "mui/list/_ViewDownReloadMixin",
  "dojo/dom-construct",
  "dojo/parser",
  "dojo/dom-geometry",
  "dojo/window",
  "../../BoxHeightMixin"
], function (
  declare,
  domStyle,
  WidgetBase,
  Contained,
  Container,
  topic,
  Deferred,
  lang,
  ChildCommonContent,
  ChildIframeContent,
  query,
  _ViewDownReloadMixin,
  domConstruct,
  parser,
  domGeom,
  win,
  BoxHeightMixin
) {
  var panel = declare(
    "sys.mportal.childPanel",
    [WidgetBase, Container, Contained, _ViewDownReloadMixin, BoxHeightMixin],
    {
      maxNum: 5,

      width: "100%",

      baseClass: "muiChildPortalPanel",

      contentMap: null,
      
      //页签ID
      tabId: null,

      pageId: null,

      drawByEven: false,
      
      //是否进行滚动
      startScrollInterval: false,
      //最大定数次数
      scrollMaxIntervalCount: 40,
      //定时次数
      scrollIntervalCount: 1,
      //定时事件
      scrollInterval: null,
      //滚动频率(毫秒)
      scrollIntervalRate: 50,
      
      optUp: {
    	  isBounce: false
      },
      
      // 下拉刷新出发回调
      onPull: function () {    	
        this.refresh({ forceRefresh: true, tabId: this.tabId, pageId: this.pageId });
        this.resize();
      },

      buildRendering: function () {
        this.inherited(arguments);
        this.setMinHeight(this.domNode);
        
        this.contentMap = this.contentMap || {};
                
        if (this.drawByEven) {
            // 首页缓存用事件触发渲染
            this.subscribe("/mui/nav/composite/child/drawPanpel", "drawByEvenFunction");
          } else {
            this.refresh();
            this.resize();
          }
        
      },
      
      
      setMinHeight: function(domNode){    	   
            var height = win.getBox().h;
            height = height - this.getBoxTotalHeight();
            this.containerHeight = height;
            domStyle.set(domNode, "min-height", height + "px");
      },
      
      getClientHeight : function () {
			return  this.containerHeight;
      },
      
      touchstartEvent: function(e){   
    	  this.startPoint = this.getPoint(e); // 记录起点
  	      this.lastPoint = this.startPoint; // 重置上次move的点
    	  if(this.useParentScroll){
    		  var height = this.domNode.scrollHeight;
    		  domStyle.set(this.domNode, "height", height + "px");
    	  }else{
    		  domStyle.set(this.domNode, "height", "inherited");
    		  /**
    		   * 1:安卓、iPhone都为 position:absolute布局
    		   * 2:安卓为 position: fixed布局，iPhone为 position:absolute 布局
    		   */
        	  if(window.__mportal_bottomTabBarPositionType == "1" && !this.isIOS()){
        		  var scrollTop = Math.abs(domGeom.position(query(".muiHeaderBox")[0]).y);
                  if(scrollTop <= 0){ 
                	  this.inherited(arguments);
                  }  
        	  }else{
        		  this.inherited(arguments);
        	  }
        	  this.startScrollFunction();	 
    	  }
    	  
      },
      
      touchmoveEvent: function(e){  
    	  var curPoint = this.getPoint(e);
    	  var moveY = curPoint.y - this.startPoint.y;
    	  //多层滚动嵌套，使用父窗口滚动
    	  if(this.useParentScroll){
    		  //下拉操作不禁止子窗口事件
    		  if(moveY < 0){
    			  this.inherited(arguments);
    		  }
    	  }else{
    		  if(window.__mportal_bottomTabBarPositionType == "1" && !this.isIOS()){
        		  var scrollTop = Math.abs(domGeom.position(query(".muiHeaderBox")[0]).y);
                  if(scrollTop <= 0){ 
                	  this.inherited(arguments);
                  }  
        	  }else{
        		  if(moveY > 0){
	        		  var scrollTop = Math.abs(domGeom.position(query(".muiHeaderBox")[0]).y);	        		 
	                  if(scrollTop <= 0){ 
	                	  this.inherited(arguments);
	                  } 
        		  }else{
        			  this.inherited(arguments);
        		  }
        		  
        	  }
    	  }
    	  
      },
      
	  touchendEvent: function(e){
		  if(window.__mportal_bottomTabBarPositionType == "1" && !this.isIOS()){
    		  var scrollTop = Math.abs(domGeom.position(query(".muiHeaderBox")[0]).y);
              if(scrollTop <= 0){ 
            	  this.inherited(arguments);
              }  
    	  }else{
    		  this.inherited(arguments);
    	  }
		 this.stopScrollFunction();		 
	  },
	  
		
	/*****************************************
	 *****************************************
	 *
	 *	  滚动事件 Starts
	 */

	  
	 //判断是够为ios设备
	 isIOS: function (){
		var iOS = /iPad|iPhone|iPod/.test(navigator.userAgent) && !window.MSStream;
		if (iOS) {
			return true;
		}
		return false;
	 },
	  
	  startScrollFunction: function(){		  
		  var self = this;   
		  if(window.__mportal_bottomTabBarPositionType == "2" && !self.isIOS()){
			  return;
		  }
    	  // 重置滚动次数为1
    	  self.scrollIntervalCount = 1;
    	  self.startScrollInterval = true;
    	  if(!this.scrollInterval){
    		  this.scrollInterval = setInterval(function(){
        		  self.scrollIntervalFunction();
        	  },self.scrollIntervalRate);
    	  }    	 
	  },
	  
	  stopScrollFunction: function(){
		  var self = this;
		  self.startScrollInterval = false;		
	  },

	  //自定义滚动操作监听函数
	  scrollIntervalFunction: function(){	
		  var self = this;
		  if(self.scrollIntervalCount < self.scrollMaxIntervalCount){
			  this.getParent().fixNavBarPosition && this.getParent().fixNavBarPosition();
		  }else{
			  //触摸结束
			  if(!self.startScrollInterval){
				  if(self.scrollIntervalCount > self.scrollMaxIntervalCount){
					  clearInterval(this.scrollInterval);
					  this.scrollInterval = null;
				  }else{
					  self.scrollIntervalCount++;
				  }
			  } 
		  }
	  },
	  
	/**
	 *			滚动事件 Ends
	 *				
	 ****************************************
	 ****************************************/
	  
	  destory: function(){
		  if(this.scrollInterval){
			  clearInterval(this.scrollInterval);
			  this.scrollInterval = null;
		  }
	  },
      
//      // 重写获取滚动位置方法
//      getScrollTop: function () {
//        return (
//          document.documentElement.scrollTop ||
//          document.body.scrollTop
//        );
//      },

      drawByEvenFunction: function (evt) {
    	//如果当前页签ID不是父页签ID
    	if(evt.tabId != this.tabId){
    		return;
    	}
        if (!this.pageId) this.pageId = evt.pageId;
        this.refresh(evt);
        this.resize(evt);
      },

      startup: function () {
        this.inherited(arguments);
        topic.subscribe(
          "/sys/mportal/composite/child/navItem/changed",
          lang.hitch(this, this.refresh)
        );
        topic.subscribe(
          "/sys/mportal/composite/child/panel/refresh",
          lang.hitch(this, this.refresh)
        );
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
        },
      
        /**
         * 获取默认选中的子页面Id
         */
        getSelectPage: function(pageId){
          var page = null
      	  for(var i=0; i < this.data.length; i++){
      		  var item = this.data[i];
      		  if(pageId == item.fdId){
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
  		  return page;
        },
        
      // TODO 兼容静态数据源
      refresh: function (evt) {
    	  if(evt && evt.tabId !=  this.tabId){
    		  //console.log("非当前页签ID：" + this.tabId + ",需要更新的页签为" +evt.tabId);
    		  return;
    	  }
    	 
          var self = this;
          var contentMap = this.contentMap;
          pageId = (this.pageId = (evt && evt.pageId) || this.pageId),
          deferred = new Deferred();
          
	      var pageInfo = this.getSelectPage(pageId);
	      
          var canScroll = true;
          if(evt && evt.useIframe){
        	  canScroll = false;
          }
          
          var curentPageId = pageId;
          
        if (!this.contentMap[pageId] || (evt && evt.forceRefresh)) {
          //强制刷新，移除原来的
          if (this.contentMap[pageId]) {
            this.contentMap[pageId].destroyRecursive();
            this.contentMap[pageId] = null;
          }
          var content = null;
          //展示Iframe
          if(evt && evt.useIframe){       	  
        	  content = new ChildIframeContent({
        		  tabId: this.tabId,
                  pageId: curentPageId,
                  url: pageInfo.pageUrl,
                  renderComplete: function () {
                    deferred.resolve();
                  },
                });  
          }else{
        	  content = new ChildCommonContent({
        		  tabId: this.tabId,
                  pageId: curentPageId,
                  data: typeof memory != "undefined" ? memory[pageInfo.pageId] : null,
                  renderComplete: function () {
                    deferred.resolve();
                  },
                });       	  
          }   
          
          //this.setMinHeight(content.domNode);
          this.addChild(content);
          contentMap[curentPageId] = content;
        } else {
          deferred.resolve();
        }
        if(!canScroll){
       	 this.optDown.use = false;
       	 this.resize();
       }else{
       	 this.optDown.use = true;
       	 this.resize();
       }
        deferred.promise.then(function () {
          self.refreshComplete(curentPageId);
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
    	return;
    	var self = this;
	    var pageInfo = this.getSelectPage(this.pageId);
        var canScroll = true;
        if(pageInfo && pageInfo.pageType == 2){
   	      canScroll = false;
        }
      },
    }
  );
  return panel;
});
