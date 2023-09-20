<%@ page language="java" contentType="text/json; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ taglib uri="/WEB-INF/KmssConfig/sys/person/person.tld" prefix="person"%>
<list:data>
	<list:data-columns var="kmReviewTemplate" list="${queryPage.list}" varIndex="status" mobile="true">
		<list:data-column property="fdId">
		</list:data-column >
	    <%-- 主题--%>	
		<list:data-column col="label" title="${ lfn:message('sys-news:sysNewsMain.docSubject') }" escape="false">
		         <c:out value="${kmReviewTemplate.fdName}"/>
		</list:data-column>
		  <%-- 分类--%>	
		<list:data-column col="docCagetory" title="${ lfn:message('sys-news:sysNewsMain.docSubject') }" escape="false">
		         <c:out value="${kmReviewTemplate.docCategory.fdName}"/>
		</list:data-column>
		 <%-- 描述--%>	
		<list:data-column col="fdDesc" title="${ lfn:message('sys-news:sysNewsMain.docSubject') }" escape="false">
		         <c:out value="${kmReviewTemplate.fdDesc}"/>
		</list:data-column>
		 <%-- 创建者--%>
		<list:data-column col="creator" title="${ lfn:message('sys-news:sysNewsMain.docCreatorId') }" >
		         <c:out value="${kmReviewTemplate.docCreator.fdName}"/>
		</list:data-column>
		 <%-- 创建者头像--%>
		<list:data-column col="icon" escape="false">
			    <person:headimageUrl personId="${kmReviewTemplate.docCreator.fdId}" size="m" />
		</list:data-column>
		 <%-- 创建时间--%>
	 	<list:data-column col="created" title="${ lfn:message('sys-news:sysNewsMain.docCreateTime') }">
	        <kmss:showDate value="${kmReviewTemplate.docCreateTime}" type="date"></kmss:showDate>
      	</list:data-column>
	</list:data-columns>
	
	<list:data-paging currentPage="${queryPage.pageno }"
		pageSize="${queryPage.rowsize }" totalSize="${queryPage.totalrows }">
	</list:data-paging>
</list:data>