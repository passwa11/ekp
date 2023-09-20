<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<list:data>
	<!-- 表格数据 -->
	<list:data-columns var="sysAuthSysRole" list="${queryPage.list }">
		<%
			pageContext.setAttribute("list_error", false);
		%>
		<list:data-column property="fdId" />
		<!-- 角色名 -->
		<list:data-column headerClass="width200" col="fdName" title="${ lfn:message('sys-authorization:sysAuthRole.fdName') }" style="text-align: left;" escape="false">
			<% try { %>
				<kmss:message key="${sysAuthSysRole.fdName}" />
			<% 
				} catch (com.landray.kmss.common.exception.KmssRuntimeException e) {
					pageContext.setAttribute("list_error", true);
				%>
					<span style="color: red;">${ lfn:message('sys-authorization:sysAuthRole.list.error') }</span>
				<%
				}
			%>
		</list:data-column>
		<!-- 描述 -->
		<list:data-column headerClass="width300" col="fdDescription" title="${ lfn:message('sys-authorization:sysAuthRole.fdDescription') }" style="text-align: left;" escape="false">
			<c:if test="${!list_error}">
			<% try { %>
				<kmss:message key="${sysAuthSysRole.fdDescription}" />
			<% 
				} catch (com.landray.kmss.common.exception.KmssRuntimeException e) {
					pageContext.setAttribute("list_error", true);
				%>
					<span style="color: red;">${ lfn:message('sys-authorization:sysAuthRole.list.error') }</span>
				<%
				}
			%>
			</c:if>
		</list:data-column>
		<!-- 权限类型 -->
		<list:data-column headerClass="width100" col="fdType" title="${ lfn:message('sys-authorization:sysAuthRole.fdType') }">
			<c:if test="${!list_error}">
				<sunbor:enumsShow value="${sysAuthSysRole.fdType}" enumsType="sys_authorization_fd_type" />
			</c:if>
		</list:data-column>
		<!-- 其它操作 -->
		<list:data-column headerClass="width200" col="operations" title="${ lfn:message('list.operation') }" escape="false">
			<c:if test="${!list_error}">
			<!--操作按钮 开始-->
			<div class="conf_show_more_w">
				<div class="conf_btn_edit">
					<!-- 查看引用 -->
					<a class="btn_txt" href="javascript:reference('${sysAuthSysRole.fdId}')">${ lfn:message('sys-authorization:sysAuthArea.SeeReferences') }</a>
					<kmss:auth requestURL="/sys/authorization/sys_auth_sys_role/sysAuthSysRole.do?method=edit">
					<!-- 用户指派 -->
					<a class="btn_txt" href="javascript:assignment('${sysAuthSysRole.fdId}')">${ lfn:message('sys-authorization:sysAuthArea.UserAssignment') }</a>
					</kmss:auth>
				</div>
			</div>
			<!--操作按钮 结束-->
			</c:if>
		</list:data-column>
	</list:data-columns>

	<!-- 分页 -->
	<list:data-paging currentPage="${queryPage.pageno }" pageSize="${queryPage.rowsize }" totalSize="${queryPage.totalrows }" />
</list:data>