<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<list:data>
	<list:data-columns var="dbEchartsChartSet" list="${queryPage.list }">
		<list:data-column property="fdId" />
		<list:data-column  col="docSubject" title="${ lfn:message('dbcenter-echarts:dbEchartsChartSet.docSubject') }" style="text-align:center;min-width:180px" escape="false">
			<span class="com_subject"><c:out value="${dbEchartsChartSet.docSubject}" /></span>
		</list:data-column>	
		<list:data-column  headerClass="width120" property="docCreateTime" title="${ lfn:message('dbcenter-echarts:dbEchartsChartSet.docCreateTime') }">
		</list:data-column>
		<list:data-column  headerClass="width120" property="docCreator.fdName" title="${ lfn:message('dbcenter-echarts:dbEchartsChartSet.docCreator') }">
		</list:data-column>
		<list:data-column headerClass="width120" property="dbEchartsTemplate.fdName" title="${ lfn:message('dbcenter-echarts:dbEchartsTemplate.fdName') }">
		</list:data-column>
		<list:data-column headerClass="width120" col="operations" title="${ lfn:message('list.operation') }" escape="false">
			<!--操作按钮 开始-->
			<div class="conf_show_more_w">
				<div class="conf_btn_edit">
					<kmss:auth requestURL="/dbcenter/echarts/db_echarts_chart_set/dbEchartsChartSet.do?method=edit&fdId=${dbEchartsChartSet.fdId}" requestMethod="GET">
						<a class="btn_txt" href="javascript:edit('${dbEchartsChartSet.fdId}')">${lfn:message('button.edit')}</a>
					</kmss:auth>
					<kmss:auth requestURL="/dbcenter/echarts/db_echarts_chart_set/dbEchartsChartSet.do?method=delete&fdId=${dbEchartsChartSet.fdId}">
				        <a class="btn_txt" href="javascript:deleteDoc('${dbEchartsChartSet.fdId}')">${lfn:message('button.delete')}</a>
			        </kmss:auth>
				</div>
			</div>
			<!--操作按钮 结束-->
		</list:data-column>
	</list:data-columns>
	<!-- 分页 -->
	<list:data-paging currentPage="${queryPage.pageno }" pageSize="${queryPage.rowsize }" totalSize="${queryPage.totalrows }" />
</list:data>
