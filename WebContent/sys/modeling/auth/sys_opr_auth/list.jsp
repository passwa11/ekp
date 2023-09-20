<%@ page language="java" contentType="text/json; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<list:data>
	<list:data-columns var="sysAuthRole" list="${queryPage.list }">
		<list:data-column property="fdId">
		</list:data-column>
		<list:data-column col="fdOprName" title="${lfn:message('sys-modeling-auth:sysModelingAuth.OperationName') }" style="text-align:left;min-width:180px" escape="false">
			<span class="com_subject"><c:out value="${sysAuthRole.fdOprName}" /></span>
		</list:data-column>
		<list:data-column col="fdType" title="${lfn:message('sys-modeling-auth:sysModelingAuth.OperationType') }" >
			<sunbor:enumsShow value="${sysAuthRole.fdType}" enumsType="sys_modeling_auth_fd_type"/>
		</list:data-column>
		<list:data-column col="fdAuthUra" title="${lfn:message('sys-modeling-auth:sysModelingAuth.AuthorizationDetails') }" escape="false">
			<c:forEach items="${sysAuthRole.fdRolesInh}" var="role" varStatus="idx">
				<c:if test="${ idx.index > 0 }">;</c:if>
				<c:forEach items="${role.authUra}" var="authUra" varStatus="indx">
					<c:if test="${ indx.index > 0 }">,</c:if>
						${ authUra.orgElement.fdName }
				</c:forEach>
			</c:forEach>
		</list:data-column>
		<list:data-column headerClass="width180" col="operations" title="${ lfn:message('list.operation') }" escape="false">
			<!-- 操作列 -->
			<div class="conf_show_more_w">
				<div class="conf_btn_edit">
					<kmss:auth requestURL="/sys/modeling/auth/sys_auth_role/sysModelingSimpleAuthRole.do?method=edit&fdId=${sysAuthRole.fdId}" requestMethod="GET">
						<!-- 设置 -->
						<a class="btn_txt" href="javascript:doSetting('${sysAuthRole.fdId}')">${lfn:message('sys-modeling-auth:sysModelingAuth.AuthoritySetting') }</a>
					</kmss:auth>
					</div>
			</div>
			<!--操作按钮 结束-->
		</list:data-column>

	</list:data-columns>

	<list:data-paging currentPage="${queryPage.pageno }"
		pageSize="${queryPage.rowsize }" totalSize="${queryPage.totalrows }">
	</list:data-paging>
</list:data>