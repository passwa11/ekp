<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<list:data>
	<!-- 表格数据 -->
	<list:data-columns var="sysOrgRole" list="${queryPage.list }">
		<list:data-column property="fdId" />
		<!-- 排序号 -->
		<list:data-column headerClass="width30"  property="fdOrder" title="${ lfn:message('sys-organization:sysOrgRole.fdOrder') }">
		</list:data-column>
		<!-- 名称 -->
		<list:data-column headerClass="width300" property="fdName" title="${ lfn:message('sys-organization:sysOrgRole.fdName') }">
		</list:data-column>
		<!-- 描述 -->
		<list:data-column headerClass="width200" property="fdMemo" title="${ lfn:message('sys-organization:sysOrgRole.fdDescription') }">
		</list:data-column>
		<!-- 是否有效 -->
		<list:data-column headerClass="width80" col="fdIsAvailable" title="${ lfn:message('sys-organization:sysOrgRole.fdIsAvailable') }">
			<sunbor:enumsShow value="${sysOrgRole.fdIsAvailable}" enumsType="sys_org_available_result" />
		</list:data-column>
		<!-- 创建时间 -->
		<list:data-column headerClass="width100" col="fdCreateTime" title="${ lfn:message('model.fdCreateTime') }">
		    <kmss:showDate value="${sysOrgRole.fdCreateTime}" type="date"/>
		</list:data-column>
		<!-- 其它操作 -->
		<list:data-column headerClass="width120" col="operations" title="${ lfn:message('list.operation') }" escape="false">
			<!--操作按钮 开始-->
			<div class="conf_show_more_w">
				<div class="conf_btn_edit">
					<kmss:auth requestURL="/sys/organization/sys_org_role/sysOrgRole.do?method=edit&fdId=${sysOrgRole.fdId}" requestMethod="GET">
						<!-- 编辑 -->
						<a class="btn_txt" href="javascript:edit('${sysOrgRole.fdId}')">${lfn:message('button.edit')}</a>
					</kmss:auth>
					<kmss:auth requestURL="/sys/organization/sys_org_role/sysOrgRole.do?method=invalidatedAll" requestMethod="POST">
						<c:choose>
							<c:when test="${sysOrgRole.fdIsAvailable}">
								<!-- 禁用 -->
								<a class="btn_txt" href="javascript:chgenabled(false, '${sysOrgRole.fdId}')">${ lfn:message('sys-organization:sys.org.available.false')}</a>
							</c:when>
							<c:otherwise>
								<!-- 启用-->
								<a class="btn_txt" href="javascript:chgenabled(true, '${sysOrgRole.fdId}')">${ lfn:message('sys-organization:sys.org.available.true')}</a>
							</c:otherwise>
						</c:choose>
					</kmss:auth>
				</div>
			</div>
			<!--操作按钮 结束-->
		</list:data-column>
	</list:data-columns>

	<!-- 分页 -->
	<list:data-paging currentPage="${queryPage.pageno }" pageSize="${queryPage.rowsize }" totalSize="${queryPage.totalrows }" />
</list:data>