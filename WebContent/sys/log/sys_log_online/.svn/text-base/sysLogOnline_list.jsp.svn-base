<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<list:data>
	<!-- 表格数据 -->
	<list:data-columns var="sysLogOnline" list="${queryPage.list }">
		<list:data-column property="fdId" />
		<!-- 用户 -->
		<list:data-column headerClass="width100" col="fdPerson" title="${ lfn:message('sys-log:sysLogOnline.fdPerson') }">
			${sysLogOnline.fdPerson.fdName}
		</list:data-column>
		<!-- 类型 -->
		<list:data-column headerClass="width100" col="fdIsExternal" title="${ lfn:message('sys-log:sysLogOnline.fdIsExternal') }">
			<c:if test="${sysLogOnline.fdIsExternal}">
				${ lfn:message('sys-log:sysLogOnline.external.true') }
			</c:if>
			<c:if test="${!sysLogOnline.fdIsExternal}">
				${ lfn:message('sys-log:sysLogOnline.external.false') }
			</c:if>
		</list:data-column>
		<!-- 是否移动端 -->
		<list:data-column headerClass="width100" col="fdIsMobile" title="${ lfn:message('sys-log:sysLogOnline.fdIsMobile') }">
			<sunbor:enumsShow value="${sysLogOnline.fdIsMobile}" enumsType="common_yesno" />
		</list:data-column>
		<!-- 部门 -->
		<list:data-column headerClass="width100" col="fdDept" title="${ lfn:message('sys-log:sysLogOnline.fdDept') }">
			${sysLogOnline.fdPerson.hbmParent.fdName}
		</list:data-column>
		<!-- 是否在线 -->
		<list:data-column headerClass="width100" col="isOnline" title="${ lfn:message('sys-log:sysLogOnline.isOnline') }">
			${sysLogOnline.fdIsUserOnline}
		</list:data-column>
		<!-- 在线时间 -->
		<list:data-column headerClass="width100" col="fdOnlineTime" title="${ lfn:message('sys-log:sysLogOnline.fdOnlineTime') }">
			<kmss:showDate value="${sysLogOnline.fdOnlineTime}" />
		</list:data-column>
		<!-- 登录时间 -->
		<list:data-column headerClass="width100" col="fdLoginTime" title="${ lfn:message('sys-log:sysLogOnline.fdLoginTime') }">
			<kmss:showDate value="${sysLogOnline.fdLoginTime}" />
		</list:data-column>
		<!-- 登录IP -->
		<list:data-column headerClass="width100" property="fdLoginIp" title="${ lfn:message('sys-log:sysLogOnline.fdLoginIp') }">
		</list:data-column>
		<!-- 上次登录时间 -->
		<list:data-column headerClass="width100" col="fdLastLoginTime" title="${ lfn:message('sys-log:sysLogOnline.fdLastLoginTime') }">
			<kmss:showDate value="${sysLogOnline.fdLastLoginTime}" />
		</list:data-column>
		<!-- 上次登录IP -->
		<list:data-column headerClass="width100" property="fdLastLoginIp" title="${ lfn:message('sys-log:sysLogOnline.fdLastLoginIp') }">
		</list:data-column>
		<!-- 登录次数 -->
		<list:data-column headerClass="width100" property="fdLoginNum" title="${ lfn:message('sys-log:sysLogOnline.fdLoginNum') }">
		</list:data-column>
		<!-- 首次登录时间 -->
		<list:data-column headerClass="width100" col="docCreateTime" title="${ lfn:message('sys-log:sysLogOnline.docCreateTime') }">
			<kmss:showDate value="${sysLogOnline.docCreateTime}" />
		</list:data-column>
	</list:data-columns>

	<!-- 分页 -->
	<list:data-paging currentPage="${queryPage.pageno }" pageSize="${queryPage.rowsize }" totalSize="${queryPage.totalrows }" />
</list:data>