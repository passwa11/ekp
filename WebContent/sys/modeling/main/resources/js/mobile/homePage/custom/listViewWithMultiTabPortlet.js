/**
 * 
 */
define(['dojo/_base/declare', "dijit/_WidgetBase", "dojo/dom-class", "dojo/dom-construct", "mui/openProxyMixin", "mui/util",
		"sys/modeling/main/resources/js/mobile/homePage/common/_IndexMixin","mui/i18n/i18n!sys-modeling-main"],
		function(declare, WidgetBase, domClass, domConstruct, openProxyMixin, util, _IndexMixin,modelingLang){
	
	return declare('sys.modeling.main.resources.js.mobile.homePage.custom.listViewWithMultiTabPortlet', [WidgetBase, openProxyMixin, _IndexMixin], {
		
		listViewUrl : "/sys/modeling/main/mobile/modelingAppMainMobileListView.do?method=listViewIndex&listViewId=!{listViewId}&arrIndex=!{arrIndex}&fdMobileId=!{fdMobileId}&nodeType=!{nodeType}&order=!{order}#path=!{tabIndex}",
		
		fdMobileId:"",

		portletInfo:{},
		
		postCreate : function() {
			this.inherited(arguments);
		},
		
		buildRendering : function() {
			this.inherited(arguments);
			domClass.add(this.domNode, "mui_pph_handleList");
		},
		
		onComplete : function(data){
			domConstruct.empty(this.domNode)
			var isAllNoAuth = true;
			var values = data.listView.attr.listViews.value;
			for(var i = 0;i < values.length;i++){
				if(values[i].auth === "true"){
					var item = this.createItem(values[i]);
					domConstruct.place(item, this.domNode, "last");
					var tabIndex = this.getTabIndex(values[i].countLv, values[i].lvCollection);
					var nodeType = this.getNodeType(values[i].listView,values[i].newlistView);
					this.proxyClick(item, util.urlResolver(this.listViewUrl, {listViewId: values[i].listView,arrIndex: i,fdMobileId:this.fdMobileId,tabIndex:tabIndex,nodeType:nodeType,order:this.portletInfo.fdOrder}), '_self');
					isAllNoAuth = false;
				}
			}
			var style1 = "color:#3E4665;height:5rem;border: 1px dashed #D4D6DB;border-radius: 2px;position:relative;";
			var style2 = "position: absolute;top:39%;left:17%";
			if(isAllNoAuth && values.length>0){
				this.showNoAuth(this.domNode,style1,style2 );
			}else if(values.length===0){
				this.showNoData(this.domNode,style1,style2 );
			}
		},
		
		createItem : function(value){
			value.count = value.count || 0;
			var html = "";
			html += "<div class='mui_pph_handleList_item'>";
			// 左侧
			html += "<div class='mui_pph_hli_title'>";
			html += "<i class='mui_pph_hli_title_icon mui_icon-ddlc'></i>";
			html += "<span>"+ (value.title || modelingLang['mui.modeling.undefind']) +"</span>";
			html += "</div>";
			// 右侧
			html += "<div class='mui_pph_hli_more'>";
			if(value.count > 0){
				html += "<span class='mui_pph_hli_more_number'>"+ value.count +"</span>";
			}
			html += "<i class='mui_pph_hli_more_icon'></i>";
			html += "</div>";
			
			html += "</div>";
			return domConstruct.toDom(html);
		}
	});
});