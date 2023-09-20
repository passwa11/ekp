<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<list:data>
	<list:data-columns list="${queryPage.list }" var="queueLog"
		varIndex="status">
		<list:data-column property="fdId">
		</list:data-column>
		<list:data-column property="fdConvertStatus">
		</list:data-column>
		<c:choose>
			<c:when
				test="${ queueLog.fdConvertStatus == 3 || queueLog.fdConvertStatus == 5 || queueLog.fdConvertStatus == 6 || queueLog.fdConvertStatus == 99 || queueLog.fdConvertStatus == 9 || queueLog.fdConvertStatus == 999}">
				<list:data-column style="color:red;" col="index">
		     	 ${status+1}
				</list:data-column>
				<list:data-column headerStyle="width:300px;" property="fdQueueId"
					title="${ lfn:message('sys-filestore:convertlog.queueId') }"
					escape="false" style="color:red;text-align:center">
				</list:data-column>
				<list:data-column style="color:red;text-align:center"
					headerStyle="width:160px;" property="fdConvertKey"
					title="${ lfn:message('sys-filestore:sysFileConverter.fdConverterFullKey') }"
					escape="false">
				</list:data-column>
				<list:data-column headerStyle="width:80px;" col="statusMessageInfo"
					title="${ lfn:message('sys-filestore:sysFileConvertQueue.fdConvertStatus') }"
					escape="false" style="color:red;text-align:center">
					<c:choose>
						<c:when test="${ queueLog.fdConvertStatus == 0 }">
							<c:out value="${ lfn:message('sys-filestore:convertStatus.0') }"></c:out>
						</c:when>
						<c:when test="${ queueLog.fdConvertStatus == 1 }">
							<c:out value="${ lfn:message('sys-filestore:convertStatus.1') }"></c:out>
						</c:when>
						<c:when test="${ queueLog.fdConvertStatus == 2 }">
							<c:out
								value="${ lfn:message('sys-filestore:convertStatus.2_log') }"></c:out>
						</c:when>
						<c:when test="${ queueLog.fdConvertStatus == 3 }">
							<c:out value="${ lfn:message('sys-filestore:convertStatus.3') }"></c:out>
						</c:when>
						<c:when test="${ queueLog.fdConvertStatus == 5 }">
							<c:out value="${ lfn:message('sys-filestore:convertStatus.5') }"></c:out>
						</c:when>
						<c:when test="${ queueLog.fdConvertStatus == 6 }">
							<c:out value="${ lfn:message('sys-filestore:convertStatus.6') }"></c:out>
						</c:when>
						<c:when test="${ queueLog.fdConvertStatus == 4 }">
							<c:out value="${ lfn:message('sys-filestore:convertStatus.4') }"></c:out>
						</c:when>
						<c:when test="${ queueLog.fdConvertStatus == 9 }">
							<c:out value="${ lfn:message('sys-filestore:convertStatus.9') }"></c:out>
						</c:when>
						<c:when test="${ queueLog.fdConvertStatus == 99 }">
							<c:out value="${ lfn:message('sys-filestore:convertStatus.99') }"></c:out>
						</c:when>
						<c:when test="${ queueLog.fdConvertStatus == 999 }">
							<c:out value="${ lfn:message('sys-filestore:convertStatus.999') }"></c:out>
						</c:when>
					</c:choose>
				</list:data-column>
				<list:data-column style="color:red;text-align:center"
					headerStyle="width:120px" col="fdStatusTime"
					title="${ lfn:message('sys-filestore:convertQueue.statusTime') }">
					<c:if test="${not empty queueLog.fdStatusTime }">
						<kmss:showDate value="${queueLog.fdStatusTime}" type="datetime"></kmss:showDate>
					</c:if>
				</list:data-column>
				<list:data-column headerStyle="width:300px;" col="fdStatusInfo"
					title="${ lfn:message('sys-filestore:convertlog.statusinfo') }"
					escape="false" style="color:red;text-align:center">
					<c:choose>
						<c:when test="${fn:length(queueLog.fdStatusInfo) > 60}">
							<c:out value="${fn:substring(queueLog.fdStatusInfo, 0, 60)}……" />
						</c:when>
						<c:otherwise>
							<c:out value="${queueLog.fdStatusInfo}" />
						</c:otherwise>
					</c:choose>
				</list:data-column>
			</c:when>
			<c:otherwise>
				<list:data-column col="index">
		     	 ${status+1}
				</list:data-column>
				<list:data-column headerStyle="width:300px;" property="fdQueueId"
					title="${ lfn:message('sys-filestore:convertlog.queueId') }"
					escape="false" style="text-align:center">
				</list:data-column>
				<list:data-column headerStyle="width:160px;" property="fdConvertKey"
					title="${ lfn:message('sys-filestore:sysFileConverter.fdConverterFullKey') }"
					escape="false" style="text-align:center">
				</list:data-column>
				<list:data-column headerStyle="width:80px;" col="statusMessageInfo"
					title="${ lfn:message('sys-filestore:sysFileConvertQueue.fdConvertStatus') }"
					escape="false" style="text-align:center">
					<c:choose>
						<c:when test="${ queueLog.fdConvertStatus == 0 }">
							<c:out value="${ lfn:message('sys-filestore:convertStatus.0') }"></c:out>
						</c:when>
						<c:when test="${ queueLog.fdConvertStatus == 1 }">
							<c:out value="${ lfn:message('sys-filestore:convertStatus.1') }"></c:out>
						</c:when>
						<c:when test="${ queueLog.fdConvertStatus == 2 }">
							<c:out
								value="${ lfn:message('sys-filestore:convertStatus.2_log') }"></c:out>
						</c:when>
						<c:when test="${ queueLog.fdConvertStatus == 3 }">
							<c:out value="${ lfn:message('sys-filestore:convertStatus.3') }"></c:out>
						</c:when>
						<c:when test="${ queueLog.fdConvertStatus == 5 }">
							<c:out value="${ lfn:message('sys-filestore:convertStatus.5') }"></c:out>
						</c:when>
						<c:when test="${ queueLog.fdConvertStatus == 6 }">
							<c:out value="${ lfn:message('sys-filestore:convertStatus.6') }"></c:out>
						</c:when>
						<c:when test="${ queueLog.fdConvertStatus == 4 }">
							<c:out value="${ lfn:message('sys-filestore:convertStatus.4') }"></c:out>
						</c:when>
						<c:when test="${ queueLog.fdConvertStatus == 9 }">
							<c:out value="${ lfn:message('sys-filestore:convertStatus.9') }"></c:out>
						</c:when>
						<c:when test="${ queueLog.fdConvertStatus == 99 }">
							<c:out value="${ lfn:message('sys-filestore:convertStatus.99') }"></c:out>
						</c:when>
						<c:when test="${ queueLog.fdConvertStatus == 999 }">
							<c:out value="${ lfn:message('sys-filestore:convertStatus.999') }"></c:out>
						</c:when>
					</c:choose>
				</list:data-column>
				<list:data-column headerStyle="width:120px" col="fdStatusTime"
					title="${ lfn:message('sys-filestore:convertQueue.statusTime') }">
					<c:if test="${not empty queueLog.fdStatusTime }">
						<kmss:showDate value="${queueLog.fdStatusTime}" type="datetime"></kmss:showDate>
					</c:if>
				</list:data-column>
				<list:data-column headerStyle="width:300px;" col="fdStatusInfo"
					title="${ lfn:message('sys-filestore:convertlog.statusinfo') }"
					escape="false" style="text-align:center">
					<c:choose>
						<c:when test="${fn:length(queueLog.fdStatusInfo) > 60}">
							<c:out value="${fn:substring(queueLog.fdStatusInfo, 0, 60)}..." />
						</c:when>
						<c:otherwise>
							<c:out value="${queueLog.fdStatusInfo}" />
						</c:otherwise>
					</c:choose>
				</list:data-column>
			</c:otherwise>
		</c:choose>

	</list:data-columns>

	<list:data-paging currentPage="${queryPage.pageno }"
		pageSize="${queryPage.rowsize }" totalSize="${queryPage.totalrows }"></list:data-paging>
</list:data>