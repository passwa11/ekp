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

	return declare("mui.tabbar.CreateButton", [TabBarButton, CategoryBase], {
		
		required: true,
		
		key: '_cateSelect',
		
		createUrl:'',
		
		buildRendering:function(){
			this.inherited(arguments);
			//this.key = '_cateSelect';//由于是双继承，第二个集成会以混入的方式覆盖该类默认值，key的值需要重新赋予。
			domAttr.set(this.domNode, 'title', '新建');
			this.domNode.dojoClick = !has('ios');
		},
		
		postCreate : function() {
			this.inherited(arguments);
			this.eventBind();
		},
		
		_onClick : function(evt) {
			this._selectCate();
		},
		
		afterSelectCate:function(evt){
			adapter.open(util.formatUrl(util.urlResolver(this.createUrl, evt)),"_self");
			this.curIds = "";
			this.curNames = "";
		},
		
		returnDialog:function(srcObj , evt){
			this.inherited(arguments);
			if(srcObj.key == this.key){
				this.defer(function(){
					this.afterSelectCate(evt);
				},1);
				
			}
		}
	});
});