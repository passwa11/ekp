<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/resource/jsp/common.jsp"%>
<%
	String fdkey = (String)request.getAttribute("fdKey");
	if(fdkey==null) {
		fdkey = "calendarView";
		request.setAttribute("fdKey",fdkey);
	}	
%>
<script type="text/javascript">
<!--
	Com_IncludeFile("calendarview.js|data.js|calendar.js");
	Com_IncludeFile("calendarview.css","style/"+Com_Parameter.Style+"/calendar/");
//-->
</script>
<div id="${HtmlParam.fdkey}_Top" name="${HtmlParam.fdkey}_Top">
	<table id="calendar_head" border="0" cellpadding="0" cellspacing="0" width="100%">	
	 	<tr>
	 		<td id="${HtmlParam.fdkey}_byDay" nowrap  class="calviewbtn" onClick="CalendarView_Info['${JsParam.fdkey}'].goto(null,CALENDARVIEW_TYPE_DAY)" align=center ><bean:message key="calendar.byDay"/></td>
	 		<td id="${HtmlParam.fdkey}_byWeek" nowrap class="calviewbtn" onClick="CalendarView_Info['${JsParam.fdkey}'].goto(null,CALENDARVIEW_TYPE_WEEK_TWO)" align=center ><bean:message key="calendar.byWeek"/></td>
	 		<td id="${HtmlParam.fdkey}_byMonth" nowrap class="calviewbtn" onClick="CalendarView_Info['${JsParam.fdkey}'].goto(null,CALENDARVIEW_TYPE_MONTH)" align=center ><bean:message key="calendar.byMonth"/></td>	 		
			<td align="right">&nbsp;</td>
		</tr>
	</table>
	<table class="calviewmenubar">
		<tr>
			<td nowrap>
				<a id="Top_Head" href="#" onclick="CalendarView_Info['${JsParam.fdkey}'].head();return false;"><bean:message key="page.first"/></a>&nbsp;&nbsp;
				<a id="Top_Head" href="#" onclick="CalendarView_Info['${JsParam.fdkey}'].prev();return false;"><bean:message key="page.prev"/></a>&nbsp;&nbsp;
				<a id="Top_Head" href="#" onclick="CalendarView_Info['${JsParam.fdkey}'].next();return false;"><bean:message key="page.next"/></a>&nbsp;&nbsp;
				<a id="Top_Head" href="#" onclick="CalendarView_Info['${JsParam.fdkey}'].last();return false;"><bean:message key="page.last"/></a>&nbsp;&nbsp;
				<a id="Top_Head" href="#" onclick="CalendarView_Info['${JsParam.fdkey}'].refresh();return false;"><bean:message key="button.refresh"/></a>&nbsp;&nbsp;
				<a id="Top_Change_${HtmlParam.fdkey}" href="" onclick="CalendarView_Info['${JsParam.fdkey}'].changeWeek();return false;"><bean:message key="calendar.switch"/></a>&nbsp;&nbsp;
				<a id="Top_Jump_${HtmlParam.fdkey}" href="#" onclick="CalendarView_Info['${JsParam.fdkey}'].selectDate();return false;"><bean:message key="page.changeTo"/></a>&nbsp;&nbsp;
			</td>
			<td nowrap align="right">
				<a id="Top_Choice_${HtmlParam.fdkey}" href="" onclick="CalendarView_Info['${JsParam.fdkey}'].showChioceMenu();return false;"><bean:message key="calendar.choice"/></a>&nbsp;&nbsp;
				&nbsp;&nbsp;
			</td>
		</tr>
		<tr>
			<td align="center" colspan="2">
				<table border="0" cellpadding="0" cellspacing="0" align="center">
					<td><a href="#" onclick="CalendarView_Info['${JsParam.fdkey}'].prev();return false;"><img src="${KMSS_Parameter_StylePath}calendar/prev.gif" border="0"></a></td>
					<td align=center id="${HtmlParam.fdkey}_Title" class="calviewmenuTitle"></td>
					<td><a href="#" onclick="CalendarView_Info['${JsParam.fdkey}'].next();return false;"><img src="${KMSS_Parameter_StylePath}calendar/next.gif" border="0"></a></td>
				</table>
			</td>
		</tr>
	</table>
</div>
	<div id="${HtmlParam.fdkey}_Body" name="${HtmlParam.fdkey}_Body">

	</div>
<script>
	if(CalendarMsg_Info.length==0) {
		CalendarMsg_Info["weeks"] = [<bean:message key="date.weekDayNames"/>]
		CalendarMsg_Info["months"] = [<bean:message key="calendar.shortMonthNames"/>]
		CalendarMsg_Info["dayname"] = "<kmss:message key="calendar.day.name"/>"
		CalendarMsg_Info["year"] = "<bean:message key="resource.period.type.year.name"/>"
		CalendarMsg_Info["begindate"] = "<bean:message key="calendar.begindate"/>"
		CalendarMsg_Info["enddate"] = "<bean:message key="calendar.enddate"/>"
		CalendarMsg_Info["subject"] = "<bean:message key="calendar.subject"/>"
		CalendarMsg_Info["applyDept"] = "<bean:message key="calendar.applyDept"/>"
		CalendarMsg_Info["prevWeek"] = "<kmss:message key="calendar.prevWeek"/>"
		CalendarMsg_Info["nextWeek"] = "<kmss:message key="calendar.nextWeek"/>"
		CalendarMsg_Info["thisWeek"] = "<kmss:message key="calendar.thisWeek"/>"
		CalendarMsg_Info["prevDay"] = "<kmss:message key="calendar.prevDay"/>"
		CalendarMsg_Info["nextDay"] = "<kmss:message key="calendar.nextDay"/>"
		CalendarMsg_Info["thisDay"] = "<kmss:message key="calendar.thisDay"/>"
	}
	var ${JsParam.fdkey} = new CalendarView("${JsParam.fdkey}","${JsParam.fdkey}_Body");
	<c:if test="${not empty param.beanName}">
		${JsParam.fdkey}.beanName = "${JsParam.beanName}";
	</c:if>
	<c:if test="${not empty param.beanURL}">
		${JsParam.fdkey}.beanURL = "${JsParam.beanURL}";
	</c:if>	
	<c:if test="${not empty param.listType}">
		${JsParam.fdkey}.listType = "${JsParam.listType}";
	</c:if>	
	<c:if test="${not empty param.clickURL}">
		${JsParam.fdkey}.clickURL = "${JsParam.clickURL}";
	</c:if>
	<c:if test="${not empty param.startDate}">
		${JsParam.fdkey}.startDate = "${JsParam.startDate}";
	</c:if>
	<c:if test="${not empty param.onListTypeChange}">
		${JsParam.fdkey}.onListTypeChange = ${JsParam.onListTypeChange};
	</c:if>
</script>