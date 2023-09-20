<%@ page language="java" contentType="text/plain; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
[ 
    {
		text : "<bean:message bundle="km-calendar"  key="kmCalendarMain.calendar.header.title"/>",
		selected : true
	},
	{
		text : "<bean:message bundle="km-calendar"  key="module.km.calendar.tree.share.calendar"/>",
		key:"share"
	}
	<c:if test="${param.hasPersonGroup}">
	,{
		text : "<bean:message bundle="km-calendar"  key="kmCalendarMain.group.header.title"/>"
	}
	</c:if>
]
