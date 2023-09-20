<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<list:data>
	<!-- 表格数据 -->
	<list:data-columns var="sysTransportImportConfig" list="${queryPage.list }">
		<list:data-column property="fdId" />
		<!-- 名称 -->
		<list:data-column property="fdName" title="${ lfn:message('sys-transport:sysTransportImportConfig.fdName') }">
		</list:data-column>
		<!-- 导入类型 -->
		<list:data-column col="fdImportType" title="${ lfn:message('sys-transport:sysTransportImportConfig.fdImportType') }">
			<sunbor:enumsShow value="${sysTransportImportConfig.fdImportType}" enumsType="sysTransport_importType" bundle="sys-transport"/>
		</list:data-column>
		<!-- 创建人 -->
		<list:data-column headerClass="width100" col="creator" title="${ lfn:message('model.fdCreator') }" escape="false">
		 	<ui:person personId="${sysTransportImportConfig.creator.fdId}" personName="${sysTransportImportConfig.creator.fdName}"></ui:person>
		</list:data-column>
		<!-- 创建时间 -->
		<list:data-column headerClass="width100" col="createTime" title="${ lfn:message('model.fdCreateTime') }">
		    <kmss:showDate value="${sysTransportImportConfig.createTime}" type="date"/>
		</list:data-column>
		<!-- 其它操作 -->
		<list:data-column headerClass="width200" col="operations" title="${ lfn:message('list.operation') }" escape="false">
			<!--操作按钮 开始-->
			<div class="conf_show_more_w">
				<div class="conf_btn_edit">
					<kmss:auth requestURL="/sys/transport/sys_transport_import/SysTransportImport.do?method=edit&fdId=${sysTransportImportConfig.fdId}&fdModelName=${sysTransportImportConfig.fdModelName}" requestMethod="GET">
						<!-- 编辑 -->
						<a class="btn_txt" href="javascript:edit('${sysTransportImportConfig.fdId}')">${lfn:message('button.edit')}</a>
					</kmss:auth>
					<kmss:auth requestURL="/sys/transport/sys_transport_import/SysTransportImport.do?method=deleteall&fdModelName=${sysTransportImportConfig.fdModelName}" requestMethod="POST">
						<!-- 删除 -->
						<a class="btn_txt" href="javascript:del('${sysTransportImportConfig.fdId}')">${lfn:message('button.delete')}</a>
					</kmss:auth>
				</div>
			</div>
			<!--操作按钮 结束-->
		</list:data-column>
	</list:data-columns>

	<!-- 分页 -->
	<list:data-paging currentPage="${queryPage.pageno }" pageSize="${queryPage.rowsize }" totalSize="${queryPage.totalrows }" />
</list:data>