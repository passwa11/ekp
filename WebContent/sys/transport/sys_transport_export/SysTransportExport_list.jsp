<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<list:data>
	<!-- 表格数据 -->
	<list:data-columns var="sysTransportExportConfig" list="${queryPage.list }">
		<list:data-column property="fdId" />
		<!-- 名称 -->
		<list:data-column property="fdName" title="${ lfn:message('sys-transport:sysTransportExportConfig.fdName') }">
		</list:data-column>
		<!-- 创建人 -->
		<list:data-column headerClass="width100" col="creator" title="${ lfn:message('model.fdCreator') }" escape="false">
		 	<ui:person personId="${sysTransportExportConfig.creator.fdId}" personName="${sysTransportExportConfig.creator.fdName}"></ui:person>
		</list:data-column>
		<!-- 创建时间 -->
		<list:data-column headerClass="width100" col="createTime" title="${ lfn:message('model.fdCreateTime') }">
		    <kmss:showDate value="${sysTransportExportConfig.createTime}" type="date"/>
		</list:data-column>
		<!-- 其它操作 -->
		<list:data-column headerClass="width200" col="operations" title="${ lfn:message('list.operation') }" escape="false">
			<!--操作按钮 开始-->
			<div class="conf_show_more_w">
				<div class="conf_btn_edit">
					<kmss:auth requestURL="/sys/transport/sys_transport_export/SysTransportExport.do?method=edit&fdId=${sysTransportExportConfig.fdId}&fdModelName=${sysTransportExportConfig.fdModelName}" requestMethod="GET">
						<!-- 编辑 -->
						<a class="btn_txt" href="javascript:edit('${sysTransportExportConfig.fdId}')">${lfn:message('button.edit')}</a>
					</kmss:auth>
					<kmss:auth requestURL="/sys/transport/sys_transport_export/SysTransportExport.do?method=deleteall&fdModelName=${sysTransportExportConfig.fdModelName}" requestMethod="POST">
						<!-- 删除 -->
						<a class="btn_txt" href="javascript:del('${sysTransportExportConfig.fdId}')">${lfn:message('button.delete')}</a>
					</kmss:auth>
				</div>
			</div>
			<!--操作按钮 结束-->
		</list:data-column>
	</list:data-columns>

	<!-- 分页 -->
	<list:data-paging currentPage="${queryPage.pageno }" pageSize="${queryPage.rowsize }" totalSize="${queryPage.totalrows }" />
</list:data>