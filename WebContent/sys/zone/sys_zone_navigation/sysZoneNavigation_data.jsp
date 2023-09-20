<%@ page language="java" contentType="text/json; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<list:data>
	<list:data-columns var="sysZoneNavigation" list="${queryPage.list}" varIndex="status">
		<list:data-column property="fdId">
		</list:data-column>
		<list:data-column col="index">
		  ${status+1}
		</list:data-column>
		
		<list:data-column col="fdName" style="max-width:30%" title="${ lfn:message('sys-zone:sysZoneNavigation.fdName') }">
		<c:out value="${sysZoneNavigation.fdName}"/>
		</list:data-column>
		<list:data-column styleClass="fdStatus" col="fdStatus" style="max-width:100px;" title="${ lfn:message('sys-zone:sysZoneNavigation.fdStatus') }">
		<sunbor:enumsShow enumsType="sysPerson_fdStatus" value="${sysZoneNavigation.fdStatus}" />
		</list:data-column>
		<list:data-column col="fdShowType" styleClass="showType" title="${ lfn:message('sys-zone:sysZoneNavigation.fdShow') }" escape="false">
			<c:out value="${sysZoneNavigation.fdShowType}" />
			<input type="hidden" name="showType_${sysZoneNavigation.fdId}" value="${sysZoneNavigation.fdShowType}"></td>
		</list:data-column>
		<list:data-column col="docCreator" title="${ lfn:message('sys-zone:sysZoneNavigation.docCreator') }">
		<c:out value="${sysZoneNavigation.docCreator.fdName}" />
		</list:data-column>
		<list:data-column col="docCreateTime" title="${ lfn:message('sys-zone:sysZoneNavigation.docCreateTime') }">
		<kmss:showDate value="${sysZoneNavigation.docCreateTime}" type="date"/>
		</list:data-column>
		<list:data-column col="docAlteror" title="${ lfn:message('sys-zone:sysZoneNavigation.docAlteror') }">
			<c:out value="${sysZoneNavigation.docAlteror.fdName}" />
		</list:data-column>
		<list:data-column col="docAlterTime" title="${ lfn:message('sys-zone:sysZoneNavigation.docAlterTime') }">
		<kmss:showDate value="${sysZoneNavigation.docAlterTime}" type="datetime"/>
		</list:data-column>
		
	</list:data-columns>
	
	<list:data-paging page="${queryPage}" />
</list:data>