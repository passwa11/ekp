<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<list:data>
	<!-- 表格数据 -->
	<list:data-columns var="sysAuthArea" list="${queryPage.list }">
		<list:data-column property="fdId" />
		<!-- 场所名称 -->
		<list:data-column headerClass="width200" property="fdName" title="${ lfn:message('sys-authorization:sysAuthArea.fdName') }">
		</list:data-column>
		<!-- 上级场所 -->
		<list:data-column headerClass="width200" col="fdParent" title="${ lfn:message('sys-authorization:sysAuthArea.fdParent') }">
			${sysAuthArea.fdParent.fdName}
		</list:data-column>
		<!-- 所属组织 -->
		<list:data-column headerClass="width200" col="authAreaOrg" title="${ lfn:message('sys-authorization:sysAuthArea.authAreaOrg') }">
			<c:forEach items="${sysAuthArea.authAreaOrg}" var="authAreaOrg" varStatus="idx">
				<c:if test="${ idx.index > 0 }">,</c:if>
				${ authAreaOrg.fdName }
			</c:forEach>
		</list:data-column>
		<!-- 场所管理员 -->
		<list:data-column headerClass="width300" col="authAreaAdmin" title="${ lfn:message('sys-authorization:sysAuthArea.authAreaAdmin') }">
			<c:forEach items="${sysAuthArea.authAreaAdmin}" var="authAreaAdmin" varStatus="idx">
				<c:if test="${ idx.index > 0 }">,</c:if>
				${ authAreaAdmin.fdName }
			</c:forEach>
		</list:data-column>
		<!-- 其它操作 -->
		<list:data-column headerClass="width200" col="operations" title="${ lfn:message('list.operation') }" escape="false">
			<!--操作按钮 开始-->
			<div class="conf_show_more_w">
				<div class="conf_btn_edit">
					<kmss:auth requestURL="/sys/authorization/sys_auth_area/sysAuthArea.do?method=edit&fdId=${sysAuthArea.fdId}" requestMethod="GET">
						<!-- 编辑 -->
						<a class="btn_txt" href="javascript:edit('${sysAuthArea.fdId}')">${lfn:message('button.edit')}</a>
					</kmss:auth>
					<kmss:auth requestURL="/sys/authorization/sys_auth_area/sysAuthArea.do?method=deleteall" requestMethod="POST">
						<!-- 删除 -->
						<a class="btn_txt" href="javascript:del('${sysAuthArea.fdId}')">${lfn:message('button.delete')}</a>
					</kmss:auth>
				</div>
			</div>
			<!--操作按钮 结束-->
		</list:data-column>
	</list:data-columns>

	<!-- 分页 -->
	<list:data-paging currentPage="${queryPage.pageno }" pageSize="${queryPage.rowsize }" totalSize="${queryPage.totalrows }" />
</list:data>