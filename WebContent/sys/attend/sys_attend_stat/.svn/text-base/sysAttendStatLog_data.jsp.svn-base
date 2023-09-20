<%@ page language="java" contentType="text/json; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<list:data>
	<list:data-columns var="sysAttendStatLog" list="${queryPage.list}" varIndex="status">
		<list:data-column property="fdId">
		</list:data-column>
		<list:data-column col="index">
		  ${status+1}
		</list:data-column>

		<list:data-column col="fdOperateType" title="${ lfn:message('sys-attend:sysAttendStatLog.fdOperType') }">
			<c:if test="${'create' eq sysAttendStatLog.docSubject}">
				${ lfn:message('sys-attend:sysAttend.tree.config.stat.btnStat.create') }
			</c:if>
			<c:if test="${'restat' eq sysAttendStatLog.docSubject}">
				${ lfn:message('sys-attend:sysAttend.tree.config.stat.btnStat') }
			</c:if>
		</list:data-column>
		<list:data-column col="statTime" title="${ lfn:message('sys-attend:sysAttend.tree.config.stat.statTime') }">
			<kmss:showDate value="${sysAttendStatLog.fdBeginDate}" type="date" />
				 —
			<kmss:showDate value="${sysAttendStatLog.fdEndDate}" type="date" />
		</list:data-column>
		<list:data-column col="docCreateTime" title="${ lfn:message('sys-attend:sysAttendRestatLog.docCreateTime') }">
			<kmss:showDate value="${sysAttendStatLog.docCreateTime}" type="datetime" />
		</list:data-column>
		<list:data-column col="docAlterTime" title="${ lfn:message('sys-attend:sysAttendRestatLog.docAlterTime') }">
			<kmss:showDate value="${sysAttendStatLog.docAlterTime}" type="datetime" />
		</list:data-column>

		<list:data-column col="fdCategoryName" title="${ lfn:message('sys-attend:sysAttendRestatLog.fdCategoryName') }" escape="false">
			<div name ="infoAotuHeight" class="statObjectName">
				${sysAttendStatLog.fdCategoryName}
			</div>
			<div style="text-align: right;">
				<span class="statObject-more" onclick="window.showMoreContent(this)">
						${ lfn:message('sys-attend:sysAttendRestatLog.more') }
				</span>
				<span class="statObject-moreFold" onclick="window.closeMoreContent(this)">
						${ lfn:message('sys-attend:sysAttendRestatLog.close.more') }
				</span>
			</div>
		</list:data-column>
		<list:data-column col="fdStatUserNames" title="${ lfn:message('sys-attend:sysAttendRestatLog.fdStatUserNames') }" escape="false">
			<div name ="infoAotuHeight" class="statObjectName">
				${sysAttendStatLog.fdStatUserNames}
			</div>
			<div style="text-align: right;">
				<span class="statObject-more" onclick="window.showMoreContent(this)">
						${ lfn:message('sys-attend:sysAttendRestatLog.more') }
				</span>
				<span class="statObject-moreFold" onclick="window.closeMoreContent(this)">
						${ lfn:message('sys-attend:sysAttendRestatLog.close.more') }
				</span>
			</div>
		</list:data-column>
		<list:data-column col="fdCreateMiss" title="${ lfn:message('sys-attend:sysAttendRestatLog.fdCreateMiss') }">

			${sysAttendStatLog.fdCreateMiss?lfn:message('sys-attend:sysAttendRestatLog.yes'):lfn:message('sys-attend:sysAttendRestatLog.no')}

		</list:data-column>
		<list:data-column col="fdStatus" title="${ lfn:message('sys-attend:sysAttendRestatLog.fdStatus') }"  escape="false">
			<c:choose>
				<c:when test="${sysAttendStatLog.fdStatus==0 }">
						<span style="color:#4285F4">	${ lfn:message('sys-attend:sysAttendRestatLog.ing') }</span>
				</c:when>
				<c:when test="${sysAttendStatLog.fdStatus==1 }">
					${ lfn:message('sys-attend:sysAttendRestatLog.ok') }
				</c:when>
				<c:when test="${sysAttendStatLog.fdStatus==2 }">
						<span style="color:red">${ lfn:message('sys-attend:sysAttendRestatLog.error') } </span>
				</c:when>
				<c:otherwise>
						<span style="color:red">${ lfn:message('sys-attend:sysAttendRestatLog.other') }</span>
				</c:otherwise>
			</c:choose>
		</list:data-column>
	</list:data-columns>
	
	<list:data-paging page="${queryPage}" />
</list:data>