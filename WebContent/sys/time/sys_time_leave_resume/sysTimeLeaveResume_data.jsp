<%@ page language="java" contentType="text/json; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page import="com.landray.kmss.sys.time.model.SysTimeLeaveResume,com.landray.kmss.sys.time.model.SysTimeLeaveConfig,com.landray.kmss.sys.time.util.SysTimeUtil"%>
<list:data>
	<list:data-columns var="sysTimeLeaveResume" list="${queryPage.list}" varIndex="status">

		<list:data-column property="fdId">
		</list:data-column>
		<list:data-column col="index" >
		 	${status+1}
		</list:data-column>
		
		<list:data-column col="fdPerson.fdName" escape="false" title="${ lfn:message('sys-time:sysTimeLeaveResume.fdPerson') }">
			<c:out value="${sysTimeLeaveResume.fdPerson.fdName}"></c:out>
		</list:data-column>
		<list:data-column col="fdLeaveName" escape="false" title="${ lfn:message('sys-time:sysTimeLeaveDetail.fdLeaveName') }">
			${sysTimeLeaveResume.fdLeaveDetail.fdLeaveName }
		</list:data-column>
		<list:data-column col="fdStartTime" escape="false" title="${ lfn:message('sys-time:sysTimeLeaveResume.fdStartTime') }">
			<c:if test="${sysTimeLeaveResume.fdLeaveDetail.fdStatType=='3'}">
				<kmss:showDate value="${sysTimeLeaveResume.fdStartTime}" type="datetime"></kmss:showDate>
			</c:if>
			<c:if test="${sysTimeLeaveResume.fdLeaveDetail.fdStatType!='3'}">
				<kmss:showDate value="${sysTimeLeaveResume.fdStartTime}" type="date"></kmss:showDate>
				<c:if test="${sysTimeLeaveResume.fdLeaveDetail.fdStatType=='2' }">
					<c:if test="${sysTimeLeaveResume.fdStartNoon eq '1' }">
						${ lfn:message('sys-time:sysTimeLeaveDetail.morning') }
					</c:if>
					<c:if test="${sysTimeLeaveResume.fdStartNoon eq '2' }">
						${ lfn:message('sys-time:sysTimeLeaveDetail.afterNoon') }
					</c:if>
				</c:if>
			</c:if>
		</list:data-column>
		<list:data-column col="fdEndTime" escape="false" title="${ lfn:message('sys-time:sysTimeLeaveResume.fdEndTime') }">
			<c:if test="${sysTimeLeaveResume.fdLeaveDetail.fdStatType=='3'}">
				<kmss:showDate value="${sysTimeLeaveResume.fdEndTime}" type="datetime"></kmss:showDate>
			</c:if>
			<c:if test="${sysTimeLeaveResume.fdLeaveDetail.fdStatType!='3'}">
				<kmss:showDate value="${sysTimeLeaveResume.fdEndTime}" type="date"></kmss:showDate>
				<c:if test="${sysTimeLeaveResume.fdLeaveDetail.fdStatType=='2' }">
					<c:if test="${sysTimeLeaveResume.fdEndNoon eq '1' }">
						${ lfn:message('sys-time:sysTimeLeaveDetail.morning') }
					</c:if>
					<c:if test="${sysTimeLeaveResume.fdEndNoon eq '2' }">
						${ lfn:message('sys-time:sysTimeLeaveDetail.afterNoon') }
					</c:if>
				</c:if>
			</c:if>
		</list:data-column>
		<list:data-column col="fdLeaveTime" escape="false" title="${ lfn:message('sys-time:sysTimeLeaveResume.fdLeaveTime') }(${ lfn:message('sys-time:sysTimeLeaveDetail.fdLeaveTime.day') })">
			<fmt:formatNumber value="${sysTimeLeaveResume.fdLeaveTime}" pattern="#.###"/>${ lfn:message('sys-time:sysTimeLeaveDetail.fdLeaveTime.day') }
		</list:data-column>
		<list:data-column col="fdOprType" escape="false" title="${ lfn:message('sys-time:sysTimeLeaveDetail.source') }">
			<c:if test="${sysTimeLeaveResume.fdOprType eq 1 }">
				${ lfn:message('sys-time:sysTimeLeaveResume.fdOprType.review') }
			</c:if>
			<c:if test="${sysTimeLeaveResume.fdOprType eq 2 }">
				${ lfn:message('sys-time:sysTimeLeaveResume.fdOprType.manual') }
			</c:if>
		</list:data-column>
		<list:data-column col="fdOprStatus" escape="false" title="${ lfn:message('sys-time:sysTimeLeaveResume.fdOprStatus') }">
			<c:if test="${sysTimeLeaveResume.fdOprStatus eq 0 || sysTimeLeaveResume.fdOprStatus eq null}">
				${ lfn:message('sys-time:sysTimeLeaveResume.fdOprStatus.no') }
			</c:if>
			<c:if test="${sysTimeLeaveResume.fdOprStatus eq 1}">
				${ lfn:message('sys-time:sysTimeLeaveResume.fdOprStatus.success') }
			</c:if>
			<c:if test="${sysTimeLeaveResume.fdOprStatus eq 2}">
				<span style="color: red;">
					${ lfn:message('sys-time:sysTimeLeaveResume.fdOprStatus.fail') }<br/>
					${sysTimeLeaveResume.fdOprDesc }
				</span>
			</c:if>
		</list:data-column>
		<list:data-column col="fdReview" escape="false" title="${ lfn:message('sys-time:sysTimeLeaveResume.fdReview') }" style="min-width: 200px;">
			<c:if test="${not empty sysTimeLeaveResume.fdReviewId}">
			<div class="conf_btn_edit">
				<a class="btn_txt" href="${LUI_ContextPath}/km/review/km_review_main/kmReviewMain.do?method=view&fdId=${sysTimeLeaveResume.fdReviewId}" target="_blank">${sysTimeLeaveResume.fdReviewName}</a>
			</div>
			</c:if>
		</list:data-column>
		<list:data-column col="operation" escape="false" title="${ lfn:message('list.operation') }" style="min-width: 100px;">
			<div class="conf_show_more_w">
				<div class="conf_btn_edit">
					<kmss:ifModuleExist path="/sys/attend">
						<c:if test="${sysTimeLeaveResume.fdOprStatus eq 1}">
						<kmss:auth requestURL="/sys/time/sys_time_leave_resume/sysTimeLeaveResume.do?method=updateAttend">
							<a class="btn_txt" href="javascript:updateResumeAttend('${sysTimeLeaveResume.fdId}');">${lfn:message('sys-time:sysTimeLeaveDetail.updateAttend')}</a>
						</kmss:auth>
						</c:if>
					</kmss:ifModuleExist>
				</div>
			</div>
		</list:data-column>
	</list:data-columns>
	<list:data-paging currentPage="${queryPage.pageno }" pageSize="${queryPage.rowsize }" totalSize="${queryPage.totalrows }">
	</list:data-paging>
</list:data>