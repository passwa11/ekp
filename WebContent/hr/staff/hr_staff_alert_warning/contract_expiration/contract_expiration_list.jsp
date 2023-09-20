<%@ page language="java" contentType="text/json; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<list:data>
	<list:data-columns var="contract" list="${queryPage.list }" varIndex="status">
		<list:data-column property="fdPersonInfo.fdId">
		</list:data-column>
		<list:data-column col="index">
		  ${status+1}
		</list:data-column>
		<!-- 姓名 -->
		<list:data-column headerClass="width100" col="fdPersonName" title="${ lfn:message('hr-staff:hrStaffPersonInfo.fdName') }">
		    ${contract.fdPersonInfo.fdName}
		</list:data-column>
			<!-- 账号 -->
		<list:data-column headerClass="width100" col="fdLoginName" title="${ lfn:message('hr-staff:hrStaffPersonInfo.fdLoginName') }">
		   <c:if test="${contract.fdPersonInfo.fdOrgPerson != null}">
	    		${contract.fdPersonInfo.fdOrgPerson.fdLoginName}
	    	</c:if> 
		</list:data-column>
		<!--合同名称-->
		<list:data-column headerClass="width80" property="fdName" title="${ lfn:message('hr-staff:hrStaffPersonExperience.contract.fdName') }"> 
		</list:data-column>
		<!--合同开始时间-->
		<list:data-column headerClass="width120" col="fdBeginDate" title="${ lfn:message('hr-staff:hrStaffPersonExperience.contract.fdBeginDate') }">
		    <kmss:showDate value="${contract.fdBeginDate}" type="date" /> 
		</list:data-column>
		<!--合同到期时间-->
		<list:data-column headerClass="width120" col="fdEndDate" title="${ lfn:message('hr-staff:hrStaffPersonExperience.contract.fdEndDate') }">
			<c:choose>
				<c:when test="${contract.fdIsLongtermContract == true }">
					${ lfn:message('hr-staff:hrStaffPersonExperience.contract.fdIsLongtermContract.1') }
				</c:when>
				<c:otherwise>
					<kmss:showDate value="${contract.fdEndDate}" type="date" />
				</c:otherwise>
			</c:choose>
		</list:data-column>
		<!--门户补充  start-->
		<!--岗位-->
		<list:data-column headerClass="width200" col="fdOrgPostNames" title="${ lfn:message('hr-staff:hrStaffPersonInfo.fdOrgPosts') }">
			<c:forEach items="${ contract.fdPersonInfo.fdOrgPosts }" varStatus="vstatus" var = "post">
			${post.fdName};
			</c:forEach>
		</list:data-column>
	<!--部门-->
		<list:data-column headerClass="width200" col="fdDeptName" title="${ lfn:message('hr-staff:hrStaffPersonInfo.fdOrgParent') }">
			${contract.fdPersonInfo.fdOrgParentsName}
		</list:data-column>
	</list:data-columns>
	<list:data-paging currentPage="${queryPage.pageno }" pageSize="${queryPage.rowsize }" totalSize="${queryPage.totalrows }">
	</list:data-paging>
</list:data>