<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<list:data>
	<list:data-columns var="sysMportalModuleCate" list="${queryPage.list }">
		<list:data-column property="fdId" />
		<list:data-column headerClass="width300" property="fdName" title="${ lfn:message('sys-mportal:sysMportalModuleCate.fdName') }">
		</list:data-column>
		<list:data-column headerClass="width30" property="fdOrder" title="${ lfn:message('sys-mportal:sysMportalModuleCate.fdOrder') }">
		</list:data-column>
		<list:data-column headerClass="width140" property="docCreateTime" title="${ lfn:message('sys-mportal:sysMportalModuleCate.docCreateTime') }">
		</list:data-column>
		<list:data-column headerClass="width140" property="docCreator.fdName" title="${ lfn:message('sys-mportal:sysMportalModuleCate.docCreator') }">
		</list:data-column>
		<list:data-column headerClass="width140" col="operations" title="${ lfn:message('list.operation') }" escape="false">
			<!--操作按钮 开始-->
			<div class="conf_show_more_w">
				<div class="conf_btn_edit">
					<kmss:auth requestURL="/sys/mportal/sys_mportal_module_cate/sysMportalModuleCate.do?method=edit&fdId=${sysMportalModuleCate.fdId}" requestMethod="GET">
						<!-- 编辑 -->
						<a class="btn_txt" href="javascript:edit('${sysMportalModuleCate.fdId}')">${lfn:message('button.edit')}</a>
					</kmss:auth>
					<kmss:auth requestURL="/sys/mportal/sys_mportal_module_cate/sysMportalModuleCate.do?method=delete&fdId=${sysMportalModuleCate.fdId}" requestMethod="GET">
						<!-- 删除 -->
						<a class="btn_txt" href="javascript:deleteAll('${sysMportalModuleCate.fdId}')">${lfn:message('button.delete')}</a>
					</kmss:auth>
				</div>
			</div>
			<!--操作按钮 结束-->
		</list:data-column>
	</list:data-columns>
	<list:data-paging currentPage="${queryPage.pageno }" pageSize="${queryPage.rowsize }" totalSize="${queryPage.totalrows }" />
</list:data>