<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<list:data>
	<!-- 表格数据 -->
	<list:data-columns var="sysPrintTemplate" list="${queryPage.list }">
		<list:data-column property="fdId" />
		<!-- 名称 -->
		<list:data-column headerClass="width200" property="fdName" title="${ lfn:message('sys-print:sysPrintTemplate.fdName') }">
		</list:data-column>
		<!-- 打印方式 -->
		<list:data-column headerClass="width80" col="fdPrintMode" title="${ lfn:message('sys-print:sysPrintTemplate.fdPrintMode') }">
			<xform:select property="fdPrintMode" value="${sysPrintTemplate.fdPrintMode}">
				<xform:enumsDataSource enumsType="fdPrint_mode" />
			</xform:select>
		</list:data-column>
		<!-- 创建人 -->
		<list:data-column headerClass="width80" property="docCreator.fdName" title="${ lfn:message('model.fdCreator') }">
		</list:data-column>
		<!-- 创建时间 -->
		<list:data-column headerClass="width120" property="docCreateTime" title="${ lfn:message('model.fdCreateTime') }">
		</list:data-column>
		<!-- 操作 -->
		<list:data-column headerClass="width200" col="operations" title="${ lfn:message('list.operation') }" escape="false">
			<!--操作按钮 开始-->
			<div class="conf_show_more_w">
				<div class="conf_btn_edit">
					<!-- 编辑 -->
					<a class="btn_txt" href="javascript:edit('${sysPrintTemplate.fdId}')">${lfn:message('button.edit')}</a>
					<a class="btn_txt" href="javascript:del('${sysPrintTemplate.fdId}')">${lfn:message('button.delete')}</a>
				</div>
			</div>
			<!--操作按钮 结束-->
		</list:data-column>
	</list:data-columns>

	<!-- 分页 -->
	<list:data-paging currentPage="${queryPage.pageno }" pageSize="${queryPage.rowsize }" totalSize="${queryPage.totalrows }" />
</list:data>