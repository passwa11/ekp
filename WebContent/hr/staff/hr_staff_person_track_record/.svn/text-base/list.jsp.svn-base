<%@ page language="java" contentType="text/json; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ page import="com.landray.kmss.hr.organization.model.HrOrganizationSyncSetting"%>
<%
	HrOrganizationSyncSetting syncSetting = new HrOrganizationSyncSetting();
	request.setAttribute("hrToEkpEnable", syncSetting.getHrToEkpEnable());
%>
<list:data>
	<list:data-columns var="trackRecord" list="${queryPage.list }" varIndex="status">
		<list:data-column property="fdId">
		</list:data-column>
		<list:data-column col="personInfoId">
			${trackRecord.fdPersonInfo.fdId}
		</list:data-column>
		<list:data-column title="${ lfn:message('page.serial') }" col="index">
		  ${status+1}
		</list:data-column>
		<list:data-column headerClass="width100" col="staffId" title="${ lfn:message('hr-staff:hrStaffPersonInfo.fdStaffNo') }">
		  ${trackRecord.fdPersonInfo.fdStaffNo}
		</list:data-column>
		<list:data-column headerClass="width100" col="fdName" title="${ lfn:message('hr-staff:hrStaffPersonInfo.fdName')}">
		   	 	${trackRecord.fdPersonInfo.getFdName()}
		</list:data-column>
		<list:data-column col="fdType" headerClass="width100" title="${ lfn:message('hr-staff:hrStaffTrackRecord.fdType')}">
			<c:choose>
				<c:when test="${not empty trackRecord.fdType}">
					<sunbor:enumsShow value="${trackRecord.fdType}" enumsType="hr_organization_office_type" />
				</c:when>
				<c:otherwise>
					${lfn:message('hr-organization:enums.office_type.1')}
				</c:otherwise>
			</c:choose>
        </list:data-column>
        <list:data-column col="fdStatus" headerClass="width100" title="${lfn:message('hr-organization:hrOrganizationConPost.fdType')}">
			<c:choose>
				<c:when test="${not empty trackRecord.fdStatus}">
					<sunbor:enumsShow value="${trackRecord.fdStatus}" enumsType="hr_staff_tract_record_fdStatus" />
				</c:when>
				<c:otherwise>
					
				</c:otherwise>
			</c:choose>
        </list:data-column>
		<list:data-column headerClass="width100" col="fdEntranceBeginDate" title="${ lfn:message('hr-staff:hrStaffTrackRecord.jobStartTime')}">
		    <kmss:showDate value="${trackRecord.fdEntranceBeginDate}" type="date" /> 
		</list:data-column>
		<list:data-column headerClass="width100" col="fdEntranceEndDate" title="${ lfn:message('hr-staff:hrStaffTrackRecord.jobFinishTime')}">
		    <kmss:showDate value="${trackRecord.fdEntranceEndDate}" type="date" /> 
		</list:data-column>
		<list:data-column headerClass="width100" col="fdRatifyDept" title="${ lfn:message('hr-staff:hrStaffTrackRecord.fdRatifyDept')}">
			<c:choose>
				<c:when test="${hrToEkpEnable }">
					<c:if test="${not empty  trackRecord.fdHrOrgDept}">
				    	${trackRecord.fdHrOrgDept.getFdName()}
				    </c:if>
				</c:when>
				<c:otherwise>
					<c:if test="${not empty  trackRecord.fdRatifyDept}">
				    	${trackRecord.fdRatifyDept.getFdName()}
				    </c:if>
				</c:otherwise>
			</c:choose>
		</list:data-column>	
		<list:data-column headerClass="width200" col="fdDeptName" title="${ lfn:message('hr-staff:hrStaffPersonInfo.fdOrgParent') }">
			<c:choose>
				<c:when test="${not empty trackRecord.fdPersonInfo.fdOrgParent }">
					
						${trackRecord.fdPersonInfo.fdOrgParent.fdName}
				</c:when>
				<c:otherwise>
					
				</c:otherwise>
			</c:choose>
			</list:data-column>
		<list:data-column headerClass="width100" col="fdSecondLevelDepartment" title="${ lfn:message('hr-staff:hrStaffPersonInfo.fdSecondLevelDepartment') }">
		
		<c:choose>
						<c:when test="${not empty  trackRecord.fdPersonInfo.fdSecondLevelDepartment}">
				
		${trackRecord.fdPersonInfo.fdSecondLevelDepartment.fdName}
				</c:when>
				<c:otherwise>
					
				</c:otherwise>
			</c:choose>
		</list:data-column>
		<list:data-column headerClass="width100" col="fdFirstLevelDepartment" title="${ lfn:message('hr-staff:hrStaffPersonInfo.fdFirstLevelDepartment') }">
		
		
		<c:choose>
						<c:when test="${not empty  trackRecord.fdPersonInfo.fdFirstLevelDepartment}">
				
		${trackRecord.fdPersonInfo.fdFirstLevelDepartment.fdName}
				</c:when>
				<c:otherwise>
					
				</c:otherwise>
			</c:choose>
		</list:data-column>
		<list:data-column headerClass="width100" col="fdThirdLevelDepartment" title="${ lfn:message('hr-staff:hrStaffPersonInfo.fdThirdLevelDepartment') }">
		
		
		<c:choose>
						<c:when test="${not empty  trackRecord.fdPersonInfo.fdThirdLevelDepartment}">
				
		${trackRecord.fdPersonInfo.fdThirdLevelDepartment.fdName}
				</c:when>
				<c:otherwise>
					
				</c:otherwise>
			</c:choose>
		</list:data-column>
		<list:data-column headerClass="width100" col="fdAffiliatedCompany" title="${ lfn:message('hr-staff:hrStaffPersonInfo.fdAffiliatedCompany') }">
		${trackRecord.fdPersonInfo.fdAffiliatedCompany}
		</list:data-column>
		<list:data-column headerClass="width100" col="fdOrgRank" title="${ lfn:message('hr-staff:hrStaffPersonInfo.fdOrgRank') }">
		${trackRecord.fdPersonInfo.fdOrgRank.fdName}
		</list:data-column>
		<list:data-column headerClass="width200" col="fdOrgPostNames" title="${ lfn:message('hr-staff:hrStaffPersonInfo.fdOrgPosts') }">
			<c:forEach items="${ trackRecord.fdPersonInfo.fdOrgPosts }" varStatus="vstatus" var = "post">
			${post.fdName};
			</c:forEach>
		</list:data-column>
		<list:data-column headerClass="width100" col="fdStaffingLevel" title="${ lfn:message('hr-staff:hrStaffPersonInfo.fdStaffingLevel')}">
		    ${trackRecord.fdStaffingLevel.getFdName()}
		</list:data-column>
		<list:data-column headerClass="width100" col="fdOrgPosts" title="${ lfn:message('hr-staff:hrStaffPersonInfo.fdOrgPosts')}">
			<c:choose>
				<c:when test="${hrToEkpEnable }">
					<c:if test="${not empty  trackRecord.fdHrOrgPost}">
						${trackRecord.fdHrOrgPost.getFdName()}
					</c:if>
				</c:when>
				<c:otherwise>
					<c:if test="${not empty  trackRecord.fdOrgPosts}">
						<c:forEach items="${ trackRecord.fdOrgPosts }" varStatus="vstatus" var = "post">
							${post.fdName};
						</c:forEach>
					</c:if>
				</c:otherwise>
			</c:choose>
		</list:data-column>
		<list:data-column headerClass="width100" col="operations" title="${ lfn:message('list.operation') }" escape="false">
			<!--操作按钮 开始-->
			<div class="lui_hr_link_group">
					<!-- 删除 -->
					<span class="lui_hr_link_item ">
						<a class="lui_text_primary" href="javascript:_delete('${trackRecord.fdId}')">${ lfn:message('button.delete') }</a>
					</span>
			</div>
			<!--操作按钮 结束-->
		</list:data-column>
		
		<c:if test="${hrToEkpEnable }">
		<kmss:auth requestURL="/hr/organization/hr_organization_con_post/hrOrganizationConPost.do?method=add">
			<list:data-column headerClass="width100" col="hrOrgoperations" title="${ lfn:message('list.operation') }" escape="false">
				<!--操作按钮 开始-->
				<div class="lui_hr_link_group">
						<span class="lui_hr_link_item ">
							<a class="lui_text_primary" href="javascript:editConPost('${trackRecord.fdId}')">${ lfn:message('button.edit') }</a>
						</span>
						<c:if test="${trackRecord.fdStatus != '2' }">
							<span class="lui_hr_link_item ">
								<a class="lui_text_primary" href="javascript:finishConPost('${trackRecord.fdId}')">${ lfn:message('hr-staff:hrStaffTrackRecord.finishPostBtn')}</a>
							</span>
						</c:if>
				</div>
				<!--操作按钮 结束-->
			</list:data-column>
		</kmss:auth>
		</c:if>
	</list:data-columns>
	<list:data-paging currentPage="${queryPage.pageno }" pageSize="${queryPage.rowsize }" totalSize="${queryPage.totalrows }">
	</list:data-paging>
</list:data>