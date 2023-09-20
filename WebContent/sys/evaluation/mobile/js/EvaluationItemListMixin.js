define([ "dojo/_base/declare", "mui/list/_TemplateItemListMixin", "mui/util",
		dojoConfig.baseUrl + "sys/evaluation/mobile/js/EvaluationItemMixin.js",
		"sys/evaluation/mobile/js/EvaluationItemListReplyMixin",
		"sys/evaluation/mobile/js/EvaluationAdditionListMixin",
		"dojo/_base/array" ], function(declare, _TemplateItemListMixin, util,
		EvaluationItemMixin, EvaluationItemListReplyMixin,EvaluationAdditionListMixin, array) {

	return declare("sys.evaluation.EvaluationItemListMixin", [
			_TemplateItemListMixin, EvaluationItemListReplyMixin, EvaluationAdditionListMixin ], {
		buildRendering : function() {
			this.inherited(arguments);
			this.url = util.urlResolver(this.url, {
				"fdModelId" : this.fdModelId,
				"fdModelName" : this.fdModelName
			});
			this.subscribe('/mui/list/loaded', 'loadedHandle');
		},

		itemRenderer : EvaluationItemMixin,

		ids : null,
		
		idList : [],
		
		loadedHandle : function(evt) {
			if (!evt)
				return;
			var datas = evt.listDatas;
			this.buildIds(datas);
			this.inherited(arguments);
		},

		// 构建查询id
		buildIds : function(datas) {
			this.ids = '';
			this.idList = [];
			array.forEach(datas, function(data, index) {
				var id = data.fdId;
				this.ids += (index == 0 ? id : ';' + id);
				this.idList.push(id);
			}, this);
		}

	});
});