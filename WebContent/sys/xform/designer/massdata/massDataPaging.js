define(function(require, exports, module) {
	var base = require("lui/base");
	var layout = require("lui/view/layout");
	var topic = require('lui/topic');
	var $ = require('lui/jquery');
	var paging = require('lui/listview/paging');

	var MassDataPaging = paging.Paging.extend({
		
		initProps : function($super, cfg){ 
			$super(cfg);
		},
		
		startup : function($super) {
			if (this.isStartup) {
				return;
			}
			var pagingLayout = new layout.Template({
				"kind" : "paging",
				"src" : "/sys/ui/extend/listview/paging.jsp",
				parent : this
			});
			this.addChild(pagingLayout);
			pagingLayout.startup();
			this.isStartup = true;
		},
		
		draw : function($super){
			var evt = {};
			evt["page"] = {};
			evt["page"].currentPage = this.currentPage;
			evt["page"].pageSize = this.pageSize;
			evt["page"].totalSize = this.totalSize;
			$super(evt);
		},
		
		onReLayout : function() {
			var pageSize = this.element.find('[data-lui-mark="paging.amount"]')
					.val(), currentPage = this.element
					.find('[data-lui-mark="paging.pageno"]').val();
			pageSize = pageSize > 500 ? 500 : pageSize;
			// 数字校验
			var reg = /^[0-9]+$/;
			if (!reg.test(pageSize) || !reg.test(currentPage))
				return;

			this.pageSize = parseInt(pageSize);
			this.currentPage = parseInt(currentPage);
			var evt = {
				pageno : this.currentPage,
				rowsize : this.pageSize,
				totalSize : this.totalSize
			};
			this.parent.reRender(evt);
		},
	});

	exports.MassDataPaging = MassDataPaging;
})