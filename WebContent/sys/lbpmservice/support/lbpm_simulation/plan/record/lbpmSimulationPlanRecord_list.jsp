<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<list:data>
	<list:data-columns var="lbpmSimulationPlanRecord" list="${queryPage.list }" varIndex="status">
		<!-- 计划id -->
		<list:data-column property="fdId" />
		
		<list:data-column  property="fdExecDate" title="${ lfn:message('sys-lbpmservice-support:lbpmSimulationPlanRecord.fdExecDate') }" style="text-align:center;min-width:120px">
		</list:data-column>
		
		<list:data-column  property="fdTotal" title="${ lfn:message('sys-lbpmservice-support:lbpmSimulationPlanRecord.fdTotal') }" style="text-align:center;min-width:120px">
		</list:data-column>
		
		<list:data-column headerClass="width130" property="fdSuccess" title="${ lfn:message('sys-lbpmservice-support:lbpmSimulationPlanRecord.fdSuccess') }">
		</list:data-column>
		
		<list:data-column headerClass="width130" property="fdFail" title="${ lfn:message('sys-lbpmservice-support:lbpmSimulationPlanRecord.fdFail') }">
		</list:data-column>
		<!-- 编辑和删除操作 -->
		<list:data-column headerClass="width140" col="operations" title="${ lfn:message('sys-lbpmservice-support:list.operation') }" escape="false">
			<!--操作按钮 开始-->
			<div class="conf_show_more_w">
				<div class="conf_btn_edit">
					<a href="javascript:void(0);" onclick="viewLbpmSimulationPlanRecord(this,'total');">
						<bean:message bundle="sys-lbpmservice-support" key="button.view" />
					</a>
				</div>
			</div>
			<!--操作按钮 结束-->
		</list:data-column>
	</list:data-columns>
	<!-- 分页 -->
	<list:data-paging currentPage="${queryPage.pageno }" pageSize="${queryPage.rowsize }" totalSize="${queryPage.totalrows }" />
</list:data>