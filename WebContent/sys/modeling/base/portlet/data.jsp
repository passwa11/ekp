<%@ page language="java" contentType="text/json; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<list:data>
    <list:data-columns var="modelingPortletCfg" list="${queryPage.list}" varIndex="status" custom="false">
        <list:data-column property="fdId" />
        <list:data-column col="index">
            ${status+1}
        </list:data-column>
        <list:data-column property="fdName" title="${lfn:message('sys-modeling-base:modelingBusiness.fdName')}" />
        <list:data-column col="fdFormat" title="${lfn:message('sys-modeling-base:modelingPortletCfg.fdFormat')}" escape="false">
            <c:out value="${modelingPortletCfg.fdFormat_text}" />
        </list:data-column>
        <list:data-column col="docCreator.name" title="${lfn:message('sys-modeling-base:modelingBusiness.docCreator')}" escape="false">
            <c:out value="${modelingPortletCfg.docCreator.fdName}" />
        </list:data-column>
        <list:data-column col="docCreator.id" escape="false">
            <c:out value="${modelingPortletCfg.docCreator.fdId}" />
        </list:data-column>
        <list:data-column col="docCreateTime" title="${lfn:message('sys-modeling-base:modelingImportConfig.createTime')}">
            <kmss:showDate value="${modelingPortletCfg.docCreateTime}" type="datetime"></kmss:showDate>
        </list:data-column>
        <%--        <list:data-column col="fdType" title="操作" >--%>
        <%--        	1-2-3--%>
        <%--     	</list:data-column>--%>
    </list:data-columns>
    <%-- 分页信息生成 --%>
    <list:data-paging page="${queryPage}" />
</list:data>
