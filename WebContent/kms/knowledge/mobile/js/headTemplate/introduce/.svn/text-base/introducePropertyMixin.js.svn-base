define([ 'dojo/_base/declare','mui/i18n/i18n!kms-knowledge:knowledge.4m'], function(declare,msg) {
	return declare('kms.knowledge.base.doc.introduce.propertyMixin', null, {
		modelName : 'com.landray.kmss.kms.knowledge.model.KmsKnowledgeCategory',
		filters : [ {
			filterType : "FilterCheckBox",
			name : "template",
			subject : msg['knowledge.4m.type'],
			options : [ {
				name : msg['knowledge.4m.multidoc'],
				value : "1"
			}, {
				name : msg['knowledge.4m.wiki'],
				value : "2"
			} ]
		}, {
			filterType : "FilterDatetime",
			type : "date",
			name : "docPublishTime",
			subject : msg['knowledge.4m.publishTime']
		} ]
	})
})
