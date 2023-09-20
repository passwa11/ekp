<%@ page language="java" contentType="text/json; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<list:data>
	<list:data-columns var="work" list="${queryPage.list }" varIndex="status">
		<list:data-column property="fdId">
		</list:data-column>
		<list:data-column col="personInfoId">
			${work.fdPersonInfo.fdId}
		</list:data-column>
		<list:data-column col="index">
		  ${status+1}
		</list:data-column>
		<!-- 姓名（账号）-->
		<list:data-column col="nameAccount" title="${ lfn:message('hr-staff:hrStaffPersonExperience.nameAccount') }" escape="false">
		    <span class="com_subject"> ${work.fdPersonInfo.nameAccount}</span>
		</list:data-column>
		<!--公司-->
		<list:data-column headerClass="width120" property="fdCompany" title="${ lfn:message('hr-staff:hrStaffPersonExperience.work.fdCompany') }"> 
		</list:data-column>
		<!--职位-->
		<list:data-column headerClass="width120" property="fdPosition" title="${ lfn:message('hr-staff:hrStaffPersonExperience.work.fdPosition') }"> 
		</list:data-column>
		<!--开始时间-->
		<list:data-column headerClass="width80" col="fdBeginDate" title="${ lfn:message('hr-staff:hrStaffPersonExperience.fdBeginDate') }">
		    <kmss:showDate value="${work.fdBeginDate}" type="date" /> 
		</list:data-column>
		<!--结束日期-->
		<list:data-column headerClass="width80" col="fdEndDate" title="${ lfn:message('hr-staff:hrStaffPersonExperience.fdEndDate') }">
			<kmss:showDate value="${work.fdEndDate}" type="date" /> 
		</list:data-column> 
		<!--工作描述-->
		<list:data-column headerClass="width100" col="fdDescription" title="${ lfn:message('hr-staff:hrStaffPersonExperience.work.fdDescription') }" escape="false">
			<span style="width:120px" class="textEllipsis" title="${ lfn:escapeHtml(work.fdDescription) }">${ lfn:escapeHtml(work.fdDescription) }</span>
		</list:data-column> 
		<!--离开原因-->
		<list:data-column headerClass="width100" col="fdReasons" title="${ lfn:message('hr-staff:hrStaffPersonExperience.work.fdReasons') }" escape="false">
			<span style="width:120px" class="textEllipsis" title="${ lfn:escapeHtml(work.fdReasons) }">${ lfn:escapeHtml(work.fdReasons) }</span>
		</list:data-column> 
		<list:data-column headerClass="width200" col="fdDeptName" title="${ lfn:message('hr-staff:hrStaffPersonInfo.fdOrgParent') }">
			<c:choose>
				<c:when test="${not empty work.fdPersonInfo.fdOrgParent }">
					
						${work.fdPersonInfo.fdOrgParent.fdName}
				</c:when>
				<c:otherwise>
					
				</c:otherwise>
			</c:choose>
			</list:data-column>	
			
			<list:data-column headerClass="width200" col="fdOrgPostNames" title="${ lfn:message('hr-staff:hrStaffPersonInfo.fdOrgPosts') }">
			<c:forEach items="${ work.fdPersonInfo.fdOrgPosts }" varStatus="vstatus" var = "post">
			${post.fdName};
			</c:forEach>
		</list:data-column>
			<list:data-column headerClass="width100" col="fdSecondLevelDepartment" title="${ lfn:message('hr-staff:hrStaffPersonInfo.fdSecondLevelDepartment') }">
		
		<c:choose>
						<c:when test="${not empty work.fdPersonInfo.fdSecondLevelDepartment}">
				
		${work.fdPersonInfo.fdSecondLevelDepartment.fdName}
				</c:when>
				<c:otherwise>
					
				</c:otherwise>
			</c:choose>
		</list:data-column>
		<list:data-column headerClass="width100" col="fdFirstLevelDepartment" title="${ lfn:message('hr-staff:hrStaffPersonInfo.fdFirstLevelDepartment') }">
		
		
		<c:choose>
						<c:when test="${not empty work.fdPersonInfo.fdFirstLevelDepartment}">
				
		${work.fdPersonInfo.fdFirstLevelDepartment.fdName}
				</c:when>
				<c:otherwise>
					
				</c:otherwise>
			</c:choose>
		</list:data-column>
		<list:data-column headerClass="width100" col="fdThirdLevelDepartment" title="${ lfn:message('hr-staff:hrStaffPersonInfo.fdThirdLevelDepartment') }">
		
		
		<c:choose>
						<c:when test="${not empty work.fdPersonInfo.fdThirdLevelDepartment}">
				
		${work.fdPersonInfo.fdThirdLevelDepartment.fdName}
				</c:when>
				<c:otherwise>
					
				</c:otherwise>
			</c:choose>
		</list:data-column>
		<list:data-column headerClass="width100" col="fdAffiliatedCompany" title="${ lfn:message('hr-staff:hrStaffPersonInfo.fdAffiliatedCompany') }">
		${work.fdPersonInfo.fdAffiliatedCompany}
		</list:data-column>
		<list:data-column headerClass="width100" col="fdOrgRank" title="${ lfn:message('hr-staff:hrStaffPersonInfo.fdOrgRank') }">
		${work.fdPersonInfo.fdOrgRank.fdName}
		</list:data-column>
		<!-- 其它操作 -->
		<list:data-column headerClass="width60" col="operations" title="${ lfn:message('list.operation') }" escape="false">
			<!--操作按钮 开始-->
			<div class="conf_show_more_w">
				<div class="conf_btn_edit">
					<!-- 删除 -->
					<a class="btn_txt" href="javascript:_delete('${LUI_ContextPath}/hr/staff/hr_staff_person_experience/work/hrStaffPersonExperienceWork.do?method=deleteall', '${work.fdId}')">${ lfn:message('button.delete') }</a>
				</div>
			</div>
			<!--操作按钮 结束-->
		</list:data-column>
	</list:data-columns>
	<list:data-paging currentPage="${queryPage.pageno }" pageSize="${queryPage.rowsize }" totalSize="${queryPage.totalrows }">
	</list:data-paging>
</list:data>