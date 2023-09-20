<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ page import="java.util.Calendar,java.util.List,java.util.ArrayList,java.util.Date,java.util.Calendar" %>
<%
	Calendar cal = Calendar.getInstance();
	// 时间
	cal.setTime(new Date());
	int startYear = cal.get(Calendar.YEAR) - 10;
	int endYear = cal.get(Calendar.YEAR) + 10;
	int curYear=cal.get(Calendar.YEAR);
	int curMonth=cal.get(Calendar.MONTH)+1;
	request.setAttribute("curYear", curYear);
	request.setAttribute("curMonth", curMonth);
%>
{$
	<li>
		<span class="criteria-input-text">
			  <SELECT class="date-select" id="startYear" style="width: 70px;height: 20px;cursor:pointer;">
			  	<c:forEach begin="<%=startYear %>" end="<%=endYear %>" var="year">
			  		<c:choose>
			  			<c:when test="${year eq curYear }">
			  			<OPTION value="${year }" selected="selected">${year }</OPTION>
			  			</c:when>
			  			<c:otherwise>
			  			<OPTION value="${year }">${year }</OPTION>
			  			</c:otherwise>
			  		</c:choose>
				</c:forEach>
              </SELECT>
              <span calss="text" style="color:#333;cursor:default;">${lfn:message('sys-ui:ui.calendar.mode.year')}</span>
 			<SELECT class="date-select" id="startMonth" style="width: 70px;height: 20px;cursor:pointer;">
                <c:forEach begin="1" end="12" var="month">
			  		<c:choose>
			  			<c:when test="${month eq curMonth }">
			  				<c:choose>
					  			<c:when test="${month<10 }">
					  			<OPTION value="0${month }" selected="selected">${month }</OPTION>
					  			</c:when>
					  			<c:otherwise>
					  				<OPTION value="${month }" selected="selected">${month }</OPTION>
					  			</c:otherwise>
					  		</c:choose>
			  			</c:when>
			  			<c:otherwise>
			  				<c:choose>
					  			<c:when test="${month<10 }">
					  			<OPTION value="0${month }">${month }</OPTION>
					  			</c:when>
					  			<c:otherwise>
					  			<OPTION value="${month }">${month }</OPTION>
					  			</c:otherwise>
					  		</c:choose>
			  			</c:otherwise>
			  		</c:choose>
				</c:forEach>
              </SELECT>
             <span calss="text" style="color:#333;cursor:default;">${lfn:message('sys-ui:ui.calendar.mode.month')}</span>
		</span>
		<input type="button" class="commit-action" value="${lfn:message('button.ok')}" />
		
		<div class="lui_criteria_number_validate_container" >
			<span class="lui_criteria_number_validate" style="width: 500px">
				<div class="lui_icon_s lui_icon_s_icon_validator" ></div>
				<div class="text" style="width: 450px"></div>
			</span>
		</div>
	
	</li>
$}