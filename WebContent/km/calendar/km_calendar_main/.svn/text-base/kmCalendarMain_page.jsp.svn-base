<%@ page language="java" contentType="text/json; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ page import="java.util.regex.Matcher"%>
<%@ page import="java.util.regex.Pattern"%>
<%@ page import="com.landray.kmss.util.StringUtil"%>
<%@ page import="com.landray.kmss.km.calendar.model.KmCalendarMain"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<list:data>
	<list:data-columns var="kmCalendarMain" list="${queryPage.list }">
		<list:data-column property="fdId"></list:data-column>
		<!-- 日程内容 -->		
		<list:data-column property="docSubject" title="${ lfn:message('km-calendar:kmCalendarMain.docSubject') }" headerStyle="text-align:left;">
		</list:data-column>
		<!-- 时间  -->
		<list:data-column col="date" title="${ lfn:message('km-calendar:kmCalendarMain.docTime') }" headerStyle="width:350px;">
			<c:if test="${kmCalendarMain.fdIsAlldayevent == true }">
				<kmss:showDate value="${kmCalendarMain.docStartTime}" type="date" /> 
				~ <kmss:showDate value="${kmCalendarMain.docFinishTime}" type="date" />
			</c:if>
			<c:if test="${kmCalendarMain.fdIsAlldayevent == false }">
				<kmss:showDate value="${kmCalendarMain.docStartTime}" type="datetime" /> 
				~ <kmss:showDate value="${kmCalendarMain.docFinishTime}" type="datetime" />
			</c:if>
		</list:data-column>
		<list:data-column col="allday" title="${ lfn:message('km-calendar:kmCalendarMain.fdIsallDay') }" headerClass="width80">
			<c:if test="${kmCalendarMain.fdIsAlldayevent == true }">
				<c:out value="${lfn:message('km-calendar:kmCalendarMain.allDay') }"></c:out>
			</c:if>
			<c:if test="${kmCalendarMain.fdIsAlldayevent == false }">
				非全天
			</c:if>
		</list:data-column>
		<list:data-column col="recurrenceType" title="${ lfn:message('km-calendar:kmCalendarMain.fdRecurrenceType') }" headerClass="width100">
			<%
				String recurrenceType = "NO";
				if(pageContext.getAttribute("kmCalendarMain") != null){
					KmCalendarMain kmCalendarMain = (KmCalendarMain)pageContext.getAttribute("kmCalendarMain");
					if(StringUtil.isNotNull(kmCalendarMain.getFdRecurrenceStr())){
						String pattern = "FREQ=([^;]*).*";
						Pattern r = Pattern.compile(pattern);
						Matcher m = r.matcher(kmCalendarMain.getFdRecurrenceStr());
					  	if (m.find()){
					  		recurrenceType = m.group(1);  
					    }
					}
				}
				request.setAttribute("recurrenceType", recurrenceType);
			%>
			<c:choose>
				<c:when test="${recurrenceType == 'NO' }">
					<c:out value="${lfn:message('km-calendar:recurrence.freq.no') }"></c:out>
				</c:when>
				<c:when test="${recurrenceType == 'DAILY' }">
					<c:out value="${lfn:message('km-calendar:recurrence.freq.daily') }"></c:out>
				</c:when>
				<c:when test="${recurrenceType == 'WEEKLY' }">
					<c:out value="${lfn:message('km-calendar:recurrence.freq.weekly') }"></c:out>
				</c:when>
				<c:when test="${recurrenceType == 'MONTHLY' }">
					<c:out value="${lfn:message('km-calendar:recurrence.freq.monthly') }"></c:out>
				</c:when>
				<c:when test="${recurrenceType == 'YEARLY' }">
					<c:out value="${lfn:message('km-calendar:recurrence.freq.yearly') }"></c:out>
				</c:when>
			</c:choose>
		</list:data-column>
		<!-- 标签 -->
		<list:data-column col="labelName" title="${ lfn:message('km-calendar:kmCalendarMain.docLabel') }" headerClass="width120">
			<c:if test="${not empty kmCalendarMain.docLabel}">
				<c:out value="${kmCalendarMain.docLabel.fdName }"></c:out>
			</c:if>
			<c:if test="${ empty kmCalendarMain.docLabel}">
				<c:out value="${lfn:message('km-calendar:kmCalendar.nav.title') }"></c:out>
			</c:if>
		</list:data-column>
	</list:data-columns>
	<list:data-paging currentPage="${queryPage.pageno }"
		pageSize="${queryPage.rowsize }" totalSize="${queryPage.totalrows }">
	</list:data-paging>
</list:data>