<%@ page language="java" contentType="text/json; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<list:data>
    <list:data-columns var="eopBasedataProvince" list="${queryPage.list}" varIndex="status" custom="false">
        <list:data-column property="fdId" />
        <list:data-column col="index">
            ${status+1}
        </list:data-column>

        <list:data-column property="fdName" title="${lfn:message('eop-basedata:eopBasedataProvince.fdName')}"  escape="false"/>
        <list:data-column col="fdCompanyName" title="${lfn:message('eop-basedata:eopBasedataProvince.fdCompanyList')}"  escape="false">
        	<c:forEach items="${eopBasedataProvince.fdCompanyList}" var="fdCompany" varStatus="status">
        		<c:if test="${status.index!=0}">;</c:if>
        		${fdCompany.fdName}
        	</c:forEach>
        </list:data-column>
        <list:data-column property="fdCode" title="${lfn:message('eop-basedata:eopBasedataProvince.fdCode')}" escape="false" />
        <list:data-column col="fdIsAvailable.name" title="${lfn:message('eop-basedata:eopBasedataProvince.fdIsAvailable')}">
            <sunbor:enumsShow value="${eopBasedataProvince.fdIsAvailable}" enumsType="common_yesno" />
        </list:data-column>
        <list:data-column col="fdCountry.name" title="${lfn:message('eop-basedata:eopBasedataProvince.fdCountry')}" escape="false">
            <c:out value="${eopBasedataProvince.fdCountry.fdName}" />
        </list:data-column>
        <list:data-column col="fdCountry.id" escape="false">
            <c:out value="${eopBasedataProvince.fdCountry.fdId}" />
        </list:data-column>
    </list:data-columns>
    <%-- 分页信息生成 --%>
    <list:data-paging page="${queryPage}" />
</list:data>
