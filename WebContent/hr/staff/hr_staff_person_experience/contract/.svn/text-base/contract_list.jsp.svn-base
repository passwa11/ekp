<%@ page language="java" contentType="text/json; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ page import="com.landray.kmss.util.StringUtil,com.landray.kmss.util.SpringBeanUtil,com.landray.kmss.hr.staff.service.IHrStaffContractTypeService,com.landray.kmss.hr.staff.model.HrStaffPersonExperienceContract,com.landray.kmss.hr.staff.util.HrStaffPersonUtil" %>
<list:data>
	<list:data-columns var="contract" list="${queryPage.list }" varIndex="status">
		<list:data-column property="fdId">
		</list:data-column>
		<list:data-column col="personInfoId">
			${contract.fdPersonInfo.fdId}
		</list:data-column>
		<list:data-column title="${ lfn:message('page.serial') }" col="index">
		  ${status+1}
		</list:data-column>
		
		
		<list:data-column headerClass="width100" col="fdSecondLevelDepartment" title="${ lfn:message('hr-staff:hrStaffPersonInfo.fdSecondLevelDepartment') }">
		
		<c:choose>
						<c:when test="${not empty contract.fdPersonInfo.fdSecondLevelDepartment}">
				
		${contract.fdPersonInfo.fdSecondLevelDepartment.fdName}
				</c:when>
				<c:otherwise>
					
				</c:otherwise>
			</c:choose>
		</list:data-column>
		<list:data-column headerClass="width100" col="fdFirstLevelDepartment" title="${ lfn:message('hr-staff:hrStaffPersonInfo.fdFirstLevelDepartment') }">
		
		
		<c:choose>
						<c:when test="${not empty contract.fdPersonInfo.fdFirstLevelDepartment}">
				
		${contract.fdPersonInfo.fdFirstLevelDepartment.fdName}
				</c:when>
				<c:otherwise>
					
				</c:otherwise>
			</c:choose>
		</list:data-column>
		<list:data-column headerClass="width100" col="fdThirdLevelDepartment" title="${ lfn:message('hr-staff:hrStaffPersonInfo.fdThirdLevelDepartment') }">
		
		
		<c:choose>
						<c:when test="${not empty contract.fdPersonInfo.fdThirdLevelDepartment}">
				
		${contract.fdPersonInfo.fdThirdLevelDepartment.fdName}
				</c:when>
				<c:otherwise>
					
				</c:otherwise>
			</c:choose>
		</list:data-column>
		<list:data-column headerClass="width100" col="staffId" title="${ lfn:message('hr-staff:hrStaffPersonInfo.fdStaffNo') }">
		  ${contract.fdPersonInfo.fdStaffNo}
		</list:data-column>
		<!-- 姓名（账号）-->
		<list:data-column headerClass="width100" col="nameAccount" title="${ lfn:message('hr-staff:hrStaffPersonExperience.nameAccount') }" escape="false">
			<span class="com_subject">${contract.fdPersonInfo.nameAccount}</span>
		</list:data-column>
		<list:data-column headerClass="width200" col="fdDeptName" title="${ lfn:message('hr-staff:hrStaffPersonInfo.fdOrgParent') }">
			<c:choose>
				<c:when test="${not empty contract.fdPersonInfo.fdOrgParent }">
					
						${contract.fdPersonInfo.fdOrgParent.fdName}
				</c:when>
				<c:otherwise>
					
				</c:otherwise>
			</c:choose>
			</list:data-column>
		<!--合同名称-->
		<list:data-column headerClass="width200" col="fdContractPeriod" title="${ lfn:message('hr-staff:hrStaffTrackRecord.fdContractPeriod') }"> 
		<c:choose>
				<c:when test="${not empty  contract.fdContractYear || not empty  contract.fdContractMonth}">
						${contract.fdContractYear}${contract.fdContractMonth}
				</c:when>
				<c:otherwise>
					无固定期限
				</c:otherwise>
			</c:choose>
		</list:data-column>
		<list:data-column headerClass="width200" property="fdName" title="${ lfn:message('hr-staff:hrStaffPersonExperience.contract.fdName') }"> 
		</list:data-column>
		<!--合同开始时间-->
		<list:data-column headerClass="width100" col="fdBeginDate" title="${ lfn:message('hr-staff:hrStaffPersonExperience.contract.fdBeginDate') }">
		    <kmss:showDate value="${contract.fdBeginDate}" type="date" /> 
		</list:data-column>
		<!--合同到期时间-->
		<list:data-column headerClass="width100" col="fdEndDate" title="${ lfn:message('hr-staff:hrStaffPersonExperience.contract.fdEndDate') }">
			<c:choose>
				<c:when test="${contract.fdIsLongtermContract == true }">
					${ lfn:message('hr-staff:hrStaffPersonExperience.contract.fdIsLongtermContract.1') }
				</c:when>
				<c:otherwise>
					<kmss:showDate value="${contract.fdEndDate}" type="date" />
				</c:otherwise>
			</c:choose>
		</list:data-column>
		<!--备注-->
		<list:data-column headerClass="width160" col="fdMemo" title="${ lfn:message('hr-staff:hrStaffPersonExperience.fdMemo') }" escape="false">
			<span style="width:250px" class="textEllipsis" title="${ lfn:escapeHtml(contract.fdMemo) }">${ lfn:escapeHtml(contract.fdMemo) }</span>
		</list:data-column>
		<list:data-column col="remark" escape="false">
			${ lfn:escapeHtml(contract.fdMemo) }
		</list:data-column> 
		<!-- 合同类型 -->
		<list:data-column col="fdContType" title="${ lfn:message('hr-staff:hrStaffPersonExperience.contract.fdContType') }">
			<%
				IHrStaffContractTypeService service = (IHrStaffContractTypeService)SpringBeanUtil.getBean("hrStaffContractTypeService");
				HrStaffPersonExperienceContract contract = (HrStaffPersonExperienceContract)pageContext.getAttribute("contract");
				String fdContType = contract.getFdContType();
				if(service.checkExist(fdContType)){%>
					<c:out value="${contract.fdContType }"></c:out>
				<%}else if(StringUtil.isNotNull(fdContType)){
					String fdRelatedProcess = contract.getFdRelatedProcess();
					String[] str = fdContType.split("\\~");
					if(str.length > 1)
						out.println(HrStaffPersonUtil.getText(str[0], str[1], fdRelatedProcess));
				}
			%>
		</list:data-column>
		<!-- 签订标识 -->
		<list:data-column col="fdSignType" title="${ lfn:message('hr-staff:hrStaffPersonExperience.contract.fdSignType') }">
			<c:choose>
				<c:when test="${ contract.fdSignType eq '1' or contract.fdSignType eq '2'}">
					<sunbor:enumsShow value="${ contract.fdSignType }" enumsType="hrStaffPersonExperienceContract_fdSignType" />
				</c:when>
				<c:otherwise>
					<%
						HrStaffPersonExperienceContract contract = (HrStaffPersonExperienceContract)pageContext.getAttribute("contract");
						String fdSignType = contract.getFdSignType();
						if(StringUtil.isNotNull(fdSignType)){
							String fdRelatedProcess = contract.getFdRelatedProcess();
							String[] str = fdSignType.split("\\~");
							if(str.length > 1)
								out.println(HrStaffPersonUtil.getText(str[0], str[1], fdRelatedProcess));
						}
					%>
				</c:otherwise>
			</c:choose>
		</list:data-column>
		<!-- 合同办理时间 -->
		<list:data-column col="fdHandleDate" title="${ lfn:message('hr-staff:hrStaffPersonExperience.contract.fdHandleDate') }">
			<kmss:showDate value="${contract.fdHandleDate}" type="date" />
		</list:data-column>
		<!-- 合同状态 -->
		<list:data-column col="fdContStatus" title="${ lfn:message('hr-staff:hrStaffPersonExperience.contract.fdContStatus') }">
			<c:choose>
				<c:when test="${empty contract.fdContStatus }">
					<bean:message bundle="hr-staff" key="hrStaffPersonExperience.contract.fdContStatus.1" />
				</c:when>
				<c:otherwise>
					<sunbor:enumsShow value="${ contract.fdContStatus }" enumsType="hrStaffPersonExperienceContract_fdContStatus" />
				</c:otherwise>
			</c:choose>
		</list:data-column>
		<!-- 合同解除时间 -->
		<list:data-column col="fdCancelDate" title="${ lfn:message('hr-staff:hrStaffPersonExperience.contract.fdCancelDate') }">
			<kmss:showDate value="${contract.fdCancelDate}" type="date" />
		</list:data-column>
		<!-- 合同解除原因 -->
		<list:data-column col="fdCancelReason" title="${ lfn:message('hr-staff:hrStaffPersonExperience.contract.fdCancelReason') }">
			<span style="width:250px" class="textEllipsis" title="${ lfn:escapeHtml(contract.fdCancelReason) }">${ lfn:escapeHtml(contract.fdCancelReason) }</span>
		</list:data-column>
		<!-- 其它操作 -->
		<list:data-column headerClass="width60" col="operations" title="${ lfn:message('list.operation') }" escape="false">
			<!--操作按钮 开始-->
			<div class="conf_show_more_w">
				<div class="conf_btn_edit">
					<!-- 删除 -->
					<a class="btn_txt" href="javascript:_delete('${LUI_ContextPath}/hr/staff/hr_staff_person_experience/contract/hrStaffPersonExperienceContract.do?method=deleteall', '${contract.fdId}')">${ lfn:message('button.delete') }</a>
				</div>
			</div>
			<!--操作按钮 结束-->
		</list:data-column>
		
		<!--门户补充  start-->
		<!--姓名->
		<list:data-column headerClass="width100" col="fdPersonName" title="${ lfn:message('hr-staff:hrStaffPersonInfo.fdName') }">
		    ${contract.fdPersonInfo.fdName}
		</list:data-column> 
		<!--部门-->
		<!--岗位-->
		<list:data-column headerClass="width200" col="fdOrgPostNames" title="${ lfn:message('hr-staff:hrStaffPersonInfo.fdOrgPosts') }">
			<c:forEach items="${ contract.fdPersonInfo.fdOrgPosts }" varStatus="vstatus" var = "post">
			${post.fdName};
			</c:forEach>
		</list:data-column>
		<list:data-column headerClass="width100" col="fdAffiliatedCompany" title="${ lfn:message('hr-staff:hrStaffPersonInfo.fdAffiliatedCompany') }">
		${contract.fdPersonInfo.fdAffiliatedCompany}
		</list:data-column>
		<list:data-column headerClass="width100" col="fdOrgRank" title="${ lfn:message('hr-staff:hrStaffPersonInfo.fdOrgRank') }">
		${contract.fdPersonInfo.fdOrgRank.fdName}
		</list:data-column>
	</list:data-columns>
	<list:data-paging currentPage="${queryPage.pageno }" pageSize="${queryPage.rowsize }" totalSize="${queryPage.totalrows }">
	</list:data-paging>
</list:data>