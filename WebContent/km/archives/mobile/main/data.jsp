<%@ page language="java" contentType="text/json; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@page import="com.landray.kmss.km.archives.model.KmArchivesConfig"%>
<%
	KmArchivesConfig kmArchivesConfig = new KmArchivesConfig();
	pageContext.setAttribute("fdDefaultRange", kmArchivesConfig.getFdDefaultRange());
%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ taglib uri="/WEB-INF/KmssConfig/sys/person/person.tld" prefix="person"%>
<list:data>
	<list:data-columns var="kmArchivesMain" list="${queryPage.list }" varIndex="status" mobile="true">
		<list:data-column property="fdId">
		</list:data-column>
		<%-- 档案名称 --%>
        <list:data-column col="title">
        	<c:out value="${kmArchivesMain.docSubject}" />
        </list:data-column>
        <%-- 所属分类 --%>
        <list:data-column col="categoryName">
        	<c:out value="${kmArchivesMain.docTemplate.fdName}"></c:out>
        </list:data-column>
        <%-- 档案有效期 --%>
        <list:data-column col="fdValidityDate" title="${lfn:message('km-archives:kmArchivesMain.fdValidityDate')}">
            <kmss:showDate value="${kmArchivesMain.fdValidityDate}" type="date"></kmss:showDate>
        </list:data-column>
        <%-- 默认授权范围 --%>
        <list:data-column col="fdDefaultRange" title="授权范围">
        	<c:out value="${pageScope.fdDefaultRange}" />
        </list:data-column>
    </list:data-columns>
    <list:data-paging currentPage="${queryPage.pageno }"
		pageSize="${queryPage.rowsize }" totalSize="${queryPage.totalrows }">
	</list:data-paging>
</list:data>