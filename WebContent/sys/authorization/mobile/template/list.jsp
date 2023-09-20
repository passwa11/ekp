<%@ page language="java" contentType="text/json; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ page import="com.landray.kmss.util.DateUtil,java.util.Date,com.landray.kmss.sys.attend.util.AttendUtil" %>
<%@ page import="com.landray.kmss.util.DbUtils" %>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<list:data>
	<list:data-columns var="model" list="${queryPage.list}" varIndex="status" mobile="true">
		<list:data-column property="fdId">
		</list:data-column >
		<list:data-column col="label">
			${model.fdName}
		</list:data-column >
		<list:data-column col="fdDesc">
			${model.fdDescription}
		</list:data-column >
		<list:data-column col="optTxt">
			<c:if test="${model.fdTemplate=='1' }">
				${ lfn:message('sys-authorization:sysAuthTemplate.use') }
			</c:if>
			<c:if test="${model.fdTemplate!='1' }">
				${ lfn:message('sys-authorization:mui.sysAuthRole.edit') }
			</c:if>
		</list:data-column >
		<list:data-column col="href" escape="false" title="">
			<c:if test="${empty model.fdTemplate && not empty model.fdTemplateId}">
				<kmss:auth requestURL="/sys/authorization/sys_auth_role/sysAuthRole.do?method=edit&fdId=${model.fdId}" requestMethod="GET">
					/sys/authorization/sys_auth_role/sysAuthRole.do?method=edit&fdId=${model.fdId}
				</kmss:auth>
			</c:if>
			<c:if test="${model.fdTemplate=='1'}">
				<kmss:auth requestURL="/sys/authorization/sys_auth_role/sysAuthRole.do?method=add&fdAuthTemplateId=${model.fdId}" requestMethod="GET">
					/sys/authorization/sys_auth_role/sysAuthRole.do?method=add&categoryId=&fdAuthTemplateId=${model.fdId}
				</kmss:auth>
			</c:if>
			
		</list:data-column>
      	
		</list:data-columns>
	
	<list:data-paging currentPage="${queryPage.pageno }"
		pageSize="${queryPage.rowsize }" totalSize="${queryPage.totalrows }">
	</list:data-paging>
</list:data>