define(["dojo/_base/declare", "dijit/_WidgetBase", "dojo/request", "dojo/topic", "mui/util"], 
		function(declare, WidgetBase, request, topic, util) {
	
	var init = declare("sys.mportal.initMixin", [WidgetBase], {
		
		LOCALCACHENAME : 'localCache',
		
		USERLOCALCONFIGNAME : 'userLocalConfig',
		
		url : '/sys/mportal/sys_mportal_page/sysMportalPage.do?method=initParams',
		
		defaultUrl: '/third/pda/indexdefault.jsp',
		
		// 传入参数
		pageId : '',
		
		startup : function() {
			this.inherited(arguments);
			this.init();
		},
		
		init : function () {
			
			var cache = dojoConfig.cacheBust;
			var localCache = this.getLocal(this.LOCALCACHENAME);
			var userConfigCache =  this.getLocal(this.USERLOCALCONFIGNAME + dojoConfig.CurrentUserId);
			userConfigCache = JSON.parse(userConfigCache);

			if(localCache && cache == localCache && userConfigCache && userConfigCache.portalList){ // 本地有缓存且版本一致，且当前用户有缓存数据
				
				this.publishEvent(userConfigCache);
				
			} else { // 去加载数据
				
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
				if(!data || data.portalList == undefined) 
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
				
				if(this.pageId){
					var portalList = obj.portalList;
					for(var i = 0; i < portalList.length; i++){
						if(portalList[i].pageId == this.pageId){
							obj.pageObj = portalList[i];
							break;
						}
					}
					obj.pageObj = obj.portalList[0];
				} else {
					obj.pageObj = obj.portalList[0];
				}
				this.totalData = obj
			}
			return this.totalData;
		},
		
		// 发送事件给各个组件，开始渲染
		publishEvent : function(obj) {
			
			var totalData = this.buildTotalData(obj);

			// 渲染头部
			this.drawHeader(obj);

			// 渲染navbar
			this.drawNavBar(obj);

			// 渲染panel
			this.drawPanel();
			
		},
		
		drawHeader: function(obj){
			topic.publish('/sys/mportal/header/drawTmpRender', obj);
		},
		
		drawNavBar: function(obj){
			var data = this.buildNavData(obj.portalList);
			topic.publish('/mui/nav/drawNavBarMore', null);
			topic.publish('/mui/nav/drawNavBar', data);
		},
		
		// 包装navBar数据
		buildNavData : function (portalList){
			var arr = new Array();
			for(var i = 0; i < portalList.length; i++) {
				var obj = portalList[i];
				arr[i] = [obj.pageId, obj.pageName, obj.pageType, obj.pageLogo];
			}
			return arr;
		},
		
		drawPanel: function(){
			topic.publish('/mui/nav/drawPanpel', this.totalData.pageObj.pageId);
			// 修改浏览器title
			var fdName = util.getUrlParameter(location.href, "fdName") || "";
			if(fdName && fdName != null && fdName != '' && fdName != undefined && fdName != 'undefined' && fdName != 'null'){
				document.getElementsByTagName("title")[0].innerText = decodeURIComponent(fdName);
			}else{
				document.getElementsByTagName("title")[0].innerText = this.totalData.pageObj.pageName;
			}

		}
	
  });

  return init;
})
