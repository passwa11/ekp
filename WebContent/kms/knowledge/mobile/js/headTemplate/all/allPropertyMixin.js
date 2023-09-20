define([ 'dojo/_base/declare','mui/i18n/i18n!kms-knowledge:list','mui/i18n/i18n!:statu','mui/i18n/i18n!kms-knowledge:knowledge.4m'], 
		function(declare,msg,msg2,msg3) {
	return declare('kms.knowledge.base.doc.all.propertyMixin', null, {
		modelName : 'com.landray.kmss.kms.knowledge.model.KmsKnowledgeCategory',
		filters : [ {
			filterType : "FilterRadio",
			name : "mydoc",
			subject : msg['list.kmDoc.my'],
			options : [ {
				name : msg['list.create'],
				value : "create"
			}, {
				name : msg['list.approval'],
				value : "approval"
			}, {
				name :  msg['list.approved'],
				value : "approved"
			}, {
				name :  msg['list.myBookmarkMobile'],
				value : "myBookmarkMobile"
			}
			]
		}, {
			filterType : "FilterCheckBox",
			name : "docStatus",
			subject : msg3['knowledge.4m.Status'],
			options : [{
				name : msg2['status.draft'],
				value : "10"
			}, {
				name : msg2['status.refuse'],
				value : "11"
			}, {
				name : msg2['status.examine'],
				value : "20"
			}, {
				name : msg2['status.publish'],
				value : "30"
			}, {
				name : msg2['status.discard'],
				value : "00"
			} ]
		}, {
			filterType : "FilterCheckBox",
			name : "template",
			subject : msg3['knowledge.4m.type'],
			options : [ {
				name : msg3['knowledge.4m.multidoc'],
				value : "1"
			}, {
				name : msg3['knowledge.4m.wiki'],
				value : "2"
			} ]
		}, {
			filterType : "FilterDatetime",
			type : "date",
			name : "docPublishTime",
			subject : msg3['knowledge.4m.publishTime']
		} ]
	})
})
