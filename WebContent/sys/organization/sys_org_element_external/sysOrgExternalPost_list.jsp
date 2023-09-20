<%@ page import="com.landray.kmss.sys.authorization.util.TripartiteAdminUtil"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<list:data>
	<!-- 表格数据 -->
	<list:data-columns var="sysOrgPost" list="${queryPage.list }">
		<list:data-column property="fdId" />
		<!-- 排序号 -->
		<list:data-column headerClass="width80"  property="fdOrder" title="${ lfn:message('sys-organization:sysOrgPost.fdOrder') }">
		</list:data-column>
		<!-- 所在组织 -->
		<list:data-column headerClass="width200" col="fdParent" title="${ lfn:message('sys-organization:sysOrgElementExternal.fdParent') }" escape="false">
			<pre>${sysOrgPost.fdParent.fdName}</pre>
		</list:data-column>
		<!-- 岗位名称 -->
		<list:data-column  headerClass="width200" col="fdName" title="${ lfn:message('sys-organization:sysOrgPost.fdName') }" escape="false">
		    <pre>${lfn:escapeHtml(sysOrgPost.fdName)}</pre>
		</list:data-column>
		<!-- 编号 -->
		<list:data-column headerClass="width100" property="fdNo" title="${ lfn:message('sys-organization:sysOrgPost.fdNo') }">
		</list:data-column>
		<!-- 岗位领导 -->
		<list:data-column headerClass="width200" col="fdThisLeader" title="${ lfn:message('sys-organization:sysOrgPost.fdThisLeader') }" escape="false">
			<pre>${sysOrgPost.hbmThisLeader.fdName}</pre>
		</list:data-column>
		<!-- 员工列表 -->
		<list:data-column headerClass="width200" col="fdPersons" title="${ lfn:message('sys-organization:sysOrgPost.fdPersons') }" escape="false">
			<pre><c:forEach items="${sysOrgPost.fdPersons}" var="person" varStatus="idx"><c:if test="${ idx.index > 0 }">,</c:if>${person.fdName}</c:forEach></pre>
		</list:data-column>
		<!-- 其它操作 -->
		<list:data-column headerClass="width200" col="operations" title="${ lfn:message('list.operation') }" escape="false">
			<!--操作按钮 开始-->
			<div class="conf_show_more_w">
				<div class="conf_btn_edit">
					<kmss:auth requestURL="/sys/organization/sys_org_element_external/sysOrgElementExternalPost.do?method=edit&fdId=${sysOrgPost.fdId}" requestMethod="GET">
						<!-- 编辑 -->
						<a class="btn_txt" href="javascript:edit('${sysOrgPost.fdId}')">${lfn:message('button.edit')}</a>
					</kmss:auth>
					<c:if test="${'true' eq sysOrgPost.fdIsAvailable}">
					<kmss:auth requestURL="/sys/organization/sys_org_element_external/sysOrgElementExternalPost.do?method=invalidated&fdId=${sysOrgPost.fdId}" requestMethod="POST">
						<!-- 禁用 -->
						<a class="btn_txt" href="javascript:invalidated('${sysOrgPost.fdId}')">${ lfn:message('sys-organization:sys.org.available.false') }</a>
					</kmss:auth>
					</c:if>
					<% if (!TripartiteAdminUtil.IS_ENABLED_TRIPARTITE_ADMIN) { %>
					<kmss:auth requestURL="/sys/organization/sys_log_organization/index.jsp?fdId=${sysOrgPost.fdId}" requestMethod="GET">
					<!-- 日志 -->
					<a class="btn_txt" href="javascript:viewLog('${sysOrgPost.fdId}', '${sysOrgPost.fdName}')">${ lfn:message('sys-organization:sys.org.operations.log') }</a>
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