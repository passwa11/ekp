<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ page import="com.landray.kmss.hr.organization.model.HrOrganizationSyncSetting"%>
<%
	HrOrganizationSyncSetting syncSetting = new HrOrganizationSyncSetting();
	request.setAttribute("hrToEkpEnable", syncSetting.getHrToEkpEnable());
	String url = request.getParameter("parent");
	url = "/sys/organization/sys_org_org/sysOrgOrg.do?"+(url==null?"":"parent="+url+"&");
	pageContext.setAttribute("actionUrl", url);
%>
<template:include ref="config.profile.list">
	<template:replace name="title">${ lfn:message('sys-organization:sysOrgElement.org') }</template:replace>
	<template:replace name="head">
		<link rel="stylesheet" href="${LUI_ContextPath}/hr/organization/resource/css/comonReset.css" />	
		<style>
			.post_part{
				border: 1px solid #4285F4;
				color:#4285F4;
			}
			.post_main{
				border: 1px solid #F48D42;
				color:#F48D42;
			}
			.post_key{
				color: #0DB22E;
				border: 1px solid rgba(30,199,64,0.80);				
			}
			.post_secret{
				border: 1px solid #F48D42;
				color: #F48D42;						
			}				
			.post_part, .post_main, .post_key, .post_secret{
				font-size:10px;
				padding:2px 5px;
				border-radius: 2px;
				display:inline-block;
				margin-left:4px;
				line-height: 15px;
			    height: 13px;
				overflow: hidden;
			    text-overflow: ellipsis;
			    white-space: nowrap;			    
			}
			.list_name{
				display:inline-block;
				
			}
			.list_name span{
				float:left;
				position:relative;
				top:-2px;
			}
			.clearFix{
				zoom:1;
			}
			.clearFix:after{
				content: "";
	            clear: both;
	            visibility:hidden;
	            height:0;
	            display: block;
			}
			.lui_listview_columntable_table tr>td:first-child{
			    width: 30px;
		 		text-align: right;
			}					
		</style>
	</template:replace>
	<template:replace name="content">
		<list:criteria id="criteria1">
			<list:cri-ref key="fdName" ref="criterion.sys.docSubject" title="${ lfn:message('hr-organization:hrOrganizationConPost.fdPerson') }" style="width: 200px;"></list:cri-ref>
			<list:cri-criterion title="${ lfn:message('hr-staff:hrStaffPersonInfo.fdStatus') }" key="_fdStatus">
				<list:box-select>
					<list:item-select cfg-defaultValue="official">
							<ui:source type="Static">
							[
								{text:'${ lfn:message('hr-staff:hrStaffPersonInfo.fdStatus.onpost') }', value:'official'},
								{text:'${ lfn:message('hr-staff:hrStaffPersonInfo.fdStatus.leave') }',value:'quit'}
							]
						</ui:source>
					</list:item-select>
				</list:box-select> 
			</list:cri-criterion>
			<list:cri-criterion title="${ lfn:message('hr-organization:hrOrganizationConPost.fdType') }" key="fdType">
				<list:box-select>
					<list:item-select>
							<ui:source type="Static">
							[
								{text:"${ lfn:message('hr-organization:hr.organization.info.majorPosition') }", value:"1"},
								{text:"${ lfn:message('hr-organization:hr.organization.info.concurrentPost') }", value:"2"}
							]
						</ui:source>
					</list:item-select>
				</list:box-select> 
			</list:cri-criterion>						
		</list:criteria>
		<div style="height:16px;background-color:#f8f8f8;"></div>		
		<c:set var="orgOrder" value="fdOrder" scope="request"></c:set>
		<div class="hr_list_box">		
		<div class="lui_list_operation">
			<div class="lui_list_operation_order_btn">
				<list:selectall></list:selectall>
			</div>		
			<div class="hr_list_operation_box">
				<div style="display: inline-block;vertical-align: middle;">
					<ui:toolbar id="Btntoolbar" count="5">
						<c:if test="${hrToEkpEnable }">
							<c:if test="${param.available != '0'}">
								<kmss:auth requestURL="/hr/organization/hr_organization_person/hrOrganizationPerson.do?method=add&fdId=${param.fdParentId }">
									<ui:button text="${lfn:message('hr-organization:hr.organization.info.addEmployee') }" onclick="addStaffPerson('${param.fdParentId }')" order="3" ></ui:button>
									<kmss:authShow roles="ROLE_HRSTAFF_EDIT">
										<ui:button text="${lfn:message('hr-organization:hr.organization.info.addBatchEmployee') }" onclick="window._modify('${param.fdParentId }')" order="3" ></ui:button>
									</kmss:authShow>
								</kmss:auth>
								<!-- 快速排序 -->
								<c:import url="/sys/profile/common/change_order_num.jsp" charEncoding="UTF-8">
									<c:param name="modelName" value="com.landray.kmss.hr.staff.model.HrStaffPersonInfo"></c:param>
									<c:param name="property" value="fdOrder"></c:param>
									<c:param name="column" value="8"></c:param>
								</c:import>							
							</c:if>
						</c:if>
					</ui:toolbar>
				</div>
			</div>
		</div>
		<ui:fixed elem=".lui_list_operation"></ui:fixed>
		<!-- 内容列表 -->
		<list:listview>
			<ui:source type="AjaxJson">
				{url:'/hr/staff/hr_staff_person_info/hrStaffPersonInfo.do?method=findPersonList&available=${JsParam.available}&orgOrder=${orgOrder}&queryPerson=${param.fdParentId }&IsHrOrg=true'}
			</ui:source>
			<list:colTable isDefault="true" layout="sys.ui.listview.columntable" 
				rowHref="/hr/staff/hr_staff_person_info/hrStaffPersonInfo.do?method=view&fdId=!{fdId}">
				<list:col-checkbox></list:col-checkbox>
				<list:col-auto props="fdName,fdPerson.fdStaffNo,fdSex,fdStatus,fdOrgPostNames,fdReportLeaderName,fdOrder"></list:col-auto>
			</list:colTable>
		</list:listview>
		<br>
		<!-- 分页 -->
	 	<list:paging/>
	 </div>	
	 	<script type="text/javascript">
		 	seajs.use(['lui/jquery', 'lui/dialog', 'lui/topic'], function($, dialog, topic) {
		 		// 监听新建更新等成功后刷新
				topic.subscribe('successReloadPage', function() {
					topic.publish("list.refresh");
				});
		 		topic.subscribe("list.loaded",function(data){
		 			console.log(data)
		 		})
		 		window.viewInfo=function(fdId){
		 			Com_OpenWindow(Com_Parameter.ContextPath+"hr/staff/hr_staff_person_info/hrStaffPersonInfo.do?method=view&fdId="+fdId);
		 		}
		 		
		 		window.addStaffPerson=function(fdParentId){
		 			Com_OpenWindow('<c:url value="/hr/staff/hr_staff_person_info/hrStaffPersonInfo.do" />?method=add&fdParentId='+fdParentId);
		 		}
		 		//批量导入员工
				window._modify = function(fdParentId) {
					dialog.iframe('/hr/organization/hr_organization_tree/person/hrStaffPersonInfo_import.jsp?fdParentId='+fdParentId, 
							'<bean:message key="hr-staff:hrStaff.import.button.modify"/>', function(data) {
							topic.publish('list.refresh');
					}, {
						width : 680,
						height : 500
					});
				};
		 	});
	 	</script>
	</template:replace>
</template:include>
