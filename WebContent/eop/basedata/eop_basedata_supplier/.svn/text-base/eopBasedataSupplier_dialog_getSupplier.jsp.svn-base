<%@ page language="java" contentType="text/json; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<list:data>
    <list:data-columns var="eopBasedataSupplier" list="${queryPage.list}" varIndex="status">
        <list:data-column property="fdId" />
        <list:data-column col="index">
            ${status+1}
        </list:data-column>

        <list:data-column property="fdName" title="${lfn:message('eop-basedata:eopBasedataSupplier.fdName')}"  escape="false"/>
        <list:data-column property="fdCode" title="${lfn:message('eop-basedata:eopBasedataSupplier.fdCode')}"  escape="false"/>
        <list:data-column col="fdIsAvailable.name" title="${lfn:message('eop-basedata:eopBasedataSupplier.fdIsAvailable')}">
            <sunbor:enumsShow value="${eopBasedataSupplier.fdIsAvailable}" enumsType="common_yesno" />
        </list:data-column>
        <list:data-column col="fdIsAvailable">
            <c:out value="${eopBasedataSupplier.fdIsAvailable}" />
        </list:data-column>
        <list:data-column property="fdTaxNo" title="${lfn:message('eop-basedata:eopBasedataSupplier.fdTaxNo')}"  escape="false"/>
        <list:data-column property="fdErpNo" title="${lfn:message('eop-basedata:eopBasedataSupplier.fdErpNo')}"  escape="false"/>
        <list:data-column col="docCreator.name" title="${lfn:message('eop-basedata:eopBasedataSupplier.docCreator')}" escape="false">
            <c:out value="${eopBasedataSupplier.docCreator.fdName}" />
        </list:data-column>
        <list:data-column col="docCreator.id" escape="false">
            <c:out value="${eopBasedataSupplier.docCreator.fdId}" />
        </list:data-column>
        <list:data-column col="docCreateTime" title="${lfn:message('eop-basedata:eopBasedataSupplier.docCreateTime')}">
            <kmss:showDate value="${eopBasedataSupplier.docCreateTime}" type="datetime"></kmss:showDate>
        </list:data-column>
        <list:data-column col="fdPhoneNo">
	        <c:set var="fdPhoneNo" value=""/>
	        <c:forEach items="${eopBasedataSupplier.fdContactPerson }" var="fdContactPerson" varStatus="vstatus">
	       		<c:if test="${fdContactPerson.fdIsfirst == 'true' && vstatus.index == 0}">
	       			<c:set var="fdPhoneNo" value="${fdContactPerson.fdPhone}"/>
	       			${fdContactPerson.fdPhone}
	       		</c:if>
	        </c:forEach>
        	<c:if test="${empty fdPhoneNo }">
        		${eopBasedataSupplier.fdContactPerson[0].fdPhone }
        	</c:if>
        </list:data-column>
    </list:data-columns>
    <%-- 分页信息生成 --%>
        <list:data-paging page="${queryPage}" />
</list:data>
