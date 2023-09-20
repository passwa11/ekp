define(function(require, exports, module) {
	var $ = require("lui/jquery");
	var env = require('lui/util/env');
	var strutil = require('lui/util/str');
	var columntable = require('lui/listview/columntable');
	var modelingUtil = require('sys/modeling/main/ui/modelingUtil');
	
	var modelingTable = columntable.ColumnTable.extend({
		buildUrl : function($super,ps, page, sorts, others){
			var url = this.source.url;
			// 定制 只处理动态模块
			var item = modelingUtil.getDynamicItem();
			if(item){
				this.source.setUrl(item.actionUrl + "?method=list");
			}else{
				// 清理加载图标
				if(this.parent.loading){
					this.parent.loading.hide();
					this.parent.element.css('min-height', 'inherit');
					this.parent.loading = null;
				}
			}
			$super(ps, page, sorts, others);	
		},
		onClick : function(evt) {
			var $target = $(evt.target);
			var goon = true;
			var tagName = evt.target.tagName.toUpperCase();
			if (tagName == 'A' || tagName == 'INPUT')
				goon = false;
			while ($target.length > 0) {
				if ($target.attr('data-lui-mark-id')) {
					if (!goon)
						return;
					var code = '';
					var rowId = $target.attr('data-lui-mark-id');
					// 定制 处理动态模块 动态补全actionurl
					var item = modelingUtil.getDynamicItem();
					var rowHref = item.actionUrl + "?method=view&fdId=!{fdId}";
					for (var j = 0; j < this.kvData.length; j++) {
						if (rowId === this.kvData[j]['rowId']) {
							var href = strutil.variableResolver(
									rowHref, this.kvData[j])
							code = ["window.open('",
									env.fn.formatUrl(href), "','",
									this.target, "')"].join('');
							break;
						}
					}
					new Function(code).apply(this.element.get(0));
					break;
				}
				$target = $target.parent();
			}
		}
		
	});
	
	exports.Table = modelingTable;
})