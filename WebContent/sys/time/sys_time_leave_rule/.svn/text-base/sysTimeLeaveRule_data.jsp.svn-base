<%@ page language="java" contentType="text/json; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<list:data>
	<list:data-columns var="sysTimeLeaveRule" list="${queryPage.list}" varIndex="status">
		<list:data-column property="fdId">
		</list:data-column>
		<list:data-column col="index" >
		 	${status+1}
		</list:data-column>
		
		<list:data-column col="fdName" escape="false" style="min-width: 80px;" title="${ lfn:message('sys-time:sysTimeLeaveRule.fdName') }">
		 	<c:out value="${sysTimeLeaveRule.fdName}" escapeXml="true"></c:out>
		</list:data-column>
		<list:data-column col="fdSerialNo" escape="false" style="min-width: 50px;" title="${ lfn:message('sys-time:sysTimeLeaveRule.fdSerialNo') }">
		 	${sysTimeLeaveRule.fdSerialNo}
		</list:data-column>
		<list:data-column col="fdStatType" escape="false" style="min-width: 50px;" title="${ lfn:message('sys-time:sysTimeLeaveRule.fdStatType') }">
			<c:if test="${sysTimeLeaveRule.fdStatType eq 1}">
				${ lfn:message('sys-time:sysTimeLeaveRule.fdStatType.day') }
			</c:if>
			<c:if test="${sysTimeLeaveRule.fdStatType eq 2}">
				${ lfn:message('sys-time:sysTimeLeaveRule.fdStatType.halfDay') }
			</c:if>
			<c:if test="${sysTimeLeaveRule.fdStatType eq 3}">
				${ lfn:message('sys-time:sysTimeLeaveRule.fdStatType.hour') }
			</c:if>
		</list:data-column>
		<list:data-column col="fdIsAvailable" escape="false" style="min-width: 30px;" title="${ lfn:message('sys-time:sysTimeLeaveRule.fdIsAvailable') }">
			<c:if test="${sysTimeLeaveRule.fdIsAvailable}">
				<span style="font-weight: bold">${lfn:message('sys-time:sysTimeLeaveRule.fdIsAvailable.enable')}</span>
			</c:if>
			<c:if test="${sysTimeLeaveRule.fdIsAvailable eq null || !sysTimeLeaveRule.fdIsAvailable}">
				${lfn:message('sys-time:sysTimeLeaveRule.fdIsAvailable.disable')}
			</c:if>
		</list:data-column>
		<list:data-column col="fdIsAmount" escape="false" style="min-width: 30px;" title="${ lfn:message('sys-time:sysTimeLeaveRule.fdIsAmount') }">
			<c:if test="${sysTimeLeaveRule.fdIsAmount }">
				<span style="font-weight: bold">${lfn:message('sys-time:sysTimeLeaveRule.fdIsAmount.limit')}</span>
			</c:if>
			<c:if test="${sysTimeLeaveRule.fdIsAmount eq null || !sysTimeLeaveRule.fdIsAmount}">
				${lfn:message('sys-time:sysTimeLeaveRule.fdIsAmount.nolimit')}
			</c:if>
		</list:data-column>
		<list:data-column col="operation" escape="false" title="${ lfn:message('list.operation') }" style="min-width: 100px;">
			<div class="conf_show_more_w">
				<div class="conf_btn_edit">
					<kmss:auth requestURL="/sys/time/sys_time_leave_rule/sysTimeLeaveRule.do?method=edit">
						<a class="btn_txt" href="javascript:edit('${sysTimeLeaveRule.fdId}');">${lfn:message('button.edit')}</a>
					</kmss:auth>
					<kmss:auth requestURL="/sys/time/sys_time_leave_rule/sysTimeLeaveRule.do?method=disable">
					<c:if test="${sysTimeLeaveRule.fdIsAvailable}">
						<c:if test="${sysTimeLeaveRule.fdIsAmount }">
							<a class="btn_txt" href="javascript:disableAndDeleteAmount('${sysTimeLeaveRule.fdId}');">${lfn:message('sys-time:sysTimeLeaveRule.fdIsAvailable.disable')}</a>
						</c:if>
						<c:if test="${sysTimeLeaveRule.fdIsAmount eq null || !sysTimeLeaveRule.fdIsAmount}">
							<a class="btn_txt" href="javascript:disable('${sysTimeLeaveRule.fdId}');">${lfn:message('sys-time:sysTimeLeaveRule.fdIsAvailable.disable')}</a>
						</c:if>
					</c:if>
					</kmss:auth>
					<kmss:auth requestURL="/sys/time/sys_time_leave_rule/sysTimeLeaveRule.do?method=enable">
					<c:if test="${sysTimeLeaveRule.fdIsAvailable eq null || !sysTimeLeaveRule.fdIsAvailable}">
						<a class="btn_txt" href="javascript:enable('${sysTimeLeaveRule.fdId}')">${lfn:message('sys-time:sysTimeLeaveRule.fdIsAvailable.enable')}</a>
					</c:if>
					</kmss:auth>
					<kmss:auth requestURL="/sys/time/sys_time_leave_rule/sysTimeLeaveRule.do?method=deleteall">
						<a class="btn_txt" href="javascript:deleteAll('${sysTimeLeaveRule.fdId}');">${lfn:message('button.delete')}</a>
					</kmss:auth>
				</div>
			</div>
		</list:data-column>
	</list:data-columns>
	
	<list:data-paging currentPage="${queryPage.pageno }" pageSize="${queryPage.rowsize }" totalSize="${queryPage.totalrows }">
	</list:data-paging>
</list:data>