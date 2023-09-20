<%@ page language="java" contentType="text/json; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>

<ui:chart-data gridMargin="120 110 80 40" legendPosition="left|40" text="${result.title.text}"  xAxisData="${result.xAxis[0].data}"  yAxisName="${result.yAxis[0].name}" y2AxisName="${result.yAxis[1].name}" xAxisShowAll="${result.xAxisShowAll}" xAxisRotate="${result.xAxisRotate}">
	
	<ui:chart-property name="title.x" value="center"></ui:chart-property>
	<ui:chart-property name="title.y" value="top"></ui:chart-property>
	<c:choose>
		<c:when test="${'1' eq isWorkbench }">
			<ui:chart-property name="toolbox" merge="false">
				{"show":false}
			</ui:chart-property>
			<ui:chart-property name="color" merge="false">
				["#4C7BFD"]
			</ui:chart-property>
		</c:when>
		<c:otherwise>
			<ui:chart-property name="toolbox" merge="false">
				{
					"show" : true,
					"y":"center",
					"right":"22",
					"orient":"vertical",
			        "feature" : {
			           "dataZoom" : {"show" : true},
			           "magicType" : {"show": true, "type": ["line","bar"]},
			           "restore" : {"show": true},
			           "saveAsImage" : {"show": true},
			           "dataTableView":{
			           		"show":true,
			           		"title":"${lfn:message('km-imeeting:kmImeetingStat.stat.section.switch')}",
			           		"icon":"${LUI_ContextPath}/km/imeeting/resource/images/switch.png"
			           }
			        }
				}
			</ui:chart-property>
			<ui:chart-property name="toolbox.feature.dataTableView.onclick" isScript="true" merge="true">
				function(){
					stat.switchChart("1");
				}
			</ui:chart-property>
		</c:otherwise>
	</c:choose>
	
	
		
	<c:forEach var="item" items="${result.seriesData}">
		<c:set var="forY2" value="false" />
		<c:if test="${item.yAxisIndex==1}">
			<c:set var="forY2" value="true" />
		</c:if>
		<c:if test="${'1' eq isWorkbench}">
			<c:set var="barWidth" value="30" />
		</c:if>
		<ui:chart-series name="${item.name}" type="${item.type}"  data="${item.data}" forY2="${forY2}" barWidth="${barWidth }">
		</ui:chart-series>
	</c:forEach>
</ui:chart-data>