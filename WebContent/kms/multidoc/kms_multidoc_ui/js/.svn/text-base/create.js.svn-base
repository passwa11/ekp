//新建分类选择框，给门户或二级页面调用
define(function(require, exports, module) {
	var dialog = require('lui/dialog');
	//参数为默认选中的分类
	function addDoc(categoryId) {
		if (categoryId) {
			var ids = categoryId.split(";");
			if (ids && ids.length != 1) {
				categoryId = "";
			}
		}
		dialog.simpleCategoryForNewFile({modelName:"com.landray.kmss.kms.knowledge.model.KmsKnowledgeCategory",
			urlParam:"/kms/multidoc/kms_multidoc_knowledge/kmsMultidocKnowledge.do?method=add&fdTemplateId=!{id}",
	 		mulSelect:false,
	 		action:null,
	 		winTitle:null,
	 		canClose:null,
	 		___urlParam:{'fdTemplateType':'1,3'},
             url:"/kms/knowledge/kms_knowledge_category/simple-category.jsp"},
             "", false, null,
			null, categoryId, null,
             null);
	}
	exports.addDoc = addDoc;
});