define(['mui/tabbar/CreateButton','mui/simplecategory/SimpleCategoryMixin','dojo/topic','mui/device/adapter','dojo/query','mui/util',"dijit/registry" ], function(
		CreateButton,SimpleCategoryMixin,topic,adapter,query,util,registry) {
				var claz = CreateButton.createSubclass([ SimpleCategoryMixin ]);
				var obj = new claz(
					{
						createUrl : '/kms/multidoc/kms_multidoc_knowledge/kmsMultidocKnowledge.do?method=add&fdTemplateId=!{curIds}',
						modelName : 'com.landray.kmss.kms.knowledge.model.KmsKnowledgeCategory',
						___urlParam : 'fdTemplateType:1;fdTemplateType:3'
					});
				obj._selectCate();
})
