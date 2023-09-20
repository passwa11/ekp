<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<list:data>
	<!-- 表格数据 -->
	<list:data-columns var="sysRuleSetCate" list="${queryPage.list }">
		<list:data-column property="fdId" />
		<!-- 上級类别 -->
		<list:data-column headerClass="width300" property="fdParent.fdName" title="${ lfn:message('sys-rule:sysRuleSetCate.fdParent') }">
		</list:data-column>
		<!-- 类别名称 -->
		<list:data-column headerClass="width300" property="fdName" title="${ lfn:message('sys-rule:sysRuleSetCate.fdName') }">
		</list:data-column>
		<!-- 创建人 -->
		<list:data-column headerClass="width80" property="fdCreator.fdName" title="${ lfn:message('sys-rule:sysRuleSetCate.fdCreator') }">
		</list:data-column>
		<!-- 创建时间 -->
		<list:data-column headerClass="width120" property="fdCreateTime" title="${ lfn:message('sys-rule:sysRuleSetCate.fdCreateTime') }">
		</list:data-column>
		<!-- 其它操作 -->
		<list:data-column headerClass="width300" col="operations" title="${ lfn:message('list.operation') }" escape="false">
			<!--操作按钮 开始-->
			<div class="conf_show_more_w">
				<div class="conf_btn_edit">
					<kmss:auth requestURL="/sys/rule/sys_ruleset_cate/sysRuleSetCate.do?method=add&parentId=${sysRuleSetCate.fdId}" requestMethod="GET">
						<!-- 新建 -->
						<a class="btn_txt" href="javascript:add('${sysRuleSetCate.fdId}')">${lfn:message('button.add')}</a>
					</kmss:auth>
					<kmss:auth requestURL="/sys/rule/sys_ruleset_cate/sysRuleSetCate.do?method=edit&fdId=${sysRuleSetCate.fdId}" requestMethod="GET">
						<!-- 编辑 -->
						<a class="btn_txt" href="javascript:edit('${sysRuleSetCate.fdId}')">${lfn:message('button.edit')}</a>
					</kmss:auth>
					<kmss:auth requestURL="/sys/rule/sys_ruleset_cate/sysRuleSetCate.do?method=deleteall&List_Selected=${sysRuleSetCate.fdId}" requestMethod="GET">
						<!-- 删除 -->
						<a class="btn_txt" href="javascript:del('${sysRuleSetCate.fdId}')">${lfn:message('button.delete')}</a>
					</kmss:auth>
				</div>
			</div>
			<!--操作按钮 结束-->
		</list:data-column>
	</list:data-columns>

	<!-- 分页 -->
	<list:data-paging currentPage="${queryPage.pageno }" pageSize="${queryPage.rowsize }" totalSize="${queryPage.totalrows }" />
</list:data>