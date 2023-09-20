<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/resource/jsp/common.jsp"%>
<c:set var="sysWfBusinessForm" value="${requestScope[param.formName]}" />
<c:set var="lbpmProcessForm" value="${sysWfBusinessForm.sysWfBusinessForm.internalForm}" />
<link type="text/css" rel="stylesheet" href="<c:url value="/sys/lbpmservice/common/css/process_tab_main.css?s_cache=${LUI_Cache}"/>"/> 
<!-- 解决一些业务模块在流程结束后还需进行编辑的情况 -->
<c:if test="${sysWfBusinessForm.docStatus == '30' && param.approveType eq 'right' }">
	<input type="hidden" name="sysWfBusinessForm.fdParameterJson" value="" id="sysWfBusinessForm.fdParameterJson">
	<input type="hidden" name="sysWfBusinessForm.fdAdditionsParameterJson" value="" id="sysWfBusinessForm.fdAdditionsParameterJson">
	<input type="hidden" name="sysWfBusinessForm.fdIsModify" value='' id="sysWfBusinessForm.fdIsModify">
	<html:hidden property="sysWfBusinessForm.fdCurHanderId"  styleId="sysWfBusinessForm.fdCurHanderId" />
	<html:hidden property="sysWfBusinessForm.fdCurNodeSavedXML"  styleId="sysWfBusinessForm.fdCurNodeSavedXML" />
	<html:hidden property="sysWfBusinessForm.fdFlowContent"  styleId="sysWfBusinessForm.fdFlowContent"  />
	<html:hidden property="sysWfBusinessForm.fdProcessId"  styleId="sysWfBusinessForm.fdProcessId"  />
	<html:hidden property="sysWfBusinessForm.fdKey"  styleId="sysWfBusinessForm.fdKey" />
	<input type="hidden" name="sysWfBusinessForm.fdModelId" id="sysWfBusinessForm.fdModelId" value='<c:out value="${sysWfBusinessForm.fdId}" />' >
	<input type="hidden" name="sysWfBusinessForm.fdModelName" id="sysWfBusinessForm.fdModelName" value='<c:out value="${sysWfBusinessForm.modelClass.name}" />' >
	<html:hidden property="sysWfBusinessForm.fdCurNodeXML"  styleId="sysWfBusinessForm.fdCurNodeXML" />
	<html:hidden property="sysWfBusinessForm.fdTemplateModelName"  styleId="sysWfBusinessForm.fdTemplateModelName"/>
	<html:hidden property="sysWfBusinessForm.fdTemplateKey" styleId="sysWfBusinessForm.fdTemplateKey"/>
	<input type="hidden" name="sysWfBusinessForm.canStartProcess" id="sysWfBusinessForm.canStartProcess" value='' >
	<html:hidden property="sysWfBusinessForm.fdTranProcessXML" styleId="sysWfBusinessForm.fdTranProcessXML"/>
	<html:hidden property="sysWfBusinessForm.fdDraftorId" styleId="sysWfBusinessForm.fdDraftorId"/>
	<html:hidden property="sysWfBusinessForm.fdDraftorName" styleId="sysWfBusinessForm.fdDraftorName"/>
	<html:hidden property="sysWfBusinessForm.fdHandlerRoleInfoIds"  styleId="sysWfBusinessForm.fdHandlerRoleInfoIds"/>
	<html:hidden property="sysWfBusinessForm.fdHandlerRoleInfoNames"  styleId="sysWfBusinessForm.fdHandlerRoleInfoNames" />
	<html:hidden property="sysWfBusinessForm.fdAuditNoteFdId"  styleId="sysWfBusinessForm.fdAuditNoteFdId" />
	<html:hidden property="sysWfBusinessForm.fdIdentityId"  styleId="sysWfBusinessForm.fdIdentityId" />
	<html:hidden property="sysWfBusinessForm.fdProcessStatus" styleId="sysWfBusinessForm.fdProcessStatus" />
	<html:hidden property="sysWfBusinessForm.fdDefaultIdentity" styleId="sysWfBusinessForm.fdDefaultIdentity" />
	<input type="hidden" id="sysWfBusinessForm.fdSubFormXML" name="sysWfBusinessForm.fdSubFormXML" value='<c:out value="${sysWfBusinessForm.sysWfBusinessForm.fdSubFormXML}" />'>
	<input type="hidden" id="__docStatus"   value='<c:out value="${sysWfBusinessForm.docStatus}" />' >
	<input type="hidden" id="__method"   value='<c:out value="${param.method}" />' >
</c:if>
<script>
$(document).ready(function(){
	$("li[name='process_head_tab']").click(function(){
		$("li[name='process_head_tab']").attr("class","");
		$(this).attr("class","active");
		
		//兼容有些模块下无法触发onresize事件的问题
		var isClick=$(this).attr("data-isClick");
		var dataLoad=$(this).attr("data-load");
		if(!isClick){
			$(this).attr("data-isClick","true");
			if(dataLoad){
				lbpm[dataLoad]();
			}
		}
		
		var radioDiv = document.getElementById('flow_log_filter_radio');
		if('flow_log_load_Frame'==dataLoad){
			radioDiv.style.display = "";
		}else{
			radioDiv.style.display = "none";
		}
		
		$("div[name='process_body']").attr("class","process_body_checked_false");
		var lis=$(this).parent().children();
		for(var i=0;i<lis.length;i++){
			var classValue=$(lis[i]).attr("class");
			if(classValue==="active"){
				var process_bodys=$("div[name='process_body']");
				$(process_bodys[i]).attr("class","process_body_checked_true");
			}
		}
	});
});

function reloadHistoryInfoTableIframe(tr,param){
	var name = $(param).attr('name');
	var radios = document.getElementsByName(name);
	
	for(var i = 0;i < radios.length;i++){
		radios[i].disabled = false;
	}
	
	var radio = param;
	radio.disabled =true;
	
	var trObj = document.getElementById(tr);
	var url = trObj.getElementsByTagName('iframe')[0].src;
	url = Com_SetUrlParameter(url,'filterType',radio.value);
	
	
	trObj.getElementsByTagName('iframe')[0].src=url; 
}
</script>
<!--begin 选项卡头部 -->
   <div class="lui_flowstate_tab_heading">
        <ul class="lui_flowstate_tabhead">
         <li name="process_head_tab" class="active"><a href="javascript:void(0);">${ lfn:message('sys-lbpmservice-support:lbpm.process.status.processHandle') }</a></li>
         <li name="process_head_tab" data-load="process_status_load_Frame"><a href="javascript:void(0);">${ lfn:message('sys-lbpmservice-support:lbpm.process.status.processStatus') }</a></li>
         <li name="process_head_tab" data-load="flow_chart_load_Frame"><a href="javascript:void(0);">${ lfn:message('sys-lbpmservice-support:lbpm.process.status.processChart') }</a></li>
         <li name="process_head_tab" data-load="flow_table_load_Frame"><a href="javascript:void(0);">${ lfn:message('sys-lbpmservice-support:lbpm.process.status.processTable') }</a></li>
         <li name="process_head_tab" data-load="flow_log_load_Frame"><a href="javascript:void(0);">${ lfn:message('sys-lbpmservice-support:lbpm.process.status.processLog') }</a></li>
         <li name="process_head_tab" data-load="process_restart_Log_Frame" id="liProcess_restart_Log_Frame"><a href="javascript:void(0);">${ lfn:message('sys-lbpmservice-support:lbpm.process.status.processRestartLog') }</a></li>
       	 <li id="flow_log_filter_radio" style="display: none;">
       	  	<div>
       	  		<label>
					<input type="radio" name="filter_type_log" value="all" checked="checked" onclick="reloadHistoryInfoTableIframe('flowLogTableTD',this)">
					<bean:message bundle="sys-lbpmservice-support" key="lbpmAuditNote.filterType.all" />
				</label>
				<label>
					<input type="radio" name="filter_type_log" value="org" onclick="reloadHistoryInfoTableIframe('flowLogTableTD',this)">
					<bean:message bundle="sys-lbpmservice-support" key="lbpmAuditNote.filterType.org" />
				</label>
				<label>
					<input type="radio" name="filter_type_log" value="dept" onclick="reloadHistoryInfoTableIframe('flowLogTableTD',this)">
					<bean:message bundle="sys-lbpmservice-support" key="lbpmAuditNote.filterType.dept" />
				</label>
       	  	</div>
       	  </li>
       </ul>
   </div>
   <!--end 选项卡头部 -->
   
    <!--begin 流程处理  -->
    <div name="process_body" class="process_body_checked_true">
    			<table class="tb_normal process_review_panel" width="100%">
		<%--#152384-参数获取文件路径直接import输出导致安全问题-开始
		<c:if test="${not empty param.historyPrePage}">
			<c:import url="${param.historyPrePage}" charEncoding="UTF-8"/>
		</c:if>
		#152384-参数获取文件路径直接import输出导致安全问题-结束--%>
		<tr class="tr_normal_title" id="followOptRow">
				<td align="right" colspan="4">
					<!-- 流程跟踪 -->
					<a href="javascript:void(0);" id="followOptButton" class="com_btn_link" style="display:none; margin: 0 10px 0 0;">
						<bean:message key="lbpmFollow.button.follow" bundle="sys-lbpmservice-support" />
					</a>
					<!-- 取消跟踪 -->
					<a href="javascript:void(0);" id="cancelFollowOptButton" class="com_btn_link" style="display:none; margin: 0 10px 0 0;">
						<bean:message key="lbpmFollow.button.cancelFollow" bundle="sys-lbpmservice-support" />
					</a>
				</td>
			</tr>
			<%--#148641-流程说明开关开启，若流程说明为空，隐藏当前行，如果流程说明不为空，显示当前行-开始--%>
			<tr id="tr_process_description">
				<td class="td_normal_title" width="20%">
					<bean:message bundle="sys-lbpmservice" key="lbpmProcess.history.description"/>
				</td>
				<td colspan=3>
					<span id="fdFlowDescription"></span>
				</td>
			</tr>
			<%--#148641-流程说明开关开启，若流程说明为空，隐藏当前行，如果流程说明不为空，显示当前行-结束--%>
		<c:if test="${not empty sysWfBusinessForm.docStatus && param.method!='add'}">
			<tr class="tr_normal_title">
				<td align="left">
					<label><input type="checkbox" value="true" checked="checked" onclick="lbpm.globals.showHistoryDisplay(this);">
					<bean:message bundle="sys-lbpmservice" key="lbpmProcess.history.description.show" /></label>
				</td>
				<td id="filterRadio">
					<label>
						<input type="radio" name="filter_type" value="all" checked="checked" onclick="reloadHistoryInfoTableIframe('historyTableTR',this)">
						<bean:message bundle="sys-lbpmservice-support" key="lbpmAuditNote.filterType.all" />
					</label>
					<label>
						<input type="radio" name="filter_type" value="org" onclick="reloadHistoryInfoTableIframe('historyTableTR',this)">
						<bean:message bundle="sys-lbpmservice-support" key="lbpmAuditNote.filterType.org" />
					</label>
					<label>
						<input type="radio" name="filter_type" value="dept" onclick="reloadHistoryInfoTableIframe('historyTableTR',this)">
						<bean:message bundle="sys-lbpmservice-support" key="lbpmAuditNote.filterType.dept" />
					</label>
				</td>
			</tr>
			<tr id="historyTableTR" style="padding: 0;display: none;">
				<td colspan=4 id="historyInfoTableTD" ${resize_prefix}onresize="lbpm.load_Frame();" style="padding: 0;">
					<iframe id="historyInfoTableIframe" width="100%" style="margin-bottom: -3px;border: none;" scrolling="no" FRAMEBORDER=0></iframe>
				</td>
			</tr>
		</c:if>
		
		<xform:isExistRelationProcesses relationType="all">
			<tr class="tr_normal_title">
				<td align="left" colspan="4">
					<label><input type="checkbox" id="relationProcessCheckBox" value="true">
					<bean:message bundle="sys-lbpmservice" key="lbpm.tab.relationProcesses" /></label>
				</td>
			</tr>
			<xform:isExistRelationProcesses relationType="parent">
				<tr id="parentFlowTR" style="display:none">
					<td class="td_normal_title" width=15%>
						<bean:message bundle="sys-lbpmservice" key="label.parentFlow" />
					</td>
					<td colspan=3>
						<xform:showParentProcesse />
					</td>
				</tr>
			</xform:isExistRelationProcesses>
			<xform:isExistRelationProcesses relationType="subs">
				<tr id="subFlowTR" style="display:none">
					<td class="td_normal_title" width=15%>
						<bean:message bundle="sys-lbpmservice" key="label.subFlow" />
					</td>
					<td colspan=3>
						<xform:showSubProcesses />
					</td>
				</tr>
			</xform:isExistRelationProcesses>
			<script>
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
		</xform:isExistRelationProcesses>
	</table>
    	
    </div>
    <!--end 流程处理  -->
   
   <!-- begin流程状态 -->
   <div name="process_body" class="process_body_checked_false">
    <table width=100%>
    	<tr>
		<td  id="processStatusTD" ${resize_prefix}onresize="lbpm.process_status_load_Frame();">
			<iframe width="100%" height="100%" scrolling="no" FRAMEBORDER=0></iframe>
		</td>
	</tr>
    </table>
   </div>
   <!-- end流程状态 -->
   
   <!-- begin流程图 -->
   <div name="process_body" class="process_body_checked_false">
    <table class="tb_normal process_review_panel" width="100%">
    	<tr id="flowGraphic">
		<td id="workflowInfoTD" ${resize_prefix}onresize="lbpm.flow_chart_load_Frame();" colspan="4">
			<iframe width="100%" height="100%" scrolling="no" id="${sysWfBusinessFormPrefix}WF_IFrame"></iframe>
			<script>
			$("#${sysWfBusinessFormPrefix}WF_IFrame").ready(function() {
				var lbpm_panel_reload = function() {
					$("#${sysWfBusinessFormPrefix}WF_IFrame").attr('src', function (i, val) {return val;});
				};
				lbpm.events.addListener(lbpm.constant.EVENT_MODIFYNODEATTRIBUTE, lbpm_panel_reload);
				lbpm.events.addListener(lbpm.constant.EVENT_MODIFYPROCESS, lbpm_panel_reload);
				lbpm.events.addListener(lbpm.constant.EVENT_SELECTEDMANUAL, lbpm_panel_reload);
				lbpm.events.addListener(lbpm.constant.EVENT_CHANGEROLE, lbpm_panel_reload);
			});
			$('#flowGraphicShowCheckbox').bind('click', function() {
				$('#workflowInfoTD').each(function() {
					var str = this.getAttribute('onresize');
					if (str) {
						(new Function(str))();
					}
				});
			});
			</script>
		</td>
	</tr>
   
    </table>
   </div>
   <!-- end流程图 -->
   
   <!-- begin流程表格 -->
   <div name="process_body" class="process_body_checked_false">
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
   
   <!-- begin流程日志 -->
   <div name="process_body" class="process_body_checked_false">
    <table width=100%>
    	<tr LKS_LabelName="<bean:message bundle="sys-lbpmservice" key="label.flowLog" />">
					<td  id="flowLogTableTD" ${resize_prefix}onresize="lbpm.flow_log_load_Frame();">
						<iframe width="100%" height="100%" scrolling="no" FRAMEBORDER=0></iframe>
					</td>
		</tr>
    </table>    	
   </div>
   <!-- end流程日志 -->
   
   <!-- begin重启日志 -->
   <div name="process_body" class="process_body_checked_false">
    <table width=100%>
    	<tr LKS_LabelName="<bean:message bundle="sys-lbpmservice" key="label.flowLog" />">
			<td  id="lbpmProcessRestartLogTD" ${resize_prefix}onresize="lbpm.process_restart_Log_Frame();">
				<iframe width="100%" height="100%" scrolling="no" FRAMEBORDER=0></iframe>
			</td>
		</tr>
    </table>    	
   </div>
   <!-- end重启日志 -->
	    
