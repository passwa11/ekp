define(["dojo/_base/declare", "dijit/_WidgetBase", "dojo/request", "dojo/topic", "mui/util", 
	"dojo/parser","dojo/dom-construct", "dojo/dom-style", "dojo/dom", "dojo/dom-class", "dojo/query", "dojo/on", "dojo/_base/lang", 
	"dojo/dom-attr","./tab/TabBar","dojo/window", "dijit/registry", "mui/device/adapter"], 
		function(declare, WidgetBase, request, topic, util, parser, domConstruct, domStyle, dom,
				domClass, query, on, lang, domAttr, TabBar,win, registry, adapter) {
	
	var init = declare("sys.mportal.composite.initMixin", [WidgetBase], {
		
		LOCALCACHENAME : 'localCache',
		
		USERLOCALCONFIGNAME : 'userLocalConfig',
		
		url : '/sys/mportal/sys_mportal_composite/sysMportalComposite.do?method=initParams',
		
		defaultUrl: '/third/pda/indexdefault.jsp',
		
		// 传入参数(复合门户ID)
		compositeId : '',
		
		//选中的门户信息
		selectPortal: null,		
		
		bottomTabBarPositionType: "1",
		
		startup : function() {
			this.inherited(arguments);
			
			/**
			 * 1:安卓、iPhone都为 position:absolute布局
			 * 2:安卓为 position: fixed布局，iPhone为 position:absolute 布局
			 */			
			window.__mportal_bottomTabBarPositionType = 1;
			this.init();
		},
		
		init : function () {
			var href = util.formatUrl("/third/pda/index.jsp?");
			var isFromAction = false;
			//来源于action
			if(location.href.indexOf("/sys/mportal/sys_mportal_composite/sysMportalComposite.do?method=view") != -1){
				var compositeId = util.getUrlParameter(location.href, "fdId");
				this.compositeId = compositeId;
				href += "fdCompositeId="+compositeId;
				isFromAction = true;
			}
			var useMobileCache = util.getUrlParameter(location.href, "useMobileCache");
			//切换门户
			if(useMobileCache){
				if(isFromAction){
					history.replaceState(null, null, href);
				}else{
					href = location.href;
					href = util.setUrlParameter(href, "useMobileCache", null);
					history.replaceState(null, null, href);
				}			
				var cache = dojoConfig.cacheBust;
				var localCache = this.getLocal(this.LOCALCACHENAME);
				var userConfigCache =  this.getLocal(this.USERLOCALCONFIGNAME + dojoConfig.CurrentUserId);
				userConfigCache = JSON.parse(userConfigCache);
				// 本地有缓存且版本一致，且当前用户有缓存数据		
				if(localCache && cache == localCache && userConfigCache && userConfigCache.compositePortalList){ 		
					this.publishEvent(userConfigCache);
				} else { // 去加载数据				
					this.setLocal(this.LOCALCACHENAME, dojoConfig.cacheBust);
					this.getInitParams();				
				}
			}else{
				if(isFromAction){
					history.replaceState(null, null, href);
				}
				//不启用缓存，目前无法保证服务器禁用、删除门户的时候移除本地缓存
				this.setLocal(this.LOCALCACHENAME, dojoConfig.cacheBust);
				this.getInitParams();
			}						
		},
		
		// 获取本地缓存数据
		getLocal : function(str) { 
			return localStorage.getItem(str);
		},

		// 保存本地缓存数据
		setLocal : function(str, obj) {
			return localStorage.setItem(str, obj);
		},
		
		// 请求数据
		getInitParams : function() {
			var self = this;
			var url = util.formatUrl(this.url);
			request.get(url, {
	            headers: {'Accept': 'application/json'},
	            handleAs: 'json'
			}).then(function(data) {
				// 请求不到数据，跳转到default页面
				if(!data || data.compositePortalList == undefined) 
					window.location.href = util.formatUrl(self.defaultUrl);
				
				self.setLocal(self.USERLOCALCONFIGNAME + dojoConfig.CurrentUserId, JSON.stringify(data));
				self.publishEvent(data);
			})
		},
		
		// 构建发送事件需要的参数
		buildTotalData : function(obj) {
			if(!this.totalData) {
				var user = new Object();
				user.id = dojoConfig.CurrentUserId;
				user.name = dojoConfig.CurrentUserName;
				user.imgUrl = util.formatUrl('/sys/person/sys_person_zone/sysPersonZone.do?method=img&fdId=' + dojoConfig.CurrentUserId);
				obj.user = user;
				
				if(this.compositeId){
					var compositePortalList = obj.compositePortalList;
					for(var i = 0; i < compositePortalList.length; i++){
						if(compositePortalList[i].compositeId == this.compositeId){
							obj.compositeObj = compositePortalList[i];
							break;
						}
					}
					obj.compositeObj = obj.compositePortalList[0];
				} else {
					obj.compositeObj = obj.compositePortalList[0];
				}
				this.totalData = obj
			}
			return this.totalData;
		},
		
		//渲染头部
		drawHeader: function(obj){
			topic.publish('/sys/mportal/header/drawTmpRender', obj);
		},
		
		// 发送事件给各个组件，开始渲染
		publishEvent : function(obj) {
			
			var selectPortal = this.getSelectedPortalInfo(obj);
			this.compositeNavLayout = selectPortal.compositeNavLayout;
			//更改标题名称
			var pageId = util.getUrlParameter(location.href, "fdId") || "";
			if(pageId && pageId != null && pageId != '' && pageId != undefined && pageId != 'undefined' && pageId != 'null'){
				var pageList = selectPortal.pages;
				for (let i = 0; i < pageList.length; i++) {
					if(pageList[i].fdId == pageId){
						adapter.setTitle(pageList[i].fdName);
					}
				}
			}else{
				adapter.setTitle(selectPortal.compositeName);
			}
			//构建发送事件需要的参数
			this.buildTotalData(obj);
			var height = win.getBox().h;
			//设置门户最小高度
			var contentNode = dom.byId("content");
			domStyle.set(contentNode,"min-height",height+"px");
			//设置门户logo
			if(selectPortal.compositeLogo){
				topic.publish('/mui/mportal/header/logoChange', selectPortal.compositeLogo);
			}		
			//顶部导航布局
			if(selectPortal.compositeNavLayout == 1){		
				domClass.add(this.domNode, "mui_ekp_portal_container_bottom_layout")
				this.renderTopNavLayout(obj);				
				// 渲染头部
				this.drawHeader(obj);				
			}else{
				domClass.add(this.domNode, "mui_ekp_portal_container_bottom_layout")
				this.renderBottomNavLayout(obj);
				// 渲染头部
				this.drawHeader(obj);
			}
		},
		
		
		/*****************************************
		 *****************************************
		 *
		 *	  顶部导航布局 Starts
		 */
		
		//渲染顶部导航布局
		renderTopNavLayout: function(obj){
			var height = win.getBox().h;
			
			var pageId = util.getUrlParameter(location.href, "fdId") || "";			
	   	  	var initPage = this.getDefaultSelectPage(pageId);	   	  	
	   	  	if(initPage){
	   	  		pageId = initPage.fdId;
	   	  	}
			var self = this;
			var height = '5rem';
			//移除底部导航
			this.tabBarNode = registry.byId("sys_mportal_Tabbar_bottom");			
			if(this.tabBarNode){
				 this.tabBarNode.destroyRecursive();
			}
			
			this.topNavLayoutNavtmpl = '<div class="mui_ekp_portal_title_list">'
						+ '<section class="et-portal-tabs main" '
						+ ' data-dojo-type="sys/mportal/mobile/header/slider/NavBar"'
						+ ' data-dojo-mixins="sys/mportal/mobile/header/slider/PushStateMixin,sys/mportal/mobile/header/slider/CompositeNavBarMixin" '
						+ ' data-dojo-props="pageId :\'' + pageId +'\',drawByEven:true,height:\''+height+'\'">'
						+ '</section>'
						+ '</div>';
								
			this.topNavLayoutPanelTmpl = '<div class="muiPortalView">'
						 + ' <div data-dojo-type="sys/mportal/mobile/composite/CommonTopPanel" '					
						 + ' data-dojo-props="pageId:\'' + pageId + '\',drawByEven:true">'
						 + '</div>'
						 + '</div>';	 	
			
			 this.topNavLayoutNavNode = domConstruct.toDom(this.topNavLayoutNavtmpl);			 
			 this.topNavLayoutPanelNode = domConstruct.toDom(this.topNavLayoutPanelTmpl);			 
       	  	 domConstruct.place(this.topNavLayoutNavNode, this.domNode, "last");
       	  	 domConstruct.place(this.topNavLayoutPanelNode, this.domNode, "last")
       	  	 
       	  	 // 渲染navbar
       	  	 parser.parse(this.topNavLayoutNavNode).then(function(instances){      	  		       	  		 
       	  		 self.drawNavBar(obj, obj.selectPortal);
       	  	 });
       	  	     	  	 
       	  	 // 渲染panel
       	  	 parser.parse(this.topNavLayoutPanelNode).then(function(instances){   
       	  		 if(pageId){
       	  			 topic.publish('/mui/nav/drawPanpel', pageId);	
       	  		 }
       	  	 }); 
		},
		
		// 渲染navbar
		drawNavBar: function(obj, selectPortal){		
			var data = this.buildNavData(selectPortal);
			topic.publish('/mui/nav/drawNavBarMore', null);
			topic.publish('/mui/nav/drawNavBar', data);
		},
		
		// 包装navBar数据
		buildNavData : function (selectPortal){		
			var arr = new Array();
			var pages = selectPortal.pages;
			var logo = selectPortal.compositeLogo;
			for(var i = 0; i < pages.length; i++) {
				var obj = pages[i];
				arr[i] = [obj.fdId, obj.fdName, obj.pageType, obj.pageUrl, obj.pageUrlOpenType];
			}
			return arr;
		},
		
		/**
		 *			顶部导航布局 Ends
		 *				
		 ****************************************
		 ****************************************/
		
		
		
		
		/*****************************************
		 *****************************************
		 *
		 *	  底部导航布局 Starts
		 */
		
		//渲染底部导航布局
		renderBottomNavLayout: function(obj){
			var height = win.getBox().h;					
		
			var headerNode = dom.byId("searchId");
			
			//domStyle.set(headerNode,"display", "none");
			//获取初始化的页签Id和页签数据
			var pages = obj.selectPortal.pages;
	   	  	var pageId = util.getUrlParameter(location.href, "fdId");	   	  	
	   	  	var initPage = this.getDefaultSelectPage(pageId);	   	  	
	   	  	if(initPage){
	   	  		pageId = initPage.fdId;
	   	  	}
	   	  		   	  	 
	   	  	 //底部导航初始化数据
	   	  	 this.tabBarNode = registry.byId("sys_mportal_Tabbar_bottom");			
	   	  	 this.tabBarNode.pageId = pageId;
	   	  	 this.tabBarNode.render(pages);
	   	  	 domStyle.set(this.tabBarNode.domNode,"display","flex");
	   	  
	   	  	 if(window.__mportal_bottomTabBarPositionType == "1"){
				var bodyDom =  query("body")[0];
				var htmlDom =  query("html")[0];
				domStyle.set(bodyDom,"height", height + "px");
				domStyle.set(htmlDom,"height", "100%");
	   	  	 }else{
				//iphone设备底部导航不设置html,body 100%会导致遮盖现象
				if(this.isIOS()){
					var bodyDom =  query("body")[0];
					var htmlDom =  query("html")[0];
					domStyle.set(bodyDom,"height", height + "px");
					domStyle.set(htmlDom,"height", "100%");
				}else{
					domStyle.set(this.tabBarNode.domNode,"position","fixed");
				}
	   	  	 }		   	 

	   	  	// 渲染panel
	       	this.bottomNavLayoutPanelTmpl = '<div class="muiPortalView">'
				 + ' <div data-dojo-type="sys/mportal/mobile/composite/CommonBottomPanel" '
				 + ' data-dojo-props="pageId:\'' + pageId + '\',drawByEven:true">'
				 + '</div>'
				 + '</div>';		       		       	
			 this.bottomNavLayoutPanelDomNode = domConstruct.toDom(this.bottomNavLayoutPanelTmpl);				 
		  	 domConstruct.place(this.bottomNavLayoutPanelDomNode, this.domNode, "last");
		  	 var self = this;		  
		  	 parser.parse(this.bottomNavLayoutPanelDomNode).then(function(instances){	
		  		self.bottomNavLayoutPanelNode = instances[0];
		  		self.bottomNavLayoutPanelNode.headerInfo = obj;
		  		//初始化panel数据
		  		topic.publish('/mui/nav/drawPanpel', pageId);
		  	 });	       	  	
		},
		
		//判断是够为ios设备
		isIOS: function (){
			var iOS = /iPad|iPhone|iPod/.test(navigator.userAgent) && !window.MSStream;
			if (iOS) {
				return true;
			}
			return false;
		},
		
		/**
		 *			底部导航布局 Ends
		 *				
		 ****************************************
		 ****************************************/
		
		
		
		
		/*****************************************
		 *****************************************
		 *
		 *	  获取门户/页签页面数据 Starts
		 */
		
		//获取选择的门户
		getSelectedPortalInfo: function(obj){
			var selectPortal = null;
			for(var i=0; i < obj.compositePortalList.length; i++){				
				var item = obj.compositePortalList[i];
				if(item != null && this.compositeId == item.compositeId){
					selectPortal = item;					
					break;
				}
			}
			
			if(selectPortal == null){
				selectPortal = obj.compositePortalList[0];
				this.compositeId = selectPortal.compositeId;
			}
			//选中的门户
			obj.selectPortal = selectPortal;
			this.selectPortal = selectPortal;
			return this.selectPortal;
		},
		
		//获取默认选择的页面
		getDefaultSelectPage: function(pageId){
			 var page = null;
			 var pages = this.selectPortal.pages;
			 for(var i=0; i < pages.length; i++){
				 if(pageId == pages[i].fdId){
					 return pages[i];
				 }
				 var type = pages[i].fdType;
				 var pageType = pages[i].pageType;
				 var pageUrlOpenType = pages[i].pageUrlOpenType;
				 //设置默认选中页面为非外部链接页面 或者 外部链接但是是本窗口内打开
				 if(!page){
					 //页签
					 if(type != 1){
						 page = pages[i];
					 } 
					 //非外部链接
					 if(type == 1 && pageType != 2){
						 page = pages[i];
					 }
					 //外部链接 本窗口内打开
					 if(type == 1 && pageType == 2 && pageUrlOpenType == 1){
						 page = pages[i];
					 }
				 }				 
			 }
			 if(!page){
				 page = pages[0]; 
			 }
			 return page;
		},
		
		//获取选择的页面
		getSelectPage: function(pageId){
			 var pages = this.selectPortal.pages;
			 for(var i=0; i < pages.length; i++){
				 if(pageId == pages[i].fdId){
					 return pages[i];
				 }
			 }
			 return null;
		},
		
		/**
		 *		获取门户/页签页面数据 Ends
		 *				
		 ****************************************
		 ****************************************/
				
		//重写滚动事件
		scrollEvent: function() {
			//底部导航禁止滚动
			if(this.compositeNavLayout == 2){
				if(this.bottomNavLayoutPanelNode && this.bottomNavLayoutPanelNode.__fixNavBarPositionWithTabBarBottom){
					this.bottomNavLayoutPanelNode.__fixNavBarPositionWithTabBarBottom();
				}
				return;
			}
			this.inherited(arguments);			  
		},
	
  });

  return init;
})
