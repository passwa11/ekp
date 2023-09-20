<%@ page language="java" contentType="text/json; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<list:data>
	<list:data-columns var="sysTimePatchwork" list="${queryPage.list }">
		<list:data-column property="fdId">
		</list:data-column>

		<list:data-column property="fdName" title="${ lfn:message('sys-time:sysTimePatchwork.fdName') }">
		</list:data-column>
		
		<list:data-column property="fdPatchWorkColor">
		</list:data-column>
		
		<list:data-column col="patchWorkColor" title="颜色" escape="false">
			<c:choose>
				<c:when test="${sysTimePatchwork.timeType == '1'}">
					<span style="color:white;display:inline-block;width:64px;height:20px;line-height:20px;text-align:center;background:${sysTimePatchwork.sysTimeCommonTime.fdWorkTimeColor };">
					</span>
				</c:when>
				<c:otherwise>
					<span style="color:white;display:inline-block;width:64px;height:20px;line-height:20px;text-align:center;background:${sysTimePatchwork.fdPatchWorkColor };">
					</span>
				</c:otherwise>
			</c:choose>
		</list:data-column>

		<list:data-column col="hbmStartTime" title="${ lfn:message('sys-time:sysTimePatchwork.time') }"  escape="false">
			<kmss:showDate value="${sysTimePatchwork.fdStartTime}" type="date"/>
			<bean:message  bundle="sys-time" key="sysTimePatchwork.end" />
			<kmss:showDate value="${sysTimePatchwork.fdEndTime}" type="date"/>				
		</list:data-column>

		<list:data-column property="docCreator.fdName" title="${ lfn:message('sys-time:sysTimePatchwork.docCreatorId') }">
		</list:data-column>

		<list:data-column col="docCreateTime" title="${ lfn:message('sys-time:sysTimePatchwork.docCreateTime') }" escape="false">
			<kmss:showDate value="${sysTimePatchwork.docCreateTime}" type="datetime"/>
		</list:data-column>
	</list:data-columns>

	<list:data-paging currentPage="${queryPage.pageno }"
		pageSize="${queryPage.rowsize }" totalSize="${queryPage.totalrows }">
	</list:data-paging>
</list:data>