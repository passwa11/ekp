<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<list:data>
	<!-- 表格数据 -->
	<list:data-columns var="sysRelationForeignModule" list="${queryPage.list }">
		<list:data-column property="fdId" />
		<!-- 模块名称 -->
		<list:data-column  col="fdModuleName" title="${ lfn:message('sys-relation:sysRelationForeignModule.fdModuleName') }" escape="false" >
			 ${sysRelationForeignModule.fdModuleName}
		</list:data-column>
		
		
		<!-- 排序号 -->
		<list:data-column headerClass="width200" col="fdOrder" title="${ lfn:message('sys-relation:sysRelationForeignModule.fdOrder') }">
			${sysRelationForeignModule.fdOrder}
		</list:data-column>
		<!-- 搜索引擎 -->
		<list:data-column headerClass="width300" col="fdSearchEngineBean" title="${ lfn:message('sys-relation:sysRelationForeignModule.fdSearchEngineBean') }">
			${sysRelationForeignModule.fdSearchEngineBean}
		</list:data-column>
	</list:data-columns>

	<!-- 分页 -->
	<list:data-paging currentPage="${queryPage.pageno }" pageSize="${queryPage.rowsize }" totalSize="${queryPage.totalrows }" />
</list:data>