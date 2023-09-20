<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<list:data>
	<!-- 表格数据 -->
	<list:data-columns var="sysAuthArea" list="${queryPage.list }">
		<list:data-column property="fdId" />
		<!-- 上级场所 -->
		<list:data-column headerClass="width200" col="fdParent" title="${ lfn:message('sys-authorization:sysAuthArea.fdParent') }">
			${sysAuthArea.fdParent.fdName}
		</list:data-column>
		<!-- 场所名称 -->
		<list:data-column headerClass="width200" property="fdName" title="${ lfn:message('sys-authorization:sysAuthArea.fdName') }">
		</list:data-column>
		<!-- 所属组织 -->
		<list:data-column headerClass="width200" col="authAreaOrg" title="${ lfn:message('sys-authorization:sysAuthArea.authAreaOrg') }">
			<c:forEach items="${sysAuthArea.authAreaOrg}" var="authAreaOrg" varStatus="idx">
				<c:if test="${ idx.index > 0 }">,</c:if>
				${ authAreaOrg.fdName }
			</c:forEach>
		</list:data-column>
		<!-- 可漫游用户 -->
		<list:data-column col="authAreaVisitor" title="${ lfn:message('sys-authorization:sysAuthArea.authAreaVisitor') }">
			<c:forEach items="${sysAuthArea.authAreaVisitor}" var="authAreaVisitor" varStatus="idx">
				<c:if test="${ idx.index > 0 }">,</c:if>
				${ authAreaVisitor.fdName }
			</c:forEach>
		</list:data-column>
	</list:data-columns>
</list:data>