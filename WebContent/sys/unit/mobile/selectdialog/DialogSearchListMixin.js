/**
 * 搜索列表Mixin
 */
define( [
	"dojo/_base/declare",
	"dojo/_base/array",
	"dojo/_base/lang",
	"dojo/json",
	"dojo/dom-construct",
	"dojox/mobile/viewRegistry",
	"mui/util",
	"mui/i18n/i18n!sys-mobile"
	], function(declare, array, lang, JSON, domConstruct, viewRegistry, util, Msg) {
	
	return declare("mui.selectdialog.DialogSearchListMixin", null, {
		lazy: true,
		
		postCreate: function(){
			this.inherited(arguments);
			this.showMore = false;
			this.subscribe("/mui/unit/search/submit","_handleSearch");
			this.subscribe("/mui/unit/search/cancel","_handleCancel");
		},
		
		generateList: function(list){
			this.inherited(arguments);
		},
		
		// 查询回调处理
		_handleSearch: function(srcObj, evt){
			if(!evt || !evt.keyword){
				return;
			}
			if(this.isVisibleView()){
				this.url = util.formatUrl(util.urlResolver(this.searchUrl, lang.mixin(evt, this) ));
				this.showMore = false;
				this.buildLoading();
				this.reload();
			}
		},
		
		_handleCancel: function(){
			if(this.isVisibleView()){
				this.buildLoading();
				this.url = util.urlResolver(util.formatUrl(this.dataUrl), this);
				this.showMore=true;
				this.reload();
			}
		},
		
		// 当前组件是否处于可视区域，可视区域组件才响应事件
		isVisibleView: function(){
			var thisView = viewRegistry.getEnclosingView(this.domNode);
			return thisView.isVisible();
		}
	
	});
});