define([
    "dojo/_base/declare",
    "dojo/_base/lang",
    "dojo/topic",
    "dojo/dom-style",
    "dojo/dom-class",
    "dojo/dom-prop",
    "dojo/query",
    "dojo/dom-construct",
    "dojo/request",
    "mui/util",
    "dijit/_WidgetBase",
    "mui/i18n/i18n!sys-mobile:mui.mobile.address.allCate"
	], function(declare, lang, topic, domStyle, domClass, domProp, query, domConstruct, request, util, WidgetBase, allCateMsg) {
	
	return declare("sys.zone.mobile.js.address.AddressBreadcrumbNav", [ WidgetBase ], {
	    
		//获取当前层级详细信息URL
		detailUrl : '/sys/organization/mobile/address.do?method=detailList&orgIds=!{curId}&deptLimit=!{deptLimit}',
	    
		// 当前层级ID
		curId : null,    
		
		// 当前层级名称
		curName: null,
		
		deptLimit: null,
	    
		startup : function() {
			this.inherited(arguments);
			
			// 监听地址本分类项change事件
			this.subscribe("/mui/category/changed", "_chgHeaderInfo");
			
			// 监听搜索框搜索
            this.subscribe("/mui/search/submit","_searchKeyWordChange");
            
            // 监听搜索框取消
	        this.subscribe("/mui/search/cancel","_searchReback");
			
			// 初始化时发布分类项change事件（触发同时加载面包屑导航栏以及员工黄页列表数据）
  	        topic.publish("/mui/category/changed", this, {
	          fdId: this.curId,
	          label: this.curName
	        });
		},
	
	   
		_chgHeaderInfo : function(srcObj, evt){
	    	if(evt){
	    		this.curId = evt.fdId;
	    		this.curName = evt.label;
	    	}
            this.loadBreadcrumbNav();
	    },
	    
		/**
		* 搜索框关键字changed事件
		* @return
		*/	    
	    _searchKeyWordChange : function(srcObj, evt){
	    	domStyle.set(this.getParent().domNode,'display','none');
	    },
	    
		/**
		* 点击搜索框“取消”
		* @return
		*/	
	    _searchReback: function(srcObj, evt) {
  	        topic.publish("/mui/category/changed", this, {
  	          fdId: this.curId,
  	          label: this.curName
  	        });	
  	      domStyle.set(this.getParent().domNode,'display','block');
	    },
	    
		/**
		* 加载面包屑导航栏
		* @return
		*/	
	    loadBreadcrumbNav : function(){
	    	var self = this;
	    	var requestUrl = util.urlResolver(this.detailUrl,self);
			request.get(util.formatUrl(requestUrl),{handleAs:'json'}).then(function(result) {
				if(result) {
					var data = null;
					if(result.length==0){
						data = {isTopNav:true};  // 根层级
					}else{
						data = result[0];  // 当前层级详细数据对象
					}
					self.buildBreadcrumbNav(data);		                    
				}
			});	
	    },
		
		/**
		* 构建面包屑导航栏DOM元素
		* @param data 当前层级详细数据
		* @return
		*/	
		buildBreadcrumbNav: function(data){

			if(this.navContainerNode){
				domConstruct.destroy(this.navContainerNode);
			}
			
			this.navContainerNode = domConstruct.create('div', { className : 'mui_zone_list_breadcrumb_nav_container' }, this.domNode);
			
			var contentNode = domConstruct.create('div', { className : 'mui_zone_list_breadcrumb_nav muiFontSizeS' }, this.navContainerNode);
			
			if(data.isTopNav){
				var topNavItemNode = domConstruct.create('div', { className : 'mui_zone_list_breadcrumb_nav_item' }, contentNode);
				topNavItemNode.innerText = allCateMsg["mui.mobile.address.allCate"];  // 根层级显示“组织架构”
			}else{
				// 父级层级名称
				var parentNavItemNode = domConstruct.create('div', { className : 'mui_zone_list_breadcrumb_nav_item' }, contentNode);
				var parentNavItemTextNode = domConstruct.create('div', { className : 'mui_zone_list_breadcrumb_nav_item_text muiFontColor' }, parentNavItemNode);
				parentNavItemTextNode.innerText = data.parentNames||allCateMsg["mui.mobile.address.allCate"];
				this.connect(parentNavItemTextNode,"click",lang.hitch(this, function() {
		    	      topic.publish("/mui/category/changed", this, {
				          fdId: data.parentId,
				          label: data.parentNames
		    	      });
		    	}));
				
				// 分隔符图标
				domConstruct.create('i', { className : 'mui_zone_list_breadcrumb_nav_item_separator fontmuis muis-to-right muiFontColorMuted' }, parentNavItemNode);
				
				// 当前层级名称
				var navItemNode = domConstruct.create('div', { className : 'mui_zone_list_breadcrumb_nav_item' }, contentNode);
				var navItemTextNode = domConstruct.create('div', { className : 'mui_zone_list_breadcrumb_nav_item_text' }, navItemNode);
				navItemTextNode.innerText = data.label;						
			}

		}
		
		
		

		
	});
});