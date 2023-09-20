<%@ page import="com.landray.kmss.sys.authorization.util.TripartiteAdminUtil"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<list:data>
	<!-- 表格数据 -->
	<list:data-columns var="external" list="${queryPage.list }">
		<list:data-column property="fdId" />
		<list:data-column col="auth">
			${external.dynamicMap['auth']}
		</list:data-column>
		<!-- 排序号 -->
		<list:data-column headerClass="width80"  col="fdOrder" title="${ lfn:message('sys-organization:sysOrgOrg.fdOrder') }">
			${external.fdElement.fdOrder}
		</list:data-column>
		<!-- 组织类型名称 -->
		<list:data-column headerClass="width200" col="fdName" title="${ lfn:message('sys-organization:sysOrgEco.fdName') }" escape="false">
			${external.fdElement.fdName}
		</list:data-column>
		<!-- 编号 -->
		<list:data-column headerClass="width100" col="fdNo" title="${ lfn:message('sys-organization:sysOrgOrg.fdNo') }">
			${external.fdElement.fdNo}
		</list:data-column>
		<!-- 组织人数 -->
		<list:data-column headerClass="width100" col="fdPersonCount" title="${ lfn:message('sys-organization:sysOrgElementExternal.personCount') }">
	    	<%=com.landray.kmss.sys.organization.util.SysOrgUtil.getPersonCountByOutOrgDept(((com.landray.kmss.sys.organization.model.SysOrgElementExternal)pageContext.getAttribute("external")).getFdElement())%>
	    </list:data-column>
	    <!-- 管理员 -->
		<list:data-column headerClass="width100" col="fdNo" title="${ lfn:message('sys-organization:sysOrgElement.authElementAdmins') }">
			<c:forEach items="${external.fdElement.authElementAdmins}" var="admin" varStatus="idx"><c:if test="${ idx.index > 0 }">,</c:if>${admin.fdName}</c:forEach>
		</list:data-column>
		<!-- 其它操作 -->
		<list:data-column headerClass="width100" col="operations" title="${ lfn:message('list.operation') }" escape="false">
			<!--操作按钮 开始-->
			<div class="conf_show_more_w">
				<div class="conf_btn_edit">
					<kmss:auth requestURL="/sys/organization/sys_org_element_external/sysOrgElementExternal.do?method=edit&fdId=${external.fdId}" requestMethod="GET">
						<!-- 编辑 -->
						<a class="btn_txt" href="javascript:edit('${external.fdId}')">${lfn:message('button.edit')}</a>
					</kmss:auth>
					<kmss:auth requestURL="/sys/organization/sys_org_element_external/sysOrgElementExternal.do?method=changeElemStatus&fdId=${external.fdId}" requestMethod="POST">
						<!-- 启用/禁用 -->
						<a class="btn_txt" href="javascript:changeElemStatus('${external.fdElement.fdIsAvailable == "true" ? false : true }', '${external.fdId}')">
						<c:if test="${'true' eq external.fdElement.fdIsAvailable}">${ lfn:message('sys-organization:sysOrgEco.available.close') }</c:if>
						<c:if test="${'false' eq external.fdElement.fdIsAvailable}">${ lfn:message('sys-organization:sysOrgEco.available.open') }</c:if>
						</a>
					</kmss:auth>
					<%-- 
					<kmss:auth requestURL="/sys/organization/sys_org_element_external/sysOrgElementExternal.do?method=edit&fdId=${external.fdId}" requestMethod="GET">
						<!-- 修改扩展属性字段 -->
						<a class="btn_txt" href="javascript:repair('${external.fdId}')">${lfn:message('sys-organization:sysOrgElementExternal.ext.prop.repair')}</a>
					</kmss:auth>
					 --%>
				</div>
			</div>
			<!--操作按钮 结束-->
		</list:data-column>
	</list:data-columns>

	<!-- 分页 -->
	<list:data-paging currentPage="${queryPage.pageno }" pageSize="${queryPage.rowsize }" totalSize="${queryPage.totalrows }" />
</list:data>