/**
 * 
 */
define(['dojo/_base/declare', "dijit/_WidgetBase", "dojo/dom-class", "dojo/dom-construct", "mui/openProxyMixin", "mui/util",
		"sys/modeling/main/resources/js/mobile/homePage/common/_IndexMixin","mui/i18n/i18n!sys-modeling-main"],
		function(declare, WidgetBase, domClass, domConstruct, openProxyMixin, util, _IndexMixin,modelingLang){
	
	return declare('sys.modeling.main.resources.js.mobile.homePage.custom.imagePortlet', [WidgetBase, openProxyMixin, _IndexMixin], {
		
		listViewUrl : "/sys/modeling/main/mobile/modelingAppMainMobileListView.do?method=listViewIndex&listViewId=!{listViewId}&arrIndex=!{arrIndex}&fdMobileId=!{fdMobileId}&nodeType=!{nodeType}&area=listView#path=!{tabIndex}",
		
		fdMobileId:"",

		portletInfo:{},
		
		postCreate : function() {
			this.inherited(arguments);
		},
		
		buildRendering : function() {
			this.inherited(arguments);
			domClass.add(this.domNode, "modelAppSpaceWidgetDemoModel");
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
			var fdUrlId=data.fdImageUrlId;
			var url = util.formatUrl("/sys/attachment/sys_att_main/sysAttMain.do?method=download&open=1&fdId="+fdUrlId);
			var fdFillStyle=data.fdFillType;
			var fillStyle= "contain";
			if(fdFillStyle){
				switch (fdFillStyle) {
					case "1":
						fillStyle = "cover";
						break;
					case "2":
						fillStyle = "contain";
						break;
					case "3":
						fillStyle = "100% 100%";
						break;
					default:
						break;
				}
			}
			var style = 'width:100%;background-color:'+data.fdFillColor;
			if(fdUrlId){

				var backgroundValue="url('"+url+"') no-repeat center"
				// style +=";background:" + backgroundValue;
				style += ";background-size:" + fillStyle;
				domConstruct.create('img',{
					className:"",
					src:url,
					style : style
				},this.domNode);
			}
			// domConstruct.create('div',{
			// 	className:"",
			// 	style:style
			// },this.domNode);
		}
	});
});