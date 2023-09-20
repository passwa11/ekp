/**
 * 
 */
define(['dojo/_base/declare', "dijit/_WidgetBase", "dojo/dom-class", "dojo/dom-construct", "mui/openProxyMixin", "mui/util",
		"sys/modeling/main/resources/js/mobile/homePage/common/_IndexMixin","mui/i18n/i18n!sys-modeling-main"],
		function(declare, WidgetBase, domClass, domConstruct, openProxyMixin, util, _IndexMixin,modelingLang){
	
	return declare('sys.modeling.main.resources.js.mobile.homePage.custom.textPortlet', [WidgetBase, openProxyMixin, _IndexMixin], {
		
		listViewUrl : "/sys/modeling/main/mobile/modelingAppMainMobileListView.do?method=listViewIndex&listViewId=!{listViewId}&arrIndex=!{arrIndex}&fdMobileId=!{fdMobileId}&nodeType=!{nodeType}&area=listView#path=!{tabIndex}",
		
		fdMobileId:"",
		portletInfo: {},
		
		postCreate : function() {
			this.inherited(arguments);
		},
		
		buildRendering : function() {
			this.inherited(arguments);
			//mui_pph_user
			domClass.add(this.domNode, " modelAppSpaceWidgetDemoModel");
			var value = this.portletInfo.fdPortletConfig;
			if(value){
				this.createItem(value);
			}
		},
		
		createItem : function(data){
			if(data.title.isHide === "0"){
				var titleDom = domConstruct.create('div',{
					className:"modelAppSpaceWidgetDemoTypeTitle",
				},this.domNode);
				titleDom.innerText = data.title.value;
			}
			var style = 'color:'+data.fdTextSetting.textColor+";" +
				"font-style:"+data.fdTextSetting.textStyle+";" +
				"font-weight:" + data.fdTextSetting.textBold +";" +
				"text-align:" + data.fdTextSetting.textAlign + ";" +
				"text-decoration:" + data.fdTextSetting.textDecoration +";"+
				"font-family:" + data.fdTextSetting.textFaceName + ";" +
				"font-size:" + data.fdTextSetting.textSize + ";" +
				"background :" + data.fdFillColor;
			var item = domConstruct.create('div',{
				className:"modelAppSpaceWidgetDemoTypeText",
				style:style
			},this.domNode);
			item.innerText = data.content.text;
		}
	});
});