/**
 * 
 */
define(['dojo/_base/declare', "dijit/_WidgetBase", "dojo/dom-class", "dojo/dom-construct", "mui/openProxyMixin", "mui/util",
		"sys/modeling/main/resources/js/mobile/homePage/common/_IndexMixin","mui/i18n/i18n!sys-modeling-main"],
		function(declare, WidgetBase, domClass, domConstruct, openProxyMixin, util, _IndexMixin,modelingLang){
	
	return declare('sys.modeling.main.resources.js.mobile.homePage.custom.listViewWithIconPortlet', [WidgetBase, openProxyMixin, _IndexMixin], {

		listViewUrl : "/sys/modeling/main/mobile/modelingAppMainMobileListView.do?method=listViewIndex&listViewId=!{listViewId}&arrIndex=!{arrIndex}&fdMobileId=!{fdMobileId}&nodeType=!{nodeType}&order=!{order}#path=!{tabIndex}",

		DATALOAD : "/sys/modeling/mobile/index/load",

		fdMobileId:"",

		portletInfo:{},

		postCreate : function() {
			this.inherited(arguments);
		},

		buildRendering : function() {
			this.inherited(arguments);
			domClass.add(this.domNode, "mportal-listView modelAppSpaceWidgetDemoModel modelAppSpaceWidgetListViewWithIcon");
			var attrs = this.portletInfo.fdPortletConfig.attr;
			if(attrs.title && attrs.title.isHide === "0" && attrs.title.value){
				this.createTitleDom(attrs.title.value);
			}
			// 发送请求获取总数
			this.createContent(attrs.listViews.value);
		},

		createTitleDom : function(title){
			var titleDom = domConstruct.create('div',{
				className:"modelAppSpaceWidgetDemoTypeTitle",
			},this.domNode);
			titleDom.innerText = title;
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
					this.proxyClick(itemDom, util.urlResolver(this.listViewUrl, {listViewId: items[i].listView,arrIndex: i,fdMobileId:this.fdMobileId,tabIndex:tabIndex,nodeType:nodeType,order:this.portletInfo.fdOrder}), '_self');
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

		getItemDom : function(value){
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
			html +='</div>';
			return domConstruct.toDom(html);
		}
	});
});