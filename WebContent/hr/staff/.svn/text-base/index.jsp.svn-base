<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ page import="com.landray.kmss.hr.staff.service.spring.HrStaffPersonInfoValidator,com.landray.kmss.hr.staff.service.spring.HrStaffEntryValidator"%>
<%@ page import="com.landray.kmss.sys.authentication.intercept.ValidatorRequestContext,com.landray.kmss.util.SpringBeanUtil"%>
<%@ page import="com.landray.kmss.hr.staff.model.HrStaffConfig" %>
<template:include ref="default.list" spa="true"  rwd="true">
<template:replace name="title">
		<c:out value="${ lfn:message('hr-staff:module.hr.staff') }"></c:out>
	</template:replace>
	<template:replace name="head">
		<script type="text/javascript">
				seajs.use(['theme!list','theme!module']);	
			</script>
		<link type="text/css" rel="stylesheet" href="${LUI_ContextPath}/hr/staff/resource/css/hr_staff.css?s_cache=${LUI_Cache}"/>
	</template:replace>
	<template:replace name="nav">
		<div class="lui_list_noCreate_frame">
		<ui:combin ref="menu.nav.create">
		<ui:varParam name="title" value="${ lfn:message('hr-staff:module.hr.staff') }" />
			<ui:varParam name="button">
				[
					{
						"text": "",
						"href": "javascript:void(0);",
						"icon": "hr_staff"
					}
				]
			</ui:varParam>			
		</ui:combin>
		</div>
		<div class="lui_list_nav_frame">
			<ui:accordionpanel>
				
				<ui:content title="${ lfn:message('hr-staff:hr.staff.nav.staff.manage')}"  expand="true">
				
					<ui:combin ref="menu.nav.simple">
		  				<ui:varParam name="source">
		  					<ui:source type="Static">
		  					[{
		  						"text" : "${ lfn:message('hr-staff:hr.staff.nav.overview')}",
		  						"href" :  "/overview",
								"router" : true,		  					
			  					"icon" : "lui_iconfont_navleft_hr_overview"
		  					},
		  					<kmss:authShow roles="ROLE_SYS_TIME_DEFAULT">
		  					{
		  						"text" : "${ lfn:message('hr-staff:hr.staff.nav.attendance.management')}",
		  						"href" :  "/management",
								"router" : true,		  					
			  					"icon" : "lui_iconfont_navleft_hr_leave"
		  					},
		  					</kmss:authShow>
		  					<kmss:authShow roles="ROLE_HRSTAFF_EMOLUMENT">
		  					{
		  						"text" : "${ lfn:message('hr-staff:hr.staff.nav.benefits')}",
		  						"href" :  "/benefits",
								"router" : true,		  					
			  					"icon" : "lui_iconfont_navleft_hr_pay"
		  					},
		  					</kmss:authShow>
		  					<kmss:authShow roles="ROLE_HRSTAFF_PAYMENT">
		  					{
		  						"text" : "${ lfn:message('hr-staff:hr.staff.nav.payroll')}",
		  						"href" :  "/payroll",
								"router" : true,		  					
			  					"icon" : "lui_iconfont_navleft_hr_payroll"
		  					},
		  					</kmss:authShow>
		  					<kmss:authShow roles="ROLE_HRSTAFF_PAYMENT">
		  					{
		  						"text" : "${ lfn:message('hr-staff:hr.staff.nav.social.security')}",
		  						"href" :  "/security",
								"router" : true,		  					
			  					"icon" : "lui_iconfont_navleft_hr_payroll"
		  					},
		  					</kmss:authShow>
		  					<kmss:authShow roles="ROLE_HRSTAFF_PAYMENT">
		  					{
		  						"text" : "${ lfn:message('hr-staff:hr.staff.nav.accumulation.fund')}",
		  						"href" :  "/accumulationFund",
								"router" : true,		  					
			  					"icon" : "lui_iconfont_navleft_hr_payroll"
		  					},
		  					</kmss:authShow>
<%-- 		  					<kmss:authShow roles="ROLE_HRSTAFF_PAYMENT"> --%>
<!-- 		  					{ -->
<%-- 		  						"text" : "${ lfn:message('hr-staff:hr.staff.nav.Ekp_H14_M_performance')}", --%>
<!-- 		  						"href" :  "/Ekp_H14_M_performance", -->
<!-- 								"router" : true,		  					 -->
<!-- 			  					"icon" : "lui_iconfont_navleft_hr_payroll" -->
<!-- 		  					}, -->
<%-- 		  					</kmss:authShow> --%>
<%-- 		  					<kmss:authShow roles="ROLE_HRSTAFF_PAYMENT"> --%>
<!-- 		  					{ -->
<%-- 		  						"text" : "${ lfn:message('hr-staff:hr.staff.nav.Ekp_H14_S')}", --%>
<!-- 		  						"href" :  "/Ekp_H14_S", -->
<!-- 								"router" : true,		  					 -->
<!-- 			  					"icon" : "lui_iconfont_navleft_hr_payroll" -->
<!-- 		  					}, -->
<%-- 		  					</kmss:authShow> --%>
<%-- 		  					<kmss:authShow roles="ROLE_HRSTAFF_PAYMENT"> --%>
<!-- 		  					{ -->
<%-- 		  						"text" : "${ lfn:message('hr-staff:hr.staff.nav.Ekp_H14_S1')}", --%>
<!-- 		  						"href" :  "/Ekp_H14_S1", -->
<!-- 								"router" : true,		  					 -->
<!-- 			  					"icon" : "lui_iconfont_navleft_hr_payroll" -->
<!-- 		  					}, -->
<%-- 		  					</kmss:authShow> --%>
		  				
<%-- 		  					<kmss:authShow roles="ROLE_HRSTAFF_PAYMENT"> --%>
<!-- 		  					{ -->
<%-- 		  						"text" : "${ lfn:message('hr-staff:hr.staff.nav.Ekp_H14_S2')}", --%>
<!-- 		  						"href" :  "/Ekp_H14_S2", -->
<!-- 								"router" : true,		  					 -->
<!-- 			  					"icon" : "lui_iconfont_navleft_hr_payroll" -->
<!-- 		  					}, -->
<%-- 		  					</kmss:authShow> --%>
		  					<kmss:authShow roles="ROLE_HRSTAFF_PAYMENT">
		  					{
		  						"text" : "${ lfn:message('hr-staff:hr.staff.nav.performance.contract.import')}",
		  						"href" :  "/performanceContractImport",
								"router" : true,		  					
			  					"icon" : "lui_iconfont_navleft_hr_payroll"
		  					},
		  					</kmss:authShow>
<%-- 		  					<kmss:authShow roles="ROLE_HRSTAFF_PAYMENT"> --%>
<!-- 		  					{ -->
<%-- 		  						"text" : "${ lfn:message('hr-staff:hr.staff.nav.ekp_H14_Intern')}", --%>
<!-- 		  						"href" :  "/ekp_H14_Intern", -->
<!-- 								"router" : true,		  					 -->
<!-- 			  					"icon" : "lui_iconfont_navleft_hr_payroll" -->
<!-- 		  					}, -->
<%-- 		  					</kmss:authShow> --%>
<%-- 		  					<kmss:authShow roles="ROLE_HRSTAFF_PAYMENT"> --%>
<!-- 		  					{ -->
<%-- 		  						"text" : "${ lfn:message('hr-staff:hr.staff.nav.performance.search')}", --%>
<!-- 		  						"href" :  "/performanceSearch", -->
<!-- 								"router" : true,		  					 -->
<!-- 			  					"icon" : "lui_iconfont_navleft_hr_payroll" -->
<!-- 		  					}, -->
<%-- 		  					</kmss:authShow> --%>
		  					<% if (com.landray.kmss.sys.subordinate.util.SubordinateUtil.getInstance().getModelByModuleAndUser("hr-staff:module.hr.staff").size() > 0) { %>
		  					{
		  						"text" : "${lfn:message('hr-staff:subordinate.hrStaffPersonInfo') }",
		  						"href" :  "/sys/subordinate",
			  					"router" : true,
			  					"icon" : "lui_iconfont_navleft_subordinate"
		  					},
		  					<% } %>
		  					]
		  					</ui:source>
		  				</ui:varParam>
	  				</ui:combin>	
		  				<div class="lui_accordionpanel_frame" id="parent1">
					<div class="lui_accordionpanel_content_frame">
						<ui:combin ref="menu.nav.knowledge.flat.all">
							<ui:varParams
								modelName="com.landray.kmss.kms.knowledge.model.KmsKnowledgeCategory" />
						</ui:combin>
					</div>
				</div> 
				</ui:content>
				<%
					HrStaffEntryValidator _val = (HrStaffEntryValidator)SpringBeanUtil.getBean("hrStaffEntryValidator");
					ValidatorRequestContext _ctx = new ValidatorRequestContext();
					boolean _flag = UserUtil.getKMSSUser().isAdmin()||UserUtil.checkRole("ROLE_HRSTAFF_READALL")||_val.validate(_ctx);
					if(_flag){ 
				%>
				<ui:content title="${ lfn:message('hr-staff:hr.staff.nav.staff.entry.info')}" expand="false">
					<ui:combin ref="menu.nav.simple">
						<ui:varParam name="source">
							<ui:source type="Static">
								[
									{
				  						"text" : "${ lfn:message('hr-staff:table.hrStaffEntry.status1')}",
				  						"href" :  "/entryStatus1",
										"router" : true,		  					
					  					"icon" : "lui_iconfont_navleft_hr_employees"
				  					},{
				  						"text" : "${ lfn:message('hr-staff:table.hrStaffEntry.status2')}",
				  						"href" :  "/entryStatus2",
										"router" : true,		  					
					  					"icon" : "lui_iconfont_navleft_hr_employees"
				  					}
								]
							</ui:source>
						</ui:varParam>
					</ui:combin>
				</ui:content>
				<%} %>
				<ui:content title="${ lfn:message('hr-staff:hr.staff.nav.staff.file.info')}"  expand="false">
					<ui:combin ref="menu.nav.simple">
		  				<ui:varParam name="source">
		  					<ui:source type="Static">
		  					[
							<%
								HrStaffConfig hrStaffConfig = new HrStaffConfig();
								boolean ownerFlag = "true".equals(hrStaffConfig.getFdSelfView());
								pageContext.setAttribute("userSysOrgPersonFdId",UserUtil.getUser().getFdId());
								if(ownerFlag){
							%>
							{
								"text" : "${ lfn:message('hr-staff:hr.staff.nav.staff.owner.file')}",
								"href" :  "/ownerFile",
								"router" : true,
								"icon" : "lui_iconfont_navleft_hr_agreement"
							},
							<% } %>
		  					<%
		  						HrStaffPersonInfoValidator val = (HrStaffPersonInfoValidator)SpringBeanUtil.getBean("hrStaffPersonInfoValidator");
								ValidatorRequestContext ctx = new ValidatorRequestContext();
								boolean flag = UserUtil.getKMSSUser().isAdmin()||UserUtil.checkRole("ROLE_HRSTAFF_READALL")||val.validate(ctx);
		  						if(flag){ %>
		  					{
		  						"text" : "${ lfn:message('hr-staff:hr.staff.nav.employee.information.in')}",
		  						"href" :  "/informationIn",
								"router" : true,		  					
			  					"icon" : "lui_iconfont_navleft_hr_employees"
		  					},

<!-- 		  					{ -->
<%-- 		  						"text" : "${ lfn:message('hr-staff:hr.staff.nav.employee.information.quit')}", --%>
<!-- 		  						"href" :  "/informationQuit", -->
<!-- 								"router" : true,		  					 -->
<!-- 			  					"icon" : "lui_iconfont_navleft_hr_quitEmployees" -->
<!-- 		  					}, -->
							<%	} %>
		  					{
		  						"text" : "${ lfn:message('hr-staff:hrStaffPersonExperience.type.contract')}",
		  						"href" :  "/contract",
								"router" : true,		  					
			  					"icon" : "lui_iconfont_navleft_hr_agreement"
		  					},{
		  						"text" : "${ lfn:message('hr-staff:hrStaffTrackRecord.record')}",
		  						"href" :  "/trackrecord",
								"router" : true,		  					
			  					"icon" : "lui_iconfont_navleft_hr_hr_trackRecord"
		  					},{
								"text" : "${ lfn:message('hr-staff:hrStaffMoveRecord.record')}",
								"href" :  "/moverecord",
								"router" : true,
								"icon" : "lui_iconfont_navleft_hr_hr_trackRecord"
							},{
		  						"text" : "${ lfn:message('hr-staff:hrStaffPerson.family')}",
		  						"href" :  "/familyInfo",
								"router" : true,		  					
			  					"icon" : "lui_iconfont_navleft_hr_hr_familyInfo"
		  					},{
		  						"text" : "${ lfn:message('hr-staff:hrStaffPersonExperience.type.work')}",
		  						"href" :  "/work",
								"router" : true,		  					
			  					"icon" : "lui_iconfont_navleft_hr_workexp"
		  					},{
		  						"text" : "${ lfn:message('hr-staff:hrStaffPersonExperience.type.education')}",
		  						"href" :  "/education",
								"router" : true,		  					
			  					"icon" : "lui_iconfont_navleft_hr_education"
		  					},{
		  						"text" : "${ lfn:message('hr-staff:hrStaffPersonExperience.type.training')}",
		  						"href" :  "/training",
								"router" : true,		  					
			  					"icon" : "lui_iconfont_navleft_hr_train"
		  					},{
		  						"text" : "${ lfn:message('hr-staff:hrStaffPersonExperience.type.qualification')}",
		  						"href" :  "/qualification",
								"router" : true,		  					
			  					"icon" : "lui_iconfont_navleft_hr_certificate"
		  					},{
		  						"text" : "${ lfn:message('hr-staff:hrStaffPersonExperience.type.bonusMalus')}",
		  						"href" :  "/bonusMalus",
								"router" : true,		  					
			  					"icon" : "lui_iconfont_navleft_hr_reward"
		  					}]
		  					</ui:source>
		  				</ui:varParam>
	  				</ui:combin>	
				</ui:content>
				<ui:content title="${ lfn:message('hr-staff:hr.staff.nav.statistical.report')}"  expand="false">
					<ui:combin ref="menu.nav.simple">
		  				<ui:varParam name="source">
		  					<ui:source type="Static">
		  					[{
		  						"text" : "${ lfn:message('hr-staff:hrStaffPersonReport.type.reportStaffNum')}",
		  						"href" :  "/reportStaffNum",
								"router" : true,		  					
			  					"icon" : "lui_iconfont_navleft_hr_statistics"
		  					},{
		  						"text" : "${ lfn:message('hr-staff:hrStaffPersonReport.type.reportAge')}",
		  						"href" :  "/reportAge",
								"router" : true,		  					
			  					"icon" : "lui_iconfont_navleft_hr_statistics"
		  					},{
		  						"text" : "${ lfn:message('hr-staff:hrStaffPersonReport.type.reportWorkTime')}",
		  						"href" :  "/reportWorkTime",
								"router" : true,		  					
			  					"icon" : "lui_iconfont_navleft_hr_statistics"
		  					},{
		  						"text" : "${ lfn:message('hr-staff:hrStaffPersonReport.type.reportEducation')}",
		  						"href" :  "/reportEducation",
								"router" : true,		  					
			  					"icon" : "lui_iconfont_navleft_hr_statistics"
		  					},{
		  						"text" : "${ lfn:message('hr-staff:hrStaffPersonReport.type.reportStaffingLevel')}",
		  						"href" :  "/reportStaffingLevel",
								"router" : true,		  					
			  					"icon" : "lui_iconfont_navleft_hr_statistics"
		  					},{
		  						"text" : "${ lfn:message('hr-staff:hrStaffPersonReport.type.reportStatus')}",
		  						"href" :  "/reportStatus",
								"router" : true,		  					
			  					"icon" : "lui_iconfont_navleft_hr_statistics"
		  					},{
								"text" : "每月异动信息",
								"href" :  "/hrStaffMoveMonthReport",
								"router" : true,
								"icon" : "lui_iconfont_navleft_hr_statistics"
							},
<!-- 							{ -->
<!-- 								"text" : "所有异动信息", -->
<!-- 								"href" :  "/hrStaffMoveAllReport", -->
<!-- 								"router" : true, -->
<!-- 								"icon" : "lui_iconfont_navleft_hr_statistics" -->
<!-- 							}, -->
							{
								"text" : "招聘信息",
								"href" :  "/hrStaffRecruitReport",
								"router" : true,
								"icon" : "lui_iconfont_navleft_hr_statistics"
							},{
								"text" : "当月新员工入职统计",
								"href" :  "/hrEntryMonthReport",
								"router" : true,
								"icon" : "lui_iconfont_navleft_hr_statistics"
							},{
								"text" : "按职位类别统计离职率",
								"href" :  "/hrLeaveLevelMonthReport",
								"router" : true,
								"icon" : "lui_iconfont_navleft_hr_statistics"
							},{
								"text" : "人员入离职率汇总",
								"href" :  "/hrLeaveMonthReport",
								"router" : true,
								"icon" : "lui_iconfont_navleft_hr_statistics"
							},{
								"text" : "人员结构分布表（职务类别不含实习生临时工，其他均含）",
								"href" :  "/hrPersonalStructureMonthReport",
								"router" : true,
								"icon" : "lui_iconfont_navleft_hr_statistics"
							},{
								"text" : "人事月报-编制人员进出",
								"href" :  "/hrPersonalMonthReport",
								"router" : true,
								"icon" : "lui_iconfont_navleft_hr_statistics"
							},{
								"text" : "人事月报-职类人员进出",
								"href" :  "/hrRankMonthReport",
								"router" : true,
								"icon" : "lui_iconfont_navleft_hr_statistics"
							}
	<!-- 					,{ -->	
							<!--	"text" : "财务个税人员信息采集表", -->	
							<!--	"href" :  "/hrPersonalIncomeTaxReportCaiwu", -->	
							<!--	"router" : true, -->	
							<!--	"icon" : "lui_iconfont_navleft_hr_statistics" -->	
						<!--	} -->	
							,{
								"text" : "个税人员信息采集表",
								"href" :  "/hrPersonalIncomeTaxReport",
								"router" : true,
								"icon" : "lui_iconfont_navleft_hr_statistics"
							},{
		  						"text" : "${ lfn:message('hr-staff:hrStaffPersonReport.type.reportMarital')}",
		  						"href" :  "/reportMarital",
								"router" : true,		  					
			  					"icon" : "lui_iconfont_navleft_hr_statistics"
		  					}]
		  					</ui:source>
		  				</ui:varParam>
	  				</ui:combin>	
				</ui:content>
				<kmss:authShow roles="ROLE_HRSTAFF_WARNING">
				<ui:content title="${ lfn:message('hr-staff:hr.staff.nav.alert.warning')}"  expand="false">
					<ui:combin ref="menu.nav.simple">
		  				<ui:varParam name="source">
		  					<ui:source type="Static">
		  					[{
		  						"text" : "${ lfn:message('hr-staff:hr.staff.nav.last.birthday')}",
		  						"href" :  "/birthday",
								"router" : true,		  					
			  					"icon" : "lui_iconfont_navleft_hr_beBirthday"
		  					},{
		  						"text" : "${ lfn:message('hr-staff:hr.staff.nav.contract.expiration')}",
		  						"href" :  "/contractExpiration",
								"router" : true,		  					
			  					"icon" : "lui_iconfont_navleft_hr_beagreement"
		  					},{
		  						"text" : "${ lfn:message('hr-staff:hr.staff.nav.trial.expiration')}",
		  						"href" :  "/trial",
								"router" : true,		  					
			  					"icon" : "lui_iconfont_navleft_hr_beformal"
		  					}]
		  					</ui:source>
		  				</ui:varParam>
	  				</ui:combin>	
				</ui:content>
				</kmss:authShow>
				<kmss:authShow roles="ROLE_HRSTAFF_BACKGROUND,ROLE_HRSTAFF_PAYMENT">
				<ui:content title="${ lfn:message('hr-staff:hr.staff.nav.other.operations')}" expand="false">
					<ui:combin ref="menu.nav.simple">
		  				<ui:varParam name="source">
		  					<ui:source type="Static">
		  					[
		  					<kmss:authShow roles="ROLE_HRSTAFF_BACKGROUND">
		  					{
								"text" : "${ lfn:message('list.manager') }",
								"icon" : "lui_iconfont_navleft_com_background",
								"router" : true,
								"href" : "/management1"
							}
		  					</kmss:authShow>
		  					]
		  					</ui:source>
		  				</ui:varParam>
	  				</ui:combin>	
				</ui:content>
				</kmss:authShow>
			</ui:accordionpanel>
		</div>
	</template:replace>
	<template:replace name="content">
			  <ui:tabpanel id="hrStaffPanel" layout="sys.ui.tabpanel.list"  cfg-router="true">
				<ui:content id="entryStatus1" title="${lfn:message('hr-staff:table.hrStaffEntry.status1')}" cfg-route="{path:'/entryStatus1'}">
					<ui:iframe src="${LUI_ContextPath }/hr/staff/hr_staff_entry/index1.jsp"></ui:iframe>
				</ui:content>
				<ui:content id="entryStatus2" title="${lfn:message('hr-staff:table.hrStaffEntry.status2')}" cfg-route="{path:'/entryStatus2'}">
					<ui:iframe src="${LUI_ContextPath }/hr/staff/hr_staff_entry/index2.jsp"></ui:iframe>
				</ui:content>
				<ui:content id="hrStaffContent" title="${lfn:message('hr-staff:hr.staff.nav.overview') }" cfg-route="{path:'/overview'}">
				 	  <ui:iframe id="overview" src="${LUI_ContextPath }/hr/staff/index_new.jsp"></ui:iframe>
				</ui:content>
				<ui:content id="hrStaffContentOwnerFile" title="${lfn:message('hr-staff:hr.staff.nav.staff.owner.file') }" cfg-route="{path:'/ownerFile'}">
					<ui:iframe id="ownerFile" src="${LUI_ContextPath }/hr/staff/hr_staff_person_info/hrStaffPersonInfo.do?method=view&readOnly=true&fdId=${userSysOrgPersonFdId}"></ui:iframe>
				</ui:content>
				<ui:content id="hrStaffTrackRecord" title="${lfn:message('hr-staff:hr.staff.nav.track.record') }" cfg-route="{path:'/trackRecord'}">
				 	  <ui:iframe id="overview" src="${LUI_ContextPath }/hr/staff/hr_staff_person_track_record/index.jsp"></ui:iframe>
				</ui:content>
			  <ui:content id="hrStaffMoveRecord" title="${lfn:message('hr-staff:hr.staff.nav.move.record') }" cfg-route="{path:'/moveRecord'}">
				  <ui:iframe id="overview" src="${LUI_ContextPath }/hr/staff/hr_staff_move_record/index.jsp"></ui:iframe>
			  </ui:content>
				<ui:content id="hrStaffPersonFamily" title="${lfn:message('hr-staff:hrStaffPerson.family')}" cfg-route="{path:'/familyInfo'}">
				 	  <ui:iframe id="overview" src="${LUI_ContextPath }/hr/staff/hr_staff_person_family/index.jsp"></ui:iframe>
				</ui:content>
				<ui:content id="hrStaffContentManage" title="${lfn:message('hr-staff:hr.staff.nav.attendance.management') }" cfg-route="{path:'/management'}">
			 		 <ui:iframe id="hrStaffAttendanceManage" src="${LUI_ContextPath}/hr/staff/hr_staff_attendance_manage/manage/index.jsp"></ui:iframe>
			  	</ui:content>
			  	<ui:content id="hrStaffContentBenefits" title="${lfn:message('hr-staff:hr.staff.nav.benefits') }" cfg-route="{path:'/benefits11'}">
				 	 <ui:iframe id="hrStaffEmolumentWelfare" src="${LUI_ContextPath}/hr/staff/hr_staff_emolument_welfare/"></ui:iframe>
				  </ui:content>
			  	<ui:content id="hrStaffContentSecurity" title="${lfn:message('hr-staff:hr.staff.nav.social.security') }" cfg-route="{path:'/fund'}">
				 	 <ui:iframe id="hrStaffSocialSecurity" src="${LUI_ContextPath}/hr/staff/hr_staff_social_security/"></ui:iframe>
				  </ui:content>
			  	<ui:content id="hrStaffContentAccumulationFund" title="${lfn:message('hr-staff:hr.staff.nav.accumulation.fund') }" cfg-route="{path:'/accumulationFund'}">
				 	 <ui:iframe id="hrStaffAccumulationFund" src="${LUI_ContextPath}/hr/staff/hr_staff_accumulation_fund/"></ui:iframe>
				  </ui:content>
			  	<ui:content id="hrStaffContentEkp_H14_M_performance" title="${lfn:message('hr-staff:hr.staff.nav.Ekp_H14_M_performance') }" cfg-route="{path:'/Ekp_H14_M_performance'}">
				 	 <ui:iframe id="hrStaffEkp_H14_M_performance" src="${LUI_ContextPath}/hr/staff/hr_staff_Ekp_H14_M_performance/"></ui:iframe>
				  </ui:content>
			  	<ui:content id="hrStaffContentEkp_H14_S" title="${lfn:message('hr-staff:hr.staff.nav.Ekp_H14_S') }" cfg-route="{path:'/Ekp_H14_S'}">
				 	 <ui:iframe id="hrStaffEkp_H14_S" src="${LUI_ContextPath}/hr/staff/hr_staff_Ekp_H14_S/"></ui:iframe>
				  </ui:content>
			  	<ui:content id="hrStaffContentEkp_H14_S1" title="${lfn:message('hr-staff:hr.staff.nav.Ekp_H14_S1') }" cfg-route="{path:'/Ekp_H14_S1'}">
				 	 <ui:iframe id="hrStaffEkp_H14_S1" src="${LUI_ContextPath}/hr/staff/hr_staff_Ekp_H14_S1/"></ui:iframe>
				  </ui:content>
			  	<ui:content id="hrStaffContentEkp_H14_S2" title="${lfn:message('hr-staff:hr.staff.nav.Ekp_H14_S2') }" cfg-route="{path:'/Ekp_H14_S2'}">
				 	 <ui:iframe id="hrStaffEkp_H14_S2" src="${LUI_ContextPath}/hr/staff/hr_staff_Ekp_H14_S2/"></ui:iframe>
				  </ui:content>
			  	<ui:content id="hrStaffContentEkp_H14_nS" title="${lfn:message('hr-staff:hr.staff.nav.Ekp_H14_nS') }" cfg-route="{path:'/Ekp_H14_nS'}">
				 	 <ui:iframe id="hrStaffEkp_H14_nS" src="${LUI_ContextPath}/hr/staff/hr_staff_Ekp_H14_nS/"></ui:iframe>
				  </ui:content>
			  	<ui:content id="ekp_H14_Intern" title="${lfn:message('hr-staff:hr.staff.nav.ekp_H14_Intern') }" cfg-route="{path:'/ekp_H14_Intern'}">
				 	 <ui:iframe id="hrStaffEkp_H14_Intern" src="${LUI_ContextPath}/hr/staff/hr_staff_Ekp_H14_Intern/"></ui:iframe>
				  </ui:content>
			  	<ui:content id="hrStaffContentPerformanceContractImport" title="${lfn:message('hr-staff:hr.staff.nav.performance.contract.import') }" cfg-route="{path:'/performanceContractImport'}">
				 	 <ui:iframe id="hrStaffPerformanceContractImport" src="${LUI_ContextPath}/hr/staff/hr_staff_performance_contract_import/"></ui:iframe>
				  </ui:content>
			  	<ui:content id="hrStaffContentPerformanceSearch" title="${lfn:message('hr-staff:hr.staff.nav.performance.search') }" cfg-route="{path:'/performanceSearch'}">
				 	 <ui:iframe id="hrStaffPerformanceSearch" src="${LUI_ContextPath}/hr/staff/hr_staff_performance_search/"></ui:iframe>
				  </ui:content>
			  	<ui:content id="hrStaffContentPayroll" title="${lfn:message('hr-staff:hr.staff.nav.payroll') }" cfg-route="{path:'/payroll'}">
			 	 <ui:iframe id="hrStaffPayrollIssuance" src="${LUI_ContextPath}/hr/staff/hr_staff_payroll_issuance/index.jsp?type=payrollIssuance"></ui:iframe>
			  </ui:content>
			   <ui:content id="hrStaffContentIn" title="${lfn:message('hr-staff:hr.staff.nav.employee.information.in') }" cfg-route="{path:'/informationIn'}">
			 	 <ui:iframe id="hrStaffPersonIn" src="${LUI_ContextPath}/hr/staff/hr_staff_person_info/index.jsp"></ui:iframe>
			  </ui:content>
			   <ui:content id="hrStaffContentQuit" title="${lfn:message('hr-staff:hr.staff.nav.employee.information.quit') }">
			 	 <ui:iframe id="hrStaffPersonQuit" src="${LUI_ContextPath}/hr/staff/hr_staff_person_info/index_quit.jsp"></ui:iframe>
			  	</ui:content>
			   <ui:content id="hrStaffContentWork" title="${lfn:message('hr-staff:hrStaffPersonExperience.type.work') }">
			 	 <ui:iframe id="hrStaffWork" src="${LUI_ContextPath}/hr/staff/hr_staff_person_experience/work/"></ui:iframe>
			  </ui:content>
			 <ui:content id="hrStaffContentContractIndex" title="${lfn:message('hr-staff:hrStaffPersonExperience.type.contract') }">
			 	 <ui:iframe id="hrStaffContentContract" src="${LUI_ContextPath}/hr/staff/hr_staff_person_experience/contract/"></ui:iframe>
			  </ui:content>
			  <ui:content id="hrStaffContentEducation" title="${lfn:message('hr-staff:hrStaffPersonExperience.type.education') }">
			 	 <ui:iframe id="hrStaffEducation" src="${LUI_ContextPath}/hr/staff/hr_staff_person_experience/education/"></ui:iframe>
			  </ui:content>
			  <ui:content id="hrStaffContentTraining" title="${lfn:message('hr-staff:hrStaffPersonExperience.type.training') }">
			 	 <ui:iframe id="hrStaffTraining" src="${LUI_ContextPath}/hr/staff/hr_staff_person_experience/training/"></ui:iframe>
			  </ui:content>
			  <ui:content id="hrStaffContentQualification" title="${lfn:message('hr-staff:hrStaffPersonExperience.type.qualification') }">
			 	 <ui:iframe id="hrStaffQualification" src="${LUI_ContextPath}/hr/staff/hr_staff_person_experience/qualification/"></ui:iframe>
			  </ui:content>
			   <ui:content id="hrStaffContentBonusMalus" title="${lfn:message('hr-staff:hrStaffPersonExperience.type.bonusMalus') }">
			 	 <ui:iframe id="hrStaffBonusMalus" src="${LUI_ContextPath}/hr/staff/hr_staff_person_experience/bonusMalus/"></ui:iframe>
			  </ui:content>
				 <ui:content id="hrStaffContentReportStaffNum" title="${lfn:message('hr-staff:hrStaffPersonReport.type.reportStaffNum') }">
				 	 <ui:iframe id="reportStaffNum" src="${LUI_ContextPath}/hr/staff/hr_staff_person_report/index.jsp?type=reportStaffNum"></ui:iframe>
				  </ui:content>
				  <ui:content id="hrStaffContentReportAge" title="${lfn:message('hr-staff:hrStaffPersonReport.type.reportAge') }">
				 	 <ui:iframe id="reportAge" src="${LUI_ContextPath}/hr/staff/hr_staff_person_report/index.jsp?type=reportAge"></ui:iframe>
				  </ui:content>
				 <ui:content id="hrStaffContentReportWorkTime" title="${lfn:message('hr-staff:hrStaffPersonReport.type.reportWorkTime') }">
				 	 <ui:iframe id="reportWorkTime" src="${LUI_ContextPath}/hr/staff/hr_staff_person_report/index.jsp?type=reportWorkTime"></ui:iframe>
				  </ui:content>
				 <ui:content id="hrStaffContentReportEducation" title="${lfn:message('hr-staff:hrStaffPersonReport.type.reportEducation') }">
				 	 <ui:iframe id="reportEducation" src="${LUI_ContextPath}/hr/staff/hr_staff_person_report/index.jsp?type=reportEducation"></ui:iframe>
				  </ui:content>
				 <ui:content id="hrStaffContentReportStaffingLevel" title="${lfn:message('hr-staff:hrStaffPersonReport.type.reportStaffingLevel') }">
				 	 <ui:iframe id="reportStaffingLevel" src="${LUI_ContextPath}/hr/staff/hr_staff_person_report/index.jsp?type=reportStaffingLevel"></ui:iframe>
				  </ui:content>
				 <ui:content id="hrStaffContentReportStatus" title="${lfn:message('hr-staff:hrStaffPersonReport.type.reportStatus') }">
				 	 <ui:iframe id="reportStatus" src="${LUI_ContextPath}/hr/staff/hr_staff_person_report/index.jsp?type=reportStatus"></ui:iframe>
				  </ui:content>
				 <ui:content id="hrStaffContentReportMarital" title="${lfn:message('hr-staff:hrStaffPersonReport.type.reportMarital') }">
				 	 <ui:iframe id="reportMarital" src="${LUI_ContextPath}/hr/staff/hr_staff_person_report/index.jsp?type=reportMarital"></ui:iframe>
				  </ui:content>
				  <ui:content id="hrStaffContentBirthday" title="${ lfn:message('hr-staff:hr.staff.nav.last.birthday')}">
				 	 <ui:iframe id="hrStaffBirthday" src="${LUI_ContextPath}/hr/staff/hr_staff_person_info/index.jsp?type=warningBirthday"></ui:iframe>
				  </ui:content>
				   <ui:content id="hrStaffContentContract" title="${ lfn:message('hr-staff:hr.staff.nav.contract.expiration')}">
				 	 <ui:iframe id="hrStaffContract" src="${LUI_ContextPath}/hr/staff/hr_staff_person_info/index.jsp?type=warningContract"></ui:iframe>
				  </ui:content>
				   <ui:content id="hrStaffContentTrial" title="${ lfn:message('hr-staff:hr.staff.nav.trial.expiration')}">
				 	 <ui:iframe id="hrStaffTrial" src="${LUI_ContextPath}/hr/staff/hr_staff_person_info/index.jsp?type=warningTrial"></ui:iframe>
				  </ui:content>
				   <ui:content id="hrStaffContentTrial" title="${ lfn:message('hr-staff:hr.staff.nav.trial.expiration')}">
				 	 <ui:iframe id="hrStaffTrial" src="${LUI_ContextPath}/hr/staff/hr_staff_person_info/index.jsp?type=warningTrial"></ui:iframe>
				  </ui:content>
				   <ui:content id="hrStaffContentTrial" title="${ lfn:message('hr-staff:hr.staff.nav.trial.expiration')}">
				 	 <ui:iframe id="hrStaffTrial" src="${LUI_ContextPath}/hr/staff/hr_staff_person_info/index.jsp?type=warningTrial"></ui:iframe>
				  </ui:content>
				   <ui:content id="hrStaffContentTrial" title="${ lfn:message('hr-staff:hr.staff.nav.trial.expiration')}">
				 	 <ui:iframe id="hrStaffTrial" src="${LUI_ContextPath}/hr/staff/hr_staff_person_info/index.jsp?type=warningTrial"></ui:iframe>
				  </ui:content>
				   <ui:content id="hrStaffContentTrial" title="${ lfn:message('hr-staff:hr.staff.nav.trial.expiration')}">
				 	 <ui:iframe id="hrStaffTrial" src="${LUI_ContextPath}/hr/staff/hr_staff_person_info/index.jsp?type=warningTrial"></ui:iframe>
				  </ui:content>
				  
				   <ui:content id="hrEntryMonthReportContent" title="${ lfn:message('hr-staff:hrStaffPersonReport.type.hrEntryMonthReport')}">
				 	 <ui:iframe id="hrEntryMonthReport" src="${LUI_ContextPath}/hr/staff/report/hrEntryMonthReport.jsp"></ui:iframe>
				  </ui:content>
				  
				   <ui:content id="hrLeaveLevelMonthReportContent" title="${ lfn:message('hr-staff:hrStaffPersonReport.type.hrLeaveLevelMonthReport')}">
				 	 <ui:iframe id="hrLeaveLevelMonthReport" src="${LUI_ContextPath}/hr/staff/report/hrLeaveLevelMonthReport.jsp"></ui:iframe>
				  </ui:content>
				  
				   <ui:content id="hrLeaveMonthReportContent" title="${ lfn:message('hr-staff:hrStaffPersonReport.type.hrLeaveMonthReport')}">
				 	 <ui:iframe id="hrLeaveMonthReport" src="${LUI_ContextPath}/hr/staff/report/hrLeaveMonthReport.jsp"></ui:iframe>
				  </ui:content>
				  
				   <ui:content id="hrPersonalStructureMonthReportContent" title="${ lfn:message('hr-staff:hrStaffPersonReport.type.hrPersonalStructureMonthReport')}">
				 	 <ui:iframe id="hrPersonalStructureMonthReport" src="${LUI_ContextPath}/hr/staff/report/hrPersonalStructureMonthReport.jsp"></ui:iframe>
				  </ui:content>
				  
				   <ui:content id="hrPersonalMonthReportContent" title="${ lfn:message('hr-staff:hrStaffPersonReport.type.hrPersonalMonthReport')}">
				 	 <ui:iframe id="hrPersonalMonthReport" src="${LUI_ContextPath}/hr/staff/report/hrPersonalMonthReport.jsp"></ui:iframe>
				  </ui:content>
				   <ui:content id="hrRankMonthReportContent" title="${ lfn:message('hr-staff:hrStaffPersonReport.type.hrRankMonthReport')}">
				 	 <ui:iframe id="hrRankMonthReport" src="${LUI_ContextPath}/hr/staff/report/hrRankMonthReport.jsp"></ui:iframe>
				  </ui:content>
				   <ui:content id="hrPersonalIncomeTaxReportContent" title="${ lfn:message('hr-staff:hrStaffPersonReport.type.hrPersonalIncomeTaxReport')}">
				 	 <ui:iframe id="hrPersonalIncomeTaxReport" src="${LUI_ContextPath}/hr/staff/report/hrPersonalIncomeTaxReport.jsp"></ui:iframe>
				  </ui:content>
				  <ui:content id="hrPersonalIncomeTaxReportContentCaiwu" title="${ lfn:message('hr-staff:hrStaffPersonReport.type.hrPersonalIncomeTaxReportCaiwu')}">
				 	 <ui:iframe id="hrPersonalIncomeTaxReportCaiwu" src="${LUI_ContextPath}/hr/staff/report/hrPersonalIncomeTaxReport.jsp"></ui:iframe>
				  </ui:content>

				  <ui:content id="hrStaffMoveAllReportContent" title="${ lfn:message('hr-staff:hrStaffPersonReport.type.hrStaffMoveAllReport')}" >
					  <ui:iframe id="hrStaffMoveAllReport" src="${LUI_ContextPath}/hr/staff/hr_staff_move_record/report/all/all_search.jsp"></ui:iframe>
				  </ui:content>
				  <ui:content id="hrStaffMoveMonthReportContent" title="${ lfn:message('hr-staff:hrStaffPersonReport.type.hrStaffMoveMonthReport')}" >
					  <ui:iframe id="hrStaffMoveMonthReport" src="${LUI_ContextPath}/hr/staff/report/hrStaffMoveMonthReport.jsp"></ui:iframe>
				  </ui:content>
				  <ui:content id="hrStaffRecruitReportContent" title="${ lfn:message('hr-staff:hrStaffPersonReport.type.hrStaffRecruitReport')}" >
					  <ui:iframe id="hrStaffRecruitReport" src="${LUI_ContextPath}/hr/staff/hr_staff_move_record/report/recruit/all_search.jsp"></ui:iframe>
				  </ui:content>
				  <%--流程流转--%>
				  <ui:content id="hrStaffApprovalReportContent" title="${ lfn:message('hr-staff:hrStaffPersonReport.type.hrStaffApprovalReport')}" >
					  <ui:iframe id="hrStaffApprovalReport" src="${LUI_ContextPath}/hr/staff/hr_staff_move_record/report/approval/all_search.jsp"></ui:iframe>
				  </ui:content>
				  <%--异常考勤预警名单--%>
				  <ui:content id="hrStaffAttendReportContent" title="${ lfn:message('hr-staff:hrStaffPersonReport.type.hrStaffApprovalReport')}" >
					  <ui:iframe id="hrStaffAttendReport" src="${LUI_ContextPath}/hr/staff/hr_staff_move_record/report/attend/all_search.jsp"></ui:iframe>
				  </ui:content>
		</ui:tabpanel>
	</template:replace>
	   	<template:replace name="script">
   		<%-- JSP中建议只出现·安装模块·的JS代码，其余JS代码采用引入方式 --%>
      	<script type="text/javascript">
      		seajs.use(['lui/framework/module'],function(Module){
      			Module.install('hrStaff',{
					//模块变量
					$var : {
						isAdmin : ''
					},
 					//模块多语言
 					$lang : {
 						hrStaffOverview : '${lfn:message("hr-staff:hr.staff.nav.overview") }',
 						pageNoSelect : '${lfn:message("page.noSelect")}',
 						confirmFiled : '${lfn:message("sys-archives:confirm.filed")}',
 						optSuccess : '${lfn:message("return.optSuccess")}',
 						optFailure : '${lfn:message("return.optFailure")}',
 						buttonDelete : '${lfn:message("button.delete")}',
 						buttonFiled : '${lfn:message("sys-archives:button.filed")}',
 						changeRightBatch : '${lfn:message("sys-right:right.button.changeRightBatch")}'
 					},
 					//搜索标识符
 					$search : ''
  				});
      			var flag = 0;
      			console.log("clickItem:"+$("#parent1")[0]);
          		$("#parent1").click(function(e){
          			console.log(this);
          			flag++;
          			console.log($(this)==$("#parent1"));
          			var children = $(this).find(".lui_dataview_cate_all_box").find('ul')[0];
          			var clickItem = $("#parent1").find(".lui_dataview_cate_item")[0];
          			console.log(children);
          			if(flag==1){
          			$(clickItem).click(function(e){
          				var children = $("#parent1").find(".lui_dataview_cate_all_box").find('ul')[0];
          				console.log(children);
              			$(children).toggle();
          				 e.stopPropagation() ;
          			});
          			$(children).toggle();
          			}
          		});
      			 }); 
      	</script>
      	<script type="text/javascript" src="${LUI_ContextPath}/hr/staff/resource/js/index.js?s_cache=${LUI_Cache}"></script>
	</template:replace>
</template:include>
