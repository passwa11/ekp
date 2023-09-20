<%@ page language="java" contentType="text/json; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ taglib uri="/WEB-INF/KmssConfig/sys/person/person.tld" prefix="person"%>
<list:data>
	<list:data-columns var="thirdMallTemplate" list="${queryPage.data}" varIndex="status" mobile="true">
		<list:data-column property="fdId">
		</list:data-column >
	    <%-- 主题--%>	
		<list:data-column col="label" title="${ lfn:message('third-mall:thirdMallTemplate.fdName') }" escape="false">
	         <c:out value="${thirdMallTemplate.fdName}"/>
		</list:data-column>
		<%-- 图片--%>
		<list:data-column col="mobilePic" title="${ lfn:message('third-mall:thirdMallTemplate.mobilePic') }" escape="false">
	         <c:out value="${thirdMallTemplate.mobilePic}"/>
		</list:data-column>
		<%-- 行业--%>	
		<list:data-column col="fdIndustryId" title="${ lfn:message('third-mall:thirdMallTemplate.fdIndustryId') }" escape="false">
	         <c:out value="${thirdMallTemplate.fdIndustryId}"/>
		</list:data-column>
	</list:data-columns>
	
	<list:data-paging currentPage="${queryPage.pageno }"
		pageSize="${queryPage.rowsize }" totalSize="${queryPage.totalrows }">
	</list:data-paging>
</list:data>