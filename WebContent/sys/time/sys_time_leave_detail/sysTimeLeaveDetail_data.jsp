<%@ page language="java" contentType="text/json; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page import="com.landray.kmss.sys.time.model.SysTimeLeaveDetail,com.landray.kmss.sys.time.util.SysTimeUtil"%>
<list:data>
	<list:data-columns var="sysTimeLeaveDetail" list="${queryPage.list}" varIndex="status">

		<list:data-column property="fdId">
		</list:data-column>
		<list:data-column col="index" >
		 	${status+1}
		</list:data-column>

		<list:data-column col="fdPerson.fdParentsName" escape="false" title="${ lfn:message('sys-time:sysTimeLeaveAmount.fdDept') }">
			<c:out value="${sysTimeLeaveDetail.fdPerson.fdParentsName}"/>
		</list:data-column>
		<list:data-column col="fdPerson.fdName" escape="false" title="${ lfn:message('sys-time:sysTimeLeaveDetail.fdPersonName') }">
			<c:out value="${sysTimeLeaveDetail.fdPerson.fdName}"/>
		</list:data-column>
		<list:data-column col="fdPerson.fdLoginName" escape="false" style="" title="${ lfn:message('sys-time:sysTimeLeaveAmount.loginName') }">
			<c:out value="${sysTimeLeaveDetail.fdPerson.fdLoginName}"></c:out>
		</list:data-column>
		<c:set var="leaveTypeTxt" value="${ lfn:message('sys-time:sysTimeLeaveDetail.fdLeaveName') }"></c:set>
		<c:set var="startTimeTxt" value="${ lfn:message('sys-time:sysTimeLeaveDetail.fdStartTime') }"></c:set>
		<c:set var="endTimeTxt" value="${ lfn:message('sys-time:sysTimeLeaveDetail.fdEndTime') }"></c:set>
		<c:set var="fdOprDescTxt" value="${ lfn:message('sys-time:sysTimeLeaveDetail.fdOprDesc') }"></c:set>
		<c:if test="${sysTimeLeaveDetail.fdType=='2'}">
			<c:set var="leaveTypeTxt" value="${ lfn:message('sys-time:sysTimeLeaveRule.leaveType') }"></c:set>
			<c:set var="startTimeTxt" value="${ lfn:message('sys-time:sysTimeLeaveDetail.overtime.startTime') }"></c:set>
			<c:set var="endTimeTxt" value="${ lfn:message('sys-time:sysTimeLeaveDetail.overtime.endTime') }"></c:set>
			<c:set var="fdOprDescTxt" value="${ lfn:message('sys-time:sysTimeLeaveDetail.overtime.fdOprDesc') }"></c:set>
		</c:if>
		<list:data-column col="fdLeaveName" escape="false" title="${leaveTypeTxt }">
			<c:out value="${sysTimeLeaveDetail.fdLeaveName}"></c:out>
		</list:data-column>
		<list:data-column col="fdStartTime" escape="false" title="${startTimeTxt }">
			<c:if test="${sysTimeLeaveDetail.fdStatType=='3'}">
				<kmss:showDate value="${sysTimeLeaveDetail.fdStartTime}" type="datetime"></kmss:showDate>
			</c:if>
			<c:if test="${sysTimeLeaveDetail.fdStatType!='3'}">
				<kmss:showDate value="${sysTimeLeaveDetail.fdStartTime}" type="date"></kmss:showDate>
				<c:if test="${sysTimeLeaveDetail.fdStatType=='2' }">
					<c:if test="${sysTimeLeaveDetail.fdStartNoon eq '1' }">
						${ lfn:message('sys-time:sysTimeLeaveDetail.morning') }
					</c:if>
					<c:if test="${sysTimeLeaveDetail.fdStartNoon eq '2' }">
						${ lfn:message('sys-time:sysTimeLeaveDetail.afterNoon') }
					</c:if>
				</c:if>
			</c:if>
			
		</list:data-column>
		<list:data-column col="fdEndTime" escape="false" title="${endTimeTxt }">
			<c:if test="${sysTimeLeaveDetail.fdStatType=='3'}">
				<kmss:showDate value="${sysTimeLeaveDetail.fdEndTime}" type="datetime"></kmss:showDate>
			</c:if>
			<c:if test="${sysTimeLeaveDetail.fdStatType !='3'}">
				<kmss:showDate value="${sysTimeLeaveDetail.fdEndTime}" type="date"></kmss:showDate>
				<c:if test="${sysTimeLeaveDetail.fdStatType=='2' }">
					<c:if test="${sysTimeLeaveDetail.fdEndNoon eq '1' }">
						${ lfn:message('sys-time:sysTimeLeaveDetail.morning') }
					</c:if>
					<c:if test="${sysTimeLeaveDetail.fdEndNoon eq '2' }">
						${ lfn:message('sys-time:sysTimeLeaveDetail.afterNoon') }
					</c:if>
				</c:if>
			</c:if>
		</list:data-column>

			<list:data-column col="fdLeaveTime" escape="false" title="${ lfn:message('sys-time:sysTimeLeaveDetail.fdLeaveTime') }(${sysTimeLeaveDetail.fdType=='2'?lfn:message('sys-time:sysTimeLeaveDetail.fdLeaveTime.hour'):lfn:message('sys-time:sysTimeLeaveDetail.fdLeaveTime.day') })">
				<c:choose>
					<c:when test="${sysTimeLeaveDetail.fdType=='2'}">
						<%
							//加班
							SysTimeLeaveDetail leaveDetail = (SysTimeLeaveDetail) pageContext.getAttribute("sysTimeLeaveDetail");
							Float fdTotalTime = leaveDetail.getFdTotalTime();
							fdTotalTime = fdTotalTime/60;
							pageContext.setAttribute("fdTotalTimeStr", SysTimeUtil.formatHourTimeStr(fdTotalTime));
						%>
						${fdTotalTimeStr}
					</c:when>
					<c:otherwise>
						<fmt:formatNumber value="${sysTimeLeaveDetail.fdLeaveTime }" pattern="#.###"/>
						<c:if test="${not empty sysTimeLeaveDetail.fdResumeTime && sysTimeLeaveDetail.fdResumeTime!=0 }">
							<span style="color: #aaa;font-size: 12px;">(${ lfn:message('sys-time:sysTimeLeaveResume.resume') }
							<fmt:formatNumber value="${sysTimeLeaveDetail.fdResumeDays }" pattern="#.###"/>)
						</c:if>
					</c:otherwise>
				</c:choose>
			</list:data-column>

		<list:data-column col="fdOprType" escape="false" title="${ lfn:message('sys-time:sysTimeLeaveDetail.source') }">
			<c:if test="${sysTimeLeaveDetail.fdOprType eq 1 }">
				${ lfn:message('sys-time:sysTimeLeaveDetail.fdOprType.review') }
			</c:if>
			<c:if test="${sysTimeLeaveDetail.fdOprType eq 2 }">
				${ lfn:message('sys-time:sysTimeLeaveDetail.fdOprType.manual') }
			</c:if>
			<c:if test="${sysTimeLeaveDetail.fdOprType eq '3' }">
				${ lfn:message('sys-time:sysTimeLeaveDetail.fdOprType.batch') }
			</c:if>
		</list:data-column>
		<list:data-column col="fdOprDesc" escape="false" title="${fdOprDescTxt }">
			<c:if test="${sysTimeLeaveDetail.fdType=='2'}">
				<c:if test="${sysTimeLeaveDetail.fdOprStatus eq 1}">
					${ lfn:message('sys-time:sysTimeLeaveDetail.overtime.fdOprDesc.suc') }
				</c:if>
				<c:if test="${sysTimeLeaveDetail.fdOprStatus eq 2}">
					${ lfn:message('sys-time:sysTimeLeaveDetail.overtime.fdOprDesc.fail') }
				</c:if>
				<c:if test="${sysTimeLeaveDetail.fdOprStatus eq 0 && not empty sysTimeLeaveDetail.fdLeaveType}">
					${ lfn:message('sys-time:sysTimeLeaveDetail.overtime.fdOprDesc.no') }
				</c:if>
			</c:if>
			<c:if test="${sysTimeLeaveDetail.fdType !='2'}">
				<c:if test="${sysTimeLeaveDetail.fdOprStatus eq 0 || sysTimeLeaveDetail.fdOprStatus eq null}">
				${ lfn:message('sys-time:sysTimeLeaveDetail.deduct.no') }
				</c:if>
				<c:if test="${sysTimeLeaveDetail.fdOprStatus eq 1}">
					${ lfn:message('sys-time:sysTimeLeaveDetail.deduct.success') }
				</c:if>
				<c:if test="${sysTimeLeaveDetail.fdOprStatus eq 2}">
					<c:if test="${sysTimeLeaveDetail.fdCanUpdateAttend}">
						${ lfn:message('sys-time:sysTimeLeaveDetail.deduct.no') }<br/>
						${sysTimeLeaveDetail.fdOprDesc }
					</c:if>
					<c:if test="${sysTimeLeaveDetail.fdCanUpdateAttend eq null || !sysTimeLeaveDetail.fdCanUpdateAttend}">
						<span style="color: red;">
							${ lfn:message('sys-time:sysTimeLeaveDetail.deduct.fail') }<br/>
							${sysTimeLeaveDetail.fdOprDesc }
						</span>
					</c:if>
				</c:if>
				<c:if test="${sysTimeLeaveDetail.fdOprStatus eq 5}">
						${sysTimeLeaveDetail.fdOprDesc }
				</c:if>
			</c:if>
			
		</list:data-column>
		<list:data-column col="fdReview" escape="false" title="${ lfn:message('sys-time:sysTimeLeaveDetail.fdReview') }" style="min-width: 200px;">
			<c:if test="${not empty sysTimeLeaveDetail.fdReviewId}">
			<div class="conf_btn_edit">
				<a class="btn_txt" href="${LUI_ContextPath}/km/review/km_review_main/kmReviewMain.do?method=view&fdId=${sysTimeLeaveDetail.fdReviewId}" target="_blank">${sysTimeLeaveDetail.fdReviewName}</a>
			</div>
			</c:if>
		</list:data-column>
		<list:data-column col="operation" escape="false" title="${ lfn:message('list.operation') }" style="min-width: 100px;">
			<div class="conf_show_more_w">
				<div class="conf_btn_edit">
					<c:if test="${sysTimeLeaveDetail.fdOprStatus eq 2}">
					<kmss:auth requestURL="/sys/time/sys_time_leave_detail/sysTimeLeaveDetail.do?method=deduct">
						<a class="btn_txt" href="javascript:deduct('${sysTimeLeaveDetail.fdId}');">${lfn:message('sys-time:sysTimeLeaveDetail.deduct')}</a>
					</kmss:auth>
					</c:if>
					
					<kmss:ifModuleExist path="/sys/attend">
					<c:if test="${sysTimeLeaveDetail.fdOprStatus eq 1 || sysTimeLeaveDetail.fdCanUpdateAttend}">
					<kmss:auth requestURL="/sys/time/sys_time_leave_detail/sysTimeLeaveDetail.do?method=updateAttend">
						<a class="btn_txt" href="javascript:updateAttend('${sysTimeLeaveDetail.fdId}');">${lfn:message('sys-time:sysTimeLeaveDetail.updateAttend')}</a>
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