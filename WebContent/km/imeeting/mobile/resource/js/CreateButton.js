define([
	"dojo/_base/declare",
	"dojo/_base/lang",
	"dojox/mobile/Button",
	"mui/form/_CategoryBase",
	"dojo/dom-construct",
	"mui/util",
	"mui/dialog/Tip",
	"dojo/dom-attr",
	"mui/device/adapter",
	"dojox/mobile/sniff",
	"mui/tabbar/TabBarButton"
	], function(declare, lang, Button, CategoryBase, domConstruct, util, Tip, domAttr,adapter,has,tabButton) {

	return declare("km.imeeting.mobile.resource.js.CreateButton", [tabButton, CategoryBase], {
		icon1: "mui mui-create",
		
		key: '',
		
		createUrl:'',
		
		postCreate : function() {
			this.inherited(arguments);
			this.eventBind();
		},
		
		_onClick : function(evt) {
			this.defer(function(){
				this._selectCate();
			}, 350);
		},
		
		afterSelectCate:function(evt){
			var process = Tip.processing();
			process.show();
			this.defer(function(){
				
				evt = lang.mixin({}, evt, window.__MEETING_CREATE_PAYLOAD__ || {});
				
				adapter.open(util.formatUrl(util.urlResolver(this.createUrl, evt)),"_self");
				this.curIds = "";
				this.curNames = "";
				process.hide();
			},300);
		},
		
		returnDialog:function(srcObj , evt){
			this.inherited(arguments);
			if(srcObj.key == this.key){
				this.afterSelectCate(evt);
			}
		}
	});
});