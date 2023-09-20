<%@ page language="java" contentType="text/json; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<list:data>
	<list:data-columns var="hrStaffRatifyLog" list="${queryPage.list }" varIndex="status">
		<list:data-column property="fdId">
		</list:data-column>
		<list:data-column col="index">
		  ${status+1}
		</list:data-column>
		<!--人员-->
		<list:data-column headerClass="width200" col="fdPersonName" title="姓名">
			${hrStaffRatifyLog.fdOrgPerson.fdName}
		</list:data-column>
		<list:data-column headerClass="width200" col="fdDeptName" title="部门">
			${hrStaffRatifyLog.fdOrgPerson.fdParentsName}
		</list:data-column>
		<!--异动前部门-->
		<list:data-column headerClass="width200" col="fdOldDept" title="异动前部门">
			${hrStaffRatifyLog.fdRatifyOldDept.fdName}
		</list:data-column>
		<!--异动前岗位-->
		<list:data-column headerClass="width200" col="fdOldPost" title="异动前岗位">
			<c:forEach items="${ hrStaffRatifyLog.fdRatifyOldPosts }" varStatus="vstatus" var = "post">
			${post.fdName};
			</c:forEach>
		</list:data-column>
		<!--异动后部门-->
		<list:data-column headerClass="width200" col="fdNewDept" title="异动后部门">
			${hrStaffRatifyLog.fdRatifyDept.fdName}
		</list:data-column>
		<!--异动后岗位-->
		<list:data-column headerClass="width200" col="fdNewPost" title="异动后岗位">
			<c:forEach items="${ hrStaffRatifyLog.fdRatifyPosts }" varStatus="vstatus" var = "post">
			${post.fdName};
			</c:forEach>
		</list:data-column>
		<!-- 异动生效时间-->
		<list:data-column headerClass="width100" col="fdRatifyDate" title="异动生效时间">
		   <kmss:showDate value="${hrStaffRatifyLog.fdRatifyDate}" type="date" /> 
		</list:data-column>
		
	</list:data-columns>
	<list:data-paging currentPage="${queryPage.pageno }" pageSize="${queryPage.rowsize }" totalSize="${queryPage.totalrows }">
	</list:data-paging>
</list:data>