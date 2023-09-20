<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ taglib uri="/WEB-INF/KmssConfig/sys/attend/map.tld" prefix="map"%>
<template:include ref="default.edit">
	<template:replace name="content">
		<div class='lui_form_subject'>
			PC端地图组件
		</div>
		<table class="tb_normal" width=100%>			
			<tr>
				<td align="right" style="border-right: 0px;" width=15%>
					编辑模式
				</td>
				<td style="border-left: 0px !important;">
					<map:location showStatus="edit" propertyName="placeA"></map:location> 
				</td> 
			</tr> 
			<tr>
				<td align="right" style="border-right: 0px;" width=15%>
					查看模式
				</td>
				<td style="border-left: 0px !important;">
					<map:location showStatus="view" propertyName="placeB" coordinateValue="22.546889,113.946534" nameValue="中钢大厦"></map:location> 
				</td> 
			</tr> 
		</table>
	</template:replace>
</template:include>