/**
 * 
 */
define(['dojo/_base/declare', "dijit/_WidgetBase", "dojo/dom-class", "dojo/dom-construct", "mui/openProxyMixin", "mui/util",
		"sys/modeling/main/resources/js/mobile/homePage/common/_IndexMixin"],
		function(declare, WidgetBase, domClass, domConstruct, openProxyMixin, util, _IndexMixin){
	
	return declare('sys.modeling.main.resources.js.mobile.homePage.default.listView', [WidgetBase, openProxyMixin, _IndexMixin], {

		listViewUrl : "/sys/modeling/main/mobile/modelingAppMainMobileListView.do?method=listViewIndex&listViewId=!{listViewId}&arrIndex=!{arrIndex}&fdMobileId=!{fdMobileId}&nodeType=!{nodeType}&area=listView#path=!{tabIndex}",
		
		DATALOAD : "/sys/modeling/mobile/index/load",

		fdMobileId:"",

		postCreate : function() {
			this.inherited(arguments);
			this.subscribe(this.DATALOAD, 'onComplete');
		},
		
		buildRendering : function() {
			this.inherited(arguments);
			domClass.add(this.domNode, "mportal-listView");
		},
		
		onComplete : function(data){
			domConstruct.empty(this.domNode)
			
			var attrs = data.listView.attr;
			if(attrs.title && attrs.title.value){
				this.createTitleDom(attrs.title.value);
			}
			// 发送请求获取总数
			this.createContent(attrs.listViews.value);
		},
		
		createTitleDom : function(title){
			var html = "";
			html += "<div class='block-title'><p>"+ title +"</p></div>";
			domConstruct.place(domConstruct.toDom(html), this.domNode, "last");
		},
		
		createContent : function(items){
			items = this.filterByAuth(items);
			if(items.length === 0){
				// 没有展示项时
				var style1 = "border: 1px dashed #D4D6DB;border-radius: 2px;";
				var style2 = "height:22rem;";
				var imageStyle = "margin-top:8.2rem;";
				var textStyle = "margin-top:7.6rem;";
				this.showNoAuth(this.domNode,style1,style2,null,imageStyle,textStyle);
			}else{
				
				for(var i = 0;i < items.length;i++){
					var itemDom =  this.getItemDom(items[i]);
					domConstruct.place(itemDom, this.domNode, "last");
					var tabIndex = this.getTabIndex(items[i].countLv, items[i].lvCollection);
					var nodeType = this.getNodeType(items[i].listView,items[i].newlistView);
					this.proxyClick(itemDom, util.urlResolver(this.listViewUrl, {listViewId: items[i].listView,arrIndex: i,fdMobileId:this.fdMobileId,tabIndex:tabIndex,nodeType:nodeType}), '_self');
				}
			}

		},
		
		// 根据后台的权限进行数组的过滤
		filterByAuth : function(items){
			var rs = [];
			for(var i = 0;i < items.length;i++){
				if(items[i].auth === "true"){
					rs.push(items[i]);
				}
			}
			return rs;
		},
		
		getItemDom : function(item){
			var itemHtml = "<div class='list-item'>";
			// 左侧
			itemHtml += "<div class='item-icon'></div>";
			
			// 中间
			itemHtml += "<div class='item-detail'><p>"+ (item.title || "未定义") +"</p></div>";
			
			// 右侧
			itemHtml += "<div class='item-arrow'></div>";
			
			itemHtml += "</div>";
			return domConstruct.toDom(itemHtml);
		}
	})
});