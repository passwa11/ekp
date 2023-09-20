<%@ page language="java" contentType="text/json; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<list:data>
	<list:data-columns var="sysTimeWork" list="${queryPage.list }">
		<list:data-column property="fdId">
		</list:data-column>
		<list:data-column col="hbmStartTime" title="${ lfn:message('sys-time:sysTimeWork.validTime') }"  escape="false">
			<bean:message  bundle="sys-time" key="sysTimeWork.validTime.start"/>
					<kmss:showDate  value="${sysTimeWork.fdStartTime}" type="date"/>
					<bean:message  bundle="sys-time" key="sysTimeWork.validTime.end.middle"/>
					<c:if test="${sysTimeWork.fdEndTime != null || sysTimeWork.fdEndTime != ''}">
						<kmss:showDate value="${sysTimeWork.fdEndTime}" type="date"/>
					</c:if>
					<c:if test="${sysTimeWork.fdEndTime == null || sysTimeWork.fdEndTime == ''}">
						<bean:message  bundle="sys-time" key="sysTimeWork.validTime.end.list"/>
					</c:if>	
		</list:data-column>
		
		<list:data-column property="fdTimeWorkColor"></list:data-column>
		
		<list:data-column col="timeWorkColor" title="颜色" escape="false">
			<c:choose>
				<c:when test="${sysTimeWork.timeType == '1'}">
					<span style="color:white;display:inline-block;width:64px;height:20px;line-height:20px;text-align:center;background:${sysTimeWork.sysTimeCommonTime.fdWorkTimeColor };">
					</span>
				</c:when>
				<c:otherwise>
					<span style="color:white;display:inline-block;width:64px;height:20px;line-height:20px;text-align:center;background:${sysTimeWork.fdTimeWorkColor };">
					</span>
				</c:otherwise>
			</c:choose>
		</list:data-column>
	
		<list:data-column col="fdWeekStartTime" title="${ lfn:message('sys-time:sysTimeWork.week') }"  escape="false">
			<bean:message  bundle="sys-time" key="sysTimeWork.week.start"/>
					<sunbor:enumsShow value="${sysTimeWork.fdWeekStartTime}" enumsType="common_week_type"/>
					<bean:message  bundle="sys-time" key="sysTimeWork.week.end"/>
					<sunbor:enumsShow value="${sysTimeWork.fdWeekEndTime}" enumsType="common_week_type"/>
		</list:data-column>
		<list:data-column col="timeType" title="${ lfn:message('sys-time:sysTimeWork.timeType') }"  escape="false">
					<c:if test="${sysTimeWork.timeType==1}">
						<bean:message  bundle="sys-time" key="sysTimeWork.common" />
					</c:if>
					<c:if test="${sysTimeWork.timeType ==2}">
						<bean:message bundle="sys-time" key="sysTimeWork.custom"/>
					</c:if>	
		</list:data-column>
		<list:data-column  property="sysTimeCommonTime.fdName" title="${ lfn:message('sys-time:sysTimeWork.timeName') }"  escape="false">
					
		</list:data-column>
		
		<list:data-column property="docCreator.fdName" title="${ lfn:message('sys-time:sysTimeWork.docCreatorId') }">
		</list:data-column>
		
		<list:data-column col="docCreateTime" title="${ lfn:message('sys-time:sysTimeWork.docCreateTime') }" escape="false">
			<kmss:showDate value="${sysTimeWork.docCreateTime}" type="datetime"/>
		</list:data-column>
	</list:data-columns>

	<list:data-paging currentPage="${queryPage.pageno }"
		pageSize="${queryPage.rowsize }" totalSize="${queryPage.totalrows }">
	</list:data-paging>
</list:data>