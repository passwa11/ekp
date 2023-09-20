<%@ page import="com.landray.kmss.sys.authorization.util.TripartiteAdminUtil"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<list:data>
	<!-- 表格数据 -->
	<list:data-columns var="sysOrgGroup" list="${queryPage.list }">
		<list:data-column property="fdId" />
		<!-- 排序号 -->
		<list:data-column headerClass="width30"  property="fdOrder" title="${ lfn:message('sys-organization:sysOrgGroup.fdOrder') }">
		</list:data-column>
		<!-- 群组类别 -->
		<list:data-column headerClass="width200" col="fdGroupCate" title="${ lfn:message('sys-organization:sysOrgGroup.fdGroupCate') }" escape="false">
			<pre>${sysOrgGroup.fdGroupCate.fdName}</pre>
		</list:data-column>
		<!-- 群组名称 -->
		<list:data-column col="fdName" title="${ lfn:message('sys-organization:sysOrgGroup.fdName') }" escape="false">
		    <pre>${lfn:escapeHtml(sysOrgGroup.fdName)}</pre>
		</list:data-column>
		<!-- 编号 -->
		<list:data-column headerClass="width100" property="fdNo" title="${ lfn:message('sys-organization:sysOrgGroup.fdNo') }">
		</list:data-column>
		<!-- 备注 -->
		<list:data-column headerClass="width200" col="fdMemo" title="${ lfn:message('sys-organization:sysOrgGroup.fdMemo') }" escape="false">
		    <pre>${lfn:escapeHtml(sysOrgGroup.fdMemo)}</pre>
		</list:data-column>
		<!-- 其它操作 -->
		<list:data-column headerClass="width200" col="operations" title="${ lfn:message('list.operation') }" escape="false">
			<!--操作按钮 开始-->
			<div class="conf_show_more_w">
				<div class="conf_btn_edit">
					<kmss:auth requestURL="/sys/organization/sys_org_group/sysOrgGroup.do?method=edit&fdId=${sysOrgGroup.fdId}" requestMethod="GET">
						<!-- 编辑 -->
						<a class="btn_txt" href="javascript:edit('${sysOrgGroup.fdId}')">${lfn:message('button.edit')}</a>
					</kmss:auth>
					<c:if test="${param.available != '0'}">
					<kmss:auth requestURL="/sys/organization/sys_org_group/sysOrgGroup.do?method=invalidatedAll" requestMethod="POST">
						<!-- 禁用 -->
						<a class="btn_txt" href="javascript:invalidated('${sysOrgGroup.fdId}')">${ lfn:message('sys-organization:sys.org.available.false') }</a>
					</kmss:auth>
					</c:if>
					<% if (!TripartiteAdminUtil.IS_ENABLED_TRIPARTITE_ADMIN) { %>
					<!-- 日志 -->
					<a class="btn_txt" href="javascript:viewLog('${sysOrgGroup.fdId}', '${sysOrgGroup.fdName}')">${ lfn:message('sys-organization:sys.org.operations.log') }</a>
					<% } %>
				</div>
			</div>
			<!--操作按钮 结束-->
		</list:data-column>
	</list:data-columns>

	<!-- 分页 -->
	<list:data-paging currentPage="${queryPage.pageno }" pageSize="${queryPage.rowsize }" totalSize="${queryPage.totalrows }" />
</list:data>