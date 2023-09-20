<%@ page language="java" contentType="text/json; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*"%>
<%@ page import="net.sf.json.*"%>
<%@ page import="com.landray.kmss.hr.staff.util.ReportResult"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>

<ui:chart-data gridMargin="80 110 80 40" text="${result.title.text}" xAxisData="${result.xAxis[0].data}" yAxisName="${result.yAxis[0].name}">
	<%
		// 因为这里有许多细节扩展，所以没法使用已经封装好的标签
		ReportResult result = (ReportResult)request.getAttribute("result");
		String seriesType = (String)result.getSeriesData().get(0).get("type");
		String title = (String)result.getSeriesData().get(0).get("title"); 
		Object seriesRadius = result.getSeriesData().get(0).get("radius");
		List<Long> dataList = (List<Long>)result.getSeriesData().get(0).get("data");
		String nameList = (String)result.getxAxis().get(0).get("data");
		JSONArray seriesData = new JSONArray();
		JSONArray seriesNameData = new JSONArray();
		String[] _nameList = nameList.split(",");
		for(int i=0;i<dataList.size();i++){
			long count = dataList.get(i);
			JSONObject obj = new JSONObject();
			obj.put("value", count);
			obj.put("name", _nameList[i]);
			seriesData.add(obj);
			seriesNameData.add(_nameList[i]);
		}
	%>
	<ui:chart-property name="title" merge="false">
		{
			 "text": "<%=title%>",
			 "show":false
		}
	</ui:chart-property>	
	<ui:chart-property name="toolbox" merge="false">
		{
			"show" : true,
			"right" : "20px",
	        "feature" : {
	           "restore" : {"show": true}, // 显示刷新
	           "saveAsImage" : {"show": true}, // 显示保存图片
	           "myDataTableView" : {"show": false} // 不显示列表视图
	        }
		}
	</ui:chart-property>
	
	<ui:chart-property name="series" merge="false">
		[
	        {
	            "type" : "<%=seriesType%>",
	            <%if(seriesRadius != null){%>
	            "radius" : <%=seriesRadius%>,
	            <%}%>
	            <%if("bar".equals(seriesType)){%>
	            "name" : "${ lfn:message('hr-staff:hrStaffPersonReport.page.staffNum') }",
	            itemStyle: {
	                normal: {
		                color: function(){
		                	var colors = [
							    "#ff7f50", "#87cefa", "#da70d6", "#32cd32", "#6495ed",
							    "#ff69b4", "#ba55d3", "#cd5c5c", "#ffa500", "#40e0d0",
							    "#1e90ff", "#ff6347", "#7b68ee", "#00fa9a", "#ffd700",
							    "#6699FF", "#ff6666", "#3cb371", "#b8860b", "#30e0e0"
							];
							var index = Math.floor(Math.random() * 20);
		                	return colors[index];
		                },
	                    label: {
	                        show: true,
	                        position: 'top',
	                        formatter: '{c}'
	                    }
	                }
	            },
	            <%}%>
	            "data" : <%=seriesData%>
	        }
	    ]
	</ui:chart-property>
	<ui:chart-property name="yAxis" merge="false">
		[
	        {
	            type : 'value'
	        }
	    ]
	</ui:chart-property>
	<%if("pie".equals(seriesType)){%>
	<ui:chart-property name="legend" merge="false">
		{
	        "x" : "center",
		    "y" : "bottom",
		    "orient" : "horizontal",
	        "data" : <%=seriesNameData%>
	    }
	</ui:chart-property>
	<%}%>
</ui:chart-data>