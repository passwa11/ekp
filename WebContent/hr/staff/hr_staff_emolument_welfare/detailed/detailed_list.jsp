<%@ page language="java" contentType="text/json; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<list:data>
	<list:data-columns var="detailed" list="${queryPage.list }" varIndex="status">
		<list:data-column property="fdId">
		</list:data-column>
		<list:data-column col="index">
			${status+1}
		</list:data-column>
		<!-- 姓名（账号）-->
		<list:data-column headerClass="width100" col="nameAccount" title="${ lfn:message('hr-staff:hrStaffPersonExperience.nameAccount') }">
			${detailed.fdPersonInfo.nameAccount}
		</list:data-column>
		<!--相关流程-->
		<list:data-column headerClass="width200" col="fdRelatedProcess" title="${ lfn:message('hr-staff:hrStaffEmolumentWelfareDetalied.fdRelatedProcess') }" escape="false">
			<c:if test="${fn:length(detailed.fdRelatedProcess) > 0}">
				<a href="${LUI_ContextPath}${detailed.fdRelatedProcess}" target="_blank">
					<bean:message bundle="hr-staff" key="hrStaffEmolumentWelfareDetalied.relatedProcess" arg0="${detailed.fdPersonInfo.fdName}"/>
				</a>
			</c:if>
		</list:data-column>
		<!--调整日期-->
		<list:data-column headerClass="width100" col="fdAdjustDate" title="${ lfn:message('hr-staff:hrStaffEmolumentWelfareDetalied.fdAdjustDate') }">
			<kmss:showDate value="${detailed.fdAdjustDate}" type="datetime" />
		</list:data-column>
		<!--调整前薪酬-->
		<list:data-column headerClass="width80" property="fdBeforeEmolument" title="${ lfn:message('hr-staff:hrStaffEmolumentWelfareDetalied.fdBeforeEmolument') }">
		</list:data-column>
		<!--调整金额-->
		<list:data-column headerClass="width80" property="fdAdjustAmount" title="${ lfn:message('hr-staff:hrStaffEmolumentWelfareDetalied.fdAdjustAmount') }">
		</list:data-column>
		<!--请假天数-->
		<list:data-column headerClass="width80" property="fdAfterEmolument" title="${ lfn:message('hr-staff:hrStaffEmolumentWelfareDetalied.fdAfterEmolument') }">
		</list:data-column>

		<!--状态-->
		<list:data-column headerClass="width80" col="fdIsEffective" title="${ lfn:message('hr-staff:hrStaffEntry.fdStatus') }">
			<c:choose>
				<c:when test="${not empty detailed.fdIsEffective}">
					<c:choose>
						<c:when test="${detailed.fdIsEffective =='false'}">
							<c:out value="未生效"></c:out>
						</c:when>
						<c:otherwise>
							<c:out value="已生效"></c:out>
						</c:otherwise>
					</c:choose>
				</c:when>
				<c:otherwise>
					已生效
				</c:otherwise>
			</c:choose>
		</list:data-column>

	</list:data-columns>
	<list:data-paging currentPage="${queryPage.pageno }" pageSize="${queryPage.rowsize }" totalSize="${queryPage.totalrows }">
	</list:data-paging>
</list:data>