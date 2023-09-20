<%@ page language="java" contentType="text/plain; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include ref="zone.criInfo">
	<template:replace name="content">
		[
			{
				"url":"/kms/knowledge/kms_knowledge_base_doc/kmsKnowledgeBaseDocIndex.do?method=listPerson&orderby=docPublishTime&ordertype=down&rowsize=8&userId=!{userId}&personType=other&q.mydoc=myOriginal",
				"text":"${lfn:message('kms-knowledge:kms.knowledge.4m.original') }",
				"isDefault":true
			},
			{
				"url":"/kms/knowledge/kms_knowledge_base_doc/kmsKnowledgeBaseDocIndex.do?method=listPerson&orderby=docPublishTime&ordertype=down&rowsize=8&userId=!{userId}&personType=other&q.mydoc=myCreate",
				"text":"${lfn:message('kms-knowledge:kms.knowledge.4m.upload') }"
				
			},
			{
				"url":"/kms/knowledge/kms_knowledge_base_doc/kmsKnowledgeBaseDocIndex.do?method=listPerson&orderby=docPublishTime&ordertype=down&rowsize=8&userId=!{userId}&personType=other&q.mydoc=myEva",
				"text":"${lfn:message('kms-knowledge:kms.knowledge.4m.comment') }"
		
			},
			{
				"url":"/kms/knowledge/kms_knowledge_base_doc/kmsKnowledgeBaseDocIndex.do?method=listPerson&orderby=docPublishTime&ordertype=down&rowsize=8&userId=!{userId}&personType=other&q.mydoc=myIntro",
				"text":"${lfn:message('kms-knowledge:kms.knowledge.4m.recommend') }"
			}
		]
	</template:replace>
</template:include>