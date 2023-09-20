<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page import="com.landray.kmss.hr.staff.util.HrStaffPersonUtil"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ page import="com.landray.kmss.hr.staff.model.HrStaffAlertIdcardSetting" %>
<%  HrStaffAlertIdcardSetting hrStaffAlertIdcardSetting = new HrStaffAlertIdcardSetting();
	String isIdcardValidate = hrStaffAlertIdcardSetting.getisIdcardValidate();
	request.setAttribute("isIdcardValidate", isIdcardValidate);
%>
<template:include ref="default.edit" sidebar="auto" showQrcode="false">
	<template:replace name="title">
		<c:choose>
			<c:when test="${ hrStaffPersonInfoForm.method_GET == 'addPerson' }">
				<c:out value="${ lfn:message('hr-staff:hrStaffPersonInfo.create.title') } - ${ lfn:message('hr-staff:module.hr.staff') }"></c:out>	
			</c:when>
			<c:otherwise>
				${ hrStaffPersonInfoForm.fdName } - ${ lfn:message('hr-staff:module.hr.staff') }
			</c:otherwise>
		</c:choose>
	</template:replace>
	<template:replace name="head">
		<style>
			.hr_select{
				width: 50%;
				max-width: 80%;
			}
		</style>
	</template:replace>
	<template:replace name="toolbar">
		<ui:toolbar id="toolbar" layout="sys.ui.toolbar.float" count="3">
			<c:if test="${ hrStaffPersonInfoForm.method_GET == 'addPerson' }">
				<ui:button text="${lfn:message('hr-staff:hrStaffPerson.batch.add.btn')}" onclick="_modify();" order="1"></ui:button>
				<ui:button text="${lfn:message('button.submit')}" 
					onclick="Com_Submit(document.hrStaffPersonInfoForm, 'save');" order="2">
				</ui:button>
			</c:if>
			<c:if test="${ hrStaffPersonInfoForm.method_GET == 'edit' }">
				<ui:button text="${lfn:message('button.save')}" 
					onclick="Com_Submit(document.hrStaffPersonInfoForm, 'update');" order="3">
				</ui:button>
			</c:if>
			<ui:button text="${lfn:message('button.close')}" order="5" onclick="Com_CloseWindow();">
			</ui:button>
		</ui:toolbar>
	</template:replace>
	<template:replace name="path">
		<ui:menu layout="sys.ui.menu.nav"  id="categoryId"> 
			<ui:menu-item text="${ lfn:message('home.home') }" icon="lui_icon_s_home" href="/" target="_self"></ui:menu-item>
			<ui:menu-item text="${ lfn:message('hr-staff:module.hr.staff') }" href="/hr/staff/" target="_self"></ui:menu-item>
			<ui:menu-item text="${ lfn:message('hr-staff:table.HrStaffPersonInfo') }" href="/hr/staff/hr_staff_person_info/" target="_self"></ui:menu-item>
		</ui:menu>
	</template:replace>	
	<template:replace name="content">
		<html:form action="/hr/staff/hr_staff_person_info/hrStaffPersonInfo.do" >
			<html:hidden property="fdId" value="${hrStaffPersonInfoForm.fdId}"/>
			<div class='lui_form_title_frame' align="center">
				<div class='lui_form_subject'>
					<c:choose>
						<c:when test="${ hrStaffPersonInfoForm.method_GET == 'addPerson' }">
							<c:out value="${ lfn:message('hr-staff:hrStaffPersonInfo.create.title') }"></c:out>	
						</c:when>
						<c:otherwise>
							${ hrStaffPersonInfoForm.fdName }
						</c:otherwise>
					</c:choose>
				</div>
			</div>
			<ui:tabpage expand="false">
				<%-- 个人信息 --%>
				<ui:content expand="true" title="${ lfn:message('hr-staff:table.HrStaffPersonInfo') }">
					<table class="tb_normal" style="margin: 20px 0" width=98%>
						<tr>
							<!-- 部门或机构 -->
							<td width="15%" class="td_normal_title">
								<bean:message bundle="hr-staff" key="hrStaffPersonInfo.fdOrgParent" />
							</td>
							<td width="35%">
								<c:choose>
									<c:when test="${ hrStaffPersonInfoForm.fdOrgPersonId != null }">
										${ hrStaffPersonInfoForm.fdOrgParentsName }
										<html:hidden property="fdOrgParentId" />
									</c:when>
									<c:otherwise>
										<xform:address propertyId="fdOrgParentId" propertyName="fdOrgParentName" orgType="ORG_TYPE_ORG|ORG_TYPE_DEPT" required="true" style="width:85%">
										</xform:address>
									</c:otherwise>
								</c:choose>
							</td>
							<!-- 状态 -->
							<td width="15%" class="td_normal_title">
								<bean:message bundle="hr-staff" key="hrStaffPersonInfo.fdStatus" />
							</td>
							<td width="35%">
								<sunbor:enums elementClass="hr_select" property="fdStatus" enumsType="hrStaffPersonInfo_fdStatus" elementType="select" />
							</td>
						</tr>
						<tr>
							<!-- 岗位 -->
							<td width="15%" class="td_normal_title">
								<bean:message bundle="hr-staff" key="hrStaffPersonInfo.fdOrgPosts" />
							</td>
							<td width="35%">
								<c:choose>
									<c:when test="${ hrStaffPersonInfoForm.fdOrgPersonId != null }">
										${ hrStaffPersonInfoForm.fdOrgPostNames }
										<html:hidden property="fdOrgPostIds" />
									</c:when>
									<c:otherwise>
										<xform:address propertyId="fdOrgPostIds" propertyName="fdOrgPostNames" mulSelect="true" showStatus="edit" orgType="ORG_TYPE_POST" style="width:85%">
										</xform:address>
									</c:otherwise>
								</c:choose>
							</td>
							<!-- 职务 -->
							<td width="15%" class="td_normal_title">
								<bean:message bundle="hr-staff" key="hrStaffPersonInfo.fdStaffingLevel" />
							</td>
							<td width="35%">
								<c:choose>
									<c:when test="${ hrStaffPersonInfoForm.fdOrgPersonId != null }">
										${ hrStaffPersonInfoForm.fdStaffingLevelName }
										<html:hidden property="fdStaffingLevelId" />
									</c:when>
									<c:otherwise>
										<xform:staffingLevel propertyName="fdStaffingLevelName" propertyId="fdStaffingLevelId" showStatus="edit"></xform:staffingLevel>
									</c:otherwise>
								</c:choose>
							</td>
						</tr>
						<tr>
							<!-- 姓名 -->
							<td width="15%" class="td_normal_title">
								<bean:message bundle="hr-staff" key="hrStaffPersonInfo.fdName" />
							</td>
							<td width="35%">
								<c:choose>
									<c:when test="${ hrStaffPersonInfoForm.fdOrgPersonId != null }">
										${ hrStaffPersonInfoForm.fdName }
										<html:hidden property="fdName" />
									</c:when>
									<c:otherwise>
										<xform:text property="fdName" style="width:95%;" className="inputsgl" showStatus="edit"/>
									</c:otherwise>
								</c:choose>
							</td>
							<!-- 性别 -->
							<td width="15%" class="td_normal_title">
								<bean:message bundle="hr-staff" key="hrStaffPersonInfo.fdSex" />
							</td>
							<td width="35%">
								<c:choose>
									<c:when test="${ hrStaffPersonInfoForm.fdOrgPersonId != null }">
										<sunbor:enumsShow value="${ hrStaffPersonInfoForm.fdSex }" enumsType="sys_org_person_sex" />
										<html:hidden property="fdSex" />
									</c:when>
									<c:otherwise>
										<sunbor:enums property="fdSex" enumsType="sys_org_person_sex" elementType="radio" />
									</c:otherwise>
								</c:choose>
							</td>
						</tr>
						<tr>
							<!-- 工号 -->
							<td width="15%" class="td_normal_title">
								<bean:message bundle="hr-staff" key="hrStaffPersonInfo.fdStaffNo" />
							</td>
							<td width="35%">
								<xform:text property="fdStaffNo" style="width:95%;" required="true" validators="uniqueStaffNo" className="inputsgl" showStatus="edit" />
							</td>
							<!-- 出生日期 -->
							<td width="15%" class="td_normal_title">
								<bean:message bundle="hr-staff" key="hrStaffPersonInfo.fdDateOfBirth" />
							</td>
							<td width="35%">
								<xform:datetime property="fdDateOfBirth" dateTimeType="date" showStatus="edit"></xform:datetime>
							</td>
						</tr>
						<tr>
							<!-- 身份证号码 -->
							<td width="15%" class="td_normal_title">
								<bean:message bundle="hr-staff" key="hrStaffPersonInfo.fdIdCard" />
							</td>
							<td>
								<c:choose>
									<c:when test="${isIdcardValidate==true}">
										<xform:text property="fdIdCard" style="width:95%;" className="inputsgl" showStatus="edit" validators="idCard" />
									</c:when>
									<c:otherwise>
										<xform:text property="fdIdCard" style="width:95%;" className="inputsgl" showStatus="edit" />
									</c:otherwise>
								</c:choose>

							</td>
							<!-- 参加工作时间 -->
							<td width="15%" class="td_normal_title">
								<bean:message bundle="hr-staff" key="hrStaffPersonInfo.fdWorkTime" />
							</td>
							<td width="35%">
								<xform:datetime property="fdWorkTime" dateTimeType="date" validators="workTime" showStatus="edit"></xform:datetime>
							</td>
						</tr>
						<tr>
							<!-- 到本单位时间 -->
							<td width="15%" class="td_normal_title">
								<bean:message bundle="hr-staff" key="hrStaffPersonInfo.fdTimeOfEnterprise" />
							</td>
							<td width="35%">
								<xform:datetime property="fdTimeOfEnterprise" dateTimeType="date" validators="timeOfEnterprise" required="true" showStatus="edit"></xform:datetime>
							</td>
							<!-- 试用到期时间 -->
							<td width="15%" class="td_normal_title">
								<bean:message bundle="hr-staff" key="hrStaffPersonInfo.fdTrialExpirationTime" />
							</td>
							<td width="35%">
								<xform:datetime property="fdTrialExpirationTime" dateTimeType="date" validators="trialExpirationTime" showStatus="edit"></xform:datetime>
							</td>
						</tr>
						<tr>
							<!-- 用工期限（年） -->
							<td width="15%" class="td_normal_title">
								<bean:message bundle="hr-staff" key="hrStaffPersonInfo.fdEmploymentPeriod" />
							</td>
							<td width="35%">
								<xform:text property="fdEmploymentPeriod" style="width:95%;" validators="digits min(0)" className="inputsgl" showStatus="edit" />
							</td>
							<!-- 试用期限 -->
							<td width="15%" class="td_normal_title">
								<bean:message bundle="hr-staff" key="hrStaffPersonInfo.fdTrialOperationPeriod" />
							</td>
							<td width="35%">
								<xform:text property="fdTrialOperationPeriod" style="width:95%;" validators="digits min(0)" className="inputsgl" showStatus="edit" />
							</td>
						</tr>
						<tr>
							<!-- 入职日期-->
							<td width="15%" class="td_normal_title">
								<bean:message bundle="hr-staff" key="hrStaffPersonInfo.fdEntryTime" />
							</td>
							<td width="35%">
								<xform:datetime property="fdEntryTime" dateTimeType="date" showStatus="edit"></xform:datetime>
							</td>
							<!-- 转正日期 -->
							<td width="15%" class="td_normal_title">
								<bean:message bundle="hr-staff" key="hrStaffPersonInfo.fdPositiveTime" />
							</td>
							<td width="35%">
								<xform:datetime property="fdPositiveTime" dateTimeType="date" showStatus="edit"></xform:datetime>
							</td>
						</tr>
						<tr>
							<!-- 离职日期 -->
							<td width="15%" class="td_normal_title">
								<bean:message bundle="hr-staff" key="hrStaffPersonInfo.fdLeaveTime" />
							</td>
							<td colspan="3">
								<xform:datetime property="fdLeaveTime" dateTimeType="date" showStatus="edit"></xform:datetime>
							</td>
						</tr>
						<tr>
							<!-- 人员类别 -->
							<td width="15%" class="td_normal_title">
								<bean:message bundle="hr-staff" key="hrStaffPersonInfo.fdStaffType" />
							</td>
							<td width="35%">
								<%=HrStaffPersonUtil.buildPersonInfoSettingHtml("fdStaffType", request)%>
							</td>
							<!-- 曾用名 -->
							<td width="15%" class="td_normal_title">
								<bean:message bundle="hr-staff" key="hrStaffPersonInfo.fdNameUsedBefore" />
							</td>
							<td width="35%">
								<xform:text property="fdNameUsedBefore" style="width:98%;" className="inputsgl" showStatus="edit" />
							</td>
						</tr>
						<tr>
							<!-- 民族 -->
							<td width="15%" class="td_normal_title">
								<bean:message bundle="hr-staff" key="hrStaffPersonInfo.fdNation" />
							</td>
							<td width="35%">
								<%=HrStaffPersonUtil.buildPersonInfoSettingHtml("fdNation", request)%>
							</td>
							<!-- 政治面貌 -->
							<td width="15%" class="td_normal_title">
								<bean:message bundle="hr-staff" key="hrStaffPersonInfo.fdPoliticalLandscape" />
							</td>
							<td width="35%">
								<%=HrStaffPersonUtil.buildPersonInfoSettingHtml("fdPoliticalLandscape", request)%>
							</td>
						</tr>
						<tr>
							<!-- 入团日期 -->
							<td width="15%" class="td_normal_title">
								<bean:message bundle="hr-staff" key="hrStaffPersonInfo.fdDateOfGroup" />
							</td>
							<td width="35%">
								<xform:datetime property="fdDateOfGroup" dateTimeType="date" showStatus="edit"></xform:datetime>
							</td>
							<!-- 入党日期 -->
							<td width="15%" class="td_normal_title">
								<bean:message bundle="hr-staff" key="hrStaffPersonInfo.fdDateOfParty" />
							</td>
							<td width="35%">
								<xform:datetime property="fdDateOfParty" dateTimeType="date" showStatus="edit"></xform:datetime>
							</td>
						</tr>
						<tr>
							<!-- 最高学历 -->
							<td width="15%" class="td_normal_title">
								<bean:message bundle="hr-staff" key="hrStaffPersonInfo.fdHighestEducation" />
							</td>
							<td width="35%">
								<%=HrStaffPersonUtil.buildPersonInfoSettingHtml("fdHighestEducation", request)%>
							</td>
							<!-- 最高学位 -->
							<td width="15%" class="td_normal_title">
								<bean:message bundle="hr-staff" key="hrStaffPersonInfo.fdHighestDegree" />
							</td>
							<td width="35%">
								<%=HrStaffPersonUtil.buildPersonInfoSettingHtml("fdHighestDegree", request)%>
							</td>
						</tr>
						<tr>
							<!-- 婚姻情况 -->
							<td width="15%" class="td_normal_title">
								<bean:message bundle="hr-staff" key="hrStaffPersonInfo.fdMaritalStatus" />
							</td>
							<td width="35%">
								<%=HrStaffPersonUtil.buildPersonInfoSettingHtml("fdMaritalStatus", request)%>
							</td>
							<!-- 健康情况 -->
							<td width="15%" class="td_normal_title">
								<bean:message bundle="hr-staff" key="hrStaffPersonInfo.fdHealth" />
							</td>
							<td width="35%">
								<%=HrStaffPersonUtil.buildPersonInfoSettingHtml("fdHealth", request)%>
							</td>
						</tr>
						<tr>
							<!-- 身高（厘米） -->
							<td width="15%" class="td_normal_title">
								<bean:message bundle="hr-staff" key="hrStaffPersonInfo.fdStature" />
							</td>
							<td width="35%">
								<xform:text property="fdStature" style="width:98%;" validators="digits min(1)" className="inputsgl" showStatus="edit" />
							</td>
							<!-- 体重（千克） -->
							<td width="15%" class="td_normal_title">
								<bean:message bundle="hr-staff" key="hrStaffPersonInfo.fdWeight" />
							</td>
							<td width="35%">
								<xform:text property="fdWeight" style="width:98%;" validators="digits min(1)" className="inputsgl" showStatus="edit" />
							</td>
						</tr>
						<tr>
							<!-- 现居地 -->
							<td width="15%" class="td_normal_title">
								<bean:message bundle="hr-staff" key="hrStaffPersonInfo.fdLivingPlace" />
							</td>
							<td width="35%">
								<xform:text property="fdLivingPlace" style="width:98%;" className="inputsgl" showStatus="edit" />
							</td>
							<!-- 籍贯 -->
							<td width="15%" class="td_normal_title">
								<bean:message bundle="hr-staff" key="hrStaffPersonInfo.fdNativePlace" />
							</td>
							<td width="35%">
								<xform:text property="fdNativePlace" style="width:98%;" className="inputsgl" showStatus="edit" />
							</td>
						</tr>
						<tr>
							<!-- 出生地 -->
							<td width="15%" class="td_normal_title">
								<bean:message bundle="hr-staff" key="hrStaffPersonInfo.fdHomeplace" />
							</td>
							<td width="35%">
								<xform:text property="fdHomeplace" style="width:98%;" className="inputsgl" showStatus="edit" />
							</td>
							<!-- 户口性质 -->
							<td width="15%" class="td_normal_title">
								<bean:message bundle="hr-staff" key="hrStaffPersonInfo.fdAccountProperties" />
							</td>
							<td width="35%">
								<xform:text property="fdAccountProperties" style="width:98%;" className="inputsgl" showStatus="edit" />
							</td>
						</tr>
						<tr>
							<!-- 户口所在地 -->
							<td width="15%" class="td_normal_title">
								<bean:message bundle="hr-staff" key="hrStaffPersonInfo.fdRegisteredResidence" />
							</td>
							<td width="35%">
								<xform:text property="fdRegisteredResidence" style="width:98%;" className="inputsgl" showStatus="edit" />
							</td>
							<!-- 户口所在派出所 -->
							<td width="15%" class="td_normal_title">
								<bean:message bundle="hr-staff" key="hrStaffPersonInfo.fdResidencePoliceStation" />
							</td>
							<td width="35%">
								<xform:text property="fdResidencePoliceStation" style="width:98%;" className="inputsgl" showStatus="edit" />
							</td>
						</tr>
						<tr>
							<!-- 是否返聘 -->
							<td width="15%" class="td_normal_title">
								<bean:message bundle="hr-staff" key="hrStaffPersonInfo.fdIsRehire" />
							</td>
							<td colspan="3">
								<xform:radio property="fdIsRehire" onValueChange="enableRehire(this);">
									<xform:enumsDataSource enumsType="common_yesno"></xform:enumsDataSource>
								</xform:radio>
								<!-- 返聘日期 -->
								<span id="rehireTime" style="display:none;">
									<bean:message bundle="hr-staff" key="hrStaffPersonInfo.fdRehireTime" />&nbsp;&nbsp;
									<xform:datetime property="fdRehireTime" dateTimeType="date" showStatus="edit"></xform:datetime>
								</span>
							</td>
						</tr>
						<%-- 引入动态属性 --%>
						<c:import url="/sys/property/custom_field/custom_fieldEdit.jsp" charEncoding="UTF-8" />
						<tr>
							<td class="td_normal_title">
								<bean:message bundle="hr-staff" key="hrStaffPersonInfo.att" />
							</td>
						<!--附件  -->
							<td width="85%" colspan="3">
								<c:import url="/sys/attachment/sys_att_main/sysAttMain_edit.jsp" charEncoding="UTF-8">
									<c:param name="fdKey" value="hrStaffPerson" />
								</c:import>
							</td>
						</tr>
					</table>
				</ui:content>
				<%--联系信息--%>
				<ui:content expand="true" title="${ lfn:message('hr-staff:hrStaffPersonInfo.contactInfo') }">
					<table class="tb_normal" width=100%>
						<tr>
							<!-- 手机 -->
							<td width="15%" class="td_normal_title">
								${ lfn:message('hr-staff:hrStaffPersonInfo.fdMobileNo') }
							</td>
							<td width="35%">
								<c:choose>
									<c:when test="${ hrStaffPersonInfoForm.fdOrgPersonId != null }">
										${ hrStaffPersonInfoForm.fdMobileNo }
										<html:hidden property="fdMobileNo" />
									</c:when>
									<c:otherwise>
										<xform:text property="fdMobileNo" validators="phone uniqueMobileNo" style="width:98%;" className="inputsgl" 
										htmlElementProperties="placeholder='${ lfn:message('hr-staff:hrStaffPersonInfo.moblieNo.tips') }'"/>
									</c:otherwise>
								</c:choose>
							</td>
							<!-- 邮箱 -->
							<td width="15%" class="td_normal_title">
								${ lfn:message('hr-staff:hrStaffPersonInfo.fdEmail') }
							</td>
							<td width="35%">
								<c:choose>
									<c:when test="${ hrStaffPersonInfoForm.fdOrgPersonId != null }">
										${ hrStaffPersonInfoForm.fdEmail }
										<html:hidden property="fdEmail" />
									</c:when>
									<c:otherwise>
										<xform:text property="fdEmail" validators="email" style="width:98%;" className="inputsgl" />
									</c:otherwise>
								</c:choose>
							</td>
						</tr>
						<tr>
							<!-- 办公地点 -->
							<td width="15%" class="td_normal_title">
								${ lfn:message('hr-staff:hrStaffPersonInfo.fdOfficeLocation') }
							</td>
							<td width="35%">
								<xform:text property="fdOfficeLocation" style="width:98%;" className="inputsgl" />
							</td>
							<!-- 办公电话 -->
							<td width="15%" class="td_normal_title">
								${ lfn:message('hr-staff:hrStaffPersonInfo.fdWorkPhone') }
							</td>
							<td width="35%">
								<c:choose>
									<c:when test="${ hrStaffPersonInfoForm.fdOrgPersonId != null }">
										${ hrStaffPersonInfoForm.fdWorkPhone }
										<html:hidden property="fdWorkPhone" />
									</c:when>
									<c:otherwise>
										<xform:text property="fdWorkPhone" validators="workPhone" style="width:98%;" className="inputsgl" 
										htmlElementProperties="placeholder='${ lfn:message('hr-staff:hrStaffPersonInfo.workPhoneNo.tips') }'"/>
									</c:otherwise>
								</c:choose>
							</td>
						</tr>
						<tr>
							<!-- 紧急联系人 -->
							<td width="15%" class="td_normal_title">
								${ lfn:message('hr-staff:hrStaffPersonInfo.fdEmergencyContact') }
							</td>
							<td width="35%">
								<xform:text property="fdEmergencyContact" style="width:98%;" className="inputsgl" />
							</td>
							<!-- 紧急联系人电话 -->
							<td width="15%" class="td_normal_title">
								${ lfn:message('hr-staff:hrStaffPersonInfo.fdEmergencyContactPhone') }
							</td>
							<td width="35%">
								<xform:text property="fdEmergencyContactPhone" style="width:98%;" className="inputsgl" />
							</td>
						</tr>
						<tr>
							<!-- 其他联系方式 -->
							<td width="15%" class="td_normal_title">
								${ lfn:message('hr-staff:hrStaffPersonInfo.fdOtherContact') }
							</td>
							<td width="85%" colspan="3">
								<xform:text property="fdOtherContact" style="width:98%;" className="inputsgl" />
							</td>
						</tr>
					</table>
				</ui:content>
			</ui:tabpage>
		</html:form>
		
		<%@ include file="/hr/staff/hr_staff_person_info/hrStaffPersonInfo_edit_script.jsp"%>
	</template:replace>
</template:include>