<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<list:data>
	<!-- 表格数据 -->
	<list:data-columns var="sysAttendSignLog" list="${queryPage.list }">
		<list:data-column property="fdId" />
        <list:data-column col="fdOperator" title="${ lfn:message('sys-attend:sysAttendSignLog.docCreator') }">
            ${sysAttendSignLog.docCreator.fdName }
        </list:data-column>
		<list:data-column col="fdOperatorDept" title="${ lfn:message('sys-attend:sysAttendMain.docCreatorDept') }">
			${not empty sysAttendSignLog.docCreator.fdParent?sysAttendSignLog.docCreator.fdParent.fdName:""}
		</list:data-column>
        <!-- 操作时间 -->
		<list:data-column  col="docCreateTime" title="${ lfn:message('sys-attend:sysAttendSignLog.docCreateTime') }">
			<kmss:showDate type="datetime" value="${sysAttendSignLog.docCreateTime}"/>
		</list:data-column>
        <list:data-column col="fdAddress" title="${ lfn:message('sys-attend:sysAttendSignLog.fdAddress') }">

			${sysAttendSignLog.fdAddress}
			<c:if test="${not empty sysAttendSignLog.fdWifiName }">
				<c:choose>
					<c:when test="${not empty sysAttendSignLog.fdAddress }">
						(${sysAttendSignLog.fdWifiName})
					</c:when>
					<c:otherwise>
						${sysAttendSignLog.fdWifiName}
					</c:otherwise>
				</c:choose>
			</c:if>
        </list:data-column>
		<list:data-column col="fdSignType" title="${ lfn:message('sys-attend:sysAttendSignLog.fdType') }" escape="false">
			<c:choose>
				<c:when test="${not empty sysAttendSignLog.fdWifiName}">
					${ lfn:message('sys-attend:sysAttendMain.export.fdSignType.wifi') }
				</c:when>
				<c:when test="${not empty sysAttendSignLog.fdAddress}">
					${ lfn:message('sys-attend:sysAttendMain.export.fdSignType.map') }
				</c:when>
				<c:otherwise>
				</c:otherwise>
			</c:choose>
		</list:data-column>
	</list:data-columns>

	<!-- 分页 -->
	<list:data-paging currentPage="${queryPage.pageno }" pageSize="${queryPage.rowsize }" totalSize="${queryPage.totalrows }" />
</list:data>