<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<list:data>
	<!-- 表格数据 -->
	<list:data-columns var="sysRuleSetDoc" list="${queryPage.list }">
		<list:data-column property="fdId" />
		<!-- 排序号 -->
		<list:data-column headerClass="width80"  property="fdOrder" title="${ lfn:message('sys-rule:sysRuleSetDoc.fdOrder') }">
		</list:data-column>
		<!-- 规则集类别 -->
		<list:data-column headerClass="width200" property="sysRuleSetCate.fdName" title="${ lfn:message('sys-rule:sysRuleSetDoc.sysRuleSetCate') }">
		</list:data-column>
		<!-- 规则集名称 -->
		<list:data-column headerClass="width200" property="fdName" title="${ lfn:message('sys-rule:sysRuleSetDoc.fdName') }">
		</list:data-column>
		<!-- 是否禁用 -->
		<list:data-column headerClass="width100" col="fdIsAvailable" title="${ lfn:message('sys-rule:sysRuleSetDoc.fdIsAvailable') }">
			<sunbor:enumsShow value="${sysRuleSetDoc.fdIsAvailable}" enumsType="sys_rule_available" />
		</list:data-column>
		<!-- 其它操作 -->
		<list:data-column headerClass="width200" col="operations" title="${ lfn:message('list.operation') }" escape="false">
			<!--操作按钮 开始-->
			<div class="conf_show_more_w">
				<div class="conf_btn_edit">
					<!-- 编辑 -->
					<kmss:auth requestURL="/sys/rule/sys_ruleset_doc/sysRuleSetDoc.do?method=edit&fdId=${sysRuleSetDoc.fdId}" requestMethod="GET">
					<a class="btn_txt" href="javascript:edit('${sysRuleSetDoc.fdId}')">${lfn:message('button.edit')}</a>
					</kmss:auth>
					<kmss:auth requestURL="/sys/rule/sys_ruleset_doc/sysRuleSetDoc.do?method=edit&fdId=${sysRuleSetDoc.fdId}" requestMethod="GET">
					<c:if test="${sysRuleSetDoc.fdIsAvailable}">
						<!-- 禁用 -->
						<a class="btn_txt" href="javascript:invalidated('${sysRuleSetDoc.fdId}')">${ lfn:message('sys-rule:sysRuleSetDoc.available.result.false') }</a>
					</c:if>
					</kmss:auth>
					<kmss:auth requestURL="/sys/rule/sys_ruleset_doc/sysRuleSetDoc.do?method=deleteall&List_Selected=${sysRuleSetDoc.fdId}" requestMethod="GET">
					<a class="btn_txt" href="javascript:del('${sysRuleSetDoc.fdId}')">${lfn:message('button.delete')}</a>
					</kmss:auth>
				</div>
			</div>
			<!--操作按钮 结束-->
		</list:data-column>
	</list:data-columns>

	<!-- 分页 -->
	<list:data-paging currentPage="${queryPage.pageno }" pageSize="${queryPage.rowsize }" totalSize="${queryPage.totalrows }" />
</list:data>