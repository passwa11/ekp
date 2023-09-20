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
  "dojo/dom-style",
  "dojo/dom-class",
  "dojo/query",
  "dojo/on",
  "dojo/dom-attr",
  "dojo/dom-geometry",
  "mui/device/adapter",
  "dojox/mobile/ScrollableView",
  "./HeaderPortalItem",
  "./HeaderPortalContainer"
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
  domStyle,
  domClass,
  query,
  on,
  domAttr,
  domGeom,
  adapter,
  ScrollableView,
  HeaderPortalItem,
  HeaderPortalContainer
) {
  var header = declare(
    "sys.mportal.composite.Header",
    [WidgetBase, Container, Contained],
    {
      width: "100%",

      url: "/sys/mportal/sys_mportal_page/sysMportalPage.do?method=loadPages",

      pageId: "",

      pageName: "",

      //是否显示更多
      showMore: false,

      forceRefresh: false,

      baseClass: "muiHeaderBox",   
      
      buildRendering: function() {
        this.inherited(arguments)
        this.buildMask();
        if (this.headerType == "1") domStyle.set(this.domNode, "height", "9rem")
        else domStyle.set(this.domNode, "height", "6rem")
        this.subscribe('/sys/mportal/header/drawTmpRender','tmpRender');
      },

      initParams : function(obj){
    	  this.userName = obj.user.name;
    	  this.imgUrl = obj.user.imgUrl;
    	  this.logo = util.formatUrl(obj.selectPortal.compositeLogo);
    	  this.compositeNavLayout = obj.selectPortal.compositeNavLayout;
    	  this.compositeHeadChangeEnabled = obj.selectPortal.compositeHeadChangeEnabled;
    	  if (this.headerType == "1") domStyle.set(this.domNode, "height", "9rem")
          else domStyle.set(this.domNode, "height", "6rem")
      },
      
      tmpRender: function(obj) {
    	  this.compositePortalList = obj.compositePortalList;
    	  this.selectPortal = obj.selectPortal;
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
        	  parser.parse(this.headerNode);
          }))
      },
      
      
      /********************************** 
       * 	复合门户切换相关  Starts
       * 
       *  门户切换层级关系：
       * 
       *  底部tabBar 
       *  .muiBottomTabBarBox
       *  	z-Index:11999
       *  
       *  门户切换遮罩
       *  .mui_ekp_composite_portal_mask
       *  	z-index:12000
       *  
       *  头部门户切换
       *  .mui_ekp_portal_composite_container .muiHeaderBox 
       *  	z-index:12001
       *  
       */
      
      
      os : {
			ios: !!navigator.userAgent.match(/\(i[^;]+;( U;)? CPU.+Mac OS X/),
			android: navigator.userAgent.indexOf('Android') > -1 || navigator.userAgent.indexOf('Adr') > -1
		},
      
      //是否隐藏复合门户切换操作
      compositeHeadChangeEnabled: false,
      
      //复合门户切换 -- 容器
      compositePortalChangeContainerNode: null,
      
      //复合门户切换 -- 图标
      compositePortalArrowNode: null,
      
      //复合么户切换 -- 内容
      compositePortalChangeContentNode: null,
      
      //用来判断收起展开门户内容
      contentShow : false,     
      
      //处理复合门户更换
      handleCompositeChange: function(sliderHeader){    	 
    	  this.childSliderHeader = sliderHeader;
    	  if(this.compositeHeadChangeEnabled){
    		  
    		  var style = "width:3.5rem;line-height: 3.5rem;height:3.5rem; position: absolute;right: 5rem;top: 3rem;";
    		  if(this.headerType == "2"){
    			  style = "margin-left:1.25rem;width:3.5rem;line-height: 6rem;height:6rem;float:right;";
    		  }
    		  
    		  this.compositePortalChangeContainerNode = domConstruct.create('div', {
	  				className: "mui_ekp_portal_info_composite",
	  				style: style
  			  }, this.childSliderHeader.domNode);
  		  
	  		  this.compositePortalArrowNode = domConstruct.create('i',{
	  			 className : 'mui mui-up-n  mui_ekp_portal_more_arrow muiFontSizeM'
	  		  },this.compositePortalChangeContainerNode);
	  		
	  		  var self = this;		  		  
	  		  on(this.compositePortalChangeContainerNode, "click" ,function(){
	  				self.toggleHandleCompositePortal();
	  		 });
      	  }    	 
      },
      
      
      toggleHandleCompositePortal: function(){
    	  this.changeIconMask();
    	  this.showOrHideMask();	  			
    	  this.renderComposites();
      },
     
      
       //点击改变图标
	  changeIconMask: function(){
		  var iconNode = this.compositePortalArrowNode;
		  // 图标
	      if (domClass.contains(iconNode, "mui-up-n")) {
	         domClass.add(iconNode, "mui-down-n");
	         domClass.remove(iconNode, "mui-up-n");
	      } else {
	         domClass.add(iconNode, "mui-up-n");
	         domClass.remove(iconNode, "mui-down-n");
	      }
	   },
	   
      //展示或隐藏遮罩     
      showOrHideMask: function(){
    	  var mask = query(".mui_ekp_composite_portal_mask")[0];
	      // 遮盖层
	      if (domStyle.get(mask, "display") == "none"){
	    	  domStyle.set(this.domNode,"touch-action", "none");
	    	  domStyle.set(mask, "display", "block");
	    	  if(this.os.android){
	    		  var contentNode = query("#content")[0];
	    		  if(contentNode){
	    			  domStyle.set(contentNode,"overflow", "hidden");
	    		  }
	    		  domStyle.set(document.body,"overflow", "hidden");
	    		  domStyle.set(document.body,"position", "fixed");
	    	  }
	      }else{
	    	  domStyle.set(this.domNode,"touch-action", "inherit");
	    	  if(this.os.android){
	    		  var contentNode = query("#content")[0];
	    		  if(contentNode){
	    			  domStyle.set(contentNode,"overflow", "inherit");
	    		  }
	    		  domStyle.set(document.body,"position", "inherit");
	    	  }	    	 
	    	  domStyle.set(mask, "display", "none");
	      }
     
      },
      
      //遮罩
	  buildMask : function(){
	      var body = query("body")[0];
	      this.mask = domConstruct.create("div",
	    	  {
	        	 className: "mui_ekp_composite_portal_mask"
	          },
	          body
	      );
	      var self = this;
	      this.connect(this.mask, "click", function() {
	    	  self.toggleHandleCompositePortal();
	      });
	  },
      
      /**
       *  渲染门户选择列表
       */
      renderComposites: function(e){
    	  if(!this.compositePortalChangeContentNode){
        	
          	  var arrowNode = this.compositePortalArrowNode;
          	  
          	  var position = domGeom.position(arrowNode);
          	  
          	  var compositeNodeLeft = position.x;
          	  var compositeNodeTop  = position.y;
          	  var compositeNodeHeight = position.h;
          	  
          	  var left = compositeNodeLeft - 75;
          	  var top = compositeNodeTop + compositeNodeHeight + 5;
          	  
          	  var headerPortalContainer= new HeaderPortalContainer({
          		  top: top,
          		  left: left
          	  });
          	  
          	  headerPortalContainer.placeAt(this.compositePortalChangeContainerNode);
          	  this.compositePortalChangeContentNode = headerPortalContainer.domNode;
          	  
          	  var self = this;
          	  
          	  var size =  this.compositePortalList.length;     	  
          	 
          	  var selectItem = null;
          	  for(var i=0; i < this.compositePortalList.length; i++){
          		  var item = this.compositePortalList[i];
          		  
          		  var isSelected = false;
          		  if(this.selectPortal.compositeId == item.compositeId){
          			 isSelected = true;
          		  }
          		  
          		  var headerPortalItem = new HeaderPortalItem({
          			  isSelected: isSelected,
          			  data: item,
          			  onClick: function(e, selectItem){
          				  self.clickChildComposite(e, selectItem);
          			  }
          		  });
          		  if(isSelected){
          			selectItem = headerPortalItem;
          		  }
          		  headerPortalContainer.addChild(headerPortalItem);
          	  }           	  
          	  this.contentShow = true;
    	  }else{
    		  if(this.contentShow){   			 
  				  domStyle.set(this.compositePortalChangeContentNode,"display","none");
  				  this.contentShow = false;
	      	  }else{
	      		  domStyle.set(this.compositePortalChangeContentNode,"display","inline-block");
	      		  this.contentShow = true;
	      	  }
    	  }
      },
      
      /**
       * 门户列表点击事件
       */
      clickChildComposite: function(e,selectItem){   
    	  var compositeId = selectItem.compositeId;
    	  if(this.selectPortal.compositeId == compositeId){
    		  this.toggleHandleCompositePortal();
    		  return;
    	  }else{
    		  this.selectPortal = selectItem;
    		  adapter.setTitle(this.selectPortal.compositeName);
    		  this.toggleHandleCompositePortal();
    		  window.location.href = util.formatUrl("/sys/mportal/sys_mportal_composite/sysMportalComposite.do?method=view&fdId=" + compositeId + "&useMobileCache=true");
    	  }
      }
    }
  )

  return header
})
