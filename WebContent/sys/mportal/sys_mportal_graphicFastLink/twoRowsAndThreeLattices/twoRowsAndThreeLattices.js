define([
    "dojo/_base/declare",
    "dojo/dom-style",
    "dojo/dom-class",
    "dojo/dom-prop",
    "dojo/query",
    "dojo/dom-construct",
    "dojo/request",
    "mui/util",
    "sys/mportal/mobile/OpenProxyMixin",
    "dijit/_WidgetBase"
	], function(declare, domStyle, domClass, domProp, query, domConstruct, request, util, openProxyMixin, WidgetBase) {
	
	
	return declare("sys.mportal.graphicFastLink.twoRowsAndThreeLattices", [ WidgetBase, openProxyMixin ], {
		
	    // 主题风格ID
	    themeStyleId : '',
	    
		// 第一个快捷链接的URL
	    firstMenuLink : '',
	    // 第一个快捷链接的标题
	    firstMenuTitle : '',
	    // 第一个快捷链接的副标题
	    firstMenuSubtitle : '',
	    
	    // 第二个快捷链接的URL
	    secondMenuLink : '',
	    // 第二个快捷链接的标题
	    secondMenuTitle : '',
	    // 第二个快捷链接的图标
	    secondMenuIcon : '',
	    
	    // 第三个快捷链接的URL
	    thirdMenuLink : '',
	    // 第三个快捷链接的标题
	    thirdMenuTitle : '',
	    // 第三个快捷链接的图标
	    thirdMenuIcon : '',

	    // 第一个快捷链接左侧个性图片
	    firstMenuPersonalityImage : '',
	    // 第一个快捷链接背景颜色
	    firstMenuBackgroundColor : '',
	    // 第二个快捷链接背景颜色
	    secondMenuBackgroundColor : '',
	    // 第三个快捷链接背景颜色
	    thirdMenuBackgroundColor : '',
	    
	    // 所有主题信息JSON数据请求URL
	    jsonUrl: '/sys/mportal/sys_mportal_graphicFastLink/twoRowsAndThreeLattices/json/themeStyleConfig.json',
	    
	    
		startup : function() {
			this.inherited(arguments);
			var self = this;
			request.get(util.formatUrl(this.jsonUrl),{handleAs:'json'}).then(function(data) {
				if(data) {
					var themeStyleConfig = null;
					for(var i=0;i<data.length;i++){
						if( self.themeStyleId == data[i].id ){
							themeStyleConfig = data[i];
							break;
						}
					}
					if(themeStyleConfig){
						self.firstMenuPersonalityImage = themeStyleConfig.firstMenuPersonalityImage;
						self.firstMenuBackgroundColor = themeStyleConfig.firstMenuBackgroundColor;
						self.secondMenuBackgroundColor = themeStyleConfig.secondMenuBackgroundColor;
						self.thirdMenuBackgroundColor = themeStyleConfig.thirdMenuBackgroundColor;
					}
					self.generateGraphicFastLink();
				}
			});
		},
		
		/**
		* 构建图文快捷方式
		* @param dataList 日程数据列表
		* @return
		*/
		generateGraphicFastLink : function() {
            
			  // 图文快捷方式最外层容器DIV
		      var containerNode = domConstruct.create('div', { className : 'muiPortalGraphicFastLinkContainer' }, this.domNode);
		      
		      // 第一行容器
		      var firstRowNode = domConstruct.create('div', { className : 'muiPortalGraphicFastLinkFirstRow' }, containerNode);
		      
              // 构建第一个快捷菜单内容
		      this.buildFirstMenuContent(firstRowNode);
		      
		      // 第二行容器
		      var secondRowNode = domConstruct.create('div', { className : 'muiPortalGraphicFastLinkSecondRow' }, containerNode);
		      
		      // 构建第二个快捷菜单内容
              this.buildSecondMenuContent(secondRowNode);
			  
		      // 构建第三个快捷菜单内容
              this.buildThirdMenuContent(secondRowNode);
			  
		},
		
		
		/**
		* 构建第一个快捷菜单内容
		* @param firstRowNode 第一行容器
		* @return
		*/		
		buildFirstMenuContent: function(firstRowNode){
		      // 第一个快捷菜单
		      var firstMenuNode = domConstruct.create('div', { className : 'muiPortalGraphicFastLinkFirstMenu' }, firstRowNode);
			  domStyle.set( firstMenuNode, { 'background-color':this.firstMenuBackgroundColor} );
			  
	          // 绑定第一个快捷菜单点击事件
	          this.proxyClick(firstMenuNode, this.firstMenuLink, '_blank');
			  
			  // 左侧的异形图
			  var firstMenuLeftImgNode = domConstruct.create('img', { className : 'muiPortalGraphicFastLinkFirstMenuLeftImg' }, firstMenuNode);
			  domProp.set( firstMenuLeftImgNode, "src", util.formatUrl(this.firstMenuPersonalityImage) );
			  
			  // 菜单标题容器
			  var titleContainerNode = domConstruct.create('div', { className : 'muiPortalGraphicFastLinkFirstMenuTitleContainer' }, firstMenuNode); 	
			  
			  // 主标题
			  var titleNode = domConstruct.create('div', { className : 'muiPortalGraphicFastLinkFirstMenuTitle muiFontSizeXL' }, titleContainerNode); 
			  titleNode.innerText = this.firstMenuTitle;
			  
			  // 副标题
			  var subTitleNode = domConstruct.create('div', { className : 'muiPortalGraphicFastLinkFirstMenuSubTitle muiFontSizeS' }, titleContainerNode);
			  subTitleNode.innerText = this.firstMenuSubtitle;
			  
			  // “立即使用”标签
			  var nowUseNode = domConstruct.create('div', { className : 'muiPortalGraphicFastLinkFirstMenuNowUse muiFontSizeS' }, firstMenuNode); 
			  nowUseNode.innerText = "立即使用";
			  
		},
		
		
		/**
		* 构建第二个快捷菜单内容
		* @param secondRowNode 第二行容器
		* @return
		*/		
		buildSecondMenuContent: function(secondRowNode){
		      // 第二个快捷菜单
		      var secondMenuNode = domConstruct.create('div', { className : 'muiPortalGraphicFastLinkSecondMenu' }, secondRowNode);
			  domStyle.set( secondMenuNode, { 'background-color':this.secondMenuBackgroundColor } );	
			  
	          // 绑定第二个快捷菜单点击事件
	          this.proxyClick(secondMenuNode, this.secondMenuLink, '_blank');

			  //素材图片展示逻辑
			  var  __rootPath = "",__imgSrc = "", __picFlag = false;
			  if(this.secondMenuIcon.indexOf('/') > -1) {
				__rootPath = dojoConfig.baseUrl.substr(0,dojoConfig.baseUrl.length-1);
				__imgSrc = 'url("' + __rootPath + this.secondMenuIcon + '") center center / auto 60% no-repeat';
				__picFlag = true;
			  }

			  // 图标
			  var iconNode = domConstruct.create('div', { className : 'muiPortalGraphicFastLinkMenuIcon' }, secondMenuNode);

			  if(__picFlag){
					domStyle.set( iconNode, { background: __imgSrc } );
			  } else {
					domClass.add(iconNode, this.secondMenuIcon);
			  }

			  // 标题
			  var titleNode = domConstruct.create('div', { className : 'muiPortalGraphicFastLinkMenuTitle muiFontSizeL' }, secondMenuNode); 
			  titleNode.innerText = this.secondMenuTitle;
			  
			  // 右下角阴影图标
			  var shadowIconNode = domConstruct.create('div', { className : 'muiPortalGraphicFastLinkMenuShadowIcon' }, secondMenuNode);
			  if(__picFlag){
				domStyle.set( shadowIconNode, { background: __imgSrc } );
			  } else {
				domClass.add(shadowIconNode, this.secondMenuIcon);
			  }
		},
		
		
		/**
		* 构建第三个快捷菜单内容
		* @param secondRowNode 第二行容器
		* @return
		*/		
		buildThirdMenuContent: function(secondRowNode){
		      // 第三个快捷菜单
		      var thirdMenuNode = domConstruct.create('div', { className : 'muiPortalGraphicFastLinkThirdMenu' }, secondRowNode);
			  domStyle.set( thirdMenuNode, { 'background-color':this.thirdMenuBackgroundColor } );		
			  
	          // 绑定第三个快捷菜单点击事件
	          this.proxyClick(thirdMenuNode, this.thirdMenuLink, '_blank');

			  //素材图片展示逻辑
			  var  __rootPath = "",__imgSrc = "",__picFlag = false;
			  if(this.thirdMenuIcon.indexOf('/') > -1) {
				__rootPath = dojoConfig.baseUrl.substr(0,dojoConfig.baseUrl.length-1);
				  __imgSrc = 'url("' + __rootPath + this.thirdMenuIcon + '") center center / auto 60% no-repeat';
				__picFlag = true;
			  }

			  // 图标
			  var iconNode = domConstruct.create('div', { className : 'muiPortalGraphicFastLinkMenuIcon' }, thirdMenuNode);
			  if(__picFlag){
			  	  domStyle.set( iconNode, { background: __imgSrc } );
			  } else {
				  domClass.add(iconNode, this.thirdMenuIcon);
			  }
			  
			  // 标题
			  var titleNode = domConstruct.create('div', { className : 'muiPortalGraphicFastLinkMenuTitle muiFontSizeL' }, thirdMenuNode); 
			  titleNode.innerText = this.thirdMenuTitle;
			  
			  // 右下角阴影图标
			  var shadowIconNode = domConstruct.create('div', { className : 'muiPortalGraphicFastLinkMenuShadowIcon' }, thirdMenuNode);
			  if(__picFlag){
				  domStyle.set( shadowIconNode, { background: __imgSrc }  );
			  } else {
				  domClass.add( shadowIconNode, this.thirdMenuIcon );
			  }
		}
		
	});
});