<%@ page language="java" contentType="text/json; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@page import="com.landray.kmss.util.StringUtil"%>
<%@ taglib uri="/WEB-INF/KmssConfig/sys/person/person.tld" prefix="person"%>
<list:data>
   	
	<list:data-columns var="DbEchartsTotal" list="${pages.list}" varIndex="status" mobile="true" >
		
	    <%-- 主题--%>	
		<list:data-column col="label" title="test" escape="false">
			<c:out value="${ DbEchartsTotal.getDocSubject()}"/> 
		</list:data-column>
		
		 <%-- 类型--%>
		<list:data-column col="creator" title="图表类型" >
		    <c:if test="${DbEchartsTotal.getEchartType() == '1'}">
		             自定义数据
		    </c:if>
		    <c:if test="${DbEchartsTotal.getEchartType() == '2'}">
		           统计图表
		    </c:if>
		    <c:if test="${DbEchartsTotal.getEchartType() == '3'}">
		          统计列表
		    </c:if>
		    <c:if test="${DbEchartsTotal.getEchartType() == '4'}">
		        统计图表集
		    </c:if>
		</list:data-column>
		
		<%--摘要--%>
		<list:data-column col="created"  title="分类">
		       <c:out value="${DbEchartsTotal.getDbEchartsTemplate().getFdName()}"/>
		</list:data-column>
		
		 <%-- 图标--%>
		 <list:data-column col="icon"  escape="false" styleClass="width:3px;height:3px">
		      <c:if test="${DbEchartsTotal.getEchartType() == '1'}">
		            ../mobile/image/zidingyi3.png
		    </c:if>
		    <c:if test="${DbEchartsTotal.getEchartType() == '2'}">
		          ../mobile/image/tubiao3.png
		    </c:if>
		    <c:if test="${DbEchartsTotal.getEchartType() == '3'}">
		          ../mobile/image/liebiao3.png
		    </c:if>
		    <c:if test="${DbEchartsTotal.getEchartType() == '4'}">
		       ../mobile/image/tubiaoji3.png
		    </c:if>
		</list:data-column>
		<%--链接--%>
		<list:data-column col="href" escape="false">
			<c:if test="${DbEchartsTotal.getEchartType() == '1'}">
		           /dbcenter/echarts/db_echarts_custom/dbEchartsCustom.do?method=view&mobile=true&fdId=${DbEchartsTotal.getFdId()}
		    </c:if>
		    <c:if test="${DbEchartsTotal.getEchartType() == '2'}">
		         /dbcenter/echarts/db_echarts_chart/dbEchartsChart.do?method=view&mobile=true&fdId=${DbEchartsTotal.getFdId()}
		    </c:if>
		    <c:if test="${DbEchartsTotal.getEchartType() == '3'}">
		       /dbcenter/echarts/db_echarts_table/dbEchartsTable.do?method=view&mobile=true&fdId=${DbEchartsTotal.getFdId()}
		    </c:if>
		    <c:if test="${DbEchartsTotal.getEchartType() == '4'}">
		       /dbcenter/echarts/db_echarts_chart_set/dbEchartsChartSet.do?method=view&mobile=true&fdId=${DbEchartsTotal.getFdId()}
		    </c:if>
		</list:data-column>
		
   </list:data-columns>

    <%-- 分页 --%>
	<list:data-paging currentPage="${pages.pageno}"
		pageSize="${pages.rowsize}" totalSize="${pages.totalrows}">
	</list:data-paging>
</list:data>