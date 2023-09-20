<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ include file="/resource/jsp/htmlhead.jsp" %>
<%-- <%@ include file="/sys/ui/jsp/jshead.jsp"%> --%>
<template:include ref="default.view" spa="true" rwd="true">
	<template:replace name="title">${ lfn:message('sys-lbpmservice-support:table.lbpmSimulationLog') }</template:replace>
	<template:replace name="content">
		<script>
			seajs.use(['theme!list']);
			Com_IncludeFile('jquery.js');
		</script>
		<p class="txttitle"><bean:message  bundle="sys-lbpmservice-support" key="table.lbpmSimulationLog"/></p>
		<list:criteria id="criteria1">
		 <!-- 仿真实例 -->
		  <list:cri-criterion title="${ lfn:message('sys-lbpmservice-support:table.lbpmSimulationExample') }" key="fdExampleId" expand="true" multi="true">
				<list:box-select>
					<list:item-select>
						<ui:source type="AjaxJson">
							{url:'/sys/lbpm/lbpm_simulation_plan/lbpmSimulationPlan.do?method=queryExampleByPlanId&planId=${param["planId"]}'}
						</ui:source>
					</list:item-select>
				</list:box-select>
			</list:cri-criterion>
			<!-- 成功，失败 -->
			 <list:cri-criterion title="${ lfn:message('sys-lbpmservice-support:lbpmSimulationPlanRecord.fdStatus') }" key="fdType" expand="true" multi="true">
				<list:box-select>
					<list:item-select>
						<ui:source type="Static">
							[{text:'${ lfn:message('sys-lbpmservice-support:lbpmSimulationPlanRecord.success') }', value:'0'},
							{text:'${ lfn:message('sys-lbpmservice-support:lbpmSimulationPlanRecord.fail') }', value:'1'}
							]
							</ui:source>
					</list:item-select>
				</list:box-select>
			</list:cri-criterion>
			</list:criteria>
			<list:listview id="listview">
				<ui:source type="AjaxJson">
					{url:'/sys/lbpmservice/support/lbpm_simulation_log/lbpmSimulationLog.do?method=findListByRecordId&recordId=${param.recordId}'}
				</ui:source>
				<!-- /sys/lbpmservice/support/lbpm_simulation_node_test_log/lbpmSimulationNodeTestLog.do?method=data&fdId=!{fdId} -->
				<list:colTable onRowClick="findLogDetailById('!{fdId}');" isDefault="true" layout="sys.ui.listview.columntable" rowHref="">
					<list:col-serial></list:col-serial>
					<list:col-auto props="fdExampleTitle;fdType;operations" />
				</list:colTable>
			</list:listview> 
			 <!-- 翻页 -->
            <list:paging />
			<br>
			<script>
			seajs.use(['lui/jquery','lui/dialog','lui/topic'], function($, dialog , topic) {
				window.findLogDetailById = function(fdId){
					 var url = Com_Parameter.ContextPath
						+ "sys/lbpmservice/support/lbpm_simulation_node_test_log/lbpmSimulationNodeTestLog.do?method=findLogDetailsByLogId&logId=" + fdId; 
						var param={
							win:window
						}
						_popupWindow(url,800,600,param);
    			};
    					
    			function _popupWindow(url,width,height,param){
    				var left = (screen.width-width)/2;
    				var top = (screen.height-height)/2;
    				if(window.showModalDialog){
    					var winStyle = "resizable:1;scroll:1;dialogwidth:"+width+"px;dialogheight:"+height+"px;dialogleft:"+left+";dialogtop:"+top;
    					var rtnVal=window.showModalDialog(url, param, winStyle);
    					if(param.AfterShow){
    						param.AfterShow(rtnVal);
    					}
    				}else{
    					var winStyle = "resizable=1,scrollbars=1,width="+width+",height="+height+",left="+left+",top="+top+",dependent=yes,alwaysRaised=1";
    					Com_Parameter.Dialog = param;
    					var tmpwin=window.open(url, "_blank", winStyle);
    					if(tmpwin){
    						tmpwin.onbeforeunload=function(){
    							if(param.AfterShow && !param.AfterShow._isShow) {
    								param.AfterShow._isShow = true;
    								param.AfterShow(tmpwin.returnValue);
    							}   
    						};
    					}		
    				}
    			}
			});
			</script>
	</template:replace>
</template:include>

