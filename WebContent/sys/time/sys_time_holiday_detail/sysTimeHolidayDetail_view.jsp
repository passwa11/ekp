<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/resource/jsp/common.jsp"%>
<table width="100%" class="tb_normal" id="TABLE_DocList">
<!-- 标题行 -->
	<tr class="td_normal_title" style="text-align:center;">
		<td class="td_normal_title" width="12%"><bean:message bundle="sys-time" key="sysTimeHoliday.holiday"/></td>
		<td class="td_normal_title" width="30%"><bean:message bundle="sys-time" key="sysTimeHoliday.holiday.day"/></td>
		<td class="td_normal_title" width="25%"><bean:message bundle="sys-time" key="sysTimeHoliday.mend.holiday.day"/></td>
		<td class="td_normal_title" width="25%"><bean:message bundle="sys-time" key="sysTimeHoliday.mend.work.day"/></td>
	</tr>
	 <!-- 内容行 -->
	<c:forEach items="${sysTimeHolidayForm.fdHolidayDetailList}" var="fdHolidayDetailList" varStatus="vstatus">
		<tr KMSS_IsContentRow="1" name="${fdHolidayDetailList.fdYear }" nm="dls">
			<input type="hidden" name="fdHolidayDetailList[${vstatus.index}].fdId" value="${fdHolidayDetailList.fdId }"/>
			<td align="center">
				<xform:text property="fdHolidayDetailList[${vstatus.index}].fdName" style="width:85%" required="true"/>
			</td>
			<td align="center">
				<xform:datetime property="fdHolidayDetailList[${vstatus.index}].fdStartDay" dateTimeType="date"/>
				—
				<xform:datetime property="fdHolidayDetailList[${vstatus.index}].fdEndDay" dateTimeType="date"/>
			</td>
			<td align="center">
				<xform:text property="fdHolidayDetailList[${vstatus.index}].fdPatchHolidayDay" />
			</td>
			<td align="center">
				<xform:text property="fdHolidayDetailList[${vstatus.index}].fdPatchDay" />
			</td>
	</c:forEach>
</table>