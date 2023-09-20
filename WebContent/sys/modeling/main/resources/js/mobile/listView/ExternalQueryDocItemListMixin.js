define([
    "dojo/_base/declare",
    'dojox/mobile/viewRegistry',
	"mui/list/_TemplateItemListMixin",
	"sys/modeling/main/resources/js/mobile/listView/ExternalQueryDocItemMixin"
	], function(declare, viewRegistry, _TemplateItemListMixin, ExternalQueryDocItemMixin) {
	
	return declare("sys.modeling.main.resources.js.mobile.listView.ExternalQueryDocItemListMixin", [_TemplateItemListMixin], {
		itemTemplateString : null,
		itemRenderer: ExternalQueryDocItemMixin,
		startup : function(){
			this.inherited(arguments);
			/* 监听window对象的 pageshow 事件
			     （注：因iPhone IOS和部分Android机，在使用history.back()返回列表页面时，页面不会自动刷新，导致新建返回列表页后无法实时看到最新数据，为了浏览器回退的时候能够强制刷新页面，所以监听pageshow事件）*/
			this.connect(window,'pageshow','_pageshow');
		},
		_pageshow : function(evt){
			/* 注：经验证，只有第一次浏览器回退的时候persisted才会为true，所以必须使用页面刷新的方式来显示新的列表数据
			 * 不可以手动去发事件去更新列表，因为只有强制刷新整个页面后，才会使得每次浏览器回退时persisted都为true
			*/ 
			if(evt.persisted){	
				var viewObj = viewRegistry.getEnclosingView(this.domNode);
				if(viewObj && viewObj.isVisible()){
					window.location.reload();
				}
			}
		},
		createListItem: function(/*Object*/item){
			return new this.itemRenderer(this._createItemProperties(item));
		},
	});
});