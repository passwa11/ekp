<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<list:data>
	<!-- 表格数据 -->
	<list:data-columns var="modelingImportConfig" list="${queryPage.list }">
		<list:data-column property="fdId" />
		<!-- 名称 -->
		<list:data-column property="fdName" title="${ lfn:message('sys-modeling-base:modelingImportConfig.fdTemplateName') }">
		</list:data-column>
		<!-- 导入类型 -->
		<list:data-column col="fdImportType" title="${ lfn:message('sys-modeling-base:modelingImportConfig.fdImportType') }">
			<sunbor:enumsShow value="${modelingImportConfig.fdImportType}" enumsType="sysTransport_importType" bundle="sys-transport"/>
		</list:data-column>
		<!-- 创建人 -->
		<list:data-column headerClass="width100" col="fdCreator" title="${ lfn:message('model.fdCreator') }" escape="false">
		 	<ui:person personId="${modelingImportConfig.fdCreator.fdId}" personName="${modelingImportConfig.fdCreator.fdName}"></ui:person>
		</list:data-column>
		<!-- 创建时间 -->
		<list:data-column headerClass="width100" col="fdCreateTime" title="${ lfn:message('model.fdCreateTime') }">
		    <kmss:showDate value="${modelingImportConfig.fdCreateTime}" type="date"/>
		</list:data-column>
		<!-- 其它操作 -->
		<list:data-column headerClass="width200" col="operations" title="${ lfn:message('list.operation') }" escape="false">
			<!--操作按钮 开始-->
			<div class="conf_show_more_w">
				<div class="conf_btn_edit">
					<!-- 编辑 -->
					<a class="btn_txt" href="javascript:editDoc('${modelingImportConfig.fdId}')">${lfn:message('button.edit')}</a>
					<!-- 删除 -->
					<a class="btn_txt" href="javascript:delOne('${modelingImportConfig.fdId}')">${lfn:message('button.delete')}</a>
				</div>
			</div>
			<!--操作按钮 结束-->
		</list:data-column>
	</list:data-columns>

	<!-- 分页 -->
	<list:data-paging currentPage="${queryPage.pageno }" pageSize="${queryPage.rowsize }" totalSize="${queryPage.totalrows }" />
</list:data>