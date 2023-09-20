<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<list:data>
	<!-- 表格数据 -->
	<list:data-columns var="sysOrgGroupCate" list="${queryPage.list }">
		<list:data-column property="fdId" />
		<!-- 父类别 -->
		<list:data-column headerClass="width300" col="fdParent" title="${ lfn:message('sys-organization:sysOrgGroupCate.fdParent') }">
			${sysOrgGroupCate.fdParent.fdName}
		</list:data-column>
		<!-- 类别名称 -->
		<list:data-column headerClass="width300" property="fdName" title="${ lfn:message('sys-organization:sysOrgGroupCate.fdName') }">
		</list:data-column>
		<!-- 其它操作 -->
		<list:data-column headerClass="width300" col="operations" title="${ lfn:message('list.operation') }" escape="false">
			<!--操作按钮 开始-->
			<div class="conf_show_more_w">
				<div class="conf_btn_edit">
					<kmss:auth requestURL="/sys/organization/sys_org_group_cate/sysOrgGroupCate.do?method=edit&fdId=${sysOrgGroupCate.fdId}" requestMethod="GET">
						<!-- 编辑 -->
						<a class="btn_txt" href="javascript:edit('${sysOrgGroupCate.fdId}')">${lfn:message('button.edit')}</a>
					</kmss:auth>
					<kmss:auth requestURL="/sys/organization/sys_org_group_cate/sysOrgGroupCate.do?method=delete&fdId=${sysOrgGroupCate.fdId}" requestMethod="GET">
						<!-- 删除 -->
						<a class="btn_txt" href="javascript:del('${sysOrgGroupCate.fdId}')">${lfn:message('button.delete')}</a>
					</kmss:auth>
				</div>
			</div>
			<!--操作按钮 结束-->
		</list:data-column>
	</list:data-columns>

	<!-- 分页 -->
	<list:data-paging currentPage="${queryPage.pageno }" pageSize="${queryPage.rowsize }" totalSize="${queryPage.totalrows }" />
</list:data>