<%@ page language="java" contentType="text/json; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<ui:chart-data gridMargin="40 40 80 70" text="${result.title.text}"  xAxisData="${result.xAxis.data}"  yAxisName="${result.yAxis.name}" xAxisRotate="60" xAxisShowAll="true">
	
	<ui:chart-property name="title.x" value="center"></ui:chart-property>
	<ui:chart-property name="title.y" value="top"></ui:chart-property>
	
	<ui:chart-property name="toolbox" merge="false">
		{
			"show" : true,
			"y":"center",
			"orient":"vertical",
			"right" : "26px",
	        "feature" : {
	           "magicType" : {"show": true, "type": ["line","bar"]},
	           "restore" : {"show": true},
	           "saveAsImage" : {"show": true},
	           "dataTableView":{
	           		"show":true,
	           		"icon":"${LUI_ContextPath}/sys/lbpmperson/resource/images/switch.png"
	           }
	        }
		}
	</ui:chart-property>
	<ui:chart-property name="toolbox.feature.dataTableView.onclick" isScript="true" merge="true">
		function(){
			stat.switchChart("1");
		}
	</ui:chart-property>
		<c:set var="forY2" value="false" />
		<c:if test="${result.seriesData.yAxisIndex==1}">
			<c:set var="forY2" value="true" />
		</c:if>
		<ui:chart-series name="${result.seriesData.name}" type="line"  data="${result.seriesData.data}" forY2="${forY2}">
		</ui:chart-series>
	
</ui:chart-data>