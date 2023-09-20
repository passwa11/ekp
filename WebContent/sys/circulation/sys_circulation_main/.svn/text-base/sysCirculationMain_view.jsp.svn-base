<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include ref="default.simple" sidebar="no">
	<template:replace name="body">
	<link rel="Stylesheet" href="${LUI_ContextPath}/sys/circulation/resource/css/circulate.css?s_cache=${MUI_Cache}" />	
	<c:set var="remindAuth" value="false" />
	<c:set var="recallAuth" value="false" />
	<kmss:auth requestURL="/sys/circulation/sys_circulation_main/sysCirculationMain.do?method=remindCir&fdId=${sysCirculationMainForm.fdId}" requestMethod="GET">
		<c:set var="remindAuth" value="true" />
	</kmss:auth>
	<kmss:auth requestURL="/sys/circulation/sys_circulation_main/sysCirculationMain.do?method=recallCir&fdId=${sysCirculationMainForm.fdId}" requestMethod="GET">
		<c:set var="recallAuth" value="true" />
	</kmss:auth>
		<center>
			<div style="margin: 25px 20px">
				<table class="tb_normal" width=100%>
					<tr>
						<td class="td_normal_title" width=15%>
							<bean:message bundle="sys-circulation" key="sysCirculationMain.fdCirculatorId" />
						</td>
						<td>
							<c:out 	value="${sysCirculationMainForm.fdCirculatorName}" />
						</td>
						<td class="td_normal_title" width=15%>
							<bean:message bundle="sys-circulation" key="sysCirculationMain.fdCirculationTime" />
						</td>
						<td>
							<c:out 	value="${sysCirculationMainForm.fdCirculationTime}" />
						</td>
					</tr>
					<tr>
						<td class="td_normal_title" width=15%>
							<bean:message bundle="sys-circulation" key="sysCirculationMain.fdExpireTime" />
						</td>
						<td colspan="3">
							<c:out 	value="${sysCirculationMainForm.fdExpireTime}" />
						</td>
					</tr>
					<tr>
						<td class="td_normal_title" width=15%>
							<bean:message bundle="sys-circulation" key="sysCirculationMain.fdRegular" />
						</td>
						<td colspan="3">
							<c:if test="${not empty sysCirculationMainForm.fdRegular}">
								<sunbor:enumsShow value="${sysCirculationMainForm.fdRegular}" enumsType="sysCirculationMain_fdRegular" bundle="sys-circulation" />
							</c:if>	
							<c:if test="${sysCirculationMainForm.fdOpinionRequired == 'true'}">
								 ；<bean:message bundle="sys-circulation" key="sysCirculationMain.fdOpinionRequired" />
							</c:if>
						</td>
					</tr>
					<tr>
						<td class="td_normal_title" width=15%>
							<bean:message bundle="sys-circulation" key="table.sysCirculationCirculors" />
						</td>
						<td colspan="3">
								<c:out value="${sysCirculationMainForm.receivedCirCulatorNames}" />
						</td>
					</tr>
					<tr>
						 <td class="td_normal_title" width=15%>
							<bean:message bundle="sys-circulation" key="sysCirculationMain.fdRemark" />
						</td>
						<td colspan="3">
							<c:out value="${sysCirculationMainForm.fdRemark}" />
						</td>
					</tr>
				</table>
				
				 <div class="circulateDetail">
				     <div class="circulateDetailHead"><b><bean:message bundle="sys-circulation" key="sysCirculationMain.tab.circulation.label" /></b> </div>
				  </div>
				 <div style="height: 15px;"></div>
			 	<ui:dataview id="statusDataView">
					<ui:source type="AjaxJson">
						{"url":"/sys/circulation/sys_circulation_opinion/sysCirculationOpinion.do?method=findByStatusCount&fdMainId=${sysCirculationMainForm.fdId}"}
					</ui:source>
					<ui:render type="Template">
						{$
							<div class="statusContainer">
						$}
							for(var key in data){
								if(data[key].id == '${param.docStatus}'){
									{$
										<div class="status selected" onclick="changeStatus('{%data[key].id%}');" id="status_{%data[key].id%}">
											<span class="status-item">{%data[key].text%}：{%data[key].value%}</span>
										</div>
									$}
								}else{
									{$
										<div class="status" onclick="changeStatus('{%data[key].id%}');" id="status_{%data[key].id%}">
											<span class="status-item">{%data[key].text%}：{%data[key].value%}</span>
										</div>
									$}
								}
							}
						{$
						 </div>	
						$}
					</ui:render>
				</ui:dataview>
				<div class="lui_list_operation">
					<div style="float:right">
						<div style="display: inline-block;vertical-align: middle;">
							<ui:toolbar count="3" id="Btntoolbar" cfg-dataInit="false">
							</ui:toolbar>
					  </div>
					</div>
				</div>
				<list:listview id="opinionView">
					<ui:source type="AjaxJson">
						{"url":"/sys/circulation/sys_circulation_opinion/sysCirculationOpinion.do?method=list&forward=listOpinion4pc&fdMainId=${sysCirculationMainForm.fdId}&docStatus=${param.docStatus}"}
					</ui:source>
					<list:colTable  isDefault="true"  layout="sys.ui.listview.listtable" name="A" onRowClick="viewCirOpinionDetail('!{fdId}')">
					    <list:col-checkbox name="List_Selected"></list:col-checkbox>
					    <list:col-serial></list:col-serial>
						<list:col-auto props="fdBelongPerson.fdParent.fdName;fdBelongPerson.fdName;docStatus;fdReadTime;fdRemindCount;fdRecallTime;docContent"></list:col-auto>
					</list:colTable>
					<list:colTable  layout="sys.ui.listview.listtable" name="B" onRowClick="viewCirOpinionDetail('!{fdId}')">
						<list:col-serial></list:col-serial>
						<list:col-auto props="fdBelongPerson.fdParent.fdName;fdBelongPerson.fdName;docStatus;fdReadTime;fdRemindCount;fdRecallTime;docContent"></list:col-auto>
					</list:colTable>
				</list:listview>
			 	<list:paging></list:paging>
		 	</div>
		</center>
		<script>
		seajs.use(['lui/jquery','lui/dialog','lui/topic','lui/toolbar'], function($, dialog , topic,toolbar) {
			
			 window.changeStatus = function(value){
				 var url= Com_GetCurDnsHost()+Com_Parameter.ContextPath+"sys/circulation/sys_circulation_opinion/sysCirculationOpinion.do?method=list&forward=listOpinion4pc&fdMainId=${sysCirculationMainForm.fdId}";
				if(value != 'all'){
					url += "&docStatus="+value;
				}
				LUI('opinionView').source.setUrl(url);
				LUI('opinionView').source.get();
				
			 	$("div[id^='status_']").removeClass('selected');
			 	$("div[id ='status_"+value+"']").addClass('selected');
			 	if(value != '10'){
					 LUI('opinionView').switchType('B');
					 if(LUI('remind')){ 
					 	LUI('Btntoolbar').removeButton(LUI('remind'));
					 	LUI('remind').destroy();
					 }
					 if(LUI('recall')){ 
						 LUI('Btntoolbar').removeButton(LUI('recall'));
						 LUI('recall').destroy();
					 }
				 }else{
					 
					 LUI('opinionView').switchType('A');
					 if(!LUI('remind') && "${remindAuth}" == 'true'){ 
		            	 var recallBtn = toolbar.buildButton({id:'remind',order:'2',text:'${ lfn:message("sys-circulation:button.remind") }',click:'remind()'});
    					 LUI('Btntoolbar').addButton(recallBtn);
	            	 }
					 if(!LUI('recall') && "${recallAuth}" == 'true'){
		            	 var recallBtn = toolbar.buildButton({id:'recall',order:'1',text:'${ lfn:message("sys-circulation:button.recall") }',click:'recall()'});
    					 LUI('Btntoolbar').addButton(recallBtn);
	            	 }
				 }
			};
			LUI.ready(function(){
				changeStatus('${param.docStatus}');
			});
			 window.recall = function(){
				var values = [];
				$("input[name='List_Selected']:checked").each(function(){
						values.push($(this).val());
					});
				if(values.length==0){
					dialog.alert('<bean:message key="page.noSelect"/>');
					return;
				}
				var url='<c:url value="/sys/circulation/sys_circulation_opinion/sysCirculationOpinion.do?method=deleteBacks"/>';
				dialog.confirm("${lfn:message('sys-circulation:sysCirculationMain.withdraw')}", function(value) {/* 您确定要对所选记录进行撤回吗？ */
					if (value == true) {
						window.recall_load = dialog.loading();
						$.ajax({
							url : url,
							type : "post",
							dataType : 'json',
							data : $.param({"List_Selected":values,"fdMainId":"${sysCirculationMainForm.fdId}"},true),
							error : function(data) {
								dialog.result(data.responseJSON);
							},
							success : function(data) {
								 LUI('statusDataView').source.get();
								if(window.recall_load!=null){
									window.recall_load.hide(); 
								}
								topic.publish("list.refresh");
								dialog.success("${lfn:message('sys-circulation:sysCirculationMain.withdraw.successfully')}");/* 撤回成功! */
							},
							complete : function() {
								if (window.recall_load != null) {
									window.recall_load.hide(); 
								}
							}
						});
					}
				});
			};
			window.remind = function(){
				var values = [];
				$("input[name='List_Selected']:checked").each(function(){
						values.push($(this).val());
					});
				if(values.length==0){
					dialog.alert('<bean:message key="page.noSelect"/>');
					return;
				}
				var url='<c:url value="/sys/circulation/sys_circulation_opinion/sysCirculationOpinion.do?method=addReminds"/>';
				dialog.confirm("${lfn:message('sys-circulation:sysCirculationMain.unread.remind')}", function(value) {/* 您确定要对所选未阅对象进行提醒吗？ */
					if (value == true) {
						window.remind_load = dialog.loading();
						$.ajax({
							url : url,
							type : "post",
							dataType : 'json',
							data : $.param({"List_Selected":values},true),
							error : function(data) {
								dialog.result(data.responseJSON);
							},
							success : function(data) {
								if(window.remind_load!=null){
									window.remind_load.hide(); 
								}
								topic.publish("list.refresh"); 
								dialog.success("${lfn:message('sys-circulation:sysCirculationMain.reminder.success')}");/* 提醒成功！ */
							},
							complete : function() {
								if (window.remind_load != null) {
									window.remind_load.hide(); 
								}
							}
						});
					}
				});
			};
			window.viewCirOpinionDetail = function(fdId){
				var url = '/sys/circulation/sys_circulation_opinion/sysCirculationOpinion.do?method=view&fdId='+fdId;
				dialog.iframe(url,'${lfn:message("sys-circulation:sysCirculationMain.circulated")}',function(rtn){},{width:650,height:450});/* 查看传阅意见 */
			}
		});
		</script>
	</template:replace>
</template:include>



