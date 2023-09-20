define([
	"dijit/_WidgetBase",
	"dojo/_base/declare",
	"mui/form/_CategoryBase",
	"dojo/dom-construct",
	"mui/util",
	"mui/dialog/Tip",
	"dojo/dom-attr",
	"mui/device/adapter",
	"dojox/mobile/sniff"
	], function(_WidgetBase,declare, CategoryBase, domConstruct, util, Tip, domAttr,adapter,has) {

	return declare("sys.mobile.categoryView.TemplateCategoryView", [_WidgetBase,CategoryBase], {
		
		required: true,
		
		key: '_cateSelect',
		
		createUrl:"",

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
		
		startup : function() {
			this.inherited(arguments);
			//直接调用this._selectCate$1，原因是this._selectCate会有listener的变化，导致切页有问题
			this._selectCate$1();
		},
		
		afterSelectCate:function(evt){
			var url = util.formatUrl(util.urlResolver(this.createUrl, evt));
			if (history.replaceState) {
				//替换链接，保证返回的时候不再回到选择分类的位置
				history.replaceState(null, null, url);
			}
			adapter.open(url,"_self");
			this.curIds = "";
			this.curNames = "";
		},
		
		returnDialog:function(srcObj , evt){
			//这里不经过this.inherited(arguments);，原因是会先销毁分类组件，导致跳转有问题（个别设备）
			this.curIds = evt.curIds
			this.curNames = evt.curNames

			if(srcObj.key == this.key){
				this.afterSelectCate(evt);
			}
		}
	});
});