<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<list:data>
	<!-- 表格数据 -->
	<list:data-columns var="lbpmExtBusinessAuthCate" list="${queryPage.list }">
		<list:data-column property="fdId" />
		<!-- 父级分类 -->
		<list:data-column headerClass="" property="fdParent.fdName" title="${lfn:message('sys-lbpmext-businessauth:lbpmext.businessAuthCate.fdParent') }">
		</list:data-column>
		<!-- 类别名称 -->
		<list:data-column headerClass="" property="fdName" title="${lfn:message('sys-lbpmext-businessauth:lbpmext.businessAuthCate.fdName') }">
		</list:data-column>
		<!-- 创建人 -->
		<list:data-column headerClass="width80" property="fdCreator.fdName" title="${ lfn:message('model.fdCreator') }">
		</list:data-column>
		<!-- 创建时间 -->
		<list:data-column headerClass="width120" property="fdCreateTime" title="${ lfn:message('model.fdCreateTime') }">
		</list:data-column>
		<!-- 其它操作 -->
		<list:data-column headerClass="width120" col="operations" title="${ lfn:message('list.operation') }" escape="false">
			<!--操作按钮 开始-->
			<div class="conf_show_more_w">
				<div class="conf_btn_edit">
					<kmss:auth requestURL="/sys/lbpmext/businessauth/lbpmBusinessAuthCate.do?method=edit&fdId=${lbpmExtBusinessAuthCate.fdId}" requestMethod="GET">
						<!-- 编辑 -->
						<a class="btn_txt" href="javascript:edit('${lbpmExtBusinessAuthCate.fdId}')">${lfn:message('button.edit')}</a>
					</kmss:auth>
					<kmss:auth requestURL="/sys/lbpmext/businessauth/lbpmBusinessAuthCate.do?method=delete&fdId=${lbpmExtBusinessAuthCate.fdId}" requestMethod="GET">
						<!-- 删除 -->
						<a class="btn_txt" href="javascript:delDoc('${lbpmExtBusinessAuthCate.fdId}')">${lfn:message('button.delete')}</a>
					</kmss:auth>
				</div>
			</div>
			<!--操作按钮 结束-->
		</list:data-column>
	</list:data-columns>

	<!-- 分页 -->
	<list:data-paging currentPage="${queryPage.pageno }" pageSize="${queryPage.rowsize }" totalSize="${queryPage.totalrows }" />
</list:data>