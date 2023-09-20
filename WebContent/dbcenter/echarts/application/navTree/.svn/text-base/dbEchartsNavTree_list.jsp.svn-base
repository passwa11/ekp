<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<list:data>
	<list:data-columns var="dbEchartsNavTree" list="${queryPage.list }">
		<list:data-column property="fdId" />
		<list:data-column  col="fdNavTitleTxt" title="${ lfn:message('dbcenter-echarts-application:dbEchartsNavTree.navName') }" style="text-align:center;min-width:180px" escape="false">
			<span class="com_subject"><c:out value="${dbEchartsNavTree.fdNavTitleTxt}" /></span>
		</list:data-column>	
		<list:data-column  headerClass="width120" property="fdEchartsCategory.fdName" title="${ lfn:message('dbcenter-echarts-application:dbEchartsNavTree.fdEchartsCategory') }">
		</list:data-column>
		<list:data-column  headerClass="width120" property="docCreateTime" title="${ lfn:message('dbcenter-echarts:dbEchartsChart.docCreateTime') }">
		</list:data-column>
		<list:data-column  headerClass="width120" property="docCreator.fdName" title="${ lfn:message('dbcenter-echarts:dbEchartsChart.docCreator') }">
		</list:data-column>
		<list:data-column headerClass="width120" col="operations" title="${ lfn:message('list.operation') }" escape="false">
			<!--操作按钮 开始-->
			<div class="conf_show_more_w">
				<div class="conf_btn_edit">
					<a class="btn_txt" href="javascript:edit('${dbEchartsNavTree.fdId}')">${lfn:message('button.edit')}</a>
					<a class="btn_txt" href="javascript:deleteAll('${dbEchartsNavTree.fdId}')">${lfn:message('button.delete')}</a>
				</div>
			</div>
			<!--操作按钮 结束-->
		</list:data-column>
	</list:data-columns>
	<!-- 分页 -->
	<list:data-paging currentPage="${queryPage.pageno }" pageSize="${queryPage.rowsize }" totalSize="${queryPage.totalrows }" />
</list:data>
