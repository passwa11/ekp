<%@ page language="java" contentType="text/json; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<list:data>
	<list:data-columns var="qualification" list="${queryPage.list }" varIndex="status">
		<list:data-column property="fdId">
		</list:data-column>
		<list:data-column col="personInfoId">
			${qualification.fdPersonInfo.fdId}
		</list:data-column>
		<list:data-column col="index">
		  ${status+1}
		</list:data-column>
		<!-- 姓名（账号）-->
		<list:data-column col="nameAccount" title="${ lfn:message('hr-staff:hrStaffPersonExperience.nameAccount') }" escape="false">
		    <span class="com_subject"> ${qualification.fdPersonInfo.nameAccount}</span>
		</list:data-column>
		<!--证书名称-->
		<list:data-column headerClass="width120" property="fdCertificateName" title="${ lfn:message('hr-staff:hrStaffPersonExperience.qualification.fdCertificateName') }"> 
		</list:data-column>
		<!--颁发日期-->
		<list:data-column headerClass="width80" col="fdBeginDate" title="${ lfn:message('hr-staff:hrStaffPersonExperience.qualification.fdBeginDate') }">
		    <kmss:showDate value="${qualification.fdBeginDate}" type="date" /> 
		</list:data-column>
		<!--失效日期-->
		<list:data-column headerClass="width80" col="fdEndDate" title="${ lfn:message('hr-staff:hrStaffPersonExperience.qualification.fdEndDate') }">
			<kmss:showDate value="${qualification.fdEndDate}" type="date" /> 
		</list:data-column> 
		<!--颁发单位-->
		<list:data-column headerClass="width120" property="fdAwardUnit" title="${ lfn:message('hr-staff:hrStaffPersonExperience.qualification.fdAwardUnit') }">
		</list:data-column> 
		<list:data-column headerClass="width100" col="fdSecondLevelDepartment" title="${ lfn:message('hr-staff:hrStaffPersonInfo.fdSecondLevelDepartment') }">
		
		<c:choose>
						<c:when test="${not empty qualification.fdPersonInfo.fdSecondLevelDepartment}">
				
		${qualification.fdPersonInfo.fdSecondLevelDepartment.fdName}
				</c:when>
				<c:otherwise>
					
				</c:otherwise>
			</c:choose>
		</list:data-column>
		<list:data-column headerClass="width100" col="fdFirstLevelDepartment" title="${ lfn:message('hr-staff:hrStaffPersonInfo.fdFirstLevelDepartment') }">
		
		
		<c:choose>
						<c:when test="${not empty qualification.fdPersonInfo.fdFirstLevelDepartment}">
				
		${qualification.fdPersonInfo.fdFirstLevelDepartment.fdName}
				</c:when>
				<c:otherwise>
					
				</c:otherwise>
			</c:choose>
		</list:data-column>
		<list:data-column headerClass="width100" col="fdThirdLevelDepartment" title="${ lfn:message('hr-staff:hrStaffPersonInfo.fdThirdLevelDepartment') }">
		
		
		<c:choose>
						<c:when test="${not empty qualification.fdPersonInfo.fdThirdLevelDepartment}">
				
		${qualification.fdPersonInfo.fdThirdLevelDepartment.fdName}
				</c:when>
				<c:otherwise>
					
				</c:otherwise>
			</c:choose>
		</list:data-column>
		<list:data-column headerClass="width100" col="fdAffiliatedCompany" title="${ lfn:message('hr-staff:hrStaffPersonInfo.fdAffiliatedCompany') }">
		${qualification.fdPersonInfo.fdAffiliatedCompany}
		</list:data-column>
		<list:data-column headerClass="width100" col="fdOrgRank" title="${ lfn:message('hr-staff:hrStaffPersonInfo.fdOrgRank') }">
		${qualification.fdPersonInfo.fdOrgRank.fdName}
		</list:data-column>
			<list:data-column headerClass="width200" col="fdOrgPostNames" title="${ lfn:message('hr-staff:hrStaffPersonInfo.fdOrgPosts') }">
			<c:forEach items="${ qualification.fdPersonInfo.fdOrgPosts }" varStatus="vstatus" var = "post">
			${post.fdName};
			</c:forEach>
		</list:data-column>
		<list:data-column headerClass="width200" col="fdDeptName" title="${ lfn:message('hr-staff:hrStaffPersonInfo.fdOrgParent') }">
			<c:choose>
				<c:when test="${not empty qualification.fdPersonInfo.fdOrgParent }">
					
						${qualification.fdPersonInfo.fdOrgParent.fdName}
				</c:when>
				<c:otherwise>
					
				</c:otherwise>
			</c:choose>
			</list:data-column>
		<!-- 其它操作 -->
		<list:data-column headerClass="width60" col="operations" title="${ lfn:message('list.operation') }" escape="false">
			<!--操作按钮 开始-->
			<div class="conf_show_more_w">
				<div class="conf_btn_edit">
					<!-- 删除 -->
					<a class="btn_txt" href="javascript:_delete('${LUI_ContextPath}/hr/staff/hr_staff_person_experience/qualification/hrStaffPersonExperienceQualification.do?method=deleteall', '${qualification.fdId}')">${ lfn:message('button.delete') }</a>
				</div>
			</div>
			<!--操作按钮 结束-->
		</list:data-column>
	</list:data-columns>
	<list:data-paging currentPage="${queryPage.pageno }" pageSize="${queryPage.rowsize }" totalSize="${queryPage.totalrows }">
	</list:data-paging>
</list:data>