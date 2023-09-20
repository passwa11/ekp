<%@ page language="java" contentType="text/plain; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
[ 
	
	{
		url : '/km/imeeting/km_imeeting_summary/kmImeetingSummary.do?method=listChildren&q.mysummary=myAttend&orderby=docCreateTime&ordertype=down',
		text: '<bean:message bundle="km-imeeting" key="mobile.summary.myAttend"/>'
	},
	{
		url : '/km/imeeting/km_imeeting_summary/kmImeetingSummary.do?method=listChildren&q.mysummary=myCreate&orderby=docCreateTime&ordertype=down',
		text: '<bean:message bundle="km-imeeting" key="mobile.summary.myCreate"/>'
	},
	{
		url : '/km/imeeting/km_imeeting_summary/kmImeetingSummary.do?method=listChildren&orderby=docCreateTime&ordertype=down',
		text: '<bean:message bundle="km-imeeting" key="mobile.summary.all"/>'
	}
]