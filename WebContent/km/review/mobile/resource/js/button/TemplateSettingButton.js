define([
        "mui/tabbar/TabBarButton",
    	"dojo/_base/declare",
    	"mui/form/_CategoryBase",
    	"dojo/dom-construct",
    	"mui/util",
    	"mui/dialog/Tip",
    	"dojo/dom-attr",
    	"mui/device/adapter",
    	"dojox/mobile/sniff"
	], function(TabBarButton, declare, CategoryBase, domConstruct, util, Tip, domAttr,adapter,has) {

	return declare("km.review.mobile.resource.js.button.TemplateSettingButton", [TabBarButton, CategoryBase], {
		icon1: "mui mui-create",
		
		createUrl:'',
		
		required: true,
		
		key:'_template_setting',
		
		buildRendering:function(){
			this.inherited(arguments);
			domAttr.set(this.domNode, 'title', '模板设置');
			this.domNode.dojoClick = !has('ios');
		},
		
		postCreate : function() {
			this.inherited(arguments);
			this.eventBind();
		},
		
		_onClick : function(evt) {
			this.defer(function(){
				if(this.createUrl){
					adapter.open(util.formatUrl(util.urlResolver(this.createUrl, evt)),"_self");
				}
			}, 350);
		}
		
	});
});