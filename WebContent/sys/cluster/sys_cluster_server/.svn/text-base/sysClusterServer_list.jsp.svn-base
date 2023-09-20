<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<list:data>
	<list:data-columns var="sysClusterServer" list="${queryPage.list}">
		<c:set var="styleTxt" value="" />
		<c:if test="${sysClusterServer.local}">
			<c:set var="styleTxt" value="font-weight:bold;" />
		</c:if>
		<list:data-column property="fdId" />
		<list:data-column col="checkbox" title="" escape="false">
			<c:if test="${sysClusterServer.status != 2}">
				<input type="checkbox" name="List_Selected" value="${sysClusterServer.fdId}" data-lui-mark="table.content.checkbox">
			</c:if>
		</list:data-column>
		<list:data-column col="fdName" title="${ lfn:message('sys-cluster:sysClusterServer.fdName') }" escape="false" style="${styleTxt}">
			<c:if test="${not sysClusterServer.local && not empty sysClusterServer.fdUrl}">
				<a href="${sysClusterServer.fdUrl}/sys/cluster/sys_cluster_server/sysClusterServer.do?method=list" target="_self"
					title="<bean:message bundle="sys-cluster" key="sysClusterServer.linkTitle"/>">
			</c:if>
			<c:out value="${sysClusterServer.fdName}" />
			<c:if test="${not sysClusterServer.local && not empty sysClusterServer.fdUrl}">
				</a>
			</c:if>
		</list:data-column>
		<list:data-column property="fdKey" title="${ lfn:message('sys-cluster:sysClusterServer.fdKey') }" style="${styleTxt}">
		</list:data-column>
		<list:data-column col="status" title="${ lfn:message('sys-cluster:sysClusterServer.status') }" escape="false" style="${styleTxt}">
			<xform:select property="serverStatus" value="${sysClusterServer.status}">
				<xform:enumsDataSource enumsType="sysClusterServer.status" />
			</xform:select>
		</list:data-column>
		<list:data-column col="fdPid" title="${ lfn:message('sys-cluster:sysClusterServer.fdPid') }" escape="false" style="${styleTxt}">
			<c:choose>
				<c:when test="${sysClusterServer.status==2}">
					<c:out value="${sysClusterServer.fdPid}" />
				</c:when>
				<c:otherwise>
		        N/A
		     </c:otherwise>
			</c:choose>
		</list:data-column>
		<list:data-column col="fdAddress" title="${ lfn:message('sys-cluster:sysClusterServer.fdAddress') }" escape="false" style="${styleTxt}">
			<c:choose>
				<c:when test="${sysClusterServer.status==2}">
					<c:out value="${sysClusterServer.fdAddress}" />
				</c:when>
				<c:otherwise>
		        N/A
		     </c:otherwise>
			</c:choose>
		</list:data-column>
		<list:data-column col="fdStartTime" title="${ lfn:message('sys-cluster:sysClusterServer.fdStartTime') }" escape="false" style="${styleTxt}">
			<c:choose>
				<c:when test="${sysClusterServer.status==2}">
				<sunbor:date value="${sysClusterServer.fdStartTime}" datePattern="${lfn:message('date.format.time.sec.2y')}"></sunbor:date>
				</c:when>
				<c:otherwise>
		        N/A
		     </c:otherwise>
			</c:choose>
		</list:data-column>
		<list:data-column col="fdRefreshTime" title="${ lfn:message('sys-cluster:sysClusterServer.fdRefreshTime') }" escape="false" style="${styleTxt}">
			<c:choose>
				<c:when test="${sysClusterServer.status==2}">
				<sunbor:date value="${sysClusterServer.fdRefreshTime}" datePattern="${lfn:message('date.format.time.sec.2y')}"></sunbor:date>
				</c:when>
				<c:otherwise>
		        N/A
		     </c:otherwise>
			</c:choose>
		</list:data-column>
		<list:data-column col="fdDispatcherType" title="${ lfn:message('sys-cluster:sysClusterServer.fdDispatcherType') }" escape="false"
			style="${styleTxt}">
			<c:choose>
				<c:when test="${sysClusterServer.status==2}">
					<xform:select property="fdDispatcherType" value="${sysClusterServer.fdDispatcherType}">
						<xform:enumsDataSource enumsType="sysClusterServer.fdDispatcherType" />
					</xform:select>
				</c:when>
				<c:otherwise>
		        N/A
		     </c:otherwise>
			</c:choose>
		</list:data-column>
		<!-- 其它操作 -->
		<list:data-column headerClass="width100" col="operations" title="${ lfn:message('list.operation') }" escape="false" style="${styleTxt}">
			<!--操作按钮 开始-->
			<div class="conf_show_more_w">
			<div class="conf_btn_edit"><kmss:auth
				requestURL="/sys/cluster/sys_cluster_server/sysClusterServer.do?method=edit&fdId=${sysClusterServer.fdId}" requestMethod="POST">
				<!-- 编辑 -->
				<a class="btn_txt" href="javascript:edit('${sysClusterServer.fdId}')">${lfn:message('button.edit')}</a>
			</kmss:auth> <c:if test="${sysClusterServer.status != 2}">
				<kmss:auth requestURL="/sys/cluster/sys_cluster_server/sysClusterServer.do?method=delete&fdId=${sysClusterServer.fdId}" requestMethod="POST">
					<!-- 删除 -->
					<a class="btn_txt" href="javascript:deleteAll('${sysClusterServer.fdId}')">${lfn:message('button.delete')}</a>
				</kmss:auth>
			</c:if></div>
			</div>
			<!--操作按钮 结束-->
		</list:data-column>
	</list:data-columns>
	<!-- 分页 -->
	<list:data-paging currentPage="${queryPage.pageno }" pageSize="${queryPage.rowsize }" totalSize="${queryPage.totalrows }" />
</list:data>