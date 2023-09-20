<%@ page language="java" contentType="text/plain; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
[ 
	
	{
		url : '/km/imeeting/km_imeeting_main/kmImeetingMain.do?method=listChildren&q.mymeeting=myApproval&orderby=docCreateTime&ordertype=down&isAll=true',
		text: '<bean:message bundle="km-imeeting" key="mobile.audit.metting"/>',
		headerTemplate:"/km/imeeting/mobile/resource/js/header/AuditMeetingProperty.js"
	},
	{
		url : '/km/imeeting/km_imeeting_summary/kmImeetingSummary.do?method=listChildren&q.mysummary=myApproval&orderby=docCreateTime&ordertype=down',
		text: '<bean:message bundle="km-imeeting" key="mobile.audit.summary"/>',
		headerTemplate:"/km/imeeting/mobile/resource/js/header/AuditSummaryProperty.js",
	},
	{
		url : '/km/imeeting/km_imeeting_topic/kmImeetingTopic.do?method=list&q.mytopic=myApproval&orderby=docCreateTime&ordertype=down',
		text: '<bean:message bundle="km-imeeting" key="mobile.audit.topic"/>',
		headerTemplate:"/km/imeeting/mobile/resource/js/header/AuditTopicProperty.js",
	},
	{
		url : '/km/imeeting/km_imeeting_book/kmImeetingBook.do?method=list&q.mydoc=waitExam',
		text : '<bean:message bundle="km-imeeting" key="mobile.audit.book"/>',
		headerTemplate:"/km/imeeting/mobile/resource/js/header/AuditBookProperty.js",
	}
]