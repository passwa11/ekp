<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ page import="com.landray.kmss.sys.authentication.user.KMSSUser"%>
<%@ page import="com.landray.kmss.util.UserUtil"%>
<template:include ref="default.view" sidebar="auto">
	<template:replace name="title">
		${ hrStaffEntryForm.fdName } - ${ lfn:message('hr-staff:module.hr.staff') }
	</template:replace>
	<template:replace name="head">
		<%
			KMSSUser kmssUser = UserUtil.getKMSSUser();
			String str = kmssUser.getLocale().getLanguage();
			request.setAttribute("language", str);
		%>
		<script type="text/javascript">
			top.window.language = "${language}";
		</script>
		<script type="text/javascript" src="${LUI_ContextPath}/resource/js/jquery.js?s_cache=${LUI_Cache}"></script>
		<link type="text/css" rel="stylesheet" href="${LUI_ContextPath}/hr/staff/resource/css/hr_staff.css?s_cache=${LUI_Cache}"/>
		
	</template:replace>
	<template:replace name="toolbar">
		<ui:toolbar id="toolbar" layout="sys.ui.toolbar.float" count="5">
			<kmss:authShow roles="ROLE_HRSTAFF_EDIT">
				<c:if test="${hrStaffEntryForm.fdStatus eq '1' }">
					<ui:button text="${lfn:message('button.edit')}" 
						onclick="Com_OpenWindow('${LUI_ContextPath}/hr/staff/hr_staff_entry/hrStaffEntry.do?method=edit&fdId=${param.fdId}','_self');" order="1">
					</ui:button>
				</c:if>
			</kmss:authShow>
			<ui:button text="${lfn:message('button.close')}" order="2" onclick="Com_CloseWindow();">
			</ui:button>
		</ui:toolbar>
	</template:replace>
	<template:replace name="path">
	  <c:if test="${hrStaffEntryForm.fdStatus eq '1' }">
		<ui:menu layout="sys.ui.menu.nav"  id="categoryId"> 
			<ui:menu-item text="${ lfn:message('home.home') }" icon="lui_icon_s_home" ></ui:menu-item>
			<ui:menu-item text="${ lfn:message('hr-staff:module.hr.staff') }"></ui:menu-item>
			<ui:menu-item text="${ lfn:message('hr-staff:table.hrStaffEntry') }"></ui:menu-item>
		</ui:menu>
		</c:if>
		  <c:if test="${hrStaffEntryForm.fdStatus eq '2' }">
		<ui:menu layout="sys.ui.menu.nav"  id="categoryId"> 
			<ui:menu-item text="${ lfn:message('home.home') }" icon="lui_icon_s_home" ></ui:menu-item>
			<ui:menu-item text="${ lfn:message('hr-staff:module.hr.staff') }"></ui:menu-item>
			<ui:menu-item text="${ lfn:message('hr-staff:table.hrStaffEntry.confined') }"></ui:menu-item>
		</ui:menu>
		</c:if>
		
		
		
	</template:replace>	
	<template:replace name="content"> 
	
		<!-- 简历 头部 Starts -->
		<%-- <div class="lui_hr_staff_resume_head">
			<div class="staff_info">
				<!-- 名称和性别 -->
				<h2>
					<span>${ hrStaffEntryForm.fdName }</span>
					<c:if test="${ 'M' eq hrStaffEntryForm.fdSex }">
					<span class="staff_sex sex_m"><i class="staff_sex_m lui_icon_s"></i></span>
					</c:if>
					<c:if test="${ 'F' eq hrStaffEntryForm.fdSex }">
					<span class="staff_sex sex_f"><i class="staff_sex_f lui_icon_s"></i></span>
					</c:if>
				</h2>
			</div>
		</div> --%>
		<!-- 简历 头部 Ends -->
	
		<ui:tabpage expand="false">
			<%--个人信息--%>
			<ui:content expand="true" title="${ lfn:message('hr-staff:table.HrStaffPersonInfo') }">
				<table class="staff_resume_simple_tb">
					<tr>
						<td width="15%" class="td_normal_title">
							${ lfn:message('hr-staff:hrStaffEntry.fdName') }
						</td>
						<td width="35%">
							<c:out value="${ hrStaffEntryForm.fdName }"></c:out>
						</td>
						<td width="15%" class="td_normal_title">
							${ lfn:message('hr-staff:hrStaffEntry.fdNameUsedBefore') }
						</td>
						<td width="35%">
							${ hrStaffEntryForm.fdNameUsedBefore }
						</td>
					</tr>
					<tr>
						<td width="15%" class="td_normal_title">
							${ lfn:message('hr-staff:hrStaffEntry.fdDateOfBirth') }
						</td>
						<td width="35%">
							${ hrStaffEntryForm.fdDateOfBirth }
						</td>
						<td width="15%" class="td_normal_title">
							${ lfn:message('hr-staff:hrStaffEntry.fdNativePlace') }
						</td>
						<td width="35%">
							${ hrStaffEntryForm.fdNativePlace }
						</td>
					</tr>
					<tr>
						<td width="15%" class="td_normal_title">
							${ lfn:message('hr-staff:hrStaffEntry.fdMaritalStatus') }
						</td>
						<td width="35%">
							${ hrStaffEntryForm.fdMaritalStatusName }
						</td>
						<td width="15%" class="td_normal_title">
							${ lfn:message('hr-staff:hrStaffEntry.fdNation') }
						</td>
						<td width="35%">
							${ hrStaffEntryForm.fdNationName }
						</td>
					</tr>
					<tr>
						<td width="15%" class="td_normal_title">
							${ lfn:message('hr-staff:hrStaffEntry.fdPoliticalLandscape') }
						</td>
						<td width="35%">
							${ hrStaffEntryForm.fdPoliticalLandscapeName }
						</td>
						<td width="15%" class="td_normal_title">
							${ lfn:message('hr-staff:hrStaffEntry.fdHealth') }
						</td>
						<td width="35%">
							${ hrStaffEntryForm.fdHealthName }
						</td>
					</tr>
					<tr>
						<td width="15%" class="td_normal_title">
							${ lfn:message('hr-staff:hrStaffEntry.fdLivingPlace') }
						</td>
						<td width="35%">
							${ hrStaffEntryForm.fdLivingPlace }
						</td>
						<td width="15%" class="td_normal_title">
							${ lfn:message('hr-staff:hrStaffEntry.fdIdCard') }
						</td>
						<td width="35%">
							${ hrStaffEntryForm.fdIdCard }
						</td>
					</tr>
					<tr>
						<td width="15%" class="td_normal_title">
							${lfn:message('hr-staff:hrStaffEntry.fdHighestDegree') }
						</td>
						<td width="35%">
							${hrStaffEntryForm.fdHighestDegreeName }
						</td>
						<td width="15%" class="td_normal_title">
							${lfn:message('hr-staff:hrStaffEntry.fdHighestEducation') }
						</td>
						<td width="35%">
							${hrStaffEntryForm.fdHighestEducationName}
						</td>
					</tr>
					<tr>
						<td width="15%" class="td_normal_title">
							${lfn:message('hr-staff:hrStaffEntry.fdProfession') }
						</td>
						<td width="35%">
							${hrStaffEntryForm.fdProfession}
						</td>
						<td width="15%" class="td_normal_title">
							${lfn:message('hr-staff:hrStaffEntry.fdWorkTime') }
						</td>
						<td width="35%">
							${hrStaffEntryForm.fdWorkTime}
						</td>
					</tr>
					<tr>
						<td width="15%" class="td_normal_title">
							${lfn:message('hr-staff:hrStaffEntry.fdDateOfGroup') }
						</td>
						<td width="35%">
							${hrStaffEntryForm.fdDateOfGroup}
						</td>
						<td width="15%" class="td_normal_title">
							${lfn:message('hr-staff:hrStaffEntry.fdDateOfParty') }
						</td>
						<td width="35%">
							${hrStaffEntryForm.fdDateOfParty}
						</td>
					</tr>
					<tr>
						<td width="15%" class="td_normal_title">
							${lfn:message('hr-staff:hrStaffEntry.fdStature') }
						</td>
						<td width="35%">
							${hrStaffEntryForm.fdStature}
						</td>
						<td width="15%" class="td_normal_title">
							${lfn:message('hr-staff:hrStaffEntry.fdWeight') }
						</td>
						<td width="35%">
							${hrStaffEntryForm.fdWeight}
						</td>
					</tr>
					<tr>
						<td width="15%" class="td_normal_title">
							${lfn:message('hr-staff:hrStaffEntry.fdHomeplace') }
						</td>
						<td width="35%">
							${hrStaffEntryForm.fdHomeplace}
						</td>
						<td width="15%" class="td_normal_title">
							${lfn:message('hr-staff:hrStaffEntry.fdAccountProperties') }
						</td>
						<td width="35%">
							${hrStaffEntryForm.fdAccountProperties}
						</td>
					</tr>
					<tr>
						<td width="15%" class="td_normal_title">
							${lfn:message('hr-staff:hrStaffEntry.fdRegisteredResidence') }
						</td>
						<td width="35%">
							${hrStaffEntryForm.fdRegisteredResidence}
						</td>
						<td width="15%" class="td_normal_title">
							${lfn:message('hr-staff:hrStaffEntry.fdResidencePoliceStation') }
						</td>
						<td width="35%">
							${hrStaffEntryForm.fdResidencePoliceStation}
						</td>
					</tr>
					<tr>
						<td width="15%" class="td_normal_title">
							${lfn:message('hr-staff:hrStaffEntry.fdPlanEntryTime') }
						</td>
						<td width="35%">
							${hrStaffEntryForm.fdPlanEntryTime}
						</td>
						<td width="15%" class="td_normal_title">
							${lfn:message('hr-staff:hrStaffEntry.fdPlanEntryDept') }
						</td>
						<td width="35%">
							${hrStaffEntryForm.fdPlanEntryDeptName}
						</td>
					</tr>
					<tr>
						<td width="15%" class="td_normal_title">
							${lfn:message('hr-staff:hrStaffEntry.fdStatus') }
						</td>
						<td width="35%">
							<sunbor:enumsShow enumsType="hrStaffEntry_fdStatus" value="${hrStaffEntryForm.fdStatus}"></sunbor:enumsShow>
						</td>
						<td width="15%" class="td_normal_title">
							${lfn:message('hr-staff:hrStaffEntry.fdAlteror') }
						</td>
						<td width="35%">
							${hrStaffEntryForm.fdAlterorName}
						</td>
					</tr>
					<tr>
						<td width="15%" class="td_normal_title">
							${lfn:message('hr-staff:hrStaffEntry.fdCheckDate') }
						</td>
						<td width="35%">
							${hrStaffEntryForm.fdCheckDate}
						</td>
						<td width="15%" class="td_normal_title">
							${lfn:message('hr-staff:hrStaffEntry.fdChecker') }
						</td>
						<td width="35%">
							${hrStaffEntryForm.fdCheckerName}
						</td>
					</tr>
				</table>
			</ui:content>
			<%--联系信息--%>
			<ui:content expand="true" title="${ lfn:message('hr-staff:hrStaffPersonInfo.contactInfo') }">
				<table class="staff_resume_simple_tb">
					<tr>
						<td width="15%" class="td_normal_title">
							${ lfn:message('hr-staff:hrStaffPersonInfo.fdMobileNo') }
						</td>
						<td width="35%">
							${ hrStaffEntryForm.fdMobileNo }
						</td>
						<td width="15%" class="td_normal_title">
							${ lfn:message('hr-staff:hrStaffPersonInfo.fdEmail') }
						</td>
						<td width="35%">
							${ hrStaffEntryForm.fdEmail }
						</td>
					</tr>
					<tr>
						<td width="15%" class="td_normal_title">
							${ lfn:message('hr-staff:hrStaffPersonInfo.fdEmergencyContact') }
						</td>
						<td width="35%">
							${ hrStaffEntryForm.fdEmergencyContact }
						</td>
						<td width="15%" class="td_normal_title">
							${ lfn:message('hr-staff:hrStaffPersonInfo.fdEmergencyContactPhone') }
						</td>
						<td width="35%">
							${ hrStaffEntryForm.fdEmergencyContactPhone }
						</td>
					</tr>
					<tr>
						<td width="15%" class="td_normal_title">
							${ lfn:message('hr-staff:hrStaffPersonInfo.fdOtherContact') }
						</td>
						<td width="85%" colspan="3">
							${ hrStaffEntryForm.fdOtherContact }
						</td>
					</tr>
				</table>
			</ui:content>
			<%-- 工作经历 --%>
			<ui:content title="${lfn:message('hr-staff:hrStaffEntry.fdHistory') }">
				<c:choose>
					<c:when test="${fn:length(hrStaffEntryForm.fdHistory_Form)>0 }">
						<table class="tb_normal" width="100%">
							<tr>
								<td>
									<bean:message key="page.serial"/>
								</td>
								<td>
									<bean:message bundle="hr-staff" key="hrStaffHistory.fdName"/>
								</td>
								<td>
									<bean:message bundle="hr-staff" key="hrStaffHistory.fdPost"/>
								</td>
								<td>
									<bean:message bundle="hr-staff" key="hrStaffHistory.fdStartDate"/>
								</td>
								<td>
									<bean:message bundle="hr-staff" key="hrStaffHistory.fdEndDate"/>
								</td>
								<td>
									<bean:message bundle="hr-staff" key="hrStaffHistory.fdDesc"/>
								</td>
								<td>
									<bean:message bundle="hr-staff" key="hrStaffHistory.fdLeaveReason"/>
								</td>
							</tr>
							<c:forEach items="${hrStaffEntryForm.fdHistory_Form }" var="history" varStatus="status">
								<tr>
									<td>
										${status.index+1 }
									</td>
									<td>
										${history.fdName }
									</td>
									<td>
										${history.fdPost }
									</td>
									<td>
										${history.fdStartDate }
									</td>
									<td>
										${history.fdEndDate }
									</td>
									<td>
										${history.fdDesc }
									</td>
									<td>
										${history.fdLeaveReason }
									</td>
								</tr>
							</c:forEach>
						</table>
					</c:when>
					<c:otherwise>
						<bean:message key="message.noRecord"/>
					</c:otherwise>
				</c:choose>
			</ui:content>
			<%-- 教育记录 --%>
			<ui:content title="${lfn:message('hr-staff:hrStaffEntry.fdEducations') }">
				<c:choose>
					<c:when test="${fn:length(hrStaffEntryForm.fdEducations_Form)>0 }">
						<table class="tb_normal" width="100%">
							<tr>
								<td>
									<bean:message key="page.serial"/>
								</td>
								<td>
									<bean:message bundle="hr-staff" key="hrStaffEduExp.fdName"/>
								</td>
								<td>
									<bean:message bundle="hr-staff" key="hrStaffEduExp.fdMajor"/>
								</td>
								<td>
									<bean:message bundle="hr-staff" key="hrStaffEduExp.fdAcademic"/>
								</td>
								<td>
									<bean:message bundle="hr-staff" key="hrStaffEduExp.fdEntranceDate"/>
								</td>
								<td>
									<bean:message bundle="hr-staff" key="hrStaffEduExp.fdGraduationDate"/>
								</td>
								<td>
									<bean:message bundle="hr-staff" key="hrStaffEduExp.fdRemark"/>
								</td>
							</tr>
							<c:forEach items="${hrStaffEntryForm.fdEducations_Form }" var="eduExp" varStatus="status">
								<tr>
									<td>
										${status.index+1 }
									</td>
									<td>
										${eduExp.fdName }
									</td>
									<td>
										${eduExp.fdMajor }
									</td>
									<td>
										${eduExp.fdAcadeRecordName }
									</td>
									<td>
										${eduExp.fdEntranceDate }
									</td>
									<td>
										${eduExp.fdGraduationDate }
									</td>
									<td>
										${eduExp.fdRemark }
									</td>
								</tr>
							</c:forEach>
						</table>
					</c:when>
					<c:otherwise>
						<bean:message key="message.noRecord"/>
					</c:otherwise>
				</c:choose>
			</ui:content>
			<%-- 资格证书 --%>
			<ui:content title="${lfn:message('hr-staff:hrStaffEntry.fdCertificate') }">
				<c:choose>
					<c:when test="${fn:length(hrStaffEntryForm.fdCertificate_Form)>0 }">
						<table class="tb_normal" width="100%">
							<tr>
								<td>
									<bean:message key="page.serial"/>
								</td>
								<td>
									<bean:message bundle="hr-staff" key="hrStaffCertifi.fdName"/>
								</td>
								<td>
									<bean:message bundle="hr-staff" key="hrStaffCertifi.fdIssuingUnit"/>
								</td>
								<td>
									<bean:message bundle="hr-staff" key="hrStaffCertifi.fdIssueDate"/>
								</td>
								<td>
									<bean:message bundle="hr-staff" key="hrStaffCertifi.fdInvalidDate"/>
								</td>
								<td>
									<bean:message bundle="hr-staff" key="hrStaffCertifi.fdRemark"/>
								</td>
							</tr>
							<c:forEach items="${hrStaffEntryForm.fdCertificate_Form }" var="cert" varStatus="status">
								<tr>
									<td>
										${status.index+1 }
									</td>
									<td>
										${cert.fdName }
									</td>
									<td>
										${cert.fdIssuingUnit }
									</td>
									<td>
										${cert.fdIssueDate }
									</td>
									<td>
										${cert.fdInvalidDate }
									</td>
									<td>
										${cert.fdRemark }
									</td>
								</tr>
							</c:forEach>
						</table>
					</c:when>
					<c:otherwise>
						<bean:message key="message.noRecord"/>
					</c:otherwise>
				</c:choose>
			</ui:content>
			<%-- 奖惩信息 --%>
			<ui:content title="${lfn:message('hr-staff:hrStaffEntry.fdRewardsPunishments') }">
				<c:choose>
					<c:when test="${fn:length(hrStaffEntryForm.fdRewardsPunishments_Form)>0 }">
						<table class="tb_normal" width="100%">
							<tr>
								<td>
									<bean:message key="page.serial"/>
								</td>
								<td>
									<bean:message bundle="hr-staff" key="hrStaffRewPuni.fdName"/>
								</td>
								<td>
									<bean:message bundle="hr-staff" key="hrStaffRewPuni.fdDate"/>
								</td>
								<td>
									<bean:message bundle="hr-staff" key="hrStaffRewPuni.fdRemark"/>
								</td>
							</tr>
							<c:forEach items="${hrStaffEntryForm.fdRewardsPunishments_Form }" var="rewPuni" varStatus="status">
								<tr>
									<td>
										${status.index+1 }
									</td>
									<td>
										${rewPuni.fdName }
									</td>
									<td>
										${rewPuni.fdDate }
									</td>
									<td>
										${rewPuni.fdRemark }
									</td>							
									</tr>
							</c:forEach>
						</table>
					</c:when>
					<c:otherwise>
						<bean:message key="message.noRecord"/>
					</c:otherwise>
				</c:choose>
			</ui:content>
			<%-- 家庭信息 --%>
			<ui:content title="${lfn:message('hr-staff:hrStaffPerson.family') }">
				<c:choose>
					<c:when test="${fn:length(hrStaffEntryForm.fdfamily_Form)>0 }">
						<table class="tb_normal" width="100%">
							<tr>
								<td>
									<bean:message key="page.serial"/>
								</td>
								<td>
									<bean:message bundle="hr-staff" key="hrStaffPerson.family.related"/>
								</td>
								<td>
									<bean:message bundle="hr-staff" key="hrStaffPerson.family.name"/>
								</td>
								<td>
									<bean:message bundle="hr-staff" key="hrStaffPerson.family.occupation"/>
								</td>
								<td>
									<bean:message bundle="hr-staff" key="hrStaffPerson.family.company"/>
								</td>
								<td>
									<bean:message bundle="hr-staff" key="hrStaffPerson.family.connect"/>
								</td>
								<td>
									<bean:message bundle="hr-staff" key="hrStaffRewPuni.fdRemark"/>
								</td>
								
							</tr>
							<c:forEach items="${hrStaffEntryForm.fdfamily_Form }" var="family" varStatus="status">
								<tr>
									<td>
										${status.index+1 }
									</td>
									<td>
										${family.fdRelated }
									</td>
									<td>
										${family.fdName }
									</td>
									<td>
										${family.fdOccupation }
									</td>
									<td>
										${family.fdCompany }
									</td>
									<td>
										${family.fdConnect }
									</td>
									<td>
										${family.fdMemo }
									</td>
							
								</tr>
							</c:forEach>
						</table>
					</c:when>
					<c:otherwise>
						<bean:message key="message.noRecord"/>
					</c:otherwise>
				</c:choose>
			</ui:content>
			<%--薪酬福利--%>
			 <ui:content expand="true" title="${ lfn:message('hr-staff:table.hrStaffEmolumentWelfare') }">
				<table class="staff_resume_simple_tb">
					                         <tr>
                             <!-- 工资账户名 -->
                             <td width="15%" class="td_normal_title">
                                 ${ lfn:message('hr-staff:hrStaffEmolumentWelfare.fdPayrollName') }
                             </td>
                             <td width="35%">
                                 <xform:text property="hrStaffEmolumentWelfareForm.fdPayrollName" style="width:95%;" className="inputsgl"/>
                             </td>
                             <!-- 工资银行 -->
                             <td width="15%" class="td_normal_title">
                                 ${ lfn:message('hr-staff:hrStaffEmolumentWelfare.fdPayrollBank') }
                             </td>
                             <td width="35%">
                                 <xform:text property="hrStaffEmolumentWelfareForm.fdPayrollBank"  style="width:98%;" className="inputsgl" />
                             </td>
                         </tr>
                         <tr>
                             <!-- 工资账号 -->
                             <td width="15%" class="td_normal_title">
                                 ${ lfn:message('hr-staff:hrStaffEmolumentWelfare.fdPayrollAccount') }
                             </td>
                             <td width="35%">
                                 <xform:text property="hrStaffEmolumentWelfareForm.fdPayrollAccount" style="width:98%;" className="inputsgl" />
                             </td>
                             <!-- 公积金账户-->
                             <td width="15%" class="td_normal_title">
                                 ${ lfn:message('hr-staff:hrStaffEmolumentWelfare.fdSurplusAccount') }
                             </td>
                             <td width="35%">
                                 <xform:text property="hrStaffEmolumentWelfareForm.fdSurplusAccount" style="width:98%;" className="inputsgl" />
                             </td>
                         </tr>
                         <tr>
                             <!-- 社保号码 -->
                             <td width="15%" class="td_normal_title">
                                 ${ lfn:message('hr-staff:hrStaffEmolumentWelfare.fdSocialSecurityNumber') }
                             </td>
                             <td width="85%" colspan="3">
                                 <xform:text property="hrStaffEmolumentWelfareForm.fdSocialSecurityNumber" style="width:98%;" className="inputsgl" />
                             </td>
                         </tr>
				</table>
			</ui:content>
		</ui:tabpage>
	</template:replace>
</template:include>