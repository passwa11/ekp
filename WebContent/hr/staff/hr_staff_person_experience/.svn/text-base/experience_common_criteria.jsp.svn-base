<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>

<!-- 筛选器 -->
<list:criteria id="criteria">
	<list:cri-ref key="_fdKey" ref="criterion.sys.docSubject" title="${ lfn:message('hr-staff:hrStaffPersonInfo.criteria.fdKey') }" style="width:300px;">
	</list:cri-ref>
	<list:cri-criterion title="${ lfn:message('hr-staff:hrStaffPersonInfo.fdStatus') }" key="_fdStatus" multi="true">
		<list:box-select>
			<list:item-select cfg-defaultValue="official">
								<ui:source type="Static">
								[{text:'${ lfn:message('hr-staff:hrStaffPersonInfo.fdStatus.onpost') }',value:'onpost'},
								{text:'${ lfn:message('hr-staff:hrStaffPersonInfo.fdStatus.official') }',value:'official'},
								{text:'${ lfn:message('hr-staff:hrStaffPersonInfo.fdStatus.trial') }', value:'trial'},
								{text:'${ lfn:message('hr-staff:hrStaffPersonInfo.fdStatus.rehireAfterRetirement') }',value:'rehireAfterRetirement'},
<%-- 								{text:'${ lfn:message('hr-staff:hrStaffPersonInfo.fdStatus.trialDelay') }',value:'trialDelay'}, --%>
<%-- 								{text:'${ lfn:message('hr-staff:hrStaffPersonInfo.fdStatus.leave') }',value:'quit'}, --%>
<%-- 								{text:'${ lfn:message('hr-staff:hrStaffPersonInfo.fdStatus.dismissal') }',value:'dismissal'}, --%>
								{text:'${ lfn:message('hr-staff:hrStaffPersonInfo.fdStatus.leave') }',value:'leave'},
								{text:'${ lfn:message('hr-staff:hrStaffPersonInfo.fdStatus.blacklist') }', value:'blacklist'}
								]
							</ui:source>
						</list:item-select>
		</list:box-select> 
	</list:cri-criterion>
	<list:cri-ref key="_fdDept" ref="criterion.sys.dept" title="${ lfn:message('hr-staff:hrStaffPersonInfo.fdOrgParent') }"></list:cri-ref>
	<c:choose>
		<c:when test="${'work' eq param.experienceType}">
			<%-- work --%>
			<list:cri-ref key="fdBeginDate" ref="criterion.sys.calendar" title="${ lfn:message('hr-staff:hrStaffPersonExperience.fdBeginDate') }"></list:cri-ref>
			<list:cri-ref key="fdEndDate" ref="criterion.sys.calendar" title="${ lfn:message('hr-staff:hrStaffPersonExperience.fdEndDate') }"></list:cri-ref>
		</c:when>
		<c:when test="${'training' eq param.experienceType}">
			<%-- training --%>
			<list:cri-ref key="fdBeginDate" ref="criterion.sys.calendar" title="${ lfn:message('hr-staff:hrStaffPersonExperience.fdBeginDate') }"></list:cri-ref>
			<list:cri-ref key="fdEndDate" ref="criterion.sys.calendar" title="${ lfn:message('hr-staff:hrStaffPersonExperience.fdEndDate') }"></list:cri-ref>
		</c:when>
		<c:when test="${'qualification' eq param.experienceType}">
			<%-- qualification --%>
			<list:cri-ref key="fdBeginDate" ref="criterion.sys.calendar" title="${ lfn:message('hr-staff:hrStaffPersonExperience.qualification.fdBeginDate') }"></list:cri-ref>
			<list:cri-ref key="fdEndDate" ref="criterion.sys.calendar" title="${ lfn:message('hr-staff:hrStaffPersonExperience.qualification.fdEndDate') }"></list:cri-ref>
		</c:when>
		<c:when test="${'education' eq param.experienceType}">
			<%-- education --%>
			<list:cri-ref key="fdBeginDate" ref="criterion.sys.calendar" title="${ lfn:message('hr-staff:hrStaffPersonExperience.education.fdBeginDate') }"></list:cri-ref>
			<list:cri-ref key="fdEndDate" ref="criterion.sys.calendar" title="${ lfn:message('hr-staff:hrStaffPersonExperience.education.fdEndDate') }"></list:cri-ref>
		</c:when>
		<c:when test="${'contract' eq param.experienceType}">
			<%-- contract --%>
			<list:cri-ref key="fdBeginDate" ref="criterion.sys.calendar" title="${ lfn:message('hr-staff:hrStaffPersonExperience.contract.fdBeginDate') }"></list:cri-ref>
			<list:cri-ref key="fdEndDate" ref="criterion.sys.calendar" title="${ lfn:message('hr-staff:hrStaffPersonExperience.contract.fdEndDate') }"></list:cri-ref>
		</c:when>
		<c:when test="${'bonusMalus' eq param.experienceType}">
			<%-- bonusMalus --%>
			<list:cri-ref key="fdBonusMalusDate" ref="criterion.sys.calendar" title="${ lfn:message('hr-staff:hrStaffPersonExperience.bonusMalus.fdBonusMalusDate') }"></list:cri-ref>
		</c:when>
	</c:choose>
</list:criteria>