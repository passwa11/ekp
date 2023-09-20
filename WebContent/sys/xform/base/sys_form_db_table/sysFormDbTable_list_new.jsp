<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<list:data>
	<list:data-columns var="sysFormDbTable" list="${queryPage.list }">
		<list:data-column property="fdId" />
		<list:data-column property="fdKey" />
		<list:data-column property="fdModelName" />
		<list:data-column property="fdFormType" />
		<list:data-column property="fdTemplateModel" />
		<list:data-column property="fdFormId" />
		<list:data-column property="fdModelId" />
		<list:data-column  property="fdName" title="${ lfn:message('sys-xform:sysFormDbTable.fdName') }" style="text-align:left;min-width:180px">
		</list:data-column>
		<list:data-column headerClass="width180" property="fdTable" title="${ lfn:message('sys-xform:sysFormDbTable.fdTable') }">
		</list:data-column>
		<list:data-column headerClass="width100" property="docCreator.fdName" title="${ lfn:message('sys-xform:sysFormDbTable.docCreator') }">
		</list:data-column>
		<list:data-column headerClass="width140" property="docCreateTime" title="${ lfn:message('sys-xform:sysFormDbTable.docCreateTime') }">
		</list:data-column>
		<!-- 其它操作 -->
		<list:data-column headerClass="width120" col="operations" title="${ lfn:message('list.operation') }" escape="false">
			<!--操作按钮 开始-->
			<div class="conf_show_more_w">
				<div class="conf_btn_edit">
					<kmss:auth requestURL="/sys/xform/base/sys_form_db_table/sysFormDbTable.do?method=edit&fdId=${sysFormDbTable.fdId}" requestMethod="GET">
						<!-- 编辑 -->
						<a class="btn_txt" href="javascript:edit('${sysFormDbTable.fdId}')">${lfn:message('button.edit')}</a>
					</kmss:auth>
					<kmss:auth requestURL="/sys/xform/base/sys_form_db_table/sysFormDbTable.do?method=delete&fdId=${sysFormDbTable.fdId}" requestMethod="POST">
						<!-- 删除 -->
						<a class="btn_txt" href="javascript:deleteAll('${sysFormDbTable.fdId}')">${lfn:message('button.delete')}</a>
					</kmss:auth>
				</div>
			</div>
			<!--操作按钮 结束-->
		</list:data-column>
	</list:data-columns>
	<!-- 分页 -->
	<list:data-paging currentPage="${queryPage.pageno }" pageSize="${queryPage.rowsize }" totalSize="${queryPage.totalrows }" />
</list:data>