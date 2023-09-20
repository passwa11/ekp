define([ 'dojo/_base/declare' ], function(declare) {
	return declare('kms.category.kem.propertyMixin', null, {
		modelName : 'com.landray.kmss.kms.kem.model.KmsKemCategory',
		filters : [ {
			filterType : "FilterRadio",
			name : "mydoc",
			subject : "我的知识",
			options : [ {
				name : "我创建的",
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
			filterType : "FilterCheckBox",
			name : "docStatus",
			subject : "文档状态",
			options : [ {
				name : "废弃",
				value : "00"
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
				name : "过期",
				value : "40"
			} ]
		}, {
			filterType : "FilterDatetime",
			type : "date",
			name : "docPublishTime",
			subject : "创建时间"
		} ]
	})
})
