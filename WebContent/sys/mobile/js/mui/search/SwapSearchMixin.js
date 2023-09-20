define([
		"dojo/_base/declare",
		"mui/util",
		"dojo/_base/array",
		"dojo/query",
		"dijit/registry",
		"dojo/topic" ], function(
		declare,
		util,
		array,
		query,
		registry,
		topic) {

	return declare(

	"mui.search.SwapSearchMixin", null, {

		// 搜索关键字字段名
		searchKey : null,

		// 搜索值
		searchValue : null,

		buildRendering : function() {

			this.inherited(arguments);

			// 触发改变数据请求链接
			this.subscribe("/mui/search/submit", 'searchConfirm');
			this.subscribe("/mui/search/cancel", 'cancelSearch');

			
		},

		generateSwapList : function(items, widget) {

			this.propertyUrls = [];

			if (this.searchValue) {

				array.forEach(items, function(item) {

					item.url = util.setUrlParameter(item.url, this.searchKey,
							this.searchValue);

				}, this);

			}

			this.inherited(arguments);

		},
		
		
		cancelSearch : function() {
			this.searchValue = null;
			this._searchChangeUrl();
		},
		
		searchConfirm : function(obj, evt) {

			if (!evt)
				return;

			this.searchValue = evt.keyword;

			this._searchChangeUrl();
		},
		
		_searchChangeUrl : function() {
			var children = this.getChildren();

			// 初始化，长度为0
			if (children.length == 0)
				return;

			// 滚动置顶
			topic.publish('/mui/list/toTop', this);

			array.forEach(children, function(view) {

				view.reloadTime = 0;

				var child = view.getChildren()[0];
				var list = registry.getEnclosingWidget(query(
						'.mblEdgeToEdgeList', view.domNode)[0]);

				child.rel.url = util.setUrlParameter(child.rel.url, this.searchKey,
						this.searchValue);

				list.url = util.formatUrl(child.rel.url);

				if (this.currView == view)
					list.reload();

			}, this);
		}
		
		

	});
});