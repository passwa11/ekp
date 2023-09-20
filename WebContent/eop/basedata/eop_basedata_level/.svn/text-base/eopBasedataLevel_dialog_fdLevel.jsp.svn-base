<%@ page language="java" contentType="text/json; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<list:data>
    <list:data-columns var="eopBasedataLevel" list="${queryPage.list}" varIndex="status">
        <list:data-column property="fdId" />
        <list:data-column property="fdName" title="${lfn:message('eop-basedata:eopBasedataLevel.fdName')}"  escape="false"/>
        <list:data-column property="fdCode" title="${lfn:message('eop-basedata:eopBasedataLevel.fdCode')}"  escape="false"/>
        <list:data-column col="fdPersonList" title="${lfn:message('eop-basedata:eopBasedataLevel.fdPersonList')}"  escape="false">
        	<c:forEach items="${eopBasedataLevel.fdPersonList }" var="person">
        		${person.fdName};
        	</c:forEach>
        </list:data-column>
        <list:data-column col="index">
            ${status+1}
        </list:data-column>

    </list:data-columns>
    <%-- 分页信息生成 --%>
        <list:data-paging page="${queryPage}" />
</list:data>
