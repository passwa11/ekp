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
	          <li name="process_head_tab" data-load="process_status_load_Frame_Ding('${sysWfBusinessForm.fdId}','${sysWfBusinessForm.modelClass.name}')"><a href="javascript:void(0);">${ lfn:message('sys-lbpmservice-support:lbpm.process.status.processStatus') }</a></li>
	        </ul>
	    </div>
	    <!--end 选项卡头部 -->	    
    <!-- begin流程状态 -->
    <div name="process_body" class="process_body_checked_true">
     <table width=100%>
     	<tr>
			<td  id="processStatusTD" ${resize_prefix}onresize="lbpm.process_status_load_Frame_Ding('${sysWfBusinessForm.fdId}','${sysWfBusinessForm.modelClass.name}');">
				<iframe width="100%" height="100%" scrolling="no" FRAMEBORDER=0></iframe>
			</td>
		</tr>
     </table>
    </div>
    <!-- end流程状态 -->