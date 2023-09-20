<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<list:data>
	<!-- 表格数据 -->
	<list:data-columns var="lbpmExtBusinessAuth" list="${queryPage.list }">
		<list:data-column property="fdId" />
		<!-- 分类名称 -->
		<list:data-column headerClass="width160" property="fdCategroy.fdName" title="${lfn:message('sys-lbpmext-businessauth:lbpmext.businessAuthCate') }">
		</list:data-column>
		<!-- 条目编号 -->
		<list:data-column headerClass="width160" property="fdNumber" title="${lfn:message('sys-lbpmext-businessauth:lbpmext.businessAuth.fdNumber') }">
		</list:data-column>
		<!-- 条目名称 -->
		<list:data-column headerClass="" property="fdName" title="${lfn:message('sys-lbpmext-businessauth:lbpmext.businessAuth.fdName') }">
		</list:data-column>
		<!-- 是否可转授 -->
		<list:data-column headerClass="width80" col="fdIsNotDelegate" title="${lfn:message('sys-lbpmext-businessauth:lbpmext.businessAuth.isCanDelegate') }" escape="false">
		    <c:if test="${lbpmExtBusinessAuth.fdIsNotDelegate}">
				${lfn:message('sys-lbpmext-businessauth:lbpmext.businessAuth.isCanDelegate.no') }
			</c:if>
			<c:if test="${!lbpmExtBusinessAuth.fdIsNotDelegate}">
				${lfn:message('sys-lbpmext-businessauth:lbpmext.businessAuth.isCanDelegate.yes') }
			</c:if>
		</list:data-column>
		<!-- 控制方式 -->
		<list:data-column headerClass="width80" property="fdTypeName" title="${lfn:message('sys-lbpmext-businessauth:lbpmext.businessAuth.fdType') }">
		</list:data-column>
		<!-- 其它操作 -->
		<list:data-column headerClass="width120" col="operations" title="${ lfn:message('list.operation') }" escape="false">
			<!--操作按钮 开始-->
			<div class="conf_show_more_w">
				<div class="conf_btn_edit">
					<kmss:auth requestURL="/sys/lbpmext/businessauth/lbpmBusinessAuth.do?method=edit&fdId=${lbpmExtBusinessAuth.fdId}" requestMethod="GET">
						<!-- 编辑 -->
						<a class="btn_txt" href="javascript:edit('${lbpmExtBusinessAuth.fdId}')">${lfn:message('button.edit')}</a>
					</kmss:auth>
					<kmss:auth requestURL="/sys/lbpmext/businessauth/lbpmBusinessAuth.do?method=delete&fdId=${lbpmExtBusinessAuth.fdId}" requestMethod="GET">
						<!-- 删除 -->
						<a class="btn_txt" href="javascript:delDoc('${lbpmExtBusinessAuth.fdId}')">${lfn:message('button.delete')}</a>
					</kmss:auth>
				</div>
			</div>
			<!--操作按钮 结束-->
		</list:data-column>
	</list:data-columns>

	<!-- 分页 -->
	<list:data-paging currentPage="${queryPage.pageno }" pageSize="${queryPage.rowsize }" totalSize="${queryPage.totalrows }" />
</list:data>