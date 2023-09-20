<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<list:data>
	<list:data-columns var="item" list="${queryPage }">
		<!-- 应用ID -->
		<list:data-column col="fdId" >
			<c:out value="${item.appId }"></c:out>
		</list:data-column>
		<list:data-column property="appId" ></list:data-column>
		<!-- 标示名 -->
		<list:data-column  property="appName" title="${lfn:message('third-pda:pdaOfflineApp.appName') }" />
		<!-- 描述 -->
		<list:data-column property="description" title="${lfn:message('third-pda:pdaOfflineApp.description') }"></list:data-column>
		<!-- 打包目录 -->
		<list:data-column  property="folder" title="${lfn:message('third-pda:pdaOfflineApp.folder') }" />
		<!-- 入口文件 -->
		<list:data-column property="homepage" title="${lfn:message('third-pda:pdaOfflineApp.homepage') }" />
	</list:data-columns>
</list:data>