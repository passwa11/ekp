<%@ page language="java" contentType="text/json; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<list:data>
    <list:data-columns var="eopBasedataVoucherType" list="${queryPage.list}" varIndex="status">
        <list:data-column property="fdId" />
        <list:data-column col="index">
            ${status+1}
        </list:data-column>

        <list:data-column property="fdName" title="${lfn:message('eop-basedata:eopBasedataVoucherType.fdName')}"  escape="false"/>
        <list:data-column property="fdCode" title="${lfn:message('eop-basedata:eopBasedataVoucherType.fdCode')}"  escape="false"/>
        <list:data-column col="fdCompany.name" title="${lfn:message('eop-basedata:eopBasedataVoucherType.fdCompany')}" escape="false">
            <c:forEach var="comp" items="${eopBasedataVoucherType.fdCompanyList}">
            	${comp.fdName};
            </c:forEach>
        </list:data-column>
        <list:data-column col="fdCompany.id" escape="false">
            <c:forEach var="comp" items="${eopBasedataVoucherType.fdCompanyList}">
            	${comp.fdId};
            </c:forEach>
        </list:data-column>
        <list:data-column col="fdIsAvailable.name" title="${lfn:message('eop-basedata:eopBasedataVoucherType.fdIsAvailable')}">
            <sunbor:enumsShow value="${eopBasedataVoucherType.fdIsAvailable}" enumsType="common_yesno" />
        </list:data-column>
        <list:data-column col="fdIsAvailable">
            <c:out value="${eopBasedataVoucherType.fdIsAvailable}" />
        </list:data-column>
        <list:data-column col="docCreator.name" title="${lfn:message('eop-basedata:eopBasedataVoucherType.docCreator')}" escape="false">
            <c:out value="${eopBasedataVoucherType.docCreator.fdName}" />
        </list:data-column>
        <list:data-column col="docCreator.id" escape="false">
            <c:out value="${eopBasedataVoucherType.docCreator.fdId}" />
        </list:data-column>
        <list:data-column col="docCreateTime" title="${lfn:message('eop-basedata:eopBasedataVoucherType.docCreateTime')}">
            <kmss:showDate value="${eopBasedataVoucherType.docCreateTime}" type="datetime"></kmss:showDate>
        </list:data-column>
    </list:data-columns>
    <%-- 分页信息生成 --%>
        <list:data-paging page="${queryPage}" />
</list:data>
