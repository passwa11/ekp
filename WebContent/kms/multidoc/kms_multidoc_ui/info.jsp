<%@ page language="java" contentType="text/plain; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
[ 
	{ text:"${lfn:message('kms-knowledge:kmsKnowledgeBaseDoc.upload')}",count_url:'/kms/knowledge/kms_knowledge_base_doc/kmsKnowledgeBaseDocIndex.do?method=count&q.mydoc=create&q.template=1','href':'/create',router:true},
	{ text:"${lfn:message('sys-bookmark:button.bookmark')}",count_url:'/sys/bookmark/sys_bookmark_main/sysBookmarkMain.do?method=count&type=create&fdModelName=com.landray.kmss.kms.multidoc.model.KmsMultidocKnowledge','href':'/bookmark',router:true }, 
	{ text:"${lfn:message('sys-evaluation:table.sysEvaluationMain')}", count_url:'/sys/evaluation/sys_evaluation_main/sysEvaluationMain.do?method=count&type=create&fdModelName=com.landray.kmss.kms.multidoc.model.KmsMultidocKnowledge','href':'/eval',router:true} 
]
