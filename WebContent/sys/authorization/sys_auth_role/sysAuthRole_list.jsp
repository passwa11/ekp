<%@ page import="com.landray.kmss.sys.authorization.util.TripartiteAdminUtil"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<list:data>
	<!-- 表格数据 -->
	<list:data-columns var="sysAuthRole" list="${queryPage.list }">
		<list:data-column property="fdId" />
		<!-- 角色名 -->
		<list:data-column headerClass="width300" property="fdName" title="${ lfn:message('sys-authorization:sysAuthRole.fdName') }">
		</list:data-column>
		<list:data-column headerClass="width300" col="fdTemplateName" title="${ lfn:message('sys-authorization:sysAuthTemplate.fdName') }">
			${sysAuthRole.fdName}
		</list:data-column>
		<!-- 所属分类 -->
		<list:data-column headerClass="width200" col="sysAuthCategory" title="${ lfn:message('sys-authorization:sysAuthRole.sysAuthCategory') }">
			${sysAuthRole.sysAuthCategory.fdName}
		</list:data-column>
		<!-- 描述 -->
		<list:data-column headerClass="width200" property="fdDescription" title="${ lfn:message('sys-authorization:sysAuthRole.fdDescription') }">
		</list:data-column>
		<!-- 创建人 -->
		<list:data-column headerClass="width100" col="fdCreator" title="${ lfn:message('model.fdCreator') }" escape="false">
		 	<ui:person personId="${sysAuthRole.fdCreator.fdId}" personName="${sysAuthRole.fdCreator.fdName}"></ui:person>
		</list:data-column>
		<list:data-column headerClass="width200" col="authEditorNames" title="${ lfn:message('sys-authorization:sysAuthTemplate.authEditors') }" escape="false">
		 	
		</list:data-column>
		<!-- 创建时间 -->
		<list:data-column headerClass="width100" col="docCreateTime" title="${ lfn:message('model.fdCreateTime') }">
		    <kmss:showDate value="${sysAuthRole.docCreateTime}" type="date"/>
		</list:data-column>
		<!-- 其它操作 -->
		<list:data-column headerClass="width200" col="operations" title="${ lfn:message('list.operation') }" escape="false">
			<!--操作按钮 开始-->
			<div class="conf_show_more_w">
				<div class="conf_btn_edit">
				      <c:if test='${param.fdTemplate == "1"}'>
						<kmss:auth requestURL="/sys/authorization/sys_auth_role/sysAuthRole.do?method=authRole&fdId=${sysAuthRole.fdId}" requestMethod="GET">
							<!-- 角色分配 -->
							<a class="btn_txt" href="javascript:authRole('${sysAuthRole.fdId}')">${lfn:message('sys-authorization:table.sysAuthRole')}</a>
						</kmss:auth>
					</c:if>
					<kmss:auth requestURL="/sys/authorization/sys_auth_role/sysAuthRole.do?method=edit&fdId=${sysAuthRole.fdId}" requestMethod="GET">
						<!-- 编辑 -->
						<a class="btn_txt" href="javascript:edit('${sysAuthRole.fdId}')">${lfn:message('button.edit')}</a>
					</kmss:auth>
					<kmss:auth requestURL="/sys/authorization/sys_auth_role/sysAuthRole.do?method=deleteall" requestMethod="POST">
						<!-- 删除 -->
						<a class="btn_txt" href="javascript:del('${sysAuthRole.fdId}')">${lfn:message('button.delete')}</a>
					</kmss:auth>
					<% if (!TripartiteAdminUtil.IS_ENABLED_TRIPARTITE_ADMIN) { %>
					<!-- 日志 -->
					<a class="btn_txt" href="javascript:viewLog('${sysAuthRole.fdId}', '<c:out value="${sysAuthRole.fdName }"></c:out>')">${lfn:message('sys-authorization:sysAuthAreaTransfer.button.log')}</a>
					<% } %>
				</div>
			</div>
			<!--操作按钮 结束-->
		</list:data-column>
	</list:data-columns>

	<!-- 分页 -->
	<list:data-paging currentPage="${queryPage.pageno }" pageSize="${queryPage.rowsize }" totalSize="${queryPage.totalrows }" />
</list:data>