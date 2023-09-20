<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<list:data>
	<!-- 表格数据 -->
	<list:data-columns var="sysOrganizationStaffingLevel" list="${queryPage.list }">
		<list:data-column property="fdId" />
		<!-- 职务 -->
		<list:data-column headerClass="width300"  property="fdName" title="${ lfn:message('sys-organization:sysOrganizationStaffingLevel.fdName') }">
		</list:data-column>
		<!-- 职务级别 -->
		<list:data-column headerClass="width100" property="fdLevel" title="${ lfn:message('sys-organization:sysOrganizationStaffingLevel.fdLevel') }">
		</list:data-column>
		<!-- 是否默认 -->
		<list:data-column headerClass="width100" col="fdIsDefault" title="${ lfn:message('sys-organization:sysOrganizationStaffingLevel.fdIsDefault') }" escape="false">
			<c:if test="${sysOrganizationStaffingLevel.fdIsDefault}">
				<img src='<c:url value="/sys/profile/resource/images/profile_list_status_y.png"/>'>
			</c:if>
		</list:data-column>
		<!-- 其它操作 -->
		<list:data-column headerClass="width200" col="operations" title="${ lfn:message('list.operation') }" escape="false">
			<!--操作按钮 开始-->
			<div class="conf_show_more_w">
				<div class="conf_btn_edit">
					<kmss:auth requestURL="/sys/organization/sys_organization_staffing_level/sysOrganizationStaffingLevel.do?method=edit&fdId=${sysOrganizationStaffingLevel.fdId}" requestMethod="GET">
						<!-- 编辑 -->
						<a class="btn_txt" href="javascript:edit('${sysOrganizationStaffingLevel.fdId}')">${lfn:message('button.edit')}</a>
					</kmss:auth>
					<kmss:auth requestURL="/sys/organization/sys_organization_staffing_level/sysOrganizationStaffingLevel.do?method=deleteall">
						<!-- 删除 -->
						<a class="btn_txt" href="javascript:del('${sysOrganizationStaffingLevel.fdId}')">${lfn:message('button.delete')}</a>
					</kmss:auth>
				</div>
			</div>
			<!--操作按钮 结束-->
		</list:data-column>
	</list:data-columns>

	<!-- 分页 -->
	<list:data-paging currentPage="${queryPage.pageno }" pageSize="${queryPage.rowsize }" totalSize="${queryPage.totalrows }" />
</list:data>