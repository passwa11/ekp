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
	          <li name="process_head_tab" data-load="flow_chart_load_Frame_Ding"><a href="javascript:void(0);">${ lfn:message('sys-lbpmservice-support:lbpm.process.status.processChart') }</a></li>
	        </ul>
	    </div>
	    <!--end 选项卡头部 -->	    
	    <!-- begin流程图 -->
	    <div name="process_body" class="process_body_checked_true">
	     <table class="tb_normal process_review_panel" width="100%">
	     		
				<c:if test="${sysWfBusinessForm.sysWfBusinessForm.fdFlowContent != null && sysWfBusinessForm.sysWfBusinessForm.fdFlowContent != ''}">
				<tr class="tr_normal_title" style="display:none">
					<td align="left" colspan="4">
						<label><input type="checkbox" id="flowGraphicShowCheckbox" value="true" >
						<bean:message bundle="sys-lbpmservice" key="lbpm.tab.graphic" /></label>
					</td>
				</tr>
				<tr id="flowGraphic">
					<td id="workflowInfoTD" ${resize_prefix}onresize="lbpm.flow_chart_load_Frame_Ding();" colspan="4">
						<iframe width="100%" height="100%" scrolling="no" id="${sysWfBusinessFormPrefix}WF_IFrame"></iframe>
						<script>
						$("#${sysWfBusinessFormPrefix}WF_IFrame").ready(function() {
							var lbpm_panel_reload = function() {
								$("#${sysWfBusinessFormPrefix}WF_IFrame").attr('src', function (i, val) {return val;});
							};
							lbpm.events.addListener(lbpm.constant.EVENT_MODIFYNODEATTRIBUTE, lbpm_panel_reload);
							lbpm.events.addListener(lbpm.constant.EVENT_MODIFYPROCESS, lbpm_panel_reload);
							lbpm.events.addListener(lbpm.constant.EVENT_SELECTEDMANUAL, lbpm_panel_reload);
						});
						$('#flowGraphicShowCheckbox').bind('click', function() {
							$('#workflowInfoTD').each(function() {
								var str = this.getAttribute('onresize');
								if (str) {
									(new Function(str))();
								}
							});
						});
						$('#relationProcessCheckBox').bind('click', function() {
							if (this.checked==true) {
								if ($('#parentFlowTR')) {
									$('#parentFlowTR').show();
								}
								if ($('#subFlowTR')) {
									$('#subFlowTR').show();
								}
							} else {
								if ($('#parentFlowTR')) {
									$('#parentFlowTR').hide();
								}
								if ($('#subFlowTR')) {
									$('#subFlowTR').hide();
								}
							}							
						});
						</script>
					</td>
				</tr>
			</c:if>
	     </table>
	    </div>
	    <!-- end流程图 -->
