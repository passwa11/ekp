<%@ page language="java" contentType="text/json; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>

<ui:chart-data gridMargin="80 110 80 60" text="${result.title.text}"  xAxisData="${result.xAxis[0].data}"  yAxisName="${result.yAxis[0].name}" y2AxisName="${result.yAxis[1].name}">
	
	<ui:chart-property name="title.x" value="left"></ui:chart-property>
	<ui:chart-property name="title.y" value="10"></ui:chart-property>
	
	<ui:chart-property name="legend.x" value="left"></ui:chart-property>
	<ui:chart-property name="legend.y" value="bottom"></ui:chart-property>
	
	<c:forEach var="item" items="${result.seriesData}">
		<c:set var="forY2" value="false" />
		<c:if test="${item.yAxisIndex==1}">
			<c:set var="forY2" value="true" />
		</c:if>
		<ui:chart-series name="${item.name}" type="${item.type}"  data="${item.data}" forY2="${forY2}">
		</ui:chart-series>
	</c:forEach>
	
</ui:chart-data>