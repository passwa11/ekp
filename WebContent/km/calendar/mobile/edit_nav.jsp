<%@ page language="java" contentType="text/plain; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>

[ 	
	{ 
		moveTo : "dateView", 
		text : '<i class="mui mui-meeting_date"></i><span class="txt"><bean:message bundle="km-calendar"  key="kmCalendarMain.docTime"/></span>',
		selected : true 
	},
	{
		moveTo : "recurrenceView",
		text : '<i class="mui mui-loop"></i><span class="txt"><bean:message bundle="km-calendar"  key="kmCalendarMain.opt.recurrence.mobile"/></span>'
	},
	{ 
		moveTo : 'notifyView', 
		text : '<i class="mui mui-ring-warning"></i><span class="txt"><bean:message bundle="km-calendar"  key="kmCalendarMain.opt.remind.mobile"/></span>'
	},
	{   
		moveTo : "labelView", 
		text : '<i class="mui mui-tag"></i><span class="txt"><bean:message bundle="km-calendar"  key="kmCalendarMain.docLabel"/></span>'
	}
]
