<%@ page language="java" contentType="text/json; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ taglib uri="/WEB-INF/KmssConfig/sys/person/person.tld" prefix="person"%>
<list:data>
	<list:data-columns var="kmArchivesBorrow" list="${queryPage.list }" varIndex="status" mobile="true">
		<list:data-column property="fdId">
		</list:data-column >
		<list:data-column col="status" title="${ lfn:message('km-archives:kmArchivesBorrow.docStatus') }" escape="false">			
			<c:choose>
				<c:when test="${kmArchivesBorrow.docStatus=='00'}">
					00
				</c:when>
				<c:when test="${kmArchivesBorrow.docStatus=='10'}">
					10
				</c:when>
				<c:when test="${kmArchivesBorrow.docStatus=='11'}">
					11
				</c:when>
				<c:when test="${kmArchivesBorrow.docStatus=='20'}">
					20
				</c:when>
				<c:when test="${kmArchivesBorrow.docStatus=='30'}">
					30
				</c:when>
				<c:when test="${kmArchivesBorrow.docStatus=='31'}">
					31
				</c:when>
			</c:choose>
		</list:data-column>
	    <%-- 主题--%>	
		<list:data-column col="label" title="${ lfn:message('km-archives:kmArchivesBorrow.docSubject') }" escape="false">
		         <c:out value="${kmArchivesBorrow.docSubject}"/>
		</list:data-column>
		 <%-- 创建者--%>
		<list:data-column col="creator" title="${ lfn:message('km-archives:kmArchivesBorrow.docCreator') }" >
		         <c:out value="${kmArchivesBorrow.docCreator.fdName}"/>
		</list:data-column>
		 <%-- 创建时间--%>
	 	<list:data-column col="created" title="${ lfn:message('km-archives:kmArchivesBorrow.docCreateTime') }">
	        <kmss:showDate value="${kmArchivesBorrow.docCreateTime}" type="date"></kmss:showDate>
      	</list:data-column>
		<%--链接--%>
		<list:data-column col="href" escape="false">
			/km/archives/km_archives_borrow/kmArchivesBorrow.do?method=view&fdId=${kmArchivesBorrow.fdId}
		</list:data-column>
	</list:data-columns>
	
	<list:data-paging currentPage="${queryPage.pageno }"
		pageSize="${queryPage.rowsize }" totalSize="${queryPage.totalrows }">
	</list:data-paging>
</list:data>
