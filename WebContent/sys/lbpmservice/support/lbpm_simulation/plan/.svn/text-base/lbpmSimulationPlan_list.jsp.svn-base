<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<list:data>
	<list:data-columns var="lbpmSimulationPlan" list="${queryPage.list }" varIndex="status">
		<list:data-column property="fdId" />
		
		<list:data-column  property="fdPlanName" title="${ lfn:message('sys-lbpmservice-support:lbpmSimulationPlan.fdPlanName') }" style="text-align:left;min-width:180px">
		</list:data-column>
		
		<list:data-column headerClass="width80" property="docCreator.fdName" title="${ lfn:message('sys-lbpmservice-support:lbpmSimulationPlan.docCreator') }">
		</list:data-column>
		
		<list:data-column headerClass="width120" property="fdCreateTime" title="${ lfn:message('sys-lbpmservice-support:lbpmSimulationPlan.fdCreateTime') }">
		</list:data-column>
		
		<list:data-column headerClass="width140" col="operations" title="${ lfn:message('sys-lbpmservice-support:list.operation') }" escape="false">
			
			<div class="conf_show_more_w">
				<div class="conf_btn_edit">
					<a class="btn_txt" href="javascript:edit('${lbpmSimulationPlan.fdId}')">${lfn:message('sys-lbpmservice-support:button.edit')}</a>
					<a class="btn_txt" href="javascript:deleteAll('${lbpmSimulationPlan.fdId}')">${lfn:message('sys-lbpmservice-support:button.delete')}</a>
				</div>
			</div>
			
		</list:data-column>
	</list:data-columns>
	
	<list:data-paging currentPage="${queryPage.pageno }" pageSize="${queryPage.rowsize }" totalSize="${queryPage.totalrows }" />
</list:data>