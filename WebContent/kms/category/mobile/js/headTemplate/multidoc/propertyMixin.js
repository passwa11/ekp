define([ 'dojo/_base/declare' ], function(declare) {
	return declare('kms.category.multidoc.propertyMixin', null, {
		modelName : 'com.landray.kmss.kms.knowledge.model.KmsKnowledgeCategory',
		filters : [ {
			filterType : "FilterRadio",
			name : "mydoc",
			subject : "我的知识",
			options : [ {
				name : "我上传的",
				value : "create"
			}, {
				name : "待我审的",
				value : "approval"
			}, {
				name : "我已审的",
				value : "approved"
			}
			]
		}, {
			filterType : "FilterRadio",
			name : "docStatus",
			subject : "状态",
			options : [ {
				name : "不限",
				value : ""
			}, {
				name : "草稿",
				value : "10"
			}, {
				name : "驳回",
				value : "11"
			}, {
				name : "待审",
				value : "20"
			}, {
				name : "发布",
				value : "30"
			}, {
				name : "废弃",
				value : "00"
			} ]
		}, {
			filterType : "FilterDatetime",
			type : "date",
			name : "docPublishTime",
			subject : "发布时间"
		} ]
	})
})
