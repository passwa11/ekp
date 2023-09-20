<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<list:data>
	<!-- 表格数据 -->
	<list:data-columns var="sysAuthCategory" list="${queryPage.list }">
		<list:data-column property="fdId" />
		<!-- 名称 -->
		<list:data-column headerClass="width300" property="fdName" title="${ lfn:message('sys-authorization:sysAuthCategory.fdName') }">
		</list:data-column>
		<!-- 排序号 -->
		<list:data-column headerClass="width30"  property="fdOrder" title="${ lfn:message('sys-authorization:sysAuthCategory.fdOrder') }">
		</list:data-column>
		<!-- 创建人 -->
		<list:data-column headerClass="width100" col="docCreator" title="${ lfn:message('model.fdCreator') }" escape="false">
		 	<ui:person personId="${sysAuthCategory.docCreator.fdId}" personName="${sysAuthCategory.docCreator.fdName}"></ui:person>
		</list:data-column>
		<!-- 创建时间 -->
		<list:data-column headerClass="width100" col="docCreateTime" title="${ lfn:message('model.fdCreateTime') }">
		    <kmss:showDate value="${sysAuthCategory.docCreateTime}" type="date"/>
		</list:data-column>
		<!-- 其它操作 -->
		<list:data-column headerClass="width200" col="operations" title="${ lfn:message('list.operation') }" escape="false">
			<!--操作按钮 开始-->
			<div class="conf_show_more_w">
				<div class="conf_btn_edit">
					<kmss:auth requestURL="/sys/authorization/sys_auth_category/sysAuthCategory.do?method=edit&fdId=${sysAuthCategory.fdId}" requestMethod="GET">
						<!-- 编辑 -->
						<a class="btn_txt" href="javascript:edit('${sysAuthCategory.fdId}')">${lfn:message('button.edit')}</a>
					</kmss:auth>
					<kmss:auth requestURL="/sys/authorization/sys_auth_category/sysAuthCategory.do?method=deleteall" requestMethod="POST">
						<!-- 删除 -->
						<a class="btn_txt" href="javascript:del('${sysAuthCategory.fdId}')">${lfn:message('button.delete')}</a>
					</kmss:auth>
				</div>
			</div>
			<!--操作按钮 结束-->
		</list:data-column>
	</list:data-columns>

	<!-- 分页 -->
	<list:data-paging currentPage="${queryPage.pageno }" pageSize="${queryPage.rowsize }" totalSize="${queryPage.totalrows }" />
</list:data>