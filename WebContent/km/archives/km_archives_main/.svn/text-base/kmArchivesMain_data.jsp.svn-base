<%@ page language="java" contentType="text/json; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<list:data>
    <list:data-columns var="kmArchivesMain" list="${queryPage.list}" varIndex="status">
        <list:data-column property="fdId" />
        <list:data-column col="index">
            ${status+1}
        </list:data-column>

        <list:data-column col="docSubject" escape="false" title="${lfn:message('km-archives:kmArchivesMain.docSubject')}">
        	<span class="com_subject"><c:out value="${kmArchivesMain.docSubject }"/></span>
        </list:data-column>
        <list:data-column property="docNumber" title="${lfn:message('km-archives:kmArchivesMain.docNumber')}" />
        <list:data-column col="docCreator.fdName" title="${lfn:message('km-archives:kmArchivesMain.docCreator')}" escape="false">
            <c:out value="${kmArchivesMain.docCreator.fdName}" />
        </list:data-column>
        <list:data-column col="docCreator.fdId" escape="false">
            <c:out value="${kmArchivesMain.docCreator.fdId}" />
        </list:data-column>
        <list:data-column col="fdFileDate" title="${lfn:message('km-archives:kmArchivesMain.fdFileDate')}">
            <kmss:showDate value="${kmArchivesMain.fdFileDate}" type="date"></kmss:showDate>
        </list:data-column>
        <!-- 创建时间 -->
        <list:data-column col="docCreateTime" title="${lfn:message('km-archives:kmArchivesMain.docCreateTime')}">
            <kmss:showDate value="${kmArchivesMain.docCreateTime}" type="date"></kmss:showDate>
        </list:data-column>
        <list:data-column col="fdValidityDate" title="${lfn:message('km-archives:kmArchivesMain.fdValidityDate')}">
            <kmss:showDate value="${kmArchivesMain.fdValidityDate}" type="date"></kmss:showDate>
        </list:data-column>
        <list:data-column col="fdLibrary" title="${lfn:message('km-archives:kmArchivesMain.fdLibrary')}" escape="false">
            <c:out value="${kmArchivesMain.fdLibrary}" />
        </list:data-column>
        <!-- lbpm_main -->
        <list:data-column col="lbpm_main_listcolumn_node" title="${lfn:message('km-archives:lbpm.currentNode') }" escape="false">
            <kmss:showWfPropertyValues var="nodevalue" idValue="${kmArchivesMain.fdId}" propertyName="nodeName" />
            <div title="${nodevalue}">
                <c:out value="${nodevalue}"></c:out>
            </div>
        </list:data-column>
        <!-- lbpm_main -->
        <list:data-column col="lbpm_main_listcolumn_handler" title="${lfn:message('km-archives:lbpm.currentHandler') }" escape="false">
            <kmss:showWfPropertyValues var="handlerValue" idValue="${kmArchivesMain.fdId}" propertyName="handlerName" />
            <div style="font-weight:bold;" title="${handlerValue}">
                <c:out value="${handlerValue}"></c:out>
            </div>
        </list:data-column>
    </list:data-columns>
    <%-- 分页信息生成 --%>
        <list:data-paging page="${queryPage}" />
</list:data>
