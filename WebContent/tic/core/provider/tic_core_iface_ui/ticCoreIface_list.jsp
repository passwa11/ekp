<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<list:data>
	<list:data-columns var="ticCoreIface" list="${queryPage.list }">
		<%--ID--%>
		<list:data-column property="fdId"></list:data-column>
		<%--接口名称--%>
		<list:data-column col="fdIfaceName" title="${ lfn:message('tic-core-provider:ticCoreIface.fdIfaceName') }" style="text-align:center" escape="false">
			<span class="com_subject"><c:out value="${ticCoreIface.fdIfaceName}" /></span>
		</list:data-column>
		<%--接口Key--%>
		<list:data-column col="fdIfaceKey" title="${ lfn:message('tic-core-provider:ticCoreIface.fdIfaceKey') }" escape="false" style="text-align:center;">
			<c:out value="${ticCoreIface.fdIfaceKey}" />
		</list:data-column>
		<%--调度模式--%>
		<list:data-column col="fdControlPattern" title="${ lfn:message('tic-core-provider:ticCoreIface.controlPattern') }">
			<sunbor:enumsShow value="${ticCoreIface.fdControlPattern}" enumsType="fd_control_pattern_enums"  />
		</list:data-column>
		<%--是否前台控制--%>
		<list:data-column col="fdIfaceControl" title="${ lfn:message('tic-core-provider:ticCoreIface.fdIfaceControl') }">
			<sunbor:enumsShow value="${ticCoreIface.fdIfaceControl}" enumsType="common_yesno"  />
		</list:data-column>
		<%--标签列表--%>
		<list:data-column col="fdIfaceTags" title="${ lfn:message('tic-core-provider:ticCoreIface.fdIfaceTags') }">
			<c:forEach items="${ticCoreIface.fdIfaceTags}" var="fdIfaceTag" varStatus="vstatus">
				<c:out value="${fdIfaceTag.fdTagName}" />;
			</c:forEach>
		</list:data-column>
	</list:data-columns>

	<list:data-paging currentPage="${queryPage.pageno }"
		pageSize="${queryPage.rowsize }" totalSize="${queryPage.totalrows }">
	</list:data-paging>
</list:data>
