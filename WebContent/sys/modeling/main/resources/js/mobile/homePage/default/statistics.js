/**
 * 
 */
define(['dojo/_base/declare',"dijit/_WidgetBase", "dojo/dom-class", "dojo/dom-construct", "mui/openProxyMixin", "mui/util", 
		"sys/modeling/main/resources/js/mobile/homePage/common/_IndexMixin"],
		function(declare, WidgetBase, domClass, domConstruct, openProxyMixin, util, _IndexMixin){
	
	return declare('sys.modeling.main.resources.js.mobile.homePage.default.statistics', [WidgetBase, openProxyMixin, _IndexMixin] , {
		
		url : "",

		listViewUrl : "/sys/modeling/main/mobile/modelingAppMainMobileListView.do?method=listViewIndex&listViewId=!{listViewId}&arrIndex=!{arrIndex}&fdMobileId=!{fdMobileId}&nodeType=!{nodeType}&area=statistics#path=!{tabIndex}",
		
		DATALOAD : "/sys/modeling/mobile/index/load",

		fdMobileId:"",
		
		postCreate : function() {
			this.inherited(arguments);
			this.subscribe(this.DATALOAD, 'onComplete');
		},
		
		buildRendering : function() {
			this.inherited(arguments);
			domClass.add(this.domNode, "mui_pph_main_message");
		},
		
		onComplete : function(data){
			domConstruct.empty(this.domNode)

			var values = data.statistics.attr.listViews.value;
			// 发送请求获取总数
			this.renderChildren(values);
		},
		
		renderChildren : function(values){
			var isAllNoAuth = true;
			for(var i = 0;i < values.length;i++){
				if(values[i].auth === "true"){
					var item = this.createItem(values[i]);
					domConstruct.place(item, this.domNode, "last");
					var tabIndex = this.getTabIndex(values[i].countLv, values[i].lvCollection);
					var nodeType = this.getNodeType(values[i].countLv,values[i].newlistView);
					this.proxyClick(item, util.urlResolver(this.listViewUrl, {listViewId: values[i].listView,arrIndex: i,fdMobileId:this.fdMobileId,tabIndex:tabIndex,nodeType:nodeType}), '_self');
					isAllNoAuth = false;
				}
			}
			if(isAllNoAuth){
				this.showNoAuth(this.domNode, "color:#3E4665");
			}
		},
		
		createItem : function(value){
			value.count = value.count || 0;
			var html = "";
			html += "<div class='mui_pph_mm_item'>";
			// 总数
			html += "<div class='mui_pph_mm_data_statistics'>";
			html += "<span";
			if(value.count === 0){
				html += " class='zero_status_color' ";
			}
			html += " >"+ value.count +"</span>";
			html += "</div>";
			// 文本
			html += "<span class='mui_pph_mm_item_introduce'>"+ value.title +"</span>";
			
			html += "</div>";
			return domConstruct.toDom(html);
		}
		
		
	});
});