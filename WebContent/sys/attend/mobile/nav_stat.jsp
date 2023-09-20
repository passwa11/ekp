<%@ page language="java" contentType="text/plain; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>

[ 
	{ 
		moveTo : "personCalendarView", 
		<c:if test="${param.navIndex !='2' && param.navIndex !='3'}">
			selected : true,
		</c:if>
		text : "${ lfn:message('sys-attend:mui.title.person.calendar') }"
	}
	<c:if test="${param.isStatReader=='true' }">
	,
	{ 
		moveTo : "statView", 
		<c:if test="${param.navIndex=='2' }">
			selected : true,
		</c:if>
		text : "${ lfn:message('sys-attend:mui.title.attend.stat') }"
	}
	</c:if>
	<c:if test="${param.isSignStatReader=='true' }">
	,{ 
		moveTo : "signStatView", 
		<c:if test="${param.navIndex=='3' }">
			selected : true,
		</c:if>
		text : "${ lfn:message('sys-attend:mui.title.cust.stat') }"
	}
	</c:if>
]
