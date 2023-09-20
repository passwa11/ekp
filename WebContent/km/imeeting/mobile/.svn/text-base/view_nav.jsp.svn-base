<%@ page language="java" contentType="text/plain; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ taglib uri="/WEB-INF/kmss.tld" prefix="kmss"%>
[ 
	{ 
		moveTo : "contentView", 
		text : "<bean:message bundle="km-imeeting" key="mobile.kmImeetingMain.nav.content"/>"
		<c:if test="${empty param.fromSysAttend }">
		, selected : true 
		</c:if>
	},
	{ 
		moveTo : "personView", 
		text : "<bean:message bundle="km-imeeting" key="mobile.kmImeetingMain.nav.person"/>"
	}
	<c:if test="${JsParam.docStatus>='30' }">
	<kmss:auth requestURL="/km/imeeting/km_imeeting_main_feedback/kmImeetingMainFeedback.do?method=getFeedbackList&meetingId=${JsParam.fdMeetingId}" requestMethod="GET">
	,
	{   
		moveTo : "feedbackListView", 
		text : "<bean:message bundle="km-imeeting" key="mobile.kmImeetingMain.nav.feedback"/>"
	}
	</kmss:auth>
	</c:if>
	
	<c:if test="${JsParam.docStatus < '30' }">
	,
	{   
		moveTo : "processView", 
		text : "<bean:message bundle="km-imeeting" key="mobile.kmImeetingMain.nav.review"/>"
	}
	</c:if>
	<kmss:ifModuleExist path="/sys/attend/">
	,
	{
		moveTo : "attendView",
		text : "<bean:message bundle="km-imeeting" key="mobile.kmImeetingMain.nav.attend"/>"
		<c:if test="${not empty param.fromSysAttend }">
		, selected : true 
		</c:if>
	}
	</kmss:ifModuleExist>
]
