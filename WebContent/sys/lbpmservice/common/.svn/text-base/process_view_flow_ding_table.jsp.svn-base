<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/resource/jsp/common.jsp"%>
<c:set var="sysWfBusinessForm" value="${requestScope[param.formName]}" />
<c:set var="lbpmProcessForm" value="${sysWfBusinessForm.sysWfBusinessForm.internalForm}" /> 
<link type="text/css" rel="stylesheet" href="<c:url value="/sys/lbpmservice/common/css/process_tab_main.css?s_cache=${LUI_Cache}"/>"/> 
		<script>
		$(document).ready(function(){
			if(lbpm){
				lbpm.approveType = "right";
				lbpm.approvePosition = "right";
			}
		});	
		</script>
		<!--begin 选项卡头部 -->
	    <div class="lui_flowstate_tab_heading">
	        <ul class="lui_flowstate_tabhead">
	          <li name="process_head_tab" data-load="flow_table_load_Frame"><a href="javascript:void(0);">${ lfn:message('sys-lbpmservice-support:lbpm.process.status.processChart') }</a></li>
	        </ul>
	    </div>
	    <!--end 选项卡头部 -->	    
	     <!-- begin流程表格 -->
	    <div name="process_body" class="process_body_checked_true">
	    	<table width=100%>
	    		<c:if test="${sysWfBusinessForm.sysWfBusinessForm.fdFlowContent != null && sysWfBusinessForm.sysWfBusinessForm.fdFlowContent != ''}">
					<tr LKS_LabelName="<bean:message bundle="sys-lbpmservice" key="lbpm.tab.table" />">
						<td id="workflowTableTD" ${resize_prefix}onresize="lbpm.flow_table_load_Frame();">
							<iframe width="100%" height="100%" scrolling="no" id="${sysWfBusinessFormPrefix}WF_TableIFrame" FRAMEBORDER=0></iframe>
						</td>
					</tr>
				</c:if>
	    	</table>
	    </div>
	    <!-- end流程表格 -->
