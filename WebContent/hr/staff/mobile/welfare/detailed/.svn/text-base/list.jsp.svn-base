<%@ page language="java" contentType="text/json; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<list:data>
	<list:data-columns var="detailed" list="${queryPage.list }" varIndex="status" mobile="true">
		<list:data-column property="fdId">
		</list:data-column>
		<!-- 姓名（账号）-->
		<list:data-column col="nameAccount" title="${ lfn:message('hr-staff:hrStaffPersonExperience.nameAccount') }">
		    ${detailed.fdPersonInfo.nameAccount}
		</list:data-column>
		<!--相关流程-->
		<list:data-column col="fdRelatedProcess" title="${ lfn:message('hr-staff:hrStaffEmolumentWelfareDetalied.fdRelatedProcess') }" escape="false">
			<c:if test="${fn:length(detailed.fdRelatedProcess) > 0}">
			    <bean:message bundle="hr-staff" key="hrStaffEmolumentWelfareDetalied.relatedProcess" arg0="${detailed.fdPersonInfo.fdName}"/>
		   	</c:if>
		</list:data-column>
		<!--调整日期-->
		<list:data-column col="fdAdjustDate" title="${ lfn:message('hr-staff:hrStaffEmolumentWelfareDetalied.fdAdjustDate') }">
			<kmss:showDate value="${detailed.fdAdjustDate}" type="datetime" /> 
		</list:data-column> 
		<!--调整前薪酬-->
		<list:data-column property="fdBeforeEmolument" title="${ lfn:message('hr-staff:hrStaffEmolumentWelfareDetalied.fdBeforeEmolument') }"> 
		</list:data-column>
		<!--调整金额-->
		<list:data-column property="fdAdjustAmount" title="${ lfn:message('hr-staff:hrStaffEmolumentWelfareDetalied.fdAdjustAmount') }"> 
		</list:data-column>
		<!--调整后薪酬-->
		<list:data-column property="fdAfterEmolument" title="${ lfn:message('hr-staff:hrStaffEmolumentWelfareDetalied.fdAfterEmolument') }"> 
		</list:data-column>
	</list:data-columns>
	<list:data-paging currentPage="${queryPage.pageno }" pageSize="${queryPage.rowsize }" totalSize="${queryPage.totalrows }">
	</list:data-paging>
</list:data>