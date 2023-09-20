<%@ page import="com.landray.kmss.sys.authorization.util.TripartiteAdminUtil"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<list:data>
	<!-- 表格数据 -->
	<list:data-columns var="sysOrgOrg" list="${queryPage.list }">
		<list:data-column property="fdId" />
		<!-- 排序号 -->
		<list:data-column headerClass="width80"  property="fdOrder" title="${ lfn:message('sys-organization:sysOrgOrg.fdOrder') }">
		</list:data-column>
		<!-- 上级机构 -->
		<list:data-column headerClass="width200" col="fdParent" title="${ lfn:message('sys-organization:sysOrgOrg.fdParent') }" escape="false">
			<pre>${sysOrgOrg.fdParent.fdName}</pre>
		</list:data-column>
		<!-- 机构名称 -->
		<list:data-column headerClass="width200" col="fdName" title="${ lfn:message('sys-organization:sysOrgOrg.fdName') }" escape="false">
		    <pre>${lfn:escapeHtml(sysOrgOrg.fdName)}</pre>
		</list:data-column>
		<!-- 机构领导 -->
		<list:data-column headerClass="width200" col="fdThisLeader" title="${ lfn:message('sys-organization:sysOrgOrg.fdThisLeader') }" escape="false">
			<pre>${sysOrgOrg.hbmThisLeader.fdName}</pre>
		</list:data-column>
		<!-- 上级领导 -->
		<list:data-column headerClass="width200" col="fdSuperLeader" title="${ lfn:message('sys-organization:sysOrgOrg.fdSuperLeader') }" escape="false">
			<pre>${sysOrgOrg.hbmSuperLeader.fdName}</pre>
		</list:data-column>
		<!-- 编号 -->
		<list:data-column headerClass="width100" property="fdNo" title="${ lfn:message('sys-organization:sysOrgOrg.fdNo') }">
		</list:data-column>
		<!-- 在职人数 -->
		<list:data-column headerClass="width200" col="fdPersonCount" title="${ lfn:message('sys-organization:sys.org.personCount') }">
			<%=com.landray.kmss.sys.organization.util.SysOrgUtil.getPersonCountByOrgDept((com.landray.kmss.sys.organization.model.SysOrgElement)pageContext.getAttribute("sysOrgOrg"))%>
		</list:data-column>
		<!-- 其它操作 -->
		<list:data-column headerClass="width250" col="operations" title="${ lfn:message('list.operation') }" escape="false">
			<!--操作按钮 开始-->
			<div class="conf_show_more_w">
				<div class="conf_btn_edit">
					<kmss:auth requestURL="/sys/organization/sys_org_org/sysOrgOrg.do?method=edit&fdId=${sysOrgOrg.fdId}" requestMethod="GET">
						<!-- 编辑 -->
						<a class="btn_txt" href="javascript:edit('${sysOrgOrg.fdId}')" title="${lfn:message('button.edit')}">${lfn:message('button.edit')}</a>
					</kmss:auth>
					<c:if test="${param.available != '0'}">
					<kmss:auth requestURL="/sys/organization/sys_org_org/sysOrgOrg.do?method=invalidatedAll" requestMethod="POST">
						<!-- 禁用 -->
						<a class="btn_txt" href="javascript:invalidated('${sysOrgOrg.fdId}')" title="${ lfn:message('sys-organization:sys.org.available.false') }">${ lfn:message('sys-organization:sys.org.available.false') }</a>
					</kmss:auth>
					</c:if>
					<% if (!TripartiteAdminUtil.IS_ENABLED_TRIPARTITE_ADMIN) { %>
					<!-- 日志 -->
					<a class="btn_txt" href="javascript:viewLog('${sysOrgOrg.fdId}', '${sysOrgOrg.fdName}')" title="${ lfn:message('sys-organization:sys.org.operations.log') }">${ lfn:message('sys-organization:sys.org.operations.log') }</a>
					<% } %>
				</div>
			</div>
			<!--操作按钮 结束-->
		</list:data-column>
	</list:data-columns>

	<!-- 分页 -->
	<list:data-paging currentPage="${queryPage.pageno }" pageSize="${queryPage.rowsize }" totalSize="${queryPage.totalrows }" />
</list:data>