<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@page import="com.landray.kmss.sys.authorization.constant.ISysAuthConstant"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<table class="tb_normal" width=100%>
	<tr>
		<%-- 人员 --%>
		<td width="15%" class="td_normal_title">
			${ lfn:message('sys-time:sysTimeLeaveAmount.fdPerson') }
		</td>
		<td width="35%">
			<c:out value="${sysTimeLeaveAmountForm.fdPersonName }" />
			<html:hidden property="fdPersonId" />
			<html:hidden property="fdPersonName" />
		</td>
		<%-- 年份 --%>
		<td width="15%" class="td_normal_title">
			${ lfn:message('sys-time:sysTimeLeaveAmount.fdYear') }
		</td>
		<td width="35%">
			${sysTimeLeaveAmountForm.fdYear }
			<html:hidden property="fdYear" />
		</td>
	</tr>
	<%-- 所属场所 --%>
    <%if(ISysAuthConstant.IS_AREA_ENABLED){ %>
		<tr>
			<c:import url="/sys/authorization/sys_auth_area/sysAuthArea_field_single.jsp" charEncoding="UTF-8">
                <c:param name="id" value="${sysTimeLeaveAmountForm.authAreaId}"/>
            </c:import>
		</tr>
	<%} %>
	<c:forEach items="${sysTimeLeaveAmountForm.fdAmountItems }" var="amountItem" varStatus="vstatus">
		<html:hidden property="fdAmountItems[${vstatus.index }].fdId" value="${amountItem.fdId }" />
		<html:hidden property="fdAmountItems[${vstatus.index }].fdAmountId" value="${amountItem.fdAmountId }"/>
		<html:hidden property="fdAmountItems[${vstatus.index }].fdIsAccumulate" value="${amountItem.fdIsAccumulate }" />
		<html:hidden property="fdAmountItems[${vstatus.index }].fdIsAuto" value="${amountItem.fdIsAuto }" />
		<html:hidden property="fdAmountItems[${vstatus.index }].fdLeaveName" value="${amountItem.fdLeaveName }" />
		<html:hidden property="fdAmountItems[${vstatus.index }].fdLeaveType" value="${amountItem.fdLeaveType }" />
		<html:hidden property="fdAmountItems[${vstatus.index }].fdIsAvail" value="${amountItem.fdIsAvail }" />
		<html:hidden property="fdAmountItems[${vstatus.index }].fdIsLastAvail" value="${amountItem.fdIsLastAvail }" />
		
		<tr>
			<%-- 总天数 --%>
			<td width="15%" class="td_normal_title">
				<c:out value="${amountItem.fdLeaveName }"/>${ lfn:message('sys-time:sysTimeLeaveAmount.fdTotalDay') }
			</td>
			<td width="35%">
				<c:if test="${not empty amountItem.fdTotalDay }">
					<fmt:formatNumber value="${amountItem.fdTotalDay }" pattern="#.###" var="_fdTotalDay" />
				</c:if>
				<c:if test="${empty amountItem.fdTotalDay }">
					<c:set var="_fdTotalDay" value="0" />
				</c:if>
				<c:if test="${amountItem.fdIsAuto }">
					${_fdTotalDay }${ lfn:message('sys-time:sysTimeLeaveAmount.day') }（${ lfn:message('sys-time:sysTimeLeaveAmount.auto.release') }）
					<html:hidden property="fdAmountItems[${vstatus.index }].fdTotalDay" value="${amountItem.fdTotalDay eq null ? 0 : amountItem.fdTotalDay}" />
				</c:if>
				<c:if test="${amountItem.fdIsAuto eq null || !amountItem.fdIsAuto}">
					<xform:text property="fdAmountItems[${vstatus.index }].fdTotalDay"
								value="${_fdTotalDay }"
								style="width: 95%"
								required="true"
								subject="${ lfn:message('sys-time:sysTimeLeaveAmount.fdTotalDay') }"
								validators="number min(0) max(365) largerThanUsed" onValueChange="onChangeTotal"></xform:text>
				</c:if>
			</td>
			<%-- 失效日期 --%>
			<td width="15%" class="td_normal_title">
				<c:out value="${amountItem.fdLeaveName }"/>${ lfn:message('sys-time:sysTimeLeaveAmount.fdValidDate') }
			</td>
			<td width="35%">
				<c:if test="${amountItem.fdIsAuto }">
					<html:hidden property="fdAmountItems[${vstatus.index }].fdValidDate" value="${amountItem.fdValidDate }"/>
					<xform:datetime property="fdAmountItems[${vstatus.index }].fdValidDate" value="${amountItem.fdValidDate }" dateTimeType="date" showStatus="view"></xform:datetime>
				</c:if>
				<c:if test="${amountItem.fdIsAuto eq null || !amountItem.fdIsAuto}">
					<xform:datetime property="fdAmountItems[${vstatus.index }].fdValidDate" value="${amountItem.fdValidDate }" dateTimeType="date" style="width: 95%" required="true" subject="${ lfn:message('sys-time:sysTimeLeaveAmount.fdValidDate') }" validators="validYearDate validDateRange" onValueChange="onChangeVDate"></xform:datetime>
				</c:if>
			</td>
			<html:hidden property="fdAmountItems[${vstatus.index }].fdRestDay" value="${amountItem.fdRestDay }"/>
			<html:hidden property="fdAmountItems[${vstatus.index }].fdUsedDay" value="${amountItem.fdUsedDay }"/>
		</tr>
		<tr>
			<%-- 上周期剩余 --%>
			<td width="15%" class="td_normal_title">
				${ lfn:message('sys-time:sysTimeLeaveAmount.lastPeriod') }${ lfn:message('sys-time:sysTimeLeaveAmount.fdRestDay') }
			</td>
			<td width="35%">
				<c:if test="${not empty amountItem.fdLastRestDay}">
					<fmt:formatNumber value="${amountItem.fdLastRestDay }" pattern="#.###"/>${ lfn:message('sys-time:sysTimeLeaveAmount.day') }
					<c:if test="${!amountItem.fdIsLastAvail }">
						<span style="color: red;">（${ lfn:message('sys-time:sysTimeLeaveAmount.notAvailable') }）</span>
					</c:if>
				</c:if>
				<html:hidden property="fdAmountItems[${vstatus.index }].fdLastRestDay" value="${amountItem.fdLastRestDay }"/>
			</td>
			<%-- 上周期失效日期 --%>
			<td width="15%" class="td_normal_title">
				${ lfn:message('sys-time:sysTimeLeaveAmount.lastPeriod') }${ lfn:message('sys-time:sysTimeLeaveAmount.fdValidDate') }
			</td>
			<td width="35%">
				<c:if test="${not empty amountItem.fdLastValidDate}">
					<xform:datetime property="fdAmountItems[${vstatus.index }].fdLastValidDate" value="${amountItem.fdLastValidDate }" dateTimeType="date" showStatus="view"></xform:datetime>
				</c:if>
				<html:hidden property="fdAmountItems[${vstatus.index }].fdLastValidDate" value="${amountItem.fdLastValidDate }"/>
			</td>
			<html:hidden property="fdAmountItems[${vstatus.index }].fdLastTotalDay" value="${amountItem.fdLastTotalDay }"/>
			<html:hidden property="fdAmountItems[${vstatus.index }].fdLastUsedDay" value="${amountItem.fdLastUsedDay }"/>
		</tr>
	</c:forEach>
</table>