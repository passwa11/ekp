/**
 * 
 */
define(['dojo/_base/declare', "dijit/_WidgetBase", "dojo/dom-class", "dojo/dom-construct", "mui/openProxyMixin", "mui/util",
		"sys/modeling/main/resources/js/mobile/homePage/common/_IndexMixin","mui/i18n/i18n!sys-modeling-main"],
		function(declare, WidgetBase, domClass, domConstruct, openProxyMixin, util, _IndexMixin,modelingLang){
	
	return declare('sys.modeling.main.resources.js.mobile.homePage.custom.listViewWithCountPortlet', [WidgetBase, openProxyMixin, _IndexMixin], {
		
		listViewUrl : "/sys/modeling/main/mobile/modelingAppMainMobileListView.do?method=listViewIndex&listViewId=!{listViewId}&arrIndex=!{arrIndex}&fdMobileId=!{fdMobileId}&nodeType=!{nodeType}&order=!{order}#path=!{tabIndex}",

		fdMobileId:"",

		portletInfo:{},
		
		postCreate : function() {
			this.inherited(arguments);
		},
		
		buildRendering : function() {
			this.inherited(arguments);
			domClass.add(this.domNode, "mui_pph_handleList modelAppSpaceWidgetDemoModel modelAppSpaceWidgetListViewWithCount");
			var isAllNoAuth = true;
			var values = this.portletInfo.fdPortletConfig.attr.listViews.value;
			if(this.portletInfo.fdPortletConfig.attr.title.isHide === "0"){
				var titleDom = domConstruct.create('div',{
					className:"modelAppSpaceWidgetDemoTypeTitle",
				},this.domNode);
				titleDom.innerText = this.portletInfo.fdPortletConfig.attr.title.value;
			}
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
			var html ="";
			html +='<div class="modelAppSpaceWidgetDemoTypeEnter">\n';
			if(value.iconType == "4"){
				 value.icon = util.formatUrl(value.icon);
				html +='                                <i class="mui" style="background: url('+value.icon+') no-repeat center"></i>\n';
			}else{
				if(value.icon){
					value.icon = "mui " + value.icon;
				}else {
					value.icon = 'mui_pph_hli_title_icon mui_icon-ddlc';
				}
				html +='                                <i class="'+value.icon+'"></i>\n';
			}
			html +='                                <strong>'+ (value.title || modelingLang['mui.modeling.undefind']) +'</strong>\n' +
				'                                <span class="modelAppSpaceWidgetDemoIconLinkN"></span>\n';
			if(value.count > 0){
				html += '<span class="modelAppSpaceWidgetDemoNum">'+value.count+'</span>\n';
			}
			html +='</div>';
			return domConstruct.toDom(html);
		}
	});
});