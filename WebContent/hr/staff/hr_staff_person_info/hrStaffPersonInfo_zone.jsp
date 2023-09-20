<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@page import="com.landray.kmss.hr.staff.util.HrStaffPrivateUtil"%>
<c:set var="userId" value="${(empty param.userId) ? KMSS_Parameter_CurrentUserId : (param.userId)}" scope="request"/>
<%
	String fdPersonId = (String)request.getAttribute("userId");
	 if(HrStaffPrivateUtil.isExist(fdPersonId)){ 
%>
<template:include ref="zone.navlink" sidebar="no">
	<template:replace name="content">
		<link rel="stylesheet" href="${LUI_ContextPath}/hr/staff/resource/css/hr_staff.css?s_cache=${LUI_Cache}" />
		<c:if test="${not empty param['j_iframe'] && param['j_iframe'] eq 'true' }">
			<c:set var="jIframe" value="true"></c:set>
			<c:set var="jIframeClass" value="lui_jIframe"></c:set>
			<c:set var="frameWidth" scope="page" value="${(empty param.pagewidth) ? '90%' : fn:escapeXml(param.pagewidth)}"/>  
			<c:set var="settingStyle" value="width:${frameWidth}; min-width:980px;max-width:${fdPageMaxWidth}; margin:0px auto;"></c:set>
		</c:if>
		<div class="lui_hr_staff_page_iframe ${jIframeClass }" style="${settingStyle}" >
			<!-- 员工黄页 主体内容 Starts -->
			<div class="lui_hr_staff_page_mbody">
				<table class="hr_staff_page_mtable">
					<tr>
						<td class="hr_staff_page_mtable_left">
							<!-- 员工黄页 选项卡 Starts -->
							<c:if test="${ jIframe !='true'}">
								<div class="hr_staff_page_tablist">
									<ul>
										<c:import url="/hr/staff/hr_staff_person_info/hrStaffPersonInfo_view_docCount.jsp" charEncoding="UTF-8">
											<c:param name="personInfoId" value="${userId}" />
										</c:import>
									</ul>
								</div>
							</c:if>
							
							<!-- 员工黄页 简历 Starts -->
							<div class="hr_staff_page_resume">
								<h2 class="resume_title"><span><bean:message bundle="hr-staff" key="hrStaffPerson.resume" /></span><span class="resume_subhead"></span></h2>
								<!-- 自我简介 -->
								<%if(!HrStaffPrivateUtil.isBriefPrivate(fdPersonId)){%>
									<c:import url="/hr/staff/hr_staff_person_experience/import/brief_view4zone.jsp" charEncoding="UTF-8">
										<c:param name="personInfoId" value="${userId}" />
									</c:import>
								<%} %>
								<!-- 项目经历 -->
								<%if(!HrStaffPrivateUtil.isProjectPrivate(fdPersonId)){%>
									<c:import url="/hr/staff/hr_staff_person_experience/import/projectExperience_view4zone.jsp" charEncoding="UTF-8">
										<c:param name="personInfoId" value="${userId}" />
									</c:import>
								<%} %>
								<!-- 工作经历 -->
								<%if(!HrStaffPrivateUtil.isWorkPrivate(fdPersonId)){%>
									<c:import url="/hr/staff/hr_staff_person_experience/import/work_view4zone.jsp" charEncoding="UTF-8">
										<c:param name="personInfoId" value="${userId}" />
									</c:import>
								<%} %>
								<!-- 教育经历 -->
								<%if(!HrStaffPrivateUtil.isEducationPrivate(fdPersonId)){%>
									<c:import url="/hr/staff/hr_staff_person_experience/import/education_view4zone.jsp" charEncoding="UTF-8">
										<c:param name="personInfoId" value="${userId}" />
									</c:import>
								<%} %>
								<!-- 培训经历 -->
								<%if(!HrStaffPrivateUtil.isTrainingPrivate(fdPersonId)){%>
									<c:import url="/hr/staff/hr_staff_person_experience/import/training_view4zone.jsp" charEncoding="UTF-8">
										<c:param name="personInfoId" value="${userId}" />
									</c:import>
								<%} %>
								<!-- 员工黄页 简历 Ends-->
							</div>
						</td>
					</tr>
				</table>
			</div>
			<!-- 员工黄页 主体内容 Ends -->
		</div>
	<c:import url="/hr/staff/hr_staff_person_experience/import/experience_operate.jsp" charEncoding="UTF-8"></c:import>
	</template:replace>
</template:include>
<%} %>