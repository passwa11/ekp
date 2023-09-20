<%@ page language="java" contentType="text/json; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<list:data>
	<list:data-columns var="hrStaffPersonInfo" list="${queryPage.list }" varIndex="status">
		<list:data-column col="fdId">
			${hrStaffPersonInfo.fdId}
		</list:data-column>
		<list:data-column col="fdIsAvailable">
			${hrStaffPersonInfo.fdPerson.fdIsAvailable}
		</list:data-column>
		<list:data-column col="imgUrl">
			${urlJson[hrStaffPersonInfo.fdPerson.fdId]}
		</list:data-column>
		<list:data-column col="index">
		  ${status+1}
		</list:data-column>
		<list:data-column headerClass="width160" col="fdType" title="${ lfn:message('hr-staff:hrStaffPersonInfo.fdName') }" escape="false">
			<c:choose>
				<c:when test="${hrStaffPersonInfo.fdType == '2' }">
					2
				</c:when>
				<c:otherwise>
					1
				</c:otherwise>
			</c:choose>
		</list:data-column>
		<!-- 姓名-->
		<list:data-column headerClass="width160" col="fdName" title="${ lfn:message('hr-staff:hrStaffPersonInfo.fdName') }" escape="false">
		<div class="list_name clearFix" >
			<span class="com_subject">
				<c:out value="${hrStaffPersonInfo.fdPerson.fdName}" />
			</span>
				<c:choose>
					<c:when test="${hrStaffPersonInfo.fdType == '2' }">
						<span class="post_part">兼岗</span>
					</c:when>
					<c:otherwise>
						<!-- <span class="post_main">主岗</span>-->
					</c:otherwise>
				</c:choose>
				<c:if test="${hrStaffPersonInfo.fdOrgPost.fdIsKey}">
					<span class="post_key">关键</span>
				</c:if>
				<c:if test="${hrStaffPersonInfo.fdOrgPost.fdIsSecret}">
					<span class="post_secret">涉密</span>
				</c:if>	
			</div>					
		</list:data-column>
		<!-- 性别-->
		<list:data-column headerClass="width100"  col="fdSex" title="${ lfn:message('hr-staff:hrStaffPersonInfo.fdSex') }" escape="false">
			<c:choose>
				<c:when test="${param.IsHrOrg }">
			      	<sunbor:enumsShow value="${hrStaffPersonInfo.fdPerson.fdSex}" enumsType="sys_org_person_sex" />
			    </c:when>
			    <c:otherwise>
			       <c:out value="${hrStaffPersonInfo.fdPerson.fdSex}" />
			    </c:otherwise>
			</c:choose>
		</list:data-column>
		<list:data-column col="personInfoName" title="${ lfn:message('hr-staff:hrStaffPersonInfo.fdName') }">
			<c:out value="${hrStaffPersonInfo.fdPerson.fdName}" />
		</list:data-column>
	
		<!--部门-->
		<list:data-column headerClass="width200" col="fdDeptName" title="${ lfn:message('hr-staff:hrStaffPersonInfo.fdOrgParent') }">
			${hrStaffPersonInfo.fdPerson.fdOrgParentsName}
			<c:if test="${empty hrStaffPersonInfo.fdPerson.fdOrgParentsName}">
				${hrStaffPersonInfo.fdPerson.fdOrgParentDeptName}
			</c:if>
		</list:data-column>
		<!--工号-->
		<list:data-column headerClass="width100" property="fdPerson.fdStaffNo" title="${ lfn:message('hr-staff:hrStaffPersonInfo.fdStaffNo') }">
		</list:data-column>
		<!--员工状态-->
		<list:data-column headerClass="width100" col="fdStatus" title="${ lfn:message('hr-staff:hrStaffPersonInfo.fdStatus') }">
			<sunbor:enumsShow value="${ hrStaffPersonInfo.fdPerson.fdStatus }" enumsType="hrStaffPersonInfo_fdStatus" />
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
		<list:data-column headerClass="width200" col="fdOrgPostNames" title="${ lfn:message('hr-staff:hrStaffPersonInfo.fdOrgPosts') }">
			${hrStaffPersonInfo.fdOrgPost.fdName}
		</list:data-column>
		<!--入职时间-->
		<list:data-column headerClass="width100" col="fdEntryTime" title="${ lfn:message('hr-staff:hrStaffPersonInfo.fdEntryTime') }">
		    <kmss:showDate value="${hrStaffPersonInfo.fdPerson.fdEntryTime}" type="date" /> 
		</list:data-column>
		<!--转正时间-->
		<list:data-column headerClass="width100" col="fdPositiveTime" title="${ lfn:message('hr-staff:hrStaffPersonInfo.fdPositiveTime') }">
		    <kmss:showDate value="${hrStaffPersonInfo.fdPerson.fdPositiveTime}" type="date" /> 
		</list:data-column>
		<list:data-column headerClass="width100" col="fdOrder" title="${ lfn:message('hr-staff:hrStaff.fdOrder') }">
		   ${hrStaffPersonInfo.fdOrder}
		</list:data-column>
		<list:data-column headerClass="width100" col="fdReportLeaderName" title="${ lfn:message('hr-staff:hrStaffPersonInfo.fdReportLeader') }">
		   ${hrStaffPersonInfo.fdPerson.fdReportLeader.fdName}
		</list:data-column>					
	</list:data-columns>
	<list:data-paging currentPage="${queryPage.pageno }" pageSize="${queryPage.rowsize }" totalSize="${queryPage.totalrows }">
	</list:data-paging>
</list:data>