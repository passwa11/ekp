<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@page import="java.util.List"%>
<%@page import="com.landray.kmss.util.ResourceUtil"%>
<%@page import="com.landray.kmss.util.DateUtil"%>
<%@page import="java.util.Date"%>
<template:include ref="default.archive" sidebar="no">
<template:replace name="head">
</template:replace>
<template:replace name="title">
	<c:out value="${hrRatifyEntryForm.docSubject }"></c:out>
</template:replace>
<template:replace name="toolbar">
</template:replace>
<template:replace name="content">
<c:set var="p_defconfig" value="${p_defconfig}" scope="request"/>
<form name="hrRatifyEntryForm" method="post" action="<c:url value="/hr/ratify/hr_ratify_entry/hrRatifyEntry.do"/>">
<center>
<div class="print_title_header">
<p id="title" class="print_txttitle">
	<bean:write name="hrRatifyEntryForm" property="docSubject" />
</p>
<div class="printDate">
  	<bean:message bundle="hr-ratify" key="hrRatifyDocumentLableName.transferDate" />:<% out.print(DateUtil.convertDateToString(new Date(), DateUtil.TYPE_DATE, request.getLocale()));%>
</div>
</div>
<div id="printTable" style="border: none;">
<div printTr="true" style="border: none;">

<%-- 基本信息 width="650px" --%>
<div>
    <div class="tr_label_title"> 
       <div class="title">
      	 <bean:message bundle="hr-ratify" key="hrRatifyDocumentLableName.baseInfo" />
       </div>
    </div>
	<table class="tb_normal" width=100%>
		<!--主题-->
		<tr>
			<td class="td_normal_title" width=15%>
				<bean:message bundle="hr-ratify" key="hrRatifyMain.docSubject" /></td>
			<td colspan=3>
				<bean:write name="hrRatifyEntryForm" property="docSubject" /></td>
		</tr>
		<!--模板名称-->
		<tr>
			<td class="td_normal_title" width=15%>
				<bean:message bundle="hr-ratify" key="hrRatifyMain.docTemplate" /></td>
			<td colspan=3>
				<bean:write name="hrRatifyEntryForm" property="docTemplateName" /></td>
		</tr>
		<!--申请人-->
		<tr>
			<td class="td_normal_title" width=15%>
				<bean:message bundle="hr-ratify" key="hrRatifyMain.docCreator" /></td>
			<td width=35%>
				<html:hidden name="hrRatifyEntryForm" property="docCreatorId" /> 
				<bean:write name="hrRatifyEntryForm" property="docCreatorName" /></td>
			<td class="td_normal_title" width=15%>
				<bean:message bundle="hr-ratify" key="hrRatifyMain.docNumber" /></td>
			<td width=35%>
				<bean:write name="hrRatifyEntryForm" property="docNumber" /></td>
		</tr>
		<!--部门-->
		<tr>
			<td class="td_normal_title" width=15%>
				<bean:message bundle="hr-ratify" key="hrRatifyMain.fdDepartment" /></td>
			<td>
				<bean:write name="hrRatifyEntryForm" property="fdDepartmentName"/></td>
			<td class="td_normal_title" width=15%>
				<bean:message bundle="hr-ratify" key="hrRatifyMain.docCreateTime" /></td>
			<td width=35%>
				<bean:write name="hrRatifyEntryForm" property="docCreateTime" /></td>
		</tr>
		<!--实施反馈人-->
		<tr>
			<td class="td_normal_title" width=15%>
				<bean:message bundle="hr-ratify" key="hrRatifyMain.fdFeedback" /></td>
			<td colspan=3>
				<bean:write name="hrRatifyEntryForm" property="fdFeedbackNames" /></td>
		</tr>
		<!--关键字-->
		<tr>
			<td class="td_normal_title" width=15%>
				<bean:message bundle="hr-ratify" key="hrRatifyMKeyword.docKeyword" /></td>
			<td colspan=3>
				<bean:write name="hrRatifyEntryForm" property="fdKeywordNames" /></td>
		</tr>
	</table>
</div>

<%-- 审批内容 --%>
<div>
    <div class="tr_label_title">
	    <div class="title">
	       <bean:message bundle="hr-ratify" key="hrRatifyDocumentLableName.reviewContent" />
	    </div>
    </div>
	
	<c:if test="${hrRatifyEntryForm.docUseXform == 'false' }">
		<table id="info_content" class="tb_normal" width=100% >
			<tr>
                <td colspan="4">${hrRatifyEntryForm.docXform}</td>
            </tr>
		</table>
	</c:if>
	<c:if test="${hrRatifyEntryForm.docUseXform == 'true' || empty hrRatifyEntryForm.docUseXform }">
		<table id="info_content" width=100% >
			<tr>
				<td id="_xform_detail">
					<%-- 表单 --%>
					<c:import url="/resource/html_locate/sysForm.jsp" charEncoding="UTF-8">
						<c:param name="formName" value="hrRatifyEntryForm" />
						<c:param name="fdKey" value="hrRatifyMain" />
						<c:param name="messageKey" value="hr-ratify:hrRatifyDocumentLableName.reviewContent" />
						<c:param name="useTab" value="false" />
						<c:param name="isPrint" value="true" />
					</c:import>
				</td>
			</tr>
		</table>
	 </c:if>
</div>

<%-- 入职人员档案信息 --%>
<div>
	<div class="tr_label_title">
	    <div class="title">
	       <bean:message bundle="hr-ratify" key="py.GeRenXinXi" />
	    </div>
    </div>
    
    <div style="text-align: left;margin: 5px 0;">${lfn:message('hr-ratify:py.JiBenXinXi') }</div>
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
				<sunbor:enums property="fdSex" enumsType="sys_org_person_sex" elementType="radio" />
			</td>
		</tr>
		<tr>
			<td class="td_normal_title" width="15%">
				<bean:message bundle="hr-ratify" key="hrRatifyEntry.fdDateOfBirth"/>
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
				<c:import url="/resource/html_locate/sysAttMain_view.jsp" charEncoding="UTF-8">
					<c:param name="fdKey" value="attRatifyEntry" />
					<c:param name="formBeanName" value="hrRatifyEntryForm" />
					<c:param name="fdRequired" value="false" />
				</c:import>
			</td>
		</tr>
	</table>
	
	<div style="text-align: left;margin: 5px 0;">${lfn:message('hr-ratify:py.LianXiXinXi') }</div>
	<table class="tb_normal" width=100%>
		<tr>
			<td class="td_normal_title" width="15%">
				邮箱地址
			</td>
			<td width="35%">
				<xform:text property="fdEmail"></xform:text>
			</td>
			<td class="td_normal_title" width="15%">
				手机号码
			</td>
			<td width="35%">
				<xform:text property="fdMobileNo" validators="phoneNumber"></xform:text>
			</td>
		</tr>
		<tr>
			<td class="td_normal_title" width="15%">
				紧急联系人
			</td>
			<td width="35%">
				<xform:text property="fdEmergencyContact"></xform:text>
			</td>
			<td class="td_normal_title" width="15%">
				紧急联系人电话
			</td>
			<td width="35%">
				<xform:text property="fdEmergencyContactPhone" validators="phoneNumber"></xform:text>
			</td>
		</tr>
		<tr>
			<td class="td_normal_title" width="15%">
				其他联系方式
			</td>
			<td colspan="3">
				<xform:text property="fdOtherContact"></xform:text>
			</td>
		</tr>
	</table>
	
	<div style="text-align: left;margin: 5px 0;">${ lfn:message('hr-ratify:table.hrRatifyHistory') }</div>
	<table class="tb_normal" width="100%" id="TABLE_DocList_fdHistory_Form" align="center" tbdraggable="true">
		<tr align="center" class="tr_normal_title">
			<td style="width: 40px;">
				${lfn:message('page.serial')}</td>
			<td>
				${lfn:message('hr-ratify:hrRatifyHistory.fdName')}</td>
			<td>
				${lfn:message('hr-ratify:hrRatifyHistory.fdPost')}</td>
			<td>
				${lfn:message('hr-ratify:hrRatifyHistory.fdStartDate')}</td>
			<td>
				${lfn:message('hr-ratify:hrRatifyHistory.fdEndDate')}</td>
		</tr>
		<c:forEach items="${hrRatifyEntryForm.fdHistory_Form}" var="fdHistory_FormItem" varStatus="vstatus">
			<tr KMSS_IsContentRow="1">
				<td align="center">
					${vstatus.index+1}
				</td>
				<td align="center">
					<%-- 公司名称--%> 
					<xform:text
						property="fdHistory_Form[${vstatus.index}].fdName"
						showStatus="view"
						subject="${lfn:message('hr-ratify:hrRatifyHistory.fdName')}"
						style="width:95%;" />
				</td>
				<td align="center">
					<%-- 职位--%>
					<xform:text
						property="fdHistory_Form[${vstatus.index}].fdPost"
						showStatus="view"
						subject="${lfn:message('hr-ratify:hrRatifyHistory.fdPost')}"
						style="width:95%;" />
				</td>
				<td align="center">
					<%-- 开始日期--%>
					<xform:datetime
						property="fdHistory_Form[${vstatus.index}].fdStartDate"
						showStatus="view" dateTimeType="date"
						style="width:95%;" />
				</td>
				<td align="center">
					<%-- 结束日期--%>
					<xform:datetime
						property="fdHistory_Form[${vstatus.index}].fdEndDate"
						showStatus="view" dateTimeType="date"
						style="width:95%;" />
				</td>
			</tr>
		</c:forEach>
	</table>
	
	<div style="text-align: left;margin: 5px 0;">${ lfn:message('hr-ratify:table.hrRatifyEduExp') }</div>
	<table class="tb_normal" width="100%" id="TABLE_DocList_fdEducations_Form" align="center" tbdraggable="true">
		<tr align="center" class="tr_normal_title">
			<td style="width: 40px;">${lfn:message('page.serial')}</td>
			<td width="30%">${lfn:message('hr-ratify:hrRatifyEduExp.fdName')}</td>
			<td>${lfn:message('hr-ratify:hrRatifyEduExp.fdMajor')}
			</td>
			<td width="15%">
				${lfn:message('hr-ratify:hrRatifyEduExp.fdAcademic')}</td>
			<td>
				${lfn:message('hr-ratify:hrRatifyEduExp.fdEntranceDate')}</td>
			<td>
				${lfn:message('hr-ratify:hrRatifyEduExp.fdGraduationDate')}
			</td>
		</tr>
		<c:forEach items="${hrRatifyEntryForm.fdEducations_Form}" var="fdEducations_FormItem" varStatus="vstatus">
			<tr KMSS_IsContentRow="1">
				<td align="center">
					${vstatus.index+1}
				</td>
				<td align="center">
					<%-- 学校名称--%> 
					<xform:text
						property="fdEducations_Form[${vstatus.index}].fdName"
						showStatus="view" 
						subject="${lfn:message('hr-ratify:hrRatifyEduExp.fdName')}"
						style="width:95%;" />
				</td>
				<td align="center" >
					<%-- 专业名称--%>
					<xform:text
						property="fdEducations_Form[${vstatus.index}].fdMajor"
						showStatus="view"
						subject="${lfn:message('hr-ratify:hrRatifyEduExp.fdMajor')}"
						style="width:95%;" />
				</td>
				<td align="center" >
					<%-- 学位--%>
					<xform:text
						property="fdEducations_Form[${vstatus.index}].fdAcadeRecordName"
						showStatus="view"
						subject="${lfn:message('hr-ratify:hrRatifyEduExp.fdAcadeRecord')}"
						style="width:95%;" />
				</td>
				<td align="center" >
					<%-- 入学日期--%>
					<xform:datetime
						property="fdEducations_Form[${vstatus.index}].fdEntranceDate"
						showStatus="view" dateTimeType="date" style="width:95%;" />
				</td>
				<td align="center" >
					<%-- 毕业日期--%>
					<xform:datetime
						property="fdEducations_Form[${vstatus.index}].fdGraduationDate"
						showStatus="view" dateTimeType="date" style="width:95%;" />
				</td>
			</tr>
		</c:forEach>	
	</table>
	
	<div style="text-align: left;margin: 5px 0;">${ lfn:message('hr-ratify:table.hrRatifyCertifi') }</div>
	<table class="tb_normal" width="100%" id="TABLE_DocList_fdCertificate_Form" align="center" tbdraggable="true">
		<tr align="center" class="tr_normal_title">
			<td style="width: 40px;">${lfn:message('page.serial')}</td>
			<td>${lfn:message('hr-ratify:hrRatifyCertifi.fdName')}
			</td>
			<td>
				${lfn:message('hr-ratify:hrRatifyCertifi.fdIssuingUnit')}</td>
			<td>
				${lfn:message('hr-ratify:hrRatifyCertifi.fdIssueDate')}</td>
			<td>
				${lfn:message('hr-ratify:hrRatifyCertifi.fdInvalidDate')}</td>
		</tr>
		<c:forEach items="${hrRatifyEntryForm.fdCertificate_Form}" var="fdCertificate_FormItem" varStatus="vstatus">
			<tr KMSS_IsContentRow="1">
				<td align="center">
					${vstatus.index+1}
				</td>
				<td align="center">
					<%-- 证书名称--%> 
					<xform:text
						property="fdCertificate_Form[${vstatus.index}].fdName"
						showStatus="view" 
						subject="${lfn:message('hr-ratify:hrRatifyCertifi.fdName')}"
						style="width:95%;" />
				</td>
				<td align="center">
					<%-- 颁发单位--%>
					<xform:text
						property="fdCertificate_Form[${vstatus.index}].fdIssuingUnit"
						showStatus="view"
						subject="${lfn:message('hr-ratify:hrRatifyCertifi.fdIssuingUnit')}"
						style="width:95%;" />
				</td>
				<td align="center">
					<%-- 颁发日期--%>
					<xform:datetime
						property="fdCertificate_Form[${vstatus.index}].fdIssueDate"
						showStatus="view" dateTimeType="date" style="width:95%;" />
				</td>
				<td align="center">
					<%-- 失效日期--%>
					<xform:datetime
						property="fdCertificate_Form[${vstatus.index}].fdInvalidDate"
						showStatus="view" dateTimeType="date" style="width:95%;" />
				</td>
			</tr>
		</c:forEach>
	</table>
	
	<div style="text-align: left;margin: 5px 0;">${ lfn:message('hr-ratify:table.hrRatifyRewPuni') }</div>
	<table class="tb_normal" width="100%" id="TABLE_DocList_fdRewardsPunishments_Form" align="center" tbdraggable="true">
		<tr align="center" class="tr_normal_title">
			<td style="width: 40px;">${lfn:message('page.serial')}</td>
			<td>${lfn:message('hr-ratify:hrRatifyRewPuni.fdName')}
			</td>
			<td>${lfn:message('hr-ratify:hrRatifyRewPuni.fdDate')}
			</td>
		</tr>
		<c:forEach items="${hrRatifyEntryForm.fdRewardsPunishments_Form}" var="fdRewardsPunishments_FormItem" varStatus="vstatus">
			<tr KMSS_IsContentRow="1">
				<td align="center">
					${vstatus.index+1}
				</td>
				<td align="center" >
					<%-- 奖惩名称--%> 
					<xform:text
						property="fdRewardsPunishments_Form[${vstatus.index}].fdName"
						showStatus="view"
						subject="${lfn:message('hr-ratify:hrRatifyRewPuni.fdName')}"
						style="width:95%;" />
				</td>
				<td  align="center" >
					<%-- 奖惩日期--%>
					<xform:datetime
						property="fdRewardsPunishments_Form[${vstatus.index}].fdDate"
						showStatus="view" dateTimeType="date" style="width:95%;" />
				</td>
			</tr>
		</c:forEach>
	</table>
	
</div>

<%-- 审批记录 --%>
<c:if test="${saveApproval }">
<div>
    <div class="tr_label_title">
	    <div class="title">
	       <bean:message bundle="hr-ratify" key="hrRatify.note" />
	    </div>
    </div>
	<table width=100%>
		<!-- 审批记录 -->
		<tr>
			<td colspan=4>
				<c:import url="/resource/html_locate/lbpmAuditNote.jsp" charEncoding="UTF-8">
					<c:param name="formName" value="hrRatifyEntryForm" />
				</c:import>
			</td>
		</tr>
	</table>
</div>
</c:if>
</div>
</div>


</center>
</form>
</template:replace>
		
</template:include>

