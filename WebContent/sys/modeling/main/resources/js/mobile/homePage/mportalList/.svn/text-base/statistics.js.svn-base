/**
 * 
 */
define(['dojo/_base/declare',"dijit/_WidgetBase", "dojo/dom-class", "dojo/dom-construct", "mui/openProxyMixin", "mui/util", 
		"sys/modeling/main/resources/js/mobile/homePage/common/_IndexMixin", "dojo/dom-class", "dojo/parser",
		"sys/modeling/main/resources/js/mobile/homePage/common/menuSlide", "sys/mportal/mobile/extend/MenuItemMixin",
		"sys/modeling/main/resources/js/mobile/homePage/common/CountMenuItem"],
		function(declare, WidgetBase, domClass, domConstruct, openProxyMixin, util,
					_IndexMixin, domClass, dojoParser, menuSlide, MenuItemMixin, CountMenuItem){
	
	return declare('sys.modeling.main.resources.js.mobile.homePage.mportalList.statistics', [WidgetBase, openProxyMixin, _IndexMixin] , {
		
		url : "",

		listViewUrl : "/sys/modeling/main/mobile/modelingAppMainMobileListView.do?method=listViewIndex&listViewId=!{listViewId}&arrIndex=!{arrIndex}&fdMobileId=!{fdMobileId}&area=statistics#path=!{tabIndex}",
		
		DATALOAD : "/sys/modeling/mobile/index/load",
		
		fixNum : 3,	// 最多展示的方块

		fdMobileId:"",
		
		postCreate : function() {
			this.inherited(arguments);
			this.subscribe(this.DATALOAD, 'onComplete');
		},
		
		buildRendering : function() {
			this.inherited(arguments);
			domClass.add(this.domNode, "mportalList-statistics");
		},
		
		onComplete : function(data){
			domConstruct.empty(this.domNode)
			var attrs = data.statistics.attr;
			this.createContent(attrs.listViews.value);
		},
		
		createContent : function(items){
			items = this.formatItems(items);
			if(items.length === 0){
				// 没有展示项时
				var style1 = "background: #4285f4;border-radius: 0.4rem;";
				var style2 = "height:8rem;";
				var fontStyle = "color:#FFFFFF;";
				var imageStyle = "margin-top:2rem;";
				var textStyle  = "margin-top:2rem;";
				this.showNoAuth(this.domNode,style1,style2,fontStyle,imageStyle,textStyle);
			}else{
				// 创建滑动块
				this.createMenuSlide(items);				
			}
		},
		
		createMenuSlide : function(items){
			var menuSlideDom = domConstruct.create("div", {
				"className" : "mportalList-swiper",
				"data-dojo-type" : "sys/modeling/main/resources/js/mobile/homePage/common/menuSlide",
				"data-dojo-mixins" : "sys/mportal/mobile/extend/MenuItemMixin"
			}, this.domNode);
			
			var slideContainerNode = domConstruct.create("div", {
				"className" : "mportalList-item-container"
			}, menuSlideDom);
			
			var menuSlides = dojoParser.instantiate([menuSlideDom],{
				itemRenderer : CountMenuItem,
				columns : this.fixNum,
				width : "inherit",
				containerNode : slideContainerNode
			});
			menuSlides[0].render(items);
		},
		
		formatItems : function(items){
			var rs = [];
			for(var i = 0;i < items.length;i++){
				if(items[i].auth === "true"){
					var tabIndex = this.getTabIndex(items[i].countLv, items[i].lvCollection);
					items[i].url = util.urlResolver(this.listViewUrl, {listViewId: items[i].listView,arrIndex: i,fdMobileId:this.fdMobileId,tabIndex:tabIndex});
					rs.push(items[i]);
				}
			}
			return rs;
		}
		
	});
});