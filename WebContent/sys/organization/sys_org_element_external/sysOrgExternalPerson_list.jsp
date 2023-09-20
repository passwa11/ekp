<%@ page import="com.landray.kmss.sys.authorization.util.TripartiteAdminUtil"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<list:data>
	<!-- 表格数据 -->
	<list:data-columns var="sysOrgPerson" list="${queryPage.list }">
		<list:data-column property="fdId" />
		<!-- 排序号 -->
		<list:data-column headerClass="width80"  col="fdOrder" title="${ lfn:message('sys-organization:sysOrgPerson.fdOrder') }">
			${sysOrgPerson.fdOrder}
		</list:data-column>
		<!-- 所在组织 -->
		<list:data-column headerClass="width200" col="fdName" title="${ lfn:message('sys-organization:sysOrgElementExternal.fdParent') }" escape="false">
		    <pre>${sysOrgPerson.fdParent.fdName}</pre>
		</list:data-column>
		<!-- 人员名称 -->
		<list:data-column headerClass="width200" col="fdName" title="${ lfn:message('sys-organization:sysOrgPerson.fdName') }" escape="false">
		    <pre>${sysOrgPerson.fdName}</pre>
		</list:data-column>
		<!-- 登录名 -->
		<list:data-column headerClass="width200" property="fdLoginName" title="${ lfn:message('sys-organization:sysOrgPerson.fdLoginName') }">
		</list:data-column>
		<!-- 是否登录系统 -->
		<list:data-column headerClass="width100" col="fdCanLogin" title="${ lfn:message('sys-organization:sysOrgPerson.fdCanLogin') }">
			<sunbor:enumsShow value="${sysOrgPerson.fdCanLogin}" enumsType="common_yesno" />
		</list:data-column>
		<!-- 手机 -->
		<list:data-column headerClass="width100" property="fdMobileNo" title="${ lfn:message('sys-organization:sysOrgPerson.fdMobileNo') }">
		</list:data-column>
		<!-- 自定义显示属性 -->
		<c:forEach items="${props}" var="prop">
			<c:if test="${'true' eq prop.fdShowList && 'true' eq prop.fdStatus}">
			<list:data-column headerClass="width100" col="${prop.fdFieldName}" title="${prop.fdName}" escape="false">
			    <pre>${sysOrgPerson.dynamicMap[prop.fdFieldName]}</pre>
			</list:data-column>
			</c:if>
		</c:forEach>
		<!-- 其它操作 -->
		<list:data-column headerClass="width200" col="operations" title="${ lfn:message('list.operation') }" escape="false">
			<!--操作按钮 开始-->
			<div class="conf_show_more_w">
				<div class="conf_btn_edit">
					<kmss:auth requestURL="/sys/organization/sys_org_element_external/sysOrgElementExternalPerson.do?method=edit&fdId=${sysOrgPerson.fdId}" requestMethod="GET">
						<!-- 编辑 -->
						<a class="btn_txt" href="javascript:edit('${sysOrgPerson.fdId}')">${lfn:message('button.edit')}</a>
					</kmss:auth>
					<c:if test="${'true' eq sysOrgPerson.fdIsAvailable}">
					<kmss:auth requestURL="/sys/organization/sys_org_element_external/sysOrgElementExternalPerson.do?method=invalidated&fdId=${sysOrgPerson.fdId}" requestMethod="POST">
						<!-- 禁用 -->
						<a class="btn_txt" href="javascript:invalidated('${sysOrgPerson.fdId}')">${ lfn:message('sys-organization:sys.org.available.false') }</a>
					</kmss:auth>
					</c:if>
					<% if (!TripartiteAdminUtil.IS_ENABLED_TRIPARTITE_ADMIN) { %>
					<kmss:auth requestURL="/sys/organization/sys_log_organization/index.jsp?fdId=${sysOrgPerson.fdId}" requestMethod="GET">
					<!-- 日志 -->
					<a class="btn_txt" href="javascript:viewLog('${sysOrgPerson.fdId}', '${sysOrgPerson.fdName}')">${ lfn:message('sys-organization:sys.org.operations.log') }</a>
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