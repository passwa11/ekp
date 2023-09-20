<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<list:data>
	<list:data-columns var="dbEchartsChart" list="${queryPage.list }">
		<list:data-column property="fdId" />
		<list:data-column  col="docSubject" title="${ lfn:message('dbcenter-echarts:dbEchartsChart.docSubject') }" style="text-align:center;min-width:180px" escape="false">
			<span class="com_subject"><c:out value="${dbEchartsChart.docSubject}" /></span>
		</list:data-column>
		<list:data-column  headerClass="width120" property="docCreateTime" title="${ lfn:message('dbcenter-echarts:dbEchartsChart.docCreateTime') }">
		</list:data-column>
		<list:data-column  headerClass="width120" property="docCreator.fdName" title="${ lfn:message('dbcenter-echarts:dbEchartsChart.docCreator') }">
		</list:data-column>
		<list:data-column headerClass="width120" property="dbEchartsTemplate.fdName" title="${ lfn:message('dbcenter-echarts:dbEchartsTemplate.fdName') }">
		</list:data-column>

		<list:data-column  col="echartTypeUrl" escape="false">
			<c:if test="${dbEchartsChart.echartType=='1'}">
				db_echarts_custom/dbEchartsCustom.do?method=view&chartDefault=1&fdId=
			</c:if>
			<c:if test="${dbEchartsChart.echartType=='2'}">
				db_echarts_chart/dbEchartsChart.do?method=view&chartDefault=1&fdId=
			</c:if>
			<c:if test="${dbEchartsChart.echartType=='3'}">
				db_echarts_table/dbEchartsTable.do?method=view&chartDefault=1&fdId=
			</c:if>
			<c:if test="${dbEchartsChart.echartType=='4'}">
				db_echarts_chart_set/dbEchartsChartSet.do?method=view&chartDefault=1&fdId=
			</c:if>
		</list:data-column>


		<list:data-column style="text-align:center;vertical-align:middle;" headerClass="width120" col="operations" title="${ lfn:message('dbcenter-echarts:module.echarts.following.title') }" escape="false">
			<!--操作按钮 开始-->
			<kmss:auth requestURL="/dbcenter/echarts/db_echarts_total/dbEchartsTotal.do?method=deleteMyAttentionEcharts&fdId=${dbEchartsChart.fdId}">
				        
				        <c:if test="${!empty dbEchartsChart.dbEchartsAttentions}">
							<a class="delete_Attention" href="javascript:deleteMyAttentionEcharts('${dbEchartsChart.fdId}')"></a>
						</c:if>
			
				        <c:if test="${empty dbEchartsChart.dbEchartsAttentions}">
				        	<a class="create_Attention" href="javascript:createMyAttentionEcharts('${dbEchartsChart.fdId}')"></a>
				        </c:if>
				    </kmss:auth>
			<!--操作按钮 结束-->
		</list:data-column>
		
		
	</list:data-columns>
	<!-- 分页 -->
	<list:data-paging currentPage="${queryPage.pageno }" pageSize="${queryPage.rowsize }" totalSize="${queryPage.totalrows }" />
</list:data>
