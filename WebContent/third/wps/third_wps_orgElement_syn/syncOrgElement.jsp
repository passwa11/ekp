<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>

<template:include ref="config.profile.edit" sidebar="no">
	
	<template:replace name="title">
		${lfn:message("third-wps:thirdWpsOrgRecord.title.syncOrgStruct")}
	</template:replace>

	<template:replace name="head">
		<script type="text/javascript">
			Com_IncludeFile("docutil.js|optbar.js");
		</script>
		<style>
			.txtlistpath{
				margin-top: 7px;
			}
			#optBarMarginTop {
				/* height: 0 !important; */
			}
			#S_OperationBar {
			    /* top: 35px !important;
			    position: relative !important; */
			}
		</style>
	</template:replace>

	<template:replace name="content">
		<div id="optBarDiv">
			<input type="button" value="${lfn:message('third-wps:thirdWpsOrgRecord.btn.toSync')}" onclick="startToSync()">
			<input type="button" value="刷新同步记录" onclick="refreshRecord()">		
		</div>
		<center>
			<table id="Label_Tabel" class="tb_normal" width=95%  style="margin-top:15px;">
				<tr  LKS_LabelName="${lfn:message('third-wps:thirdWpsOrgRecord.message.label.syncDetail')}">
					<td>
					<table class="tb_normal" width=100%>
						<tr class="tb_normal">
							<td class="td_normal_title" width=25%>${lfn:message('third-wps:thirdWpsOrgRecord.message.isSync')}</td>
							<td id="synResult_isSync">
								<c:if test="${isHadSync}">${lfn:message('third-wps:thirdWpsOrgRecord.message.yes')}</c:if>
								<c:if test="${!isHadSync}">${lfn:message('third-wps:thirdWpsOrgRecord.message.no')}</c:if>
							</td>
						</tr>	
						<tr class="tb_normal">
							<td class="td_normal_title" width=25%>${lfn:message('third-wps:thirdWpsOrgRecord.message.currentDeptNum')}</td>
							<td id="synResult_deptNum">${deptNum}</td>
						</tr>	
						<tr class="tb_normal">
							<td class="td_normal_title" width=25%>${lfn:message('third-wps:thirdWpsOrgRecord.message.syncDeptNum')}</td>
							<td id="synResult_syncDeptNum">${syncDeptNum}</td>
						</tr>
						<c:if test="${syncFailedDeptNum > 0}">
							<tr class="tb_normal">
								<td class="td_normal_title" width=25%>${lfn:message('third-wps:thirdWpsOrgRecord.message.syncFailedDeptNum')}</td>
								<td id="synResult_syncFailedDeptNum">${syncFailedDeptNum}</td>
							</tr>
						</c:if>
						<tr class="tb_normal">
							<td class="td_normal_title" width=25%>${lfn:message('third-wps:thirdWpsOrgRecord.message.currentMemberNum')}</td>
							<td id="synResult_memberNum">${memberNum}</td>
						</tr>	
						<tr class="tb_normal">
							<td class="td_normal_title" width=25%>${lfn:message('third-wps:thirdWpsOrgRecord.message.syncMemberNum')}</td>
							<td id="synResult_syncMemberNum">${syncMemberNum}</td>
						</tr>
						<c:if test="${syncFailedMemberNum > 0}">
							<tr class="tb_normal">
								<td class="td_normal_title" width=25%>${lfn:message('third-wps:thirdWpsOrgRecord.message.syncFailedMemberNum')}</td>
								<td id="synResult_syncFailedMemberNum">${syncFailedMemberNum}</td>
							</tr>
						</c:if>
						</table>						 
					</td>
				</tr>
				<tr  LKS_LabelName="${lfn:message('third-wps:thirdWpsOrgRecord.message.label.syncRecord')}" >
					<td>
					<list:listview id="wpsSyncRecordList" channel="wpsSyncRecordListChannel">	
					<!-- 列表视图 -->
					<list:colTable  isDefault="false" name="gridtableList" 
					rowHref="/third/wps/third_wps_org_record/thirdWpsOrgRecord.do?method=view&fdId=!{fdId}" >
						<ui:source type="AjaxJson">
										{url:'/third/wps/third_wps_org_record/thirdWpsOrgRecord.do?method=data&rowsize=16'}
									</ui:source>
						<list:col-serial></list:col-serial>		
						<list:col-auto props="fdStartTime;fdEndTime;fdSyncTag;"></list:col-auto>
					</list:colTable>				
					</list:listview>
					<list:paging channel="wpsSyncRecordListChannel"></list:paging>
					</td>
				</tr>		
			</table>

		<script type="text/javascript">
		 seajs.use(['lui/jquery','lui/dialog', 'lui/util/env',"lui/topic"], function($, dialog, env, topic){
			 window.startToSync = function(){
					var url = env.fn.formatUrl("/third/wps/third_wps_org_record/thirdWpsOrgRecord.do?method=startToSync");
					window.syncLoading = dialog.loading();
					$.ajax({
			        	url: url ,
			        	dataType: "json",
			        	data:{},
			        	type:"get",
			        	async: false,
			        	success: function(data,textStatus, jqXHR) {	
			        		window.syncLoading.hide();
			        		if(data.status == "ERROR"){
			        			dialog.alert(data.errorMsg);
			        		}else{
			        			dialog.alert("${lfn:message('third-wps:thirdWpsOrgRecord.dialog.syncRemind')}",function(){			        			
				        			 LUI.$("#Label_Tabel_Label_Btn_2").click();
				        			 topic.channel("wpsSyncRecordListChannel").publish("list.refresh");	        			
				        		});		
			        		}	
			            },
			            error:function(){
			            	window.syncLoading.hide();
			            }
					});
			}
			 
			 window.refreshRecord = function(){
				 //LUI.$("#Label_Tabel_Label_Btn_2").click();
    			 topic.channel("wpsSyncRecordListChannel").publish("list.refresh");	
    			 window.getSyncRecordInfo();
			 }
			 			 
			 window.getSyncRecordInfo = function(){
				 var url = env.fn.formatUrl("/third/wps/third_wps_org_element/thirdWpsOrgElement.do?method=getSyncOrgInfo");
					$.ajax({
			        	url: url ,
			        	dataType: "json",
			        	data:{},
			        	type:"get",
			        	async: false,
			        	success: function(data) {				        		
			        		if(data){			        					        		
		        				if(data.isHadSync){
		        					$("#synResult_isHadSync").html("${lfn:message('third-wps:thirdWpsOrgRecord.message.yes')}");
		        				}else{
		        					$("#synResult_isHadSync").html("${lfn:message('third-wps:thirdWpsOrgRecord.message.yes')}");
		        				}
		        				if(data.deptNum >= 0){
		        					$("#synResult_deptNum").html(data.deptNum);
		        				}
		        				if(data.memberNum >= 0){
		        					$("#synResult_memberNum").html(data.memberNum);
		        				}
		        				if(data.syncDeptNum >= 0){
		        					$("#synResult_syncDeptNum").html(data.syncDeptNum);
		        				}
		        				if(data.syncMemberNum >= 0){
		        					$("#synResult_syncMemberNum").html(data.syncMemberNum);
		        				}
		        				if(data.syncFailedDeptNum > 0){
		        					$("#synResult_syncFailedDeptNum").html(data.syncFailedDeptNum);
		        				}
		        				if(data.syncFailedMemberNum > 0){
		        					$("#synResult_syncFailedMemberNum").html(data.syncFailedMemberNum);
		        				}
			        		}			      		
			            }
					});
			 }
		 });
		</script>
	</template:replace>
</template:include>
