<%@ page language="java" contentType="text/json; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<list:data>
	<list:data-columns var="hrStaffPersonInfoLog" list="${queryPage.list }" varIndex="status" mobile="true">
		<list:data-column property="fdId">
		</list:data-column>
		<!-- 操作时间-->
		<list:data-column col="fdCreateTime" title="${ lfn:message('hr-staff:hrStaffPersonInfoLog.fdCreateTime') }">
		   <kmss:showDate value="${hrStaffPersonInfoLog.fdCreateTime}" type="datetime" /> 
		</list:data-column>
		<!--IP地址-->
		<list:data-column property="fdIp" title="${ lfn:message('hr-staff:hrStaffPersonInfoLog.fdIp') }"> 
		</list:data-column>
		<!--浏览器-->
		<list:data-column property="fdBrowser" title="${ lfn:message('hr-staff:hrStaffPersonInfoLog.fdBrowser') }">
		</list:data-column>
		<!--设备-->
		<list:data-column property="fdEquipment" title="${ lfn:message('hr-staff:hrStaffPersonInfoLog.fdEquipment') }">
		</list:data-column> 
		<!--操作者-->
		<list:data-column col="fdCreator" title="${ lfn:message('hr-staff:hrStaffPersonInfoLog.fdCreator') }">
			<c:choose>
				<c:when test="${hrStaffPersonInfoLog.isAnonymous}">
					${ lfn:message('hr-staff:hrStaffPersonInfoLog.sync.creator') }
				</c:when>
				<c:otherwise>
					${hrStaffPersonInfoLog.fdCreator.fdName}
				</c:otherwise>
			</c:choose>
		</list:data-column>
		<!--操作方法-->
		<list:data-column col="fdParaMethod" title="${ lfn:message('hr-staff:hrStaffPersonInfoLog.fdParaMethod') }">
			<sunbor:enumsShow value="${ hrStaffPersonInfoLog.fdParaMethod }" enumsType="hrStaffPersonInfoLog_fdParaMethod" />
		</list:data-column>
		<!--操作记录-->
		<list:data-column property="fdDetails" title="${ lfn:message('hr-staff:hrStaffPersonInfoLog.fdDetails') }">
		</list:data-column>
	</list:data-columns>
	<list:data-paging currentPage="${queryPage.pageno }" pageSize="${queryPage.rowsize }" totalSize="${queryPage.totalrows }">
	</list:data-paging>
</list:data>