<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ taglib uri="/WEB-INF/KmssConfig/sys/attend/map.tld" prefix="map"%>
<template:include ref="mobile.edit" compatibleMode="true">
	<template:replace name="head">
		<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/sys/attend/map/mobile/resource/css/location.css?s_cache=${MUI_Cache}"></link>
	</template:replace>
	<template:replace name="content"> 
		<p style="text-align: center;font-size: 2.2rem;margin-top: 2rem;">移动端地图组件</p>
		<table class="muiSimple" cellpadding="0" cellspacing="0" style="margin: 1rem;width: 90%;">
			<tr>
				<td class="muiTitle">
					编辑模式
				</td>
				<td>
					<map:location propertyName="placeA" mobile="true" showStatus="edit"></map:location>
				</td>
			</tr>
			<tr>
				<td class="muiTitle">
					查看模式
				</td>
				<td>
					<map:location propertyName="placeB" mobile="true" showStatus="view" coordinateValue="22.546889,113.946534" nameValue="中钢大厦"></map:location>
				</td>
			</tr>
		</table>
	</template:replace>
</template:include>