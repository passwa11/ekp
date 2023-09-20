<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/resource/jsp/common.jsp"%>
<c:set var="sysWfBusinessForm" value="${requestScope[param.formName]}" />
<c:set var="lbpmProcessForm" value="${sysWfBusinessForm.sysWfBusinessForm.internalForm}" />
		
		<!--begin 选项卡头部 -->
	    <div class="lui_flowstate_tab_heading" style="display: inline-block;">
	         <ul class="lui_flowstate_tabhead">
		          <li name="process_head_tab" class="active"><a href="javascript:void(0);">${ lfn:message('sys-lbpmservice-support:lbpm.process.status.processHandle') }</a></li>
		          <li name="process_head_tab" data-load="process_status_load_Frame"><a href="javascript:void(0);">${ lfn:message('sys-lbpmservice-support:lbpm.process.status.processStatus') }</a></li>
		          <li name="process_head_tab" data-load="flow_chart_load_Frame_Ding"><a href="javascript:void(0);">${ lfn:message('sys-lbpmservice-support:lbpm.process.status.processChart') }</a></li>
		          <li name="process_head_tab" data-load="flow_table_load_Frame"><a href="javascript:void(0);">${ lfn:message('sys-lbpmservice-support:lbpm.process.status.processTable') }</a></li>
	              <li name="process_head_tab" data-load="flow_log_load_Frame"><a href="javascript:void(0);">${ lfn:message('sys-lbpmservice-support:lbpm.process.status.processLog') }</a></li>
		     </ul>
	    </div>
	    <!--end 选项卡头部 -->
	       <!--begin 流程处理  -->
	    <div name="process_body" class="process_body_checked_true">
	    	<!-- #131277 自定义的流程，私密意见选项请去掉（添加判断自由流引用和普通引用）-开始 -->
			<c:choose>
				<c:when test="${lbpmProcessForm.fdTemplateType=='4'}">
					<c:import url="/sys/lbpmservice/common/process_edit_freeFlow_right.jsp" charEncoding="UTF-8"></c:import>
				</c:when>
				<c:otherwise>
		    		<c:import url="/sys/lbpmservice/common/process_edit_right.jsp" charEncoding="UTF-8"></c:import>
				</c:otherwise>
			</c:choose>
			<!-- #131277 自定义的流程，私密意见选项请去掉（添加判断自由流引用和普通引用）-结束 -->
	    </div>
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
<script>
		lbpm.onLoadEvents.once.push(function(){
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
