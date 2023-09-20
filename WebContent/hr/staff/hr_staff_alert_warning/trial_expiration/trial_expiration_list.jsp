<%@ page language="java" contentType="text/json; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<list:data>
	<list:data-columns var="hrStaffPersonInfo" list="${queryPage.list }" varIndex="status">
		<list:data-column property="fdId">
		</list:data-column>
		<list:data-column col="index">
		  ${status+1}
		</list:data-column>
		<!-- 姓名-->
		<list:data-column headerClass="width100" property="fdName" title="${ lfn:message('hr-staff:hrStaffPersonInfo.fdName') }">
		</list:data-column>
		<!--账号-->
		<list:data-column headerClass="width80" col="fdLoginName" title="${ lfn:message('hr-staff:hrStaffPersonInfo.fdLoginName') }"> 
		     <c:if test="${hrStaffPersonInfo.fdOrgPerson != null}">
	    		${hrStaffPersonInfo.fdOrgPerson.fdLoginName}
	    	</c:if>
		</list:data-column>
		<!--部门-->
		<list:data-column headerClass="width200" col="fdDeptName" title="${ lfn:message('hr-staff:hrStaffPersonInfo.fdOrgParent') }">
			<c:choose>
				<c:when test="${hrStaffPersonInfo.fdParent != null && hrStaffPersonInfo.fdParent != '' &&
				 hrStaffPersonInfo.fdParent.fdName != '' && hrStaffPersonInfo.fdParent.fdName != null && 
				 hrStaffPersonInfo.fdParent.fdParentsName != null && hrStaffPersonInfo.fdParent.fdParentsName != ''}">
					${hrStaffPersonInfo.fdParent.fdParentsName}_${hrStaffPersonInfo.fdParent.fdName}
				</c:when>
				<c:otherwise>
					${hrStaffPersonInfo.fdParent.fdName}
				</c:otherwise>
			</c:choose>
		</list:data-column> 
		<!--试用到期时间-->
		<list:data-column headerClass="width80" col="fdTrialExpirationTime" title="${ lfn:message('hr-staff:hrStaffPersonInfo.fdTrialExpirationTime') }"> 
		     <c:if test="${hrStaffPersonInfo.fdTrialExpirationTime != null}">
	    		<sunbor:date value="${hrStaffPersonInfo.fdTrialExpirationTime}" ></sunbor:date>
	    	</c:if>
		</list:data-column>
	</list:data-columns>
	<list:data-paging currentPage="${queryPage.pageno }" pageSize="${queryPage.rowsize }" totalSize="${queryPage.totalrows }">
	</list:data-paging>
</list:data>