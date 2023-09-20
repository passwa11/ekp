<%@ page language="java" contentType="text/plain; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@page import="com.landray.kmss.km.imeeting.util.KmImeetingConfigUtil"%>
<%
	request.setAttribute("useCycle", KmImeetingConfigUtil.isCycle());
%>

[ 
	{
		url : '/km/imeeting/km_imeeting_main/kmImeetingMain.do?method=listChildren&orderby=docCreateTime&ordertype=down&isAll=true',
		text: '<bean:message bundle="km-imeeting" key="mobile.imeeting.search.all"/>'
	},
	{
		url : '/km/imeeting/km_imeeting_main/kmImeetingMain.do?method=listChildren&orderby=docCreateTime&ordertype=down&isVideo=false',
		text: '<bean:message bundle="km-imeeting" key="mobile.imeeting.search.normal"/>'
	},
	<c:if test="${useCycle eq 'true'}">
	{
		url : '/km/imeeting/km_imeeting_main/kmImeetingMain.do?method=listChildren&orderby=docCreateTime&ordertype=down&isCycle=true',
		text: '<bean:message bundle="km-imeeting" key="mobile.imeeting.search.cycle"/>'
	},
	</c:if>
	<%if(KmImeetingConfigUtil.isVideoMeetingEnable()){ %>
	{
		url : '/km/imeeting/km_imeeting_main/kmImeetingMain.do?method=listChildren&orderby=docCreateTime&ordertype=down&isVideo=true',
		text: '<bean:message bundle="km-imeeting" key="mobile.imeeting.search.video"/>'
	}
	<%}%>
]