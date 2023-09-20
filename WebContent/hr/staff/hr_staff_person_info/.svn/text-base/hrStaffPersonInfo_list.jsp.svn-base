<%@ page language="java" contentType="text/json; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ page import="com.landray.kmss.hr.staff.util.HrStaffPersonUtil,
				com.landray.kmss.hr.organization.model.HrOrganizationSyncSetting"%>
<%
	HrOrganizationSyncSetting syncSetting = new HrOrganizationSyncSetting();
	request.setAttribute("hrToEkpEnable", syncSetting.getHrToEkpEnable());
%>

<list:data>
	<list:data-columns var="hrStaffPersonInfo" list="${queryPage.list }" varIndex="status">
		<list:data-column property="fdId">
		</list:data-column>
		<list:data-column col="fdIsAvailable">
			${hrStaffPersonInfo.fdOrgPerson.fdIsAvailable}
		</list:data-column>
		<list:data-column col="imgUrl">
			${urlJson[hrStaffPersonInfo.fdId]}
		</list:data-column>
		<list:data-column col="index" headerClass="width20">
		  ${status+1}
		</list:data-column>
		<!-- 姓名-->
		<list:data-column headerClass="width160" col="fdName" title="${ lfn:message('hr-staff:hrStaffPersonInfo.fdName') }" escape="false">
			<span class="com_subject"><c:out value="${hrStaffPersonInfo.fdName}" /></span>
		</list:data-column>
		<!-- 性别-->
		<list:data-column headerClass="width100"  col="fdSex" title="${ lfn:message('hr-staff:hrStaffPersonInfo.fdSex') }" escape="false">
			<c:choose>
				<c:when test="${param.IsHrOrg }">
			      	<sunbor:enumsShow value="${hrStaffPersonInfo.fdSex}" enumsType="sys_org_person_sex" />
			    </c:when>
			    <c:otherwise>
			      	<sunbor:enumsShow value="${hrStaffPersonInfo.fdSex}" enumsType="sys_org_person_sex" />
			    </c:otherwise>
			</c:choose>
		</list:data-column>
		
		<!-- 工作时间 -->
		<list:data-column  col="fdWorkingYears" title="${ lfn:message('hr-staff:hrStaffPersonInfo.fdWorkingYears') }" escape="false">
			<c:out value="${hrStaffPersonInfo.fdWorkingYears}" />
		</list:data-column>
		<list:data-column col="personInfoName" title="${ lfn:message('hr-staff:hrStaffPersonInfo.fdName') }">
			<c:out value="${hrStaffPersonInfo.fdName}" />
		</list:data-column>
		<!--系统账号-->
		<list:data-column headerClass="width80" col="fdLoginName" title="${ lfn:message('hr-staff:hrStaffPersonInfo.fdLoginName') }"> 
		     <c:if test="${hrStaffPersonInfo.fdOrgPerson != null}">
	    		${hrStaffPersonInfo.fdOrgPerson.fdLoginName}
	    	</c:if>
	    	  <c:if test="${hrStaffPersonInfo.fdOrgPerson == null}">
	    		${hrStaffPersonInfo.fdLoginName}
	    	</c:if>
		</list:data-column>
		<!--到本单位时间-->
		<list:data-column headerClass="width100" col="fdTimeOfEnterprise" title="${ lfn:message('hr-staff:hrStaffPersonInfo.fdTimeOfEnterprise') }">
		    <kmss:showDate value="${hrStaffPersonInfo.fdTimeOfEnterprise}" type="date" /> 
		</list:data-column>
		<list:data-column headerClass="width200" col="fdFirstLevelDepartment" title="${ lfn:message('hr-staff:hrStaffPersonInfo.fdFirstLevelDepartment') }">
			${hrStaffPersonInfo.fdFirstLevelDepartment.fdName}
		</list:data-column>
		<list:data-column headerClass="width200" col="fdSecondLevelDepartment" title="${ lfn:message('hr-staff:hrStaffPersonInfo.fdSecondLevelDepartment') }">
			${hrStaffPersonInfo.fdSecondLevelDepartment.fdName}
		</list:data-column>
		<list:data-column headerClass="width200" col="fdThirdLevelDepartment" title="${ lfn:message('hr-staff:hrStaffPersonInfo.fdThirdLevelDepartment') }">
			${hrStaffPersonInfo.fdThirdLevelDepartment.fdName}
		</list:data-column>
		<!--部门-->
		<list:data-column headerClass="width200" col="fdDeptName" title="${ lfn:message('hr-staff:hrStaffPersonInfo.fdOrgParent') }">
			<c:choose>
				<c:when test="${hrToEkpEnable == true }">
					<c:if test="${not empty hrStaffPersonInfo.fdOrgParent}">
						${hrStaffPersonInfo.fdOrgParent.fdName}
					</c:if>
				</c:when>
				<c:otherwise>
						${hrStaffPersonInfo.fdOrgParent.fdName}
					<c:if test="${empty hrStaffPersonInfo.fdOrgParent}">
						${hrStaffPersonInfo.fdOrgParent.fdName}
					</c:if>
				</c:otherwise>
			</c:choose>
		</list:data-column>
		<list:data-column headerClass="width100" property="fdAge" title="${ lfn:message('hr-staff:hrStaffPersonInfo.fdAge') }">
		</list:data-column>
		<list:data-column headerClass="width100" property="fdNation" title="${ lfn:message('hr-staff:hrStaffPersonInfo.fdNation') }">
		</list:data-column>
		<list:data-column headerClass="width100" property="fdHighestEducation" title="${ lfn:message('hr-staff:hrStaffPersonInfo.fdHighestEducation') }">
		</list:data-column>
		<list:data-column headerClass="width100" property="fdMaritalStatus" title="${ lfn:message('hr-staff:hrStaffPersonInfo.fdMaritalStatus') }">
		</list:data-column>
		<list:data-column headerClass="width100" property="fdNativePlace" title="${ lfn:message('hr-staff:hrStaffPersonInfo.fdNativePlace') }">
		</list:data-column>
		<list:data-column headerClass="width100" property="fdProbationPeriod" title="${ lfn:message('hr-staff:hrStaffPersonInfo.fdProbationPeriod') }">
		</list:data-column>
		<list:data-column headerClass="width100" property="fdEnterpriseAge" title="${ lfn:message('hr-staff:hrStaffPersonInfo.fdWorkingYears') }">
		</list:data-column>
		<list:data-column headerClass="width100" col="fdWorkTime" title="${ lfn:message('hr-staff:hrStaffPersonInfo.fdWorkTime') }">
		 <kmss:showDate value="${hrStaffPersonInfo.fdWorkTime}" type="date" /> 
		</list:data-column>
		<list:data-column headerClass="width100" col="fdResignationDate" title="${ lfn:message('hr-staff:hrStaffPersonInfo.fdResignationDate') }">
		 <kmss:showDate value="${hrStaffPersonInfo.fdResignationDate}" type="date" /> 
		</list:data-column>
		<list:data-column headerClass="width100" property="fdStaffAge" title="${ lfn:message('hr-staff:hrStaffPersonInfo.fdUninterruptedWorkTime') }">
		</list:data-column>
		<list:data-column headerClass="width100" property="fdAffiliatedCompany" title="${ lfn:message('hr-staff:hrStaffPersonInfo.fdAffiliatedCompany') }">
		</list:data-column>
		<list:data-column headerClass="width100" property="fdStaffType" title="${ lfn:message('hr-staff:hrStaffPersonInfo.fdStaffType') }">
		</list:data-column>
		<list:data-column headerClass="width100" col="fdProposedEmploymentConfirmationDate" title="${ lfn:message('hr-staff:hrStaffPersonInfo.fdProposedEmploymentConfirmationDate') }">
		 <kmss:showDate value="${hrStaffPersonInfo.fdProposedEmploymentConfirmationDate}" type="date" /> 
		</list:data-column>
		<list:data-column headerClass="width100" col="fdStaffingLevel" title="${ lfn:message('hr-staff:hrStaffPersonInfo.fdStaffingLevel') }">
		${hrStaffPersonInfo.fdStaffingLevel.fdName}
		</list:data-column>
		<list:data-column headerClass="width100" col="fdOrgRank" title="${ lfn:message('hr-staff:hrStaffPersonInfo.fdOrgRank') }">
		${hrStaffPersonInfo.fdOrgRank.fdName}
		</list:data-column>
		<list:data-column headerClass="width100" col="fdReportLeader" title="${ lfn:message('hr-staff:hrStaffPersonInfo.fdReportLeader') }">
		${hrStaffPersonInfo.fdReportLeader.fdName}
		</list:data-column>
		<list:data-column headerClass="width100" col="fdDepartmentHead" title="${ lfn:message('hr-staff:hrStaffPersonInfo.fdDepartmentHead') }">
		${hrStaffPersonInfo.fdDepartmentHead.fdName}
		</list:data-column>
		<list:data-column headerClass="width100" col="fdHeadOfFirstLevelDepartment" title="${ lfn:message('hr-staff:hrStaffPersonInfo.fdHeadOfFirstLevelDepartment') }">
		${hrStaffPersonInfo.fdHeadOfFirstLevelDepartment.fdName}
		</list:data-column>
		<!--工号-->
		<list:data-column headerClass="width100" property="fdStaffNo" title="${ lfn:message('hr-staff:hrStaffPersonInfo.fdStaffNo') }">
		</list:data-column>
		<list:data-column headerClass="width100" property="fdRegisteredResidence" title="${ lfn:message('hr-staff:hrStaffPersonInfo.fdRegisteredResidence') }">
		</list:data-column>
		<!--员工状态-->
		<list:data-column headerClass="width100" col="fdStatus" title="${ lfn:message('hr-staff:hrStaffPersonInfo.fdStatus') }">
			<sunbor:enumsShow value="${ hrStaffPersonInfo.fdStatus }" enumsType="hrStaffPersonInfo_fdStatus" />
		</list:data-column>
		<list:data-column headerClass="width100" col="fdContType" title="${ lfn:message('hr-staff:hrStaffPersonExperience.contract.fdContType') }">
		${hrStaffPersonInfo.fdContType}
		</list:data-column>
		<list:data-column headerClass="width100" col="fdContractUnit" title="${ lfn:message('hr-staff:hrStaffPersonExperience.contract.fdContUnit') }">
		${hrStaffPersonInfo.fdContractUnit}
		</list:data-column>
		<list:data-column headerClass="width100" col="fdBeginDate" title="${ lfn:message('hr-staff:hrStaffPersonExperience.contract.fdBeginDate') }">
		 <kmss:showDate value="${hrStaffPersonInfo.fdBeginDate}" type="date" /> 
		</list:data-column>
		<list:data-column headerClass="width100" col="fdEndDate" title="${ lfn:message('hr-staff:hrStaffPersonExperience.contract.fdEndDate') }">
		 <kmss:showDate value="${hrStaffPersonInfo.fdEndDate}" type="date" /> 
		</list:data-column>
		<list:data-column headerClass="width100" col="fdContractPeriod" title="${ lfn:message('hr-staff:hrStaffPersonExperience.contract.fdContPeriod') }">
		 <c:if test="${not empty hrStaffPersonInfo.fdContractYear}">
		 ${hrStaffPersonInfo.fdContractYear}年${hrStaffPersonInfo.fdContractMonth}月
					</c:if>
		</list:data-column>
		<!-- 其它操作 -->
		<list:data-column headerClass="width100" col="operations" title="${ lfn:message('list.operation') }" escape="false">
			<!--操作按钮 开始-->
			<div class="conf_show_more_w">
				<div class="conf_btn_edit">
					<kmss:authShow roles="ROLE_HRSTAFF_EDIT">
					<!-- 编辑 -->
					<a class="btn_txt" href="javascript:edit('${hrStaffPersonInfo.fdId}')">${lfn:message('button.edit')}</a>
					</kmss:authShow>
					<kmss:authShow roles="ROLE_HRSTAFF_DELETE">
					<!-- 删除 -->
					<a class="btn_txt" href="javascript:del('${hrStaffPersonInfo.fdId}')">${ lfn:message('button.delete') }</a>
			    	</kmss:authShow>
				</div>
			</div>
			<!--操作按钮 结束-->
		</list:data-column>
		<list:data-column headerClass="width100" col="hrOperations" title="${ lfn:message('list.operation') }" escape="false">
			<!--操作按钮 开始-->
			<div class="conf_show_more_w">
				<div class="conf_btn_edit">
					<a class="btn_txt" href="javascript:viewInfo('${hrStaffPersonInfo.fdId}')">详情</a>
				</div>
			</div>
			<!--操作按钮 结束-->
		</list:data-column>		
		<!--门户补充  start-->
		<!--岗位-->
		<%
			int a = 0;
		%>
		<c:forEach items="${ hrStaffPersonInfo.fdOrgPosts }" varStatus="vstatus" var = "post">
			<%
				a++;
			%>
			</c:forEach>
		<list:data-column headerClass="width200" col="fdOrgPostNames" title="${ lfn:message('hr-staff:hrStaffPersonInfo.fdOrgPosts') }">
			<%
				if(a==1){
			%>
			<c:forEach items="${ hrStaffPersonInfo.fdOrgPosts }" varStatus="vstatus" var = "post">
			${post.fdName}
			</c:forEach>
			<%
				}
			%>
			<%
				if(a!=1){
			%>
			<c:forEach items="${ hrStaffPersonInfo.fdOrgPosts }" varStatus="vstatus" var = "post">
			${post.fdName};
			</c:forEach>
			<%
				}
			%>
		</list:data-column>
		<!--岗位fdPosts-->
		<list:data-column headerClass="width200" col="fdPosts" title="${ lfn:message('hr-staff:hrStaffPersonInfo.fdOrgPosts') }">
			<c:forEach items="${ hrStaffPersonInfo.fdPosts }" varStatus="vstatus" var = "post">
				${post.fdName};
			</c:forEach>
		</list:data-column>
		<!--入职时间-->
		<list:data-column headerClass="width100" col="fdEntryTime" title="${ lfn:message('hr-staff:hrStaffPersonInfo.fdEntryTime') }">
		    <kmss:showDate value="${hrStaffPersonInfo.fdEntryTime}" type="date" /> 
		</list:data-column>
		<!--转正时间-->
		<list:data-column headerClass="width100" col="fdPositiveTime" title="${ lfn:message('hr-staff:hrStaffPersonInfo.fdPositiveTime') }">
		    <kmss:showDate value="${hrStaffPersonInfo.fdPositiveTime}" type="date" /> 
		</list:data-column>
		<!--出生时间-->
		<list:data-column headerClass="width100" col="fdDateOfBirth" title="${ lfn:message('hr-staff:hrStaffPersonInfo.fdDateOfBirth') }">
		    <kmss:showDate value="${hrStaffPersonInfo.fdDateOfBirth}" type="date" /> 
		</list:data-column>
		<list:data-column headerClass="width100" col="fdOrder" title="排序号">
		   ${hrStaffPersonInfo.fdOrder}
		</list:data-column>
		<list:data-column headerClass="width100" col="fdReportLeaderName" title="汇报上级">
		   ${hrStaffPersonInfo.fdReportLeader.fdName}
		</list:data-column>					
	</list:data-columns>
	<list:data-paging currentPage="${queryPage.pageno }" pageSize="${queryPage.rowsize }" totalSize="${queryPage.totalrows }">
	</list:data-paging>
</list:data>