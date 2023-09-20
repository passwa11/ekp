<%@ page language="java" contentType="text/plain; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>

[ 
	{ 
		moveTo : "contentView", 
		text : "<bean:message bundle="km-imeeting"  key="mobile.kmImeetingSummary.nav.information"/>", 
		selected : true 
	},
	{ 
		moveTo : "personView", 
		text : "<bean:message bundle="km-imeeting"  key="mobile.kmImeetingSummary.nav.person"/>"
	},
	{   
		moveTo : "processView", 
		text : "<bean:message bundle="km-imeeting"  key="mobile.kmImeetingSummary.nav.desion"/>"
	}
	
]
