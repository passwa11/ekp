<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<list:data>
	<list:data-columns var="item" list="${queryPage }">
		<list:data-column col="fdId" >
			<c:out value="${item.name }"></c:out>
		</list:data-column>
		<!-- 标示名 -->
		<list:data-column  property="name" title="${ lfn:message('third-pda:pdaModuleConfigMain.fdName') }" />
		<!-- 目标文件 -->
		<list:data-column  property="targetFile" title="${ lfn:message('third-pda:pdaZipSettingView.targetFile') }" />
		<!-- 源文件 -->
		<list:data-column property="srcFold" title="${ lfn:message('third-pda:pdaZipSettingView.directory') }" />
		<!-- 类型 -->
		<list:data-column property="type" title="${ lfn:message('third-pda:pdaZipSettingView.type') }" />
		<!-- 是否执行 -->
		<list:data-column col="executed" title="${ lfn:message('third-pda:pdaZipSettingView.executed') }" >
			<sunbor:enumsShow value="${item.done}" enumsType="common_yesno" />
		</list:data-column>
		<!-- 执行时间 -->
		<list:data-column property="createTime" title="${ lfn:message('third-pda:pdaZipSettingView.executionTime') }" />
		
	</list:data-columns>
</list:data>