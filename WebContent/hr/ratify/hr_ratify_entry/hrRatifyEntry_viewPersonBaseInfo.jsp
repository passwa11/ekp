<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<table class="tb_normal" width=100%>
	<tr>
		<td class="td_normal_title" width="15%">
			<bean:message bundle="hr-ratify" key="hrRatifyEntry.fdNameUsedBefore"/>
		</td>
		<td width="35%">
			<xform:text property="fdNameUsedBefore" value="${hrRatifyEntryForm.fdNameUsedBefore }"></xform:text>
		</td>
		<td class="td_normal_title" width="15%">
			<bean:message bundle="hr-ratify" key="hrRatifyEntry.fdSex"/>
		</td>
		<td width="35%"> 
			<sunbor:enums property="fdSex" enumsType="sys_org_person_sex"  elementType="radio" htmlElementProperties="disabled"/>
		</td>
	</tr>
	<tr>
		<td class="td_normal_title" width="15%">
			<bean:message bundle="hr-ratify" key="hrRatifyEntry.fdDateOfBirth" />
		</td>
		<td width="35%">
			<xform:datetime property="fdDateOfBirth" dateTimeType="date"></xform:datetime>
		</td>
		<td class="td_normal_title" width="15%">
			<bean:message bundle="hr-ratify" key="hrRatifyEntry.fdNativePlace"/>
		</td>
		<td width="35%">
			<xform:text property="fdNativePlace"></xform:text>
		</td>
	</tr>
	<tr>
		<td class="td_normal_title" width="15%">
			<bean:message bundle="hr-ratify" key="hrRatifyEntry.fdMaritalStatus"/>
		</td>
		<td width="35%">
			<xform:select property="fdMaritalStatusId">
				<xform:beanDataSource serviceBean="hrStaffPersonInfoSetNewService" selectBlock="fdId,fdName" whereBlock="hrStaffPersonInfoSettingNew.fdType='fdMaritalStatus'" orderBy="fdOrder"></xform:beanDataSource>
			</xform:select>
		</td>
		<td class="td_normal_title" width="15%">
			<bean:message bundle="hr-ratify" key="hrRatifyEntry.fdNation"/>
		</td>
		<td width="35%">
			<xform:select property="fdNationId">
				<xform:beanDataSource serviceBean="hrStaffPersonInfoSetNewService" selectBlock="fdId,fdName" whereBlock="hrStaffPersonInfoSettingNew.fdType='fdNation'" orderBy="fdOrder"></xform:beanDataSource>
			</xform:select>
		</td>
	</tr>
	<tr>
		<td class="td_normal_title" width="15%">
			<bean:message bundle="hr-ratify" key="hrRatifyEntry.fdPoliticalLandscape"/>
		</td>
		<td width="35%">
			<xform:select property="fdPoliticalLandscapeId">
				<xform:beanDataSource serviceBean="hrStaffPersonInfoSetNewService" selectBlock="fdId,fdName" whereBlock="hrStaffPersonInfoSettingNew.fdType='fdPoliticalLandscape'" orderBy="fdOrder"></xform:beanDataSource>
			</xform:select>
		</td>
		<td class="td_normal_title" width="15%">
			<bean:message bundle="hr-ratify" key="hrRatifyEntry.fdHealth"/>
		</td>
		<td width="35%">
			<xform:select property="fdHealthId">
				<xform:beanDataSource serviceBean="hrStaffPersonInfoSetNewService" selectBlock="fdId,fdName" whereBlock="hrStaffPersonInfoSettingNew.fdType='fdHealth'" orderBy="fdOrder"></xform:beanDataSource>
			</xform:select>
		</td>
	</tr>
	<tr>
		<td class="td_normal_title" width="15%">
			<bean:message bundle="hr-ratify" key="hrRatifyEntry.fdLivingPlace"/>
		</td>
		<td width="35%">
			<xform:text property="fdLivingPlace"></xform:text>
		</td>
		<td class="td_normal_title" width="15%">
			<bean:message bundle="hr-ratify" key="hrRatifyEntry.fdIdCard"/>
		</td>
		<td width="35%">
			<xform:text property="fdIdCard" ></xform:text>
		</td>
	</tr>
	<tr>
		<td class="td_normal_title" width="15%">
			<bean:message bundle="hr-ratify" key="hrRatifyEntry.fdHighestDegree"/>
		</td>
		<td width="35%">
			<xform:select property="fdHighestDegreeId">
				<xform:beanDataSource serviceBean="hrStaffPersonInfoSetNewService" selectBlock="fdId,fdName" whereBlock="hrStaffPersonInfoSettingNew.fdType='fdHighestDegree'" orderBy="fdOrder"></xform:beanDataSource>
			</xform:select>
		</td>
		<td class="td_normal_title" width="15%">
			<bean:message bundle="hr-ratify" key="hrRatifyEntry.fdHighestEducation"/>
		</td>
		<td width="35%">
			<xform:select property="fdHighestEducationId">
				<xform:beanDataSource serviceBean="hrStaffPersonInfoSetNewService" selectBlock="fdId,fdName" whereBlock="hrStaffPersonInfoSettingNew.fdType='fdHighestEducation'" orderBy="fdOrder"></xform:beanDataSource>
			</xform:select>
		</td>
	</tr>
	<tr>
		<td class="td_normal_title" width="15%">
			<bean:message bundle="hr-ratify" key="hrRatifyEntry.fdProfession"/>
		</td>
		<td width="35%">
			<xform:text property="fdProfession"></xform:text>
		</td>
		<td class="td_normal_title" width="15%">
			<bean:message bundle="hr-ratify" key="hrRatifyEntry.fdWorkTime"/>
		</td>
		<td width="35%">
			<xform:datetime property="fdWorkTime" dateTimeType="date"></xform:datetime>
		</td>
	</tr>
	<tr>
		<td class="td_normal_title" width="15%">
			<bean:message bundle="hr-ratify" key="hrRatifyEntry.fdDateOfGroup"/>
		</td>
		<td width="35%">
			<xform:datetime property="fdDateOfGroup" dateTimeType="date"></xform:datetime>
		</td>
		<td class="td_normal_title" width="15%">
			<bean:message bundle="hr-ratify" key="hrRatifyEntry.fdDateOfParty"/>
		</td>
		<td width="35%">
			<xform:datetime property="fdDateOfParty" dateTimeType="date"></xform:datetime>
		</td>
	</tr>
	<tr>
		<td class="td_normal_title" width="15%">
			<bean:message bundle="hr-ratify" key="hrRatifyEntry.fdStature"/>
		</td>
		<td width="35%">
			<xform:text property="fdStature" validators="digits min(1)" className="inputsgl"></xform:text>
		</td>
		<td class="td_normal_title" width="15%">
			<bean:message bundle="hr-ratify" key="hrRatifyEntry.fdWeight"/>
		</td>
		<td width="35%">
			<xform:text property="fdWeight" validators="digits min(1)" className="inputsgl"></xform:text>
		</td>
	</tr>
	<tr>
		<td class="td_normal_title" width="15%">
			<bean:message bundle="hr-ratify" key="hrRatifyEntry.fdHomeplace"/>
		</td>
		<td width="35%">
			<xform:text property="fdHomeplace"></xform:text>
		</td>
		<td class="td_normal_title" width="15%">
			<bean:message bundle="hr-ratify" key="hrRatifyEntry.fdAccountProperties"/>
		</td>
		<td width="35%">
			<xform:text property="fdAccountProperties"></xform:text>
		</td>
	</tr>
	<tr>
		<td class="td_normal_title" width="15%">
			<bean:message bundle="hr-ratify" key="hrRatifyEntry.fdRegisteredResidence"/>
		</td>
		<td width="35%">
			<xform:text property="fdRegisteredResidence"></xform:text>
		</td>
		<td class="td_normal_title" width="15%">
			<bean:message bundle="hr-ratify" key="hrRatifyEntry.fdResidencePoliceStation"/>
		</td>
		<td width="35%">
			<xform:text property="fdResidencePoliceStation"></xform:text>
		</td>
	</tr>
	<tr>
		<td class="td_normal_title" width="15%">
			<bean:message bundle="hr-ratify" key="hrRatifyEntry.attRatifyEntry"/>
		</td>
		<td colspan="3">
			<c:import url="/sys/attachment/sys_att_main/sysAttMain_view.jsp" charEncoding="UTF-8">
				<c:param name="fdKey" value="attRatifyEntry" />
				<c:param name="formBeanName" value="hrRatifyEntryForm" />
				<c:param name="fdRequired" value="false" />
			</c:import>
		</td>
	</tr>
</table>