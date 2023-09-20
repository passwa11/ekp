<%@ page language="java" contentType="text/json; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@page import="com.landray.kmss.km.calendar.model.KmCalendarPersonGroup" %>
<%@page import="java.util.List" %>
<%@page import="com.landray.kmss.sys.organization.model.SysOrgElement" %>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<list:data>
	<list:data-columns var="kmCalendarPersonGroup" list="${groupList }">
		<list:data-column col="fdId">
			${kmCalendarPersonGroup.id}
		</list:data-column>
		<list:data-column col="fdName" title="${ lfn:message('km-calendar:kmCalendarPersonGroup.docSubject') }" escape="false" style="text-align:left;min-width:150px;">
			${kmCalendarPersonGroup.name}
		</list:data-column>
	</list:data-columns>
</list:data>