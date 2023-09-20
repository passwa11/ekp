<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<list:data>
	<list:data-columns var="sysUnitGroup" list="${queryPage.list }">
		<list:data-column property="fdId" />
		<list:data-column  property="fdName" title="机构组名称" style="min-width:100px"></list:data-column>
		<list:data-column headerClass="width80" property="fdOrder" title="排序号"></list:data-column>
		<list:data-column headerClass="width100" property="docCreator.fdName" title="创建人">
		</list:data-column>
		<list:data-column headerClass="width140" property="docCreateTime" title="创建时间">
		</list:data-column>
		<list:data-column  property="fdDesc" title="备注" style="min-width:100px"></list:data-column>
		<!-- 其它操作 -->
		<list:data-column headerClass="width140" col="operations" title="更多操作" escape="false">
			<!--操作按钮 开始-->
			<div class="conf_show_more_w">
				<div class="conf_btn_edit">
					<kmss:auth requestURL="/sys/unit/sys_unit_group/sysUnitGroup.do?method=edit&fdId=${sysUnitGroup.fdId}" requestMethod="GET">
						<!-- 编辑 -->
						<a class="btn_txt" href="javascript:edit('${sysUnitGroup.fdId}')">${lfn:message('button.edit')}</a>
					</kmss:auth>
					<kmss:auth requestURL="/sys/unit/sys_unit_group/sysUnitGroup.do?method=delete&fdId=${sysUnitGroup.fdId}" requestMethod="POST">
						<!-- 禁用 -->
						<a class="btn_txt" href="javascript:deleteAll('${sysUnitGroup.fdId}')">删除</a>
					</kmss:auth>
				</div>
			</div>
			<!--操作按钮 结束-->
		</list:data-column>
	</list:data-columns>
	<!-- 分页 -->
	<list:data-paging currentPage="${queryPage.pageno}" pageSize="${queryPage.rowsize}" totalSize="${queryPage.totalrows}" />
</list:data>