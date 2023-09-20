<%@ page import="com.landray.kmss.sys.authorization.util.TripartiteAdminUtil"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<list:data>
	<!-- 表格数据 -->
	<list:data-columns var="sysOrgDept" list="${queryPage.list }">
		<list:data-column property="fdId" />
		<list:data-column col="isView">
			<kmss:auth requestURL="/sys/organization/sys_org_element_external/sysOrgElementExternalDept.do?method=view&fdId=${sysOrgDept.fdId}" requestMethod="GET">
				true
			</kmss:auth>
		</list:data-column>
		<!-- 排序号 -->
		<list:data-column headerClass="width80"  col="fdOrder" title="${ lfn:message('sys-organization:sysOrgDept.fdOrder') }">
			${sysOrgDept.fdOrder}
		</list:data-column>
		<!-- 上级组织名称 -->
		<list:data-column headerClass="width200" col="fdParent" title="${ lfn:message('sys-organization:sysOrgElementExternal.parent') }" escape="false">
		    <pre>${sysOrgDept.fdParent.fdName}</pre>
		</list:data-column>
		<!-- 组织名称 -->
		<list:data-column headerClass="width200" col="fdName" title="${ lfn:message('sys-organization:sysOrgElementExternal.fdName') }" escape="false">
		    <pre>${sysOrgDept.fdName}</pre>
		</list:data-column>
		<!-- 编号 -->
		<list:data-column headerClass="width100" col="fdNo" title="${ lfn:message('sys-organization:sysOrgDept.fdNo') }">
			${sysOrgDept.fdNo}
		</list:data-column>
		<!-- 负责人 -->
		<list:data-column headerClass="width100" col="fdNo" title="${ lfn:message('sys-organization:sysOrgElementExternal.authElementAdmin') }">
			<c:forEach items="${sysOrgDept.authElementAdmins}" var="admin" varStatus="idx"><c:if test="${ idx.index > 0 }">,</c:if>${admin.fdName}</c:forEach>
		</list:data-column>
		<!-- 组织人数 -->
		<list:data-column headerClass="width100" col="fdPersonCount" title="${ lfn:message('sys-organization:sysOrgElementExternal.personCount') }">
			<%=com.landray.kmss.sys.organization.util.SysOrgUtil.getPersonCountByOutOrgDept((com.landray.kmss.sys.organization.model.SysOrgElement)pageContext.getAttribute("sysOrgDept"))%>
		</list:data-column>
		<!-- 自定义显示属性 -->
		<c:forEach items="${props}" var="prop">
			<c:if test="${'true' eq prop.fdShowList && 'true' eq prop.fdStatus}">
			<list:data-column headerClass="width100" col="${prop.fdFieldName}" title="${prop.fdName}" escape="false">
			    <pre>${sysOrgDept.dynamicMap[prop.fdFieldName]}</pre>
			</list:data-column>
			</c:if>
		</c:forEach>
		<!-- 其它操作 -->
		<list:data-column headerClass="width200" col="operations" title="${ lfn:message('list.operation') }" escape="false">
			<!--操作按钮 开始-->
			<div class="conf_show_more_w">
				<div class="conf_btn_edit">
					<kmss:auth requestURL="/sys/organization/sys_org_element_external/sysOrgElementExternalDept.do?method=edit&fdId=${sysOrgDept.fdId}" requestMethod="GET">
						<!-- 编辑 -->
						<a class="btn_txt" href="javascript:edit('${sysOrgDept.fdId}')">${lfn:message('button.edit')}</a>
					</kmss:auth>
					<c:if test="${'true' eq sysOrgDept.fdIsAvailable}">
					<kmss:auth requestURL="/sys/organization/sys_org_element_external/sysOrgElementExternalDept.do?method=invalidated&fdId=${sysOrgDept.fdId}" requestMethod="POST">
						<!-- 禁用 -->
						<a class="btn_txt" href="javascript:invalidated('${sysOrgDept.fdId}')">${ lfn:message('sys-organization:sys.org.available.false') }</a>
					</kmss:auth>
					</c:if>
					<% if (!TripartiteAdminUtil.IS_ENABLED_TRIPARTITE_ADMIN) { %>
					<kmss:auth requestURL="/sys/organization/sys_log_organization/index.jsp?fdId=${sysOrgDept.fdId}" requestMethod="GET">
					<!-- 日志 -->
					<a class="btn_txt" href="javascript:viewLog('${sysOrgDept.fdId}', '${sysOrgDept.fdName}')">${ lfn:message('sys-organization:sys.org.operations.log') }</a>
					</kmss:auth>
					<% } %>
				</div>
			</div>
			<!--操作按钮 结束-->
		</list:data-column>
	</list:data-columns>

	<!-- 分页 -->
	<list:data-paging currentPage="${queryPage.pageno }" pageSize="${queryPage.rowsize }" totalSize="${queryPage.totalrows }" />
</list:data>