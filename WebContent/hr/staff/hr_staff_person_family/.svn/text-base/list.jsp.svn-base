<%@ page language="java" contentType="text/json; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<list:data>
	<list:data-columns var="familyInfo" list="${queryPage.list }" varIndex="status">
		<list:data-column property="fdId">
		</list:data-column>
		<list:data-column col="personInfoId">
			${familyInfo.fdPersonInfo.fdId}
		</list:data-column>
		<list:data-column title="${ lfn:message('page.serial') }" col="index">
		  ${status+1}
		</list:data-column>
		<list:data-column headerClass="width100" col="fdStaffFdName" title="${ lfn:message('hr-staff:hrStaffPerson.family.eployee.name') }">
		    ${familyInfo.fdPersonInfo.fdName}
		</list:data-column>
		<list:data-column headerClass="width100" col="fdRelated" title="${ lfn:message('hr-staff:hrStaffPerson.family.relationship') }">
		    ${familyInfo.getFdRelated()}
		</list:data-column>
		<list:data-column headerClass="width100" col="fdName" title="${ lfn:message('hr-staff:hrStaffPerson.family.related.name') }">
		     ${familyInfo.getFdName()} 
		</list:data-column>
		<list:data-column headerClass="width100" col="fdCompany" title="${ lfn:message('hr-staff:hrStaffPerson.family.company') }">
		    ${familyInfo.getFdCompany()} 
		</list:data-column>
		<list:data-column headerClass="width100" col="fdOccupation" title="${ lfn:message('hr-staff:hrStaffPerson.family.occupation') }">
		    ${familyInfo.getFdOccupation()} 
		</list:data-column>
		<list:data-column headerClass="width100" col="fdMemo" title="${ lfn:message('hr-staff:hrStaffPerson.family.memo') }">
			${familyInfo.getFdMemo()}
		</list:data-column>
			<list:data-column headerClass="width200" col="fdDeptName" title="${ lfn:message('hr-staff:hrStaffPersonInfo.fdOrgParent') }">
			<c:choose>
				<c:when test="${not empty familyInfo.fdPersonInfo.fdOrgParent }">
					
						${familyInfo.fdPersonInfo.fdOrgParent.fdName}
				</c:when>
				<c:otherwise>
					
				</c:otherwise>
			</c:choose>
			</list:data-column>	
			<list:data-column headerClass="width200" col="fdDeptName" title="${ lfn:message('hr-staff:hrStaffPersonInfo.fdOrgParent') }">
			<c:choose>
				<c:when test="${not empty familyInfo.fdPersonInfo.fdOrgParent }">
					
						${familyInfo.fdPersonInfo.fdOrgParent.fdName}
				</c:when>
				<c:otherwise>
					
				</c:otherwise>
			</c:choose>
			</list:data-column>
			<list:data-column headerClass="width200" col="fdOrgPostNames" title="${ lfn:message('hr-staff:hrStaffPersonInfo.fdOrgPosts') }">
			<c:forEach items="${ familyInfo.fdPersonInfo.fdOrgPosts }" varStatus="vstatus" var = "post">
			${post.fdName};
			</c:forEach>
		</list:data-column>
			<list:data-column headerClass="width100" col="fdSecondLevelDepartment" title="${ lfn:message('hr-staff:hrStaffPersonInfo.fdSecondLevelDepartment') }">
		
		<c:choose>
						<c:when test="${not empty familyInfo.fdPersonInfo.fdSecondLevelDepartment}">
				
		${familyInfo.fdPersonInfo.fdSecondLevelDepartment.fdName}
				</c:when>
				<c:otherwise>
					
				</c:otherwise>
			</c:choose>
		</list:data-column>
		<list:data-column headerClass="width100" col="fdFirstLevelDepartment" title="${ lfn:message('hr-staff:hrStaffPersonInfo.fdFirstLevelDepartment') }">
		
		
		<c:choose>
						<c:when test="${not empty familyInfo.fdPersonInfo.fdFirstLevelDepartment}">
				
		${familyInfo.fdPersonInfo.fdFirstLevelDepartment.fdName}
				</c:when>
				<c:otherwise>
					
				</c:otherwise>
			</c:choose>
		</list:data-column>
		<list:data-column headerClass="width100" col="fdThirdLevelDepartment" title="${ lfn:message('hr-staff:hrStaffPersonInfo.fdThirdLevelDepartment') }">
		
		
		<c:choose>
						<c:when test="${not empty familyInfo.fdPersonInfo.fdThirdLevelDepartment}">
				
		${familyInfo.fdPersonInfo.fdThirdLevelDepartment.fdName}
				</c:when>
				<c:otherwise>
					
				</c:otherwise>
			</c:choose>
		</list:data-column>
		<list:data-column headerClass="width100" col="fdAffiliatedCompany" title="${ lfn:message('hr-staff:hrStaffPersonInfo.fdAffiliatedCompany') }">
		${familyInfo.fdPersonInfo.fdAffiliatedCompany}
		</list:data-column>
		<list:data-column headerClass="width100" col="fdOrgRank" title="${ lfn:message('hr-staff:hrStaffPersonInfo.fdOrgRank') }">
		${familyInfo.fdPersonInfo.fdOrgRank.fdName}
		</list:data-column>
		<list:data-column headerClass="width80" col="operations" title="${ lfn:message('list.operation') }" escape="false">
			<!--操作按钮 开始-->
			<div class="conf_show_more_w">
				<div class="conf_btn_edit">
					<!-- 删除 -->
					<a class="btn_txt" href="javascript:_delete('${familyInfo.fdId}')">${ lfn:message('button.delete') }</a>
				</div>
			</div>
			<!--操作按钮 结束-->
		</list:data-column>
	</list:data-columns>
	<list:data-paging currentPage="${queryPage.pageno }" pageSize="${queryPage.rowsize }" totalSize="${queryPage.totalrows }">
	</list:data-paging>
</list:data>