<%@ page import="com.landray.kmss.sys.authorization.util.TripartiteAdminUtil"%>
<%@ page import="com.landray.kmss.sys.organization.util.SysOrgEcoUtil" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<list:data>
	<!-- 表格数据 -->
	<list:data-columns var="sysOrgDept" list="${queryPage.list }">
		<list:data-column property="fdId" />
		<!-- 排序号 -->
		<list:data-column headerClass="width80"  property="fdOrder" title="${ lfn:message('sys-organization:sysOrgDept.fdOrder') }">
		</list:data-column>
		<!-- 上级部门 -->
		<list:data-column headerClass="width200" col="fdParent" title="${ lfn:message('sys-organization:sysOrgDept.fdParent') }" escape="false">
			<pre>${sysOrgDept.fdParent.fdName}</pre>
		</list:data-column>
		<!-- 部门名称 -->
		<list:data-column headerClass="width200" col="fdName" title="${ lfn:message('sys-organization:sysOrgDept.fdName') }" escape="false">
		    <pre>${lfn:escapeHtml(sysOrgDept.fdName)}</pre>
		</list:data-column>
		<!-- 部门领导 -->
		<list:data-column headerClass="width200" col="fdThisLeader" title="${ lfn:message('sys-organization:sysOrgDept.fdThisLeader') }" escape="false">
			<pre>${sysOrgDept.hbmThisLeader.fdName}</pre>
		</list:data-column>
		<!-- 上级领导 -->
		<list:data-column headerClass="width200" col="fdSuperLeader" title="${ lfn:message('sys-organization:sysOrgDept.fdSuperLeader') }" escape="false">
			<pre>${sysOrgDept.hbmSuperLeader.fdName}</pre>
		</list:data-column>
		<!-- 编号 -->
		<list:data-column headerClass="width100" property="fdNo" title="${ lfn:message('sys-organization:sysOrgDept.fdNo') }">
		</list:data-column>
		<!-- 在职人数 -->
		<list:data-column headerClass="width200" col="fdPersonCount" title="${ lfn:message('sys-organization:sys.org.personCount') }">
			<%=com.landray.kmss.sys.organization.util.SysOrgUtil.getPersonCountByOrgDept((com.landray.kmss.sys.organization.model.SysOrgElement)pageContext.getAttribute("sysOrgDept"))%>
		</list:data-column>
		<!-- 其它操作 -->
		<list:data-column headerClass="width200" col="operations" title="${ lfn:message('list.operation') }" escape="false">
			<!--操作按钮 开始-->
			<div class="conf_show_more_w">
				<div class="conf_btn_edit">
					<kmss:auth requestURL="/sys/organization/sys_org_dept/sysOrgDept.do?method=edit&fdId=${sysOrgDept.fdId}" requestMethod="GET">
						<!-- 编辑 -->
						<a class="btn_txt" href="javascript:edit('${sysOrgDept.fdId}')" title="${lfn:message('button.edit')}">${lfn:message('button.edit')}</a>
					</kmss:auth>
					<c:if test="${param.available != '0'}">
					<kmss:auth requestURL="/sys/organization/sys_org_person/sysOrgPerson.do?method=add" requestMethod="GET">
						<!-- 新建人员 -->
						<a class="btn_txt" href="javascript:addPerson('${sysOrgDept.fdId}')" title="${ lfn:message('sys-organization:sys.org.operations.createPerson') }">${ lfn:message('sys-organization:sys.org.operations.createPerson') }</a>
					</kmss:auth>
					<kmss:auth requestURL="/sys/organization/sys_org_dept/sysOrgDept.do?method=invalidatedAll" requestMethod="POST">
						<!-- 禁用 -->
							<a class="btn_txt" href="javascript:invalidated('${sysOrgDept.fdId}')" title="${ lfn:message('sys-organization:sys.org.available.false') }">${ lfn:message('sys-organization:sys.org.available.false') }</a>
					</kmss:auth>
					<kmss:auth requestURL="/sys/organization/sys_org_org/sysOrgOrg.do?method=updateDeptToOrg" requestMethod="POST">
						<!-- 转机构 -->
							<a class="btn_txt" href="javascript:toOrg('${sysOrgDept.fdId}')" title="${ lfn:message('sys-organization:sys.org.operations.toOrg') }">${ lfn:message('sys-organization:sys.org.operations.toOrg') }</a>
					</kmss:auth>
					</c:if>
					<% if (!TripartiteAdminUtil.IS_ENABLED_TRIPARTITE_ADMIN) { %>
					<!-- 日志 -->
					<a class="btn_txt" href="javascript:viewLog('${sysOrgDept.fdId}', '${sysOrgDept.fdName}')" title="${ lfn:message('sys-organization:sys.org.operations.log') }">${ lfn:message('sys-organization:sys.org.operations.log') }</a>
					<% } %>
					<% if (SysOrgEcoUtil.IS_ENABLED_ECO) { %>
					<kmss:auth requestURL="/sys/organization/sys_org_dept/sysOrgDept.do?method=edit&fdId=${sysOrgDept.fdId}" requestMethod="GET">
						<!-- 转外部组织 -->
						<a class="btn_txt" title="${ lfn:message('sys-organization:sys.org.operations.toOutDept') }" href="javascript:transformOut('${sysOrgDept.fdId}', '${sysOrgDept.fdName}')">${ lfn:message('sys-organization:sys.org.operations.toOutDept') }</a>
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